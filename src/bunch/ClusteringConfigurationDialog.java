/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.awt.Container;
/*   4:    */ import java.awt.Frame;
/*   5:    */ import java.awt.GridBagConstraints;
/*   6:    */ import java.awt.GridBagLayout;
/*   7:    */ import java.awt.Insets;
/*   8:    */ import java.awt.event.ActionEvent;
/*   9:    */ import javax.swing.JButton;
/*  10:    */ import javax.swing.JDialog;
/*  11:    */ import javax.swing.JPanel;
/*  12:    */ 
/*  13:    */ public abstract class ClusteringConfigurationDialog
/*  14:    */   extends JDialog
/*  15:    */ {
/*  16: 44 */   JPanel panel1 = new JPanel();
/*  17:    */   Configuration configuration_d;
/*  18: 46 */   GridBagLayout gridBagLayout1 = new GridBagLayout();
/*  19: 47 */   JButton okButton_d = new JButton();
/*  20: 48 */   JButton cancelButton_d = new JButton();
/*  21: 49 */   Frame parentFrame = null;
/*  22: 54 */   JPanel optionsPanel_d = new JPanel();
/*  23:    */   Graph graph_d;
/*  24:    */   
/*  25:    */   public ClusteringConfigurationDialog(Frame frame, String title, boolean modal)
/*  26:    */   {
/*  27: 63 */     super(frame, title, modal);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ClusteringConfigurationDialog()
/*  31:    */   {
/*  32: 72 */     this(null, "", false);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setParentFrame(Frame f)
/*  36:    */   {
/*  37: 79 */     this.parentFrame = f;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Frame getParenetFrame()
/*  41:    */   {
/*  42: 85 */     return this.parentFrame;
/*  43:    */   }
/*  44:    */   
/*  45:    */   void jbInit()
/*  46:    */     throws Exception
/*  47:    */   {
/*  48: 94 */     this.panel1.setLayout(this.gridBagLayout1);
/*  49: 95 */     this.okButton_d.setText("  OK  ");
/*  50: 96 */     this.okButton_d.addActionListener(new ClusteringConfigurationDialog_okButton_d_actionAdapter(this));
/*  51: 97 */     this.cancelButton_d.setText("Cancel");
/*  52: 98 */     this.cancelButton_d.addActionListener(new ClusteringConfigurationDialog_cancelButton_d_actionAdapter(this));
/*  53:    */     
/*  54:100 */     getContentPane().add(this.panel1);
/*  55:101 */     this.panel1.add(this.okButton_d, new GridBagConstraints2(0, 1, 1, 1, 0.5D, 0.0D, 10, 0, new Insets(5, 5, 5, 5), 0, 0));
/*  56:    */     
/*  57:103 */     this.panel1.add(this.cancelButton_d, new GridBagConstraints2(1, 1, 1, 1, 0.5D, 0.0D, 10, 0, new Insets(5, 5, 5, 5), 0, 0));
/*  58:    */     
/*  59:105 */     this.panel1.add(this.optionsPanel_d, new GridBagConstraints(0, 0, 4, 1, 1.0D, 1.0D, 10, 1, new Insets(5, 5, 5, 0), 0, 0));
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Configuration getConfiguration()
/*  63:    */   {
/*  64:119 */     return this.configuration_d;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setConfiguration(Configuration c)
/*  68:    */   {
/*  69:133 */     this.configuration_d = c;
/*  70:    */   }
/*  71:    */   
/*  72:    */   void cancelButton_d_actionPerformed(ActionEvent e)
/*  73:    */   {
/*  74:144 */     setConfiguration(null);
/*  75:145 */     setVisible(false);
/*  76:146 */     dispose();
/*  77:    */   }
/*  78:    */   
/*  79:    */   void okButton_d_actionPerformed(ActionEvent e)
/*  80:    */   {
/*  81:157 */     setConfiguration(createConfiguration());
/*  82:158 */     setVisible(false);
/*  83:159 */     dispose();
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected abstract Configuration createConfiguration();
/*  87:    */   
/*  88:    */   public void setGraph(Graph g)
/*  89:    */   {
/*  90:188 */     this.graph_d = g;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Graph getGraph()
/*  94:    */   {
/*  95:202 */     return this.graph_d;
/*  96:    */   }
/*  97:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.ClusteringConfigurationDialog
 * JD-Core Version:    0.7.0.1
 */