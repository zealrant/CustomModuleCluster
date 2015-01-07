/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Enumeration;
/*   6:    */ import java.util.Hashtable;
/*   7:    */ import java.util.Set;
/*   8:    */ 
/*   9:    */ public class NextLevelGraph
/*  10:    */ {
/*  11:    */   class NodeInfo
/*  12:    */   {
/*  13:    */     public String name;
/*  14:    */     public int id;
/*  15:    */     public Hashtable dependencies;
/*  16:    */     public Hashtable backEdges;
/*  17:    */     public Hashtable dWeights;
/*  18:    */     public Hashtable beWeights;
/*  19:    */     public Hashtable childNodes;
/*  20:    */     public int[] arrayDependencies;
/*  21:    */     public int[] arrayWeights;
/*  22:    */     
/*  23:    */     public NodeInfo(String n)
/*  24:    */     {
/*  25: 54 */       this.name = n;
/*  26: 55 */       this.id = -1;
/*  27: 56 */       this.dependencies = new Hashtable();
/*  28: 57 */       this.dWeights = new Hashtable();
/*  29: 58 */       this.backEdges = new Hashtable();
/*  30: 59 */       this.beWeights = new Hashtable();
/*  31: 60 */       this.childNodes = new Hashtable();
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Graph genNextLevelGraph(Graph g)
/*  36:    */   {
/*  37: 69 */     int lvl = g.getGraphLevel() + 1;
/*  38: 70 */     int[] cnames = g.getClusterNames();
/*  39: 71 */     Node[] nodeList = g.getNodes();
/*  40: 72 */     Node[] newNodeList = new Node[cnames.length];
/*  41: 73 */     Hashtable cnameht = new Hashtable();
/*  42: 74 */     Hashtable clusterMap = new Hashtable();
/*  43: 75 */     int nodeLevel = g.getGraphLevel();
/*  44:    */     
/*  45: 77 */     int[] clusters = g.getClusters();
/*  46: 78 */     for (int i = 0; i < nodeList.length; i++) {
/*  47: 80 */       nodeList[i].cluster = clusters[i];
/*  48:    */     }
/*  49: 82 */     cnames = g.getClusterNames();
/*  50: 84 */     for (int i = 0; i < cnames.length; i++)
/*  51:    */     {
/*  52: 86 */       String name = "clusterLvl" + lvl + "Num" + i;
/*  53: 87 */       NodeInfo ni = new NodeInfo(name);
/*  54: 88 */       ni.id = i;
/*  55: 89 */       cnameht.put(new Integer(cnames[i]), ni);
/*  56:    */     }
/*  57: 93 */     for (int i = 0; i < nodeList.length; i++)
/*  58:    */     {
/*  59: 95 */       Node srcNode = nodeList[i];
/*  60:    */       
/*  61:    */ 
/*  62: 98 */       int[] edges = srcNode.getDependencies();
/*  63: 99 */       int[] weights = srcNode.getWeights();
/*  64:    */       
/*  65:101 */       clusterMap.put(new Integer(srcNode.nodeID), new Integer(srcNode.cluster));
/*  66:    */       
/*  67:103 */       NodeInfo niTmp = (NodeInfo)cnameht.get(new Integer(srcNode.cluster));
/*  68:104 */       niTmp.childNodes.put(new Integer(srcNode.nodeID), srcNode);
/*  69:106 */       for (int j = 0; j < edges.length; j++)
/*  70:    */       {
/*  71:108 */         Node destNode = nodeList[edges[j]];
/*  72:109 */         int srcCluster = srcNode.cluster;
/*  73:110 */         int destCluster = destNode.cluster;
/*  74:112 */         if (srcCluster != destCluster)
/*  75:    */         {
/*  76:115 */           int weight = weights[j];
/*  77:    */           
/*  78:117 */           NodeInfo niSrc = (NodeInfo)cnameht.get(new Integer(srcCluster));
/*  79:118 */           NodeInfo niDest = (NodeInfo)cnameht.get(new Integer(destCluster));
/*  80:    */           
/*  81:120 */           Integer src = new Integer(niSrc.id);
/*  82:121 */           Integer dest = new Integer(niDest.id);
/*  83:122 */           Integer w = new Integer(weight);
/*  84:124 */           if (niSrc.dependencies.get(dest) == null)
/*  85:    */           {
/*  86:126 */             niSrc.dependencies.put(dest, dest);
/*  87:127 */             niSrc.dWeights.put(dest, w);
/*  88:    */           }
/*  89:    */           else
/*  90:    */           {
/*  91:131 */             Integer edgeW = (Integer)niSrc.dWeights.get(dest);
/*  92:132 */             w = new Integer(w.intValue() + edgeW.intValue());
/*  93:133 */             niSrc.dWeights.put(dest, w);
/*  94:    */           }
/*  95:136 */           if (niDest.backEdges.get(src) == null)
/*  96:    */           {
/*  97:138 */             niDest.backEdges.put(src, src);
/*  98:139 */             niDest.beWeights.put(src, w);
/*  99:    */           }
/* 100:    */           else
/* 101:    */           {
/* 102:143 */             Integer edgeW = (Integer)niDest.beWeights.get(src);
/* 103:144 */             w = new Integer(w.intValue() + edgeW.intValue());
/* 104:145 */             niDest.beWeights.put(src, w);
/* 105:    */           }
/* 106:    */         }
/* 107:    */       }
/* 108:    */     }
/* 109:151 */     Graph retGraph = new Graph(cnameht.size());
/* 110:152 */     retGraph.clear();
/* 111:153 */     Node[] newNL = retGraph.getNodes();
/* 112:    */     
/* 113:155 */     Object[] nl = cnameht.values().toArray();
/* 114:156 */     for (int i = 0; i < nl.length; i++)
/* 115:    */     {
/* 116:158 */       Node n = new Node();
/* 117:159 */       NodeInfo ni = (NodeInfo)nl[i];
/* 118:160 */       newNL[ni.id] = n;
/* 119:161 */       n.setName(ni.name);
/* 120:162 */       n.nodeID = ni.id;
/* 121:163 */       n.setIsCluster(true);
/* 122:164 */       n.nodeLevel = nodeLevel;
/* 123:165 */       n.children = new Node[ni.childNodes.size()];
/* 124:166 */       int numForwardDep = ni.dependencies.size();
/* 125:167 */       int numBackDep = ni.backEdges.size();
/* 126:168 */       n.dependencies = new int[numForwardDep];
/* 127:169 */       n.weights = new int[numForwardDep];
/* 128:170 */       n.backEdges = new int[numBackDep];
/* 129:171 */       n.beWeights = new int[numBackDep];
/* 130:    */       
/* 131:173 */       int j = 0;
/* 132:174 */       for (Enumeration e = ni.childNodes.elements(); e.hasMoreElements();) {
/* 133:175 */         n.children[(j++)] = ((Node)e.nextElement());
/* 134:    */       }
/* 135:181 */       updateEdgeArrays(ni.dependencies, n.dependencies, ni.dWeights, n.weights);
/* 136:182 */       updateEdgeArrays(ni.backEdges, n.backEdges, ni.beWeights, n.beWeights);
/* 137:    */     }
/* 138:188 */     retGraph.setPreviousLevelGraph(g);
/* 139:189 */     retGraph.setGraphLevel(g.getGraphLevel() + 1);
/* 140:190 */     retGraph.setIsClusterTree(g.isClusterTree());
/* 141:    */     
/* 142:192 */     return retGraph;
/* 143:    */   }
/* 144:    */   
/* 145:    */   private void dumpNode(Node n)
/* 146:    */   {
/* 147:197 */     System.out.println("Node: " + n.name_d);
/* 148:198 */     System.out.println("Node ID:  " + n.nodeID);
/* 149:199 */     System.out.print("EDGES(Weight): ");
/* 150:200 */     for (int i = 0; i < n.dependencies.length; i++) {
/* 151:201 */       System.out.print(n.dependencies[i] + "(" + n.weights[i] + ")");
/* 152:    */     }
/* 153:202 */     System.out.println();
/* 154:203 */     System.out.print("BACK EDGES(Weight): ");
/* 155:204 */     for (int i = 0; i < n.backEdges.length; i++) {
/* 156:205 */       System.out.print(n.backEdges[i] + "(" + n.beWeights[i] + ")");
/* 157:    */     }
/* 158:206 */     System.out.println();
/* 159:207 */     System.out.print("Children: ");
/* 160:208 */     for (int i = 0; i < n.children.length; i++) {
/* 161:209 */       System.out.print("[" + n.children[i].name_d + "] ");
/* 162:    */     }
/* 163:210 */     System.out.println();
/* 164:211 */     System.out.println("======================================");
/* 165:    */   }
/* 166:    */   
/* 167:    */   private void updateEdgeArrays(Hashtable edgeH, int[] edge, Hashtable weightH, int[] weight)
/* 168:    */   {
/* 169:216 */     int[] tmpEdge = edge;
/* 170:217 */     int[] tmpWeight = weight;
/* 171:    */     
/* 172:219 */     Object[] eo = edgeH.keySet().toArray();
/* 173:220 */     for (int i = 0; i < eo.length; i++)
/* 174:    */     {
/* 175:222 */       Integer Ikey = (Integer)eo[i];
/* 176:223 */       Integer Iweight = (Integer)weightH.get(Ikey);
/* 177:224 */       int edgeTo = Ikey.intValue();
/* 178:225 */       int edgeWeight = Iweight.intValue();
/* 179:226 */       tmpEdge[i] = edgeTo;
/* 180:227 */       tmpWeight[i] = edgeWeight;
/* 181:    */     }
/* 182:229 */     edge = tmpEdge;
/* 183:230 */     weight = tmpWeight;
/* 184:    */   }
/* 185:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.NextLevelGraph
 * JD-Core Version:    0.7.0.1
 */