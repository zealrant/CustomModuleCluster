/*   1:    */ package bunch.BunchServer;
/*   2:    */ 
/*   3:    */ import java.awt.Dimension;
/*   4:    */ import java.awt.Toolkit;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import javax.swing.UIManager;
/*   7:    */ 
/*   8:    */ public class BunchServer
/*   9:    */ {
/*  10: 24 */   boolean packFrame = false;
/*  11: 25 */   boolean guiMode = true;
/*  12: 26 */   String[] startupArgs = null;
/*  13:    */   
/*  14:    */   public void setStartupParms(String[] args, boolean runMode)
/*  15:    */   {
/*  16: 34 */     this.guiMode = runMode;
/*  17: 35 */     this.startupArgs = args;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void start()
/*  21:    */   {
/*  22: 40 */     if (this.guiMode == true) {
/*  23: 41 */       BunchServerGui();
/*  24:    */     } else {
/*  25: 43 */       BunchServerText();
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void BunchServerText()
/*  30:    */   {
/*  31: 48 */     if (this.startupArgs.length != 4)
/*  32:    */     {
/*  33: 50 */       System.out.println("Usage:  BunchServer NameSpace ServerName NameServerName NameServerPort");
/*  34: 51 */       System.exit(0);
/*  35:    */     }
/*  36:    */     else
/*  37:    */     {
/*  38:    */       try
/*  39:    */       {
/*  40: 56 */         BSTextServer bts = new BSTextServer(this.startupArgs);
/*  41: 57 */         bts.start();
/*  42:    */       }
/*  43:    */       catch (Exception ex)
/*  44:    */       {
/*  45: 60 */         System.out.println("Exception in text server: " + ex.toString());
/*  46:    */       }
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void BunchServerGui()
/*  51:    */   {
/*  52: 66 */     BSWindow frame = new BSWindow();
/*  53: 69 */     if (this.packFrame) {
/*  54: 70 */       frame.pack();
/*  55:    */     } else {
/*  56: 72 */       frame.validate();
/*  57:    */     }
/*  58: 74 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/*  59: 75 */     Dimension frameSize = frame.getSize();
/*  60: 76 */     if (frameSize.height > screenSize.height) {
/*  61: 77 */       frameSize.height = screenSize.height;
/*  62:    */     }
/*  63: 78 */     if (frameSize.width > screenSize.width) {
/*  64: 79 */       frameSize.width = screenSize.width;
/*  65:    */     }
/*  66: 80 */     frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
/*  67: 81 */     frame.setVisible(true);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static void main(String[] args)
/*  71:    */   {
/*  72: 86 */     boolean startGuiMode = true;
/*  73:    */     try
/*  74:    */     {
/*  75: 89 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*  76:    */     }
/*  77:    */     catch (Exception e) {}
/*  78: 95 */     if (args.length == 0) {
/*  79: 96 */       startGuiMode = true;
/*  80:    */     } else {
/*  81: 98 */       startGuiMode = false;
/*  82:    */     }
/*  83:100 */     BunchServer theServer = new BunchServer();
/*  84:101 */     theServer.setStartupParms(args, startGuiMode);
/*  85:102 */     theServer.start();
/*  86:    */   }
/*  87:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchServer.BunchServer
 * JD-Core Version:    0.7.0.1
 */