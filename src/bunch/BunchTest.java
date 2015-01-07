/*  1:   */ package bunch;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class BunchTest
/*  6:   */ {
/*  7:   */   public static void main(String[] args)
/*  8:   */   {
/*  9:   */     try
/* 10:   */     {
/* 11:45 */       System.out.println("TEST: Clustering bunch (need MDG file named bunch)...");
/* 12:46 */       BunchAPIOld b = new BunchAPIOld("d:\\bunch\\mdgs\\random37.mdg");
/* 13:47 */       b.runBatch(100);
/* 14:48 */       System.out.println("TEST Finished, check bunchTest.dot file for output!");
/* 15:   */     }
/* 16:   */     catch (Exception e)
/* 17:   */     {
/* 18:51 */       e.printStackTrace();
/* 19:   */     }
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchTest
 * JD-Core Version:    0.7.0.1
 */