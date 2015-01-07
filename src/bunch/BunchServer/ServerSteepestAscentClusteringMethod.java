/*   1:    */ package bunch.BunchServer;
/*   2:    */ 
/*   3:    */ import bunch.Cluster;
/*   4:    */ import bunch.Configuration;
/*   5:    */ import bunch.GenericDistribHillClimbingClusteringMethod;
/*   6:    */ import bunch.HillClimbingConfiguration;
/*   7:    */ import bunch.util.BunchUtilities;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ 
/*  10:    */ public class ServerSteepestAscentClusteringMethod
/*  11:    */   extends GenericDistribHillClimbingClusteringMethod
/*  12:    */ {
/*  13: 25 */   int[] currWork = null;
/*  14: 26 */   int pos = -1;
/*  15: 26 */   int maxPos = -1;
/*  16: 28 */   FindNeighbor nServer = new FindNeighbor();
/*  17:    */   
/*  18:    */   protected Cluster getLocalMaxGraph(Cluster c)
/*  19:    */   {
/*  20: 37 */     System.out.print("IN:  " + c.getObjFnValue() + "  ");
/*  21:    */     
/*  22: 39 */     Cluster maxC = c.cloneCluster();
/*  23: 40 */     Cluster intermC = c.cloneCluster();
/*  24:    */     
/*  25: 42 */     double maxOF = maxC.getObjFnValue();
/*  26: 43 */     double originalMax = maxOF;
/*  27:    */     
/*  28: 45 */     int[] clustNames = c.getClusterNames();
/*  29:    */     
/*  30: 47 */     int[] clusters = c.getClusterVector();
/*  31:    */     
/*  32: 49 */     int[] maxClust = maxC.getClusterVector();
/*  33:    */     
/*  34: 51 */     boolean[] locks = c.getLocks();
/*  35:    */     
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41: 58 */     this.pos = 0;
/*  42: 59 */     this.maxPos = clusters.length;
/*  43:    */     
/*  44:    */ 
/*  45: 62 */     int[] workVector = null;
/*  46: 64 */     while ((workVector = getMoreWork()) != null)
/*  47:    */     {
/*  48: 66 */       intermC = this.nServer.clusterWorklist(c, intermC, clustNames, locks, workVector);
/*  49: 67 */       if (BunchUtilities.compareGreater(intermC.getObjFnValue(), maxOF))
/*  50:    */       {
/*  51: 69 */         maxC.copyFromCluster(intermC);
/*  52: 70 */         maxOF = maxC.getObjFnValue();
/*  53:    */       }
/*  54:    */     }
/*  55: 74 */     if (BunchUtilities.compareGreater(maxOF, originalMax)) {
/*  56: 76 */       c.copyFromCluster(maxC);
/*  57:    */     } else {
/*  58: 80 */       c.setConverged(true);
/*  59:    */     }
/*  60: 84 */     System.out.println("OUT:  " + c.getObjFnValue() + "  ");
/*  61:    */     
/*  62: 86 */     return c;
/*  63:    */   }
/*  64:    */   
/*  65:    */   int[] getMoreWork()
/*  66:    */   {
/*  67: 91 */     int start = this.pos;
/*  68: 92 */     int end = Math.min(this.pos + 5, this.maxPos);
/*  69:    */     
/*  70: 94 */     int delta = end - start;
/*  71: 96 */     if (delta == 0) {
/*  72: 97 */       return null;
/*  73:    */     }
/*  74: 99 */     int[] workArea = new int[delta];
/*  75:100 */     for (int i = 0; i < delta; i++) {
/*  76:101 */       workArea[i] = (this.pos++);
/*  77:    */     }
/*  78:103 */     return workArea;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Configuration getConfiguration()
/*  82:    */   {
/*  83:110 */     boolean reconf = false;
/*  84:111 */     if (this.configuration_d == null) {
/*  85:112 */       reconf = true;
/*  86:    */     }
/*  87:115 */     HillClimbingConfiguration hc = (HillClimbingConfiguration)super.getConfiguration();
/*  88:117 */     if (reconf)
/*  89:    */     {
/*  90:118 */       hc.setThreshold(0.1D);
/*  91:119 */       hc.setNumOfIterations(100);
/*  92:120 */       hc.setPopulationSize(5);
/*  93:    */     }
/*  94:122 */     return hc;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setDefaultConfiguration()
/*  98:    */   {
/*  99:128 */     HillClimbingConfiguration hc = (HillClimbingConfiguration)super.getConfiguration();
/* 100:    */     
/* 101:130 */     hc.setThreshold(0.1D);
/* 102:131 */     hc.setNumOfIterations(100);
/* 103:132 */     hc.setPopulationSize(5);
/* 104:    */     
/* 105:134 */     setConfiguration(hc);
/* 106:    */   }
/* 107:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchServer.ServerSteepestAscentClusteringMethod
 * JD-Core Version:    0.7.0.1
 */