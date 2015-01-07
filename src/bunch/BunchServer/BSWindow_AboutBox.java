/*   1:    */ package bunch.BunchServer;
/*   2:    */ 
/*   3:    */ import java.awt.BorderLayout;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.awt.FlowLayout;
/*   6:    */ import java.awt.Frame;
/*   7:    */ import java.awt.GridLayout;
/*   8:    */ import java.awt.event.ActionEvent;
/*   9:    */ import java.awt.event.ActionListener;
/*  10:    */ import java.awt.event.WindowEvent;
/*  11:    */ import javax.swing.ImageIcon;
/*  12:    */ import javax.swing.JButton;
/*  13:    */ import javax.swing.JDialog;
/*  14:    */ import javax.swing.JLabel;
/*  15:    */ import javax.swing.JPanel;
/*  16:    */ import javax.swing.border.EmptyBorder;
/*  17:    */ 
/*  18:    */ public class BSWindow_AboutBox
/*  19:    */   extends JDialog
/*  20:    */   implements ActionListener
/*  21:    */ {
/*  22: 27 */   JPanel panel1 = new JPanel();
/*  23: 28 */   JPanel panel2 = new JPanel();
/*  24: 29 */   JPanel insetsPanel1 = new JPanel();
/*  25: 30 */   JPanel insetsPanel2 = new JPanel();
/*  26: 31 */   JPanel insetsPanel3 = new JPanel();
/*  27: 32 */   JButton button1 = new JButton();
/*  28: 33 */   JLabel imageControl1 = new JLabel();
/*  29:    */   ImageIcon imageIcon;
/*  30: 35 */   JLabel label1 = new JLabel();
/*  31: 36 */   JLabel label2 = new JLabel();
/*  32: 37 */   JLabel label3 = new JLabel();
/*  33: 38 */   JLabel label4 = new JLabel();
/*  34: 39 */   BorderLayout borderLayout1 = new BorderLayout();
/*  35: 40 */   BorderLayout borderLayout2 = new BorderLayout();
/*  36: 41 */   FlowLayout flowLayout1 = new FlowLayout();
/*  37: 42 */   FlowLayout flowLayout2 = new FlowLayout();
/*  38: 43 */   GridLayout gridLayout1 = new GridLayout();
/*  39: 44 */   String product = "Your Product Name";
/*  40: 45 */   String version = "";
/*  41: 46 */   String copyright = "Copyright (c) 1999";
/*  42: 47 */   String comments = "Your description";
/*  43:    */   
/*  44:    */   public BSWindow_AboutBox(Frame parent)
/*  45:    */   {
/*  46: 49 */     super(parent);
/*  47: 50 */     enableEvents(64L);
/*  48:    */     try
/*  49:    */     {
/*  50: 52 */       jbInit();
/*  51:    */     }
/*  52:    */     catch (Exception e)
/*  53:    */     {
/*  54: 55 */       e.printStackTrace();
/*  55:    */     }
/*  56: 58 */     pack();
/*  57:    */   }
/*  58:    */   
/*  59:    */   private void jbInit()
/*  60:    */     throws Exception
/*  61:    */   {
/*  62: 63 */     setTitle("About");
/*  63: 64 */     setResizable(false);
/*  64: 65 */     this.panel1.setLayout(this.borderLayout1);
/*  65: 66 */     this.panel2.setLayout(this.borderLayout2);
/*  66: 67 */     this.insetsPanel1.setLayout(this.flowLayout1);
/*  67: 68 */     this.insetsPanel2.setLayout(this.flowLayout1);
/*  68: 69 */     this.insetsPanel2.setBorder(new EmptyBorder(10, 10, 10, 10));
/*  69: 70 */     this.gridLayout1.setRows(4);
/*  70: 71 */     this.gridLayout1.setColumns(1);
/*  71: 72 */     this.label1.setText(this.product);
/*  72: 73 */     this.label2.setText(this.version);
/*  73: 74 */     this.label3.setText(this.copyright);
/*  74: 75 */     this.label4.setText(this.comments);
/*  75: 76 */     this.insetsPanel3.setLayout(this.gridLayout1);
/*  76: 77 */     this.insetsPanel3.setBorder(new EmptyBorder(10, 60, 10, 10));
/*  77: 78 */     this.button1.setText("OK");
/*  78: 79 */     this.button1.addActionListener(this);
/*  79: 80 */     this.insetsPanel2.add(this.imageControl1, null);
/*  80: 81 */     this.panel2.add(this.insetsPanel2, "West");
/*  81: 82 */     getContentPane().add(this.panel1, null);
/*  82: 83 */     this.insetsPanel3.add(this.label1, null);
/*  83: 84 */     this.insetsPanel3.add(this.label2, null);
/*  84: 85 */     this.insetsPanel3.add(this.label3, null);
/*  85: 86 */     this.insetsPanel3.add(this.label4, null);
/*  86: 87 */     this.panel2.add(this.insetsPanel3, "Center");
/*  87: 88 */     this.insetsPanel1.add(this.button1, null);
/*  88: 89 */     this.panel1.add(this.insetsPanel1, "South");
/*  89: 90 */     this.panel1.add(this.panel2, "North");
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected void processWindowEvent(WindowEvent e)
/*  93:    */   {
/*  94: 94 */     if (e.getID() == 201) {
/*  95: 95 */       cancel();
/*  96:    */     }
/*  97: 97 */     super.processWindowEvent(e);
/*  98:    */   }
/*  99:    */   
/* 100:    */   void cancel()
/* 101:    */   {
/* 102:101 */     dispose();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void actionPerformed(ActionEvent e)
/* 106:    */   {
/* 107:105 */     if (e.getSource() == this.button1) {
/* 108:106 */       cancel();
/* 109:    */     }
/* 110:    */   }
/* 111:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchServer.BSWindow_AboutBox
 * JD-Core Version:    0.7.0.1
 */