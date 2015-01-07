/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ public class BunchEvent
/*   4:    */ {
/*   5:    */   public static final int EVENT_GOTWORK = 1;
/*   6:    */   public static final int EVENT_SERVERDONE = 2;
/*   7:    */   public static final int EVENT_STATE_NOT_INIT = -1;
/*   8:    */   public static final int EVENT_STATE_SUBMITTED = 1;
/*   9:    */   public static final int EVENT_STATE_PENDING = 2;
/*  10:    */   public static final int EVENT_STATE_PROCESSED = 3;
/*  11:    */   private int theEvent;
/*  12: 41 */   private Object eventObj = null;
/*  13: 42 */   private Object notifyObj = null;
/*  14: 43 */   private Thread submitterThread = null;
/*  15: 44 */   private int eventState = -1;
/*  16:    */   
/*  17:    */   public void setEventState(int state)
/*  18:    */   {
/*  19: 52 */     this.eventState = state;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public int getEventState()
/*  23:    */   {
/*  24: 58 */     return this.eventState;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void setEventObj(Object eo)
/*  28:    */   {
/*  29: 65 */     this.eventObj = eo;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setNotifyObj(Object no)
/*  33:    */   {
/*  34: 71 */     this.notifyObj = no;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Object getNotifyObj()
/*  38:    */   {
/*  39: 77 */     return this.notifyObj;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Thread getSubmitterThread()
/*  43:    */   {
/*  44: 84 */     return this.submitterThread;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setSubmitterThread(Thread st)
/*  48:    */   {
/*  49: 91 */     this.submitterThread = st;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Object getEventObj()
/*  53:    */   {
/*  54: 97 */     return this.eventObj;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setEventID(int e)
/*  58:    */   {
/*  59:103 */     this.theEvent = e;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getEventID()
/*  63:    */   {
/*  64:109 */     return this.theEvent;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setEvent(int e, Object eo)
/*  68:    */   {
/*  69:119 */     this.theEvent = e;
/*  70:120 */     this.eventObj = eo;
/*  71:    */   }
/*  72:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchEvent
 * JD-Core Version:    0.7.0.1
 */