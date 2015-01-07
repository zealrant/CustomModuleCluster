/*    1:     */ package bunch.engine;
/*    2:     */ 
/*    3:     */ import bunch.BunchPreferences;
/*    4:     */ import bunch.Cluster;
/*    5:     */ import bunch.ClusteringMethod;
/*    6:     */ import bunch.ClusteringMethodFactory;
/*    7:     */ import bunch.Configuration;
/*    8:     */ import bunch.DependencyFileParser;
/*    9:     */ import bunch.GAConfiguration;
/*   10:     */ import bunch.Graph;
/*   11:     */ import bunch.GraphOutput;
/*   12:     */ import bunch.GraphOutputFactory;
/*   13:     */ import bunch.NAHCConfiguration;
/*   14:     */ import bunch.NextLevelGraph;
/*   15:     */ import bunch.Node;
/*   16:     */ import bunch.ObjectiveFunctionCalculatorFactory;
/*   17:     */ import bunch.Parser;
/*   18:     */ import bunch.ParserFactory;
/*   19:     */ import bunch.SATechnique;
/*   20:     */ import bunch.SwingWorker;
/*   21:     */ import bunch.api.BunchAsyncNotify;
/*   22:     */ import bunch.api.BunchMDG;
/*   23:     */ import bunch.api.ProgressCallbackInterface;
/*   24:     */ import bunch.stats.StatsManager;
/*   25:     */ import bunch.util.BunchUtilities;
/*   26:     */ import bunch.util.MQCalculator;
/*   27:     */ import bunch.util.PrecisionRecallCalculator;
/*   28:     */ import java.awt.event.ActionEvent;
/*   29:     */ import java.awt.event.ActionListener;
/*   30:     */ import java.beans.Beans;
/*   31:     */ import java.io.File;
/*   32:     */ import java.util.ArrayList;
/*   33:     */ import java.util.Collection;
/*   34:     */ import java.util.Enumeration;
/*   35:     */ import java.util.Hashtable;
/*   36:     */ import java.util.StringTokenizer;
/*   37:     */ import javax.swing.Timer;
/*   38:     */ 
/*   39:     */ public class BunchEngine
/*   40:     */ {
/*   41:  51 */   Hashtable bunchArgs = null;
/*   42:  52 */   Hashtable results = null;
/*   43:     */   ClusteringMethod clusteringMethod_d;
/*   44:     */   GraphOutput graphOutput_d;
/*   45:     */   Graph initialGraph_d;
/*   46:     */   BunchPreferences preferences_d;
/*   47:  57 */   StatsManager stats = StatsManager.getInstance();
/*   48:     */   Configuration configuration_d;
/*   49:     */   ProgressCallbackInterface cbInterfaceObj;
/*   50:     */   int callbackFrequency;
/*   51:     */   long startTime;
/*   52:     */   long endTime;
/*   53:  63 */   long totalTime = 0L;
/*   54:  64 */   Cluster baseCluster = null;
/*   55:  65 */   ArrayList clusterList = null;
/*   56:  66 */   Timer timeoutTimer = null;
/*   57:  67 */   Thread clusteringProcessThread = null;
/*   58:  68 */   int reflexiveEdgeCount = 0;
/*   59:     */   String precision;
/*   60:     */   String recall;
/*   61:     */   String MQCalcMdgFileName;
/*   62:     */   String MQCalcSilFileName;
/*   63:     */   String MQCalcValue;
/*   64:     */   
/*   65:     */   String getFileDelims()
/*   66:     */   {
/*   67:  81 */     String delims = "";
/*   68:  82 */     String def_delims = (String)this.bunchArgs.get("MDGParserDelimeters");
/*   69:  83 */     if (def_delims != null) {
/*   70:  84 */       delims = delims + def_delims;
/*   71:     */     }
/*   72:  86 */     if (((String)this.bunchArgs.get("MDGParserUseSpaces")).equalsIgnoreCase("TRUE")) {
/*   73:  87 */       delims = " " + delims;
/*   74:     */     }
/*   75:  88 */     if (((String)this.bunchArgs.get("MDGParserUseTabs")).equalsIgnoreCase("TRUE")) {
/*   76:  89 */       delims = "\t" + delims;
/*   77:     */     }
/*   78:  91 */     return delims;
/*   79:     */   }
/*   80:     */   
/*   81:     */   Hashtable getSAConfigHTFromString(String saKey)
/*   82:     */   {
/*   83:  96 */     Hashtable h = new Hashtable();
/*   84:     */     
/*   85:  98 */     StringTokenizer st = new StringTokenizer(saKey, ",");
/*   86:  99 */     while (st.hasMoreElements())
/*   87:     */     {
/*   88: 101 */       String key = st.nextToken();
/*   89: 102 */       StringTokenizer keyTokenizer = new StringTokenizer(key, "=");
/*   90: 103 */       if (keyTokenizer.countTokens() == 2)
/*   91:     */       {
/*   92: 105 */         String keyValue = keyTokenizer.nextToken();
/*   93: 106 */         Double value = new Double(keyTokenizer.nextToken());
/*   94: 107 */         h.put(keyValue, value);
/*   95:     */       }
/*   96:     */     }
/*   97: 110 */     return h;
/*   98:     */   }
/*   99:     */   
/*  100:     */   Collection parseStringToCollection(String saKey)
/*  101:     */   {
/*  102: 115 */     ArrayList al = new ArrayList();
/*  103:     */     
/*  104: 117 */     StringTokenizer st = new StringTokenizer(saKey, ",");
/*  105: 118 */     while (st.hasMoreElements()) {
/*  106: 120 */       al.add(st.nextToken());
/*  107:     */     }
/*  108: 122 */     return al;
/*  109:     */   }
/*  110:     */   
/*  111:     */   private String[] stringArrayFromString(String in)
/*  112:     */   {
/*  113: 127 */     if (in == null) {
/*  114: 127 */       return null;
/*  115:     */     }
/*  116: 129 */     StringTokenizer st = new StringTokenizer(in, " ,\n\r");
/*  117: 130 */     int howMany = st.countTokens();
/*  118: 131 */     String[] retArray = new String[howMany];
/*  119: 132 */     int idx = 0;
/*  120: 133 */     while (st.hasMoreElements()) {
/*  121: 134 */       retArray[(idx++)] = st.nextToken();
/*  122:     */     }
/*  123: 136 */     if (idx == 0) {
/*  124: 136 */       return null;
/*  125:     */     }
/*  126: 137 */     return retArray;
/*  127:     */   }
/*  128:     */   
/*  129:     */   public void arrangeLibrariesClientsAndSuppliers(Graph g, Hashtable special)
/*  130:     */   {
/*  131: 150 */     Object[] suppliers = null;
/*  132: 151 */     Object[] clients = null;
/*  133: 152 */     Object[] centrals = null;
/*  134: 153 */     Object[] libraries = null;
/*  135: 155 */     if (special.get("OmnipresentCentral") != null) {
/*  136: 156 */       centrals = ((Collection)special.get("OmnipresentCentral")).toArray();
/*  137:     */     }
/*  138: 157 */     if (special.get("OmnipresentClient") != null) {
/*  139: 158 */       clients = ((Collection)special.get("OmnipresentClient")).toArray();
/*  140:     */     }
/*  141: 159 */     if (special.get("OmnipresentSupplier") != null) {
/*  142: 160 */       suppliers = ((Collection)special.get("OmnipresentSupplier")).toArray();
/*  143:     */     }
/*  144: 161 */     if (special.get("LibraryModule") != null) {
/*  145: 162 */       libraries = ((Collection)special.get("LibraryModule")).toArray();
/*  146:     */     }
/*  147: 164 */     Node[] nodeList = g.getNodes();
/*  148: 165 */     Node[] originalList = nodeList;
/*  149: 168 */     for (int j = 0; j < originalList.length; j++)
/*  150:     */     {
/*  151: 169 */       if (suppliers != null) {
/*  152: 170 */         for (int i = 0; i < suppliers.length; i++)
/*  153:     */         {
/*  154: 171 */           String name = originalList[j].getName();
/*  155: 172 */           if (name.equals((String)suppliers[i]))
/*  156:     */           {
/*  157: 173 */             originalList[j].setType(2);
/*  158: 174 */             break;
/*  159:     */           }
/*  160:     */         }
/*  161:     */       }
/*  162: 178 */       if (clients != null) {
/*  163: 179 */         for (int i = 0; i < clients.length; i++)
/*  164:     */         {
/*  165: 180 */           String name = originalList[j].getName();
/*  166: 181 */           if (name.equals((String)clients[i]))
/*  167:     */           {
/*  168: 182 */             originalList[j].setType(1);
/*  169: 183 */             break;
/*  170:     */           }
/*  171:     */         }
/*  172:     */       }
/*  173: 187 */       if (centrals != null) {
/*  174: 188 */         for (int i = 0; i < centrals.length; i++)
/*  175:     */         {
/*  176: 189 */           String name = originalList[j].getName();
/*  177: 190 */           if (name.equals((String)centrals[i]))
/*  178:     */           {
/*  179: 191 */             originalList[j].setType(3);
/*  180: 192 */             break;
/*  181:     */           }
/*  182:     */         }
/*  183:     */       }
/*  184: 197 */       if (libraries != null) {
/*  185: 198 */         for (int i = 0; i < libraries.length; i++)
/*  186:     */         {
/*  187: 199 */           String name = originalList[j].getName();
/*  188: 200 */           if (name.equals((String)libraries[i]))
/*  189:     */           {
/*  190: 201 */             originalList[j].setType(4);
/*  191: 202 */             break;
/*  192:     */           }
/*  193:     */         }
/*  194:     */       }
/*  195:     */     }
/*  196: 208 */     int deadNodes = 0;
/*  197: 210 */     for (int i = 0; i < originalList.length; i++) {
/*  198: 211 */       if (originalList[i].getType() == 0)
/*  199:     */       {
/*  200: 212 */         boolean noNormalDeps = true;
/*  201: 213 */         int[] tmpDeps = originalList[i].getDependencies();
/*  202: 214 */         int[] tmpBeDeps = originalList[i].getBackEdges();
/*  203: 215 */         int client = 0;
/*  204: 216 */         int supplier = 0;
/*  205: 217 */         int central = 0;
/*  206: 218 */         int library = 0;
/*  207: 219 */         for (int j = 0; j < tmpDeps.length; j++)
/*  208:     */         {
/*  209: 221 */           if ((originalList[tmpDeps[j]].getType() == 0) || (originalList[tmpDeps[j]].getType() >= 128))
/*  210:     */           {
/*  211: 224 */             noNormalDeps = false;
/*  212: 225 */             break;
/*  213:     */           }
/*  214: 229 */           switch (originalList[tmpDeps[j]].getType())
/*  215:     */           {
/*  216:     */           case 1: 
/*  217: 232 */             client++; break;
/*  218:     */           case 2: 
/*  219: 234 */             supplier++; break;
/*  220:     */           case 3: 
/*  221: 236 */             central++; break;
/*  222:     */           case 4: 
/*  223: 238 */             library++;
/*  224:     */           }
/*  225:     */         }
/*  226: 242 */         for (int j = 0; j < tmpBeDeps.length; j++)
/*  227:     */         {
/*  228: 244 */           if ((originalList[tmpBeDeps[j]].getType() == 0) || (originalList[tmpBeDeps[j]].getType() >= 128))
/*  229:     */           {
/*  230: 247 */             noNormalDeps = false;
/*  231: 248 */             break;
/*  232:     */           }
/*  233: 252 */           switch (originalList[tmpBeDeps[j]].getType())
/*  234:     */           {
/*  235:     */           case 1: 
/*  236: 255 */             client++; break;
/*  237:     */           case 2: 
/*  238: 257 */             supplier++; break;
/*  239:     */           case 3: 
/*  240: 259 */             central++; break;
/*  241:     */           case 4: 
/*  242: 261 */             library++;
/*  243:     */           }
/*  244:     */         }
/*  245: 265 */         if (noNormalDeps == true)
/*  246:     */         {
/*  247: 267 */           deadNodes++;
/*  248: 268 */           int n1 = Math.max(client, supplier);
/*  249: 269 */           int n2 = Math.max(central, library);
/*  250: 270 */           int max = Math.max(n1, n2);
/*  251: 271 */           int type = 1;
/*  252: 273 */           if (max == client) {
/*  253: 273 */             type = 1;
/*  254:     */           }
/*  255: 274 */           if (max == supplier) {
/*  256: 274 */             type = 2;
/*  257:     */           }
/*  258: 275 */           if (max == central) {
/*  259: 275 */             type = 3;
/*  260:     */           }
/*  261: 276 */           if (max == library) {
/*  262: 276 */             type = 4;
/*  263:     */           }
/*  264: 277 */           originalList[i].setType(128 + max);
/*  265:     */         }
/*  266:     */       }
/*  267:     */     }
/*  268: 283 */     nodeList = new Node[originalList.length - (clients.length + suppliers.length + deadNodes + centrals.length + libraries.length)];
/*  269:     */     
/*  270:     */ 
/*  271: 286 */     int j = 0;
/*  272:     */     
/*  273: 288 */     Hashtable normal = new Hashtable();
/*  274: 290 */     for (int i = 0; i < originalList.length; i++) {
/*  275: 291 */       if (originalList[i].getType() == 0)
/*  276:     */       {
/*  277: 292 */         normal.put(new Integer(originalList[i].getId()), new Integer(j));
/*  278: 293 */         nodeList[(j++)] = originalList[i].cloneNode();
/*  279:     */       }
/*  280:     */     }
/*  281: 299 */     for (int i = 0; i < nodeList.length; i++)
/*  282:     */     {
/*  283: 301 */       nodeList[i].nodeID = i;
/*  284: 302 */       int[] deps = nodeList[i].getDependencies();
/*  285: 303 */       int[] beDeps = nodeList[i].getBackEdges();
/*  286: 304 */       int[] weight = nodeList[i].getWeights();
/*  287: 305 */       int[] beWeight = nodeList[i].getBeWeights();
/*  288: 306 */       int depsRemoveCount = 0;
/*  289: 307 */       int beDeptsRemoveCount = 0;
/*  290: 310 */       for (int z = 0; z < deps.length; z++)
/*  291:     */       {
/*  292: 312 */         Integer tmpAssoc = (Integer)normal.get(new Integer(deps[z]));
/*  293: 313 */         if (tmpAssoc == null)
/*  294:     */         {
/*  295: 314 */           deps[z] = -1;
/*  296: 315 */           depsRemoveCount++;
/*  297:     */         }
/*  298:     */         else
/*  299:     */         {
/*  300: 317 */           deps[z] = tmpAssoc.intValue();
/*  301:     */         }
/*  302:     */       }
/*  303: 321 */       for (int z = 0; z < beDeps.length; z++)
/*  304:     */       {
/*  305: 322 */         Integer tmpAssoc = (Integer)normal.get(new Integer(beDeps[z]));
/*  306: 323 */         if (tmpAssoc == null)
/*  307:     */         {
/*  308: 324 */           beDeps[z] = -1;
/*  309: 325 */           beDeptsRemoveCount++;
/*  310:     */         }
/*  311:     */         else
/*  312:     */         {
/*  313: 327 */           beDeps[z] = tmpAssoc.intValue();
/*  314:     */         }
/*  315:     */       }
/*  316: 331 */       if (depsRemoveCount > 0)
/*  317:     */       {
/*  318: 333 */         int[] newDeps = new int[deps.length - depsRemoveCount];
/*  319: 334 */         int[] newWeight = new int[deps.length - depsRemoveCount];
/*  320:     */         
/*  321: 336 */         int pos = 0;
/*  322: 337 */         for (int z = 0; z < deps.length; z++) {
/*  323: 338 */           if (deps[z] != -1)
/*  324:     */           {
/*  325: 339 */             newDeps[pos] = deps[z];
/*  326: 340 */             newWeight[pos] = weight[z];
/*  327: 341 */             pos++;
/*  328:     */           }
/*  329:     */         }
/*  330: 343 */         deps = newDeps;
/*  331: 344 */         weight = newWeight;
/*  332:     */       }
/*  333: 347 */       if (beDeptsRemoveCount > 0)
/*  334:     */       {
/*  335: 349 */         int[] newBeDeps = new int[beDeps.length - beDeptsRemoveCount];
/*  336: 350 */         int[] newBeWeight = new int[beDeps.length - beDeptsRemoveCount];
/*  337:     */         
/*  338: 352 */         int pos = 0;
/*  339: 353 */         for (int z = 0; z < beDeps.length; z++) {
/*  340: 354 */           if (beDeps[z] != -1)
/*  341:     */           {
/*  342: 355 */             newBeDeps[pos] = beDeps[z];
/*  343: 356 */             newBeWeight[pos] = beWeight[z];
/*  344: 357 */             pos++;
/*  345:     */           }
/*  346:     */         }
/*  347: 359 */         beDeps = newBeDeps;
/*  348: 360 */         beWeight = newBeWeight;
/*  349:     */       }
/*  350: 363 */       nodeList[i].setDependencies(deps);
/*  351: 364 */       nodeList[i].setWeights(weight);
/*  352: 365 */       nodeList[i].setBackEdges(beDeps);
/*  353: 366 */       nodeList[i].setBeWeights(beWeight);
/*  354:     */     }
/*  355: 371 */     g.initGraph(nodeList.length);
/*  356: 372 */     g.clear();
/*  357: 373 */     g.setNodes(nodeList);
/*  358: 374 */     g.setOriginalNodes(originalList);
/*  359:     */   }
/*  360:     */   
/*  361:     */   public Hashtable getDefaultSpecialNodes(String graphName)
/*  362:     */   {
/*  363: 378 */     return getDefaultSpecialNodes(graphName, 3.0D);
/*  364:     */   }
/*  365:     */   
/*  366:     */   public Hashtable getDefaultSpecialNodes(String graphName, double threshold)
/*  367:     */   {
/*  368:     */     try
/*  369:     */     {
/*  370: 384 */       Hashtable h = new Hashtable();
/*  371: 385 */       Hashtable centrals = new Hashtable();
/*  372: 386 */       Hashtable clients = new Hashtable();
/*  373: 387 */       Hashtable suppliers = new Hashtable();
/*  374: 388 */       Hashtable libraries = new Hashtable();
/*  375:     */       
/*  376:     */ 
/*  377:     */ 
/*  378:     */ 
/*  379: 393 */       BunchPreferences prefs = (BunchPreferences)Beans.instantiate(null, "bunch.BunchPreferences");
/*  380:     */       
/*  381:     */ 
/*  382:     */ 
/*  383:     */ 
/*  384:     */ 
/*  385:     */ 
/*  386: 400 */       String parserClass = "dependency";
/*  387: 401 */       if ((graphName.endsWith(".gxl")) || (graphName.endsWith(".GXL"))) {
/*  388: 402 */         parserClass = "gxl";
/*  389:     */       }
/*  390: 404 */       Parser p = this.preferences_d.getParserFactory().getParser(parserClass);
/*  391: 405 */       p.setInput(graphName);
/*  392: 406 */       Graph g = (Graph)p.parse();
/*  393:     */       
/*  394: 408 */       Node[] nodeList = g.getNodes();
/*  395: 411 */       for (int i = 0; i < nodeList.length; i++)
/*  396:     */       {
/*  397: 412 */         String nname = nodeList[i].getName();
/*  398: 413 */         if (((nodeList[i].getDependencies() == null) || (nodeList[i].getDependencies().length == 0)) && (!clients.containsKey(nodeList[i].getName())) && (!suppliers.containsKey(nodeList[i].getName())) && (!centrals.containsKey(nodeList[i].getName()))) {
/*  399: 417 */           libraries.put(nodeList[i].getName(), nodeList[i].getName());
/*  400:     */         }
/*  401:     */       }
/*  402: 425 */       double avg = 0.0D;double sum = 0.0D;
/*  403: 426 */       for (int i = 0; i < nodeList.length; i++) {
/*  404: 427 */         if (nodeList[i].getDependencies() != null) {
/*  405: 428 */           sum += nodeList[i].getDependencies().length;
/*  406:     */         }
/*  407:     */       }
/*  408: 431 */       avg = sum / nodeList.length;
/*  409: 432 */       avg *= threshold;
/*  410: 433 */       for (int i = 0; i < nodeList.length; i++) {
/*  411: 434 */         if ((nodeList[i].getDependencies() != null) && (nodeList[i].getDependencies().length > avg) && (!libraries.containsKey(nodeList[i].getName()))) {
/*  412: 437 */           clients.put(nodeList[i].getName(), nodeList[i].getName());
/*  413:     */         }
/*  414:     */       }
/*  415: 442 */       avg = 0.0D;sum = 0.0D;
/*  416: 443 */       int[] inNum = new int[nodeList.length];
/*  417: 445 */       for (int j = 0; j < nodeList.length; j++)
/*  418:     */       {
/*  419: 446 */         int currval = 0;
/*  420: 447 */         for (int i = 0; i < nodeList.length; i++)
/*  421:     */         {
/*  422: 448 */           int[] deps = nodeList[i].getDependencies();
/*  423: 449 */           if (deps != null) {
/*  424: 450 */             for (int n = 0; n < deps.length; n++) {
/*  425: 451 */               if (deps[n] == j) {
/*  426: 452 */                 currval++;
/*  427:     */               }
/*  428:     */             }
/*  429:     */           }
/*  430:     */         }
/*  431: 457 */         inNum[j] = currval;
/*  432:     */       }
/*  433: 459 */       for (int i = 0; i < inNum.length; i++) {
/*  434: 460 */         sum += inNum[i];
/*  435:     */       }
/*  436: 462 */       avg = sum / nodeList.length;
/*  437: 463 */       avg *= threshold;
/*  438: 464 */       for (int i = 0; i < nodeList.length; i++) {
/*  439: 465 */         if ((inNum[i] > avg) && (!libraries.containsKey(nodeList[i].getName()))) {
/*  440: 467 */           suppliers.put(nodeList[i].getName(), nodeList[i].getName());
/*  441:     */         }
/*  442:     */       }
/*  443: 472 */       ArrayList clientsAL = new ArrayList(clients.values());
/*  444: 474 */       for (int i = 0; i < clientsAL.size(); i++)
/*  445:     */       {
/*  446: 475 */         String client = (String)clientsAL.get(i);
/*  447: 476 */         if (suppliers.containsKey(client)) {
/*  448: 477 */           centrals.put(client, client);
/*  449:     */         }
/*  450:     */       }
/*  451: 480 */       Enumeration e = centrals.elements();
/*  452: 481 */       while (e.hasMoreElements())
/*  453:     */       {
/*  454: 483 */         String elem = (String)e.nextElement();
/*  455: 484 */         clients.remove(elem);
/*  456: 485 */         suppliers.remove(elem);
/*  457:     */       }
/*  458: 491 */       h.put("OmnipresentCentral", centrals.values());
/*  459: 492 */       h.put("OmnipresentClient", clients.values());
/*  460: 493 */       h.put("OmnipresentSupplier", suppliers.values());
/*  461: 494 */       h.put("LibraryModule", libraries.values());
/*  462: 495 */       return h;
/*  463:     */     }
/*  464:     */     catch (Exception e)
/*  465:     */     {
/*  466: 499 */       e.printStackTrace();
/*  467:     */     }
/*  468: 500 */     return null;
/*  469:     */   }
/*  470:     */   
/*  471:     */   Hashtable getSpecialModulesFromProperties()
/*  472:     */   {
/*  473: 506 */     Hashtable h = new Hashtable();
/*  474: 507 */     ArrayList emptyList = new ArrayList();
/*  475: 508 */     boolean containsSpecial = false;
/*  476:     */     
/*  477: 510 */     emptyList.clear();
/*  478: 512 */     if (this.bunchArgs.get("OmnipresentBoth") != null)
/*  479:     */     {
/*  480: 513 */       containsSpecial = true;
/*  481: 514 */       String mods = (String)this.bunchArgs.get("OmnipresentBoth");
/*  482: 515 */       Collection c = parseStringToCollection(mods);
/*  483: 516 */       h.put("OmnipresentCentral", c);
/*  484:     */     }
/*  485:     */     else
/*  486:     */     {
/*  487: 519 */       h.put("OmnipresentCentral", emptyList);
/*  488:     */     }
/*  489: 521 */     if (this.bunchArgs.get("OmnipresentClients") != null)
/*  490:     */     {
/*  491: 522 */       containsSpecial = true;
/*  492: 523 */       String mods = (String)this.bunchArgs.get("OmnipresentClients");
/*  493: 524 */       Collection c = parseStringToCollection(mods);
/*  494: 525 */       h.put("OmnipresentClient", c);
/*  495:     */     }
/*  496:     */     else
/*  497:     */     {
/*  498: 528 */       h.put("OmnipresentClient", emptyList);
/*  499:     */     }
/*  500: 530 */     if (this.bunchArgs.get("OmnipresentSuppliers") != null)
/*  501:     */     {
/*  502: 531 */       containsSpecial = true;
/*  503: 532 */       String mods = (String)this.bunchArgs.get("OmnipresentSuppliers");
/*  504: 533 */       Collection c = parseStringToCollection(mods);
/*  505: 534 */       h.put("OmnipresentSupplier", c);
/*  506:     */     }
/*  507:     */     else
/*  508:     */     {
/*  509: 537 */       h.put("OmnipresentSupplier", emptyList);
/*  510:     */     }
/*  511: 539 */     if (this.bunchArgs.get("LibraryList") != null)
/*  512:     */     {
/*  513: 540 */       containsSpecial = true;
/*  514: 541 */       String mods = (String)this.bunchArgs.get("LibraryList");
/*  515: 542 */       Collection c = parseStringToCollection(mods);
/*  516: 543 */       h.put("LibraryModule", c);
/*  517:     */     }
/*  518:     */     else
/*  519:     */     {
/*  520: 546 */       h.put("LibraryModule", emptyList);
/*  521:     */     }
/*  522: 548 */     if (containsSpecial == true) {
/*  523: 549 */       return h;
/*  524:     */     }
/*  525: 551 */     return null;
/*  526:     */   }
/*  527:     */   
/*  528:     */   boolean initClustering()
/*  529:     */   {
/*  530:     */     try
/*  531:     */     {
/*  532: 558 */       this.clusterList = new ArrayList();
/*  533:     */       
/*  534:     */ 
/*  535: 561 */       this.preferences_d = ((BunchPreferences)Beans.instantiate(null, "bunch.BunchPreferences"));
/*  536: 565 */       if (this.bunchArgs.get("MDGInputFile") != null)
/*  537:     */       {
/*  538: 567 */         Parser p = this.preferences_d.getParserFactory().getParser("dependency");
/*  539: 568 */         p.setInput((String)this.bunchArgs.get("MDGInputFile"));
/*  540: 569 */         p.setDelims(getFileDelims());
/*  541: 570 */         this.initialGraph_d = ((Graph)p.parse());
/*  542: 571 */         this.reflexiveEdgeCount = ((DependencyFileParser)p).getReflexiveEdges();
/*  543:     */       }
/*  544: 574 */       if (this.bunchArgs.get("MDGGraphObject") != null)
/*  545:     */       {
/*  546: 576 */         BunchMDG mdgObj = (BunchMDG)this.bunchArgs.get("MDGGraphObject");
/*  547:     */         
/*  548:     */ 
/*  549: 579 */         this.initialGraph_d = BunchUtilities.toInternalGraph(mdgObj);
/*  550: 580 */         this.reflexiveEdgeCount = 0;
/*  551:     */       }
/*  552: 585 */       String userSILFile = (String)this.bunchArgs.get("UserDirectedClusterSIL");
/*  553: 586 */       if (userSILFile != null)
/*  554:     */       {
/*  555: 588 */         boolean lock = true;
/*  556: 589 */         String lockStr = (String)this.bunchArgs.get("LockUserSetClusters");
/*  557: 590 */         if ((lockStr != null) && 
/*  558: 591 */           (lockStr.equalsIgnoreCase("false"))) {
/*  559: 591 */           lock = false;
/*  560:     */         }
/*  561: 593 */         Parser cp = this.preferences_d.getParserFactory().getParser("cluster");
/*  562: 594 */         cp.setInput(userSILFile);
/*  563: 595 */         cp.setObject(this.initialGraph_d);
/*  564: 596 */         cp.parse();
/*  565: 597 */         if (lock == true) {
/*  566: 598 */           this.initialGraph_d.setDoubleLocks(true);
/*  567:     */         }
/*  568: 603 */         int[] clust = this.initialGraph_d.getClusters();
/*  569: 604 */         boolean[] locks = this.initialGraph_d.getLocks();
/*  570: 605 */         for (int i = 0; i < clust.length; i++) {
/*  571: 606 */           if (clust[i] != -1) {
/*  572: 607 */             locks[i] = true;
/*  573:     */           }
/*  574:     */         }
/*  575:     */       }
/*  576: 613 */       if (this.bunchArgs.get("SpecialModuleHashTable") != null)
/*  577:     */       {
/*  578: 615 */         Hashtable special = (Hashtable)this.bunchArgs.get("SpecialModuleHashTable");
/*  579: 616 */         arrangeLibrariesClientsAndSuppliers(this.initialGraph_d, special);
/*  580:     */       }
/*  581: 619 */       Hashtable specialFromInput = getSpecialModulesFromProperties();
/*  582: 620 */       if (specialFromInput != null) {
/*  583: 621 */         arrangeLibrariesClientsAndSuppliers(this.initialGraph_d, specialFromInput);
/*  584:     */       }
/*  585: 624 */       String clustAlg = (String)this.bunchArgs.get("ClusteringAlgorithm");
/*  586: 625 */       if (clustAlg == null) {
/*  587: 625 */         return false;
/*  588:     */       }
/*  589: 626 */       this.clusteringMethod_d = this.preferences_d.getClusteringMethodFactory().getMethod(clustAlg);
/*  590: 627 */       if (this.clusteringMethod_d == null) {
/*  591: 627 */         return false;
/*  592:     */       }
/*  593: 629 */       this.configuration_d = this.clusteringMethod_d.getConfiguration();
/*  594: 630 */       if ((this.initialGraph_d != null) && (this.configuration_d != null)) {
/*  595: 631 */         this.configuration_d.init(this.initialGraph_d);
/*  596:     */       }
/*  597: 633 */       if (clustAlg.equals("GA"))
/*  598:     */       {
/*  599: 635 */         GAConfiguration gaConfig = (GAConfiguration)this.configuration_d;
/*  600:     */         
/*  601: 637 */         String method = (String)this.bunchArgs.get("GASelectionMethod");
/*  602: 638 */         String cProb = (String)this.bunchArgs.get("GACrossoverProbability");
/*  603: 639 */         String mProb = (String)this.bunchArgs.get("GAMutationProb");
/*  604: 640 */         String popSz = (String)this.bunchArgs.get("GAPopulationSize");
/*  605: 641 */         String numGens = (String)this.bunchArgs.get("GANumGenerations");
/*  606: 643 */         if (method != null)
/*  607:     */         {
/*  608: 645 */           String tournMethod = "tournament";
/*  609: 646 */           String roulMethod = "roulette wheel";
/*  610: 647 */           if (method.equals("GASelectionMethodRoulette")) {
/*  611: 648 */             gaConfig.setMethod(roulMethod);
/*  612:     */           }
/*  613: 649 */           if (method.equals("GASelectionMethodTournament")) {
/*  614: 650 */             gaConfig.setMethod(tournMethod);
/*  615:     */           }
/*  616:     */         }
/*  617: 653 */         if (numGens != null)
/*  618:     */         {
/*  619: 655 */           int nGens = Integer.parseInt(numGens);
/*  620: 656 */           gaConfig.setNumOfIterations(nGens);
/*  621:     */         }
/*  622: 659 */         if (cProb != null)
/*  623:     */         {
/*  624: 661 */           double crossProb = Double.parseDouble(cProb);
/*  625: 662 */           gaConfig.setCrossoverThreshold(crossProb);
/*  626:     */         }
/*  627: 665 */         if (mProb != null)
/*  628:     */         {
/*  629: 667 */           double mutationProb = Double.parseDouble(mProb);
/*  630: 668 */           gaConfig.setMutationThreshold(mutationProb);
/*  631:     */         }
/*  632: 671 */         if (popSz != null)
/*  633:     */         {
/*  634: 673 */           int pSize = Integer.parseInt(popSz);
/*  635: 674 */           gaConfig.setPopulationSize(pSize);
/*  636:     */         }
/*  637:     */       }
/*  638: 678 */       if (clustAlg.equals("SAHC"))
/*  639:     */       {
/*  640: 680 */         Integer popSz = (Integer)this.bunchArgs.get("SAHCPopulationSize");
/*  641: 682 */         if (popSz != null) {
/*  642: 683 */           this.configuration_d.setPopulationSize(popSz.intValue());
/*  643:     */         }
/*  644:     */       }
/*  645: 686 */       if (clustAlg.equals("NAHC"))
/*  646:     */       {
/*  647: 688 */         NAHCConfiguration c = (NAHCConfiguration)this.configuration_d;
/*  648: 689 */         if (this.bunchArgs.get("NAHCRandomizePct") != null)
/*  649:     */         {
/*  650: 691 */           Integer randomize = (Integer)this.bunchArgs.get("NAHCRandomizePct");
/*  651: 692 */           c.setRandomizePct(randomize.intValue());
/*  652:     */         }
/*  653: 695 */         if (this.bunchArgs.get("NAHCHillClimbPct") != null)
/*  654:     */         {
/*  655: 698 */           Integer hcThreshold = (Integer)this.bunchArgs.get("NAHCHillClimbPct");
/*  656:     */           
/*  657: 700 */           c.setMinPctToConsider(hcThreshold.intValue());
/*  658:     */         }
/*  659:     */       }
/*  660: 705 */       if (clustAlg.equals("NAHC"))
/*  661:     */       {
/*  662: 707 */         Integer HCPct = (Integer)this.bunchArgs.get("NAHCHillClimbPct");
/*  663: 708 */         Integer rndPct = (Integer)this.bunchArgs.get("NAHCRandomizePct");
/*  664: 709 */         Integer popSz = (Integer)this.bunchArgs.get("NAHCPopulationSize");
/*  665:     */         
/*  666: 711 */         NAHCConfiguration c = (NAHCConfiguration)this.configuration_d;
/*  667: 713 */         if (popSz != null) {
/*  668: 714 */           c.setPopulationSize(popSz.intValue());
/*  669:     */         }
/*  670: 716 */         if (HCPct != null)
/*  671:     */         {
/*  672: 718 */           c.setMinPctToConsider(HCPct.intValue());
/*  673: 720 */           if (rndPct != null)
/*  674:     */           {
/*  675: 721 */             c.setRandomizePct(rndPct.intValue());
/*  676:     */           }
/*  677:     */           else
/*  678:     */           {
/*  679: 724 */             int pctTmp = 100 - HCPct.intValue();
/*  680: 725 */             c.setRandomizePct(pctTmp);
/*  681:     */           }
/*  682:     */         }
/*  683: 729 */         String SAClass = (String)this.bunchArgs.get("NAHCSimulatedAnnealingClass");
/*  684: 730 */         if (SAClass != null)
/*  685:     */         {
/*  686: 732 */           SATechnique saHandler = (SATechnique)Beans.instantiate(null, SAClass);
/*  687: 733 */           if (saHandler != null)
/*  688:     */           {
/*  689: 735 */             String saHandlerArgs = (String)this.bunchArgs.get("NAHCSimulatedAnnealingConfig");
/*  690: 736 */             if (saHandlerArgs != null)
/*  691:     */             {
/*  692: 738 */               Hashtable saConfig = getSAConfigHTFromString(saHandlerArgs);
/*  693: 739 */               saHandler.setConfig(saConfig);
/*  694:     */             }
/*  695: 741 */             c.setSATechnique(saHandler);
/*  696:     */           }
/*  697:     */         }
/*  698:     */       }
/*  699: 747 */       if (((String)this.bunchArgs.get("ClusteringApproach")).equalsIgnoreCase("ClustApproachAgglomerative")) {
/*  700: 748 */         this.initialGraph_d.setIsClusterTree(true);
/*  701:     */       } else {
/*  702: 750 */         this.initialGraph_d.setIsClusterTree(false);
/*  703:     */       }
/*  704: 753 */       String objFnCalc = (String)this.bunchArgs.get("MQCalculatorClass");
/*  705: 754 */       this.preferences_d.getObjectiveFunctionCalculatorFactory().setCurrentCalculator(objFnCalc);
/*  706: 755 */       Graph.setObjectiveFunctionCalculatorFactory(this.preferences_d.getObjectiveFunctionCalculatorFactory());
/*  707: 756 */       this.initialGraph_d.setObjectiveFunctionCalculator(objFnCalc);
/*  708:     */       
/*  709:     */ 
/*  710: 759 */       this.clusteringMethod_d.initialize();
/*  711: 760 */       this.clusteringMethod_d.setGraph(this.initialGraph_d.cloneGraph());
/*  712:     */       
/*  713:     */ 
/*  714: 763 */       StatsManager.getInstance();
/*  715:     */       
/*  716:     */ 
/*  717: 766 */       this.cbInterfaceObj = ((ProgressCallbackInterface)this.bunchArgs.get("CallbackObjectReference"));
/*  718: 767 */       Integer iTmp = (Integer)this.bunchArgs.get("CallbackObjectFrequency");
/*  719: 768 */       if (iTmp != null) {
/*  720: 769 */         this.callbackFrequency = iTmp.intValue();
/*  721:     */       }
/*  722: 772 */       Integer toTime = (Integer)this.bunchArgs.get("TimoutTime");
/*  723: 773 */       if (toTime != null) {
/*  724: 774 */         this.timeoutTimer = new Timer(toTime.intValue(), new TimeoutTimer());
/*  725:     */       }
/*  726: 777 */       this.graphOutput_d = null;
/*  727: 778 */       String outputMode = (String)this.bunchArgs.get("OutputFormat");
/*  728: 779 */       if ((outputMode != null) || (!outputMode.equalsIgnoreCase("NullOutputFormat")))
/*  729:     */       {
/*  730: 781 */         String driver = null;
/*  731: 782 */         if (outputMode.equalsIgnoreCase("DotOutputFormat")) {
/*  732: 783 */           driver = "Dotty";
/*  733: 784 */         } else if (outputMode.equalsIgnoreCase("TextOutputFormat")) {
/*  734: 785 */           driver = "Text";
/*  735: 786 */         } else if (outputMode.equalsIgnoreCase("GXLOutputFormat")) {
/*  736: 787 */           driver = "GXL";
/*  737:     */         }
/*  738: 789 */         if (driver != null)
/*  739:     */         {
/*  740: 791 */           String outFileName = (String)this.bunchArgs.get("OutputFile");
/*  741: 792 */           if (outFileName == null) {
/*  742: 793 */             outFileName = (String)this.bunchArgs.get("MDGInputFile");
/*  743:     */           }
/*  744: 795 */           this.graphOutput_d = this.preferences_d.getGraphOutputFactory().getOutput(driver);
/*  745:     */           
/*  746: 797 */           String outTree = (String)this.bunchArgs.get("OutputTree");
/*  747: 798 */           if (outTree != null) {
/*  748: 800 */             if (outTree.equalsIgnoreCase("true")) {
/*  749: 802 */               this.graphOutput_d.setNestedLevels(true);
/*  750:     */             }
/*  751:     */           }
/*  752: 806 */           this.graphOutput_d.setBaseName(outFileName);
/*  753: 807 */           this.graphOutput_d.setBasicName(outFileName);
/*  754: 808 */           String outputFileName = this.graphOutput_d.getBaseName();
/*  755: 809 */           String outputPath = (String)this.bunchArgs.get("OutputDirectory");
/*  756: 810 */           if (outputPath != null)
/*  757:     */           {
/*  758: 812 */             File f = new File(this.graphOutput_d.getBaseName());
/*  759: 813 */             String filename = f.getName();
/*  760: 814 */             outputFileName = outputPath + filename;
/*  761:     */           }
/*  762: 816 */           this.graphOutput_d.setCurrentName(outputFileName);
/*  763:     */         }
/*  764:     */       }
/*  765:     */     }
/*  766:     */     catch (Exception e1)
/*  767:     */     {
/*  768: 822 */       e1.printStackTrace();
/*  769: 823 */       return false;
/*  770:     */     }
/*  771: 825 */     return true;
/*  772:     */   }
/*  773:     */   
/*  774:     */   public Graph getBestGraph()
/*  775:     */   {
/*  776: 830 */     if (this.clusteringMethod_d == null) {
/*  777: 831 */       return null;
/*  778:     */     }
/*  779: 833 */     return this.clusteringMethod_d.getBestGraph().cloneGraph();
/*  780:     */   }
/*  781:     */   
/*  782:     */   boolean runClusteringAsync(final BunchAsyncNotify nObject)
/*  783:     */   {
/*  784: 839 */     nObject.setStatus(2);
/*  785: 840 */     SwingWorker worker_d = new SwingWorker()
/*  786:     */     {
/*  787:     */       public Object construct()
/*  788:     */       {
/*  789:     */         try
/*  790:     */         {
/*  791: 846 */           BunchEngine.this.runClustering();
/*  792:     */         }
/*  793:     */         catch (Exception threadEx)
/*  794:     */         {
/*  795: 848 */           threadEx.printStackTrace();
/*  796:     */         }
/*  797: 849 */         return "Done";
/*  798:     */       }
/*  799:     */       
/*  800:     */       public void interrupt()
/*  801:     */       {
/*  802: 853 */         suspend();
/*  803: 854 */         super.interrupt();
/*  804:     */       }
/*  805:     */       
/*  806:     */       public void finished()
/*  807:     */       {
/*  808: 858 */         nObject.setStatus(3);
/*  809: 859 */         nObject.notifyDone();
/*  810:     */       }
/*  811: 862 */     };
/*  812: 863 */     worker_d.setPriority(1);
/*  813: 864 */     worker_d.start();
/*  814: 865 */     nObject.setThread(worker_d.getThread());
/*  815: 866 */     return true;
/*  816:     */   }
/*  817:     */   
/*  818:     */   boolean runClustering()
/*  819:     */   {
/*  820: 871 */     if (!initClustering()) {
/*  821: 872 */       return false;
/*  822:     */     }
/*  823: 874 */     BunchAsyncNotify notifyClass = null;
/*  824:     */     
/*  825:     */ 
/*  826:     */ 
/*  827:     */ 
/*  828:     */ 
/*  829:     */ 
/*  830: 881 */     ExecuteClusteringEngine ce = new ExecuteClusteringEngine();
/*  831:     */     
/*  832:     */ 
/*  833:     */ 
/*  834:     */ 
/*  835:     */ 
/*  836:     */ 
/*  837: 888 */     Cluster bestC = this.clusteringMethod_d.getBestCluster();
/*  838: 889 */     this.baseCluster = this.clusteringMethod_d.getBestCluster().cloneCluster();
/*  839: 890 */     this.clusterList.add(this.clusteringMethod_d.getBestCluster().cloneCluster());
/*  840:     */     
/*  841:     */ 
/*  842: 893 */     String cApproach = (String)this.bunchArgs.get("ClusteringApproach");
/*  843: 894 */     if (cApproach.equalsIgnoreCase("ClustApproachAgglomerative"))
/*  844:     */     {
/*  845: 896 */       Graph g = this.clusteringMethod_d.getBestGraph().cloneGraph();
/*  846:     */       
/*  847: 898 */       int[] cNames = g.getClusterNames();
/*  848: 899 */       while (cNames.length > 1)
/*  849:     */       {
/*  850: 902 */         NextLevelGraph nextL = new NextLevelGraph();
/*  851: 903 */         Graph newG = nextL.genNextLevelGraph(g);
/*  852:     */         
/*  853: 905 */         newG.setPreviousLevelGraph(g);
/*  854: 906 */         newG.setGraphLevel(g.getGraphLevel() + 1);
/*  855:     */         
/*  856: 908 */         this.clusteringMethod_d.setGraph(newG);
/*  857: 909 */         this.clusteringMethod_d.initialize();
/*  858:     */         
/*  859:     */ 
/*  860:     */ 
/*  861:     */ 
/*  862:     */ 
/*  863:     */ 
/*  864:     */ 
/*  865:     */ 
/*  866:     */ 
/*  867:     */ 
/*  868: 920 */         ce = new ExecuteClusteringEngine();
/*  869:     */         
/*  870:     */ 
/*  871:     */ 
/*  872: 924 */         bestC = this.clusteringMethod_d.getBestCluster();
/*  873: 925 */         this.clusterList.add(this.clusteringMethod_d.getBestCluster().cloneCluster());
/*  874:     */         
/*  875:     */ 
/*  876:     */ 
/*  877:     */ 
/*  878:     */ 
/*  879:     */ 
/*  880:     */ 
/*  881: 933 */         g = this.clusteringMethod_d.getBestGraph().cloneGraph();
/*  882:     */         
/*  883: 935 */         cNames = g.getClusterNames();
/*  884:     */       }
/*  885:     */     }
/*  886: 938 */     if (this.graphOutput_d != null)
/*  887:     */     {
/*  888: 940 */       this.graphOutput_d.setGraph(this.clusteringMethod_d.getBestGraph());
/*  889: 941 */       this.graphOutput_d.write();
/*  890:     */     }
/*  891: 947 */     return true;
/*  892:     */   }
/*  893:     */   
/*  894:     */   boolean runMQCalc()
/*  895:     */   {
/*  896: 952 */     this.MQCalcMdgFileName = ((String)this.bunchArgs.get("MQCalcMDGFile"));
/*  897: 953 */     this.MQCalcSilFileName = ((String)this.bunchArgs.get("MQCalcSILFile"));
/*  898: 954 */     String MQCalcClass = (String)this.bunchArgs.get("MQCalculatorClass");
/*  899:     */     
/*  900: 956 */     double mqResult = MQCalculator.CalcMQ(this.MQCalcMdgFileName, this.MQCalcSilFileName, MQCalcClass);
/*  901: 957 */     Double Dmq = new Double(mqResult);
/*  902: 958 */     this.MQCalcValue = Dmq.toString();
/*  903: 959 */     return true;
/*  904:     */   }
/*  905:     */   
/*  906:     */   boolean runPRCalc()
/*  907:     */   {
/*  908: 964 */     String clusterF = (String)this.bunchArgs.get("PRClusterDecomposition");
/*  909: 965 */     String expertF = (String)this.bunchArgs.get("PRExpertDecomposition");
/*  910:     */     
/*  911: 967 */     PrecisionRecallCalculator calc = new PrecisionRecallCalculator(expertF, clusterF);
/*  912:     */     
/*  913:     */ 
/*  914: 970 */     this.precision = calc.get_precision();
/*  915: 971 */     this.recall = calc.get_recall();
/*  916:     */     
/*  917: 973 */     return true;
/*  918:     */   }
/*  919:     */   
/*  920:     */   private int getMedianLevelNumber()
/*  921:     */   {
/*  922: 978 */     if (this.clusteringMethod_d == null) {
/*  923: 979 */       return -1;
/*  924:     */     }
/*  925: 981 */     Graph g = this.clusteringMethod_d.getBestGraph();
/*  926: 982 */     Graph medianG = g.getMedianTree();
/*  927: 983 */     return medianG.getGraphLevel();
/*  928:     */   }
/*  929:     */   
/*  930:     */   public Hashtable getResultsHT()
/*  931:     */   {
/*  932: 988 */     String runMode = (String)this.bunchArgs.get("RunMode");
/*  933: 989 */     if (runMode.equalsIgnoreCase("Cluster")) {
/*  934: 991 */       return getClusteringResultsHT();
/*  935:     */     }
/*  936: 994 */     if (runMode.equalsIgnoreCase("PRCalculator")) {
/*  937: 996 */       return getPRResultsHT();
/*  938:     */     }
/*  939: 999 */     if (runMode.equalsIgnoreCase("MQCalculator")) {
/*  940:1001 */       return getMQCalcResultsHT();
/*  941:     */     }
/*  942:1004 */     return null;
/*  943:     */   }
/*  944:     */   
/*  945:     */   public Hashtable getMQCalcResultsHT()
/*  946:     */   {
/*  947:1009 */     this.results = new Hashtable();
/*  948:1010 */     if (this.MQCalcValue == null) {
/*  949:1011 */       return null;
/*  950:     */     }
/*  951:1013 */     this.results.put("MQCalcResultValue", this.MQCalcValue);
/*  952:1014 */     return this.results;
/*  953:     */   }
/*  954:     */   
/*  955:     */   public Hashtable getPRResultsHT()
/*  956:     */   {
/*  957:1019 */     this.results = new Hashtable();
/*  958:1020 */     if ((this.precision == null) || (this.recall == null)) {
/*  959:1021 */       return null;
/*  960:     */     }
/*  961:1023 */     this.results.put("PRPrecisionValue", this.precision);
/*  962:1024 */     this.results.put("PRRecallValue", this.recall);
/*  963:1025 */     return this.results;
/*  964:     */   }
/*  965:     */   
/*  966:     */   public void setDebugStats(boolean b)
/*  967:     */   {
/*  968:1029 */     this.stats.setCollectClusteringDetails(b);
/*  969:     */   }
/*  970:     */   
/*  971:     */   public ArrayList getClusterList()
/*  972:     */   {
/*  973:1033 */     return this.clusterList;
/*  974:     */   }
/*  975:     */   
/*  976:     */   public Hashtable getClusteringResultsHT()
/*  977:     */   {
/*  978:1038 */     if (this.clusteringMethod_d == null) {
/*  979:1038 */       return null;
/*  980:     */     }
/*  981:1039 */     if (this.baseCluster == null) {
/*  982:1039 */       return null;
/*  983:     */     }
/*  984:1041 */     this.results = new Hashtable();
/*  985:     */     
/*  986:1043 */     Long rt = new Long(this.totalTime);
/*  987:1044 */     Long mqEvals = new Long(this.stats.getMQCalculations());
/*  988:1045 */     Integer totalClusterLevels = new Integer(this.clusterList.size());
/*  989:1046 */     Long saMovesTaken = new Long(this.stats.getSAOverrides());
/*  990:1047 */     Integer medianLvl = new Integer(getMedianLevelNumber());
/*  991:     */     
/*  992:1049 */     this.results.put("Runtime", rt.toString());
/*  993:1050 */     this.results.put("MQEvaluations", mqEvals.toString());
/*  994:1051 */     this.results.put("TotalClusterLevels", totalClusterLevels.toString());
/*  995:1052 */     this.results.put("SANeighborsTaken", saMovesTaken.toString());
/*  996:1053 */     this.results.put("MedianLevelGraph", medianLvl.toString());

				    
/*  997:     */     
/*  998:     */ 
/*  999:1056 */     Hashtable errorHT = new Hashtable();
/* 1000:1057 */     this.results.put("ErrorHashtable", errorHT);
/* 1001:     */     
/* 1002:1059 */     Hashtable warningHT = new Hashtable();
/* 1003:1060 */     if (this.reflexiveEdgeCount > 0)
/* 1004:     */     {
/* 1005:1062 */       Integer re = new Integer(this.reflexiveEdgeCount);
/* 1006:1063 */       warningHT.put("ReflexiveEdgeCount", re.toString());
/* 1007:     */     }
/* 1008:1065 */     this.results.put("WarningHashtable", warningHT);
/* 1009:     */     
/* 1010:1067 */     Hashtable[] resultClusters = new Hashtable[this.clusterList.size()];
/* 1011:1069 */     for (int i = 0; i < this.clusterList.size(); i++)
/* 1012:     */     {
/* 1013:1071 */       Integer level = new Integer(i);
/* 1014:1072 */       Hashtable lvlHT = new Hashtable();
/* 1015:1073 */       lvlHT.clear();
/* 1016:     */       
/* 1017:1075 */       Cluster c = (Cluster)this.clusterList.get(i);
/* 1018:1076 */       Double bestMQ = new Double(c.getObjFnValue());
/* 1019:1077 */       Long clusterDepth = new Long(c.getDepth());
/* 1020:1078 */       Integer numClusters = new Integer(c.getClusterNames().length);
/* 1021:     */		  Double numIntraEdge = new Double((int) c.getGraph().getIntradependenciesValue());
					  Double numInterEdge = new Double((int) c.getGraph().getInterdependenciesValue());

					  lvlHT.put("InterEdge", numInterEdge.toString());
					  lvlHT.put("IntraEdge", numIntraEdge.toString());
					  
/* 1022:1080 */       lvlHT.put("ClusterLevel", level.toString());
/* 1023:1081 */       lvlHT.put("MQValue", bestMQ.toString());
/* 1024:1082 */       lvlHT.put("BestClusterDepth", clusterDepth.toString());
/* 1025:1083 */       lvlHT.put("BestPartitionClusters", numClusters.toString());
					   //TODO:????
					  lvlHT.put("Graph",c.getGraph());
/* 1026:     */       
/* 1027:1085 */       resultClusters[i] = lvlHT;
/* 1028:     */     }
/* 1029:1088 */     this.results.put("ResultClusterObjects", resultClusters);
/* 1030:1089 */     StatsManager.cleanup();
/* 1031:     */     
/* 1032:1091 */     Configuration cTmp = this.clusteringMethod_d.getConfiguration();
/* 1033:1092 */     if ((cTmp instanceof NAHCConfiguration))
/* 1034:     */     {
/* 1035:1094 */       NAHCConfiguration nahcConf = (NAHCConfiguration)cTmp;
/* 1036:1095 */       if (nahcConf.getSATechnique() != null) {
/* 1037:1096 */         nahcConf.getSATechnique().reset();
/* 1038:     */       }
/* 1039:     */     }
/* 1040:1099 */     return this.results;
/* 1041:     */   }
/* 1042:     */   
/* 1043:     */   public boolean run(Hashtable args)
/* 1044:     */   {
/* 1045:1104 */     this.bunchArgs = args;
/* 1046:     */     
/* 1047:1106 */     String runMode = (String)this.bunchArgs.get("RunMode");
/* 1048:1107 */     if (runMode == null) {
/* 1049:1107 */       return false;
/* 1050:     */     }
/* 1051:1109 */     if (runMode.equalsIgnoreCase("Cluster"))
/* 1052:     */     {
/* 1053:1112 */       BunchAsyncNotify notifyClass = null;
/* 1054:1113 */       if (this.bunchArgs.get("RunAsyncNotifyClass") != null) {
/* 1055:1114 */         notifyClass = (BunchAsyncNotify)this.bunchArgs.get("RunAsyncNotifyClass");
/* 1056:     */       }
/* 1057:     */       boolean rc;
/* 1058:     */       
/* 1059:1116 */       if (notifyClass == null) {
/* 1060:1117 */         rc = runClustering();
/* 1061:     */       } else {
/* 1062:1119 */         rc = runClusteringAsync(notifyClass);
/* 1063:     */       }
/* 1064:1122 */       return rc;
/* 1065:     */     }
/* 1066:1124 */     if (runMode.equalsIgnoreCase("PRCalculator")) {
/* 1067:1126 */       return runPRCalc();
/* 1068:     */     }
/* 1069:1129 */     if (runMode.equalsIgnoreCase("MQCalculator")) {
/* 1070:1131 */       return runMQCalc();
/* 1071:     */     }
/* 1072:1133 */     return false;
/* 1073:     */   }
/* 1074:     */   
/* 1075:     */   class ExecuteClusteringEngine
/* 1076:     */   {
/* 1077:     */     Object monitor;
/* 1078:     */     
/* 1079:     */     ExecuteClusteringEngine()
/* 1080:     */     {
/* 1081:1146 */       this.monitor = new Object();
/* 1082:1147 */       run();
/* 1083:     */     }
/* 1084:     */     
/* 1085:     */     public void run()
/* 1086:     */     {
/* 1087:1152 */       Runnable runThread = new Runnable()
/* 1088:     */       {
/* 1089:     */         public void run()
/* 1090:     */         {
/* 1091:     */           try
/* 1092:     */           {
/* 1093:1157 */             BunchEngine.this.clusteringProcessThread = Thread.currentThread();
/* 1094:     */             
/* 1095:1159 */             BunchEngine.this.startTime = System.currentTimeMillis();
/* 1096:1161 */             if (BunchEngine.this.timeoutTimer != null) {
/* 1097:1162 */               BunchEngine.this.timeoutTimer.start();
/* 1098:     */             }
/* 1099:1164 */             BunchEngine.this.clusteringMethod_d.run();
/* 1100:1165 */             BunchEngine.this.endTime = System.currentTimeMillis();
/* 1101:1166 */             BunchEngine.this.totalTime += BunchEngine.this.endTime - BunchEngine.this.startTime;
/* 1102:1168 */             if (BunchEngine.this.timeoutTimer != null) {
/* 1103:1169 */               BunchEngine.this.timeoutTimer.stop();
/* 1104:     */             }
/* 1105:1171 */             synchronized (BunchEngine.ExecuteClusteringEngine.this.monitor)
/* 1106:     */             {
/* 1107:1172 */               BunchEngine.ExecuteClusteringEngine.this.monitor.notifyAll();
/* 1108:     */             }
/* 1109:1174 */             if (BunchEngine.this.clusteringProcessThread != null) {
/* 1110:1175 */               synchronized (BunchEngine.this.clusteringProcessThread)
/* 1111:     */               {
/* 1112:1176 */                 BunchEngine.this.clusteringProcessThread = null;
/* 1113:     */               }
/* 1114:     */             }
/* 1115:     */           }
/* 1116:     */           catch (Exception threadEx)
/* 1117:     */           {
/* 1118:1179 */             threadEx.printStackTrace();
/* 1119:     */           }
/* 1120:     */         }
/* 1121:1182 */       };
/* 1122:1183 */       Thread t = new Thread(runThread);
/* 1123:     */       
/* 1124:1185 */       t.start();
/* 1125:     */       try
/* 1126:     */       {
/* 1127:1190 */         synchronized (this.monitor)
/* 1128:     */         {
/* 1129:1191 */           this.monitor.wait();
/* 1130:     */         }
/* 1131:     */       }
/* 1132:     */       catch (Exception e1)
/* 1133:     */       {
/* 1134:1193 */         e1.printStackTrace();
/* 1135:     */       }
/* 1136:     */     }
/* 1137:     */   }
/* 1138:     */   
/* 1139:     */   class ExecuteClusteringEngineAsync
/* 1140:     */   {
/* 1141:1201 */     BunchAsyncNotify notifyObject = null;
/* 1142:1202 */     SwingWorker worker_d = null;
/* 1143:     */     
/* 1144:     */     ExecuteClusteringEngineAsync(BunchAsyncNotify nObject)
/* 1145:     */     {
/* 1146:1206 */       this.notifyObject = nObject;
/* 1147:     */       
/* 1148:     */ 
/* 1149:     */ 
/* 1150:1210 */       run();
/* 1151:     */     }
/* 1152:     */     
/* 1153:     */     public void run()
/* 1154:     */     {
/* 1155:1215 */       this.notifyObject.setStatus(2);
/* 1156:1216 */       this.worker_d = new SwingWorker()
/* 1157:     */       {
/* 1158:     */         public Object construct()
/* 1159:     */         {
/* 1160:     */           try
/* 1161:     */           {
/* 1162:1223 */             BunchEngine.this.clusteringProcessThread = Thread.currentThread();
/* 1163:     */             
/* 1164:1225 */             BunchEngine.this.startTime = System.currentTimeMillis();
/* 1165:1227 */             if (BunchEngine.this.timeoutTimer != null) {
/* 1166:1228 */               BunchEngine.this.timeoutTimer.start();
/* 1167:     */             }
/* 1168:1230 */             BunchEngine.this.clusteringMethod_d.run();
/* 1169:1231 */             BunchEngine.this.endTime = System.currentTimeMillis();
/* 1170:1232 */             BunchEngine.this.totalTime += BunchEngine.this.endTime - BunchEngine.this.startTime;
/* 1171:1234 */             if (BunchEngine.this.timeoutTimer != null) {
/* 1172:1235 */               BunchEngine.this.timeoutTimer.stop();
/* 1173:     */             }
/* 1174:     */           }
/* 1175:     */           catch (Exception threadEx)
/* 1176:     */           {
/* 1177:1245 */             threadEx.printStackTrace();
/* 1178:     */           }
/* 1179:1246 */           return "Done";
/* 1180:     */         }
/* 1181:     */         
/* 1182:     */         public void interrupt()
/* 1183:     */         {
/* 1184:1250 */           suspend();
/* 1185:1251 */           super.interrupt();
/* 1186:     */         }
/* 1187:     */         
/* 1188:     */         public void finished()
/* 1189:     */         {
/* 1190:1255 */           BunchEngine.ExecuteClusteringEngineAsync.this.notifyObject.setStatus(3);
/* 1191:     */         }
/* 1192:1259 */       };
/* 1193:1260 */       this.worker_d.setPriority(1);
/* 1194:1261 */       this.worker_d.start();
/* 1195:     */     }
/* 1196:     */   }
/* 1197:     */   
/* 1198:     */   class TimeoutTimer
/* 1199:     */     implements ActionListener
/* 1200:     */   {
/* 1201:     */     TimeoutTimer() {}
/* 1202:     */     
/* 1203:     */     public void actionPerformed(ActionEvent e)
/* 1204:     */     {
/* 1205:     */       try
/* 1206:     */       {
/* 1207:1274 */         synchronized (BunchEngine.this.clusteringProcessThread)
/* 1208:     */         {
/* 1209:1276 */           if (BunchEngine.this.clusteringProcessThread == null) {
/* 1210:1277 */             return;
/* 1211:     */           }
/* 1212:1279 */           BunchEngine.this.clusteringProcessThread.interrupt();
/* 1213:     */         }
/* 1214:     */       }
/* 1215:     */       catch (Exception timerEx)
/* 1216:     */       {
/* 1217:1282 */         timerEx.printStackTrace();
/* 1218:     */       }
/* 1219:     */     }
/* 1220:     */   }
/* 1221:     */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.engine.BunchEngine
 * JD-Core Version:    0.7.0.1
 */