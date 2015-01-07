/*  1:   */ package bunch;
/*  2:   */ 
/*  3:   */ public class GAMethodFactory
/*  4:   */   extends GenericFactory
/*  5:   */ {
/*  6:   */   public GAMethodFactory()
/*  7:   */   {
/*  8:41 */     setFactoryType("GAMethod");
/*  9:42 */     addItem("tournament", "bunch.GATournamentMethod");
/* 10:43 */     addItem("roulette wheel", "bunch.GARouletteWheelMethod");
/* 11:   */   }
/* 12:   */   
/* 13:   */   public GAMethod getMethod(String name)
/* 14:   */   {
/* 15:58 */     return (GAMethod)getItemInstance(name);
/* 16:   */   }
/* 17:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.GAMethodFactory
 * JD-Core Version:    0.7.0.1
 */