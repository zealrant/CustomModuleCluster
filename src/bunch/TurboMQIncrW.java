/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import bunch.stats.StatsManager;
/*   4:    */ 
/*   5:    */ public class TurboMQIncrW
/*   6:    */   implements ObjectiveFunctionCalculator
/*   7:    */ {
/*   8:    */   private Graph graph_d;
/*   9: 36 */   private static int[][] clusterMatrix_d = (int[][])null;
/*  10:    */   private Node[] nodes_x;
/*  11: 38 */   private int[] clusters_x = null;
/*  12:    */   private int numberOfNodes_d;
/*  13: 40 */   private StatsManager sm = StatsManager.getInstance();
/*  14: 42 */   private int[] muE = null;
/*  15: 43 */   private int[] epE = null;
/*  16:    */   
/*  17:    */   public void init(Graph g)
/*  18:    */   {
/*  19: 63 */     this.graph_d = g;
/*  20: 64 */     this.numberOfNodes_d = g.getNumberOfNodes();
/*  21: 65 */     this.nodes_x = g.getNodes();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public double calculate(Cluster c)
/*  25:    */   {
/*  26: 88 */     if (!c.isMoveValid())
/*  27:    */     {
/*  28: 91 */       this.clusters_x = c.getClusterNames();
/*  29: 92 */       return calcAll(c);
/*  30:    */     }
/*  31: 94 */     if (c.hasClusterNamesChanged()) {
/*  32: 95 */       this.clusters_x = c.getClusterNames();
/*  33:    */     }
/*  34: 97 */     return calcIncr(c, c.getLmEncoding());
/*  35:    */   }
/*  36:    */   
/*  37:    */   private double calcAll(Cluster c)
/*  38:    */   {
/*  39:120 */     this.sm.incrCalcAllCalcs();
/*  40:121 */     c.allocEdgeCounters();
/*  41:122 */     this.muE = c.getMuEdgeVector();
/*  42:123 */     this.epE = c.getEpsilonEdgeVector();
/*  43:125 */     for (int i = 0; i < this.nodes_x.length; i++)
/*  44:    */     {
/*  45:127 */       Node n = this.nodes_x[i];
/*  46:    */       
/*  47:129 */       int[] fe = n.dependencies;
/*  48:130 */       int[] feW = n.weights;
/*  49:131 */       int[] be = n.backEdges;
/*  50:132 */       int[] beW = n.beWeights;
/*  51:133 */       int[] cv = c.getClusterVector();
/*  52:134 */       int currentNode = n.nodeID;
/*  53:    */       
/*  54:136 */       int currentNodeCluster = cv[currentNode];
/*  55:137 */       n.cluster = cv[n.nodeID];
/*  56:139 */       for (int j = 0; j < fe.length; j++)
/*  57:    */       {
/*  58:141 */         int target = fe[j];
/*  59:142 */         int weight = feW[j];
/*  60:143 */         int targetCluster = cv[target];
/*  61:145 */         if (targetCluster == currentNodeCluster)
/*  62:    */         {
/*  63:146 */           this.muE[currentNodeCluster] += weight;
/*  64:    */         }
/*  65:    */         else
/*  66:    */         {
/*  67:149 */           this.epE[currentNodeCluster] += weight;
/*  68:150 */           this.epE[targetCluster] += weight;
/*  69:    */         }
/*  70:    */       }
/*  71:    */     }
/*  72:155 */     double MQ = 0.0D;
/*  73:156 */     for (int k = 0; k < this.clusters_x.length; k++)
/*  74:    */     {
/*  75:158 */       int currentCluster = this.clusters_x[k];
/*  76:159 */       double dMuE = this.muE[currentCluster];
/*  77:160 */       double dEpE = this.epE[currentCluster];
/*  78:162 */       if (dMuE + dEpE > 0.0D) {
/*  79:164 */         MQ += 2.0D * dMuE / (2.0D * dMuE + dEpE);
/*  80:    */       }
/*  81:    */     }
/*  82:169 */     return MQ;
/*  83:    */   }
/*  84:    */   
/*  85:    */   private double calcIncr(Cluster c, int[] lastMv)
/*  86:    */   {
/*  87:175 */     this.sm.incrCalcIncrCalcs();
/*  88:176 */     this.muE = c.getMuEdgeVector();
/*  89:177 */     this.epE = c.getEpsilonEdgeVector();
/*  90:178 */     double lastObjFn = c.getLastMvObjFn();
/*  91:    */     
/*  92:180 */     int currentNode = lastMv[0];
/*  93:181 */     int lmOrigC = lastMv[1];
/*  94:182 */     int lmNewC = lastMv[2];
/*  95:    */     
/*  96:184 */     double CFiOrig = calcCFi(lmOrigC);
/*  97:185 */     double CFiNew = calcCFi(lmNewC);
/*  98:    */     
/*  99:187 */     Node n = this.nodes_x[currentNode];
/* 100:    */     
/* 101:189 */     int[] fe = n.dependencies;
/* 102:190 */     int[] feW = n.weights;
/* 103:191 */     int[] be = n.backEdges;
/* 104:192 */     int[] beW = n.beWeights;
/* 105:193 */     int[] cv = c.getClusterVector();
/* 106:    */     
/* 107:    */ 
/* 108:196 */     int currentNodeCluster = cv[currentNode];
/* 109:197 */     n.cluster = cv[n.nodeID];
/* 110:199 */     for (int j = 0; j < fe.length; j++)
/* 111:    */     {
/* 112:201 */       int target = fe[j];
/* 113:202 */       int weight = feW[j];
/* 114:203 */       int targetCluster = cv[target];
/* 115:    */       
/* 116:205 */       Node x = this.nodes_x[target];
/* 117:206 */       String nname = x.getName();
/* 118:207 */       int nid = n.nodeID;
/* 119:209 */       if (targetCluster == lmNewC)
/* 120:    */       {
/* 121:211 */         this.muE[lmNewC] += weight;
/* 122:212 */         this.epE[lmOrigC] -= weight;
/* 123:213 */         this.epE[lmNewC] -= weight;
/* 124:    */       }
/* 125:214 */       else if (targetCluster == lmOrigC)
/* 126:    */       {
/* 127:216 */         this.muE[lmOrigC] -= weight;
/* 128:217 */         this.epE[lmNewC] += weight;
/* 129:218 */         this.epE[lmOrigC] += weight;
/* 130:    */       }
/* 131:    */       else
/* 132:    */       {
/* 133:222 */         this.epE[lmOrigC] -= weight;
/* 134:223 */         this.epE[lmNewC] += weight;
/* 135:    */       }
/* 136:    */     }
/* 137:228 */     for (int j = 0; j < be.length; j++)
/* 138:    */     {
/* 139:230 */       int target = be[j];
/* 140:231 */       int bWeight = beW[j];
/* 141:232 */       int targetCluster = cv[target];
/* 142:234 */       if (targetCluster == lmNewC)
/* 143:    */       {
/* 144:236 */         this.muE[lmNewC] += bWeight;
/* 145:237 */         this.epE[lmNewC] -= bWeight;
/* 146:238 */         this.epE[lmOrigC] -= bWeight;
/* 147:    */       }
/* 148:239 */       else if (targetCluster == lmOrigC)
/* 149:    */       {
/* 150:241 */         this.muE[lmOrigC] -= bWeight;
/* 151:242 */         this.epE[lmOrigC] += bWeight;
/* 152:243 */         this.epE[lmNewC] += bWeight;
/* 153:    */       }
/* 154:    */       else
/* 155:    */       {
/* 156:246 */         this.epE[lmOrigC] -= bWeight;
/* 157:247 */         this.epE[lmNewC] += bWeight;
/* 158:    */       }
/* 159:    */     }
/* 160:252 */     double newCFiOrig = calcCFi(lmOrigC);
/* 161:253 */     double newCFiNew = calcCFi(lmNewC);
/* 162:    */     
/* 163:255 */     double MQ = c.getLastMvObjFn();
/* 164:    */     
/* 165:257 */     MQ = MQ - CFiOrig - CFiNew + newCFiOrig + newCFiNew;
/* 166:    */     
/* 167:    */ 
/* 168:260 */     return MQ;
/* 169:    */   }
/* 170:    */   
/* 171:    */   private double calcCFi(int c)
/* 172:    */   {
/* 173:265 */     double dMuE = this.muE[c];
/* 174:266 */     double dEpE = this.epE[c];
/* 175:268 */     if (dMuE + dEpE > 0.0D) {
/* 176:269 */       return 2.0D * dMuE / (2.0D * dMuE + dEpE);
/* 177:    */     }
/* 178:271 */     return 0.0D;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void calculate()
/* 182:    */   {
/* 183:282 */     int k = 0;
/* 184:283 */     double intra = 0.0D;
/* 185:284 */     double inter = 0.0D;
/* 186:285 */     double objTalley = 0.0D;
/* 187:    */     
/* 188:287 */     this.clusters_x = this.graph_d.getClusters();
/* 189:289 */     if (clusterMatrix_d.length != this.numberOfNodes_d) {
/* 190:290 */       clusterMatrix_d = (int[][])null;
/* 191:    */     }
/* 192:291 */     if (clusterMatrix_d == null) {
/* 193:292 */       clusterMatrix_d = new int[this.numberOfNodes_d][this.numberOfNodes_d + 1];
/* 194:    */     }
/* 195:294 */     for (int i = 0; i < this.numberOfNodes_d; i++)
/* 196:    */     {
/* 197:295 */       clusterMatrix_d[i][0] = 0;
/* 198:296 */       this.nodes_x[i].cluster = -1;
/* 199:    */     }
/* 200:299 */     int pos = 0;
/* 201:300 */     for (int i = 0; i < this.numberOfNodes_d; i++)
/* 202:    */     {
/* 203:301 */       int numCluster = this.clusters_x[i]; int 
/* 204:302 */         tmp137_136 = 0; int[] tmp137_135 = clusterMatrix_d[numCluster]; int tmp141_140 = (tmp137_135[tmp137_136] + 1);tmp137_135[tmp137_136] = tmp141_140;clusterMatrix_d[numCluster][tmp141_140] = i;
/* 205:303 */       this.nodes_x[i].cluster = numCluster;
/* 206:    */     }
/* 207:305 */     double dDebug = this.graph_d.getObjectiveFunctionValue();
/* 208:307 */     for (int i = 0; i < clusterMatrix_d.length; i++) {
/* 209:308 */       if (clusterMatrix_d[i][0] > 0)
/* 210:    */       {
/* 211:309 */         int[] clust = clusterMatrix_d[i];
/* 212:310 */         objTalley += calculateIntradependenciesValue(clust, i);
/* 213:311 */         k++;
/* 214:    */       }
/* 215:    */     }
/* 216:315 */     this.graph_d.setIntradependenciesValue(0.0D);
/* 217:316 */     this.graph_d.setInterdependenciesValue(0.0D);
/* 218:317 */     this.graph_d.setObjectiveFunctionValue(objTalley);
/* 219:    */   }
/* 220:    */   
/* 221:    */   public double calculateIntradependenciesValue(int[] c, int numCluster)
/* 222:    */   {
/* 223:328 */     double intradep = 0.0D;
/* 224:329 */     double intraEdges = 0.0D;
/* 225:330 */     double interEdges = 0.0D;
/* 226:331 */     double exitEdges = 0.0D;
/* 227:332 */     int k = 0;
/* 228:333 */     for (int i = 1; i <= c[0]; i++)
/* 229:    */     {
/* 230:334 */       Node node = this.nodes_x[c[i]];
/* 231:335 */       k++;
/* 232:336 */       int[] c2 = node.dependencies;
/* 233:337 */       int[] w = node.weights;
/* 234:339 */       if (c2 != null) {
/* 235:340 */         for (int j = 0; j < c2.length; j++) {
/* 236:341 */           if (this.nodes_x[c2[j]].cluster == numCluster)
/* 237:    */           {
/* 238:345 */             intradep += w[j];
/* 239:346 */             intraEdges += 1.0D;
/* 240:    */           }
/* 241:    */           else
/* 242:    */           {
/* 243:350 */             exitEdges += w[j];
/* 244:351 */             interEdges += 1.0D;
/* 245:    */           }
/* 246:    */         }
/* 247:    */       }
/* 248:    */     }
/* 249:357 */     if ((k == 0) || (k == 1)) {
/* 250:358 */       k = 1;
/* 251:    */     } else {
/* 252:360 */       k *= (k - 1);
/* 253:    */     }
/* 254:367 */     double objValue = 0.0D;
/* 255:377 */     if (exitEdges + intradep == 0.0D) {
/* 256:378 */       objValue = 0.0D;
/* 257:    */     } else {
/* 258:380 */       objValue = intradep / (intradep + exitEdges);
/* 259:    */     }
/* 260:386 */     return objValue;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public double calculateInterdependenciesValue(int[] c1, int[] c2, int nc1, int nc2)
/* 264:    */   {
/* 265:397 */     double interdep = 0.0D;
/* 266:398 */     for (int i = 1; i <= c1[0]; i++)
/* 267:    */     {
/* 268:399 */       int[] ca = this.nodes_x[c1[i]].dependencies;
/* 269:400 */       int[] w = this.nodes_x[c1[i]].weights;
/* 270:402 */       if (ca != null) {
/* 271:403 */         for (int j = 0; j < ca.length; j++) {
/* 272:406 */           if (this.nodes_x[ca[j]].cluster == nc2) {
/* 273:407 */             interdep += w[j];
/* 274:    */           }
/* 275:    */         }
/* 276:    */       }
/* 277:    */     }
/* 278:413 */     for (int i = 1; i <= c2[0]; i++)
/* 279:    */     {
/* 280:414 */       int[] ca = this.nodes_x[c2[i]].dependencies;
/* 281:415 */       int[] w = this.nodes_x[c2[i]].weights;
/* 282:417 */       if (ca != null) {
/* 283:418 */         for (int j = 0; j < ca.length; j++) {
/* 284:421 */           if (this.nodes_x[ca[j]].cluster == nc1) {
/* 285:422 */             interdep += w[j];
/* 286:    */           }
/* 287:    */         }
/* 288:    */       }
/* 289:    */     }
/* 290:427 */     interdep /= 2.0D * c1[0] * c2[0];
/* 291:428 */     return interdep;
/* 292:    */   }
/* 293:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.TurboMQIncrW
 * JD-Core Version:    0.7.0.1
 */