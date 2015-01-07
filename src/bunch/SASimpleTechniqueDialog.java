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
/*  11:    */ import java.util.Hashtable;
/*  12:    */ import javax.swing.JButton;
/*  13:    */ import javax.swing.JDialog;
/*  14:    */ import javax.swing.JLabel;
/*  15:    */ import javax.swing.JPanel;
/*  16:    */ import javax.swing.JTextField;
/*  17:    */ 
/*  18:    */ public class SASimpleTechniqueDialog
/*  19:    */   extends JDialog
/*  20:    */ {
/*  21: 32 */   JPanel panel1 = new JPanel();
/*  22: 33 */   GridBagLayout gridBagLayout1 = new GridBagLayout();
/*  23: 34 */   JLabel jLabel1 = new JLabel();
/*  24: 35 */   JTextField initialTempEF = new JTextField();
/*  25: 36 */   JLabel jLabel2 = new JLabel();
/*  26: 37 */   JTextField alphaEF = new JTextField();
/*  27: 38 */   JLabel DescriptionST = new JLabel();
/*  28: 39 */   JPanel jPanel1 = new JPanel();
/*  29: 40 */   JButton okPB = new JButton();
/*  30: 41 */   JButton cancelPB = new JButton();
/*  31: 43 */   SASimpleTechnique saTechnique = null;
/*  32:    */   
/*  33:    */   public SASimpleTechniqueDialog(Frame frame, String title, boolean modal)
/*  34:    */   {
/*  35: 46 */     super(frame, title, modal);
/*  36:    */     try
/*  37:    */     {
/*  38: 48 */       jbInit();
/*  39: 49 */       pack();
/*  40:    */     }
/*  41:    */     catch (Exception ex)
/*  42:    */     {
/*  43: 52 */       ex.printStackTrace();
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setSATechnique(SASimpleTechnique s)
/*  48:    */   {
/*  49: 57 */     this.saTechnique = s;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public SASimpleTechniqueDialog()
/*  53:    */   {
/*  54: 60 */     this(null, "", false);
/*  55:    */   }
/*  56:    */   
/*  57:    */   void jbInit()
/*  58:    */     throws Exception
/*  59:    */   {
/*  60: 64 */     this.panel1.setLayout(this.gridBagLayout1);
/*  61: 65 */     this.jLabel1.setText("Initial Temp. T(0):");
/*  62: 66 */     this.jLabel2.setText("Alpha:");
/*  63: 67 */     this.DescriptionST.setForeground(Color.red);
/*  64: 68 */     this.DescriptionST.setText("Description:");
/*  65: 69 */     this.okPB.setText("OK");
/*  66: 70 */     this.okPB.addActionListener(new ActionListener()
/*  67:    */     {
/*  68:    */       public void actionPerformed(ActionEvent e)
/*  69:    */       {
/*  70: 73 */         SASimpleTechniqueDialog.this.okPB_actionPerformed(e);
/*  71:    */       }
/*  72: 75 */     });
/*  73: 76 */     this.cancelPB.setText("Cancel");
/*  74: 77 */     this.cancelPB.addActionListener(new ActionListener()
/*  75:    */     {
/*  76:    */       public void actionPerformed(ActionEvent e)
/*  77:    */       {
/*  78: 80 */         SASimpleTechniqueDialog.this.cancelPB_actionPerformed(e);
/*  79:    */       }
/*  80: 82 */     });
/*  81: 83 */     getContentPane().add(this.panel1, "Center");
/*  82: 84 */     this.panel1.add(this.jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 10, 0, 10), 0, 0));
/*  83:    */     
/*  84: 86 */     this.panel1.add(this.initialTempEF, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 10), 155, 0));
/*  85:    */     
/*  86: 88 */     this.panel1.add(this.jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 10, 0, 10), 0, 0));
/*  87:    */     
/*  88: 90 */     this.panel1.add(this.alphaEF, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 10), 0, 0));
/*  89:    */     
/*  90: 92 */     this.panel1.add(this.DescriptionST, new GridBagConstraints(0, 2, 2, 1, 0.0D, 0.0D, 10, 2, new Insets(5, 10, 0, 0), 0, 0));
/*  91:    */     
/*  92: 94 */     getContentPane().add(this.jPanel1, "South");
/*  93: 95 */     this.jPanel1.add(this.okPB, null);
/*  94: 96 */     this.jPanel1.add(this.cancelPB, null);
/*  95:    */     
/*  96: 98 */     this.DescriptionST.setText("Description Goes Here");
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setConfiguration(Hashtable h)
/* 100:    */   {
/* 101:103 */     Double alpha = (Double)h.get("Alpha");
/* 102:104 */     Double temp = (Double)h.get("InitialTemp");
/* 103:105 */     this.DescriptionST.setText(SASimpleTechnique.getDescription());
/* 104:107 */     if ((alpha == null) || (temp == null)) {
/* 105:108 */       return;
/* 106:    */     }
/* 107:110 */     this.alphaEF.setText(alpha.toString());
/* 108:111 */     this.initialTempEF.setText(temp.toString());
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Hashtable getConfiguration()
/* 112:    */   {
/* 113:116 */     Hashtable h = new Hashtable();
/* 114:117 */     h.clear();
/* 115:118 */     h.put("Alpha", new Double(this.alphaEF.getText()));
/* 116:119 */     h.put("InitialTemp", new Double(this.initialTempEF.getText()));
/* 117:120 */     return h;
/* 118:    */   }
/* 119:    */   
/* 120:    */   void okPB_actionPerformed(ActionEvent e)
/* 121:    */   {
/* 122:124 */     setVisible(false);
/* 123:125 */     if (this.saTechnique != null)
/* 124:    */     {
/* 125:127 */       Hashtable h = getConfiguration();
/* 126:128 */       this.saTechnique.setConfig(h);
/* 127:    */     }
/* 128:130 */     dispose();
/* 129:    */   }
/* 130:    */   
/* 131:    */   void cancelPB_actionPerformed(ActionEvent e)
/* 132:    */   {
/* 133:134 */     dispose();
/* 134:    */   }
/* 135:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.SASimpleTechniqueDialog
 * JD-Core Version:    0.7.0.1
 */