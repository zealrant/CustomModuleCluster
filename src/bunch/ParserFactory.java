/*  1:   */ package bunch;
/*  2:   */ 
/*  3:   */ public class ParserFactory
/*  4:   */   extends GenericFactory
/*  5:   */ {
/*  6:   */   public ParserFactory()
/*  7:   */   {
/*  8:37 */     setFactoryType("Parser");
/*  9:38 */     addItem("dependency", "bunch.DependencyFileParser");
/* 10:39 */     addItem("gxl", "bunch.gxl.parser.GXLGraphParser");
/* 11:40 */     addItem("cluster", "bunch.ClusterFileParser");
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Parser getParser(String name)
/* 15:   */   {
/* 16:47 */     return (Parser)getItemInstance(name);
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.ParserFactory
 * JD-Core Version:    0.7.0.1
 */