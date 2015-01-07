/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.awt.event.ActionEvent;
/*   4:    */ import java.awt.event.ActionListener;
/*   5:    */ 
/*   6:    */ class ClusteringConfigurationDialog_cancelButton_d_actionAdapter
/*   7:    */   implements ActionListener
/*   8:    */ {
/*   9:    */   ClusteringConfigurationDialog adaptee;
/*  10:    */   
/*  11:    */   ClusteringConfigurationDialog_cancelButton_d_actionAdapter(ClusteringConfigurationDialog adaptee)
/*  12:    */   {
/*  13:216 */     this.adaptee = adaptee;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public void actionPerformed(ActionEvent e)
/*  17:    */   {
/*  18:220 */     this.adaptee.cancelButton_d_actionPerformed(e);
/*  19:    */   }
/*  20:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.ClusteringConfigurationDialog_cancelButton_d_actionAdapter
 * JD-Core Version:    0.7.0.1
 */