/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import bunch.util.BunchUtilities;
/*   4:    */ 
/*   5:    */ public class SteepestAscentHillClimbingClusteringMethod
/*   6:    */   extends GenericHillClimbingClusteringMethod
/*   7:    */ {
/*   8:    */   protected Cluster getLocalMaxGraph(Cluster c)
/*   9:    */   {
/*  10: 41 */     if (c == null) {
/*  11: 41 */       return null;
/*  12:    */     }
/*  13: 43 */     Cluster maxC = c.cloneCluster();
/*  14:    */     
/*  15:    */ 
/*  16:    */ 
/*  17: 47 */     double maxOF = maxC.getObjFnValue();
/*  18: 48 */     double originalMax = maxOF;
/*  19:    */     
/*  20: 50 */     int[] clustNames = c.getClusterNames();
/*  21: 51 */     int[] clusters = c.getClusterVector();
/*  22: 52 */     int[] maxClust = maxC.getClusterVector();
/*  23: 53 */     boolean[] locks = c.getLocks();
/*  24: 74 */     for (int i = 0; i < clusters.length; i++)
/*  25:    */     {
/*  26: 75 */       int currClust = clusters[i];
/*  27: 77 */       for (int j = 0; j < clustNames.length; j++) {
/*  28: 79 */         if ((locks[i] == false) && (clustNames[j] != currClust))
/*  29:    */         {
/*  30: 80 */           double t = c.getObjFnValue();
/*  31: 81 */           c.relocate(i, clustNames[j]);
/*  32: 86 */           if (BunchUtilities.compareGreater(c.getObjFnValue(), maxOF))
/*  33:    */           {
/*  34: 88 */             maxC.copyFromCluster(c);
/*  35:    */             
/*  36: 90 */             maxOF = c.getObjFnValue();
/*  37:    */           }
/*  38:    */         }
/*  39:    */       }
/*  40: 98 */       c.relocate(i, currClust);
/*  41:    */     }
/*  42:104 */     if (!BunchUtilities.compareGreater(maxOF, originalMax))
/*  43:    */     {
/*  44:105 */       Node[] nodes = c.getGraph().getNodes();
/*  45:106 */       int newClusterID = c.allocateNewCluster();
/*  46:108 */       for (int i = 0; i < clusters.length; i++)
/*  47:    */       {
/*  48:109 */         int currClust = clusters[i];
/*  49:110 */         c.relocate(i, newClusterID);
/*  50:111 */         int[] edges = nodes[i].getDependencies();
/*  51:113 */         for (int j = 0; j < edges.length; j++)
/*  52:    */         {
/*  53:115 */           int otherNode = edges[j];
/*  54:116 */           if ((locks[i] == false) && (locks[otherNode] == false))
/*  55:    */           {
/*  56:117 */             int otherNodeCluster = clusters[otherNode];
/*  57:118 */             c.relocate(otherNode, newClusterID);
/*  58:120 */             if (BunchUtilities.compareGreater(c.getObjFnValue(), maxOF))
/*  59:    */             {
/*  60:121 */               maxC.copyFromCluster(c);
/*  61:122 */               maxOF = c.getObjFnValue();
/*  62:    */             }
/*  63:125 */             c.relocate(otherNode, otherNodeCluster);
/*  64:    */           }
/*  65:    */         }
/*  66:128 */         c.relocate(i, currClust);
/*  67:    */       }
/*  68:130 */       c.removeNewCluster(newClusterID);
/*  69:    */     }
/*  70:134 */     if (BunchUtilities.compareGreater(maxOF, originalMax))
/*  71:    */     {
/*  72:136 */       c.copyFromCluster(maxC);
/*  73:137 */       c.incrDepth();
/*  74:    */     }
/*  75:    */     else
/*  76:    */     {
/*  77:144 */       c.setConverged(true);
/*  78:    */     }
/*  79:151 */     return c;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Configuration getConfiguration()
/*  83:    */   {
/*  84:158 */     boolean reconf = false;
/*  85:159 */     if (this.configuration_d == null) {
/*  86:160 */       reconf = true;
/*  87:    */     }
/*  88:163 */     HillClimbingConfiguration hc = (HillClimbingConfiguration)super.getConfiguration();
/*  89:165 */     if (reconf)
/*  90:    */     {
/*  91:166 */       hc.setThreshold(1.0D);
/*  92:167 */       hc.setNumOfIterations(1);
/*  93:168 */       hc.setPopulationSize(1);
/*  94:    */     }
/*  95:170 */     return hc;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setDefaultConfiguration()
/*  99:    */   {
/* 100:176 */     HillClimbingConfiguration hc = (HillClimbingConfiguration)super.getConfiguration();
/* 101:    */     
/* 102:178 */     hc.setThreshold(1.0D);
/* 103:179 */     hc.setNumOfIterations(1);
/* 104:180 */     hc.setPopulationSize(1);
/* 105:    */     
/* 106:182 */     setConfiguration(hc);
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.SteepestAscentHillClimbingClusteringMethod
 * JD-Core Version:    0.7.0.1
 */