/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import bunch.stats.StatsManager;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Frame;
/*   7:    */ import java.awt.GridBagConstraints;
/*   8:    */ import java.awt.GridBagLayout;
/*   9:    */ import java.awt.Insets;
/*  10:    */ import java.awt.event.ActionEvent;
/*  11:    */ import java.awt.event.ActionListener;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.util.LinkedList;
/*  14:    */ import javax.swing.BorderFactory;
/*  15:    */ import javax.swing.JButton;
/*  16:    */ import javax.swing.JCheckBox;
/*  17:    */ import javax.swing.JComboBox;
/*  18:    */ import javax.swing.JDialog;
/*  19:    */ import javax.swing.JLabel;
/*  20:    */ import javax.swing.JOptionPane;
/*  21:    */ import javax.swing.JPanel;
/*  22:    */ import javax.swing.JProgressBar;
/*  23:    */ import javax.swing.Timer;
/*  24:    */ import javax.swing.border.Border;
/*  25:    */ 
/*  26:    */ public class ClusteringProgressDialog
/*  27:    */   extends JDialog
/*  28:    */   implements IterationListener
/*  29:    */ {
/*  30:    */   public static final int MODE_END = 1;
/*  31:    */   public static final int MODE_LEVEL = 2;
/*  32:    */   public static final int MODE_TEMP = 3;
/*  33: 85 */   long updateCounter = 0L;
/*  34: 87 */   JPanel panel1 = new JPanel();
/*  35: 88 */   GridBagLayout gridBagLayout1 = new GridBagLayout();
/*  36: 89 */   JLabel percentLabel_d = new JLabel();
/*  37: 90 */   JLabel overallPercentLabel_d = new JLabel();
/*  38: 91 */   JProgressBar overallProgressBar_d = new JProgressBar();
/*  39: 92 */   JLabel timeTitleLabel_d = new JLabel();
/*  40: 93 */   JLabel currentTimeLabel_d = new JLabel();
/*  41: 94 */   BunchFrame frame_d = null;
/*  42: 95 */   GraphOutput graphOutput_x = null;
/*  43: 96 */   ClusteringMethod clusteringMethod_x = null;
/*  44:    */   SwingWorker worker_d;
/*  45: 98 */   int iteration_d = 0;
/*  46:    */   int overallIteration_d;
/*  47: 99 */   boolean finished_d = false;
/*  48:100 */   boolean showOverallProgressBar_d = true;
/*  49:101 */   JLabel jLabel1 = new JLabel();
/*  50:102 */   JLabel IterationsProcessed_st = new JLabel();
/*  51:103 */   JLabel jLabel2 = new JLabel();
/*  52:    */   long startTime;
/*  53:    */   Timer eventTimer;
/*  54:    */   Timer toTimer;
/*  55:107 */   JPanel BestClustPanel = new JPanel();
/*  56:108 */   GridBagLayout gridBagLayout2 = new GridBagLayout();
/*  57:109 */   JLabel jLabel4 = new JLabel();
/*  58:110 */   JLabel jLabel5 = new JLabel();
/*  59:111 */   JLabel DepthCount = new JLabel();
/*  60:112 */   JLabel bestMQValueFound_d = new JLabel();
/*  61:113 */   JLabel MQEvalCount = new JLabel();
/*  62:114 */   JPanel jPanel1 = new JPanel();
/*  63:115 */   JButton outputButton_d = new JButton();
/*  64:116 */   JButton viewPB = new JButton();
/*  65:117 */   JButton pauseButton_d = new JButton();
/*  66:118 */   JButton cancelButton_d = new JButton();
/*  67:119 */   JLabel CurrentActivity = new JLabel();
/*  68:120 */   JLabel jLabel3 = new JLabel();
/*  69:122 */   StatsManager stats = StatsManager.getInstance();
/*  70:123 */   boolean cancelPending = false;
/*  71:124 */   boolean isPaused = false;
/*  72:125 */   JLabel jLabel6 = new JLabel();
/*  73:126 */   JLabel numClusters = new JLabel();
/*  74:127 */   Graph origGraph = null;
/*  75:128 */   RunMonitor monitor = new RunMonitor();
/*  76:129 */   String basicTitle = "";
/*  77:130 */   JLabel progressLbl = new JLabel();
/*  78:131 */   JLabel progressMsg = new JLabel();
/*  79:132 */   boolean isExhaustive = false;
/*  80:133 */   JLabel gotoLBL = new JLabel();
/*  81:134 */   JComboBox lvlViewerCB = new JComboBox();
/*  82:135 */   LinkedList bestCLL = new LinkedList();
/*  83:136 */   Cluster currentViewC = null;
/*  84:    */   
/*  85:    */   public ClusteringProgressDialog(Frame frame, String title, boolean modal)
/*  86:    */   {
/*  87:144 */     super(frame, title, modal);
/*  88:145 */     this.frame_d = ((BunchFrame)frame);
/*  89:    */     
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:150 */     this.startTime = System.currentTimeMillis();
/*  94:151 */     this.graphOutput_x = this.frame_d.getGraphOutput();
/*  95:152 */     this.clusteringMethod_x = this.frame_d.getClusteringMethod();
/*  96:153 */     this.clusteringMethod_x.setIterationListener(this);
/*  97:    */     
/*  98:155 */     String methodName = this.clusteringMethod_x.getClass().getName();
/*  99:156 */     if (methodName.equals("bunch.GAClusteringMethod")) {
/* 100:157 */       this.showOverallProgressBar_d = false;
/* 101:    */     }
/* 102:159 */     this.eventTimer = new Timer(2000, new updateTimer());
/* 103:160 */     this.toTimer = new Timer((int)this.frame_d.getTimoutTime(), new timeoutTimer());
/* 104:    */     try
/* 105:    */     {
/* 106:164 */       jbInit();
/* 107:165 */       pack();
/* 108:    */     }
/* 109:    */     catch (Exception ex)
/* 110:    */     {
/* 111:168 */       ex.printStackTrace();
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void updateTitle(int level)
/* 116:    */   {
/* 117:179 */     setTitle(this.basicTitle + " (Level " + level + ")");
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void startClustering()
/* 121:    */   {
/* 122:202 */     if (this.frame_d.limitRuntime()) {
/* 123:204 */       this.toTimer.start();
/* 124:    */     }
/* 125:207 */     String basicTitle = getTitle();
/* 126:208 */     this.bestCLL.clear();
/* 127:    */     
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:213 */     this.worker_d = new SwingWorker()
/* 132:    */     {
/* 133:    */       public Object construct()
/* 134:    */       {
/* 135:    */         try
/* 136:    */         {
/* 137:222 */           int level = 0;
/* 138:223 */           ClusteringProgressDialog.this.CurrentActivity.setText("Clustering...");
/* 139:224 */           Thread.currentThread().setPriority(1);
/* 140:225 */           ClusteringProgressDialog.this.eventTimer.start();
/* 141:227 */           if (ClusteringProgressDialog.this.frame_d.isAgglomerativeTechnique()) {
/* 142:228 */             ClusteringProgressDialog.this.updateTitle(level);
/* 143:    */           }
/* 144:233 */           ClusteringProgressDialog.this.clusteringMethod_x.run();
/* 145:    */           
/* 146:235 */           ClusteringProgressDialog.this.currentViewC = new Cluster(ClusteringProgressDialog.this.clusteringMethod_x.getBestGraph().cloneGraph(), ClusteringProgressDialog.this.clusteringMethod_x.getBestGraph().getClusters());
/* 147:    */           
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:241 */           ClusteringProgressDialog.this.currentViewC.force();
/* 153:242 */           ClusteringProgressDialog.this.currentViewC.copyFromCluster(ClusteringProgressDialog.this.clusteringMethod_x.getBestCluster());
/* 154:    */           
/* 155:244 */           ClusteringProgressDialog.this.bestCLL.addLast(ClusteringProgressDialog.this.currentViewC);
/* 156:249 */           if (ClusteringProgressDialog.this.frame_d.isAgglomerativeTechnique())
/* 157:    */           {
/* 158:254 */             Graph g = ClusteringProgressDialog.this.clusteringMethod_x.getBestGraph().cloneGraph();
/* 159:    */             
/* 160:256 */             int[] cNames = g.getClusterNames();
/* 161:262 */             while (cNames.length > 1)
/* 162:    */             {
/* 163:264 */               level++;
/* 164:265 */               ClusteringProgressDialog.this.updateTitle(level);
/* 165:    */               
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:270 */               NextLevelGraph nextL = new NextLevelGraph();
/* 170:271 */               Graph newG = nextL.genNextLevelGraph(g);
/* 171:    */               
/* 172:273 */               newG.setPreviousLevelGraph(g);
/* 173:274 */               newG.setGraphLevel(g.getGraphLevel() + 1);
/* 174:    */               
/* 175:276 */               ClusteringProgressDialog.this.clusteringMethod_x.setGraph(newG);
/* 176:277 */               ClusteringProgressDialog.this.clusteringMethod_x.initialize();
/* 177:    */               
/* 178:    */ 
/* 179:    */ 
/* 180:    */ 
/* 181:282 */               ClusteringProgressDialog.this.clusteringMethod_x.run();
/* 182:    */               
/* 183:284 */               ClusteringProgressDialog.this.currentViewC = new Cluster(ClusteringProgressDialog.this.clusteringMethod_x.getBestGraph().cloneGraph(), ClusteringProgressDialog.this.clusteringMethod_x.getBestGraph().getClusters());
/* 184:    */               
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:290 */               ClusteringProgressDialog.this.currentViewC.force();
/* 190:291 */               ClusteringProgressDialog.this.currentViewC.copyFromCluster(ClusteringProgressDialog.this.clusteringMethod_x.getBestCluster());
/* 191:292 */               ClusteringProgressDialog.this.bestCLL.addLast(ClusteringProgressDialog.this.currentViewC);
/* 192:    */               
/* 193:294 */               g = ClusteringProgressDialog.this.clusteringMethod_x.getBestGraph().cloneGraph();
/* 194:    */               
/* 195:296 */               cNames = g.getClusterNames();
/* 196:    */             }
/* 197:    */           }
/* 198:    */         }
/* 199:    */         catch (Exception ex)
/* 200:    */         {
/* 201:300 */           ex.printStackTrace();
/* 202:    */         }
/* 203:305 */         return "Done";
/* 204:    */       }
/* 205:    */       
/* 206:    */       public void interrupt()
/* 207:    */       {
/* 208:313 */         suspend();
/* 209:314 */         super.interrupt();
/* 210:    */       }
/* 211:    */       
/* 212:    */       public void finished()
/* 213:    */       {
/* 214:325 */         ClusteringProgressDialog.this.eventTimer.stop();
/* 215:326 */         ClusteringProgressDialog.this.toTimer.stop();
/* 216:    */         
/* 217:328 */         ClusteringProgressDialog.this.CurrentActivity.setText("Post Processing...");
/* 218:330 */         if (ClusteringProgressDialog.this.showOverallProgressBar_d) {
/* 219:331 */           ClusteringProgressDialog.this.overallProgressBar_d.setValue(ClusteringProgressDialog.this.overallProgressBar_d.getMaximum());
/* 220:    */         }
/* 221:333 */         ClusteringProgressDialog.this.updateStats();
/* 222:    */         
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:338 */         ClusteringProgressDialog.this.outputButton_d.setEnabled(false);
/* 227:339 */         ClusteringProgressDialog.this.pauseButton_d.setEnabled(false);
/* 228:340 */         ClusteringProgressDialog.this.cancelButton_d.setText("Close");
/* 229:345 */         if (ClusteringProgressDialog.this.frame_d.getOutputMethod().equals("Dotty")) {
/* 230:346 */           ClusteringProgressDialog.this.viewPB.setEnabled(true);
/* 231:    */         }
/* 232:351 */         StatsManager.cleanup();
/* 233:    */         
/* 234:353 */         Configuration cTmp = ClusteringProgressDialog.this.clusteringMethod_x.getConfiguration();
/* 235:354 */         if ((cTmp instanceof NAHCConfiguration))
/* 236:    */         {
/* 237:356 */           NAHCConfiguration nahcConf = (NAHCConfiguration)cTmp;
/* 238:357 */           if (nahcConf.getSATechnique() != null) {
/* 239:358 */             nahcConf.getSATechnique().reset();
/* 240:    */           }
/* 241:    */         }
/* 242:364 */         ClusteringProgressDialog.this.outputGraph(1);
/* 243:    */         
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:369 */         ClusteringProgressDialog.this.CurrentActivity.setForeground(Color.red.darker());
/* 248:370 */         ClusteringProgressDialog.this.CurrentActivity.setText("Finished Clustering!");
/* 249:376 */         if (ClusteringProgressDialog.this.frame_d.isAgglomerativeTechnique())
/* 250:    */         {
/* 251:378 */           Graph tmpG = ClusteringProgressDialog.this.clusteringMethod_x.getBestCluster().getGraph();
/* 252:379 */           int gLvl = tmpG.getGraphLevel();
/* 253:380 */           int medianLevel = tmpG.getMedianTree().getGraphLevel();
/* 254:382 */           for (int i = 0; i <= gLvl; i++) {
/* 255:383 */             if (i == 0) {
/* 256:384 */               ClusteringProgressDialog.this.lvlViewerCB.addItem("Level " + i + " <-- Detail Level");
/* 257:385 */             } else if (i == medianLevel) {
/* 258:386 */               ClusteringProgressDialog.this.lvlViewerCB.addItem("Level " + i + " <-- Median Level");
/* 259:    */             } else {
/* 260:388 */               ClusteringProgressDialog.this.lvlViewerCB.addItem("Level " + i);
/* 261:    */             }
/* 262:    */           }
/* 263:390 */           ClusteringProgressDialog.this.lvlViewerCB.setEnabled(true);
/* 264:    */           
/* 265:392 */           int outTechnique = ClusteringProgressDialog.this.graphOutput_x.getOutputTechnique();
/* 266:    */           
/* 267:394 */           int median = ClusteringProgressDialog.this.clusteringMethod_x.getBestCluster().getGraph().getMedianTree().getGraphLevel();
/* 268:399 */           switch (outTechnique)
/* 269:    */           {
/* 270:    */           case 2: 
/* 271:    */           case 3: 
/* 272:403 */             ClusteringProgressDialog.this.lvlViewerCB.setSelectedIndex(median); break;
/* 273:    */           case 4: 
/* 274:406 */             ClusteringProgressDialog.this.lvlViewerCB.setSelectedIndex(0); break;
/* 275:    */           case 1: 
/* 276:410 */             ClusteringProgressDialog.this.lvlViewerCB.setSelectedIndex(ClusteringProgressDialog.this.clusteringMethod_x.getBestCluster().getGraph().getGraphLevel());
/* 277:    */           }
/* 278:    */         }
/* 279:420 */         ClusteringProgressDialog.this.setFinished(true);
/* 280:421 */         ClusteringProgressDialog.this.stats.dumpStatsLog();
/* 281:    */       }
/* 282:428 */     };
/* 283:429 */     this.worker_d.setPriority(1);
/* 284:430 */     this.worker_d.start();
/* 285:    */   }
/* 286:    */   
/* 287:    */   public ClusteringProgressDialog()
/* 288:    */   {
/* 289:439 */     this(null, "", false);
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void outputGraph(int mode)
/* 293:    */   {
/* 294:456 */     boolean state = this.frame_d.consolidateDriftersCB.isSelected();
/* 295:457 */     boolean driftersFound = false;
/* 296:459 */     if (state == true) {
/* 297:461 */       if (driftersFound) {
/* 298:461 */         System.out.println("Drifters were found!!!!");
/* 299:    */       }
/* 300:    */     }
/* 301:467 */     if (mode == 1) {
/* 302:468 */       this.graphOutput_x.setCurrentName(this.graphOutput_x.getBaseName());
/* 303:470 */     } else if (mode == 2) {
/* 304:471 */       this.graphOutput_x.setCurrentName(this.graphOutput_x.getBaseName() + "-" + this.overallIteration_d);
/* 305:    */     } else {
/* 306:473 */       this.graphOutput_x.setCurrentName(this.graphOutput_x.getBaseName() + "-" + "TMP");
/* 307:    */     }
/* 308:478 */     this.graphOutput_x.setGraph(this.clusteringMethod_x.getBestGraph());
/* 309:479 */     this.frame_d.setLastResultGraph(this.clusteringMethod_x.getBestGraph().cloneGraph());
/* 310:480 */     this.graphOutput_x.write();
/* 311:    */   }
/* 312:    */   
/* 313:    */   public boolean consolidateDrifters()
/* 314:    */   {
/* 315:492 */     Drifters d = new Drifters(this.clusteringMethod_x.getBestGraph());
/* 316:493 */     return d.consolidate();
/* 317:    */   }
/* 318:    */   
/* 319:    */   void jbInit()
/* 320:    */     throws Exception
/* 321:    */   {
/* 322:504 */     this.panel1.setLayout(this.gridBagLayout1);
/* 323:505 */     this.timeTitleLabel_d.setText("Elapsed Time:");
/* 324:506 */     this.currentTimeLabel_d.setText("0.0  seconds                      ");
/* 325:507 */     this.jLabel2.setText("Total Evaluations:");
/* 326:508 */     this.BestClustPanel.setBorder(BorderFactory.createEtchedBorder());
/* 327:    */     
/* 328:510 */     this.BestClustPanel.setLayout(this.gridBagLayout2);
/* 329:511 */     this.jLabel4.setText("Depth (h):");
/* 330:512 */     this.jLabel5.setText("Evaluation Value:");
/* 331:513 */     this.DepthCount.setText("0");
/* 332:514 */     this.bestMQValueFound_d.setText("0.0");
/* 333:515 */     this.MQEvalCount.setText("0");
/* 334:516 */     Border b = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Best Cluster Statistics");
/* 335:    */     
/* 336:518 */     this.BestClustPanel.setBorder(b);
/* 337:520 */     if (!this.showOverallProgressBar_d) {
/* 338:524 */       this.panel1.add(this.overallPercentLabel_d, new GridBagConstraints2(0, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 5, 5), 0, 0));
/* 339:    */     }
/* 340:527 */     this.outputButton_d.setEnabled(false);
/* 341:528 */     this.outputButton_d.setText("Output");
/* 342:529 */     this.outputButton_d.addActionListener(new ActionListener()
/* 343:    */     {
/* 344:    */       public void actionPerformed(ActionEvent e)
/* 345:    */       {
/* 346:531 */         ClusteringProgressDialog.this.outputButton_d_actionPerformed(e);
/* 347:    */       }
/* 348:534 */     });
/* 349:535 */     this.viewPB.setEnabled(false);
/* 350:536 */     this.viewPB.setText("View Graph");
/* 351:537 */     this.viewPB.addActionListener(new ActionListener()
/* 352:    */     {
/* 353:    */       public void actionPerformed(ActionEvent e)
/* 354:    */       {
/* 355:539 */         ClusteringProgressDialog.this.viewPB_actionPerformed(e);
/* 356:    */       }
/* 357:542 */     });
/* 358:543 */     this.pauseButton_d.setText("Pause");
/* 359:544 */     this.pauseButton_d.addActionListener(new ActionListener()
/* 360:    */     {
/* 361:    */       public void actionPerformed(ActionEvent e)
/* 362:    */       {
/* 363:546 */         ClusteringProgressDialog.this.pauseButton_d_actionPerformed(e);
/* 364:    */       }
/* 365:549 */     });
/* 366:550 */     this.cancelButton_d.setText("Cancel");
/* 367:551 */     this.cancelButton_d.addActionListener(new ActionListener()
/* 368:    */     {
/* 369:    */       public void actionPerformed(ActionEvent e)
/* 370:    */       {
/* 371:553 */         ClusteringProgressDialog.this.cancelButton_d_actionPerformed(e);
/* 372:    */       }
/* 373:556 */     });
/* 374:557 */     this.CurrentActivity.setForeground(Color.blue);
/* 375:558 */     this.CurrentActivity.setText("Initializing...");
/* 376:559 */     this.jLabel3.setText("Activity:");
/* 377:560 */     this.jLabel6.setText("Number of Clusters:");
/* 378:561 */     this.numClusters.setText("0");
/* 379:562 */     this.progressLbl.setText("Progress:");
/* 380:563 */     this.progressMsg.setText("0/0 - 0% Finished");
/* 381:564 */     this.gotoLBL.setText("Go To Level:");
/* 382:565 */     this.lvlViewerCB.addActionListener(new ActionListener()
/* 383:    */     {
/* 384:    */       public void actionPerformed(ActionEvent e)
/* 385:    */       {
/* 386:567 */         ClusteringProgressDialog.this.lvlViewerCB_actionPerformed(e);
/* 387:    */       }
/* 388:570 */     });
/* 389:571 */     getContentPane().add(this.panel1, "Center");
/* 390:572 */     this.panel1.add(this.timeTitleLabel_d, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 16, 1, new Insets(3, 5, 0, 0), 10, 0));
/* 391:    */     
/* 392:574 */     this.panel1.add(this.currentTimeLabel_d, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 0, 0, 0), 0, 0));
/* 393:    */     
/* 394:576 */     this.panel1.add(this.jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 395:    */     
/* 396:578 */     this.panel1.add(this.IterationsProcessed_st, new GridBagConstraints(1, 0, 2, 1, 0.0D, 0.0D, 15, 0, new Insets(0, 0, 0, 0), 50, 0));
/* 397:    */     
/* 398:580 */     this.panel1.add(this.jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 16, 1, new Insets(8, 5, 0, 0), 10, 0));
/* 399:    */     
/* 400:582 */     this.panel1.add(this.MQEvalCount, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 16, 1, new Insets(8, 0, 0, 0), 0, 0));
/* 401:    */     
/* 402:584 */     this.panel1.add(this.BestClustPanel, new GridBagConstraints(0, 0, 3, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 403:    */     
/* 404:586 */     this.BestClustPanel.add(this.jLabel4, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 5, 0, 0), 69, 0));
/* 405:    */     
/* 406:588 */     this.BestClustPanel.add(this.jLabel5, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(0, 5, 0, 0), 5, 0));
/* 407:    */     
/* 408:590 */     this.BestClustPanel.add(this.DepthCount, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(0, 0, 0, 0), 203, 0));
/* 409:    */     
/* 410:592 */     this.BestClustPanel.add(this.bestMQValueFound_d, new GridBagConstraints(2, 1, 1, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 0, 0, 0), 0, 0));
/* 411:    */     
/* 412:594 */     this.BestClustPanel.add(this.jLabel6, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 5, 0, 0), 0, 0));
/* 413:    */     
/* 414:596 */     this.BestClustPanel.add(this.numClusters, new GridBagConstraints(2, 2, 1, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 0, 0, 0), 0, 0));
/* 415:    */     
/* 416:598 */     this.BestClustPanel.add(this.gotoLBL, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 5, 0, 0), 0, 0));
/* 417:    */     
/* 418:600 */     this.BestClustPanel.add(this.lvlViewerCB, new GridBagConstraints(2, 3, 1, 1, 0.0D, 0.0D, 17, 2, new Insets(0, 0, 0, 0), 0, -3));
/* 419:    */     
/* 420:602 */     this.panel1.add(this.jPanel1, new GridBagConstraints(0, 5, 3, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 0, 0));
/* 421:    */     
/* 422:604 */     this.jPanel1.add(this.outputButton_d, null);
/* 423:605 */     this.jPanel1.add(this.viewPB, null);
/* 424:606 */     this.jPanel1.add(this.pauseButton_d, null);
/* 425:607 */     this.jPanel1.add(this.cancelButton_d, null);
/* 426:608 */     this.panel1.add(this.CurrentActivity, new GridBagConstraints(1, 4, 2, 1, 0.0D, 0.0D, 16, 1, new Insets(3, 0, 0, 0), 0, 0));
/* 427:    */     
/* 428:610 */     this.panel1.add(this.jLabel3, new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 16, 1, new Insets(3, 5, 0, 0), 10, 0));
/* 429:    */     
/* 430:612 */     this.panel1.add(this.progressLbl, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(3, 5, 0, 0), 0, 0));
/* 431:    */     
/* 432:614 */     this.panel1.add(this.progressMsg, new GridBagConstraints(1, 3, 2, 1, 0.0D, 0.0D, 17, 2, new Insets(0, 0, 0, 0), 0, 0));
/* 433:    */     
/* 434:    */ 
/* 435:617 */     this.basicTitle = getTitle();
/* 436:619 */     if (!(this.clusteringMethod_x instanceof OptimalClusteringMethod))
/* 437:    */     {
/* 438:621 */       this.progressLbl.setVisible(false);
/* 439:622 */       this.progressMsg.setVisible(false);
/* 440:623 */       this.isExhaustive = false;
/* 441:    */     }
/* 442:    */     else
/* 443:    */     {
/* 444:626 */       this.isExhaustive = true;
/* 445:    */     }
/* 446:628 */     if (!this.frame_d.isAgglomerativeTechnique())
/* 447:    */     {
/* 448:630 */       this.lvlViewerCB.setVisible(false);
/* 449:631 */       this.gotoLBL.setVisible(false);
/* 450:    */     }
/* 451:    */     else
/* 452:    */     {
/* 453:634 */       this.lvlViewerCB.setEnabled(false);
/* 454:    */     }
/* 455:    */   }
/* 456:    */   
/* 457:    */   void outputButton_d_actionPerformed(ActionEvent e)
/* 458:    */   {
/* 459:645 */     outputGraph(2);
/* 460:    */   }
/* 461:    */   
/* 462:    */   void pauseButton_d_actionPerformed(ActionEvent e)
/* 463:    */   {
/* 464:658 */     if (!this.isPaused)
/* 465:    */     {
/* 466:663 */       this.isPaused = true;
/* 467:668 */       if (this.worker_d != null) {
/* 468:669 */         this.worker_d.suspend();
/* 469:    */       }
/* 470:670 */       this.eventTimer.stop();
/* 471:671 */       updateStats();
/* 472:672 */       this.CurrentActivity.setText("Paused");
/* 473:673 */       this.pauseButton_d.setText("Resume");
/* 474:674 */       this.outputButton_d.setEnabled(true);
/* 475:    */       
/* 476:    */ 
/* 477:    */ 
/* 478:    */ 
/* 479:679 */       this.currentViewC = new Cluster(this.clusteringMethod_x.getBestGraph().cloneGraph(), this.clusteringMethod_x.getBestGraph().getClusters());
/* 480:    */       
/* 481:681 */       this.currentViewC.force();
/* 482:682 */       this.graphOutput_x.setGraph(this.clusteringMethod_x.getBestGraph().cloneGraph());
/* 483:687 */       if (this.frame_d.getOutputMethod().equals("Dotty")) {
/* 484:688 */         this.viewPB.setEnabled(true);
/* 485:    */       }
/* 486:    */     }
/* 487:    */     else
/* 488:    */     {
/* 489:695 */       this.outputButton_d.setEnabled(false);
/* 490:696 */       this.viewPB.setEnabled(false);
/* 491:697 */       this.pauseButton_d.setText("  Pause  ");
/* 492:698 */       this.eventTimer.start();
/* 493:699 */       this.CurrentActivity.setText("Clustering...");
/* 494:704 */       if (this.worker_d != null) {
/* 495:705 */         this.worker_d.resume();
/* 496:    */       }
/* 497:706 */       this.isPaused = false;
/* 498:    */     }
/* 499:    */   }
/* 500:    */   
/* 501:    */   public void setFinished(boolean v)
/* 502:    */   {
/* 503:721 */     this.finished_d = v;
/* 504:722 */     if (v == true) {
/* 505:723 */       this.eventTimer.stop();
/* 506:    */     } else {
/* 507:725 */       this.eventTimer.start();
/* 508:    */     }
/* 509:    */   }
/* 510:    */   
/* 511:    */   public boolean isFinished()
/* 512:    */   {
/* 513:739 */     return this.finished_d;
/* 514:    */   }
/* 515:    */   
/* 516:    */   void cancelButton_d_actionPerformed(ActionEvent e)
/* 517:    */   {
/* 518:750 */     if (this.cancelPending == true) {
/* 519:751 */       return;
/* 520:    */     }
/* 521:752 */     this.cancelPending = true;
/* 522:    */     
/* 523:754 */     StatsManager.cleanup();
/* 524:    */     
/* 525:756 */     Configuration cTmp = this.clusteringMethod_x.getConfiguration();
/* 526:757 */     if ((cTmp instanceof NAHCConfiguration))
/* 527:    */     {
/* 528:759 */       NAHCConfiguration nahcConf = (NAHCConfiguration)cTmp;
/* 529:760 */       if (nahcConf.getSATechnique() != null) {
/* 530:761 */         nahcConf.getSATechnique().reset();
/* 531:    */       }
/* 532:    */     }
/* 533:764 */     if (isFinished())
/* 534:    */     {
/* 535:765 */       setVisible(false);
/* 536:766 */       dispose();
/* 537:767 */       return;
/* 538:    */     }
/* 539:774 */     if (this.worker_d != null) {
/* 540:775 */       this.worker_d.suspend();
/* 541:    */     }
/* 542:777 */     int result = JOptionPane.showConfirmDialog(this.frame_d, "This will cancel the clustering process.\n Are you sure?", "Cancel Clustering?", 0);
/* 543:781 */     if (result == 1)
/* 544:    */     {
/* 545:786 */       if (this.worker_d != null) {
/* 546:787 */         this.worker_d.resume();
/* 547:    */       }
/* 548:788 */       this.cancelPending = false;
/* 549:789 */       return;
/* 550:    */     }
/* 551:796 */     if (this.worker_d != null) {
/* 552:797 */       this.worker_d.interrupt();
/* 553:    */     }
/* 554:798 */     setVisible(false);
/* 555:799 */     setFinished(true);
/* 556:800 */     dispose();
/* 557:    */   }
/* 558:    */   
/* 559:    */   public void newIteration(IterationEvent e) {}
/* 560:    */   
/* 561:    */   public void newExperiment(IterationEvent e)
/* 562:    */   {
/* 563:829 */     Integer i = new Integer(e.getExpNum());
/* 564:830 */     this.IterationsProcessed_st.setText(i.toString());
/* 565:    */   }
/* 566:    */   
/* 567:    */   void viewPB_actionPerformed(ActionEvent e)
/* 568:    */   {
/* 569:    */     try
/* 570:    */     {
/* 571:844 */       int desiredLvl = this.currentViewC.getGraph().getGraphLevel();
/* 572:845 */       Graph tmpG = this.clusteringMethod_x.getBestGraph().cloneGraph();
/* 573:846 */       while ((tmpG != null) && (tmpG.getGraphLevel() > desiredLvl)) {
/* 574:847 */         tmpG = tmpG.getPreviousLevelGraph();
/* 575:    */       }
/* 576:852 */       ((DotGraphOutput)this.graphOutput_x).writeGraph("bunchtmp.dot", tmpG);
/* 577:    */       
/* 578:    */ 
/* 579:    */ 
/* 580:    */ 
/* 581:857 */       Runtime r = Runtime.getRuntime();
/* 582:858 */       r.exec("dotty bunchtmp.dot");
/* 583:    */     }
/* 584:    */     catch (Exception ex)
/* 585:    */     {
/* 586:861 */       JOptionPane.showMessageDialog(this, "Error (check if dotty is in your path): " + ex.toString(), "Error Execing Graph Viewer", 0);
/* 587:    */       
/* 588:    */ 
/* 589:    */ 
/* 590:865 */       ex.printStackTrace();
/* 591:    */     }
/* 592:    */   }
/* 593:    */   
/* 594:    */   public void updateStats()
/* 595:    */   {
/* 596:875 */     if (this.clusteringMethod_x.getBestCluster() == null) {
/* 597:875 */       return;
/* 598:    */     }
/* 599:877 */     double elapsedTime = (System.currentTimeMillis() - this.startTime) / 1000.0D;
/* 600:878 */     double mq = this.clusteringMethod_x.getBestCluster().getObjFnValue();
/* 601:879 */     long depth = this.clusteringMethod_x.getBestCluster().getDepth();
/* 602:880 */     long totalMQCalcs = this.stats.getMQCalculations();
/* 603:882 */     if (++this.updateCounter % 10L == 0L) {
/* 604:883 */       this.clusteringMethod_x.getBestCluster().getClusterNames();
/* 605:    */     }
/* 606:885 */     int nc = this.clusteringMethod_x.getBestCluster().getNumClusters();
/* 607:    */     
/* 608:887 */     this.currentTimeLabel_d.setText(Double.toString(elapsedTime) + " seconds");
/* 609:888 */     this.bestMQValueFound_d.setText(Double.toString(mq));
/* 610:889 */     this.DepthCount.setText(Long.toString(depth));
/* 611:890 */     this.numClusters.setText(Integer.toString(nc));
/* 612:891 */     this.MQEvalCount.setText(Long.toString(totalMQCalcs));
/* 613:893 */     if (this.isExhaustive == true)
/* 614:    */     {
/* 615:895 */       long done = this.stats.getExhaustiveFinished();
/* 616:896 */       long total = this.stats.getExhaustiveTotal();
/* 617:897 */       int pct = this.stats.getExhaustivePct();
/* 618:898 */       String msg = done + "/" + total + " - " + pct + "% Finished";
/* 619:899 */       this.progressMsg.setText(msg);
/* 620:    */     }
/* 621:    */   }
/* 622:    */   
/* 623:    */   class updateTimer
/* 624:    */     implements ActionListener
/* 625:    */   {
/* 626:    */     updateTimer() {}
/* 627:    */     
/* 628:    */     public void actionPerformed(ActionEvent e)
/* 629:    */     {
/* 630:911 */       ClusteringProgressDialog.this.updateStats();
/* 631:    */     }
/* 632:    */   }
/* 633:    */   
/* 634:    */   class timeoutTimer
/* 635:    */     implements ActionListener
/* 636:    */   {
/* 637:    */     timeoutTimer() {}
/* 638:    */     
/* 639:    */     public void actionPerformed(ActionEvent e)
/* 640:    */     {
/* 641:924 */       if (ClusteringProgressDialog.this.worker_d != null)
/* 642:    */       {
/* 643:926 */         ClusteringProgressDialog.this.worker_d.interrupt();
/* 644:927 */         ClusteringProgressDialog.this.worker_d.finished();
/* 645:928 */         ClusteringProgressDialog.this.CurrentActivity.setText("Finished due to Timeout!");
/* 646:    */       }
/* 647:    */     }
/* 648:    */   }
/* 649:    */   
/* 650:    */   void lvlViewerCB_actionPerformed(ActionEvent e)
/* 651:    */   {
/* 652:941 */     int lvl = this.lvlViewerCB.getSelectedIndex();
/* 653:    */     
/* 654:943 */     Object[] clustO = this.bestCLL.toArray();
/* 655:944 */     Cluster lvlC = null;
/* 656:946 */     for (int i = 0; i < clustO.length; i++)
/* 657:    */     {
/* 658:948 */       Cluster tmpC = (Cluster)clustO[i];
/* 659:949 */       if (tmpC.getGraph().getGraphLevel() == lvl)
/* 660:    */       {
/* 661:951 */         lvlC = tmpC;
/* 662:952 */         break;
/* 663:    */       }
/* 664:    */     }
/* 665:956 */     if (lvlC == null) {
/* 666:956 */       return;
/* 667:    */     }
/* 668:958 */     this.currentViewC = lvlC;
/* 669:959 */     this.bestMQValueFound_d.setText(Double.toString(lvlC.getObjFnValue()));
/* 670:960 */     this.DepthCount.setText(Long.toString(lvlC.getDepth()));
/* 671:961 */     this.numClusters.setText(Integer.toString(lvlC.getClusterNames().length));
/* 672:962 */     setTitle(this.basicTitle + " (Level " + lvl + ")");
/* 673:    */   }
/* 674:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.ClusteringProgressDialog
 * JD-Core Version:    0.7.0.1
 */