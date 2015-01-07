/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.Hashtable;
/*   8:    */ import java.util.Set;
/*   9:    */ import java.util.StringTokenizer;
/*  10:    */ 
/*  11:    */ public class DependencyFileParser
/*  12:    */   extends Parser
/*  13:    */ {
/*  14:    */   private int reflexiveEdges;
/*  15:    */   
/*  16:    */   class ParserNode
/*  17:    */   {
/*  18:    */     public String name;
/*  19:    */     public Hashtable dependencies;
/*  20:    */     public Hashtable backEdges;
/*  21:    */     public Hashtable dWeights;
/*  22:    */     public Hashtable beWeights;
/*  23:    */     public int[] arrayDependencies;
/*  24:    */     public int[] arrayWeights;
/*  25:    */     
/*  26:    */     public ParserNode(String n)
/*  27:    */     {
/*  28: 60 */       this.name = n;
/*  29: 61 */       this.dependencies = new Hashtable();
/*  30: 62 */       this.dWeights = new Hashtable();
/*  31: 63 */       this.backEdges = new Hashtable();
/*  32: 64 */       this.beWeights = new Hashtable();
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public DependencyFileParser()
/*  37:    */   {
/*  38: 74 */     this.reflexiveEdges = 0;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getReflexiveEdges()
/*  42:    */   {
/*  43: 83 */     return this.reflexiveEdges;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean hasReflexiveEdges()
/*  47:    */   {
/*  48: 91 */     return this.reflexiveEdges > 0;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Object parse()
/*  52:    */   {
/*  53:100 */     this.reflexiveEdges = 0;
/*  54:101 */     Hashtable nodes = new Hashtable();
/*  55:102 */     Graph retGraph = null;
/*  56:    */     try
/*  57:    */     {
/*  58:    */       for (;;)
/*  59:    */       {
/*  60:109 */         String line = this.reader_d.readLine();
/*  61:110 */         if (line == null) {
/*  62:    */           break;
/*  63:    */         }
/*  64:113 */         if (line != "")
/*  65:    */         {
/*  66:118 */           StringTokenizer st = new StringTokenizer(line, this.delims_d);
/*  67:119 */           if (st.hasMoreTokens())
/*  68:    */           {
/*  69:123 */             ParserNode currentNode = null;
/*  70:124 */             ParserNode targetNode = null;
/*  71:    */             
/*  72:    */ 
/*  73:127 */             String nod = st.nextToken();
/*  74:    */             
/*  75:    */ 
/*  76:130 */             String target = null;
/*  77:131 */             if (st.hasMoreElements()) {
/*  78:132 */               target = st.nextToken();
/*  79:    */             }
/*  80:134 */             if (nod.equals(target))
/*  81:    */             {
/*  82:136 */               this.reflexiveEdges += 1;
/*  83:    */             }
/*  84:    */             else
/*  85:    */             {
/*  86:140 */               currentNode = (ParserNode)nodes.get(nod);
/*  87:143 */               if (currentNode == null)
/*  88:    */               {
/*  89:145 */                 currentNode = new ParserNode(nod);
/*  90:146 */                 nodes.put(nod, currentNode);
/*  91:    */               }
/*  92:151 */               Integer w = new Integer(1);
/*  93:154 */               if (target != null)
/*  94:    */               {
/*  95:156 */                 String dep = target;
/*  96:160 */                 if (st.hasMoreElements()) {
/*  97:161 */                   w = new Integer(st.nextToken());
/*  98:    */                 }
/*  99:164 */                 targetNode = (ParserNode)nodes.get(dep);
/* 100:165 */                 if (targetNode == null)
/* 101:    */                 {
/* 102:167 */                   targetNode = new ParserNode(dep);
/* 103:168 */                   nodes.put(dep, targetNode);
/* 104:    */                 }
/* 105:173 */                 if (!currentNode.dependencies.containsKey(dep))
/* 106:    */                 {
/* 107:175 */                   currentNode.dependencies.put(dep, dep);
/* 108:176 */                   currentNode.dWeights.put(dep, w);
/* 109:    */                 }
/* 110:    */                 else
/* 111:    */                 {
/* 112:181 */                   Integer wExisting = (Integer)currentNode.dWeights.get(dep);
/* 113:182 */                   Integer wtemp = new Integer(w.intValue() + wExisting.intValue());
/* 114:183 */                   currentNode.dWeights.put(dep, wtemp);
/* 115:    */                 }
/* 116:186 */                 if (!targetNode.backEdges.containsKey(nod))
/* 117:    */                 {
/* 118:188 */                   targetNode.backEdges.put(nod, nod);
/* 119:189 */                   targetNode.beWeights.put(nod, w);
/* 120:    */                 }
/* 121:    */                 else
/* 122:    */                 {
/* 123:193 */                   Integer wExisting = (Integer)targetNode.beWeights.get(nod);
/* 124:194 */                   Integer wtemp = new Integer(w.intValue() + wExisting.intValue());
/* 125:195 */                   targetNode.beWeights.put(nod, wtemp);
/* 126:    */                 }
/* 127:    */               }
/* 128:    */             }
/* 129:    */           }
/* 130:    */         }
/* 131:    */       }
/* 132:201 */       int sz = nodes.size();
/* 133:202 */       Hashtable nameTable = new Hashtable();
/* 134:    */       
/* 135:    */ 
/* 136:205 */       Object[] oa = nodes.keySet().toArray();
/* 137:206 */       for (int i = 0; i < oa.length; i++)
/* 138:    */       {
/* 139:208 */         String n = (String)oa[i];
/* 140:209 */         nameTable.put(n, new Integer(i));
/* 141:    */       }
/* 142:213 */       retGraph = new Graph(nodes.size());
/* 143:214 */       retGraph.clear();
/* 144:215 */       Node[] nodeList = retGraph.getNodes();
/* 145:    */       
/* 146:    */ 
/* 147:218 */       Object[] nl = nodes.values().toArray();
/* 148:219 */       for (int i = 0; i < nl.length; i++)
/* 149:    */       {
/* 150:221 */         Node n = new Node();
/* 151:222 */         nodeList[i] = n;
/* 152:223 */         ParserNode p = (ParserNode)nl[i];
/* 153:224 */         n.setName(p.name);
/* 154:225 */         Integer nid = (Integer)nameTable.get(p.name);
/* 155:226 */         n.nodeID = nid.intValue();
/* 156:227 */         n.dependencies = ht2ArrayFromKey(nameTable, p.dependencies);
/* 157:228 */         n.weights = ht2ArrayValFromKey(nameTable, p.dWeights);
/* 158:229 */         n.backEdges = ht2ArrayFromKey(nameTable, p.backEdges);
/* 159:230 */         n.beWeights = ht2ArrayValFromKey(nameTable, p.beWeights);
/* 160:    */       }
/* 161:    */     }
/* 162:    */     catch (IOException e)
/* 163:    */     {
/* 164:234 */       e.printStackTrace();
/* 165:    */     }
/* 166:238 */     return retGraph;
/* 167:    */   }
/* 168:    */   
/* 169:    */   private int[] ht2ArrayFromKey(Hashtable key, Hashtable values)
/* 170:    */   {
/* 171:247 */     int[] retArray = new int[values.size()];
/* 172:    */     try
/* 173:    */     {
/* 174:250 */       Object[] oa = values.keySet().toArray();
/* 175:251 */       for (int i = 0; i < oa.length; i++)
/* 176:    */       {
/* 177:253 */         String s = (String)oa[i];
/* 178:254 */         Integer val = (Integer)key.get(s);
/* 179:255 */         retArray[i] = val.intValue();
/* 180:    */       }
/* 181:257 */       return retArray;
/* 182:    */     }
/* 183:    */     catch (Exception e)
/* 184:    */     {
/* 185:261 */       e.printStackTrace();
/* 186:    */     }
/* 187:262 */     return null;
/* 188:    */   }
/* 189:    */   
/* 190:    */   private int[] ht2ArrayValFromKey(Hashtable key, Hashtable values)
/* 191:    */   {
/* 192:272 */     int[] retArray = new int[values.size()];
/* 193:    */     try
/* 194:    */     {
/* 195:275 */       Object[] oa = values.keySet().toArray();
/* 196:276 */       for (int i = 0; i < oa.length; i++)
/* 197:    */       {
/* 198:278 */         String s = (String)oa[i];
/* 199:279 */         Integer value = (Integer)values.get(s);
/* 200:280 */         retArray[i] = value.intValue();
/* 201:    */       }
/* 202:282 */       return retArray;
/* 203:    */     }
/* 204:    */     catch (Exception e)
/* 205:    */     {
/* 206:286 */       e.printStackTrace();
/* 207:    */     }
/* 208:287 */     return null;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void dumpGraph(Hashtable h)
/* 212:    */   {
/* 213:296 */     Object[] oa = h.keySet().toArray();
/* 214:297 */     for (int i = 0; i < oa.length; i++)
/* 215:    */     {
/* 216:299 */       ParserNode n = (ParserNode)oa[i];
/* 217:300 */       System.out.print(n.name + ": ");
/* 218:301 */       Hashtable dep = n.dependencies;
/* 219:302 */       Object[] oa1 = dep.keySet().toArray();
/* 220:303 */       for (int j = 0; j < oa1.length; j++)
/* 221:    */       {
/* 222:305 */         ParserNode n1 = (ParserNode)oa1[j];
/* 223:306 */         System.out.print(n1.name + " ");
/* 224:    */       }
/* 225:308 */       oa1 = n.backEdges.keySet().toArray();
/* 226:309 */       for (int j = 0; j < oa1.length; j++)
/* 227:    */       {
/* 228:311 */         ParserNode n1 = (ParserNode)oa1[j];
/* 229:312 */         System.out.print("be(" + n1.name + ") ");
/* 230:    */       }
/* 231:314 */       System.out.println();
/* 232:    */     }
/* 233:    */   }
/* 234:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.DependencyFileParser
 * JD-Core Version:    0.7.0.1
 */