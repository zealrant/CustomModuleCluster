/*   1:    */ package bunch.BunchServer;
/*   2:    */ 
/*   3:    */ import bunch.Callback;
/*   4:    */ import bunch.Cluster;
/*   5:    */ import bunch.Graph;
/*   6:    */ import bunch.util.BunchUtilities;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ 
/*   9:    */ public class ClusterUsingVectorSAHC
/*  10:    */   implements Runnable
/*  11:    */ {
/*  12: 27 */   int[] currWork = null;
/*  13: 28 */   int pos = -1;
/*  14: 28 */   int maxPos = -1;
/*  15: 29 */   Cluster currCluster = null;
/*  16: 30 */   Callback cliCallback = null;
/*  17: 31 */   Graph graph = null;
/*  18: 32 */   ServerProperties sProps = null;
/*  19: 33 */   IterationManager iMgr = null;
/*  20: 34 */   BSWindow parent = null;
/*  21: 35 */   ServerClusteringProgress progressWindow = null;
/*  22: 36 */   int totalWork = 0;
/*  23: 37 */   boolean adaptiveEnabled = true;
/*  24: 38 */   int currUOWSz = -1;
/*  25: 40 */   FindNeighbor nServer = new FindNeighbor();
/*  26:    */   
/*  27:    */   public ClusterUsingVectorSAHC(BSWindow p, ServerProperties sp, Cluster c, Callback cb)
/*  28:    */   {
/*  29: 43 */     this.parent = p;
/*  30: 44 */     this.sProps = sp;
/*  31: 45 */     this.graph = sp.theGraph;
/*  32: 46 */     this.currCluster = c;
/*  33: 47 */     this.cliCallback = cb;
/*  34: 48 */     this.progressWindow = null;
/*  35: 49 */     this.iMgr = new IterationManager();
/*  36: 50 */     this.totalWork = 0;
/*  37: 51 */     this.adaptiveEnabled = sp.adaptiveEnabled;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setProgressWindow(ServerClusteringProgress pw)
/*  41:    */   {
/*  42: 56 */     this.progressWindow = pw;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void disposeProgressWindow()
/*  46:    */   {
/*  47: 61 */     if (this.progressWindow != null) {
/*  48: 62 */       this.progressWindow.dispose();
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void run()
/*  53:    */   {
/*  54: 82 */     Cluster bestCluster = maximizeCluster(this.currCluster);
/*  55: 83 */     ReportToClientBestCluster(bestCluster);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Cluster maximizeCluster(Cluster c)
/*  59:    */   {
/*  60: 94 */     if (c == null) {
/*  61: 94 */       return null;
/*  62:    */     }
/*  63: 96 */     Cluster maxC = c.cloneCluster();
/*  64: 97 */     Cluster intermC = c.cloneCluster();
/*  65: 98 */     this.totalWork = 0;
/*  66:    */     
/*  67:100 */     double maxOF = maxC.getObjFnValue();
/*  68:101 */     double originalMax = maxOF;
/*  69:103 */     if (this.sProps.bestObjFnValue < maxOF) {
/*  70:104 */       this.sProps.bestObjFnValue = maxOF;
/*  71:    */     }
/*  72:106 */     int[] clustNames = c.getClusterNames();
/*  73:    */     
/*  74:108 */     int[] clusters = c.getClusterVector();
/*  75:    */     
/*  76:110 */     int[] maxClust = maxC.getClusterVector();
/*  77:    */     
/*  78:112 */     boolean[] locks = c.getLocks();
/*  79:    */     
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:119 */     this.pos = 0;
/*  86:120 */     this.maxPos = clusters.length;
/*  87:    */     
/*  88:    */ 
/*  89:123 */     int[] workVector = null;
/*  90:125 */     while ((workVector = getMoreWork(workVector)) != null)
/*  91:    */     {
/*  92:127 */       this.totalWork += workVector.length;
/*  93:128 */       intermC = this.nServer.clusterWorklist(c, intermC, clustNames, locks, workVector);
/*  94:129 */       if (this.progressWindow != null) {
/*  95:130 */         this.progressWindow.updateWorkProcessed(workVector.length, intermC.getObjFnValue(), this.currUOWSz, this.adaptiveEnabled);
/*  96:    */       }
/*  97:131 */       if (intermC.getObjFnValue() > maxOF)
/*  98:    */       {
/*  99:132 */         maxC.copyFromCluster(intermC);
/* 100:133 */         maxOF = maxC.getObjFnValue();
/* 101:    */       }
/* 102:    */     }
/* 103:137 */     if (maxOF > originalMax)
/* 104:    */     {
/* 105:138 */       c.copyFromCluster(maxC);
/* 106:139 */       if (this.sProps.bestObjFnValue < maxOF) {
/* 107:140 */         this.sProps.bestObjFnValue = maxOF;
/* 108:    */       }
/* 109:    */     }
/* 110:    */     else
/* 111:    */     {
/* 112:144 */       c.setConverged(true);
/* 113:    */     }
/* 114:148 */     return c;
/* 115:    */   }
/* 116:    */   
/* 117:    */   int[] getMoreWork(int[] lastWorkVector)
/* 118:    */   {
/* 119:153 */     if (this.iMgr == null) {
/* 120:153 */       return null;
/* 121:    */     }
/* 122:155 */     this.iMgr.direction = 1;
/* 123:156 */     this.iMgr.msgType = "GET_CLUSTER_VECTOR";
/* 124:157 */     this.iMgr.jndiServerName = this.sProps.jndiName;
/* 125:158 */     this.iMgr.svrID = this.sProps.svrID;
/* 126:159 */     this.iMgr.workVector = lastWorkVector;
/* 127:    */     try
/* 128:    */     {
/* 129:164 */       byte[] so = BunchUtilities.toByteArray(this.iMgr);
/* 130:165 */       byte[] rtdObj = this.cliCallback.callFromServerWithObj("GET_NEXT_VECTOR", so);
/* 131:166 */       this.iMgr = ((IterationManager)BunchUtilities.fromByteArray(rtdObj));
/* 132:167 */       this.currUOWSz = this.iMgr.uowSz;
/* 133:168 */       return this.iMgr.clusterVector;
/* 134:    */     }
/* 135:    */     catch (Exception ex)
/* 136:    */     {
/* 137:172 */       System.out.println("EXCEPTION - getMoreWork(): " + ex.toString());
/* 138:    */     }
/* 139:173 */     return null;
/* 140:    */   }
/* 141:    */   
/* 142:    */   void DumpWorkVector(int[] wv)
/* 143:    */   {
/* 144:179 */     System.out.println("DEBUG:  DUMPING WORK VECTOR");
/* 145:180 */     for (int i = 0; i < wv.length; i++) {
/* 146:181 */       System.out.println("   " + i + " = " + wv[i]);
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   void ReportToClientBestCluster(Cluster best)
/* 151:    */   {
/* 152:186 */     if (this.iMgr == null) {
/* 153:186 */       return;
/* 154:    */     }
boolean rc;
/* 155:188 */     this.iMgr.direction = 1;
/* 156:189 */     this.iMgr.msgType = "SEND_CLUSTER_VECTOR";
/* 157:190 */     this.iMgr.workVector = best.getClusterVector();
/* 158:191 */     this.iMgr.jndiServerName = this.sProps.jndiName;
/* 159:    */     try
/* 160:    */     {
/* 161:195 */       byte[] so = BunchUtilities.toByteArray(this.iMgr);
/* 162:196 */       rc = this.cliCallback.bCallFromServerWithObj("BEST_ITERATION_VECTOR", so);
/* 163:    */     }
/* 164:    */     catch (Exception ex)
/* 165:    */     {
/* 166:    */      
/* 167:201 */       System.out.println("EXCEPTION - ReportBestCluster(): " + ex.toString());
/* 168:    */     }
/* 169:    */   }
/* 170:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchServer.ClusterUsingVectorSAHC
 * JD-Core Version:    0.7.0.1
 */