/*   1:    */ package bunch.api;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Hashtable;
/*   6:    */ 
/*   7:    */ public class BunchCluster
/*   8:    */ {
/*   9: 33 */   int clusterID = -1;
/*  10: 34 */   String clusterName = "";
/*  11: 35 */   ArrayList clusterNodes = null;
/*  12: 36 */   ArrayList overlapNodes = null;
/*  13: 37 */   Hashtable nodeHT = null;
/*  14:    */   
/*  15:    */   public BunchCluster(int id, String name, ArrayList nodes)
/*  16:    */   {
/*  17: 40 */     this.clusterID = id;
/*  18: 41 */     this.clusterName = name;
/*  19: 42 */     this.clusterNodes = nodes;
/*  20: 43 */     this.nodeHT = null;
/*  21: 45 */     for (int i = 0; i < nodes.size(); i++)
/*  22:    */     {
/*  23: 47 */       BunchNode bn = (BunchNode)nodes.get(i);
/*  24: 48 */       bn.setMemberCluster(this);
/*  25:    */     }
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int getSize()
/*  29:    */   {
/*  30: 54 */     if (this.clusterNodes == null) {
/*  31: 54 */       return 0;
/*  32:    */     }
/*  33: 55 */     return this.clusterNodes.size();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Collection getClusterNodes()
/*  37:    */   {
/*  38: 59 */     return this.clusterNodes;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Collection getOverlapNodes()
/*  42:    */   {
/*  43: 62 */     return this.overlapNodes;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getOverlapNodeCount()
/*  47:    */   {
/*  48: 66 */     if (this.overlapNodes != null) {
/*  49: 67 */       return this.overlapNodes.size();
/*  50:    */     }
/*  51: 69 */     return 0;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void addOverlapNode(BunchNode bn)
/*  55:    */   {
/*  56: 74 */     if (this.overlapNodes == null) {
/*  57: 75 */       this.overlapNodes = new ArrayList();
/*  58:    */     }
/*  59: 77 */     this.overlapNodes.add(bn);
/*  60: 78 */     this.nodeHT = null;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void addNode(BunchNode bn)
/*  64:    */   {
/*  65: 83 */     bn.setMemberCluster(this);
/*  66: 84 */     this.clusterNodes.add(bn);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void removeNode(BunchNode bn)
/*  70:    */   {
/*  71: 89 */     bn.setMemberCluster(null);
/*  72: 90 */     this.clusterNodes.remove(bn);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getID()
/*  76:    */   {
/*  77: 94 */     return this.clusterID;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getName()
/*  81:    */   {
/*  82: 97 */     return this.clusterName;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean containsNode(BunchNode bn)
/*  86:    */   {
/*  87:101 */     return containsNode(bn.getName());
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean containsNode(String nodeName)
/*  91:    */   {
/*  92:106 */     if (this.nodeHT == null) {
/*  93:107 */       this.nodeHT = constructNodeHT();
/*  94:    */     }
/*  95:109 */     return this.nodeHT.containsKey(nodeName);
/*  96:    */   }
/*  97:    */   
/*  98:    */   private Hashtable constructNodeHT()
/*  99:    */   {
/* 100:114 */     Hashtable h = new Hashtable();
/* 101:115 */     h.clear();
/* 102:117 */     if (this.clusterNodes != null) {
/* 103:119 */       for (int i = 0; i < this.clusterNodes.size(); i++)
/* 104:    */       {
/* 105:121 */         BunchNode bn = (BunchNode)this.clusterNodes.get(i);
/* 106:122 */         String key = bn.getName();
/* 107:123 */         h.put(key, bn);
/* 108:    */       }
/* 109:    */     }
/* 110:126 */     if (this.overlapNodes != null) {
/* 111:128 */       for (int i = 0; i < this.overlapNodes.size(); i++)
/* 112:    */       {
/* 113:130 */         BunchNode bn = (BunchNode)this.overlapNodes.get(i);
/* 114:131 */         String key = bn.getName();
/* 115:132 */         h.put(key, bn);
/* 116:    */       }
/* 117:    */     }
/* 118:135 */     return h;
/* 119:    */   }
/* 120:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.BunchCluster
 * JD-Core Version:    0.7.0.1
 */