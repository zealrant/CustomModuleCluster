/*   1:    */ package bunch.util;
/*   2:    */ 
/*   3:    */ import bunch.ObjectiveFunctionCalculatorFactory;
/*   4:    */ import bunch.api.BunchCluster;
/*   5:    */ import bunch.api.BunchGraph;
/*   6:    */ import bunch.api.BunchGraphUtils;
/*   7:    */ import bunch.api.BunchNode;
/*   8:    */ import java.awt.BorderLayout;
/*   9:    */ import java.awt.Color;
/*  10:    */ import java.awt.Container;
/*  11:    */ import java.awt.FileDialog;
/*  12:    */ import java.awt.Font;
/*  13:    */ import java.awt.Frame;
/*  14:    */ import java.awt.GridBagConstraints;
/*  15:    */ import java.awt.GridBagLayout;
/*  16:    */ import java.awt.Insets;
/*  17:    */ import java.awt.event.ActionEvent;
/*  18:    */ import java.awt.event.ActionListener;
/*  19:    */ import java.io.File;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.Collection;
/*  22:    */ import java.util.Enumeration;
/*  23:    */ import javax.swing.JButton;
/*  24:    */ import javax.swing.JComboBox;
/*  25:    */ import javax.swing.JDialog;
/*  26:    */ import javax.swing.JFileChooser;
/*  27:    */ import javax.swing.JLabel;
/*  28:    */ import javax.swing.JPanel;
/*  29:    */ import javax.swing.JTabbedPane;
/*  30:    */ import javax.swing.JTextField;
/*  31:    */ 
/*  32:    */ public class BunchClusteringUtil
/*  33:    */   extends JDialog
/*  34:    */ {
/*  35: 25 */   JPanel panel1 = new JPanel();
/*  36: 26 */   BorderLayout borderLayout1 = new BorderLayout();
/*  37: 27 */   JPanel jPanel1 = new JPanel();
/*  38: 28 */   JButton DonePB = new JButton();
/*  39: 29 */   JTabbedPane jTabbedPane1 = new JTabbedPane();
/*  40: 30 */   JPanel OrphanAdoption = new JPanel();
/*  41: 31 */   GridBagLayout gridBagLayout1 = new GridBagLayout();
/*  42: 32 */   JLabel jLabel1 = new JLabel();
/*  43: 33 */   JTextField mdgFileNameEF = new JTextField();
/*  44: 34 */   JButton mdgSelectPB = new JButton();
/*  45: 35 */   JLabel jLabel2 = new JLabel();
/*  46: 36 */   JTextField silFileNameEF = new JTextField();
/*  47: 37 */   JButton silFileSelectPB = new JButton();
/*  48:    */   FileDialog fd;
/*  49:    */   JFileChooser fileChooser;
/*  50: 41 */   JLabel jLabel3 = new JLabel();
/*  51: 42 */   JTextField orphanEF = new JTextField();
/*  52: 43 */   JPanel jPanel2 = new JPanel();
/*  53: 44 */   JButton RunPB = new JButton();
/*  54: 45 */   JButton DeterminePB = new JButton();
/*  55: 46 */   JLabel jLabel4 = new JLabel();
/*  56: 47 */   JTextField outputSILEF = new JTextField();
/*  57: 48 */   JButton outputSILPB = new JButton();
/*  58: 49 */   JLabel messageST = new JLabel();
/*  59: 50 */   JLabel jLabel6 = new JLabel();
/*  60: 51 */   JComboBox calculatorCB = new JComboBox();
/*  61:    */   ObjectiveFunctionCalculatorFactory of;
/*  62:    */   
/*  63:    */   public BunchClusteringUtil(Frame frame, String title, boolean modal)
/*  64:    */   {
/*  65: 55 */     super(frame, title, modal);
/*  66:    */     try
/*  67:    */     {
/*  68: 57 */       jbInit();
/*  69: 58 */       pack();
/*  70:    */     }
/*  71:    */     catch (Exception ex)
/*  72:    */     {
/*  73: 61 */       ex.printStackTrace();
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public BunchClusteringUtil()
/*  78:    */   {
/*  79: 66 */     this(null, "", false);
/*  80:    */   }
/*  81:    */   
/*  82:    */   void jbInit()
/*  83:    */     throws Exception
/*  84:    */   {
/*  85: 69 */     this.panel1.setLayout(this.borderLayout1);
/*  86: 70 */     this.DonePB.setText("Close");
/*  87: 71 */     this.DonePB.addActionListener(new ActionListener()
/*  88:    */     {
/*  89:    */       public void actionPerformed(ActionEvent e)
/*  90:    */       {
/*  91: 73 */         BunchClusteringUtil.this.DonePB_actionPerformed(e);
/*  92:    */       }
/*  93: 75 */     });
/*  94: 76 */     this.OrphanAdoption.setLayout(this.gridBagLayout1);
/*  95: 77 */     this.jLabel1.setText("MDG File Name:");
/*  96: 78 */     this.mdgSelectPB.setText("Select...");
/*  97: 79 */     this.mdgSelectPB.addActionListener(new ActionListener()
/*  98:    */     {
/*  99:    */       public void actionPerformed(ActionEvent e)
/* 100:    */       {
/* 101: 81 */         BunchClusteringUtil.this.mdgSelectPB_actionPerformed(e);
/* 102:    */       }
/* 103: 83 */     });
/* 104: 84 */     this.jLabel2.setText("SIL File Name:");
/* 105: 85 */     this.silFileSelectPB.setToolTipText("");
/* 106: 86 */     this.silFileSelectPB.setText("Select...");
/* 107: 87 */     this.silFileSelectPB.addActionListener(new ActionListener()
/* 108:    */     {
/* 109:    */       public void actionPerformed(ActionEvent e)
/* 110:    */       {
/* 111: 89 */         BunchClusteringUtil.this.silFileSelectPB_actionPerformed(e);
/* 112:    */       }
/* 113: 91 */     });
/* 114: 92 */     this.jLabel3.setText("Orphan Module:");
/* 115: 93 */     this.RunPB.setText("Run...");
/* 116: 94 */     this.RunPB.addActionListener(new ActionListener()
/* 117:    */     {
/* 118:    */       public void actionPerformed(ActionEvent e)
/* 119:    */       {
/* 120: 96 */         BunchClusteringUtil.this.RunPB_actionPerformed(e);
/* 121:    */       }
/* 122: 98 */     });
/* 123: 99 */     this.DeterminePB.setText("Detect...");
/* 124:100 */     this.DeterminePB.addActionListener(new ActionListener()
/* 125:    */     {
/* 126:    */       public void actionPerformed(ActionEvent e)
/* 127:    */       {
/* 128:102 */         BunchClusteringUtil.this.DeterminePB_actionPerformed(e);
/* 129:    */       }
/* 130:104 */     });
/* 131:105 */     this.jLabel4.setText("Output SIL File:");
/* 132:106 */     this.outputSILPB.setText("Select...");
/* 133:107 */     this.outputSILPB.addActionListener(new ActionListener()
/* 134:    */     {
/* 135:    */       public void actionPerformed(ActionEvent e)
/* 136:    */       {
/* 137:109 */         BunchClusteringUtil.this.outputSILPB_actionPerformed(e);
/* 138:    */       }
/* 139:111 */     });
/* 140:112 */     this.messageST.setFont(new Font("Dialog", 1, 12));
/* 141:113 */     this.messageST.setForeground(Color.red);
/* 142:114 */     this.messageST.setText("Provide the required ifnromation and press Run...");
/* 143:115 */     this.jLabel6.setText("MQ Calculator:");
/* 144:116 */     getContentPane().add(this.panel1);
/* 145:117 */     this.panel1.add(this.jPanel1, "South");
/* 146:118 */     this.jPanel1.add(this.DonePB, null);
/* 147:119 */     this.panel1.add(this.jTabbedPane1, "Center");
/* 148:120 */     this.jTabbedPane1.add(this.OrphanAdoption, "Orphan Adoption");
/* 149:121 */     this.OrphanAdoption.add(this.jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 10, 0, 5), 0, 0));
/* 150:    */     
/* 151:123 */     this.OrphanAdoption.add(this.mdgFileNameEF, new GridBagConstraints(1, 0, 2, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 161, 0));
/* 152:    */     
/* 153:125 */     this.OrphanAdoption.add(this.mdgSelectPB, new GridBagConstraints(3, 0, 1, 1, 0.0D, 0.0D, 17, 2, new Insets(0, 5, 0, 10), 0, 0));
/* 154:    */     
/* 155:127 */     this.OrphanAdoption.add(this.jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 10, 0, 0), 0, 0));
/* 156:    */     
/* 157:129 */     this.OrphanAdoption.add(this.silFileNameEF, new GridBagConstraints(1, 1, 2, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 44, 0));
/* 158:    */     
/* 159:131 */     this.OrphanAdoption.add(this.silFileSelectPB, new GridBagConstraints(3, 1, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 5, 0, 10), 0, 0));
/* 160:    */     
/* 161:133 */     this.OrphanAdoption.add(this.jLabel3, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 162:    */     
/* 163:135 */     this.OrphanAdoption.add(this.orphanEF, new GridBagConstraints(1, 3, 2, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 0, 0));
/* 164:    */     
/* 165:137 */     this.OrphanAdoption.add(this.jPanel2, new GridBagConstraints(0, 5, 4, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
/* 166:    */     
/* 167:139 */     this.jPanel2.add(this.RunPB, null);
/* 168:140 */     this.OrphanAdoption.add(this.DeterminePB, new GridBagConstraints(3, 3, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 5, 0, 10), 0, 0));
/* 169:    */     
/* 170:142 */     this.OrphanAdoption.add(this.jLabel4, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 10, 0, 0), 0, 0));
/* 171:    */     
/* 172:144 */     this.OrphanAdoption.add(this.outputSILEF, new GridBagConstraints(2, 2, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 0, 0));
/* 173:    */     
/* 174:146 */     this.OrphanAdoption.add(this.outputSILPB, new GridBagConstraints(3, 2, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 5, 0, 10), 0, 0));
/* 175:    */     
/* 176:148 */     this.OrphanAdoption.add(this.messageST, new GridBagConstraints(0, 6, 4, 1, 0.0D, 0.0D, 10, 0, new Insets(10, 0, 10, 0), 0, 0));
/* 177:    */     
/* 178:150 */     this.OrphanAdoption.add(this.jLabel6, new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 17, 2, new Insets(0, 10, 0, 0), 0, 0));
/* 179:    */     
/* 180:152 */     this.OrphanAdoption.add(this.calculatorCB, new GridBagConstraints(2, 4, 2, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 10), 0, 0));
/* 181:    */     
/* 182:    */ 
/* 183:155 */     this.fileChooser = new JFileChooser();
/* 184:    */     
/* 185:157 */     this.of = new ObjectiveFunctionCalculatorFactory();
/* 186:158 */     Enumeration e = this.of.getAvailableItems();
/* 187:159 */     while (e.hasMoreElements()) {
/* 188:160 */       this.calculatorCB.addItem((String)e.nextElement());
/* 189:    */     }
/* 190:162 */     this.calculatorCB.setSelectedItem(this.of.getCurrentCalculator());
/* 191:    */   }
/* 192:    */   
/* 193:    */   void DonePB_actionPerformed(ActionEvent e)
/* 194:    */   {
/* 195:166 */     dispose();
/* 196:    */   }
/* 197:    */   
/* 198:    */   void mdgSelectPB_actionPerformed(ActionEvent e)
/* 199:    */   {
/* 200:170 */     int returnVal = this.fileChooser.showOpenDialog(this);
/* 201:171 */     if (returnVal == 0) {
/* 202:172 */       this.mdgFileNameEF.setText(this.fileChooser.getSelectedFile().getAbsolutePath());
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   void silFileSelectPB_actionPerformed(ActionEvent e)
/* 207:    */   {
/* 208:176 */     int returnVal = this.fileChooser.showOpenDialog(this);
/* 209:177 */     if (returnVal == 0) {
/* 210:178 */       this.silFileNameEF.setText(this.fileChooser.getSelectedFile().getAbsolutePath());
/* 211:    */     }
/* 212:    */   }
/* 213:    */   
/* 214:    */   void RunPB_actionPerformed(ActionEvent e)
/* 215:    */   {
/* 216:183 */     String outputSil = this.outputSILEF.getText();
/* 217:184 */     String orphan = this.orphanEF.getText();
/* 218:185 */     String mdg = this.mdgFileNameEF.getText();
/* 219:    */     
/* 220:187 */     int bestCID = -1;
/* 221:188 */     double bestMQ = 0.0D;
/* 222:189 */     BunchCluster bestCluster = null;
/* 223:    */     
/* 224:191 */     BunchGraph g = BunchGraphUtils.constructFromSil(this.mdgFileNameEF.getText(), this.silFileNameEF.getText());
/* 225:    */     
/* 226:    */ 
/* 227:194 */     int numClusters = g.getClusters().size();
/* 228:195 */     ArrayList cl = new ArrayList(g.getClusters());
/* 229:196 */     BunchNode bn = g.findNode(orphan);
/* 230:198 */     for (int i = 0; i < numClusters; i++)
/* 231:    */     {
/* 232:200 */       BunchCluster bc = (BunchCluster)cl.get(i);
/* 233:201 */       int cID = bc.getID();
/* 234:202 */       bn.resetCluster(cID);
/* 235:203 */       bc.addNode(bn);
/* 236:    */       try
/* 237:    */       {
/* 238:206 */         g.writeSILFile(outputSil);
/* 239:    */       }
/* 240:    */       catch (Exception ex)
/* 241:    */       {
/* 242:209 */         this.messageST.setText("Exception while writing the output SIL file!");
/* 243:    */       }
/* 244:212 */       double mqResult = MQCalculator.CalcMQ(mdg, outputSil, (String)this.calculatorCB.getSelectedItem());
/* 245:215 */       if (mqResult > bestMQ)
/* 246:    */       {
/* 247:217 */         bestMQ = mqResult;
/* 248:218 */         bestCID = bc.getID();
/* 249:219 */         bestCluster = bc;
/* 250:    */       }
/* 251:222 */       bc.removeNode(bn);
/* 252:223 */       bn.resetCluster(-1);
/* 253:    */     }
/* 254:    */     double mqResult;
/* 255:226 */     if (bestCluster != null)
/* 256:    */     {
/* 257:228 */       int id = bestCluster.getID();
/* 258:229 */       bn.resetCluster(id);
/* 259:230 */       bestCluster.addNode(bn);
/* 260:    */       try
/* 261:    */       {
/* 262:233 */         g.writeSILFile(outputSil);
/* 263:    */       }
/* 264:    */       catch (Exception ex)
/* 265:    */       {
/* 266:236 */         this.messageST.setText("Exception while writing the final output SIL file!");
/* 267:    */       }
/* 268:239 */       mqResult = MQCalculator.CalcMQ(mdg, outputSil, (String)this.calculatorCB.getSelectedItem());
/* 269:    */     }
/* 270:    */   }
/* 271:    */   
/* 272:    */   void DeterminePB_actionPerformed(ActionEvent e)
/* 273:    */   {
/* 274:245 */     BunchGraph g = BunchGraphUtils.constructFromSil(this.mdgFileNameEF.getText(), this.silFileNameEF.getText());
/* 275:    */     
/* 276:    */ 
/* 277:248 */     ArrayList al = new ArrayList(g.getNodes());
/* 278:249 */     for (int i = 0; i < al.size(); i++)
/* 279:    */     {
/* 280:251 */       BunchNode n = (BunchNode)al.get(i);
/* 281:252 */       if (n.getCluster() == -1) {
/* 282:253 */         this.orphanEF.setText(n.getName());
/* 283:    */       }
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   void outputSILPB_actionPerformed(ActionEvent e)
/* 288:    */   {
/* 289:258 */     int returnVal = this.fileChooser.showSaveDialog(this);
/* 290:259 */     if (returnVal == 0) {
/* 291:260 */       this.outputSILEF.setText(this.fileChooser.getSelectedFile().getAbsolutePath());
/* 292:    */     }
/* 293:    */   }
/* 294:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.util.BunchClusteringUtil
 * JD-Core Version:    0.7.0.1
 */