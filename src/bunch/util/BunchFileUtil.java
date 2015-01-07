/*   1:    */ package bunch.util;
/*   2:    */ 
/*   3:    */ import bunch.ObjectiveFunctionCalculatorFactory;
/*   4:    */ import bunch.gxl.proxy.IMDGtoGXL;
/*   5:    */ import java.awt.BorderLayout;
/*   6:    */ import java.awt.Color;
/*   7:    */ import java.awt.Container;
/*   8:    */ import java.awt.FileDialog;
/*   9:    */ import java.awt.Font;
/*  10:    */ import java.awt.Frame;
/*  11:    */ import java.awt.GridBagConstraints;
/*  12:    */ import java.awt.GridBagLayout;
/*  13:    */ import java.awt.Insets;
/*  14:    */ import java.awt.event.ActionEvent;
/*  15:    */ import java.awt.event.ActionListener;
/*  16:    */ import java.awt.event.FocusAdapter;
/*  17:    */ import java.awt.event.FocusEvent;
/*  18:    */ import java.io.File;
/*  19:    */ import java.net.URL;
/*  20:    */ import java.net.URLClassLoader;
/*  21:    */ import javax.swing.JButton;
/*  22:    */ import javax.swing.JCheckBox;
/*  23:    */ import javax.swing.JDialog;
/*  24:    */ import javax.swing.JFileChooser;
/*  25:    */ import javax.swing.JLabel;
/*  26:    */ import javax.swing.JPanel;
/*  27:    */ import javax.swing.JTabbedPane;
/*  28:    */ import javax.swing.JTextField;
/*  29:    */ 
/*  30:    */ public class BunchFileUtil
/*  31:    */   extends JDialog
/*  32:    */ {
/*  33: 26 */   public static String convClassName = "bunch.gxl.converter.MDGtoGXL";
/*  34: 27 */   public static String convClassJarName = "BunchGXL.jar";
/*  35: 29 */   JPanel panel1 = new JPanel();
/*  36: 30 */   BorderLayout borderLayout1 = new BorderLayout();
/*  37: 31 */   JPanel jPanel1 = new JPanel();
/*  38: 32 */   JButton DonePB = new JButton();
/*  39: 33 */   JTabbedPane jTabbedPane1 = new JTabbedPane();
/*  40: 34 */   JPanel MDGtoGXL = new JPanel();
/*  41: 35 */   GridBagLayout gridBagLayout1 = new GridBagLayout();
/*  42: 36 */   JLabel jLabel1 = new JLabel();
/*  43: 37 */   JTextField mdgFileNameEF = new JTextField();
/*  44: 38 */   JButton mdgSelectPB = new JButton();
/*  45: 39 */   JLabel jLabel2 = new JLabel();
/*  46: 40 */   JTextField gxlFileNameEF = new JTextField();
/*  47: 41 */   JButton gxlFileSelectPB = new JButton();
/*  48:    */   FileDialog fd;
/*  49:    */   JFileChooser fileChooser;
/*  50: 45 */   JPanel jPanel2 = new JPanel();
/*  51: 46 */   JButton ConvertPB = new JButton();
/*  52: 47 */   JLabel gxlDTDPathLB = new JLabel();
/*  53: 48 */   JTextField gxlDTDPathEF = new JTextField();
/*  54: 49 */   JButton gxlDTDSelectPB = new JButton();
/*  55: 50 */   JLabel messageST = new JLabel();
/*  56:    */   ObjectiveFunctionCalculatorFactory of;
/*  57: 52 */   JCheckBox embedDTDCB = new JCheckBox();
/*  58: 54 */   String convJarName = convClassJarName;
/*  59: 55 */   JCheckBox LoadFromClassPathCB = new JCheckBox();
/*  60: 56 */   JLabel JarFilePathST = new JLabel();
/*  61: 57 */   JTextField JarFilePathEF = new JTextField();
/*  62: 58 */   JButton BunchGXLJarPB = new JButton();
/*  63:    */   
/*  64:    */   public BunchFileUtil(Frame frame, String title, boolean modal)
/*  65:    */   {
/*  66: 61 */     super(frame, title, modal);
/*  67:    */     try
/*  68:    */     {
/*  69: 63 */       jbInit();
/*  70: 64 */       pack();
/*  71:    */     }
/*  72:    */     catch (Exception ex)
/*  73:    */     {
/*  74: 67 */       ex.printStackTrace();
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public BunchFileUtil()
/*  79:    */   {
/*  80: 72 */     this(null, "", false);
/*  81:    */   }
/*  82:    */   
/*  83:    */   void jbInit()
/*  84:    */     throws Exception
/*  85:    */   {
/*  86: 75 */     this.panel1.setLayout(this.borderLayout1);
/*  87: 76 */     this.DonePB.setText("Close");
/*  88: 77 */     this.DonePB.addActionListener(new ActionListener()
/*  89:    */     {
/*  90:    */       public void actionPerformed(ActionEvent e)
/*  91:    */       {
/*  92: 79 */         BunchFileUtil.this.DonePB_actionPerformed(e);
/*  93:    */       }
/*  94: 81 */     });
/*  95: 82 */     this.MDGtoGXL.setLayout(this.gridBagLayout1);
/*  96: 83 */     this.jLabel1.setText("MDG File Name:");
/*  97: 84 */     this.mdgSelectPB.setText("Select...");
/*  98: 85 */     this.mdgSelectPB.addActionListener(new ActionListener()
/*  99:    */     {
/* 100:    */       public void actionPerformed(ActionEvent e)
/* 101:    */       {
/* 102: 87 */         BunchFileUtil.this.mdgSelectPB_actionPerformed(e);
/* 103:    */       }
/* 104: 89 */     });
/* 105: 90 */     this.jLabel2.setText("GXL File Name:");
/* 106: 91 */     this.gxlFileSelectPB.setToolTipText("");
/* 107: 92 */     this.gxlFileSelectPB.setText("Select...");
/* 108: 93 */     this.gxlFileSelectPB.addActionListener(new ActionListener()
/* 109:    */     {
/* 110:    */       public void actionPerformed(ActionEvent e)
/* 111:    */       {
/* 112: 95 */         BunchFileUtil.this.gxlFileSelectPB_actionPerformed(e);
/* 113:    */       }
/* 114: 97 */     });
/* 115: 98 */     this.ConvertPB.setText("Convert...");
/* 116: 99 */     this.ConvertPB.addActionListener(new ActionListener()
/* 117:    */     {
/* 118:    */       public void actionPerformed(ActionEvent e)
/* 119:    */       {
/* 120:101 */         BunchFileUtil.this.ConvertPB_actionPerformed(e);
/* 121:    */       }
/* 122:103 */     });
/* 123:104 */     this.gxlDTDPathLB.setEnabled(false);
/* 124:105 */     this.gxlDTDPathLB.setText("GXL DTD Path:");
/* 125:106 */     this.gxlDTDSelectPB.setEnabled(false);
/* 126:107 */     this.gxlDTDSelectPB.setText("Select...");
/* 127:108 */     this.gxlDTDSelectPB.addActionListener(new ActionListener()
/* 128:    */     {
/* 129:    */       public void actionPerformed(ActionEvent e)
/* 130:    */       {
/* 131:110 */         BunchFileUtil.this.gxlDTDSelectPB_actionPerformed(e);
/* 132:    */       }
/* 133:112 */     });
/* 134:113 */     this.messageST.setFont(new Font("Dialog", 1, 12));
/* 135:114 */     this.messageST.setForeground(Color.red);
/* 136:115 */     this.messageST.setText("Provide the required ifnromation and press Convert...");
/* 137:116 */     this.embedDTDCB.setSelected(true);
/* 138:117 */     this.embedDTDCB.setText("Embed the DTD file in generated GXL file");
/* 139:118 */     this.embedDTDCB.addActionListener(new ActionListener()
/* 140:    */     {
/* 141:    */       public void actionPerformed(ActionEvent e)
/* 142:    */       {
/* 143:120 */         BunchFileUtil.this.embedDTDCB_actionPerformed(e);
/* 144:    */       }
/* 145:122 */     });
/* 146:123 */     this.gxlDTDPathEF.setEnabled(false);
/* 147:124 */     this.gxlDTDPathEF.setText("GXL.dtd");
/* 148:125 */     this.mdgFileNameEF.addFocusListener(new FocusAdapter()
/* 149:    */     {
/* 150:    */       public void focusLost(FocusEvent e)
/* 151:    */       {
/* 152:127 */         BunchFileUtil.this.mdgFileNameEF_focusLost(e);
/* 153:    */       }
/* 154:129 */     });
/* 155:130 */     this.mdgFileNameEF.addActionListener(new ActionListener()
/* 156:    */     {
/* 157:    */       public void actionPerformed(ActionEvent e)
/* 158:    */       {
/* 159:132 */         BunchFileUtil.this.mdgFileNameEF_actionPerformed(e);
/* 160:    */       }
/* 161:134 */     });
/* 162:135 */     this.LoadFromClassPathCB.setSelected(true);
/* 163:136 */     this.LoadFromClassPathCB.setText("Load Converter Class From CLASSPATH");
/* 164:137 */     this.LoadFromClassPathCB.addActionListener(new ActionListener()
/* 165:    */     {
/* 166:    */       public void actionPerformed(ActionEvent e)
/* 167:    */       {
/* 168:139 */         BunchFileUtil.this.LoadFromClassPathCB_actionPerformed(e);
/* 169:    */       }
/* 170:141 */     });
/* 171:142 */     this.JarFilePathST.setEnabled(false);
/* 172:143 */     this.JarFilePathST.setText("BunchGXL Jar File:");
/* 173:144 */     this.JarFilePathEF.setEnabled(false);
/* 174:145 */     this.JarFilePathEF.setText("BunchGXL.jar");
/* 175:146 */     this.BunchGXLJarPB.setEnabled(false);
/* 176:147 */     this.BunchGXLJarPB.setText("Select...");
/* 177:148 */     this.BunchGXLJarPB.addActionListener(new ActionListener()
/* 178:    */     {
/* 179:    */       public void actionPerformed(ActionEvent e)
/* 180:    */       {
/* 181:150 */         BunchFileUtil.this.BunchGXLJarPB_actionPerformed(e);
/* 182:    */       }
/* 183:152 */     });
/* 184:153 */     getContentPane().add(this.panel1);
/* 185:154 */     this.panel1.add(this.jPanel1, "South");
/* 186:155 */     this.jPanel1.add(this.DonePB, null);
/* 187:156 */     this.panel1.add(this.jTabbedPane1, "Center");
/* 188:157 */     this.jTabbedPane1.add(this.MDGtoGXL, "MDG To GXL Converter");
/* 189:158 */     this.MDGtoGXL.add(this.jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(20, 10, 0, 5), 0, 0));
/* 190:    */     
/* 191:160 */     this.MDGtoGXL.add(this.mdgFileNameEF, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(20, 0, 0, 0), 161, 0));
/* 192:    */     
/* 193:162 */     this.MDGtoGXL.add(this.mdgSelectPB, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 17, 2, new Insets(20, 5, 0, 10), 0, 0));
/* 194:    */     
/* 195:164 */     this.MDGtoGXL.add(this.jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 10, 0, 0), 0, 0));
/* 196:    */     
/* 197:166 */     this.MDGtoGXL.add(this.gxlFileNameEF, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 44, 0));
/* 198:    */     
/* 199:168 */     this.MDGtoGXL.add(this.gxlFileSelectPB, new GridBagConstraints(2, 1, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 5, 0, 10), 0, 0));
/* 200:    */     
/* 201:170 */     this.MDGtoGXL.add(this.jPanel2, new GridBagConstraints(0, 7, 3, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
/* 202:    */     
/* 203:172 */     this.jPanel2.add(this.ConvertPB, null);
/* 204:173 */     this.MDGtoGXL.add(this.gxlDTDPathLB, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 20, 0, 0), 0, 0));
/* 205:    */     
/* 206:175 */     this.MDGtoGXL.add(this.gxlDTDPathEF, new GridBagConstraints(1, 3, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 0, 0));
/* 207:    */     
/* 208:177 */     this.MDGtoGXL.add(this.gxlDTDSelectPB, new GridBagConstraints(2, 3, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 5, 0, 10), 0, 0));
/* 209:    */     
/* 210:179 */     this.MDGtoGXL.add(this.messageST, new GridBagConstraints(0, 8, 3, 1, 0.0D, 0.0D, 10, 0, new Insets(10, 0, 10, 0), 0, 0));
/* 211:    */     
/* 212:181 */     this.MDGtoGXL.add(this.embedDTDCB, new GridBagConstraints(0, 2, 2, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 10, 0, 0), 0, 0));
/* 213:    */     
/* 214:183 */     this.MDGtoGXL.add(this.LoadFromClassPathCB, new GridBagConstraints(0, 5, 3, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 10, 0, 0), 0, 0));
/* 215:    */     
/* 216:185 */     this.MDGtoGXL.add(this.JarFilePathST, new GridBagConstraints(0, 6, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 20, 0, 5), 0, 0));
/* 217:    */     
/* 218:187 */     this.MDGtoGXL.add(this.JarFilePathEF, new GridBagConstraints(1, 6, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
/* 219:    */     
/* 220:189 */     this.MDGtoGXL.add(this.BunchGXLJarPB, new GridBagConstraints(2, 6, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 221:    */     
/* 222:    */ 
/* 223:192 */     this.fileChooser = new JFileChooser();
/* 224:    */   }
/* 225:    */   
/* 226:    */   void DonePB_actionPerformed(ActionEvent e)
/* 227:    */   {
/* 228:197 */     dispose();
/* 229:    */   }
/* 230:    */   
/* 231:    */   void mdgSelectPB_actionPerformed(ActionEvent e)
/* 232:    */   {
/* 233:201 */     int returnVal = this.fileChooser.showOpenDialog(this);
/* 234:202 */     if (returnVal == 0) {
/* 235:203 */       this.mdgFileNameEF.setText(this.fileChooser.getSelectedFile().getAbsolutePath());
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   void gxlFileSelectPB_actionPerformed(ActionEvent e)
/* 240:    */   {
/* 241:207 */     int returnVal = this.fileChooser.showSaveDialog(this);
/* 242:208 */     if (returnVal == 0) {
/* 243:209 */       this.gxlFileNameEF.setText(this.fileChooser.getSelectedFile().getAbsolutePath());
/* 244:    */     }
/* 245:    */   }
/* 246:    */   
/* 247:    */   void ConvertPB_actionPerformed(ActionEvent e)
/* 248:    */   {
/* 249:213 */     String mdg = this.mdgFileNameEF.getText();
/* 250:214 */     String gxl = this.gxlFileNameEF.getText();
/* 251:215 */     boolean embedDTD = this.embedDTDCB.isSelected();
/* 252:216 */     String gxlPath = this.gxlDTDPathEF.getText();
/* 253:217 */     String jarPath = this.JarFilePathEF.getText();
/* 254:    */     
/* 255:219 */     this.messageST.setText("Provide the required ifnromation and press Convert...");
/* 256:221 */     if (!verifyFileName(mdg))
/* 257:    */     {
/* 258:223 */       this.messageST.setText("MDG File Name/Location is INVALID!");
/* 259:224 */       return;
/* 260:    */     }
/* 261:227 */     if (!verifyFilePath(gxl))
/* 262:    */     {
/* 263:229 */       this.messageST.setText("GXL Output File Name/Location is INVALID!");
/* 264:230 */       return;
/* 265:    */     }
/* 266:233 */     if (!this.LoadFromClassPathCB.isSelected()) {
/* 267:235 */       if (!verifyFileName(jarPath))
/* 268:    */       {
/* 269:237 */         this.messageST.setText("BunchGXL Jar File Name/Location is INVALID!");
/* 270:238 */         return;
/* 271:    */       }
/* 272:    */     }
/* 273:242 */     Object oConv = getGXLHelperClass();
/* 274:243 */     if (oConv == null)
/* 275:    */     {
/* 276:245 */       this.messageST.setText("BunchGXL Converter Class COULD NOT be loaded!");
/* 277:246 */       return;
/* 278:    */     }
/* 279:    */     try
/* 280:    */     {
/* 281:251 */       IMDGtoGXL converter = (IMDGtoGXL)oConv;
/* 282:253 */       if (embedDTD == true) {
/* 283:254 */         converter.setOptions(mdg, gxl, true);
/* 284:    */       } else {
/* 285:256 */         converter.setOptions(mdg, gxl, gxlPath, false);
/* 286:    */       }
/* 287:258 */       converter.convert();
/* 288:259 */       this.messageST.setText("Converstion finished successfully!");
/* 289:    */     }
/* 290:    */     catch (Exception ex)
/* 291:    */     {
/* 292:263 */       ex.printStackTrace();
/* 293:    */     }
/* 294:    */   }
/* 295:    */   
/* 296:    */   void gxlDTDSelectPB_actionPerformed(ActionEvent e)
/* 297:    */   {
/* 298:268 */     int returnVal = this.fileChooser.showOpenDialog(this);
/* 299:269 */     if (returnVal == 0) {
/* 300:270 */       this.gxlDTDPathEF.setText(this.fileChooser.getSelectedFile().getAbsolutePath());
/* 301:    */     }
/* 302:    */   }
/* 303:    */   
/* 304:    */   void embedDTDCB_actionPerformed(ActionEvent e)
/* 305:    */   {
/* 306:274 */     boolean state = this.embedDTDCB.isSelected();
/* 307:275 */     if (state == true)
/* 308:    */     {
/* 309:277 */       this.gxlDTDPathLB.setEnabled(false);
/* 310:278 */       this.gxlDTDPathEF.setEnabled(false);
/* 311:279 */       this.gxlDTDSelectPB.setEnabled(false);
/* 312:    */     }
/* 313:    */     else
/* 314:    */     {
/* 315:283 */       this.gxlDTDPathLB.setEnabled(true);
/* 316:284 */       this.gxlDTDPathEF.setEnabled(true);
/* 317:285 */       this.gxlDTDSelectPB.setEnabled(true);
/* 318:    */     }
/* 319:    */   }
/* 320:    */   
/* 321:    */   void mdgFileNameEF_focusLost(FocusEvent e)
/* 322:    */   {
/* 323:290 */     String val = this.mdgFileNameEF.getText();
/* 324:291 */     this.gxlFileNameEF.setText(val + ".gxl");
/* 325:    */   }
/* 326:    */   
/* 327:    */   private boolean verifyFilePath(String fn)
/* 328:    */   {
/* 329:    */     try
/* 330:    */     {
/* 331:297 */       File f = new File(fn);
/* 332:298 */       boolean state = f.createNewFile();
/* 333:301 */       if (state == true) {
/* 334:301 */         f.delete();
/* 335:    */       }
/* 336:302 */       return true;
/* 337:    */     }
/* 338:    */     catch (Exception e) {}
/* 339:305 */     return false;
/* 340:    */   }
/* 341:    */   
/* 342:    */   private boolean verifyFileName(String fn)
/* 343:    */   {
/* 344:    */     try
/* 345:    */     {
/* 346:311 */       File f = new File(fn);
/* 347:312 */       return f.exists();
/* 348:    */     }
/* 349:    */     catch (Exception e) {}
/* 350:315 */     return false;
/* 351:    */   }
/* 352:    */   
/* 353:    */   private Object getGXLHelperClass()
/* 354:    */   {
/* 355:    */     try
/* 356:    */     {
/* 357:323 */       Class c = null;
/* 358:325 */       if (this.LoadFromClassPathCB.isSelected())
/* 359:    */       {
/* 360:327 */         ClassLoader loader = getClass().getClassLoader();
/* 361:328 */         c = loader.loadClass(convClassName);
/* 362:    */       }
/* 363:    */       else
/* 364:    */       {
/* 365:332 */         String jarName = this.JarFilePathEF.getText();
/* 366:333 */         URL[] urlList = { new File(jarName).toURL() };
/* 367:334 */         ClassLoader loader = new URLClassLoader(urlList);
/* 368:335 */         c = loader.loadClass(convClassName);
/* 369:    */       }
/* 370:337 */       return c.newInstance();
/* 371:    */     }
/* 372:    */     catch (Exception e)
/* 373:    */     {
/* 374:341 */       e.printStackTrace();
/* 375:    */     }
/* 376:342 */     return null;
/* 377:    */   }
/* 378:    */   
/* 379:    */   void mdgFileNameEF_actionPerformed(ActionEvent e) {}
/* 380:    */   
/* 381:    */   void BunchGXLJarPB_actionPerformed(ActionEvent e)
/* 382:    */   {
/* 383:350 */     int returnVal = this.fileChooser.showOpenDialog(this);
/* 384:351 */     if (returnVal == 0) {
/* 385:352 */       this.JarFilePathEF.setText(this.fileChooser.getSelectedFile().getAbsolutePath());
/* 386:    */     }
/* 387:    */   }
/* 388:    */   
/* 389:    */   void LoadFromClassPathCB_actionPerformed(ActionEvent e)
/* 390:    */   {
/* 391:356 */     if (this.LoadFromClassPathCB.isSelected())
/* 392:    */     {
/* 393:358 */       this.JarFilePathST.setEnabled(false);
/* 394:359 */       this.JarFilePathEF.setEnabled(false);
/* 395:360 */       this.BunchGXLJarPB.setEnabled(false);
/* 396:    */     }
/* 397:    */     else
/* 398:    */     {
/* 399:364 */       this.JarFilePathST.setEnabled(true);
/* 400:365 */       this.JarFilePathEF.setEnabled(true);
/* 401:366 */       this.BunchGXLJarPB.setEnabled(true);
/* 402:    */     }
/* 403:    */   }
/* 404:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.util.BunchFileUtil
 * JD-Core Version:    0.7.0.1
 */