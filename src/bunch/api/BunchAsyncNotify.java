/*  1:   */ package bunch.api;
/*  2:   */ 
/*  3:   */ public abstract class BunchAsyncNotify
/*  4:   */ {
/*  5:   */   public static final int STATUS_NONE = 1;
/*  6:   */   public static final int STATUS_RUNNING = 2;
/*  7:   */   public static final int STATUS_DONE = 3;
/*  8:18 */   public int status = 1;
/*  9:19 */   Thread th = null;
/* 10:   */   
/* 11:   */   public void setThread(Thread t)
/* 12:   */   {
/* 13:25 */     this.th = t;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Thread getThread()
/* 17:   */   {
/* 18:28 */     return this.th;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setStatus(int s)
/* 22:   */   {
/* 23:31 */     this.status = s;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int getStatus()
/* 27:   */   {
/* 28:34 */     return this.status;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public abstract void notifyDone();
/* 32:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.BunchAsyncNotify
 * JD-Core Version:    0.7.0.1
 */