/*   1:    */ package bunch.api;
/*   2:    */ 
/*   3:    */ import bunch.Graph;
/*   4:    */ import bunch.NextLevelGraph;
/*   5:    */ import bunch.Node;
/*   6:    */ import java.io.BufferedWriter;
/*   7:    */ import java.io.FileWriter;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Collection;
/*  12:    */ import java.util.Hashtable;
/*  13:    */ import java.util.Iterator;
/*  14:    */ import java.util.Stack;
/*  15:    */ 
/*  16:    */ public class BunchGraph
/*  17:    */ {
/*  18: 38 */   ArrayList nodeList = null;
/*  19: 39 */   ArrayList edgeList = null;
/*  20: 40 */   ArrayList clusterList = null;
/*  21: 41 */   Hashtable nodeHT = null;
/*  22: 42 */   boolean includesIsomorphicUpdates = false;
/*  23: 44 */   double mqValue = 0.0D;
/*  24: 45 */   int numClusters = 0;
/*  25: 47 */   int gLvl = -1;
/*  26:    */   
/*  27:    */   public boolean isomorphicUpdatesIncluded()
/*  28:    */   {
/*  29: 54 */     return this.includesIsomorphicUpdates;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public int getGraphLevel()
/*  33:    */   {
/*  34: 57 */     return this.gLvl;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setGraphLevel(int l)
/*  38:    */   {
/*  39: 60 */     this.gLvl = l;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Collection getNodes()
/*  43:    */   {
/*  44: 64 */     return this.nodeList;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int getTotalOverlapNodes()
/*  48:    */   {
/*  49: 69 */     int total = 0;
/*  50: 70 */     if (this.clusterList != null) {
/*  51: 72 */       for (int i = 0; i < this.clusterList.size(); i++)
/*  52:    */       {
/*  53: 74 */         BunchCluster bc = (BunchCluster)this.clusterList.get(i);
/*  54: 75 */         total += bc.getOverlapNodeCount();
/*  55:    */       }
/*  56:    */     }
/*  57: 78 */     return total;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Collection getEdges()
/*  61:    */   {
/*  62: 83 */     return this.edgeList;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Collection getClusters()
/*  66:    */   {
/*  67: 87 */     return this.clusterList;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public double getMQValue()
/*  71:    */   {
/*  72: 90 */     return this.mqValue;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getNumClusters()
/*  76:    */   {
/*  77: 94 */     return this.numClusters;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getNumNodes()
/*  81:    */   {
/*  82: 99 */     return this.nodeList.size();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getNumEdges()
/*  86:    */   {
/*  87:104 */     return this.edgeList.size();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getIntraEdgeCount()
/*  91:    */   {
/*  92:109 */     int numEdges = this.edgeList.size();
/*  93:110 */     int tally = 0;
/*  94:111 */     for (int i = 0; i < numEdges; i++)
/*  95:    */     {
/*  96:113 */       BunchEdge be = (BunchEdge)this.edgeList.get(i);
/*  97:114 */       BunchNode src = be.getSrcNode();
/*  98:115 */       BunchNode dst = be.getDestNode();
/*  99:116 */       if (src.getCluster() == dst.getCluster()) {
/* 100:117 */         tally++;
/* 101:    */       }
/* 102:    */     }
/* 103:119 */     return tally;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getInterEdgeCount()
/* 107:    */   {
/* 108:124 */     int numEdges = this.edgeList.size();
/* 109:125 */     int tally = 0;
/* 110:126 */     for (int i = 0; i < numEdges; i++)
/* 111:    */     {
/* 112:128 */       BunchEdge be = (BunchEdge)this.edgeList.get(i);
/* 113:129 */       BunchNode src = be.getSrcNode();
/* 114:130 */       BunchNode dst = be.getDestNode();
/* 115:131 */       if (src.getCluster() != dst.getCluster()) {
/* 116:132 */         tally++;
/* 117:    */       }
/* 118:    */     }
/* 119:134 */     return tally;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Collection getIntraEdgeList()
/* 123:    */   {
/* 124:139 */     ArrayList al = new ArrayList();
/* 125:140 */     int numEdges = this.edgeList.size();
/* 126:142 */     for (int i = 0; i < numEdges; i++)
/* 127:    */     {
/* 128:144 */       BunchEdge be = (BunchEdge)this.edgeList.get(i);
/* 129:145 */       BunchNode src = be.getSrcNode();
/* 130:146 */       BunchNode dst = be.getDestNode();
/* 131:147 */       if (src.getCluster() == dst.getCluster()) {
/* 132:148 */         al.add(be);
/* 133:    */       }
/* 134:    */     }
/* 135:150 */     return al;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Collection getInterEdgeList()
/* 139:    */   {
/* 140:155 */     ArrayList al = new ArrayList();
/* 141:156 */     int numEdges = this.edgeList.size();
/* 142:158 */     for (int i = 0; i < numEdges; i++)
/* 143:    */     {
/* 144:160 */       BunchEdge be = (BunchEdge)this.edgeList.get(i);
/* 145:161 */       BunchNode src = be.getSrcNode();
/* 146:162 */       BunchNode dst = be.getDestNode();
/* 147:163 */       if (src.getCluster() != dst.getCluster()) {
/* 148:164 */         al.add(be);
/* 149:    */       }
/* 150:    */     }
/* 151:166 */     return al;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void writeSILFile(String fname)
/* 155:    */     throws IOException
/* 156:    */   {
/* 157:171 */     writeSILFile(fname, false);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public BunchNode findNode(String nodeName)
/* 161:    */   {
/* 162:176 */     if (this.nodeHT == null) {
/* 163:177 */       this.nodeHT = constructNodeHT();
/* 164:    */     }
/* 165:179 */     return (BunchNode)this.nodeHT.get(nodeName);
/* 166:    */   }
/* 167:    */   
/* 168:    */   private Hashtable constructNodeHT()
/* 169:    */   {
/* 170:184 */     Hashtable h = new Hashtable();
/* 171:185 */     h.clear();
/* 172:186 */     for (int i = 0; i < this.nodeList.size(); i++)
/* 173:    */     {
/* 174:188 */       BunchNode theNode = (BunchNode)this.nodeList.get(i);
/* 175:189 */       String key = theNode.getName();
/* 176:190 */       h.put(key, theNode);
/* 177:    */     }
/* 178:192 */     return h;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void writeSILFile(String fname, boolean includeOverlapNodes)
/* 182:    */     throws IOException
/* 183:    */   {
/* 184:198 */     FileWriter outF = new FileWriter(fname);
/* 185:199 */     BufferedWriter out = new BufferedWriter(outF);
/* 186:201 */     for (int i = 0; i < this.clusterList.size(); i++)
/* 187:    */     {
/* 188:203 */       BunchCluster bc = (BunchCluster)this.clusterList.get(i);
/* 189:204 */       ArrayList clusterNodes = new ArrayList(bc.getClusterNodes());
/* 190:205 */       if (clusterNodes.size() != 0)
/* 191:    */       {
/* 192:207 */         out.write("SS(" + bc.getName() + ")=");
/* 193:208 */         for (int j = 0; j < clusterNodes.size(); j++)
/* 194:    */         {
/* 195:210 */           BunchNode bn = (BunchNode)clusterNodes.get(j);
/* 196:211 */           out.write(bn.getName());
/* 197:212 */           if (j < clusterNodes.size() - 1) {
/* 198:213 */             out.write(", ");
/* 199:    */           }
/* 200:    */         }
/* 201:215 */         if ((includeOverlapNodes == true) && (bc.getOverlapNodes() != null))
/* 202:    */         {
/* 203:217 */           ArrayList overlapNodes = new ArrayList(bc.getOverlapNodes());
/* 204:218 */           if (overlapNodes.size() > 0) {
/* 205:219 */             out.write(", ");
/* 206:    */           }
/* 207:221 */           for (int j = 0; j < overlapNodes.size(); j++)
/* 208:    */           {
/* 209:223 */             BunchNode bn = (BunchNode)overlapNodes.get(j);
/* 210:224 */             out.write(bn.getName());
/* 211:225 */             if (j < overlapNodes.size() - 1) {
/* 212:226 */               out.write(", ");
/* 213:    */             }
/* 214:    */           }
/* 215:    */         }
/* 216:229 */         out.write("\r\n");
/* 217:    */       }
/* 218:    */     }
/* 219:231 */     out.close();
/* 220:    */   }
/* 221:    */   
/* 222:    */   private ArrayList getChildrenList(Node n)
/* 223:    */   {
/* 224:236 */     ArrayList a = new ArrayList();
/* 225:237 */     if (!n.isCluster())
/* 226:    */     {
/* 227:239 */       a.add(n);
/* 228:240 */       return a;
/* 229:    */     }
/* 230:242 */     Stack s = new Stack();
/* 231:243 */     s.push(n);
/* 232:244 */     while (!s.isEmpty())
/* 233:    */     {
/* 234:246 */       Node c = (Node)s.pop();
/* 235:247 */       Node[] childrenList = c.children;
/* 236:248 */       for (int i = 0; i < childrenList.length; i++)
/* 237:    */       {
/* 238:250 */         Node aChild = childrenList[i];
/* 239:251 */         if (aChild.isCluster() == true) {
/* 240:252 */           s.push(aChild);
/* 241:    */         } else {
/* 242:254 */           a.add(aChild);
/* 243:    */         }
/* 244:    */       }
/* 245:    */     }
/* 246:257 */     return a;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public Hashtable calcIsomorphicStats()
/* 250:    */   {
/* 251:262 */     Iterator nodeI = getNodes().iterator();
/* 252:263 */     ArrayList theClusters = new ArrayList(getClusters());
/* 253:264 */     int isoConfigs = 0;
/* 254:265 */     int isoNodes = 0;
/* 255:266 */     int totalCount = getNodes().size();
/* 256:267 */     int totalPossibilities = 0;
/* 257:268 */     boolean nodeIsomorphic = false;
/* 258:270 */     while (nodeI.hasNext())
/* 259:    */     {
/* 260:272 */       BunchNode bn = (BunchNode)nodeI.next();
/* 261:273 */       nodeIsomorphic = false;
/* 262:274 */       int[] cv = howConnected(bn);
/* 263:    */       
/* 264:276 */       int currClust = bn.getCluster();
/* 265:277 */       int currStrength = cv[currClust];
/* 266:278 */       BunchCluster homeCluster = (BunchCluster)theClusters.get(currClust);
/* 267:279 */       for (int i = 0; i < cv.length; i++) {
/* 268:281 */         if (i != currClust)
/* 269:    */         {
/* 270:283 */           int connectStrength = cv[i];
/* 271:284 */           if (connectStrength > 0) {
/* 272:285 */             totalPossibilities++;
/* 273:    */           }
/* 274:286 */           if (connectStrength == currStrength)
/* 275:    */           {
/* 276:288 */             BunchCluster bc = (BunchCluster)theClusters.get(i);
/* 277:    */             
/* 278:    */ 
/* 279:291 */             isoConfigs++;
/* 280:292 */             nodeIsomorphic = true;
/* 281:    */           }
/* 282:    */         }
/* 283:    */       }
/* 284:298 */       if (nodeIsomorphic == true) {
/* 285:298 */         isoNodes++;
/* 286:    */       }
/* 287:    */     }
/* 288:301 */     Hashtable h = new Hashtable();
/* 289:302 */     h.put("NODES", new Integer(isoNodes));
/* 290:303 */     h.put("CONFIGS", new Integer(isoConfigs));
/* 291:    */     
/* 292:305 */     double nodesPct = isoNodes / getNumNodes();
/* 293:306 */     double configPct = isoConfigs / totalPossibilities;
/* 294:307 */     h.put("NODEDENSITY", new Double(nodesPct));
/* 295:308 */     h.put("CONFIGDENSITY", new Double(configPct));
/* 296:309 */     return h;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void determineIsomorphic()
/* 300:    */   {
/* 301:317 */     if (this.includesIsomorphicUpdates == true) {
/* 302:318 */       return;
/* 303:    */     }
/* 304:320 */     this.includesIsomorphicUpdates = true;
/* 305:321 */     Iterator nodeI = getNodes().iterator();
/* 306:322 */     ArrayList theClusters = new ArrayList(getClusters());
/* 307:323 */     int adjustCount = 0;
/* 308:324 */     int nodeAdjustCount = 0;
/* 309:325 */     int totalCount = getNodes().size();
/* 310:326 */     boolean nodeIsomorphic = false;
/* 311:328 */     while (nodeI.hasNext())
/* 312:    */     {
/* 313:330 */       BunchNode bn = (BunchNode)nodeI.next();
/* 314:331 */       nodeIsomorphic = false;
/* 315:332 */       int[] cv = howConnected(bn);
/* 316:    */       
/* 317:334 */       int currClust = bn.getCluster();
/* 318:335 */       int currStrength = cv[currClust];
/* 319:336 */       BunchCluster homeCluster = (BunchCluster)theClusters.get(currClust);
/* 320:337 */       for (int i = 0; i < cv.length; i++) {
/* 321:339 */         if (i != currClust)
/* 322:    */         {
/* 323:340 */           int connectStrength = cv[i];
/* 324:341 */           if (connectStrength == currStrength)
/* 325:    */           {
/* 326:343 */             BunchCluster bc = (BunchCluster)theClusters.get(i);
/* 327:344 */             bc.addOverlapNode(bn);
/* 328:345 */             bn.subscribeToCluster(bc);
/* 329:346 */             adjustCount++;
/* 330:347 */             nodeIsomorphic = true;
/* 331:    */           }
/* 332:    */         }
/* 333:    */       }
/* 334:353 */       if (nodeIsomorphic == true) {
/* 335:353 */         nodeAdjustCount++;
/* 336:    */       }
/* 337:    */     }
/* 338:    */   }
/* 339:    */   
/* 340:    */   private int[] howConnected(BunchNode bn)
/* 341:    */   {
/* 342:362 */     int howManyClusters = getClusters().size();
/* 343:363 */     int[] connectVector = new int[howManyClusters];
/* 344:364 */     Iterator fdeps = null;
/* 345:365 */     Iterator bdeps = null;
/* 346:367 */     for (int i = 0; i < connectVector.length; i++) {
/* 347:368 */       connectVector[i] = 0;
/* 348:    */     }
/* 349:370 */     if (bn.getDeps() != null)
/* 350:    */     {
/* 351:372 */       fdeps = bn.getDeps().iterator();
/* 352:373 */       while (fdeps.hasNext())
/* 353:    */       {
/* 354:375 */         BunchEdge be = (BunchEdge)fdeps.next();
/* 355:376 */         BunchNode target = be.getDestNode();
/* 356:377 */         int targetCluster = target.getCluster();
/* 357:378 */         connectVector[targetCluster] += 1;
/* 358:    */       }
/* 359:    */     }
/* 360:383 */     if (bn.getBackDeps() != null)
/* 361:    */     {
/* 362:385 */       bdeps = bn.getBackDeps().iterator();
/* 363:386 */       while (bdeps.hasNext())
/* 364:    */       {
/* 365:388 */         BunchEdge be = (BunchEdge)bdeps.next();
/* 366:389 */         BunchNode target = be.getSrcNode();
/* 367:390 */         int targetCluster = target.getCluster();
/* 368:391 */         connectVector[targetCluster] += 1;
/* 369:    */       }
/* 370:    */     }
/* 371:395 */     return connectVector;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public boolean construct(Graph gBase)
/* 375:    */   {
/* 376:401 */     Graph g = gBase.getDetailedGraph();
/* 377:    */     
/* 378:403 */     this.nodeList = new ArrayList();
/* 379:404 */     this.edgeList = new ArrayList();
/* 380:405 */     this.clusterList = new ArrayList();
/* 381:    */     
/* 382:407 */     Node[] graphNodes = g.getNodes();
/* 383:408 */     int[] clustVector = g.getClusters();
/* 384:413 */     if (graphNodes.length != clustVector.length) {
/* 385:414 */       return false;
/* 386:    */     }
/* 387:417 */     for (int i = 0; i < graphNodes.length; i++)
/* 388:    */     {
/* 389:419 */       Node n = graphNodes[i];
/* 390:420 */       BunchNode bn = new BunchNode(n.getName(), i, clustVector[i], n.isCluster());
/* 391:    */       
/* 392:422 */       this.nodeList.add(i, bn);
/* 393:    */     }
/* 394:426 */     for (int i = 0; i < graphNodes.length; i++)
/* 395:    */     {
/* 396:428 */       Node n = graphNodes[i];
/* 397:429 */       int[] deps = n.getDependencies();
/* 398:430 */       int[] weights = n.getWeights();
/* 399:431 */       int[] backDeps = n.getBackEdges();
/* 400:432 */       int[] backWeights = n.getBeWeights();
/* 401:433 */       if ((deps != null) && (deps.length != weights.length)) {
/* 402:434 */         return false;
/* 403:    */       }
/* 404:435 */       if ((backDeps != null) && (backDeps.length != backWeights.length)) {
/* 405:436 */         return false;
/* 406:    */       }
/* 407:438 */       ArrayList forwardList = null;
/* 408:439 */       ArrayList backList = null;
/* 409:441 */       if (deps != null) {
/* 410:442 */         forwardList = new ArrayList();
/* 411:    */       }
/* 412:444 */       for (int j = 0; j < deps.length; j++)
/* 413:    */       {
/* 414:446 */         int edgeWeight = weights[j];
/* 415:447 */         int srcIdx = i;
/* 416:448 */         int destIdx = deps[j];
/* 417:449 */         BunchEdge be = new BunchEdge(edgeWeight, (BunchNode)this.nodeList.get(srcIdx), (BunchNode)this.nodeList.get(destIdx));
/* 418:    */         
/* 419:    */ 
/* 420:452 */         this.edgeList.add(be);
/* 421:453 */         forwardList.add(be);
/* 422:    */       }
/* 423:455 */       if (backDeps != null) {
/* 424:456 */         backList = new ArrayList();
/* 425:    */       }
/* 426:458 */       for (int j = 0; j < backDeps.length; j++)
/* 427:    */       {
/* 428:460 */         int edgeWeight = backWeights[j];
/* 429:461 */         int srcIdx = backDeps[j];
/* 430:462 */         int destIdx = i;
/* 431:463 */         BunchEdge be = new BunchEdge(edgeWeight, (BunchNode)this.nodeList.get(srcIdx), (BunchNode)this.nodeList.get(destIdx));
/* 432:    */         
/* 433:    */ 
/* 434:    */ 
/* 435:467 */         backList.add(be);
/* 436:    */       }
/* 437:470 */       BunchNode bn = (BunchNode)this.nodeList.get(i);
/* 438:471 */       bn.setDeps(forwardList, backList);
/* 439:    */     }
/* 440:474 */     this.mqValue = g.getObjectiveFunctionValue();
/* 441:475 */     int[] cnames = g.getClusterNames();
/* 442:476 */     this.numClusters = cnames.length;
/* 443:    */     
/* 444:    */ 
/* 445:479 */     Graph nextLvlG = null;
/* 446:480 */     Graph cLvlG = gBase.cloneGraph();
/* 447:    */     
/* 448:    */ 
/* 449:    */ 
/* 450:    */ 
/* 451:485 */     NextLevelGraph nextLvl = new NextLevelGraph();
/* 452:486 */     nextLvlG = nextLvl.genNextLevelGraph(cLvlG);
/* 453:487 */     Node[] nodeArray = nextLvlG.getNodes();
/* 454:489 */     for (int i = 0; i < nodeArray.length; i++)
/* 455:    */     {
/* 456:491 */       String cname = nodeArray[i].getName();
/* 457:492 */       if (nodeArray[i].isCluster)
/* 458:    */       {
/* 459:493 */         Node[] members = nodeArray[i].children;
/* 460:494 */         ArrayList memberList = new ArrayList();
/* 461:496 */         for (int j = 0; j < members.length; j++)
/* 462:    */         {
/* 463:498 */           Node aMember = members[j];
/* 464:499 */           ArrayList childrenList = getChildrenList(aMember);
/* 465:500 */           for (int k = 0; k < childrenList.size(); k++)
/* 466:    */           {
/* 467:502 */             Node leafMember = (Node)childrenList.get(k);
/* 468:503 */             String memberName = leafMember.getName();
/* 469:504 */             for (int l = 0; l < this.nodeList.size(); l++)
/* 470:    */             {
/* 471:506 */               BunchNode bn = (BunchNode)this.nodeList.get(l);
/* 472:507 */               String nodeName = bn.getName();
/* 473:508 */               if (memberName.equals(nodeName)) {
/* 474:510 */                 if (bn.getCluster() != -1)
/* 475:    */                 {
/* 476:512 */                   bn.resetCluster(i);
/* 477:513 */                   memberList.add(bn);
/* 478:    */                 }
/* 479:    */               }
/* 480:    */             }
/* 481:    */           }
/* 482:    */         }
/* 483:519 */         if (memberList.size() > 0)
/* 484:    */         {
/* 485:521 */           BunchCluster bc = new BunchCluster(i, cname, memberList);
/* 486:522 */           this.clusterList.add(bc);
/* 487:    */         }
/* 488:    */       }
/* 489:    */     }
/* 490:545 */     this.numClusters = this.clusterList.size();
/* 491:546 */     return true;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public boolean constructRaw(Graph g)
/* 495:    */   {
/* 496:554 */     this.nodeList = new ArrayList();
/* 497:555 */     this.edgeList = new ArrayList();
/* 498:556 */     this.clusterList = new ArrayList();
/* 499:    */     
/* 500:558 */     Node[] graphNodes = g.getNodes();
/* 501:559 */     int[] clustVector = g.getClusters();
/* 502:563 */     if (graphNodes.length != clustVector.length) {
/* 503:564 */       return false;
/* 504:    */     }
/* 505:567 */     for (int i = 0; i < graphNodes.length; i++)
/* 506:    */     {
/* 507:569 */       Node n = graphNodes[i];
/* 508:570 */       BunchNode bn = new BunchNode(n.getName(), i, clustVector[i], n.isCluster());
/* 509:    */       
/* 510:572 */       this.nodeList.add(i, bn);
/* 511:    */     }
/* 512:576 */     for (int i = 0; i < graphNodes.length; i++)
/* 513:    */     {
/* 514:578 */       Node n = graphNodes[i];
/* 515:579 */       int[] deps = n.getDependencies();
/* 516:580 */       int[] weights = n.getWeights();
/* 517:581 */       int[] backDeps = n.getBackEdges();
/* 518:582 */       int[] backWeights = n.getBeWeights();
/* 519:583 */       if ((deps != null) && (deps.length != weights.length)) {
/* 520:584 */         return false;
/* 521:    */       }
/* 522:585 */       if ((backDeps != null) && (backDeps.length != backWeights.length)) {
/* 523:586 */         return false;
/* 524:    */       }
/* 525:588 */       ArrayList forwardList = null;
/* 526:589 */       ArrayList backList = null;
/* 527:591 */       if (deps != null) {
/* 528:592 */         forwardList = new ArrayList();
/* 529:    */       }
/* 530:594 */       for (int j = 0; j < deps.length; j++)
/* 531:    */       {
/* 532:596 */         int edgeWeight = weights[j];
/* 533:597 */         int srcIdx = i;
/* 534:598 */         int destIdx = deps[j];
/* 535:599 */         BunchEdge be = new BunchEdge(edgeWeight, (BunchNode)this.nodeList.get(srcIdx), (BunchNode)this.nodeList.get(destIdx));
/* 536:    */         
/* 537:    */ 
/* 538:602 */         this.edgeList.add(be);
/* 539:603 */         forwardList.add(be);
/* 540:    */       }
/* 541:605 */       if (backDeps != null) {
/* 542:606 */         backList = new ArrayList();
/* 543:    */       }
/* 544:608 */       for (int j = 0; j < backDeps.length; j++)
/* 545:    */       {
/* 546:610 */         int edgeWeight = backWeights[j];
/* 547:611 */         int srcIdx = backDeps[j];
/* 548:612 */         int destIdx = i;
/* 549:613 */         BunchEdge be = new BunchEdge(edgeWeight, (BunchNode)this.nodeList.get(srcIdx), (BunchNode)this.nodeList.get(destIdx));
/* 550:    */         
/* 551:    */ 
/* 552:    */ 
/* 553:617 */         backList.add(be);
/* 554:    */       }
/* 555:620 */       BunchNode bn = (BunchNode)this.nodeList.get(i);
/* 556:621 */       bn.setDeps(forwardList, backList);
/* 557:    */     }
/* 558:624 */     this.mqValue = g.getObjectiveFunctionValue();
/* 559:625 */     int[] cnames = g.getClusterNames();
/* 560:626 */     this.numClusters = cnames.length;
/* 561:    */     
/* 562:    */ 
/* 563:629 */     Graph nextLvlG = null;
/* 564:630 */     Graph cLvlG = g.cloneGraph();
/* 565:    */     
/* 566:    */ 
/* 567:    */ 
/* 568:    */ 
/* 569:635 */     NextLevelGraph nextLvl = new NextLevelGraph();
/* 570:636 */     nextLvlG = nextLvl.genNextLevelGraph(cLvlG);
/* 571:637 */     Node[] nodeArray = nextLvlG.getNodes();
/* 572:    */     
/* 573:639 */     System.out.println("DEBUG, Nodes = " + nodeArray.length);
/* 574:640 */     for (int i = 0; i < nodeArray.length; i++)
/* 575:    */     {
/* 576:642 */       String cname = nodeArray[i].getName();
/* 577:643 */       if (nodeArray[i].isCluster)
/* 578:    */       {
/* 579:644 */         Node[] members = nodeArray[i].children;
/* 580:645 */         ArrayList memberList = new ArrayList();
/* 581:647 */         for (int j = 0; j < members.length; j++)
/* 582:    */         {
/* 583:649 */           Node aMember = members[j];
/* 584:650 */           String memberName = aMember.getName();
/* 585:651 */           for (int k = 0; k < this.nodeList.size(); k++)
/* 586:    */           {
/* 587:653 */             BunchNode bn = (BunchNode)this.nodeList.get(k);
/* 588:654 */             String nodeName = bn.getName();
/* 589:655 */             if (memberName.equals(nodeName))
/* 590:    */             {
/* 591:657 */               bn.resetCluster(i);
/* 592:658 */               memberList.add(bn);
/* 593:    */             }
/* 594:    */           }
/* 595:    */         }
/* 596:662 */         if (memberList.size() > 0)
/* 597:    */         {
/* 598:664 */           BunchCluster bc = new BunchCluster(i, cname, memberList);
/* 599:665 */           this.clusterList.add(bc);
/* 600:    */         }
/* 601:    */       }
/* 602:    */     }
/* 603:688 */     this.numClusters = this.clusterList.size();
/* 604:689 */     return true;
/* 605:    */   }
/* 606:    */   
/* 607:    */   public void printGraph()
/* 608:    */   {
/* 609:694 */     System.out.println("PRINTING GRAPH\n");
/* 610:695 */     System.out.println("Node Count:         " + this.nodeList.size());
/* 611:696 */     System.out.println("Edge Count:         " + this.edgeList.size());
/* 612:697 */     System.out.println("MQ Value:           " + this.mqValue);
/* 613:698 */     System.out.println("Number of Clusters: " + this.numClusters);
/* 614:699 */     System.out.println();
/* 615:701 */     for (int i = 0; i < this.nodeList.size(); i++)
/* 616:    */     {
/* 617:703 */       BunchNode bn = (BunchNode)this.nodeList.get(i);
/* 618:704 */       ArrayList fdeps = null;
/* 619:705 */       ArrayList bdeps = null;
/* 620:    */       
/* 621:707 */       System.out.println("NODE:   " + bn.getName());
/* 622:708 */       System.out.println("Cluster ID:   " + bn.getCluster());
/* 623:710 */       if (bn.getDeps() != null)
/* 624:    */       {
/* 625:712 */         fdeps = new ArrayList(bn.getDeps());
/* 626:713 */         for (int j = 0; j < fdeps.size(); j++)
/* 627:    */         {
/* 628:715 */           BunchEdge be = (BunchEdge)fdeps.get(j);
/* 629:716 */           String depName = be.getDestNode().getName();
/* 630:717 */           int weight = be.getWeight();
/* 631:718 */           System.out.println("   ===> " + depName + " (" + weight + ")");
/* 632:    */         }
/* 633:    */       }
/* 634:722 */       if (bn.getBackDeps() != null)
/* 635:    */       {
/* 636:724 */         bdeps = new ArrayList(bn.getBackDeps());
/* 637:725 */         for (int j = 0; j < bdeps.size(); j++)
/* 638:    */         {
/* 639:727 */           BunchEdge be = (BunchEdge)bdeps.get(j);
/* 640:728 */           String depName = be.getSrcNode().getName();
/* 641:729 */           int weight = be.getWeight();
/* 642:730 */           System.out.println("   <=== " + depName + " (" + weight + ")");
/* 643:    */         }
/* 644:    */       }
/* 645:733 */       System.out.println();
/* 646:    */     }
/* 647:737 */     System.out.println("Cluster Breakdown\n");
/* 648:738 */     ArrayList clusts = new ArrayList(getClusters());
/* 649:739 */     for (int i = 0; i < clusts.size(); i++)
/* 650:    */     {
/* 651:741 */       BunchCluster bc = (BunchCluster)clusts.get(i);
/* 652:742 */       System.out.println("Cluster id:   " + bc.getID());
/* 653:743 */       System.out.println("Custer name:  " + bc.getName());
/* 654:744 */       System.out.println("Cluster size: " + bc.getSize());
/* 655:    */       
/* 656:746 */       ArrayList members = new ArrayList(bc.getClusterNodes());
/* 657:747 */       for (int j = 0; j < members.size(); j++)
/* 658:    */       {
/* 659:749 */         BunchNode bn = (BunchNode)members.get(j);
/* 660:750 */         System.out.println("   --> " + bn.getName() + "   (" + bn.getCluster() + ")");
/* 661:    */       }
/* 662:752 */       System.out.println();
/* 663:    */     }
/* 664:    */   }
/* 665:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.BunchGraph
 * JD-Core Version:    0.7.0.1
 */