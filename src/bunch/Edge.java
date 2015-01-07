/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ class Edge
/*   4:    */ {
/*   5:    */   public Node from_d;
/*   6:    */   public Node to_d;
/*   7:    */   
/*   8:    */   public Edge(Node from, Node to)
/*   9:    */   {
/*  10:524 */     this.from_d = from;
/*  11:525 */     this.to_d = to;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public Node getFrom()
/*  15:    */   {
/*  16:532 */     return this.from_d;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public Node getTo()
/*  20:    */   {
/*  21:539 */     return this.to_d;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public boolean equalByCluster(Edge e)
/*  25:    */   {
/*  26:546 */     if ((this.from_d.cluster == e.getFrom().cluster) && (this.to_d.cluster == e.getTo().cluster)) {
/*  27:548 */       return true;
/*  28:    */     }
/*  29:550 */     return false;
/*  30:    */   }
/*  31:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.Edge
 * JD-Core Version:    0.7.0.1
 */