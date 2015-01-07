/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.io.BufferedWriter;
/*   4:    */ import java.io.FileWriter;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Hashtable;
/*   8:    */ import java.util.LinkedList;
/*   9:    */ import java.util.Stack;
/*  10:    */ import java.util.Vector;
/*  11:    */ 
/*  12:    */ public class DotGraphOutput
/*  13:    */   extends GraphOutput
/*  14:    */ {
/*  15: 57 */   int clusterIDTmp = 0;
/*  16:    */   
/*  17:    */   public void writeHeader()
/*  18:    */     throws IOException
/*  19:    */   {
/*  20: 70 */     this.writer_d.write("/* ------------------------------------------------------------ */\n");
/*  21: 71 */     this.writer_d.write("/* created with bunch v3 */\n");
/*  22: 72 */     this.writer_d.write("/* Objective Function value = " + this.graph_d.getObjectiveFunctionValue() + "*/\n");
/*  23: 73 */     this.writer_d.write("/* ------------------------------------------------------------ */\n\n");
/*  24: 74 */     this.writer_d.write("digraph G {\n");
/*  25: 75 */     this.writer_d.write("size= \"10,10\";\n");
/*  26: 76 */     this.writer_d.write("rotate = 90;\n");
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void writeSpecialModules(Node[] originalNodes)
/*  30:    */     throws IOException
/*  31:    */   {
/*  32: 84 */     ArrayList deadList = new ArrayList();
/*  33: 85 */     deadList.clear();
/*  34: 87 */     if (originalNodes != null)
/*  35:    */     {
/*  36: 89 */       boolean hasSuppliers = false;
/*  37: 90 */       boolean hasClients = false;
/*  38: 91 */       boolean hasCentrals = false;
/*  39: 92 */       boolean hasLibraries = false;
/*  40: 98 */       for (int i = 0; i < originalNodes.length; i++)
/*  41:    */       {
/*  42:100 */         if ((!hasSuppliers) && (originalNodes[i].getType() == 2)) {
/*  43:101 */           hasSuppliers = true;
/*  44:    */         }
/*  45:103 */         if ((!hasClients) && (originalNodes[i].getType() == 1)) {
/*  46:104 */           hasClients = true;
/*  47:    */         }
/*  48:106 */         if ((!hasCentrals) && (originalNodes[i].getType() == 3)) {
/*  49:107 */           hasCentrals = true;
/*  50:    */         }
/*  51:109 */         if ((!hasLibraries) && (originalNodes[i].getType() == 4)) {
/*  52:110 */           hasLibraries = true;
/*  53:    */         }
/*  54:    */       }
/*  55:114 */       if (hasLibraries)
/*  56:    */       {
/*  57:117 */         this.writer_d.write("subgraph cluster_libraries {\n");
/*  58:118 */         this.writer_d.write("label = \"libraries\";\n");
/*  59:119 */         this.writer_d.write("color = black;\n");
/*  60:120 */         this.writer_d.write("style = bold;\n\n");
/*  61:121 */         for (int i = 0; i < originalNodes.length; i++)
/*  62:    */         {
/*  63:123 */           if (originalNodes[i].getType() == 4) {
/*  64:124 */             this.writer_d.write("\"" + originalNodes[i].getName() + "\"[shape=diamond,color=lightgray,fontcolor=black,style=filled];\n");
/*  65:    */           }
/*  66:126 */           if ((originalNodes[i].getType() >= 128) && 
/*  67:127 */             (originalNodes[i].getType() - 128 == 4))
/*  68:    */           {
/*  69:129 */             this.writer_d.write("\"" + originalNodes[i].getName() + "\"[label=\"" + originalNodes[i].getName() + "\",shape=box,color=lightblue,fontcolor=black,style=filled];\n");
/*  70:130 */             deadList.add(originalNodes[i]);
/*  71:    */           }
/*  72:    */         }
/*  73:133 */         this.writer_d.write("}\n");
/*  74:    */       }
/*  75:136 */       if (hasSuppliers)
/*  76:    */       {
/*  77:139 */         this.writer_d.write("subgraph cluster_omnipresent_suppliers {\n");
/*  78:140 */         this.writer_d.write("label = \"omnipresent suppliers\";\n");
/*  79:141 */         this.writer_d.write("color = black;\n");
/*  80:142 */         this.writer_d.write("style = bold;\n\n");
/*  81:143 */         for (int i = 0; i < originalNodes.length; i++)
/*  82:    */         {
/*  83:145 */           if (originalNodes[i].getType() == 2) {
/*  84:146 */             this.writer_d.write("\"" + originalNodes[i].getName() + "\"[shape=diamond,color=lightgray,fontcolor=black,style=filled];\n");
/*  85:    */           }
/*  86:148 */           if ((originalNodes[i].getType() >= 128) && 
/*  87:149 */             (originalNodes[i].getType() - 128 == 2))
/*  88:    */           {
/*  89:151 */             this.writer_d.write("\"" + originalNodes[i].getName() + "\"[label=\"" + originalNodes[i].getName() + "\",shape=box,color=lightblue,fontcolor=black,style=filled];\n");
/*  90:152 */             deadList.add(originalNodes[i]);
/*  91:    */           }
/*  92:    */         }
/*  93:155 */         this.writer_d.write("}\n");
/*  94:    */       }
/*  95:158 */       if (hasClients)
/*  96:    */       {
/*  97:161 */         this.writer_d.write("subgraph cluster_omnipresent_clients {\n");
/*  98:162 */         this.writer_d.write("label = \"omnipresent clients\";\n");
/*  99:163 */         this.writer_d.write("color = black;\n");
/* 100:164 */         this.writer_d.write("style = bold;\n\n");
/* 101:165 */         for (int i = 0; i < originalNodes.length; i++)
/* 102:    */         {
/* 103:167 */           if (originalNodes[i].getType() == 1) {
/* 104:168 */             this.writer_d.write("\"" + originalNodes[i].getName() + "\"[shape=diamond,color=lightgray,fontcolor=black,style=filled];\n");
/* 105:    */           }
/* 106:170 */           if ((originalNodes[i].getType() >= 128) && 
/* 107:171 */             (originalNodes[i].getType() - 128 == 1))
/* 108:    */           {
/* 109:173 */             this.writer_d.write("\"" + originalNodes[i].getName() + "\"[label=\"" + originalNodes[i].getName() + "\",shape=box,color=lightblue,fontcolor=black,style=filled];\n");
/* 110:174 */             deadList.add(originalNodes[i]);
/* 111:    */           }
/* 112:    */         }
/* 113:177 */         this.writer_d.write("}\n");
/* 114:    */       }
/* 115:180 */       if (hasCentrals)
/* 116:    */       {
/* 117:183 */         this.writer_d.write("subgraph cluster_omnipresent_centrals {\n");
/* 118:184 */         this.writer_d.write("label = \"omnipresent clients/suppliers\";\n");
/* 119:185 */         this.writer_d.write("color = black;\n");
/* 120:186 */         this.writer_d.write("style = bold;\n\n");
/* 121:187 */         for (int i = 0; i < originalNodes.length; i++)
/* 122:    */         {
/* 123:189 */           if (originalNodes[i].getType() == 3) {
/* 124:190 */             this.writer_d.write("\"" + originalNodes[i].getName() + "\"[shape=diamond,color=lightgray,fontcolor=black,style=filled];\n");
/* 125:    */           }
/* 126:192 */           if ((originalNodes[i].getType() >= 128) && 
/* 127:193 */             (originalNodes[i].getType() - 128 == 3))
/* 128:    */           {
/* 129:195 */             this.writer_d.write("\"" + originalNodes[i].getName() + "\"[label=\"" + originalNodes[i].getName() + "\",shape=box,color=lightblue,fontcolor=black,style=filled];\n");
/* 130:196 */             deadList.add(originalNodes[i]);
/* 131:    */           }
/* 132:    */         }
/* 133:199 */         this.writer_d.write("}\n");
/* 134:    */       }
/* 135:    */     }
/* 136:203 */     if (deadList.size() > 0)
/* 137:    */     {
/* 138:205 */       this.writer_d.write("\n");
/* 139:206 */       for (int z = 0; z < deadList.size(); z++)
/* 140:    */       {
/* 141:208 */         Node tmpN = (Node)deadList.get(z);
/* 142:209 */         writeEdges(tmpN, originalNodes);
/* 143:    */       }
/* 144:211 */       this.writer_d.write("\n");
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void writeEdges(Node n, Node[] origList)
/* 149:    */     throws IOException
/* 150:    */   {
/* 151:223 */     String srcName = n.getName();
/* 152:224 */     int[] deps = n.getDependencies();
/* 153:226 */     for (int i = 0; i < deps.length; i++)
/* 154:    */     {
/* 155:228 */       String tgtName = origList[deps[i]].getName();
/* 156:229 */       this.writer_d.write("\"" + srcName + "\" -> \"" + tgtName + "\" [color=blue,font=6];\n");
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void writeClosing()
/* 161:    */     throws IOException
/* 162:    */   {
/* 163:240 */     this.writer_d.write("}\n");
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void genCluster(Node n, long baseID)
/* 167:    */     throws IOException
/* 168:    */   {
/* 169:250 */     Stack st = new Stack();
/* 170:251 */     Hashtable ht = new Hashtable();
/* 171:    */     
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:256 */     st.push(n);
/* 176:262 */     while (!st.empty())
/* 177:    */     {
/* 178:264 */       Node tmp = (Node)st.peek();
/* 179:265 */       if (tmp.isCluster())
/* 180:    */       {
/* 181:267 */         String strongestNode = findStrongestNode(tmp);
/* 182:268 */         String hkey = "SS_" + tmp.name_d;
/* 183:273 */         if (ht.containsKey(tmp.getUniqueID()))
/* 184:    */         {
/* 185:275 */           this.writer_d.write("}\n\n");
/* 186:276 */           ht.remove(tmp.getUniqueID());
/* 187:277 */           st.pop();
/* 188:    */         }
/* 189:    */         else
/* 190:    */         {
/* 191:284 */           ht.put(tmp.getUniqueID(), tmp.getUniqueID());
/* 192:    */           
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:289 */           String cName = "(SS-L" + tmp.nodeLevel + "):" + strongestNode;
/* 197:290 */           long clustID = tmp.nodeID + baseID++;
/* 198:    */           
/* 199:292 */           this.writer_d.write("subgraph cluster" + clustID + " {\n");
/* 200:293 */           this.writer_d.write("label = \"" + cName + "\";\n");
/* 201:294 */           this.writer_d.write("color = black;\n");
/* 202:295 */           this.writer_d.write("style = bold;\n\n");
/* 203:300 */           for (int j = 0; j < tmp.children.length; j++) {
/* 204:301 */             st.push(tmp.children[j]);
/* 205:    */           }
/* 206:    */         }
/* 207:    */       }
/* 208:    */       else
/* 209:    */       {
/* 210:310 */         this.writer_d.write("\"" + tmp.getName() + "\"[label=\"" + tmp.getName() + "\",shape=ellipse,color=lightblue,fontcolor=black,style=filled];\n");
/* 211:311 */         st.pop();
/* 212:    */       }
/* 213:    */     }
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void generateClusters(Graph cLvlG)
/* 217:    */     throws IOException
/* 218:    */   {
/* 219:322 */     Graph nextLvlG = null;
/* 220:329 */     if ((cLvlG.getClusterNames().length <= 1) && (cLvlG.getPreviousLevelGraph() != null)) {
/* 221:330 */       cLvlG = cLvlG.getPreviousLevelGraph();
/* 222:    */     }
/* 223:337 */     NextLevelGraph nextLvl = new NextLevelGraph();
/* 224:338 */     nextLvlG = nextLvl.genNextLevelGraph(cLvlG);
/* 225:    */     
/* 226:    */ 
/* 227:341 */     Node[] nodeList = nextLvlG.getNodes();
/* 228:342 */     int Lvl = cLvlG.getGraphLevel();
/* 229:    */     
/* 230:344 */     long base = 1000L;
/* 231:349 */     for (int i = 0; i < nodeList.length; i++)
/* 232:    */     {
/* 233:355 */       Node tmp = nodeList[i];
/* 234:356 */       if (tmp.children != null) {
/* 235:359 */         if (tmp.children.length != 0)
/* 236:    */         {
/* 237:366 */           findStrongestNode(tmp);
/* 238:    */           
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:    */ 
/* 243:372 */           genCluster(nodeList[i], base);
/* 244:373 */           base += 1000L;
/* 245:    */         }
/* 246:    */       }
/* 247:    */     }
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void genOneLevel(Graph cLvlG)
/* 251:    */     throws IOException
/* 252:    */   {
/* 253:383 */     NextLevelGraph nextLvl = new NextLevelGraph();
/* 254:384 */     Graph nextLvlG = nextLvl.genNextLevelGraph(cLvlG);
/* 255:385 */     long baseID = 100000 * cLvlG.getGraphLevel();
/* 256:    */     
/* 257:    */ 
/* 258:    */ 
/* 259:    */ 
/* 260:390 */     Node[] nodeList = nextLvlG.getNodes();
/* 261:391 */     for (int i = 0; i < nodeList.length; i++)
/* 262:    */     {
/* 263:397 */       Node tmp = nodeList[i];
/* 264:398 */       if (tmp.children != null)
/* 265:    */       {
/* 266:402 */         long clustID = tmp.nodeID + baseID++;
/* 267:403 */         this.writer_d.write("subgraph cluster" + clustID + " {\n");
/* 268:404 */         this.writer_d.write("label = \"" + tmp.name_d + "\";\n");
/* 269:405 */         this.writer_d.write("color = black;\n");
/* 270:406 */         this.writer_d.write("style = bold;\n\n");
/* 271:409 */         for (int j = 0; j < tmp.children.length; j++) {
/* 272:410 */           this.writer_d.write("\"" + tmp.children[j].getName() + "\"[label=\"" + tmp.children[j].getName() + "\",shape=ellipse,color=lightblue,fontcolor=black,style=filled];\n");
/* 273:    */         }
/* 274:413 */         this.writer_d.write("}\n\n");
/* 275:    */       }
/* 276:    */     }
/* 277:419 */     nodeList = cLvlG.getNodes();
/* 278:420 */     for (int i = 0; i < nodeList.length; i++)
/* 279:    */     {
/* 280:422 */       Node tmp = nodeList[i];
/* 281:423 */       if (tmp.children != null)
/* 282:    */       {
/* 283:426 */         String srcName = tmp.getName();
/* 284:427 */         int[] dep = tmp.getDependencies();
/* 285:428 */         if (dep != null) {
/* 286:430 */           for (int j = 0; j < dep.length; j++)
/* 287:    */           {
/* 288:432 */             String tgtName = nodeList[dep[j]].getName();
/* 289:433 */             this.writer_d.write("\"" + srcName + "\" -> \"" + tgtName + "\" [color=blue,font=6];\n");
/* 290:    */           }
/* 291:    */         }
/* 292:    */       }
/* 293:    */     }
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void genChildrenFromOneLevel(Graph cLvlG)
/* 297:    */     throws IOException
/* 298:    */   {
/* 299:446 */     Graph nextLvlG = null;
/* 300:453 */     if ((cLvlG.getClusterNames().length <= 1) && (cLvlG.getPreviousLevelGraph() != null)) {
/* 301:455 */       cLvlG = cLvlG.getPreviousLevelGraph();
/* 302:    */     }
/* 303:463 */     NextLevelGraph nextLvl = new NextLevelGraph();
/* 304:464 */     nextLvlG = nextLvl.genNextLevelGraph(cLvlG);
/* 305:    */     
/* 306:466 */     Node[] nodeList = nextLvlG.getNodes();
/* 307:467 */     Vector cVect = new Vector();
/* 308:468 */     int Lvl = cLvlG.getGraphLevel();
/* 309:    */     
/* 310:470 */     cVect.removeAllElements();
/* 311:475 */     for (int i = 0; i < nodeList.length; i++)
/* 312:    */     {
/* 313:477 */       Node tmp = nodeList[i];
/* 314:483 */       if (tmp.children != null) {
/* 315:486 */         if (tmp.children.length != 0)
/* 316:    */         {
/* 317:490 */           findStrongestNode(tmp);
/* 318:    */           
/* 319:    */ 
/* 320:493 */           Vector newCluster = new Vector();
/* 321:494 */           newCluster.removeAllElements();
/* 322:495 */           cVect.addElement(newCluster);
/* 323:    */           
/* 324:    */ 
/* 325:498 */           echoNestedChildren(tmp, newCluster);
/* 326:    */         }
/* 327:    */       }
/* 328:    */     }
/* 329:505 */     WriteOutputClusters(cVect, Lvl);
/* 330:    */   }
/* 331:    */   
/* 332:    */   public String findStrongestNode(Node n)
/* 333:    */   {
/* 334:519 */     if (!n.isCluster()) {
/* 335:520 */       return "";
/* 336:    */     }
/* 337:522 */     int lvl = n.nodeLevel;
/* 338:523 */     boolean lvlIncr = false;
/* 339:    */     
/* 340:525 */     Vector nodeV = new Vector();
/* 341:    */     
/* 342:    */ 
/* 343:    */ 
/* 344:    */ 
/* 345:    */ 
/* 346:531 */     LinkedList l = new LinkedList();
/* 347:532 */     l.clear();
/* 348:533 */     nodeV.clear();
/* 349:    */     
/* 350:    */ 
/* 351:536 */     l.addLast(n);
/* 352:542 */     while (!l.isEmpty())
/* 353:    */     {
/* 354:544 */       Node curr = (Node)l.removeFirst();
/* 355:551 */       if (curr.isCluster())
/* 356:    */       {
/* 357:553 */         Node[] children = curr.children;
/* 358:554 */         if ((children != null) && (children.length > 0)) {
/* 359:555 */           for (int j = 0; j < children.length; j++) {
/* 360:556 */             l.addLast(children[j]);
/* 361:    */           }
/* 362:    */         }
/* 363:    */       }
/* 364:    */       else
/* 365:    */       {
/* 366:563 */         nodeV.add(curr);
/* 367:    */       }
/* 368:    */     }
/* 369:571 */     String ssName = findStrongestNode(nodeV);
/* 370:    */     
/* 371:    */ 
/* 372:    */ 
/* 373:    */ 
/* 374:576 */     n.setName(ssName);
/* 375:577 */     return ssName;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public String findStrongestNode(Vector v)
/* 379:    */   {
/* 380:588 */     int maxEdgeWeight = 0;
/* 381:589 */     int maxEdgeCount = 0;
/* 382:590 */     Node domEdgeNode = null;
/* 383:591 */     Node domWeightNode = null;
/* 384:596 */     if (v == null) {
/* 385:596 */       return "EmptyCluster";
/* 386:    */     }
/* 387:601 */     for (int i = 0; i < v.size(); i++)
/* 388:    */     {
/* 389:603 */       Node n = (Node)v.elementAt(i);
/* 390:604 */       String name = n.getName();
/* 391:605 */       int edgeWeights = 0;
/* 392:606 */       int depCount = 0;
/* 393:607 */       int beCount = 0;
/* 394:613 */       if (n.dependencies != null) {
/* 395:614 */         depCount = n.dependencies.length;
/* 396:    */       }
/* 397:616 */       if (n.backEdges != null) {
/* 398:617 */         beCount = n.backEdges.length;
/* 399:    */       }
/* 400:619 */       int edgeCount = depCount + beCount;
/* 401:626 */       if (edgeCount >= maxEdgeCount)
/* 402:    */       {
/* 403:628 */         maxEdgeCount = edgeCount;
/* 404:629 */         domEdgeNode = n;
/* 405:    */       }
/* 406:632 */       if (n.weights != null) {
/* 407:633 */         for (int j = 0; j < n.weights.length; j++) {
/* 408:634 */           edgeWeights += n.weights[j];
/* 409:    */         }
/* 410:    */       }
/* 411:636 */       if (n.beWeights != null) {
/* 412:637 */         for (int j = 0; j < n.beWeights.length; j++) {
/* 413:638 */           edgeWeights += n.beWeights[j];
/* 414:    */         }
/* 415:    */       }
/* 416:640 */       if (edgeWeights >= maxEdgeWeight)
/* 417:    */       {
/* 418:642 */         maxEdgeWeight = edgeWeights;
/* 419:643 */         domWeightNode = n;
/* 420:    */       }
/* 421:    */     }
/* 422:651 */     return domEdgeNode.getName();
/* 423:    */   }
/* 424:    */   
/* 425:    */   public void echoNestedChildren(Node n, Vector v)
/* 426:    */     throws IOException
/* 427:    */   {
/* 428:663 */     Stack s = new Stack();
/* 429:664 */     boolean firstNode = true;
/* 430:    */     
/* 431:    */ 
/* 432:    */ 
/* 433:    */ 
/* 434:669 */     s.push(n);
/* 435:670 */     while (!s.isEmpty())
/* 436:    */     {
/* 437:672 */       Node tmpNode = (Node)s.pop();
/* 438:678 */       if (tmpNode.children != null) {
/* 439:685 */         for (int i = 0; i < tmpNode.children.length; i++)
/* 440:    */         {
/* 441:687 */           Node childNode = tmpNode.children[i];
/* 442:688 */           if (childNode.isCluster()) {
/* 443:689 */             s.push(childNode);
/* 444:    */           } else {
/* 445:692 */             v.addElement(childNode);
/* 446:    */           }
/* 447:    */         }
/* 448:    */       }
/* 449:    */     }
/* 450:    */   }
/* 451:    */   
/* 452:    */   public void WriteOutputClusters(Vector cVect, int lvl)
/* 453:    */     throws IOException
/* 454:    */   {
/* 455:704 */     if (cVect == null) {
/* 456:704 */       return;
/* 457:    */     }
/* 458:709 */     for (int i = 0; i < cVect.size(); i++)
/* 459:    */     {
/* 460:714 */       Vector cluster = (Vector)cVect.elementAt(i);
/* 461:715 */       String cName = findStrongestNode(cluster);
/* 462:716 */       cName = "(SS-L" + lvl + "):" + cName;
/* 463:    */       
/* 464:    */ 
/* 465:    */ 
/* 466:    */ 
/* 467:721 */       long clustID = this.baseID++;
/* 468:722 */       this.writer_d.write("subgraph cluster" + clustID + " {\n");
/* 469:723 */       this.writer_d.write("label = \"" + cName + "\";\n");
/* 470:724 */       this.writer_d.write("color = black;\n");
/* 471:725 */       this.writer_d.write("style = bold;\n\n");
/* 472:730 */       for (int j = 0; j < cluster.size(); j++)
/* 473:    */       {
/* 474:732 */         Node n = (Node)cluster.elementAt(j);
/* 475:733 */         this.writer_d.write("\"" + n.getName() + "\"[label=\"" + n.getName() + "\",shape=ellipse,color=lightblue,fontcolor=black,style=filled];\n");
/* 476:    */       }
/* 477:737 */       this.writer_d.write("}\n");
/* 478:    */     }
/* 479:    */   }
/* 480:    */   
/* 481:    */   public void write()
/* 482:    */   {
/* 483:747 */     Graph gWriteGraph = this.graph_d;
/* 484:    */     
/* 485:    */ 
/* 486:    */ 
/* 487:    */ 
/* 488:    */ 
/* 489:753 */     int technique = getOutputTechnique();
/* 490:754 */     String fileName = getCurrentName();
/* 491:759 */     switch (technique)
/* 492:    */     {
/* 493:    */     case 3: 
/* 494:764 */       Graph gLvl = this.graph_d;
/* 495:769 */       while (gLvl.getGraphLevel() > 0) {
/* 496:771 */         if (gLvl.getClusterNames().length <= 1)
/* 497:    */         {
/* 498:773 */           gLvl = gLvl.getPreviousLevelGraph();
/* 499:    */         }
/* 500:    */         else
/* 501:    */         {
/* 502:776 */           String fName = fileName + "L" + gLvl.getGraphLevel() + ".dot";
/* 503:777 */           writeGraph(fName, gLvl);
/* 504:778 */           gLvl = gLvl.getPreviousLevelGraph();
/* 505:    */         }
/* 506:    */       }
/* 507:781 */       fileName = fileName + ".dot";
/* 508:    */       
/* 509:    */ 
/* 510:    */ 
/* 511:    */ 
/* 512:786 */       writeGraph(fileName, this.graph_d.getMedianTree());
/* 513:    */       
/* 514:788 */       break;
/* 515:    */     case 2: 
/* 516:794 */       fileName = fileName + ".dot";
/* 517:    */       
/* 518:796 */       Graph g = this.graph_d;
/* 519:797 */       if (this.graph_d.isClusterTree()) {
/* 520:798 */         g = this.graph_d.getMedianTree();
/* 521:    */       }
/* 522:803 */       writeGraph(fileName, g);
/* 523:804 */       break;
/* 524:    */     case 1: 
/* 525:809 */       fileName = fileName + ".dot";
/* 526:810 */       writeGraph(fileName, this.graph_d);
/* 527:811 */       break;
/* 528:    */     case 4: 
/* 529:815 */       fileName = fileName + ".dot";
/* 530:816 */       Graph tmpG = this.graph_d;
/* 531:817 */       while (tmpG.getGraphLevel() > 0) {
/* 532:818 */         tmpG = tmpG.getPreviousLevelGraph();
/* 533:    */       }
/* 534:820 */       writeGraph(fileName, tmpG);
/* 535:821 */       break;
/* 536:    */     }
/* 537:    */   }
/* 538:    */   
/* 539:    */   public void writeGraph(String fileName, Graph g)
/* 540:    */   {
/* 541:    */     try
/* 542:    */     {
/* 543:837 */       this.writer_d = new BufferedWriter(new FileWriter(fileName));
/* 544:838 */       generateOutput(g);
/* 545:839 */       this.writer_d.close();
/* 546:    */     }
/* 547:    */     catch (IOException e)
/* 548:    */     {
/* 549:842 */       e.printStackTrace();
/* 550:    */     }
/* 551:    */   }
/* 552:    */   
/* 553:    */   public void generateOutput(Graph g)
/* 554:    */     throws IOException
/* 555:    */   {
/* 556:853 */     Graph gBase = g;
/* 557:855 */     while (gBase.getGraphLevel() != 0) {
/* 558:856 */       gBase = gBase.getPreviousLevelGraph();
/* 559:    */     }
/* 560:858 */     int[] clusters = gBase.getClusters();
/* 561:859 */     Node[] nodeList = gBase.getNodes();
/* 562:860 */     int nodes = nodeList.length;
/* 563:861 */     int[][] clustMatrix = new int[nodes][nodes + 1];
/* 564:866 */     for (int i = 0; i < nodes; i++)
/* 565:    */     {
/* 566:867 */       clustMatrix[i][0] = 0;
/* 567:868 */       nodeList[i].cluster = -1;
/* 568:    */     }
/* 569:871 */     int pos = 0;
/* 570:872 */     for (int i = 0; i < nodes; i++)
/* 571:    */     {
/* 572:873 */       int numCluster = clusters[i]; int 
/* 573:874 */         tmp108_107 = 0; int[] tmp108_106 = clustMatrix[numCluster]; int tmp112_111 = (tmp108_106[tmp108_107] + 1);tmp108_106[tmp108_107] = tmp112_111;clustMatrix[numCluster][tmp112_111] = i;
/* 574:875 */       nodeList[i].cluster = numCluster;
/* 575:    */     }
/* 576:881 */     writeHeader();
/* 577:882 */     Node[] on = gBase.getOriginalNodes();
/* 578:887 */     if ((on != null) && (on.length != nodeList.length)) {
/* 579:888 */       writeSpecialModules(gBase.getOriginalNodes());
/* 580:    */     }
/* 581:894 */     if (!getWriteNestedLevels()) {
/* 582:895 */       genChildrenFromOneLevel(g);
/* 583:    */     } else {
/* 584:897 */       generateClusters(g);
/* 585:    */     }
/* 586:902 */     genEdges(g);
/* 587:    */     
/* 588:    */ 
/* 589:    */ 
/* 590:    */ 
/* 591:907 */     writeClosing();
/* 592:    */   }
/* 593:    */   
/* 594:    */   public void genEdges(Graph g)
/* 595:    */     throws IOException
/* 596:    */   {
/* 597:915 */     Graph gBase = g;
/* 598:920 */     while (gBase.getGraphLevel() != 0) {
/* 599:921 */       gBase = gBase.getPreviousLevelGraph();
/* 600:    */     }
/* 601:926 */     int[] clusters = gBase.getClusters();
/* 602:927 */     Node[] nodeList = gBase.getNodes();
/* 603:928 */     int nodes = nodeList.length;
/* 604:929 */     int[][] clustMatrix = new int[nodes][nodes + 1];
/* 605:935 */     for (int i = 0; i < nodes; i++)
/* 606:    */     {
/* 607:936 */       clustMatrix[i][0] = 0;
/* 608:937 */       nodeList[i].cluster = -1;
/* 609:    */     }
/* 610:940 */     int pos = 0;
/* 611:941 */     for (int i = 0; i < nodes; i++)
/* 612:    */     {
/* 613:942 */       int numCluster = clusters[i]; int 
/* 614:943 */         tmp108_107 = 0; int[] tmp108_106 = clustMatrix[numCluster]; int tmp112_111 = (tmp108_106[tmp108_107] + 1);tmp108_106[tmp108_107] = tmp112_111;clustMatrix[numCluster][tmp112_111] = i;
/* 615:944 */       nodeList[i].cluster = numCluster;
/* 616:    */     }
/* 617:952 */     for (int i = 0; i < nodes; i++)
/* 618:    */     {
/* 619:953 */       int[] l = nodeList[i].dependencies;
/* 620:954 */       if (l != null) {
/* 621:956 */         for (int j = 0; j < l.length; j++) {
/* 622:957 */           this.writer_d.write("\"" + nodeList[i].getName() + "\" -> \"" + nodeList[l[j]].getName() + "\" [color=blue,font=6];\n");
/* 623:    */         }
/* 624:    */       }
/* 625:    */     }
/* 626:    */   }
/* 627:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.DotGraphOutput
 * JD-Core Version:    0.7.0.1
 */