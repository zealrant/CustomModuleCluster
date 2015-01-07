/*   1:    */ package bunch.util;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import java.util.Vector;
/*   5:    */ 
/*   6:    */ public class PrecisionRecallCalculator
/*   7:    */ {
/*   8:    */   String m_S_filename1;
/*   9:    */   String m_S_filename2;
/*  10:    */   String S_precision;
/*  11:    */   String S_recall;
/*  12:    */   Vector m_v_expert_modules_names;
/*  13:    */   Vector m_v_expert_modules_content;
/*  14:    */   Vector m_v_tested_modules_names;
/*  15:    */   Vector m_v_tested_modules_content;
/*  16:    */   
/*  17:    */   public PrecisionRecallCalculator(String expertFileName, String testFileName)
/*  18:    */   {
/*  19: 38 */     this.m_S_filename1 = new String(expertFileName);
/*  20: 39 */     this.m_S_filename2 = new String(testFileName);
/*  21: 40 */     this.m_v_expert_modules_names = new Vector();
/*  22: 41 */     this.m_v_expert_modules_content = new Vector();
/*  23: 42 */     this.m_v_tested_modules_names = new Vector();
/*  24: 43 */     this.m_v_tested_modules_content = new Vector();
/*  25: 44 */     ReadBunch();
/*  26: 45 */     compare();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String get_precision()
/*  30:    */   {
/*  31: 66 */     return this.S_precision;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String get_recall()
/*  35:    */   {
/*  36: 71 */     return this.S_recall;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void compare()
/*  40:    */   {
/*  41: 76 */     Compare cp = new Compare(this.m_v_expert_modules_content, this.m_v_tested_modules_content, this.m_v_tested_modules_names);
/*  42: 77 */     cp.do_compare();
/*  43: 78 */     this.S_precision = (cp.get_precision() + "%");
/*  44: 79 */     this.S_recall = (cp.get_recall() + "%");
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void ReadBunch()
/*  48:    */   {
/*  49: 86 */     Hashtable ht1 = new Hashtable();
/*  50: 87 */     GBunchRW bunch1 = new GBunchRW(this.m_S_filename1);
/*  51:    */     
/*  52: 89 */     ht1 = bunch1.read();
/*  53:    */     
/*  54: 91 */     this.m_v_expert_modules_names = bunch1.getModuleNames();
/*  55: 92 */     this.m_v_expert_modules_content = bunch1.getModulesContent();
/*  56:    */     
/*  57:    */ 
/*  58: 95 */     boolean found = false;
/*  59: 96 */     for (int i = 0; i < this.m_v_expert_modules_names.size(); i++)
/*  60:    */     {
/*  61: 98 */       String S_module_name = new String(this.m_v_expert_modules_names.get(i).toString());
/*  62: 99 */       Vector v_module_content = new Vector((Vector)this.m_v_expert_modules_content.get(i));
/*  63:100 */       found = false;
/*  64:101 */       for (int j = 0; (j < v_module_content.size()) && (!found); j++) {
/*  65:103 */         if (this.m_v_expert_modules_names.contains(v_module_content.get(j)))
/*  66:    */         {
/*  67:105 */           this.m_v_expert_modules_names.remove(i);
/*  68:106 */           this.m_v_expert_modules_content.remove(i);
/*  69:107 */           i--;
/*  70:108 */           found = true;
/*  71:    */         }
/*  72:    */       }
/*  73:    */     }
/*  74:117 */     Hashtable ht2 = new Hashtable();
/*  75:118 */     GBunchRW bunch2 = new GBunchRW(this.m_S_filename2);
/*  76:    */     
/*  77:120 */     ht2 = bunch2.read();
/*  78:    */     
/*  79:122 */     this.m_v_tested_modules_names = bunch2.getModuleNames();
/*  80:123 */     this.m_v_tested_modules_content = bunch2.getModulesContent();
/*  81:126 */     for (int i = 0; i < this.m_v_tested_modules_names.size(); i++)
/*  82:    */     {
/*  83:128 */       String S_module_name = new String(this.m_v_tested_modules_names.get(i).toString());
/*  84:129 */       Vector v_module_content = new Vector((Vector)this.m_v_tested_modules_content.get(i));
/*  85:130 */       found = false;
/*  86:131 */       for (int j = 0; (j < v_module_content.size()) && (!found); j++) {
/*  87:133 */         if (this.m_v_tested_modules_names.contains(v_module_content.get(j)))
/*  88:    */         {
/*  89:135 */           this.m_v_tested_modules_names.remove(i);
/*  90:136 */           this.m_v_tested_modules_content.remove(i);
/*  91:137 */           i--;
/*  92:138 */           found = true;
/*  93:    */         }
/*  94:    */       }
/*  95:    */     }
/*  96:    */   }
/*  97:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.util.PrecisionRecallCalculator
 * JD-Core Version:    0.7.0.1
 */