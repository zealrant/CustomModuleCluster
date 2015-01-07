/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.StringTokenizer;
/*   6:    */ import java.util.Vector;
/*   7:    */ 
/*   8:    */ public class ClusterFileParser
/*   9:    */   extends Parser
/*  10:    */ {
/*  11:    */   Graph graph_d;
/*  12:    */   
/*  13:    */   public void setObject(Object obj)
/*  14:    */   {
/*  15: 56 */     this.graph_d = ((Graph)obj);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public Object getObject()
/*  19:    */   {
/*  20: 68 */     return this.graph_d;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Object parse()
/*  24:    */   {
/*  25: 79 */     int linecount = 0;
/*  26: 80 */     Node[] nodes = this.graph_d.getNodes();
/*  27: 81 */     int[] clusters = this.graph_d.getClusters();
/*  28: 82 */     Vector clusterNames = new Vector();
/*  29:    */     try
/*  30:    */     {
/*  31:    */       for (;;)
/*  32:    */       {
/*  33: 89 */         String line = this.reader_d.readLine();
/*  34: 90 */         if (line == null) {
/*  35:    */           break;
/*  36:    */         }
/*  37: 93 */         if (!line.equals(""))
/*  38:    */         {
/*  39:100 */           StringTokenizer tok = new StringTokenizer(line, ", =");
/*  40:101 */           String first = tok.nextToken();
/*  41:102 */           if ((first.charAt(0) != '/') || (first.charAt(1) != '/'))
/*  42:    */           {
/*  43:109 */             StringTokenizer tok2 = new StringTokenizer(first, "()");
/*  44:110 */             tok2.nextToken();
/*  45:111 */             String cname = tok2.nextToken();
/*  46:112 */             clusterNames.addElement(cname);
/*  47:117 */             while (tok.hasMoreTokens())
/*  48:    */             {
/*  49:118 */               String next = tok.nextToken();
/*  50:119 */               if ((next.charAt(0) == '/') && (next.charAt(1) == '/'))
/*  51:    */               {
/*  52:120 */                 linecount--;
/*  53:121 */                 break;
/*  54:    */               }
/*  55:124 */               for (int i = 0; i < nodes.length; i++) {
/*  56:125 */                 if (nodes[i].getName().equals(next)) {
/*  57:126 */                   clusters[i] = linecount;
/*  58:    */                 }
/*  59:    */               }
/*  60:    */             }
/*  61:130 */             linecount++;
/*  62:    */           }
/*  63:    */         }
/*  64:    */       }
/*  65:    */     }
/*  66:    */     catch (Exception e)
/*  67:    */     {
/*  68:134 */       e.printStackTrace();
/*  69:    */     }
/*  70:136 */     return this.graph_d;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean areAllNodesInCluster()
/*  74:    */   {
/*  75:141 */     int[] clusters = this.graph_d.getClusters();
/*  76:146 */     for (int i = 0; i < clusters.length; i++) {
/*  77:147 */       if (clusters[i] == -1) {
/*  78:148 */         return false;
/*  79:    */       }
/*  80:    */     }
/*  81:150 */     return true;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public ArrayList getNodesNotAssignedToClusters()
/*  85:    */   {
/*  86:155 */     int[] clusters = this.graph_d.getClusters();
/*  87:156 */     ArrayList<String> nodeList = new ArrayList();
/*  88:157 */     Node[] nodes = this.graph_d.getNodes();
/*  89:162 */     for (int i = 0; i < clusters.length; i++) {
/*  90:163 */       if (clusters[i] == -1) {
/*  91:164 */         nodeList.add(nodes[i].getName());
/*  92:    */       }
/*  93:    */     }
/*  94:167 */     if (nodeList.isEmpty()) {
/*  95:168 */       return null;
/*  96:    */     }
/*  97:170 */     return nodeList;
/*  98:    */   }
/*  99:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.ClusterFileParser
 * JD-Core Version:    0.7.0.1
 */