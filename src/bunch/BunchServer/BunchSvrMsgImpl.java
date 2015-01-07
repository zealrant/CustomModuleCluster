/*   1:    */ package bunch.BunchServer;
/*   2:    */ 
/*   3:    */ import bunch.BunchPreferences;
/*   4:    */ import bunch.Callback;
/*   5:    */ import bunch.Cluster;
/*   6:    */ import bunch.Graph;
/*   7:    */ import bunch.SwingWorker;
/*   8:    */ import bunch.util.BunchUtilities;
/*   9:    */ import java.awt.Dimension;
/*  10:    */ import java.awt.Point;
/*  11:    */ import java.io.PrintStream;
/*  12:    */ import java.rmi.RemoteException;
/*  13:    */ import javax.rmi.PortableRemoteObject;
/*  14:    */ 
/*  15:    */ public class BunchSvrMsgImpl
/*  16:    */   extends PortableRemoteObject
/*  17:    */   implements BunchSvrMsg
/*  18:    */ {
/*  19: 36 */   Callback clientCallback = null;
/*  20: 37 */   private ServerProperties sp = new ServerProperties();
/*  21: 38 */   BSWindow parent = null;
/*  22: 39 */   ServerClusteringProgress progressWindow = null;
/*  23: 40 */   String jndiName = "";
/*  24: 41 */   boolean textMode = false;
/*  25:    */   
/*  26:    */   public BunchSvrMsgImpl()
/*  27:    */     throws RemoteException
/*  28:    */   {}
/*  29:    */   
/*  30:    */   public void setGUIMode()
/*  31:    */   {
/*  32: 48 */     this.textMode = false;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setTextMode()
/*  36:    */   {
/*  37: 51 */     this.textMode = true;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setJndiName(String s)
/*  41:    */   {
/*  42: 55 */     this.jndiName = s;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setParent(BSWindow win)
/*  46:    */   {
/*  47: 59 */     this.parent = win;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean invokeMessage(String name, byte[] serializedClass)
/*  51:    */   {
/*  52: 63 */     if (name.equals("Init"))
/*  53:    */     {
/*  54: 65 */       DistribInit di = (DistribInit)BunchUtilities.fromByteArray(serializedClass);
/*  55: 66 */       System.out.println("Got the init message");
/*  56: 67 */       this.sp.svrName = di.svrName;
/*  57: 68 */       this.sp.svrID = di.svrID;
/*  58: 69 */       this.sp.theGraph = di.theGraph;
/*  59: 70 */       this.sp.objFn = di.objFunction;
/*  60: 71 */       this.sp.clusteringMethod = di.clusteringTechnique;
/*  61: 72 */       this.sp.bp = di.bp;
/*  62: 73 */       this.sp.adaptiveEnabled = di.adaptiveEnabled;
/*  63: 74 */       this.sp.jndiName = this.jndiName;
/*  64: 75 */       Graph.setObjectiveFunctionCalculatorFactory(this.sp.bp.getObjectiveFunctionCalculatorFactory());
/*  65:    */       
/*  66: 77 */       System.out.println("The number of nodes in the graph is:  " + this.sp.theGraph.getNumberOfNodes());
/*  67:    */     }
/*  68:    */     else
/*  69:    */     {
/*  70: 79 */       if (name.equals("Run"))
/*  71:    */       {
/*  72: 81 */         String msg = "Just got the run message";
/*  73: 83 */         if (this.parent != null) {
/*  74: 84 */           this.parent.appendLogMsg(msg);
/*  75:    */         } else {
/*  76: 86 */           System.out.println(msg);
/*  77:    */         }
/*  78: 88 */         ServerClusteringEngine sce = new ServerClusteringEngine(this.sp);
/*  79: 89 */         boolean rc = sce.run();
/*  80:    */         
/*  81: 91 */         msg = "Just finished running the server";
/*  82: 93 */         if (this.parent != null) {
/*  83: 94 */           this.parent.appendLogMsg(msg);
/*  84:    */         } else {
/*  85: 96 */           System.out.println(msg);
/*  86:    */         }
/*  87: 98 */         double objFnValue = this.sp.theGraph.getObjectiveFunctionValue();
/*  88:    */         
/*  89:100 */         return rc;
/*  90:    */       }
/*  91:102 */       if (name.equals("StartIteration"))
/*  92:    */       {
/*  93:115 */         final byte[] sc = serializedClass;
/*  94:116 */         SwingWorker worker_d = new SwingWorker()
/*  95:    */         {
/*  96:    */           public Object construct()
/*  97:    */           {
/*  98:118 */             IterationManager starti = (IterationManager)BunchUtilities.fromByteArray(sc);
/*  99:119 */             Cluster workCluster = new Cluster(BunchSvrMsgImpl.this.sp.theGraph, starti.clusterVector);
/* 100:120 */             ClusterUsingVectorSAHC cThread = new ClusterUsingVectorSAHC(BunchSvrMsgImpl.this.parent, BunchSvrMsgImpl.this.sp, workCluster, BunchSvrMsgImpl.this.clientCallback);
/* 101:121 */             cThread.setProgressWindow(BunchSvrMsgImpl.this.progressWindow);
/* 102:122 */             cThread.run();
/* 103:    */             
/* 104:124 */             return "Done";
/* 105:    */           }
/* 106:    */           
/* 107:    */           public void finished() {}
/* 108:128 */         };
/* 109:129 */         worker_d.setPriority(1);
/* 110:130 */         worker_d.start();
/* 111:    */         
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:137 */         return true;
/* 118:    */       }
/* 119:139 */       if (name.equals("Done"))
/* 120:    */       {
/* 121:141 */         String msg = "Best Objective Function Value Found = " + this.sp.bestObjFnValue;
/* 122:142 */         if (this.progressWindow != null)
/* 123:    */         {
/* 124:144 */           this.progressWindow.dispose();
/* 125:145 */           this.progressWindow = null;
/* 126:    */         }
/* 127:148 */         if (this.parent != null) {
/* 128:149 */           this.parent.appendLogMsg(msg);
/* 129:    */         } else {
/* 130:151 */           System.out.println(msg);
/* 131:    */         }
/* 132:    */       }
/* 133:153 */       else if (name.equals("Start"))
/* 134:    */       {
/* 135:155 */         if ((this.progressWindow == null) && (!this.textMode))
/* 136:    */         {
/* 137:157 */           this.progressWindow = new ServerClusteringProgress(this.parent, "Clustering In Progress...", false);
/* 138:    */           
/* 139:159 */           Dimension dlgSize = this.progressWindow.getPreferredSize();
/* 140:160 */           Dimension frmSize = this.parent.getSize();
/* 141:161 */           Point loc = this.parent.getLocation();
/* 142:162 */           this.progressWindow.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
/* 143:    */           
/* 144:164 */           this.progressWindow.setVisible(true);
/* 145:165 */           this.progressWindow.repaint();
/* 146:    */         }
/* 147:    */         else
/* 148:    */         {
/* 149:169 */           System.out.println("Clustering in progress...");
/* 150:170 */           String msg = "Adaptive Algorithm:  ";
/* 151:171 */           if ((this.sp.adaptiveEnabled = true) != false) {
/* 152:172 */             msg = msg + "Enabled";
/* 153:    */           } else {
/* 154:174 */             msg = msg + "Disabled";
/* 155:    */           }
/* 156:175 */           System.out.println(msg);
/* 157:    */         }
/* 158:    */       }
/* 159:    */     }
/* 160:180 */     return true;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public boolean registerCallback(Callback c)
/* 164:    */   {
/* 165:185 */     this.clientCallback = c;
/* 166:186 */     this.sp.clientCB = c;
/* 167:187 */     System.out.println("Got client callback object");
/* 168:    */     try
/* 169:    */     {
/* 170:189 */       c.callFromServer("Hello from server");
/* 171:    */     }
/* 172:    */     catch (Exception ex) {}
/* 173:192 */     return true;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean doAction(String command)
/* 177:    */   {
/* 178:197 */     return true;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public Callback getClientCallback()
/* 182:    */   {
/* 183:201 */     return this.clientCallback;
/* 184:    */   }
/* 185:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchServer.BunchSvrMsgImpl
 * JD-Core Version:    0.7.0.1
 */