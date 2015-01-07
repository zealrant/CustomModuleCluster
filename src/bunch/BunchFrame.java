/*    1:     */ package bunch;
/*    2:     */ 
/*    3:     */ import bunch.BunchServer.BunchSvrMsg;
/*    4:     */ import bunch.BunchServer.DistribInit;
/*    5:     */ import bunch.LoadBalancer.Manager;
/*    6:     */ import bunch.stats.StatsManager;
/*    7:     */ import bunch.util.BunchClusteringUtil;
/*    8:     */ import bunch.util.BunchFileUtil;
/*    9:     */ import bunch.util.BunchUtilities;
/*   10:     */ import bunch.util.MeasurementUtil;
/*   11:     */ import java.awt.Container;
/*   12:     */ import java.awt.Dimension;
/*   13:     */ import java.awt.FileDialog;
/*   14:     */ import java.awt.Font;
/*   15:     */ import java.awt.GridBagConstraints;
/*   16:     */ import java.awt.GridBagLayout;
/*   17:     */ import java.awt.Insets;
/*   18:     */ import java.awt.Point;
/*   19:     */ import java.awt.event.ActionEvent;
/*   20:     */ import java.awt.event.ActionListener;
/*   21:     */ import java.awt.event.ItemEvent;
/*   22:     */ import java.awt.event.MouseEvent;
/*   23:     */ import java.awt.event.WindowEvent;
/*   24:     */ import java.beans.Beans;
/*   25:     */ import java.io.File;
/*   26:     */ import java.io.PrintStream;
/*   27:     */ import java.util.Hashtable;
/*   28:     */ import java.util.Properties;
/*   29:     */ import java.util.Vector;
/*   30:     */ import javax.naming.Binding;
/*   31:     */ import javax.naming.InitialContext;
/*   32:     */ import javax.naming.NamingEnumeration;
/*   33:     */ import javax.swing.DefaultListModel;
/*   34:     */ import javax.swing.JButton;
/*   35:     */ import javax.swing.JCheckBox;
/*   36:     */ import javax.swing.JComboBox;
/*   37:     */ import javax.swing.JFrame;
/*   38:     */ import javax.swing.JLabel;
/*   39:     */ import javax.swing.JList;
/*   40:     */ import javax.swing.JMenu;
/*   41:     */ import javax.swing.JMenuBar;
/*   42:     */ import javax.swing.JMenuItem;
/*   43:     */ import javax.swing.JOptionPane;
/*   44:     */ import javax.swing.JPanel;
/*   45:     */ import javax.swing.JScrollPane;
/*   46:     */ import javax.swing.JTabbedPane;
/*   47:     */ import javax.swing.JTextField;
/*   48:     */ import javax.swing.JViewport;
/*   49:     */ import javax.swing.event.ChangeEvent;
/*   50:     */ import javax.swing.event.ChangeListener;
/*   51:     */ import javax.swing.event.ListSelectionEvent;
/*   52:     */ import javax.swing.event.ListSelectionListener;
/*   53:     */ 
/*   54:     */ public class BunchFrame
/*   55:     */   extends JFrame
/*   56:     */ {
/*   57:     */   public static final int DEFAULT_UNIT_OF_WORK_SZ = 5;
/*   58: 114 */   JMenuBar bunchMenubar_d = new JMenuBar();
/*   59: 115 */   JMenu fileMenu_d = new JMenu();
/*   60: 116 */   JMenuItem menuFileExit = new JMenuItem();
/*   61: 117 */   JMenu helpMenu_d = new JMenu();
/*   62: 118 */   JMenuItem menuHelpAbout = new JMenuItem();
/*   63: 119 */   GridBagLayout gridBagLayout1 = new GridBagLayout();
/*   64: 120 */   JPanel bunchSettingsPanel_d = new JPanel();
/*   65: 121 */   GridBagLayout gridBagLayout2 = new GridBagLayout();
/*   66: 122 */   JButton selectOutputFileButton_d = new JButton();
/*   67: 123 */   JTextField outputClusterFilename_d = new JTextField();
/*   68: 124 */   JButton selectGraphFileButton_d = new JButton();
/*   69: 125 */   JTextField inputGraphFilename_d = new JTextField();
/*   70: 126 */   JLabel outputLabel_d = new JLabel();
/*   71: 127 */   JLabel outputFileLabel_d = new JLabel();
/*   72: 128 */   JLabel inputGraphLabel_d = new JLabel();
/*   73: 129 */   JLabel clusteringLabel_ = new JLabel();
/*   74: 130 */   JComboBox clusteringMethodList_d = new JComboBox();
/*   75: 131 */   JComboBox outputFileFormatList_d = new JComboBox();
/*   76: 132 */   JButton clusteringOptionsButton_d = new JButton();
/*   77: 133 */   JButton runActionButton_d = new JButton();
/*   78:     */   FileDialog fileSelector_d;
/*   79:     */   ClusteringMethod clusteringMethod_d;
/*   80:     */   GraphOutput graphOutput_d;
/*   81:     */   Graph initialGraph_d;
/*   82:     */   BunchPreferences preferences_d;
/*   83: 139 */   JMenu configureMenu_d = new JMenu();
/*   84: 140 */   JMenuItem configureOptionsMenuItem_d = new JMenuItem();
/*   85: 141 */   JTabbedPane mainTabbedPane_d = new JTabbedPane();
/*   86: 142 */   JLabel commandLabel_d = new JLabel();
/*   87: 143 */   JComboBox actionList_d = new JComboBox();
/*   88: 144 */   JLabel optionsLabel_d = new JLabel();
/*   89:     */   Configuration configuration_d;
/*   90: 146 */   String fileBasicName_d = null;
/*   91: 147 */   JButton outputLastButton_d = new JButton();
/*   92: 148 */   JButton nextLevelGraphButton_d = new JButton();
/*   93: 149 */   Graph lastResultGraph_d = null;
/*   94: 150 */   JPanel omnipresentPane_d = new JPanel();
/*   95: 152 */   GridBagLayout gridBagLayout3 = new GridBagLayout();
/*   96: 153 */   JLabel nodesLabel_d = new JLabel();
/*   97: 154 */   JLabel suppliersLabel_d = new JLabel();
/*   98: 155 */   JList standardNodeList_d = new JList();
/*   99: 156 */   DefaultListModel standardNodeListModel_d = new DefaultListModel();
/*  100: 157 */   JList suppliersList_d = new JList();
/*  101: 158 */   DefaultListModel suppliersListModel_d = new DefaultListModel();
/*  102: 159 */   JList clientsList_d = new JList();
/*  103: 160 */   DefaultListModel clientsListModel_d = new DefaultListModel();
/*  104: 161 */   JList centralList_d = new JList();
/*  105: 162 */   DefaultListModel centralListModel_d = new DefaultListModel();
/*  106:     */   JScrollPane standardNodeListPane_d;
/*  107:     */   JScrollPane clientsListPane_d;
/*  108:     */   JScrollPane suppliersListPane_d;
/*  109: 166 */   JLabel clientsLabels_d = new JLabel();
/*  110: 167 */   JButton sendToSuppliersButton_d = new JButton();
/*  111: 168 */   JButton receiveFromClientsButton_d = new JButton();
/*  112: 169 */   JButton receiveFromSuppliersButton_d = new JButton();
/*  113: 170 */   JButton sendToClientsButton_d = new JButton();
/*  114: 171 */   JPanel supplierButtonPanel_d = new JPanel();
/*  115: 172 */   GridBagLayout gridBagLayout4 = new GridBagLayout();
/*  116: 173 */   JPanel clientButtonPanel_d = new JPanel();
/*  117: 174 */   GridBagLayout gridBagLayout5 = new GridBagLayout();
/*  118: 175 */   JPanel omniInternalPane_d = new JPanel();
/*  119: 176 */   JButton findOmnipresentNodesButton_d = new JButton();
/*  120: 177 */   JTextField findOmnipresentThreshold_d = new JTextField();
/*  121: 178 */   JLabel findOmniLabel1_d = new JLabel();
/*  122: 179 */   JLabel findOmnilabel2_d = new JLabel();
/*  123: 181 */   GridBagLayout gridBagLayout6 = new GridBagLayout();
/*  124: 182 */   GridBagLayout gridBagLayout8 = new GridBagLayout();
/*  125: 183 */   JLabel suppliersLabel2_d = new JLabel();
/*  126: 184 */   JButton findLibraryNodesButton_d = new JButton();
/*  127: 185 */   JList standardNodeListLib_d = new JList();
/*  128: 186 */   JList librariesList_d = new JList();
/*  129: 187 */   JLabel nodesLabel2_d = new JLabel();
/*  130: 188 */   JButton sendLibToClientsButton_d = new JButton();
/*  131:     */   JScrollPane librariesListPane_d;
/*  132:     */   JScrollPane standardNodeListPaneLib_d;
/*  133: 191 */   JScrollPane centralListPane_d = new JScrollPane();
/*  134: 192 */   JLabel findOmniLabel1_d1 = new JLabel();
/*  135: 193 */   JPanel clientButtonPanel_d1 = new JPanel();
/*  136: 194 */   JPanel omniInternalPane_d1 = new JPanel();
/*  137: 195 */   JPanel librariesPane_d = new JPanel();
/*  138: 196 */   DefaultListModel librariesListModel_d = new DefaultListModel();
/*  139: 197 */   JButton receiveLibFromClientsButton_d = new JButton();
/*  140: 198 */   JPanel userDirectedClusteringPane_d = new JPanel();
/*  141: 199 */   GridBagLayout gridBagLayout7 = new GridBagLayout();
/*  142: 200 */   JTextField inputClusterFile_d = new JTextField();
/*  143: 201 */   JButton inputClusterFileSelectButton_d = new JButton();
/*  144: 202 */   JLabel inputClusterLabel_d = new JLabel();
/*  145: 203 */   JCheckBox lockClustersCheckbox_d = new JCheckBox();
/*  146: 204 */   JPanel centralButtonPanel_d = new JPanel();
/*  147: 205 */   GridBagLayout gridBagLayout9 = new GridBagLayout();
/*  148: 206 */   JButton receiveFromCentralButton_d = new JButton();
/*  149: 207 */   JButton sendToCentralButton_d = new JButton();
/*  150: 208 */   JLabel centralLabel_d = new JLabel();
/*  151: 209 */   JPanel clusteringOptions = new JPanel();
/*  152: 210 */   GridBagLayout gridBagLayout10 = new GridBagLayout();
/*  153: 211 */   JCheckBox consolidateDriftersCB = new JCheckBox();
/*  154: 212 */   JLabel jLabel1 = new JLabel();
/*  155: 213 */   JLabel jLabel2 = new JLabel();
/*  156: 214 */   JComboBox ClusteringAlgEF = new JComboBox();
/*  157: 215 */   JTextField delimEF = new JTextField();
/*  158: 216 */   JLabel jLabel3 = new JLabel();
/*  159: 217 */   JCheckBox spaceDelimCB = new JCheckBox();
/*  160: 218 */   JCheckBox tabDelimCB = new JCheckBox();
/*  161: 219 */   JButton visualizeButton_d = new JButton();
/*  162: 220 */   JPanel distPane = new JPanel();
/*  163: 221 */   GridBagLayout gridBagLayout11 = new GridBagLayout();
/*  164: 222 */   JCheckBox distClustEnableCB = new JCheckBox();
/*  165: 223 */   JLabel jLabel4 = new JLabel();
/*  166: 224 */   JLabel jLabel5 = new JLabel();
/*  167: 225 */   JTextField nameServerEF = new JTextField();
/*  168: 226 */   JLabel jLabel6 = new JLabel();
/*  169: 227 */   JTextField portEF = new JTextField();
/*  170: 228 */   JButton queryNS = new JButton();
/*  171: 229 */   JLabel jLabel7 = new JLabel();
/*  172: 230 */   JButton includeDistSvrsPB = new JButton();
/*  173: 231 */   JTextField nameSpaceEF = new JTextField();
/*  174: 232 */   JScrollPane jScrollPane1 = new JScrollPane();
/*  175: 233 */   JList serverList = new JList();
/*  176:     */   Vector serverVector;
/*  177:     */   Vector activeServerVector;
/*  178:     */   CallbackImpl svrCallback;
/*  179: 238 */   BunchEvent bevent = null;
/*  180: 239 */   JLabel jLabel8 = new JLabel();
/*  181: 240 */   JTextField UOWSzEF = new JTextField();
/*  182: 241 */   JButton deactivatePB = new JButton();
/*  183: 242 */   JCheckBox adaptiveEnableCB = new JCheckBox();
/*  184: 244 */   StatsManager stats = StatsManager.getInstance();
/*  185: 245 */   JCheckBox timeoutEnable = new JCheckBox();
/*  186: 246 */   JTextField maxRuntimeEF = new JTextField();
/*  187: 247 */   JLabel jLabel9 = new JLabel();
/*  188: 248 */   JMenu utilityMenu_d = new JMenu();
/*  189: 249 */   JMenuItem utilityMeasurementCalc = new JMenuItem();
/*  190: 250 */   JLabel jLabel10 = new JLabel();
/*  191: 251 */   JLabel jLabel11 = new JLabel();
/*  192: 252 */   JComboBox agglomOutputCB = new JComboBox();
/*  193: 253 */   JCheckBox outputTreeCB = new JCheckBox();
/*  194: 254 */   JButton ClearClusterFile = new JButton();
/*  195: 255 */   JMenuItem menuShowDistributedTab = new JMenuItem();
/*  196: 256 */   JMenuItem clusteringUtilsMenu = new JMenuItem();
/*  197: 257 */   JMenuItem fileUtilsMenu = new JMenuItem();
/*  198:     */   
/*  199:     */   public BunchFrame()
/*  200:     */   {
/*  201: 267 */     enableEvents(64L);
/*  202:     */     try
/*  203:     */     {
/*  204: 269 */       jbInit();
/*  205:     */     }
/*  206:     */     catch (Exception e)
/*  207:     */     {
/*  208: 272 */       e.printStackTrace();
/*  209:     */     }
/*  210:     */   }
/*  211:     */   
/*  212:     */   private void jbInit()
/*  213:     */     throws Exception
/*  214:     */   {
/*  215: 287 */     this.preferences_d = ((BunchPreferences)Beans.instantiate(null, "bunch.BunchPreferences"));
/*  216:     */     
/*  217:     */ 
/*  218: 290 */     String[] methodList = this.preferences_d.getClusteringMethodFactory().getItemList();
/*  219: 291 */     for (int i = 0; i < methodList.length; i++) {
/*  220: 292 */       this.clusteringMethodList_d.addItem(methodList[i]);
/*  221:     */     }
/*  222: 296 */     String defaultCM = this.preferences_d.getClusteringMethodFactory().getDefaultMethod();
/*  223: 297 */     setClusteringMethod(defaultCM);
/*  224: 298 */     this.clusteringMethodList_d.setSelectedItem(defaultCM);
/*  225:     */     
/*  226:     */ 
/*  227: 301 */     String[] mqFnList = this.preferences_d.getObjectiveFunctionCalculatorFactory().getItemList();
/*  228: 302 */     for (int i = 0; i < mqFnList.length; i++) {
/*  229: 303 */       this.ClusteringAlgEF.addItem(mqFnList[i]);
/*  230:     */     }
/*  231: 306 */     String defaultMqFn = this.preferences_d.getObjectiveFunctionCalculatorFactory().getDefaultMethod();
/*  232: 307 */     this.ClusteringAlgEF.setSelectedItem(defaultMqFn);
/*  233:     */     
/*  234:     */ 
/*  235:     */ 
/*  236:     */ 
/*  237: 312 */     String defOutputType = this.preferences_d.getGraphOutputFactory().defaultOption;
/*  238:     */     
/*  239: 314 */     this.outputFileFormatList_d.setSelectedItem(defOutputType);
/*  240: 315 */     this.outputFileFormatList_d.addActionListener(new ActionListener()
/*  241:     */     {
/*  242:     */       public void actionPerformed(ActionEvent e)
/*  243:     */       {
/*  244: 318 */         BunchFrame.this.outputFileFormatList_d_actionPerformed(e);
/*  245:     */       }
/*  246: 321 */     });
/*  247: 322 */     this.standardNodeListPane_d = new JScrollPane(this.standardNodeList_d);
/*  248: 323 */     this.clientsListPane_d = new JScrollPane(this.clientsList_d);
/*  249: 324 */     this.suppliersListPane_d = new JScrollPane(this.suppliersList_d);
/*  250: 325 */     this.centralListPane_d = new JScrollPane(this.centralList_d);
/*  251: 326 */     this.librariesListPane_d = new JScrollPane(this.librariesList_d);
/*  252: 327 */     this.standardNodeListPaneLib_d = new JScrollPane(this.standardNodeListLib_d);
/*  253:     */     
/*  254:     */ 
/*  255:     */ 
/*  256: 331 */     Graph.setObjectiveFunctionCalculatorFactory(this.preferences_d.getObjectiveFunctionCalculatorFactory());
/*  257:     */     
/*  258:     */ 
/*  259:     */ 
/*  260: 335 */     getContentPane().setLayout(this.gridBagLayout1);
/*  261: 336 */     setSize(new Dimension(622, 476));
/*  262: 337 */     setTitle("Clustering Tool - December 2014");
/*  263: 338 */     this.selectOutputFileButton_d.setText("Select...");
/*  264: 339 */     this.selectOutputFileButton_d.setActionCommand("Select Output File");
/*  265: 340 */     this.selectOutputFileButton_d.addActionListener(new BunchFrame_selectOutputFileButton_d_actionAdapter(this));
/*  266: 341 */     this.selectGraphFileButton_d.setText("Select...");
/*  267:     */     
/*  268: 343 */     this.selectGraphFileButton_d.setActionCommand("Select Input Graph File");
/*  269: 344 */     this.selectGraphFileButton_d.addActionListener(new BunchFrame_selectGraphFileButton_d_actionAdapter(this));
/*  270: 345 */     this.outputLabel_d.setText("Output Cluster File:");
/*  271: 346 */     this.outputFileLabel_d.setText("Output File Format:");
/*  272: 347 */     this.inputGraphLabel_d.setText("Input Graph File:");
/*  273: 348 */     this.clusteringLabel_.setText("Clustering Method:");
/*  274: 349 */     this.clusteringOptionsButton_d.setText("Options");
/*  275: 350 */     this.clusteringOptionsButton_d.setActionCommand("Select Clustering Options");
/*  276: 351 */     this.runActionButton_d.setText("Run");
/*  277: 352 */     this.fileSelector_d = new FileDialog(this);
/*  278: 353 */     this.clusteringMethodList_d.addActionListener(new ActionListener()
/*  279:     */     {
/*  280:     */       public void actionPerformed(ActionEvent e)
/*  281:     */       {
/*  282: 355 */         BunchFrame.this.clusteringMethodList_d_actionPerformed(e);
/*  283:     */       }
/*  284: 357 */     });
/*  285: 358 */     this.clusteringMethodList_d.addItemListener(new BunchFrame_clusteringMethodList_d_itemAdapter(this));
/*  286: 359 */     this.clusteringOptionsButton_d.addActionListener(new BunchFrame_clusteringOptionsButton_d_actionAdapter(this));
/*  287: 360 */     this.runActionButton_d.setActionCommand("Run Action");
/*  288: 361 */     this.runActionButton_d.addActionListener(new BunchFrame_runActionButton_d_actionAdapter(this));
/*  289: 362 */     this.configureMenu_d.setText("Configure");
/*  290: 363 */     this.configureOptionsMenuItem_d.setText("Options");
/*  291: 364 */     this.commandLabel_d.setText("Action:");
/*  292: 365 */     this.optionsLabel_d.setText("Options:");
/*  293: 366 */     this.outputLastButton_d.setText("Save...");
/*  294: 367 */     this.nextLevelGraphButton_d.setText("Generate Next Level");
/*  295: 368 */     this.nodesLabel_d.setText("Nodes:");
/*  296: 369 */     this.sendToSuppliersButton_d.setText("->");
/*  297: 370 */     this.sendToSuppliersButton_d.setFont(new Font("Monospaced", 0, 11));
/*  298: 371 */     this.sendToSuppliersButton_d.addActionListener(new BunchFrame_sendToSuppliersButton_d_actionAdapter(this));
/*  299: 372 */     this.receiveFromClientsButton_d.setText("<-");
/*  300: 373 */     this.receiveFromClientsButton_d.setFont(new Font("Monospaced", 0, 11));
/*  301: 374 */     this.receiveFromClientsButton_d.addActionListener(new BunchFrame_receiveFromClientsButton_d_actionAdapter(this));
/*  302: 375 */     this.findOmnipresentNodesButton_d.setText("jButton1");
/*  303: 376 */     this.findOmnipresentNodesButton_d.setText("Find");
/*  304: 377 */     this.findOmnipresentNodesButton_d.addActionListener(new BunchFrame_findOmnipresentNodesButton_d_actionAdapter(this));
/*  305: 378 */     this.findOmniLabel1_d.setText("omnipresent modules with");
/*  306: 379 */     this.findOmnipresentThreshold_d.setText("3.0");
/*  307: 380 */     this.findOmnilabel2_d.setText("times the average connections");
/*  308: 381 */     this.suppliersLabel2_d.setText("Libraries:");
/*  309: 382 */     this.findLibraryNodesButton_d.setText("jButton1");
/*  310: 383 */     this.findLibraryNodesButton_d.setText("Find");
/*  311: 384 */     this.findLibraryNodesButton_d.addActionListener(new BunchFrame_findLibraryNodesButton_d_actionAdapter(this));
/*  312: 385 */     this.nodesLabel2_d.setText("Nodes:                      ");
/*  313: 386 */     this.sendLibToClientsButton_d.setText("->");
/*  314: 387 */     this.sendLibToClientsButton_d.setFont(new Font("Monospaced", 0, 11));
/*  315: 388 */     this.sendLibToClientsButton_d.addActionListener(new BunchFrame_sendLibToClientsButton_d_actionAdapter(this));
/*  316: 389 */     this.findOmniLabel1_d1.setText("library modules automatically");
/*  317: 390 */     this.clientButtonPanel_d1.setLayout(this.gridBagLayout8);
/*  318: 391 */     this.librariesPane_d.setLayout(this.gridBagLayout6);
/*  319: 392 */     this.receiveLibFromClientsButton_d.setText("<-");
/*  320: 393 */     this.receiveLibFromClientsButton_d.setFont(new Font("Monospaced", 0, 11));
/*  321: 394 */     this.inputClusterFile_d.setText("                                                         ");
/*  322: 395 */     this.inputClusterFileSelectButton_d.setText("jButton1");
/*  323: 396 */     this.inputClusterFileSelectButton_d.setActionCommand("Select Input Cluster File");
/*  324: 397 */     this.inputClusterFileSelectButton_d.setText("Select...");
/*  325: 398 */     this.inputClusterLabel_d.setText("Input Cluster File:");
/*  326: 399 */     this.lockClustersCheckbox_d.setEnabled(false);
/*  327: 400 */     this.lockClustersCheckbox_d.setText("Lock Clusters");
/*  328: 401 */     this.receiveFromCentralButton_d.setText("<-");
/*  329: 402 */     this.receiveFromCentralButton_d.setFont(new Font("Monospaced", 0, 11));
/*  330: 403 */     this.sendToCentralButton_d.setText("->");
/*  331: 404 */     this.sendToCentralButton_d.setFont(new Font("Monospaced", 0, 11));
/*  332: 405 */     this.centralLabel_d.setText("Clients & Suppliers");
/*  333: 406 */     this.centralLabel_d.setToolTipText("");
/*  334: 407 */     this.consolidateDriftersCB.setText("jCheckBox1");
/*  335: 408 */     this.consolidateDriftersCB.setActionCommand("Consolidate Drifters");
/*  336: 409 */     this.consolidateDriftersCB.setText("Consolidate Drifters");
/*  337: 410 */     this.consolidateDriftersCB.setFont(new Font("Dialog", 0, 12));
/*  338: 411 */     this.consolidateDriftersCB.setSelected(true);
/*  339: 412 */     this.consolidateDriftersCB.addActionListener(new ActionListener()
/*  340:     */     {
/*  341:     */       public void actionPerformed(ActionEvent e)
/*  342:     */       {
/*  343: 414 */         BunchFrame.this.consolidateDriftersCB_actionPerformed(e);
/*  344:     */       }
/*  345: 416 */     });
/*  346: 417 */     this.jLabel1.setText("Use the following options to control clustering engine:");
/*  347: 418 */     this.jLabel2.setText("Clustering Algorithm:");
/*  348: 419 */     this.delimEF.setFont(new Font("Dialog", 0, 12));
/*  349: 420 */     this.delimEF.addActionListener(new ActionListener()
/*  350:     */     {
/*  351:     */       public void actionPerformed(ActionEvent e)
/*  352:     */       {
/*  353: 422 */         BunchFrame.this.delimEF_actionPerformed(e);
/*  354:     */       }
/*  355: 424 */     });
/*  356: 425 */     this.jLabel3.setText("Graph File Delimiters:");
/*  357: 426 */     this.spaceDelimCB.setText("Include Space");
/*  358: 427 */     this.spaceDelimCB.setActionCommand("spaceDelim");
/*  359: 428 */     this.spaceDelimCB.setFont(new Font("Dialog", 0, 12));
/*  360: 429 */     this.spaceDelimCB.setSelected(true);
/*  361: 430 */     this.spaceDelimCB.setToolTipText("Include spaces");
/*  362: 431 */     this.tabDelimCB.setText("Tab");
/*  363: 432 */     this.tabDelimCB.setToolTipText("Include Tabs");
/*  364: 433 */     this.tabDelimCB.setSelected(true);
/*  365: 434 */     this.spaceDelimCB.addActionListener(new ActionListener()
/*  366:     */     {
/*  367:     */       public void actionPerformed(ActionEvent e)
/*  368:     */       {
/*  369: 436 */         BunchFrame.this.spaceDelimCB_actionPerformed(e);
/*  370:     */       }
/*  371: 438 */     });
/*  372: 439 */     this.ClusteringAlgEF.addActionListener(new ActionListener()
/*  373:     */     {
/*  374:     */       public void actionPerformed(ActionEvent e)
/*  375:     */       {
/*  376: 441 */         BunchFrame.this.ClusteringAlgEF_actionPerformed(e);
/*  377:     */       }
/*  378: 443 */     });
/*  379: 444 */     this.clusteringOptions.setLayout(this.gridBagLayout10);
/*  380: 445 */     this.sendToCentralButton_d.addActionListener(new BunchFrame_sendToCentralButton_d_actionAdapter(this));
/*  381: 446 */     this.receiveFromCentralButton_d.addActionListener(new BunchFrame_receiveFromCentralButton_d_actionAdapter(this));
/*  382: 447 */     this.centralButtonPanel_d.setLayout(this.gridBagLayout9);
/*  383: 448 */     this.inputClusterFileSelectButton_d.addActionListener(new BunchFrame_inputClusterFileSelectButton_d_actionAdapter(this));
/*  384: 449 */     this.userDirectedClusteringPane_d.setLayout(this.gridBagLayout7);
/*  385: 450 */     this.receiveLibFromClientsButton_d.addActionListener(new BunchFrame_receiveLibFromClientsButton_d_actionAdapter(this));
/*  386: 451 */     this.clientButtonPanel_d.setLayout(this.gridBagLayout5);
/*  387: 452 */     this.supplierButtonPanel_d.setLayout(this.gridBagLayout4);
/*  388: 453 */     this.receiveFromSuppliersButton_d.setText("<-");
/*  389: 454 */     this.receiveFromSuppliersButton_d.setFont(new Font("Monospaced", 0, 11));
/*  390: 455 */     this.receiveFromSuppliersButton_d.addActionListener(new BunchFrame_receiveFromSuppliersButton_d_actionAdapter(this));
/*  391: 456 */     this.sendToClientsButton_d.setText("->");
/*  392: 457 */     this.sendToClientsButton_d.setFont(new Font("Monospaced", 0, 11));
/*  393: 458 */     this.sendToClientsButton_d.addActionListener(new BunchFrame_sendToClientsButton_d_actionAdapter(this));
/*  394: 459 */     this.suppliersLabel_d.setText("Suppliers:");
/*  395: 460 */     this.clientsLabels_d.setText("Clients:");
/*  396: 461 */     this.omnipresentPane_d.setLayout(this.gridBagLayout3);
/*  397: 462 */     this.outputLastButton_d.addActionListener(new BunchFrame_outputLastButton_d_actionAdapter(this));
/*  398: 463 */     this.nextLevelGraphButton_d.addActionListener(new BunchFrame_nextLevelGraphButton_d_actionAdapter(this));
/*  399: 464 */     this.configureOptionsMenuItem_d.addActionListener(new BunchFrame_configureOptionsMenuItem_d_actionAdapter(this));
/*  400: 465 */     this.bunchSettingsPanel_d.setLayout(this.gridBagLayout2);
/*  401: 466 */     this.fileMenu_d.setText("File");
/*  402: 467 */     this.menuFileExit.setText("Exit");
/*  403: 468 */     this.menuFileExit.addActionListener(new BunchFrame_menuFileExit_ActionAdapter(this));
/*  404: 469 */     this.helpMenu_d.setText("Help");
/*  405: 470 */     this.menuHelpAbout.setText("About");
/*  406: 471 */     this.menuHelpAbout.addActionListener(new BunchFrame_menuHelpAbout_ActionAdapter(this));
/*  407: 472 */     this.visualizeButton_d.setEnabled(false);
/*  408: 473 */     this.visualizeButton_d.setText("Visualize...");
/*  409:     */     
/*  410: 475 */     this.distPane.setLayout(this.gridBagLayout11);
/*  411: 476 */     this.distClustEnableCB.setText("jCheckBox1");
/*  412: 477 */     this.distClustEnableCB.setText("Enable Distributed Clustering");
/*  413: 478 */     this.distClustEnableCB.addChangeListener(new ChangeListener()
/*  414:     */     {
/*  415:     */       public void stateChanged(ChangeEvent e)
/*  416:     */       {
/*  417: 481 */         BunchFrame.this.distClustEnableCB_stateChanged(e);
/*  418:     */       }
/*  419: 483 */     });
/*  420: 484 */     this.jLabel4.setText("Namespace:");
/*  421: 485 */     this.jLabel5.setText("Name Server:");
/*  422: 486 */     this.jLabel6.setText("Port:");
/*  423: 487 */     this.queryNS.setText("Query Name Server");
/*  424: 488 */     this.queryNS.addActionListener(new ActionListener()
/*  425:     */     {
/*  426:     */       public void actionPerformed(ActionEvent e)
/*  427:     */       {
/*  428: 491 */         BunchFrame.this.queryNS_actionPerformed(e);
/*  429:     */       }
/*  430: 493 */     });
/*  431: 494 */     this.queryNS.addActionListener(new ActionListener()
/*  432:     */     {
/*  433:     */       public void actionPerformed(ActionEvent e)
/*  434:     */       {
/*  435: 497 */         BunchFrame.this.queryNS_actionPerformed(e);
/*  436:     */       }
/*  437: 499 */     });
/*  438: 500 */     this.queryNS.addActionListener(new BunchFrame_queryNS_actionAdapter(this));
/*  439: 501 */     this.jLabel7.setFont(new Font("Dialog", 1, 12));
/*  440: 502 */     this.jLabel7.setHorizontalAlignment(0);
/*  441: 503 */     this.jLabel7.setText("Results (Select to Activate - use CTRL key for multiple selections)");
/*  442: 504 */     this.includeDistSvrsPB.setText("Include Selected Servers");
/*  443: 505 */     this.includeDistSvrsPB.addActionListener(new ActionListener()
/*  444:     */     {
/*  445:     */       public void actionPerformed(ActionEvent e)
/*  446:     */       {
/*  447: 508 */         BunchFrame.this.includeDistSvrsPB_actionPerformed(e);
/*  448:     */       }
/*  449: 511 */     });
/*  450: 512 */     this.nameSpaceEF.setText("BunchServer");
/*  451: 513 */     this.portEF.setText("900");
/*  452: 514 */     this.jLabel8.setText("Base UOW Size:");
/*  453: 515 */     this.UOWSzEF.setText("5");
/*  454: 516 */     this.deactivatePB.setEnabled(false);
/*  455: 517 */     this.deactivatePB.setSelected(true);
/*  456: 518 */     this.deactivatePB.setText("Deactivate All Servers");
/*  457: 519 */     this.deactivatePB.addActionListener(new ActionListener()
/*  458:     */     {
/*  459:     */       public void actionPerformed(ActionEvent e)
/*  460:     */       {
/*  461: 522 */         BunchFrame.this.deactivatePB_actionPerformed(e);
/*  462:     */       }
/*  463: 524 */     });
/*  464: 525 */     this.serverList.addListSelectionListener(new ListSelectionListener()
/*  465:     */     {
/*  466:     */       public void valueChanged(ListSelectionEvent e)
/*  467:     */       {
/*  468: 528 */         BunchFrame.this.serverList_valueChanged(e);
/*  469:     */       }
/*  470: 530 */     });
/*  471: 531 */     this.adaptiveEnableCB.setSelected(true);
/*  472: 532 */     this.adaptiveEnableCB.setEnabled(false);
/*  473: 533 */     this.adaptiveEnableCB.setText("Use Adaptive Load Balancing");
/*  474: 534 */     this.timeoutEnable.setText("Limit Runtime To");
/*  475: 535 */     this.timeoutEnable.addActionListener(new ActionListener()
/*  476:     */     {
/*  477:     */       public void actionPerformed(ActionEvent e)
/*  478:     */       {
/*  479: 538 */         BunchFrame.this.timeoutEnable_actionPerformed(e);
/*  480:     */       }
/*  481: 540 */     });
/*  482: 541 */     this.jLabel9.setText("(ms)");
/*  483: 542 */     this.maxRuntimeEF.setEnabled(false);
/*  484: 543 */     this.maxRuntimeEF.setText("1000");
/*  485: 544 */     this.utilityMenu_d.setText("Utility");
/*  486: 545 */     this.utilityMeasurementCalc.setText("Measurement Calculators...");
/*  487: 546 */     this.utilityMeasurementCalc.addActionListener(new ActionListener()
/*  488:     */     {
/*  489:     */       public void actionPerformed(ActionEvent e)
/*  490:     */       {
/*  491: 549 */         BunchFrame.this.utilityMeasurementCalc_actionPerformed(e);
/*  492:     */       }
/*  493: 551 */     });
/*  494: 552 */     this.actionList_d.addActionListener(new ActionListener()
/*  495:     */     {
/*  496:     */       public void actionPerformed(ActionEvent e)
/*  497:     */       {
/*  498: 555 */         BunchFrame.this.actionList_d_actionPerformed(e);
/*  499:     */       }
/*  500: 557 */     });
/*  501: 558 */     this.jLabel10.setText("Agglomerative");
/*  502: 559 */     this.jLabel11.setText("Output Options:");
/*  503: 560 */     this.agglomOutputCB.addActionListener(new ActionListener()
/*  504:     */     {
/*  505:     */       public void actionPerformed(ActionEvent e)
/*  506:     */       {
/*  507: 563 */         BunchFrame.this.agglomOutputCB_actionPerformed(e);
/*  508:     */       }
/*  509: 565 */     });
/*  510: 566 */     this.outputTreeCB.setText("Generate Tree Fomat");
/*  511: 567 */     this.ClearClusterFile.setText("Clear");
/*  512: 568 */     this.ClearClusterFile.addActionListener(new ActionListener()
/*  513:     */     {
/*  514:     */       public void actionPerformed(ActionEvent e)
/*  515:     */       {
/*  516: 571 */         BunchFrame.this.ClearClusterFile_actionPerformed(e);
/*  517:     */       }
/*  518: 573 */     });
/*  519: 574 */     this.menuShowDistributedTab.setText("Show Distributed Tab");
/*  520: 575 */     this.menuShowDistributedTab.addActionListener(new ActionListener()
/*  521:     */     {
/*  522:     */       public void actionPerformed(ActionEvent e)
/*  523:     */       {
/*  524: 577 */         BunchFrame.this.menuShowDistributedTab_actionPerformed(e);
/*  525:     */       }
/*  526: 579 */     });
/*  527: 580 */     this.clusteringUtilsMenu.setText("Clustering Utilities...");
/*  528: 581 */     this.clusteringUtilsMenu.addActionListener(new BunchFrame_clusteringUtilsMenu_actionAdapter(this));
/*  529: 582 */     this.fileUtilsMenu.setText("File Utilities...");
/*  530: 583 */     this.fileUtilsMenu.addActionListener(new BunchFrame_fileUtilsMenu_actionAdapter(this));
/*  531: 584 */     this.fileMenu_d.add(this.menuFileExit);
/*  532: 585 */     this.helpMenu_d.add(this.menuHelpAbout);
/*  533: 586 */     this.bunchMenubar_d.add(this.fileMenu_d);
/*  534: 587 */     this.bunchMenubar_d.add(this.utilityMenu_d);
/*  535: 588 */     this.bunchMenubar_d.add(this.helpMenu_d);
/*  536: 589 */     setJMenuBar(this.bunchMenubar_d);
/*  537: 590 */     setResizable(false);
/*  538:     */     
/*  539: 592 */     this.standardNodeList_d.setModel(this.standardNodeListModel_d);
/*  540: 593 */     this.clientsList_d.setModel(this.clientsListModel_d);
/*  541: 594 */     this.suppliersList_d.setModel(this.suppliersListModel_d);
/*  542: 595 */     this.centralList_d.setModel(this.centralListModel_d);
/*  543: 596 */     this.librariesList_d.setModel(this.librariesListModel_d);
/*  544: 597 */     this.standardNodeListLib_d.setModel(this.standardNodeListModel_d);
/*  545:     */     
/*  546: 599 */     this.actionList_d.addItem("Agglomerative Clustering");
/*  547: 600 */     this.actionList_d.addItem("User-Driven Clustering");
/*  548:     */     
/*  549: 602 */     getContentPane().add(this.runActionButton_d, new GridBagConstraints(1, 3, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(5, 5, 5, 5), 0, 0));
/*  550:     */     
/*  551: 604 */     getContentPane().add(this.mainTabbedPane_d, new GridBagConstraints(0, 1, 2, 1, 1.0D, 1.0D, 10, 1, new Insets(0, 10, 4, 6), 53, 50));
/*  552:     */     
/*  553: 606 */     this.mainTabbedPane_d.addTab("Basic", this.bunchSettingsPanel_d);
/*  554: 607 */     this.bunchSettingsPanel_d.add(this.selectOutputFileButton_d, new GridBagConstraints2(3, 2, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(5, 5, 5, 5), 0, 0));
/*  555:     */     
/*  556: 609 */     this.bunchSettingsPanel_d.add(this.outputClusterFilename_d, new GridBagConstraints2(1, 2, 1, 1, 1.0D, 0.0D, 10, 2, new Insets(5, 5, 5, 5), 0, 0));
/*  557:     */     
/*  558: 611 */     this.bunchSettingsPanel_d.add(this.selectGraphFileButton_d, new GridBagConstraints2(3, 0, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(10, 5, 5, 5), 0, 0));
/*  559:     */     
/*  560: 613 */     this.bunchSettingsPanel_d.add(this.inputGraphFilename_d, new GridBagConstraints2(1, 0, 1, 1, 1.0D, 0.0D, 10, 2, new Insets(10, 5, 5, 5), 0, 0));
/*  561:     */     
/*  562: 615 */     this.bunchSettingsPanel_d.add(this.outputLabel_d, new GridBagConstraints2(0, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(2, 10, 2, 2), 0, 0));
/*  563:     */     
/*  564: 617 */     this.bunchSettingsPanel_d.add(this.outputFileLabel_d, new GridBagConstraints2(0, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(2, 10, 2, 2), 0, 0));
/*  565:     */     
/*  566: 619 */     this.bunchSettingsPanel_d.add(this.inputGraphLabel_d, new GridBagConstraints2(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(10, 10, 2, 2), 0, 0));
/*  567:     */     
/*  568: 621 */     this.bunchSettingsPanel_d.add(this.clusteringLabel_, new GridBagConstraints2(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 10, 2, 2), 0, 0));
/*  569:     */     
/*  570: 623 */     this.bunchSettingsPanel_d.add(this.clusteringMethodList_d, new GridBagConstraints2(1, 1, 2, 1, 1.0D, 0.0D, 10, 2, new Insets(5, 5, 5, 5), 0, 0));
/*  571:     */     
/*  572: 625 */     this.bunchSettingsPanel_d.add(this.outputFileFormatList_d, new GridBagConstraints2(1, 3, 1, 1, 1.0D, 0.0D, 10, 2, new Insets(5, 5, 5, 5), 0, 0));
/*  573:     */     
/*  574: 627 */     this.bunchSettingsPanel_d.add(this.clusteringOptionsButton_d, new GridBagConstraints2(3, 1, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(5, 5, 5, 5), 0, 0));
/*  575:     */     
/*  576: 629 */     this.bunchSettingsPanel_d.add(this.outputLastButton_d, new GridBagConstraints2(3, 3, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(5, 5, 5, 5), 0, 0));
/*  577:     */     
/*  578: 631 */     this.bunchSettingsPanel_d.add(this.nextLevelGraphButton_d, new GridBagConstraints2(2, 4, 2, 1, 0.0D, 0.0D, 13, 0, new Insets(5, 5, 5, 5), 0, 0));
/*  579:     */     
/*  580:     */ 
/*  581: 634 */     this.mainTabbedPane_d.addTab("Clustering Options", this.clusteringOptions);
/*  582: 635 */     this.clusteringOptions.add(this.consolidateDriftersCB, new GridBagConstraints2(0, 1, 4, 1, 0.0D, 0.0D, 18, 1, new Insets(0, 0, 0, 0), 0, 0));
/*  583:     */     
/*  584: 637 */     this.clusteringOptions.add(this.jLabel1, new GridBagConstraints2(0, 0, 4, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 14));
/*  585:     */     
/*  586: 639 */     this.clusteringOptions.add(this.jLabel2, new GridBagConstraints2(0, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 4), 0, 0));
/*  587:     */     
/*  588: 641 */     this.clusteringOptions.add(this.ClusteringAlgEF, new GridBagConstraints2(1, 2, 3, 1, 0.0D, 0.0D, 13, 2, new Insets(0, 0, 4, 0), 67, 0));
/*  589:     */     
/*  590: 643 */     this.clusteringOptions.add(this.delimEF, new GridBagConstraints2(1, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(2, 2, 7, 5), 73, 6));
/*  591:     */     
/*  592: 645 */     this.clusteringOptions.add(this.jLabel3, new GridBagConstraints2(0, 3, 1, 1, 0.0D, 0.0D, 15, 3, new Insets(4, 0, 10, 0), 4, 0));
/*  593:     */     
/*  594: 647 */     this.clusteringOptions.add(this.spaceDelimCB, new GridBagConstraints2(2, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
/*  595:     */     
/*  596: 649 */     this.clusteringOptions.add(this.tabDelimCB, new GridBagConstraints2(3, 3, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
/*  597:     */     
/*  598: 651 */     this.clusteringOptions.add(this.timeoutEnable, new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 0, 0, 0), 0, 0));
/*  599:     */     
/*  600: 653 */     this.clusteringOptions.add(this.maxRuntimeEF, new GridBagConstraints(1, 4, 1, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 0, 0, 0), 0, 0));
/*  601:     */     
/*  602: 655 */     this.clusteringOptions.add(this.jLabel9, new GridBagConstraints(2, 4, 1, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 5, 0, 0), 0, 0));
/*  603:     */     
/*  604: 657 */     this.clusteringOptions.add(this.jLabel10, new GridBagConstraints(0, 5, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(4, 0, -2, 0), 0, 0));
/*  605:     */     
/*  606: 659 */     this.clusteringOptions.add(this.jLabel11, new GridBagConstraints(0, 6, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
/*  607:     */     
/*  608: 661 */     this.clusteringOptions.add(this.agglomOutputCB, new GridBagConstraints(1, 6, 2, 1, 0.0D, 0.0D, 17, 2, new Insets(0, 0, 0, 0), 0, 0));
/*  609:     */     
/*  610: 663 */     this.clusteringOptions.add(this.outputTreeCB, new GridBagConstraints(3, 6, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 5, 0, 0), 0, 0));
/*  611:     */     
/*  612: 665 */     getContentPane().add(this.commandLabel_d, new GridBagConstraints2(0, 2, 2, 1, 0.0D, 0.0D, 17, 2, new Insets(5, 10, 5, 10), 0, 0));
/*  613:     */     
/*  614: 667 */     getContentPane().add(this.actionList_d, new GridBagConstraints2(0, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 10, 5, 10), 72, 0));
/*  615:     */     
/*  616: 669 */     getContentPane().add(this.optionsLabel_d, new GridBagConstraints2(0, 0, 1, 1, 0.0D, 0.0D, 17, 1, new Insets(5, 10, 5, 5), 0, 0));
/*  617:     */     
/*  618:     */ 
/*  619: 672 */     this.mainTabbedPane_d.addTab("Libraries", this.librariesPane_d);
/*  620: 673 */     this.librariesPane_d.add(this.suppliersLabel2_d, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 17, 1, new Insets(5, 5, 5, 5), 175, 0));
/*  621:     */     
/*  622: 675 */     this.librariesPane_d.add(this.librariesListPane_d, new GridBagConstraints(2, 1, 1, 1, 0.0D, 0.0D, 13, 1, new Insets(10, 3, 0, 7), 0, 0));
/*  623:     */     
/*  624: 677 */     this.librariesPane_d.add(this.clientButtonPanel_d1, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(5, 5, 5, 5), 0, 125));
/*  625:     */     
/*  626: 679 */     this.clientButtonPanel_d1.add(this.sendLibToClientsButton_d, new GridBagConstraints2(0, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(2, 2, 2, 2), 0, 0));
/*  627:     */     
/*  628: 681 */     this.clientButtonPanel_d1.add(this.receiveLibFromClientsButton_d, new GridBagConstraints2(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(2, 2, 2, 2), 0, 0));
/*  629:     */     
/*  630: 683 */     this.librariesPane_d.add(this.omniInternalPane_d1, new GridBagConstraints2(0, 2, 3, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
/*  631:     */     
/*  632: 685 */     this.omniInternalPane_d1.add(this.findLibraryNodesButton_d, null);
/*  633: 686 */     this.omniInternalPane_d1.add(this.findOmniLabel1_d1, null);
/*  634: 687 */     this.librariesPane_d.add(this.nodesLabel2_d, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 14, 1, new Insets(5, 5, 5, 5), 125, 0));
/*  635:     */     
/*  636: 689 */     this.librariesPane_d.add(this.standardNodeListPaneLib_d, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 1, new Insets(10, 4, 0, 6), 0, 0));
/*  637:     */     
/*  638: 691 */     this.mainTabbedPane_d.addTab("Omnipresent", this.omnipresentPane_d);
/*  639: 692 */     this.omnipresentPane_d.add(this.standardNodeListPane_d, new GridBagConstraints2(0, 1, 1, 5, 1.0D, 1.0D, 18, 1, new Insets(5, 5, 5, 5), 0, 0));
/*  640:     */     
/*  641: 694 */     this.omnipresentPane_d.add(this.nodesLabel_d, new GridBagConstraints2(0, 0, 2, 1, 0.0D, 0.0D, 17, 0, new Insets(6, 10, 4, 0), 0, 0));
/*  642:     */     
/*  643: 696 */     this.omnipresentPane_d.add(this.suppliersLabel_d, new GridBagConstraints2(2, 0, 3, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 5, 5), 0, 0));
/*  644:     */     
/*  645: 698 */     this.omnipresentPane_d.add(this.suppliersListPane_d, new GridBagConstraints2(2, 1, 2, 1, 1.0D, 1.0D, 10, 2, new Insets(5, 5, 5, 5), 0, 10));
/*  646:     */     
/*  647: 700 */     this.omnipresentPane_d.add(this.clientsLabels_d, new GridBagConstraints2(2, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 0, 0), 0, 0));
/*  648:     */     
/*  649: 702 */     this.omnipresentPane_d.add(this.clientsListPane_d, new GridBagConstraints2(2, 3, 2, 1, 1.0D, 1.0D, 10, 2, new Insets(5, 5, 5, 5), 0, 13));
/*  650:     */     
/*  651: 704 */     this.omnipresentPane_d.add(this.supplierButtonPanel_d, new GridBagConstraints2(1, 1, 1, 2, 0.0D, 0.0D, 11, 0, new Insets(0, 4, 10, 6), 0, 0));
/*  652:     */     
/*  653: 706 */     this.supplierButtonPanel_d.add(this.receiveFromSuppliersButton_d, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(1, 1, 1, 1), 0, 0));
/*  654:     */     
/*  655: 708 */     this.supplierButtonPanel_d.add(this.sendToSuppliersButton_d, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 15, 1, new Insets(1, 1, 1, 1), 0, 0));
/*  656:     */     
/*  657: 710 */     this.omnipresentPane_d.add(this.clientButtonPanel_d, new GridBagConstraints2(1, 3, 1, 2, 0.0D, 0.0D, 11, 0, new Insets(1, 5, 9, 5), 0, 0));
/*  658:     */     
/*  659: 712 */     this.clientButtonPanel_d.add(this.sendToClientsButton_d, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(1, 1, 1, 1), 0, 0));
/*  660:     */     
/*  661: 714 */     this.clientButtonPanel_d.add(this.receiveFromClientsButton_d, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(1, 1, 1, 1), 0, 0));
/*  662:     */     
/*  663: 716 */     this.omnipresentPane_d.add(this.omniInternalPane_d, new GridBagConstraints(0, 6, 4, 1, 0.0D, 0.0D, 15, 2, new Insets(20, 5, 5, 5), 0, 0));
/*  664:     */     
/*  665: 718 */     this.omniInternalPane_d.add(this.findOmnipresentNodesButton_d, null);
/*  666: 719 */     this.omniInternalPane_d.add(this.findOmniLabel1_d, null);
/*  667: 720 */     this.omniInternalPane_d.add(this.findOmnipresentThreshold_d, null);
/*  668: 721 */     this.omniInternalPane_d.add(this.findOmnilabel2_d, null);
/*  669: 722 */     this.omnipresentPane_d.add(this.centralButtonPanel_d, new GridBagConstraints(1, 5, 1, 2, 0.0D, 0.0D, 11, 2, new Insets(1, 5, 9, 5), 0, 0));
/*  670:     */     
/*  671: 724 */     this.centralButtonPanel_d.add(this.receiveFromCentralButton_d, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(1, 1, 1, 1), 0, 0));
/*  672:     */     
/*  673: 726 */     this.centralButtonPanel_d.add(this.sendToCentralButton_d, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(1, 1, 1, 1), 0, 0));
/*  674:     */     
/*  675: 728 */     this.omnipresentPane_d.add(this.centralListPane_d, new GridBagConstraints2(2, 5, 1, 1, 1.0D, 1.0D, 15, 2, new Insets(5, 5, 5, 5), 0, 12));
/*  676:     */     
/*  677: 730 */     this.omnipresentPane_d.add(this.centralLabel_d, new GridBagConstraints2(2, 4, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 0, 0), 0, 0));
/*  678:     */     
/*  679:     */ 
/*  680: 733 */     this.mainTabbedPane_d.addTab("User Directed Clustering", this.userDirectedClusteringPane_d);
/*  681: 734 */     this.userDirectedClusteringPane_d.add(this.inputClusterFile_d, new GridBagConstraints2(1, 0, 2, 1, 0.0D, 0.0D, 10, 2, new Insets(5, 5, 5, 5), 0, 0));
/*  682:     */     
/*  683: 736 */     this.userDirectedClusteringPane_d.add(this.inputClusterFileSelectButton_d, new GridBagConstraints2(3, 0, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(5, 5, 5, 5), 0, 0));
/*  684:     */     
/*  685: 738 */     this.userDirectedClusteringPane_d.add(this.inputClusterLabel_d, new GridBagConstraints2(0, 0, 1, 1, 0.0D, 0.0D, 17, 2, new Insets(5, 5, 5, 5), 0, 0));
/*  686:     */     
/*  687: 740 */     this.userDirectedClusteringPane_d.add(this.lockClustersCheckbox_d, new GridBagConstraints2(1, 1, 1, 1, 0.0D, 0.0D, 14, 1, new Insets(0, 0, 0, 0), 0, 0));
/*  688:     */     
/*  689: 742 */     this.userDirectedClusteringPane_d.add(this.ClearClusterFile, new GridBagConstraints(3, 1, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 5, 0, 5), 0, 0));
/*  690:     */     
/*  691:     */ 
/*  692: 745 */     this.distPane.add(this.distClustEnableCB, new GridBagConstraints(0, 0, 2, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
/*  693:     */     
/*  694: 747 */     this.distPane.add(this.jLabel4, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(0, 0, 0, 16), 0, 0));
/*  695:     */     
/*  696: 749 */     this.distPane.add(this.jLabel5, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(0, 0, 0, 0), 0, 0));
/*  697:     */     
/*  698: 751 */     this.distPane.add(this.nameServerEF, new GridBagConstraints(1, 2, 2, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 0, 0, 0), 0, 0));
/*  699:     */     
/*  700: 753 */     this.distPane.add(this.jLabel6, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(0, 0, 0, 0), 0, 0));
/*  701:     */     
/*  702: 755 */     this.distPane.add(this.portEF, new GridBagConstraints(1, 3, 2, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 0, 0, 0), 0, 0));
/*  703:     */     
/*  704: 757 */     this.distPane.add(this.queryNS, new GridBagConstraints(0, 5, 3, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 0, 0, 0), 0, -7));
/*  705:     */     
/*  706: 759 */     this.distPane.add(this.jLabel7, new GridBagConstraints(0, 6, 3, 1, 0.0D, 0.0D, 10, 0, new Insets(8, 0, 1, 0), 27, 0));
/*  707:     */     
/*  708: 761 */     this.distPane.add(this.includeDistSvrsPB, new GridBagConstraints(0, 9, 2, 1, 0.0D, 0.0D, 17, 0, new Insets(9, 0, 0, 0), 0, -5));
/*  709:     */     
/*  710: 763 */     this.distPane.add(this.nameSpaceEF, new GridBagConstraints(1, 1, 2, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 0, 0, 0), 0, 0));
/*  711:     */     
/*  712: 765 */     this.distPane.add(this.jScrollPane1, new GridBagConstraints(0, 7, 3, 2, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 5, 0), 6, 58));
/*  713:     */     
/*  714: 767 */     this.distPane.add(this.jLabel8, new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(0, 0, 0, 5), 0, 0));
/*  715:     */     
/*  716: 769 */     this.distPane.add(this.UOWSzEF, new GridBagConstraints(1, 4, 2, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 0, 0, 0), 0, 0));
/*  717:     */     
/*  718: 771 */     this.distPane.add(this.deactivatePB, new GridBagConstraints(2, 9, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(2, 7, 0, 0), 0, -5));
/*  719:     */     
/*  720: 773 */     this.distPane.add(this.adaptiveEnableCB, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 1), -6, 0));
/*  721:     */     
/*  722: 775 */     this.jScrollPane1.getViewport().add(this.serverList, null);
/*  723: 776 */     this.configureMenu_d.add(this.configureOptionsMenuItem_d);
/*  724: 777 */     this.utilityMenu_d.add(this.utilityMeasurementCalc);
/*  725: 778 */     this.utilityMenu_d.add(this.clusteringUtilsMenu);
/*  726: 779 */     this.utilityMenu_d.add(this.fileUtilsMenu);
/*  727: 780 */     this.utilityMenu_d.add(this.menuShowDistributedTab);
/*  728:     */     
/*  729:     */ 
/*  730: 783 */     this.mainTabbedPane_d.setEnabledAt(this.mainTabbedPane_d.indexOfComponent(this.omnipresentPane_d), false);
/*  731: 784 */     this.mainTabbedPane_d.setEnabledAt(this.mainTabbedPane_d.indexOfComponent(this.librariesPane_d), false);
/*  732: 785 */     this.mainTabbedPane_d.setEnabledAt(this.mainTabbedPane_d.indexOfComponent(this.userDirectedClusteringPane_d), false);
/*  733: 786 */     this.runActionButton_d.setEnabled(false);
/*  734: 787 */     this.nextLevelGraphButton_d.setEnabled(false);
/*  735: 788 */     this.outputLastButton_d.setEnabled(false);
/*  736: 789 */     this.inputClusterFile_d.setEditable(false);
/*  737: 790 */     this.inputGraphFilename_d.setEditable(false);
/*  738: 791 */     this.visualizeButton_d.setEnabled(false);
/*  739:     */     
/*  740: 793 */     this.consolidateDriftersCB.setVisible(false);
/*  741:     */     
/*  742: 795 */     this.agglomOutputCB.addItem("Output Median Level");
/*  743: 796 */     this.agglomOutputCB.addItem("Output Detailed Level");
/*  744: 797 */     this.agglomOutputCB.addItem("Output Top Level");
/*  745: 798 */     this.agglomOutputCB.addItem("Output All Levels");
/*  746:     */     
/*  747:     */ 
/*  748:     */ 
/*  749:     */ 
/*  750:     */ 
/*  751: 804 */     setLastResultGraph(null);
/*  752:     */     
/*  753:     */ 
/*  754: 807 */     methodList = this.preferences_d.getGraphOutputFactory().getItemList();
/*  755: 808 */     for (int i = 0; i < methodList.length; i++) {
/*  756: 809 */       this.outputFileFormatList_d.addItem(methodList[i]);
/*  757:     */     }
/*  758: 811 */     String defaultOutput = this.preferences_d.getGraphOutputFactory().defaultOption;
/*  759: 812 */     this.outputFileFormatList_d.setSelectedItem(defaultOutput);
/*  760:     */   }
/*  761:     */   
/*  762:     */   public boolean isAgglomerativeTechnique()
/*  763:     */   {
/*  764: 824 */     String action = (String)this.actionList_d.getSelectedItem();
/*  765: 825 */     if (action.equals("Agglomerative Clustering")) {
/*  766: 826 */       return true;
/*  767:     */     }
/*  768: 828 */     return false;
/*  769:     */   }
/*  770:     */   
/*  771:     */   public boolean isUserDrivenTechnique()
/*  772:     */   {
/*  773: 838 */     String action = (String)this.actionList_d.getSelectedItem();
/*  774: 839 */     if (action.equals("User-Driven Clustering")) {
/*  775: 840 */       return true;
/*  776:     */     }
/*  777: 842 */     return false;
/*  778:     */   }
/*  779:     */   
/*  780:     */   private void setClusteringMethod(String method)
/*  781:     */   {
/*  782: 856 */     if (!method.getClass().getName().equals(method))
/*  783:     */     {
/*  784: 857 */       this.clusteringMethod_d = this.preferences_d.getClusteringMethodFactory().getMethod(method);
/*  785: 858 */       this.clusteringOptionsButton_d.setEnabled(this.clusteringMethod_d.isConfigurable());
/*  786: 859 */       this.configuration_d = this.clusteringMethod_d.getConfiguration();
/*  787: 860 */       if ((this.initialGraph_d != null) && (this.configuration_d != null)) {
/*  788: 861 */         this.configuration_d.init(this.initialGraph_d);
/*  789:     */       }
/*  790:     */     }
/*  791:     */   }
/*  792:     */   
/*  793:     */   public void fileExit_actionPerformed(ActionEvent e)
/*  794:     */   {
/*  795: 873 */     System.exit(0);
/*  796:     */   }
/*  797:     */   
/*  798:     */   public void helpAbout_actionPerformed(ActionEvent e)
/*  799:     */   {
/*  800: 883 */     BunchFrame_AboutBox dlg = new BunchFrame_AboutBox(this);
/*  801: 884 */     Dimension dlgSize = dlg.getPreferredSize();
/*  802: 885 */     Dimension frmSize = getSize();
/*  803: 886 */     Point loc = getLocation();
/*  804: 887 */     dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
/*  805: 888 */     dlg.setModal(true);
/*  806: 889 */     dlg.show();
/*  807:     */   }
/*  808:     */   
/*  809:     */   protected void processWindowEvent(WindowEvent e)
/*  810:     */   {
/*  811: 899 */     super.processWindowEvent(e);
/*  812: 900 */     if (e.getID() == 201) {
/*  813: 901 */       fileExit_actionPerformed(null);
/*  814:     */     }
/*  815:     */   }
/*  816:     */   
/*  817:     */   public String getDelims()
/*  818:     */   {
/*  819: 913 */     String delims = this.delimEF.getText();
/*  820: 914 */     boolean state = this.spaceDelimCB.isSelected();
/*  821: 915 */     if (state == true) {
/*  822: 916 */       delims = " " + delims;
/*  823:     */     }
/*  824: 917 */     state = this.tabDelimCB.isSelected();
/*  825: 918 */     if (state == true) {
/*  826: 919 */       delims = "\t" + delims;
/*  827:     */     }
/*  828: 921 */     return delims;
/*  829:     */   }
/*  830:     */   
/*  831:     */   boolean checkFile(String fileName)
/*  832:     */   {
/*  833: 926 */     File f = new File(fileName);
/*  834:     */     
/*  835: 928 */     return f.isFile();
/*  836:     */   }
/*  837:     */   
/*  838:     */   void selectGraphFileButton_d_actionPerformed(ActionEvent e)
/*  839:     */   {
/*  840: 940 */     String delims = this.delimEF.getText();
/*  841: 941 */     boolean state = this.spaceDelimCB.isSelected();
/*  842: 942 */     if (state == true) {
/*  843: 943 */       delims = " " + delims;
/*  844:     */     }
/*  845: 944 */     state = this.tabDelimCB.isSelected();
/*  846: 945 */     if (state == true) {
/*  847: 946 */       delims = "\t" + delims;
/*  848:     */     }
/*  849: 951 */     this.fileSelector_d.setVisible(true);
/*  850: 952 */     if (this.fileSelector_d.getFile() != null)
/*  851:     */     {
/*  852: 953 */       String filename = this.fileSelector_d.getDirectory() + this.fileSelector_d.getFile();
/*  853: 954 */       if (!checkFile(filename))
/*  854:     */       {
/*  855: 956 */         JOptionPane.showMessageDialog(this, "There is a problem with the selected MDG file - " + filename, "Error: Bad File Name", 0);
/*  856:     */         
/*  857:     */ 
/*  858:     */ 
/*  859: 960 */         return;
/*  860:     */       }
/*  861: 963 */       this.inputGraphFilename_d.setText(filename);
/*  862: 964 */       String parserClass = "dependency";
/*  863: 965 */       if ((filename.endsWith(".gxl")) || (filename.endsWith(".GXL"))) {
/*  864: 966 */         parserClass = "gxl";
/*  865:     */       }
/*  866: 968 */       Parser p = this.preferences_d.getParserFactory().getParser(parserClass);
/*  867: 969 */       p.setInput(filename);
/*  868: 970 */       p.setDelims(delims);
/*  869:     */       
/*  870: 972 */       this.initialGraph_d = ((Graph)p.parse());
/*  871: 979 */       if (p.hasReflexiveEdges())
/*  872:     */       {
/*  873: 982 */         int count = p.getReflexiveEdges();
/*  874: 983 */         Integer reflexiveEdgeCnt = new Integer(count);
/*  875:     */         
/*  876: 985 */         String msg = "Bunch has determined that your input\n";
/*  877: 986 */         msg = msg + "MDG contains " + reflexiveEdgeCnt.toString() + " reflexive edges.\n";
/*  878: 987 */         msg = msg + "Bunch assumes cohesiveness in modules/classes, thus these\n";
/*  879: 988 */         msg = msg + "edges will be removed from the custering process.";
/*  880:     */         
/*  881: 990 */         JOptionPane.showMessageDialog(this, msg, "Warning: Found Reflexive Edges", 2);
/*  882:     */       }
/*  883: 995 */       if (this.configuration_d != null) {
/*  884: 996 */         this.configuration_d.init(this.initialGraph_d);
/*  885:     */       }
/*  886:1003 */       String cmd = (String)this.actionList_d.getSelectedItem();
/*  887:1004 */       if (cmd.equals("Agglomerative Clustering"))
/*  888:     */       {
/*  889:1006 */         if (this.initialGraph_d != null) {
/*  890:1007 */           this.initialGraph_d.setIsClusterTree(true);
/*  891:     */         }
/*  892:1008 */         this.nextLevelGraphButton_d.setEnabled(false);
/*  893:     */       }
/*  894:     */       else
/*  895:     */       {
/*  896:1012 */         if (this.initialGraph_d != null) {
/*  897:1013 */           this.initialGraph_d.setIsClusterTree(false);
/*  898:     */         }
/*  899:1014 */         this.nextLevelGraphButton_d.setEnabled(true);
/*  900:     */       }
/*  901:1016 */       this.outputClusterFilename_d.setText(filename);
/*  902:1017 */       clearGUIElements(false);
/*  903:     */     }
/*  904:     */   }
/*  905:     */   
/*  906:     */   public void clearGUIElements(boolean nextLevel)
/*  907:     */   {
/*  908:1029 */     this.inputClusterFile_d.setText("");
/*  909:1030 */     this.fileBasicName_d = this.fileSelector_d.getFile();
/*  910:1031 */     this.standardNodeListModel_d.removeAllElements();
/*  911:1032 */     this.clientsListModel_d.removeAllElements();
/*  912:1033 */     this.suppliersListModel_d.removeAllElements();
/*  913:1034 */     this.centralListModel_d.removeAllElements();
/*  914:1035 */     this.librariesListModel_d.removeAllElements();
/*  915:1036 */     Node[] nl = this.initialGraph_d.getNodes();
/*  916:1037 */     for (int i = 0; i < nl.length; i++) {
/*  917:1038 */       this.standardNodeListModel_d.addElement(nl[i].getName());
/*  918:     */     }
/*  919:1040 */     this.mainTabbedPane_d.setEnabledAt(this.mainTabbedPane_d.indexOfComponent(this.omnipresentPane_d), true);
/*  920:1041 */     this.mainTabbedPane_d.setEnabledAt(this.mainTabbedPane_d.indexOfComponent(this.librariesPane_d), true);
/*  921:1042 */     this.mainTabbedPane_d.setEnabledAt(this.mainTabbedPane_d.indexOfComponent(this.userDirectedClusteringPane_d), true);
/*  922:     */     
/*  923:1044 */     this.runActionButton_d.setEnabled(true);
/*  924:1045 */     if (!nextLevel)
/*  925:     */     {
/*  926:1046 */       this.nextLevelGraphButton_d.setEnabled(false);
/*  927:1047 */       this.outputLastButton_d.setEnabled(false);
/*  928:1048 */       this.visualizeButton_d.setEnabled(false);
/*  929:     */     }
/*  930:     */   }
/*  931:     */   
/*  932:     */   void selectOutputFileButton_d_actionPerformed(ActionEvent e)
/*  933:     */   {
/*  934:1061 */     this.fileSelector_d.setVisible(true);
/*  935:1062 */     if (this.fileSelector_d.getFile() != null)
/*  936:     */     {
/*  937:1063 */       String filename = this.fileSelector_d.getDirectory() + this.fileSelector_d.getFile();
/*  938:1064 */       this.outputClusterFilename_d.setText(filename);
/*  939:1065 */       this.fileBasicName_d = this.fileSelector_d.getFile();
/*  940:     */     }
/*  941:     */   }
/*  942:     */   
/*  943:     */   void configureOptionsMenuItem_d_actionPerformed(ActionEvent e) {}
/*  944:     */   
/*  945:     */   void clusteringOptionsButton_d_actionPerformed(ActionEvent e)
/*  946:     */   {
/*  947:1087 */     ClusteringConfigurationDialog dlg = null;
/*  948:     */     try
/*  949:     */     {
/*  950:1090 */       dlg = (ClusteringConfigurationDialog)Beans.instantiate(null, this.clusteringMethod_d.getConfigurationDialogName());
/*  951:1091 */       dlg.setModal(true);
/*  952:1092 */       dlg.setParentFrame(this);
/*  953:1093 */       dlg.setTitle("Clustering Algorithm Configuration");
/*  954:1094 */       dlg.setGraph(this.initialGraph_d);
/*  955:1095 */       dlg.setConfiguration(this.configuration_d);
/*  956:1096 */       dlg.jbInit();
/*  957:1097 */       dlg.pack();
/*  958:     */     }
/*  959:     */     catch (Exception ex)
/*  960:     */     {
/*  961:1100 */       ex.printStackTrace();
/*  962:     */     }
/*  963:1102 */     if ((this.inputGraphFilename_d.getText() == null) || (this.inputGraphFilename_d.getText().equals("")) || (this.initialGraph_d == null))
/*  964:     */     {
/*  965:1106 */       JOptionPane.showMessageDialog(this, "Error: missing input graph.", "MQ Calculation: Missing Parameter", 0);
/*  966:     */       
/*  967:     */ 
/*  968:1109 */       return;
/*  969:     */     }
/*  970:1111 */     Dimension dlgSize = dlg.getPreferredSize();
/*  971:1112 */     Dimension frmSize = getSize();
/*  972:1113 */     Point loc = getLocation();
/*  973:1114 */     dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
/*  974:1115 */     dlg.setVisible(true);
/*  975:     */     
/*  976:1117 */     this.clusteringMethod_d.setConfiguration(dlg.getConfiguration());
/*  977:     */   }
/*  978:     */   
/*  979:     */   void clusteringMethodList_d_itemStateChanged(ItemEvent e)
/*  980:     */   {
/*  981:1126 */     setClusteringMethod((String)this.clusteringMethodList_d.getSelectedItem());
/*  982:1127 */     setupClusteringOptions();
/*  983:     */   }
/*  984:     */   
/*  985:     */   void inputClusterFileSelectButton_d_actionPerformed(ActionEvent e)
/*  986:     */   {
/*  987:1140 */     if ((this.inputGraphFilename_d.getText() == null) || (this.inputGraphFilename_d.getText().equals("")))
/*  988:     */     {
/*  989:1142 */       JOptionPane.showMessageDialog(this, "Error: missing input graph. \n Select an input graph file first.", "Error: Missing Parameter", 0);
/*  990:     */       
/*  991:     */ 
/*  992:1145 */       return;
/*  993:     */     }
/*  994:1147 */     this.fileSelector_d.setVisible(true);
/*  995:1148 */     if (this.fileSelector_d.getFile() != null)
/*  996:     */     {
/*  997:1149 */       String filename = this.fileSelector_d.getDirectory() + this.fileSelector_d.getFile();
/*  998:1150 */       this.inputClusterFile_d.setText(filename);
/*  999:     */       
/* 1000:1152 */       Parser p = this.preferences_d.getParserFactory().getParser("cluster");
/* 1001:1153 */       p.setInput(filename);
/* 1002:1154 */       p.setObject(this.initialGraph_d);
/* 1003:1155 */       p.parse();
/* 1004:1156 */       this.lockClustersCheckbox_d.setEnabled(true);
/* 1005:     */     }
/* 1006:     */   }
/* 1007:     */   
/* 1008:     */   public int getUOWSz()
/* 1009:     */   {
/* 1010:     */     try
/* 1011:     */     {
/* 1012:1169 */       Integer i = new Integer(this.UOWSzEF.getText());
/* 1013:1170 */       return i.intValue();
/* 1014:     */     }
/* 1015:     */     catch (Exception e) {}
/* 1016:1172 */     return 5;
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   public boolean getAdaptiveEnableFlag()
/* 1020:     */   {
/* 1021:1184 */     return this.adaptiveEnableCB.isSelected();
/* 1022:     */   }
/* 1023:     */   
/* 1024:     */   public CallbackImpl getSvrCallback()
/* 1025:     */   {
/* 1026:1196 */     return this.svrCallback;
/* 1027:     */   }
/* 1028:     */   
/* 1029:     */   void runActionButton_d_actionPerformed(ActionEvent e)
/* 1030:     */   {
/* 1031:1213 */     if ((this.outputClusterFilename_d.getText() == null) || (this.outputClusterFilename_d.getText().equals("")))
/* 1032:     */     {
/* 1033:1216 */       JOptionPane.showMessageDialog(this, "Error: missing input graph file\nor output graph filename.", "MQ Calculation: Missing Parameter", 0);
/* 1034:     */       
/* 1035:     */ 
/* 1036:1219 */       return;
/* 1037:     */     }
/* 1038:1225 */     String method = (String)this.clusteringMethodList_d.getSelectedItem();
/* 1039:1226 */     String outputMethod = (String)this.outputFileFormatList_d.getSelectedItem();
/* 1040:     */     
/* 1041:1228 */     this.mainTabbedPane_d.setSelectedComponent(this.bunchSettingsPanel_d);
/* 1042:1233 */     if (this.lockClustersCheckbox_d.isSelected()) {
/* 1043:1234 */       this.initialGraph_d.setDoubleLocks(true);
/* 1044:     */     }
/* 1045:1237 */     int[] clust = this.initialGraph_d.getClusters();
/* 1046:1238 */     boolean[] locks = this.initialGraph_d.getLocks();
/* 1047:1239 */     for (int i = 0; i < clust.length; i++) {
/* 1048:1240 */       if (clust[i] != -1) {
/* 1049:1241 */         locks[i] = true;
/* 1050:     */       }
/* 1051:     */     }
/* 1052:1245 */     if ((this.librariesListModel_d.size() > 0) || (this.suppliersListModel_d.size() > 0) || (this.clientsListModel_d.size() > 0) || (this.centralListModel_d.size() > 0)) {
/* 1053:1247 */       arrangeLibrariesClientsAndSuppliers();
/* 1054:     */     }
/* 1055:1250 */     this.clusteringMethod_d.initialize();
/* 1056:1251 */     this.clusteringMethod_d.setGraph(this.initialGraph_d.cloneGraph());
/* 1057:1252 */     this.graphOutput_d = this.preferences_d.getGraphOutputFactory().getOutput(outputMethod);
/* 1058:1253 */     this.graphOutput_d.setBaseName(this.outputClusterFilename_d.getText());
/* 1059:1254 */     this.graphOutput_d.setBasicName(this.fileBasicName_d);
/* 1060:1255 */     configureOptions();
/* 1061:     */     
/* 1062:     */ 
/* 1063:     */ 
/* 1064:     */ 
/* 1065:     */ 
/* 1066:     */ 
/* 1067:     */ 
/* 1068:1263 */     boolean doDistrib = this.distClustEnableCB.isSelected();
/* 1069:1264 */     if (doDistrib == true)
/* 1070:     */     {
/* 1071:1269 */       Manager lbManager = new Manager();
/* 1072:1270 */       DistribInit diMsg = new DistribInit();
/* 1073:1271 */       diMsg.theGraph = this.initialGraph_d;
/* 1074:1272 */       diMsg.clusteringTechnique = method;
/* 1075:1273 */       diMsg.objFunction = ((String)this.ClusteringAlgEF.getSelectedItem());
/* 1076:1274 */       diMsg.config = this.configuration_d;
/* 1077:1275 */       diMsg.bp = this.preferences_d;
/* 1078:     */       
/* 1079:1277 */       lbManager.baseUOWSz = getUOWSz();
/* 1080:1278 */       lbManager.useAdaptiveAlg = getAdaptiveEnableFlag();
/* 1081:1283 */       if (this.activeServerVector != null) {
/* 1082:1284 */         for (int i = 0; i < this.activeServerVector.size(); i++)
/* 1083:     */         {
/* 1084:1286 */           Binding b = (Binding)this.activeServerVector.elementAt(i);
/* 1085:1287 */           diMsg.svrID = lbManager.createNewServer();
/* 1086:1288 */           diMsg.svrName = b.getName();
/* 1087:1289 */           diMsg.adaptiveEnabled = getAdaptiveEnableFlag();
/* 1088:1290 */           byte[] so = BunchUtilities.toByteArray(diMsg);
boolean rc;

/* 1089:1291 */           if (so != null)
/* 1090:     */           {
/* 1091:1293 */             BunchSvrMsg bsm = (BunchSvrMsg)b.getObject();
/* 1092:     */             try
/* 1093:     */             {
/* 1094:1295 */               rc = bsm.invokeMessage("Init", so);
/* 1095:     */             }
/* 1096:     */             catch (Exception ex)
/* 1097:     */             {
/* 1098:     */               
/* 1099:1298 */               JOptionPane.showMessageDialog(this, ex.toString(), "Error Initializing Server: " + b.getName(), 0);
/* 1100:     */             }
/* 1101:     */           }
/* 1102:     */         }
/* 1103:     */       }
/* 1104:     */       try
/* 1105:     */       {
/* 1106:1310 */         this.bevent = new BunchEvent();
/* 1107:     */         
/* 1108:1312 */         DistributedHCClusteringMethod dcm = new DistributedHCClusteringMethod();
/* 1109:     */         
/* 1110:     */ 
/* 1111:1315 */         dcm.setEventObject(this.bevent);
/* 1112:1316 */         dcm.setActiveServerVector(this.activeServerVector);
/* 1113:     */         
/* 1114:1318 */         lbManager.baseUOWSz = getUOWSz();
/* 1115:1319 */         lbManager.useAdaptiveAlg = getAdaptiveEnableFlag();
/* 1116:1320 */         this.svrCallback.bevent = this.bevent;
/* 1117:1321 */         this.svrCallback.lbManager = lbManager;
/* 1118:     */         
/* 1119:     */ 
/* 1120:     */ 
/* 1121:     */ 
/* 1122:     */ 
/* 1123:     */ 
/* 1124:1328 */         NAHCConfiguration hcc = (NAHCConfiguration)dcm.getConfiguration();
/* 1125:     */         
/* 1126:1330 */         hcc.setNumOfIterations(1);
/* 1127:1331 */         hcc.setThreshold(1.0D);
/* 1128:1332 */         hcc.setRandomizePct(100);
/* 1129:1333 */         hcc.setMinPctToConsider(0);
/* 1130:     */         
/* 1131:1335 */         dcm.setConfiguration(this.configuration_d);
/* 1132:     */         
/* 1133:     */ 
/* 1134:     */ 
/* 1135:     */ 
/* 1136:1340 */         dcm.initialize();
/* 1137:1341 */         dcm.setGraph(this.initialGraph_d.cloneGraph());
/* 1138:     */         
/* 1139:     */ 
/* 1140:     */ 
/* 1141:     */ 
/* 1142:     */ 
/* 1143:1347 */         DistribClusteringProgressDlg dlg = null;
/* 1144:1348 */         dlg = new DistribClusteringProgressDlg(this, "Distributed Clustering " + this.initialGraph_d.getNumberOfNodes() + " nodes...", true, dcm);
/* 1145:     */         
/* 1146:1350 */         Dimension dlgSize = dlg.getPreferredSize();
/* 1147:1351 */         Dimension frmSize = getSize();
/* 1148:1352 */         Point loc = getLocation();
/* 1149:1353 */         dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
/* 1150:1354 */         dlg.setVisible(true);
/* 1151:     */       }
/* 1152:     */       catch (Exception ex)
/* 1153:     */       {
/* 1154:1362 */         JOptionPane.showMessageDialog(this, ex.toString(), "Error Doing Distributed Clustering: " + ex.toString(), 0);
/* 1155:     */       }
/* 1156:     */     }
/* 1157:     */     else
/* 1158:     */     {
/* 1159:1376 */       ClusteringProgressDialog dlg = null;
/* 1160:1377 */       dlg = new ClusteringProgressDialog(this, "Clustering " + this.initialGraph_d.getNumberOfNodes() + " nodes...", true);
/* 1161:     */       
/* 1162:1379 */       Dimension dlgSize = dlg.getPreferredSize();
/* 1163:1380 */       Dimension frmSize = getSize();
/* 1164:1381 */       Point loc = getLocation();
/* 1165:1382 */       dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
/* 1166:     */       
/* 1167:1384 */       dlg.setModal(false);
/* 1168:1385 */       dlg.setVisible(true);
/* 1169:1386 */       dlg.startClustering();
/* 1170:1387 */       dlg.setModal(true);
/* 1171:1394 */       if (!this.actionList_d.getSelectedItem().equals("Agglomerative Clustering")) {
/* 1172:1395 */         this.nextLevelGraphButton_d.setEnabled(true);
/* 1173:     */       }
/* 1174:1396 */       this.outputLastButton_d.setEnabled(true);
/* 1175:1401 */       if (this.outputFileFormatList_d.getSelectedItem().equals("Dotty")) {
/* 1176:1402 */         this.visualizeButton_d.setEnabled(true);
/* 1177:     */       }
/* 1178:     */     }
/* 1179:     */   }
/* 1180:     */   
/* 1181:     */   public void arrangeLibrariesClientsAndSuppliers()
/* 1182:     */   {
/* 1183:1415 */     Node[] nodeList = null;
/* 1184:1417 */     if (this.initialGraph_d.getOriginalNodes() != null) {
/* 1185:1418 */       nodeList = this.initialGraph_d.getOriginalNodes();
/* 1186:     */     } else {
/* 1187:1420 */       nodeList = this.initialGraph_d.getNodes();
/* 1188:     */     }
/* 1189:1422 */     Node[] originalList = (Node[])nodeList.clone();
/* 1190:1423 */     Node[] origListCopy = (Node[])nodeList.clone();
/* 1191:1425 */     for (int j = 0; j < originalList.length; j++) {
/* 1192:1426 */       originalList[j].setType(0);
/* 1193:     */     }
/* 1194:1430 */     for (int j = 0; j < originalList.length; j++)
/* 1195:     */     {
/* 1196:1431 */       for (int i = 0; i < this.suppliersListModel_d.size(); i++)
/* 1197:     */       {
/* 1198:1432 */         String name = originalList[j].getName();
/* 1199:1433 */         if (name.equals((String)this.suppliersListModel_d.elementAt(i)))
/* 1200:     */         {
/* 1201:1434 */           originalList[j].setType(2);
/* 1202:1435 */           break;
/* 1203:     */         }
/* 1204:     */       }
/* 1205:1438 */       for (int i = 0; i < this.clientsListModel_d.size(); i++)
/* 1206:     */       {
/* 1207:1439 */         String name = originalList[j].getName();
/* 1208:1440 */         if (name.equals((String)this.clientsListModel_d.elementAt(i)))
/* 1209:     */         {
/* 1210:1441 */           originalList[j].setType(1);
/* 1211:1442 */           break;
/* 1212:     */         }
/* 1213:     */       }
/* 1214:1445 */       for (int i = 0; i < this.centralListModel_d.size(); i++)
/* 1215:     */       {
/* 1216:1446 */         String name = originalList[j].getName();
/* 1217:1447 */         if (name.equals((String)this.centralListModel_d.elementAt(i)))
/* 1218:     */         {
/* 1219:1448 */           originalList[j].setType(3);
/* 1220:1449 */           break;
/* 1221:     */         }
/* 1222:     */       }
/* 1223:1453 */       for (int i = 0; i < this.librariesListModel_d.size(); i++)
/* 1224:     */       {
/* 1225:1454 */         String name = originalList[j].getName();
/* 1226:1455 */         if (name.equals((String)this.librariesListModel_d.elementAt(i)))
/* 1227:     */         {
/* 1228:1456 */           originalList[j].setType(4);
/* 1229:1457 */           break;
/* 1230:     */         }
/* 1231:     */       }
/* 1232:     */     }
/* 1233:1462 */     int deadNodes = 0;
/* 1234:1464 */     for (int i = 0; i < originalList.length; i++) {
/* 1235:1465 */       if (originalList[i].getType() == 0)
/* 1236:     */       {
/* 1237:1466 */         boolean noNormalDeps = true;
/* 1238:1467 */         int[] tmpDeps = originalList[i].getDependencies();
/* 1239:1468 */         int[] tmpBeDeps = originalList[i].getBackEdges();
/* 1240:1469 */         int client = 0;
/* 1241:1470 */         int supplier = 0;
/* 1242:1471 */         int central = 0;
/* 1243:1472 */         int library = 0;
/* 1244:1474 */         for (int j = 0; j < tmpDeps.length; j++)
/* 1245:     */         {
/* 1246:1476 */           if ((originalList[tmpDeps[j]].getType() == 0) || (originalList[tmpDeps[j]].getType() >= 128))
/* 1247:     */           {
/* 1248:1479 */             noNormalDeps = false;
/* 1249:1480 */             break;
/* 1250:     */           }
/* 1251:1484 */           switch (originalList[tmpDeps[j]].getType())
/* 1252:     */           {
/* 1253:     */           case 1: 
/* 1254:1487 */             client++; break;
/* 1255:     */           case 2: 
/* 1256:1489 */             supplier++; break;
/* 1257:     */           case 3: 
/* 1258:1491 */             central++; break;
/* 1259:     */           case 4: 
/* 1260:1493 */             library++;
/* 1261:     */           }
/* 1262:     */         }
/* 1263:1497 */         for (int j = 0; j < tmpBeDeps.length; j++)
/* 1264:     */         {
/* 1265:1499 */           if ((originalList[tmpBeDeps[j]].getType() == 0) || (originalList[tmpBeDeps[j]].getType() >= 128))
/* 1266:     */           {
/* 1267:1502 */             noNormalDeps = false;
/* 1268:1503 */             break;
/* 1269:     */           }
/* 1270:1507 */           switch (originalList[tmpBeDeps[j]].getType())
/* 1271:     */           {
/* 1272:     */           case 1: 
/* 1273:1510 */             client++; break;
/* 1274:     */           case 2: 
/* 1275:1512 */             supplier++; break;
/* 1276:     */           case 3: 
/* 1277:1514 */             central++; break;
/* 1278:     */           case 4: 
/* 1279:1516 */             library++;
/* 1280:     */           }
/* 1281:     */         }
/* 1282:1520 */         if (noNormalDeps == true)
/* 1283:     */         {
/* 1284:1522 */           deadNodes++;
/* 1285:1523 */           int n1 = Math.max(client, supplier);
/* 1286:1524 */           int n2 = Math.max(central, library);
/* 1287:1525 */           int max = Math.max(n1, n2);
/* 1288:1526 */           int type = 1;
/* 1289:1528 */           if (max == client) {
/* 1290:1528 */             type = 1;
/* 1291:     */           }
/* 1292:1529 */           if (max == supplier) {
/* 1293:1529 */             type = 2;
/* 1294:     */           }
/* 1295:1530 */           if (max == central) {
/* 1296:1530 */             type = 3;
/* 1297:     */           }
/* 1298:1531 */           if (max == library) {
/* 1299:1531 */             type = 4;
/* 1300:     */           }
/* 1301:1532 */           originalList[i].setType(128 + max);
/* 1302:     */         }
/* 1303:     */       }
/* 1304:     */     }
/* 1305:1538 */     nodeList = new Node[originalList.length - (this.clientsListModel_d.size() + this.suppliersListModel_d.size() + deadNodes + this.centralListModel_d.size() + this.librariesListModel_d.size())];
/* 1306:     */     
/* 1307:     */ 
/* 1308:1541 */     int j = 0;
/* 1309:     */     
/* 1310:1543 */     Hashtable normal = new Hashtable();
/* 1311:1546 */     for (int i = 0; i < originalList.length; i++) {
/* 1312:1547 */       if (originalList[i].getType() == 0)
/* 1313:     */       {
/* 1314:1548 */         normal.put(new Integer(originalList[i].getId()), new Integer(j));
/* 1315:1549 */         nodeList[(j++)] = originalList[i].cloneNode();
/* 1316:     */       }
/* 1317:     */     }
/* 1318:1554 */     for (int i = 0; i < nodeList.length; i++)
/* 1319:     */     {
/* 1320:1556 */       nodeList[i].nodeID = i;
/* 1321:1557 */       int[] deps = nodeList[i].getDependencies();
/* 1322:1558 */       int[] beDeps = nodeList[i].getBackEdges();
/* 1323:1559 */       int[] weight = nodeList[i].getWeights();
/* 1324:1560 */       int[] beWeight = nodeList[i].getBeWeights();
/* 1325:1561 */       int depsRemoveCount = 0;
/* 1326:1562 */       int beDeptsRemoveCount = 0;
/* 1327:1565 */       for (int z = 0; z < deps.length; z++)
/* 1328:     */       {
/* 1329:1567 */         Integer tmpAssoc = (Integer)normal.get(new Integer(deps[z]));
/* 1330:1568 */         if (tmpAssoc == null)
/* 1331:     */         {
/* 1332:1569 */           deps[z] = -1;
/* 1333:1570 */           depsRemoveCount++;
/* 1334:     */         }
/* 1335:     */         else
/* 1336:     */         {
/* 1337:1572 */           deps[z] = tmpAssoc.intValue();
/* 1338:     */         }
/* 1339:     */       }
/* 1340:1576 */       for (int z = 0; z < beDeps.length; z++)
/* 1341:     */       {
/* 1342:1577 */         Integer tmpAssoc = (Integer)normal.get(new Integer(beDeps[z]));
/* 1343:1578 */         if (tmpAssoc == null)
/* 1344:     */         {
/* 1345:1579 */           beDeps[z] = -1;
/* 1346:1580 */           beDeptsRemoveCount++;
/* 1347:     */         }
/* 1348:     */         else
/* 1349:     */         {
/* 1350:1582 */           beDeps[z] = tmpAssoc.intValue();
/* 1351:     */         }
/* 1352:     */       }
/* 1353:1586 */       if (depsRemoveCount > 0)
/* 1354:     */       {
/* 1355:1588 */         int[] newDeps = new int[deps.length - depsRemoveCount];
/* 1356:1589 */         int[] newWeight = new int[deps.length - depsRemoveCount];
/* 1357:     */         
/* 1358:1591 */         int pos = 0;
/* 1359:1592 */         for (int z = 0; z < deps.length; z++) {
/* 1360:1593 */           if (deps[z] != -1)
/* 1361:     */           {
/* 1362:1594 */             newDeps[pos] = deps[z];
/* 1363:1595 */             newWeight[pos] = weight[z];
/* 1364:1596 */             pos++;
/* 1365:     */           }
/* 1366:     */         }
/* 1367:1598 */         deps = newDeps;
/* 1368:1599 */         weight = newWeight;
/* 1369:     */       }
/* 1370:1602 */       if (beDeptsRemoveCount > 0)
/* 1371:     */       {
/* 1372:1604 */         int[] newBeDeps = new int[beDeps.length - beDeptsRemoveCount];
/* 1373:1605 */         int[] newBeWeight = new int[beDeps.length - beDeptsRemoveCount];
/* 1374:     */         
/* 1375:1607 */         int pos = 0;
/* 1376:1608 */         for (int z = 0; z < beDeps.length; z++) {
/* 1377:1609 */           if (beDeps[z] != -1)
/* 1378:     */           {
/* 1379:1610 */             newBeDeps[pos] = beDeps[z];
/* 1380:1611 */             newBeWeight[pos] = beWeight[z];
/* 1381:1612 */             pos++;
/* 1382:     */           }
/* 1383:     */         }
/* 1384:1614 */         beDeps = newBeDeps;
/* 1385:1615 */         beWeight = newBeWeight;
/* 1386:     */       }
/* 1387:1618 */       nodeList[i].setDependencies(deps);
/* 1388:1619 */       nodeList[i].setWeights(weight);
/* 1389:1620 */       nodeList[i].setBackEdges(beDeps);
/* 1390:1621 */       nodeList[i].setBeWeights(beWeight);
/* 1391:     */     }
/* 1392:1625 */     this.initialGraph_d.initGraph(nodeList.length);
/* 1393:1626 */     this.initialGraph_d.clear();
/* 1394:1627 */     this.initialGraph_d.setNodes(nodeList);
/* 1395:     */     
/* 1396:1629 */     this.initialGraph_d.setOriginalNodes(origListCopy);
/* 1397:     */   }
/* 1398:     */   
/* 1399:     */   public void debugDump(int[] b, int[] a)
/* 1400:     */   {
/* 1401:1638 */     System.out.print("Before: ");
/* 1402:1639 */     for (int i = 0; i < b.length; i++) {
/* 1403:1640 */       System.out.print(b[i] + " ");
/* 1404:     */     }
/* 1405:1641 */     System.out.println();
/* 1406:1642 */     System.out.print("After: ");
/* 1407:1643 */     for (int i = 0; i < a.length; i++) {
/* 1408:1644 */       System.out.print(a[i] + " ");
/* 1409:     */     }
/* 1410:1645 */     System.out.println();
/* 1411:1646 */     System.out.println();
/* 1412:     */   }
/* 1413:     */   
/* 1414:     */   public void setLastResultGraph(Graph g)
/* 1415:     */   {
/* 1416:1660 */     if (g == null)
/* 1417:     */     {
/* 1418:1661 */       this.outputLastButton_d.setEnabled(false);
/* 1419:1662 */       this.nextLevelGraphButton_d.setEnabled(false);
/* 1420:     */     }
/* 1421:     */     else
/* 1422:     */     {
/* 1423:1665 */       this.outputLastButton_d.setEnabled(true);
/* 1424:1666 */       if (!this.actionList_d.getSelectedItem().equals("Agglomerative Clustering")) {
/* 1425:1667 */         this.nextLevelGraphButton_d.setEnabled(true);
/* 1426:     */       }
/* 1427:     */     }
/* 1428:1669 */     this.lastResultGraph_d = g;
/* 1429:     */   }
/* 1430:     */   
/* 1431:     */   public Graph getLastResultGraph()
/* 1432:     */   {
/* 1433:1681 */     return this.lastResultGraph_d;
/* 1434:     */   }
/* 1435:     */   
/* 1436:     */   public GraphOutput getGraphOutput()
/* 1437:     */   {
/* 1438:1693 */     return this.graphOutput_d;
/* 1439:     */   }
/* 1440:     */   
/* 1441:     */   public ClusteringMethod getClusteringMethod()
/* 1442:     */   {
/* 1443:1705 */     return this.clusteringMethod_d;
/* 1444:     */   }
/* 1445:     */   
/* 1446:     */   public void configureOptions()
/* 1447:     */   {
/* 1448:1716 */     String s = (String)this.agglomOutputCB.getSelectedItem();
/* 1449:1721 */     if (s.equals("Output All Levels")) {
/* 1450:1722 */       this.graphOutput_d.setOutputTechnique(3);
/* 1451:1723 */     } else if (s.equals("Output Median Level")) {
/* 1452:1724 */       this.graphOutput_d.setOutputTechnique(2);
/* 1453:1725 */     } else if (s.equals("Output Top Level")) {
/* 1454:1726 */       this.graphOutput_d.setOutputTechnique(1);
/* 1455:1727 */     } else if (s.equals("Output Detailed Level")) {
/* 1456:1728 */       this.graphOutput_d.setOutputTechnique(4);
/* 1457:     */     }
/* 1458:1730 */     this.graphOutput_d.setNestedLevels(this.outputTreeCB.isSelected());
/* 1459:     */   }
/* 1460:     */   
/* 1461:     */   public String getOutputMethod()
/* 1462:     */   {
/* 1463:1739 */     return (String)this.outputFileFormatList_d.getSelectedItem();
/* 1464:     */   }
/* 1465:     */   
/* 1466:     */   public Graph getInitalGraph()
/* 1467:     */   {
/* 1468:1749 */     return this.initialGraph_d;
/* 1469:     */   }
/* 1470:     */   
/* 1471:     */   void nextLevelGraphButton_d_actionPerformed(ActionEvent e)
/* 1472:     */   {
/* 1473:1765 */     if (getLastResultGraph() == null) {
/* 1474:1766 */       throw new RuntimeException("Error:\n Result graph was null but output button \nwas enabled!");
/* 1475:     */     }
/* 1476:1768 */     Graph g = getLastResultGraph();
/* 1477:1769 */     int[] cNames = g.getClusterNames();
/* 1478:1770 */     if (cNames.length == 1)
/* 1479:     */     {
/* 1480:1771 */       JOptionPane.showMessageDialog(this, "Already only one cluster present.\nCan't create another level.", "Next Level Cluster Generation", 0);
/* 1481:     */       
/* 1482:     */ 
/* 1483:1774 */       return;
/* 1484:     */     }
/* 1485:1777 */     NextLevelGraph nextL = new NextLevelGraph();
/* 1486:1778 */     Graph newG = nextL.genNextLevelGraph(g);
/* 1487:1779 */     this.initialGraph_d = newG;
/* 1488:1780 */     this.initialGraph_d.setPreviousLevelGraph(g);
/* 1489:1781 */     this.initialGraph_d.setGraphLevel(g.getGraphLevel() + 1);
/* 1490:     */     
/* 1491:1783 */     this.outputClusterFilename_d.setText(this.outputClusterFilename_d.getText() + "L" + this.initialGraph_d.getGraphLevel());
/* 1492:1784 */     clearGUIElements(true);
/* 1493:     */   }
/* 1494:     */   
/* 1495:     */   void outputLastButton_d_actionPerformed(ActionEvent e)
/* 1496:     */   {
/* 1497:1797 */     if (getLastResultGraph() == null) {
/* 1498:1798 */       throw new RuntimeException("Error:\n Result graph was null but output button \nwas enabled!");
/* 1499:     */     }
/* 1500:1800 */     if ((this.outputClusterFilename_d.getText() == null) || (this.outputClusterFilename_d.getText().equals("")))
/* 1501:     */     {
/* 1502:1803 */       JOptionPane.showMessageDialog(this, "Error: Missing output graph filename.", "Output Graph: Missing Parameter", 0);
/* 1503:     */       
/* 1504:     */ 
/* 1505:1806 */       return;
/* 1506:     */     }
/* 1507:1809 */     String outputMethod = (String)this.outputFileFormatList_d.getSelectedItem();
/* 1508:1810 */     this.graphOutput_d = this.preferences_d.getGraphOutputFactory().getOutput(outputMethod);
/* 1509:1811 */     this.graphOutput_d.setBaseName(this.outputClusterFilename_d.getText());
/* 1510:1812 */     this.graphOutput_d.setBasicName(this.fileBasicName_d);
/* 1511:1813 */     this.graphOutput_d.setCurrentName(this.outputClusterFilename_d.getText());
/* 1512:1814 */     this.graphOutput_d.setGraph(getLastResultGraph());
/* 1513:1815 */     this.graphOutput_d.write();
/* 1514:     */   }
/* 1515:     */   
/* 1516:     */   void receiveFromSuppliersButton_d_actionPerformed(ActionEvent e)
/* 1517:     */   {
/* 1518:1825 */     if (this.suppliersList_d.getSelectedIndex() != -1)
/* 1519:     */     {
/* 1520:1826 */       String element = (String)this.suppliersListModel_d.elementAt(this.suppliersList_d.getSelectedIndex());
/* 1521:1827 */       this.suppliersList_d.setSelectedIndex(-1);
/* 1522:1828 */       this.standardNodeList_d.setSelectedIndex(-1);
/* 1523:1829 */       this.standardNodeListModel_d.addElement(element);
/* 1524:1830 */       this.suppliersListModel_d.removeElement(element);
/* 1525:1831 */       this.standardNodeList_d.revalidate();
/* 1526:1832 */       this.standardNodeListPane_d.revalidate();
/* 1527:1833 */       this.suppliersList_d.revalidate();
/* 1528:1834 */       this.suppliersListPane_d.revalidate();
/* 1529:     */     }
/* 1530:     */   }
/* 1531:     */   
/* 1532:     */   void sendToSuppliersButton_d_actionPerformed(ActionEvent e)
/* 1533:     */   {
/* 1534:1845 */     if (this.standardNodeList_d.getSelectedIndex() != -1)
/* 1535:     */     {
/* 1536:1846 */       String element = (String)this.standardNodeListModel_d.elementAt(this.standardNodeList_d.getSelectedIndex());
/* 1537:1847 */       this.suppliersList_d.setSelectedIndex(-1);
/* 1538:1848 */       this.standardNodeList_d.setSelectedIndex(-1);
/* 1539:1849 */       this.suppliersListModel_d.addElement(element);
/* 1540:1850 */       this.standardNodeListModel_d.removeElement(element);
/* 1541:1851 */       this.standardNodeList_d.revalidate();
/* 1542:1852 */       this.standardNodeListPane_d.revalidate();
/* 1543:1853 */       this.suppliersList_d.revalidate();
/* 1544:1854 */       this.suppliersListPane_d.revalidate();
/* 1545:     */     }
/* 1546:     */   }
/* 1547:     */   
/* 1548:     */   void sendToClientsButton_d_actionPerformed(ActionEvent e)
/* 1549:     */   {
/* 1550:1865 */     if (this.standardNodeList_d.getSelectedIndex() != -1)
/* 1551:     */     {
/* 1552:1866 */       String element = (String)this.standardNodeListModel_d.elementAt(this.standardNodeList_d.getSelectedIndex());
/* 1553:1867 */       this.clientsList_d.setSelectedIndex(-1);
/* 1554:1868 */       this.standardNodeList_d.setSelectedIndex(-1);
/* 1555:1869 */       this.clientsListModel_d.addElement(element);
/* 1556:1870 */       this.standardNodeListModel_d.removeElement(element);
/* 1557:1871 */       this.standardNodeList_d.revalidate();
/* 1558:1872 */       this.standardNodeListPane_d.revalidate();
/* 1559:1873 */       this.clientsList_d.revalidate();
/* 1560:1874 */       this.clientsListPane_d.revalidate();
/* 1561:     */     }
/* 1562:     */   }
/* 1563:     */   
/* 1564:     */   void receiveFromClientsButton_d_actionPerformed(ActionEvent e)
/* 1565:     */   {
/* 1566:1885 */     if (this.clientsList_d.getSelectedIndex() != -1)
/* 1567:     */     {
/* 1568:1886 */       String element = (String)this.clientsListModel_d.elementAt(this.clientsList_d.getSelectedIndex());
/* 1569:1887 */       this.clientsList_d.setSelectedIndex(-1);
/* 1570:1888 */       this.standardNodeList_d.setSelectedIndex(-1);
/* 1571:1889 */       this.standardNodeListModel_d.addElement(element);
/* 1572:1890 */       this.clientsListModel_d.removeElement(element);
/* 1573:1891 */       this.standardNodeList_d.revalidate();
/* 1574:1892 */       this.standardNodeListPane_d.revalidate();
/* 1575:1893 */       this.clientsList_d.revalidate();
/* 1576:1894 */       this.clientsListPane_d.revalidate();
/* 1577:     */     }
/* 1578:     */   }
/* 1579:     */   
/* 1580:     */   void sendToCentralButton_d_actionPerformed(ActionEvent e)
/* 1581:     */   {
/* 1582:1905 */     if (this.standardNodeList_d.getSelectedIndex() != -1)
/* 1583:     */     {
/* 1584:1906 */       String element = (String)this.standardNodeListModel_d.elementAt(this.standardNodeList_d.getSelectedIndex());
/* 1585:1907 */       this.centralList_d.setSelectedIndex(-1);
/* 1586:1908 */       this.standardNodeList_d.setSelectedIndex(-1);
/* 1587:1909 */       this.centralListModel_d.addElement(element);
/* 1588:1910 */       this.standardNodeListModel_d.removeElement(element);
/* 1589:1911 */       this.standardNodeList_d.revalidate();
/* 1590:1912 */       this.standardNodeListPane_d.revalidate();
/* 1591:1913 */       this.centralList_d.revalidate();
/* 1592:1914 */       this.centralListPane_d.revalidate();
/* 1593:     */     }
/* 1594:     */   }
/* 1595:     */   
/* 1596:     */   void receiveFromCentralButton_d_actionPerformed(ActionEvent e)
/* 1597:     */   {
/* 1598:1925 */     if (this.centralList_d.getSelectedIndex() != -1)
/* 1599:     */     {
/* 1600:1926 */       String element = (String)this.centralListModel_d.elementAt(this.centralList_d.getSelectedIndex());
/* 1601:1927 */       this.centralList_d.setSelectedIndex(-1);
/* 1602:1928 */       this.standardNodeList_d.setSelectedIndex(-1);
/* 1603:1929 */       this.standardNodeListModel_d.addElement(element);
/* 1604:1930 */       this.centralListModel_d.removeElement(element);
/* 1605:1931 */       this.standardNodeList_d.revalidate();
/* 1606:1932 */       this.standardNodeListPane_d.revalidate();
/* 1607:1933 */       this.centralList_d.revalidate();
/* 1608:1934 */       this.centralListPane_d.revalidate();
/* 1609:     */     }
/* 1610:     */   }
/* 1611:     */   
/* 1612:     */   void findOmnipresentNodesButton_d_actionPerformed(ActionEvent e)
/* 1613:     */   {
/* 1614:1947 */     if ((this.inputGraphFilename_d.getText() == null) || (this.inputGraphFilename_d.getText().equals("")) || (this.initialGraph_d == null))
/* 1615:     */     {
/* 1616:1951 */       JOptionPane.showMessageDialog(this, "Error: Missing input graph.", "Omnipresent Calculator: Missing Parameter", 0);
/* 1617:     */       
/* 1618:     */ 
/* 1619:1954 */       return;
/* 1620:     */     }
/* 1621:1958 */     if ((this.clientsListModel_d.size() > 0) || (this.suppliersListModel_d.size() > 0) || (this.centralListModel_d.size() > 0))
/* 1622:     */     {
/* 1623:1959 */       int result = JOptionPane.showConfirmDialog(this, "This will clear the clients and suppliers\n you have already selected\n and start again.\n Are you sure?", "Cancel Automatic Calculation?", 0);
/* 1624:1962 */       if (result == 1) {
/* 1625:1963 */         return;
/* 1626:     */       }
/* 1627:1967 */       for (int i = 0; i < this.clientsListModel_d.size(); i++) {
/* 1628:1968 */         this.standardNodeListModel_d.addElement(this.clientsListModel_d.elementAt(i));
/* 1629:     */       }
/* 1630:1970 */       for (int i = 0; i < this.suppliersListModel_d.size(); i++) {
/* 1631:1971 */         this.standardNodeListModel_d.addElement(this.suppliersListModel_d.elementAt(i));
/* 1632:     */       }
/* 1633:1973 */       for (int i = 0; i < this.centralListModel_d.size(); i++) {
/* 1634:1974 */         this.standardNodeListModel_d.addElement(this.centralListModel_d.elementAt(i));
/* 1635:     */       }
/* 1636:1976 */       this.clientsListModel_d.removeAllElements();
/* 1637:1977 */       this.suppliersListModel_d.removeAllElements();
/* 1638:1978 */       this.centralListModel_d.removeAllElements();
/* 1639:1979 */       this.standardNodeList_d.revalidate();
/* 1640:1980 */       this.standardNodeListPane_d.revalidate();
/* 1641:1981 */       this.clientsList_d.revalidate();
/* 1642:1982 */       this.clientsListPane_d.revalidate();
/* 1643:1983 */       this.suppliersList_d.revalidate();
/* 1644:1984 */       this.suppliersListPane_d.revalidate();
/* 1645:1985 */       this.centralList_d.revalidate();
/* 1646:1986 */       this.centralListPane_d.revalidate();
/* 1647:     */     }
/* 1648:1989 */     this.clientsList_d.setSelectedIndex(-1);
/* 1649:1990 */     this.suppliersList_d.setSelectedIndex(-1);
/* 1650:1991 */     this.centralList_d.setSelectedIndex(-1);
/* 1651:1992 */     this.standardNodeList_d.setSelectedIndex(-1);
/* 1652:     */     
/* 1653:1994 */     double threshold = Double.valueOf(this.findOmnipresentThreshold_d.getText()).doubleValue();
/* 1654:1995 */     Node[] nodeList = this.initialGraph_d.getNodes();
/* 1655:     */     
/* 1656:     */ 
/* 1657:1998 */     double avg = 0.0D;double sum = 0.0D;
/* 1658:1999 */     for (int i = 0; i < nodeList.length; i++) {
/* 1659:2000 */       if (nodeList[i].getDependencies() != null) {
/* 1660:2001 */         sum += nodeList[i].getDependencies().length;
/* 1661:     */       }
/* 1662:     */     }
/* 1663:2004 */     avg = sum / nodeList.length;
/* 1664:2005 */     avg *= threshold;
/* 1665:2006 */     for (int i = 0; i < nodeList.length; i++) {
/* 1666:2007 */       if ((nodeList[i].getDependencies() != null) && (nodeList[i].getDependencies().length > avg) && (!usesModule(this.librariesListModel_d, nodeList[i].getName())))
/* 1667:     */       {
/* 1668:2010 */         this.standardNodeListModel_d.removeElement(nodeList[i].getName());
/* 1669:2011 */         this.clientsListModel_d.addElement(nodeList[i].getName());
/* 1670:     */       }
/* 1671:     */     }
/* 1672:2016 */     avg = 0.0D;sum = 0.0D;
/* 1673:2017 */     int[] inNum = new int[nodeList.length];
/* 1674:2019 */     for (int j = 0; j < nodeList.length; j++)
/* 1675:     */     {
/* 1676:2020 */       int currval = 0;
/* 1677:2021 */       for (int i = 0; i < nodeList.length; i++)
/* 1678:     */       {
/* 1679:2022 */         int[] deps = nodeList[i].getDependencies();
/* 1680:2023 */         if (deps != null) {
/* 1681:2024 */           for (int n = 0; n < deps.length; n++) {
/* 1682:2025 */             if (deps[n] == j) {
/* 1683:2026 */               currval++;
/* 1684:     */             }
/* 1685:     */           }
/* 1686:     */         }
/* 1687:     */       }
/* 1688:2031 */       inNum[j] = currval;
/* 1689:     */     }
/* 1690:2033 */     for (int i = 0; i < inNum.length; i++) {
/* 1691:2034 */       sum += inNum[i];
/* 1692:     */     }
/* 1693:2036 */     avg = sum / nodeList.length;
/* 1694:2037 */     avg *= threshold;
/* 1695:2038 */     for (int i = 0; i < nodeList.length; i++) {
/* 1696:2039 */       if ((inNum[i] > avg) && (!usesModule(this.librariesListModel_d, nodeList[i].getName())))
/* 1697:     */       {
/* 1698:2041 */         this.standardNodeListModel_d.removeElement(nodeList[i].getName());
/* 1699:2042 */         this.suppliersListModel_d.addElement(nodeList[i].getName());
/* 1700:     */       }
/* 1701:     */     }
/* 1702:2047 */     for (int i = 0; i < this.clientsListModel_d.getSize(); i++)
/* 1703:     */     {
/* 1704:2048 */       String client = (String)this.clientsListModel_d.getElementAt(i);
/* 1705:2049 */       for (int j = 0; j < this.suppliersListModel_d.getSize(); j++)
/* 1706:     */       {
/* 1707:2050 */         String supp = (String)this.suppliersListModel_d.getElementAt(j);
/* 1708:2051 */         if (client.equals(supp))
/* 1709:     */         {
/* 1710:2052 */           this.centralListModel_d.addElement(client);
/* 1711:2053 */           break;
/* 1712:     */         }
/* 1713:     */       }
/* 1714:     */     }
/* 1715:2058 */     for (int i = 0; i < this.centralListModel_d.getSize(); i++)
/* 1716:     */     {
/* 1717:2059 */       String name = (String)this.centralListModel_d.elementAt(i);
/* 1718:2060 */       this.clientsListModel_d.removeElement(name);
/* 1719:2061 */       this.suppliersListModel_d.removeElement(name);
/* 1720:     */     }
/* 1721:2065 */     this.standardNodeList_d.revalidate();
/* 1722:2066 */     this.standardNodeListPane_d.revalidate();
/* 1723:2067 */     this.clientsList_d.revalidate();
/* 1724:2068 */     this.clientsListPane_d.revalidate();
/* 1725:2069 */     this.suppliersList_d.revalidate();
/* 1726:2070 */     this.suppliersListPane_d.revalidate();
/* 1727:2071 */     this.centralList_d.revalidate();
/* 1728:2072 */     this.centralListPane_d.revalidate();
/* 1729:     */   }
/* 1730:     */   
/* 1731:     */   void receiveLibFromClientsButton_d_actionPerformed(ActionEvent e)
/* 1732:     */   {
/* 1733:2082 */     if (this.librariesList_d.getSelectedIndex() != -1)
/* 1734:     */     {
/* 1735:2083 */       String element = (String)this.librariesListModel_d.elementAt(this.librariesList_d.getSelectedIndex());
/* 1736:2084 */       this.librariesList_d.setSelectedIndex(-1);
/* 1737:2085 */       this.standardNodeListLib_d.setSelectedIndex(-1);
/* 1738:2086 */       this.standardNodeListModel_d.addElement(element);
/* 1739:2087 */       this.librariesListModel_d.removeElement(element);
/* 1740:2088 */       this.standardNodeListLib_d.revalidate();
/* 1741:2089 */       this.standardNodeListPaneLib_d.revalidate();
/* 1742:2090 */       this.librariesList_d.revalidate();
/* 1743:2091 */       this.librariesListPane_d.revalidate();
/* 1744:     */     }
/* 1745:     */   }
/* 1746:     */   
/* 1747:     */   void sendLibToClientsButton_d_actionPerformed(ActionEvent e)
/* 1748:     */   {
/* 1749:2102 */     if (this.standardNodeListLib_d.getSelectedIndex() != -1)
/* 1750:     */     {
/* 1751:2103 */       String element = (String)this.standardNodeListModel_d.elementAt(this.standardNodeListLib_d.getSelectedIndex());
/* 1752:2104 */       this.librariesList_d.setSelectedIndex(-1);
/* 1753:2105 */       this.standardNodeListLib_d.setSelectedIndex(-1);
/* 1754:2106 */       this.librariesListModel_d.addElement(element);
/* 1755:2107 */       this.standardNodeListModel_d.removeElement(element);
/* 1756:2108 */       this.standardNodeListLib_d.revalidate();
/* 1757:2109 */       this.standardNodeListPaneLib_d.revalidate();
/* 1758:2110 */       this.librariesList_d.revalidate();
/* 1759:2111 */       this.librariesListPane_d.revalidate();
/* 1760:     */     }
/* 1761:     */   }
/* 1762:     */   
/* 1763:     */   void findLibraryNodesButton_d_actionPerformed(ActionEvent e)
/* 1764:     */   {
/* 1765:2123 */     if ((this.inputGraphFilename_d.getText() == null) || (this.inputGraphFilename_d.getText().equals("")) || (this.initialGraph_d == null))
/* 1766:     */     {
/* 1767:2127 */       JOptionPane.showMessageDialog(this, "Error: Missing input graph.", "Library Finder: Missing Parameter", 0);
/* 1768:     */       
/* 1769:     */ 
/* 1770:2130 */       return;
/* 1771:     */     }
/* 1772:2134 */     if (this.librariesListModel_d.size() > 0)
/* 1773:     */     {
/* 1774:2135 */       int result = JOptionPane.showConfirmDialog(this, "This will clear the libraries\n you have already selected\n and start again.\n Are you sure?", "Cancel Automatic Calculation?", 0);
/* 1775:2138 */       if (result == 1) {
/* 1776:2139 */         return;
/* 1777:     */       }
/* 1778:2143 */       for (int i = 0; i < this.librariesListModel_d.size(); i++) {
/* 1779:2144 */         this.standardNodeListModel_d.addElement(this.librariesListModel_d.elementAt(i));
/* 1780:     */       }
/* 1781:2146 */       this.librariesListModel_d.removeAllElements();
/* 1782:2147 */       this.standardNodeList_d.revalidate();
/* 1783:2148 */       this.standardNodeListPane_d.revalidate();
/* 1784:2149 */       this.librariesList_d.revalidate();
/* 1785:2150 */       this.librariesListPane_d.revalidate();
/* 1786:     */     }
/* 1787:2153 */     this.librariesList_d.setSelectedIndex(-1);
/* 1788:2154 */     this.standardNodeList_d.setSelectedIndex(-1);
/* 1789:     */     
/* 1790:2156 */     Vector libraries = new Vector();
/* 1791:2157 */     Vector normal = new Vector();
/* 1792:2158 */     Node[] nodeList = this.initialGraph_d.getNodes();
/* 1793:2161 */     for (int i = 0; i < nodeList.length; i++)
/* 1794:     */     {
/* 1795:2162 */       String nname = nodeList[i].getName();
/* 1796:2163 */       if (((nodeList[i].getDependencies() == null) || (nodeList[i].getDependencies().length == 0)) && (!usesModule(this.clientsListModel_d, nodeList[i].getName())) && (!usesModule(this.suppliersListModel_d, nodeList[i].getName())) && (!usesModule(this.centralListModel_d, nodeList[i].getName())))
/* 1797:     */       {
/* 1798:2167 */         this.standardNodeListModel_d.removeElement(nodeList[i].getName());
/* 1799:2168 */         this.librariesListModel_d.addElement(nodeList[i].getName());
/* 1800:     */       }
/* 1801:     */     }
/* 1802:2173 */     this.standardNodeList_d.revalidate();
/* 1803:2174 */     this.standardNodeListPane_d.revalidate();
/* 1804:2175 */     this.librariesList_d.revalidate();
/* 1805:2176 */     this.librariesListPane_d.revalidate();
/* 1806:     */   }
/* 1807:     */   
/* 1808:     */   private boolean usesModule(DefaultListModel list, String element)
/* 1809:     */   {
/* 1810:2186 */     for (int i = 0; i < list.size(); i++) {
/* 1811:2187 */       if (element.equals((String)list.elementAt(i))) {
/* 1812:2188 */         return true;
/* 1813:     */       }
/* 1814:     */     }
/* 1815:2191 */     return false;
/* 1816:     */   }
/* 1817:     */   
/* 1818:     */   void ClusteringAlgEF_actionPerformed(ActionEvent e)
/* 1819:     */   {
/* 1820:2199 */     String objFnCalc = (String)this.ClusteringAlgEF.getSelectedItem();
/* 1821:2200 */     this.preferences_d.getObjectiveFunctionCalculatorFactory().setCurrentCalculator(objFnCalc);
/* 1822:     */     
/* 1823:2202 */     setupClusteringOptions();
/* 1824:     */   }
/* 1825:     */   
/* 1826:     */   void clusteringMethodList_d_actionPerformed(ActionEvent e) {}
/* 1827:     */   
/* 1828:     */   void setupClusteringOptions()
/* 1829:     */   {
/* 1830:2216 */     String objFnCalc = (String)this.ClusteringAlgEF.getSelectedItem();
/* 1831:2218 */     if (this.clusteringMethod_d != null)
/* 1832:     */     {
/* 1833:2220 */       if ((this.clusteringMethod_d instanceof GenericHillClimbingClusteringMethod)) {
/* 1834:2221 */         if (objFnCalc.equals("Turbo MQ Function"))
/* 1835:     */         {
/* 1836:2223 */           HillClimbingConfiguration hcc = (HillClimbingConfiguration)this.clusteringMethod_d.getConfiguration();
/* 1837:2224 */           hcc.setNumOfIterations(1);
/* 1838:2225 */           hcc.setThreshold(1.0D);
/* 1839:2226 */           ((GenericHillClimbingClusteringMethod)this.clusteringMethod_d).setConfiguration(hcc);
/* 1840:     */         }
/* 1841:     */         else
/* 1842:     */         {
/* 1843:2230 */           this.clusteringMethod_d.setDefaultConfiguration();
/* 1844:     */         }
/* 1845:     */       }
/* 1846:2233 */       this.configuration_d = this.clusteringMethod_d.getConfiguration();
/* 1847:     */     }
/* 1848:     */   }
/* 1849:     */   
/* 1850:     */   void consolidateDriftersCB_actionPerformed(ActionEvent e) {}
/* 1851:     */   
/* 1852:     */   void delimEF_actionPerformed(ActionEvent e) {}
/* 1853:     */   
/* 1854:     */   void spaceDelimCB_actionPerformed(ActionEvent e) {}
/* 1855:     */   
/* 1856:     */   void queryNS_actionPerformed(ActionEvent e)
/* 1857:     */   {
/* 1858:     */     try
/* 1859:     */     {
/* 1860:2263 */       DefaultListModel svrLM = new DefaultListModel();
/* 1861:2264 */       this.serverList.setModel(svrLM);
/* 1862:     */       
/* 1863:2266 */       Properties env = new Properties();
/* 1864:     */       
/* 1865:2268 */       env.put("java.naming.factory.initial", "com.sun.jndi.cosnaming.CNCtxFactory");
/* 1866:     */       
/* 1867:2270 */       String namingURL = "iiop://" + this.nameServerEF.getText() + ":" + this.portEF.getText();
/* 1868:2271 */       env.put("java.naming.provider.url", namingURL);
/* 1869:     */       
/* 1870:     */ 
/* 1871:     */ 
/* 1872:     */ 
/* 1873:     */ 
/* 1874:2277 */       InitialContext context = new InitialContext(env);
/* 1875:     */       
/* 1876:     */ 
/* 1877:     */ 
/* 1878:     */ 
/* 1879:2282 */       NamingEnumeration ne = context.listBindings(this.nameSpaceEF.getText());
/* 1880:     */       
/* 1881:2284 */       this.serverVector = new Vector();
/* 1882:2285 */       this.serverVector.removeAllElements();
/* 1883:2290 */       while (ne.hasMoreElements())
/* 1884:     */       {
/* 1885:2292 */         Binding b = (Binding)ne.next();
/* 1886:2293 */         this.serverVector.addElement(b);
/* 1887:2294 */         svrLM.addElement(b.getName());
/* 1888:     */       }
/* 1889:2300 */       int sz = svrLM.size();
/* 1890:2301 */       if (sz > 0)
/* 1891:     */       {
/* 1892:2303 */         int[] selectAll = new int[sz];
/* 1893:2304 */         for (int z = 0; z < sz; z++) {
/* 1894:2305 */           selectAll[z] = z;
/* 1895:     */         }
/* 1896:2307 */         this.serverList.setSelectedIndices(selectAll);
/* 1897:     */       }
/* 1898:     */     }
/* 1899:     */     catch (Exception excpt)
/* 1900:     */     {
/* 1901:2315 */       String msg = BunchUtilities.DelimitString(excpt.toString(), 25);
/* 1902:2316 */       JOptionPane.showMessageDialog(this, msg, "Naming Server Exception", 0);
/* 1903:     */     }
/* 1904:     */   }
/* 1905:     */   
/* 1906:     */   private void CreateCallbackObj()
/* 1907:     */   {
/* 1908:     */     try
/* 1909:     */     {
/* 1910:2329 */       this.svrCallback = new CallbackImpl();
/* 1911:     */     }
/* 1912:     */     catch (Exception excpt)
/* 1913:     */     {
/* 1914:2333 */       String msg = excpt.toString() + "\n\n\n\n";
/* 1915:     */       
/* 1916:2335 */       JOptionPane.showMessageDialog(this, msg, "Error Creating Callback Object", 0);
/* 1917:     */     }
/* 1918:     */   }
/* 1919:     */   
/* 1920:     */   public void ReportException(String title, Exception excpt)
/* 1921:     */   {
/* 1922:2346 */     String msg = excpt.toString() + "\n\n\n\n";
/* 1923:     */     
/* 1924:2348 */     JOptionPane.showMessageDialog(this, msg, title, 0);
/* 1925:     */   }
/* 1926:     */   
/* 1927:     */   void includeDistSvrsPB_actionPerformed(ActionEvent e)
/* 1928:     */   {
/* 1929:2357 */     CreateCallbackObj();
/* 1930:2358 */     int[] idx = this.serverList.getSelectedIndices();
/* 1931:     */     
/* 1932:2360 */     DefaultListModel svrLM = (DefaultListModel)this.serverList.getModel();
/* 1933:2365 */     if (this.activeServerVector != null) {
/* 1934:2366 */       this.activeServerVector.removeAllElements();
/* 1935:     */     } else {
/* 1936:2368 */       this.activeServerVector = new Vector();
/* 1937:     */     }
/* 1938:2374 */     deactivateAllServers();
/* 1939:2379 */     for (int i = 0; i < idx.length; i++)
/* 1940:     */     {
/* 1941:2381 */       String lbMsg = (String)svrLM.elementAt(idx[i]);
/* 1942:2382 */       lbMsg = "SELECTED--> " + lbMsg;
/* 1943:2383 */       svrLM.setElementAt(lbMsg, idx[i]);
/* 1944:2384 */       Binding b = (Binding)this.serverVector.elementAt(idx[i]);
/* 1945:2385 */       this.activeServerVector.addElement(b);
/* 1946:2386 */       BunchSvrMsg bsm = (BunchSvrMsg)b.getObject();
/* 1947:     */       try
/* 1948:     */       {
/* 1949:2393 */         bsm.registerCallback(this.svrCallback);
/* 1950:     */       }
/* 1951:     */       catch (Exception excpt)
/* 1952:     */       {
/* 1953:2395 */         ReportException("Error Registering Callback", excpt);
/* 1954:     */       }
/* 1955:     */     }
/* 1956:2401 */     if (this.activeServerVector.size() > 0) {
/* 1957:2402 */       this.deactivatePB.setEnabled(true);
/* 1958:     */     } else {
/* 1959:2404 */       this.deactivatePB.setEnabled(false);
/* 1960:     */     }
/* 1961:2406 */     this.serverList.clearSelection();
/* 1962:     */     
/* 1963:     */ 
/* 1964:     */ 
/* 1965:     */ 
/* 1966:2411 */     Integer uowSz = new Integer(this.UOWSzEF.getText());
/* 1967:2412 */     this.svrCallback.baseUOWSz = uowSz.intValue();
/* 1968:     */   }
/* 1969:     */   
/* 1970:     */   private void deactivateAllServers()
/* 1971:     */   {
/* 1972:2423 */     DefaultListModel svrLM = (DefaultListModel)this.serverList.getModel();
/* 1973:2428 */     if (this.activeServerVector != null) {
/* 1974:2429 */       this.activeServerVector.removeAllElements();
/* 1975:     */     }
/* 1976:2431 */     svrLM.clear();
/* 1977:2437 */     for (int i = 0; i < this.serverVector.size(); i++)
/* 1978:     */     {
/* 1979:2439 */       Binding b = (Binding)this.serverVector.elementAt(i);
/* 1980:2440 */       svrLM.addElement(b.getName());
/* 1981:     */     }
/* 1982:2446 */     this.deactivatePB.setEnabled(false);
/* 1983:2447 */     this.serverList.clearSelection();
/* 1984:     */   }
/* 1985:     */   
/* 1986:     */   void deactivatePB_actionPerformed(ActionEvent e)
/* 1987:     */   {
/* 1988:2455 */     deactivateAllServers();
/* 1989:     */   }
/* 1990:     */   
/* 1991:     */   void serverList_valueChanged(ListSelectionEvent e) {}
/* 1992:     */   
/* 1993:     */   void serverList_mouseClicked(MouseEvent e)
/* 1994:     */   {
/* 1995:2471 */     if (this.serverList.isSelectionEmpty())
/* 1996:     */     {
/* 1997:2473 */       this.includeDistSvrsPB.setEnabled(false);
/* 1998:2474 */       this.deactivatePB.setEnabled(false);
/* 1999:     */     }
/* 2000:     */     else
/* 2001:     */     {
/* 2002:2477 */       this.includeDistSvrsPB.setEnabled(true);
/* 2003:     */     }
/* 2004:     */   }
/* 2005:     */   
/* 2006:     */   void distClustEnableCB_stateChanged(ChangeEvent e)
/* 2007:     */   {
/* 2008:2486 */     if (this.distClustEnableCB.isSelected() == true) {
/* 2009:2487 */       this.adaptiveEnableCB.setEnabled(true);
/* 2010:     */     } else {
/* 2011:2489 */       this.adaptiveEnableCB.setEnabled(false);
/* 2012:     */     }
/* 2013:     */   }
/* 2014:     */   
/* 2015:     */   void timeoutEnable_actionPerformed(ActionEvent e)
/* 2016:     */   {
/* 2017:2498 */     boolean state = this.timeoutEnable.isSelected();
/* 2018:2499 */     if (state == true)
/* 2019:     */     {
/* 2020:2501 */       this.maxRuntimeEF.setEnabled(true);
/* 2021:2502 */       getTimoutTime();
/* 2022:     */     }
/* 2023:     */     else
/* 2024:     */     {
/* 2025:2505 */       this.maxRuntimeEF.setEnabled(false);
/* 2026:     */     }
/* 2027:     */   }
/* 2028:     */   
/* 2029:     */   public boolean limitRuntime()
/* 2030:     */   {
/* 2031:2516 */     return this.timeoutEnable.isSelected();
/* 2032:     */   }
/* 2033:     */   
/* 2034:     */   public long getTimoutTime()
/* 2035:     */   {
/* 2036:     */     try
/* 2037:     */     {
/* 2038:2530 */       Long to = new Long(this.maxRuntimeEF.getText());
/* 2039:2531 */       return to.longValue();
/* 2040:     */     }
/* 2041:     */     catch (Exception ex)
/* 2042:     */     {
/* 2043:2535 */       ReportException("Error Getting TimeoutValue", ex);
/* 2044:     */     }
/* 2045:2537 */     return 0L;
/* 2046:     */   }
/* 2047:     */   
/* 2048:     */   void menuMQCalc_actionPerformed(ActionEvent e)
/* 2049:     */   {
/* 2050:2546 */     MQCalculatorUtil mqCalcUtil = new MQCalculatorUtil(this, "MQ Calculator Utilility", true);
/* 2051:     */     
/* 2052:     */ 
/* 2053:2549 */     Dimension dlgSize = mqCalcUtil.getPreferredSize();
/* 2054:2550 */     Dimension frmSize = getSize();
/* 2055:2551 */     Point loc = getLocation();
/* 2056:2552 */     mqCalcUtil.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
/* 2057:2553 */     mqCalcUtil.setVisible(true);
/* 2058:     */   }
/* 2059:     */   
/* 2060:     */   void utilityMeasurementCalc_actionPerformed(ActionEvent e)
/* 2061:     */   {
/* 2062:2562 */     MeasurementUtil CalcUtil = new MeasurementUtil(this, "Calculator Utilility", true);
/* 2063:     */     
/* 2064:     */ 
/* 2065:2565 */     Dimension dlgSize = CalcUtil.getPreferredSize();
/* 2066:2566 */     Dimension frmSize = getSize();
/* 2067:2567 */     Point loc = getLocation();
/* 2068:2568 */     CalcUtil.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
/* 2069:2569 */     CalcUtil.setVisible(true);
/* 2070:     */   }
/* 2071:     */   
/* 2072:     */   void actionList_d_actionPerformed(ActionEvent e)
/* 2073:     */   {
/* 2074:2578 */     String cmd = (String)this.actionList_d.getSelectedItem();
/* 2075:2584 */     if (cmd.equals("Agglomerative Clustering"))
/* 2076:     */     {
/* 2077:2586 */       if (this.initialGraph_d != null) {
/* 2078:2587 */         this.initialGraph_d.setIsClusterTree(true);
/* 2079:     */       }
/* 2080:2588 */       this.nextLevelGraphButton_d.setEnabled(false);
/* 2081:2589 */       this.agglomOutputCB.setSelectedItem("Output Median Level");
/* 2082:     */     }
/* 2083:     */     else
/* 2084:     */     {
/* 2085:2593 */       if (this.initialGraph_d != null) {
/* 2086:2594 */         this.initialGraph_d.setIsClusterTree(false);
/* 2087:     */       }
/* 2088:2595 */       this.nextLevelGraphButton_d.setEnabled(true);
/* 2089:2596 */       this.agglomOutputCB.setSelectedItem("Output Top Level");
/* 2090:     */     }
/* 2091:     */   }
/* 2092:     */   
/* 2093:     */   void agglomOutputCB_actionPerformed(ActionEvent e) {}
/* 2094:     */   
/* 2095:     */   void outputFileFormatList_d_actionPerformed(ActionEvent e)
/* 2096:     */   {
/* 2097:2615 */     String choice = (String)this.outputFileFormatList_d.getSelectedItem();
/* 2098:2616 */     if (choice.equals("Text Tree"))
/* 2099:     */     {
/* 2100:2617 */       this.agglomOutputCB.setSelectedItem("Output Top Level");
/* 2101:     */     }
/* 2102:     */     else
/* 2103:     */     {
/* 2104:2620 */       String cmd = (String)this.actionList_d.getSelectedItem();
/* 2105:2622 */       if (cmd.equals("Agglomerative Clustering")) {
/* 2106:2623 */         this.agglomOutputCB.setSelectedItem("Output Median Level");
/* 2107:     */       } else {
/* 2108:2625 */         this.agglomOutputCB.setSelectedItem("Output Top Level");
/* 2109:     */       }
/* 2110:     */     }
/* 2111:     */   }
/* 2112:     */   
/* 2113:     */   void ClearClusterFile_actionPerformed(ActionEvent e)
/* 2114:     */   {
/* 2115:2635 */     this.inputClusterFile_d.setText("");
/* 2116:2636 */     this.initialGraph_d.resetNodeLocks();
/* 2117:2637 */     this.lockClustersCheckbox_d.setEnabled(false);
/* 2118:     */   }
/* 2119:     */   
/* 2120:     */   void menuShowDistributedTab_actionPerformed(ActionEvent e)
/* 2121:     */   {
/* 2122:2647 */     int idx = this.mainTabbedPane_d.indexOfComponent(this.distPane);
/* 2123:2648 */     if (idx == -1)
/* 2124:     */     {
/* 2125:2650 */       this.mainTabbedPane_d.add(this.distPane, "Distributed Clustering", 2);
/* 2126:2651 */       this.menuShowDistributedTab.setText("Hide Distributed Tab");
/* 2127:     */     }
/* 2128:     */     else
/* 2129:     */     {
/* 2130:2655 */       this.mainTabbedPane_d.remove(this.distPane);
/* 2131:2656 */       this.menuShowDistributedTab.setText("Show Distributed Tab");
/* 2132:     */     }
/* 2133:     */   }
/* 2134:     */   
/* 2135:     */   void clusteringUtilsMenu_actionPerformed(ActionEvent e)
/* 2136:     */   {
/* 2137:2666 */     BunchClusteringUtil BunchUtil = new BunchClusteringUtil(this, "Bunch Utilility", true);
/* 2138:     */     
/* 2139:     */ 
/* 2140:2669 */     Dimension dlgSize = BunchUtil.getPreferredSize();
/* 2141:2670 */     Dimension frmSize = getSize();
/* 2142:2671 */     Point loc = getLocation();
/* 2143:2672 */     BunchUtil.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
/* 2144:2673 */     BunchUtil.setVisible(true);
/* 2145:     */   }
/* 2146:     */   
/* 2147:     */   void fileUtilsMenu_actionPerformed(ActionEvent e)
/* 2148:     */   {
/* 2149:2678 */     BunchFileUtil BunchUtil = new BunchFileUtil(this, "Bunch File Utilities", true);
/* 2150:     */     
/* 2151:     */ 
/* 2152:2681 */     Dimension dlgSize = BunchUtil.getPreferredSize();
/* 2153:2682 */     Dimension frmSize = getSize();
/* 2154:2683 */     Point loc = getLocation();
/* 2155:2684 */     BunchUtil.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
/* 2156:2685 */     BunchUtil.setVisible(true);
/* 2157:     */   }
/* 2158:     */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchFrame
 * JD-Core Version:    0.7.0.1
 */