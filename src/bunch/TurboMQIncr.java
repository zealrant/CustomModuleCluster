/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ public class TurboMQIncr
/*   4:    */   implements ObjectiveFunctionCalculator
/*   5:    */ {
/*   6:    */   private Graph graph_d;
/*   7: 35 */   private static int[][] clusterMatrix_d = (int[][])null;
/*   8:    */   private Node[] nodes_x;
/*   9: 37 */   private int[] clusters_x = null;
/*  10:    */   private int numberOfNodes_d;
/*  11: 40 */   private int[] muE = null;
/*  12: 41 */   private int[] epE = null;
/*  13:    */   
/*  14:    */   public void init(Graph g)
/*  15:    */   {
/*  16: 61 */     this.graph_d = g;
/*  17: 62 */     this.numberOfNodes_d = g.getNumberOfNodes();
/*  18: 63 */     this.nodes_x = g.getNodes();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public double calculate(Cluster c)
/*  22:    */   {
/*  23: 86 */     if (!c.isMoveValid())
/*  24:    */     {
/*  25: 88 */       if (this.clusters_x == null) {
/*  26: 89 */         this.clusters_x = c.getClusterNames();
/*  27:    */       }
/*  28: 90 */       return calcAll(c);
/*  29:    */     }
/*  30: 93 */     return calcIncr(c, c.getLmEncoding());
/*  31:    */   }
/*  32:    */   
/*  33:    */   private double calcAll(Cluster c)
/*  34:    */   {
/*  35:116 */     c.allocEdgeCounters();
/*  36:117 */     this.muE = c.getMuEdgeVector();
/*  37:118 */     this.epE = c.getEpsilonEdgeVector();
/*  38:120 */     for (int i = 0; i < this.nodes_x.length; i++)
/*  39:    */     {
/*  40:122 */       Node n = this.nodes_x[i];
/*  41:    */       
/*  42:124 */       int[] fe = n.dependencies;
/*  43:125 */       int[] feW = n.weights;
/*  44:126 */       int[] be = n.backEdges;
/*  45:127 */       int[] beW = n.beWeights;
/*  46:128 */       int[] cv = c.getClusterVector();
/*  47:129 */       int currentNode = n.nodeID;
/*  48:    */       
/*  49:131 */       int currentNodeCluster = cv[currentNode];
/*  50:132 */       n.cluster = cv[n.nodeID];
/*  51:134 */       for (int j = 0; j < fe.length; j++)
/*  52:    */       {
/*  53:136 */         int target = fe[j];
/*  54:137 */         int targetCluster = cv[target];
/*  55:139 */         if (targetCluster == currentNodeCluster) {
/*  56:140 */           this.muE[currentNodeCluster] += 1;
/*  57:    */         } else {
/*  58:142 */           this.epE[currentNodeCluster] += 1;
/*  59:    */         }
/*  60:    */       }
/*  61:    */     }
/*  62:145 */     double MQ = 0.0D;
/*  63:146 */     for (int k = 0; k < this.clusters_x.length; k++)
/*  64:    */     {
/*  65:148 */       int currentCluster = this.clusters_x[k];
/*  66:149 */       double dMuE = this.muE[currentCluster];
/*  67:150 */       double dEpE = this.epE[currentCluster];
/*  68:152 */       if (dMuE + dEpE > 0.0D) {
/*  69:154 */         MQ += dMuE / (dMuE + dEpE);
/*  70:    */       }
/*  71:    */     }
/*  72:159 */     return MQ;
/*  73:    */   }
/*  74:    */   
/*  75:    */   private double calcIncr(Cluster c, int[] lastMv)
/*  76:    */   {
/*  77:166 */     this.muE = c.getMuEdgeVector();
/*  78:167 */     this.epE = c.getEpsilonEdgeVector();
/*  79:168 */     double lastObjFn = c.getLastMvObjFn();
/*  80:    */     
/*  81:170 */     int currentNode = lastMv[0];
/*  82:171 */     int lmOrigC = lastMv[1];
/*  83:172 */     int lmNewC = lastMv[2];
/*  84:    */     
/*  85:174 */     double CFiOrig = calcCFi(lmOrigC);
/*  86:175 */     double CFiNew = calcCFi(lmNewC);
/*  87:    */     
/*  88:177 */     Node n = this.nodes_x[currentNode];
/*  89:    */     
/*  90:179 */     int[] fe = n.dependencies;
/*  91:180 */     int[] feW = n.weights;
/*  92:181 */     int[] be = n.backEdges;
/*  93:182 */     int[] beW = n.beWeights;
/*  94:183 */     int[] cv = c.getClusterVector();
/*  95:    */     
/*  96:    */ 
/*  97:186 */     int currentNodeCluster = cv[currentNode];
/*  98:187 */     n.cluster = cv[n.nodeID];
/*  99:189 */     for (int j = 0; j < fe.length; j++)
/* 100:    */     {
/* 101:191 */       int target = fe[j];
/* 102:192 */       int targetCluster = cv[target];
/* 103:    */       
/* 104:194 */       Node x = this.nodes_x[target];
/* 105:195 */       String nname = x.getName();
/* 106:196 */       int nid = n.nodeID;
/* 107:198 */       if (targetCluster == lmNewC)
/* 108:    */       {
/* 109:200 */         this.muE[lmNewC] += 1;
/* 110:201 */         this.epE[lmOrigC] -= 1;
/* 111:    */       }
/* 112:202 */       else if (targetCluster == lmOrigC)
/* 113:    */       {
/* 114:204 */         this.muE[lmOrigC] -= 1;
/* 115:205 */         this.epE[lmNewC] += 1;
/* 116:    */       }
/* 117:    */       else
/* 118:    */       {
/* 119:209 */         this.epE[lmOrigC] -= 1;
/* 120:210 */         this.epE[lmNewC] += 1;
/* 121:    */       }
/* 122:    */     }
/* 123:215 */     for (int j = 0; j < be.length; j++)
/* 124:    */     {
/* 125:217 */       int target = be[j];
/* 126:218 */       int targetCluster = cv[target];
/* 127:220 */       if (targetCluster == lmNewC)
/* 128:    */       {
/* 129:222 */         this.muE[lmNewC] += 1;
/* 130:223 */         this.epE[lmNewC] -= 1;
/* 131:    */       }
/* 132:224 */       else if (targetCluster == lmOrigC)
/* 133:    */       {
/* 134:226 */         this.muE[lmOrigC] -= 1;
/* 135:227 */         this.epE[lmOrigC] += 1;
/* 136:    */       }
/* 137:    */     }
/* 138:236 */     double newCFiOrig = calcCFi(lmOrigC);
/* 139:237 */     double newCFiNew = calcCFi(lmNewC);
/* 140:    */     
/* 141:239 */     double MQ = c.getLastMvObjFn();
/* 142:    */     
/* 143:241 */     MQ = MQ - CFiOrig - CFiNew + newCFiOrig + newCFiNew;
/* 144:    */     
/* 145:    */ 
/* 146:244 */     return MQ;
/* 147:    */   }
/* 148:    */   
/* 149:    */   private double calcCFi(int c)
/* 150:    */   {
/* 151:249 */     double dMuE = this.muE[c];
/* 152:250 */     double dEpE = this.epE[c];
/* 153:252 */     if (dMuE + dEpE > 0.0D) {
/* 154:253 */       return dMuE / (dMuE + dEpE);
/* 155:    */     }
/* 156:255 */     return 0.0D;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void calculate()
/* 160:    */   {
/* 161:266 */     int k = 0;
/* 162:267 */     double intra = 0.0D;
/* 163:268 */     double inter = 0.0D;
/* 164:269 */     double objTalley = 0.0D;
/* 165:    */     
/* 166:271 */     this.clusters_x = this.graph_d.getClusters();
/* 167:273 */     if (clusterMatrix_d.length != this.numberOfNodes_d) {
/* 168:274 */       clusterMatrix_d = (int[][])null;
/* 169:    */     }
/* 170:275 */     if (clusterMatrix_d == null) {
/* 171:276 */       clusterMatrix_d = new int[this.numberOfNodes_d][this.numberOfNodes_d + 1];
/* 172:    */     }
/* 173:278 */     for (int i = 0; i < this.numberOfNodes_d; i++)
/* 174:    */     {
/* 175:279 */       clusterMatrix_d[i][0] = 0;
/* 176:280 */       this.nodes_x[i].cluster = -1;
/* 177:    */     }
/* 178:283 */     int pos = 0;
/* 179:284 */     for (int i = 0; i < this.numberOfNodes_d; i++)
/* 180:    */     {
/* 181:285 */       int numCluster = this.clusters_x[i]; int 
/* 182:286 */         tmp137_136 = 0; int[] tmp137_135 = clusterMatrix_d[numCluster]; int tmp141_140 = (tmp137_135[tmp137_136] + 1);tmp137_135[tmp137_136] = tmp141_140;clusterMatrix_d[numCluster][tmp141_140] = i;
/* 183:287 */       this.nodes_x[i].cluster = numCluster;
/* 184:    */     }
/* 185:289 */     double dDebug = this.graph_d.getObjectiveFunctionValue();
/* 186:291 */     for (int i = 0; i < clusterMatrix_d.length; i++) {
/* 187:292 */       if (clusterMatrix_d[i][0] > 0)
/* 188:    */       {
/* 189:293 */         int[] clust = clusterMatrix_d[i];
/* 190:294 */         objTalley += calculateIntradependenciesValue(clust, i);
/* 191:295 */         k++;
/* 192:    */       }
/* 193:    */     }
/* 194:299 */     this.graph_d.setIntradependenciesValue(0.0D);
/* 195:300 */     this.graph_d.setInterdependenciesValue(0.0D);
/* 196:301 */     this.graph_d.setObjectiveFunctionValue(objTalley);
/* 197:    */   }
/* 198:    */   
/* 199:    */   public double calculateIntradependenciesValue(int[] c, int numCluster)
/* 200:    */   {
/* 201:312 */     double intradep = 0.0D;
/* 202:313 */     double intraEdges = 0.0D;
/* 203:314 */     double interEdges = 0.0D;
/* 204:315 */     double exitEdges = 0.0D;
/* 205:316 */     int k = 0;
/* 206:317 */     for (int i = 1; i <= c[0]; i++)
/* 207:    */     {
/* 208:318 */       Node node = this.nodes_x[c[i]];
/* 209:319 */       k++;
/* 210:320 */       int[] c2 = node.dependencies;
/* 211:321 */       int[] w = node.weights;
/* 212:323 */       if (c2 != null) {
/* 213:324 */         for (int j = 0; j < c2.length; j++) {
/* 214:325 */           if (this.nodes_x[c2[j]].cluster == numCluster)
/* 215:    */           {
/* 216:329 */             intradep += w[j];
/* 217:330 */             intraEdges += 1.0D;
/* 218:    */           }
/* 219:    */           else
/* 220:    */           {
/* 221:334 */             exitEdges += w[j];
/* 222:335 */             interEdges += 1.0D;
/* 223:    */           }
/* 224:    */         }
/* 225:    */       }
/* 226:    */     }
/* 227:341 */     if ((k == 0) || (k == 1)) {
/* 228:342 */       k = 1;
/* 229:    */     } else {
/* 230:344 */       k *= (k - 1);
/* 231:    */     }
/* 232:351 */     double objValue = 0.0D;
/* 233:361 */     if (exitEdges + intradep == 0.0D) {
/* 234:362 */       objValue = 0.0D;
/* 235:    */     } else {
/* 236:364 */       objValue = intradep / (intradep + exitEdges);
/* 237:    */     }
/* 238:370 */     return objValue;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public double calculateInterdependenciesValue(int[] c1, int[] c2, int nc1, int nc2)
/* 242:    */   {
/* 243:381 */     double interdep = 0.0D;
/* 244:382 */     for (int i = 1; i <= c1[0]; i++)
/* 245:    */     {
/* 246:383 */       int[] ca = this.nodes_x[c1[i]].dependencies;
/* 247:384 */       int[] w = this.nodes_x[c1[i]].weights;
/* 248:386 */       if (ca != null) {
/* 249:387 */         for (int j = 0; j < ca.length; j++) {
/* 250:390 */           if (this.nodes_x[ca[j]].cluster == nc2) {
/* 251:391 */             interdep += w[j];
/* 252:    */           }
/* 253:    */         }
/* 254:    */       }
/* 255:    */     }
/* 256:397 */     for (int i = 1; i <= c2[0]; i++)
/* 257:    */     {
/* 258:398 */       int[] ca = this.nodes_x[c2[i]].dependencies;
/* 259:399 */       int[] w = this.nodes_x[c2[i]].weights;
/* 260:401 */       if (ca != null) {
/* 261:402 */         for (int j = 0; j < ca.length; j++) {
/* 262:405 */           if (this.nodes_x[ca[j]].cluster == nc1) {
/* 263:406 */             interdep += w[j];
/* 264:    */           }
/* 265:    */         }
/* 266:    */       }
/* 267:    */     }
/* 268:411 */     interdep /= 2.0D * c1[0] * c2[0];
/* 269:412 */     return interdep;
/* 270:    */   }
/* 271:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.TurboMQIncr
 * JD-Core Version:    0.7.0.1
 */