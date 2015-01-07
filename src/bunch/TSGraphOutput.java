/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.io.BufferedWriter;
/*   4:    */ import java.io.FileWriter;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.Vector;
/*   7:    */ 
/*   8:    */ public class TSGraphOutput
/*   9:    */   extends GraphOutput
/*  10:    */ {
/*  11:    */   public void write()
/*  12:    */   {
/*  13: 36 */     Vector edges = new Vector();
/*  14: 37 */     int[] clusters = this.graph_d.getClusters();
/*  15: 38 */     Node[] nodeList = this.graph_d.getNodes();
/*  16: 39 */     int nodes = nodeList.length;
/*  17:    */     try
/*  18:    */     {
/*  19: 42 */       this.writer_d = new BufferedWriter(new FileWriter(getCurrentName() + ".nav"));
/*  20:    */       
/*  21:    */ 
/*  22: 45 */       int pos = 0;
/*  23: 46 */       int minClusterNum = 2147483647;
/*  24: 47 */       int maxClusterNum = 0;
/*  25: 49 */       for (int i = 0; i < nodes; i++)
/*  26:    */       {
/*  27: 50 */         int numCluster = clusters[i];
/*  28: 51 */         if (numCluster < minClusterNum) {
/*  29: 52 */           minClusterNum = numCluster;
/*  30: 53 */         } else if (numCluster > maxClusterNum) {
/*  31: 54 */           maxClusterNum = numCluster;
/*  32:    */         }
/*  33: 55 */         nodeList[i].cluster = -1;
/*  34:    */       }
/*  35: 58 */       int clusterNum = maxClusterNum - minClusterNum;
/*  36: 59 */       int[][] clustersMatrix = new int[clusterNum + 1][nodes + 1];
/*  37: 60 */       for (int i = 0; i <= clusterNum; i++) {
/*  38: 61 */         clustersMatrix[i][0] = 0;
/*  39:    */       }
/*  40: 64 */       for (int i = 0; i < nodes; i++)
/*  41:    */       {
/*  42: 65 */         int numCluster = clusters[i];
/*  43: 66 */         numCluster -= minClusterNum; int 
/*  44:    */         
/*  45: 68 */           tmp212_211 = 0; int[] tmp212_210 = clustersMatrix[numCluster]; int tmp216_215 = (tmp212_210[tmp212_211] + 1);tmp212_210[tmp212_211] = tmp216_215;clustersMatrix[numCluster][tmp216_215] = i;
/*  46: 69 */         nodeList[i].cluster = numCluster;
/*  47:    */       }
/*  48: 72 */       Node[] originalNodes = this.graph_d.getOriginalNodes();
/*  49: 73 */       boolean hasSuppliers = false;
/*  50: 74 */       boolean hasClients = false;
/*  51: 75 */       boolean hasLibraries = false;
/*  52: 76 */       boolean hasCentrals = false;
/*  53: 78 */       if ((originalNodes != null) && (originalNodes.length != nodeList.length)) {
/*  54: 79 */         for (int i = 0; i < originalNodes.length; i++)
/*  55:    */         {
/*  56: 80 */           if ((!hasSuppliers) && (originalNodes[i].getType() == 2)) {
/*  57: 81 */             hasSuppliers = true;
/*  58:    */           }
/*  59: 83 */           if ((!hasClients) && (originalNodes[i].getType() == 1)) {
/*  60: 84 */             hasClients = true;
/*  61:    */           }
/*  62: 86 */           if ((!hasCentrals) && (originalNodes[i].getType() == 3)) {
/*  63: 87 */             hasCentrals = true;
/*  64:    */           }
/*  65: 89 */           if ((!hasLibraries) && (originalNodes[i].getType() == 4)) {
/*  66: 90 */             hasLibraries = true;
/*  67:    */           }
/*  68:    */         }
/*  69:    */       }
/*  70: 96 */       this.writer_d.write("// ------------------------------------------------------------ \n");
/*  71: 97 */       this.writer_d.write("// created with bunch v2 */\n");
/*  72: 98 */       this.writer_d.write("// Objective Function value = " + this.graph_d.getObjectiveFunctionValue() + "\n");
/*  73: 99 */       this.writer_d.write("// ------------------------------------------------------------ \n\n");
/*  74:100 */       this.writer_d.write("// Graph Layout Toolkit\n");
/*  75:101 */       this.writer_d.write("Navigation file\n");
/*  76:102 */       this.writer_d.write("// topDown\n");
/*  77:103 */       this.writer_d.write("FALSE\n");
/*  78:104 */       this.writer_d.write("// needsRecalculation\n");
/*  79:105 */       this.writer_d.write("TRUE\n");
/*  80:106 */       this.writer_d.write("// needsRelabeling\n");
/*  81:107 */       this.writer_d.write("FALSE\n\n");
/*  82:    */       
/*  83:    */ 
/*  84:110 */       this.writer_d.write("// Graphs\n");
/*  85:    */       
/*  86:112 */       this.writer_d.write(getBasicName() + "C.tss\n");
/*  87:113 */       FileWriter clustersFile = new FileWriter(getCurrentName() + "C.tss");
/*  88:114 */       clustersFile.write("// Circular Layout\n");
/*  89:115 */       clustersFile.write("// undirected\n");
/*  90:116 */       clustersFile.write("FALSE\n");
/*  91:117 */       clustersFile.write("// Symmetric Layout\n");
/*  92:118 */       clustersFile.write("// undirected\n");
/*  93:119 */       clustersFile.write("FALSE\n");
/*  94:120 */       clustersFile.write("// Hierarchical Layout\n");
/*  95:121 */       clustersFile.write("// undirected\n");
/*  96:122 */       clustersFile.write("FALSE\n");
/*  97:123 */       clustersFile.write("// Orthogonal Layout\n");
/*  98:124 */       clustersFile.write("// undirected\n");
/*  99:125 */       clustersFile.write("FALSE\n");
/* 100:126 */       clustersFile.write("// Digraph\n");
/* 101:127 */       clustersFile.write("// lastLayoutStyle\n");
/* 102:128 */       clustersFile.write("ORTHOGONAL\n");
/* 103:129 */       clustersFile.write("// layoutStyle\n");
/* 104:130 */       clustersFile.write("ORTHOGONAL\n");
/* 105:131 */       clustersFile.write("\n");
/* 106:132 */       clustersFile.write("\n");
/* 107:133 */       clustersFile.write("// Editor Digraph\n");
/* 108:134 */       clustersFile.write("Version 3.0\n");
/* 109:135 */       clustersFile.write(getBasicName() + "\n");
/* 110:136 */       clustersFile.write("\n");
/* 111:137 */       clustersFile.write("// Nodes\n");
/* 112:140 */       if ((originalNodes != null) && (originalNodes.length != nodeList.length))
/* 113:    */       {
/* 114:142 */         if (hasLibraries)
/* 115:    */         {
/* 116:145 */           this.writer_d.write(getBasicName() + "_lib.tss\n");
/* 117:146 */           clustersFile.write("// Node\n");
/* 118:147 */           clustersFile.write("// name\n");
/* 119:148 */           clustersFile.write("Libraries\n");
/* 120:149 */           clustersFile.write("// Editor Node\n");
/* 121:150 */           clustersFile.write("Libraries\n");
/* 122:151 */           clustersFile.write("TSEShapeNodeView 2\n");
/* 123:152 */           clustersFile.write("0x0\n");
/* 124:153 */           clustersFile.write("0x7480F8\n");
/* 125:154 */           clustersFile.write("2\n");
/* 126:155 */           clustersFile.write("// width\n");
/* 127:156 */           clustersFile.write("140 \n");
/* 128:157 */           clustersFile.write("// height\n");
/* 129:158 */           clustersFile.write("40\n");
/* 130:    */           
/* 131:160 */           FileWriter libsFile = new FileWriter(getCurrentName() + "_lib.tss");
/* 132:161 */           libsFile.write("// Hierarchical Layout\n");
/* 133:162 */           libsFile.write("// magnifiedSpacing\n");
/* 134:163 */           libsFile.write("FALSE\n");
/* 135:164 */           libsFile.write("// undirected\n");
/* 136:165 */           libsFile.write("TRUE\n");
/* 137:166 */           libsFile.write("// Digraph\n");
/* 138:167 */           libsFile.write("// lastLayoutStyle\n");
/* 139:168 */           libsFile.write("HIERARCHICAL\n");
/* 140:169 */           libsFile.write("// layoutStyle\n");
/* 141:170 */           libsFile.write("HIERARCHICAL\n");
/* 142:171 */           libsFile.write("\n");
/* 143:172 */           libsFile.write("// Editor Digraph\n");
/* 144:173 */           libsFile.write("Version 3.0\n");
/* 145:174 */           libsFile.write(" \n\n");
/* 146:175 */           libsFile.write("// Nodes\n");
/* 147:177 */           for (int i = 0; i < originalNodes.length; i++) {
/* 148:178 */             if (originalNodes[i].getType() == 4)
/* 149:    */             {
/* 150:179 */               libsFile.write("// Node\n");
/* 151:180 */               libsFile.write("// name\n");
/* 152:181 */               libsFile.write(originalNodes[i].getName() + "\n");
/* 153:182 */               libsFile.write("// Editor Node\n");
/* 154:183 */               libsFile.write(originalNodes[i].getName() + "\n");
/* 155:184 */               libsFile.write("TSEShapeNodeView 4\n");
/* 156:185 */               libsFile.write("0x0\n");
/* 157:186 */               libsFile.write("0x4958E9\n");
/* 158:187 */               libsFile.write("2\n");
/* 159:188 */               libsFile.write("// width\n");
/* 160:189 */               libsFile.write(originalNodes[i].getName().length() * 10 + "\n");
/* 161:190 */               libsFile.write("// height\n");
/* 162:191 */               libsFile.write("25\n");
/* 163:    */             }
/* 164:    */           }
/* 165:194 */           libsFile.close();
/* 166:    */         }
/* 167:198 */         if (hasSuppliers)
/* 168:    */         {
/* 169:201 */           this.writer_d.write(getBasicName() + "_sup.tss\n");
/* 170:202 */           clustersFile.write("// Node\n");
/* 171:203 */           clustersFile.write("// name\n");
/* 172:204 */           clustersFile.write("Suppliers\n");
/* 173:205 */           clustersFile.write("// Editor Node\n");
/* 174:206 */           clustersFile.write("Suppliers\n");
/* 175:207 */           clustersFile.write("TSEShapeNodeView 2\n");
/* 176:208 */           clustersFile.write("0x0\n");
/* 177:209 */           clustersFile.write("0x7480F8\n");
/* 178:210 */           clustersFile.write("2\n");
/* 179:211 */           clustersFile.write("// width\n");
/* 180:212 */           clustersFile.write("130 \n");
/* 181:213 */           clustersFile.write("// height\n");
/* 182:214 */           clustersFile.write("40\n");
/* 183:    */           
/* 184:216 */           FileWriter supsFile = new FileWriter(getCurrentName() + "_sup.tss");
/* 185:217 */           supsFile.write("// Hierarchical Layout\n");
/* 186:218 */           supsFile.write("// magnifiedSpacing\n");
/* 187:219 */           supsFile.write("FALSE\n");
/* 188:220 */           supsFile.write("// undirected\n");
/* 189:221 */           supsFile.write("TRUE\n");
/* 190:222 */           supsFile.write("// Digraph\n");
/* 191:223 */           supsFile.write("// lastLayoutStyle\n");
/* 192:224 */           supsFile.write("HIERARCHICAL\n");
/* 193:225 */           supsFile.write("// layoutStyle\n");
/* 194:226 */           supsFile.write("HIERARCHICAL\n");
/* 195:227 */           supsFile.write("\n");
/* 196:228 */           supsFile.write("// Editor Digraph\n");
/* 197:229 */           supsFile.write("Version 3.0\n");
/* 198:230 */           supsFile.write(" \n\n");
/* 199:231 */           supsFile.write("// Nodes\n");
/* 200:233 */           for (int i = 0; i < originalNodes.length; i++) {
/* 201:234 */             if (originalNodes[i].getType() == 2)
/* 202:    */             {
/* 203:235 */               supsFile.write("// Node\n");
/* 204:236 */               supsFile.write("// name\n");
/* 205:237 */               supsFile.write(originalNodes[i].getName() + "\n");
/* 206:238 */               supsFile.write("// Editor Node\n");
/* 207:239 */               supsFile.write(originalNodes[i].getName() + "\n");
/* 208:240 */               supsFile.write("TSEShapeNodeView 4\n");
/* 209:241 */               supsFile.write("0x0\n");
/* 210:242 */               supsFile.write("0x4958E9\n");
/* 211:243 */               supsFile.write("2\n");
/* 212:244 */               supsFile.write("// width\n");
/* 213:245 */               supsFile.write(originalNodes[i].getName().length() * 10 + "\n");
/* 214:246 */               supsFile.write("// height\n");
/* 215:247 */               supsFile.write("30\n");
/* 216:    */             }
/* 217:    */           }
/* 218:250 */           supsFile.close();
/* 219:    */         }
/* 220:253 */         if (hasClients)
/* 221:    */         {
/* 222:256 */           this.writer_d.write(getBasicName() + "_cli.tss\n");
/* 223:257 */           clustersFile.write("// Node\n");
/* 224:258 */           clustersFile.write("// name\n");
/* 225:259 */           clustersFile.write("Clients\n");
/* 226:260 */           clustersFile.write("// Editor Node\n");
/* 227:261 */           clustersFile.write("Clients\n");
/* 228:262 */           clustersFile.write("TSEShapeNodeView 2\n");
/* 229:263 */           clustersFile.write("0x0\n");
/* 230:264 */           clustersFile.write("0x7480F8\n");
/* 231:265 */           clustersFile.write("2\n");
/* 232:266 */           clustersFile.write("// width\n");
/* 233:267 */           clustersFile.write("130 \n");
/* 234:268 */           clustersFile.write("// height\n");
/* 235:269 */           clustersFile.write("40\n");
/* 236:    */           
/* 237:271 */           FileWriter cliFile = new FileWriter(getCurrentName() + "_cli.tss");
/* 238:272 */           cliFile.write("// Hierarchical Layout\n");
/* 239:273 */           cliFile.write("// magnifiedSpacing\n");
/* 240:274 */           cliFile.write("FALSE\n");
/* 241:275 */           cliFile.write("// undirected\n");
/* 242:276 */           cliFile.write("TRUE\n");
/* 243:277 */           cliFile.write("// Digraph\n");
/* 244:278 */           cliFile.write("// lastLayoutStyle\n");
/* 245:279 */           cliFile.write("HIERARCHICAL\n");
/* 246:280 */           cliFile.write("// layoutStyle\n");
/* 247:281 */           cliFile.write("HIERARCHICAL\n");
/* 248:282 */           cliFile.write("\n");
/* 249:283 */           cliFile.write("// Editor Digraph\n");
/* 250:284 */           cliFile.write("Version 3.0\n");
/* 251:285 */           cliFile.write(" \n\n");
/* 252:286 */           cliFile.write("// Nodes\n");
/* 253:288 */           for (int i = 0; i < originalNodes.length; i++) {
/* 254:289 */             if (originalNodes[i].getType() == 1)
/* 255:    */             {
/* 256:290 */               cliFile.write("// Node\n");
/* 257:291 */               cliFile.write("// name\n");
/* 258:292 */               cliFile.write(originalNodes[i].getName() + "\n");
/* 259:293 */               cliFile.write("// Editor Node\n");
/* 260:294 */               cliFile.write(originalNodes[i].getName() + "\n");
/* 261:295 */               cliFile.write("TSEShapeNodeView 4\n");
/* 262:296 */               cliFile.write("0x0\n");
/* 263:297 */               cliFile.write("0x4958E9\n");
/* 264:298 */               cliFile.write("2\n");
/* 265:299 */               cliFile.write("// width\n");
/* 266:300 */               cliFile.write(originalNodes[i].getName().length() * 10 + "\n");
/* 267:301 */               cliFile.write("// height\n");
/* 268:302 */               cliFile.write("30\n");
/* 269:    */             }
/* 270:    */           }
/* 271:305 */           cliFile.close();
/* 272:    */         }
/* 273:310 */         if (hasCentrals)
/* 274:    */         {
/* 275:313 */           this.writer_d.write(getBasicName() + "_cen.tss\n");
/* 276:314 */           clustersFile.write("// Node\n");
/* 277:315 */           clustersFile.write("// name\n");
/* 278:316 */           clustersFile.write("Clients/Suppliers\n");
/* 279:317 */           clustersFile.write("// Editor Node\n");
/* 280:318 */           clustersFile.write("Clients/Suppliers\n");
/* 281:319 */           clustersFile.write("TSEShapeNodeView 2\n");
/* 282:320 */           clustersFile.write("0x0\n");
/* 283:321 */           clustersFile.write("0x7480F8\n");
/* 284:322 */           clustersFile.write("2\n");
/* 285:323 */           clustersFile.write("// width\n");
/* 286:324 */           clustersFile.write("240 \n");
/* 287:325 */           clustersFile.write("// height\n");
/* 288:326 */           clustersFile.write("40\n");
/* 289:    */           
/* 290:328 */           FileWriter cenFile = new FileWriter(getCurrentName() + "_cen.tss");
/* 291:329 */           cenFile.write("// Hierarchical Layout\n");
/* 292:330 */           cenFile.write("// magnifiedSpacing\n");
/* 293:331 */           cenFile.write("FALSE\n");
/* 294:332 */           cenFile.write("// undirected\n");
/* 295:333 */           cenFile.write("TRUE\n");
/* 296:334 */           cenFile.write("// Digraph\n");
/* 297:335 */           cenFile.write("// lastLayoutStyle\n");
/* 298:336 */           cenFile.write("HIERARCHICAL\n");
/* 299:337 */           cenFile.write("// layoutStyle\n");
/* 300:338 */           cenFile.write("HIERARCHICAL\n");
/* 301:339 */           cenFile.write("\n");
/* 302:340 */           cenFile.write("// Editor Digraph\n");
/* 303:341 */           cenFile.write("Version 3.0\n");
/* 304:342 */           cenFile.write(" \n\n");
/* 305:343 */           cenFile.write("// Nodes\n");
/* 306:345 */           for (int i = 0; i < originalNodes.length; i++) {
/* 307:346 */             if (originalNodes[i].getType() == 3)
/* 308:    */             {
/* 309:347 */               cenFile.write("// Node\n");
/* 310:348 */               cenFile.write("// name\n");
/* 311:349 */               cenFile.write(originalNodes[i].getName() + "\n");
/* 312:350 */               cenFile.write("// Editor Node\n");
/* 313:351 */               cenFile.write(originalNodes[i].getName() + "\n");
/* 314:352 */               cenFile.write("TSEShapeNodeView 4\n");
/* 315:353 */               cenFile.write("0x0\n");
/* 316:354 */               cenFile.write("0x4958E9\n");
/* 317:355 */               cenFile.write("2\n");
/* 318:356 */               cenFile.write("// width\n");
/* 319:357 */               cenFile.write(originalNodes[i].getName().length() * 10 + "\n");
/* 320:358 */               cenFile.write("// height\n");
/* 321:359 */               cenFile.write("30\n");
/* 322:    */             }
/* 323:    */           }
/* 324:362 */           cenFile.close();
/* 325:    */         }
/* 326:    */       }
/* 327:369 */       int id = 0;
/* 328:370 */       int clusterIndex = 0;
/* 329:371 */       for (int i = 0; i <= clusterNum; i++) {
/* 330:372 */         if (clustersMatrix[i][0] > 0)
/* 331:    */         {
/* 332:374 */           this.writer_d.write(getBasicName() + clusterIndex + ".tss\n");
/* 333:    */           
/* 334:376 */           clustersFile.write("// Node\n");
/* 335:377 */           clustersFile.write("// name\n");
/* 336:378 */           clustersFile.write("Cluster " + clusterIndex + "\n");
/* 337:379 */           clustersFile.write("// Editor Node\n");
/* 338:380 */           clustersFile.write("Cluster " + clusterIndex + "\n");
/* 339:381 */           clustersFile.write("TSEShapeNodeView 4\n");
/* 340:382 */           clustersFile.write("0x0\n");
/* 341:383 */           clustersFile.write("0x9AC884\n");
/* 342:384 */           clustersFile.write("2\n");
/* 343:385 */           clustersFile.write("// width\n");
/* 344:386 */           clustersFile.write("120 \n");
/* 345:387 */           clustersFile.write("// height\n");
/* 346:388 */           clustersFile.write("40\n");
/* 347:    */           
/* 348:390 */           FileWriter currentFile = new FileWriter(getCurrentName() + clusterIndex + ".tss");
/* 349:391 */           currentFile.write("// Hierarchical Layout\n");
/* 350:392 */           currentFile.write("// magnifiedSpacing\n");
/* 351:393 */           currentFile.write("TRUE\n");
/* 352:394 */           currentFile.write("// undirected\n");
/* 353:395 */           currentFile.write("FALSE\n");
/* 354:396 */           currentFile.write("// Digraph\n");
/* 355:397 */           currentFile.write("// lastLayoutStyle\n");
/* 356:398 */           currentFile.write("HIERARCHICAL\n");
/* 357:399 */           currentFile.write("// layoutStyle\n");
/* 358:400 */           currentFile.write("HIERARCHICAL\n");
/* 359:401 */           currentFile.write("\n");
/* 360:402 */           currentFile.write("// Editor Digraph\n");
/* 361:403 */           currentFile.write("Version 3.0\n");
/* 362:404 */           currentFile.write(" \n\n");
/* 363:405 */           currentFile.write("// Nodes\n");
/* 364:408 */           for (int j = 1; j <= clustersMatrix[i][0]; j++)
/* 365:    */           {
/* 366:409 */             String name = nodeList[clustersMatrix[i][j]].getName();
/* 367:410 */             nodeList[clustersMatrix[i][j]].cluster = clusterIndex;
/* 368:411 */             currentFile.write("// Node\n");
/* 369:412 */             currentFile.write("// name\n");
/* 370:413 */             currentFile.write(name + "\n");
/* 371:414 */             currentFile.write("// Editor Node\n");
/* 372:415 */             currentFile.write(name + "\n");
/* 373:416 */             currentFile.write("TSEShapeNodeView 4\n");
/* 374:417 */             currentFile.write("0x0\n");
/* 375:418 */             currentFile.write("0xfbb3b3\n");
/* 376:419 */             currentFile.write("2\n");
/* 377:420 */             currentFile.write("// width\n");
/* 378:421 */             currentFile.write(name.length() * 9 + "\n");
/* 379:422 */             currentFile.write("// height\n");
/* 380:423 */             currentFile.write("20\n");
/* 381:    */           }
/* 382:428 */           currentFile.write("\n\n");
/* 383:429 */           currentFile.write("// Edges\n");
/* 384:430 */           int edgeCounter = 0;
/* 385:431 */           for (int k = 0; k < nodes; k++)
/* 386:    */           {
/* 387:432 */             int[] l = nodeList[k].dependencies;
/* 388:433 */             if (l != null) {
/* 389:434 */               for (int j = 0; j < l.length; j++) {
/* 390:435 */                 if ((nodeList[k].cluster == clusterIndex) && (clusterIndex == nodeList[l[j]].cluster))
/* 391:    */                 {
/* 392:437 */                   currentFile.write("// Edge\n");
/* 393:438 */                   currentFile.write("// name\n");
/* 394:439 */                   currentFile.write("Edge " + edgeCounter++ + "\n");
/* 395:440 */                   currentFile.write("// fromNodeName\n");
/* 396:441 */                   currentFile.write(nodeList[k].getName() + "\n");
/* 397:442 */                   currentFile.write("// toNodeName\n");
/* 398:443 */                   currentFile.write(nodeList[l[j]].getName() + "\n");
/* 399:    */                 }
/* 400:    */               }
/* 401:    */             }
/* 402:    */           }
/* 403:449 */           clusterIndex++;
/* 404:450 */           currentFile.close();
/* 405:    */         }
/* 406:    */       }
/* 407:456 */       int edgeCounter = 0;
/* 408:457 */       clustersFile.write("\n\n");
/* 409:458 */       clustersFile.write("// Edges\n");
/* 410:459 */       boolean equalEdge = false;
/* 411:460 */       for (int k = 0; k < nodes; k++)
/* 412:    */       {
/* 413:461 */         int[] l = nodeList[k].dependencies;
/* 414:462 */         if (l != null) {
/* 415:463 */           for (int j = 0; j < l.length; j++) {
/* 416:464 */             if (nodeList[k].cluster != nodeList[l[j]].cluster)
/* 417:    */             {
/* 418:465 */               Edge newEdge = new Edge(nodeList[k], nodeList[l[j]]);
/* 419:466 */               for (int h = 0; h < edges.size(); h++)
/* 420:    */               {
/* 421:467 */                 equalEdge = ((Edge)edges.elementAt(h)).equalByCluster(newEdge);
/* 422:468 */                 if (equalEdge) {
/* 423:    */                   break;
/* 424:    */                 }
/* 425:    */               }
/* 426:471 */               if (!equalEdge)
/* 427:    */               {
/* 428:472 */                 clustersFile.write("// Edge\n");
/* 429:473 */                 clustersFile.write("// name\n");
/* 430:474 */                 clustersFile.write("Cluster Edge " + edgeCounter++ + "\n");
/* 431:475 */                 clustersFile.write("// fromNodeName\n");
/* 432:476 */                 clustersFile.write("Cluster " + nodeList[k].cluster + "\n");
/* 433:477 */                 clustersFile.write("// toNodeName\n");
/* 434:478 */                 clustersFile.write("Cluster " + nodeList[l[j]].cluster + "\n");
/* 435:479 */                 edges.addElement(newEdge);
/* 436:    */               }
/* 437:    */             }
/* 438:    */           }
/* 439:    */         }
/* 440:    */       }
/* 441:486 */       clustersFile.close();
/* 442:    */       
/* 443:    */ 
/* 444:    */ 
/* 445:490 */       this.writer_d.write("// Navigation relationships\n");
/* 446:491 */       if (hasLibraries)
/* 447:    */       {
/* 448:492 */         this.writer_d.write("Libraries\n");
/* 449:493 */         this.writer_d.write(getBasicName() + "_lib.tss\n");
/* 450:    */       }
/* 451:495 */       if (hasSuppliers)
/* 452:    */       {
/* 453:496 */         this.writer_d.write("Suppliers\n");
/* 454:497 */         this.writer_d.write(getBasicName() + "_sup.tss\n");
/* 455:    */       }
/* 456:499 */       if (hasClients)
/* 457:    */       {
/* 458:500 */         this.writer_d.write("Clients\n");
/* 459:501 */         this.writer_d.write(getBasicName() + "_cli.tss\n");
/* 460:    */       }
/* 461:503 */       if (hasCentrals)
/* 462:    */       {
/* 463:504 */         this.writer_d.write("Clients/Suppliers\n");
/* 464:505 */         this.writer_d.write(getBasicName() + "_cen.tss\n");
/* 465:    */       }
/* 466:507 */       for (int i = 0; i < clusterIndex; i++)
/* 467:    */       {
/* 468:508 */         this.writer_d.write("Cluster " + i + "\n");
/* 469:509 */         this.writer_d.write(getBasicName() + i + ".tss\n");
/* 470:    */       }
/* 471:511 */       this.writer_d.close();
/* 472:    */     }
/* 473:    */     catch (IOException e)
/* 474:    */     {
/* 475:514 */       e.printStackTrace();
/* 476:    */     }
/* 477:    */   }
/* 478:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.TSGraphOutput
 * JD-Core Version:    0.7.0.1
 */