/*  1:   */ package bunch;
/*  2:   */ 
/*  3:   */ import java.awt.GridBagConstraints;
/*  4:   */ import java.awt.Insets;
/*  5:   */ import java.io.Serializable;
/*  6:   */ 
/*  7:   */ public class GridBagConstraints2
/*  8:   */   extends GridBagConstraints
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:   */   public GridBagConstraints2(int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill, Insets insets, int ipadx, int ipady)
/* 12:   */   {
/* 13:50 */     this.gridx = gridx;
/* 14:51 */     this.gridy = gridy;
/* 15:52 */     this.gridwidth = gridwidth;
/* 16:53 */     this.gridheight = gridheight;
/* 17:54 */     this.fill = fill;
/* 18:55 */     this.ipadx = ipadx;
/* 19:56 */     this.ipady = ipady;
/* 20:57 */     this.insets = insets;
/* 21:58 */     this.anchor = anchor;
/* 22:59 */     this.weightx = weightx;
/* 23:60 */     this.weighty = weighty;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String toString()
/* 27:   */   {
/* 28:64 */     return ": " + this.gridx + "," + this.gridy + "," + this.gridwidth + "," + this.gridheight;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.GridBagConstraints2
 * JD-Core Version:    0.7.0.1
 */