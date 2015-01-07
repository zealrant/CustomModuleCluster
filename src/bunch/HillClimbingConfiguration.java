/*  1:   */ package bunch;
/*  2:   */ 
/*  3:   */ public class HillClimbingConfiguration
/*  4:   */   extends Configuration
/*  5:   */ {
/*  6:33 */   double threshold_d = 0.1D;
/*  7:   */   
/*  8:   */   public HillClimbingConfiguration() {}
/*  9:   */   
/* 10:   */   public HillClimbingConfiguration(Graph g)
/* 11:   */   {
/* 12:52 */     init(g);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void init(Graph g)
/* 16:   */   {
/* 17:66 */     int nodes = g.getNumberOfNodes();
/* 18:67 */     super.init(g);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setThreshold(double t)
/* 22:   */   {
/* 23:84 */     this.threshold_d = t;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public double getThreshold()
/* 27:   */   {
/* 28:98 */     return this.threshold_d;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.HillClimbingConfiguration
 * JD-Core Version:    0.7.0.1
 */