/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ public class SpirosMQ
/*   4:    */   implements ObjectiveFunctionCalculator
/*   5:    */ {
/*   6:    */   private Graph graph_d;
/*   7: 32 */   private static int[][] clusterMatrix_d = (int[][])null;
/*   8:    */   private Node[] nodes_x;
/*   9:    */   private int[] clusters_x;
/*  10:    */   private int numberOfNodes_d;
/*  11:    */   
/*  12:    */   public void init(Graph g)
/*  13:    */   {
/*  14: 52 */     this.graph_d = g;
/*  15: 53 */     this.numberOfNodes_d = g.getNumberOfNodes();
/*  16: 54 */     this.nodes_x = g.getNodes();
/*  17: 55 */     this.clusters_x = g.getClusters();
/*  18: 56 */     clusterMatrix_d = new int[this.numberOfNodes_d][this.numberOfNodes_d + 1];
/*  19: 58 */     if (clusterMatrix_d == null) {
/*  20: 59 */       clusterMatrix_d = new int[this.numberOfNodes_d][this.numberOfNodes_d + 1];
/*  21:    */     }
/*  22: 61 */     for (int i = 0; i < clusterMatrix_d.length; i++) {
/*  23: 62 */       clusterMatrix_d[i][0] = 0;
/*  24:    */     }
/*  25:    */   }
/*  26:    */   
/*  27:    */   public double calculate(Cluster c)
/*  28:    */   {
/*  29: 68 */     calculate();
/*  30: 69 */     return this.graph_d.getObjectiveFunctionValue();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void calculate()
/*  34:    */   {
/*  35: 80 */     int k = 0;
/*  36: 81 */     double intra = 0.0D;
/*  37: 82 */     double inter = 0.0D;
/*  38: 83 */     double objTalley = 0.0D;
/*  39: 85 */     if (clusterMatrix_d.length != this.numberOfNodes_d) {
/*  40: 86 */       clusterMatrix_d = (int[][])null;
/*  41:    */     }
/*  42: 87 */     if (clusterMatrix_d == null) {
/*  43: 88 */       clusterMatrix_d = new int[this.numberOfNodes_d][this.numberOfNodes_d + 1];
/*  44:    */     }
/*  45: 90 */     for (int i = 0; i < this.numberOfNodes_d; i++)
/*  46:    */     {
/*  47: 91 */       clusterMatrix_d[i][0] = 0;
/*  48: 92 */       this.nodes_x[i].cluster = -1;
/*  49:    */     }
/*  50: 95 */     int pos = 0;
/*  51: 96 */     for (int i = 0; i < this.numberOfNodes_d; i++)
/*  52:    */     {
/*  53: 97 */       int numCluster = this.clusters_x[i]; int 
/*  54: 98 */         tmp126_125 = 0; int[] tmp126_124 = clusterMatrix_d[numCluster]; int tmp130_129 = (tmp126_124[tmp126_125] + 1);tmp126_124[tmp126_125] = tmp130_129;clusterMatrix_d[numCluster][tmp130_129] = i;
/*  55: 99 */       this.nodes_x[i].cluster = numCluster;
/*  56:    */     }
/*  57:102 */     for (int i = 0; i < clusterMatrix_d.length; i++) {
/*  58:103 */       if (clusterMatrix_d[i][0] > 0)
/*  59:    */       {
/*  60:104 */         int[] clust = clusterMatrix_d[i];
/*  61:105 */         objTalley += calculateIntradependenciesValue(clust, i);
/*  62:106 */         k++;
/*  63:    */       }
/*  64:    */     }
/*  65:110 */     this.graph_d.setIntradependenciesValue(0.0D);
/*  66:111 */     this.graph_d.setInterdependenciesValue(0.0D);
/*  67:112 */     this.graph_d.setObjectiveFunctionValue(objTalley);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public double calculateIntradependenciesValue(int[] c, int numCluster)
/*  71:    */   {
/*  72:123 */     double intradep = 0.0D;
/*  73:124 */     double intraEdges = 0.0D;
/*  74:125 */     double interEdges = 0.0D;
/*  75:126 */     double exitEdges = 0.0D;
/*  76:127 */     int k = 0;
/*  77:128 */     for (int i = 1; i <= c[0]; i++)
/*  78:    */     {
/*  79:129 */       Node node = this.nodes_x[c[i]];
/*  80:130 */       k++;
/*  81:131 */       int[] c2 = node.dependencies;
/*  82:132 */       int[] w = node.weights;
/*  83:134 */       if (c2 != null) {
/*  84:135 */         for (int j = 0; j < c2.length; j++) {
/*  85:136 */           if (this.nodes_x[c2[j]].cluster == numCluster)
/*  86:    */           {
/*  87:140 */             intradep += w[j];
/*  88:141 */             intraEdges += 1.0D;
/*  89:    */           }
/*  90:    */           else
/*  91:    */           {
/*  92:145 */             exitEdges += w[j];
/*  93:146 */             interEdges += 1.0D;
/*  94:    */           }
/*  95:    */         }
/*  96:    */       }
/*  97:    */     }
/*  98:152 */     if ((k == 0) || (k == 1)) {
/*  99:153 */       k = 1;
/* 100:    */     } else {
/* 101:155 */       k *= (k - 1);
/* 102:    */     }
/* 103:162 */     double objValue = 0.0D;
/* 104:172 */     if (exitEdges + intradep == 0.0D) {
/* 105:173 */       objValue = 0.0D;
/* 106:    */     } else {
/* 107:175 */       objValue = intraEdges / (intraEdges + interEdges) * (intradep / (intradep + exitEdges));
/* 108:    */     }

/* 109:180 */     return objValue;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public double calculateInterdependenciesValue(int[] c1, int[] c2, int nc1, int nc2)
/* 113:    */   {
/* 114:191 */     double interdep = 0.0D;
/* 115:192 */     for (int i = 1; i <= c1[0]; i++)
/* 116:    */     {
/* 117:193 */       int[] ca = this.nodes_x[c1[i]].dependencies;
/* 118:194 */       int[] w = this.nodes_x[c1[i]].weights;
/* 119:196 */       if (ca != null) {
/* 120:197 */         for (int j = 0; j < ca.length; j++) {
/* 121:198 */           if (this.nodes_x[ca[j]].cluster == nc2) {
/* 122:199 */             interdep += w[j];
/* 123:    */           }
/* 124:    */         }
/* 125:    */       }
/* 126:    */     }
/* 127:205 */     for (int i = 1; i <= c2[0]; i++)
/* 128:    */     {
/* 129:206 */       int[] ca = this.nodes_x[c2[i]].dependencies;
/* 130:207 */       int[] w = this.nodes_x[c2[i]].weights;
/* 131:209 */       if (ca != null) {
/* 132:210 */         for (int j = 0; j < ca.length; j++) {
/* 133:211 */           if (this.nodes_x[ca[j]].cluster == nc1) {
/* 134:212 */             interdep += w[j];
/* 135:    */           }
/* 136:    */         }
/* 137:    */       }
/* 138:    */     }
/* 139:217 */     interdep /= 2.0D * c1[0] * c2[0];
/* 140:218 */     return interdep;
/* 141:    */   }
/* 142:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.SpirosMQ
 * JD-Core Version:    0.7.0.1
 */