/*  1:   */ package bunch;
/*  2:   */ 
/*  3:   */ public class NAHCConfiguration
/*  4:   */   extends HillClimbingConfiguration
/*  5:   */ {
/*  6:49 */   SATechnique saTechnique = null;
/*  7:50 */   int minPctToConsider = 0;
/*  8:51 */   int randomizePct = 0;
/*  9:   */   
/* 10:   */   public int getRandomizePct()
/* 11:   */   {
/* 12:57 */     return this.randomizePct;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void setRandomizePct(int pct)
/* 16:   */   {
/* 17:60 */     this.randomizePct = pct;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setSATechnique(SATechnique t)
/* 21:   */   {
/* 22:63 */     this.saTechnique = t;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public SATechnique getSATechnique()
/* 26:   */   {
/* 27:66 */     return this.saTechnique;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int getMinPctToConsider()
/* 31:   */   {
/* 32:69 */     return this.minPctToConsider;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setMinPctToConsider(int pct)
/* 36:   */   {
/* 37:72 */     this.minPctToConsider = pct;
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.NAHCConfiguration
 * JD-Core Version:    0.7.0.1
 */