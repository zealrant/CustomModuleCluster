/*   1:    */ package bunch.util;
/*   2:    */ 
/*   3:    */ import java.text.NumberFormat;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ import java.util.Vector;
/*   6:    */ 
/*   7:    */ class Compare
/*   8:    */ {
/*   9:    */   private Hashtable m_ht_vars_orig;
/*  10:    */   private Hashtable m_ht_vars_new;
/*  11:    */   private Vector m_v_original_distance;
/*  12:    */   private Vector m_v_new_distance_name;
/*  13:    */   private Vector m_v_new_distance_number;
/*  14:    */   private double m_d_recall;
/*  15:    */   private double m_d_precision;
/*  16:    */   
/*  17:    */   public String get_precision()
/*  18:    */   {
/*  19:175 */     NumberFormat nf = NumberFormat.getNumberInstance();
/*  20:176 */     String fx = nf.format(this.m_d_precision);
/*  21:177 */     return fx;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public String get_recall()
/*  25:    */   {
/*  26:182 */     NumberFormat nf = NumberFormat.getNumberInstance();
/*  27:183 */     String fx = nf.format(this.m_d_recall);
/*  28:184 */     return fx;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Compare(Vector orig, Vector newname, Vector newnumber)
/*  32:    */   {
/*  33:189 */     this.m_ht_vars_orig = new Hashtable();
/*  34:190 */     this.m_ht_vars_new = new Hashtable();
/*  35:191 */     this.m_v_original_distance = new Vector(orig);
/*  36:192 */     this.m_v_new_distance_name = new Vector(newname);
/*  37:193 */     this.m_v_new_distance_number = new Vector(newnumber);
/*  38:194 */     this.m_d_recall = 0.0D;
/*  39:195 */     this.m_d_precision = 0.0D;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void do_compare()
/*  43:    */   {
/*  44:208 */     boolean found1 = false;
/*  45:209 */     boolean found2 = false;
/*  46:210 */     int pairs_found = 0;
/*  47:211 */     int pairs_total = 0;
/*  48:212 */     int not_found = 0;
/*  49:    */     
/*  50:214 */     Vector v_temp = new Vector();
/*  51:215 */     Vector v_new = new Vector();
/*  52:216 */     for (int i = 0; i < this.m_v_original_distance.size(); i++)
/*  53:    */     {
/*  54:218 */       v_temp = (Vector)this.m_v_original_distance.get(i);
/*  55:    */       
/*  56:220 */       pairs_total += v_temp.size() * (v_temp.size() - 1) / 2;
/*  57:223 */       for (int j = 0; j < v_temp.size() - 1; j++)
/*  58:    */       {
/*  59:225 */         found1 = false;
/*  60:226 */         String s_var1 = new String(v_temp.get(j).toString());
/*  61:227 */         for (int l = 0; (l < this.m_v_new_distance_name.size()) && (!found1); l++)
/*  62:    */         {
/*  63:229 */           v_new = (Vector)this.m_v_new_distance_name.get(l);
/*  64:230 */           if (v_new.indexOf(s_var1) >= 0) {
/*  65:233 */             found1 = true;
/*  66:    */           }
/*  67:    */         }
/*  68:237 */         if (!found1)
/*  69:    */         {
/*  70:239 */           not_found++;
/*  71:240 */           pairs_total -= v_temp.size() - 1 - j;
/*  72:    */         }
/*  73:243 */         if (found1) {
/*  74:247 */           for (int k = j + 1; k < v_temp.size(); k++)
/*  75:    */           {
/*  76:249 */             String s_var2 = new String(v_temp.get(k).toString());
/*  77:251 */             if (v_new.indexOf(s_var2) >= 0) {
/*  78:253 */               pairs_found++;
/*  79:    */             }
/*  80:    */           }
/*  81:    */         }
/*  82:    */       }
/*  83:    */     }
/*  84:261 */     if (pairs_total != 0) {
/*  85:262 */       this.m_d_recall = (pairs_found * 100.0D / pairs_total);
/*  86:    */     }
/*  87:267 */     pairs_found = 0;
/*  88:268 */     pairs_total = 0;
/*  89:269 */     not_found = 0;
/*  90:271 */     for (int i = 0; i < this.m_v_new_distance_name.size(); i++)
/*  91:    */     {
/*  92:273 */       v_temp = (Vector)this.m_v_new_distance_name.get(i);
/*  93:    */       
/*  94:275 */       pairs_total += v_temp.size() * (v_temp.size() - 1) / 2;
/*  95:278 */       for (int j = 0; j < v_temp.size() - 1; j++)
/*  96:    */       {
/*  97:280 */         found1 = false;
/*  98:281 */         String s_var1 = new String(v_temp.get(j).toString());
/*  99:282 */         for (int l = 0; (l < this.m_v_original_distance.size()) && (!found1); l++)
/* 100:    */         {
/* 101:284 */           v_new = (Vector)this.m_v_original_distance.get(l);
/* 102:285 */           if (v_new.indexOf(s_var1) >= 0) {
/* 103:288 */             found1 = true;
/* 104:    */           }
/* 105:    */         }
/* 106:292 */         if (!found1)
/* 107:    */         {
/* 108:294 */           not_found++;
/* 109:295 */           pairs_total -= v_temp.size() - 1 - j;
/* 110:    */         }
/* 111:298 */         if (found1) {
/* 112:302 */           for (int k = j + 1; k < v_temp.size(); k++)
/* 113:    */           {
/* 114:304 */             String s_var2 = new String(v_temp.get(k).toString());
/* 115:306 */             if (v_new.indexOf(s_var2) >= 0) {
/* 116:308 */               pairs_found++;
/* 117:    */             }
/* 118:    */           }
/* 119:    */         }
/* 120:    */       }
/* 121:    */     }
/* 122:316 */     if (pairs_total != 0) {
/* 123:317 */       this.m_d_precision = (pairs_found * 100.0D / pairs_total);
/* 124:    */     }
/* 125:    */   }
/* 126:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.util.Compare
 * JD-Core Version:    0.7.0.1
 */