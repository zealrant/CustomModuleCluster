/*   1:    */ package bunch.BunchServer;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.awt.Font;
/*   6:    */ import java.awt.Frame;
/*   7:    */ import java.awt.GridBagConstraints;
/*   8:    */ import java.awt.GridBagLayout;
/*   9:    */ import java.awt.Insets;
/*  10:    */ import java.awt.event.ActionEvent;
/*  11:    */ import java.awt.event.ActionListener;
/*  12:    */ import javax.swing.JButton;
/*  13:    */ import javax.swing.JDialog;
/*  14:    */ import javax.swing.JLabel;
/*  15:    */ import javax.swing.JPanel;
/*  16:    */ import javax.swing.JSlider;
/*  17:    */ import javax.swing.event.ChangeEvent;
/*  18:    */ import javax.swing.event.ChangeListener;
/*  19:    */ 
/*  20:    */ public class ServerClusteringProgress
/*  21:    */   extends JDialog
/*  22:    */ {
/*  23: 34 */   JPanel panel1 = new JPanel();
/*  24: 35 */   GridBagLayout gridBagLayout1 = new GridBagLayout();
/*  25: 36 */   JLabel jLabel1 = new JLabel();
/*  26: 37 */   JLabel NeighborsProcessedST = new JLabel();
/*  27: 38 */   JLabel jLabel2 = new JLabel();
/*  28: 39 */   JLabel BestMQST = new JLabel();
/*  29: 40 */   JButton toggleServerPB = new JButton();
/*  30: 41 */   int workProcessed = 0;
/*  31: 42 */   double bestMQ = 0.0D;
/*  32: 43 */   Thread workerThread = null;
/*  33: 44 */   JLabel jLabel3 = new JLabel();
/*  34: 45 */   JLabel uowSz = new JLabel();
/*  35: 46 */   JLabel adaptiveEnableLabel = new JLabel();
/*  36: 47 */   JLabel adaptiveEnableMsg = new JLabel();
/*  37: 48 */   JLabel jLabel4 = new JLabel();
/*  38: 49 */   int currentRefresh = 1;
/*  39: 50 */   long totalUpdates = 0L;
/*  40: 51 */   JLabel jLabel5 = new JLabel();
/*  41: 52 */   JLabel jLabel6 = new JLabel();
/*  42: 53 */   JSlider UpdateSpeed = new JSlider();
/*  43: 54 */   JLabel jLabel7 = new JLabel();
/*  44: 55 */   JLabel jLabel8 = new JLabel();
/*  45:    */   
/*  46:    */   public ServerClusteringProgress(Frame frame, String title, boolean modal)
/*  47:    */   {
/*  48: 58 */     super(frame, title, modal);
/*  49:    */     try
/*  50:    */     {
/*  51: 60 */       this.workProcessed = 0;
/*  52: 61 */       this.bestMQ = 0.0D;
/*  53: 62 */       jbInit();
/*  54: 63 */       pack();
/*  55:    */     }
/*  56:    */     catch (Exception ex)
/*  57:    */     {
/*  58: 66 */       ex.printStackTrace();
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setWorkerThread(Thread t)
/*  63:    */   {
/*  64: 71 */     this.workerThread = t;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public ServerClusteringProgress()
/*  68:    */   {
/*  69: 74 */     this(null, "", false);
/*  70:    */   }
/*  71:    */   
/*  72:    */   void jbInit()
/*  73:    */     throws Exception
/*  74:    */   {
/*  75: 78 */     this.panel1.setLayout(this.gridBagLayout1);
/*  76: 79 */     this.jLabel1.setText("Neighbors Processed");
/*  77: 80 */     this.NeighborsProcessedST.setText("0");
/*  78: 81 */     this.jLabel2.setText("Best MQ So Far:");
/*  79: 82 */     this.BestMQST.setText("0.0");
/*  80: 83 */     this.toggleServerPB.setText("Pause Server");
/*  81: 84 */     this.toggleServerPB.addActionListener(new ActionListener()
/*  82:    */     {
/*  83:    */       public void actionPerformed(ActionEvent e)
/*  84:    */       {
/*  85: 87 */         ServerClusteringProgress.this.toggleServerPB_actionPerformed(e);
/*  86:    */       }
/*  87: 89 */     });
/*  88: 90 */     this.jLabel3.setText("Current UOW Size:");
/*  89: 91 */     this.uowSz.setText("0");
/*  90: 92 */     this.adaptiveEnableLabel.setText("Adaptive Algorithm:");
/*  91: 93 */     this.adaptiveEnableMsg.setText("Enabled");
/*  92: 94 */     this.jLabel4.setText("Update Frequency:");
/*  93: 95 */     this.jLabel5.setText("     ");
/*  94: 96 */     this.jLabel6.setText("      ");
/*  95: 97 */     this.UpdateSpeed.setPaintLabels(true);
/*  96: 98 */     this.UpdateSpeed.setPaintTicks(true);
/*  97: 99 */     this.UpdateSpeed.setMaximum(10);
/*  98:100 */     this.UpdateSpeed.setValueIsAdjusting(true);
/*  99:101 */     this.UpdateSpeed.setOpaque(false);
/* 100:102 */     this.UpdateSpeed.addChangeListener(new ChangeListener()
/* 101:    */     {
/* 102:    */       public void stateChanged(ChangeEvent e)
/* 103:    */       {
/* 104:104 */         ServerClusteringProgress.this.UpdateSpeed_stateChanged(e);
/* 105:    */       }
/* 106:106 */     });
/* 107:107 */     this.jLabel7.setFont(new Font("Dialog", 1, 12));
/* 108:108 */     this.jLabel7.setForeground(Color.blue);
/* 109:109 */     this.jLabel7.setText("Fast");
/* 110:110 */     this.jLabel8.setFont(new Font("Dialog", 1, 12));
/* 111:111 */     this.jLabel8.setForeground(Color.blue);
/* 112:112 */     this.jLabel8.setText("Slow");
/* 113:113 */     getContentPane().add(this.panel1);
/* 114:114 */     this.panel1.add(this.jLabel1, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 16, 3, new Insets(0, 10, 0, 21), 0, 0));
/* 115:    */     
/* 116:116 */     this.panel1.add(this.NeighborsProcessedST, new GridBagConstraints(3, 0, 3, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 0, 0, 0), 142, 0));
/* 117:    */     
/* 118:118 */     this.panel1.add(this.jLabel2, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 10, 0, 0), 0, 0));
/* 119:    */     
/* 120:120 */     this.panel1.add(this.BestMQST, new GridBagConstraints(3, 1, 3, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 0, 0, 0), 0, 0));
/* 121:    */     
/* 122:122 */     this.panel1.add(this.toggleServerPB, new GridBagConstraints(1, 6, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(5, 10, 0, 0), 0, -6));
/* 123:    */     
/* 124:124 */     this.panel1.add(this.jLabel3, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(0, 9, 0, 0), 0, 0));
/* 125:    */     
/* 126:126 */     this.panel1.add(this.uowSz, new GridBagConstraints(3, 2, 3, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 0, 0, 0), 0, 0));
/* 127:    */     
/* 128:128 */     this.panel1.add(this.adaptiveEnableLabel, new GridBagConstraints(1, 3, 1, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 9, 0, 0), 0, 0));
/* 129:    */     
/* 130:130 */     this.panel1.add(this.adaptiveEnableMsg, new GridBagConstraints(3, 3, 3, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 0, 0, 0), 0, 0));
/* 131:    */     
/* 132:132 */     this.panel1.add(this.jLabel4, new GridBagConstraints(1, 4, 1, 1, 0.0D, 0.0D, 18, 0, new Insets(0, 9, 0, 0), 0, 0));
/* 133:    */     
/* 134:134 */     this.panel1.add(this.jLabel5, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 135:    */     
/* 136:136 */     this.panel1.add(this.jLabel6, new GridBagConstraints(6, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 137:    */     
/* 138:138 */     this.panel1.add(this.UpdateSpeed, new GridBagConstraints(3, 4, 3, 1, 0.0D, 0.0D, 15, 0, new Insets(0, 0, 0, 0), 0, -9));
/* 139:    */     
/* 140:140 */     this.panel1.add(this.jLabel7, new GridBagConstraints(3, 5, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 141:    */     
/* 142:142 */     this.panel1.add(this.jLabel8, new GridBagConstraints(5, 5, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 0, 0), 0, 0));
/* 143:    */     
/* 144:    */ 
/* 145:145 */     this.currentRefresh = 10;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void updateWorkProcessed(int amt, double mq, int uowSize, boolean adaptiveEnabled)
/* 149:    */   {
/* 150:150 */     this.workProcessed += amt;
/* 151:151 */     if (mq > this.bestMQ) {
/* 152:152 */       this.bestMQ = mq;
/* 153:    */     }
/* 154:154 */     this.totalUpdates += 1L;
/* 155:156 */     if (this.totalUpdates % (this.currentRefresh * this.currentRefresh) != 0L) {
/* 156:159 */       return;
/* 157:    */     }
/* 158:162 */     int iMQ = (int)(this.bestMQ * 10000.0D);
/* 159:    */     
/* 160:164 */     Integer iwp = new Integer(this.workProcessed);
/* 161:165 */     Double mqDbl = new Double(iMQ / 10000.0D);
/* 162:166 */     Integer iUOW = new Integer(uowSize);
/* 163:    */     
/* 164:168 */     String adaptEnab = "Enabled";
/* 165:169 */     if (!adaptiveEnabled) {
/* 166:170 */       adaptEnab = "Disabled";
/* 167:    */     }
/* 168:172 */     this.NeighborsProcessedST.setText(iwp.toString());
/* 169:173 */     this.BestMQST.setText(mqDbl.toString());
/* 170:174 */     this.uowSz.setText(iUOW.toString());
/* 171:175 */     this.adaptiveEnableMsg.setText(adaptEnab);
/* 172:    */   }
/* 173:    */   
/* 174:    */   void toggleServerPB_actionPerformed(ActionEvent e)
/* 175:    */   {
/* 176:179 */     if (this.toggleServerPB.getText().equals("Pause Server"))
/* 177:    */     {
/* 178:181 */       this.toggleServerPB.setText("Resume Server");
/* 179:182 */       synchronized (this.workerThread)
/* 180:    */       {
/* 181:    */         try
/* 182:    */         {
/* 183:184 */           this.workerThread.wait();
/* 184:    */         }
/* 185:    */         catch (Exception ex) {}
/* 186:    */       }
/* 187:    */     }
/* 188:    */     else
/* 189:    */     {
/* 190:190 */       this.toggleServerPB.setText("Pause Server");
/* 191:191 */       synchronized (this.workerThread)
/* 192:    */       {
/* 193:    */         try
/* 194:    */         {
/* 195:193 */           this.workerThread.notify();
/* 196:    */         }
/* 197:    */         catch (Exception ex) {}
/* 198:    */       }
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   void UpdateSpeed_stateChanged(ChangeEvent e)
/* 203:    */   {
/* 204:200 */     int val = this.UpdateSpeed.getValue();
/* 205:201 */     this.currentRefresh = (val + 1);
/* 206:    */   }
/* 207:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchServer.ServerClusteringProgress
 * JD-Core Version:    0.7.0.1
 */