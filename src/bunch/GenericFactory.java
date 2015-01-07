/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.beans.Beans;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.Enumeration;
/*   6:    */ import java.util.Hashtable;
/*   7:    */ 
/*   8:    */ public class GenericFactory
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   protected Hashtable methodTable_d;
/*  12:    */   public static final long serialVersionUID = 100L;
/*  13:    */   protected String factoryType_d;
/*  14:    */   
/*  15:    */   public GenericFactory()
/*  16:    */   {
/*  17: 60 */     this.methodTable_d = new Hashtable(10);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void setFactoryType(String name)
/*  21:    */   {
/*  22: 74 */     this.factoryType_d = name;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String getFactoryType()
/*  26:    */   {
/*  27: 88 */     return this.factoryType_d;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void addItem(String name, String className)
/*  31:    */   {
/*  32:102 */     this.methodTable_d.put(name, className);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Enumeration getAvailableItems()
/*  36:    */   {
/*  37:115 */     return this.methodTable_d.keys();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String[] getItemList()
/*  41:    */   {
/*  42:122 */     String[] list = new String[this.methodTable_d.size()];
/*  43:123 */     Enumeration e = this.methodTable_d.keys();
/*  44:124 */     int i = 0;
/*  45:125 */     while (e.hasMoreElements()) {
/*  46:126 */       list[(i++)] = ((String)e.nextElement());
/*  47:    */     }
/*  48:128 */     return list;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getItemName(String name)
/*  52:    */   {
/*  53:143 */     return (String)this.methodTable_d.get(name);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Object getItemInstance(String name)
/*  57:    */   {
/*  58:157 */     String cls = null;
/*  59:158 */     if (name.toLowerCase().equals("default")) {
/*  60:159 */       cls = "bunch.Default" + this.factoryType_d;
/*  61:    */     } else {
/*  62:162 */       cls = (String)this.methodTable_d.get(name);
/*  63:    */     }
/*  64:165 */     Object obj = null;
/*  65:    */     try
/*  66:    */     {
/*  67:168 */       obj = Beans.instantiate(null, cls);
/*  68:    */     }
/*  69:    */     catch (Exception e)
/*  70:    */     {
/*  71:171 */       return getItemInstanceFromClass(name);
/*  72:    */     }
/*  73:174 */     return obj;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Object getItemInstanceFromClass(String cls)
/*  77:    */   {
/*  78:184 */     Object obj = null;
/*  79:    */     try
/*  80:    */     {
/*  81:187 */       obj = Beans.instantiate(null, cls);
/*  82:    */     }
/*  83:    */     catch (Exception e)
/*  84:    */     {
/*  85:190 */       throw new RuntimeException(e.toString());
/*  86:    */     }
/*  87:192 */     return obj;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getDefaultMethod()
/*  91:    */   {
/*  92:202 */     return null;
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.GenericFactory
 * JD-Core Version:    0.7.0.1
 */