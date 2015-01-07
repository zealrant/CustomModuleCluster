/*   1:    */ package bunch.BunchServer;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Properties;
/*   5:    */ import javax.naming.CompositeName;
/*   6:    */ import javax.naming.InitialContext;
/*   7:    */ 
/*   8:    */ public class BSTextServer
/*   9:    */ {
/*  10: 37 */   String nameSpace = "";
/*  11: 38 */   String server = "";
/*  12: 39 */   String nameSvr = "";
/*  13: 40 */   String port = "";
/*  14: 41 */   BunchSvrMsgImpl bunchMsg = null;
/*  15: 42 */   InitialContext corbaContext = null;
/*  16: 43 */   String jndiName = "";
/*  17:    */   
/*  18:    */   public BSTextServer(String[] args)
/*  19:    */     throws Exception
/*  20:    */   {
/*  21: 47 */     if (args.length != 4) {
/*  22: 48 */       throw new Exception("Invalid Parameter(s), can not start text server!");
/*  23:    */     }
/*  24: 50 */     this.nameSpace = args[0];
/*  25: 51 */     this.server = args[1];
/*  26: 52 */     this.nameSvr = args[2];
/*  27: 53 */     this.port = args[3];
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getJndiName()
/*  31:    */   {
/*  32: 58 */     return this.jndiName;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean start()
/*  36:    */   {
/*  37:    */     try
/*  38:    */     {
/*  39: 66 */       Properties env = new Properties();
/*  40:    */       
/*  41: 68 */       env.put("java.naming.factory.initial", "com.sun.jndi.cosnaming.CNCtxFactory");
/*  42:    */       
/*  43: 70 */       String nsURL = "iiop://" + this.nameSvr + ":" + this.port;
/*  44: 71 */       System.out.println("Name Server URL: " + nsURL);
/*  45:    */       
/*  46: 73 */       String cnStr = "/" + this.nameSpace + "/" + this.server;
/*  47: 74 */       this.jndiName = cnStr;
/*  48: 75 */       System.out.println("Object Registration Name: " + cnStr);
/*  49:    */       
/*  50: 77 */       env.put("java.naming.provider.url", nsURL);
/*  51:    */       
/*  52: 79 */       InitialContext context = new InitialContext(env);
/*  53:    */       try
/*  54:    */       {
/*  55: 85 */         context.createSubcontext(this.nameSpace);
/*  56:    */       }
/*  57:    */       catch (Exception e1) {}
/*  58: 89 */       CompositeName cn = new CompositeName(cnStr);
/*  59:    */       
/*  60: 91 */       this.bunchMsg = new BunchSvrMsgImpl();
/*  61: 92 */       this.bunchMsg.setParent(null);
/*  62: 93 */       this.bunchMsg.setJndiName(this.jndiName);
/*  63: 94 */       this.bunchMsg.setTextMode();
/*  64:    */       
/*  65: 96 */       context.rebind(cn, this.bunchMsg);
/*  66:    */       
/*  67: 98 */       this.corbaContext = context;
/*  68:    */       
/*  69:100 */       System.out.println("SERVER Started OK!");
/*  70:    */       
/*  71:102 */       return true;
/*  72:    */     }
/*  73:    */     catch (Exception ex)
/*  74:    */     {
/*  75:106 */       String excp = ex.toString();
/*  76:107 */       System.out.println("Server exception: " + excp);
/*  77:    */     }
/*  78:108 */     return false;
/*  79:    */   }
/*  80:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchServer.BSTextServer
 * JD-Core Version:    0.7.0.1
 */