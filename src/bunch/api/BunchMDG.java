/*  1:   */ package bunch.api;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Collection;
/*  5:   */ 
/*  6:   */ public class BunchMDG
/*  7:   */ {
/*  8:24 */   ArrayList mdgEdges = null;
/*  9:   */   
/* 10:   */   public BunchMDG()
/* 11:   */   {
/* 12:27 */     this.mdgEdges = new ArrayList();
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean addMDGEdges(Collection c)
/* 16:   */   {
/* 17:32 */     return this.mdgEdges.addAll(c);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean setMDGEdges(Collection c)
/* 21:   */   {
/* 22:37 */     this.mdgEdges.clear();
/* 23:38 */     return addMDGEdges(c);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean addMDGEdge(BunchMDGDependency d)
/* 27:   */   {
/* 28:43 */     return this.mdgEdges.add(d);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean addMDGEdge(String s, String d, int w, String r)
/* 32:   */   {
/* 33:48 */     BunchMDGDependency bmd = new BunchMDGDependency(s, d, w, r);
/* 34:49 */     return addMDGEdge(bmd);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public boolean addMDGEdge(String s, String d, int w)
/* 38:   */   {
/* 39:54 */     BunchMDGDependency bmd = new BunchMDGDependency(s, d, w);
/* 40:55 */     return addMDGEdge(bmd);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean addMDGEdge(String s, String d)
/* 44:   */   {
/* 45:60 */     BunchMDGDependency bmd = new BunchMDGDependency(s, d);
/* 46:61 */     return addMDGEdge(bmd);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void clearMDG()
/* 50:   */   {
/* 51:66 */     this.mdgEdges.clear();
/* 52:   */   }
/* 53:   */   
/* 54:   */   public Collection getMDGEdges()
/* 55:   */   {
/* 56:71 */     return this.mdgEdges;
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.BunchMDG
 * JD-Core Version:    0.7.0.1
 */