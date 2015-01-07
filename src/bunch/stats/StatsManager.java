/*   1:    */ package bunch.stats;
/*   2:    */ 
/*   3:    */ import java.io.BufferedWriter;
/*   4:    */ import java.io.FileWriter;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ 
/*   7:    */ public class StatsManager
/*   8:    */ {
/*   9: 34 */   public static String logFileNm = "BunchStats.log";
/*  10: 35 */   long mqCalculations = 0L;
/*  11: 36 */   long calcAllCalcs = 0L;
/*  12: 37 */   long calcIncrCalcs = 0L;
/*  13: 38 */   long exhaustiveTotal = -1L;
/*  14: 39 */   long exhaustiveFinished = 0L;
/*  15: 40 */   long simulatedAnnealingOverrides = 0L;
/*  16: 42 */   boolean collectClusteringDetails = false;
/*  17:    */   private static StatsManager singletonObj;
/*  18:    */   
/*  19:    */   public static StatsManager getInstance()
/*  20:    */   {
/*  21: 51 */     if (singletonObj == null) {
/*  22: 52 */       synchronized (StatsManager.class)
/*  23:    */       {
/*  24: 53 */         if (singletonObj == null) {
/*  25: 54 */           singletonObj = new StatsManager();
/*  26:    */         }
/*  27:    */       }
/*  28:    */     }
/*  29: 58 */     return singletonObj;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static void cleanup()
/*  33:    */   {
/*  34: 62 */     singletonObj = null;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setCollectClusteringDetails(boolean b)
/*  38:    */   {
/*  39: 65 */     this.collectClusteringDetails = b;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean getCollectClusteringDetails()
/*  43:    */   {
/*  44: 68 */     return this.collectClusteringDetails;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public long getMQCalculations()
/*  48:    */   {
/*  49: 71 */     return this.mqCalculations;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public long incrMQCalculations()
/*  53:    */   {
/*  54: 74 */     return ++this.mqCalculations;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public long incrCalcAllCalcs()
/*  58:    */   {
/*  59: 77 */     return ++this.calcAllCalcs;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public long getCalcAllCalcs()
/*  63:    */   {
/*  64: 80 */     return this.calcAllCalcs;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public long incrCalcIncrCalcs()
/*  68:    */   {
/*  69: 83 */     return ++this.calcIncrCalcs;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public long getCalcIncrCalcs()
/*  73:    */   {
/*  74: 86 */     return this.calcIncrCalcs;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setExhaustiveTotal(int t)
/*  78:    */   {
/*  79: 89 */     this.exhaustiveTotal = t;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public long getExhaustiveTotal()
/*  83:    */   {
/*  84: 92 */     return this.exhaustiveTotal;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public long getExhaustiveFinished()
/*  88:    */   {
/*  89: 95 */     return this.exhaustiveFinished;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void incrExhaustiveFinished()
/*  93:    */   {
/*  94: 98 */     this.exhaustiveFinished += 1L;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void clearExhaustiveFinished()
/*  98:    */   {
/*  99:101 */     this.exhaustiveFinished = 0L;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getExhaustivePct()
/* 103:    */   {
/* 104:105 */     if (this.exhaustiveTotal <= 0L) {
/* 105:105 */       return 0;
/* 106:    */     }
/* 107:107 */     double pct = this.exhaustiveFinished / this.exhaustiveTotal;
/* 108:108 */     pct *= 100.0D;
/* 109:109 */     int iPct = (int)pct;
/* 110:110 */     return iPct;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public long getSAOverrides()
/* 114:    */   {
/* 115:114 */     return this.simulatedAnnealingOverrides;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void incrSAOverrides()
/* 119:    */   {
/* 120:117 */     this.simulatedAnnealingOverrides += 1L;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean dumpStatsLog()
/* 124:    */   {
/* 125:    */     try
/* 126:    */     {
/* 127:123 */       BufferedWriter writer = new BufferedWriter(new FileWriter(logFileNm));
/* 128:124 */       writer.write("Total MQ Calculations:  " + this.mqCalculations + "\n");
/* 129:125 */       writer.write("Simulated Annealing Overrides: " + this.simulatedAnnealingOverrides + "\n");
/* 130:126 */       writer.close();
/* 131:    */     }
/* 132:    */     catch (Exception e)
/* 133:    */     {
/* 134:130 */       System.out.println("Error creating the logfile at location: " + logFileNm);
/* 135:131 */       return false;
/* 136:    */     }
/* 137:133 */     return true;
/* 138:    */   }
/* 139:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.stats.StatsManager
 * JD-Core Version:    0.7.0.1
 */