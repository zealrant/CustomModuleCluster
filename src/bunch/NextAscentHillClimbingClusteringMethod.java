/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import bunch.util.BunchUtilities;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.util.Random;
/*   6:    */ 
/*   7:    */ public class NextAscentHillClimbingClusteringMethod
/*   8:    */   extends GenericHillClimbingClusteringMethod
/*   9:    */ {
/*  10:    */   private Random random_d;
/*  11:    */   
/*  12:    */   public NextAscentHillClimbingClusteringMethod()
/*  13:    */   {
/*  14: 72 */     this.random_d = new Random(System.currentTimeMillis());
/*  15:    */   }
/*  16:    */   
/*  17:    */   protected Cluster getLocalMaxGraph(Cluster c)
/*  18:    */   {
/*  19: 79 */     if (c == null) {
/*  20: 79 */       return null;
/*  21:    */     }
/*  22: 83 */     SATechnique saAlg = ((NAHCConfiguration)this.configuration_d).getSATechnique();
/*  23: 84 */     int iPct = ((NAHCConfiguration)this.configuration_d).getRandomizePct();
/*  24:    */     
/*  25:    */ 
/*  26:    */ 
/*  27:    */ 
/*  28: 89 */     double dPct = iPct / 100.0D;
/*  29:    */     
/*  30: 91 */     boolean acceptSAMove = false;
/*  31:    */     
/*  32: 93 */     Cluster maxC = c.cloneCluster();
/*  33: 94 */     Cluster intermC = c.cloneCluster();
/*  34:    */     
/*  35:    */ 
/*  36: 97 */     double maxOF = maxC.getObjFnValue();
/*  37: 98 */     double originalMax = maxOF;
/*  38:    */     
/*  39:100 */     int[] clustNames = c.getClusterNames();
/*  40:101 */     int[] clusters = c.getClusterVector();
/*  41:102 */     int[] maxClust = maxC.getClusterVector();
/*  42:103 */     boolean[] locks = c.getLocks();
/*  43:    */     
/*  44:105 */     long maxPartitionsToExamine = clusters.length * clustNames.length;
/*  45:106 */     int currClustersExamined = 0;
/*  46:107 */     double evalPct = ((NAHCConfiguration)this.configuration_d).getMinPctToConsider() / 100.0D;
/*  47:108 */     double partitionsToExamine =  (maxPartitionsToExamine * evalPct);
/*  48:    */     
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:113 */     int[] rndClustNameOrdering = new int[clustNames.length];
/*  53:114 */     int[] rndClustOrdering = new int[clusters.length];
/*  54:116 */     for (int i = 0; i < rndClustNameOrdering.length; i++) {
/*  55:117 */       rndClustNameOrdering[i] = i;
/*  56:    */     }
/*  57:119 */     for (int i = 0; i < rndClustOrdering.length; i++) {
/*  58:120 */       rndClustOrdering[i] = i;
/*  59:    */     }
/*  60:124 */     int rndFreq = (int)(dPct * (rndClustOrdering.length / 2.0D));
/*  61:127 */     for (int i = 0; i < rndFreq; i++)
/*  62:    */     {
/*  63:128 */       int pos1 = (int)(this.random_d.nextFloat() * (rndClustOrdering.length - 1));
/*  64:129 */       int pos2 = (int)(this.random_d.nextFloat() * (rndClustOrdering.length - 1));
/*  65:130 */       int tmp = rndClustOrdering[pos1];
/*  66:131 */       rndClustOrdering[pos1] = rndClustOrdering[pos2];
/*  67:132 */       rndClustOrdering[pos2] = tmp;
/*  68:    */     }
/*  69:135 */     rndFreq = (int)(dPct * (rndClustNameOrdering.length / 2.0D));
/*  70:137 */     for (int i = 0; i < rndFreq; i++)
/*  71:    */     {
/*  72:138 */       int pos1 = (int)(this.random_d.nextFloat() * (rndClustNameOrdering.length - 1));
/*  73:139 */       int pos2 = (int)(this.random_d.nextFloat() * (rndClustNameOrdering.length - 1));
/*  74:140 */       int tmp = rndClustNameOrdering[pos1];
/*  75:141 */       rndClustNameOrdering[pos1] = rndClustNameOrdering[pos2];
/*  76:142 */       rndClustNameOrdering[pos2] = tmp;
/*  77:    */     }
/*  78:161 */     boolean foundbetter = false;
/*  79:    */     try
/*  80:    */     {
/*  81:170 */       for (int i = 0; i < clusters.length; i++)
/*  82:    */       {
/*  83:172 */         int currNode = rndClustOrdering[i];
/*  84:173 */         int currClust = clusters[currNode];
/*  85:174 */         int tmpClust = currClust;
/*  86:177 */         for (int j = 0; j < clustNames.length; j++) {
/*  87:179 */           if ((locks[currNode] == false) && (clustNames[rndClustNameOrdering[j]] != currClust))
/*  88:    */           {
/*  89:181 */             currClustersExamined++;
/*  90:182 */             if ((foundbetter) && (currClustersExamined > partitionsToExamine))
/*  91:    */             {
/*  92:184 */               if (saAlg != null) {
/*  93:185 */                 saAlg.changeTemp(null);
/*  94:    */               }
/*  95:187 */               c.copyFromCluster(maxC);
/*  96:188 */               c.incrDepth();
/*  97:189 */               c.setConverged(false);
/*  98:    */               
/*  99:    */ 
/* 100:192 */               return c;
/* 101:    */             }
/* 102:196 */             c.relocate(currNode, clustNames[rndClustNameOrdering[j]]);
/* 103:198 */             if (saAlg != null)
/* 104:    */             {
/* 105:200 */               double dMQ = maxOF - c.getObjFnValue();
/* 106:202 */               if (dMQ < 0.0D) {
/* 107:203 */                 acceptSAMove = saAlg.accept(dMQ);
/* 108:    */               }
/* 109:    */             }
/* 110:206 */             if ((BunchUtilities.compareGreater(c.getObjFnValue(), maxOF)) || (acceptSAMove))
/* 111:    */             {
/* 112:208 */               maxC.copyFromCluster(c);
/* 113:    */               
/* 114:210 */               maxOF = c.getObjFnValue();
/* 115:    */               
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:217 */               foundbetter = true;
/* 122:222 */               if ((currClustersExamined > partitionsToExamine) || (acceptSAMove))
/* 123:    */               {
/* 124:225 */                 if (saAlg != null) {
/* 125:226 */                   saAlg.changeTemp(null);
/* 126:    */                 }
/* 127:228 */                 c.copyFromCluster(maxC);
/* 128:229 */                 c.incrDepth();
/* 129:230 */                 c.setConverged(false);
/* 130:    */                 
/* 131:    */ 
/* 132:233 */                 return c;
/* 133:    */               }
/* 134:    */             }
/* 135:    */           }
/* 136:    */         }
/* 137:249 */         c.relocate(currNode, currClust);
/* 138:    */       }
/* 139:    */     }
/* 140:    */     catch (Exception ex)
/* 141:    */     {
/* 142:254 */       System.out.println(ex.toString());
/* 143:    */     }
/* 144:257 */     if (!BunchUtilities.compareGreater(maxOF, originalMax))
/* 145:    */     {
/* 146:258 */       Node[] nodes = c.getGraph().getNodes();
/* 147:259 */       int newClusterID = c.allocateNewCluster();
/* 148:261 */       for (int i = 0; i < clusters.length; i++)
/* 149:    */       {
/* 150:262 */         int currNode = rndClustOrdering[i];
/* 151:263 */         int currClust = clusters[currNode];
/* 152:    */         
/* 153:265 */         c.relocate(currNode, newClusterID);
/* 154:266 */         int[] edges = nodes[currNode].getDependencies();
/* 155:268 */         for (int j = 0; j < edges.length; j++)
/* 156:    */         {
/* 157:270 */           int otherNode = edges[j];
/* 158:271 */           if ((locks[currNode] == false) && (locks[otherNode] == false))
/* 159:    */           {
/* 160:272 */             int otherNodeCluster = clusters[otherNode];
/* 161:273 */             c.relocate(otherNode, newClusterID);
/* 162:275 */             if (BunchUtilities.compareGreater(c.getObjFnValue(), maxOF))
/* 163:    */             {
/* 164:276 */               maxC.copyFromCluster(c);
/* 165:277 */               maxOF = c.getObjFnValue();
/* 166:278 */               c.copyFromCluster(maxC);
/* 167:279 */               c.incrDepth();
/* 168:280 */               c.setConverged(false);
/* 169:    */               
/* 170:282 */               return c;
/* 171:    */             }
/* 172:284 */             c.relocate(otherNode, otherNodeCluster);
/* 173:    */           }
/* 174:    */         }
/* 175:287 */         c.relocate(currNode, currClust);
/* 176:    */       }
/* 177:289 */       c.removeNewCluster(newClusterID);
/* 178:    */     }
/* 179:295 */     if (saAlg != null) {
/* 180:296 */       saAlg.changeTemp(null);
/* 181:    */     }
/* 182:298 */     if (BunchUtilities.compareGreater(maxOF, originalMax))
/* 183:    */     {
/* 184:299 */       c.copyFromCluster(maxC);
/* 185:300 */       c.incrDepth();
/* 186:    */     }
/* 187:    */     else
/* 188:    */     {
/* 189:304 */       c.setConverged(true);
/* 190:    */     }
/* 191:310 */     return c;
/* 192:    */   }
/* 193:    */   
/* 194:    */   protected Graph getLocalMaxGraph(Graph g)
/* 195:    */   {
/* 196:317 */     double maxOF = g.getObjectiveFunctionValue();
/* 197:    */     
/* 198:319 */     int[] clustNames = null;
/* 199:320 */     if (g.hasDoubleLocks()) {
/* 200:321 */       clustNames = g.getUnlockedClusterNames();
/* 201:    */     } else {
/* 202:324 */       clustNames = g.getClusterNames();
/* 203:    */     }
/* 204:326 */     int[] clusters = g.getClusters();
/* 205:327 */     int[] ranClust = new int[clusters.length];
/* 206:328 */     boolean[] locks = g.getLocks();
/* 207:330 */     for (int i = 0; i < ranClust.length; i++) {
/* 208:331 */       ranClust[i] = i;
/* 209:    */     }
/* 210:335 */     for (int i = 0; i < ranClust.length / 2; i++)
/* 211:    */     {
/* 212:336 */       int pos1 = (int)(this.random_d.nextFloat() * (ranClust.length - 1));
/* 213:337 */       int pos2 = (int)(this.random_d.nextFloat() * (ranClust.length - 1));
/* 214:338 */       int tmp = ranClust[pos1];
/* 215:339 */       ranClust[pos1] = ranClust[pos2];
/* 216:340 */       ranClust[pos2] = tmp;
/* 217:    */     }
/* 218:343 */     int freepos = 0;int freeval = 0;
/* 219:344 */     boolean foundbetter = false;
/* 220:345 */     int num = 0;
/* 221:347 */     while ((!foundbetter) && (num < ranClust.length))
/* 222:    */     {
/* 223:350 */       for (int i = 0; i < clustNames.length / 2; i++)
/* 224:    */       {
/* 225:351 */         int pos1 = (int)(this.random_d.nextFloat() * (clustNames.length - 1));
/* 226:352 */         int pos2 = (int)(this.random_d.nextFloat() * (clustNames.length - 1));
/* 227:353 */         int tmp = clustNames[pos1];
/* 228:354 */         clustNames[pos1] = clustNames[pos2];
/* 229:355 */         clustNames[pos2] = tmp;
/* 230:    */       }
/* 231:363 */       int j = 0;
/* 232:364 */       int i = ranClust[(num++)];
/* 233:365 */       int currClust = clusters[i];
/* 234:367 */       for (; j < clustNames.length; j++) {
/* 235:368 */         if (clustNames[j] == currClust)
/* 236:    */         {
/* 237:369 */           freepos = j;
/* 238:370 */           freeval = clustNames[j];
/* 239:371 */           if (clustNames.length >= clusters.length) {
/* 240:    */             break;
/* 241:    */           }
/* 242:372 */           clustNames[j] = g.findFreeCluster(clustNames); break;
/* 243:    */         }
/* 244:    */       }
/* 245:378 */       for (j = 0; j < clustNames.length; j++) {
/* 246:380 */         if (locks[i] == false)
/* 247:    */         {
/* 248:381 */           clusters[i] = clustNames[j];
/* 249:382 */           g.calculateObjectiveFunctionValue();
/* 250:383 */           if (g.getObjectiveFunctionValue() > maxOF)
/* 251:    */           {
/* 252:384 */             foundbetter = true;
/* 253:385 */             break;
/* 254:    */           }
/* 255:    */         }
/* 256:    */       }
/* 257:389 */       if (foundbetter) {
/* 258:    */         break;
/* 259:    */       }
/* 260:392 */       clusters[i] = currClust;
/* 261:393 */       clustNames[freepos] = freeval;
/* 262:    */     }
/* 263:397 */     if (!foundbetter) {
/* 264:398 */       g.setMaximum(true);
/* 265:    */     }
/* 266:400 */     g.calculateObjectiveFunctionValue();
/* 267:401 */     return g;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public Configuration getConfiguration()
/* 271:    */   {
/* 272:408 */     boolean reconf = false;
/* 273:409 */     if (this.configuration_d == null)
/* 274:    */     {
/* 275:410 */       this.configuration_d = new NAHCConfiguration();
/* 276:411 */       reconf = true;
/* 277:    */     }
/* 278:414 */     NAHCConfiguration hc = (NAHCConfiguration)this.configuration_d;
/* 279:416 */     if (reconf)
/* 280:    */     {
/* 281:417 */       hc.setThreshold(1.0D);
/* 282:418 */       hc.setNumOfIterations(1);
/* 283:419 */       hc.setPopulationSize(1);
/* 284:420 */       hc.setMinPctToConsider(0);
/* 285:421 */       hc.setRandomizePct(100);
/* 286:422 */       hc.setSATechnique(null);
/* 287:    */     }
/* 288:425 */     return hc;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public String getConfigurationDialogName()
/* 292:    */   {
/* 293:432 */     return "bunch.NAHCClusteringConfigurationDialog";
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setDefaultConfiguration()
/* 297:    */   {
/* 298:438 */     NAHCConfiguration hc = (NAHCConfiguration)getConfiguration();
/* 299:    */     
/* 300:440 */     hc.setThreshold(1.0D);
/* 301:441 */     hc.setNumOfIterations(1);
/* 302:442 */     hc.setPopulationSize(1);
/* 303:443 */     hc.setSATechnique(null);
/* 304:444 */     hc.setMinPctToConsider(0);
/* 305:445 */     hc.setRandomizePct(100);
/* 306:446 */     setConfiguration(hc);
/* 307:    */   }
/* 308:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.NextAscentHillClimbingClusteringMethod
 * JD-Core Version:    0.7.0.1
 */