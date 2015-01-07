/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ 
/*   5:    */ public class GARouletteWheelMethod
/*   6:    */   extends GAMethod
/*   7:    */ {
/*   8:    */   protected int[] tempArray_d;
/*   9:    */   protected int shakeUpCount_d;
/*  10:    */   
/*  11:    */   public void init()
/*  12:    */   {
/*  13: 41 */     setIncrementCounter(2);
/*  14: 42 */     setInitCounter(0);
/*  15: 43 */     setMaxCounter(this.currentPopulation_d.length - 1);
/*  16: 44 */     this.tempArray_d = new int[this.currentPopulation_d[0].getClusters().length];
/*  17: 45 */     this.shakeUpCount_d = -1500;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void selectReproduceCrossAndMutate(int pos)
/*  21:    */   {
/*  22: 56 */     Graph parent1 = null;Graph parent2 = null;
/*  23:    */     
/*  24:    */ 
/*  25: 59 */     double partsum = 0.0D;
/*  26: 60 */     double rand1 = this.sumOFValue_d * this.randomGenerator_d.nextFloat();
/*  27: 61 */     double rand2 = this.sumOFValue_d * this.randomGenerator_d.nextFloat();
/*  28: 63 */     for (int i = 0; i < this.fitnessArray_d.length; i++)
/*  29:    */     {
/*  30: 64 */       if ((parent1 == null) && (rand1 <= this.fitnessArray_d[i])) {
/*  31: 65 */         parent1 = this.currentPopulation_d[i];
/*  32:    */       }
/*  33: 67 */       if ((parent2 == null) && (rand2 <= this.fitnessArray_d[i])) {
/*  34: 68 */         parent2 = this.currentPopulation_d[i];
/*  35:    */       }
/*  36: 70 */       if ((parent1 != null) && (parent2 != null)) {
/*  37:    */         break;
/*  38:    */       }
/*  39:    */     }
/*  40: 75 */     int[] p1c = parent1.getClusters();
/*  41: 76 */     int[] p2c = parent2.getClusters();
/*  42:    */     
/*  43: 78 */     int[] np1c = this.newPopulation_d[pos].getClusters();
/*  44: 79 */     int[] np2c = this.newPopulation_d[(pos + 1)].getClusters();
/*  45: 80 */     System.arraycopy(p1c, 0, np1c, 0, p1c.length);
/*  46: 81 */     System.arraycopy(p2c, 0, np2c, 0, p2c.length);
/*  47: 84 */     if (this.randomGenerator_d.nextFloat() < this.crossoverThreshold_d)
/*  48:    */     {
/*  49: 85 */       int crossp = (int)(this.randomGenerator_d.nextFloat() * (np1c.length - 1));
/*  50: 86 */       cross(np1c, np2c, crossp);
/*  51:    */     }
/*  52: 90 */     for (int i = 0; i < np1c.length; i++) {
/*  53: 91 */       if (this.randomGenerator_d.nextFloat() < this.mutationThreshold_d) {
/*  54: 92 */         mutate(np1c, i);
/*  55:    */       }
/*  56:    */     }
/*  57: 97 */     for (int i = 0; i < np2c.length; i++) {
/*  58: 98 */       if (this.randomGenerator_d.nextFloat() < this.mutationThreshold_d) {
/*  59: 99 */         mutate(np2c, i);
/*  60:    */       }
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected void processFitnessValues()
/*  65:    */   {
/*  66:111 */     this.sumOFValue_d = 0.0D;
/*  67:112 */     int nummax = 0;
/*  68:113 */     for (int i = 0; i < this.fitnessArray_d.length; i++) {
/*  69:114 */       if (this.fitnessArray_d[i] == this.maxOFValue_d) {
/*  70:115 */         nummax++;
/*  71:    */       }
/*  72:    */     }
/*  73:119 */     if (nummax > this.currentPopulation_d.length / 2)
/*  74:    */     {
/*  75:120 */       nummax = (int)(nummax * 0.6D);
/*  76:122 */       for (int i = 0; (i < this.fitnessArray_d.length) && (nummax > 0); i++)
/*  77:    */       {
/*  78:123 */         if (this.fitnessArray_d[i] == this.maxOFValue_d)
/*  79:    */         {
/*  80:124 */           this.currentPopulation_d[i].shuffleClusters();
/*  81:125 */           this.currentPopulation_d[i].calculateObjectiveFunctionValue();
/*  82:126 */           this.fitnessArray_d[i] = ((this.currentPopulation_d[i].getObjectiveFunctionValue() + 1.0D) / 2.0D);
/*  83:127 */           nummax--;
/*  84:    */         }
/*  85:129 */         if (this.fitnessArray_d[i] > this.maxOFValue_d)
/*  86:    */         {
/*  87:130 */           this.maxOFValue_d = this.fitnessArray_d[i];
/*  88:131 */           if (this.currentPopulation_d[i].getObjectiveFunctionValue() > getBestGraph().getObjectiveFunctionValue()) {
/*  89:133 */             setBestGraph(this.currentPopulation_d[i].cloneGraph());
/*  90:    */           }
/*  91:    */         }
/*  92:136 */         if (this.minOFValue_d > this.fitnessArray_d[i]) {
/*  93:137 */           this.minOFValue_d = this.fitnessArray_d[i];
/*  94:    */         }
/*  95:    */       }
/*  96:    */     }
/*  97:142 */     double prior = getScaledFitness(this.fitnessArray_d[0]);
/*  98:143 */     this.fitnessArray_d[0] = prior;
/*  99:144 */     for (int i = 1; i < this.fitnessArray_d.length; i++)
/* 100:    */     {
/* 101:145 */       this.fitnessArray_d[i] = (getScaledFitness(this.fitnessArray_d[i]) + prior);
/* 102:146 */       prior = this.fitnessArray_d[i];
/* 103:    */     }
/* 104:148 */     this.sumOFValue_d = this.fitnessArray_d[(this.fitnessArray_d.length - 1)];
/* 105:    */   }
/* 106:    */   
/* 107:    */   public double getScaledFitness(double fit)
/* 108:    */   {
/* 109:158 */     double mult = 1.0D;
/* 110:159 */     double cdiff = fit - this.minOFValue_d;
/* 111:160 */     double odiff = this.maxOFValue_d - this.minOFValue_d;
/* 112:161 */     if (odiff == 0.0D)
/* 113:    */     {
/* 114:162 */       odiff = 1.0D;
/* 115:163 */       cdiff = 1.0D;
/* 116:    */     }
/* 117:165 */     mult = 1.0D * (cdiff / odiff);
/* 118:166 */     return mult;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void mutate(int[] c, int pos)
/* 122:    */   {
/* 123:176 */     c[pos] = ((int)(this.randomGenerator_d.nextFloat() * (c.length - 1)));
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void shakePopulation() {}
/* 127:    */   
/* 128:    */   public void cross(int[] c1c, int[] c2c, int crossp)
/* 129:    */   {
/* 130:192 */     System.arraycopy(c1c, crossp, this.tempArray_d, crossp, c1c.length - crossp);
/* 131:193 */     System.arraycopy(c2c, crossp, c1c, crossp, c1c.length - crossp);
/* 132:194 */     System.arraycopy(this.tempArray_d, crossp, c2c, crossp, c1c.length - crossp);
/* 133:    */   }
/* 134:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.GARouletteWheelMethod
 * JD-Core Version:    0.7.0.1
 */