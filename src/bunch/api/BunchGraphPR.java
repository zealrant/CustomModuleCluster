/*   1:    */ package bunch.api;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ 
/*   6:    */ class BunchGraphPR
/*   7:    */ {
/*   8:    */   BunchGraph expertG;
/*   9:    */   BunchGraph clusterG;
/*  10:363 */   double precision = 0.0D;
/*  11:364 */   double recall = 0.0D;
/*  12:365 */   long combinationsConsidered = 0L;
/*  13:366 */   long matchingCombinations = 0L;
/*  14:    */   
/*  15:    */   public BunchGraphPR(BunchGraph expert, BunchGraph cluster)
/*  16:    */   {
/*  17:370 */     this.expertG = expert;
/*  18:371 */     this.clusterG = cluster;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public boolean run()
/*  22:    */   {
/*  23:376 */     this.precision = runPR(this.clusterG, this.expertG);
/*  24:377 */     this.recall = runPR(this.expertG, this.clusterG);
/*  25:378 */     return true;
/*  26:    */   }
/*  27:    */   
/*  28:    */   private double runPR(BunchGraph g1, BunchGraph g2)
/*  29:    */   {
/*  30:383 */     double result = 0.0D;
/*  31:384 */     this.combinationsConsidered = 0L;
/*  32:385 */     this.matchingCombinations = 0L;
/*  33:    */     
/*  34:387 */     Iterator clusterList = g1.getClusters().iterator();
/*  35:388 */     while (clusterList.hasNext())
/*  36:    */     {
/*  37:390 */       BunchCluster bc = (BunchCluster)clusterList.next();
/*  38:391 */       processCluster(bc, g2);
/*  39:    */     }
/*  40:394 */     result = this.matchingCombinations / this.combinationsConsidered;
/*  41:    */     
/*  42:396 */     return result;
/*  43:    */   }
/*  44:    */   
/*  45:    */   private boolean processCluster(BunchCluster bc, BunchGraph bg)
/*  46:    */   {
/*  47:401 */     Object[] nodeO = bc.getClusterNodes().toArray();
/*  48:402 */     for (int i = 0; i < nodeO.length; i++)
/*  49:    */     {
/*  50:404 */       BunchNode srcNode = (BunchNode)nodeO[i];
/*  51:405 */       BunchCluster srcClusterInGraph = bg.findNode(srcNode.getName()).getMemberCluster();
/*  52:406 */       for (int j = i + 1; j < nodeO.length; j++)
/*  53:    */       {
/*  54:408 */         this.combinationsConsidered += 1L;
/*  55:409 */         BunchNode tgtNode = (BunchNode)nodeO[j];
/*  56:410 */         String tgtNodeName = tgtNode.getName();
/*  57:411 */         if (srcClusterInGraph.containsNode(tgtNodeName)) {
/*  58:412 */           this.matchingCombinations += 1L;
/*  59:    */         }
/*  60:    */       }
/*  61:    */     }
/*  62:415 */     return true;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public double getPrecision()
/*  66:    */   {
/*  67:419 */     return this.precision;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public double getRecall()
/*  71:    */   {
/*  72:422 */     return this.recall;
/*  73:    */   }
/*  74:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.BunchGraphPR
 * JD-Core Version:    0.7.0.1
 */