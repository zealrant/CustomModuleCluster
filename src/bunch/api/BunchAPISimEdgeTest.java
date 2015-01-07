/*   1:    */ package bunch.api;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Hashtable;
/*   7:    */ import java.util.Iterator;
/*   8:    */ 
/*   9:    */ public class BunchAPISimEdgeTest
/*  10:    */ {
/*  11:    */   long totalNodes;
/*  12:    */   long totalAdjustments;
/*  13: 37 */   ArrayList bunchGraphs = null;
/*  14: 39 */   int[] esfreq = new int[11];
/*  15: 40 */   int[] esIfreq = new int[11];
/*  16: 41 */   int[] prfreq = new int[11];
/*  17: 42 */   int[] prIfreq = new int[11];
/*  18: 43 */   int[] meclFreq = new int[11];
/*  19: 44 */   int[] meclIFreq = new int[11];
/*  20: 46 */   String mode = "NAHC";
/*  21:    */   
/*  22:    */   public BunchAPISimEdgeTest()
/*  23:    */   {
/*  24: 50 */     String graphName = "d:\\linux\\linux";
/*  25: 51 */     this.mode = "NAHC";
/*  26:    */     
/*  27: 53 */     System.out.println("***** G R A P H   N A M E :   " + graphName + "\n");
/*  28: 54 */     writeHeader();
/*  29: 55 */     runTest(graphName, false);
/*  30: 56 */     runTest(graphName, true);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void runTest(String graphName, boolean removeSpecial)
/*  34:    */   {
/*  35: 61 */     this.totalNodes = (this.totalAdjustments = 0L);
/*  36: 62 */     this.bunchGraphs = new ArrayList();
/*  37: 63 */     boolean removeSpecialModules = removeSpecial;
/*  38: 65 */     for (int i = 0; i < 10; i++) {
/*  39: 67 */       runClustering(graphName, removeSpecialModules);
/*  40:    */     }
/*  41: 70 */     double avgValue = expirPR(this.prfreq);
/*  42: 71 */     double avgMeclValue = expirMecl(this.meclFreq);
/*  43: 72 */     double avgESValue = expirES(this.esfreq);
/*  44: 73 */     double avgIsomorphicValue = expirIsomorphicPR();
/*  45: 74 */     double avgMeclIValue = expirMecl(this.meclIFreq);
/*  46: 75 */     double avgESIValue = expirES(this.esIfreq);
/*  47: 76 */     BunchGraph bg = (BunchGraph)this.bunchGraphs.get(0);
/*  48: 77 */     double avgIsomorphicCount = expirIsomorphicCount();
/*  49: 80 */     if (!removeSpecial)
/*  50:    */     {
/*  51: 82 */       dumpFreqArray("PR (BASELINE)  ", this.prfreq, avgValue, avgIsomorphicCount);
/*  52: 83 */       dumpFreqArray("MECL(BASELINE) ", this.meclFreq, avgMeclValue, avgIsomorphicCount);
/*  53: 84 */       dumpFreqArray("ES(BASELINE)   ", this.esfreq, avgESValue, avgIsomorphicCount);
/*  54: 85 */       dumpFreqArray("PR (NO ISO)    ", this.prIfreq, avgIsomorphicValue, avgIsomorphicCount);
/*  55: 86 */       dumpFreqArray("MECL(NO ISO)   ", this.meclIFreq, avgMeclIValue, avgIsomorphicCount);
/*  56: 87 */       dumpFreqArray("ES(NO ISO)     ", this.esIfreq, avgESIValue, avgIsomorphicCount);
/*  57:    */     }
/*  58:    */     else
/*  59:    */     {
/*  60: 91 */       dumpFreqArray("PR (NO SPEC)   ", this.prfreq, avgValue, avgIsomorphicCount);
/*  61: 92 */       dumpFreqArray("MECL(NO SPEC)  ", this.meclFreq, avgMeclValue, avgIsomorphicCount);
/*  62: 93 */       dumpFreqArray("ES(NO SPEC)    ", this.esfreq, avgESValue, avgIsomorphicCount);
/*  63: 94 */       dumpFreqArray("PR (NO S&I)    ", this.prIfreq, avgIsomorphicValue, avgIsomorphicCount);
/*  64: 95 */       dumpFreqArray("MECL(NO S&I)   ", this.meclIFreq, avgMeclIValue, avgIsomorphicCount);
/*  65: 96 */       dumpFreqArray("ES(NO S&I)     ", this.esIfreq, avgESIValue, avgIsomorphicCount);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   private void writeHeader()
/*  70:    */   {
/*  71:109 */     System.out.println("                 |-------------------------------- F R E Q U E N C Y --------------------------------|");
/*  72:110 */     System.out.println("                   0-9   10-19   20-29   30-39   40-49   50-59   60-69   70-79   80-89   90-99     100     AVG  AVG-ISO");
/*  73:111 */     System.out.println("                 =====   =====   =====   =====   =====   =====   =====   =====   =====   =====   =====    ====  =======");
/*  74:    */   }
/*  75:    */   
/*  76:    */   private void dumpFreqArray(String lbl, int[] a, double avgValue, double avgIso)
/*  77:    */   {
/*  78:116 */     StringBuffer sb = new StringBuffer("      ");
/*  79:117 */     System.out.print(lbl + " [");
/*  80:118 */     for (int i = 0; i < a.length; i++)
/*  81:    */     {
/*  82:120 */       Integer count = new Integer(a[i]);
/*  83:121 */       String scnt = count.toString();
/*  84:122 */       StringBuffer sbItem = new StringBuffer(sb.toString());
/*  85:123 */       sbItem.replace(sbItem.length() - scnt.length() - 1, sbItem.length() - 1, scnt);
/*  86:124 */       System.out.print(sbItem);
/*  87:125 */       if (i < a.length - 1) {
/*  88:126 */         System.out.print("  ");
/*  89:    */       }
/*  90:    */     }
/*  91:128 */     System.out.print("] ");
/*  92:    */     
/*  93:130 */     int avg = (int)(avgValue * 100.0D);
/*  94:131 */     if (avg < 100) {
/*  95:132 */       avg++;
/*  96:    */     }
/*  97:133 */     Integer avgI = new Integer(avg);
/*  98:134 */     String scnt = avgI.toString();
/*  99:135 */     StringBuffer sbItem = new StringBuffer(sb.toString());
/* 100:136 */     sbItem.replace(sbItem.length() - scnt.length() - 1, sbItem.length() - 1, scnt);
/* 101:137 */     System.out.print(sbItem);
/* 102:    */     
/* 103:139 */     int avgIsoI = (int)avgIso;
/* 104:140 */     avgI = new Integer(avgIsoI);
/* 105:141 */     scnt = avgI.toString();
/* 106:142 */     sbItem = new StringBuffer(sb.toString());
/* 107:143 */     sbItem.replace(sbItem.length() - scnt.length() - 1, sbItem.length() - 1, scnt);
/* 108:144 */     System.out.println("   " + sbItem);
/* 109:    */   }
/* 110:    */   
/* 111:    */   private double expirIsomorphicPR()
/* 112:    */   {
/* 113:150 */     for (int i = 0; i < this.bunchGraphs.size(); i++)
/* 114:    */     {
/* 115:152 */       BunchGraph g = (BunchGraph)this.bunchGraphs.get(i);
/* 116:153 */       g.determineIsomorphic();
/* 117:    */     }
/* 118:155 */     return expirPR(this.prIfreq);
/* 119:    */   }
/* 120:    */   
/* 121:    */   private double expirIsomorphicCount()
/* 122:    */   {
/* 123:160 */     int accum = 0;
/* 124:161 */     for (int i = 0; i < this.bunchGraphs.size(); i++)
/* 125:    */     {
/* 126:163 */       BunchGraph g = (BunchGraph)this.bunchGraphs.get(i);
/* 127:164 */       accum += g.getTotalOverlapNodes();
/* 128:    */     }
/* 129:166 */     return accum / this.bunchGraphs.size();
/* 130:    */   }
/* 131:    */   
/* 132:    */   private void clearDistArray(int[] distArray)
/* 133:    */   {
/* 134:171 */     for (int i = 0; i < distArray.length; i++) {
/* 135:172 */       distArray[i] = 0;
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   private int findIndex(double value)
/* 140:    */   {
/* 141:177 */     if ((value < 0.0D) || (value > 1.0D)) {
/* 142:178 */       return 0;
/* 143:    */     }
/* 144:180 */     double tmp = value * 100.0D;
/* 145:181 */     int iTmp = (int)tmp;
/* 146:182 */     iTmp /= 10;
/* 147:183 */     return iTmp;
/* 148:    */   }
/* 149:    */   
/* 150:    */   private double expirES(int[] distArray)
/* 151:    */   {
/* 152:188 */     long trials = 0L;
/* 153:189 */     double accum = 0.0D;
/* 154:    */     
/* 155:191 */     clearDistArray(distArray);
/* 156:192 */     for (int i = 0; i < this.bunchGraphs.size(); i++)
/* 157:    */     {
/* 158:194 */       BunchGraph g1 = (BunchGraph)this.bunchGraphs.get(i);
/* 159:195 */       for (int j = i; j < this.bunchGraphs.size(); j++)
/* 160:    */       {
/* 161:197 */         BunchGraph g2 = (BunchGraph)this.bunchGraphs.get(j);
/* 162:    */         
/* 163:199 */         Double prValue = new Double(BunchGraphUtils.calcEdgeSimiliarities(g1, g2));
/* 164:214 */         if (i != j)
/* 165:    */         {
/* 166:216 */           trials += 1L;
/* 167:217 */           int idx = findIndex(prValue.doubleValue());
/* 168:218 */           distArray[idx] += 1;
/* 169:219 */           accum += prValue.doubleValue();
/* 170:    */         }
/* 171:    */       }
/* 172:    */     }
/* 173:223 */     return accum / trials;
/* 174:    */   }
/* 175:    */   
/* 176:    */   private double expirPR(int[] distArray)
/* 177:    */   {
/* 178:228 */     long trials = 0L;
/* 179:229 */     double accum = 0.0D;
/* 180:    */     
/* 181:231 */     clearDistArray(distArray);
/* 182:232 */     for (int i = 0; i < this.bunchGraphs.size(); i++)
/* 183:    */     {
/* 184:234 */       BunchGraph g1 = (BunchGraph)this.bunchGraphs.get(i);
/* 185:235 */       for (int j = i; j < this.bunchGraphs.size(); j++)
/* 186:    */       {
/* 187:237 */         BunchGraph g2 = (BunchGraph)this.bunchGraphs.get(j);
/* 188:    */         
/* 189:    */ 
/* 190:    */ 
/* 191:241 */         Hashtable results = BunchGraphUtils.calcPR(g1, g2);
/* 192:242 */         Double prValue = (Double)results.get("AVERAGE");
/* 193:243 */         String prsValue = "null";
/* 194:244 */         if (prsValue != null) {
/* 195:245 */           prsValue = prValue.toString();
/* 196:    */         } else {
/* 197:247 */           prValue = new Double(0.0D);
/* 198:    */         }
/* 199:251 */         if (i != j)
/* 200:    */         {
/* 201:253 */           trials += 1L;
/* 202:254 */           int idx = findIndex(prValue.doubleValue());
/* 203:255 */           distArray[idx] += 1;
/* 204:256 */           accum += prValue.doubleValue();
/* 205:    */         }
/* 206:    */       }
/* 207:    */     }
/* 208:260 */     return accum / trials;
/* 209:    */   }
/* 210:    */   
/* 211:    */   private double expirMecl(int[] distArray)
/* 212:    */   {
/* 213:265 */     long trials = 0L;
/* 214:266 */     double accum = 0.0D;
/* 215:    */     
/* 216:268 */     clearDistArray(distArray);
/* 217:269 */     for (int i = 0; i < this.bunchGraphs.size(); i++)
/* 218:    */     {
/* 219:271 */       BunchGraph g1 = (BunchGraph)this.bunchGraphs.get(i);
/* 220:272 */       for (int j = i; j < this.bunchGraphs.size(); j++)
/* 221:    */       {
/* 222:274 */         BunchGraph g2 = (BunchGraph)this.bunchGraphs.get(j);
/* 223:275 */         Hashtable meClValue1 = BunchGraphUtils.getMeClMeasurement(g1, g2);
/* 224:276 */         Hashtable meClValue2 = BunchGraphUtils.getMeClMeasurement(g2, g1);
/* 225:    */         
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:281 */         Double meclValue1 = (Double)meClValue1.get("MeclQualityMetric");
/* 230:282 */         Double meclValue2 = (Double)meClValue2.get("MeclQualityMetric");
/* 231:283 */         double d1 = meclValue1.doubleValue();
/* 232:284 */         double d2 = meclValue2.doubleValue();
/* 233:    */         
/* 234:286 */         double d = Math.max(d1, d2);
/* 235:    */         
/* 236:288 */         Double meclValue = new Double(d);
/* 237:290 */         if (i != j)
/* 238:    */         {
/* 239:292 */           trials += 1L;
/* 240:293 */           int idx = findIndex(meclValue.doubleValue());
/* 241:294 */           distArray[idx] += 1;
/* 242:295 */           accum += meclValue.doubleValue();
/* 243:    */         }
/* 244:    */       }
/* 245:    */     }
/* 246:299 */     return accum / trials;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void runClustering(String mdgFileName, boolean removeSpecialNodes)
/* 250:    */   {
/* 251:304 */     BunchAPI api = new BunchAPI();
/* 252:305 */     BunchProperties bp = new BunchProperties();
/* 253:306 */     bp.setProperty("MDGInputFile", mdgFileName);
/* 254:    */     
/* 255:308 */     Hashtable htSpecial = api.getSpecialModules(mdgFileName);
/* 256:    */     
/* 257:310 */     bp.setProperty("ClusteringAlgorithm", "NAHC");
/* 258:311 */     bp.setProperty("OutputFormat", "TextOutputFormat");
/* 259:313 */     if (this.mode.equals("SAHC"))
/* 260:    */     {
/* 261:315 */       bp.setProperty("NAHCHillClimbPct", "100");
/* 262:316 */       bp.setProperty("NAHCRandomizePct", "0");
/* 263:    */     }
/* 264:319 */     if (removeSpecialNodes) {
/* 265:320 */       api.setAPIProperty("SpecialModuleHashTable", htSpecial);
/* 266:    */     }
/* 267:322 */     api.setProperties(bp);
/* 268:323 */     api.run();
/* 269:324 */     Hashtable results = api.getResults();
/* 270:325 */     String sMedLvl = (String)results.get("MedianLevelGraph");
/* 271:326 */     Integer iMedLvl = new Integer(sMedLvl);
/* 272:    */     
/* 273:    */ 
/* 274:    */ 
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:333 */     BunchGraph bg = api.getPartitionedGraph(iMedLvl.intValue());
/* 279:    */     
/* 280:    */ 
/* 281:    */ 
/* 282:337 */     this.bunchGraphs.add(bg);
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void findIsomorphic(BunchGraph bg)
/* 286:    */   {
/* 287:348 */     Iterator nodeI = bg.getNodes().iterator();
/* 288:349 */     ArrayList theClusters = new ArrayList(bg.getClusters());
/* 289:350 */     int adjustCount = 0;
/* 290:351 */     int nodeAdjustCount = 0;
/* 291:352 */     int totalCount = bg.getNodes().size();
/* 292:353 */     boolean nodeIsomorphic = false;
/* 293:354 */     while (nodeI.hasNext())
/* 294:    */     {
/* 295:356 */       BunchNode bn = (BunchNode)nodeI.next();
/* 296:357 */       nodeIsomorphic = false;
/* 297:358 */       int[] cv = howConnected(bg, bn);
/* 298:359 */       printConnectVector(bn, cv);
/* 299:    */       
/* 300:361 */       int currClust = bn.getCluster();
/* 301:362 */       int currStrength = cv[currClust];
/* 302:363 */       BunchCluster homeCluster = (BunchCluster)theClusters.get(currClust);
/* 303:364 */       for (int i = 0; i < cv.length; i++) {
/* 304:366 */         if (i != currClust)
/* 305:    */         {
/* 306:367 */           int connectStrength = cv[i];
/* 307:368 */           if (connectStrength == currStrength)
/* 308:    */           {
/* 309:370 */             BunchCluster bc = (BunchCluster)theClusters.get(i);
/* 310:371 */             bc.addOverlapNode(bn);
/* 311:372 */             adjustCount++;
/* 312:373 */             nodeIsomorphic = true;
/* 313:    */           }
/* 314:    */         }
/* 315:    */       }
/* 316:378 */       if (nodeIsomorphic == true) {
/* 317:378 */         nodeAdjustCount++;
/* 318:    */       }
/* 319:    */     }
/* 320:380 */     System.out.println("Adjustments = Nodes: " + nodeAdjustCount + " --> " + adjustCount + "/" + totalCount);
/* 321:381 */     this.totalNodes += totalCount;
/* 322:382 */     this.totalAdjustments += nodeAdjustCount;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void printConnectVector(BunchNode bn, int[] cv)
/* 326:    */   {
/* 327:387 */     String status = "OK:";
/* 328:388 */     String nodeName = bn.getName();
/* 329:389 */     int nodeCluster = bn.getCluster();
/* 330:390 */     int homeStrength = cv[nodeCluster];
/* 331:391 */     String cvStr = "";
/* 332:392 */     for (int i = 0; i < cv.length; i++)
/* 333:    */     {
/* 334:394 */       String modifier = "";
/* 335:395 */       int cstr = cv[i];
/* 336:396 */       if (i == nodeCluster) {
/* 337:396 */         modifier = "*";
/* 338:    */       }
/* 339:397 */       if (i != nodeCluster)
/* 340:    */       {
/* 341:399 */         if (cstr > homeStrength)
/* 342:    */         {
/* 343:401 */           modifier = ">";
/* 344:402 */           status = "BAD:";
/* 345:    */         }
/* 346:404 */         if (cstr < homeStrength) {
/* 347:404 */           modifier = "<";
/* 348:    */         }
/* 349:405 */         if (cstr == homeStrength)
/* 350:    */         {
/* 351:407 */           if (!status.equals("BAD:")) {
/* 352:408 */             status = "ISOMORPHIC:";
/* 353:    */           }
/* 354:409 */           modifier = "=";
/* 355:    */         }
/* 356:    */       }
/* 357:412 */       Integer idx = new Integer(i);
/* 358:413 */       Integer clustStrength = new Integer(cv[i]);
/* 359:414 */       cvStr = cvStr + "(" + modifier + clustStrength.toString() + ")";
/* 360:    */     }
/* 361:    */   }
/* 362:    */   
/* 363:    */   public int[] howConnected(BunchGraph bg, BunchNode bn)
/* 364:    */   {
/* 365:421 */     int howManyClusters = bg.getClusters().size();
/* 366:422 */     int[] connectVector = new int[howManyClusters];
/* 367:423 */     Iterator fdeps = null;
/* 368:424 */     Iterator bdeps = null;
/* 369:426 */     for (int i = 0; i < connectVector.length; i++) {
/* 370:427 */       connectVector[i] = 0;
/* 371:    */     }
/* 372:429 */     if (bn.getDeps() != null)
/* 373:    */     {
/* 374:431 */       fdeps = bn.getDeps().iterator();
/* 375:432 */       while (fdeps.hasNext())
/* 376:    */       {
/* 377:434 */         BunchEdge be = (BunchEdge)fdeps.next();
/* 378:435 */         BunchNode target = be.getDestNode();
/* 379:436 */         int targetCluster = target.getCluster();
/* 380:437 */         connectVector[targetCluster] += 1;
/* 381:    */       }
/* 382:    */     }
/* 383:442 */     if (bn.getBackDeps() != null)
/* 384:    */     {
/* 385:444 */       bdeps = bn.getBackDeps().iterator();
/* 386:445 */       while (bdeps.hasNext())
/* 387:    */       {
/* 388:447 */         BunchEdge be = (BunchEdge)bdeps.next();
/* 389:448 */         BunchNode target = be.getSrcNode();
/* 390:449 */         int targetCluster = target.getCluster();
/* 391:450 */         connectVector[targetCluster] += 1;
/* 392:    */       }
/* 393:    */     }
/* 394:454 */     return connectVector;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public void printBunchGraph(BunchGraph bg)
/* 398:    */   {
/* 399:459 */     Collection nodeList = bg.getNodes();
/* 400:460 */     Collection edgeList = bg.getEdges();
/* 401:461 */     Collection clusterList = bg.getClusters();
/* 402:    */     
/* 403:    */ 
/* 404:    */ 
/* 405:    */ 
/* 406:466 */     System.out.println("PRINTING BUNCH GRAPH\n");
/* 407:467 */     System.out.println("Node Count:         " + nodeList.size());
/* 408:468 */     System.out.println("Edge Count:         " + edgeList.size());
/* 409:469 */     System.out.println("MQ Value:           " + bg.getMQValue());
/* 410:470 */     System.out.println("Number of Clusters: " + bg.getNumClusters());
/* 411:471 */     System.out.println();
/* 412:    */     
/* 413:    */ 
/* 414:    */ 
/* 415:    */ 
/* 416:    */ 
/* 417:477 */     Iterator nodeI = nodeList.iterator();
/* 418:479 */     while (nodeI.hasNext())
/* 419:    */     {
/* 420:481 */       BunchNode bn = (BunchNode)nodeI.next();
/* 421:482 */       Iterator fdeps = null;
/* 422:483 */       Iterator bdeps = null;
/* 423:    */       
/* 424:485 */       System.out.println("NODE:         " + bn.getName());
/* 425:486 */       System.out.println("Cluster ID:   " + bn.getCluster());
/* 426:489 */       if (bn.getDeps() != null)
/* 427:    */       {
/* 428:491 */         fdeps = bn.getDeps().iterator();
/* 429:492 */         while (fdeps.hasNext())
/* 430:    */         {
/* 431:494 */           BunchEdge be = (BunchEdge)fdeps.next();
/* 432:495 */           String depName = be.getDestNode().getName();
/* 433:496 */           int weight = be.getWeight();
/* 434:497 */           System.out.println("   ===> " + depName + " (" + weight + ")");
/* 435:    */         }
/* 436:    */       }
/* 437:502 */       if (bn.getBackDeps() != null)
/* 438:    */       {
/* 439:504 */         bdeps = bn.getBackDeps().iterator();
/* 440:505 */         while (bdeps.hasNext())
/* 441:    */         {
/* 442:507 */           BunchEdge be = (BunchEdge)bdeps.next();
/* 443:508 */           String depName = be.getSrcNode().getName();
/* 444:509 */           int weight = be.getWeight();
/* 445:510 */           System.out.println("   <=== " + depName + " (" + weight + ")");
/* 446:    */         }
/* 447:    */       }
/* 448:513 */       System.out.println();
/* 449:    */     }
/* 450:520 */     System.out.println("Cluster Breakdown\n");
/* 451:521 */     Iterator clusts = bg.getClusters().iterator();
/* 452:522 */     while (clusts.hasNext())
/* 453:    */     {
/* 454:524 */       BunchCluster bc = (BunchCluster)clusts.next();
/* 455:525 */       System.out.println("Cluster id:   " + bc.getID());
/* 456:526 */       System.out.println("Custer name:  " + bc.getName());
/* 457:527 */       System.out.println("Cluster size: " + bc.getSize());
/* 458:    */       
/* 459:529 */       Iterator members = bc.getClusterNodes().iterator();
/* 460:530 */       while (members.hasNext())
/* 461:    */       {
/* 462:532 */         BunchNode bn = (BunchNode)members.next();
/* 463:533 */         System.out.println("   --> " + bn.getName() + "   (" + bn.getCluster() + ")");
/* 464:    */       }
/* 465:535 */       System.out.println();
/* 466:    */     }
/* 467:    */   }
/* 468:    */   
/* 469:    */   public static void main(String[] args)
/* 470:    */   {
/* 471:540 */     BunchAPISimEdgeTest bunchAPISimEdgeTest1 = new BunchAPISimEdgeTest();
/* 472:    */   }
/* 473:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.BunchAPISimEdgeTest
 * JD-Core Version:    0.7.0.1
 */