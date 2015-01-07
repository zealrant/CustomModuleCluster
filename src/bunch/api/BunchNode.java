/*   1:    */ package bunch.api;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.HashMap;
/*   6:    */ 
/*   7:    */ public class BunchNode
/*   8:    */ {
/*   9:    */   public static final int NOT_A_MEMBER_OF_A_CLUSTER = -1;
/*  10: 38 */   String nodeName = "";
/*  11: 39 */   int nodeIndex = -1;
/*  12: 40 */   int nodeCluster = -1;
/*  13: 41 */   BunchCluster memberCluster = null;
/*  14: 42 */   boolean isNodeCluster = false;
/*  15: 43 */   ArrayList deps = null;
/*  16: 44 */   ArrayList backDeps = null;
/*  17: 45 */   HashMap clusterMemberships = null;
/*  18:    */   
/*  19:    */   public BunchNode(String name, int index, int cluster, boolean isCluster)
/*  20:    */   {
/*  21: 54 */     this.nodeName = name;
/*  22: 55 */     this.nodeIndex = index;
/*  23: 56 */     this.nodeCluster = cluster;
/*  24: 57 */     this.isNodeCluster = isCluster;
/*  25: 58 */     this.clusterMemberships = new HashMap();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void subscribeToCluster(BunchCluster bc)
/*  29:    */   {
/*  30: 63 */     if (bc != null) {
/*  31: 64 */       this.clusterMemberships.put(bc.getName(), bc);
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean isAMemberOfCluster(String name)
/*  36:    */   {
/*  37: 68 */     return this.clusterMemberships.containsKey(name);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean isAMemberOfCluster(BunchCluster bc)
/*  41:    */   {
/*  42: 71 */     return isAMemberOfCluster(bc.getName());
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int memberOfHowManyClusters()
/*  46:    */   {
/*  47: 74 */     return this.clusterMemberships.size();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setDeps(ArrayList deps, ArrayList backDeps)
/*  51:    */   {
/*  52: 77 */     this.deps = deps;
/*  53: 78 */     this.backDeps = backDeps;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getName()
/*  57:    */   {
/*  58: 82 */     return this.nodeName;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getCluster()
/*  62:    */   {
/*  63: 85 */     return this.nodeCluster;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void resetCluster(int newClustNumber)
/*  67:    */   {
/*  68: 88 */     this.nodeCluster = newClustNumber;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Collection getDeps()
/*  72:    */   {
/*  73: 91 */     return this.deps;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Collection getBackDeps()
/*  77:    */   {
/*  78: 94 */     return this.backDeps;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean isCluster()
/*  82:    */   {
/*  83: 97 */     return this.isNodeCluster;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public BunchCluster getMemberCluster()
/*  87:    */   {
/*  88:100 */     return this.memberCluster;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setMemberCluster(BunchCluster bc)
/*  92:    */   {
/*  93:104 */     this.memberCluster = bc;
/*  94:105 */     subscribeToCluster(bc);
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.BunchNode
 * JD-Core Version:    0.7.0.1
 */