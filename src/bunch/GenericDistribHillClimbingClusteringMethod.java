/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import bunch.BunchServer.BunchSvrMsg;
/*   4:    */ import bunch.BunchServer.IterationManager;
/*   5:    */ import bunch.util.BunchUtilities;
/*   6:    */ import java.io.BufferedWriter;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.util.Vector;
/*   9:    */ import javax.naming.Binding;
/*  10:    */ 
/*  11:    */ public abstract class GenericDistribHillClimbingClusteringMethod
/*  12:    */   extends GenericClusteringMethod2
/*  13:    */ {
/*  14:    */   NAHCConfiguration config_d;
/*  15: 56 */   protected BunchEvent event = null;
/*  16: 57 */   protected Vector activeServerVector = null;
/*  17: 58 */   protected CallbackImpl svrCallback = null;
/*  18: 59 */   protected SynchronizedEventQueue eventQ = null;
/*  19: 60 */   protected int BaseUOWSize = 5;
/*  20: 61 */   protected boolean useAdaptiveAlg = true;
/*  21:    */   
/*  22:    */   public GenericDistribHillClimbingClusteringMethod()
/*  23:    */   {
/*  24: 66 */     setConfigurable(true);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void setEventObject(BunchEvent be)
/*  28:    */   {
/*  29: 75 */     this.event = be;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setAdaptiveEnable(boolean b)
/*  33:    */   {
/*  34: 82 */     this.useAdaptiveAlg = b;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setUOWSize(int i)
/*  38:    */   {
/*  39: 88 */     this.BaseUOWSize = i;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setActiveServerVector(Vector v)
/*  43:    */   {
/*  44: 95 */     this.activeServerVector = v;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setSvrCallback(CallbackImpl cb)
/*  48:    */   {
/*  49:104 */     this.svrCallback = cb;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setEventQueue(SynchronizedEventQueue eq)
/*  53:    */   {
/*  54:112 */     this.eventQ = eq;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public SynchronizedEventQueue getEventQueue()
/*  58:    */   {
/*  59:118 */     return this.eventQ;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void makeEventQueue()
/*  63:    */   {
/*  64:124 */     this.eventQ = new SynchronizedEventQueue(Thread.currentThread());
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void initBunchEvent()
/*  68:    */   {
/*  69:131 */     this.event = new BunchEvent();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void init()
/*  73:    */   {
/*  74:142 */     this.config_d = ((NAHCConfiguration)getConfiguration());
/*  75:    */     
/*  76:144 */     setNumOfExperiments(this.config_d.getNumOfIterations());
/*  77:145 */     setThreshold(this.config_d.getThreshold());
/*  78:146 */     setPopSize(this.config_d.getPopulationSize());
/*  79:    */     
/*  80:148 */     super.init();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean nextGeneration()
/*  84:    */   {
/*  85:159 */     long[] sequence = new long[this.currentPopulation_d.size()];
/*  86:162 */     if (this.configuration_d.runBatch_d == true)
/*  87:    */     {
/*  88:164 */       System.out.println("Run Batch = " + this.configuration_d.runBatch_d);
/*  89:165 */       System.out.println("Exp Number = " + this.configuration_d.expNumber_d);
/*  90:    */     }
/*  91:    */     try
/*  92:    */     {
/*  93:170 */       String outLine = "";
/*  94:171 */       String sCluster = "";
/*  95:172 */       String sAligned = "";
/*  96:    */       
/*  97:174 */       manageDistribWorkIteration("Start");
/*  98:177 */       for (int i = 0; i < this.currentPopulation_d.size(); i++) {
/*  99:178 */         sequence[i] = 0L;
/* 100:    */       }
/* 101:207 */       boolean end = false;
/* 102:209 */       while (!end)
/* 103:    */       {
/* 104:211 */         end = true;
/* 105:212 */         for (int i = 0; i < this.currentPopulation_d.size(); i++)
/* 106:    */         {
/* 107:214 */           Cluster dbgC = this.currentPopulation_d.getCluster(i);
/* 108:216 */           if (!this.currentPopulation_d.getCluster(i).isMaximum())
/* 109:    */           {
/* 110:218 */             if (this.configuration_d.runBatch_d == true)
/* 111:    */             {
/* 112:220 */               int exp = this.configuration_d.expNumber_d;
/* 113:221 */               sCluster = "";
/* 114:222 */               sAligned = "";
/* 115:223 */               int[] n = this.currentPopulation_d.getCluster(i).getClusterVector();
/* 116:    */               
/* 117:225 */               int[] c = new int[n.length];
/* 118:227 */               for (int z = 0; z < n.length; z++) {
/* 119:228 */                 c[z] = n[z];
/* 120:    */               }
/* 121:230 */               realignClusters(c);
/* 122:232 */               for (int zz = 0; zz < n.length; zz++)
/* 123:    */               {
/* 124:234 */                 sAligned = sAligned + c[zz] + "|";
/* 125:235 */                 sCluster = sCluster + n[zz] + "|";
/* 126:    */               }
/* 127:238 */               sequence[i] += 1L;
/* 128:239 */               outLine = exp + "," + i + "," + sequence[i] + "," + this.currentPopulation_d.getCluster(i).getObjFnValue() + "," + sCluster + "," + sAligned;
/* 129:240 */               this.configuration_d.writer_d.write(outLine + "\r\n");
/* 130:    */             }
/* 131:243 */             getLocalMaxGraph(this.currentPopulation_d.getCluster(i));
/* 132:    */           }
/* 133:245 */           if (!this.currentPopulation_d.getCluster(i).isMaximum()) {
/* 134:247 */             end = false;
/* 135:    */           }
/* 136:249 */           if (this.currentPopulation_d.getCluster(i).getObjFnValue() > getBestCluster().getObjFnValue()) {
/* 137:252 */             setBestCluster(this.currentPopulation_d.getCluster(i).cloneCluster());
/* 138:    */           }
/* 139:    */         }
/* 140:    */       }
/* 141:257 */       manageDistribWorkIteration("Done");
/* 142:258 */       return end;
/* 143:    */     }
/* 144:    */     catch (Exception e)
/* 145:    */     {
/* 146:262 */       manageDistribWorkIteration("Done");
/* 147:    */     }
/* 148:263 */     return false;
/* 149:    */   }
/* 150:    */   
/* 151:    */   private void manageDistribWorkIteration(String activity)
/* 152:    */   {
/* 153:272 */     Vector svrV = this.activeServerVector;
/* 154:274 */     if (svrV == null) {
/* 155:275 */       return;
/* 156:    */     }
/* 157:277 */     IterationManager im = new IterationManager();
/* 158:    */     
/* 159:279 */     byte[] so = BunchUtilities.toByteArray(im);
/* 160:281 */     for (int i = 0; i < svrV.size(); i++)
/* 161:    */     {
/* 162:283 */       Binding b = (Binding)this.activeServerVector.elementAt(i);
/* 163:284 */       if (so != null)
/* 164:    */       {
/* 165:286 */         BunchSvrMsg bsm = (BunchSvrMsg)b.getObject();
boolean rc;
/* 166:    */         try
/* 167:    */         {
/* 168:288 */           rc = bsm.invokeMessage(activity, so);
/* 169:    */         }
/* 170:    */         catch (Exception ex)
/* 171:    */         {
/* 172:    */           
/* 173:290 */           System.out.println("EXCEPTION - manageDistribWorkIteration(" + activity + "):  " + ex.toString());
/* 174:291 */           return;
/* 175:    */         }
/* 176:    */       }
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   private void realignClusters(int[] c)
/* 181:    */   {
/* 182:303 */     int[] map = new int[c.length];
/* 183:304 */     int index = 0;
/* 184:306 */     for (int i = 0; i < c.length; i++) {
/* 185:307 */       map[i] = -1;
/* 186:    */     }
/* 187:309 */     for (int j = 0; j < c.length; j++)
/* 188:    */     {
/* 189:311 */       int clus = c[j];
/* 190:313 */       if (map[clus] == -1)
/* 191:    */       {
/* 192:315 */         index++;
/* 193:316 */         map[clus] = index;
/* 194:    */       }
/* 195:    */     }
/* 196:320 */     for (int k = 0; k < c.length; k++) {
/* 197:322 */       c[k] = map[c[k]];
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   protected abstract Cluster getLocalMaxGraph(Cluster paramCluster);
/* 202:    */   
/* 203:    */   public void reInit()
/* 204:    */   {
/* 205:339 */     this.currentPopulation_d.shuffle();
/* 206:    */   }
/* 207:    */   
/* 208:    */   public String getConfigurationDialogName()
/* 209:    */   {
/* 210:355 */     return "bunch.HillClimbingClusteringConfigurationDialog";
/* 211:    */   }
/* 212:    */   
/* 213:    */   public Configuration getConfiguration()
/* 214:    */   {
/* 215:371 */     if (this.configuration_d == null) {
/* 216:372 */       this.configuration_d = new HillClimbingConfiguration();
/* 217:    */     }
/* 218:374 */     return this.configuration_d;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setConfiguration(HillClimbingConfiguration c)
/* 222:    */   {
/* 223:381 */     this.configuration_d = c;
/* 224:    */   }
/* 225:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.GenericDistribHillClimbingClusteringMethod
 * JD-Core Version:    0.7.0.1
 */