/*   1:    */ package bunch.api;
/*   2:    */ 
/*   3:    */ import bunch.Graph;
/*   4:    */ import bunch.engine.BunchEngine;
/*   5:    */ import java.io.FileInputStream;
/*   6:    */ import java.io.FileNotFoundException;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStream;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Enumeration;
/*  11:    */ import java.util.Hashtable;
/*  12:    */ 
/*  13:    */ public class BunchAPI
/*  14:    */ {
/*  15:    */   public static final String CALLBACK_OBJECT_REF = "CallbackObjectReference";
/*  16:    */   public static final String CALLBACK_OBJECT_FREQ = "CallbackObjectFrequency";
/*  17:    */   public static final String RUNTIME = "Runtime";
/*  18:    */   public static final String MQEVALUATIONS = "MQEvaluations";
/*  19:    */   public static final String CLUSTER_LEVEL = "ClusterLevel";
/*  20:    */   public static final String MQVALUE = "MQValue";
/*  21:    */   public static final String CLUSTER_DEPTH = "BestClusterDepth";
/*  22:    */   public static final String NUMBER_CLUSTERS = "BestPartitionClusters";
/*  23:    */   public static final String TOTAL_CLUSTER_LEVELS = "TotalClusterLevels";
/*  24:    */   public static final String RESULT_CLUSTER_OBJS = "ResultClusterObjects";
/*  25:    */   public static final String SA_NEIGHBORS_TAKEN = "SANeighborsTaken";
/*  26:    */   public static final String MEDIAN_LEVEL_GRAPH = "MedianLevelGraph";
/*  27:    */   public static final String PR_PRECISION_VALUE = "PRPrecisionValue";
/*  28:    */   public static final String PR_RECALL_VALUE = "PRRecallValue";
/*  29:    */   public static final String MQCALC_RESULT_VALUE = "MQCalcResultValue";
/*  30:    */   public static final String ERROR_HASHTABLE = "ErrorHashtable";
/*  31:    */   public static final String WARNING_HASHTABLE = "WarningHashtable";
/*  32:    */   public static final String REFLEXIVE_EDGE_COUNT = "ReflexiveEdgeCount";
/*  33:    */   public static final String OMNIPRESENT_CLIENT = "OmnipresentClient";
/*  34:    */   public static final String OMNIPRESENT_SUPPLIER = "OmnipresentSupplier";
/*  35:    */   public static final String OMNIPRESENT_CENTRAL = "OmnipresentCentral";
/*  36:    */   public static final String LIBRARY_MODULE = "LibraryModule";
/*  37:    */   BunchProperties bunchProps;
/*  38:    */   Hashtable bunchArgs;
/*  39: 77 */   Hashtable resultsHashtable = null;
/*  40: 78 */   ProgressCallback progressCB = null;
/*  41: 79 */   int progressUpdateFreq = 1000;
/*  42:    */   BunchEngine engine;
/*  43:    */   
/*  44:    */   public BunchAPI()
/*  45:    */   {
/*  46: 83 */     this.bunchArgs = null;
/*  47: 84 */     this.engine = new BunchEngine();
/*  48:    */   }
/*  49:    */   
/*  50:    */   Hashtable loadHTFromProperties(BunchProperties bp)
/*  51:    */   {
/*  52: 89 */     Hashtable h = new Hashtable();
/*  53: 90 */     Enumeration e = bp.propertyNames();
/*  54: 91 */     while (e.hasMoreElements())
/*  55:    */     {
/*  56: 93 */       String key = (String)e.nextElement();
/*  57: 94 */       String value = bp.getProperty(key);
/*  58: 95 */       h.put(key, value);
/*  59:    */     }
/*  60: 98 */     String HCPct = (String)h.get("NAHCHillClimbPct");
/*  61: 99 */     if (HCPct != null)
/*  62:    */     {
/*  63:101 */       Integer pct = new Integer(HCPct);
/*  64:102 */       h.put("NAHCHillClimbPct", pct);
/*  65:103 */       String rndPct = (String)h.get("NAHCRandomizePct");
/*  66:104 */       if (rndPct != null)
/*  67:    */       {
/*  68:106 */         Integer iRndPct = new Integer(rndPct);
/*  69:107 */         h.put("NAHCRandomizePct", iRndPct);
/*  70:    */       }
/*  71:    */     }
/*  72:111 */     String TimeoutTime = (String)h.get("TimoutTime");
/*  73:112 */     if (TimeoutTime != null)
/*  74:    */     {
/*  75:114 */       Integer toTime = new Integer(TimeoutTime);
/*  76:115 */       h.put("TimoutTime", toTime);
/*  77:    */     }
/*  78:118 */     String NAHCPop = (String)h.get("NAHCPopulationSize");
/*  79:119 */     if (NAHCPop != null)
/*  80:    */     {
/*  81:121 */       Integer pop = new Integer(NAHCPop);
/*  82:122 */       h.put("NAHCPopulationSize", pop);
/*  83:    */     }
/*  84:125 */     String SAHCPop = (String)h.get("SAHCPopulationSize");
/*  85:126 */     if (SAHCPop != null)
/*  86:    */     {
/*  87:128 */       Integer pop = new Integer(SAHCPop);
/*  88:129 */       h.put("SAHCPopulationSize", pop);
/*  89:    */     }
/*  90:133 */     return h;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void reset()
/*  94:    */   {
/*  95:138 */     if (this.bunchArgs != null)
/*  96:    */     {
/*  97:140 */       this.bunchArgs.clear();
/*  98:141 */       this.bunchArgs = null;
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setProperties(BunchProperties bp)
/* 103:    */   {
/* 104:147 */     this.bunchProps = bp;
/* 105:148 */     Hashtable htArgs = loadHTFromProperties(bp);
/* 106:149 */     if (this.bunchArgs == null) {
/* 107:150 */       this.bunchArgs = htArgs;
/* 108:    */     } else {
/* 109:152 */       this.bunchArgs.putAll(htArgs);
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setAPIProperty(Object key, Object value)
/* 114:    */   {
/* 115:157 */     if (this.bunchArgs == null) {
/* 116:157 */       this.bunchArgs = new Hashtable();
/* 117:    */     }
/* 118:158 */     this.bunchArgs.put(key, value);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Object removeAPIProperty(Object key)
/* 122:    */   {
/* 123:163 */     if (this.bunchArgs != null) {
/* 124:164 */       return this.bunchArgs.remove(key);
/* 125:    */     }
/* 126:165 */     return null;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setProperties(String fileName)
/* 130:    */     throws FileNotFoundException, IOException
/* 131:    */   {
/* 132:170 */     this.bunchProps = new BunchProperties();
/* 133:171 */     this.bunchProps.load(new FileInputStream(fileName));
/* 134:172 */     this.bunchArgs = loadHTFromProperties(this.bunchProps);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setProperties(InputStream in)
/* 138:    */     throws IOException
/* 139:    */   {
/* 140:177 */     this.bunchProps = new BunchProperties();
/* 141:178 */     this.bunchProps.load(in);
/* 142:179 */     this.bunchArgs = loadHTFromProperties(this.bunchProps);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean validate()
/* 146:    */   {
/* 147:184 */     boolean rc = true;
/* 148:186 */     if (this.bunchProps.getProperty("MDGInputFile") == null) {
/* 149:187 */       rc = false;
/* 150:    */     }
/* 151:189 */     if (this.bunchProps.getProperty("MDGOutputFile") == null) {
/* 152:191 */       if (this.bunchProps.getProperty("OutputDevice").equalsIgnoreCase("OutputFile")) {
/* 153:192 */         rc = false;
/* 154:    */       }
/* 155:    */     }
/* 156:194 */     return rc;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setProgressCallback(ProgressCallback cb)
/* 160:    */   {
/* 161:199 */     String sFreq = this.bunchProps.getProperty("ProgressCallbackFreq");
/* 162:200 */     Integer i = new Integer(sFreq);
/* 163:201 */     setProgressCallback(cb, i.intValue());
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setProgressCallback(ProgressCallback cb, int freqUpdate)
/* 167:    */   {
/* 168:206 */     this.progressCB = cb;
/* 169:207 */     this.progressUpdateFreq = freqUpdate;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public Hashtable getResults()
/* 173:    */   {
/* 174:217 */     return this.engine.getResultsHT();
/* 175:    */   }
/* 176:    */   
/* 177:    */   public Hashtable getSpecialModules(String mdgFileName)
/* 178:    */   {
/* 179:222 */     return this.engine.getDefaultSpecialNodes(mdgFileName);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public Hashtable getSpecialModules(String mdgFileName, double threshold)
/* 183:    */   {
/* 184:227 */     return this.engine.getDefaultSpecialNodes(mdgFileName, threshold);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public boolean run()
/* 188:    */   {
/* 189:232 */     boolean rc = true;
/* 190:233 */     this.resultsHashtable = new Hashtable();
/* 191:234 */     if (this.progressCB != null)
/* 192:    */     {
/* 193:235 */       this.bunchArgs.put("CallbackObjectReference", this.progressCB);
/* 194:236 */       this.bunchArgs.put("CallbackObjectFrequency", new Integer(this.progressUpdateFreq));
/* 195:    */     }
/* 196:239 */     this.engine = new BunchEngine();
/* 197:240 */     this.engine.run(this.bunchArgs);
/* 198:241 */     return rc;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setDebugStats(boolean b)
/* 202:    */   {
/* 203:246 */     this.engine.setDebugStats(b);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public ArrayList getClusters()
/* 207:    */   {
/* 208:251 */     return this.engine.getClusterList();
/* 209:    */   }
/* 210:    */   
/* 211:    */   public BunchGraph getPartitionedGraph()
/* 212:    */   {
/* 213:256 */     return getPartitionedGraph(0);
/* 214:    */   }
/* 215:    */   
/* 216:    */   public ArrayList getPartitionedBunchGraphs()
/* 217:    */   {
/* 218:261 */     Graph baseGraph = this.engine.getBestGraph();
/* 219:262 */     if (baseGraph == null) {
/* 220:262 */       return null;
/* 221:    */     }
/* 222:264 */     int maxLvl = baseGraph.getGraphLevel();
/* 223:265 */     if (maxLvl < 0) {
/* 224:266 */       return null;
/* 225:    */     }
/* 226:268 */     BunchGraph[] bgA = new BunchGraph[maxLvl + 2];
/* 227:    */     
/* 228:270 */     Graph g = baseGraph;
/* 229:271 */     while (g.getGraphLevel() > 0)
/* 230:    */     {
/* 231:273 */       BunchGraph bg = new BunchGraph();
/* 232:274 */       boolean rc = bg.construct(g);
/* 233:275 */       if (!rc) {
/* 234:275 */         return null;
/* 235:    */       }
/* 236:276 */       int lvl = g.getGraphLevel();
/* 237:277 */       bg.setGraphLevel(lvl + 1);
/* 238:278 */       bgA[(lvl + 1)] = bg;
/* 239:279 */       g = g.getPreviousLevelGraph();
/* 240:    */     }
/* 241:283 */     if (g.getGraphLevel() != 0) {
/* 242:283 */       return null;
/* 243:    */     }
/* 244:284 */     BunchGraph bg = new BunchGraph();
/* 245:285 */     boolean rc = bg.construct(g);
/* 246:286 */     if (!rc) {
/* 247:286 */       return null;
/* 248:    */     }
/* 249:287 */     bgA[1] = bg;
/* 250:    */     
/* 251:289 */     int medLevel = Math.max(maxLvl / 2, 0);
/* 252:290 */     medLevel++;
/* 253:291 */     bgA[0] = bgA[medLevel];
/* 254:    */     
/* 255:293 */     ArrayList al = new ArrayList(bgA.length);
/* 256:294 */     for (int i = 0; i < bgA.length; i++) {
/* 257:295 */       al.add(i, bgA[i]);
/* 258:    */     }
/* 259:297 */     return al;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public BunchGraph getPartitionedGraph(int Level)
/* 263:    */   {
/* 264:302 */     Graph baseGraph = this.engine.getBestGraph();
/* 265:303 */     if (baseGraph == null) {
/* 266:303 */       return null;
/* 267:    */     }
/* 268:305 */     int lvl = baseGraph.getGraphLevel();
/* 269:306 */     if ((Level < 0) || (Level > lvl)) {
/* 270:307 */       return null;
/* 271:    */     }
/* 272:309 */     Graph g = baseGraph;
/* 273:310 */     while (g.getGraphLevel() > Level) {
/* 274:311 */       g = g.getPreviousLevelGraph();
/* 275:    */     }
/* 276:313 */     BunchGraph bg = new BunchGraph();
/* 277:314 */     boolean rc = bg.construct(g);
/* 278:315 */     if (!rc) {
/* 279:315 */       return null;
/* 280:    */     }
/* 281:317 */     return bg;
/* 282:    */   }
/* 283:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.BunchAPI
 * JD-Core Version:    0.7.0.1
 */