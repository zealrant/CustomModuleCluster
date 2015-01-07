/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public class BunchPreferences
/*   6:    */   implements Serializable
/*   7:    */ {
/*   8:    */   ClusteringMethodFactory methodFactory_d;
/*   9:    */   ObjectiveFunctionCalculatorFactory calculatorFactory_d;
/*  10:    */   ParserFactory parserFactory_d;
/*  11:    */   GraphOutputFactory outputFactory_d;
/*  12:    */   public static final long serialVersionUID = 100L;
/*  13:    */   
/*  14:    */   public BunchPreferences()
/*  15:    */   {
/*  16: 52 */     this.methodFactory_d = new ClusteringMethodFactory();
/*  17: 53 */     this.calculatorFactory_d = new ObjectiveFunctionCalculatorFactory();
/*  18: 54 */     this.parserFactory_d = new ParserFactory();
/*  19: 55 */     this.outputFactory_d = new GraphOutputFactory();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void setClusteringMethodFactory(ClusteringMethodFactory fac)
/*  23:    */   {
/*  24: 69 */     this.methodFactory_d = fac;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ClusteringMethodFactory getClusteringMethodFactory()
/*  28:    */   {
/*  29: 83 */     return this.methodFactory_d;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setObjectiveFunctionCalculatorFactory(ObjectiveFunctionCalculatorFactory fac)
/*  33:    */   {
/*  34: 97 */     this.calculatorFactory_d = fac;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public ObjectiveFunctionCalculatorFactory getObjectiveFunctionCalculatorFactory()
/*  38:    */   {
/*  39:112 */     return this.calculatorFactory_d;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setParserFactory(ParserFactory fac)
/*  43:    */   {
/*  44:126 */     this.parserFactory_d = fac;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public ParserFactory getParserFactory()
/*  48:    */   {
/*  49:140 */     return this.parserFactory_d;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public GraphOutputFactory getGraphOutputFactory()
/*  53:    */   {
/*  54:154 */     return this.outputFactory_d;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setGraphOutputFactory(GraphOutputFactory og)
/*  58:    */   {
/*  59:168 */     this.outputFactory_d = og;
/*  60:    */   }
/*  61:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchPreferences
 * JD-Core Version:    0.7.0.1
 */