/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import bunch.BunchServer.BunchSvrMsg;
/*   4:    */ import bunch.BunchServer.IterationManager;
/*   5:    */ import bunch.util.BunchUtilities;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.util.Hashtable;
/*   8:    */ import java.util.Stack;
/*   9:    */ import java.util.Vector;
/*  10:    */ import javax.naming.Binding;
/*  11:    */ 
/*  12:    */ public class DistributedHCClusteringMethod
/*  13:    */   extends GenericDistribHillClimbingClusteringMethod
/*  14:    */ {
/*  15:    */   public static final int STAT_PENDING = 0;
/*  16:    */   public static final int STAT_CHECKED_OUT = 1;
/*  17:    */   public static final int STAT_FINISHED = 2;
/*  18:    */   public static final int NO_SERVER_WORKING = -1;
/*  19:    */   int[] workQueue;
/*  20:    */   int[] serverWorkingElement;
/*  21:    */   int[] status;
/*  22:    */   int pendingCount;
/*  23:    */   int checkedOutCount;
/*  24:    */   int finishedCount;
/*  25: 54 */   int currWorkVectorIdx = 0;
/*  26: 55 */   int totalCount = 0;
/*  27: 56 */   int baseUnitSize = 5;
/*  28: 58 */   Stack workQ = new Stack();
/*  29: 59 */   Hashtable pendingQ = new Hashtable();
/*  30: 61 */   int msgIDCtr = 0;
/*  31: 62 */   transient int runningServers = 0;
/*  32:    */   
/*  33:    */   private boolean initWorkVectors(Cluster c)
/*  34:    */   {
/*  35: 76 */     if (c == null) {
/*  36: 76 */       return false;
/*  37:    */     }
/*  38: 78 */     int[] cv = c.getClusterVector();
/*  39: 79 */     int vSize = cv.length;
/*  40: 80 */     this.totalCount = vSize;
/*  41:    */     
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45: 85 */     this.workQ.clear();
/*  46: 86 */     this.pendingQ.clear();
/*  47: 92 */     if (this.workQueue == null)
/*  48:    */     {
/*  49: 94 */       this.workQueue = new int[vSize];
/*  50: 95 */       this.serverWorkingElement = new int[vSize];
/*  51: 96 */       this.status = new int[vSize];
/*  52:    */     }
/*  53:102 */     if (this.workQueue.length != vSize)
/*  54:    */     {
/*  55:104 */       this.workQueue = new int[vSize];
/*  56:105 */       this.serverWorkingElement = new int[vSize];
/*  57:106 */       this.status = new int[vSize];
/*  58:    */     }
/*  59:110 */     this.runningServers = 0;
/*  60:    */     
/*  61:112 */     this.currWorkVectorIdx = 0;
/*  62:118 */     for (int i = 0; i < vSize; i++)
/*  63:    */     {
/*  64:120 */       this.workQueue[i] = i;
/*  65:121 */       this.serverWorkingElement[i] = -1;
/*  66:122 */       this.status[i] = 0;
/*  67:123 */       this.workQ.add(new Integer(i));
/*  68:    */     }
/*  69:129 */     this.pendingCount = 0;
/*  70:130 */     this.checkedOutCount = 0;
/*  71:131 */     this.finishedCount = 0;
/*  72:    */     
/*  73:133 */     return true;
/*  74:    */   }
/*  75:    */   
/*  76:    */   private boolean startIteration(Cluster c)
/*  77:    */   {
/*  78:148 */     Vector svrV = this.activeServerVector;
/*  79:149 */     IterationManager im = new IterationManager();
/*  80:150 */     initWorkVectors(c);
/*  81:    */     
/*  82:    */ 
/*  83:153 */     im.workVector = c.getClusterVector();
/*  84:154 */     im.clusterVector = c.getClusterVector();
/*  85:155 */     im.msgID = (++this.msgIDCtr);
/*  86:    */     
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:160 */     byte[] so = BunchUtilities.toByteArray(im);
/*  91:166 */     for (int i = 0; i < svrV.size(); i++)
/*  92:    */     {
/*  93:168 */       Binding b = (Binding)this.activeServerVector.elementAt(i);
/*  94:169 */       if (so != null)
/*  95:    */       {
/*  96:171 */         BunchSvrMsg bsm = (BunchSvrMsg)b.getObject();
/*  97:    */         try
/*  98:    */         {
/*  99:173 */           boolean rc = bsm.invokeMessage("StartIteration", so);
/* 100:174 */           synchronized (this)
/* 101:    */           {
/* 102:176 */             if (rc == true) {
/* 103:177 */               this.runningServers += 1;
/* 104:    */             }
/* 105:    */           }
/* 106:    */         }
/* 107:    */         catch (Exception ex)
/* 108:    */         {
/* 109:181 */           System.out.println("EXCEPTION - startIteration():  " + ex.toString());
/* 110:182 */           return false;
/* 111:    */         }
/* 112:    */       }
/* 113:    */     }
/* 114:186 */     return true;
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected Cluster getLocalMaxGraph(Cluster c)
/* 118:    */   {
/* 119:197 */     this.eventQ.setManagerThread(Thread.currentThread());
/* 120:198 */     initWorkVectors(c);
/* 121:    */     
/* 122:200 */     Cluster maxC = c.cloneCluster();
/* 123:201 */     Cluster intermC = c.cloneCluster();
/* 124:    */     
/* 125:203 */     double originalMax = maxC.getObjFnValue();
/* 126:204 */     double maxOF = originalMax;
/* 127:    */     
/* 128:206 */     int[] clustNames = c.getClusterNames();
/* 129:207 */     int[] clusters = c.getClusterVector();
/* 130:208 */     long maxPartitionsToExamine = clustNames.length;
/* 131:209 */     int currClustersExamined = 0;
/* 132:210 */     double evalPct = ((NAHCConfiguration)this.configuration_d).getMinPctToConsider() / 100.0D;
/* 133:211 */     double partitionsToExamine = (maxPartitionsToExamine * evalPct);
/* 134:    */     
/* 135:213 */     boolean exceededMaxExamination = false;
/* 136:    */     try
/* 137:    */     {
/* 138:222 */       boolean rc = startIteration(c);
/* 139:223 */       if (!rc) {
/* 140:223 */         return c;
/* 141:    */       }
/* 142:228 */       while (isMoreWork())
/* 143:    */       {
/* 144:235 */         BunchEvent be = this.eventQ.getEvent();
/* 145:238 */         if ((be.getEventObj() instanceof WorkRequestEvent))
/* 146:    */         {
/* 147:240 */           WorkRequestEvent wre = (WorkRequestEvent)be.getEventObj();
/* 148:    */           
/* 149:    */ 
/* 150:243 */           wre.workToDo = getMoreWork(wre.requestWorkSz);
/* 151:244 */           if (wre.workToDo == null) {
/* 152:245 */             wre.actualWorkSz = 0;
/* 153:    */           } else {
/* 154:247 */             wre.actualWorkSz = wre.workToDo.length;
/* 155:    */           }
/* 156:250 */           if (exceededMaxExamination == true)
/* 157:    */           {
/* 158:252 */             wre.workToDo = null;
/* 159:253 */             wre.actualWorkSz = 0;
/* 160:    */           }
/* 161:    */         }
/* 162:257 */         else if ((be.getEventObj() instanceof WorkFinishedEvent))
/* 163:    */         {
/* 164:259 */           WorkFinishedEvent wfe = (WorkFinishedEvent)be.getEventObj();
/* 165:260 */           intermC.setClusterVector(wfe.clusterVector);
/* 166:261 */           this.finishedCount += wfe.clusterVector.length;
/* 167:264 */           synchronized (this)
/* 168:    */           {
/* 169:266 */             this.runningServers -= 1;
/* 170:    */           }
/* 171:271 */           if (intermC.getObjFnValue() > maxOF)
/* 172:    */           {
/* 173:272 */             maxC.copyFromCluster(intermC);
/* 174:273 */             maxOF = maxC.getObjFnValue();
/* 175:275 */             if (this.finishedCount >= partitionsToExamine) {
/* 176:276 */               exceededMaxExamination = true;
/* 177:    */             }
/* 178:    */           }
/* 179:    */         }
/* 180:282 */         this.eventQ.releaseEvent(be);
/* 181:    */       }
/* 182:    */     }
/* 183:    */     catch (Exception e)
/* 184:    */     {
/* 185:287 */       System.out.println("EXCEPTION - getLocalMaxGraph():  " + e.toString());
/* 186:288 */       return c;
/* 187:    */     }
/* 188:291 */     if (maxOF > originalMax) {
/* 189:294 */       c.copyFromCluster(maxC);
/* 190:    */     } else {
/* 191:299 */       c.setConverged(true);
/* 192:    */     }
/* 193:307 */     return c;
/* 194:    */   }
/* 195:    */   
/* 196:    */   boolean isMoreWork()
/* 197:    */     throws Exception
/* 198:    */   {
/* 199:318 */     if (this.currWorkVectorIdx == this.totalCount) {
/* 200:320 */       synchronized (this)
/* 201:    */       {
/* 202:322 */         if (this.runningServers == 0) {
/* 203:323 */           return false;
/* 204:    */         }
/* 205:325 */         return true;
/* 206:    */       }
/* 207:    */     }
/* 208:329 */     return true;
/* 209:    */   }
/* 210:    */   
/* 211:    */   int[] getMoreWork(int requestSz)
/* 212:    */   {
/* 213:344 */     int start = this.currWorkVectorIdx;
/* 214:345 */     int pos = start;
/* 215:346 */     int maxPos = this.totalCount;
/* 216:347 */     int end = Math.min(pos + requestSz, maxPos);
/* 217:    */     
/* 218:349 */     int delta = end - start;
/* 219:    */     
/* 220:351 */     this.currWorkVectorIdx += delta;
/* 221:352 */     this.checkedOutCount += delta;
/* 222:355 */     if (delta == 0) {
/* 223:356 */       return null;
/* 224:    */     }
/* 225:358 */     int[] workArea = new int[delta];
/* 226:363 */     for (int i = 0; i < delta; i++)
/* 227:    */     {
/* 228:365 */       workArea[i] = this.workQueue[(pos++)];
/* 229:366 */       this.status[i] = 0;
/* 230:    */     }
/* 231:369 */     return workArea;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public Configuration getConfiguration()
/* 235:    */   {
/* 236:380 */     boolean reconf = false;
/* 237:385 */     if (this.configuration_d == null)
/* 238:    */     {
/* 239:386 */       this.configuration_d = new NAHCConfiguration();
/* 240:387 */       reconf = true;
/* 241:    */     }
/* 242:390 */     NAHCConfiguration hc = (NAHCConfiguration)this.configuration_d;
/* 243:395 */     if (reconf)
/* 244:    */     {
/* 245:396 */       hc.setThreshold(1.0D);
/* 246:397 */       hc.setNumOfIterations(1);
/* 247:398 */       hc.setPopulationSize(1);
/* 248:399 */       hc.setMinPctToConsider(0);
/* 249:400 */       hc.setRandomizePct(100);
/* 250:401 */       hc.setSATechnique(null);
/* 251:    */     }
/* 252:404 */     return hc;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setDefaultConfiguration()
/* 256:    */   {
/* 257:414 */     HillClimbingConfiguration hc = (HillClimbingConfiguration)super.getConfiguration();
/* 258:    */     
/* 259:416 */     hc.setThreshold(0.1D);
/* 260:417 */     hc.setNumOfIterations(100);
/* 261:418 */     hc.setPopulationSize(5);
/* 262:    */     
/* 263:420 */     setConfiguration(hc);
/* 264:    */   }
/* 265:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.DistributedHCClusteringMethod
 * JD-Core Version:    0.7.0.1
 */