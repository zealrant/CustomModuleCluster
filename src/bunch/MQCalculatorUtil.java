/*  1:   */ package bunch;
/*  2:   */ 
/*  3:   */ import java.awt.Container;
/*  4:   */ import java.awt.Frame;
/*  5:   */ import java.awt.GridBagConstraints;
/*  6:   */ import java.awt.GridBagLayout;
/*  7:   */ import java.awt.Insets;
/*  8:   */ import java.awt.event.ActionEvent;
/*  9:   */ import javax.swing.JButton;
/* 10:   */ import javax.swing.JComboBox;
/* 11:   */ import javax.swing.JDialog;
/* 12:   */ import javax.swing.JLabel;
/* 13:   */ import javax.swing.JPanel;
/* 14:   */ import javax.swing.JTextField;
/* 15:   */ 
/* 16:   */ public class MQCalculatorUtil
/* 17:   */   extends JDialog
/* 18:   */ {
/* 19:38 */   JPanel panel1 = new JPanel();
/* 20:39 */   GridBagLayout gridBagLayout1 = new GridBagLayout();
/* 21:40 */   JLabel jLabel1 = new JLabel();
/* 22:41 */   JTextField fileNameEF = new JTextField();
/* 23:42 */   JButton fileSelectPB = new JButton();
/* 24:43 */   JLabel jLabel2 = new JLabel();
/* 25:44 */   JComboBox jComboBox1 = new JComboBox();
/* 26:45 */   JButton evaluatePB = new JButton();
/* 27:46 */   JButton cancelPB = new JButton();
/* 28:   */   
/* 29:   */   public MQCalculatorUtil(Frame frame, String title, boolean modal)
/* 30:   */   {
/* 31:49 */     super(frame, title, modal);
/* 32:   */     try
/* 33:   */     {
/* 34:51 */       jbInit();
/* 35:52 */       pack();
/* 36:   */     }
/* 37:   */     catch (Exception ex)
/* 38:   */     {
/* 39:55 */       ex.printStackTrace();
/* 40:   */     }
/* 41:   */   }
/* 42:   */   
/* 43:   */   public MQCalculatorUtil()
/* 44:   */   {
/* 45:60 */     this(null, "", false);
/* 46:   */   }
/* 47:   */   
/* 48:   */   void jbInit()
/* 49:   */     throws Exception
/* 50:   */   {
/* 51:67 */     this.panel1.setLayout(this.gridBagLayout1);
/* 52:68 */     this.jLabel1.setText("Input File Name:");
/* 53:69 */     this.fileSelectPB.setText("Select ...");
/* 54:70 */     this.jLabel2.setText("MQ Calculator:");
/* 55:71 */     this.evaluatePB.setText("Evaluate...");
/* 56:72 */     this.cancelPB.setText("Done");
/* 57:73 */     this.cancelPB.addActionListener(new MQCalculatorUtil_cancelPB_actionAdapter(this));
/* 58:74 */     getContentPane().add(this.panel1);
/* 59:75 */     this.panel1.add(this.jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 5, 5), 0, 0));
/* 60:   */     
/* 61:77 */     this.panel1.add(this.fileNameEF, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 5, 5), 158, 0));
/* 62:   */     
/* 63:79 */     this.panel1.add(this.fileSelectPB, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 5, 0), 0, -6));
/* 64:   */     
/* 65:81 */     this.panel1.add(this.jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(6, 0, 0, 0), 0, 0));
/* 66:   */     
/* 67:83 */     this.panel1.add(this.jComboBox1, new GridBagConstraints(1, 1, 2, 1, 0.0D, 0.0D, 16, 1, new Insets(0, 0, 0, 0), 0, -8));
/* 68:   */     
/* 69:85 */     this.panel1.add(this.evaluatePB, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(6, 0, 0, 0), 0, -7));
/* 70:   */     
/* 71:87 */     this.panel1.add(this.cancelPB, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 16, 0, new Insets(0, 5, 0, 0), 0, -7));
/* 72:   */   }
/* 73:   */   
/* 74:   */   void cancelPB_actionPerformed(ActionEvent e)
/* 75:   */   {
/* 76:92 */     dispose();
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.MQCalculatorUtil
 * JD-Core Version:    0.7.0.1
 */