/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.awt.Frame;
/*   4:    */ import java.awt.GridBagLayout;
/*   5:    */ import java.awt.Insets;
/*   6:    */ import javax.swing.JLabel;
/*   7:    */ import javax.swing.JPanel;
/*   8:    */ import javax.swing.JTextField;
/*   9:    */ 
/*  10:    */ public class HillClimbingClusteringConfigurationDialog
/*  11:    */   extends ClusteringConfigurationDialog
/*  12:    */ {
/*  13: 51 */   GridBagLayout gridBagLayout2 = new GridBagLayout();
/*  14: 52 */   JLabel numGenlabel_d = new JLabel();
/*  15: 53 */   JTextField jTextField1 = new JTextField();
/*  16: 54 */   JLabel popSizelabel_d = new JLabel();
/*  17: 55 */   JTextField popSize_d = new JTextField();
/*  18: 56 */   JLabel thresholdLabel_d = new JLabel();
/*  19: 57 */   JTextField jTextField2 = new JTextField();
/*  20:    */   
/*  21:    */   public HillClimbingClusteringConfigurationDialog(Frame frame, String title, boolean modal)
/*  22:    */   {
/*  23: 63 */     super(frame, title, modal);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public HillClimbingClusteringConfigurationDialog() {}
/*  27:    */   
/*  28:    */   public void jbInit()
/*  29:    */     throws Exception
/*  30:    */   {
/*  31: 81 */     this.numGenlabel_d.setText("Number Of Generations:");
/*  32: 82 */     this.popSizelabel_d.setText("Population Size:");
/*  33: 83 */     this.thresholdLabel_d.setText("Cutoff Threshold:");
/*  34: 84 */     this.optionsPanel_d.setLayout(this.gridBagLayout2);
/*  35:    */     
/*  36: 86 */     this.jTextField1.setText(Integer.toString(this.configuration_d.getNumOfIterations()));
/*  37: 87 */     this.popSize_d.setText(Integer.toString(this.configuration_d.getPopulationSize()));
/*  38: 88 */     this.jTextField2.setText(Double.toString(((HillClimbingConfiguration)this.configuration_d).getThreshold()));
/*  39:    */     
/*  40: 90 */     this.optionsPanel_d.add(this.numGenlabel_d, new GridBagConstraints2(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 5, 5), 0, 0));
/*  41:    */     
/*  42: 92 */     this.optionsPanel_d.add(this.jTextField1, new GridBagConstraints2(1, 0, 1, 1, 0.4D, 0.0D, 10, 2, new Insets(5, 5, 5, 5), 0, 0));
/*  43:    */     
/*  44: 94 */     this.optionsPanel_d.add(this.popSizelabel_d, new GridBagConstraints2(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 5, 5), 0, 0));
/*  45:    */     
/*  46: 96 */     this.optionsPanel_d.add(this.popSize_d, new GridBagConstraints2(1, 1, 1, 1, 0.4D, 0.0D, 10, 2, new Insets(5, 5, 5, 5), 0, 0));
/*  47:    */     
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:104 */     this.numGenlabel_d.setVisible(false);
/*  55:105 */     this.jTextField1.setVisible(false);
/*  56:    */     
/*  57:107 */     super.jbInit();
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected Configuration createConfiguration()
/*  61:    */   {
/*  62:120 */     this.configuration_d.setNumOfIterations(Integer.parseInt(this.jTextField1.getText()));
/*  63:121 */     this.configuration_d.setPopulationSize(Integer.parseInt(this.popSize_d.getText()));
/*  64:122 */     ((HillClimbingConfiguration)this.configuration_d).setThreshold(Double.valueOf(this.jTextField2.getText()).doubleValue());
/*  65:123 */     return this.configuration_d;
/*  66:    */   }
/*  67:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.HillClimbingClusteringConfigurationDialog
 * JD-Core Version:    0.7.0.1
 */