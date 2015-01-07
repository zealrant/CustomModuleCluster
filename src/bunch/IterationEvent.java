/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.util.EventObject;
/*   4:    */ 
/*   5:    */ public class IterationEvent
/*   6:    */   extends EventObject
/*   7:    */ {
/*   8:    */   int iteration_d;
/*   9:    */   int overallIteration_d;
/*  10:    */   int expNum;
/*  11:    */   
/*  12:    */   public IterationEvent(Object source)
/*  13:    */   {
/*  14: 44 */     super(source);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public void setIteration(int num)
/*  18:    */   {
/*  19: 54 */     this.iteration_d = num;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void setExpNum(int num)
/*  23:    */   {
/*  24: 64 */     this.expNum = num;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int getExpNum()
/*  28:    */   {
/*  29: 74 */     return this.expNum;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public int getIteration()
/*  33:    */   {
/*  34: 84 */     return this.iteration_d;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setOverallIteration(int num)
/*  38:    */   {
/*  39: 94 */     this.overallIteration_d = num;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getOverallIteration()
/*  43:    */   {
/*  44:104 */     return this.overallIteration_d;
/*  45:    */   }
/*  46:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.IterationEvent
 * JD-Core Version:    0.7.0.1
 */