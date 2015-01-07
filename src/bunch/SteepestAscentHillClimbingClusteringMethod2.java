/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ 
/*   5:    */ public class SteepestAscentHillClimbingClusteringMethod2
/*   6:    */   extends GenericDistribHillClimbingClusteringMethod
/*   7:    */ {
/*   8:    */   protected Cluster getLocalMaxGraph(Cluster c)
/*   9:    */   {
/*  10: 37 */     System.out.print("IN:  " + c.getObjFnValue() + "  ");
/*  11: 38 */     double maxOF = c.getObjFnValue();
/*  12: 39 */     double originalMax = maxOF;
/*  13:    */     
/*  14: 41 */     int[] clustNames = c.getClusterNames();
/*  15:    */     
/*  16: 43 */     int[] clusters = c.getClusterVector();
/*  17:    */     
/*  18: 45 */     int[] maxClust = new int[clusters.length];
/*  19: 46 */     boolean[] locks = c.getLocks();
/*  20:    */     
/*  21:    */ 
/*  22: 49 */     System.arraycopy(clusters, 0, maxClust, 0, clusters.length);
/*  23: 51 */     for (int i = 0; i < clusters.length; i++)
/*  24:    */     {
/*  25: 52 */       int currClust = clusters[i];
/*  26: 53 */       for (int j = 0; j < clustNames.length; j++) {
/*  27: 55 */         if ((locks[i] == false) && (clustNames[j] != currClust))
/*  28:    */         {
/*  29: 56 */           clusters[i] = clustNames[j];
/*  30: 57 */           c.calcObjFn();
/*  31: 58 */           if (c.getObjFnValue() > maxOF)
/*  32:    */           {
/*  33: 59 */             System.arraycopy(clusters, 0, maxClust, 0, clusters.length);
/*  34: 60 */             maxOF = c.getObjFnValue();
/*  35:    */           }
/*  36:    */         }
/*  37:    */       }
/*  38: 64 */       clusters[i] = currClust;
/*  39:    */     }
/*  40: 67 */     if (maxOF > originalMax) {
/*  41: 68 */       System.arraycopy(maxClust, 0, clusters, 0, clusters.length);
/*  42:    */     } else {
/*  43: 72 */       c.setConverged(true);
/*  44:    */     }
/*  45: 74 */     c.calcObjFn();
/*  46:    */     
/*  47: 76 */     System.out.println("OUT:  " + c.getObjFnValue() + "  ");
/*  48:    */     
/*  49: 78 */     return c;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Configuration getConfiguration()
/*  53:    */   {
/*  54: 85 */     boolean reconf = false;
/*  55: 86 */     if (this.configuration_d == null) {
/*  56: 87 */       reconf = true;
/*  57:    */     }
/*  58: 90 */     HillClimbingConfiguration hc = (HillClimbingConfiguration)super.getConfiguration();
/*  59: 92 */     if (reconf)
/*  60:    */     {
/*  61: 93 */       hc.setThreshold(0.1D);
/*  62: 94 */       hc.setNumOfIterations(100);
/*  63: 95 */       hc.setPopulationSize(5);
/*  64:    */     }
/*  65: 97 */     return hc;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setDefaultConfiguration()
/*  69:    */   {
/*  70:103 */     HillClimbingConfiguration hc = (HillClimbingConfiguration)super.getConfiguration();
/*  71:    */     
/*  72:105 */     hc.setThreshold(0.1D);
/*  73:106 */     hc.setNumOfIterations(100);
/*  74:107 */     hc.setPopulationSize(5);
/*  75:    */     
/*  76:109 */     setConfiguration(hc);
/*  77:    */   }
/*  78:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.SteepestAscentHillClimbingClusteringMethod2
 * JD-Core Version:    0.7.0.1
 */