/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ public abstract class GenericClusteringMethod2
/*   4:    */   extends ClusteringMethod2
/*   5:    */ {
/*   6: 31 */   public static int DEFAULT_NUM_EXPERIMENTS = 200;
/*   7: 32 */   public static int DEFAULT_POP_SIZE = 25;
/*   8: 33 */   public static double DEFAULT_THRESHOLD = 0.1D;
/*   9:    */   protected Population currentPopulation_d;
/*  10: 36 */   protected int popSize_d = DEFAULT_POP_SIZE;
/*  11: 38 */   protected int numExperiments_d = DEFAULT_NUM_EXPERIMENTS;
/*  12: 39 */   protected double threshold_d = DEFAULT_THRESHOLD;
/*  13: 40 */   protected double bestOFValue_d = 0.0D;
/*  14:    */   
/*  15:    */   public GenericClusteringMethod2()
/*  16:    */   {
/*  17: 48 */     setPopSize(DEFAULT_POP_SIZE);
/*  18: 49 */     setThreshold(DEFAULT_THRESHOLD);
/*  19: 50 */     setNumOfExperiments(DEFAULT_NUM_EXPERIMENTS);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void init()
/*  23:    */   {
/*  24: 60 */     this.currentPopulation_d = new Population(getGraph());
/*  25: 61 */     this.currentPopulation_d.genPopulation(getPopSize());
/*  26: 63 */     if (getBestCluster() == null) {
/*  27: 64 */       setBestCluster(this.currentPopulation_d.getCluster(0).cloneCluster());
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void reInit() {}
/*  32:    */   
/*  33:    */   public void run()
/*  34:    */   {
/*  35:103 */     init();
/*  36:    */     
/*  37:105 */     int generationsSinceLastChange = 0;
/*  38:    */     
/*  39:    */ 
/*  40:108 */     Cluster c2 = this.currentPopulation_d.getCluster(0);
/*  41:110 */     if (c2.getObjFnValue() > getBestCluster().getObjFnValue()) {
/*  42:111 */       setBestCluster(c2);
/*  43:    */     }
/*  44:114 */     long t = System.currentTimeMillis();
/*  45:115 */     IterationEvent ev = new IterationEvent(this);
/*  46:116 */     this.bestOFValue_d = getBestCluster().getObjFnValue();
/*  47:119 */     for (int x = 0; x < this.numExperiments_d; x++)
/*  48:    */     {
/*  49:122 */       boolean end = nextGeneration();
/*  50:124 */       if (this.bestOFValue_d != getBestCluster().getObjFnValue())
/*  51:    */       {
/*  52:126 */         setBestObjectiveFunctionValue(getBestCluster().getObjFnValue());
/*  53:127 */         generationsSinceLastChange = x;
/*  54:    */       }
/*  55:130 */       if (end)
/*  56:    */       {
/*  57:132 */         if (x - generationsSinceLastChange > this.numExperiments_d * getThreshold()) {
/*  58:    */           break;
/*  59:    */         }
/*  60:138 */         ev.setIteration(x - generationsSinceLastChange);
/*  61:139 */         ev.setOverallIteration(x);
/*  62:140 */         fireIterationEvent(ev);
/*  63:141 */         reInit();
/*  64:    */       }
/*  65:    */       else
/*  66:    */       {
/*  67:146 */         ev.setIteration(x);
/*  68:147 */         ev.setOverallIteration(x);
/*  69:148 */         fireIterationEvent(ev);
/*  70:    */       }
/*  71:151 */       setElapsedTime((System.currentTimeMillis() - t) / 1000.0D);
/*  72:    */     }
/*  73:153 */     ev.setIteration(getMaxIterations());
/*  74:154 */     ev.setOverallIteration(getMaxIterations());
/*  75:    */     
/*  76:156 */     fireIterationEvent(ev);
/*  77:157 */     setElapsedTime((System.currentTimeMillis() - t) / 1000.0D);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public abstract boolean nextGeneration();
/*  81:    */   
/*  82:    */   public void setThreshold(double t)
/*  83:    */   {
/*  84:182 */     this.threshold_d = t;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public double getThreshold()
/*  88:    */   {
/*  89:196 */     return this.threshold_d;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getMaxIterations()
/*  93:    */   {
/*  94:211 */     return (int)(getNumOfExperiments() * getThreshold());
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setNumOfExperiments(int max)
/*  98:    */   {
/*  99:225 */     this.numExperiments_d = max;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getNumOfExperiments()
/* 103:    */   {
/* 104:239 */     return this.numExperiments_d;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setPopSize(int psz)
/* 108:    */   {
/* 109:252 */     this.popSize_d = psz;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int getPopSize()
/* 113:    */   {
/* 114:265 */     return this.popSize_d;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setBestObjectiveFunctionValue(double v)
/* 118:    */   {
/* 119:278 */     this.bestOFValue_d = v;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public double getBestObjectiveFunctionValue()
/* 123:    */   {
/* 124:291 */     return this.bestOFValue_d;
/* 125:    */   }
/* 126:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.GenericClusteringMethod2
 * JD-Core Version:    0.7.0.1
 */