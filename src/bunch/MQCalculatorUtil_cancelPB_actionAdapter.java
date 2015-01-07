/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.awt.event.ActionEvent;
/*   4:    */ import java.awt.event.ActionListener;
/*   5:    */ 
/*   6:    */ class MQCalculatorUtil_cancelPB_actionAdapter
/*   7:    */   implements ActionListener
/*   8:    */ {
/*   9:    */   MQCalculatorUtil adaptee;
/*  10:    */   
/*  11:    */   MQCalculatorUtil_cancelPB_actionAdapter(MQCalculatorUtil adaptee)
/*  12:    */   {
/*  13:100 */     this.adaptee = adaptee;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public void actionPerformed(ActionEvent e)
/*  17:    */   {
/*  18:104 */     this.adaptee.cancelPB_actionPerformed(e);
/*  19:    */   }
/*  20:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.MQCalculatorUtil_cancelPB_actionAdapter
 * JD-Core Version:    0.7.0.1
 */