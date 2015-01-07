/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ 
/*   5:    */ public class GAClusteringMethod
/*   6:    */   extends GenericClusteringMethod
/*   7:    */ {
/*   8:    */   GAConfiguration config_d;
/*   9:    */   GAMethod method_d;
/*  10:    */   Feature[] preFeatures_d;
/*  11:    */   Feature[] features_d;
/*  12:    */   Feature[] postFeatures_d;
/*  13:    */   
/*  14:    */   public GAClusteringMethod()
/*  15:    */   {
/*  16: 54 */     setConfigurable(true);
/*  17: 55 */     setThreshold(1.0D);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public int getMaxIterations()
/*  21:    */   {
/*  22: 66 */     return getConfiguration().getNumOfIterations();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void init()
/*  26:    */   {
/*  27: 76 */     setPopSize(getConfiguration().getPopulationSize());
/*  28: 77 */     setNumOfExperiments(getConfiguration().getNumOfIterations());
/*  29: 78 */     this.config_d = ((GAConfiguration)getConfiguration());
/*  30: 79 */     this.method_d = this.config_d.getMethod();
/*  31:    */     
/*  32: 81 */     Graph graph = getGraph().cloneGraph();
/*  33: 82 */     this.method_d.setRandomNumberGenerator(new Random(System.currentTimeMillis()));
/*  34: 83 */     this.method_d.setBestGraph(graph.cloneWithRandomClusters());
/*  35: 84 */     this.method_d.getBestGraph().calculateObjectiveFunctionValue();
/*  36:    */     
/*  37: 86 */     this.currentPopulation_d = new Graph[getPopSize()];
/*  38: 88 */     for (int i = 0; i < getPopSize(); i++)
/*  39:    */     {
/*  40: 89 */       this.currentPopulation_d[i] = graph.cloneWithRandomClusters();
/*  41: 90 */       this.currentPopulation_d[i].shuffleClusters();
/*  42: 91 */       this.currentPopulation_d[i].calculateObjectiveFunctionValue();
/*  43: 93 */       if (this.currentPopulation_d[i].getObjectiveFunctionValue() > getBestGraph().getObjectiveFunctionValue()) {
/*  44: 95 */         setBestGraph(this.currentPopulation_d[i].cloneGraph());
/*  45:    */       }
/*  46:    */     }
/*  47: 99 */     this.currentPopulation_d[0] = this.currentPopulation_d[0].cloneAllNodesCluster();
/*  48:100 */     this.currentPopulation_d[0].calculateObjectiveFunctionValue();
/*  49:102 */     if (getPopSize() >= 2)
/*  50:    */     {
/*  51:104 */       this.currentPopulation_d[1] = this.currentPopulation_d[0].cloneSingleNodeClusters();
/*  52:105 */       this.currentPopulation_d[1].calculateObjectiveFunctionValue();
/*  53:    */     }
/*  54:108 */     this.method_d.setPopulation(this.currentPopulation_d);
/*  55:109 */     this.method_d.setMutationThreshold(this.config_d.getMutationThreshold());
/*  56:110 */     this.method_d.setCrossoverThreshold(this.config_d.getCrossoverThreshold());
/*  57:111 */     this.method_d.init();
/*  58:    */     
/*  59:113 */     this.preFeatures_d = this.config_d.getPreConditionFeatures();
/*  60:114 */     this.features_d = this.config_d.getFeatures();
/*  61:115 */     this.postFeatures_d = this.config_d.getPostConditionFeatures();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setBestGraph(Graph g)
/*  65:    */   {
/*  66:125 */     if (this.method_d != null) {
/*  67:126 */       this.method_d.setBestGraph(g);
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Cluster getBestCluster()
/*  72:    */   {
/*  73:135 */     Graph bestG = getBestGraph();
/*  74:136 */     Cluster c = new Cluster(bestG, bestG.getClusters());
/*  75:137 */     c.calcObjFn();
/*  76:138 */     return c;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Graph getBestGraph()
/*  80:    */   {
/*  81:148 */     return this.method_d.getBestGraph();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean nextGeneration()
/*  85:    */   {
/*  86:158 */     this.method_d.calcStatistics();
/*  87:    */     
/*  88:160 */     int parent1 = 0;int parent2 = 0;
/*  89:161 */     int crossp = 0;
/*  90:    */     
/*  91:163 */     int n = this.method_d.getInitCounter();
/*  92:164 */     int incr = this.method_d.getIncrementCounter();
/*  93:165 */     int top = this.method_d.getMaxCounter();
/*  94:167 */     if (this.preFeatures_d != null) {
/*  95:168 */       for (int i = 0; i < this.preFeatures_d.length; i++) {
/*  96:169 */         this.preFeatures_d[i].apply(this.currentPopulation_d);
/*  97:    */       }
/*  98:    */     }
/*  99:173 */     while (n < top)
/* 100:    */     {
/* 101:174 */       this.method_d.selectReproduceCrossAndMutate(n);
/* 102:176 */       if (this.features_d != null) {
/* 103:177 */         for (int i = 0; i < this.features_d.length; i++) {
/* 104:178 */           this.features_d[i].apply(this.method_d);
/* 105:    */         }
/* 106:    */       }
/* 107:182 */       n += incr;
/* 108:    */     }
/* 109:185 */     this.method_d.shakePopulation();
/* 110:    */     
/* 111:187 */     this.method_d.finishGeneration();
/* 112:    */     
/* 113:189 */     this.currentPopulation_d = this.method_d.getCurrentPopulation();
/* 114:191 */     if (this.postFeatures_d != null) {
/* 115:192 */       for (int i = 0; i < this.postFeatures_d.length; i++) {
/* 116:193 */         this.postFeatures_d[i].apply(this.currentPopulation_d);
/* 117:    */       }
/* 118:    */     }
/* 119:197 */     this.method_d.getRandomNumberGenerator().setSeed(System.currentTimeMillis());
/* 120:    */     
/* 121:199 */     return false;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getConfigurationDialogName()
/* 125:    */   {
/* 126:209 */     return "bunch.GAClusteringConfigurationDialog";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Configuration getConfiguration()
/* 130:    */   {
/* 131:219 */     if (this.configuration_d == null) {
/* 132:220 */       this.configuration_d = new GAConfiguration();
/* 133:    */     }
/* 134:222 */     return this.configuration_d;
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.GAClusteringMethod
 * JD-Core Version:    0.7.0.1
 */