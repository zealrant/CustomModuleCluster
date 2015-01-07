/*   1:    */ package bunch.api;
/*   2:    */ 
/*   3:    */ import bunch.BunchPreferences;
/*   4:    */ import bunch.Cluster;
/*   5:    */ import bunch.Graph;
/*   6:    */ import bunch.ObjectiveFunctionCalculatorFactory;
/*   7:    */ import bunch.Parser;
/*   8:    */ import bunch.ParserFactory;
/*   9:    */ import java.beans.Beans;
/*  10:    */ import java.io.BufferedWriter;
/*  11:    */ import java.io.FileWriter;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.util.Hashtable;
/*  14:    */ 
/*  15:    */ public class BunchStatsTest
/*  16:    */ {
/*  17:    */   public BunchStatsTest()
/*  18:    */   {
/*  19: 35 */     checkGraphTest();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void checkGraphTest()
/*  23:    */   {
/*  24:    */     try
/*  25:    */     {
/*  26: 42 */       String filename = "d:\\proj\\bunch\\examples\\bison";
/*  27: 43 */       BunchPreferences pref = (BunchPreferences)Beans.instantiate(null, "bunch.BunchPreferences");
/*  28:    */       
/*  29: 45 */       Parser p = pref.getParserFactory().getParser("dependency");
/*  30: 46 */       p.setInput(filename);
/*  31: 47 */       p.setDelims(" \t");
/*  32: 48 */       Graph g = (Graph)p.parse();
/*  33:    */       
/*  34: 50 */       String objFnCalc = "bunch.TurboMQIncrW";
/*  35: 51 */       pref.getObjectiveFunctionCalculatorFactory().setCurrentCalculator(objFnCalc);
/*  36: 52 */       Graph.setObjectiveFunctionCalculatorFactory(pref.getObjectiveFunctionCalculatorFactory());
/*  37: 53 */       g.setObjectiveFunctionCalculator(objFnCalc);
/*  38: 55 */       if (g == null)
/*  39:    */       {
/*  40: 57 */         System.out.println("The graph is null");
/*  41: 58 */         return;
/*  42:    */       }
/*  43: 61 */       for (int i = 0; i < 100; i++)
/*  44:    */       {
/*  45: 63 */         int[] clusterV = g.genRandomClusterSize();
/*  46: 64 */         Cluster c = new Cluster(g, clusterV);
/*  47: 65 */         System.out.println("NumClusters = " + c.getClusterNames().length + " MQ Value = " + c.getObjFnValue());
/*  48:    */       }
/*  49:    */     }
/*  50:    */     catch (Exception ex)
/*  51:    */     {
/*  52: 69 */       ex.printStackTrace();
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void runStatsTest()
/*  57:    */   {
/*  58: 76 */     String fileName = "e:\\bunchstats.txt";
/*  59:    */     try
/*  60:    */     {
/*  61: 79 */       BufferedWriter writer_d = new BufferedWriter(new FileWriter(fileName));
/*  62: 80 */       double mqAccum = 0.0D;
/*  63: 81 */       int testRuns = 0;
/*  64: 82 */       String header = "Run Number, Runtime(ms), Best MQ, Depth, Number of Clusters, MQ Evaluations, SA Neighbors Taken";
/*  65: 83 */       System.out.println(header);
/*  66: 84 */       writer_d.write(header + "\r\n");
/*  67: 86 */       for (int i = 0; i < 100; i++)
/*  68:    */       {
/*  69: 88 */         BunchAPI api = new BunchAPI();
/*  70: 89 */         BunchProperties bp = new BunchProperties();
/*  71: 90 */         bp.setProperty("MDGInputFile", "e:\\incl");
/*  72: 91 */         bp.setProperty("OutputFormat", "NullOutputFormat");
/*  73:    */         
/*  74: 93 */         bp.setProperty("ClusteringAlgorithm", "NAHC");
/*  75: 94 */         bp.setProperty("NAHCHillClimbPct", "1");
/*  76:    */         
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:100 */         api.setProperties(bp);
/*  82:    */         
/*  83:102 */         api.run();
/*  84:    */         
/*  85:104 */         Hashtable results = api.getResults();
/*  86:    */         
/*  87:106 */         String rt = (String)results.get("Runtime");
/*  88:107 */         String evals = (String)results.get("MQEvaluations");
/*  89:108 */         String levels = (String)results.get("TotalClusterLevels");
/*  90:109 */         String saMovesTaken = (String)results.get("SANeighborsTaken");
/*  91:    */         
/*  92:111 */         Hashtable[] resultLevels = (Hashtable[])results.get("ResultClusterObjects");
/*  93:    */         
/*  94:113 */         String mq = "null";
/*  95:114 */         String depth = "null";
/*  96:115 */         String numC = "null";
/*  97:116 */         if (resultLevels.length >= 1)
/*  98:    */         {
/*  99:118 */           Hashtable lvlResults = resultLevels[0];
/* 100:119 */           mq = (String)lvlResults.get("MQValue");
/* 101:120 */           depth = (String)lvlResults.get("BestClusterDepth");
/* 102:121 */           numC = (String)lvlResults.get("BestPartitionClusters");
/* 103:    */         }
/* 104:124 */         String outString = i + "," + rt + "," + mq + "," + depth + "," + numC + "," + evals + "," + saMovesTaken;
/* 105:125 */         System.out.println(outString);
/* 106:126 */         writer_d.write(outString + "\r\n");
/* 107:127 */         testRuns++;
/* 108:128 */         mqAccum += Double.parseDouble(mq);
/* 109:    */       }
/* 110:130 */       writer_d.close();
/* 111:131 */       System.out.println();
/* 112:132 */       System.out.println("***** Average MQ = " + mqAccum / testRuns);
/* 113:    */     }
/* 114:    */     catch (Exception e)
/* 115:    */     {
/* 116:135 */       e.printStackTrace();
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static void main(String[] args)
/* 121:    */   {
/* 122:139 */     BunchStatsTest bunchStatsTest1 = new BunchStatsTest();
/* 123:    */   }
/* 124:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.BunchStatsTest
 * JD-Core Version:    0.7.0.1
 */