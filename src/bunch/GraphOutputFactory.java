/*  1:   */ package bunch;
/*  2:   */ 
/*  3:   */ public class GraphOutputFactory
/*  4:   */   extends GenericFactory
/*  5:   */ {
/*  6:39 */   public String defaultOption = "Dotty";
/*  7:   */   
/*  8:   */   public GraphOutputFactory()
/*  9:   */   {
/* 10:48 */     setFactoryType("GraphOutput");
/* 11:49 */     addItem("Dotty", "bunch.DotGraphOutput");
/* 12:50 */     addItem("Text", "bunch.TXTGraphOutput");
/* 13:51 */     addItem("GXL", "bunch.gxl.io.GXLGraphOutput");
				addItem("RSF","bunch.RSFGraphOutput");
/* 14:   */   }
/* 15:   */   
/* 16:   */   public GraphOutput getOutput(String name)
/* 17:   */   {
/* 18:68 */     return (GraphOutput)getItemInstance(name);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getDefaultMethod()
/* 22:   */   {
/* 23:73 */     return this.defaultOption;
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.GraphOutputFactory
 * JD-Core Version:    0.7.0.1
 */