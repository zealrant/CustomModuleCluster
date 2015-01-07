/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.io.BufferedWriter;
/*   4:    */ import java.io.FileWriter;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.Hashtable;
/*   7:    */ import java.util.LinkedList;
/*   8:    */ import java.util.Stack;
/*   9:    */ import java.util.Vector;
/*  10:    */ 
/*  11:    */ public class TXTTreeGraphOutput
/*  12:    */   extends GraphOutput
/*  13:    */ {
/*  14:    */   public void writeHeader(Graph gBase)
/*  15:    */     throws IOException
/*  16:    */   {
/*  17: 53 */     this.writer_d.write("// ------------------------------------------------------------ \n");
/*  18: 54 */     this.writer_d.write("// created with bunch v2 \n");
/*  19: 55 */     this.writer_d.write("// Objective Function value = " + gBase.getObjectiveFunctionValue() + "\n");
/*  20: 56 */     this.writer_d.write("// ------------------------------------------------------------ \n\n");
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void writeSpecialModules(Node[] originalNodes)
/*  24:    */     throws IOException
/*  25:    */   {
/*  26: 61 */     if (originalNodes != null)
/*  27:    */     {
/*  28: 62 */       boolean hasSuppliers = false;
/*  29: 63 */       boolean hasClients = false;
/*  30: 64 */       boolean hasCentrals = false;
/*  31: 65 */       boolean hasLibraries = false;
/*  32: 67 */       for (int j = 0; j < originalNodes.length; j++)
/*  33:    */       {
/*  34: 68 */         if ((!hasSuppliers) && (originalNodes[j].getType() == 2)) {
/*  35: 69 */           hasSuppliers = true;
/*  36:    */         }
/*  37: 71 */         if ((!hasClients) && (originalNodes[j].getType() == 1)) {
/*  38: 72 */           hasClients = true;
/*  39:    */         }
/*  40: 74 */         if ((!hasCentrals) && (originalNodes[j].getType() == 3)) {
/*  41: 75 */           hasCentrals = true;
/*  42:    */         }
/*  43: 77 */         if ((!hasLibraries) && (originalNodes[j].getType() == 4)) {
/*  44: 78 */           hasLibraries = true;
/*  45:    */         }
/*  46:    */       }
/*  47: 82 */       int count = 1;
/*  48: 83 */       if (hasLibraries)
/*  49:    */       {
/*  50: 85 */         this.writer_d.write("SS(libraries) = ");
/*  51: 86 */         for (int i = 0; i < originalNodes.length; i++) {
/*  52: 87 */           if (originalNodes[i].getType() == 4)
/*  53:    */           {
/*  54: 88 */             this.writer_d.write(originalNodes[i].getName());
/*  55: 89 */             count++;
/*  56: 90 */             if (count == originalNodes.length) {
/*  57: 91 */               this.writer_d.write("\n");
/*  58:    */             } else {
/*  59: 93 */               this.writer_d.write(", ");
/*  60:    */             }
/*  61:    */           }
/*  62:    */         }
/*  63:    */       }
/*  64: 99 */       count = 1;
/*  65:100 */       if (hasSuppliers)
/*  66:    */       {
/*  67:102 */         this.writer_d.write("SS(omnipresent_suppliers) = ");
/*  68:103 */         for (int i = 0; i < originalNodes.length; i++) {
/*  69:104 */           if (originalNodes[i].getType() == 2)
/*  70:    */           {
/*  71:105 */             this.writer_d.write(originalNodes[i].getName());
/*  72:106 */             count++;
/*  73:107 */             if (count == originalNodes.length) {
/*  74:108 */               this.writer_d.write("\n");
/*  75:    */             } else {
/*  76:110 */               this.writer_d.write(", ");
/*  77:    */             }
/*  78:    */           }
/*  79:    */         }
/*  80:    */       }
/*  81:115 */       count = 1;
/*  82:116 */       if (hasClients)
/*  83:    */       {
/*  84:118 */         this.writer_d.write("SS(omnipresent_clients) = ");
/*  85:119 */         for (int i = 0; i < originalNodes.length; i++) {
/*  86:120 */           if (originalNodes[i].getType() == 1)
/*  87:    */           {
/*  88:121 */             this.writer_d.write(originalNodes[i].getName());
/*  89:122 */             count++;
/*  90:123 */             if (count == originalNodes.length) {
/*  91:124 */               this.writer_d.write("\n");
/*  92:    */             } else {
/*  93:126 */               this.writer_d.write(", ");
/*  94:    */             }
/*  95:    */           }
/*  96:    */         }
/*  97:    */       }
/*  98:132 */       count = 1;
/*  99:133 */       if (hasCentrals)
/* 100:    */       {
/* 101:135 */         this.writer_d.write("SS(omnipresent_centrals) = ");
/* 102:136 */         for (int i = 0; i < originalNodes.length; i++) {
/* 103:137 */           if (originalNodes[i].getType() == 3)
/* 104:    */           {
/* 105:138 */             count++;
/* 106:139 */             if (count == originalNodes.length) {
/* 107:140 */               this.writer_d.write("\n");
/* 108:    */             } else {
/* 109:142 */               this.writer_d.write(", ");
/* 110:    */             }
/* 111:    */           }
/* 112:    */         }
/* 113:    */       }
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void writeClosing()
/* 118:    */     throws IOException
/* 119:    */   {
/* 120:152 */     this.writer_d.close();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void genCluster(Node n, long baseID)
/* 124:    */     throws IOException
/* 125:    */   {
/* 126:157 */     Stack st = new Stack();
/* 127:158 */     Hashtable ht = new Hashtable();
/* 128:    */     
/* 129:160 */     st.push(n);
/* 130:161 */     while (!st.empty())
/* 131:    */     {
/* 132:163 */       Node tmp = (Node)st.peek();
/* 133:164 */       if (tmp.isCluster())
/* 134:    */       {
/* 135:166 */         if (ht.containsKey(tmp.name_d))
/* 136:    */         {
/* 137:168 */           this.writer_d.write("}\n\n");
/* 138:169 */           ht.remove(tmp.name_d);
/* 139:170 */           st.pop();
/* 140:    */         }
/* 141:    */         else
/* 142:    */         {
/* 143:174 */           long clustID = tmp.nodeID + baseID++;
/* 144:175 */           this.writer_d.write("subgraph cluster" + clustID + " {\n");
/* 145:176 */           this.writer_d.write("label = \"" + tmp.name_d + "\";\n");
/* 146:177 */           this.writer_d.write("color = black;\n");
/* 147:178 */           this.writer_d.write("style = bold;\n\n");
/* 148:179 */           for (int j = 0; j < tmp.children.length; j++) {
/* 149:180 */             st.push(tmp.children[j]);
/* 150:    */           }
/* 151:181 */           ht.put(tmp.name_d, tmp.name_d);
/* 152:    */         }
/* 153:    */       }
/* 154:    */       else
/* 155:    */       {
/* 156:186 */         this.writer_d.write("\"" + tmp.getName() + "\"[shape=ellipse,color=lightblue,fontcolor=black,style=filled];\n");
/* 157:187 */         st.pop();
/* 158:    */       }
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void generateClusters(Node[] na)
/* 163:    */     throws IOException
/* 164:    */   {
/* 165:194 */     long base = 1000L;
/* 166:195 */     for (int i = 0; i < na.length; i++)
/* 167:    */     {
/* 168:197 */       genCluster(na[i], base);
/* 169:198 */       base += 1000L;
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void echoNestedChildrenOLD(Node n)
/* 174:    */     throws IOException
/* 175:    */   {
/* 176:204 */     Stack s = new Stack();
/* 177:205 */     boolean firstNode = true;
/* 178:206 */     s.push(n);
/* 179:207 */     while (!s.isEmpty())
/* 180:    */     {
/* 181:209 */       Node tmpNode = (Node)s.pop();
/* 182:210 */       if (tmpNode.children != null) {
/* 183:212 */         for (int i = 0; i < tmpNode.children.length; i++)
/* 184:    */         {
/* 185:214 */           Node childNode = tmpNode.children[i];
/* 186:215 */           if (childNode.isCluster())
/* 187:    */           {
/* 188:216 */             s.push(childNode);
/* 189:    */           }
/* 190:    */           else
/* 191:    */           {
/* 192:219 */             if (!firstNode) {
/* 193:220 */               this.writer_d.write(", ");
/* 194:    */             } else {
/* 195:222 */               firstNode = false;
/* 196:    */             }
/* 197:223 */             this.writer_d.write(childNode.getName());
/* 198:    */           }
/* 199:    */         }
/* 200:    */       }
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void genChildrenFromOneLevelOLD(Graph cLvlG)
/* 205:    */     throws IOException
/* 206:    */   {
/* 207:231 */     NextLevelGraph nextLvl = new NextLevelGraph();
/* 208:232 */     Graph nextLvlG = nextLvl.genNextLevelGraph(cLvlG);
/* 209:233 */     Node[] nodeList = nextLvlG.getNodes();
/* 210:235 */     for (int i = 0; i < nodeList.length; i++)
/* 211:    */     {
/* 212:237 */       Node tmp = nodeList[i];
/* 213:238 */       if (tmp.children != null) {
/* 214:241 */         if (tmp.children.length != 0)
/* 215:    */         {
/* 216:245 */           this.writer_d.write("SS(" + tmp.getName() + ") = ");
/* 217:    */           
/* 218:    */ 
/* 219:248 */           echoNestedChildrenOLD(tmp);
/* 220:    */           
/* 221:    */ 
/* 222:251 */           this.writer_d.write("\n");
/* 223:    */         }
/* 224:    */       }
/* 225:    */     }
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void echoNestedChildren(Node n, Vector v)
/* 229:    */     throws IOException
/* 230:    */   {
/* 231:257 */     Stack s = new Stack();
/* 232:258 */     boolean firstNode = true;
/* 233:259 */     s.push(n);
/* 234:260 */     while (!s.isEmpty())
/* 235:    */     {
/* 236:262 */       Node tmpNode = (Node)s.pop();
/* 237:263 */       if (tmpNode.children != null) {
/* 238:265 */         for (int i = 0; i < tmpNode.children.length; i++)
/* 239:    */         {
/* 240:267 */           Node childNode = tmpNode.children[i];
/* 241:268 */           if (childNode.isCluster()) {
/* 242:269 */             s.push(childNode);
/* 243:    */           } else {
/* 244:276 */             v.addElement(childNode);
/* 245:    */           }
/* 246:    */         }
/* 247:    */       }
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void genChildrenFromOneLevel(Graph cLvlG)
/* 252:    */     throws IOException
/* 253:    */   {
/* 254:284 */     Graph nextLvlG = null;
/* 255:285 */     if (cLvlG.getClusterNames().length > 1)
/* 256:    */     {
/* 257:287 */       NextLevelGraph nextLvl = new NextLevelGraph();
/* 258:288 */       nextLvlG = nextLvl.genNextLevelGraph(cLvlG);
/* 259:    */     }
/* 260:    */     else
/* 261:    */     {
/* 262:291 */       nextLvlG = cLvlG;
/* 263:    */     }
/* 264:293 */     Node[] nodeList = nextLvlG.getNodes();
/* 265:294 */     Vector cVect = new Vector();
/* 266:295 */     int Lvl = cLvlG.getGraphLevel();
/* 267:    */     
/* 268:297 */     cVect.removeAllElements();
/* 269:298 */     for (int i = 0; i < nodeList.length; i++)
/* 270:    */     {
/* 271:300 */       Node tmp = nodeList[i];
/* 272:301 */       if (tmp.children != null) {
/* 273:304 */         if (tmp.children.length != 0)
/* 274:    */         {
/* 275:307 */           findStrongestNode(tmp);
/* 276:308 */           cVect.addElement(tmp);
/* 277:    */         }
/* 278:    */       }
/* 279:    */     }
/* 280:311 */     if (cVect.size() > 0) {
/* 281:312 */       this.writer_d.write("SS(ROOT) = ");
/* 282:    */     }
/* 283:314 */     for (int i = 0; i < cVect.size(); i++)
/* 284:    */     {
/* 285:316 */       Node n = (Node)cVect.elementAt(i);
/* 286:317 */       this.writer_d.write(n.getName());
/* 287:318 */       if (i < cVect.size() - 1) {
/* 288:319 */         this.writer_d.write(", ");
/* 289:    */       } else {
/* 290:321 */         this.writer_d.write("\n");
/* 291:    */       }
/* 292:    */     }
/* 293:324 */     echoNestedTree(cVect);
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void echoNestedTree(Vector v)
/* 297:    */     throws IOException
/* 298:    */   {
/* 299:339 */     LinkedList l = new LinkedList();
/* 300:341 */     for (int i = 0; i < v.size(); i++) {
/* 301:342 */       l.addLast(v.elementAt(i));
/* 302:    */     }
/* 303:344 */     while (l.size() > 0)
/* 304:    */     {
/* 305:346 */       Node n = (Node)l.removeFirst();
/* 306:347 */       if (((n.children != null ? 1 : 0) & (n.children.length > 0 ? 1 : 0)) != 0)
/* 307:    */       {
/* 308:349 */         String ssName = findStrongestNode(n);
/* 309:    */         
/* 310:351 */         this.writer_d.write("SS(" + n.getName() + ") = ");
/* 311:352 */         for (int i = 0; i < n.children.length; i++)
/* 312:    */         {
/* 313:354 */           Node c = n.children[i];
/* 314:355 */           if (c.isCluster())
/* 315:    */           {
/* 316:357 */             l.addLast(c);
/* 317:358 */             findStrongestNode(c);
/* 318:    */           }
/* 319:360 */           this.writer_d.write(c.getName());
/* 320:361 */           if (i < n.children.length - 1) {
/* 321:362 */             this.writer_d.write(", ");
/* 322:    */           } else {
/* 323:364 */             this.writer_d.write("\n");
/* 324:    */           }
/* 325:    */         }
/* 326:    */       }
/* 327:    */     }
/* 328:    */   }
/* 329:    */   
/* 330:    */   public String findStrongestNode(Node n)
/* 331:    */   {
/* 332:372 */     if (!n.isCluster()) {
/* 333:373 */       return "";
/* 334:    */     }
/* 335:375 */     int lvl = n.nodeLevel;
/* 336:376 */     boolean lvlIncr = false;
/* 337:    */     
/* 338:378 */     Vector nodeV = new Vector();
/* 339:    */     
/* 340:380 */     LinkedList l = new LinkedList();
/* 341:381 */     l.clear();
/* 342:382 */     nodeV.clear();
/* 343:    */     
/* 344:    */ 
/* 345:    */ 
/* 346:386 */     l.addLast(n);
/* 347:387 */     while (!l.isEmpty())
/* 348:    */     {
/* 349:389 */       Node curr = (Node)l.removeFirst();
/* 350:390 */       if (curr.isCluster())
/* 351:    */       {
/* 352:392 */         Node[] children = curr.children;
/* 353:393 */         if ((children != null) && (children.length > 0)) {
/* 354:394 */           for (int j = 0; j < children.length; j++) {
/* 355:395 */             l.addLast(children[j]);
/* 356:    */           }
/* 357:    */         }
/* 358:    */       }
/* 359:    */       else
/* 360:    */       {
/* 361:399 */         nodeV.add(curr);
/* 362:    */       }
/* 363:    */     }
/* 364:404 */     String ssName = "(SS-L" + n.nodeLevel + "):" + findStrongestNode(nodeV);
/* 365:405 */     n.setName(ssName);
/* 366:406 */     return ssName;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public String findStrongestNode(Vector v)
/* 370:    */   {
/* 371:412 */     int maxEdgeWeight = 0;
/* 372:413 */     int maxEdgeCount = 0;
/* 373:414 */     Node domEdgeNode = null;
/* 374:415 */     Node domWeightNode = null;
/* 375:417 */     if (v == null) {
/* 376:417 */       return "EmptyCluster";
/* 377:    */     }
/* 378:419 */     for (int i = 0; i < v.size(); i++)
/* 379:    */     {
/* 380:421 */       Node n = (Node)v.elementAt(i);
/* 381:422 */       String name = n.getName();
/* 382:423 */       int edgeWeights = 0;
/* 383:424 */       int depCount = 0;
/* 384:425 */       int beCount = 0;
/* 385:427 */       if (n.dependencies != null) {
/* 386:428 */         depCount = n.dependencies.length;
/* 387:    */       }
/* 388:430 */       if (n.backEdges != null) {
/* 389:431 */         beCount = n.backEdges.length;
/* 390:    */       }
/* 391:433 */       int edgeCount = depCount + beCount;
/* 392:435 */       if (edgeCount > maxEdgeCount)
/* 393:    */       {
/* 394:437 */         maxEdgeCount = edgeCount;
/* 395:438 */         domEdgeNode = n;
/* 396:    */       }
/* 397:441 */       if (n.weights != null) {
/* 398:442 */         for (int j = 0; j < n.weights.length; j++) {
/* 399:443 */           edgeWeights += n.weights[j];
/* 400:    */         }
/* 401:    */       }
/* 402:445 */       if (n.beWeights != null) {
/* 403:446 */         for (int j = 0; j < n.beWeights.length; j++) {
/* 404:447 */           edgeWeights += n.beWeights[j];
/* 405:    */         }
/* 406:    */       }
/* 407:449 */       if (edgeWeights > maxEdgeWeight)
/* 408:    */       {
/* 409:451 */         maxEdgeWeight = edgeWeights;
/* 410:452 */         domWeightNode = n;
/* 411:    */       }
/* 412:    */     }
/* 413:456 */     return domEdgeNode.getName();
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void WriteOutputClusters(Vector cVect, int lvl)
/* 417:    */     throws IOException
/* 418:    */   {
/* 419:461 */     if (cVect == null) {
/* 420:461 */       return;
/* 421:    */     }
/* 422:463 */     for (int i = 0; i < cVect.size(); i++)
/* 423:    */     {
/* 424:465 */       Vector cluster = (Vector)cVect.elementAt(i);
/* 425:466 */       String cName = findStrongestNode(cluster);
/* 426:467 */       if (lvl > 0) {
/* 427:468 */         cName = cName + "L" + lvl;
/* 428:    */       }
/* 429:470 */       cName = cName + ".ss";
/* 430:    */       
/* 431:472 */       this.writer_d.write("SS(" + cName + ") = ");
/* 432:473 */       for (int j = 0; j < cluster.size(); j++)
/* 433:    */       {
/* 434:475 */         Node n = (Node)cluster.elementAt(j);
/* 435:476 */         this.writer_d.write(n.getName());
/* 436:477 */         if (j < cluster.size() - 1) {
/* 437:478 */           this.writer_d.write(", ");
/* 438:    */         } else {
/* 439:480 */           this.writer_d.write("\n");
/* 440:    */         }
/* 441:    */       }
/* 442:    */     }
/* 443:    */   }
/* 444:    */   
/* 445:    */   public void write()
/* 446:    */   {
/* 447:490 */     Graph gBase = this.graph_d;
/* 448:491 */     while (gBase.getGraphLevel() != 0) {
/* 449:492 */       gBase = gBase.getPreviousLevelGraph();
/* 450:    */     }
/* 451:494 */     int[] clusters = gBase.getClusters();
/* 452:495 */     Node[] nodeList = gBase.getNodes();
/* 453:496 */     int nodes = nodeList.length;
/* 454:497 */     int[][] clustMatrix = new int[nodes][nodes + 1];
/* 455:    */     
/* 456:499 */     Vector edges = new Vector();
/* 457:    */     try
/* 458:    */     {
/* 459:508 */       this.writer_d = new BufferedWriter(new FileWriter(getCurrentName() + ".bunch"));
/* 460:510 */       for (int i = 0; i < nodes; i++)
/* 461:    */       {
/* 462:511 */         clustMatrix[i][0] = 0;
/* 463:512 */         nodeList[i].cluster = -1;
/* 464:    */       }
/* 465:515 */       int pos = 0;
/* 466:516 */       for (int i = 0; i < nodes; i++)
/* 467:    */       {
/* 468:517 */         int numCluster = clusters[i]; int 
/* 469:518 */           tmp157_156 = 0; int[] tmp157_155 = clustMatrix[numCluster]; int tmp161_160 = (tmp157_155[tmp157_156] + 1);tmp157_155[tmp157_156] = tmp161_160;clustMatrix[numCluster][tmp161_160] = i;
/* 470:519 */         nodeList[i].cluster = numCluster;
/* 471:    */       }
/* 472:524 */       Node[] on = gBase.getOriginalNodes();
/* 473:525 */       if ((on != null) && (on.length != nodeList.length)) {
/* 474:526 */         writeSpecialModules(gBase.getOriginalNodes());
/* 475:    */       }
/* 476:527 */       genChildrenFromOneLevel(this.graph_d);
/* 477:528 */       writeClosing();
/* 478:    */     }
/* 479:    */     catch (IOException e)
/* 480:    */     {
/* 481:579 */       e.printStackTrace();
/* 482:    */     }
/* 483:    */   }
/* 484:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.TXTTreeGraphOutput
 * JD-Core Version:    0.7.0.1
 */