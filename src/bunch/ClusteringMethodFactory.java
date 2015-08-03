/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ public class ClusteringMethodFactory
/*   4:    */   extends GenericFactory
/*   5:    */ {
/*   6: 42 */   String defaultMethod = "Hill Climbing";
/*   7:    */   
/*   8:    */   public ClusteringMethodFactory()
/*   9:    */   {
/*  10: 52 */     setFactoryType("ClusteringMethod");
/*  11: 53 */     addItem("Hill Climbing", "bunch.GeneralHillClimbingClusteringMethod");
/*  12: 54 */     addItem("NAHC", "bunch.NextAscentHillClimbingClusteringMethod");
/*  13: 55 */     addItem("SAHC", "bunch.SteepestAscentHillClimbingClusteringMethod");
/*  14: 56 */     addItem("GA", "bunch.GAClusteringMethod");
/*  15: 57 */     addItem("Exhaustive", "bunch.OptimalClusteringMethod");

/*  16:    */   }
/*  17:    */   
/*  18:    */   public String getDefaultMethod()
/*  19:    */   {
/*  20: 66 */     return this.defaultMethod;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public String[] getItemList()
/*  24:    */   {
/*  25: 76 */     String[] masterList = super.getItemList();
/*  26: 77 */     String[] resList = new String[masterList.length - 2];
/*  27:    */     
/*  28: 79 */     int resPos = 0;
/*  29: 80 */     for (int i = 0; i < masterList.length; i++)
/*  30:    */     {
/*  31: 82 */       String item = masterList[i];
/*  32: 83 */       if ((!item.equals("SAHC")) && (!item.equals("NAHC"))) {
/*  33: 86 */         resList[(resPos++)] = item;
/*  34:    */       }
/*  35:    */     }
/*  36: 89 */     return resList;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public ClusteringMethod getMethod(String name)
/*  40:    */   {
/*  41:104 */     return (ClusteringMethod)getItemInstance(name);
/*  42:    */   }
/*  43:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.ClusteringMethodFactory
 * JD-Core Version:    0.7.0.1
 */