/*   1:    */ package bunch.util;
/*   2:    */ 
/*   3:    */ import bunch.BunchFrame;
/*   4:    */ import bunch.ClusterFileParser;
/*   5:    */ import bunch.DependencyFileParser;
/*   6:    */ import bunch.Graph;
/*   7:    */ import bunch.Node;
/*   8:    */ import bunch.ObjectiveFunctionCalculatorFactory;
/*   9:    */ import bunch.Parser;
/*  10:    */ import bunch.api.BunchGraph;
/*  11:    */ import bunch.api.BunchGraphUtils;
/*  12:    */ import java.awt.BorderLayout;
/*  13:    */ import java.awt.Color;
/*  14:    */ import java.awt.Container;
/*  15:    */ import java.awt.FileDialog;
/*  16:    */ import java.awt.Frame;
/*  17:    */ import java.awt.GridBagConstraints;
/*  18:    */ import java.awt.GridBagLayout;
/*  19:    */ import java.awt.GridLayout;
/*  20:    */ import java.awt.Insets;
/*  21:    */ import java.awt.event.ActionEvent;
/*  22:    */ import java.awt.event.ActionListener;
/*  23:    */ import java.io.File;
/*  24:    */ import java.util.ArrayList;
/*  25:    */ import java.util.Enumeration;
/*  26:    */ import javax.swing.BorderFactory;
/*  27:    */ import javax.swing.JButton;
/*  28:    */ import javax.swing.JComboBox;
/*  29:    */ import javax.swing.JDialog;
/*  30:    */ import javax.swing.JFileChooser;
/*  31:    */ import javax.swing.JLabel;
/*  32:    */ import javax.swing.JOptionPane;
/*  33:    */ import javax.swing.JPanel;
/*  34:    */ import javax.swing.JScrollPane;
/*  35:    */ import javax.swing.JTabbedPane;
/*  36:    */ import javax.swing.JTextArea;
/*  37:    */ import javax.swing.JTextField;
/*  38:    */ import javax.swing.JViewport;
/*  39:    */ import javax.swing.border.Border;
/*  40:    */ import javax.swing.border.EtchedBorder;
/*  41:    */ import javax.swing.border.TitledBorder;
/*  42:    */ 
/*  43:    */ public class MeasurementUtil
/*  44:    */   extends JDialog
/*  45:    */ {
/*  46: 41 */   JPanel panel1 = new JPanel();
/*  47:    */   Border border1;
/*  48:    */   TitledBorder titledBorder1;
/*  49:    */   Border border2;
/*  50:    */   TitledBorder titledBorder2;
/*  51:    */   FileDialog fd;
/*  52:    */   JFileChooser fileChooser;
/*  53:    */   BunchFrame bunchFrame;
/*  54: 49 */   BorderLayout borderLayout1 = new BorderLayout();
/*  55: 50 */   JTabbedPane jTabbedPane1 = new JTabbedPane();
/*  56: 51 */   JPanel MQCalcPanel = new JPanel();
/*  57: 52 */   JPanel PRCalcTab = new JPanel();
/*  58: 53 */   GridBagLayout gridBagLayout1 = new GridBagLayout();
/*  59: 54 */   JPanel jPanel1 = new JPanel();
/*  60:    */   Border border3;
/*  61:    */   TitledBorder titledBorder3;
/*  62: 57 */   JPanel jPanel2 = new JPanel();
/*  63:    */   Border border4;
/*  64:    */   TitledBorder titledBorder4;
/*  65: 60 */   JPanel jPanel3 = new JPanel();
/*  66: 61 */   JButton MQcalculatePB = new JButton();
/*  67: 62 */   JButton MQCancelPB = new JButton();
/*  68: 63 */   GridBagLayout gridBagLayout2 = new GridBagLayout();
/*  69: 64 */   JLabel jLabel1 = new JLabel();
/*  70: 65 */   JTextField mdgEF = new JTextField();
/*  71: 66 */   JButton mdgSelectPB = new JButton();
/*  72: 67 */   JLabel jLabel2 = new JLabel();
/*  73: 68 */   JTextField silEF = new JTextField();
/*  74: 69 */   JButton silSelectPB = new JButton();
/*  75: 70 */   JLabel jLabel3 = new JLabel();
/*  76: 71 */   JComboBox calculatorCB = new JComboBox();
/*  77: 72 */   GridLayout gridLayout1 = new GridLayout();
/*  78: 73 */   JLabel jLabel4 = new JLabel();
/*  79: 74 */   JLabel nodeST = new JLabel();
/*  80: 75 */   JLabel jLabel6 = new JLabel();
/*  81: 76 */   JLabel edgesST = new JLabel();
/*  82: 77 */   JLabel jLabel8 = new JLabel();
/*  83: 78 */   JLabel clusterST = new JLabel();
/*  84:    */   ObjectiveFunctionCalculatorFactory of;
/*  85: 80 */   JLabel mqLabel = new JLabel();
/*  86: 81 */   JLabel mqST = new JLabel();
/*  87: 82 */   GridBagLayout gridBagLayout3 = new GridBagLayout();
/*  88: 83 */   JPanel jPanel4 = new JPanel();
/*  89:    */   Border border5;
/*  90:    */   TitledBorder titledBorder5;
/*  91: 86 */   JPanel PRTab = new JPanel();
/*  92:    */   Border border6;
/*  93:    */   TitledBorder titledBorder6;
/*  94: 89 */   JPanel jPanel6 = new JPanel();
/*  95: 90 */   JButton prCalcPB = new JButton();
/*  96: 91 */   JButton prCancel = new JButton();
/*  97: 92 */   GridBagLayout gridBagLayout4 = new GridBagLayout();
/*  98: 93 */   JLabel jLabel5 = new JLabel();
/*  99: 94 */   JTextField expertFileEF = new JTextField();
/* 100: 95 */   JButton expertSelectPB = new JButton();
/* 101: 96 */   JLabel jLabel7 = new JLabel();
/* 102: 97 */   JTextField sampleFileEF = new JTextField();
/* 103: 98 */   JButton samplePB = new JButton();
/* 104: 99 */   GridLayout gridLayout2 = new GridLayout();
/* 105:100 */   JLabel jLabel9 = new JLabel();
/* 106:101 */   JLabel jLabel10 = new JLabel();
/* 107:102 */   JLabel precisionST = new JLabel();
/* 108:103 */   JLabel recallST = new JLabel();
/* 109:104 */   JPanel jPanel7 = new JPanel();
/* 110:105 */   GridBagLayout gridBagLayout5 = new GridBagLayout();
/* 111:106 */   JPanel ESMeclTab = new JPanel();
/* 112:    */   Border border7;
/* 113:    */   TitledBorder titledBorder7;
/* 114:109 */   GridBagLayout gridBagLayout6 = new GridBagLayout();
/* 115:110 */   JLabel jLabel11 = new JLabel();
/* 116:111 */   JTextField GraphAEF = new JTextField();
/* 117:112 */   JButton GraphASPB = new JButton();
/* 118:113 */   JLabel jLabel12 = new JLabel();
/* 119:114 */   JTextField GraphBEF = new JTextField();
/* 120:115 */   JButton GraphBSelPB = new JButton();
/* 121:116 */   JLabel jLabel13 = new JLabel();
/* 122:117 */   JTextField MDGEF = new JTextField();
/* 123:118 */   JButton MDGSelPB = new JButton();
/* 124:119 */   JLabel jLabel14 = new JLabel();
/* 125:120 */   JComboBox MeasurementDD = new JComboBox();
/* 126:121 */   JPanel jPanel5 = new JPanel();
/* 127:    */   Border border8;
/* 128:    */   TitledBorder titledBorder8;
/* 129:124 */   BorderLayout borderLayout2 = new BorderLayout();
/* 130:125 */   JPanel jPanel8 = new JPanel();
/* 131:126 */   JButton CalculatePB = new JButton();
/* 132:127 */   JButton EdgeSimCancelPB = new JButton();
/* 133:128 */   JScrollPane jScrollPane1 = new JScrollPane();
/* 134:129 */   JTextArea resultsTA = new JTextArea();
/* 135:    */   
/* 136:    */   public MeasurementUtil(Frame frame, String title, boolean modal)
/* 137:    */   {
/* 138:132 */     super(frame, title, modal);
/* 139:    */     try
/* 140:    */     {
/* 141:134 */       this.bunchFrame = ((BunchFrame)frame);
/* 142:135 */       jbInit();
/* 143:136 */       pack();
/* 144:    */     }
/* 145:    */     catch (Exception ex)
/* 146:    */     {
/* 147:139 */       ex.printStackTrace();
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public MeasurementUtil()
/* 152:    */   {
/* 153:144 */     this(null, "", false);
/* 154:    */   }
/* 155:    */   
/* 156:    */   void jbInit()
/* 157:    */     throws Exception
/* 158:    */   {
/* 159:148 */     this.border1 = new EtchedBorder(0, Color.white, new Color(142, 142, 142));
/* 160:149 */     this.titledBorder1 = new TitledBorder(this.border1, "Input Parameters");
/* 161:150 */     this.border2 = new EtchedBorder(0, Color.white, new Color(142, 142, 142));
/* 162:151 */     this.titledBorder2 = new TitledBorder(this.border2, "Results");
/* 163:152 */     this.border3 = BorderFactory.createBevelBorder(0, Color.white, Color.white, new Color(142, 142, 142), new Color(99, 99, 99));
/* 164:153 */     this.titledBorder3 = new TitledBorder(new EtchedBorder(0, Color.white, new Color(142, 142, 142)), "Inputs");
/* 165:154 */     this.border4 = new EtchedBorder(0, Color.white, new Color(142, 142, 142));
/* 166:155 */     this.titledBorder4 = new TitledBorder(this.border4, "Outputs");
/* 167:156 */     this.border5 = new EtchedBorder(0, Color.white, new Color(142, 142, 142));
/* 168:157 */     this.titledBorder5 = new TitledBorder(this.border5, "Inputs");
/* 169:158 */     this.border6 = BorderFactory.createEmptyBorder();
/* 170:159 */     this.titledBorder6 = new TitledBorder(new EtchedBorder(0, Color.white, new Color(142, 142, 142)), "Outputs");
/* 171:160 */     this.border7 = BorderFactory.createEtchedBorder(Color.white, new Color(134, 134, 134));
/* 172:161 */     this.titledBorder7 = new TitledBorder(this.border7, "Inputs");
/* 173:162 */     this.border8 = BorderFactory.createEtchedBorder(Color.white, new Color(134, 134, 134));
/* 174:163 */     this.titledBorder8 = new TitledBorder(this.border8, "Outputs");
/* 175:164 */     this.panel1.setLayout(this.borderLayout1);
/* 176:165 */     this.MQCalcPanel.setLayout(this.gridBagLayout1);
/* 177:166 */     this.jPanel1.setBorder(this.titledBorder3);
/* 178:167 */     this.jPanel1.setLayout(this.gridBagLayout2);
/* 179:168 */     this.jPanel2.setBorder(this.titledBorder4);
/* 180:169 */     this.jPanel2.setLayout(this.gridLayout1);
/* 181:170 */     this.MQcalculatePB.setText("Calculate");
/* 182:171 */     this.MQcalculatePB.addActionListener(new ActionListener()
/* 183:    */     {
/* 184:    */       public void actionPerformed(ActionEvent e)
/* 185:    */       {
/* 186:174 */         MeasurementUtil.this.MQcalculatePB_actionPerformed(e);
/* 187:    */       }
/* 188:176 */     });
/* 189:177 */     this.MQCancelPB.setText("Cancel");
/* 190:178 */     this.MQCancelPB.addActionListener(new ActionListener()
/* 191:    */     {
/* 192:    */       public void actionPerformed(ActionEvent e)
/* 193:    */       {
/* 194:181 */         MeasurementUtil.this.MQCancelPB_actionPerformed(e);
/* 195:    */       }
/* 196:183 */     });
/* 197:184 */     this.jLabel1.setText("MDG File:");
/* 198:185 */     this.mdgSelectPB.setText("Select...");
/* 199:186 */     this.mdgSelectPB.addActionListener(new ActionListener()
/* 200:    */     {
/* 201:    */       public void actionPerformed(ActionEvent e)
/* 202:    */       {
/* 203:189 */         MeasurementUtil.this.mdgSelectPB_actionPerformed(e);
/* 204:    */       }
/* 205:191 */     });
/* 206:192 */     this.jLabel2.setText("SIL File:");
/* 207:193 */     this.silSelectPB.setText("Select...");
/* 208:194 */     this.silSelectPB.addActionListener(new ActionListener()
/* 209:    */     {
/* 210:    */       public void actionPerformed(ActionEvent e)
/* 211:    */       {
/* 212:197 */         MeasurementUtil.this.silSelectPB_actionPerformed(e);
/* 213:    */       }
/* 214:199 */     });
/* 215:200 */     this.jLabel3.setText("Calculator:");
/* 216:201 */     this.gridLayout1.setRows(4);
/* 217:202 */     this.gridLayout1.setColumns(2);
/* 218:203 */     this.gridLayout1.setHgap(1);
/* 219:204 */     this.jLabel4.setText("Graph Size (Nodes):");
/* 220:205 */     this.nodeST.setToolTipText("");
/* 221:206 */     this.nodeST.setText("0");
/* 222:207 */     this.jLabel6.setText("Graph Size (Edges):");
/* 223:208 */     this.edgesST.setText("0");
/* 224:209 */     this.jLabel8.setText("Number of Clusters:");
/* 225:210 */     this.clusterST.setText("0");
/* 226:211 */     this.mqLabel.setText("Objective Function Value(MQ):");
/* 227:212 */     this.mqST.setText("0.0");
/* 228:213 */     this.PRCalcTab.setLayout(this.gridBagLayout3);
/* 229:214 */     this.jPanel4.setBorder(this.titledBorder5);
/* 230:215 */     this.jPanel4.setLayout(this.gridBagLayout4);
/* 231:216 */     this.PRTab.setBorder(this.titledBorder6);
/* 232:217 */     this.PRTab.setLayout(this.gridLayout2);
/* 233:218 */     this.prCalcPB.setText("Calculate");
/* 234:219 */     this.prCalcPB.addActionListener(new ActionListener()
/* 235:    */     {
/* 236:    */       public void actionPerformed(ActionEvent e)
/* 237:    */       {
/* 238:222 */         MeasurementUtil.this.prCalcPB_actionPerformed(e);
/* 239:    */       }
/* 240:224 */     });
/* 241:225 */     this.prCancel.setText("Cancel");
/* 242:226 */     this.prCancel.addActionListener(new ActionListener()
/* 243:    */     {
/* 244:    */       public void actionPerformed(ActionEvent e)
/* 245:    */       {
/* 246:229 */         MeasurementUtil.this.prCancel_actionPerformed(e);
/* 247:    */       }
/* 248:231 */     });
/* 249:232 */     this.jLabel5.setText("Expert Decomposition:");
/* 250:233 */     this.expertSelectPB.setText("Select...");
/* 251:234 */     this.expertSelectPB.addActionListener(new ActionListener()
/* 252:    */     {
/* 253:    */       public void actionPerformed(ActionEvent e)
/* 254:    */       {
/* 255:237 */         MeasurementUtil.this.expertSelectPB_actionPerformed(e);
/* 256:    */       }
/* 257:239 */     });
/* 258:240 */     this.jLabel7.setText("Sample Decomposition:");
/* 259:241 */     this.samplePB.setText("Select...");
/* 260:242 */     this.samplePB.addActionListener(new ActionListener()
/* 261:    */     {
/* 262:    */       public void actionPerformed(ActionEvent e)
/* 263:    */       {
/* 264:245 */         MeasurementUtil.this.samplePB_actionPerformed(e);
/* 265:    */       }
/* 266:247 */     });
/* 267:248 */     this.gridLayout2.setRows(2);
/* 268:249 */     this.gridLayout2.setColumns(2);
/* 269:250 */     this.jLabel9.setText("  Precision:");
/* 270:251 */     this.jLabel10.setText("  Recall:");
/* 271:252 */     this.precisionST.setText("0 %");
/* 272:253 */     this.recallST.setText("0 %");
/* 273:254 */     this.jPanel7.setLayout(this.gridBagLayout5);
/* 274:255 */     this.ESMeclTab.setBorder(this.titledBorder7);
/* 275:256 */     this.ESMeclTab.setLayout(this.gridBagLayout6);
/* 276:257 */     this.jLabel11.setText("Graph A (SIL FIle):");
/* 277:258 */     this.GraphASPB.setText("Select...");
/* 278:259 */     this.GraphASPB.addActionListener(new ActionListener()
/* 279:    */     {
/* 280:    */       public void actionPerformed(ActionEvent e)
/* 281:    */       {
/* 282:261 */         MeasurementUtil.this.GraphASPB_actionPerformed(e);
/* 283:    */       }
/* 284:263 */     });
/* 285:264 */     this.jLabel12.setText("Graph B (SIL File):");
/* 286:265 */     this.GraphBSelPB.setText("Select...");
/* 287:266 */     this.GraphBSelPB.addActionListener(new ActionListener()
/* 288:    */     {
/* 289:    */       public void actionPerformed(ActionEvent e)
/* 290:    */       {
/* 291:268 */         MeasurementUtil.this.GraphBSelPB_actionPerformed(e);
/* 292:    */       }
/* 293:270 */     });
/* 294:271 */     this.jLabel13.setText("MDG File:");
/* 295:272 */     this.MDGSelPB.setText("Select...");
/* 296:273 */     this.MDGSelPB.addActionListener(new ActionListener()
/* 297:    */     {
/* 298:    */       public void actionPerformed(ActionEvent e)
/* 299:    */       {
/* 300:275 */         MeasurementUtil.this.MDGSelPB_actionPerformed(e);
/* 301:    */       }
/* 302:277 */     });
/* 303:278 */     this.jLabel14.setText("Measurement:");
/* 304:279 */     this.jPanel5.setBorder(this.titledBorder8);
/* 305:280 */     this.jPanel5.setLayout(this.borderLayout2);
/* 306:281 */     this.CalculatePB.setText("Calculate...");
/* 307:282 */     this.CalculatePB.addActionListener(new ActionListener()
/* 308:    */     {
/* 309:    */       public void actionPerformed(ActionEvent e)
/* 310:    */       {
/* 311:284 */         MeasurementUtil.this.CalculatePB_actionPerformed(e);
/* 312:    */       }
/* 313:286 */     });
/* 314:287 */     this.EdgeSimCancelPB.setText("Cancel");
/* 315:288 */     this.EdgeSimCancelPB.addActionListener(new ActionListener()
/* 316:    */     {
/* 317:    */       public void actionPerformed(ActionEvent e)
/* 318:    */       {
/* 319:290 */         MeasurementUtil.this.EdgeSimCancelPB_actionPerformed(e);
/* 320:    */       }
/* 321:292 */     });
/* 322:293 */     this.calculatorCB.addActionListener(new ActionListener()
/* 323:    */     {
/* 324:    */       public void actionPerformed(ActionEvent e)
/* 325:    */       {
/* 326:295 */         MeasurementUtil.this.calculatorCB_actionPerformed(e);
/* 327:    */       }
/* 328:297 */     });
/* 329:298 */     getContentPane().add(this.panel1);
/* 330:299 */     this.panel1.add(this.jTabbedPane1, "Center");
/* 331:300 */     this.jTabbedPane1.add(this.MQCalcPanel, "MQ Calculator");
/* 332:301 */     this.jTabbedPane1.add(this.PRCalcTab, "Precision/Recall Calculator");
/* 333:302 */     this.PRCalcTab.add(this.jPanel4, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 10, 10));
/* 334:    */     
/* 335:304 */     this.jPanel4.add(this.jLabel5, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 336:    */     
/* 337:306 */     this.jPanel4.add(this.expertFileEF, new GridBagConstraints(1, 0, 4, 2, 0.0D, 0.0D, 10, 3, new Insets(0, 5, 0, 5), 194, 0));
/* 338:    */     
/* 339:308 */     this.jPanel4.add(this.expertSelectPB, new GridBagConstraints(5, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, -6));
/* 340:    */     
/* 341:310 */     this.jPanel4.add(this.jLabel7, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 342:    */     
/* 343:312 */     this.jPanel4.add(this.sampleFileEF, new GridBagConstraints(4, 2, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 5, 0, 5), 0, 0));
/* 344:    */     
/* 345:314 */     this.jPanel4.add(this.samplePB, new GridBagConstraints(5, 2, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, -6));
/* 346:    */     
/* 347:316 */     this.PRCalcTab.add(this.PRTab, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
/* 348:    */     
/* 349:318 */     this.PRTab.add(this.jLabel9, null);
/* 350:319 */     this.PRTab.add(this.precisionST, null);
/* 351:320 */     this.PRTab.add(this.jLabel10, null);
/* 352:321 */     this.PRTab.add(this.recallST, null);
/* 353:322 */     this.PRCalcTab.add(this.jPanel6, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
/* 354:    */     
/* 355:324 */     this.jPanel6.add(this.prCalcPB, null);
/* 356:325 */     this.jPanel6.add(this.prCancel, null);
/* 357:326 */     this.jTabbedPane1.add(this.jPanel7, "EdgeSim/MeCl");
/* 358:327 */     this.jPanel7.add(this.ESMeclTab, new GridBagConstraints(0, 0, 4, 1, 0.0D, 0.0D, 14, 0, new Insets(0, 0, 0, 0), 28, 0));
/* 359:    */     
/* 360:329 */     this.ESMeclTab.add(this.jLabel11, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 361:    */     
/* 362:331 */     this.ESMeclTab.add(this.GraphAEF, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 5, 0, 5), 171, 0));
/* 363:    */     
/* 364:333 */     this.ESMeclTab.add(this.GraphASPB, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, -4));
/* 365:    */     
/* 366:335 */     this.ESMeclTab.add(this.jLabel12, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 2, new Insets(0, 0, 0, 0), 0, 0));
/* 367:    */     
/* 368:337 */     this.ESMeclTab.add(this.GraphBEF, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 5, 0, 5), 0, 0));
/* 369:    */     
/* 370:339 */     this.ESMeclTab.add(this.GraphBSelPB, new GridBagConstraints(2, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, -4));
/* 371:    */     
/* 372:341 */     this.ESMeclTab.add(this.jLabel13, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 373:    */     
/* 374:343 */     this.ESMeclTab.add(this.MDGEF, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 5, 0, 5), 0, 0));
/* 375:    */     
/* 376:345 */     this.ESMeclTab.add(this.MDGSelPB, new GridBagConstraints(2, 2, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, -4));
/* 377:    */     
/* 378:347 */     this.ESMeclTab.add(this.jLabel14, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 379:    */     
/* 380:349 */     this.ESMeclTab.add(this.MeasurementDD, new GridBagConstraints(1, 3, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 5, 0, 5), 0, 0));
/* 381:    */     
/* 382:351 */     this.jPanel7.add(this.jPanel5, new GridBagConstraints(3, 1, 1, 5, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 0, 52));
/* 383:    */     
/* 384:353 */     this.jPanel5.add(this.jScrollPane1, "Center");
/* 385:354 */     this.jScrollPane1.getViewport().add(this.resultsTA, null);
/* 386:355 */     this.jPanel7.add(this.jPanel8, new GridBagConstraints(3, 6, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
/* 387:    */     
/* 388:357 */     this.jPanel8.add(this.CalculatePB, null);
/* 389:358 */     this.jPanel8.add(this.EdgeSimCancelPB, null);
/* 390:    */     
/* 391:360 */     this.MQCalcPanel.add(this.jPanel1, new GridBagConstraints(0, 0, 4, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 10, 10));
/* 392:    */     
/* 393:362 */     this.jPanel1.add(this.jLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 25, 0));
/* 394:    */     
/* 395:364 */     this.jPanel1.add(this.mdgEF, new GridBagConstraints(1, 1, 5, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 5, 0, 5), 220, 0));
/* 396:    */     
/* 397:366 */     this.jPanel1.add(this.mdgSelectPB, new GridBagConstraints(6, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, -6));
/* 398:    */     
/* 399:368 */     this.jPanel1.add(this.jLabel2, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 400:    */     
/* 401:370 */     this.jPanel1.add(this.silEF, new GridBagConstraints(5, 2, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 5, 0, 5), 0, 0));
/* 402:    */     
/* 403:372 */     this.jPanel1.add(this.silSelectPB, new GridBagConstraints(6, 2, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, -6));
/* 404:    */     
/* 405:374 */     this.jPanel1.add(this.jLabel3, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 406:    */     
/* 407:376 */     this.jPanel1.add(this.calculatorCB, new GridBagConstraints(5, 3, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 5, 0, 5), 0, 0));
/* 408:    */     
/* 409:378 */     this.MQCalcPanel.add(this.jPanel2, new GridBagConstraints(3, 1, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 1, 0));
/* 410:    */     
/* 411:380 */     this.jPanel2.add(this.jLabel4, null);
/* 412:381 */     this.jPanel2.add(this.nodeST, null);
/* 413:382 */     this.jPanel2.add(this.jLabel6, null);
/* 414:383 */     this.jPanel2.add(this.edgesST, null);
/* 415:384 */     this.jPanel2.add(this.jLabel8, null);
/* 416:385 */     this.jPanel2.add(this.clusterST, null);
/* 417:386 */     this.jPanel2.add(this.mqLabel, null);
/* 418:387 */     this.jPanel2.add(this.mqST, null);
/* 419:388 */     this.MQCalcPanel.add(this.jPanel3, new GridBagConstraints(3, 3, 1, 3, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 0, 34));
/* 420:    */     
/* 421:390 */     this.jPanel3.add(this.MQcalculatePB, null);
/* 422:391 */     this.jPanel3.add(this.MQCancelPB, null);
/* 423:    */     
/* 424:393 */     this.MeasurementDD.addItem("EdgeSim");
/* 425:394 */     this.MeasurementDD.addItem("MeCl");
/* 426:    */     
/* 427:396 */     this.mqLabel.setForeground(Color.red.darker());
/* 428:397 */     this.mqST.setForeground(Color.red.darker());
/* 429:    */     
/* 430:399 */     this.fileChooser = new JFileChooser();
/* 431:    */     
/* 432:401 */     this.of = new ObjectiveFunctionCalculatorFactory();
/* 433:402 */     Enumeration e = this.of.getAvailableItems();
/* 434:403 */     while (e.hasMoreElements()) {
/* 435:404 */       this.calculatorCB.addItem((String)e.nextElement());
/* 436:    */     }
/* 437:406 */     this.calculatorCB.setSelectedItem(this.of.getCurrentCalculator());
/* 438:    */   }
/* 439:    */   
/* 440:    */   void cancelPB_actionPerformed(ActionEvent e)
/* 441:    */   {
/* 442:410 */     dispose();
/* 443:    */   }
/* 444:    */   
/* 445:    */   void mdgSelectPB_actionPerformed(ActionEvent e)
/* 446:    */   {
/* 447:414 */     int returnVal = this.fileChooser.showOpenDialog(this);
/* 448:415 */     if (returnVal == 0) {
/* 449:416 */       this.mdgEF.setText(this.fileChooser.getSelectedFile().getAbsolutePath());
/* 450:    */     }
/* 451:    */   }
/* 452:    */   
/* 453:    */   void silSelectPB_actionPerformed(ActionEvent e)
/* 454:    */   {
/* 455:420 */     int returnVal = this.fileChooser.showOpenDialog(this);
/* 456:421 */     if (returnVal == 0) {
/* 457:422 */       this.silEF.setText(this.fileChooser.getSelectedFile().getAbsolutePath());
/* 458:    */     }
/* 459:    */   }
/* 460:    */   
/* 461:    */   void MQCancelPB_actionPerformed(ActionEvent e)
/* 462:    */   {
/* 463:426 */     dispose();
/* 464:    */   }
/* 465:    */   
/* 466:    */   void MQcalculatePB_actionPerformed(ActionEvent e)
/* 467:    */   {
/* 468:    */     try
/* 469:    */     {
/* 470:434 */       String mdg = this.mdgEF.getText();
/* 471:435 */       String sil = this.silEF.getText();
/* 472:438 */       if (!BunchGraphUtils.isSilFileOK(mdg, sil))
/* 473:    */       {
/* 474:440 */         String out = "The SIL File is Missing Nodes from MDG";
/* 475:441 */         out = out + "\r\nThe following modules need to be in the SIL File:\r\n";
/* 476:    */         
/* 477:443 */         ArrayList mlist = BunchGraphUtils.getMissingSilNodes(mdg, sil);
/* 478:444 */         for (int i = 0; i < mlist.size(); i++) {
/* 479:445 */           out = out + "\r\n" + (i + 1) + ". " + mlist.get(i);
/* 480:    */         }
/* 481:447 */         JOptionPane.showMessageDialog(null, out, "SIL File Error", 0);
/* 482:    */         
/* 483:    */ 
/* 484:    */ 
/* 485:451 */         return;
/* 486:    */       }
/* 487:455 */       Parser p = new DependencyFileParser();
/* 488:456 */       p.setInput(mdg);
/* 489:457 */       p.setDelims(this.bunchFrame.getDelims());
/* 490:    */       
/* 491:459 */       Graph g = (Graph)p.parse();
/* 492:460 */       ObjectiveFunctionCalculatorFactory ofc = new ObjectiveFunctionCalculatorFactory();
/* 493:461 */       ofc.setCurrentCalculator((String)this.calculatorCB.getSelectedItem());
/* 494:462 */       Graph.setObjectiveFunctionCalculatorFactory(ofc);
/* 495:    */       
/* 496:464 */       g.setObjectiveFunctionCalculator((String)this.calculatorCB.getSelectedItem());
/* 497:    */       
/* 498:466 */       ClusterFileParser cfp = new ClusterFileParser();
/* 499:467 */       cfp.setInput(sil);
/* 500:468 */       cfp.setObject(g);
/* 501:469 */       cfp.parse();
/* 502:470 */       g.calculateObjectiveFunctionValue();
/* 503:    */       
/* 504:    */ 
/* 505:473 */       long edgeCnt = 0L;
/* 506:474 */       Node[] n = g.getNodes();
/* 507:475 */       for (int i = 0; i < n.length; i++) {
/* 508:477 */         if (n[i].dependencies != null) {
/* 509:478 */           edgeCnt += n[i].dependencies.length;
/* 510:    */         }
/* 511:    */       }
/* 512:482 */       this.nodeST.setText(Integer.toString(g.getNodes().length));
/* 513:483 */       this.clusterST.setText(Integer.toString(g.getClusterNames().length));
/* 514:484 */       this.edgesST.setText(Long.toString(edgeCnt));
/* 515:485 */       this.mqST.setText(Double.toString(g.getObjectiveFunctionValue()));
/* 516:    */     }
/* 517:    */     catch (Exception calcExcept)
/* 518:    */     {
/* 519:491 */       calcExcept.printStackTrace();
/* 520:    */     }
/* 521:    */   }
/* 522:    */   
/* 523:    */   void expertSelectPB_actionPerformed(ActionEvent e)
/* 524:    */   {
/* 525:496 */     int returnVal = this.fileChooser.showOpenDialog(this);
/* 526:497 */     if (returnVal == 0) {
/* 527:498 */       this.expertFileEF.setText(this.fileChooser.getSelectedFile().getAbsolutePath());
/* 528:    */     }
/* 529:    */   }
/* 530:    */   
/* 531:    */   void samplePB_actionPerformed(ActionEvent e)
/* 532:    */   {
/* 533:502 */     int returnVal = this.fileChooser.showOpenDialog(this);
/* 534:503 */     if (returnVal == 0) {
/* 535:504 */       this.sampleFileEF.setText(this.fileChooser.getSelectedFile().getAbsolutePath());
/* 536:    */     }
/* 537:    */   }
/* 538:    */   
/* 539:    */   void prCancel_actionPerformed(ActionEvent e)
/* 540:    */   {
/* 541:508 */     dispose();
/* 542:    */   }
/* 543:    */   
/* 544:    */   void prCalcPB_actionPerformed(ActionEvent e)
/* 545:    */   {
/* 546:512 */     String expertFileName = this.expertFileEF.getText();
/* 547:513 */     String sampleFileName = this.sampleFileEF.getText();
/* 548:    */     
/* 549:515 */     PrecisionRecallCalculator prcalc = new PrecisionRecallCalculator(expertFileName, sampleFileName);
/* 550:516 */     this.precisionST.setText(prcalc.get_precision());
/* 551:517 */     this.recallST.setText(prcalc.get_recall());
/* 552:    */   }
/* 553:    */   
/* 554:    */   void GraphASPB_actionPerformed(ActionEvent e)
/* 555:    */   {
/* 556:521 */     int returnVal = this.fileChooser.showOpenDialog(this);
/* 557:522 */     if (returnVal == 0) {
/* 558:523 */       this.GraphAEF.setText(this.fileChooser.getSelectedFile().getAbsolutePath());
/* 559:    */     }
/* 560:    */   }
/* 561:    */   
/* 562:    */   void GraphBSelPB_actionPerformed(ActionEvent e)
/* 563:    */   {
/* 564:527 */     int returnVal = this.fileChooser.showOpenDialog(this);
/* 565:528 */     if (returnVal == 0) {
/* 566:529 */       this.GraphBEF.setText(this.fileChooser.getSelectedFile().getAbsolutePath());
/* 567:    */     }
/* 568:    */   }
/* 569:    */   
/* 570:    */   void MDGSelPB_actionPerformed(ActionEvent e)
/* 571:    */   {
/* 572:533 */     int returnVal = this.fileChooser.showOpenDialog(this);
/* 573:534 */     if (returnVal == 0) {
/* 574:535 */       this.MDGEF.setText(this.fileChooser.getSelectedFile().getAbsolutePath());
/* 575:    */     }
/* 576:    */   }
/* 577:    */   
/* 578:    */   void CalculatePB_actionPerformed(ActionEvent e)
/* 579:    */   {
/* 580:539 */     String ga = this.GraphAEF.getText();
/* 581:540 */     String gb = this.GraphBEF.getText();
/* 582:541 */     String mdg = this.MDGEF.getText();
/* 583:542 */     String tt = (String)this.MeasurementDD.getSelectedItem();
/* 584:545 */     if (!BunchGraphUtils.isSilFileOK(mdg, ga))
/* 585:    */     {
/* 586:547 */       String out = "Graph A SIL File Missing Nodes in MDG";
/* 587:548 */       out = out + "\r\nThe following modules need to be in the SIL File:\r\n";
/* 588:    */       
/* 589:550 */       ArrayList mlist = BunchGraphUtils.getMissingSilNodes(mdg, ga);
/* 590:551 */       for (int i = 0; i < mlist.size(); i++) {
/* 591:552 */         out = out + "\r\n" + (i + 1) + ". " + mlist.get(i);
/* 592:    */       }
/* 593:554 */       this.resultsTA.setText(out);
/* 594:555 */       return;
/* 595:    */     }
/* 596:557 */     if (!BunchGraphUtils.isSilFileOK(mdg, gb))
/* 597:    */     {
/* 598:559 */       String out = "Graph B SIL File Missing Nodes in MDG";
/* 599:560 */       out = out + "\r\nThe following modules need to be in the SIL File:\r\n";
/* 600:    */       
/* 601:562 */       ArrayList mlist = BunchGraphUtils.getMissingSilNodes(mdg, gb);
/* 602:563 */       for (int i = 0; i < mlist.size(); i++) {
/* 603:564 */         out = out + "\r\n" + (i + 1) + ". " + mlist.get(i);
/* 604:    */       }
/* 605:566 */       this.resultsTA.setText(out);
/* 606:567 */       return;
/* 607:    */     }
/* 608:571 */     BunchGraph bgA = BunchGraphUtils.constructFromSil(mdg, ga);
/* 609:572 */     BunchGraph bgB = BunchGraphUtils.constructFromSil(mdg, gb);
/* 610:    */     
/* 611:574 */     String out = "";
/* 612:576 */     if (tt.equalsIgnoreCase("EdgeSim"))
/* 613:    */     {
/* 614:578 */       double es = BunchGraphUtils.calcEdgeSim(bgA, bgB);
/* 615:579 */       es *= 10000.0D;
/* 616:580 */       int ies = (int)es;
/* 617:581 */       double es2 = ies / 100.0D;
/* 618:    */       
/* 619:583 */       out = out + "EdgeSim(A,B) = " + es2 + "\r\n";
/* 620:    */     }
/* 621:    */     else
/* 622:    */     {
/* 623:587 */       double m1 = BunchGraphUtils.getMeClDistance(bgA, bgB);
/* 624:588 */       double m2 = BunchGraphUtils.getMeClDistance(bgB, bgA);
/* 625:    */       
/* 626:590 */       m1 = 100.0D - m1;
/* 627:591 */       m2 = 100.0D - m2;
/* 628:    */       
/* 629:593 */       double mmin = Math.min(m1, m2);
/* 630:    */       
/* 631:595 */       out = out + "MeCl(A,B) = " + m1 + "%\r\n";
/* 632:596 */       out = out + "MeCl(B,A) = " + m2 + "%\r\n";
/* 633:598 */       if (m1 <= m2) {
/* 634:599 */         out = out + "MeCl = " + m1 + "% because MeCl(A,B) <= Mecl(B,A)\r\n";
/* 635:    */       } else {
/* 636:601 */         out = out + "MeCl = " + m2 + "% because MeCl(B,A) < Mecl(A,B)\r\n";
/* 637:    */       }
/* 638:    */     }
/* 639:606 */     this.resultsTA.setText(out);
/* 640:    */   }
/* 641:    */   
/* 642:    */   void EdgeSimCancelPB_actionPerformed(ActionEvent e)
/* 643:    */   {
/* 644:610 */     dispose();
/* 645:    */   }
/* 646:    */   
/* 647:    */   void calculatorCB_actionPerformed(ActionEvent e) {}
/* 648:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.util.MeasurementUtil
 * JD-Core Version:    0.7.0.1
 */