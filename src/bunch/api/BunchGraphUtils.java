/*   1:    */ package bunch.api;
/*   2:    */ 
/*   3:    */ import bunch.ClusterFileParser;
/*   4:    */ import bunch.DependencyFileParser;
/*   5:    */ import bunch.Graph;
/*   6:    */ import bunch.Node;
/*   7:    */ import bunch.ObjectiveFunctionCalculatorFactory;
/*   8:    */ import bunch.Parser;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Collection;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.Hashtable;
/*  13:    */ import java.util.Iterator;
/*  14:    */ 
/*  15:    */ public class BunchGraphUtils
/*  16:    */ {
/*  17:    */   public static final String MECL_VALUE = "MeclValue";
/*  18:    */   public static final String MECL_QUALITY_METRIC = "MeclQualityMetric";
/*  19:    */   
/*  20:    */   public static Collection getModuleNames(String mdgFileName)
/*  21:    */   {
/*  22: 41 */     ArrayList al = new ArrayList();
/*  23:    */     
/*  24: 43 */     Parser p = new DependencyFileParser();
/*  25: 44 */     p.setInput(mdgFileName);
/*  26: 45 */     p.setDelims(" ,\t");
/*  27:    */     
/*  28: 47 */     Graph g = (Graph)p.parse();
/*  29:    */     
/*  30: 49 */     Node[] na = g.getNodes();
/*  31: 50 */     for (int i = 0; i < na.length; i++)
/*  32:    */     {
/*  33: 52 */       Node n = na[i];
/*  34: 53 */       al.add(n.getName());
/*  35:    */     }
/*  36: 56 */     return al;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static BunchGraph constructFromSil(String mdgFileName, String sFileName)
/*  40:    */   {
/*  41: 61 */     return constructFromSil(mdgFileName, sFileName, null);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static BunchGraph constructFromMdg(String mdgFileName)
/*  45:    */   {
/*  46: 66 */     BunchGraph bg = new BunchGraph();
/*  47:    */     
/*  48: 68 */     Parser p = new DependencyFileParser();
/*  49: 69 */     p.setInput(mdgFileName);
/*  50: 70 */     p.setDelims(" ,\t");
/*  51:    */     
/*  52: 72 */     Graph g = (Graph)p.parse();
/*  53: 73 */     Graph g1 = g.cloneAllNodesCluster();
/*  54:    */     
/*  55: 75 */     ObjectiveFunctionCalculatorFactory ocf = new ObjectiveFunctionCalculatorFactory();
/*  56: 76 */     Graph.setObjectiveFunctionCalculatorFactory(ocf);
/*  57:    */     
/*  58: 78 */     g1.calculateObjectiveFunctionValue();
/*  59: 79 */     bg.construct(g);
/*  60: 80 */     return bg;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static boolean isSilFileOK(String mdgFileName, String sFileName)
/*  64:    */   {
/*  65: 85 */     BunchGraph bg = new BunchGraph();
/*  66:    */     
/*  67: 87 */     Parser p = new DependencyFileParser();
/*  68: 88 */     p.setInput(mdgFileName);
/*  69: 89 */     p.setDelims(" ,\t");
/*  70:    */     
/*  71: 91 */     Graph g = (Graph)p.parse();
/*  72:    */     
/*  73: 93 */     ClusterFileParser cfp = new ClusterFileParser();
/*  74: 94 */     cfp.setInput(sFileName);
/*  75: 95 */     cfp.setObject(g);
/*  76: 96 */     cfp.parse();
/*  77:    */     
/*  78: 98 */     return cfp.areAllNodesInCluster();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static ArrayList getMissingSilNodes(String mdgFileName, String sFileName)
/*  82:    */   {
/*  83:103 */     BunchGraph bg = new BunchGraph();
/*  84:    */     
/*  85:105 */     Parser p = new DependencyFileParser();
/*  86:106 */     p.setInput(mdgFileName);
/*  87:107 */     p.setDelims(" ,\t");
/*  88:    */     
/*  89:109 */     Graph g = (Graph)p.parse();
/*  90:    */     
/*  91:111 */     ClusterFileParser cfp = new ClusterFileParser();
/*  92:112 */     cfp.setInput(sFileName);
/*  93:113 */     cfp.setObject(g);
/*  94:114 */     cfp.parse();
/*  95:    */     
/*  96:116 */     return cfp.getNodesNotAssignedToClusters();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static BunchGraph constructFromSil(String mdgFileName, String sFileName, String mqCalcClass)
/* 100:    */   {
/* 101:122 */     BunchGraph bg = new BunchGraph();
/* 102:    */     
/* 103:124 */     Parser p = new DependencyFileParser();
/* 104:125 */     p.setInput(mdgFileName);
/* 105:126 */     p.setDelims(" ,\t");
/* 106:    */     
/* 107:128 */     Graph g = (Graph)p.parse();
/* 108:    */     
/* 109:130 */     ClusterFileParser cfp = new ClusterFileParser();
/* 110:131 */     cfp.setInput(sFileName);
/* 111:132 */     cfp.setObject(g);
/* 112:133 */     cfp.parse();
/* 113:    */     
/* 114:135 */     ObjectiveFunctionCalculatorFactory ocf = new ObjectiveFunctionCalculatorFactory();
/* 115:136 */     Graph.setObjectiveFunctionCalculatorFactory(ocf);
/* 116:138 */     if (mqCalcClass == null)
/* 117:    */     {
/* 118:140 */       g.setObjectiveFunctionCalculator(ocf.getDefaultMethod());
/* 119:    */     }
/* 120:    */     else
/* 121:    */     {
/* 122:144 */       ocf.setCurrentCalculator(mqCalcClass);
/* 123:145 */       g.setObjectiveFunctionCalculator(mqCalcClass);
/* 124:    */     }
/* 125:147 */     g.calculateObjectiveFunctionValue();
/* 126:148 */     bg.construct(g);
/* 127:149 */     return bg;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static Hashtable calcPR(BunchGraph expert, BunchGraph cluster)
/* 131:    */   {
/* 132:155 */     Hashtable results = new Hashtable();
/* 133:156 */     results.clear();
/* 134:157 */     BunchGraphPR prUtil = new BunchGraphPR(expert, cluster);
/* 135:158 */     boolean rc = prUtil.run();
/* 136:159 */     if (!rc) {
/* 137:160 */       return null;
/* 138:    */     }
/* 139:162 */     results.put("PRECISION", new Double(prUtil.getPrecision()));
/* 140:163 */     results.put("RECALL", new Double(prUtil.getRecall()));
/* 141:    */     
/* 142:165 */     double avgPR = (prUtil.getPrecision() + prUtil.getRecall()) / 2.0D;
/* 143:    */     
/* 144:167 */     results.put("AVERAGE", new Double(avgPR));
/* 145:168 */     return results;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static long getMeClDistance(BunchGraph g1, BunchGraph g2)
/* 149:    */   {
/* 150:173 */     MeCl dist = new MeCl(g1, g2);
/* 151:174 */     long ret = dist.run();
/* 152:175 */     return ret;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public static Hashtable getMeClMeasurement(BunchGraph g1, BunchGraph g2)
/* 156:    */   {
/* 157:180 */     Hashtable h = new Hashtable();
/* 158:181 */     MeCl dist = new MeCl(g1, g2);
/* 159:182 */     long ret = dist.run();
/* 160:183 */     h.put("MeclValue", new Long(ret));
/* 161:    */     
/* 162:185 */     double quality = dist.getQualityMetric();
/* 163:186 */     h.put("MeclQualityMetric", new Double(quality));
/* 164:187 */     return h;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static long calcSimEdges(BunchGraph g1, BunchGraph g2)
/* 168:    */   {
/* 169:192 */     long matches = 0L;
/* 170:193 */     long nomatch = 0L;
/* 171:194 */     long total = 0L;
/* 172:195 */     HashMap g1Lookup = new HashMap();
/* 173:196 */     HashMap g2Lookup = new HashMap();
/* 174:197 */     g1Lookup.clear();
/* 175:198 */     g2Lookup.clear();
/* 176:    */     
/* 177:200 */     Iterator load = g1.getEdges().iterator();
/* 178:201 */     while (load.hasNext())
/* 179:    */     {
/* 180:203 */       BunchEdge be = (BunchEdge)load.next();
/* 181:204 */       String key = be.getSrcNode().getName() + be.getDestNode().getName();
/* 182:205 */       g1Lookup.put(key, be);
/* 183:    */     }
/* 184:208 */     load = g2.getEdges().iterator();
/* 185:209 */     while (load.hasNext())
/* 186:    */     {
/* 187:211 */       BunchEdge be = (BunchEdge)load.next();
/* 188:212 */       String key = be.getSrcNode().getName() + be.getDestNode().getName();
/* 189:213 */       g2Lookup.put(key, be);
/* 190:    */     }
/* 191:216 */     Iterator iG1 = g1.getEdges().iterator();
/* 192:    */     boolean be2InSame;
/* 193:217 */     while (iG1.hasNext())
/* 194:    */     {
/* 195:219 */       total += 1L;
/* 196:220 */       BunchEdge be1 = (BunchEdge)iG1.next();
/* 197:221 */       String key = be1.getSrcNode().getName() + be1.getDestNode().getName();
/* 198:222 */       BunchEdge be2 = (BunchEdge)g2Lookup.get(key);
/* 199:    */       
/* 200:    */ 
/* 201:    */ 
/* 202:    */ 
/* 203:227 */       boolean be1InSame = be1.getSrcNode().getCluster() == be1.getDestNode().getCluster();
/* 204:229 */       if (be1InSame == true)
/* 205:    */       {
/* 206:231 */         BunchNode n1 = be2.getSrcNode();
/* 207:232 */         BunchNode n2 = be2.getDestNode();
/* 208:233 */         if ((n2.isAMemberOfCluster(n1.getMemberCluster())) || (n1.isAMemberOfCluster(n2.getMemberCluster()))) {
/* 209:235 */           matches += 1L;
/* 210:    */         }
/* 211:    */       }
/* 212:    */       else
/* 213:    */       {
/* 214:239 */         BunchNode n1 = be2.getSrcNode();
/* 215:240 */         BunchNode n2 = be2.getDestNode();
/* 216:241 */         if ((n2.isAMemberOfCluster(n1.getMemberCluster())) || (n1.isAMemberOfCluster(n2.getMemberCluster())))
/* 217:    */         {
/* 218:244 */           if ((n2.memberOfHowManyClusters() > 1) || (n1.memberOfHowManyClusters() > 1)) {
/* 219:246 */             matches += 1L;
/* 220:    */           }
/* 221:    */         }
/* 222:    */         else {
/* 223:249 */           matches += 1L;
/* 224:    */         }
/* 225:    */       }
/* 226:252 */       be2InSame = be2.getSrcNode().getCluster() == be2.getDestNode().getCluster();
/* 227:    */     }
/* 228:266 */     if (total == 0L) {
/* 229:266 */       return 0L;
/* 230:    */     }
/* 231:267 */     return matches;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public static double calcEdgeSim(BunchGraph g1, BunchGraph g2)
/* 235:    */   {
/* 236:272 */     return calcEdgeSimiliarities(g1, g2);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public static double calcEdgeSimiliarities(BunchGraph g1, BunchGraph g2)
/* 240:    */   {
/* 241:277 */     long matches = 0L;
/* 242:278 */     long nomatch = 0L;
/* 243:279 */     long total = 0L;
/* 244:280 */     HashMap g1Lookup = new HashMap();
/* 245:281 */     HashMap g2Lookup = new HashMap();
/* 246:282 */     g1Lookup.clear();
/* 247:283 */     g2Lookup.clear();
/* 248:    */     
/* 249:285 */     Iterator load = g1.getEdges().iterator();
/* 250:286 */     while (load.hasNext())
/* 251:    */     {
/* 252:288 */       BunchEdge be = (BunchEdge)load.next();
/* 253:289 */       String key = be.getSrcNode().getName() + be.getDestNode().getName();
/* 254:290 */       g1Lookup.put(key, be);
/* 255:    */     }
/* 256:293 */     load = g2.getEdges().iterator();
/* 257:294 */     while (load.hasNext())
/* 258:    */     {
/* 259:296 */       BunchEdge be = (BunchEdge)load.next();
/* 260:297 */       String key = be.getSrcNode().getName() + be.getDestNode().getName();
/* 261:298 */       g2Lookup.put(key, be);
/* 262:    */     }
/* 263:301 */     Iterator iG1 = g1.getEdges().iterator();
/* 264:    */     boolean be2InSame;
/* 265:302 */     while (iG1.hasNext())
/* 266:    */     {
/* 267:304 */       total += 1L;
/* 268:305 */       BunchEdge be1 = (BunchEdge)iG1.next();
/* 269:306 */       String key = be1.getSrcNode().getName() + be1.getDestNode().getName();
/* 270:307 */       BunchEdge be2 = (BunchEdge)g2Lookup.get(key);
/* 271:    */       
/* 272:    */ 
/* 273:    */ 
/* 274:    */ 
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:315 */       boolean be1InSame = be1.getSrcNode().getCluster() == be1.getDestNode().getCluster();
/* 279:317 */       if (be1InSame == true)
/* 280:    */       {
/* 281:319 */         BunchNode n1 = be2.getSrcNode();
/* 282:320 */         BunchNode n2 = be2.getDestNode();
/* 283:321 */         if ((n2.isAMemberOfCluster(n1.getMemberCluster())) || (n1.isAMemberOfCluster(n2.getMemberCluster()))) {
/* 284:323 */           matches += 1L;
/* 285:    */         }
/* 286:    */       }
/* 287:    */       else
/* 288:    */       {
/* 289:327 */         BunchNode n1 = be2.getSrcNode();
/* 290:328 */         BunchNode n2 = be2.getDestNode();
/* 291:329 */         if ((n2.isAMemberOfCluster(n1.getMemberCluster())) || (n1.isAMemberOfCluster(n2.getMemberCluster())))
/* 292:    */         {
/* 293:332 */           if ((n2.memberOfHowManyClusters() > 1) || (n1.memberOfHowManyClusters() > 1)) {
/* 294:334 */             matches += 1L;
/* 295:    */           }
/* 296:    */         }
/* 297:    */         else {
/* 298:337 */           matches += 1L;
/* 299:    */         }
/* 300:    */       }
/* 301:340 */       be2InSame = be2.getSrcNode().getCluster() == be2.getDestNode().getCluster();
/* 302:    */     }
/* 303:354 */     if (total == 0L) {
/* 304:354 */       return 0.0D;
/* 305:    */     }
/* 306:355 */     return matches / total;
/* 307:    */   }
/* 308:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.BunchGraphUtils
 * JD-Core Version:    0.7.0.1
 */