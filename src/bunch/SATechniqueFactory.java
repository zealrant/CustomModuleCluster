/*  1:   */ package bunch;
/*  2:   */ 
/*  3:   */ public class SATechniqueFactory
/*  4:   */   extends GenericFactory
/*  5:   */ {
/*  6:32 */   String defaultFactoryItem = "Simple Algorithm";
/*  7:   */   
/*  8:   */   public SATechniqueFactory()
/*  9:   */   {
/* 10:36 */     setFactoryType("SATechnique");
/* 11:37 */     addItem("Simple Algorithm", "bunch.SASimpleTechnique");
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getDefaultTechnique()
/* 15:   */   {
/* 16:42 */     return this.defaultFactoryItem;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public SATechnique getMethod(String name)
/* 20:   */   {
/* 21:49 */     return (SATechnique)getItemInstance(name);
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.SATechniqueFactory
 * JD-Core Version:    0.7.0.1
 */