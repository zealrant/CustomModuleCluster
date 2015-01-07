/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import bunch.stats.StatsManager;
/*   4:    */ import bunch.util.BunchUtilities;
/*   5:    */ import java.awt.Dimension;
/*   6:    */ import java.awt.Frame;
/*   7:    */ import java.awt.Point;
/*   8:    */ import java.beans.Beans;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.Hashtable;
/*  11:    */ 
/*  12:    */ public class SASimpleTechnique
/*  13:    */   extends SATechnique
/*  14:    */ {
/*  15:    */   public static final String SET_INITIAL_TEMP_KEY = "InitialTemp";
/*  16:    */   public static final String SET_ALPHA_KEY = "Alpha";
/*  17:    */   public static final String SET_DELTA_MQ = "DeltaMQ";
/*  18: 48 */   double configuredTemp = 1.0D;
/*  19: 49 */   double configuredAlpha = 0.85D;
/*  20: 51 */   double initTemp = 1.0D;
/*  21: 52 */   double alpha = 0.85D;
/*  22: 53 */   double currTemp = this.initTemp;
/*  23: 54 */   boolean initialized = true;
/*  24:    */   
/*  25:    */   public Hashtable getConfig()
/*  26:    */   {
/*  27: 61 */     Double Alpha = new Double(this.alpha);
/*  28: 62 */     Double Temp = new Double(this.initTemp);
/*  29: 63 */     Hashtable h = new Hashtable();
/*  30: 64 */     h.clear();
/*  31: 65 */     h.put("InitialTemp", Temp);
/*  32: 66 */     h.put("Alpha", Alpha);
/*  33: 67 */     return h;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean setConfig(Hashtable h)
/*  37:    */   {
/*  38: 72 */     Double Alpha = (Double)h.get("Alpha");
/*  39: 73 */     Double Temp = (Double)h.get("InitialTemp");
/*  40: 75 */     if ((Alpha == null) || (Temp == null))
/*  41:    */     {
/*  42: 77 */       this.initialized = false;
/*  43: 78 */       System.out.println("setConfig() - Setting initialized to false");
/*  44: 79 */       return false;
/*  45:    */     }
/*  46: 82 */     this.alpha = Alpha.doubleValue();
/*  47: 83 */     this.initTemp = Temp.doubleValue();
/*  48: 84 */     this.currTemp = this.initTemp;
/*  49:    */     
/*  50: 86 */     this.configuredTemp = this.initTemp;
/*  51: 87 */     this.configuredAlpha = this.alpha;
/*  52:    */     
/*  53: 89 */     this.initialized = true;
/*  54:    */     
/*  55: 91 */     return true;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getConfigDialogName()
/*  59:    */   {
/*  60: 95 */     return "bunch.SASimpleTechniqueDialog";
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean configureUsingDialog(Frame parent)
/*  64:    */   {
/*  65: 99 */     boolean rc = false;
/*  66:    */     
/*  67:101 */     SASimpleTechniqueDialog dlg = null;
/*  68:    */     try
/*  69:    */     {
/*  70:104 */       dlg = (SASimpleTechniqueDialog)Beans.instantiate(null, getConfigDialogName());
/*  71:105 */       dlg.setModal(true);
/*  72:106 */       dlg.setTitle("Simulated Annealing Simple Technique Conifugration");
/*  73:107 */       dlg.setConfiguration(getConfig());
/*  74:    */     }
/*  75:    */     catch (Exception ex)
/*  76:    */     {
/*  77:113 */       ex.printStackTrace();
/*  78:114 */       return false;
/*  79:    */     }
/*  80:118 */     Dimension dlgSize = dlg.getPreferredSize();
/*  81:119 */     Dimension frmSize = parent.getSize();
/*  82:120 */     Point loc = parent.getLocation();
/*  83:121 */     dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
/*  84:    */     
/*  85:123 */     dlg.setSATechnique(this);
/*  86:124 */     dlg.setVisible(true);
/*  87:    */     
/*  88:126 */     return true;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean configure()
/*  92:    */   {
/*  93:131 */     return true;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean init(Hashtable h)
/*  97:    */   {
/*  98:136 */     Double dTemp = (Double)h.get("InitialTemp");
/*  99:137 */     Double dAlpha = (Double)h.get("Alpha");
/* 100:139 */     if ((dTemp == null) || (dAlpha == null))
/* 101:    */     {
/* 102:141 */       this.initialized = false;
/* 103:142 */       System.out.println("init() - Setting initialized to false");
/* 104:143 */       return false;
/* 105:    */     }
/* 106:146 */     this.initTemp = dTemp.doubleValue();
/* 107:147 */     this.alpha = dAlpha.doubleValue();
/* 108:148 */     this.currTemp = this.initTemp;
/* 109:    */     
/* 110:150 */     this.configuredTemp = this.initTemp;
/* 111:151 */     this.configuredAlpha = this.alpha;
/* 112:    */     
/* 113:153 */     this.initialized = true;
/* 114:154 */     return true;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void reset()
/* 118:    */   {
/* 119:159 */     this.stats = StatsManager.getInstance();
/* 120:160 */     this.initTemp = this.configuredTemp;
/* 121:161 */     this.alpha = this.configuredAlpha;
/* 122:162 */     this.currTemp = this.initTemp;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean changeTemp(Hashtable args)
/* 126:    */   {
/* 127:167 */     if (!this.initialized) {
/* 128:167 */       return false;
/* 129:    */     }
/* 130:171 */     this.currTemp *= this.alpha;
/* 131:172 */     return true;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean accept(Hashtable args)
/* 135:    */   {
/* 136:177 */     if (!this.initialized) {
/* 137:177 */       return false;
/* 138:    */     }
/* 139:179 */     Double deltaMQ = (Double)args.get("DeltaMQ");
/* 140:180 */     if (deltaMQ == null) {
/* 141:181 */       return false;
/* 142:    */     }
/* 143:183 */     double dMQ = deltaMQ.doubleValue();
/* 144:184 */     return accept(dMQ);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean accept(double dMQ)
/* 148:    */   {
/* 149:189 */     if (!this.initialized) {
/* 150:189 */       return false;
/* 151:    */     }
/* 152:195 */     if (BunchUtilities.compareGreaterEqual(dMQ, 0.0D)) {
/* 153:196 */       return false;
/* 154:    */     }
/* 155:198 */     double exponent = dMQ / this.currTemp;
/* 156:    */     
/* 157:200 */     double prob = Math.exp(exponent);
/* 158:201 */     double rndProb = getNextRndNumber();
/* 159:    */     
/* 160:203 */     boolean acceptMove = rndProb < prob;
/* 161:207 */     if (acceptMove) {
/* 162:208 */       this.stats.incrSAOverrides();
/* 163:    */     }
/* 164:210 */     return acceptMove;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static String getDescription()
/* 168:    */   {
/* 169:215 */     return "P(accept) = exp(deltaMQ/T);  T(k+1)=alpha*T(k)";
/* 170:    */   }
/* 171:    */   
/* 172:    */   public String getObjectDescription()
/* 173:    */   {
/* 174:220 */     return getDescription();
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String getWellKnownName()
/* 178:    */   {
/* 179:224 */     return "Simple Algorithm";
/* 180:    */   }
/* 181:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.SASimpleTechnique
 * JD-Core Version:    0.7.0.1
 */