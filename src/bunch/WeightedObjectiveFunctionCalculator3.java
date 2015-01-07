/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ public class WeightedObjectiveFunctionCalculator3
/*   4:    */   implements ObjectiveFunctionCalculator
/*   5:    */ {
/*   6:    */   private Graph graph_d;
/*   7: 35 */   private static int[][] clusterMatrix_d = (int[][])null;
/*   8:    */   private Node[] nodes_x;
/*   9:    */   private int[] clusters_x;
/*  10:    */   private int numberOfNodes_d;
/*  11:    */   
/*  12:    */   public void init(Graph g)
/*  13:    */   {
/*  14: 58 */     this.graph_d = g;
/*  15: 59 */     this.numberOfNodes_d = g.getNumberOfNodes();
/*  16: 60 */     this.nodes_x = g.getNodes();
/*  17: 61 */     this.clusters_x = g.getClusters();
/*  18: 62 */     clusterMatrix_d = new int[this.numberOfNodes_d][this.numberOfNodes_d + 1];
/*  19: 64 */     if (clusterMatrix_d == null) {
/*  20: 65 */       clusterMatrix_d = new int[this.numberOfNodes_d][this.numberOfNodes_d + 1];
/*  21:    */     }
/*  22: 67 */     for (int i = 0; i < clusterMatrix_d.length; i++) {
/*  23: 68 */       clusterMatrix_d[i][0] = 0;
/*  24:    */     }
/*  25:    */   }
/*  26:    */   
/*  27:    */   public double calculate(Cluster c)
/*  28:    */   {
/*  29: 74 */     calculate();
/*  30: 75 */     return this.graph_d.getObjectiveFunctionValue();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void calculate()
/*  34:    */   {
/*  35: 86 */     int k = 0;
/*  36: 87 */     double intra = 0.0D;
/*  37: 88 */     double inter = 0.0D;
/*  38: 89 */     double objTalley = 0.0D;
/*  39: 91 */     if (clusterMatrix_d.length != this.numberOfNodes_d) {
/*  40: 92 */       clusterMatrix_d = (int[][])null;
/*  41:    */     }
/*  42: 93 */     if (clusterMatrix_d == null) {
/*  43: 94 */       clusterMatrix_d = new int[this.numberOfNodes_d][this.numberOfNodes_d + 1];
/*  44:    */     }
/*  45: 96 */     for (int i = 0; i < this.numberOfNodes_d; i++)
/*  46:    */     {
/*  47: 97 */       clusterMatrix_d[i][0] = 0;
/*  48: 98 */       this.nodes_x[i].cluster = -1;
/*  49:    */     }
/*  50:101 */     int pos = 0;
/*  51:102 */     for (int i = 0; i < this.numberOfNodes_d; i++)
/*  52:    */     {
/*  53:103 */       int numCluster = this.clusters_x[i]; int 
/*  54:104 */         tmp126_125 = 0; int[] tmp126_124 = clusterMatrix_d[numCluster]; int tmp130_129 = (tmp126_124[tmp126_125] + 1);tmp126_124[tmp126_125] = tmp130_129;clusterMatrix_d[numCluster][tmp130_129] = i;
/*  55:105 */       this.nodes_x[i].cluster = numCluster;
/*  56:    */     }
/*  57:108 */     double[] intraTally = new double[clusterMatrix_d.length];
/*  58:109 */     double[] interTally = new double[clusterMatrix_d.length];
/*  59:111 */     for (int i = 0; i < clusterMatrix_d.length; i++)
/*  60:    */     {
/*  61:112 */       double tmp190_189 = 0.0D;interTally[i] = tmp190_189;intraTally[i] = tmp190_189;
/*  62:    */     }
/*  63:115 */     for (int i = 0; i < clusterMatrix_d.length; i++) {
/*  64:116 */       if (clusterMatrix_d[i][0] > 0)
/*  65:    */       {
/*  66:117 */         int[] clust = clusterMatrix_d[i];
/*  67:118 */         objTalley += calculateIntradependenciesValue(clust, i, intraTally, interTally);
/*  68:119 */         k++;
/*  69:    */       }
/*  70:    */     }
/*  71:123 */     objTalley = 0.0D;
/*  72:125 */     for (int i = 0; i < clusterMatrix_d.length; i++)
/*  73:    */     {
/*  74:127 */       double denom = intraTally[i] + interTally[i];
/*  75:128 */       if (denom > 0.0D) {
/*  76:129 */         objTalley += intraTally[i] / denom;
/*  77:    */       }
/*  78:    */     }
/*  79:133 */     this.graph_d.setIntradependenciesValue(0.0D);
/*  80:134 */     this.graph_d.setInterdependenciesValue(0.0D);
/*  81:135 */     this.graph_d.setObjectiveFunctionValue(objTalley);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public double calculateIntradependenciesValue(int[] c, int numCluster, double[] intraTally, double[] interTally)
/*  85:    */   {
/*  86:146 */     double intradep = 0.0D;
/*  87:147 */     double intraEdges = 0.0D;
/*  88:148 */     double interEdges = 0.0D;
/*  89:149 */     double exitEdges = 0.0D;
/*  90:150 */     int k = 0;
/*  91:151 */     for (int i = 1; i <= c[0]; i++)
/*  92:    */     {
/*  93:152 */       Node node = this.nodes_x[c[i]];
/*  94:153 */       k++;
/*  95:154 */       int[] c2 = node.dependencies;
/*  96:155 */       int[] w = node.weights;
/*  97:157 */       if (c2 != null) {
/*  98:158 */         for (int j = 0; j < c2.length; j++) {
/*  99:159 */           if (this.nodes_x[c2[j]].cluster == numCluster)
/* 100:    */           {
/* 101:163 */             intradep += w[j];
/* 102:164 */             intraEdges += 1.0D;
/* 103:165 */             intraTally[numCluster] += w[j];
/* 104:    */           }
/* 105:    */           else
/* 106:    */           {
/* 107:169 */             exitEdges += w[j];
/* 108:170 */             interEdges += 1.0D;
/* 109:171 */             interTally[numCluster] += 0.5D * w[j];
/* 110:172 */             interTally[this.nodes_x[c2[j]].cluster] += 0.5D * w[j];
/* 111:    */           }
/* 112:    */         }
/* 113:    */       }
/* 114:    */     }
/* 115:178 */     if ((k == 0) || (k == 1)) {
/* 116:179 */       k = 1;
/* 117:    */     } else {
/* 118:181 */       k *= (k - 1);
/* 119:    */     }
/* 120:188 */     double objValue = 0.0D;
/* 121:201 */     if (exitEdges + intradep == 0.0D) {
/* 122:202 */       objValue = 0.0D;
/* 123:    */     } else {
/* 124:204 */       objValue = 0.5D - intraEdges / (intraEdges + interEdges) * (intradep / (intradep + exitEdges));
/* 125:    */     }
/* 126:222 */     return objValue;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public double calculateInterdependenciesValue(int[] c1, int[] c2, int nc1, int nc2)
/* 130:    */   {
/* 131:233 */     double interdep = 0.0D;
/* 132:234 */     for (int i = 1; i <= c1[0]; i++)
/* 133:    */     {
/* 134:235 */       int[] ca = this.nodes_x[c1[i]].dependencies;
/* 135:236 */       int[] w = this.nodes_x[c1[i]].weights;
/* 136:238 */       if (ca != null) {
/* 137:239 */         for (int j = 0; j < ca.length; j++) {
/* 138:242 */           if (this.nodes_x[ca[j]].cluster == nc2) {
/* 139:243 */             interdep += w[j];
/* 140:    */           }
/* 141:    */         }
/* 142:    */       }
/* 143:    */     }
/* 144:249 */     for (int i = 1; i <= c2[0]; i++)
/* 145:    */     {
/* 146:250 */       int[] ca = this.nodes_x[c2[i]].dependencies;
/* 147:251 */       int[] w = this.nodes_x[c2[i]].weights;
/* 148:253 */       if (ca != null) {
/* 149:254 */         for (int j = 0; j < ca.length; j++) {
/* 150:257 */           if (this.nodes_x[ca[j]].cluster == nc1) {
/* 151:258 */             interdep += w[j];
/* 152:    */           }
/* 153:    */         }
/* 154:    */       }
/* 155:    */     }
/* 156:263 */     interdep /= 2.0D * c1[0] * c2[0];
/* 157:264 */     return interdep;
/* 158:    */   }
/* 159:    */
 }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.WeightedObjectiveFunctionCalculator3
 * JD-Core Version:    0.7.0.1
 */