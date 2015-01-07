/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import bunch.stats.StatsManager;
/*   4:    */ import bunch.util.BunchUtilities;
/*   5:    */ 
/*   6:    */ public class OptimalClusteringMethod
/*   7:    */   extends ClusteringMethod2
/*   8:    */ {
/*   9: 31 */   boolean hasMorePartitions_d = false;
/*  10:    */   int[] tmpClusters_d;
/*  11:    */   int[] nClusters_d;
/*  12: 34 */   int NC = 0;
/*  13:    */   
/*  14:    */   public void run()
/*  15:    */   {
/*  16: 46 */     Graph graph = getGraph().cloneGraph();
/*  17: 47 */     StatsManager sm = StatsManager.getInstance();
/*  18:    */     
/*  19:    */ 
/*  20:    */ 
/*  21: 51 */     int[] clusters = graph.getClusters();
/*  22: 52 */     int cSz = clusters.length;
/*  23:    */     
/*  24: 54 */     this.nClusters_d = new int[clusters.length + 1];
/*  25:    */     
/*  26:    */ 
/*  27: 57 */     this.tmpClusters_d = new int[clusters.length + 1];
/*  28:    */     
/*  29:    */ 
/*  30: 60 */     clusters = new int[cSz];
/*  31: 61 */     int[] lastCluster = new int[cSz];
/*  32:    */     
/*  33: 63 */     IterationEvent ev = new IterationEvent(this);
/*  34: 64 */     System.arraycopy(this.nClusters_d, 1, clusters, 0, clusters.length);
/*  35:    */     
/*  36:    */ 
/*  37:    */ 
/*  38: 68 */     sm.clearExhaustiveFinished();
/*  39: 69 */     sm.setExhaustiveTotal(getMaxIterations());
/*  40: 70 */     sm.incrExhaustiveFinished();
/*  41: 71 */     boolean morePartitions = findNextPartition();
/*  42:    */     
/*  43: 73 */     System.arraycopy(clusters, 0, lastCluster, 0, clusters.length);
/*  44:    */     
/*  45:    */ 
/*  46: 76 */     Cluster currC = new Cluster(graph, clusters);
/*  47:    */     
/*  48: 78 */     setBestCluster(currC.cloneCluster());
/*  49: 79 */     Cluster bestCluster = new Cluster();
/*  50: 80 */     bestCluster.copyFromCluster(currC);
/*  51:    */     
/*  52: 82 */     double bestOFValue = bestCluster.calcObjFn();
/*  53: 83 */     int j = 2;
/*  54: 85 */     while (morePartitions)
/*  55:    */     {
/*  56: 87 */       System.arraycopy(this.nClusters_d, 1, clusters, 0, clusters.length);
/*  57: 90 */       for (int i = 0; i < clusters.length; i++) {
/*  58: 91 */         if (clusters[i] != lastCluster[i]) {
/*  59: 92 */           currC.relocate(i, clusters[i]);
/*  60:    */         }
/*  61:    */       }
/*  62:101 */       double ofValue = currC.calcObjFn();
/*  63:102 */       if (BunchUtilities.compareGreater(ofValue, bestOFValue))
/*  64:    */       {
/*  65:104 */         currC.incrDepth();
/*  66:105 */         bestCluster.copyFromCluster(currC);
/*  67:    */         
/*  68:107 */         bestOFValue = ofValue;
/*  69:    */         
/*  70:109 */         bestCluster.getClusterNames();
/*  71:110 */         setBestCluster(bestCluster.cloneCluster());
/*  72:    */       }
/*  73:112 */       ev.setIteration(j++);
/*  74:113 */       System.arraycopy(clusters, 0, lastCluster, 0, clusters.length);
/*  75:114 */       morePartitions = findNextPartition();
/*  76:115 */       sm.incrExhaustiveFinished();
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:121 */   static int xx = 1;
/*  81:    */   
/*  82:    */   private boolean findNextPartition()
/*  83:    */   {
/*  84:127 */     int N = getGraph().getNumberOfNodes();
/*  85:129 */     if (this.hasMorePartitions_d)
/*  86:    */     {
/*  87:130 */       int M = N;
/*  88:131 */       boolean more = true;
/*  89:132 */       int L = this.nClusters_d[M];
/*  90:133 */       while (more)
/*  91:    */       {
/*  92:134 */         L = this.nClusters_d[M];
/*  93:135 */         if (this.tmpClusters_d[L] != 1)
/*  94:    */         {
/*  95:136 */           more = false;
/*  96:    */         }
/*  97:    */         else
/*  98:    */         {
/*  99:139 */           this.nClusters_d[M] = 1;
/* 100:140 */           M -= 1;
/* 101:    */         }
/* 102:    */       }
/* 103:143 */       this.NC = (this.NC + M - N);
/* 104:144 */       this.tmpClusters_d[1] = (this.tmpClusters_d[1] + N - M);
/* 105:145 */       if (L == this.NC)
/* 106:    */       {
/* 107:146 */         this.NC += 1;
/* 108:147 */         this.tmpClusters_d[this.NC] = 0;
/* 109:    */       }
/* 110:150 */       this.nClusters_d[M] = (L + 1);
/* 111:151 */       this.tmpClusters_d[L] -= 1;
/* 112:152 */       this.tmpClusters_d[(L + 1)] += 1;
/* 113:    */     }
/* 114:    */     else
/* 115:    */     {
/* 116:156 */       this.NC = 1;
/* 117:157 */       for (int i = 1; i <= N; i++) {
/* 118:158 */         this.nClusters_d[i] = 1;
/* 119:    */       }
/* 120:159 */       this.tmpClusters_d[1] = N;
/* 121:    */     }
/* 122:162 */     this.hasMorePartitions_d = (this.NC != N);
/* 123:    */     
/* 124:164 */     return this.hasMorePartitions_d;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int getMaxIterations()
/* 128:    */   {
/* 129:171 */     return (int)getGraph().getNumberOfPartitions();
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.OptimalClusteringMethod
 * JD-Core Version:    0.7.0.1
 */