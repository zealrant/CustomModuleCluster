/*   1:    */ package bunch.api;
/*   2:    */ 
/*   3:    */ import java.io.BufferedWriter;
/*   4:    */ import java.io.FileWriter;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.sql.Connection;
/*   7:    */ import java.sql.DriverManager;
/*   8:    */ import java.sql.PreparedStatement;
/*   9:    */ import java.sql.Statement;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Collection;
/*  12:    */ import java.util.Enumeration;
/*  13:    */ import java.util.Hashtable;
/*  14:    */ import java.util.Random;
/*  15:    */ import java.util.Vector;
/*  16:    */ 
/*  17:    */ public class BunchAPISimAnalysis
/*  18:    */ {
/*  19: 26 */   String testID = "CIAT1";
/*  20: 27 */   Connection con = null;
/*  21: 28 */   PreparedStatement writeRecord = null;
/*  22: 30 */   double pr = 0.0D;
/*  23: 31 */   double pr1 = 0.0D;
/*  24: 32 */   double es = 0.0D;
/*  25: 33 */   double es1 = 0.0D;
/*  26: 34 */   double mc = 0.0D;
/*  27: 35 */   double mc1 = 0.0D;
/*  28: 36 */   int total = 0;
/*  29:    */   
/*  30:    */   public BunchAPISimAnalysis()
/*  31:    */   {
/*  32: 39 */     this.testID = "CIAT1";
/*  33:    */     
/*  34:    */ 
/*  35: 42 */     doTest("e:\\hack\\hack", "e:\\hack\\hack", 25);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void randomHack(String baseFName, int count, int mcount)
/*  39:    */   {
/*  40: 51 */     Random r = new Random(System.currentTimeMillis());
/*  41: 53 */     for (int i = 0; i < count; i++)
/*  42:    */     {
/*  43: 55 */       int base = 30 + r.nextInt(10);
/*  44: 56 */       Hashtable h = new Hashtable();
/*  45: 57 */       h.clear();
/*  46: 59 */       for (int j = 0; j < base; j++)
/*  47:    */       {
/*  48: 61 */         String id = "SS_" + j;
/*  49: 62 */         h.put(id, new Vector());
/*  50:    */       }
/*  51: 65 */       for (int j = 0; j < mcount; j++)
/*  52:    */       {
/*  53: 67 */         int ssID = r.nextInt(base);
/*  54: 68 */         String mName = "M" + j;
/*  55: 69 */         String ssStrID = "SS_" + ssID;
/*  56: 70 */         Vector v = (Vector)h.get(ssStrID);
/*  57: 71 */         if (v == null) {
/*  58: 71 */           System.out.println("A BUG...");
/*  59:    */         }
/*  60: 72 */         v.add(mName);
/*  61:    */       }
/*  62: 75 */       dumpRandomOutput(baseFName, i, h);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void dumpRandomOutput(String baseFName, int id, Hashtable h)
/*  67:    */   {
/*  68: 81 */     String outFileName = baseFName + id + ".bunch";
/*  69:    */     try
/*  70:    */     {
/*  71: 84 */       BufferedWriter writer = new BufferedWriter(new FileWriter(outFileName));
/*  72: 85 */       Enumeration e = h.keys();
/*  73: 86 */       while (e.hasMoreElements())
/*  74:    */       {
/*  75: 88 */         String ssKey = (String)e.nextElement();
/*  76: 89 */         Vector v = (Vector)h.get(ssKey);
/*  77: 90 */         if (v.size() != 0)
/*  78:    */         {
/*  79: 93 */           writer.write("SS(" + ssKey + ")=");
/*  80: 94 */           for (int j = 0; j < v.size(); j++)
/*  81:    */           {
/*  82: 96 */             String mname = (String)v.elementAt(j);
/*  83: 97 */             writer.write(mname);
/*  84: 98 */             if (j < v.size() - 1) {
/*  85: 99 */               writer.write(",");
/*  86:    */             } else {
/*  87:101 */               writer.write("\n");
/*  88:    */             }
/*  89:    */           }
/*  90:    */         }
/*  91:    */       }
/*  92:105 */       writer.close();
/*  93:    */     }
/*  94:    */     catch (Exception ex)
/*  95:    */     {
/*  96:109 */       ex.printStackTrace();
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void genHackMDG(String baseFName, int howMany)
/* 101:    */   {
/* 102:116 */     String outFileName = baseFName;
/* 103:117 */     Random r = new Random(System.currentTimeMillis());
/* 104:    */     try
/* 105:    */     {
/* 106:120 */       BufferedWriter writer = new BufferedWriter(new FileWriter(outFileName));
/* 107:122 */       for (long i = 0L; i < 10 * howMany; i += 1L)
/* 108:    */       {
/* 109:124 */         int rNum = r.nextInt(howMany * howMany);
/* 110:125 */         int m1 = rNum / howMany;
/* 111:126 */         int m2 = rNum % howMany;
/* 112:127 */         String M1 = "M" + m1;
/* 113:128 */         String M2 = "M" + m2;
/* 114:129 */         writer.write(M1 + " " + M2 + "\n");
/* 115:    */       }
/* 116:139 */       writer.close();
/* 117:    */     }
/* 118:    */     catch (Exception ex)
/* 119:    */     {
/* 120:143 */       ex.printStackTrace();
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void doTest(String mdgFileName, String baseFileName, int howMany)
/* 125:    */   {
/* 126:148 */     BunchGraph[] bgList = new BunchGraph[howMany];
/* 127:152 */     for (int i = 0; i < howMany; i++)
/* 128:    */     {
/* 129:154 */       Integer idx = new Integer(i);
/* 130:155 */       String fn = baseFileName + idx.toString() + ".bunch";
/* 131:156 */       bgList[i] = BunchGraphUtils.constructFromSil(mdgFileName, fn);
/* 132:    */     }
/* 133:159 */     doPrecisionRecall("PR", bgList);
/* 134:160 */     this.pr = this.pr1;
/* 135:161 */     doEdgeSim("ES", bgList);
/* 136:162 */     this.es = this.es1;
/* 137:163 */     doMecl("MECL", bgList);
/* 138:164 */     this.mc = this.mc1;
/* 139:    */     
/* 140:166 */     this.pr1 = (this.es1 = this.mc1 = 0.0D);
/* 141:168 */     for (int i = 0; i < howMany; i++) {
/* 142:169 */       bgList[i].determineIsomorphic();
/* 143:    */     }
/* 144:171 */     doPrecisionRecall("PRNOI", bgList);
/* 145:172 */     doEdgeSim("ESNOI", bgList);
/* 146:173 */     doMecl("MECLNOI", bgList);
/* 147:    */     
/* 148:175 */     this.total = 0;
/* 149:176 */     for (int i = 0; i < howMany; i++) {
/* 150:177 */       for (int j = i + 1; j < howMany; j++) {
/* 151:178 */         this.total += 1;
/* 152:    */       }
/* 153:    */     }
/* 154:181 */     System.out.println();
/* 155:182 */     int numNodes = bgList[0].getNodes().size();
/* 156:    */     
/* 157:184 */     System.out.println("Node Count = " + numNodes);
/* 158:185 */     System.out.println("AVG(PR) = " + this.pr / this.total);
/* 159:186 */     System.out.println("AVG(ES) = " + this.es / this.total);
/* 160:187 */     System.out.println("AVG(MC) = " + this.mc / this.total);
/* 161:188 */     System.out.println("AVG(PR_NOS) = " + this.pr1 / this.total);
/* 162:189 */     System.out.println("AVG(ES_NOS) = " + this.es1 / this.total);
/* 163:190 */     System.out.println("AVG(MC_NOS) = " + this.mc1 / this.total);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void doPrecisionRecall(String fn, BunchGraph[] bgList)
/* 167:    */   {
/* 168:195 */     int sz = bgList.length;
/* 169:196 */     for (int i = 0; i < sz; i++) {
/* 170:197 */       for (int j = i + 1; j < sz; j++)
/* 171:    */       {
/* 172:199 */         BunchGraph bg1 = bgList[i];
/* 173:200 */         BunchGraph bg2 = bgList[j];
/* 174:201 */         Hashtable ht1 = BunchGraphUtils.calcPR(bg1, bg2);
/* 175:202 */         Double prValue = (Double)ht1.get("AVERAGE");
/* 176:203 */         System.out.print(fn + "(" + i + "," + j + ") = ");
/* 177:204 */         if (prValue != null)
/* 178:    */         {
/* 179:206 */           double dTmp = prValue.doubleValue() * 100.0D;
/* 180:207 */           this.pr1 += dTmp;
/* 181:208 */           System.out.println((int)dTmp);
/* 182:    */         }
/* 183:    */         else
/* 184:    */         {
/* 185:211 */           System.out.println("0");
/* 186:    */         }
/* 187:    */       }
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void doEdgeSim(String fn, BunchGraph[] bgList)
/* 192:    */   {
/* 193:217 */     int sz = bgList.length;
/* 194:218 */     for (int i = 0; i < sz; i++) {
/* 195:219 */       for (int j = i + 1; j < sz; j++)
/* 196:    */       {
/* 197:221 */         BunchGraph bg1 = bgList[i];
/* 198:222 */         BunchGraph bg2 = bgList[j];
/* 199:    */         
/* 200:224 */         Double esValue = new Double(BunchGraphUtils.calcEdgeSimiliarities(bg1, bg2));
/* 201:    */         
/* 202:226 */         System.out.print(fn + "(" + i + "," + j + ") = ");
/* 203:227 */         if (esValue != null)
/* 204:    */         {
/* 205:229 */           double dTmp = esValue.doubleValue() * 100.0D;
/* 206:230 */           this.es1 += dTmp;
/* 207:231 */           System.out.println((int)dTmp);
/* 208:    */         }
/* 209:    */         else
/* 210:    */         {
/* 211:234 */           System.out.println("0");
/* 212:    */         }
/* 213:    */       }
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void doMecl(String fn, BunchGraph[] bgList)
/* 218:    */   {
/* 219:240 */     int sz = bgList.length;
/* 220:241 */     for (int i = 0; i < sz; i++) {
/* 221:242 */       for (int j = i + 1; j < sz; j++)
/* 222:    */       {
/* 223:244 */         BunchGraph bg1 = bgList[i];
/* 224:245 */         BunchGraph bg2 = bgList[j];
/* 225:    */         
/* 226:247 */         Hashtable meClValue1 = BunchGraphUtils.getMeClMeasurement(bg1, bg2);
/* 227:248 */         Hashtable meClValue2 = BunchGraphUtils.getMeClMeasurement(bg2, bg1);
/* 228:    */         
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:253 */         Double meclValue1 = (Double)meClValue1.get("MeclQualityMetric");
/* 233:254 */         Double meclValue2 = (Double)meClValue2.get("MeclQualityMetric");
/* 234:255 */         double d1 = meclValue1.doubleValue();
/* 235:256 */         double d2 = meclValue2.doubleValue();
/* 236:    */         
/* 237:    */ 
/* 238:259 */         long simEdges = BunchGraphUtils.calcSimEdges(bg1, bg2);
/* 239:260 */         long totalEdges = bg1.getEdges().size();
/* 240:261 */         long diffEdges = totalEdges - simEdges;
/* 241:    */         
/* 242:263 */         double mc1 = BunchGraphUtils.getMeClDistance(bg1, bg2);
/* 243:264 */         long mc2 = BunchGraphUtils.getMeClDistance(bg2, bg1);
/* 244:267 */         if (diffEdges != mc1 + mc2) {
/* 245:268 */           System.out.println("EDGESIM = " + diffEdges + "   MC=" + (mc1 + mc2));
/* 246:    */         }
/* 247:275 */         double d = Math.max(d1, d2);
/* 248:    */         
/* 249:277 */         Double meclValue = new Double(d);
/* 250:    */         
/* 251:279 */         System.out.print(fn + "(" + i + "," + j + ") = ");
/* 252:280 */         if (meclValue != null)
/* 253:    */         {
/* 254:282 */           double dTmp = meclValue.doubleValue() * 100.0D;
/* 255:283 */           mc1 =  (mc1 + dTmp);
/* 256:284 */           System.out.println((int)dTmp);
/* 257:    */         }
/* 258:    */         else
/* 259:    */         {
/* 260:287 */           System.out.println("0");
/* 261:    */         }
/* 262:    */       }
/* 263:    */     }
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setDB()
/* 267:    */   {
/* 268:    */     try
/* 269:    */     {
/* 270:295 */       Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
/* 271:    */       
/* 272:297 */       this.con = DriverManager.getConnection("jdbc:odbc:MyInventory");
/* 273:    */       
/* 274:299 */       String stmt = "insert into ClustResults values ( ? , ? , ? , ?)";
/* 275:300 */       this.writeRecord = this.con.prepareStatement(stmt);
/* 276:    */       
/* 277:302 */       String delStmt = "delete from ClustResults where TESTID = '" + this.testID + "'";
/* 278:303 */       Statement s = this.con.createStatement();
/* 279:304 */       s.executeUpdate(delStmt);
/* 280:    */     }
/* 281:    */     catch (Exception e)
/* 282:    */     {
/* 283:315 */       e.printStackTrace();
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void buildCmpDB(BunchGraph bg1, BunchGraph bg2) {}
/* 288:    */   
/* 289:    */   public void echoCmpGraph(int idx, BunchGraph bg)
/* 290:    */   {
/* 291:325 */     ArrayList bnList = new ArrayList(bg.getNodes());
/* 292:326 */     int maxSize = bnList.size();
/* 293:328 */     for (int i = 0; i < maxSize; i++)
/* 294:    */     {
/* 295:330 */       BunchNode n1 = (BunchNode)bnList.get(i);
/* 296:331 */       for (int j = i; j < maxSize; j++)
/* 297:    */       {
/* 298:335 */         BunchNode n2 = (BunchNode)bnList.get(j);
/* 299:    */         int result;
/* 301:336 */         if (n1.getCluster() == n2.getCluster()) {
/* 302:337 */           result = 1;
/* 303:    */         } else {
/* 304:339 */           result = 0;
/* 305:    */         }
/* 306:    */         try
/* 307:    */         {
/* 308:343 */           this.writeRecord.setString(1, this.testID);
/* 309:344 */           this.writeRecord.setString(2, n1.getName());
/* 310:345 */           this.writeRecord.setString(3, n2.getName());
/* 311:346 */           this.writeRecord.setInt(4, result);
/* 312:    */           
/* 313:348 */           this.writeRecord.executeUpdate();
/* 314:    */         }
/* 315:    */         catch (Exception e)
/* 316:    */         {
/* 317:350 */           e.printStackTrace();
/* 318:    */         }
/* 319:    */       }
/* 320:    */     }
/* 321:    */   }
/* 322:    */   
/* 323:    */   public static void main(String[] args)
/* 324:    */   {
/* 325:357 */     System.out.println("HACK1");
/* 326:358 */     BunchAPISimAnalysis bunchAPISimAnalysis1 = new BunchAPISimAnalysis();
/* 327:    */   }
/* 328:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.BunchAPISimAnalysis
 * JD-Core Version:    0.7.0.1
 */