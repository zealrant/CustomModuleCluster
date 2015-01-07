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
/*  12:    */ public class DistributedSAHCClusteringMethod
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
/*  25: 53 */   int currWorkVectorIdx = 0;
/*  26: 54 */   int totalCount = 0;
/*  27: 55 */   int baseUnitSize = 5;
/*  28: 57 */   Stack workQ = new Stack();
/*  29: 58 */   Hashtable pendingQ = new Hashtable();
/*  30: 60 */   int msgIDCtr = 0;
/*  31: 61 */   transient int runningServers = 0;
/*  32:    */   
/*  33:    */   private boolean initWorkVectors(Cluster c)
/*  34:    */   {
/*  35: 75 */     if (c == null) {
/*  36: 75 */       return false;
/*  37:    */     }
/*  38: 77 */     int[] cv = c.getClusterVector();
/*  39: 78 */     int vSize = cv.length;
/*  40: 79 */     this.totalCount = vSize;
/*  41:    */     
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45: 84 */     this.workQ.clear();
/*  46: 85 */     this.pendingQ.clear();
/*  47: 90 */     if (this.workQueue == null)
/*  48:    */     {
/*  49: 92 */       this.workQueue = new int[vSize];
/*  50: 93 */       this.serverWorkingElement = new int[vSize];
/*  51: 94 */       this.status = new int[vSize];
/*  52:    */     }
/*  53:100 */     if (this.workQueue.length != vSize)
/*  54:    */     {
/*  55:102 */       this.workQueue = new int[vSize];
/*  56:103 */       this.serverWorkingElement = new int[vSize];
/*  57:104 */       this.status = new int[vSize];
/*  58:    */     }
/*  59:108 */     this.runningServers = 0;
/*  60:    */     
/*  61:110 */     this.currWorkVectorIdx = 0;
/*  62:116 */     for (int i = 0; i < vSize; i++)
/*  63:    */     {
/*  64:118 */       this.workQueue[i] = i;
/*  65:119 */       this.serverWorkingElement[i] = -1;
/*  66:120 */       this.status[i] = 0;
/*  67:121 */       this.workQ.add(new Integer(i));
/*  68:    */     }
/*  69:127 */     this.pendingCount = 0;
/*  70:128 */     this.checkedOutCount = 0;
/*  71:129 */     this.finishedCount = 0;
/*  72:    */     
/*  73:131 */     return true;
/*  74:    */   }
/*  75:    */   
/*  76:    */   private boolean startIteration(Cluster c)
/*  77:    */   {
/*  78:146 */     Vector svrV = this.activeServerVector;
/*  79:147 */     IterationManager im = new IterationManager();
/*  80:148 */     initWorkVectors(c);
/*  81:    */     
/*  82:    */ 
/*  83:151 */     im.workVector = c.getClusterVector();
/*  84:152 */     im.clusterVector = c.getClusterVector();
/*  85:153 */     im.msgID = (++this.msgIDCtr);
/*  86:    */     
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:158 */     byte[] so = BunchUtilities.toByteArray(im);
/*  91:164 */     for (int i = 0; i < svrV.size(); i++)
/*  92:    */     {
/*  93:166 */       Binding b = (Binding)this.activeServerVector.elementAt(i);
/*  94:167 */       if (so != null)
/*  95:    */       {
/*  96:169 */         BunchSvrMsg bsm = (BunchSvrMsg)b.getObject();
/*  97:    */         try
/*  98:    */         {
/*  99:171 */           boolean rc = bsm.invokeMessage("StartIteration", so);
/* 100:172 */           synchronized (this)
/* 101:    */           {
/* 102:174 */             if (rc == true) {
/* 103:175 */               this.runningServers += 1;
/* 104:    */             }
/* 105:    */           }
/* 106:    */         }
/* 107:    */         catch (Exception ex)
/* 108:    */         {
/* 109:179 */           System.out.println("EXCEPTION - startIteration():  " + ex.toString());
/* 110:180 */           return false;
/* 111:    */         }
/* 112:    */       }
/* 113:    */     }
/* 114:184 */     return true;
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected Cluster getLocalMaxGraph(Cluster c)
/* 118:    */   {
/* 119:195 */     this.eventQ.setManagerThread(Thread.currentThread());
/* 120:196 */     initWorkVectors(c);
/* 121:    */     
/* 122:198 */     Cluster maxC = c.cloneCluster();
/* 123:199 */     Cluster intermC = c.cloneCluster();
/* 124:    */     
/* 125:201 */     double originalMax = maxC.getObjFnValue();
/* 126:202 */     double maxOF = originalMax;
/* 127:    */     try
/* 128:    */     {
/* 129:209 */       boolean rc = startIteration(c);
/* 130:210 */       if (!rc) {
/* 131:210 */         return c;
/* 132:    */       }
/* 133:215 */       while (isMoreWork())
/* 134:    */       {
/* 135:222 */         BunchEvent be = this.eventQ.getEvent();
/* 136:225 */         if ((be.getEventObj() instanceof WorkRequestEvent))
/* 137:    */         {
/* 138:227 */           WorkRequestEvent wre = (WorkRequestEvent)be.getEventObj();
/* 139:    */           
/* 140:    */ 
/* 141:230 */           wre.workToDo = getMoreWork(wre.requestWorkSz);
/* 142:231 */           if (wre.workToDo == null) {
/* 143:232 */             wre.actualWorkSz = 0;
/* 144:    */           } else {
/* 145:234 */             wre.actualWorkSz = wre.workToDo.length;
/* 146:    */           }
/* 147:    */         }
/* 148:237 */         else if ((be.getEventObj() instanceof WorkFinishedEvent))
/* 149:    */         {
/* 150:239 */           WorkFinishedEvent wfe = (WorkFinishedEvent)be.getEventObj();
/* 151:240 */           intermC.setClusterVector(wfe.clusterVector);
/* 152:241 */           this.finishedCount += wfe.clusterVector.length;
/* 153:244 */           synchronized (this)
/* 154:    */           {
/* 155:246 */             this.runningServers -= 1;
/* 156:    */           }
/* 157:251 */           if (intermC.getObjFnValue() > maxOF)
/* 158:    */           {
/* 159:252 */             maxC.copyFromCluster(intermC);
/* 160:253 */             maxOF = maxC.getObjFnValue();
/* 161:    */           }
/* 162:    */         }
/* 163:260 */         this.eventQ.releaseEvent(be);
/* 164:    */       }
/* 165:    */     }
/* 166:    */     catch (Exception e)
/* 167:    */     {
/* 168:265 */       System.out.println("EXCEPTION - getLocalMaxGraph():  " + e.toString());
/* 169:266 */       return c;
/* 170:    */     }
/* 171:269 */     if (maxOF > originalMax) {
/* 172:271 */       c.copyFromCluster(maxC);
/* 173:    */     } else {
/* 174:275 */       c.setConverged(true);
/* 175:    */     }
/* 176:283 */     return c;
/* 177:    */   }
/* 178:    */   
/* 179:    */   boolean isMoreWork()
/* 180:    */     throws Exception
/* 181:    */   {
/* 182:294 */     if (this.currWorkVectorIdx == this.totalCount) {
/* 183:296 */       synchronized (this)
/* 184:    */       {
/* 185:298 */         if (this.runningServers == 0) {
/* 186:299 */           return false;
/* 187:    */         }
/* 188:301 */         return true;
/* 189:    */       }
/* 190:    */     }
/* 191:305 */     return true;
/* 192:    */   }
/* 193:    */   
/* 194:    */   int[] getMoreWork(int requestSz)
/* 195:    */   {
/* 196:321 */     int start = this.currWorkVectorIdx;
/* 197:322 */     int pos = start;
/* 198:323 */     int maxPos = this.totalCount;
/* 199:324 */     int end = Math.min(pos + requestSz, maxPos);
/* 200:    */     
/* 201:326 */     int delta = end - start;
/* 202:    */     
/* 203:328 */     this.currWorkVectorIdx += delta;
/* 204:329 */     this.checkedOutCount += delta;
/* 205:332 */     if (delta == 0) {
/* 206:333 */       return null;
/* 207:    */     }
/* 208:335 */     int[] workArea = new int[delta];
/* 209:340 */     for (int i = 0; i < delta; i++)
/* 210:    */     {
/* 211:342 */       workArea[i] = this.workQueue[(pos++)];
/* 212:343 */       this.status[i] = 0;
/* 213:    */     }
/* 214:346 */     return workArea;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public Configuration getConfiguration()
/* 218:    */   {
/* 219:357 */     boolean reconf = false;
/* 220:362 */     if (this.configuration_d == null) {
/* 221:363 */       reconf = true;
/* 222:    */     }
/* 223:366 */     HillClimbingConfiguration hc = (HillClimbingConfiguration)super.getConfiguration();
/* 224:371 */     if (reconf)
/* 225:    */     {
/* 226:372 */       hc.setThreshold(0.1D);
/* 227:373 */       hc.setNumOfIterations(100);
/* 228:374 */       hc.setPopulationSize(5);
/* 229:    */     }
/* 230:376 */     return hc;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setDefaultConfiguration()
/* 234:    */   {
/* 235:386 */     HillClimbingConfiguration hc = (HillClimbingConfiguration)super.getConfiguration();
/* 236:    */     
/* 237:388 */     hc.setThreshold(0.1D);
/* 238:389 */     hc.setNumOfIterations(100);
/* 239:390 */     hc.setPopulationSize(5);
/* 240:    */     
/* 241:392 */     setConfiguration(hc);
/* 242:    */   }
/* 243:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.DistributedSAHCClusteringMethod
 * JD-Core Version:    0.7.0.1
 */