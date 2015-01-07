/*    1:     */ package bunch.api;
/*    2:     */ 
/*    3:     */ import bunch.Cluster;
/*    4:     */ import java.io.BufferedWriter;
/*    5:     */ import java.io.File;
/*    6:     */ import java.io.FileWriter;
/*    7:     */ import java.io.PrintStream;
/*    8:     */ import java.util.ArrayList;
/*    9:     */ import java.util.Collection;
/*   10:     */ import java.util.Hashtable;
/*   11:     */ import java.util.Iterator;
/*   12:     */ 
/*   13:     */ public class BunchAPITest
/*   14:     */ {
/*   15:     */   long totalNodes;
/*   16:     */   long totalAdjustments;
/*   17:  50 */   ArrayList bunchGraphs = null;
/*   18:  52 */   int[] prfreq = new int[11];
/*   19:  53 */   int[] prIfreq = new int[11];
/*   20:     */   
/*   21:     */   public void BunchAPITest4()
/*   22:     */   {
/*   23:  57 */     BunchAPI api = new BunchAPI();
/*   24:  58 */     BunchProperties bp = new BunchProperties();
/*   25:     */     
/*   26:  60 */     BunchMDG bmdg = new BunchMDG();
/*   27:     */     
/*   28:     */ 
/*   29:     */ 
/*   30:     */ 
/*   31:     */ 
/*   32:     */ 
/*   33:     */ 
/*   34:     */ 
/*   35:     */ 
/*   36:  70 */     boolean doWithFile = false;
/*   37:  72 */     if (!doWithFile)
/*   38:     */     {
/*   39:  74 */       bmdg.addMDGEdge("50", "105", 1);
/*   40:  75 */       bmdg.addMDGEdge("170", "56", 7);
/*   41:  76 */       bmdg.addMDGEdge("29", "144", 4);
/*   42:  77 */       bmdg.addMDGEdge("150", "211", 10);
/*   43:  78 */       bmdg.addMDGEdge("211", "328", 1);
/*   44:  79 */       bmdg.addMDGEdge("29", "105", 1);
/*   45:  80 */       bmdg.addMDGEdge("211", "14", 34);
/*   46:  81 */       bmdg.addMDGEdge("21", "16", 1);
/*   47:  82 */       bmdg.addMDGEdge("21", "144", 6);
/*   48:  83 */       bmdg.addMDGEdge("17", "16", 2);
/*   49:  84 */       bmdg.addMDGEdge("17", "144", 1);
/*   50:  85 */       bmdg.addMDGEdge("17", "105", 11);
/*   51:  86 */       bmdg.addMDGEdge("14", "16", 6);
/*   52:  87 */       bmdg.addMDGEdge("14", "144", 7);
/*   53:  88 */       bmdg.addMDGEdge("14", "105", 9);
/*   54:  89 */       bmdg.addMDGEdge("170", "6", 3);
/*   55:  90 */       bmdg.addMDGEdge("308", "50", 4);
/*   56:  91 */       bmdg.addMDGEdge("6", "105", 2);
/*   57:  92 */       bmdg.addMDGEdge("211", "150", 12);
/*   58:  93 */       bmdg.addMDGEdge("21", "82", 2);
/*   59:  94 */       bmdg.addMDGEdge("125", "56", 4);
/*   60:  95 */       bmdg.addMDGEdge("14", "82", 4);
/*   61:  96 */       bmdg.addMDGEdge("56", "125", 8);
/*   62:  97 */       bmdg.addMDGEdge("170", "14", 25);
/*   63:  98 */       bmdg.addMDGEdge("144", "6", 8);
/*   64:  99 */       bmdg.addMDGEdge("79", "17", 2);
/*   65: 100 */       bmdg.addMDGEdge("467", "79", 1);
/*   66: 101 */       bmdg.addMDGEdge("82", "21", 20);
/*   67: 102 */       bmdg.addMDGEdge("150", "328", 5);
/*   68: 103 */       bmdg.addMDGEdge("79", "21", 1);
/*   69: 104 */       bmdg.addMDGEdge("150", "14", 4);
/*   70: 105 */       bmdg.addMDGEdge("29", "125", 11);
/*   71: 106 */       bmdg.addMDGEdge("144", "14", 6);
/*   72: 107 */       bmdg.addMDGEdge("79", "211", 67);
/*   73: 108 */       bmdg.addMDGEdge("79", "56", 7);
/*   74: 109 */       bmdg.addMDGEdge("56", "79", 6);
/*   75: 110 */       bmdg.addMDGEdge("14", "125", 8);
/*   76: 111 */       bmdg.addMDGEdge("53", "79", 30);
/*   77: 112 */       bmdg.addMDGEdge("11", "125", 8);
/*   78: 113 */       bmdg.addMDGEdge("50", "79", 12);
/*   79: 114 */       bmdg.addMDGEdge("119", "328", 5);
/*   80: 115 */       bmdg.addMDGEdge("144", "150", 10);
/*   81:     */       
/*   82: 117 */       api.setAPIProperty("MDGGraphObject", bmdg);
/*   83:     */     }
/*   84:     */     else
/*   85:     */     {
/*   86: 121 */       bp.setProperty("MDGInputFile", "e:\\SampleMDGs\\paul.mdg");
/*   87:     */     }
/*   88: 145 */     bp.setProperty("ClusteringAlgorithm", "NAHC");
/*   89: 146 */     bp.setProperty("OutputFormat", "NullOutputFormat");
/*   90:     */     
/*   91:     */ 
/*   92:     */ 
/*   93: 150 */     bp.setProperty("ClusteringApproach", "ClustApproachAgglomerative");
/*   94:     */     
/*   95:     */ 
/*   96: 153 */     bp.setProperty("ProgressCallbackClass", "bunch.api.BunchAPITestCallback");
/*   97: 154 */     bp.setProperty("ProgressCallbackFreq", "5");
/*   98: 155 */     api.setProperties(bp);
/*   99: 156 */     System.out.println("Running...");
/*  100: 157 */     api.run();
/*  101: 158 */     Hashtable results = api.getResults();
/*  102: 159 */     System.out.println("Results:");
/*  103:     */     
/*  104: 161 */     String rt = (String)results.get("Runtime");
/*  105: 162 */     String evals = (String)results.get("MQEvaluations");
/*  106: 163 */     String levels = (String)results.get("TotalClusterLevels");
/*  107: 164 */     String saMovesTaken = (String)results.get("SANeighborsTaken");
/*  108:     */     
/*  109: 166 */     System.out.println("Runtime = " + rt + " ms.");
/*  110: 167 */     System.out.println("Total Evaluations = " + evals);
/*  111: 168 */     System.out.println("Total Levels = " + levels);
/*  112: 169 */     System.out.println("Simulated Annealing Moves Taken = " + saMovesTaken);
/*  113: 170 */     System.out.println();
/*  114:     */   }
/*  115:     */   
/*  116:     */   public static Hashtable collectFinalGraphs(String mdgFileName, String baseFileDirectory, int howMany)
/*  117:     */   {
/*  118: 190 */     BunchGraph[] bgList = new BunchGraph[howMany];
/*  119: 191 */     String baseOutputFileName = mdgFileName;
/*  120: 193 */     if ((baseFileDirectory != null) && (!baseFileDirectory.equals("")))
/*  121:     */     {
/*  122: 195 */       File f = null;
/*  123: 196 */       String baseFileName = "";
/*  124:     */       try
/*  125:     */       {
/*  126: 198 */         f = new File(mdgFileName);
/*  127: 199 */         baseFileName = f.getName();
/*  128:     */       }
/*  129:     */       catch (Exception e)
/*  130:     */       {
/*  131: 204 */         e.printStackTrace();
/*  132:     */       }
/*  133: 207 */       String pathSep = File.separator;
/*  134: 208 */       if (!baseFileDirectory.endsWith(pathSep)) {
/*  135: 209 */         baseFileDirectory = baseFileDirectory + pathSep;
/*  136:     */       }
/*  137: 211 */       baseOutputFileName = baseFileDirectory + baseFileName;
/*  138:     */     }
/*  139: 215 */     for (int i = 0; i < howMany; i++)
/*  140:     */     {
/*  141: 217 */       Integer idx = new Integer(i);
/*  142: 218 */       String fn = baseOutputFileName + idx.toString() + ".bunch";
/*  143: 219 */       bgList[i] = BunchGraphUtils.constructFromSil(mdgFileName, fn);
/*  144:     */     }
/*  145: 222 */     String referenceFile = baseFileDirectory + "temp.bunch";
/*  146: 223 */     BunchGraph bgRef = BunchGraphUtils.constructFromSil(mdgFileName, referenceFile);
/*  147:     */     
/*  148: 225 */     Hashtable h = new Hashtable();
/*  149: 226 */     h.put("reference", bgRef);
/*  150: 227 */     h.put("results", bgList);
/*  151:     */     
/*  152: 229 */     return h;
/*  153:     */   }
/*  154:     */   
/*  155:     */   public static Hashtable processFinalResults(Hashtable in)
/*  156:     */   {
/*  157: 234 */     BunchGraph[] bgList = (BunchGraph[])in.get("results");
/*  158: 235 */     BunchGraph bgRef = (BunchGraph)in.get("reference");
/*  159:     */     
/*  160: 237 */     double meclAccum = 0.0D;double meclMin = 100.0D;double meclMax = 0.0D;
/*  161: 238 */     double prAccum = 0.0D;double prMin = 100.0D;double prMax = 0.0D;
/*  162: 239 */     double esAccum = 0.0D;double esMin = 100.0D;double esMax = 0.0D;
/*  163: 241 */     if ((bgList == null) || (bgRef == null)) {
/*  164: 241 */       return null;
/*  165:     */     }
/*  166: 243 */     for (int i = 0; i < bgList.length; i++)
/*  167:     */     {
/*  168: 245 */       BunchGraph bg = bgList[i];
/*  169:     */       
/*  170: 247 */       double esValue = BunchGraphUtils.calcEdgeSimiliarities(bg, bgRef);
/*  171: 248 */       System.out.println("ES:" + esValue);
/*  172: 249 */       esAccum += esValue;
/*  173: 250 */       if (esValue < esMin) {
/*  174: 250 */         esMin = esValue;
/*  175:     */       }
/*  176: 251 */       if (esValue > esMax) {
/*  177: 251 */         esMax = esValue;
/*  178:     */       }
/*  179: 253 */       Hashtable h1 = BunchGraphUtils.calcPR(bg, bgRef);
/*  180: 254 */       Double prValue = (Double)h1.get("AVERAGE");
/*  181: 255 */       prAccum += prValue.doubleValue();
/*  182: 256 */       System.out.println("PR:" + prValue.doubleValue());
/*  183: 257 */       if (prValue.doubleValue() < prMin) {
/*  184: 257 */         prMin = prValue.doubleValue();
/*  185:     */       }
/*  186: 258 */       if (prValue.doubleValue() > prMax) {
/*  187: 258 */         prMax = prValue.doubleValue();
/*  188:     */       }
/*  189: 260 */       Hashtable meClValue1 = BunchGraphUtils.getMeClMeasurement(bg, bgRef);
/*  190: 261 */       Hashtable meClValue2 = BunchGraphUtils.getMeClMeasurement(bgRef, bg);
/*  191: 262 */       Double meclValue1 = (Double)meClValue1.get("MeclQualityMetric");
/*  192: 263 */       Double meclValue2 = (Double)meClValue2.get("MeclQualityMetric");
/*  193: 264 */       double d1 = meclValue1.doubleValue();
/*  194: 265 */       double d2 = meclValue2.doubleValue();
/*  195: 266 */       double meclValue = Math.max(d1, d2);
/*  196: 267 */       meclAccum += meclValue;
/*  197: 268 */       System.out.println("ML:" + meclValue);
/*  198: 269 */       if (meclValue < meclMin) {
/*  199: 269 */         meclMin = meclValue;
/*  200:     */       }
/*  201: 270 */       if (meclValue > meclMax) {
/*  202: 270 */         meclMax = meclValue;
/*  203:     */       }
/*  204:     */     }
/*  205: 273 */     double denom = bgList.length;
/*  206: 274 */     double mecl = meclAccum / denom;
/*  207: 275 */     double pr = prAccum / denom;
/*  208: 276 */     double es = esAccum / denom;
/*  209:     */     
/*  210: 278 */     Hashtable h = new Hashtable();
/*  211:     */     
/*  212: 280 */     h.put("mecl", new Double(mecl));
/*  213: 281 */     h.put("pr", new Double(pr));
/*  214: 282 */     h.put("es", new Double(es));
/*  215:     */     
/*  216: 284 */     h.put("meclMin", new Double(meclMin));
/*  217: 285 */     h.put("prMin", new Double(prMin));
/*  218: 286 */     h.put("esMin", new Double(esMin));
/*  219:     */     
/*  220: 288 */     h.put("meclMax", new Double(meclMax));
/*  221: 289 */     h.put("prMax", new Double(prMax));
/*  222: 290 */     h.put("esMax", new Double(esMax));
/*  223:     */     
/*  224: 292 */     System.out.println("==============STATS RESULTS=================");
/*  225: 293 */     System.out.println("Mecl = " + meclMin + ", " + mecl + ", " + meclMax);
/*  226: 294 */     System.out.println("PR   = " + prMin + ", " + pr + ", " + prMax);
/*  227: 295 */     System.out.println("ES   = " + esMin + ", " + es + ", " + esMax);
/*  228:     */     
/*  229: 297 */     return h;
/*  230:     */   }
/*  231:     */   
/*  232:     */   public void BunchAPITestxxx()
/*  233:     */   {
/*  234: 302 */     String baseDir = "e:\\SampleMDGs\\";
/*  235: 303 */     String mdgFileName = "compiler";
/*  236: 304 */     String pathMDG = baseDir + mdgFileName;
/*  237: 305 */     int howMany = 50;
/*  238:     */     
/*  239: 307 */     Hashtable res = collectFinalGraphs(pathMDG, baseDir, howMany);
/*  240: 308 */     Hashtable mes = processFinalResults(res);
/*  241:     */   }
/*  242:     */   
/*  243:     */   private double calcSlope(ArrayList inputX, ArrayList inputY)
/*  244:     */   {
/*  245: 314 */     double n = inputX.size();
/*  246: 315 */     double SSxx = 0.0D;
/*  247: 316 */     double SSxy = 0.0D;
/*  248:     */     
/*  249: 318 */     double Sxi2 = 0.0D;
/*  250: 319 */     double Sxi = 0.0D;
/*  251: 320 */     double Syi = 0.0D;
/*  252: 321 */     double Sxy = 0.0D;
/*  253: 323 */     if (inputX.size() != inputY.size()) {
/*  254: 323 */       return -1.0D;
/*  255:     */     }
/*  256: 325 */     for (int i = 0; i < inputX.size(); i++)
/*  257:     */     {
/*  258: 327 */       Double dxi = (Double)inputX.get(i);
/*  259: 328 */       double xi = dxi.doubleValue();
/*  260: 329 */       double xi2 = xi * xi;
/*  261: 330 */       Double Dyi = (Double)inputY.get(i);
/*  262: 331 */       double yi = Dyi.doubleValue();
/*  263: 332 */       double xy = xi * yi;
/*  264:     */       
/*  265: 334 */       Sxi2 += xi2;
/*  266: 335 */       Sxi += xi;
/*  267: 336 */       Syi += yi;
/*  268: 337 */       Sxy += xy;
/*  269:     */     }
/*  270: 340 */     SSxx = Sxi2 - Sxi / n;
/*  271: 341 */     SSxy = Sxy - Sxi * Syi / n;
/*  272:     */     
/*  273: 343 */     double slope = SSxy / SSxx;
/*  274:     */     
/*  275: 345 */     return slope;
/*  276:     */   }
/*  277:     */   
/*  278:     */   private Hashtable calcVelocityAccel(ArrayList input)
/*  279:     */   {
/*  280: 349 */     Hashtable h = new Hashtable();
/*  281: 350 */     ArrayList ax = new ArrayList();
/*  282: 353 */     if (input.size() < 3) {
/*  283: 353 */       return null;
/*  284:     */     }
/*  285: 356 */     for (int i = 0; i < input.size(); i++) {
/*  286: 357 */       ax.add(new Double(i));
/*  287:     */     }
/*  288: 360 */     double v = calcSlope(ax, input);
/*  289:     */     
/*  290:     */ 
/*  291: 363 */     ArrayList axv = new ArrayList();
/*  292: 364 */     ArrayList ayv = new ArrayList();
/*  293: 365 */     for (int i = 1; i < input.size(); i++)
/*  294:     */     {
/*  295: 369 */       Double y1 = (Double)input.get(i - 1);
/*  296: 370 */       Double y2 = (Double)input.get(i);
/*  297: 371 */       Double x1 = (Double)ax.get(i - 1);
/*  298: 372 */       Double x2 = (Double)ax.get(i);
/*  299: 373 */       double deltaX = x2.doubleValue() - x1.doubleValue();
/*  300: 374 */       double deltaY = y2.doubleValue() - y1.doubleValue();
/*  301:     */       
/*  302: 376 */       double slope = deltaY / deltaX;
/*  303:     */       
/*  304:     */ 
/*  305: 379 */       double xmid = (x2.doubleValue() - x1.doubleValue()) / 2.0D;
/*  306: 380 */       axv.add(new Double(xmid));
/*  307: 381 */       ayv.add(new Double(slope));
/*  308:     */     }
/*  309: 385 */     double accel = calcSlope(axv, ayv);
/*  310:     */     
/*  311: 387 */     h.clear();
/*  312: 388 */     h.put("V", new Double(v));
/*  313: 389 */     h.put("A", new Double(accel));
/*  314: 390 */     return h;
/*  315:     */   }
/*  316:     */   
/*  317:     */   public void BunchAPITest1x()
/*  318:     */   {
/*  319: 396 */     String mdgFile = "c:\\research\\mdgs\\pgsql";
/*  320: 397 */     String cluFile = "c:\\research\\mdgs\\pgsql.clu";
/*  321:     */     
/*  322: 399 */     System.out.println("Starting...");
/*  323: 400 */     BunchProperties bp = new BunchProperties();
/*  324: 401 */     bp.setProperty("MDGInputFile", mdgFile);
/*  325: 402 */     bp.setProperty("ClusteringAlgorithm", "NAHC");
/*  326: 403 */     bp.setProperty("OutputFormat", "TextOutputFormat");
/*  327: 404 */     bp.setProperty("OutputTree", "true");
/*  328: 405 */     bp.setProperty("OutputFile", cluFile);
/*  329:     */     
/*  330: 407 */     BunchAPI api = new BunchAPI();
/*  331: 408 */     api.setProperties(bp);
/*  332: 409 */     api.run();
/*  333: 410 */     System.out.println("Done");
/*  334:     */   }
/*  335:     */   
/*  336:     */   public void BunchAPITestOld99()
/*  337:     */   {
/*  338: 417 */     String mdg = "e:\\samplemdgs\\bison";
/*  339: 418 */     int numRuns = 1;
/*  340: 419 */     boolean useSA = false;
/*  341:     */     
/*  342: 421 */     long min = 99999L;
/*  343: 422 */     long max = 0L;
/*  344: 423 */     long accum = 0L;
/*  345: 427 */     for (int i = 0; i < numRuns; i++)
/*  346:     */     {
/*  347: 429 */       BunchAPI api = new BunchAPI();
/*  348: 430 */       BunchProperties bp = new BunchProperties();
/*  349:     */       
/*  350: 432 */       bp.setProperty("MDGInputFile", mdg);
/*  351:     */       
/*  352: 434 */       bp.setProperty("ClusteringAlgorithm", "NAHC");
/*  353: 436 */       if (useSA)
/*  354:     */       {
/*  355: 438 */         bp.setProperty("NAHCHillClimbPct", "30");
/*  356: 439 */         bp.setProperty("NAHCRandomizePct", "20");
/*  357: 440 */         bp.setProperty("NAHCSimulatedAnnealingClass", "bunch.SASimpleTechnique");
/*  358: 441 */         bp.setProperty("NAHCSimulatedAnnealingConfig", "InitialTemp=10.0,Alpha=0.85");
/*  359: 442 */         bp.setProperty("OutputFormat", "NullOutputFormat");
/*  360:     */       }
/*  361: 445 */       bp.setProperty("NAHCHillClimbPct", "100");
/*  362:     */       
/*  363:     */ 
/*  364: 448 */       bp.setProperty("OutputFormat", "GXLOutputFormat");
/*  365:     */       
/*  366: 450 */       api.setProperties(bp);
/*  367:     */       
/*  368:     */ 
/*  369:     */ 
/*  370: 454 */       long startTime = System.currentTimeMillis();
/*  371: 455 */       api.run();
/*  372: 456 */       long runTime = System.currentTimeMillis() - startTime;
/*  373: 457 */       ArrayList cList = api.getClusters();
/*  374: 458 */       for (int zz = 0; zz < cList.size(); zz++)
/*  375:     */       {
/*  376: 460 */         System.out.println("LEVEL = " + zz);
/*  377: 461 */         Cluster c = (Cluster)cList.get(zz);
/*  378: 462 */         ArrayList alc = c.getClusteringDetails();
/*  379:     */         
/*  380: 464 */         long depth = c.getDepth();
/*  381: 465 */         double baseMQ = c.getBaseObjFnValue();
/*  382: 466 */         double finalMQ = c.getObjFnValue();
/*  383: 467 */         int numClusters = c.getNumClusters();
/*  384: 468 */         long numMQEvaluations = c.getNumMQEvaluations();
/*  385:     */         
/*  386:     */ 
/*  387: 471 */         System.out.println("Depth: " + depth + "  BaseMQ: " + baseMQ + "  FinalMQ: " + finalMQ + "  NumClusters: " + numClusters + "  MQEvals: " + numMQEvaluations);
/*  388: 474 */         if (alc != null)
/*  389:     */         {
/*  390: 479 */           if (alc.size() > 2)
/*  391:     */           {
/*  392: 481 */             double start = Double.parseDouble(alc.get(0).toString());
/*  393: 482 */             double end = Double.parseDouble(alc.get(alc.size() - 1).toString());
/*  394: 483 */             double mqInterval = end - start;
/*  395: 484 */             double improvement = 0.0D;
/*  396: 485 */             double steps = 0.0D;
/*  397: 486 */             for (int zzz = 1; zzz < alc.size() - 1; zzz++)
/*  398:     */             {
/*  399: 488 */               double ds = Double.parseDouble(alc.get(zzz).toString());
/*  400: 489 */               double dsLast = Double.parseDouble(alc.get(zzz - 1).toString());
/*  401: 490 */               improvement += ds - dsLast;
/*  402: 491 */               double pct = (ds - start) / mqInterval;
/*  403: 492 */               System.out.println("   i[" + zzz + "]=" + pct);
/*  404: 493 */               steps += 1.0D;
/*  405:     */             }
/*  406: 495 */             System.out.println("Steps = " + (int)steps + "  Avg. Step Size = " + improvement / steps);
/*  407: 496 */             System.out.println();
/*  408:     */           }
/*  409: 499 */           Hashtable h = calcVelocityAccel(alc);
/*  410: 500 */           if (h != null)
/*  411:     */           {
/*  412: 502 */             System.out.println("***** V = " + h.get("V"));
/*  413: 503 */             System.out.println("***** A = " + h.get("A"));
/*  414:     */           }
/*  415:     */         }
/*  416:     */         else
/*  417:     */         {
/*  418: 507 */           System.out.println("List of details is null");
/*  419:     */         }
/*  420:     */       }
/*  421: 513 */       System.out.println("Run " + i + ":  Finished in " + runTime + " ms.");
/*  422: 515 */       if (runTime > max) {
/*  423: 515 */         max = runTime;
/*  424:     */       }
/*  425: 516 */       if (runTime < min) {
/*  426: 516 */         min = runTime;
/*  427:     */       }
/*  428: 517 */       accum += runTime;
/*  429:     */     }
/*  430: 520 */     System.out.println();
/*  431: 521 */     System.out.println("MIN Runtime = " + min + " ms.");
/*  432: 522 */     System.out.println("MAX Runtime = " + max + " ms.");
/*  433: 523 */     System.out.println("AVG Runtime = " + accum / numRuns + " ms.");
/*  434: 524 */     System.out.println("USE SA = " + useSA);
/*  435:     */   }
/*  436:     */   
/*  437:     */   public void BunchAPITestBigBad()
/*  438:     */   {
/*  439: 529 */     String mdg = "e:\\samplemdgs\\compiler";
/*  440: 530 */     String sil = "e:\\samplemdgs\\compiler.bunch";
/*  441:     */     
/*  442:     */ 
/*  443:     */ 
/*  444:     */ 
/*  445:     */ 
/*  446:     */ 
/*  447:     */ 
/*  448:     */ 
/*  449:     */ 
/*  450:     */ 
/*  451:     */ 
/*  452:     */ 
/*  453:     */ 
/*  454:     */ 
/*  455:     */ 
/*  456:     */ 
/*  457:     */ 
/*  458:     */ 
/*  459:     */ 
/*  460: 550 */     BunchAPI api = new BunchAPI();
/*  461: 551 */     BunchProperties bp = new BunchProperties();
/*  462:     */     
/*  463:     */ 
/*  464: 554 */     bp.setProperty("MDGInputFile", mdg);
/*  465: 555 */     bp.setProperty("ClusteringAlgorithm", "NAHC");
/*  466:     */     
/*  467:     */ 
/*  468: 558 */     bp.setProperty("OutputFormat", "TextOutputFormat");
/*  469: 559 */     bp.setProperty("OutputTree", "true");
/*  470: 560 */     bp.setProperty("OutputFile", "e:\\samplemdgs\\compiler.clu");
/*  471:     */     
/*  472:     */ 
/*  473:     */ 
/*  474:     */ 
/*  475:     */ 
/*  476:     */ 
/*  477:     */ 
/*  478:     */ 
/*  479:     */ 
/*  480:     */ 
/*  481: 571 */     api.setProperties(bp);
/*  482:     */     
/*  483:     */ 
/*  484:     */ 
/*  485: 575 */     System.out.println("Running...");
/*  486: 576 */     api.run();
/*  487:     */     
/*  488: 578 */     File f1 = new File("e:\\samplemdgs\\compiler.clu.bunch");
/*  489: 579 */     File fnew = new File("e:\\samplemdgs\\compiler.clu");
/*  490: 580 */     fnew.delete();
/*  491: 581 */     f1.renameTo(fnew);
/*  492:     */     
/*  493:     */ 
/*  494:     */ 
/*  495:     */ 
/*  496:     */ 
/*  497:     */ 
/*  498: 588 */     Hashtable results = api.getResults();
/*  499: 589 */     System.out.println("Results:");
/*  500:     */     
/*  501: 591 */     String rt = (String)results.get("Runtime");
/*  502: 592 */     String evals = (String)results.get("MQEvaluations");
/*  503: 593 */     String levels = (String)results.get("TotalClusterLevels");
/*  504: 594 */     String saMovesTaken = (String)results.get("SANeighborsTaken");
/*  505: 595 */     String medLvl = (String)results.get("MedianLevelGraph");
/*  506:     */     
/*  507: 597 */     System.out.println("Runtime = " + rt + " ms.");
/*  508: 598 */     System.out.println("Total Evaluations = " + evals);
/*  509: 599 */     System.out.println("Simulated Annealing Moves Taken = " + saMovesTaken);
/*  510: 600 */     System.out.println("Median Level: " + medLvl);
/*  511: 601 */     System.out.println();
/*  512:     */     
/*  513: 603 */     BunchGraph gg = api.getPartitionedGraph(Integer.parseInt("0"));
/*  514: 604 */     System.out.println("MQ Value = " + gg.getMQValue());
/*  515:     */     
/*  516: 606 */     System.exit(0);
/*  517:     */     
/*  518: 608 */     Hashtable[] resultLevels = (Hashtable[])results.get("ResultClusterObjects");
/*  519:     */     
/*  520: 610 */     BunchGraph bg = api.getPartitionedGraph();
/*  521: 611 */     if (bg != null) {
/*  522: 612 */       bg.printGraph();
/*  523:     */     }
/*  524: 614 */     Integer iLvls = new Integer(levels);
/*  525: 615 */     for (int i = 0; i < iLvls.intValue(); i++)
/*  526:     */     {
/*  527: 617 */       System.out.println(" ************* LEVEL " + i + " ******************");
/*  528: 618 */       BunchGraph bgLvl = api.getPartitionedGraph(i);
/*  529: 619 */       bgLvl.printGraph();
/*  530: 620 */       System.out.println("\n\n");
/*  531:     */     }
/*  532:     */   }
/*  533:     */   
/*  534:     */   private void dump(String s, Collection c)
/*  535:     */   {
/*  536: 626 */     System.out.println("Special Modules: " + s);
/*  537: 627 */     if (c == null)
/*  538:     */     {
/*  539: 628 */       System.out.println("====>null");
/*  540:     */     }
/*  541:     */     else
/*  542:     */     {
/*  543: 630 */       Iterator i = c.iterator();
/*  544: 631 */       while (i.hasNext()) {
/*  545: 633 */         System.out.println("====>" + i.next());
/*  546:     */       }
/*  547:     */     }
/*  548: 637 */     System.out.println();
/*  549:     */   }
/*  550:     */   
/*  551:     */   public void BunchAPITestOld()
/*  552:     */   {
/*  553: 642 */     BunchAPI api = new BunchAPI();
/*  554: 643 */     Hashtable htSpecial = api.getSpecialModules("e:\\linux\\linux");
/*  555:     */     
/*  556: 645 */     Collection suppliers = (Collection)htSpecial.get("OmnipresentSupplier");
/*  557: 646 */     Collection clients = (Collection)htSpecial.get("OmnipresentClient");
/*  558: 647 */     Collection centrals = (Collection)htSpecial.get("OmnipresentCentral");
/*  559: 648 */     Collection libraries = (Collection)htSpecial.get("LibraryModule");
/*  560: 649 */     dump("clients", clients);
/*  561: 650 */     dump("suppliers", suppliers);
/*  562: 651 */     dump("centrals", centrals);
/*  563: 652 */     dump("libraries", libraries);
/*  564:     */   }
/*  565:     */   
/*  566:     */   public void BunchAPITest5()
/*  567:     */   {
/*  568: 657 */     BunchProperties bp = new BunchProperties();
/*  569:     */     
/*  570: 659 */     bp.setProperty("MDGInputFile", "e:\\expir\\small");
/*  571: 660 */     bp.setProperty("ClusteringAlgorithm", "GA");
/*  572:     */     
/*  573: 662 */     bp.setProperty("GAPopulationSize", "50");
/*  574:     */     
/*  575:     */ 
/*  576:     */ 
/*  577:     */ 
/*  578:     */ 
/*  579: 668 */     BunchAPI api = new BunchAPI();
/*  580: 669 */     api.setProperties(bp);
/*  581: 670 */     api.run();
/*  582: 671 */     Hashtable results = api.getResults();
/*  583: 672 */     printResutls(results);
/*  584:     */   }
/*  585:     */   
/*  586:     */   public void printResutls(Hashtable results)
/*  587:     */   {
/*  588: 680 */     String rt = (String)results.get("Runtime");
/*  589: 681 */     String evals = (String)results.get("MQEvaluations");
/*  590: 682 */     String levels = (String)results.get("TotalClusterLevels");
/*  591: 683 */     String saMovesTaken = (String)results.get("SANeighborsTaken");
/*  592:     */     
/*  593: 685 */     System.out.println("Runtime = " + rt + " ms.");
/*  594: 686 */     System.out.println("Total Evaluations = " + evals);
/*  595: 687 */     System.out.println("Simulated Annealing Moves Taken = " + saMovesTaken);
/*  596: 688 */     System.out.println();
/*  597: 689 */     Hashtable[] resultLevels = (Hashtable[])results.get("ResultClusterObjects");
/*  598: 691 */     for (int i = 0; i < resultLevels.length; i++)
/*  599:     */     {
/*  600: 693 */       Hashtable lvlResults = resultLevels[i];
/*  601: 694 */       System.out.println("***** LEVEL " + i + "*****");
/*  602: 695 */       String mq = (String)lvlResults.get("MQValue");
/*  603: 696 */       String depth = (String)lvlResults.get("BestClusterDepth");
/*  604: 697 */       String numC = (String)lvlResults.get("BestPartitionClusters");
/*  605:     */       
/*  606: 699 */       System.out.println("  MQ Value = " + mq);
/*  607: 700 */       System.out.println("  Best Cluster Depth = " + depth);
/*  608: 701 */       System.out.println("  Number of Clusters in Best Partition = " + numC);
/*  609: 702 */       System.out.println();
/*  610:     */     }
/*  611:     */   }
/*  612:     */   
/*  613:     */   public void BunchAPITest8()
/*  614:     */   {
/*  615: 708 */     String graphName = "e:\\expir\\rcs";
/*  616:     */     
/*  617: 710 */     System.out.println("***** G R A P H   N A M E :   " + graphName + "\n");
/*  618: 711 */     writeHeader();
/*  619: 712 */     runTest(graphName, false);
/*  620: 713 */     runTest(graphName, true);
/*  621:     */   }
/*  622:     */   
/*  623:     */   public void runTest(String graphName, boolean removeSpecial)
/*  624:     */   {
/*  625: 717 */     this.totalNodes = (this.totalAdjustments = 0L);
/*  626: 718 */     this.bunchGraphs = new ArrayList();
/*  627:     */     
/*  628:     */ 
/*  629: 721 */     boolean removeSpecialModules = removeSpecial;
/*  630: 723 */     for (int i = 0; i < 2; i++) {
/*  631: 725 */       runClustering(graphName, removeSpecialModules);
/*  632:     */     }
/*  633: 728 */     double avgValue = expirPR(this.prfreq);
/*  634: 729 */     double avgIsomorphicValue = expirIsomorphicPR();
/*  635: 730 */     BunchGraph bg = (BunchGraph)this.bunchGraphs.get(0);
/*  636: 731 */     double avgIsomorphicCount = expirIsomorphicCount();
/*  637: 734 */     if (!removeSpecial)
/*  638:     */     {
/*  639: 736 */       dumpFreqArray("BASELINE       ", this.prfreq, avgValue, avgIsomorphicCount);
/*  640: 737 */       dumpFreqArray("NO ISOMORPHIC  ", this.prIfreq, avgIsomorphicValue, avgIsomorphicCount);
/*  641:     */     }
/*  642:     */     else
/*  643:     */     {
/*  644: 741 */       dumpFreqArray("NO SPECIAL     ", this.prfreq, avgValue, avgIsomorphicCount);
/*  645: 742 */       dumpFreqArray("NO SPEC & ISO  ", this.prIfreq, avgIsomorphicValue, avgIsomorphicCount);
/*  646:     */     }
/*  647:     */   }
/*  648:     */   
/*  649:     */   private void writeHeader()
/*  650:     */   {
/*  651: 755 */     System.out.println("                 |-------------------------------- F R E Q U E N C Y --------------------------------|");
/*  652: 756 */     System.out.println("                   0-9   10-19   20-29   30-39   40-49   50-59   60-69   70-79   80-89   90-99     100     AVG  AVG-ISO");
/*  653: 757 */     System.out.println("                 =====   =====   =====   =====   =====   =====   =====   =====   =====   =====   =====    ====  =======");
/*  654:     */   }
/*  655:     */   
/*  656:     */   private void dumpFreqArray(String lbl, int[] a, double avgValue, double avgIso)
/*  657:     */   {
/*  658: 762 */     StringBuffer sb = new StringBuffer("      ");
/*  659: 763 */     System.out.print(lbl + " [");
/*  660: 764 */     for (int i = 0; i < a.length; i++)
/*  661:     */     {
/*  662: 766 */       Integer count = new Integer(a[i]);
/*  663: 767 */       String scnt = count.toString();
/*  664: 768 */       StringBuffer sbItem = new StringBuffer(sb.toString());
/*  665: 769 */       sbItem.replace(sbItem.length() - scnt.length() - 1, sbItem.length() - 1, scnt);
/*  666: 770 */       System.out.print(sbItem);
/*  667: 771 */       if (i < a.length - 1) {
/*  668: 772 */         System.out.print("  ");
/*  669:     */       }
/*  670:     */     }
/*  671: 774 */     System.out.print("] ");
/*  672:     */     
/*  673: 776 */     int avg = (int)(avgValue * 100.0D);
/*  674: 777 */     if (avg < 100) {
/*  675: 778 */       avg++;
/*  676:     */     }
/*  677: 779 */     Integer avgI = new Integer(avg);
/*  678: 780 */     String scnt = avgI.toString();
/*  679: 781 */     StringBuffer sbItem = new StringBuffer(sb.toString());
/*  680: 782 */     sbItem.replace(sbItem.length() - scnt.length() - 1, sbItem.length() - 1, scnt);
/*  681: 783 */     System.out.print(sbItem);
/*  682:     */     
/*  683: 785 */     int avgIsoI = (int)avgIso;
/*  684: 786 */     avgI = new Integer(avgIsoI);
/*  685: 787 */     scnt = avgI.toString();
/*  686: 788 */     sbItem = new StringBuffer(sb.toString());
/*  687: 789 */     sbItem.replace(sbItem.length() - scnt.length() - 1, sbItem.length() - 1, scnt);
/*  688: 790 */     System.out.println("   " + sbItem);
/*  689:     */   }
/*  690:     */   
/*  691:     */   private double expirIsomorphicPR()
/*  692:     */   {
/*  693: 796 */     for (int i = 0; i < this.bunchGraphs.size(); i++)
/*  694:     */     {
/*  695: 798 */       BunchGraph g = (BunchGraph)this.bunchGraphs.get(i);
/*  696: 799 */       g.determineIsomorphic();
/*  697:     */     }
/*  698: 801 */     return expirPR(this.prIfreq);
/*  699:     */   }
/*  700:     */   
/*  701:     */   private double expirIsomorphicCount()
/*  702:     */   {
/*  703: 806 */     int accum = 0;
/*  704: 807 */     for (int i = 0; i < this.bunchGraphs.size(); i++)
/*  705:     */     {
/*  706: 809 */       BunchGraph g = (BunchGraph)this.bunchGraphs.get(i);
/*  707: 810 */       accum += g.getTotalOverlapNodes();
/*  708:     */     }
/*  709: 812 */     return accum / this.bunchGraphs.size();
/*  710:     */   }
/*  711:     */   
/*  712:     */   private void clearDistArray(int[] distArray)
/*  713:     */   {
/*  714: 817 */     for (int i = 0; i < distArray.length; i++) {
/*  715: 818 */       distArray[i] = 0;
/*  716:     */     }
/*  717:     */   }
/*  718:     */   
/*  719:     */   private int findIndex(double value)
/*  720:     */   {
/*  721: 823 */     if ((value < 0.0D) || (value > 1.0D)) {
/*  722: 824 */       return 0;
/*  723:     */     }
/*  724: 826 */     double tmp = value * 100.0D;
/*  725: 827 */     int iTmp = (int)tmp;
/*  726: 828 */     iTmp /= 10;
/*  727: 829 */     return iTmp;
/*  728:     */   }
/*  729:     */   
/*  730:     */   private double expirPR(int[] distArray)
/*  731:     */   {
/*  732: 834 */     long trials = 0L;
/*  733: 835 */     double accum = 0.0D;
/*  734:     */     
/*  735: 837 */     clearDistArray(distArray);
/*  736: 838 */     for (int i = 0; i < this.bunchGraphs.size(); i++)
/*  737:     */     {
/*  738: 840 */       BunchGraph g1 = (BunchGraph)this.bunchGraphs.get(i);
/*  739: 841 */       for (int j = i; j < this.bunchGraphs.size(); j++)
/*  740:     */       {
/*  741: 843 */         BunchGraph g2 = (BunchGraph)this.bunchGraphs.get(j);
/*  742:     */         
/*  743: 845 */         Double prValue = new Double(BunchGraphUtils.calcEdgeSimiliarities(g1, g2));
/*  744:     */         
/*  745: 847 */         Hashtable meClValue = BunchGraphUtils.getMeClMeasurement(g1, g2);
/*  746:     */         
/*  747:     */ 
/*  748: 850 */         System.out.println("The distance is:  " + meClValue.get("MeclValue") + "   quality = " + meClValue.get("MeclQualityMetric"));
/*  749: 865 */         if (i != j)
/*  750:     */         {
/*  751: 867 */           trials += 1L;
/*  752: 868 */           int idx = findIndex(prValue.doubleValue());
/*  753: 869 */           distArray[idx] += 1;
/*  754: 870 */           accum += prValue.doubleValue();
/*  755:     */         }
/*  756:     */       }
/*  757:     */     }
/*  758: 874 */     return accum / trials;
/*  759:     */   }
/*  760:     */   
/*  761:     */   public void runClustering(String mdgFileName, boolean removeSpecialNodes)
/*  762:     */   {
/*  763: 879 */     BunchAPI api = new BunchAPI();
/*  764: 880 */     BunchProperties bp = new BunchProperties();
/*  765: 881 */     bp.setProperty("MDGInputFile", mdgFileName);
/*  766:     */     
/*  767: 883 */     Hashtable htSpecial = api.getSpecialModules(mdgFileName);
/*  768:     */     
/*  769: 885 */     bp.setProperty("ClusteringAlgorithm", "NAHC");
/*  770: 886 */     bp.setProperty("OutputFormat", "TextOutputFormat");
/*  771: 888 */     if (removeSpecialNodes) {
/*  772: 889 */       api.setAPIProperty("SpecialModuleHashTable", htSpecial);
/*  773:     */     }
/*  774: 891 */     api.setProperties(bp);
/*  775: 892 */     api.run();
/*  776: 893 */     Hashtable results = api.getResults();
/*  777: 894 */     String sMedLvl = (String)results.get("MedianLevelGraph");
/*  778: 895 */     Integer iMedLvl = new Integer(sMedLvl);
/*  779:     */     
/*  780:     */ 
/*  781:     */ 
/*  782:     */ 
/*  783:     */ 
/*  784:     */ 
/*  785: 902 */     BunchGraph bg = api.getPartitionedGraph(iMedLvl.intValue());
/*  786:     */     
/*  787:     */ 
/*  788:     */ 
/*  789: 906 */     this.bunchGraphs.add(bg);
/*  790:     */   }
/*  791:     */   
/*  792:     */   public void findIsomorphic(BunchGraph bg)
/*  793:     */   {
/*  794: 917 */     Iterator nodeI = bg.getNodes().iterator();
/*  795: 918 */     ArrayList theClusters = new ArrayList(bg.getClusters());
/*  796: 919 */     int adjustCount = 0;
/*  797: 920 */     int nodeAdjustCount = 0;
/*  798: 921 */     int totalCount = bg.getNodes().size();
/*  799: 922 */     boolean nodeIsomorphic = false;
/*  800: 923 */     while (nodeI.hasNext())
/*  801:     */     {
/*  802: 925 */       BunchNode bn = (BunchNode)nodeI.next();
/*  803: 926 */       nodeIsomorphic = false;
/*  804: 927 */       int[] cv = howConnected(bg, bn);
/*  805: 928 */       printConnectVector(bn, cv);
/*  806:     */       
/*  807: 930 */       int currClust = bn.getCluster();
/*  808: 931 */       int currStrength = cv[currClust];
/*  809: 932 */       BunchCluster homeCluster = (BunchCluster)theClusters.get(currClust);
/*  810: 933 */       for (int i = 0; i < cv.length; i++) {
/*  811: 935 */         if (i != currClust)
/*  812:     */         {
/*  813: 936 */           int connectStrength = cv[i];
/*  814: 937 */           if (connectStrength == currStrength)
/*  815:     */           {
/*  816: 939 */             BunchCluster bc = (BunchCluster)theClusters.get(i);
/*  817: 940 */             bc.addOverlapNode(bn);
/*  818: 941 */             adjustCount++;
/*  819: 942 */             nodeIsomorphic = true;
/*  820:     */           }
/*  821:     */         }
/*  822:     */       }
/*  823: 947 */       if (nodeIsomorphic == true) {
/*  824: 947 */         nodeAdjustCount++;
/*  825:     */       }
/*  826:     */     }
/*  827: 949 */     System.out.println("Adjustments = Nodes: " + nodeAdjustCount + " --> " + adjustCount + "/" + totalCount);
/*  828: 950 */     this.totalNodes += totalCount;
/*  829: 951 */     this.totalAdjustments += nodeAdjustCount;
/*  830:     */   }
/*  831:     */   
/*  832:     */   public void printConnectVector(BunchNode bn, int[] cv)
/*  833:     */   {
/*  834: 956 */     String status = "OK:";
/*  835: 957 */     String nodeName = bn.getName();
/*  836: 958 */     int nodeCluster = bn.getCluster();
/*  837: 959 */     int homeStrength = cv[nodeCluster];
/*  838: 960 */     String cvStr = "";
/*  839: 961 */     for (int i = 0; i < cv.length; i++)
/*  840:     */     {
/*  841: 963 */       String modifier = "";
/*  842: 964 */       int cstr = cv[i];
/*  843: 965 */       if (i == nodeCluster) {
/*  844: 965 */         modifier = "*";
/*  845:     */       }
/*  846: 966 */       if (i != nodeCluster)
/*  847:     */       {
/*  848: 968 */         if (cstr > homeStrength)
/*  849:     */         {
/*  850: 970 */           modifier = ">";
/*  851: 971 */           status = "BAD:";
/*  852:     */         }
/*  853: 973 */         if (cstr < homeStrength) {
/*  854: 973 */           modifier = "<";
/*  855:     */         }
/*  856: 974 */         if (cstr == homeStrength)
/*  857:     */         {
/*  858: 976 */           if (!status.equals("BAD:")) {
/*  859: 977 */             status = "ISOMORPHIC:";
/*  860:     */           }
/*  861: 978 */           modifier = "=";
/*  862:     */         }
/*  863:     */       }
/*  864: 981 */       Integer idx = new Integer(i);
/*  865: 982 */       Integer clustStrength = new Integer(cv[i]);
/*  866: 983 */       cvStr = cvStr + "(" + modifier + clustStrength.toString() + ")";
/*  867:     */     }
/*  868:     */   }
/*  869:     */   
/*  870:     */   public int[] howConnected(BunchGraph bg, BunchNode bn)
/*  871:     */   {
/*  872: 990 */     int howManyClusters = bg.getClusters().size();
/*  873: 991 */     int[] connectVector = new int[howManyClusters];
/*  874: 992 */     Iterator fdeps = null;
/*  875: 993 */     Iterator bdeps = null;
/*  876: 995 */     for (int i = 0; i < connectVector.length; i++) {
/*  877: 996 */       connectVector[i] = 0;
/*  878:     */     }
/*  879: 998 */     if (bn.getDeps() != null)
/*  880:     */     {
/*  881:1000 */       fdeps = bn.getDeps().iterator();
/*  882:1001 */       while (fdeps.hasNext())
/*  883:     */       {
/*  884:1003 */         BunchEdge be = (BunchEdge)fdeps.next();
/*  885:1004 */         BunchNode target = be.getDestNode();
/*  886:1005 */         int targetCluster = target.getCluster();
/*  887:1006 */         connectVector[targetCluster] += 1;
/*  888:     */       }
/*  889:     */     }
/*  890:1011 */     if (bn.getBackDeps() != null)
/*  891:     */     {
/*  892:1013 */       bdeps = bn.getBackDeps().iterator();
/*  893:1014 */       while (bdeps.hasNext())
/*  894:     */       {
/*  895:1016 */         BunchEdge be = (BunchEdge)bdeps.next();
/*  896:1017 */         BunchNode target = be.getSrcNode();
/*  897:1018 */         int targetCluster = target.getCluster();
/*  898:1019 */         connectVector[targetCluster] += 1;
/*  899:     */       }
/*  900:     */     }
/*  901:1023 */     return connectVector;
/*  902:     */   }
/*  903:     */   
/*  904:     */   public void printBunchGraph(BunchGraph bg)
/*  905:     */   {
/*  906:1028 */     Collection nodeList = bg.getNodes();
/*  907:1029 */     Collection edgeList = bg.getEdges();
/*  908:1030 */     Collection clusterList = bg.getClusters();
/*  909:     */     
/*  910:     */ 
/*  911:     */ 
/*  912:     */ 
/*  913:1035 */     System.out.println("PRINTING BUNCH GRAPH\n");
/*  914:1036 */     System.out.println("Node Count:         " + nodeList.size());
/*  915:1037 */     System.out.println("Edge Count:         " + edgeList.size());
/*  916:1038 */     System.out.println("MQ Value:           " + bg.getMQValue());
/*  917:1039 */     System.out.println("Number of Clusters: " + bg.getNumClusters());
/*  918:1040 */     System.out.println();
/*  919:     */     
/*  920:     */ 
/*  921:     */ 
/*  922:     */ 
/*  923:     */ 
/*  924:1046 */     Iterator nodeI = nodeList.iterator();
/*  925:1048 */     while (nodeI.hasNext())
/*  926:     */     {
/*  927:1050 */       BunchNode bn = (BunchNode)nodeI.next();
/*  928:1051 */       Iterator fdeps = null;
/*  929:1052 */       Iterator bdeps = null;
/*  930:     */       
/*  931:1054 */       System.out.println("NODE:         " + bn.getName());
/*  932:1055 */       System.out.println("Cluster ID:   " + bn.getCluster());
/*  933:1058 */       if (bn.getDeps() != null)
/*  934:     */       {
/*  935:1060 */         fdeps = bn.getDeps().iterator();
/*  936:1061 */         while (fdeps.hasNext())
/*  937:     */         {
/*  938:1063 */           BunchEdge be = (BunchEdge)fdeps.next();
/*  939:1064 */           String depName = be.getDestNode().getName();
/*  940:1065 */           int weight = be.getWeight();
/*  941:1066 */           System.out.println("   ===> " + depName + " (" + weight + ")");
/*  942:     */         }
/*  943:     */       }
/*  944:1071 */       if (bn.getBackDeps() != null)
/*  945:     */       {
/*  946:1073 */         bdeps = bn.getBackDeps().iterator();
/*  947:1074 */         while (bdeps.hasNext())
/*  948:     */         {
/*  949:1076 */           BunchEdge be = (BunchEdge)bdeps.next();
/*  950:1077 */           String depName = be.getSrcNode().getName();
/*  951:1078 */           int weight = be.getWeight();
/*  952:1079 */           System.out.println("   <=== " + depName + " (" + weight + ")");
/*  953:     */         }
/*  954:     */       }
/*  955:1082 */       System.out.println();
/*  956:     */     }
/*  957:1089 */     System.out.println("Cluster Breakdown\n");
/*  958:1090 */     Iterator clusts = bg.getClusters().iterator();
/*  959:1091 */     while (clusts.hasNext())
/*  960:     */     {
/*  961:1093 */       BunchCluster bc = (BunchCluster)clusts.next();
/*  962:1094 */       System.out.println("Cluster id:   " + bc.getID());
/*  963:1095 */       System.out.println("Custer name:  " + bc.getName());
/*  964:1096 */       System.out.println("Cluster size: " + bc.getSize());
/*  965:     */       
/*  966:1098 */       Iterator members = bc.getClusterNodes().iterator();
/*  967:1099 */       while (members.hasNext())
/*  968:     */       {
/*  969:1101 */         BunchNode bn = (BunchNode)members.next();
/*  970:1102 */         System.out.println("   --> " + bn.getName() + "   (" + bn.getCluster() + ")");
/*  971:     */       }
/*  972:1104 */       System.out.println();
/*  973:     */     }
/*  974:     */   }
/*  975:     */   
/*  976:     */   public void BunchAPITest3()
/*  977:     */   {
/*  978:     */     try
/*  979:     */     {
/*  980:1111 */       String mdgFile = "e:\\expir\\cia";
/*  981:1112 */       int runCount = 50;
/*  982:     */       
/*  983:1114 */       FileWriter outF = new FileWriter(mdgFile + ".txt");
/*  984:1115 */       BufferedWriter out = new BufferedWriter(outF);
/*  985:1117 */       for (int i = 0; i < runCount; i++)
/*  986:     */       {
/*  987:1119 */         BunchAPI api = new BunchAPI();
/*  988:1120 */         BunchProperties bp = new BunchProperties();
/*  989:     */         
/*  990:1122 */         bp.setProperty("MDGInputFile", mdgFile);
/*  991:1123 */         bp.setProperty("OutputFormat", "TextOutputFormat");
/*  992:     */         
/*  993:1125 */         bp.setProperty("ClusteringAlgorithm", "NAHC");
/*  994:1126 */         bp.setProperty("NAHCHillClimbPct", "100");
/*  995:1127 */         bp.setProperty("NAHCRandomizePct", "0");
/*  996:     */         
/*  997:1129 */         Integer cnt = new Integer(i);
/*  998:1130 */         String outFileName = mdgFile + cnt.toString();
/*  999:1131 */         bp.setProperty("OutputFile", outFileName);
/* 1000:1132 */         bp.setProperty("EchoResultsToConsole", "True");
/* 1001:1133 */         api.setProperties(bp);
/* 1002:     */         
/* 1003:     */ 
/* 1004:1136 */         api.run();
/* 1005:     */         
/* 1006:     */ 
/* 1007:1139 */         Hashtable results = api.getResults();
/* 1008:     */         
/* 1009:     */ 
/* 1010:1142 */         String rt = (String)results.get("Runtime");
/* 1011:1143 */         String evals = (String)results.get("MQEvaluations");
/* 1012:1144 */         String medLvl = (String)results.get("MedianLevelGraph");
/* 1013:1145 */         Hashtable[] resultLevels = (Hashtable[])results.get("ResultClusterObjects");
/* 1014:     */         
/* 1015:1147 */         Hashtable medLvlResults = resultLevels[Integer.parseInt(medLvl)];
/* 1016:     */         
/* 1017:1149 */         String numClusters = (String)medLvlResults.get("BestPartitionClusters");
/* 1018:1150 */         String mqValue = (String)medLvlResults.get("MQValue");
/* 1019:     */         
/* 1020:1152 */         String outLine = outFileName + "\t" + numClusters.toString() + "\t" + mqValue.toString() + "\r\n";
/* 1021:1153 */         out.write(outLine);
/* 1022:1154 */         if (i % 10 == 0) {
/* 1023:1155 */           System.out.println("Pct = " + i / runCount);
/* 1024:     */         }
/* 1025:     */       }
/* 1026:1160 */       out.close();
/* 1027:1161 */       outF.close();
/* 1028:     */       
/* 1029:1163 */       outF = new FileWriter(mdgFile + "_pr.txt");
/* 1030:1164 */       out = new BufferedWriter(outF);
/* 1031:1165 */       long total = runCount * (runCount - 1) / 2;
/* 1032:1166 */       long performed = 0L;
/* 1033:1168 */       for (int i = 0; i < runCount; i++)
/* 1034:     */       {
/* 1035:1170 */         BunchAPI api = new BunchAPI();
/* 1036:1171 */         BunchProperties bp = new BunchProperties();
/* 1037:1172 */         for (int j = i + 1; j < runCount; j++) {
/* 1038:1174 */           if (i != j)
/* 1039:     */           {
/* 1040:1175 */             performed += 1L;
/* 1041:     */             
/* 1042:1177 */             Integer iI = new Integer(i);
/* 1043:1178 */             Integer iJ = new Integer(j);
/* 1044:1179 */             String file1 = mdgFile + iI.toString() + ".bunch";
/* 1045:1180 */             String file2 = mdgFile + iJ.toString() + ".bunch";
/* 1046:1181 */             bp.setProperty("RunMode", "PRCalculator");
/* 1047:1182 */             bp.setProperty("PRClusterDecomposition", file1);
/* 1048:1183 */             bp.setProperty("PRExpertDecomposition", file2);
/* 1049:1184 */             api.setProperties(bp);
/* 1050:1185 */             api.run();
/* 1051:1186 */             Hashtable results = api.getResults();
/* 1052:1187 */             String precision = (String)results.get("PRPrecisionValue");
/* 1053:1188 */             String recall = (String)results.get("PRRecallValue");
/* 1054:1189 */             String outLine = "PR(" + file1 + ", " + file2 + ")\t" + precision + "\t" + recall + "\r\n";
/* 1055:     */             
/* 1056:1191 */             out.write(outLine);
/* 1057:1192 */             if (performed % 100L == 0L) {
/* 1058:1193 */               System.out.println("Pct PR: " + performed / total);
/* 1059:     */             }
/* 1060:     */           }
/* 1061:     */         }
/* 1062:     */       }
/* 1063:     */     }
/* 1064:     */     catch (Exception e)
/* 1065:     */     {
/* 1066:1197 */       e.printStackTrace();
/* 1067:     */     }
/* 1068:     */   }
/* 1069:     */   
/* 1070:     */   BunchAPITest()
/* 1071:     */   {
/* 1072:1201 */     BunchAPI api = new BunchAPI();
/* 1073:1202 */     BunchProperties bp = new BunchProperties();
/* 1074:1203 */     bp.setProperty("MDGInputFile", "/Users/brianmitchell/dev/mdgs/incl");
/* 1075:     */     
/* 1076:1205 */     bp.setProperty("ClusteringAlgorithm", "NAHC");
/* 1077:     */     
/* 1078:     */ 
/* 1079:1208 */     bp.setProperty("NAHCHillClimbPct", "55");
/* 1080:1209 */     bp.setProperty("NAHCRandomizePct", "20");
/* 1081:1210 */     bp.setProperty("NAHCSimulatedAnnealingClass", "bunch.SASimpleTechnique");
/* 1082:1211 */     bp.setProperty("NAHCSimulatedAnnealingConfig", "InitialTemp=100.0,Alpha=0.95");
/* 1083:     */     
/* 1084:     */ 
/* 1085:     */ 
/* 1086:     */ 
/* 1087:     */ 
/* 1088:     */ 
/* 1089:1218 */     bp.setProperty("OutputFormat", "DotOutputFormat");
/* 1090:1219 */     bp.setProperty("OutputDirectory", "/Users/brianmitchell/dev/mdgs");
/* 1091:     */     
/* 1092:     */ 
/* 1093:     */ 
/* 1094:1223 */     api.setProperties(bp);
/* 1095:1224 */     System.out.println("Running...");
/* 1096:     */     
/* 1097:     */ 
/* 1098:1227 */     api.run();
/* 1099:1228 */     Hashtable results = api.getResults();
/* 1100:1229 */     System.out.println("Results:");
/* 1101:     */     
/* 1102:1231 */     String rt = (String)results.get("Runtime");
/* 1103:1232 */     String evals = (String)results.get("MQEvaluations");
/* 1104:1233 */     String levels = (String)results.get("TotalClusterLevels");
/* 1105:1234 */     String saMovesTaken = (String)results.get("SANeighborsTaken");
/* 1106:     */     
/* 1107:1236 */     System.out.println("Runtime = " + rt + " ms.");
/* 1108:1237 */     System.out.println("Total Evaluations = " + evals);
/* 1109:1238 */     System.out.println("Simulated Annealing Moves Taken = " + saMovesTaken);
/* 1110:1239 */     System.out.println();
/* 1111:1240 */     Hashtable[] resultLevels = (Hashtable[])results.get("ResultClusterObjects");
/* 1112:1242 */     for (int i = 0; i < resultLevels.length; i++)
/* 1113:     */     {
/* 1114:1244 */       Hashtable lvlResults = resultLevels[i];
/* 1115:1245 */       System.out.println("***** LEVEL " + i + "*****");
/* 1116:1246 */       String mq = (String)lvlResults.get("MQValue");
/* 1117:1247 */       String depth = (String)lvlResults.get("BestClusterDepth");
/* 1118:1248 */       String numC = (String)lvlResults.get("BestPartitionClusters");
/* 1119:     */       
/* 1120:1250 */       System.out.println("  MQ Value = " + mq);
/* 1121:1251 */       System.out.println("  Best Cluster Depth = " + depth);
/* 1122:1252 */       System.out.println("  Number of Clusters in Best Partition = " + numC);
/* 1123:1253 */       System.out.println();
/* 1124:     */     }
/* 1125:     */     try
/* 1126:     */     {
/* 1127:1259 */       Runtime r = Runtime.getRuntime();
/* 1128:1260 */       r.exec("dot -Tps e:\\pstopcl\\incl.dot > e:\\pstopcl\\in\\incl.ps");
/* 1129:     */     }
/* 1130:     */     catch (Exception ex)
/* 1131:     */     {
/* 1132:1262 */       ex.printStackTrace();
/* 1133:     */     }
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   public static void main(String[] args)
/* 1137:     */   {
/* 1138:1268 */     BunchAPITest bunchAPITest1 = new BunchAPITest();
/* 1139:     */   }
/* 1140:     */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.BunchAPITest
 * JD-Core Version:    0.7.0.1
 */