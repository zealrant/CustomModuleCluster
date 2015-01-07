/*  1:   */ package bunch;
/*  2:   */ 
/*  3:   */ import bunch.stats.StatsManager;
/*  4:   */ import java.awt.Frame;
/*  5:   */ import java.util.Hashtable;
/*  6:   */ import java.util.Random;
/*  7:   */ 
/*  8:   */ public abstract class SATechnique
/*  9:   */ {
/* 10:37 */   protected Hashtable SAargs = new Hashtable();
/* 11:38 */   protected Random rndNum = new Random();
/* 12:39 */   protected StatsManager stats = StatsManager.getInstance();
/* 13:   */   
/* 14:   */   public SATechnique()
/* 15:   */   {
/* 16:42 */     this.rndNum.setSeed(System.currentTimeMillis());
/* 17:   */   }
/* 18:   */   
/* 19:   */   public abstract boolean init(Hashtable paramHashtable);
/* 20:   */   
/* 21:   */   public abstract String getConfigDialogName();
/* 22:   */   
/* 23:   */   public abstract boolean configure();
/* 24:   */   
/* 25:   */   public abstract boolean changeTemp(Hashtable paramHashtable);
/* 26:   */   
/* 27:   */   public boolean configureUsingDialog(Frame parent)
/* 28:   */   {
/* 29:54 */     return false;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean accept()
/* 33:   */   {
/* 34:57 */     return false;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public boolean accept(Hashtable args)
/* 38:   */   {
/* 39:60 */     return false;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public boolean accept(double dMQ)
/* 43:   */   {
/* 44:63 */     return false;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Hashtable getConfig()
/* 48:   */   {
/* 49:66 */     return null;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public boolean setConfig(Hashtable h)
/* 53:   */   {
/* 54:69 */     return false;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public double getNextRndNumber()
/* 58:   */   {
/* 59:73 */     return this.rndNum.nextDouble();
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void reset() {}
/* 63:   */   
/* 64:   */   public static String getDescription()
/* 65:   */   {
/* 66:80 */     return "";
/* 67:   */   }
/* 68:   */   
/* 69:   */   public String getObjectDescription()
/* 70:   */   {
/* 71:83 */     return getDescription();
/* 72:   */   }
/* 73:   */   
/* 74:   */   public abstract String getWellKnownName();
/* 75:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.SATechnique
 * JD-Core Version:    0.7.0.1
 */