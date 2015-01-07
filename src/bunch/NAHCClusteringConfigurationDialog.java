/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.awt.Frame;
/*   6:    */ import java.awt.GridBagConstraints;
/*   7:    */ import java.awt.GridBagLayout;
/*   8:    */ import java.awt.Insets;
/*   9:    */ import java.awt.event.ActionEvent;
/*  10:    */ import java.awt.event.ActionListener;
/*  11:    */ import java.awt.event.InputMethodEvent;
/*  12:    */ import java.awt.event.InputMethodListener;
/*  13:    */ import java.util.Enumeration;
/*  14:    */ import java.util.Hashtable;
/*  15:    */ import javax.swing.BorderFactory;
/*  16:    */ import javax.swing.JButton;
/*  17:    */ import javax.swing.JCheckBox;
/*  18:    */ import javax.swing.JComboBox;
/*  19:    */ import javax.swing.JLabel;
/*  20:    */ import javax.swing.JPanel;
/*  21:    */ import javax.swing.JSlider;
/*  22:    */ import javax.swing.JTextField;
/*  23:    */ import javax.swing.border.Border;
/*  24:    */ import javax.swing.border.EtchedBorder;
/*  25:    */ import javax.swing.border.TitledBorder;
/*  26:    */ import javax.swing.event.ChangeEvent;
/*  27:    */ import javax.swing.event.ChangeListener;
/*  28:    */ 
/*  29:    */ public class NAHCClusteringConfigurationDialog
/*  30:    */   extends ClusteringConfigurationDialog
/*  31:    */ {
/*  32: 56 */   JPanel panel1 = new JPanel();
/*  33: 57 */   GridBagLayout gridBagLayout1 = new GridBagLayout();
/*  34: 58 */   JPanel jPanel1 = new JPanel();
/*  35:    */   Border border1;
/*  36:    */   TitledBorder titledBorder1;
/*  37: 61 */   GridBagLayout gridBagLayout2 = new GridBagLayout();
/*  38: 62 */   JLabel jLabel1 = new JLabel();
/*  39: 63 */   JTextField populationSzEF = new JTextField();
/*  40: 64 */   JPanel jPanel2 = new JPanel();
/*  41: 65 */   GridBagLayout gridBagLayout3 = new GridBagLayout();
/*  42:    */   Border border2;
/*  43:    */   TitledBorder titledBorder2;
/*  44: 68 */   JCheckBox SAEnable = new JCheckBox();
/*  45: 69 */   JLabel jLabel2 = new JLabel();
/*  46: 70 */   JComboBox SATechniqueCB = new JComboBox();
/*  47: 71 */   JButton ConfigurePB = new JButton();
/*  48: 72 */   SATechniqueFactory safactory = new SATechniqueFactory();
/*  49: 73 */   SATechnique saMethod = null;
/*  50: 74 */   JLabel descriptionST = new JLabel();
/*  51: 75 */   Frame parentFrame = null;
/*  52: 76 */   NAHCConfiguration nahcConfig = null;
/*  53: 77 */   JLabel jLabel3 = new JLabel();
/*  54: 78 */   JLabel pctToConsiderST = new JLabel();
/*  55: 79 */   JSlider sliderAdjust = new JSlider();
/*  56: 80 */   JLabel jLabel4 = new JLabel();
/*  57: 81 */   JLabel jLabel5 = new JLabel();
/*  58: 82 */   JLabel fillerLBL = new JLabel();
/*  59: 83 */   JLabel jLabel6 = new JLabel();
/*  60: 84 */   JTextField randomizePctEF = new JTextField();
/*  61:    */   
/*  62:    */   public NAHCClusteringConfigurationDialog(Frame frame, String title, boolean modal)
/*  63:    */   {
/*  64: 87 */     super(frame, title, modal);
/*  65: 88 */     this.parentFrame = frame;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public NAHCClusteringConfigurationDialog()
/*  69:    */   {
/*  70:100 */     this(null, "", false);
/*  71:    */   }
/*  72:    */   
/*  73:    */   void jbInit()
/*  74:    */     throws Exception
/*  75:    */   {
/*  76:104 */     this.nahcConfig = ((NAHCConfiguration)this.configuration_d);
/*  77:    */     
/*  78:106 */     this.border1 = BorderFactory.createBevelBorder(0, Color.white, Color.white, new Color(142, 142, 142), new Color(99, 99, 99));
/*  79:107 */     this.titledBorder1 = new TitledBorder(new EtchedBorder(0, Color.white, new Color(142, 142, 142)), "Clustering Options");
/*  80:108 */     this.border2 = BorderFactory.createBevelBorder(0, Color.white, Color.white, new Color(142, 142, 142), new Color(99, 99, 99));
/*  81:109 */     this.titledBorder2 = new TitledBorder(new EtchedBorder(0, Color.white, new Color(142, 142, 142)), "Simulated Annealing");
/*  82:110 */     this.panel1.setLayout(this.gridBagLayout1);
/*  83:111 */     this.jPanel1.setBorder(this.titledBorder1);
/*  84:112 */     this.jPanel1.setLayout(this.gridBagLayout2);
/*  85:113 */     this.jLabel1.setText("Population Size:");
/*  86:114 */     this.populationSzEF.setText("5");
/*  87:115 */     this.populationSzEF.setText(Integer.toString(this.configuration_d.getPopulationSize()));
/*  88:116 */     this.jPanel2.setLayout(this.gridBagLayout3);
/*  89:117 */     this.jPanel2.setBorder(this.titledBorder2);
/*  90:118 */     this.SAEnable.setText("Enable Simulated Annealing");
/*  91:119 */     this.SAEnable.addActionListener(new ActionListener()
/*  92:    */     {
/*  93:    */       public void actionPerformed(ActionEvent e)
/*  94:    */       {
/*  95:122 */         NAHCClusteringConfigurationDialog.this.SAEnable_actionPerformed(e);
/*  96:    */       }
/*  97:124 */     });
/*  98:125 */     this.jLabel2.setText("Technique:");
/*  99:126 */     this.ConfigurePB.setEnabled(false);
/* 100:127 */     this.ConfigurePB.setText("Configure...");
/* 101:128 */     this.ConfigurePB.addActionListener(new ActionListener()
/* 102:    */     {
/* 103:    */       public void actionPerformed(ActionEvent e)
/* 104:    */       {
/* 105:131 */         NAHCClusteringConfigurationDialog.this.ConfigurePB_actionPerformed(e);
/* 106:    */       }
/* 107:134 */     });
/* 108:135 */     this.SATechniqueCB.setEnabled(false);
/* 109:136 */     this.SATechniqueCB.addActionListener(new ActionListener()
/* 110:    */     {
/* 111:    */       public void actionPerformed(ActionEvent e)
/* 112:    */       {
/* 113:139 */         NAHCClusteringConfigurationDialog.this.SATechniqueCB_actionPerformed(e);
/* 114:    */       }
/* 115:141 */     });
/* 116:142 */     this.descriptionST.setForeground(Color.red);
/* 117:143 */     this.descriptionST.setText("Description:  ");
/* 118:144 */     this.jLabel3.setText("Minimum % of Search Space To Consider:");
/* 119:145 */     this.pctToConsiderST.setForeground(Color.blue);
/* 120:146 */     this.pctToConsiderST.setText("  0%");
/* 121:147 */     this.sliderAdjust.setValue(0);
/* 122:148 */     this.sliderAdjust.addChangeListener(new ChangeListener()
/* 123:    */     {
/* 124:    */       public void stateChanged(ChangeEvent e)
/* 125:    */       {
/* 126:151 */         NAHCClusteringConfigurationDialog.this.sliderAdjust_stateChanged(e);
/* 127:    */       }
/* 128:153 */     });
/* 129:154 */     this.sliderAdjust.addInputMethodListener(new InputMethodListener()
/* 130:    */     {
/* 131:    */       public void inputMethodTextChanged(InputMethodEvent e) {}
/* 132:    */       
/* 133:    */       public void caretPositionChanged(InputMethodEvent e)
/* 134:    */       {
/* 135:160 */         NAHCClusteringConfigurationDialog.this.sliderAdjust_caretPositionChanged(e);
/* 136:    */       }
/* 137:162 */     });
/* 138:163 */     this.jLabel4.setText("NAHC");
/* 139:164 */     this.jLabel5.setText("SAHC");
/* 140:165 */     this.fillerLBL.setText("               ");
/* 141:166 */     this.jLabel6.setText("Randomize %:");
/* 142:167 */     this.randomizePctEF.setText("100");
/* 143:168 */     getContentPane().add(this.panel1);
/* 144:    */     
/* 145:170 */     this.panel1.add(this.jPanel1, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 191, 0));
/* 146:    */     
/* 147:172 */     this.jPanel1.add(this.jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 5), 0, 0));
/* 148:    */     
/* 149:174 */     this.jPanel1.add(this.populationSzEF, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 5, 0, 0), 40, 0));
/* 150:    */     
/* 151:176 */     this.jPanel1.add(this.jLabel3, new GridBagConstraints(0, 1, 3, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 0, 0, 5), 0, 0));
/* 152:    */     
/* 153:178 */     this.jPanel1.add(this.sliderAdjust, new GridBagConstraints(0, 2, 3, 1, 0.0D, 0.0D, 10, 2, new Insets(5, 0, 0, 0), 0, 0));
/* 154:    */     
/* 155:180 */     this.jPanel1.add(this.pctToConsiderST, new GridBagConstraints(3, 2, 2, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 0, 0), 0, 0));
/* 156:    */     
/* 157:182 */     this.jPanel1.add(this.jLabel4, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 158:    */     
/* 159:184 */     this.jPanel1.add(this.jLabel5, new GridBagConstraints(2, 3, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 160:    */     
/* 161:186 */     this.jPanel1.add(this.fillerLBL, new GridBagConstraints(4, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 162:    */     
/* 163:188 */     this.jPanel1.add(this.jLabel6, new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 0, 0, 0), 0, 0));
/* 164:    */     
/* 165:190 */     this.jPanel1.add(this.randomizePctEF, new GridBagConstraints(2, 4, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 5, 0, 0), 11, 0));
/* 166:    */     
/* 167:    */ 
/* 168:193 */     this.panel1.add(this.jPanel2, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(5, 0, 0, 0), 0, 0));
/* 169:    */     
/* 170:195 */     this.jPanel2.add(this.SAEnable, new GridBagConstraints(0, 0, 2, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 171:    */     
/* 172:197 */     this.jPanel2.add(this.jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 5), 0, 0));
/* 173:    */     
/* 174:199 */     this.jPanel2.add(this.SATechniqueCB, new GridBagConstraints(1, 1, 2, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 0, 0, 0), 70, 0));
/* 175:    */     
/* 176:201 */     this.jPanel2.add(this.ConfigurePB, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 0, 5, 0), 0, -4));
/* 177:    */     
/* 178:203 */     this.jPanel2.add(this.descriptionST, new GridBagConstraints(0, 2, 3, 1, 0.0D, 0.0D, 10, 2, new Insets(5, 0, 5, 0), 0, 0));
/* 179:    */     
/* 180:    */ 
/* 181:206 */     this.optionsPanel_d.add(this.panel1);
/* 182:    */     
/* 183:208 */     Enumeration e = this.safactory.getAvailableItems();
/* 184:209 */     while (e.hasMoreElements())
/* 185:    */     {
/* 186:211 */       String name = (String)e.nextElement();
/* 187:212 */       this.SATechniqueCB.addItem(name);
/* 188:    */     }
/* 189:215 */     String initTechnique = this.safactory.getDefaultTechnique();
/* 190:216 */     this.SATechniqueCB.setSelectedItem(initTechnique);
/* 191:217 */     this.saMethod = ((SATechnique)this.safactory.getItemInstance(initTechnique));
/* 192:218 */     this.descriptionST.setText(this.saMethod.getObjectDescription());
/* 193:220 */     if (this.nahcConfig.getSATechnique() == null)
/* 194:    */     {
/* 195:221 */       this.SATechniqueCB.setEnabled(false);
/* 196:    */     }
/* 197:    */     else
/* 198:    */     {
/* 199:224 */       this.SAEnable.setSelected(true);
/* 200:225 */       this.SATechniqueCB.setEnabled(true);
/* 201:226 */       this.ConfigurePB.setEnabled(true);
/* 202:227 */       this.saMethod = this.nahcConfig.getSATechnique();
/* 203:228 */       this.descriptionST.setText(this.saMethod.getObjectDescription());
/* 204:229 */       this.SATechniqueCB.setSelectedItem(this.saMethod.getWellKnownName());
/* 205:    */     }
/* 206:232 */     this.sliderAdjust.setValue(this.nahcConfig.getMinPctToConsider());
/* 207:233 */     int pct = this.sliderAdjust.getValue();
/* 208:234 */     Integer val = new Integer(this.nahcConfig.getRandomizePct());
/* 209:235 */     this.randomizePctEF.setText(val.toString());
/* 210:    */     
/* 211:237 */     super.jbInit();
/* 212:    */   }
/* 213:    */   
/* 214:    */   protected Configuration createConfiguration()
/* 215:    */   {
/* 216:247 */     this.configuration_d.setNumOfIterations(1);
/* 217:248 */     this.configuration_d.setPopulationSize(Integer.parseInt(this.populationSzEF.getText()));
/* 218:249 */     ((HillClimbingConfiguration)this.configuration_d).setThreshold(1.0D);
/* 219:251 */     if (this.SAEnable.isSelected())
/* 220:    */     {
/* 221:253 */       ((NAHCConfiguration)this.configuration_d).setSATechnique(this.saMethod);
/* 222:254 */       Hashtable h = this.saMethod.getConfig();
/* 223:255 */       this.saMethod.setConfig(h);
/* 224:    */     }
/* 225:    */     else
/* 226:    */     {
/* 227:258 */       ((NAHCConfiguration)this.configuration_d).setSATechnique(null);
/* 228:    */     }
/* 229:260 */     ((NAHCConfiguration)this.configuration_d).setMinPctToConsider(this.sliderAdjust.getValue());
/* 230:    */     
/* 231:262 */     int pctVal = Integer.parseInt(this.randomizePctEF.getText());
/* 232:263 */     ((NAHCConfiguration)this.configuration_d).setRandomizePct(pctVal);
/* 233:264 */     return this.configuration_d;
/* 234:    */   }
/* 235:    */   
/* 236:    */   void SAEnable_actionPerformed(ActionEvent e)
/* 237:    */   {
/* 238:268 */     boolean state = this.SAEnable.isSelected();
/* 239:269 */     if (state == true)
/* 240:    */     {
/* 241:271 */       this.ConfigurePB.setEnabled(true);
/* 242:272 */       this.SATechniqueCB.setEnabled(true);
/* 243:    */     }
/* 244:    */     else
/* 245:    */     {
/* 246:276 */       this.ConfigurePB.setEnabled(false);
/* 247:277 */       this.SATechniqueCB.setEnabled(false);
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   void ConfigurePB_actionPerformed(ActionEvent e)
/* 252:    */   {
/* 253:282 */     String technique = (String)this.SATechniqueCB.getSelectedItem();
/* 254:284 */     if ((this.saMethod == null) || (!this.saMethod.getWellKnownName().equals(technique)))
/* 255:    */     {
/* 256:286 */       this.saMethod = ((SATechnique)this.safactory.getItemInstance(technique));
/* 257:287 */       this.descriptionST.setText(this.saMethod.getObjectDescription());
/* 258:    */     }
/* 259:289 */     if (getParenetFrame() == null) {
/* 260:290 */       return;
/* 261:    */     }
/* 262:291 */     this.saMethod.configureUsingDialog(getParenetFrame());
/* 263:    */   }
/* 264:    */   
/* 265:    */   void SATechniqueCB_actionPerformed(ActionEvent e) {}
/* 266:    */   
/* 267:    */   void sliderAdjust_caretPositionChanged(InputMethodEvent e)
/* 268:    */   {
/* 269:303 */     int pct = this.sliderAdjust.getValue();
/* 270:304 */     this.pctToConsiderST.setText(pct + "%");
/* 271:    */   }
/* 272:    */   
/* 273:    */   void sliderAdjust_stateChanged(ChangeEvent e)
/* 274:    */   {
/* 275:309 */     int pct = this.sliderAdjust.getValue();
/* 276:    */     
/* 277:    */ 
/* 278:    */ 
/* 279:    */ 
/* 280:    */ 
/* 281:    */ 
/* 282:    */ 
/* 283:    */ 
/* 284:    */ 
/* 285:319 */     this.pctToConsiderST.setText(pct + "%");
/* 286:320 */     Integer val = new Integer(100 - pct);
/* 287:321 */     this.randomizePctEF.setText(val.toString());
/* 288:    */   }
/* 289:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.NAHCClusteringConfigurationDialog
 * JD-Core Version:    0.7.0.1
 */