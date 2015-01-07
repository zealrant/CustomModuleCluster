/*   1:    */ package bunch.util;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.FileReader;
/*   5:    */ import java.io.FileWriter;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.util.Enumeration;
/*   9:    */ import java.util.Hashtable;
/*  10:    */ import java.util.StringTokenizer;
/*  11:    */ import java.util.Vector;
/*  12:    */ 
/*  13:    */ class GBunchRW
/*  14:    */ {
/*  15:    */   private Hashtable m_ht_bunchread;
/*  16:    */   private String m_S_filename;
/*  17:    */   
/*  18:    */   public GBunchRW(String filename)
/*  19:    */   {
/*  20:328 */     this.m_ht_bunchread = new Hashtable();
/*  21:329 */     this.m_S_filename = new String(filename);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Hashtable read()
/*  25:    */   {
/*  26:334 */     int i_start_location_of_SS = 0;
/*  27:335 */     int i_end_location_of_SS = 0;
/*  28:336 */     String S_module_name = new String();
/*  29:337 */     Vector v_module_value = new Vector();
/*  30:    */     try
/*  31:    */     {
/*  32:340 */       BufferedReader br = new BufferedReader(new FileReader(this.m_S_filename));
/*  33:    */       for (;;)
/*  34:    */       {
/*  35:342 */         v_module_value = new Vector();
/*  36:343 */         S_module_name = new String();
/*  37:    */         
/*  38:345 */         String line = br.readLine();
/*  39:347 */         if (line == null) {
/*  40:    */           break;
/*  41:    */         }
/*  42:349 */         line = line.trim();
/*  43:351 */         if ((line.length() != 0) && (
/*  44:    */         
/*  45:    */ 
/*  46:354 */           (line.length() <= 1) || (line.charAt(0) != '/') || (line.trim().charAt(1) != '/')))
/*  47:    */         {
/*  48:357 */           i_start_location_of_SS = line.indexOf("SS(") + 3;
/*  49:358 */           i_end_location_of_SS = line.indexOf(")");
/*  50:359 */           S_module_name = line.substring(i_start_location_of_SS, i_end_location_of_SS);
/*  51:360 */           line = line.substring(line.indexOf("=") + 1);
/*  52:    */           
/*  53:    */ 
/*  54:    */ 
/*  55:364 */           StringTokenizer st = new StringTokenizer(line, ",");
/*  56:365 */           while (st.hasMoreTokens()) {
/*  57:366 */             v_module_value.add(st.nextToken().trim());
/*  58:    */           }
/*  59:368 */           this.m_ht_bunchread.put(S_module_name, v_module_value);
/*  60:    */         }
/*  61:    */       }
/*  62:    */     }
/*  63:    */     catch (IOException e)
/*  64:    */     {
/*  65:372 */       System.out.println("Opps: " + e);
/*  66:    */     }
/*  67:375 */     return (Hashtable)this.m_ht_bunchread.clone();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void write(Hashtable ht)
/*  71:    */   {
/*  72:    */     try
/*  73:    */     {
/*  74:381 */       FileWriter fos = new FileWriter(this.m_S_filename);
/*  75:    */       
/*  76:383 */       fos.write("//Created automatically using GBunchRW...\n");
/*  77:384 */       Enumeration keys = ht.keys();
/*  78:385 */       while (keys.hasMoreElements())
/*  79:    */       {
/*  80:387 */         String S_temp = new String(keys.nextElement().toString());
/*  81:388 */         Vector v_temp = new Vector((Vector)ht.get(S_temp));
/*  82:    */         
/*  83:390 */         fos.write("SS(" + S_temp + ")= ");
/*  84:391 */         for (int i = 0; i < v_temp.size() - 1; i++) {
/*  85:393 */           fos.write(v_temp.get(i) + ", ");
/*  86:    */         }
/*  87:395 */         fos.write(v_temp.get(v_temp.size() - 1).toString() + "\n");
/*  88:    */       }
/*  89:398 */       fos.close();
/*  90:    */     }
/*  91:    */     catch (IOException e)
/*  92:    */     {
/*  93:400 */       System.out.println("Opps: " + e);
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Vector getModuleNames()
/*  98:    */   {
/*  99:407 */     Vector v_temp = new Vector();
/* 100:408 */     Enumeration keys = this.m_ht_bunchread.keys();
/* 101:409 */     while (keys.hasMoreElements())
/* 102:    */     {
/* 103:411 */       String S_temp = new String(keys.nextElement().toString());
/* 104:412 */       v_temp.add(S_temp);
/* 105:    */     }
/* 106:415 */     return (Vector)v_temp.clone();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Vector getModulesContent()
/* 110:    */   {
/* 111:420 */     Vector v_modules = new Vector();
/* 112:    */     
/* 113:    */ 
/* 114:423 */     Enumeration keys = this.m_ht_bunchread.keys();
/* 115:424 */     while (keys.hasMoreElements())
/* 116:    */     {
/* 117:426 */       String S_temp = new String(keys.nextElement().toString());
/* 118:427 */       Vector v_temp = new Vector((Vector)this.m_ht_bunchread.get(S_temp));
/* 119:428 */       v_modules.add(v_temp);
/* 120:    */     }
/* 121:430 */     return (Vector)v_modules.clone();
/* 122:    */   }
/* 123:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.util.GBunchRW
 * JD-Core Version:    0.7.0.1
 */