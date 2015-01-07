/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ public abstract class ClusteringMethod
/*   4:    */   implements Runnable
/*   5:    */ {
/*   6:    */   private IterationListener listener_d;
/*   7:    */   private Graph graph_d;
/*   8:    */   private Graph bestGraph_d;
/*   9:    */   private Cluster bestCluster;
/*  10: 41 */   private boolean isConfigurable_d = false;
/*  11:    */   protected Configuration configuration_d;
/*  12: 43 */   protected double elapsedTime_d = 0.0D;
/*  13:    */   
/*  14:    */   public void initialize()
/*  15:    */   {
/*  16: 60 */     setBestGraph(null);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public void setGraph(Graph g)
/*  20:    */   {
/*  21: 73 */     this.graph_d = g;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Graph getGraph()
/*  25:    */   {
/*  26: 86 */     return this.graph_d;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setBestGraph(Graph g)
/*  30:    */   {
/*  31:100 */     this.bestGraph_d = g;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Graph getBestGraph()
/*  35:    */   {
/*  36:114 */     return this.bestGraph_d;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public double getBestObjectiveFunctionValue()
/*  40:    */   {
/*  41:126 */     return this.bestGraph_d.getObjectiveFunctionValue();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public double getElapsedTime()
/*  45:    */   {
/*  46:139 */     return this.elapsedTime_d;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setElapsedTime(double l)
/*  50:    */   {
/*  51:153 */     this.elapsedTime_d = l;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIterationListener(IterationListener il)
/*  55:    */   {
/*  56:169 */     this.listener_d = il;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public IterationListener getIterationListener()
/*  60:    */   {
/*  61:183 */     return this.listener_d;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void fireIterationEvent(IterationEvent e)
/*  65:    */   {
/*  66:193 */     if (this.listener_d != null) {
/*  67:195 */       this.listener_d.newIteration(e);
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void fireExpermentEvent(IterationEvent e)
/*  72:    */   {
/*  73:206 */     if (this.listener_d != null) {
/*  74:208 */       this.listener_d.newExperiment(e);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public abstract int getMaxIterations();
/*  79:    */   
/*  80:    */   public abstract Cluster getBestCluster();
/*  81:    */   
/*  82:    */   public boolean isConfigurable()
/*  83:    */   {
/*  84:236 */     return this.isConfigurable_d;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setConfigurable(boolean isC)
/*  88:    */   {
/*  89:250 */     this.isConfigurable_d = isC;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setConfiguration(Configuration c)
/*  93:    */   {
/*  94:265 */     this.configuration_d = c;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Configuration getConfiguration()
/*  98:    */   {
/*  99:278 */     return this.configuration_d;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setDefaultConfiguration() {}
/* 103:    */   
/* 104:    */   public String getConfigurationDialogName()
/* 105:    */   {
/* 106:299 */     return null;
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.ClusteringMethod
 * JD-Core Version:    0.7.0.1
 */