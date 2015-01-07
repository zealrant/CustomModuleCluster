/*  1:   */ package bunch.BunchServer;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class IterationManager
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   public static final int DIR_TO_CLIENT = 1;
/*  9:   */   public static final int DIR_TO_SERVER = 2;
/* 10:   */   public static final String MSG_GET_CLUSTER_VECTOR = "GET_CLUSTER_VECTOR";
/* 11:   */   public static final String MSG_SEND_CLUSTER_VECTOR = "SEND_CLUSTER_VECTOR";
/* 12:29 */   public String msgType = null;
/* 13:30 */   public int msgID = -1;
/* 14:31 */   public String jndiServerName = null;
/* 15:32 */   public int svrID = -1;
/* 16:33 */   public int[] clusterVector = null;
/* 17:34 */   public int[] workVector = null;
/* 18:35 */   public int direction = -1;
/* 19:36 */   public int uowSz = -1;
/* 20:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchServer.IterationManager
 * JD-Core Version:    0.7.0.1
 */