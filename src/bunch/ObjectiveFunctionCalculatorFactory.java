/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ public class ObjectiveFunctionCalculatorFactory
/*   4:    */   extends GenericFactory
/*   5:    */ {
/*   6: 35 */   String currObjFnMethod = "Incremental MQ Weighted";
/*   7: 36 */   String defaultMethod = "Incremental MQ Weighted";
/*   8:    */   
/*   9:    */   public ObjectiveFunctionCalculatorFactory()
/*  10:    */   {
/*  11: 46 */     setFactoryType("ObjectiveFunctionCalculator");
/*  12: 47 */     addItem("Basic MQ Function", "bunch.BasicMQ");
/*  13: 48 */     addItem("Turbo MQ Function", "bunch.TurboMQ");
/*  14:    */     
/*  15: 50 */     addItem("Incremental MQ Weighted", "bunch.TurboMQIncrW");
/*  16:    */     
/*  17: 52 */     addItem("BasicMQ", "bunch.BasicMQ");
/*  18: 53 */     addItem("TurboMQ", "bunch.TurboMQ");
/*  19: 54 */     addItem("ITurboMQ", "bunch.TurboMQIncrW");
/*  20: 55 */     addItem("TurboMQIncrW", "bunch.TurboMQIncrW");
					//TODO: 여기다가 MQ Calcu추가 bunch.클래스명
				  addItem("UserCustomCalculator", "bunch.UserCalc");
/*  21:    */   }
/*  22:    */   
/*  23:    */   public ObjectiveFunctionCalculator getCalculator(String name)
/*  24:    */   {
/*  25: 75 */     return (ObjectiveFunctionCalculator)getItemInstance(name);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public ObjectiveFunctionCalculator getSelectedCalculator()
/*  29:    */   {
/*  30: 82 */     return (ObjectiveFunctionCalculator)getItemInstance(this.currObjFnMethod);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getDefaultMethod()
/*  34:    */   {
/*  35: 91 */     return this.defaultMethod;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getCurrentCalculator()
/*  39:    */   {
/*  40: 98 */     return this.currObjFnMethod;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setCurrentCalculator(String sCalc)
/*  44:    */   {
/*  45:105 */     this.currObjFnMethod = sCalc;
/*  46:    */   }
/*  47:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.ObjectiveFunctionCalculatorFactory
 * JD-Core Version:    0.7.0.1
 */