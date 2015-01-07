/*   1:    */ package bunch.util;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ 
/*   5:    */ class ParserNode
/*   6:    */ {
/*   7:    */   public String name;
/*   8:    */   public Hashtable dependencies;
/*   9:    */   public Hashtable backEdges;
/*  10:    */   public Hashtable dWeights;
/*  11:    */   public Hashtable beWeights;
/*  12:    */   public int[] arrayDependencies;
/*  13:    */   public int[] arrayWeights;
/*  14:    */   
/*  15:    */   public ParserNode(String n)
/*  16:    */   {
/*  17:337 */     this.name = n;
/*  18:338 */     this.dependencies = new Hashtable();
/*  19:339 */     this.dWeights = new Hashtable();
/*  20:340 */     this.backEdges = new Hashtable();
/*  21:341 */     this.beWeights = new Hashtable();
/*  22:    */   }
/*  23:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.util.ParserNode
 * JD-Core Version:    0.7.0.1
 */