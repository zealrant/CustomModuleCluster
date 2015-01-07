/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Vector;
/*   5:    */ 
/*   6:    */ public class Drifters
/*   7:    */ {
/*   8:    */   public static final int NODE_NOT_CONNECTED = -1;
/*   9:    */   protected Graph graph_d;
/*  10: 38 */   Vector edges = new Vector();
/*  11: 39 */   int[] clusters = null;
/*  12: 40 */   Node[] nodeList = null;
/*  13: 41 */   int nodes = -1;
/*  14: 42 */   int[][] clustersMatrix = (int[][])null;
/*  15: 43 */   int[] partners = null;
/*  16:    */   
/*  17:    */   public Drifters(Graph g)
/*  18:    */   {
/*  19: 51 */     this.graph_d = g;
/*  20:    */   }
/*  21:    */   
/*  22:    */   private void initStructures()
/*  23:    */   {
/*  24: 61 */     this.clusters = this.graph_d.getClusters();
/*  25: 62 */     this.nodeList = this.graph_d.getNodes();
/*  26: 63 */     this.nodes = this.nodeList.length;
/*  27: 64 */     this.clustersMatrix = new int[this.nodes][this.nodes + 1];
/*  28: 65 */     this.partners = new int[this.nodes];
/*  29: 67 */     for (int i = 0; i < this.nodes; i++)
/*  30:    */     {
/*  31: 68 */       this.clustersMatrix[i][0] = 0;
/*  32: 69 */       this.nodeList[i].cluster = -1;
/*  33: 70 */       this.partners[i] = 0;
/*  34:    */     }
/*  35: 72 */     for (int i = 0; i < this.nodes; i++)
/*  36:    */     {
/*  37: 73 */       int numCluster = this.clusters[i]; int 
/*  38: 74 */         tmp131_130 = 0; int[] tmp131_129 = this.clustersMatrix[numCluster]; int tmp135_134 = (tmp131_129[tmp131_130] + 1);tmp131_129[tmp131_130] = tmp135_134;this.clustersMatrix[numCluster][tmp135_134] = i;
/*  39: 75 */       this.nodeList[i].cluster = numCluster;
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean consolidate()
/*  44:    */   {
/*  45: 87 */     boolean foundDrifter = false;
/*  46: 88 */     initStructures();
/*  47: 90 */     for (int i = 0; i < this.nodes; i++)
/*  48:    */     {
/*  49: 91 */       boolean rc = fixDrifters();
/*  50: 92 */       if (rc == true) {
/*  51: 92 */         foundDrifter = true;
/*  52:    */       }
/*  53: 93 */       if (!rc) {
/*  54: 94 */         return foundDrifter;
/*  55:    */       }
/*  56:    */     }
/*  57: 96 */     return foundDrifter;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean fixDrifters()
/*  61:    */   {
/*  62:106 */     boolean drifterFound = false;
/*  63:107 */     int pos = 0;
/*  64:    */     
/*  65:109 */     int id = 0;
/*  66:110 */     int clusterIndex = 1;
/*  67:112 */     for (int i = 0; i < this.nodes; i++) {
/*  68:114 */       if (this.clustersMatrix[i][0] > 0) {
/*  69:116 */         for (int j = 1; j <= this.clustersMatrix[i][0]; j++)
/*  70:    */         {
/*  71:117 */           int potDrifter = this.clustersMatrix[i][j];
/*  72:118 */           int connections = 0;
/*  73:120 */           for (int k = 1; k <= this.clustersMatrix[i][0]; k++)
/*  74:    */           {
/*  75:121 */             int otherNode = this.clustersMatrix[i][k];
/*  76:124 */             if (potDrifter != otherNode)
/*  77:    */             {
/*  78:125 */               boolean forw = hasEdgeBetween(potDrifter, otherNode);
/*  79:126 */               boolean back = hasEdgeBetween(otherNode, potDrifter);
/*  80:127 */               if ((forw == true) || (back == true)) {
/*  81:128 */                 connections++;
/*  82:    */               }
/*  83:    */             }
/*  84:    */           }
/*  85:133 */           if (connections == 0)
/*  86:    */           {
/*  87:134 */             System.err.println("Drifter: " + this.nodeList[potDrifter].getName() + " is a drifter");
/*  88:135 */             drifterFound = true;
/*  89:136 */             findDrifterHomes(potDrifter);
/*  90:137 */             initStructures();
/*  91:    */           }
/*  92:    */         }
/*  93:    */       }
/*  94:    */     }
/*  95:142 */     return drifterFound;
/*  96:    */   }
/*  97:    */   
/*  98:    */   private boolean hasEdgeBetween(int i, int j)
/*  99:    */   {
/* 100:152 */     if (this.nodeList[i].dependencies == null) {
/* 101:153 */       return false;
/* 102:    */     }
/* 103:155 */     int[] e = this.nodeList[i].dependencies;
/* 104:160 */     for (int z = 0; z < e.length; z++) {
/* 105:161 */       if (e[z] == j) {
/* 106:163 */         return true;
/* 107:    */       }
/* 108:    */     }
/* 109:166 */     return false;
/* 110:    */   }
/* 111:    */   
/* 112:    */   private void findDrifterHomes(int d)
/* 113:    */   {
/* 114:178 */     int[] density = new int[this.nodes];
/* 115:179 */     Node n = this.nodeList[d];
/* 116:180 */     int homeCluster = n.cluster;
/* 117:181 */     for (int i = 0; i < this.nodes; i++) {
/* 118:182 */       density[i] = 0;
/* 119:    */     }
/* 120:184 */     for (int i = 0; i < this.nodes; i++) {
/* 121:186 */       if (this.clustersMatrix[i][0] > 0) {
/* 122:188 */         for (int j = 1; j <= this.clustersMatrix[i][0]; j++)
/* 123:    */         {
/* 124:189 */           int nid = this.clustersMatrix[i][j];
/* 125:190 */           if (nid == d)
/* 126:    */           {
/* 127:192 */             int[] edges = this.nodeList[d].dependencies;
/* 128:193 */             if (edges != null) {
/* 129:194 */               for (int ecnt = 0; ecnt < edges.length; ecnt++) {
/* 130:195 */                 density[this.nodeList[edges[ecnt]].cluster] += 1;
/* 131:    */               }
/* 132:    */             }
/* 133:    */           }
/* 134:    */           else
/* 135:    */           {
/* 136:200 */             int[] edges = this.nodeList[nid].dependencies;
/* 137:201 */             if (edges != null) {
/* 138:202 */               for (int ecnt = 0; ecnt < edges.length; ecnt++) {
/* 139:203 */                 if (edges[ecnt] == d) {
/* 140:204 */                   density[this.nodeList[nid].cluster] += 1;
/* 141:    */                 }
/* 142:    */               }
/* 143:    */             }
/* 144:    */           }
/* 145:    */         }
/* 146:    */       }
/* 147:    */     }
/* 148:211 */     int newHome = getMaxConnectedCluster(d, density);
/* 149:212 */     if (newHome != -1) {
/* 150:213 */       moveNode(d, this.nodeList[d].cluster, newHome);
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   private void moveNode(int node, int srcCluster, int destCluster)
/* 155:    */   {
/* 156:223 */     this.clusters[node] = destCluster;
/* 157:224 */     this.nodeList[node].cluster = destCluster;
/* 158:225 */     this.graph_d.setNodes(this.nodeList);
/* 159:226 */     this.graph_d.setClusters(this.clusters);
/* 160:227 */     initStructures();
/* 161:    */   }
/* 162:    */   
/* 163:    */   private int getMaxConnectedCluster(int d, int[] density)
/* 164:    */   {
/* 165:237 */     int maxConn = -1;
/* 166:238 */     int maxConnCluster = -1;
/* 167:239 */     for (int i = 0; i < density.length; i++) {
/* 168:241 */       if (density[i] > 0) {
/* 169:243 */         if (density[i] > maxConn)
/* 170:    */         {
/* 171:244 */           maxConn = density[i];
/* 172:245 */           maxConnCluster = i;
/* 173:    */         }
/* 174:    */       }
/* 175:    */     }
/* 176:249 */     String src = this.nodeList[d].getName();
/* 177:250 */     return maxConnCluster;
/* 178:    */   }
/* 179:    */   
/* 180:    */   private void dumpFreqArray(int d, int[] density)
/* 181:    */   {
/* 182:260 */     for (int i = 0; i < density.length; i++) {
/* 183:262 */       if (density[i] > 0)
/* 184:    */       {
/* 185:264 */         String src = this.nodeList[d].getName();
/* 186:265 */         System.err.println(src + " has " + density[i] + " connection(s) to cluster " + i);
/* 187:    */       }
/* 188:    */     }
/* 189:    */   }
/* 190:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.Drifters
 * JD-Core Version:    0.7.0.1
 */