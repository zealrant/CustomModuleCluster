/*  1:   */ package bunch.api;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.util.Hashtable;
/*  5:   */ 
/*  6:   */ public class BunchAPITestCallback
/*  7:   */   implements ProgressCallbackInterface
/*  8:   */ {
/*  9:   */   public void stats(Hashtable h)
/* 10:   */   {
/* 11:36 */     System.out.println("Callback executed");
/* 12:37 */     System.err.flush();
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.BunchAPITestCallback
 * JD-Core Version:    0.7.0.1
 */