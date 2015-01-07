/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ 
/*   6:    */ public class SynchronizedEventQueue
/*   7:    */ {
/*   8: 24 */   Thread managerThread = null;
/*   9: 25 */   ArrayList workQueue = null;
/*  10:    */   
/*  11:    */   public SynchronizedEventQueue()
/*  12:    */   {
/*  13: 28 */     this.workQueue = new ArrayList();
/*  14:    */   }
/*  15:    */   
/*  16:    */   public SynchronizedEventQueue(Thread mgr)
/*  17:    */   {
/*  18: 32 */     this.workQueue = new ArrayList();
/*  19: 33 */     this.managerThread = mgr;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void setManagerThread(Thread t)
/*  23:    */   {
/*  24: 38 */     this.managerThread = t;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Thread getManagerThread()
/*  28:    */   {
/*  29: 41 */     return this.managerThread;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public BunchEvent getEvent()
/*  33:    */   {
/*  34:    */     try
/*  35:    */     {
/*  36: 47 */       synchronized (this.managerThread)
/*  37:    */       {
/*  38: 48 */         while (this.workQueue.size() == 0) {
/*  39: 50 */           this.managerThread.wait();
/*  40:    */         }
/*  41:    */       }
/*  42: 53 */       synchronized (this)
/*  43:    */       {
/*  44: 55 */         BunchEvent be = (BunchEvent)this.workQueue.get(0);
/*  45: 56 */         be.setEventState(2);
/*  46: 57 */         this.workQueue.remove(0);
/*  47: 58 */         return be;
/*  48:    */       }     }
/*  51:    */     catch (Exception ex)
/*  52:    */     {
/*  53: 61 */       System.out.println("SEM EXCEPTION - getEvent(): " + ex.toString());
/*  54:    */     }
/*  55:    */
return null;   }
/*  56:    */   
/*  57:    */   public void releaseEvent(BunchEvent be)
/*  58:    */   {
/*  59:    */     try
/*  60:    */     {
/*  61: 69 */       synchronized (be.getSubmitterThread())
/*  62:    */       {
/*  63: 70 */         be.setEventState(3);
/*  64: 71 */         be.getSubmitterThread().notify();
/*  65:    */       }
/*  66:    */     }
/*  67:    */     catch (Exception ex)
/*  68:    */     {
/*  69: 74 */       System.out.println("SEM EXCEPTION: - releaseEvent()" + ex.toString());
/*  70:    */     }

		
/*  71:    */   }
/*  72:    */   
/*  73:    */   public BunchEvent putAndWait(BunchEvent be)
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77: 82 */       synchronized (this)
/*  78:    */       {
/*  79: 83 */         be.setEventState(1);
/*  80: 84 */         this.workQueue.add(be);
/*  81:    */       }
/*  82: 87 */       synchronized (this.managerThread)
/*  83:    */       {
/*  84: 88 */         this.managerThread.notify();
/*  85:    */       }
/*  86: 91 */       synchronized (be.getSubmitterThread())
/*  87:    */       {
/*  88: 92 */         while (be.getEventState() != 3) {
/*  89: 93 */           be.getSubmitterThread().wait();
/*  90:    */         }
/*  91: 95 */         return be;
/*  92:    */       }     }
/*  95:    */     catch (Exception ex)
/*  96:    */     {
/*  97: 98 */       System.out.println("SEM EXCEPTION - putAndWait(): " + ex.toString());
/*  98:    */     }
					return null;
/*  99:    */   }
/* 100:    */   
/* 101:    */   private void DEBUGDump(String caller, Thread callerThread, Thread mgrThr)
/* 102:    */   {
/* 103:106 */     System.out.println("Debug dump of threads from - " + caller);
/* 104:107 */     System.out.println("   CurrentThread(" + Thread.currentThread().getName() + ")  CallerThread(" + callerThread.getName() + ")  ManagerThread(" + mgrThr.getName() + ")");
/* 105:    */     
/* 106:    */ 
/* 107:110 */     System.out.flush();
/* 108:    */   }
/* 109:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.SynchronizedEventQueue
 * JD-Core Version:    0.7.0.1
 */