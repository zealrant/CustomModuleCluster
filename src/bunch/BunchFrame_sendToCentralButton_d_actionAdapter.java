/*    1:     */ package bunch;
/*    2:     */ 
/*    3:     */ import java.awt.event.ActionEvent;
/*    4:     */ import java.awt.event.ActionListener;
/*    5:     */ 
/*    6:     */ class BunchFrame_sendToCentralButton_d_actionAdapter
/*    7:     */   implements ActionListener
/*    8:     */ {
/*    9:     */   BunchFrame adaptee;
/*   10:     */   
/*   11:     */   BunchFrame_sendToCentralButton_d_actionAdapter(BunchFrame adaptee)
/*   12:     */   {
/*   13:3010 */     this.adaptee = adaptee;
/*   14:     */   }
/*   15:     */   
/*   16:     */   public void actionPerformed(ActionEvent e)
/*   17:     */   {
/*   18:3014 */     this.adaptee.sendToCentralButton_d_actionPerformed(e);
/*   19:     */   }
/*   20:     */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchFrame_sendToCentralButton_d_actionAdapter
 * JD-Core Version:    0.7.0.1
 */