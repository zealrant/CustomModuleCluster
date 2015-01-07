/*   1:    */ package bunch.BunchServer;
/*   2:    */ 
/*   3:    */ import bunch.util.BunchUtilities;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.Font;
/*   8:    */ import java.awt.GridBagConstraints;
/*   9:    */ import java.awt.GridBagLayout;
/*  10:    */ import java.awt.Insets;
/*  11:    */ import java.awt.Point;
/*  12:    */ import java.awt.event.ActionEvent;
/*  13:    */ import java.awt.event.ActionListener;
/*  14:    */ import java.awt.event.WindowEvent;
/*  15:    */ import java.util.Properties;
/*  16:    */ import javax.naming.CompositeName;
/*  17:    */ import javax.naming.InitialContext;
/*  18:    */ import javax.swing.JButton;
/*  19:    */ import javax.swing.JFrame;
/*  20:    */ import javax.swing.JLabel;
/*  21:    */ import javax.swing.JMenu;
/*  22:    */ import javax.swing.JMenuBar;
/*  23:    */ import javax.swing.JMenuItem;
/*  24:    */ import javax.swing.JScrollPane;
/*  25:    */ import javax.swing.JTextArea;
/*  26:    */ import javax.swing.JTextField;
/*  27:    */ import javax.swing.JViewport;
/*  28:    */ 
/*  29:    */ public class BSWindow
/*  30:    */   extends JFrame
/*  31:    */ {
/*  32: 34 */   JMenuBar menuBar1 = new JMenuBar();
/*  33: 35 */   JMenu menuFile = new JMenu();
/*  34: 36 */   JMenuItem menuFileExit = new JMenuItem();
/*  35: 37 */   JMenu menuHelp = new JMenu();
/*  36: 38 */   JMenuItem menuHelpAbout = new JMenuItem();
/*  37: 39 */   GridBagLayout gridBagLayout1 = new GridBagLayout();
/*  38: 40 */   JLabel jLabel1 = new JLabel();
/*  39: 41 */   JTextField nsName = new JTextField();
/*  40: 42 */   JLabel jLabel2 = new JLabel();
/*  41: 43 */   JTextField svrName = new JTextField();
/*  42: 44 */   JButton startPB = new JButton();
/*  43: 45 */   JButton stopPB = new JButton();
/*  44: 46 */   JLabel jLabel3 = new JLabel();
/*  45: 47 */   JLabel jLabel4 = new JLabel();
/*  46: 48 */   JTextField nameServerEF = new JTextField();
/*  47: 49 */   JTextField portEF = new JTextField();
/*  48: 50 */   JLabel jLabel5 = new JLabel();
/*  49: 51 */   JScrollPane jScrollPane1 = new JScrollPane();
/*  50: 52 */   JTextArea logText = new JTextArea();
/*  51: 53 */   JButton clearLogPB = new JButton();
/*  52: 54 */   JLabel jLabel6 = new JLabel();
/*  53: 55 */   JLabel msgTxt = new JLabel();
/*  54:    */   BunchSvrMsgImpl bunchMsg;
/*  55: 61 */   InitialContext corbaContext = null;
/*  56:    */   
/*  57:    */   public BSWindow()
/*  58:    */   {
/*  59: 65 */     enableEvents(64L);
/*  60:    */     try
/*  61:    */     {
/*  62: 67 */       jbInit();
/*  63:    */     }
/*  64:    */     catch (Exception e)
/*  65:    */     {
/*  66: 70 */       e.printStackTrace();
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   private void jbInit()
/*  71:    */     throws Exception
/*  72:    */   {
/*  73: 76 */     getContentPane().setLayout(this.gridBagLayout1);
/*  74: 77 */     setSize(new Dimension(428, 385));
/*  75: 78 */     setTitle("Bunch Server - v1.0B");
/*  76: 79 */     this.menuFile.setText("File");
/*  77: 80 */     this.menuFileExit.setText("Exit");
/*  78: 81 */     this.menuFileExit.addActionListener(new ActionListener()
/*  79:    */     {
/*  80:    */       public void actionPerformed(ActionEvent e)
/*  81:    */       {
/*  82: 84 */         BSWindow.this.fileExit_actionPerformed(e);
/*  83:    */       }
/*  84: 86 */     });
/*  85: 87 */     this.menuHelp.setText("Help");
/*  86: 88 */     this.menuHelpAbout.setText("About");
/*  87: 89 */     this.menuHelpAbout.addActionListener(new ActionListener()
/*  88:    */     {
/*  89:    */       public void actionPerformed(ActionEvent e)
/*  90:    */       {
/*  91: 92 */         BSWindow.this.helpAbout_actionPerformed(e);
/*  92:    */       }
/*  93: 94 */     });
/*  94: 95 */     this.jLabel1.setText("Namespace:");
/*  95: 96 */     this.nsName.setMaximumSize(new Dimension(80, 21));
/*  96: 97 */     this.nsName.setMinimumSize(new Dimension(80, 21));
/*  97: 98 */     this.nsName.setPreferredSize(new Dimension(80, 21));
/*  98: 99 */     this.nsName.setText("BunchServer");
/*  99:100 */     this.jLabel2.setText("Server Name:");
/* 100:101 */     this.svrName.setMaximumSize(new Dimension(80, 21));
/* 101:102 */     this.svrName.setMinimumSize(new Dimension(80, 21));
/* 102:103 */     this.svrName.setPreferredSize(new Dimension(80, 21));
/* 103:104 */     this.svrName.setText("bServer1");
/* 104:105 */     this.startPB.setText("Start");
/* 105:106 */     this.startPB.addActionListener(new ActionListener()
/* 106:    */     {
/* 107:    */       public void actionPerformed(ActionEvent e)
/* 108:    */       {
/* 109:109 */         BSWindow.this.startPB_actionPerformed(e);
/* 110:    */       }
/* 111:111 */     });
/* 112:112 */     this.stopPB.setEnabled(false);
/* 113:113 */     this.stopPB.setText("Stop");
/* 114:114 */     this.stopPB.addActionListener(new ActionListener()
/* 115:    */     {
/* 116:    */       public void actionPerformed(ActionEvent e)
/* 117:    */       {
/* 118:117 */         BSWindow.this.stopPB_actionPerformed(e);
/* 119:    */       }
/* 120:119 */     });
/* 121:120 */     this.jLabel3.setText("Name Server:");
/* 122:121 */     this.jLabel4.setText("Port");
/* 123:122 */     this.portEF.setMaximumSize(new Dimension(80, 21));
/* 124:123 */     this.portEF.setMinimumSize(new Dimension(80, 21));
/* 125:124 */     this.portEF.setPreferredSize(new Dimension(80, 21));
/* 126:125 */     this.portEF.setText("900");
/* 127:126 */     this.portEF.addActionListener(new ActionListener()
/* 128:    */     {
/* 129:    */       public void actionPerformed(ActionEvent e)
/* 130:    */       {
/* 131:129 */         BSWindow.this.portEF_actionPerformed(e);
/* 132:    */       }
/* 133:131 */     });
/* 134:132 */     this.nameServerEF.addActionListener(new ActionListener()
/* 135:    */     {
/* 136:    */       public void actionPerformed(ActionEvent e)
/* 137:    */       {
/* 138:135 */         BSWindow.this.nameServerEF_actionPerformed(e);
/* 139:    */       }
/* 140:137 */     });
/* 141:138 */     this.nameServerEF.setMaximumSize(new Dimension(80, 21));
/* 142:139 */     this.nameServerEF.setMinimumSize(new Dimension(80, 21));
/* 143:140 */     this.nameServerEF.setPreferredSize(new Dimension(80, 21));
/* 144:141 */     this.nameServerEF.setText("localhost");
/* 145:142 */     this.jLabel5.setText("Log Messages:");
/* 146:143 */     this.clearLogPB.setText("Clear Log");
/* 147:144 */     this.clearLogPB.addActionListener(new ActionListener()
/* 148:    */     {
/* 149:    */       public void actionPerformed(ActionEvent e)
/* 150:    */       {
/* 151:147 */         BSWindow.this.clearLogPB_actionPerformed(e);
/* 152:    */       }
/* 153:149 */     });
/* 154:150 */     this.jLabel6.setText("Message:");
/* 155:151 */     this.msgTxt.setFont(new Font("Dialog", 1, 12));
/* 156:152 */     this.msgTxt.setForeground(Color.red);
/* 157:153 */     this.msgTxt.setText("Server not running...");
/* 158:154 */     this.menuFile.add(this.menuFileExit);
/* 159:155 */     this.menuHelp.add(this.menuHelpAbout);
/* 160:156 */     this.menuBar1.add(this.menuFile);
/* 161:157 */     this.menuBar1.add(this.menuHelp);
/* 162:158 */     setJMenuBar(this.menuBar1);
/* 163:    */     
/* 164:    */ 
/* 165:    */ 
/* 166:162 */     this.svrName.setText(BunchUtilities.getLocalHostName());
/* 167:    */     
/* 168:    */ 
/* 169:165 */     getContentPane().add(this.nsName, new GridBagConstraints(1, 0, 4, 1, 0.0D, 0.0D, 17, 1, new Insets(0, 0, 0, 0), 238, 0));
/* 170:    */     
/* 171:167 */     getContentPane().add(this.jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(0, 0, 0, 0), 10, 0));
/* 172:    */     
/* 173:169 */     getContentPane().add(this.svrName, new GridBagConstraints(1, 1, 3, 1, 0.0D, 0.0D, 17, 1, new Insets(0, 0, 0, 0), 238, 0));
/* 174:    */     
/* 175:171 */     getContentPane().add(this.jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 176:    */     
/* 177:173 */     getContentPane().add(this.stopPB, new GridBagConstraints(1, 4, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(9, -20, 0, 0), 0, -5));
/* 178:    */     
/* 179:175 */     getContentPane().add(this.startPB, new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(0, 0, 0, 0), 0, -6));
/* 180:    */     
/* 181:177 */     getContentPane().add(this.jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 182:    */     
/* 183:179 */     getContentPane().add(this.jLabel4, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 184:    */     
/* 185:181 */     getContentPane().add(this.nameServerEF, new GridBagConstraints(1, 2, 2, 1, 0.0D, 0.0D, 17, 1, new Insets(0, 0, 0, 0), 238, 0));
/* 186:    */     
/* 187:183 */     getContentPane().add(this.portEF, new GridBagConstraints(1, 3, 1, 1, 0.0D, 0.0D, 17, 1, new Insets(0, 0, 0, 0), 238, 0));
/* 188:    */     
/* 189:185 */     getContentPane().add(this.jLabel5, new GridBagConstraints(0, 5, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(10, 0, 0, 0), 0, 0));
/* 190:    */     
/* 191:187 */     getContentPane().add(this.jScrollPane1, new GridBagConstraints(0, 6, 2, 1, 0.0D, 0.0D, 17, 1, new Insets(4, 0, 0, 0), 297, 90));
/* 192:    */     
/* 193:189 */     getContentPane().add(this.clearLogPB, new GridBagConstraints(0, 7, 2, 1, 0.0D, 0.0D, 10, 0, new Insets(6, 0, 0, 0), 0, -4));
/* 194:    */     
/* 195:191 */     getContentPane().add(this.jLabel6, new GridBagConstraints(0, 8, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(10, 0, 0, 0), 0, 0));
/* 196:    */     
/* 197:193 */     getContentPane().add(this.msgTxt, new GridBagConstraints(1, 8, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 198:    */     
/* 199:195 */     this.jScrollPane1.getViewport().add(this.logText, null);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void fileExit_actionPerformed(ActionEvent e)
/* 203:    */   {
/* 204:200 */     System.exit(0);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void helpAbout_actionPerformed(ActionEvent e)
/* 208:    */   {
/* 209:205 */     BSWindow_AboutBox dlg = new BSWindow_AboutBox(this);
/* 210:206 */     Dimension dlgSize = dlg.getPreferredSize();
/* 211:207 */     Dimension frmSize = getSize();
/* 212:208 */     Point loc = getLocation();
/* 213:209 */     dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
/* 214:210 */     dlg.setModal(true);
/* 215:211 */     dlg.show();
/* 216:    */   }
/* 217:    */   
/* 218:    */   protected void processWindowEvent(WindowEvent e)
/* 219:    */   {
/* 220:216 */     super.processWindowEvent(e);
/* 221:217 */     if (e.getID() == 201) {
/* 222:218 */       fileExit_actionPerformed(null);
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   void nameServerEF_actionPerformed(ActionEvent e) {}
/* 227:    */   
/* 228:    */   void portEF_actionPerformed(ActionEvent e) {}
/* 229:    */   
/* 230:    */   public String getJndiName()
/* 231:    */   {
/* 232:232 */     String jndiName = "/" + this.nsName.getText() + "/" + this.svrName.getText();
/* 233:233 */     return jndiName;
/* 234:    */   }
/* 235:    */   
/* 236:    */   void startPB_actionPerformed(ActionEvent e)
/* 237:    */   {
/* 238:237 */     String nameSpace = this.nsName.getText();
/* 239:238 */     String server = this.svrName.getText();
/* 240:239 */     String nameSvr = this.nameServerEF.getText();
/* 241:240 */     String port = this.portEF.getText();
/* 242:    */     try
/* 243:    */     {
/* 244:244 */       Properties env = new Properties();
/* 245:    */       
/* 246:246 */       env.put("java.naming.factory.initial", "com.sun.jndi.cosnaming.CNCtxFactory");
/* 247:    */       
/* 248:248 */       String nsURL = "iiop://" + nameSvr + ":" + port;
/* 249:249 */       newLogMsg("Name Server URL: " + nsURL);
/* 250:250 */       String cnStr = "/" + nameSpace + "/" + server;
/* 251:251 */       appendLogMsg("Object Registration Name: " + cnStr);
/* 252:    */       
/* 253:253 */       env.put("java.naming.provider.url", nsURL);
/* 254:    */       
/* 255:255 */       InitialContext context = new InitialContext(env);
/* 256:    */       try
/* 257:    */       {
/* 258:261 */         context.createSubcontext(nameSpace);
/* 259:    */       }
/* 260:    */       catch (Exception e1) {}
/* 261:265 */       CompositeName cn = new CompositeName(cnStr);
/* 262:    */       
/* 263:267 */       this.bunchMsg = new BunchSvrMsgImpl();
/* 264:268 */       this.bunchMsg.setParent(this);
/* 265:269 */       this.bunchMsg.setJndiName(getJndiName());
/* 266:270 */       this.bunchMsg.setGUIMode();
/* 267:    */       
/* 268:272 */       context.rebind(cn, this.bunchMsg);
/* 269:    */       
/* 270:274 */       this.corbaContext = context;
/* 271:    */       
/* 272:276 */       msgOK("SERVER Started OK!");
/* 273:    */       
/* 274:278 */       this.startPB.setEnabled(false);
/* 275:279 */       this.stopPB.setEnabled(true);
/* 276:    */       
/* 277:281 */       this.nsName.setEnabled(false);
/* 278:282 */       this.svrName.setEnabled(false);
/* 279:283 */       this.nameServerEF.setEnabled(false);
/* 280:284 */       this.portEF.setEnabled(false);
/* 281:    */     }
/* 282:    */     catch (Exception ex)
/* 283:    */     {
/* 284:289 */       msgError("EXCEPTION:  Please see log message area!");
/* 285:290 */       String excp = ex.toString();
/* 286:291 */       appendLogMsg(excp);
/* 287:292 */       ex.printStackTrace();
/* 288:    */     }
/* 289:    */   }
/* 290:    */   
/* 291:    */   void newLogMsg(String smsg)
/* 292:    */   {
/* 293:298 */     this.logText.setText("");
/* 294:299 */     this.logText.setText(smsg);
/* 295:    */   }
/* 296:    */   
/* 297:    */   void appendLogMsg(String smsg)
/* 298:    */   {
/* 299:304 */     String s = this.logText.getText() + "\n" + smsg;
/* 300:305 */     this.logText.setText(s);
/* 301:    */   }
/* 302:    */   
/* 303:    */   void msgOK(String msg)
/* 304:    */   {
/* 305:310 */     this.msgTxt.setForeground(Color.green);
/* 306:311 */     this.msgTxt.setText(msg);
/* 307:    */   }
/* 308:    */   
/* 309:    */   void msgError(String msg)
/* 310:    */   {
/* 311:316 */     this.msgTxt.setForeground(Color.red);
/* 312:317 */     this.msgTxt.setText(msg);
/* 313:    */   }
/* 314:    */   
/* 315:    */   void stopPB_actionPerformed(ActionEvent e)
/* 316:    */   {
/* 317:    */     try
/* 318:    */     {
/* 319:323 */       String nameSpace = this.nsName.getText();
/* 320:    */       try
/* 321:    */       {
/* 322:326 */         this.corbaContext.destroySubcontext(nameSpace);
/* 323:    */       }
/* 324:    */       catch (Exception e1) {}
/* 325:330 */       this.corbaContext.close();
/* 326:331 */       msgOK("SERVER Stopped OK!");
/* 327:332 */       newLogMsg("Server stopped...");
/* 328:333 */       this.startPB.setEnabled(true);
/* 329:334 */       this.stopPB.setEnabled(false);
/* 330:335 */       this.nsName.setEnabled(true);
/* 331:336 */       this.svrName.setEnabled(true);
/* 332:337 */       this.nameServerEF.setEnabled(true);
/* 333:338 */       this.portEF.setEnabled(true);
/* 334:    */     }
/* 335:    */     catch (Exception ex)
/* 336:    */     {
/* 337:342 */       msgError("EXCEPTION:  Please see log message area!");
/* 338:343 */       String excp = ex.toString();
/* 339:344 */       newLogMsg(excp);
/* 340:    */     }
/* 341:    */   }
/* 342:    */   
/* 343:    */   void clearLogPB_actionPerformed(ActionEvent e)
/* 344:    */   {
/* 345:349 */     this.logText.setText("");
/* 346:    */   }
/* 347:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchServer.BSWindow
 * JD-Core Version:    0.7.0.1
 */