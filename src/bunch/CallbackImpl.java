/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import bunch.BunchServer.IterationManager;
/*   4:    */ import bunch.LoadBalancer.Manager;
/*   5:    */ import bunch.util.BunchUtilities;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.rmi.RemoteException;
/*   8:    */ import javax.rmi.PortableRemoteObject;
/*   9:    */ 
/*  10:    */ public class CallbackImpl
/*  11:    */   extends PortableRemoteObject
/*  12:    */   implements Callback
/*  13:    */ {
/*  14:    */   public static final String GET_NEXT_VECTOR = "GET_NEXT_VECTOR";
/*  15:    */   public static final String BEST_ITERATION_VECTOR = "BEST_ITERATION_VECTOR";
/*  16: 38 */   int MsgIdCtr = 0;
/*  17: 39 */   public int baseUOWSz = 5;
/*  18: 40 */   public BunchEvent bevent = null;
/*  19: 41 */   public SynchronizedEventQueue eventQ = null;
/*  20: 42 */   public Manager lbManager = null;
/*  21:    */   
/*  22:    */   public CallbackImpl()
/*  23:    */     throws RemoteException
/*  24:    */   {}
/*  25:    */   
/*  26:    */   public byte[] callFromServer(String data)
/*  27:    */   {
/*  28: 54 */     System.out.println("Thread: " + Thread.currentThread().getName() + "  CALLBACK RECEIVED FROM SERVER:  " + data);
/*  29: 55 */     return null;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public byte[] callFromServerWithObj(String input, byte[] so)
/*  33:    */   {
/*  34: 73 */     if (input.equals("GET_NEXT_VECTOR")) {
/*  35:    */       try
/*  36:    */       {
/*  37: 77 */         BunchEvent bevt = new BunchEvent();
/*  38:    */         
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43: 83 */         IterationManager im = (IterationManager)BunchUtilities.fromByteArray(so);
/*  44:    */         
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48: 88 */         WorkRequestEvent wre = new WorkRequestEvent();
/*  49: 89 */         this.lbManager.incrementWork(im.svrID);
/*  50: 90 */         wre.requestWorkSz = this.lbManager.getCurrentUOWSz(im.svrID);
/*  51: 91 */         wre.workPerformed = im.workVector;
/*  52: 92 */         wre.svrID = im.svrID;
/*  53: 93 */         wre.svrName = im.jndiServerName;
/*  54: 94 */         bevt.setEventObj(wre);
/*  55: 95 */         bevt.setSubmitterThread(Thread.currentThread());
/*  56:    */         
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:100 */         bevt = this.eventQ.putAndWait(bevt);
/*  61:    */         
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:106 */         im.clusterVector = wre.workToDo;
/*  67:107 */         im.uowSz = this.lbManager.getCurrentUOWSz(im.svrID);
/*  68:    */         
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:113 */         return BunchUtilities.toByteArray(im);
/*  74:    */       }
/*  75:    */       catch (Exception ex)
/*  76:    */       {
/*  77:118 */         System.out.println("EXCEPTION - callFromServerWithObj():  " + ex.toString());
/*  78:    */       }
/*  79:    */     }
/*  80:121 */     return null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean bCallFromServerWithObj(String input, byte[] so)
/*  84:    */   {
/*  85:140 */     if (input.equals("BEST_ITERATION_VECTOR")) {
/*  86:    */       try
/*  87:    */       {
/*  88:144 */         BunchEvent bevt = new BunchEvent();
/*  89:    */         
/*  90:146 */         IterationManager im = (IterationManager)BunchUtilities.fromByteArray(so);
/*  91:    */         
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:153 */         WorkFinishedEvent wfe = new WorkFinishedEvent();
/*  98:154 */         wfe.clusterVector = im.workVector;
/*  99:155 */         bevt.setEventObj(wfe);
/* 100:156 */         bevt.setSubmitterThread(Thread.currentThread());
/* 101:    */         
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:161 */         bevt = this.eventQ.putAndWait(bevt);
/* 106:162 */         return true;
/* 107:    */       }
/* 108:    */       catch (Exception ex)
/* 109:    */       {
/* 110:166 */         System.out.println("EXCEPTION - bCallFromServerWithObj():  " + ex.toString());
/* 111:    */       }
/* 112:    */     }
/* 113:169 */     return false;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean bCallFromServer(String input)
/* 117:    */   {
/* 118:179 */     return true;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public synchronized void DEBUGDump(IterationManager im)
/* 122:    */   {
/* 123:190 */     System.out.print(im.jndiServerName + ": [ ");
/* 124:191 */     if (im.clusterVector == null) {
/* 125:192 */       System.out.print("no work");
/* 126:    */     } else {
/* 127:194 */       for (int i = 0; i < im.clusterVector.length; i++) {
/* 128:195 */         System.out.print(im.clusterVector[i] + " ");
/* 129:    */       }
/* 130:    */     }
/* 131:196 */     System.out.println("]");
/* 132:    */   }
/* 133:    */   
/* 134:    */   public synchronized void DEBUGBiDump(IterationManager im)
/* 135:    */   {
/* 136:207 */     System.out.println("***********************************************");
/* 137:208 */     System.out.println("Server:  " + im.jndiServerName + " just reported a work finished event");
/* 138:209 */     System.out.println("***********************************************");
/* 139:    */   }
/* 140:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.CallbackImpl
 * JD-Core Version:    0.7.0.1
 */