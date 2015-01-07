/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.awt.BorderLayout;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Dialog;
/*   6:    */ import java.awt.FlowLayout;
/*   7:    */ import java.awt.Frame;
/*   8:    */ import java.awt.GridLayout;
/*   9:    */ import java.awt.event.ActionEvent;
/*  10:    */ import java.awt.event.ActionListener;
/*  11:    */ import java.awt.event.WindowEvent;
/*  12:    */ import javax.swing.ImageIcon;
/*  13:    */ import javax.swing.JButton;
/*  14:    */ import javax.swing.JLabel;
/*  15:    */ import javax.swing.JPanel;
/*  16:    */ import javax.swing.border.EmptyBorder;
/*  17:    */ 
/*  18:    */ public class BunchFrame_AboutBox
/*  19:    */   extends Dialog
/*  20:    */   implements ActionListener
/*  21:    */ {
/*  22: 44 */   JPanel panel1 = new JPanel();
/*  23: 45 */   JPanel panel2 = new JPanel();
/*  24: 46 */   JPanel insetsPanel1 = new JPanel();
/*  25: 47 */   JPanel insetsPanel2 = new JPanel();
/*  26: 48 */   JPanel insetsPanel3 = new JPanel();
/*  27: 49 */   JButton button1 = new JButton();
/*  28: 50 */   JLabel imageControl1 = new JLabel();
/*  29:    */   ImageIcon imageIcon;
/*  30: 52 */   JLabel label1 = new JLabel();
/*  31: 53 */   JLabel label2 = new JLabel();
/*  32: 54 */   JLabel label3 = new JLabel();
/*  33: 55 */   JLabel label4 = new JLabel();
/*  34: 56 */   BorderLayout borderLayout1 = new BorderLayout();
/*  35: 57 */   BorderLayout borderLayout2 = new BorderLayout();
/*  36: 58 */   FlowLayout flowLayout1 = new FlowLayout();
/*  37: 59 */   FlowLayout flowLayout2 = new FlowLayout();
/*  38: 60 */   GridLayout gridLayout1 = new GridLayout();
/*  39: 61 */   String product = "Bunch 2.0 - JDK 1.2 Edition";
/*  40: 62 */   String version = "";
/*  41: 63 */   String copyright = "Copyright (c) 1999";
/*  42: 64 */   String comments = "Bunch Version 2.0";
/*  43: 65 */   JLabel jLabel1 = new JLabel();
/*  44: 66 */   JLabel jLabel2 = new JLabel();
/*  45: 67 */   JLabel jLabel3 = new JLabel();
/*  46: 68 */   JLabel jLabel4 = new JLabel();
/*  47:    */   
/*  48:    */   public BunchFrame_AboutBox(Frame parent)
/*  49:    */   {
/*  50: 73 */     super(parent);
/*  51: 74 */     enableEvents(64L);
/*  52:    */     try
/*  53:    */     {
/*  54: 76 */       jbInit();
/*  55:    */     }
/*  56:    */     catch (Exception e)
/*  57:    */     {
/*  58: 79 */       e.printStackTrace();
/*  59:    */     }
/*  60: 83 */     pack();
/*  61:    */   }
/*  62:    */   
/*  63:    */   private void jbInit()
/*  64:    */     throws Exception
/*  65:    */   {
/*  66: 92 */     setTitle("About");
/*  67: 93 */     setResizable(false);
/*  68: 94 */     this.panel1.setLayout(this.borderLayout1);
/*  69: 95 */     this.panel2.setLayout(this.borderLayout2);
/*  70: 96 */     this.insetsPanel1.setLayout(this.flowLayout1);
/*  71: 97 */     this.insetsPanel2.setLayout(this.flowLayout1);
/*  72: 98 */     this.insetsPanel2.setBorder(new EmptyBorder(10, 10, 10, 10));
/*  73: 99 */     this.gridLayout1.setRows(8);
/*  74:100 */     this.gridLayout1.setColumns(1);
/*  75:101 */     this.label1.setText("Bunch 3.5 - May 2013");
/*  76:102 */     this.label2.setText("Drexel University Software Engineering Group (SERG)");
/*  77:103 */     this.label3.setText("Copyright (c) 1997-2011");
/*  78:104 */     this.label4.setText("Bunch Edition 3D");
/*  79:105 */     this.insetsPanel3.setLayout(this.gridLayout1);
/*  80:106 */     this.insetsPanel3.setBorder(new EmptyBorder(10, 60, 10, 10));
/*  81:107 */     this.button1.setText("OK");
/*  82:108 */     this.button1.addActionListener(this);
/*  83:109 */     this.jLabel3.setText("For Help and Documentation Please Visit:");
/*  84:110 */     this.jLabel1.setText("CVS Release Tag:  REL3-3");
/*  85:111 */     this.jLabel4.setForeground(Color.blue);
/*  86:112 */     this.jLabel4.setText("http://serg.mcs.drexel.edu/bunch");
/*  87:113 */     this.insetsPanel2.add(this.imageControl1, null);
/*  88:114 */     this.panel2.add(this.insetsPanel2, "West");
/*  89:115 */     add(this.panel1, null);
/*  90:116 */     this.insetsPanel3.add(this.label1, null);
/*  91:117 */     this.insetsPanel3.add(this.label2, null);
/*  92:118 */     this.insetsPanel3.add(this.label3, null);
/*  93:119 */     this.insetsPanel3.add(this.label4, null);
/*  94:120 */     this.insetsPanel3.add(this.jLabel1, null);
/*  95:121 */     this.insetsPanel3.add(this.jLabel2, null);
/*  96:122 */     this.insetsPanel3.add(this.jLabel3, null);
/*  97:123 */     this.insetsPanel3.add(this.jLabel4, null);
/*  98:124 */     this.panel2.add(this.insetsPanel3, "Center");
/*  99:125 */     this.insetsPanel1.add(this.button1, null);
/* 100:126 */     this.panel1.add(this.insetsPanel1, "South");
/* 101:127 */     this.panel1.add(this.panel2, "North");
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected void processWindowEvent(WindowEvent e)
/* 105:    */   {
/* 106:134 */     if (e.getID() == 201) {
/* 107:135 */       cancel();
/* 108:    */     }
/* 109:137 */     super.processWindowEvent(e);
/* 110:    */   }
/* 111:    */   
/* 112:    */   void cancel()
/* 113:    */   {
/* 114:143 */     dispose();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void actionPerformed(ActionEvent e)
/* 118:    */   {
/* 119:150 */     if (e.getSource() == this.button1) {
/* 120:151 */       cancel();
/* 121:    */     }
/* 122:    */   }
/* 123:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchFrame_AboutBox
 * JD-Core Version:    0.7.0.1
 */