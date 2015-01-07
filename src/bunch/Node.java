/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public class Node
/*   6:    */   implements Serializable
/*   7:    */ {
/*   8:    */   public int[] dependencies;
/*   9:    */   public static final int NORMAL = 0;
/*  10:    */   public static final int CLIENT = 1;
/*  11:    */   public static final int SUPPLIER = 2;
/*  12:    */   public static final int CENTRAL = 3;
/*  13:    */   public static final int LIBRARY = 4;
/*  14:    */   public static final int CLUSTER = 5;
/*  15:    */   public static final int DEAD = 128;
/*  16: 91 */   public int type_d = 0;
/*  17: 93 */   private static long nodeCounter = 0L;
/*  18: 95 */   private Long uniqueID = null;
/*  19:    */   public int[] weights;
/*  20:    */   public int[] backEdges;
/*  21:    */   public int[] beWeights;
/*  22:    */   public int nodeID;
/*  23:104 */   public Node[] children = null;
/*  24:105 */   public boolean isCluster = false;
/*  25:106 */   public int nodeLevel = -1;
/*  26:    */   String name_d;
/*  27:    */   int cluster;
/*  28:    */   
/*  29:    */   private void assignUniqueID()
/*  30:    */   {
/*  31:119 */     synchronized (this)
/*  32:    */     {
/*  33:121 */       this.uniqueID = new Long(++nodeCounter);
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void resetNode()
/*  38:    */   {
/*  39:127 */     this.type_d = 0;
/*  40:128 */     this.children = null;
/*  41:129 */     this.isCluster = false;
/*  42:130 */     this.nodeLevel = -1;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Long getUniqueID()
/*  46:    */   {
/*  47:134 */     return this.uniqueID;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean isCluster()
/*  51:    */   {
/*  52:137 */     return this.isCluster;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getCluster()
/*  56:    */   {
/*  57:140 */     return this.cluster;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setCluster(int c)
/*  61:    */   {
/*  62:143 */     this.cluster = c;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIsCluster(boolean ic)
/*  66:    */   {
/*  67:146 */     this.isCluster = ic;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getId()
/*  71:    */   {
/*  72:149 */     return this.nodeID;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Node()
/*  76:    */   {
/*  77:157 */     assignUniqueID();
/*  78:158 */     setType(0);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Node(String name, int[] deps, int[] weights)
/*  82:    */   {
/*  83:172 */     assignUniqueID();
/*  84:173 */     setType(0);
/*  85:174 */     setName(name);
/*  86:175 */     setDependencies(deps);
/*  87:176 */     setWeights(weights);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Node(String name, int[] deps)
/*  91:    */   {
/*  92:190 */     assignUniqueID();
/*  93:191 */     setType(0);
/*  94:192 */     setDependencies(deps);
/*  95:193 */     int[] ws = new int[deps.length];
/*  96:194 */     for (int i = 0; i < deps.length; i++) {
/*  97:195 */       ws[i] = 1;
/*  98:    */     }
/*  99:197 */     setWeights(ws);
/* 100:198 */     setName(name);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String toString()
/* 104:    */   {
/* 105:211 */     String str = new String();
/* 106:212 */     str = str + "\n" + this.name_d + " = ";
/* 107:213 */     for (int i = 0; i < this.dependencies.length; i++) {
/* 108:214 */       str = str + this.dependencies[i] + " / ";
/* 109:    */     }
/* 110:216 */     return str;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getName()
/* 114:    */   {
/* 115:229 */     return this.name_d;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setName(String name)
/* 119:    */   {
/* 120:242 */     this.name_d = name;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setDependencies(int[] deps)
/* 124:    */   {
/* 125:256 */     this.dependencies = deps;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public int[] getDependencies()
/* 129:    */   {
/* 130:270 */     return this.dependencies;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setWeights(int[] ws)
/* 134:    */   {
/* 135:285 */     this.weights = ws;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int[] getWeights()
/* 139:    */   {
/* 140:300 */     return this.weights;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setBackEdges(int[] deps)
/* 144:    */   {
/* 145:307 */     this.backEdges = deps;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public int[] getBackEdges()
/* 149:    */   {
/* 150:321 */     return this.backEdges;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setBeWeights(int[] ws)
/* 154:    */   {
/* 155:336 */     this.beWeights = ws;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public int[] getBeWeights()
/* 159:    */   {
/* 160:351 */     return this.beWeights;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Node cloneNode()
/* 164:    */   {
/* 165:364 */     Node n = new Node();
/* 166:365 */     n.setName(getName());
/* 167:366 */     n.cluster = this.cluster;
/* 168:367 */     if (this.dependencies != null)
/* 169:    */     {
/* 170:368 */       n.dependencies = new int[this.dependencies.length];
/* 171:369 */       System.arraycopy(this.dependencies, 0, n.dependencies, 0, this.dependencies.length);
/* 172:    */     }
/* 173:371 */     if (this.weights != null)
/* 174:    */     {
/* 175:372 */       n.weights = new int[this.weights.length];
/* 176:373 */       System.arraycopy(this.weights, 0, n.weights, 0, this.weights.length);
/* 177:    */     }
/* 178:375 */     if (this.backEdges != null)
/* 179:    */     {
/* 180:376 */       n.backEdges = new int[this.backEdges.length];
/* 181:377 */       System.arraycopy(this.backEdges, 0, n.backEdges, 0, this.backEdges.length);
/* 182:    */     }
/* 183:379 */     if (this.beWeights != null)
/* 184:    */     {
/* 185:380 */       n.beWeights = new int[this.beWeights.length];
/* 186:381 */       System.arraycopy(this.beWeights, 0, n.beWeights, 0, this.beWeights.length);
/* 187:    */     }
/* 188:384 */     n.setType(this.type_d);
/* 189:385 */     n.nodeID = this.nodeID;
/* 190:386 */     return n;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setType(int type)
/* 194:    */   {
/* 195:399 */     this.type_d = type;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public int getType()
/* 199:    */   {
/* 200:412 */     return this.type_d;
/* 201:    */   }
/* 202:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.Node
 * JD-Core Version:    0.7.0.1
 */