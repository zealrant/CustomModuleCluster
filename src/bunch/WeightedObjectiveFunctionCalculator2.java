/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ public class WeightedObjectiveFunctionCalculator2
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
/*  35: 87 */     int k = 0;
/*  36: 88 */     double intra = 0.0D;
/*  37: 89 */     double inter = 0.0D;
/*  38: 90 */     double objTalley = 0.0D;
/*  39: 92 */     if (clusterMatrix_d.length != this.numberOfNodes_d) {
/*  40: 93 */       clusterMatrix_d = (int[][])null;
/*  41:    */     }
/*  42: 94 */     if (clusterMatrix_d == null) {
/*  43: 95 */       clusterMatrix_d = new int[this.numberOfNodes_d][this.numberOfNodes_d + 1];
/*  44:    */     }
/*  45: 97 */     for (int i = 0; i < this.numberOfNodes_d; i++)
/*  46:    */     {
/*  47: 98 */       clusterMatrix_d[i][0] = 0;
/*  48: 99 */       this.nodes_x[i].cluster = -1;
/*  49:    */     }
/*  50:102 */     int pos = 0;
/*  51:103 */     for (int i = 0; i < this.numberOfNodes_d; i++)
/*  52:    */     {
/*  53:104 */       int numCluster = this.clusters_x[i]; int 
/*  54:105 */         tmp126_125 = 0; int[] tmp126_124 = clusterMatrix_d[numCluster]; int tmp130_129 = (tmp126_124[tmp126_125] + 1);tmp126_124[tmp126_125] = tmp130_129;clusterMatrix_d[numCluster][tmp130_129] = i;
/*  55:106 */       this.nodes_x[i].cluster = numCluster;
/*  56:    */     }
/*  57:109 */     for (int i = 0; i < clusterMatrix_d.length; i++) {
/*  58:110 */       if (clusterMatrix_d[i][0] > 0)
/*  59:    */       {
/*  60:111 */         int[] clust = clusterMatrix_d[i];
/*  61:112 */         objTalley += calculateIntradependenciesValue(clust, i);
/*  62:113 */         k++;
/*  63:    */       }
/*  64:    */     }
/*  65:117 */     this.graph_d.setIntradependenciesValue(0.0D);
/*  66:118 */     this.graph_d.setInterdependenciesValue(0.0D);
/*  67:119 */     this.graph_d.setObjectiveFunctionValue(objTalley);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public double calculateIntradependenciesValue(int[] c, int numCluster)
/*  71:    */   {
/*  72:130 */     double intradep = 0.0D;
/*  73:131 */     double intraEdges = 0.0D;
/*  74:132 */     double interEdges = 0.0D;
/*  75:133 */     double exitEdges = 0.0D;
/*  76:134 */     int k = 0;
/*  77:135 */     for (int i = 1; i <= c[0]; i++)
/*  78:    */     {
/*  79:136 */       Node node = this.nodes_x[c[i]];
/*  80:137 */       k++;
/*  81:138 */       int[] c2 = node.dependencies;
/*  82:139 */       int[] w = node.weights;
/*  83:141 */       if (c2 != null) {
/*  84:142 */         for (int j = 0; j < c2.length; j++) {
/*  85:143 */           if (this.nodes_x[c2[j]].cluster == numCluster)
/*  86:    */           {
/*  87:147 */             intradep += w[j];
/*  88:148 */             intraEdges += 1.0D;
/*  89:    */           }
/*  90:    */           else
/*  91:    */           {
/*  92:152 */             exitEdges += w[j];
/*  93:153 */             interEdges += 1.0D;
/*  94:    */           }
/*  95:    */         }
/*  96:    */       }
/*  97:    */     }
/*  98:159 */     if ((k == 0) || (k == 1)) {
/*  99:160 */       k = 1;
/* 100:    */     } else {
/* 101:162 */       k *= (k - 1);
/* 102:    */     }
/* 103:169 */     double objValue = 0.0D;
/* 104:182 */     if (exitEdges + intradep == 0.0D) {
/* 105:183 */       objValue = 0.0D;
/* 106:    */     } else {
/* 107:185 */       objValue = 0.5D - intraEdges / (intraEdges + interEdges) * (intradep / (intradep + exitEdges));
/* 108:    */     }
/* 109:203 */     return objValue;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public double calculateInterdependenciesValue(int[] c1, int[] c2, int nc1, int nc2)
/* 113:    */   {
/* 114:214 */     double interdep = 0.0D;
/* 115:215 */     for (int i = 1; i <= c1[0]; i++)
/* 116:    */     {
/* 117:216 */       int[] ca = this.nodes_x[c1[i]].dependencies;
/* 118:217 */       int[] w = this.nodes_x[c1[i]].weights;
/* 119:219 */       if (ca != null) {
/* 120:220 */         for (int j = 0; j < ca.length; j++) {
/* 121:223 */           if (this.nodes_x[ca[j]].cluster == nc2) {
/* 122:224 */             interdep += w[j];
/* 123:    */           }
/* 124:    */         }
/* 125:    */       }
/* 126:    */     }
/* 127:230 */     for (int i = 1; i <= c2[0]; i++)
/* 128:    */     {
/* 129:231 */       int[] ca = this.nodes_x[c2[i]].dependencies;
/* 130:232 */       int[] w = this.nodes_x[c2[i]].weights;
/* 131:234 */       if (ca != null) {
/* 132:235 */         for (int j = 0; j < ca.length; j++) {
/* 133:238 */           if (this.nodes_x[ca[j]].cluster == nc1) {
/* 134:239 */             interdep += w[j];
/* 135:    */           }
/* 136:    */         }
/* 137:    */       }
/* 138:    */     }
/* 139:244 */     interdep /= 2.0D * c1[0] * c2[0];
/* 140:245 */     return interdep;
/* 141:    */   }
/* 142:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.WeightedObjectiveFunctionCalculator2
 * JD-Core Version:    0.7.0.1
 */