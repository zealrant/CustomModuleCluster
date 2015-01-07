/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import bunch.BunchServer.BunchServer;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Toolkit;
/*   6:    */ import java.io.FileOutputStream;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import javax.swing.UIManager;
/*   9:    */ import javax.swing.plaf.metal.MetalLookAndFeel;
/*  10:    */ 
/*  11:    */ public class Bunch
/*  12:    */ {
/*  13: 39 */   boolean packFrame = false;
/*  14:    */   
/*  15:    */   public Bunch()
/*  16:    */   {
/*  17: 44 */     BunchFrame frame = new BunchFrame();
/*  18: 48 */     if (this.packFrame) {
/*  19: 49 */       frame.pack();
/*  20:    */     } else {
/*  21: 51 */       frame.validate();
/*  22:    */     }
/*  23: 54 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/*  24: 55 */     Dimension frameSize = frame.getSize();
/*  25: 56 */     if (frameSize.height > screenSize.height) {
/*  26: 57 */       frameSize.height = screenSize.height;
/*  27:    */     }
/*  28: 58 */     if (frameSize.width > screenSize.width) {
/*  29: 59 */       frameSize.width = screenSize.width;
/*  30:    */     }
/*  31: 60 */     frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
/*  32: 61 */     frame.setVisible(true);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static void main(String[] args)
/*  36:    */   {
/*  37:    */     try
/*  38:    */     {
/*  39: 69 */       if (args.length > 0) {
/*  40: 70 */         System.setErr(new PrintStream(new FileOutputStream(args[0])));
/*  41:    */       }
/*  42: 78 */       UIManager.setLookAndFeel(new MetalLookAndFeel());
/*  43:    */     }
/*  44:    */     catch (Exception e)
/*  45:    */     {
/*  46:    */       try
/*  47:    */       {
/*  48: 82 */         UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
/*  49:    */       }
/*  50:    */       catch (Exception e2)
/*  51:    */       {
/*  52: 84 */         e2.printStackTrace();
/*  53:    */       }
/*  54:    */     }
/*  55: 88 */     if (args.length == 1)
/*  56:    */     {
/*  57: 90 */       String a = args[0];
/*  58: 95 */       if ((a.equalsIgnoreCase("-s")) || (a.equalsIgnoreCase("-server")))
/*  59:    */       {
/*  60: 97 */         BunchServer theServer = new BunchServer();
/*  61: 98 */         theServer.setStartupParms(args, true);
/*  62: 99 */         theServer.start();
/*  63:    */       }
/*  64:    */       else
/*  65:    */       {
/*  66:102 */         System.out.println("Bad arguement, for BunchServer use -s or -server");
/*  67:    */       }
/*  68:    */     }
/*  69:    */     else
/*  70:    */     {
/*  71:105 */       new Bunch();
/*  72:    */     }
/*  73:    */   }
/*  74:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.Bunch
 * JD-Core Version:    0.7.0.1
 */