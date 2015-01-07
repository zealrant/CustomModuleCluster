/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.awt.Frame;
/*   6:    */ import java.awt.GridBagLayout;
/*   7:    */ import java.awt.Insets;
/*   8:    */ import java.awt.event.ActionEvent;
/*   9:    */ import java.awt.event.ActionListener;
/*  10:    */ import javax.swing.JButton;
/*  11:    */ import javax.swing.JCheckBox;
/*  12:    */ import javax.swing.JDialog;
/*  13:    */ import javax.swing.JLabel;
/*  14:    */ import javax.swing.JOptionPane;
/*  15:    */ import javax.swing.JPanel;
/*  16:    */ import javax.swing.JProgressBar;
/*  17:    */ import javax.swing.SwingUtilities;
/*  18:    */ 
/*  19:    */ public class DistribClusteringProgressDlg
/*  20:    */   extends JDialog
/*  21:    */   implements IterationListener
/*  22:    */ {
/*  23: 48 */   JPanel panel1 = new JPanel();
/*  24: 49 */   GridBagLayout gridBagLayout1 = new GridBagLayout();
/*  25: 50 */   JLabel percentLabel_d = new JLabel();
/*  26: 51 */   JLabel overallPercentLabel_d = new JLabel();
/*  27: 52 */   JProgressBar progressBar_d = new JProgressBar();
/*  28: 53 */   JProgressBar overallProgressBar_d = new JProgressBar();
/*  29: 54 */   JLabel bestMQLabel_d = new JLabel();
/*  30: 55 */   JLabel bestMQValueFound_d = new JLabel();
/*  31: 56 */   JLabel timeTitleLabel_d = new JLabel();
/*  32: 57 */   JLabel currentTimeLabel_d = new JLabel();
/*  33: 58 */   JButton pauseButton_d = new JButton();
/*  34: 59 */   JButton outputButton_d = new JButton();
/*  35: 60 */   JButton cancelButton_d = new JButton();
/*  36: 61 */   BunchFrame frame_d = null;
/*  37: 62 */   GraphOutput graphOutput_x = null;
/*  38: 63 */   ClusteringMethod2 clusteringMethod_x = null;
/*  39:    */   SwingWorker worker_d;
/*  40: 65 */   int iteration_d = 0;
/*  41:    */   int overallIteration_d;
/*  42: 66 */   boolean finished_d = false;
/*  43: 67 */   boolean showOverallProgressBar_d = true;
/*  44: 68 */   JLabel jLabel1 = new JLabel();
/*  45: 69 */   JLabel IterationsProcessed_st = new JLabel();
/*  46:    */   
/*  47:    */   public DistribClusteringProgressDlg(Frame frame, String title, boolean modal, ClusteringMethod2 cm2)
/*  48:    */   {
/*  49: 77 */     super(frame, title, modal);
/*  50: 78 */     this.frame_d = ((BunchFrame)frame);
/*  51:    */     
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55: 83 */     this.graphOutput_x = this.frame_d.getGraphOutput();
/*  56: 84 */     this.clusteringMethod_x = cm2;
/*  57: 85 */     this.clusteringMethod_x.setIterationListener(this);
/*  58:    */     
/*  59: 87 */     String methodName = this.clusteringMethod_x.getClass().getName();
/*  60: 89 */     if (methodName.equals("bunch.GAClusteringMethod")) {
/*  61: 90 */       this.showOverallProgressBar_d = false;
/*  62:    */     }
/*  63:    */     try
/*  64:    */     {
/*  65: 96 */       jbInit();
/*  66: 97 */       pack();
/*  67:    */     }
/*  68:    */     catch (Exception ex)
/*  69:    */     {
/*  70:100 */       ex.printStackTrace();
/*  71:    */     }
/*  72:103 */     this.progressBar_d.setMaximum(this.clusteringMethod_x.getMaxIterations());
/*  73:104 */     this.progressBar_d.setMinimum(0);
/*  74:    */     
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:109 */     ((GenericDistribHillClimbingClusteringMethod)this.clusteringMethod_x).makeEventQueue();
/*  79:110 */     SynchronizedEventQueue eventQ = ((GenericDistribHillClimbingClusteringMethod)this.clusteringMethod_x).getEventQueue();
/*  80:    */     
/*  81:    */ 
/*  82:113 */     CallbackImpl svrCB = this.frame_d.getSvrCallback();
/*  83:114 */     svrCB.eventQ = eventQ;
/*  84:    */     
/*  85:116 */     BunchEvent be = new BunchEvent();
/*  86:117 */     svrCB.bevent = be;
/*  87:118 */     ((GenericDistribHillClimbingClusteringMethod)this.clusteringMethod_x).setEventObject(be);
/*  88:    */     
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:123 */     int uowSz = this.frame_d.getUOWSz();
/*  93:124 */     boolean useAdaptive = this.frame_d.getAdaptiveEnableFlag();
/*  94:    */     
/*  95:126 */     ((GenericDistribHillClimbingClusteringMethod)this.clusteringMethod_x).setUOWSize(uowSz);
/*  96:127 */     ((GenericDistribHillClimbingClusteringMethod)this.clusteringMethod_x).setAdaptiveEnable(useAdaptive);
/*  97:    */     
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:138 */     this.worker_d = new SwingWorker()
/* 108:    */     {
/* 109:    */       public Object construct()
/* 110:    */       {
/* 111:145 */         DistribClusteringProgressDlg.this.clusteringMethod_x.run();
/* 112:146 */         return "Done";
/* 113:    */       }
/* 114:    */       
/* 115:    */       public void finished()
/* 116:    */       {
/* 117:154 */         Runnable doSetProgressBarValue = new Runnable()
/* 118:    */         {
/* 119:    */           public void run()
/* 120:    */           {
/* 121:158 */             DistribClusteringProgressDlg.this.progressBar_d.setValue(DistribClusteringProgressDlg.this.progressBar_d.getMaximum());
/* 122:159 */             if (DistribClusteringProgressDlg.this.showOverallProgressBar_d) {
/* 123:160 */               DistribClusteringProgressDlg.this.overallProgressBar_d.setValue(DistribClusteringProgressDlg.this.overallProgressBar_d.getMaximum());
/* 124:    */             }
/* 125:161 */             DistribClusteringProgressDlg.this.bestMQLabel_d.setForeground(Color.red.darker());
/* 126:162 */             DistribClusteringProgressDlg.this.bestMQValueFound_d.setForeground(Color.red.darker());
/* 127:163 */             DistribClusteringProgressDlg.this.outputButton_d.setEnabled(false);
/* 128:164 */             DistribClusteringProgressDlg.this.pauseButton_d.setEnabled(false);
/* 129:165 */             DistribClusteringProgressDlg.this.cancelButton_d.setText("Close");
/* 130:166 */             DistribClusteringProgressDlg.this.bestMQLabel_d.setText(DistribClusteringProgressDlg.this.bestMQLabel_d.getText());
/* 131:167 */             DistribClusteringProgressDlg.this.bestMQValueFound_d.setText(Double.toString(DistribClusteringProgressDlg.this.clusteringMethod_x.getBestObjectiveFunctionValue()));
/* 132:168 */             DistribClusteringProgressDlg.this.currentTimeLabel_d.setText(Double.toString(DistribClusteringProgressDlg.this.clusteringMethod_x.getElapsedTime()) + "  seconds");
/* 133:    */           }
/* 134:170 */         };
/* 135:171 */         SwingUtilities.invokeLater(doSetProgressBarValue);
/* 136:172 */         DistribClusteringProgressDlg.this.outputGraph(true);
/* 137:173 */         DistribClusteringProgressDlg.this.setFinished(true);
/* 138:    */       }
/* 139:179 */     };
/* 140:180 */     this.worker_d.setPriority(1);
/* 141:181 */     this.worker_d.start();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void outputGraph(boolean end)
/* 145:    */   {
/* 146:198 */     boolean state = this.frame_d.consolidateDriftersCB.isSelected();
/* 147:199 */     if (state == true) {
/* 148:200 */       consolidateDrifters();
/* 149:    */     }
/* 150:202 */     if (end) {
/* 151:203 */       this.graphOutput_x.setCurrentName(this.graphOutput_x.getBaseName());
/* 152:    */     } else {
/* 153:206 */       this.graphOutput_x.setCurrentName(this.graphOutput_x.getBaseName() + "-" + this.overallIteration_d);
/* 154:    */     }
/* 155:209 */     Cluster c = this.clusteringMethod_x.getBestCluster();
/* 156:210 */     Graph g = c.getGraph();
/* 157:211 */     this.graphOutput_x.setGraph(g);
/* 158:212 */     this.frame_d.setLastResultGraph(g.cloneGraph());
/* 159:213 */     this.graphOutput_x.write();
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void consolidateDrifters()
/* 163:    */   {
/* 164:220 */     Drifters d = new Drifters(this.clusteringMethod_x.getBestGraph());
/* 165:221 */     d.consolidate();
/* 166:    */   }
/* 167:    */   
/* 168:    */   void jbInit()
/* 169:    */     throws Exception
/* 170:    */   {
/* 171:232 */     this.panel1.setLayout(this.gridBagLayout1);
/* 172:    */     
/* 173:234 */     this.bestMQLabel_d.setText("Best MQ value found:");
/* 174:235 */     this.timeTitleLabel_d.setText("Elapsed Time:");
/* 175:236 */     this.bestMQValueFound_d.setText("                                             ");
/* 176:237 */     this.currentTimeLabel_d.setText("0.0  seconds                      ");
/* 177:238 */     this.pauseButton_d.setText("  Pause  ");
/* 178:    */     
/* 179:240 */     this.pauseButton_d.addActionListener(new ActionListener()
/* 180:    */     {
/* 181:    */       public void actionPerformed(ActionEvent e)
/* 182:    */       {
/* 183:242 */         DistribClusteringProgressDlg.this.pauseButton_d_actionPerformed(e);
/* 184:    */       }
/* 185:245 */     });
/* 186:246 */     this.cancelButton_d.setText("Cancel");
/* 187:247 */     this.cancelButton_d.addActionListener(new ActionListener()
/* 188:    */     {
/* 189:    */       public void actionPerformed(ActionEvent e)
/* 190:    */       {
/* 191:249 */         DistribClusteringProgressDlg.this.cancelButton_d_actionPerformed(e);
/* 192:    */       }
/* 193:252 */     });
/* 194:253 */     this.outputButton_d.setText("Output");
/* 195:254 */     this.outputButton_d.addActionListener(new ActionListener()
/* 196:    */     {
/* 197:    */       public void actionPerformed(ActionEvent e)
/* 198:    */       {
/* 199:256 */         DistribClusteringProgressDlg.this.outputButton_d_actionPerformed(e);
/* 200:    */       }
/* 201:259 */     });
/* 202:260 */     getContentPane().add(this.panel1);
/* 203:261 */     this.outputButton_d.setEnabled(false);
/* 204:262 */     if (!this.showOverallProgressBar_d) {
/* 205:267 */       this.panel1.add(this.overallPercentLabel_d, new GridBagConstraints2(0, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 5, 5), 0, 0));
/* 206:    */     }
/* 207:270 */     this.panel1.add(this.progressBar_d, new GridBagConstraints2(0, 2, 3, 1, 0.0D, 0.0D, 10, 2, new Insets(2, 2, 2, 2), 165, 0));
/* 208:    */     
/* 209:    */ 
/* 210:273 */     this.panel1.add(this.timeTitleLabel_d, new GridBagConstraints2(0, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 2, 5), 0, 0));
/* 211:    */     
/* 212:275 */     this.panel1.add(this.currentTimeLabel_d, new GridBagConstraints2(1, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 2, 5), 0, 0));
/* 213:    */     
/* 214:    */ 
/* 215:278 */     this.panel1.add(this.bestMQLabel_d, new GridBagConstraints2(0, 4, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(2, 5, 5, 5), 0, 0));
/* 216:    */     
/* 217:280 */     this.panel1.add(this.bestMQValueFound_d, new GridBagConstraints2(1, 4, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(2, 5, 5, 5), 0, 0));
/* 218:    */     
/* 219:    */ 
/* 220:283 */     this.panel1.add(this.outputButton_d, new GridBagConstraints2(0, 5, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 5, 5, 5), 0, 0));
/* 221:    */     
/* 222:285 */     this.panel1.add(this.pauseButton_d, new GridBagConstraints2(1, 5, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 5, 5, 5), 0, 0));
/* 223:    */     
/* 224:287 */     this.panel1.add(this.cancelButton_d, new GridBagConstraints2(2, 5, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 5, 5, 5), 0, 0));
/* 225:    */     
/* 226:289 */     this.panel1.add(this.jLabel1, new GridBagConstraints2(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 227:    */     
/* 228:291 */     this.panel1.add(this.IterationsProcessed_st, new GridBagConstraints2(1, 0, 1, 1, 0.0D, 0.0D, 15, 0, new Insets(0, 0, 0, 0), 50, 0));
/* 229:    */   }
/* 230:    */   
/* 231:    */   void outputButton_d_actionPerformed(ActionEvent e)
/* 232:    */   {
/* 233:303 */     outputGraph(false);
/* 234:    */   }
/* 235:    */   
/* 236:    */   void pauseButton_d_actionPerformed(ActionEvent e)
/* 237:    */   {
/* 238:316 */     if (this.pauseButton_d.getText().equals("  Pause  "))
/* 239:    */     {
/* 240:317 */       this.worker_d.suspend();
/* 241:318 */       this.pauseButton_d.setText("Resume");
/* 242:319 */       this.outputButton_d.setEnabled(true);
/* 243:    */     }
/* 244:    */     else
/* 245:    */     {
/* 246:322 */       this.outputButton_d.setEnabled(false);
/* 247:323 */       this.pauseButton_d.setText("  Pause  ");
/* 248:324 */       this.worker_d.resume();
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setFinished(boolean v)
/* 253:    */   {
/* 254:339 */     this.finished_d = v;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public boolean isFinished()
/* 258:    */   {
/* 259:353 */     return this.finished_d;
/* 260:    */   }
/* 261:    */   
/* 262:    */   void cancelButton_d_actionPerformed(ActionEvent e)
/* 263:    */   {
/* 264:364 */     if (isFinished())
/* 265:    */     {
/* 266:365 */       setVisible(false);
/* 267:366 */       dispose();
/* 268:367 */       return;
/* 269:    */     }
/* 270:370 */     this.worker_d.suspend();
/* 271:371 */     int result = JOptionPane.showConfirmDialog(this.frame_d, "This will cancel the clustering process.\n Are you sure?", "Cancel Clustering?", 0);
/* 272:374 */     if (result == 1)
/* 273:    */     {
/* 274:375 */       this.worker_d.resume();
/* 275:376 */       return;
/* 276:    */     }
/* 277:379 */     this.worker_d.finished();
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void newIteration(IterationEvent e)
/* 281:    */   {
/* 282:393 */     this.iteration_d = e.getIteration();
/* 283:    */     
/* 284:395 */     this.overallIteration_d = e.getOverallIteration();
/* 285:396 */     Runnable doSetProgressBarValue = new Runnable()
/* 286:    */     {
/* 287:    */       public void run()
/* 288:    */       {
/* 289:400 */         DistribClusteringProgressDlg.this.progressBar_d.setValue(DistribClusteringProgressDlg.this.iteration_d);
/* 290:401 */         if (DistribClusteringProgressDlg.this.showOverallProgressBar_d) {
/* 291:402 */           DistribClusteringProgressDlg.this.overallProgressBar_d.setValue(DistribClusteringProgressDlg.this.overallIteration_d);
/* 292:    */         }
/* 293:404 */         DistribClusteringProgressDlg.this.bestMQValueFound_d.setText(Double.toString(DistribClusteringProgressDlg.this.clusteringMethod_x.getBestObjectiveFunctionValue()));
/* 294:405 */         DistribClusteringProgressDlg.this.currentTimeLabel_d.setText(Double.toString(DistribClusteringProgressDlg.this.clusteringMethod_x.getElapsedTime()) + "  seconds");
/* 295:    */       }
/* 296:407 */     };
/* 297:408 */     SwingUtilities.invokeLater(doSetProgressBarValue);
/* 298:410 */     if (this.iteration_d >= this.clusteringMethod_x.getMaxIterations()) {
/* 299:411 */       this.worker_d.finished();
/* 300:    */     }
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void newExperiment(IterationEvent e)
/* 304:    */   {
/* 305:418 */     Integer i = new Integer(e.getExpNum());
/* 306:419 */     this.IterationsProcessed_st.setText(i.toString());
/* 307:    */   }
/* 308:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.DistribClusteringProgressDlg
 * JD-Core Version:    0.7.0.1
 */