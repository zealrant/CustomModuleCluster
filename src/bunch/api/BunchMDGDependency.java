/*  1:   */ package bunch.api;
/*  2:   */ 
/*  3:   */ public class BunchMDGDependency
/*  4:   */ {
/*  5:   */   String srcNode;
/*  6:   */   String destNode;
/*  7:   */   int edgeW;
/*  8:   */   String relType;
/*  9:   */   
/* 10:   */   public BunchMDGDependency()
/* 11:   */   {
/* 12:29 */     this.srcNode = null;
/* 13:30 */     this.destNode = null;
/* 14:31 */     this.edgeW = 0;
/* 15:32 */     this.relType = null;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public BunchMDGDependency(String s, String d, int w, String r)
/* 19:   */   {
/* 20:37 */     this.srcNode = s;
/* 21:38 */     this.destNode = d;
/* 22:39 */     this.edgeW = w;
/* 23:40 */     this.relType = r;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public BunchMDGDependency(String s, String d, int w)
/* 27:   */   {
/* 28:45 */     this.srcNode = s;
/* 29:46 */     this.destNode = d;
/* 30:47 */     this.edgeW = w;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public BunchMDGDependency(String s, String d)
/* 34:   */   {
/* 35:52 */     this.srcNode = s;
/* 36:53 */     this.destNode = d;
/* 37:54 */     this.edgeW = 1;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void setSrcNode(String n)
/* 41:   */   {
/* 42:58 */     this.srcNode = n;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String getSrcNode()
/* 46:   */   {
/* 47:61 */     return this.srcNode;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setDestNode(String n)
/* 51:   */   {
/* 52:64 */     this.destNode = n;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public String getDestNode()
/* 56:   */   {
/* 57:67 */     return this.destNode;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void setRelType(String t)
/* 61:   */   {
/* 62:70 */     this.relType = t;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public String getRelType()
/* 66:   */   {
/* 67:73 */     return this.relType;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void setEdgeW(int w)
/* 71:   */   {
/* 72:76 */     this.edgeW = w;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public int getEdgeW()
/* 76:   */   {
/* 77:79 */     return this.edgeW;
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.BunchMDGDependency
 * JD-Core Version:    0.7.0.1
 */