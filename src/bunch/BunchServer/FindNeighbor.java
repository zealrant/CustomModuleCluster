/*  1:   */ package bunch.BunchServer;
/*  2:   */ 
/*  3:   */ import bunch.Cluster;
/*  4:   */ import bunch.util.BunchUtilities;
/*  5:   */ 
/*  6:   */ public class FindNeighbor
/*  7:   */ {
/*  8:   */   public Cluster clusterWorklist(Cluster c, Cluster maxC, int[] clustNames, boolean[] locks, int[] workList)
/*  9:   */   {
/* 10:32 */     double maxOF = c.getObjFnValue();
/* 11:33 */     double originalMax = maxOF;
/* 12:   */     
/* 13:   */ 
/* 14:   */ 
/* 15:37 */     int[] clusters = c.getClusterVector();
/* 16:   */     
/* 17:39 */     int[] maxClust = maxC.getClusterVector();
/* 18:45 */     for (int i = 0; i < workList.length; i++)
/* 19:   */     {
/* 20:46 */       int currNode = workList[i];
/* 21:47 */       int currClust = clusters[currNode];
/* 22:48 */       for (int j = 0; j < clustNames.length; j++) {
/* 23:50 */         if ((locks[i] == false) && (clustNames[j] != currClust))
/* 24:   */         {
/* 25:51 */           c.relocate(currNode, clustNames[j]);
/* 26:54 */           if (BunchUtilities.compareGreater(c.getObjFnValue(), maxOF))
/* 27:   */           {
/* 28:56 */             maxC.copyFromCluster(c);
/* 29:   */             
/* 30:58 */             maxOF = c.getObjFnValue();
/* 31:   */           }
/* 32:   */         }
/* 33:   */       }
/* 34:62 */       c.relocate(currNode, currClust);
/* 35:   */     }
/* 36:74 */     return maxC;
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchServer.FindNeighbor
 * JD-Core Version:    0.7.0.1
 */