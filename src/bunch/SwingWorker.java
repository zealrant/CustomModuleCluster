/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import javax.swing.SwingUtilities;
/*   4:    */ 
/*   5:    */ public abstract class SwingWorker
/*   6:    */ {
/*   7:    */   private Object value;
/*   8:    */   private Thread thread;
/*   9:    */   private ThreadVar threadVar;
/*  10:    */   
/*  11:    */   private static class ThreadVar
/*  12:    */   {
/*  13:    */     private Thread thread;
/*  14:    */     
/*  15:    */     ThreadVar(Thread t)
/*  16:    */     {
/*  17: 44 */       this.thread = t;
/*  18:    */     }
/*  19:    */     
/*  20:    */     synchronized Thread get()
/*  21:    */     {
/*  22: 45 */       return this.thread;
/*  23:    */     }
/*  24:    */     
/*  25:    */     synchronized void clear()
/*  26:    */     {
/*  27: 46 */       this.thread = null;
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected synchronized Object getValue()
/*  32:    */   {
/*  33: 56 */     return this.value;
/*  34:    */   }
/*  35:    */   
/*  36:    */   private synchronized void setValue(Object x)
/*  37:    */   {
/*  38: 63 */     this.value = x;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public abstract Object construct();
/*  42:    */   
/*  43:    */   public void finished() {}
/*  44:    */   
/*  45:    */   public void interrupt()
/*  46:    */   {
/*  47: 83 */     Thread t = this.threadVar.get();
/*  48: 84 */     if (t != null) {
/*  49: 85 */       t.interrupt();
/*  50:    */     }
/*  51: 87 */     this.threadVar.clear();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void suspend()
/*  55:    */   {
/*  56: 92 */     Thread t = this.threadVar.get();
/*  57: 93 */     if (t != null) {
/*  58: 94 */       t.suspend();
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void resume()
/*  63:    */   {
/*  64: 98 */     Thread t = this.threadVar.get();
/*  65: 99 */     if (t != null) {
/*  66:100 */       t.resume();
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setPriority(int p)
/*  71:    */   {
/*  72:105 */     Thread t = this.threadVar.get();
/*  73:106 */     if (t != null) {
/*  74:107 */       t.setPriority(p);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Thread getThread()
/*  79:    */   {
/*  80:112 */     return this.threadVar.get();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Object get()
/*  84:    */   {
/*  85:    */     for (;;)
/*  86:    */     {
/*  87:124 */       Thread t = this.threadVar.get();
/*  88:125 */       if (t == null) {
/*  89:126 */         return getValue();
/*  90:    */       }
/*  91:    */       try
/*  92:    */       {
/*  93:129 */         t.join();
/*  94:    */       }
/*  95:    */       catch (InterruptedException e)
/*  96:    */       {
/*  97:132 */         Thread.currentThread().interrupt();
/*  98:133 */         return null;
/*  99:    */       }
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public SwingWorker()
/* 104:    */   {
/* 105:144 */     final Runnable doFinished = new Runnable()
/* 106:    */     {
/* 107:    */       public void run()
/* 108:    */       {
/* 109:145 */         SwingWorker.this.finished();
/* 110:    */       }
/* 111:147 */     };
/* 112:148 */     Runnable doConstruct = new Runnable()
/* 113:    */     {
/* 114:    */       public void run()
/* 115:    */       {
/* 116:    */         try
/* 117:    */         {
/* 118:151 */           SwingWorker.this.setValue(SwingWorker.this.construct());
/* 119:    */         }
/* 120:    */         finally
/* 121:    */         {
/* 122:154 */           SwingWorker.this.threadVar.clear();
/* 123:    */         }
/* 124:157 */         SwingUtilities.invokeLater(doFinished);
/* 125:    */       }
/* 126:160 */     };
/* 127:161 */     Thread t = new Thread(doConstruct);
/* 128:162 */     this.threadVar = new ThreadVar(t);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void start()
/* 132:    */   {
/* 133:169 */     Thread t = this.threadVar.get();
/* 134:170 */     if (t != null) {
/* 135:171 */       t.start();
/* 136:    */     }
/* 137:    */   }
/* 138:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.SwingWorker
 * JD-Core Version:    0.7.0.1
 */