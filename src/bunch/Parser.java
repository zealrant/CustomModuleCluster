/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.FileReader;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.InputStreamReader;
/*   7:    */ 
/*   8:    */ public abstract class Parser
/*   9:    */ {
/*  10:    */   protected BufferedReader reader_d;
/*  11: 38 */   protected String delims_d = " \t\r\n";
/*  12:    */   protected String inputFileName;
/*  13:    */   
/*  14:    */   public void setDelims(String d)
/*  15:    */   {
/*  16: 52 */     this.delims_d = d;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public String getDelims()
/*  20:    */   {
/*  21: 57 */     return this.delims_d;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public boolean hasReflexiveEdges()
/*  25:    */   {
/*  26: 63 */     return false;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int getReflexiveEdges()
/*  30:    */   {
/*  31: 69 */     return 0;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setInput(InputStream is)
/*  35:    */   {
/*  36: 81 */     this.reader_d = new BufferedReader(new InputStreamReader(is));
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setInput(String fileName)
/*  40:    */   {
/*  41:    */     try
/*  42:    */     {
/*  43: 94 */       this.reader_d = new BufferedReader(new FileReader(fileName));
/*  44: 95 */       this.inputFileName = fileName;
/*  45:    */     }
/*  46:    */     catch (Exception e)
/*  47:    */     {
/*  48: 98 */       e.printStackTrace();
/*  49: 99 */       System.exit(1);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String getInputFileName()
/*  54:    */   {
/*  55:105 */     return this.inputFileName;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setObject(Object obj) {}
/*  59:    */   
/*  60:    */   public Object getObject()
/*  61:    */   {
/*  62:128 */     return null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public BufferedReader getReader()
/*  66:    */   {
/*  67:140 */     return this.reader_d;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public abstract Object parse();
/*  71:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.Parser
 * JD-Core Version:    0.7.0.1
 */