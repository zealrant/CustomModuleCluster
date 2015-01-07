/*  1:   */ package bunch.BunchServer;
/*  2:   */ 
/*  3:   */ import bunch.BunchPreferences;
/*  4:   */ import bunch.Callback;
/*  5:   */ import bunch.Configuration;
/*  6:   */ import bunch.Graph;
/*  7:   */ 
/*  8:   */ public class ServerProperties
/*  9:   */ {
/* 10:24 */   public String svrName = null;
/* 11:25 */   public int svrID = -1;
/* 12:26 */   public Graph theGraph = null;
/* 13:27 */   public String clusteringMethod = null;
/* 14:28 */   public String objFn = null;
/* 15:29 */   public Configuration cfg = null;
/* 16:30 */   public BunchPreferences bp = null;
/* 17:31 */   public Callback clientCB = null;
/* 18:32 */   public boolean adaptiveEnabled = true;
/* 19:33 */   public String jndiName = "";
/* 20:34 */   public double bestObjFnValue = -1.0D;
/* 21:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchServer.ServerProperties
 * JD-Core Version:    0.7.0.1
 */