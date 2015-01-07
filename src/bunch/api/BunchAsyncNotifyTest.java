/*  1:   */ package bunch.api;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class BunchAsyncNotifyTest
/*  6:   */   extends BunchAsyncNotify
/*  7:   */ {
/*  8:   */   Object monitor;
/*  9:   */   
/* 10:   */   public BunchAsyncNotifyTest()
/* 11:   */   {
/* 12:17 */     this.monitor = new Object();
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void notifyDone()
/* 16:   */   {
/* 17:22 */     System.out.println("We are done");
/* 18:23 */     synchronized (this.monitor)
/* 19:   */     {
/* 20:24 */       this.monitor.notifyAll();
/* 21:   */     }
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void waitUntilDone()
/* 25:   */   {
/* 26:29 */     System.out.println("Getting Ready To Wait");
/* 27:   */     try
/* 28:   */     {
/* 29:32 */       synchronized (this.monitor)
/* 30:   */       {
/* 31:33 */         this.monitor.wait();
/* 32:   */       }
/* 33:   */     }
/* 34:   */     catch (Exception e1)
/* 35:   */     {
/* 36:35 */       e1.printStackTrace();
/* 37:   */     }
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.BunchAsyncNotifyTest
 * JD-Core Version:    0.7.0.1
 */