/*  1:   */ package bunch;
/*  2:   */ 
/*  3:   */ import java.awt.Container;
/*  4:   */ import java.awt.Frame;
/*  5:   */ import javax.swing.JDialog;
/*  6:   */ import javax.swing.JPanel;
/*  7:   */ 
/*  8:   */ public class BunchPreferencesDialog
/*  9:   */   extends JDialog
/* 10:   */ {
/* 11:33 */   JPanel panel1 = new JPanel();
/* 12:   */   
/* 13:   */   public BunchPreferencesDialog(Frame frame, String title, boolean modal)
/* 14:   */   {
/* 15:38 */     super(frame, title, modal);
/* 16:   */     try
/* 17:   */     {
/* 18:40 */       jbInit();
/* 19:41 */       pack();
/* 20:   */     }
/* 21:   */     catch (Exception ex)
/* 22:   */     {
/* 23:44 */       ex.printStackTrace();
/* 24:   */     }
/* 25:   */   }
/* 26:   */   
/* 27:   */   public BunchPreferencesDialog()
/* 28:   */   {
/* 29:51 */     this(null, "", false);
/* 30:   */   }
/* 31:   */   
/* 32:   */   private void jbInit()
/* 33:   */     throws Exception
/* 34:   */   {
/* 35:58 */     getContentPane().add(this.panel1);
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchPreferencesDialog
 * JD-Core Version:    0.7.0.1
 */