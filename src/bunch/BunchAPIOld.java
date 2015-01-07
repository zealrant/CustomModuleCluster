/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.awt.FileDialog;
/*   4:    */ import java.beans.Beans;
/*   5:    */ import javax.swing.DefaultListModel;
/*   6:    */ 
/*   7:    */ public class BunchAPIOld
/*   8:    */ {
/*   9:    */   public static final int OUTPUT_DOTTY = 1;
/*  10:    */   public static final int OUTPUT_TOMSAWYER = 2;
/*  11:    */   public static final int OUTPUT_TEXT = 3;
/*  12:    */   public static final int ALG_NAHC = 1;
/*  13:    */   public static final int ALG_SAHC = 2;
/*  14:    */   public static final int ALG_GA = 3;
/*  15:    */   public static final int ALG_OPTIMAL = 4;
/*  16:    */   public static final int MQ_TURBO = 1;
/*  17:    */   public static final int MQ_ORIG = 2;
/*  18:    */   public static final int MQ_WEIGHTED = 3;
/*  19:    */   public static final int GAMETHOD_RW = 1;
/*  20:    */   public static final int GAMETHOD_TOURNAMENT = 2;
/*  21:    */   public static final float DEFAULT_OPM_THRESHOLD = 3.0F;
/*  22:    */   FileDialog fileSelector_d;
/*  23:    */   ClusteringMethod clusteringMethod_d;
/*  24:    */   GraphOutput graphOutput_d;
/*  25:    */   Graph initialGraph_d;
/*  26:    */   Graph lastResultGraph_d;
/*  27:    */   BunchPreferences preferences_d;
/*  28:    */   Configuration configuration_d;
/*  29: 74 */   DefaultListModel standardNodeListModel_d = new DefaultListModel();
/*  30: 75 */   DefaultListModel suppliersListModel_d = new DefaultListModel();
/*  31: 76 */   DefaultListModel clientsListModel_d = new DefaultListModel();
/*  32: 77 */   DefaultListModel centralListModel_d = new DefaultListModel();
/*  33: 78 */   DefaultListModel librariesListModel_d = new DefaultListModel();
/*  34: 79 */   String mqCalc_d = "default";
/*  35: 80 */   String clusterName_d = "";
/*  36: 81 */   String outputMethod = "Dotty";
/*  37: 82 */   String inputFileName = "";
/*  38: 83 */   String outputFileName = "";
/*  39: 84 */   boolean doDrifters = true;
/*  40: 85 */   HillClimbingConfiguration hcc = null;
/*  41: 86 */   GAConfiguration gac = null;
/*  42: 87 */   String mdgFileName = "";
/*  43: 88 */   String delims = "";
/*  44: 90 */   int alg = 1;
/*  45: 91 */   int mqFn = 1;
/*  46:    */   
/*  47:    */   public BunchAPIOld(String sMDGFile)
/*  48:    */     throws Exception
/*  49:    */   {
/*  50:102 */     this.mdgFileName = sMDGFile;
/*  51:103 */     this.delims = " ";
/*  52:104 */     init(sMDGFile, " ");
/*  53:    */   }
/*  54:    */   
/*  55:    */   public BunchAPIOld(String sMDGFile, String delims)
/*  56:    */     throws Exception
/*  57:    */   {
/*  58:120 */     this.mdgFileName = sMDGFile;
/*  59:121 */     delims = delims;
/*  60:122 */     init(sMDGFile, delims);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void init(String sMDG, String delims)
/*  64:    */     throws Exception
/*  65:    */   {
/*  66:137 */     this.preferences_d = ((BunchPreferences)Beans.instantiate(null, "bunch.BunchPreferences"));
/*  67:138 */     Graph.setObjectiveFunctionCalculatorFactory(this.preferences_d.getObjectiveFunctionCalculatorFactory());
/*  68:139 */     this.inputFileName = sMDG;
/*  69:140 */     this.outputFileName = sMDG;
/*  70:141 */     parseGraph(sMDG, delims);
/*  71:142 */     setOutputFile(sMDG);
/*  72:143 */     setClusteringMethod(2);
/*  73:144 */     setMQCalc(1);
/*  74:145 */     setOutputMethod(1);
/*  75:146 */     setDriftersOptimization(true);
/*  76:147 */     setDefaultPreferences();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setClusteringMethod(int iMethod)
/*  80:    */   {
/*  81:163 */     this.alg = iMethod;
/*  82:    */     String method;
/*  83:170 */     switch (iMethod)
/*  84:    */     {
/*  85:    */     case 1: 
/*  86:173 */       method = "NAHC:    nodes in [50,100)";
/*  87:174 */       setHillClimbingConfiguration(1, 10, 0.1D);
/*  88:175 */       this.configuration_d = this.hcc;
/*  89:176 */       break;
/*  90:    */     case 2: 
/*  91:178 */       method = "SAHC:    nodes in [10,50)";
/*  92:179 */       setHillClimbingConfiguration(1, 5, 0.1D);
/*  93:180 */       this.configuration_d = this.hcc;
/*  94:181 */       break;
/*  95:    */     case 3: 
/*  96:183 */       method = "GA:          nodes in [100,...)";
/*  97:184 */       setGAConfiguration(6900, 230, 0.6D, 0.025D, 1);
/*  98:185 */       this.configuration_d = this.gac;
/*  99:186 */       break;
/* 100:    */     case 4: 
/* 101:188 */       method = "Optimal: nodes in [1, 10)";
/* 102:189 */       this.configuration_d = null;
/* 103:190 */       break;
/* 104:    */     default: 
/* 105:192 */       method = "SAHC:    nodes in [10,50)";
/* 106:    */     }
/* 107:201 */     if (!method.getClass().getName().equals(method))
/* 108:    */     {
/* 109:202 */       this.clusteringMethod_d = this.preferences_d.getClusteringMethodFactory().getMethod(method);
/* 110:203 */       if (this.configuration_d == null) {
/* 111:204 */         this.configuration_d = this.clusteringMethod_d.getConfiguration();
/* 112:    */       }
/* 113:206 */       this.clusteringMethod_d.setIterationListener(null);
/* 114:208 */       if ((this.initialGraph_d != null) && (this.configuration_d != null))
/* 115:    */       {
/* 116:209 */         this.configuration_d.init(this.initialGraph_d);
/* 117:    */         
/* 118:211 */         this.clusteringMethod_d.setConfiguration(this.configuration_d);
/* 119:    */       }
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   void parseGraph(String filename, String delims)
/* 124:    */   {
/* 125:230 */     Parser p = this.preferences_d.getParserFactory().getParser("dependency");
/* 126:231 */     p.setInput(filename);
/* 127:232 */     p.setDelims(delims);
/* 128:233 */     this.initialGraph_d = ((Graph)p.parse());
/* 129:234 */     if (this.configuration_d != null) {
/* 130:235 */       this.configuration_d.init(this.initialGraph_d);
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setOutputFile(String filename)
/* 135:    */   {
/* 136:249 */     this.outputFileName = filename;
/* 137:    */   }
/* 138:    */   
/* 139:    */   void parseClusterFile(String filename)
/* 140:    */   {
/* 141:261 */     Parser p = this.preferences_d.getParserFactory().getParser("cluster");
/* 142:262 */     p.setInput(filename);
/* 143:263 */     p.setObject(this.initialGraph_d);
/* 144:264 */     p.parse();
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void run()
/* 148:    */   {
/* 149:277 */     configureOptions();
/* 150:    */     
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:283 */     int[] clust = this.initialGraph_d.getClusters();
/* 156:284 */     boolean[] locks = this.initialGraph_d.getLocks();
/* 157:285 */     for (int i = 0; i < clust.length; i++) {
/* 158:286 */       if (clust[i] != -1) {
/* 159:287 */         locks[i] = true;
/* 160:    */       }
/* 161:    */     }
/* 162:295 */     this.clusteringMethod_d.initialize();
/* 163:296 */     this.clusteringMethod_d.setGraph(this.initialGraph_d.cloneGraph());
/* 164:297 */     this.graphOutput_d.setBasicName(this.outputFileName);
/* 165:298 */     this.clusteringMethod_d.run();
/* 166:299 */     outputGraph();
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void runBatch(int howMany)
/* 170:    */     throws Exception
/* 171:    */   {
/* 172:314 */     int expNum = 0;
/* 173:    */     
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:319 */     this.configuration_d.createLogFile();
/* 178:324 */     for (int z = 0; z < howMany; z++)
/* 179:    */     {
/* 180:326 */       expNum++;
/* 181:327 */       configureOptions();
/* 182:    */       
/* 183:329 */       int[] clust = this.initialGraph_d.getClusters();
/* 184:330 */       boolean[] locks = this.initialGraph_d.getLocks();
/* 185:331 */       for (int i = 0; i < clust.length; i++) {
/* 186:333 */         if (clust[i] != -1) {
/* 187:334 */           locks[i] = true;
/* 188:    */         }
/* 189:    */       }
/* 190:338 */       this.clusteringMethod_d.initialize();
/* 191:339 */       this.clusteringMethod_d.setGraph(this.initialGraph_d.cloneGraph());
/* 192:340 */       this.graphOutput_d.setBasicName(this.outputFileName);
/* 193:341 */       this.configuration_d.expNumber_d = expNum;
/* 194:342 */       this.configuration_d.runBatch_d = true;
/* 195:343 */       this.clusteringMethod_d.run();
/* 196:344 */       outputGraph();
/* 197:    */     }
/* 198:350 */     this.configuration_d.closeLogFile();
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setLastResultGraph(Graph g)
/* 202:    */   {
/* 203:365 */     this.lastResultGraph_d = g;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public Graph getLastResultGraph()
/* 207:    */   {
/* 208:377 */     return this.lastResultGraph_d;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public GraphOutput getGraphOutput()
/* 212:    */   {
/* 213:389 */     return this.graphOutput_d;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public ClusteringMethod getClusteringMethod()
/* 217:    */   {
/* 218:401 */     return this.clusteringMethod_d;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setMQCalc(int iCalc)
/* 222:    */   {
/* 223:413 */     this.mqFn = iCalc;
/* 224:419 */     switch (iCalc)
/* 225:    */     {
/* 226:    */     case 1: 
/* 227:422 */       this.mqCalc_d = "Turbo MQ Function";
/* 228:423 */       break;
/* 229:    */     case 2: 
/* 230:425 */       this.mqCalc_d = "MQ Function";
/* 231:426 */       break;
/* 232:    */     case 3: 
/* 233:428 */       this.mqCalc_d = "Eperimental Weighted 2";
/* 234:429 */       break;
/* 235:    */     default: 
/* 236:431 */       this.mqCalc_d = "Turbo MQ Function";
/* 237:    */     }
/* 238:438 */     String objFnCalc = this.mqCalc_d;
/* 239:439 */     this.preferences_d.getObjectiveFunctionCalculatorFactory().setCurrentCalculator(objFnCalc);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void configureOptions()
/* 243:    */   {
/* 244:450 */     String objFnCalc = this.mqCalc_d;
/* 245:451 */     this.preferences_d.getObjectiveFunctionCalculatorFactory().setCurrentCalculator(objFnCalc);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setHillClimbingConfiguration(int iInterations, int iPopSz, double dThreshold)
/* 249:    */   {
/* 250:465 */     this.hcc = new HillClimbingConfiguration();
/* 251:466 */     this.hcc.init(this.initialGraph_d);
/* 252:467 */     this.hcc.setNumOfIterations(iInterations);
/* 253:468 */     this.hcc.setPopulationSize(iPopSz);
/* 254:469 */     this.hcc.setThreshold(dThreshold);
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setGAConfiguration(int iInterations, int iPopSz, double crossThreshold, double mutationThreshold, int iMethod)
/* 258:    */   {
/* 259:486 */     this.gac = new GAConfiguration();
/* 260:487 */     this.gac.init(this.initialGraph_d);
/* 261:488 */     this.gac.setNumOfIterations(iInterations);
/* 262:489 */     this.gac.setPopulationSize(iPopSz);
/* 263:490 */     this.gac.setCrossoverThreshold(crossThreshold);
/* 264:491 */     this.gac.setMutationThreshold(mutationThreshold);
/* 265:493 */     if (iMethod == 1) {
/* 266:494 */       this.gac.setMethod("roulette wheel");
/* 267:495 */     } else if (iMethod == 2) {
/* 268:496 */       this.gac.setMethod("tournament");
/* 269:    */     } else {
/* 270:498 */       this.gac.setMethod("roulette wheel");
/* 271:    */     }
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setOutputMethod(int iMethod)
/* 275:    */   {
/* 276:    */     String sMethod;
/* 277:516 */     switch (iMethod)
/* 278:    */     {
/* 279:    */     case 1: 
/* 280:519 */       sMethod = "Dotty";
/* 281:520 */       break;
/* 282:    */     case 2: 
/* 283:522 */       sMethod = "Tom Sawyer";
/* 284:523 */       break;
/* 285:    */     case 3: 
/* 286:525 */       sMethod = "Text";
/* 287:526 */       break;
/* 288:    */     default: 
/* 289:528 */       sMethod = "Dotty";
/* 290:    */     }
/* 291:532 */     this.outputMethod = sMethod;
/* 292:533 */     this.graphOutput_d = this.preferences_d.getGraphOutputFactory().getOutput(this.outputMethod);
/* 293:534 */     this.graphOutput_d.setBaseName(this.outputFileName);
/* 294:535 */     this.graphOutput_d.setBasicName(this.outputFileName);
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void setDriftersOptimization(boolean doIt)
/* 298:    */   {
/* 299:548 */     this.doDrifters = doIt;
/* 300:    */   }
/* 301:    */   
/* 302:    */   void outputGraph()
/* 303:    */   {
/* 304:557 */     this.graphOutput_d = this.preferences_d.getGraphOutputFactory().getOutput(this.outputMethod);
/* 305:558 */     this.graphOutput_d.setBaseName(this.clusterName_d);
/* 306:559 */     this.graphOutput_d.setBasicName(this.outputFileName);
/* 307:560 */     this.graphOutput_d.setCurrentName(this.outputFileName);
/* 308:561 */     this.graphOutput_d.setGraph(this.clusteringMethod_d.getBestGraph());
/* 309:562 */     this.graphOutput_d.write();
/* 310:    */   }
/* 311:    */   
/* 312:    */   private void setDefaultPreferences()
/* 313:    */   {
/* 314:574 */     setGAConfiguration(6900, 230, 0.6D, 0.025D, 1);
/* 315:575 */     setHillClimbingConfiguration(100, 5, 0.1D);
/* 316:577 */     if (this.mqFn == 1)
/* 317:    */     {
/* 318:579 */       if (this.alg == 1) {
/* 319:580 */         setHillClimbingConfiguration(1, 10, 1.0D);
/* 320:581 */       } else if (this.alg == 1) {
/* 321:582 */         setHillClimbingConfiguration(1, 5, 1.0D);
/* 322:    */       }
/* 323:    */     }
/* 324:586 */     else if (this.alg == 1) {
/* 325:587 */       setHillClimbingConfiguration(200, 10, 0.1D);
/* 326:588 */     } else if (this.alg == 1) {
/* 327:589 */       setHillClimbingConfiguration(100, 5, 0.1D);
/* 328:    */     }
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void excludeOmnipresentModules()
/* 332:    */   {
/* 333:601 */     excludeOmnipresentModules(3.0F);
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void excludeOmnipresentModules(float threshold)
/* 337:    */   {
/* 338:614 */     Node[] nodeList = this.initialGraph_d.getNodes();
/* 339:    */     
/* 340:    */ 
/* 341:617 */     double avg = 0.0D;double sum = 0.0D;
/* 342:618 */     for (int i = 0; i < nodeList.length; i++) {
/* 343:619 */       if (nodeList[i].getDependencies() != null) {
/* 344:620 */         sum += nodeList[i].getDependencies().length;
/* 345:    */       }
/* 346:    */     }
/* 347:623 */     avg = sum / nodeList.length;
/* 348:624 */     avg *= threshold;
/* 349:625 */     for (int i = 0; i < nodeList.length; i++) {
/* 350:626 */       if ((nodeList[i].getDependencies() != null) && (nodeList[i].getDependencies().length > avg) && (!usesModule(this.librariesListModel_d, nodeList[i].getName())))
/* 351:    */       {
/* 352:629 */         this.standardNodeListModel_d.removeElement(nodeList[i].getName());
/* 353:630 */         this.clientsListModel_d.addElement(nodeList[i].getName());
/* 354:    */       }
/* 355:    */     }
/* 356:635 */     avg = 0.0D;sum = 0.0D;
/* 357:636 */     int[] inNum = new int[nodeList.length];
/* 358:638 */     for (int j = 0; j < nodeList.length; j++)
/* 359:    */     {
/* 360:639 */       int currval = 0;
/* 361:640 */       for (int i = 0; i < nodeList.length; i++)
/* 362:    */       {
/* 363:641 */         int[] deps = nodeList[i].getDependencies();
/* 364:642 */         if (deps != null) {
/* 365:643 */           for (int n = 0; n < deps.length; n++) {
/* 366:644 */             if (deps[n] == j) {
/* 367:645 */               currval++;
/* 368:    */             }
/* 369:    */           }
/* 370:    */         }
/* 371:    */       }
/* 372:650 */       inNum[j] = currval;
/* 373:    */     }
/* 374:652 */     for (int i = 0; i < inNum.length; i++) {
/* 375:653 */       sum += inNum[i];
/* 376:    */     }
/* 377:655 */     avg = sum / nodeList.length;
/* 378:656 */     avg *= threshold;
/* 379:657 */     for (int i = 0; i < nodeList.length; i++) {
/* 380:658 */       if ((inNum[i] > avg) && (!usesModule(this.librariesListModel_d, nodeList[i].getName())))
/* 381:    */       {
/* 382:660 */         this.standardNodeListModel_d.removeElement(nodeList[i].getName());
/* 383:661 */         this.suppliersListModel_d.addElement(nodeList[i].getName());
/* 384:    */       }
/* 385:    */     }
/* 386:666 */     for (int i = 0; i < this.clientsListModel_d.getSize(); i++)
/* 387:    */     {
/* 388:667 */       String client = (String)this.clientsListModel_d.getElementAt(i);
/* 389:668 */       for (int j = 0; j < this.suppliersListModel_d.getSize(); j++)
/* 390:    */       {
/* 391:669 */         String supp = (String)this.suppliersListModel_d.getElementAt(j);
/* 392:670 */         if (client.equals(supp))
/* 393:    */         {
/* 394:671 */           this.centralListModel_d.addElement(client);
/* 395:672 */           break;
/* 396:    */         }
/* 397:    */       }
/* 398:    */     }
/* 399:677 */     for (int i = 0; i < this.centralListModel_d.getSize(); i++)
/* 400:    */     {
/* 401:678 */       String name = (String)this.centralListModel_d.elementAt(i);
/* 402:679 */       this.clientsListModel_d.removeElement(name);
/* 403:680 */       this.suppliersListModel_d.removeElement(name);
/* 404:    */     }
/* 405:687 */     arrangeLibrariesClientsAndSuppliers();
/* 406:    */   }
/* 407:    */   
/* 408:    */   private void arrangeLibrariesClientsAndSuppliers()
/* 409:    */   {
/* 410:698 */     Node[] nodeList = this.initialGraph_d.getNodes();
/* 411:699 */     Node[] originalList = nodeList;
/* 412:702 */     for (int j = 0; j < originalList.length; j++)
/* 413:    */     {
/* 414:703 */       for (int i = 0; i < this.suppliersListModel_d.size(); i++)
/* 415:    */       {
/* 416:704 */         String name = originalList[j].getName();
/* 417:705 */         if (name.equals((String)this.suppliersListModel_d.elementAt(i)))
/* 418:    */         {
/* 419:706 */           originalList[j].setType(2);
/* 420:707 */           break;
/* 421:    */         }
/* 422:    */       }
/* 423:710 */       for (int i = 0; i < this.clientsListModel_d.size(); i++)
/* 424:    */       {
/* 425:711 */         String name = originalList[j].getName();
/* 426:712 */         if (name.equals((String)this.clientsListModel_d.elementAt(i)))
/* 427:    */         {
/* 428:713 */           originalList[j].setType(1);
/* 429:714 */           break;
/* 430:    */         }
/* 431:    */       }
/* 432:717 */       for (int i = 0; i < this.centralListModel_d.size(); i++)
/* 433:    */       {
/* 434:718 */         String name = originalList[j].getName();
/* 435:719 */         if (name.equals((String)this.centralListModel_d.elementAt(i)))
/* 436:    */         {
/* 437:720 */           originalList[j].setType(3);
/* 438:721 */           break;
/* 439:    */         }
/* 440:    */       }
/* 441:725 */       for (int i = 0; i < this.librariesListModel_d.size(); i++)
/* 442:    */       {
/* 443:726 */         String name = originalList[j].getName();
/* 444:727 */         if (name.equals((String)this.librariesListModel_d.elementAt(i)))
/* 445:    */         {
/* 446:728 */           originalList[j].setType(4);
/* 447:729 */           break;
/* 448:    */         }
/* 449:    */       }
/* 450:    */     }
/* 451:737 */     nodeList = new Node[originalList.length - (this.clientsListModel_d.size() + this.suppliersListModel_d.size() + this.centralListModel_d.size() + this.librariesListModel_d.size())];
/* 452:    */     
/* 453:    */ 
/* 454:740 */     int j = 0;
/* 455:743 */     for (int i = 0; i < originalList.length; i++) {
/* 456:744 */       if (originalList[i].getType() == 0) {
/* 457:745 */         nodeList[(j++)] = originalList[i].cloneNode();
/* 458:    */       }
/* 459:    */     }
/* 460:750 */     for (int i = 0; i < nodeList.length; i++)
/* 461:    */     {
/* 462:751 */       int[] deps = nodeList[i].getDependencies();
/* 463:752 */       if (deps != null)
/* 464:    */       {
/* 465:753 */         int[] weights = nodeList[i].getWeights();
/* 466:754 */         int count = 0;
/* 467:755 */         for (int n = 0; n < deps.length; n++) {
/* 468:756 */           if (originalList[deps[n]].getType() != 0)
/* 469:    */           {
/* 470:757 */             count++;
/* 471:758 */             deps[n] = -1;
/* 472:    */           }
/* 473:    */           else
/* 474:    */           {
/* 475:761 */             String name = originalList[deps[n]].getName();
/* 476:762 */             for (int x = 0; x < nodeList.length; x++) {
/* 477:763 */               if (name.equals(nodeList[x].getName()))
/* 478:    */               {
/* 479:764 */                 deps[n] = x;
/* 480:765 */                 break;
/* 481:    */               }
/* 482:    */             }
/* 483:    */           }
/* 484:    */         }
/* 485:771 */         if (deps.length == count)
/* 486:    */         {
/* 487:772 */           nodeList[i].setDependencies(null);
/* 488:773 */           nodeList[i].setWeights(null);
/* 489:    */         }
/* 490:    */         else
/* 491:    */         {
/* 492:776 */           int[] ndeps = new int[deps.length - count];
/* 493:777 */           int[] ws = null;
/* 494:778 */           if (weights != null) {
/* 495:779 */             ws = new int[deps.length - count];
/* 496:    */           }
/* 497:781 */           int m = 0;
/* 498:782 */           for (int x = 0; x < deps.length; x++) {
/* 499:783 */             if (deps[x] != -1)
/* 500:    */             {
/* 501:784 */               ndeps[(m++)] = deps[x];
/* 502:785 */               if (weights != null) {
/* 503:786 */                 ws[(m - 1)] = weights[x];
/* 504:    */               }
/* 505:    */             }
/* 506:    */           }
/* 507:790 */           nodeList[i].setDependencies(ndeps);
/* 508:791 */           nodeList[i].setWeights(ws);
/* 509:    */         }
/* 510:    */       }
/* 511:    */     }
/* 512:797 */     this.initialGraph_d.initGraph(nodeList.length);
/* 513:798 */     this.initialGraph_d.clear();
/* 514:799 */     this.initialGraph_d.setNodes(nodeList);
/* 515:800 */     this.initialGraph_d.setOriginalNodes(originalList);
/* 516:    */   }
/* 517:    */   
/* 518:    */   private boolean usesModule(DefaultListModel list, String element)
/* 519:    */   {
/* 520:815 */     for (int i = 0; i < list.size(); i++) {
/* 521:816 */       if (element.equals((String)list.elementAt(i))) {
/* 522:817 */         return true;
/* 523:    */       }
/* 524:    */     }
/* 525:820 */     return false;
/* 526:    */   }
/* 527:    */   
/* 528:    */   public Graph getBestGraph()
/* 529:    */   {
/* 530:830 */     if (this.clusteringMethod_d != null) {
/* 531:831 */       return this.clusteringMethod_d.getBestGraph();
/* 532:    */     }
/* 533:833 */     return null;
/* 534:    */   }
/* 535:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchAPIOld
 * JD-Core Version:    0.7.0.1
 */