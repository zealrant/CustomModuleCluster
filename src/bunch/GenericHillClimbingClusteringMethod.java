/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.io.BufferedWriter;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ 
/*   6:    */ public abstract class GenericHillClimbingClusteringMethod
/*   7:    */   extends GenericClusteringMethod2
/*   8:    */ {
/*   9:    */   HillClimbingConfiguration config_d;
/*  10:    */   
/*  11:    */   public GenericHillClimbingClusteringMethod()
/*  12:    */   {
/*  13: 51 */     setConfigurable(true);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public void init()
/*  17:    */   {
/*  18: 63 */     this.config_d = ((HillClimbingConfiguration)getConfiguration());
/*  19: 64 */     setNumOfExperiments(this.config_d.getNumOfIterations());
/*  20: 65 */     setThreshold(this.config_d.getThreshold());
/*  21: 66 */     setPopSize(this.config_d.getPopulationSize());
/*  22:    */     
/*  23: 68 */     super.init();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean nextGeneration()
/*  27:    */   {
/*  28: 79 */     long[] sequence = new long[this.currentPopulation_d.size()];
/*  29: 85 */     if (this.configuration_d.runBatch_d == true)
/*  30:    */     {
/*  31: 87 */       System.out.println("Run Batch = " + this.configuration_d.runBatch_d);
/*  32: 88 */       System.out.println("Exp Number = " + this.configuration_d.expNumber_d);
/*  33:    */     }
/*  34:    */     try
/*  35:    */     {
/*  36: 93 */       String outLine = "";
/*  37: 94 */       String sCluster = "";
/*  38: 95 */       String sAligned = "";
/*  39: 97 */       for (int i = 0; i < this.currentPopulation_d.size(); i++) {
/*  40: 98 */         sequence[i] = 0L;
/*  41:    */       }
/*  42:126 */       boolean end = false;
/*  43:127 */       while (!end)
/*  44:    */       {
/*  45:129 */         end = true;
/*  46:130 */         for (int i = 0; i < this.currentPopulation_d.size(); i++)
/*  47:    */         {
/*  48:132 */           if (!this.currentPopulation_d.getCluster(i).isMaximum())
/*  49:    */           {
/*  50:134 */             if (this.configuration_d.runBatch_d == true)
/*  51:    */             {
/*  52:136 */               int exp = this.configuration_d.expNumber_d;
/*  53:137 */               sCluster = "";
/*  54:138 */               sAligned = "";
/*  55:139 */               int[] n = this.currentPopulation_d.getCluster(i).getClusterVector();
/*  56:    */               
/*  57:141 */               int[] c = new int[n.length];
/*  58:143 */               for (int z = 0; z < n.length; z++) {
/*  59:144 */                 c[z] = n[z];
/*  60:    */               }
/*  61:146 */               realignClusters(c);
/*  62:148 */               for (int zz = 0; zz < n.length; zz++)
/*  63:    */               {
/*  64:150 */                 sAligned = sAligned + c[zz] + "|";
/*  65:151 */                 sCluster = sCluster + n[zz] + "|";
/*  66:    */               }
/*  67:153 */               sequence[i] += 1L;
/*  68:154 */               outLine = exp + "," + i + "," + sequence[i] + "," + this.currentPopulation_d.getCluster(i).getObjFnValue() + "," + sCluster + "," + sAligned;
/*  69:155 */               this.configuration_d.writer_d.write(outLine + "\r\n");
/*  70:    */             }
/*  71:159 */             getLocalMaxGraph(this.currentPopulation_d.getCluster(i));
/*  72:    */           }
/*  73:162 */           if (!this.currentPopulation_d.getCluster(i).isMaximum()) {
/*  74:164 */             end = false;
/*  75:    */           }
/*  76:166 */           if (this.currentPopulation_d.getCluster(i).getObjFnValue() > getBestCluster().getObjFnValue()) {
/*  77:169 */             setBestCluster(this.currentPopulation_d.getCluster(i).cloneCluster());
/*  78:    */           }
/*  79:    */         }
/*  80:    */       }
/*  81:173 */       return end;
/*  82:    */     }
/*  83:    */     catch (Exception e) {}
/*  84:176 */     return false;
/*  85:    */   }
/*  86:    */   
/*  87:    */   private void realignClusters(int[] c)
/*  88:    */   {
/*  89:185 */     int[] map = new int[c.length];
/*  90:186 */     int index = 0;
/*  91:188 */     for (int i = 0; i < c.length; i++) {
/*  92:189 */       map[i] = -1;
/*  93:    */     }
/*  94:191 */     for (int j = 0; j < c.length; j++)
/*  95:    */     {
/*  96:193 */       int clus = c[j];
/*  97:195 */       if (map[clus] == -1)
/*  98:    */       {
/*  99:197 */         index++;
/* 100:198 */         map[clus] = index;
/* 101:    */       }
/* 102:    */     }
/* 103:202 */     for (int k = 0; k < c.length; k++) {
/* 104:204 */       c[k] = map[c[k]];
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected abstract Cluster getLocalMaxGraph(Cluster paramCluster);
/* 109:    */   
/* 110:    */   public void reInit()
/* 111:    */   {
/* 112:224 */     this.currentPopulation_d.shuffle();
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getConfigurationDialogName()
/* 116:    */   {
/* 117:240 */     return "bunch.HillClimbingClusteringConfigurationDialog";
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Configuration getConfiguration()
/* 121:    */   {
/* 122:256 */     if (this.configuration_d == null) {
/* 123:257 */       this.configuration_d = new HillClimbingConfiguration();
/* 124:    */     }
/* 125:259 */     return this.configuration_d;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setConfiguration(HillClimbingConfiguration c)
/* 129:    */   {
/* 130:266 */     this.configuration_d = c;
/* 131:    */   }
/* 132:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.GenericHillClimbingClusteringMethod
 * JD-Core Version:    0.7.0.1
 */