/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import bunch.stats.StatsManager;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Hashtable;
/*   7:    */ 
/*   8:    */ public class Cluster
/*   9:    */ {
/*  10:    */   public static final double CLUSTER_OBJ_FN_VAL_NOT_DEFINED = -999.0D;
/*  11: 45 */   int[] clusterVector = null;
/*  12: 46 */   int[] epsilonEdges = null;
/*  13: 47 */   int[] muEdges = null;
/*  14: 48 */   int[] lastMv = new int[3];
/*  15: 49 */   double objFnValue = -999.0D;
/*  16: 50 */   Graph graph = null;
/*  17: 51 */   boolean converged = false;
/*  18: 52 */   boolean validMove = false;
/*  19: 54 */   int[] clusterNames = null;
/*  20: 55 */   int[] clustersUsed = null;
/*  21: 56 */   boolean clusterNamesDirty = true;
/*  22: 57 */   int numClustNames = -1;
/*  23: 58 */   boolean clusterNamesChanged = false;
/*  24: 59 */   double baseObjFnValue = -999.0D;
/*  25: 60 */   long numMQEvaluations = 0L;
/*  26: 61 */   int baseNumClusters = 0;
/*  27: 62 */   Cluster baseCluster = null;
/*  28: 68 */   int lastMoveNode = -1;
/*  29: 69 */   int lastMoveOrigCluster = -1;
/*  30: 70 */   int lastMoveNewCluster = -1;
/*  31: 71 */   double lastMoveObjectiveFnValue = 0.0D;
/*  32: 73 */   int pushNode = -1;
/*  33: 74 */   int pushCluster = -1;
/*  34: 75 */   double pushObjectiveFnValue = 0.0D;
/*  35: 77 */   boolean isDirty = true;
/*  36: 78 */   long depth = 0L;
/*  37: 79 */   ArrayList cDetails = null;
/*  38: 82 */   ObjectiveFunctionCalculator calculator = null;
/*  39: 83 */   StatsManager stats = StatsManager.getInstance();
/*  40:    */   
/*  41:    */   public Cluster()
/*  42:    */   {
/*  43: 89 */     int tmp175_174 = (this.lastMv[2] = -1);this.lastMv[1] = tmp175_174;this.lastMv[0] = tmp175_174;
/*  44: 90 */     this.depth = 0L;
/*  45: 91 */     if (this.stats.getCollectClusteringDetails()) {
/*  46: 92 */       this.cDetails = new ArrayList();
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Cluster(Graph g, int[] cv)
/*  51:    */   {
/*  52:101 */     int tmp175_174 = (this.lastMv[2] = -1);this.lastMv[1] = tmp175_174;this.lastMv[0] = tmp175_174;
/*  53:102 */     this.graph = g;
/*  54:103 */     setClusterVector(cv);
/*  55:104 */     initCalculator();
/*  56:105 */     if (this.stats.getCollectClusteringDetails()) {
/*  57:106 */       this.cDetails = new ArrayList();
/*  58:    */     }
/*  59:108 */     this.baseObjFnValue = getObjFnValue();
/*  60:109 */     this.baseNumClusters = getNumClusters();
/*  61:110 */     this.baseCluster = cloneCluster();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Cluster getBaseCluster()
/*  65:    */   {
/*  66:115 */     return this.baseCluster;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getBaseNumClusters()
/*  70:    */   {
/*  71:119 */     return this.baseNumClusters;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public double getBaseObjFnValue()
/*  75:    */   {
/*  76:122 */     return this.baseObjFnValue;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public long getNumMQEvaluations()
/*  80:    */   {
/*  81:125 */     return this.numMQEvaluations;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public ArrayList getClusteringDetails()
/*  85:    */   {
/*  86:128 */     return this.cDetails;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public long getDepth()
/*  90:    */   {
/*  91:134 */     return this.depth;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void incrDepth()
/*  95:    */   {
/*  96:141 */     this.depth += 1L;
/*  97:143 */     if ((this.cDetails != null) && (this.stats.getCollectClusteringDetails())) {
/*  98:144 */       this.cDetails.add(new Double(this.objFnValue));
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int size()
/* 103:    */   {
/* 104:151 */     return this.clusterVector.length;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int getCluster(int node)
/* 108:    */   {
/* 109:159 */     return this.clusterVector[node];
/* 110:    */   }
/* 111:    */   
/* 112:    */   private void invalidateLastMove()
/* 113:    */   {
/* 114:168 */     this.lastMoveNode = -1;
/* 115:169 */     this.lastMoveOrigCluster = -1;
/* 116:170 */     this.lastMoveNewCluster = -1;
/* 117:171 */     this.epsilonEdges = null;
/* 118:172 */     this.muEdges = null;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void allocEdgeCounters()
/* 122:    */   {
/* 123:181 */     if (this.clusterVector == null) {
/* 124:182 */       return;
/* 125:    */     }
/* 126:184 */     this.epsilonEdges = new int[this.clusterVector.length];
/* 127:185 */     this.muEdges = new int[this.clusterVector.length];
/* 128:187 */     for (int i = 0; i < this.clusterVector.length; i++)
/* 129:    */     {
/* 130:188 */       int tmp52_51 = 0;this.muEdges[i] = tmp52_51;this.epsilonEdges[i] = tmp52_51;
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public int[] getEpsilonEdgeVector()
/* 135:    */   {
/* 136:197 */     return this.epsilonEdges;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setEpsillonEdgeVector(int[] ev)
/* 140:    */   {
/* 141:206 */     this.epsilonEdges = ev;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int[] getMuEdgeVector()
/* 145:    */   {
/* 146:215 */     return this.muEdges;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setMuEdgeVector(int[] ev)
/* 150:    */   {
/* 151:224 */     this.muEdges = ev;
/* 152:    */   }
/* 153:    */   
/* 154:    */   private boolean initCalculator()
/* 155:    */   {
/* 156:232 */     if (this.graph == null) {
/* 157:233 */       return false;
/* 158:    */     }
/* 159:238 */     this.calculator = Graph.objectiveFunctionCalculatorFactory_sd.getSelectedCalculator();
/* 160:239 */     if (this.calculator == null) {
/* 161:240 */       return false;
/* 162:    */     }
/* 163:242 */     this.calculator.init(this.graph);
/* 164:243 */     return true;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public ObjectiveFunctionCalculator getCalculator()
/* 168:    */   {
/* 169:253 */     return this.calculator;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public boolean isDirty()
/* 173:    */   {
/* 174:263 */     return this.isDirty;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setClusterVector(int[] cv)
/* 178:    */   {
/* 179:276 */     invalidateLastMove();
/* 180:277 */     this.isDirty = true;
/* 181:278 */     this.clusterVector = new int[cv.length];
/* 182:279 */     System.arraycopy(cv, 0, this.clusterVector, 0, cv.length);
/* 183:    */     
/* 184:281 */     this.epsilonEdges = null;
/* 185:282 */     this.muEdges = null;
/* 186:283 */     this.lastMv = new int[3];
/* 187:284 */     this.converged = false;
/* 188:285 */     this.validMove = false;
/* 189:    */     
/* 190:287 */     this.lastMoveNode = -1;
/* 191:288 */     this.lastMoveOrigCluster = -1;
/* 192:289 */     this.lastMoveNewCluster = -1;
/* 193:290 */     this.lastMoveObjectiveFnValue = 0.0D;
/* 194:291 */     this.numClustNames = -1;
/* 195:    */     
/* 196:293 */     this.pushNode = -1;
/* 197:294 */     this.pushCluster = -1;
/* 198:295 */     this.pushObjectiveFnValue = 0.0D;
/* 199:    */     
/* 200:297 */     this.isDirty = true;
/* 201:299 */     if (this.graph != null) {
/* 202:300 */       calcObjFn();
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setObjFnValue(double o)
/* 207:    */   {
/* 208:308 */     this.objFnValue = o;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public double getObjFnValue()
/* 212:    */   {
/* 213:316 */     return this.objFnValue;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public final int[] getClusterVector()
/* 217:    */   {
/* 218:324 */     return this.clusterVector;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void force()
/* 222:    */   {
/* 223:333 */     this.isDirty = true;
/* 224:334 */     this.validMove = false;
/* 225:335 */     calcObjFn();
/* 226:    */   }
/* 227:    */   
/* 228:    */   public double calcObjFn()
/* 229:    */   {
/* 230:346 */     this.stats.incrMQCalculations();
/* 231:347 */     this.numMQEvaluations += 1L;
/* 232:349 */     if (this.graph == null) {
/* 233:350 */       return -999.0D;
/* 234:    */     }
/* 235:353 */     if (!this.isDirty) {
/* 236:354 */       return this.objFnValue;
/* 237:    */     }
/* 238:356 */     if (this.calculator == null) {
/* 239:357 */       initCalculator();
/* 240:    */     }
/* 241:359 */     if (!this.validMove) {
/* 242:360 */       invalidateLastMove();
/* 243:    */     }
/* 244:362 */     int[] cltemp = this.graph.getClusters();
/* 245:    */     
/* 246:364 */     double objfn = this.calculator.calculate(this);
/* 247:    */     
/* 248:366 */     setObjFnValue(objfn);
/* 249:    */     
/* 250:368 */     this.isDirty = false;
/* 251:    */     
/* 252:370 */     return objfn;
/* 253:    */   }
/* 254:    */   
/* 255:    */   private void copy(int[] cv)
/* 256:    */   {
/* 257:381 */     setClusterVector(cv);
/* 258:    */   }
/* 259:    */   
/* 260:    */   public boolean isMaximum()
/* 261:    */   {
/* 262:390 */     return this.converged;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setConverged(boolean state)
/* 266:    */   {
/* 267:399 */     this.converged = state;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public Graph getGraph()
/* 271:    */   {
/* 272:409 */     return this.graph;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public boolean isEqualTo(Cluster c)
/* 276:    */   {
/* 277:421 */     double otherMQ = c.getObjFnValue();
/* 278:    */     
/* 279:423 */     int iMQ = (int)(this.objFnValue * 10000.0D);
/* 280:424 */     int iMQ2 = (int)(otherMQ * 10000.0D);
/* 281:    */     
/* 282:426 */     return iMQ == iMQ2;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public boolean isGreaterThan(Cluster c)
/* 286:    */   {
/* 287:439 */     double otherMQ = c.getObjFnValue();
/* 288:    */     
/* 289:441 */     int iMQ = (int)(this.objFnValue * 10000.0D);
/* 290:442 */     int iMQ2 = (int)(otherMQ * 10000.0D);
/* 291:    */     
/* 292:444 */     return iMQ > iMQ2;
/* 293:    */   }
/* 294:    */   
/* 295:    */   private void setFromCluster(Cluster c)
/* 296:    */   {
/* 297:456 */     if (c.getClusterVector() == null)
/* 298:    */     {
/* 299:457 */       this.clusterVector = null;
/* 300:    */     }
/* 301:    */     else
/* 302:    */     {
/* 303:460 */       this.clusterVector = new int[c.getClusterVector().length];
/* 304:461 */       System.arraycopy(c.getClusterVector(), 0, this.clusterVector, 0, this.clusterVector.length);
/* 305:    */     }
/* 306:464 */     this.objFnValue = c.getObjFnValue();
/* 307:465 */     this.graph = c.getGraph();
/* 308:466 */     this.converged = c.isMaximum();
/* 309:467 */     this.calculator = c.getCalculator();
/* 310:468 */     this.isDirty = c.isDirty;
/* 311:469 */     this.depth = c.depth;
/* 312:470 */     this.baseObjFnValue = c.baseObjFnValue;
/* 313:471 */     this.numMQEvaluations = c.numMQEvaluations;
/* 314:472 */     this.baseNumClusters = c.baseNumClusters;
/* 315:473 */     this.baseCluster = c.baseCluster;
/* 316:475 */     if (c.cDetails != null) {
/* 317:476 */       this.cDetails = ((ArrayList)c.cDetails.clone());
/* 318:    */     }
/* 319:478 */     this.converged = c.converged;
/* 320:479 */     this.validMove = c.validMove;
/* 321:481 */     if ((c.epsilonEdges == null) || (c.muEdges == null))
/* 322:    */     {
/* 323:483 */       this.epsilonEdges = (this.muEdges = null);
/* 324:    */     }
/* 325:    */     else
/* 326:    */     {
/* 327:487 */       this.epsilonEdges = new int[c.epsilonEdges.length];
/* 328:488 */       this.muEdges = new int[c.muEdges.length];
/* 329:489 */       System.arraycopy(c.epsilonEdges, 0, this.epsilonEdges, 0, this.epsilonEdges.length);
/* 330:490 */       System.arraycopy(c.muEdges, 0, this.muEdges, 0, this.muEdges.length);
/* 331:    */     }
/* 332:493 */     this.lastMv = new int[3];
/* 333:494 */     System.arraycopy(c.lastMv, 0, this.lastMv, 0, this.lastMv.length);
/* 334:    */     
/* 335:496 */     this.lastMoveNode = c.lastMoveNode;
/* 336:497 */     this.lastMoveOrigCluster = c.lastMoveOrigCluster;
/* 337:498 */     this.lastMoveNewCluster = c.lastMoveNewCluster;
/* 338:499 */     this.lastMoveObjectiveFnValue = c.lastMoveObjectiveFnValue;
/* 339:    */     
/* 340:501 */     this.pushNode = c.pushNode;
/* 341:502 */     this.pushCluster = c.pushCluster;
/* 342:503 */     this.pushObjectiveFnValue = c.pushObjectiveFnValue;
/* 343:    */     
/* 344:505 */     this.numClustNames = c.numClustNames;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public Cluster cloneCluster()
/* 348:    */   {
/* 349:516 */     Cluster c = new Cluster();
/* 350:517 */     c.setFromCluster(this);
/* 351:518 */     return c;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void copyFromCluster(Cluster c)
/* 355:    */   {
/* 356:530 */     setFromCluster(c);
/* 357:    */   }
/* 358:    */   
/* 359:    */   public int getNumClusters()
/* 360:    */   {
/* 361:539 */     if (this.numClustNames == -1) {
/* 362:539 */       return 0;
/* 363:    */     }
/* 364:540 */     return this.numClustNames;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public boolean[] getLocks()
/* 368:    */   {
/* 369:554 */     return this.graph.getLocks();
/* 370:    */   }
/* 371:    */   
/* 372:    */   public int findNewClusterID()
/* 373:    */   {
/* 374:565 */     int[] clusterNames = getClusterNames();
/* 375:566 */     int[] tmpVector = new int[this.clusterVector.length];
/* 376:567 */     for (int i = 0; i < this.clusterVector.length; i++) {
/* 377:568 */       tmpVector[i] = i;
/* 378:    */     }
/* 379:570 */     for (int i = 0; i < clusterNames.length; i++) {
/* 380:571 */       tmpVector[clusterNames[i]] = -1;
/* 381:    */     }
/* 382:573 */     int newClusterID = -1;
/* 383:574 */     for (int i = 0; i < this.clusterVector.length; i++) {
/* 384:576 */       if (tmpVector[i] != -1)
/* 385:    */       {
/* 386:577 */         newClusterID = i;
/* 387:578 */         break;
/* 388:    */       }
/* 389:    */     }
/* 390:581 */     return newClusterID;
/* 391:    */   }
/* 392:    */   
/* 393:    */   public int allocateNewCluster()
/* 394:    */   {
/* 395:592 */     int newClusterID = findNewClusterID();
/* 396:593 */     this.clusterNamesChanged = true;
/* 397:594 */     return newClusterID;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public boolean moveNodeToNewCluster(int nodeID, int newClusterID)
/* 401:    */   {
/* 402:606 */     this.clusterNamesChanged = true;
/* 403:607 */     return relocate(nodeID, newClusterID);
/* 404:    */   }
/* 405:    */   
/* 406:    */   public boolean removeNewCluster(int newClusterID)
/* 407:    */   {
/* 408:617 */     this.clusterNamesChanged = true;
/* 409:618 */     return true;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public boolean hasClusterNamesChanged()
/* 413:    */   {
/* 414:628 */     return this.clusterNamesChanged;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public int[] getClusterNames()
/* 418:    */   {
/* 419:639 */     Hashtable usedClusts = new Hashtable();
/* 420:    */     
/* 421:641 */     int[] clusts = new int[this.clusterVector.length];
/* 422:    */     
/* 423:643 */     int numClusts = 0;
/* 424:644 */     boolean hasDoubleLocks = this.graph.hasDoubleLocks();
/* 425:645 */     boolean[] locks = this.graph.getLocks();
/* 426:650 */     for (int i = 0; i < this.clusterVector.length; i++) {
/* 427:651 */       if ((!hasDoubleLocks) || 
/* 428:652 */         (locks[i] == false))
/* 429:    */       {
/* 430:654 */         int name = this.clusterVector[i];
/* 431:655 */         Integer iNm = new Integer(name);
/* 432:657 */         if (!usedClusts.containsKey(iNm))
/* 433:    */         {
/* 434:659 */           clusts[numClusts] = name;
/* 435:660 */           numClusts++;
/* 436:661 */           usedClusts.put(iNm, iNm);
/* 437:    */         }
/* 438:    */       }
/* 439:    */     }
/* 440:664 */     int[] tmp = new int[numClusts];
/* 441:665 */     System.arraycopy(clusts, 0, tmp, 0, numClusts);
/* 442:    */     
/* 443:667 */     this.numClustNames = numClusts;
/* 444:668 */     this.clusterNames = tmp;
/* 445:    */     
/* 446:670 */     this.clusterNamesChanged = false;
/* 447:671 */     return tmp;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public double getLastMvObjFn()
/* 451:    */   {
/* 452:683 */     return this.lastMoveObjectiveFnValue;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public int[] getLmEncoding()
/* 456:    */   {
/* 457:700 */     this.lastMv[0] = this.lastMoveNode;
/* 458:701 */     this.lastMv[1] = this.lastMoveOrigCluster;
/* 459:702 */     this.lastMv[2] = this.lastMoveNewCluster;
/* 460:703 */     return this.lastMv;
/* 461:    */   }
/* 462:    */   
/* 463:    */   public boolean relocate(int node, int cluster)
/* 464:    */   {
/* 465:717 */     int currentCluster = this.clusterVector[node];
/* 466:718 */     if (currentCluster != cluster) {
/* 467:719 */       return move(node, currentCluster, cluster);
/* 468:    */     }
/* 469:721 */     return true;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public boolean move(int node, int origCluster, int newCluster)
/* 473:    */   {
/* 474:738 */     if (this.clusterVector[node] != origCluster)
/* 475:    */     {
/* 476:740 */       System.out.println("This is bad");
/* 477:741 */       return false;
/* 478:    */     }
/* 479:745 */     this.lastMoveNode = node;
/* 480:746 */     this.lastMoveOrigCluster = origCluster;
/* 481:747 */     this.lastMoveNewCluster = newCluster;
/* 482:748 */     this.lastMoveObjectiveFnValue = getObjFnValue();
/* 483:    */     
/* 484:    */ 
/* 485:751 */     this.clusterVector[node] = newCluster;
/* 486:    */     
/* 487:753 */     this.isDirty = true;
/* 488:754 */     this.validMove = true;
/* 489:755 */     calcObjFn();
/* 490:756 */     this.validMove = false;
/* 491:757 */     this.isDirty = false;
/* 492:    */     
/* 493:759 */     return true;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public boolean isMoveValid()
/* 497:    */   {
/* 498:766 */     return this.validMove;
/* 499:    */   }
/* 500:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.Cluster
 * JD-Core Version:    0.7.0.1
 */