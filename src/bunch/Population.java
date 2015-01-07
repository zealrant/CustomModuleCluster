/*  1:   */ package bunch;
/*  2:   */ 
/*  3:   */ import java.util.Enumeration;
/*  4:   */ import java.util.Vector;
/*  5:   */ 
/*  6:   */ public class Population
/*  7:   */ {
/*  8:31 */   Vector pop = new Vector();
/*  9:32 */   static Graph g = null;
/* 10:33 */   Cluster bestCluster = null;
/* 11:   */   
/* 12:   */   public Population(Graph graph)
/* 13:   */   {
/* 14:36 */     g = graph.cloneGraph();
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void shuffle()
/* 18:   */   {
/* 19:41 */     for (int i = 0; i < this.pop.size(); i++)
/* 20:   */     {
/* 21:43 */       Cluster c = (Cluster)this.pop.elementAt(i);
/* 22:44 */       g.setClusters(c.getClusterVector());
/* 23:45 */       g.shuffleClusters();
/* 24:46 */       c.setClusterVector(g.getClusters());
/* 25:47 */       c.setConverged(false);
/* 26:   */     }
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void genPopulation(int howMany)
/* 30:   */   {
/* 31:54 */     this.pop.removeAllElements();
/* 32:55 */     for (int i = 0; i < howMany; i++)
/* 33:   */     {
/* 34:61 */       int[] clusterV = g.genRandomClusterSize();
/* 35:62 */       Cluster c = new Cluster(g, clusterV);
/* 36:63 */       this.pop.addElement(c);
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   public int size()
/* 41:   */   {
/* 42:69 */     return this.pop.size();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Cluster getCluster(int whichOne)
/* 46:   */   {
/* 47:74 */     if ((whichOne >= 0) && (whichOne < size())) {
/* 48:75 */       return (Cluster)this.pop.elementAt(whichOne);
/* 49:   */     }
/* 50:77 */     return null;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public Enumeration elements()
/* 54:   */   {
/* 55:82 */     return this.pop.elements();
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.Population
 * JD-Core Version:    0.7.0.1
 */