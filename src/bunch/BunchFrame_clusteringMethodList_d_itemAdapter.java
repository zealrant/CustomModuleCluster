/*    1:     */ package bunch;
/*    2:     */ 
/*    3:     */ import java.awt.event.ItemEvent;
/*    4:     */ import java.awt.event.ItemListener;
/*    5:     */ 
/*    6:     */ class BunchFrame_clusteringMethodList_d_itemAdapter
/*    7:     */   implements ItemListener
/*    8:     */ {
/*    9:     */   BunchFrame adaptee;
/*   10:     */   
/*   11:     */   BunchFrame_clusteringMethodList_d_itemAdapter(BunchFrame adaptee)
/*   12:     */   {
/*   13:2851 */     this.adaptee = adaptee;
/*   14:     */   }
/*   15:     */   
/*   16:     */   public void itemStateChanged(ItemEvent e)
/*   17:     */   {
/*   18:2855 */     this.adaptee.clusteringMethodList_d_itemStateChanged(e);
/*   19:     */   }
/*   20:     */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchFrame_clusteringMethodList_d_itemAdapter
 * JD-Core Version:    0.7.0.1
 */