/*   1:    */ package bunch.util;
/*   2:    */ 
/*   3:    */ import bunch.Graph;
/*   4:    */ import bunch.Node;
/*   5:    */ import bunch.api.BunchMDG;
/*   6:    */ import bunch.api.BunchMDGDependency;
/*   7:    */ import java.io.ByteArrayInputStream;
/*   8:    */ import java.io.ByteArrayOutputStream;
/*   9:    */ import java.io.ObjectInputStream;
/*  10:    */ import java.io.ObjectOutputStream;
/*  11:    */ import java.io.PrintStream;
/*  12:    */ import java.io.Serializable;
/*  13:    */ import java.net.InetAddress;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Collection;
/*  16:    */ import java.util.Hashtable;
/*  17:    */ import java.util.Set;
/*  18:    */ import java.util.StringTokenizer;
/*  19:    */ 
/*  20:    */ public class BunchUtilities
/*  21:    */ {
/*  22:    */   public static final double defaultPrecision = 0.0001D;
/*  23:    */   
/*  24:    */   public static Object fromByteArray(byte[] byteArray)
/*  25:    */   {
/*  26: 39 */     if ((byteArray == null) || (byteArray.length == 0)) {
/*  27: 40 */       return null;
/*  28:    */     }
/*  29:    */     try
/*  30:    */     {
/*  31: 44 */       ByteArrayInputStream bai = new ByteArrayInputStream(byteArray);
/*  32: 45 */       ObjectInputStream oi = new ObjectInputStream(bai);
/*  33:    */       
/*  34: 47 */       return oi.readObject();
/*  35:    */     }
/*  36:    */     catch (Exception e)
/*  37:    */     {
/*  38: 50 */       e.printStackTrace();
/*  39:    */     }
/*  40: 51 */     return null;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static byte[] toByteArray(Serializable obj)
/*  44:    */   {
/*  45: 62 */     if (obj == null) {
/*  46: 63 */       return null;
/*  47:    */     }
/*  48:    */     try
/*  49:    */     {
/*  50: 67 */       ByteArrayOutputStream bao = new ByteArrayOutputStream();
/*  51: 68 */       ObjectOutputStream oo = new ObjectOutputStream(bao);
/*  52:    */       
/*  53: 70 */       oo.writeObject(obj);
/*  54: 71 */       oo.flush();
/*  55:    */       
/*  56: 73 */       return bao.toByteArray();
/*  57:    */     }
/*  58:    */     catch (Exception e)
/*  59:    */     {
/*  60: 76 */       e.printStackTrace();
/*  61:    */     }
/*  62: 77 */     return null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static String DelimitString(String input, int rowWidth)
/*  66:    */   {
/*  67: 83 */     System.out.println(input);
/*  68: 84 */     StringBuffer sb = new StringBuffer(input);
/*  69: 85 */     int totalLen = input.length();
/*  70: 86 */     String out = "";
/*  71: 87 */     int pos = 0;
/*  72: 89 */     while (pos < totalLen)
/*  73:    */     {
/*  74: 91 */       int getSize = Math.min(rowWidth, totalLen - pos);
/*  75: 92 */       int newOffset = pos + getSize;
/*  76: 93 */       while (newOffset < totalLen)
/*  77:    */       {
/*  78: 95 */         char c = sb.charAt(newOffset);
/*  79: 96 */         if ((c == ' ') || (c == '\t') || (c == '\n') || (c == '\r')) {
/*  80:    */           break;
/*  81:    */         }
/*  82: 99 */         newOffset++;
/*  83:    */       }
/*  84:102 */       out = out + sb.substring(pos, newOffset) + "\n";
/*  85:103 */       pos += newOffset + 1;
/*  86:    */     }
/*  87:106 */     return out;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static boolean compareEqual(double a, double b)
/*  91:    */   {
/*  92:112 */     int ia = (int)(a / 0.0001D);
/*  93:113 */     int ib = (int)(b / 0.0001D);
/*  94:    */     
/*  95:115 */     return ia == ib;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static boolean compareGreater(double a, double b)
/*  99:    */   {
/* 100:120 */     int ia = (int)(a / 0.0001D);
/* 101:121 */     int ib = (int)(b / 0.0001D);
/* 102:    */     
/* 103:123 */     return ia > ib;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static boolean compareGreaterEqual(double a, double b)
/* 107:    */   {
/* 108:128 */     int ia = (int)(a / 0.0001D);
/* 109:129 */     int ib = (int)(b / 0.0001D);
/* 110:    */     
/* 111:131 */     return ia >= ib;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static String getLocalHostName()
/* 115:    */   {
/* 116:    */     try
/* 117:    */     {
/* 118:138 */       String sname = InetAddress.getLocalHost().getHostName();
/* 119:    */       
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:143 */       StringTokenizer st = new StringTokenizer(sname, ".");
/* 124:    */       
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:148 */       return st.nextToken();
/* 129:    */     }
/* 130:    */     catch (Exception ex) {}
/* 131:155 */     return "bSvrLocal";
/* 132:    */   }
/* 133:    */   
/* 134:    */   public static Graph toInternalGraph(BunchMDG bunchMDG)
/* 135:    */   {
/* 136:161 */     ArrayList al = new ArrayList(bunchMDG.getMDGEdges());
/* 137:162 */     Hashtable nodes = new Hashtable();
/* 138:164 */     for (int i = 0; i < al.size(); i++)
/* 139:    */     {
/* 140:166 */       BunchMDGDependency bmd = (BunchMDGDependency)al.get(i);
/* 141:    */       
/* 142:168 */       ParserNode currentNode = null;
/* 143:169 */       ParserNode targetNode = null;
/* 144:174 */       if (!bmd.getSrcNode().equals(bmd.getDestNode()))
/* 145:    */       {
/* 146:177 */         currentNode = (ParserNode)nodes.get(bmd.getSrcNode());
/* 147:179 */         if (currentNode == null)
/* 148:    */         {
/* 149:181 */           currentNode = new ParserNode(bmd.getSrcNode());
/* 150:182 */           nodes.put(bmd.getSrcNode(), currentNode);
/* 151:    */         }
/* 152:185 */         targetNode = (ParserNode)nodes.get(bmd.getDestNode());
/* 153:187 */         if (targetNode == null)
/* 154:    */         {
/* 155:189 */           targetNode = new ParserNode(bmd.getDestNode());
/* 156:190 */           nodes.put(bmd.getDestNode(), targetNode);
/* 157:    */         }
/* 158:193 */         String src = bmd.getSrcNode();
/* 159:194 */         String dep = bmd.getDestNode();
/* 160:195 */         Integer w = new Integer(bmd.getEdgeW());
/* 161:199 */         if (!currentNode.dependencies.containsKey(dep))
/* 162:    */         {
/* 163:201 */           currentNode.dependencies.put(dep, dep);
/* 164:202 */           currentNode.dWeights.put(dep, w);
/* 165:    */         }
/* 166:    */         else
/* 167:    */         {
/* 168:207 */           Integer wExisting = (Integer)currentNode.dWeights.get(dep);
/* 169:208 */           Integer wtemp = new Integer(w.intValue() + wExisting.intValue());
/* 170:209 */           currentNode.dWeights.put(dep, wtemp);
/* 171:    */         }
/* 172:212 */         if (!targetNode.backEdges.containsKey(src))
/* 173:    */         {
/* 174:214 */           targetNode.backEdges.put(src, src);
/* 175:215 */           targetNode.beWeights.put(src, w);
/* 176:    */         }
/* 177:    */         else
/* 178:    */         {
/* 179:219 */           Integer wExisting = (Integer)targetNode.beWeights.get(src);
/* 180:220 */           Integer wtemp = new Integer(w.intValue() + wExisting.intValue());
/* 181:221 */           targetNode.beWeights.put(src, wtemp);
/* 182:    */         }
/* 183:    */       }
/* 184:    */     }
/* 185:230 */     int sz = nodes.size();
/* 186:231 */     Hashtable nameTable = new Hashtable();
/* 187:    */     
/* 188:    */ 
/* 189:234 */     Object[] oa = nodes.keySet().toArray();
/* 190:235 */     for (int i = 0; i < oa.length; i++)
/* 191:    */     {
/* 192:237 */       String n = (String)oa[i];
/* 193:238 */       nameTable.put(n, new Integer(i));
/* 194:    */     }
/* 195:242 */     Graph retGraph = new Graph(nodes.size());
/* 196:243 */     retGraph.clear();
/* 197:244 */     Node[] nodeList = retGraph.getNodes();
/* 198:    */     
/* 199:    */ 
/* 200:247 */     Object[] nl = nodes.values().toArray();
/* 201:248 */     for (int i = 0; i < nl.length; i++)
/* 202:    */     {
/* 203:250 */       Node n = new Node();
/* 204:251 */       nodeList[i] = n;
/* 205:252 */       ParserNode p = (ParserNode)nl[i];
/* 206:253 */       n.setName(p.name);
/* 207:254 */       Integer nid = (Integer)nameTable.get(p.name);
/* 208:255 */       n.nodeID = nid.intValue();
/* 209:256 */       n.dependencies = ht2ArrayFromKey(nameTable, p.dependencies);
/* 210:257 */       n.weights = ht2ArrayValFromKey(nameTable, p.dWeights);
/* 211:258 */       n.backEdges = ht2ArrayFromKey(nameTable, p.backEdges);
/* 212:259 */       n.beWeights = ht2ArrayValFromKey(nameTable, p.beWeights);
/* 213:    */     }
/* 214:262 */     return retGraph;
/* 215:    */   }
/* 216:    */   
/* 217:    */   private static int[] ht2ArrayFromKey(Hashtable key, Hashtable values)
/* 218:    */   {
/* 219:271 */     int[] retArray = new int[values.size()];
/* 220:    */     try
/* 221:    */     {
/* 222:274 */       Object[] oa = values.keySet().toArray();
/* 223:275 */       for (int i = 0; i < oa.length; i++)
/* 224:    */       {
/* 225:277 */         String s = (String)oa[i];
/* 226:278 */         Integer val = (Integer)key.get(s);
/* 227:279 */         retArray[i] = val.intValue();
/* 228:    */       }
/* 229:281 */       return retArray;
/* 230:    */     }
/* 231:    */     catch (Exception e)
/* 232:    */     {
/* 233:285 */       e.printStackTrace();
/* 234:    */     }
/* 235:286 */     return null;
/* 236:    */   }
/* 237:    */   
/* 238:    */   private static int[] ht2ArrayValFromKey(Hashtable key, Hashtable values)
/* 239:    */   {
/* 240:296 */     int[] retArray = new int[values.size()];
/* 241:    */     try
/* 242:    */     {
/* 243:299 */       Object[] oa = values.keySet().toArray();
/* 244:300 */       for (int i = 0; i < oa.length; i++)
/* 245:    */       {
/* 246:302 */         String s = (String)oa[i];
/* 247:303 */         Integer value = (Integer)values.get(s);
/* 248:304 */         retArray[i] = value.intValue();
/* 249:    */       }
/* 250:306 */       return retArray;
/* 251:    */     }
/* 252:    */     catch (Exception e)
/* 253:    */     {
/* 254:310 */       e.printStackTrace();
/* 255:    */     }
/* 256:311 */     return null;
/* 257:    */   }
/* 258:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.util.BunchUtilities
 * JD-Core Version:    0.7.0.1
 */