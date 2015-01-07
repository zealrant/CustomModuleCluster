/*   1:    */ package bunch.api;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Set;
/*   9:    */ 
/*  10:    */ class MeCl
/*  11:    */ {
/*  12:    */   BunchGraph A;
/*  13:    */   BunchGraph B;
/*  14:    */   HashMap edgeA;
/*  15:    */   long meclValue;
/*  16:    */   
/*  17:    */   public MeCl(BunchGraph g1, BunchGraph g2)
/*  18:    */   {
/*  19:434 */     this.A = g1;
/*  20:435 */     this.B = g2;
/*  21:436 */     this.edgeA = new HashMap();
/*  22:437 */     this.edgeA.clear();
/*  23:438 */     this.meclValue = 0L;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public long run()
/*  27:    */   {
/*  28:443 */     this.edgeA.clear();
/*  29:444 */     HashMap Ca = determineSubClusters();
/*  30:    */     
/*  31:    */ 
/*  32:    */ 
/*  33:448 */     constructEdgeSet();
/*  34:    */     
/*  35:450 */     this.meclValue = collectSubClusters(Ca);
/*  36:    */     
/*  37:452 */     return this.meclValue;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public long getMeClValue()
/*  41:    */   {
/*  42:456 */     return this.meclValue;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public double getQualityMetric()
/*  46:    */   {
/*  47:460 */     int edgeCount = this.A.getEdges().size();
/*  48:461 */     double pct = this.meclValue / edgeCount;
/*  49:462 */     return 1.0D - pct;
/*  50:    */   }
/*  51:    */   
/*  52:    */   private void dumpSubClusters(HashMap h)
/*  53:    */   {
/*  54:467 */     Iterator i = h.keySet().iterator();
/*  55:468 */     while (i.hasNext())
/*  56:    */     {
/*  57:470 */       String clusterName = (String)i.next();
/*  58:471 */       HashMap Cc = (HashMap)h.get(clusterName);
/*  59:472 */       Iterator j = Cc.keySet().iterator();
/*  60:473 */       while (j.hasNext())
/*  61:    */       {
/*  62:475 */         String subCluster = (String)j.next();
/*  63:476 */         ArrayList al = (ArrayList)Cc.get(subCluster);
/*  64:477 */         System.out.print(clusterName + "->" + subCluster + ": ");
/*  65:478 */         for (int k = 0; k < al.size(); k++)
/*  66:    */         {
/*  67:480 */           BunchNode bn = (BunchNode)al.get(k);
/*  68:481 */           System.out.print(bn.getName() + "  ");
/*  69:    */         }
/*  70:483 */         System.out.println();
/*  71:    */       }
/*  72:    */     }
/*  73:486 */     System.out.println("\n\n");
/*  74:    */   }
/*  75:    */   
/*  76:    */   private long collectSubClusters(HashMap Ca)
/*  77:    */   {
/*  78:491 */     long tally = 0L;
/*  79:492 */     HashMap Ccollected = new HashMap();
/*  80:493 */     Ccollected.clear();
/*  81:    */     
/*  82:495 */     Iterator i = Ca.values().iterator();
/*  83:496 */     while (i.hasNext())
/*  84:    */     {
/*  85:498 */       HashMap Ci = (HashMap)i.next();
/*  86:499 */       Iterator j = Ci.keySet().iterator();
/*  87:500 */       while (j.hasNext())
/*  88:    */       {
/*  89:502 */         String key = (String)j.next();
/*  90:503 */         ArrayList value = (ArrayList)Ci.get(key);
/*  91:504 */         tally += mergeSubCluster(Ccollected, key, value);
/*  92:    */       }
/*  93:    */     }
/*  94:507 */     return tally;
/*  95:    */   }
/*  96:    */   
/*  97:    */   private long mergeSubCluster(HashMap Ccollected, String key, ArrayList value)
/*  98:    */   {
/*  99:512 */     long tally = 0L;
/* 100:    */     
/* 101:514 */     ArrayList currentSubCluster = (ArrayList)Ccollected.get(key);
/* 102:515 */     if (currentSubCluster == null)
/* 103:    */     {
/* 104:517 */       Ccollected.put(key, value);
/* 105:518 */       return 0L;
/* 106:    */     }
/* 107:521 */     for (int i = 0; i < currentSubCluster.size(); i++)
/* 108:    */     {
/* 109:523 */       BunchNode bn1 = (BunchNode)currentSubCluster.get(i);
/* 110:524 */       for (int j = 0; j < value.size(); j++)
/* 111:    */       {
/* 112:526 */         BunchNode bn2 = (BunchNode)value.get(j);
/* 113:527 */         if (!bn2.isAMemberOfCluster(bn1.getMemberCluster().getName())) {
/* 114:530 */           tally += getConnectedWeight(bn1.getName(), bn2.getName());
/* 115:    */         }
/* 116:    */       }
/* 117:    */     }
/* 118:534 */     currentSubCluster.addAll(value);
/* 119:535 */     return tally;
/* 120:    */   }
/* 121:    */   
/* 122:    */   private void constructEdgeSet()
/* 123:    */   {
/* 124:540 */     Iterator i = this.A.getEdges().iterator();
/* 125:542 */     while (i.hasNext())
/* 126:    */     {
/* 127:544 */       BunchEdge be = (BunchEdge)i.next();
/* 128:545 */       String key = be.getSrcNode().getName() + be.getDestNode().getName();
/* 129:546 */       Integer weight = new Integer(be.getWeight());
/* 130:    */       
/* 131:548 */       this.edgeA.put(key, weight);
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int getConnectedWeight(String n1, String n2)
/* 136:    */   {
/* 137:554 */     String key1 = n1 + n2;
/* 138:555 */     String key2 = n2 + n1;
/* 139:556 */     int total = 0;
/* 140:    */     
/* 141:558 */     Integer forward = (Integer)this.edgeA.get(key1);
/* 142:559 */     if (forward != null) {
/* 143:561 */       total += forward.intValue();
/* 144:    */     }
/* 145:564 */     Integer reverse = (Integer)this.edgeA.get(key2);
/* 146:565 */     if (reverse != null) {
/* 147:567 */       total += reverse.intValue();
/* 148:    */     }
/* 149:572 */     return total;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public HashMap determineSubClusters()
/* 153:    */   {
/* 154:577 */     HashMap Ca = new HashMap();
/* 155:    */     
/* 156:579 */     Iterator i = this.A.getClusters().iterator();
/* 157:580 */     while (i.hasNext())
/* 158:    */     {
/* 159:582 */       HashMap subClustersA = new HashMap();
/* 160:583 */       BunchCluster Ai = (BunchCluster)i.next();
/* 161:584 */       Iterator j = Ai.getClusterNodes().iterator();
/* 162:585 */       while (j.hasNext())
/* 163:    */       {
/* 164:587 */         BunchNode bnInA = (BunchNode)j.next();
/* 165:588 */         String nodeName = bnInA.getName();
/* 166:    */         
/* 167:    */ 
/* 168:591 */         BunchNode bnInB = this.B.findNode(nodeName);
/* 169:592 */         String bnInBClusterName = bnInB.getMemberCluster().getName();
/* 170:    */         
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:597 */         ArrayList members = (ArrayList)subClustersA.get(bnInBClusterName);
/* 175:598 */         if (members == null)
/* 176:    */         {
/* 177:600 */           members = new ArrayList();
/* 178:601 */           subClustersA.put(bnInBClusterName, members);
/* 179:    */         }
/* 180:603 */         members.add(bnInA);
/* 181:    */       }
/* 182:607 */       Ca.put(Ai.getName(), subClustersA);
/* 183:    */     }
/* 184:609 */     return Ca;
/* 185:    */   }
/* 186:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.MeCl
 * JD-Core Version:    0.7.0.1
 */