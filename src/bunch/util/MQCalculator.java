/*  1:   */ package bunch.util;
/*  2:   */ 
/*  3:   */ import bunch.ClusterFileParser;
/*  4:   */ import bunch.DependencyFileParser;
/*  5:   */ import bunch.Graph;
/*  6:   */ import bunch.Node;
/*  7:   */ import bunch.ObjectiveFunctionCalculatorFactory;
/*  8:   */ import bunch.Parser;
/*  9:   */ 
/* 10:   */ public class MQCalculator
/* 11:   */ {
/* 12:   */   public static double CalcMQ(String mdgFileName, String silFileName, String calculatorName)
/* 13:   */   {
/* 14:   */     try
/* 15:   */     {
/* 16:24 */       String mdg = mdgFileName;
/* 17:25 */       String sil = silFileName;
/* 18:   */       
/* 19:27 */       Parser p = new DependencyFileParser();
/* 20:28 */       p.setInput(mdg);
/* 21:29 */       p.setDelims(" \t");
/* 22:   */       
/* 23:31 */       Graph g = (Graph)p.parse();
/* 24:32 */       ObjectiveFunctionCalculatorFactory ofc = new ObjectiveFunctionCalculatorFactory();
/* 25:33 */       ofc.setCurrentCalculator(calculatorName);
/* 26:34 */       Graph.setObjectiveFunctionCalculatorFactory(ofc);
/* 27:   */       
/* 28:36 */       g.setObjectiveFunctionCalculator(calculatorName);
/* 29:   */       
/* 30:38 */       ClusterFileParser cfp = new ClusterFileParser();
/* 31:39 */       cfp.setInput(sil);
/* 32:40 */       cfp.setObject(g);
/* 33:41 */       cfp.parse();
/* 34:42 */       g.calculateObjectiveFunctionValue();
/* 35:   */       
/* 36:   */ 
/* 37:45 */       long edgeCnt = 0L;
/* 38:46 */       Node[] n = g.getNodes();
/* 39:47 */       for (int i = 0; i < n.length; i++) {
/* 40:49 */         if (n[i].dependencies != null) {
/* 41:50 */           edgeCnt += n[i].dependencies.length;
/* 42:   */         }
/* 43:   */       }
/* 44:54 */       return g.getObjectiveFunctionValue();
/* 45:   */     }
/* 46:   */     catch (Exception calcExcept)
/* 47:   */     {
/* 48:60 */       calcExcept.printStackTrace();
/* 49:   */     }
/* 50:61 */     return -1.0D;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.util.MQCalculator
 * JD-Core Version:    0.7.0.1
 */