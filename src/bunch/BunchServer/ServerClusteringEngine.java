/*  1:   */ package bunch.BunchServer;
/*  2:   */ 
/*  3:   */ import bunch.Cluster;
/*  4:   */ import bunch.ClusteringMethod2;
/*  5:   */ import bunch.GenericDistribHillClimbingClusteringMethod;
/*  6:   */ import bunch.Graph;
/*  7:   */ import bunch.HillClimbingConfiguration;
/*  8:   */ import java.util.Random;
/*  9:   */ 
/* 10:   */ public class ServerClusteringEngine
/* 11:   */ {
/* 12:   */   ServerProperties sProps;
/* 13:   */   
/* 14:   */   public ServerClusteringEngine(ServerProperties sp)
/* 15:   */   {
/* 16:27 */     this.sProps = sp;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean run()
/* 20:   */   {
/* 21:32 */     this.sProps.theGraph.setRandom(new Random(10L));
/* 22:   */     
/* 23:34 */     ClusteringMethod2 cm = new ServerSteepestAscentClusteringMethod();
/* 24:   */     
/* 25:36 */     HillClimbingConfiguration hcc = (HillClimbingConfiguration)cm.getConfiguration();
/* 26:37 */     hcc.setNumOfIterations(1);
/* 27:38 */     hcc.setThreshold(1.0D);
/* 28:39 */     ((GenericDistribHillClimbingClusteringMethod)cm).setConfiguration(hcc);
/* 29:   */     
/* 30:41 */     cm.initialize();
/* 31:42 */     cm.setGraph(this.sProps.theGraph.cloneGraph());
/* 32:43 */     cm.run();
/* 33:44 */     Cluster c = cm.getBestCluster();
/* 34:45 */     Graph g = cm.getBestGraph();
/* 35:   */     
/* 36:47 */     this.sProps.theGraph = g.cloneGraph();
/* 37:   */     
/* 38:49 */     return true;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchServer.ServerClusteringEngine
 * JD-Core Version:    0.7.0.1
 */