/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.awt.Frame;
/*   4:    */ import java.awt.GridBagLayout;
/*   5:    */ import java.awt.Insets;
/*   6:    */ import java.util.Enumeration;
/*   7:    */ import javax.swing.JComboBox;
/*   8:    */ import javax.swing.JLabel;
/*   9:    */ import javax.swing.JPanel;
/*  10:    */ import javax.swing.JTextField;
/*  11:    */ 
/*  12:    */ public class Joon2ArchiveClusteringConfigurationDialog
/*  13:    */   extends ClusteringConfigurationDialog
/*  14:    */ {
/*  15: 38 */   GridBagLayout gridBagLayout2 = new GridBagLayout();
/*  16: 39 */   JLabel numGenlabel_d = new JLabel();
/*  17: 40 */   JTextField jTextField1 = new JTextField();
/*  18: 41 */   JLabel popSizelabel_d = new JLabel();
/*  19: 42 */   JTextField popSize_d = new JTextField();
/*  20: 43 */   JLabel crossLabel_d = new JLabel();
/*  21: 44 */   JTextField jTextField2 = new JTextField();
/*  22: 45 */   JLabel mutLabel_d = new JLabel();
/*  23: 46 */   JTextField jTextField3 = new JTextField();
/*  24: 47 */   JComboBox methodList_d = new JComboBox();
/*  25:    */   
/*  26:    */   public Joon2ArchiveClusteringConfigurationDialog(Frame frame, String title, boolean modal)
/*  27:    */   {
/*  28: 52 */     super(frame, title, modal);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Joon2ArchiveClusteringConfigurationDialog() {}
/*  32:    */   
/*  33:    */   public void jbInit()
/*  34:    */     throws Exception
/*  35:    */   {
/*  36: 69 */     this.numGenlabel_d.setText("Number Of Generations:");
/*  37: 70 */     this.popSizelabel_d.setText("Population Size:");
/*  38: 71 */     this.crossLabel_d.setText("Crossover Probability:");
/*  39: 72 */     this.mutLabel_d.setText("Mutation Probability:");
/*  40: 73 */     this.optionsPanel_d.setLayout(this.gridBagLayout2);
/*  41:    */     
/*  42: 75 */     this.jTextField1.setText(Integer.toString(this.configuration_d.getNumOfIterations()));
/*  43: 76 */     this.popSize_d.setText(Integer.toString(this.configuration_d.getPopulationSize()));
/*  44: 77 */     this.jTextField2.setText(Double.toString(((GAConfiguration)this.configuration_d).getCrossoverThreshold()));
/*  45: 78 */     this.jTextField3.setText(Double.toString(((GAConfiguration)this.configuration_d).getMutationThreshold()));
/*  46: 79 */     Enumeration e = ((GAConfiguration)this.configuration_d).getMethodFactory().getAvailableItems();
/*  47: 80 */     while (e.hasMoreElements()) {
/*  48: 81 */       this.methodList_d.addItem((String)e.nextElement());
/*  49:    */     }
/*  50: 84 */     this.optionsPanel_d.add(new JLabel("GA Selection Method:"), new GridBagConstraints2(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 5, 5), 0, 0));
/*  51:    */     
/*  52: 86 */     this.optionsPanel_d.add(this.methodList_d, new GridBagConstraints2(1, 0, 1, 1, 0.4D, 0.0D, 10, 2, new Insets(5, 5, 5, 5), 0, 0));
/*  53:    */     
/*  54: 88 */     this.optionsPanel_d.add(this.numGenlabel_d, new GridBagConstraints2(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 5, 5), 0, 0));
/*  55:    */     
/*  56: 90 */     this.optionsPanel_d.add(this.jTextField1, new GridBagConstraints2(1, 1, 1, 1, 0.4D, 0.0D, 10, 2, new Insets(5, 5, 5, 5), 0, 0));
/*  57:    */     
/*  58: 92 */     this.optionsPanel_d.add(this.popSizelabel_d, new GridBagConstraints2(0, 2, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 5, 5), 0, 0));
/*  59:    */     
/*  60: 94 */     this.optionsPanel_d.add(this.popSize_d, new GridBagConstraints2(1, 2, 1, 1, 0.4D, 0.0D, 10, 2, new Insets(5, 5, 5, 5), 0, 0));
/*  61:    */     
/*  62: 96 */     this.optionsPanel_d.add(this.crossLabel_d, new GridBagConstraints2(0, 3, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 5, 5), 0, 0));
/*  63:    */     
/*  64: 98 */     this.optionsPanel_d.add(this.jTextField2, new GridBagConstraints2(1, 3, 1, 1, 0.4D, 0.0D, 10, 2, new Insets(5, 5, 5, 5), 0, 0));
/*  65:    */     
/*  66:100 */     this.optionsPanel_d.add(this.mutLabel_d, new GridBagConstraints2(0, 4, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(5, 5, 5, 5), 0, 0));
/*  67:    */     
/*  68:102 */     this.optionsPanel_d.add(this.jTextField3, new GridBagConstraints2(1, 4, 1, 1, 0.4D, 0.0D, 10, 2, new Insets(5, 5, 5, 5), 0, 0));
/*  69:    */     
/*  70:    */ 
/*  71:    */ 
/*  72:106 */     super.jbInit();
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected Configuration createConfiguration()
/*  76:    */   {
/*  77:119 */     this.configuration_d.setNumOfIterations(Integer.parseInt(this.jTextField1.getText()));
/*  78:120 */     this.configuration_d.setPopulationSize(Integer.parseInt(this.popSize_d.getText()));
/*  79:121 */     ((GAConfiguration)this.configuration_d).setCrossoverThreshold(Double.valueOf(this.jTextField2.getText()).doubleValue());
/*  80:122 */     ((GAConfiguration)this.configuration_d).setMutationThreshold(Double.valueOf(this.jTextField3.getText()).doubleValue());
/*  81:123 */     ((GAConfiguration)this.configuration_d).setMethod((String)this.methodList_d.getSelectedItem());
/*  82:124 */     return this.configuration_d;
/*  83:    */   }
/*  84:    */ }



/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\

 * Qualified Name:     bunch.GAClusteringConfigurationDialog

 * JD-Core Version:    0.7.0.1

 */