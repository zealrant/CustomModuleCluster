/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.io.BufferedWriter;
/*   4:    */ 
/*   5:    */ public abstract class GraphOutput
/*   6:    */ {
/*   7:    */   public static final int OUTPUT_TOP_ONLY = 1;
/*   8:    */   public static final int OUTPUT_MEDIAN_ONLY = 2;
/*   9:    */   public static final int OUTPUT_ALL_LEVELS = 3;
/*  10:    */   public static final int OUTPUT_DETAILED_LEVEL_ONLY = 4;
/*  11:    */   protected Graph graph_d;
/*  12:    */   protected BufferedWriter writer_d;
/*  13:    */   protected String fileName_d;
/*  14:    */   protected String currentName_d;
/*  15:    */   protected String basicName_d;
/*  16: 55 */   protected boolean writeNestedLevels = false;
/*  17: 56 */   protected boolean agglomWriteAllLevels = false;
/*  18: 57 */   protected int outputTechnique = 2;
/*  19: 60 */   protected int baseID = 0;
/*  20:    */   
/*  21:    */   public void setOutputTechnique(int t)
/*  22:    */   {
/*  23: 74 */     this.outputTechnique = t;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int getOutputTechnique()
/*  27:    */   {
/*  28: 80 */     return this.outputTechnique;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setNestedLevels(boolean b)
/*  32:    */   {
/*  33: 86 */     this.writeNestedLevels = b;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setAgglomWriteAllLevels(boolean b)
/*  37:    */   {
/*  38: 92 */     this.agglomWriteAllLevels = b;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean getWriteNestedLevels()
/*  42:    */   {
/*  43: 99 */     return this.writeNestedLevels;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean getAgglomWriteAllLevels()
/*  47:    */   {
/*  48:102 */     return this.agglomWriteAllLevels;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setGraph(Graph g)
/*  52:    */   {
/*  53:112 */     this.graph_d = g;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Graph getGraph()
/*  57:    */   {
/*  58:124 */     return this.graph_d;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setBaseName(String name)
/*  62:    */   {
/*  63:145 */     this.fileName_d = name;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getBaseName()
/*  67:    */   {
/*  68:158 */     return this.fileName_d;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setCurrentName(String n)
/*  72:    */   {
/*  73:176 */     this.currentName_d = n;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getCurrentName()
/*  77:    */   {
/*  78:189 */     return this.currentName_d;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setBasicName(String bn)
/*  82:    */   {
/*  83:206 */     this.basicName_d = bn;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getBasicName()
/*  87:    */   {
/*  88:219 */     return this.basicName_d;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public abstract void write();
/*  92:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.GraphOutput
 * JD-Core Version:    0.7.0.1
 */