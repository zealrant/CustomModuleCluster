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
/*  12:    */ public class TXTGraphOutput
/*  13:    */   extends GraphOutput
/*  14:    */ {
/*  15: 53 */   boolean hasSuppliers = false;
/*  16: 54 */   boolean hasClients = false;
/*  17: 55 */   boolean hasCentrals = false;
/*  18: 56 */   boolean hasLibraries = false;
/*  19:    */   
/*  20:    */   public void writeHeader(Graph gBase)
/*  21:    */     throws IOException
/*  22:    */   {
/*  23: 65 */     this.writer_d.write("// ------------------------------------------------------------ \n");
/*  24: 66 */     this.writer_d.write("// created with bunch v2 \n");
/*  25: 67 */     this.writer_d.write("// Objective Function value = " + gBase.getObjectiveFunctionValue() + "\n");
/*  26: 68 */     this.writer_d.write("// ------------------------------------------------------------ \n\n");
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void checkForSpecialModules(Node[] originalNodes)
/*  30:    */   {
/*  31: 73 */     if (originalNodes != null)
/*  32:    */     {
/*  33: 74 */       this.hasSuppliers = false;
/*  34: 75 */       this.hasClients = false;
/*  35: 76 */       this.hasCentrals = false;
/*  36: 77 */       this.hasLibraries = false;
/*  37: 79 */       for (int j = 0; j < originalNodes.length; j++)
/*  38:    */       {
/*  39: 80 */         if ((!this.hasSuppliers) && (originalNodes[j].getType() == 2)) {
/*  40: 81 */           this.hasSuppliers = true;
/*  41:    */         }
/*  42: 83 */         if ((!this.hasClients) && (originalNodes[j].getType() == 1)) {
/*  43: 84 */           this.hasClients = true;
/*  44:    */         }
/*  45: 86 */         if ((!this.hasCentrals) && (originalNodes[j].getType() == 3)) {
/*  46: 87 */           this.hasCentrals = true;
/*  47:    */         }
/*  48: 89 */         if ((!this.hasLibraries) && (originalNodes[j].getType() == 4)) {
/*  49: 90 */           this.hasLibraries = true;
/*  50:    */         }
/*  51:    */       }
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void writeSpecialModules(Node[] originalNodes)
/*  56:    */     throws IOException
/*  57:    */   {
/*  58: 98 */     ArrayList deadList = new ArrayList();
/*  59: 99 */     deadList.clear();
/*  60:101 */     if (originalNodes != null)
/*  61:    */     {
/*  62:102 */       this.hasSuppliers = false;
/*  63:103 */       this.hasClients = false;
/*  64:104 */       this.hasCentrals = false;
/*  65:105 */       this.hasLibraries = false;
/*  66:107 */       for (int j = 0; j < originalNodes.length; j++)
/*  67:    */       {
/*  68:108 */         if ((!this.hasSuppliers) && (originalNodes[j].getType() == 2)) {
/*  69:109 */           this.hasSuppliers = true;
/*  70:    */         }
/*  71:111 */         if ((!this.hasClients) && (originalNodes[j].getType() == 1)) {
/*  72:112 */           this.hasClients = true;
/*  73:    */         }
/*  74:114 */         if ((!this.hasCentrals) && (originalNodes[j].getType() == 3)) {
/*  75:115 */           this.hasCentrals = true;
/*  76:    */         }
/*  77:117 */         if ((!this.hasLibraries) && (originalNodes[j].getType() == 4)) {
/*  78:118 */           this.hasLibraries = true;
/*  79:    */         }
/*  80:    */       }
/*  81:122 */       int count = 1;
/*  82:123 */       if (this.hasLibraries)
/*  83:    */       {
/*  84:125 */         this.writer_d.write("SS(libraries) = ");
/*  85:126 */         for (int i = 0; i < originalNodes.length; i++)
/*  86:    */         {
/*  87:127 */           if (originalNodes[i].getType() == 4)
/*  88:    */           {
/*  89:128 */             if (count > 1) {
/*  90:128 */               this.writer_d.write(", ");
/*  91:    */             }
/*  92:129 */             this.writer_d.write(originalNodes[i].getName());
/*  93:130 */             count++;
/*  94:    */           }
/*  95:132 */           if ((originalNodes[i].getType() >= 128) && 
/*  96:133 */             (originalNodes[i].getType() - 128 == 4))
/*  97:    */           {
/*  98:134 */             if (count > 1) {
/*  99:134 */               this.writer_d.write(", ");
/* 100:    */             }
/* 101:135 */             this.writer_d.write(originalNodes[i].getName());
/* 102:136 */             deadList.add(originalNodes[i]);
/* 103:137 */             count++;
/* 104:    */           }
/* 105:    */         }
/* 106:140 */         this.writer_d.write("\n");
/* 107:    */       }
/* 108:143 */       count = 1;
/* 109:144 */       if (this.hasSuppliers)
/* 110:    */       {
/* 111:146 */         this.writer_d.write("SS(omnipresent_suppliers) = ");
/* 112:147 */         for (int i = 0; i < originalNodes.length; i++)
/* 113:    */         {
/* 114:148 */           if (originalNodes[i].getType() == 2)
/* 115:    */           {
/* 116:149 */             if (count > 1) {
/* 117:149 */               this.writer_d.write(", ");
/* 118:    */             }
/* 119:150 */             this.writer_d.write(originalNodes[i].getName());
/* 120:151 */             count++;
/* 121:    */           }
/* 122:153 */           if ((originalNodes[i].getType() >= 128) && 
/* 123:154 */             (originalNodes[i].getType() - 128 == 2))
/* 124:    */           {
/* 125:155 */             if (count > 1) {
/* 126:155 */               this.writer_d.write(", ");
/* 127:    */             }
/* 128:156 */             this.writer_d.write(originalNodes[i].getName());
/* 129:157 */             deadList.add(originalNodes[i]);
/* 130:158 */             count++;
/* 131:    */           }
/* 132:    */         }
/* 133:161 */         this.writer_d.write("\n");
/* 134:    */       }
/* 135:164 */       count = 1;
/* 136:165 */       if (this.hasClients)
/* 137:    */       {
/* 138:167 */         this.writer_d.write("SS(omnipresent_clients) = ");
/* 139:168 */         for (int i = 0; i < originalNodes.length; i++)
/* 140:    */         {
/* 141:169 */           if (originalNodes[i].getType() == 1)
/* 142:    */           {
/* 143:170 */             if (count > 1) {
/* 144:170 */               this.writer_d.write(", ");
/* 145:    */             }
/* 146:171 */             this.writer_d.write(originalNodes[i].getName());
/* 147:172 */             count++;
/* 148:    */           }
/* 149:174 */           if ((originalNodes[i].getType() >= 128) && 
/* 150:175 */             (originalNodes[i].getType() - 128 == 1))
/* 151:    */           {
/* 152:176 */             if (count > 1) {
/* 153:176 */               this.writer_d.write(", ");
/* 154:    */             }
/* 155:177 */             this.writer_d.write(originalNodes[i].getName());
/* 156:178 */             deadList.add(originalNodes[i]);
/* 157:179 */             count++;
/* 158:    */           }
/* 159:    */         }
/* 160:182 */         this.writer_d.write("\n");
/* 161:    */       }
/* 162:185 */       count = 1;
/* 163:186 */       if (this.hasCentrals)
/* 164:    */       {
/* 165:188 */         this.writer_d.write("SS(omnipresent_centrals) = ");
/* 166:189 */         for (int i = 0; i < originalNodes.length; i++)
/* 167:    */         {
/* 168:190 */           if (originalNodes[i].getType() == 3)
/* 169:    */           {
/* 170:191 */             if (count > 1) {
/* 171:191 */               this.writer_d.write(", ");
/* 172:    */             }
/* 173:192 */             this.writer_d.write(originalNodes[i].getName());
/* 174:193 */             count++;
/* 175:    */           }
/* 176:195 */           if ((originalNodes[i].getType() >= 128) && 
/* 177:196 */             (originalNodes[i].getType() - 128 == 3))
/* 178:    */           {
/* 179:197 */             if (count > 1) {
/* 180:197 */               this.writer_d.write(", ");
/* 181:    */             }
/* 182:198 */             this.writer_d.write(originalNodes[i].getName());
/* 183:199 */             deadList.add(originalNodes[i]);
/* 184:200 */             count++;
/* 185:    */           }
/* 186:    */         }
/* 187:203 */         this.writer_d.write("\n");
/* 188:    */       }
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void writeClosing()
/* 193:    */     throws IOException
/* 194:    */   {}
/* 195:    */   
/* 196:    */   public void genClusterOLD(Node n, long baseID)
/* 197:    */     throws IOException
/* 198:    */   {
/* 199:216 */     Stack st = new Stack();
/* 200:217 */     Hashtable ht = new Hashtable();
/* 201:    */     
/* 202:219 */     st.push(n);
/* 203:220 */     while (!st.empty())
/* 204:    */     {
/* 205:222 */       Node tmp = (Node)st.peek();
/* 206:223 */       if (tmp.isCluster())
/* 207:    */       {
/* 208:225 */         if (ht.containsKey(tmp.name_d))
/* 209:    */         {
/* 210:227 */           this.writer_d.write("}\n\n");
/* 211:228 */           ht.remove(tmp.name_d);
/* 212:229 */           st.pop();
/* 213:    */         }
/* 214:    */         else
/* 215:    */         {
/* 216:233 */           long clustID = tmp.nodeID + baseID++;
/* 217:234 */           this.writer_d.write("subgraph cluster" + clustID + " {\n");
/* 218:235 */           this.writer_d.write("label = \"" + tmp.name_d + "\";\n");
/* 219:236 */           this.writer_d.write("color = black;\n");
/* 220:237 */           this.writer_d.write("style = bold;\n\n");
/* 221:238 */           for (int j = 0; j < tmp.children.length; j++) {
/* 222:239 */             st.push(tmp.children[j]);
/* 223:    */           }
/* 224:240 */           ht.put(tmp.name_d, tmp.name_d);
/* 225:    */         }
/* 226:    */       }
/* 227:    */       else
/* 228:    */       {
/* 229:245 */         this.writer_d.write("\"" + tmp.getName() + "\"[shape=ellipse,color=lightblue,fontcolor=black,style=filled];\n");
/* 230:246 */         st.pop();
/* 231:    */       }
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void generateClustersOLD(Node[] na)
/* 236:    */     throws IOException
/* 237:    */   {
/* 238:253 */     long base = 1000L;
/* 239:254 */     for (int i = 0; i < na.length; i++)
/* 240:    */     {
/* 241:256 */       genCluster(na[i], base);
/* 242:257 */       base += 1000L;
/* 243:    */     }
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void echoNestedChildrenOLD(Node n)
/* 247:    */     throws IOException
/* 248:    */   {
/* 249:263 */     Stack s = new Stack();
/* 250:264 */     boolean firstNode = true;
/* 251:265 */     s.push(n);
/* 252:266 */     while (!s.isEmpty())
/* 253:    */     {
/* 254:268 */       Node tmpNode = (Node)s.pop();
/* 255:269 */       if (tmpNode.children != null) {
/* 256:271 */         for (int i = 0; i < tmpNode.children.length; i++)
/* 257:    */         {
/* 258:273 */           Node childNode = tmpNode.children[i];
/* 259:274 */           if (childNode.isCluster())
/* 260:    */           {
/* 261:275 */             s.push(childNode);
/* 262:    */           }
/* 263:    */           else
/* 264:    */           {
/* 265:278 */             if (!firstNode) {
/* 266:279 */               this.writer_d.write(", ");
/* 267:    */             } else {
/* 268:281 */               firstNode = false;
/* 269:    */             }
/* 270:282 */             this.writer_d.write(childNode.getName());
/* 271:    */           }
/* 272:    */         }
/* 273:    */       }
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void genChildrenFromOneLevelOLD(Graph cLvlG)
/* 278:    */     throws IOException
/* 279:    */   {
/* 280:290 */     NextLevelGraph nextLvl = new NextLevelGraph();
/* 281:291 */     Graph nextLvlG = nextLvl.genNextLevelGraph(cLvlG);
/* 282:292 */     Node[] nodeList = nextLvlG.getNodes();
/* 283:294 */     for (int i = 0; i < nodeList.length; i++)
/* 284:    */     {
/* 285:296 */       Node tmp = nodeList[i];
/* 286:297 */       if (tmp.children != null) {
/* 287:300 */         if (tmp.children.length != 0)
/* 288:    */         {
/* 289:304 */           this.writer_d.write("SS(" + tmp.getName() + ") = ");
/* 290:    */           
/* 291:    */ 
/* 292:307 */           echoNestedChildrenOLD(tmp);
/* 293:    */           
/* 294:    */ 
/* 295:310 */           this.writer_d.write("\n");
/* 296:    */         }
/* 297:    */       }
/* 298:    */     }
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void genCluster(Node n, long baseID)
/* 302:    */     throws IOException
/* 303:    */   {
/* 304:316 */     Stack st = new Stack();
/* 305:317 */     Hashtable ht = new Hashtable();
/* 306:    */     
/* 307:319 */     st.push(n);
/* 308:320 */     while (!st.empty())
/* 309:    */     {
/* 310:322 */       Node tmp = (Node)st.peek();
/* 311:323 */       if (tmp.isCluster())
/* 312:    */       {
/* 313:325 */         if (ht.containsKey(tmp.name_d))
/* 314:    */         {
/* 315:327 */           this.writer_d.write("}\n\n");
/* 316:328 */           ht.remove(tmp.name_d);
/* 317:329 */           st.pop();
/* 318:    */         }
/* 319:    */         else
/* 320:    */         {
/* 321:333 */           String cName = findStrongestNode(tmp);
/* 322:    */           
/* 323:335 */           cName = cName + ".ssL" + tmp.nodeLevel;
/* 324:336 */           long clustID = tmp.nodeID + baseID++;
/* 325:337 */           this.writer_d.write("subgraph cluster" + clustID + " {\n");
/* 326:338 */           this.writer_d.write("label = \"" + cName + "\";\n");
/* 327:339 */           this.writer_d.write("color = black;\n");
/* 328:340 */           this.writer_d.write("style = bold;\n\n");
/* 329:341 */           for (int j = 0; j < tmp.children.length; j++) {
/* 330:342 */             st.push(tmp.children[j]);
/* 331:    */           }
/* 332:343 */           ht.put(tmp.name_d, tmp.name_d);
/* 333:    */         }
/* 334:    */       }
/* 335:    */       else
/* 336:    */       {
/* 337:348 */         this.writer_d.write("\"" + tmp.getName() + "\"[shape=ellipse,color=lightblue,fontcolor=black,style=filled];\n");
/* 338:349 */         st.pop();
/* 339:    */       }
/* 340:    */     }
/* 341:    */   }
/* 342:    */   
/* 343:    */   public void generateClusters(Graph cLvlG)
/* 344:    */     throws IOException
/* 345:    */   {
/* 346:356 */     Graph nextLvlG = null;
/* 347:358 */     if ((cLvlG.getClusterNames().length <= 1) && (cLvlG.getPreviousLevelGraph() != null)) {
/* 348:359 */       cLvlG = cLvlG.getPreviousLevelGraph();
/* 349:    */     }
/* 350:362 */     NextLevelGraph nextLvl = new NextLevelGraph();
/* 351:363 */     nextLvlG = nextLvl.genNextLevelGraph(cLvlG);
/* 352:    */     
/* 353:    */ 
/* 354:366 */     Node[] nodeList = nextLvlG.getNodes();
/* 355:367 */     int Lvl = cLvlG.getGraphLevel();
/* 356:368 */     Vector cVect = new Vector();
/* 357:369 */     cVect.clear();
/* 358:    */     
/* 359:371 */     long base = 1000L;
/* 360:372 */     this.writer_d.write("SS(ROOT) = ");
/* 361:373 */     int count = 1;
/* 362:374 */     for (int i = 0; i < nodeList.length; i++)
/* 363:    */     {
/* 364:376 */       Node tmp = nodeList[i];
/* 365:377 */       if (tmp.children != null) {
/* 366:380 */         if (tmp.children.length != 0)
/* 367:    */         {
/* 368:383 */           findStrongestNode(tmp);
/* 369:384 */           cVect.addElement(tmp);
/* 370:    */         }
/* 371:    */       }
/* 372:    */     }
/* 373:388 */     for (int i = 0; i < cVect.size(); i++)
/* 374:    */     {
/* 375:390 */       if (count > 1) {
/* 376:390 */         this.writer_d.write(", ");
/* 377:    */       }
/* 378:391 */       count++;
/* 379:392 */       Node n = (Node)cVect.elementAt(i);
/* 380:    */       
/* 381:394 */       String ssName = n.getName() + ".ssL" + n.nodeLevel;
/* 382:395 */       this.writer_d.write(ssName);
/* 383:    */     }
/* 384:398 */     if (this.hasSuppliers)
/* 385:    */     {
/* 386:400 */       if (count > 1) {
/* 387:400 */         this.writer_d.write(", ");
/* 388:    */       }
/* 389:401 */       count++;
/* 390:402 */       this.writer_d.write("omnipresent_suppliers");
/* 391:    */     }
/* 392:404 */     if (this.hasClients)
/* 393:    */     {
/* 394:406 */       if (count > 1) {
/* 395:406 */         this.writer_d.write(", ");
/* 396:    */       }
/* 397:407 */       count++;
/* 398:408 */       this.writer_d.write("omnipresent_clients");
/* 399:    */     }
/* 400:410 */     if (this.hasCentrals)
/* 401:    */     {
/* 402:412 */       if (count > 1) {
/* 403:412 */         this.writer_d.write(", ");
/* 404:    */       }
/* 405:413 */       count++;
/* 406:414 */       this.writer_d.write("omnipresent_centrals");
/* 407:    */     }
/* 408:416 */     if (this.hasLibraries)
/* 409:    */     {
/* 410:418 */       if (count > 1) {
/* 411:418 */         this.writer_d.write(", ");
/* 412:    */       }
/* 413:419 */       count++;
/* 414:420 */       this.writer_d.write("libraries");
/* 415:    */     }
/* 416:422 */     this.writer_d.write("\n");
/* 417:    */     
/* 418:424 */     echoNestedTree(cVect);
/* 419:    */   }
/* 420:    */   
/* 421:    */   public void echoNestedTree(Vector v)
/* 422:    */     throws IOException
/* 423:    */   {
/* 424:429 */     LinkedList l = new LinkedList();
/* 425:431 */     for (int i = 0; i < v.size(); i++) {
/* 426:432 */       l.addLast(v.elementAt(i));
/* 427:    */     }
/* 428:434 */     while (l.size() > 0)
/* 429:    */     {
/* 430:436 */       Node n = (Node)l.removeFirst();
/* 431:437 */       if (((n.children != null ? 1 : 0) & (n.children.length > 0 ? 1 : 0)) != 0)
/* 432:    */       {
/* 433:439 */         findStrongestNode(n);
/* 434:    */         
/* 435:    */ 
/* 436:442 */         String ssName = n.getName() + ".ssL" + n.nodeLevel;
/* 437:443 */         this.writer_d.write("SS(" + ssName + ") = ");
/* 438:444 */         for (int i = 0; i < n.children.length; i++)
/* 439:    */         {
/* 440:446 */           Node c = n.children[i];
/* 441:447 */           if (c.isCluster())
/* 442:    */           {
/* 443:449 */             l.addLast(c);
/* 444:450 */             findStrongestNode(c);
/* 445:    */             
/* 446:452 */             ssName = c.getName() + ".ssL" + c.nodeLevel;
/* 447:453 */             this.writer_d.write(ssName);
/* 448:    */           }
/* 449:    */           else
/* 450:    */           {
/* 451:456 */             this.writer_d.write(c.getName());
/* 452:    */           }
/* 453:457 */           if (i < n.children.length - 1) {
/* 454:458 */             this.writer_d.write(", ");
/* 455:    */           } else {
/* 456:460 */             this.writer_d.write("\n");
/* 457:    */           }
/* 458:    */         }
/* 459:    */       }
/* 460:    */     }
/* 461:    */   }
/* 462:    */   
/* 463:    */   public void echoNestedChildren(Node n, Vector v)
/* 464:    */     throws IOException
/* 465:    */   {
/* 466:468 */     Stack s = new Stack();
/* 467:469 */     boolean firstNode = true;
/* 468:470 */     s.push(n);
/* 469:471 */     while (!s.isEmpty())
/* 470:    */     {
/* 471:473 */       Node tmpNode = (Node)s.pop();
/* 472:474 */       if (tmpNode.children != null) {
/* 473:476 */         for (int i = 0; i < tmpNode.children.length; i++)
/* 474:    */         {
/* 475:478 */           Node childNode = tmpNode.children[i];
/* 476:479 */           if (childNode.isCluster()) {
/* 477:480 */             s.push(childNode);
/* 478:    */           } else {
/* 479:487 */             v.addElement(childNode);
/* 480:    */           }
/* 481:    */         }
/* 482:    */       }
/* 483:    */     }
/* 484:    */   }
/* 485:    */   
/* 486:    */   public void genChildrenFromOneLevel(Graph cLvlG)
/* 487:    */     throws IOException
/* 488:    */   {
/* 489:495 */     Graph nextLvlG = null;
/* 490:498 */     if ((cLvlG.getClusterNames().length <= 1) && (cLvlG.getPreviousLevelGraph() != null))
/* 491:    */     {
/* 492:500 */       cLvlG = cLvlG.getPreviousLevelGraph();
/* 493:501 */       fixupNodeList(cLvlG);
/* 494:    */     }
/* 495:507 */     NextLevelGraph nextLvl = new NextLevelGraph();
/* 496:508 */     nextLvlG = nextLvl.genNextLevelGraph(cLvlG);
/* 497:    */     
/* 498:    */ 
/* 499:    */ 
/* 500:    */ 
/* 501:513 */     Node[] nodeList = nextLvlG.getNodes();
/* 502:514 */     Vector cVect = new Vector();
/* 503:515 */     int Lvl = cLvlG.getGraphLevel();
/* 504:    */     
/* 505:517 */     cVect.removeAllElements();
/* 506:518 */     for (int i = 0; i < nodeList.length; i++)
/* 507:    */     {
/* 508:520 */       Node tmp = nodeList[i];
/* 509:521 */       if (tmp.children != null) {
/* 510:524 */         if (tmp.children.length != 0)
/* 511:    */         {
/* 512:527 */           findStrongestNode(tmp);
/* 513:    */           
/* 514:529 */           Vector newCluster = new Vector();
/* 515:530 */           newCluster.removeAllElements();
/* 516:531 */           cVect.addElement(newCluster);
/* 517:532 */           echoNestedChildren(tmp, newCluster);
/* 518:    */         }
/* 519:    */       }
/* 520:    */     }
/* 521:542 */     WriteOutputClusters(cVect, Lvl);
/* 522:    */   }
/* 523:    */   
/* 524:    */   public String findStrongestNode(Node n)
/* 525:    */   {
/* 526:547 */     if (!n.isCluster()) {
/* 527:548 */       return "";
/* 528:    */     }
/* 529:550 */     int lvl = n.nodeLevel;
/* 530:551 */     boolean lvlIncr = false;
/* 531:    */     
/* 532:553 */     Vector nodeV = new Vector();
/* 533:    */     
/* 534:555 */     LinkedList l = new LinkedList();
/* 535:556 */     l.clear();
/* 536:557 */     nodeV.clear();
/* 537:    */     
/* 538:    */ 
/* 539:    */ 
/* 540:561 */     l.addLast(n);
/* 541:562 */     while (!l.isEmpty())
/* 542:    */     {
/* 543:564 */       Node curr = (Node)l.removeFirst();
/* 544:565 */       if (curr.isCluster())
/* 545:    */       {
/* 546:567 */         Node[] children = curr.children;
/* 547:569 */         if ((children != null) && (children.length > 0)) {
/* 548:570 */           for (int j = 0; j < children.length; j++) {
/* 549:571 */             l.addLast(children[j]);
/* 550:    */           }
/* 551:    */         }
/* 552:    */       }
/* 553:    */       else
/* 554:    */       {
/* 555:575 */         nodeV.add(curr);
/* 556:    */       }
/* 557:    */     }
/* 558:580 */     String ssName = findStrongestNode(nodeV);
/* 559:581 */     n.setName(ssName);
/* 560:    */     
/* 561:583 */     ssName = ssName + ".ssL" + n.nodeLevel;
/* 562:584 */     return ssName;
/* 563:    */   }
/* 564:    */   
/* 565:    */   public String findStrongestNode(Vector v)
/* 566:    */   {
/* 567:589 */     int maxEdgeWeight = 0;
/* 568:590 */     int maxEdgeCount = 0;
/* 569:591 */     Node domEdgeNode = null;
/* 570:592 */     Node domWeightNode = null;
/* 571:594 */     if (v == null) {
/* 572:594 */       return "EmptyCluster";
/* 573:    */     }
/* 574:597 */     for (int i = 0; i < v.size(); i++)
/* 575:    */     {
/* 576:599 */       Node n = (Node)v.elementAt(i);
/* 577:600 */       String name = n.getName();
/* 578:601 */       int edgeWeights = 0;
/* 579:602 */       int depCount = 0;
/* 580:603 */       int beCount = 0;
/* 581:605 */       if (n.dependencies != null) {
/* 582:606 */         depCount = n.dependencies.length;
/* 583:    */       }
/* 584:608 */       if (n.backEdges != null) {
/* 585:609 */         beCount = n.backEdges.length;
/* 586:    */       }
/* 587:611 */       int edgeCount = depCount + beCount;
/* 588:613 */       if (edgeCount >= maxEdgeCount)
/* 589:    */       {
/* 590:615 */         maxEdgeCount = edgeCount;
/* 591:616 */         domEdgeNode = n;
/* 592:    */       }
/* 593:619 */       if (n.weights != null) {
/* 594:620 */         for (int j = 0; j < n.weights.length; j++) {
/* 595:621 */           edgeWeights += n.weights[j];
/* 596:    */         }
/* 597:    */       }
/* 598:623 */       if (n.beWeights != null) {
/* 599:624 */         for (int j = 0; j < n.beWeights.length; j++) {
/* 600:625 */           edgeWeights += n.beWeights[j];
/* 601:    */         }
/* 602:    */       }
/* 603:627 */       if (edgeWeights >= maxEdgeWeight)
/* 604:    */       {
/* 605:629 */         maxEdgeWeight = edgeWeights;
/* 606:630 */         domWeightNode = n;
/* 607:    */       }
/* 608:    */     }
/* 609:638 */     return domEdgeNode.getName();
/* 610:    */   }
/* 611:    */   
/* 612:    */   public void WriteOutputClusters(Vector cVect, int lvl)
/* 613:    */     throws IOException
/* 614:    */   {
/* 615:643 */     if (cVect == null) {
/* 616:643 */       return;
/* 617:    */     }
/* 618:645 */     for (int i = 0; i < cVect.size(); i++)
/* 619:    */     {
/* 620:647 */       Vector cluster = (Vector)cVect.elementAt(i);
/* 621:648 */       String cName = findStrongestNode(cluster);
/* 622:    */       
/* 623:    */ 
/* 624:    */ 
/* 625:    */ 
/* 626:    */ 
/* 627:654 */       this.writer_d.write("SS(" + cName + ".ss) = ");
/* 628:655 */       for (int j = 0; j < cluster.size(); j++)
/* 629:    */       {
/* 630:657 */         Node n = (Node)cluster.elementAt(j);
/* 631:658 */         this.writer_d.write(n.getName());
/* 632:659 */         if (j < cluster.size() - 1) {
/* 633:660 */           this.writer_d.write(", ");
/* 634:    */         } else {
/* 635:662 */           this.writer_d.write("\n");
/* 636:    */         }
/* 637:    */       }
/* 638:    */     }
/* 639:    */   }
/* 640:    */   
/* 641:    */   public void write()
/* 642:    */   {
/* 643:669 */     Graph gWriteGraph = this.graph_d;
/* 644:    */     
/* 645:671 */     int technique = getOutputTechnique();
/* 646:672 */     String fileName = getCurrentName();
/* 647:674 */     switch (technique)
/* 648:    */     {
/* 649:    */     case 3: 
/* 650:678 */       Graph gLvl = this.graph_d;
/* 651:680 */       while (gLvl.getGraphLevel() > 0)
/* 652:    */       {
/* 653:682 */         fixupNodeList(gLvl);
/* 654:683 */         if (gLvl.getClusterNames().length <= 1)
/* 655:    */         {
/* 656:685 */           gLvl = gLvl.getPreviousLevelGraph();
/* 657:    */         }
/* 658:    */         else
/* 659:    */         {
/* 660:688 */           String fName = fileName + "L" + gLvl.getGraphLevel() + ".bunch";
/* 661:689 */           writeGraph(fName, gLvl);
/* 662:690 */           gLvl = gLvl.getPreviousLevelGraph();
/* 663:    */         }
/* 664:    */       }
/* 665:693 */       fileName = fileName + ".bunch";
/* 666:694 */       writeGraph(fileName, this.graph_d.getMedianTree());
/* 667:    */       
/* 668:696 */       break;
/* 669:    */     case 2: 
/* 670:700 */       fileName = fileName + ".bunch";
/* 671:    */       
/* 672:702 */       Graph g = this.graph_d;
/* 673:703 */       if (this.graph_d.isClusterTree()) {
/* 674:704 */         g = this.graph_d.getMedianTree();
/* 675:    */       }
/* 676:705 */       writeGraph(fileName, g);
/* 677:706 */       break;
/* 678:    */     case 1: 
/* 679:710 */       fileName = fileName + ".bunch";
/* 680:711 */       writeGraph(fileName, this.graph_d);
/* 681:712 */       break;
/* 682:    */     case 4: 
/* 683:716 */       fileName = fileName + ".bunch";
/* 684:717 */       Graph tmpG = this.graph_d;
/* 685:718 */       while (tmpG.getGraphLevel() > 0) {
/* 686:719 */         tmpG = tmpG.getPreviousLevelGraph();
/* 687:    */       }
/* 688:721 */       writeGraph(fileName, tmpG);
/* 689:722 */       break;
/* 690:    */     }
/* 691:    */   }
/* 692:    */   
/* 693:    */   public void writeGraph(String fileName, Graph g)
/* 694:    */   {
/* 695:    */     try
/* 696:    */     {
/* 697:730 */       this.writer_d = new BufferedWriter(new FileWriter(fileName));
/* 698:731 */       generateOutput(g);
/* 699:732 */       this.writer_d.close();
/* 700:    */     }
/* 701:    */     catch (IOException e)
/* 702:    */     {
/* 703:735 */       e.printStackTrace();
/* 704:    */     }
/* 705:    */   }
/* 706:    */   
/* 707:    */   public void fixupNodeList(Graph g)
/* 708:    */   {
/* 709:743 */     Node[] nodeList = g.getNodes();
/* 710:744 */     int[] clusters = g.getClusters();
/* 711:745 */     for (int i = 0; i < nodeList.length; i++) {
/* 712:746 */       nodeList[i].cluster = clusters[i];
/* 713:    */     }
/* 714:    */   }
/* 715:    */   
/* 716:    */   public void generateOutput(Graph g)
/* 717:    */     throws IOException
/* 718:    */   {
/* 719:755 */     Graph gBase = g;
/* 720:756 */     Graph gWriteGraph = g;
/* 721:758 */     while (gBase.getGraphLevel() != 0) {
/* 722:759 */       gBase = gBase.getPreviousLevelGraph();
/* 723:    */     }
/* 724:761 */     int[] clusters = gBase.getClusters();
/* 725:762 */     Node[] nodeList = gBase.getNodes();
/* 726:763 */     int nodes = nodeList.length;
/* 727:764 */     int[][] clustMatrix = new int[nodes][nodes + 1];
/* 728:    */     
/* 729:766 */     Vector edges = new Vector();
/* 730:776 */     for (int i = 0; i < nodes; i++)
/* 731:    */     {
/* 732:777 */       clustMatrix[i][0] = 0;
/* 733:778 */       nodeList[i].cluster = -1;
/* 734:    */     }
/* 735:781 */     int pos = 0;
/* 736:782 */     for (int i = 0; i < nodes; i++)
/* 737:    */     {
/* 738:783 */       int numCluster = clusters[i]; int 
/* 739:784 */         tmp121_120 = 0; int[] tmp121_119 = clustMatrix[numCluster]; int tmp125_124 = (tmp121_119[tmp121_120] + 1);tmp121_119[tmp121_120] = tmp125_124;clustMatrix[numCluster][tmp125_124] = i;
/* 740:785 */       nodeList[i].cluster = numCluster;
/* 741:    */     }
/* 742:797 */     Node[] on = gBase.getOriginalNodes();
/* 743:798 */     if ((on != null) && (on.length != nodeList.length)) {
/* 744:799 */       checkForSpecialModules(gBase.getOriginalNodes());
/* 745:    */     }
/* 746:801 */     if (!getWriteNestedLevels()) {
/* 747:802 */       genChildrenFromOneLevel(gWriteGraph);
/* 748:    */     } else {
/* 749:804 */       generateClusters(gWriteGraph);
/* 750:    */     }
/* 751:806 */     on = gBase.getOriginalNodes();
/* 752:807 */     if ((on != null) && (on.length != nodeList.length)) {
/* 753:808 */       writeSpecialModules(gBase.getOriginalNodes());
/* 754:    */     }
/* 755:812 */     writeClosing();
/* 756:    */   }
/* 757:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.TXTGraphOutput
 * JD-Core Version:    0.7.0.1
 */