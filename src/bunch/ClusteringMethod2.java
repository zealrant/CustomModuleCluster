/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ public abstract class ClusteringMethod2
/*   4:    */   extends ClusteringMethod
/*   5:    */ {
/*   6:    */   private IterationListener listener_d;
/*   7:    */   private Graph graph_d;
/*   8:    */   private Cluster bestCluster_d;
/*   9: 42 */   private boolean isConfigurable_d = false;
/*  10:    */   protected Configuration configuration_d;
/*  11: 44 */   protected double elapsedTime_d = 0.0D;
/*  12:    */   
/*  13:    */   public void initialize()
/*  14:    */   {
/*  15: 61 */     setBestCluster(null);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void setGraph(Graph g)
/*  19:    */   {
/*  20: 74 */     this.graph_d = g;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Graph getGraph()
/*  24:    */   {
/*  25: 87 */     return this.graph_d;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setBestCluster(Cluster c)
/*  29:    */   {
/*  30:101 */     this.bestCluster_d = c;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Cluster getBestCluster()
/*  34:    */   {
/*  35:115 */     return this.bestCluster_d;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Graph getBestGraph()
/*  39:    */   {
/*  40:129 */     Cluster best = getBestCluster();
/*  41:130 */     this.graph_d.setClusters(best.getClusterVector());
/*  42:131 */     this.graph_d.setObjectiveFunctionValue(best.getObjFnValue());
/*  43:132 */     return this.graph_d;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public double getBestObjectiveFunctionValue()
/*  47:    */   {
/*  48:144 */     return this.bestCluster_d.getObjFnValue();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public double getElapsedTime()
/*  52:    */   {
/*  53:157 */     return this.elapsedTime_d;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setElapsedTime(double l)
/*  57:    */   {
/*  58:171 */     this.elapsedTime_d = l;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIterationListener(IterationListener il)
/*  62:    */   {
/*  63:187 */     this.listener_d = il;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public IterationListener getIterationListener()
/*  67:    */   {
/*  68:201 */     return this.listener_d;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void fireIterationEvent(IterationEvent e)
/*  72:    */   {
/*  73:211 */     if (this.listener_d != null) {
/*  74:213 */       this.listener_d.newIteration(e);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void fireExpermentEvent(IterationEvent e)
/*  79:    */   {
/*  80:224 */     if (this.listener_d != null) {
/*  81:226 */       this.listener_d.newExperiment(e);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public abstract int getMaxIterations();
/*  86:    */   
/*  87:    */   public boolean isConfigurable()
/*  88:    */   {
/*  89:252 */     return this.isConfigurable_d;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setConfigurable(boolean isC)
/*  93:    */   {
/*  94:266 */     this.isConfigurable_d = isC;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setConfiguration(Configuration c)
/*  98:    */   {
/*  99:281 */     this.configuration_d = c;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Configuration getConfiguration()
/* 103:    */   {
/* 104:294 */     return this.configuration_d;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setDefaultConfiguration() {}
/* 108:    */   
/* 109:    */   public String getConfigurationDialogName()
/* 110:    */   {
/* 111:315 */     return null;
/* 112:    */   }
/* 113:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.ClusteringMethod2
 * JD-Core Version:    0.7.0.1
 */