/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.io.BufferedWriter;
/*   4:    */ import java.io.FileWriter;
/*   5:    */ import java.io.Serializable;
/*   6:    */ 
/*   7:    */ public class Configuration
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   Feature[] preFeatures_d;
/*  11:    */   Feature[] features_d;
/*  12:    */   Feature[] postFeatures_d;
/*  13:    */   int numIterations_d;
/*  14:    */   int popSize_d;
/*  15: 45 */   public int expNumber_d = 0;
/*  16: 46 */   public boolean runBatch_d = false;
/*  17: 47 */   BufferedWriter writer_d = null;
/*  18:    */   
/*  19:    */   public void init(Graph g) {}
/*  20:    */   
/*  21:    */   public void createLogFile()
/*  22:    */     throws Exception
/*  23:    */   {
/*  24: 75 */     this.writer_d = new BufferedWriter(new FileWriter("c:\\bunch.log"));
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void closeLogFile()
/*  28:    */     throws Exception
/*  29:    */   {
/*  30: 83 */     this.writer_d.close();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setNumOfIterations(int n)
/*  34:    */   {
/*  35: 96 */     this.numIterations_d = n;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getNumOfIterations()
/*  39:    */   {
/*  40:109 */     return this.numIterations_d;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setPopulationSize(int p)
/*  44:    */   {
/*  45:123 */     this.popSize_d = p;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getPopulationSize()
/*  49:    */   {
/*  50:136 */     return this.popSize_d;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Feature[] getPreConditionFeatures()
/*  54:    */   {
/*  55:149 */     return this.preFeatures_d;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setPreConditionFeatures(Feature[] p)
/*  59:    */   {
/*  60:162 */     this.preFeatures_d = p;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Feature[] getFeatures()
/*  64:    */   {
/*  65:176 */     return this.features_d;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setFeatures(Feature[] f)
/*  69:    */   {
/*  70:190 */     this.features_d = f;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Feature[] getPostConditionFeatures()
/*  74:    */   {
/*  75:203 */     return this.postFeatures_d;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setPostConditionFeatures(Feature[] p)
/*  79:    */   {
/*  80:216 */     this.postFeatures_d = p;
/*  81:    */   }
/*  82:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.Configuration
 * JD-Core Version:    0.7.0.1
 */