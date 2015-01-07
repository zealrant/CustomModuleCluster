/*   1:    */ package bunch.LoadBalancer;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ 
/*   5:    */ public class Manager
/*   6:    */ {
/*   7:    */   public static final int RE_EVAL_FREQ = 50;
/*   8:    */   public static final double ADJUSTMENT_THRESHOLD = 0.15D;
/*   9:    */   public static final int MAX_WORK_MULTIPLIER = 10;
/*  10:    */   public static final int STABILITY_THRESHOLD = 5;
/*  11: 32 */   public int baseUOWSz = 5;
/*  12: 33 */   public int minUOWSz = this.baseUOWSz;
/*  13: 34 */   public int maxUOWSz = this.baseUOWSz * 10;
/*  14: 35 */   public boolean useAdaptiveAlg = true;
/*  15: 37 */   int stabilityCounter = 0;
/*  16: 40 */   Hashtable svrList = new Hashtable();
/*  17: 42 */   ServerStats[] ssArray = null;
/*  18: 43 */   int svrCount = 0;
/*  19: 44 */   int reEvalTracker = 0;
/*  20:    */   
/*  21:    */   public int createNewServer()
/*  22:    */   {
/*  23: 51 */     ServerStats ss = new ServerStats();
/*  24: 52 */     ss.svrID = this.svrCount;
/*  25: 53 */     ss.currUOWSz = this.baseUOWSz;
/*  26:    */     
/*  27:    */ 
/*  28:    */ 
/*  29: 57 */     this.svrCount += 1;
/*  30:    */     
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34: 62 */     ServerStats[] tmpSSArray = new ServerStats[this.svrCount];
/*  35: 63 */     if (this.ssArray != null) {
/*  36: 64 */       System.arraycopy(this.ssArray, 0, tmpSSArray, 0, this.ssArray.length);
/*  37:    */     }
/*  38: 66 */     tmpSSArray[ss.svrID] = ss;
/*  39:    */     
/*  40: 68 */     this.ssArray = tmpSSArray;
/*  41:    */     
/*  42: 70 */     return ss.svrID;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean incrementWork(int sid)
/*  46:    */   {
/*  47: 75 */     ServerStats ss = this.ssArray[sid];
/*  48: 76 */     ss.totalWork += 1;
/*  49: 77 */     ss.workSinceLastAdjustment += 1;
/*  50: 78 */     this.reEvalTracker += 1;
/*  51: 80 */     if (this.reEvalTracker >= 50)
/*  52:    */     {
/*  53: 82 */       this.reEvalTracker = 0;
/*  54: 83 */       return adaptiveUpdate();
/*  55:    */     }
/*  56: 85 */     return true;
/*  57:    */   }
/*  58:    */   
/*  59:    */   private boolean adaptiveUpdate()
/*  60:    */   {
/*  61: 90 */     if (this.ssArray == null) {
/*  62: 91 */       return false;
/*  63:    */     }
/*  64: 93 */     int workCounter = 0;
/*  65: 94 */     int totalServers = this.ssArray.length;
/*  66: 96 */     if (totalServers == 0) {
/*  67: 97 */       return false;
/*  68:    */     }
/*  69: 99 */     if (totalServers == 1)
/*  70:    */     {
/*  71:101 */       this.ssArray[0].workSinceLastAdjustment = 0;
/*  72:102 */       return true;
/*  73:    */     }
/*  74:105 */     boolean madeAdjustments = false;
/*  75:    */     
/*  76:107 */     double avgExpectedWork = 50.0D / totalServers;
/*  77:109 */     for (int i = 0; i < totalServers; i++)
/*  78:    */     {
/*  79:111 */       double pctAdjustment = this.ssArray[i].workSinceLastAdjustment / avgExpectedWork;
/*  80:112 */       int uowSz = this.ssArray[i].currUOWSz;
/*  81:115 */       if (pctAdjustment >= 1.15D) {
/*  82:116 */         pctAdjustment = 1.15D;
/*  83:    */       }
/*  84:119 */       if (pctAdjustment <= 0.85D) {
/*  85:120 */         pctAdjustment = 0.85D;
/*  86:    */       }
/*  87:122 */       int newUOWSz = (int)(uowSz * pctAdjustment);
/*  88:124 */       if (newUOWSz == uowSz) {
/*  89:125 */         if (pctAdjustment > 1.0D) {
/*  90:126 */           newUOWSz++;
/*  91:127 */         } else if (pctAdjustment < 1.0D) {
/*  92:128 */           newUOWSz--;
/*  93:    */         }
/*  94:    */       }
/*  95:131 */       if (newUOWSz < this.baseUOWSz) {
/*  96:132 */         newUOWSz = this.baseUOWSz;
/*  97:    */       }
/*  98:134 */       if (newUOWSz > this.baseUOWSz * 10) {
/*  99:135 */         newUOWSz = this.baseUOWSz * 10;
/* 100:    */       }
/* 101:138 */       if (uowSz != newUOWSz) {
/* 102:140 */         madeAdjustments = true;
/* 103:    */       }
/* 104:146 */       this.ssArray[i].workSinceLastAdjustment = 0;
/* 105:147 */       this.ssArray[i].currUOWSz = newUOWSz;
/* 106:    */     }
/* 107:150 */     if (!madeAdjustments)
/* 108:    */     {
/* 109:152 */       this.stabilityCounter += 1;
/* 110:153 */       if (this.stabilityCounter >= 5)
/* 111:    */       {
/* 112:155 */         this.stabilityCounter = 0;
/* 113:156 */         for (int i = 0; i < totalServers; i++) {
/* 114:158 */           this.ssArray[i].currUOWSz += 1;
/* 115:    */         }
/* 116:    */       }
/* 117:    */     }
/* 118:    */     else
/* 119:    */     {
/* 120:165 */       this.stabilityCounter = 0;
/* 121:    */     }
/* 122:167 */     return true;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public int getCurrentUOWSz(int sid)
/* 126:    */   {
/* 127:172 */     ServerStats ss = this.ssArray[sid];
/* 128:173 */     return ss.currUOWSz;
/* 129:    */   }
/* 130:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.LoadBalancer.Manager
 * JD-Core Version:    0.7.0.1
 */