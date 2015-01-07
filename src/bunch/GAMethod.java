/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ 
/*   5:    */ public abstract class GAMethod
/*   6:    */ {
/*   7: 35 */   protected int maxCounter_d = 0;
/*   8: 35 */   protected int increment_d = 1;
/*   9: 35 */   protected int initCounter_d = 0;
/*  10: 39 */   protected double sumOFValue_d = 0.0D;
/*  11: 39 */   protected double averageOFValue_d = 0.0D;
/*  12: 41 */   protected float mutCounter_d = 0.0F;
/*  13: 43 */   protected double maxOFValue_d = 0.0D;
/*  14: 43 */   protected double minOFValue_d = 1.0D;
/*  15:    */   protected Random randomGenerator_d;
/*  16:    */   protected Graph[] currentPopulation_d;
/*  17:    */   protected Graph[] newPopulation_d;
/*  18:    */   protected Graph bestGraph_d;
/*  19:    */   protected double crossoverThreshold_d;
/*  20:    */   protected float mutationThreshold_d;
/*  21:    */   protected double[] fitnessArray_d;
/*  22:    */   
/*  23:    */   public void setRandomNumberGenerator(Random rgen)
/*  24:    */   {
/*  25: 63 */     this.randomGenerator_d = rgen;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Random getRandomNumberGenerator()
/*  29:    */   {
/*  30: 76 */     return this.randomGenerator_d;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setPopulation(Graph[] currPop)
/*  34:    */   {
/*  35: 87 */     this.currentPopulation_d = currPop;
/*  36: 88 */     this.newPopulation_d = new Graph[this.currentPopulation_d.length];
/*  37: 89 */     this.fitnessArray_d = new double[this.currentPopulation_d.length];
/*  38: 90 */     for (int i = 0; i < this.newPopulation_d.length; i++) {
/*  39: 91 */       this.newPopulation_d[i] = this.currentPopulation_d[i].cloneGraph();
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Graph[] getCurrentPopulation()
/*  44:    */   {
/*  45:105 */     return this.currentPopulation_d;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public abstract void init();
/*  49:    */   
/*  50:    */   public abstract void selectReproduceCrossAndMutate(int paramInt);
/*  51:    */   
/*  52:    */   public double getScaledFitnessSimple(double fit)
/*  53:    */   {
/*  54:134 */     return (fit + 1.0D) / 2.0D;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void calcStatistics()
/*  58:    */   {
/*  59:146 */     this.maxOFValue_d = 0.0D;
/*  60:147 */     this.minOFValue_d = 1.0D;
/*  61:148 */     this.sumOFValue_d = 0.0D;
/*  62:150 */     for (int i = 0; i < this.currentPopulation_d.length; i++)
/*  63:    */     {
/*  64:151 */       this.currentPopulation_d[i].calculateObjectiveFunctionValue();
/*  65:152 */       this.fitnessArray_d[i] = ((this.currentPopulation_d[i].getObjectiveFunctionValue() + 1.0D) / 2.0D);
/*  66:153 */       if (this.fitnessArray_d[i] > this.maxOFValue_d)
/*  67:    */       {
/*  68:154 */         this.maxOFValue_d = this.fitnessArray_d[i];
/*  69:155 */         if (this.currentPopulation_d[i].getObjectiveFunctionValue() > getBestGraph().getObjectiveFunctionValue()) {
/*  70:157 */           setBestGraph(this.currentPopulation_d[i].cloneGraph());
/*  71:    */         }
/*  72:    */       }
/*  73:160 */       if (this.minOFValue_d > this.fitnessArray_d[i]) {
/*  74:161 */         this.minOFValue_d = this.fitnessArray_d[i];
/*  75:    */       }
/*  76:    */     }
/*  77:165 */     processFitnessValues();
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected void processFitnessValues() {}
/*  81:    */   
/*  82:    */   public void shakePopulation() {}
/*  83:    */   
/*  84:    */   public void setInitCounter(int i)
/*  85:    */   {
/*  86:204 */     this.initCounter_d = i;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getInitCounter()
/*  90:    */   {
/*  91:217 */     return this.initCounter_d;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setMaxCounter(int m)
/*  95:    */   {
/*  96:232 */     this.maxCounter_d = m;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getMaxCounter()
/* 100:    */   {
/* 101:245 */     return this.maxCounter_d;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getIncrementCounter()
/* 105:    */   {
/* 106:258 */     return this.increment_d;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setIncrementCounter(int i)
/* 110:    */   {
/* 111:273 */     this.increment_d = i;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Graph getBestGraph()
/* 115:    */   {
/* 116:286 */     return this.bestGraph_d;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setBestGraph(Graph g)
/* 120:    */   {
/* 121:301 */     this.bestGraph_d = (g != null ? g.cloneGraph() : null);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Graph[] getNewPopulation()
/* 125:    */   {
/* 126:313 */     return this.newPopulation_d;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setSumObjectiveFunctionValue(double val)
/* 130:    */   {
/* 131:327 */     this.sumOFValue_d = val;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public double getSumObjectiveFunctionValue()
/* 135:    */   {
/* 136:341 */     return this.sumOFValue_d;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setAverageObjectiveFunctionValue(double val)
/* 140:    */   {
/* 141:355 */     this.averageOFValue_d = val;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public double getAverageObjectiveFunctionValue()
/* 145:    */   {
/* 146:369 */     return this.averageOFValue_d;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setMutationThreshold(double t)
/* 150:    */   {
/* 151:383 */     this.mutationThreshold_d = ((float)t);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public double getMutationThreshold()
/* 155:    */   {
/* 156:396 */     return this.mutationThreshold_d;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setCrossoverThreshold(double t)
/* 160:    */   {
/* 161:410 */     this.crossoverThreshold_d = t;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public double getCrossoverThreshold()
/* 165:    */   {
/* 166:423 */     return this.crossoverThreshold_d;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void finishGeneration()
/* 170:    */   {
/* 171:434 */     Graph[] tmpPop = this.currentPopulation_d;
/* 172:435 */     this.currentPopulation_d = this.newPopulation_d;
/* 173:436 */     this.newPopulation_d = tmpPop;
/* 174:    */   }
/* 175:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.GAMethod
 * JD-Core Version:    0.7.0.1
 */