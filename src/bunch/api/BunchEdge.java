/*  1:   */ package bunch.api;
/*  2:   */ 
/*  3:   */ public class BunchEdge
/*  4:   */ {
/*  5:   */   int weight;
/*  6:   */   BunchNode srcNode;
/*  7:   */   BunchNode destNode;
/*  8:   */   
/*  9:   */   public BunchEdge(int w, BunchNode src, BunchNode dest)
/* 10:   */   {
/* 11:29 */     this.weight = w;
/* 12:30 */     this.srcNode = src;
/* 13:31 */     this.destNode = dest;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public int getWeight()
/* 17:   */   {
/* 18:35 */     return this.weight;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public BunchNode getSrcNode()
/* 22:   */   {
/* 23:38 */     return this.srcNode;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public BunchNode getDestNode()
/* 27:   */   {
/* 28:41 */     return this.destNode;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.BunchEdge
 * JD-Core Version:    0.7.0.1
 */