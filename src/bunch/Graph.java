/*    1:     */ package bunch;
/*    2:     */ 
/*    3:     */ import java.io.Serializable;
/*    4:     */ import java.util.Random;
/*    5:     */ 
/*    6:     */ public class Graph
/*    7:     */   implements Serializable
/*    8:     */ {
/*    9:     */   private Node[] nodes_d;
/*   10:     */   private Node[] originalNodes_d;
/*   11:     */   private int[] clusters_d;
/*   12:     */   private boolean[] locked_d;
/*   13:  60 */   private boolean hasLocks_d = false;
/*   14:  61 */   private boolean isMaximum_d = false;
/*   15:  62 */   private int graphLevel_d = 0;
/*   16:  63 */   private boolean isClusterTree_d = false;
/*   17:     */   private Graph previousLevelGraph_d;
/*   18:     */   private transient double intradependenciesValue_d;
/*   19:     */   private transient double interdependenciesValue_d;
/*   20:     */   private transient double objectiveFunctionValue_d;
/*   21:     */   private transient Random random_d;
/*   22:  73 */   transient ObjectiveFunctionCalculator calculator_d = null;
/*   23:     */   public static ObjectiveFunctionCalculatorFactory objectiveFunctionCalculatorFactory_sd;
/*   24:     */   
/*   25:     */   public Graph()
/*   26:     */   {
/*   27:  86 */     this.random_d = new Random(System.currentTimeMillis());
/*   28:     */   }
/*   29:     */   
/*   30:     */   private void checkRandomOK()
/*   31:     */   {
/*   32:  91 */     if (this.random_d == null) {
/*   33:  92 */       this.random_d = new Random(System.currentTimeMillis());
/*   34:     */     }
/*   35:     */   }
/*   36:     */   
/*   37:     */   public Graph(int nodes)
/*   38:     */   {
/*   39: 104 */     this();
/*   40: 105 */     initGraph(nodes);
/*   41:     */   }
/*   42:     */   
/*   43:     */   public static void setObjectiveFunctionCalculatorFactory(ObjectiveFunctionCalculatorFactory of)
/*   44:     */   {
/*   45: 120 */     objectiveFunctionCalculatorFactory_sd = of;
/*   46:     */   }
/*   47:     */   
/*   48:     */   public void initGraph(int nodes)
/*   49:     */   {
/*   50: 134 */     this.nodes_d = new Node[nodes];
/*   51: 135 */     this.clusters_d = new int[nodes];
/*   52: 136 */     this.locked_d = new boolean[nodes];
/*   53:     */   }
/*   54:     */   
/*   55:     */   public void setObjectiveFunctionCalculator(String name)
/*   56:     */   {
/*   57: 151 */     this.calculator_d = objectiveFunctionCalculatorFactory_sd.getCalculator(name);
/*   58: 152 */     this.calculator_d.init(this);
/*   59:     */   }
/*   60:     */   
/*   61:     */   public ObjectiveFunctionCalculator getObjectiveFunctionCalculator()
/*   62:     */   {
/*   63: 158 */     return this.calculator_d;
/*   64:     */   }
/*   65:     */   
/*   66:     */   public void clear()
/*   67:     */   {
/*   68: 169 */     for (int i = 0; i < this.nodes_d.length; i++)
/*   69:     */     {
/*   70: 170 */       this.nodes_d[i] = new Node();
/*   71: 171 */       this.clusters_d[i] = -1;
/*   72: 172 */       this.locked_d[i] = false;
/*   73: 173 */       setDoubleLocks(false);
/*   74:     */     }
/*   75:     */   }
/*   76:     */   
/*   77:     */   public void resetNodeLocks()
/*   78:     */   {
/*   79: 182 */     for (int i = 0; i < this.nodes_d.length; i++) {
/*   80: 183 */       this.nodes_d[i].resetNode();
/*   81:     */     }
/*   82: 185 */     setDoubleLocks(false);
/*   83:     */     
/*   84: 187 */     boolean[] locks = getLocks();
/*   85: 189 */     for (int i = 0; i < locks.length; i++) {
/*   86: 190 */       locks[i] = false;
/*   87:     */     }
/*   88: 192 */     setLocks(locks);
/*   89:     */   }
/*   90:     */   
/*   91:     */   public int getNumberOfNodes()
/*   92:     */   {
/*   93: 204 */     return this.nodes_d.length;
/*   94:     */   }
/*   95:     */   
/*   96:     */   public Node[] getOriginalNodes()
/*   97:     */   {
/*   98: 217 */     return this.originalNodes_d;
/*   99:     */   }
/*  100:     */   
/*  101:     */   public void setOriginalNodes(Node[] nodes)
/*  102:     */   {
/*  103: 232 */     this.originalNodes_d = nodes;
/*  104:     */   }
/*  105:     */   
/*  106:     */   public Node[] getNodes()
/*  107:     */   {
/*  108: 245 */     return this.nodes_d;
/*  109:     */   }
/*  110:     */   
/*  111:     */   public void setNodes(Node[] nodes)
/*  112:     */   {
/*  113: 258 */     this.nodes_d = nodes;
/*  114:     */   }
/*  115:     */   
/*  116:     */   public int[] getClusters()
/*  117:     */   {
/*  118: 271 */     return this.clusters_d;
/*  119:     */   }
/*  120:     */   
/*  121:     */   public void setClusters(int[] clusters)
/*  122:     */   {
/*  123: 284 */     this.clusters_d = clusters;
/*  124:     */   }
/*  125:     */   
/*  126:     */   public void setLocks(boolean[] locked)
/*  127:     */   {
/*  128: 300 */     this.locked_d = locked;
/*  129:     */   }
/*  130:     */   
/*  131:     */   public boolean[] getLocks()
/*  132:     */   {
/*  133: 313 */     return this.locked_d;
/*  134:     */   }
/*  135:     */   
/*  136:     */   public Graph cloneGraph()
/*  137:     */   {
/*  138: 329 */     Graph g = new Graph();
/*  139: 330 */     g.nodes_d = this.nodes_d;
/*  140: 331 */     g.clusters_d = new int[this.nodes_d.length];
/*  141: 332 */     g.originalNodes_d = this.originalNodes_d;
/*  142: 333 */     g.locked_d = new boolean[this.nodes_d.length];
/*  143: 334 */     g.intradependenciesValue_d = this.intradependenciesValue_d;
/*  144: 335 */     g.interdependenciesValue_d = this.interdependenciesValue_d;
/*  145: 336 */     g.objectiveFunctionValue_d = this.objectiveFunctionValue_d;
/*  146: 337 */     System.arraycopy(this.clusters_d, 0, g.clusters_d, 0, this.clusters_d.length);
/*  147: 338 */     System.arraycopy(this.locked_d, 0, g.locked_d, 0, this.locked_d.length);
/*  148: 339 */     g.previousLevelGraph_d = this.previousLevelGraph_d;
/*  149: 340 */     g.graphLevel_d = this.graphLevel_d;
/*  150: 341 */     g.setDoubleLocks(hasDoubleLocks());
/*  151: 342 */     g.random_d = this.random_d;
/*  152: 343 */     g.isClusterTree_d = this.isClusterTree_d;
/*  153: 344 */     g.checkRandomOK();
/*  154: 345 */     return g;
/*  155:     */   }
/*  156:     */   
/*  157:     */   public double getObjectiveFunctionValue()
/*  158:     */   {
/*  159: 359 */     return this.objectiveFunctionValue_d;
/*  160:     */   }
/*  161:     */   
/*  162:     */   public void setObjectiveFunctionValue(double objVal)
/*  163:     */   {
/*  164: 374 */     this.objectiveFunctionValue_d = objVal;
/*  165:     */   }
/*  166:     */   
/*  167:     */   public double getInterdependenciesValue()
/*  168:     */   {
/*  169: 389 */     return this.interdependenciesValue_d;
/*  170:     */   }
/*  171:     */   
/*  172:     */   public void setInterdependenciesValue(double inter)
/*  173:     */   {
/*  174: 404 */     this.interdependenciesValue_d = inter;
/*  175:     */   }
/*  176:     */   
/*  177:     */   public double getIntradependenciesValue()
/*  178:     */   {
/*  179: 419 */     return this.intradependenciesValue_d;
/*  180:     */   }
/*  181:     */   
/*  182:     */   public void setIntradependenciesValue(double intra)
/*  183:     */   {
/*  184: 434 */     this.intradependenciesValue_d = intra;
/*  185:     */   }
/*  186:     */   
/*  187:     */   public void calculateObjectiveFunctionValue()
/*  188:     */   {
/*  189: 445 */     if (this.calculator_d == null) {
/*  190: 446 */       setObjectiveFunctionCalculator(objectiveFunctionCalculatorFactory_sd.getCurrentCalculator());
/*  191:     */     }
/*  192: 449 */     Cluster c = new Cluster(this, getClusters());
/*  193: 450 */     c.calcObjFn();
/*  194: 451 */     setObjectiveFunctionValue(c.getObjFnValue());
/*  195:     */   }
/*  196:     */   
/*  197:     */   public long getNumberOfPartitions()
/*  198:     */   {
/*  199: 464 */     long p = 0L;
/*  200: 466 */     for (int i = 1; i <= this.nodes_d.length; i++)
/*  201:     */     {
/*  202: 467 */       long ip = calcStirling(this.nodes_d.length, i);
/*  203: 468 */       p += ip;
/*  204:     */     }
/*  205: 471 */     return p;
/*  206:     */   }
/*  207:     */   
/*  208:     */   private long calcStirling(int n, int k)
/*  209:     */   {
/*  210: 482 */     if (k == 1) {
/*  211: 483 */       return 1L;
/*  212:     */     }
/*  213: 484 */     if (n == k) {
/*  214: 485 */       return 1L;
/*  215:     */     }
/*  216: 487 */     return calcStirling(n - 1, k - 1) + k * calcStirling(n - 1, k);
/*  217:     */   }
/*  218:     */   
/*  219:     */   public Graph cloneAllNodesCluster()
/*  220:     */   {
/*  221: 500 */     Graph g = cloneGraph();
/*  222: 501 */     if (g.hasDoubleLocks())
/*  223:     */     {
/*  224: 502 */       int num = g.findFreeCluster(g.getClusterNames());
/*  225: 503 */       for (int i = 0; i < g.clusters_d.length; i++) {
/*  226: 504 */         if (g.locked_d[i] == false) {
/*  227: 505 */           g.clusters_d[i] = num;
/*  228:     */         }
/*  229:     */       }
/*  230:     */     }
/*  231:     */     else
/*  232:     */     {
/*  233: 510 */       for (int i = 0; i < g.clusters_d.length; i++) {
/*  234: 511 */         if (g.locked_d[i] == false) {
/*  235: 512 */           g.clusters_d[i] = 0;
/*  236:     */         }
/*  237:     */       }
/*  238:     */     }
/*  239: 516 */     return g;
/*  240:     */   }
/*  241:     */   
/*  242:     */   public int findFreeCluster(int[] c)
/*  243:     */   {
/*  244: 534 */     int n = 0;
/*  245: 535 */     boolean change = true;
/*  246: 536 */     while (change)
/*  247:     */     {
/*  248: 537 */       change = false;
/*  249: 538 */       for (int i = 0; i < c.length; i++) {
/*  250: 539 */         if (c[i] == n)
/*  251:     */         {
/*  252: 540 */           n++;
/*  253: 541 */           change = true;
/*  254: 542 */           break;
/*  255:     */         }
/*  256:     */       }
/*  257:     */     }
/*  258: 546 */     return n;
/*  259:     */   }
/*  260:     */   
/*  261:     */   public int findFreeRandomCluster(int[] c)
/*  262:     */   {
/*  263: 566 */     checkRandomOK();
/*  264: 567 */     int n = (int)(this.random_d.nextFloat() * (this.clusters_d.length - 1));
/*  265: 568 */     int loops = 0;
/*  266: 569 */     boolean change = true;
/*  267: 570 */     while ((change) && (loops++ < this.clusters_d.length * 2))
/*  268:     */     {
/*  269: 571 */       change = false;
/*  270: 572 */       for (int i = 0; i < c.length; i++) {
/*  271: 573 */         if (c[i] == n)
/*  272:     */         {
/*  273: 574 */           n = (int)(this.random_d.nextFloat() * (this.clusters_d.length - 1));
/*  274: 575 */           change = true;
/*  275: 576 */           break;
/*  276:     */         }
/*  277:     */       }
/*  278:     */     }
/*  279: 580 */     return n;
/*  280:     */   }
/*  281:     */   
/*  282:     */   public Graph cloneSingleNodeClusters()
/*  283:     */   {
/*  284: 593 */     Graph g = cloneGraph();
/*  285: 595 */     if (g.hasDoubleLocks()) {
/*  286: 596 */       for (int i = 0; i < g.clusters_d.length; i++) {
/*  287: 597 */         if (g.locked_d[i] == false)
/*  288:     */         {
/*  289: 598 */           int num = g.findFreeCluster(g.getClusterNames());
/*  290: 599 */           g.clusters_d[i] = num;
/*  291:     */         }
/*  292:     */       }
/*  293:     */     } else {
/*  294: 604 */       for (int i = 0; i < g.clusters_d.length; i++) {
/*  295: 605 */         if (g.locked_d[i] == false) {
/*  296: 606 */           g.clusters_d[i] = i;
/*  297:     */         }
/*  298:     */       }
/*  299:     */     }
/*  300: 610 */     return g;
/*  301:     */   }
/*  302:     */   
/*  303:     */   public void setDoubleLocks(boolean v)
/*  304:     */   {
/*  305: 624 */     this.hasLocks_d = v;
/*  306:     */   }
/*  307:     */   
/*  308:     */   public boolean hasDoubleLocks()
/*  309:     */   {
/*  310: 638 */     return this.hasLocks_d;
/*  311:     */   }
/*  312:     */   
/*  313:     */   public void shuffleClusters()
/*  314:     */   {
/*  315: 651 */     int[] clustNames = null;
/*  316: 652 */     if (hasDoubleLocks()) {
/*  317: 653 */       clustNames = getUnlockedClusterNames();
/*  318:     */     } else {
/*  319: 656 */       clustNames = getClusterNames();
/*  320:     */     }
/*  321: 658 */     if ((clustNames == null) || (clustNames.length == 0)) {
/*  322: 659 */       return;
/*  323:     */     }
/*  324: 661 */     for (int i = 0; i < this.clusters_d.length; i++) {
/*  325: 662 */       if ((Math.random() > 0.6D) && (this.locked_d[i] == false)) {
/*  326: 663 */         this.clusters_d[i] = clustNames[((int)(Math.random() * (clustNames.length - 1)))];
/*  327:     */       }
/*  328:     */     }
/*  329:     */   }
/*  330:     */   
/*  331:     */   public Graph cloneWithRandomClusters()
/*  332:     */   {
/*  333: 677 */     checkRandomOK();
/*  334: 678 */     Graph g = cloneGraph();
/*  335: 679 */     if (g.hasDoubleLocks()) {
/*  336: 680 */       for (int i = 0; i < g.clusters_d.length; i++) {
/*  337: 681 */         if (g.locked_d[i] == false) {
/*  338: 682 */           g.clusters_d[i] = g.findFreeRandomCluster(g.getClusterNames());
/*  339:     */         }
/*  340:     */       }
/*  341:     */     } else {
/*  342: 687 */       for (int i = 0; i < g.clusters_d.length; i++) {
/*  343: 688 */         if (g.locked_d[i] == false) {
/*  344: 689 */           g.clusters_d[i] = ((int)(this.random_d.nextFloat() * (g.clusters_d.length - 1)));
/*  345:     */         }
/*  346:     */       }
/*  347:     */     }
/*  348: 693 */     return g;
/*  349:     */   }
/*  350:     */   
/*  351:     */   public int[] getRandomCluster()
/*  352:     */   {
/*  353: 703 */     checkRandomOK();
/*  354: 704 */     int[] c = new int[this.nodes_d.length];
/*  355: 706 */     if (hasDoubleLocks()) {
/*  356: 707 */       for (int i = 0; i < this.clusters_d.length; i++) {
/*  357: 708 */         if (this.locked_d[i] == false) {
/*  358: 709 */           c[i] = findFreeRandomCluster(getClusterNames());
/*  359:     */         }
/*  360:     */       }
/*  361:     */     } else {
/*  362: 714 */       for (int i = 0; i < this.clusters_d.length; i++) {
/*  363: 715 */         if (this.locked_d[i] == false) {
/*  364: 716 */           c[i] = ((int)(this.random_d.nextFloat() * (this.clusters_d.length - 1)));
/*  365:     */         }
/*  366:     */       }
/*  367:     */     }
/*  368: 720 */     return c;
/*  369:     */   }
/*  370:     */   
/*  371:     */   public int[] genRandomClusterSize()
/*  372:     */   {
/*  373: 729 */     checkRandomOK();
/*  374: 730 */     int[] c = new int[this.nodes_d.length];
/*  375: 731 */     int[] existingClusters = getClusters();
/*  376: 732 */     int numNodes = this.clusters_d.length;
/*  377:     */     
/*  378: 734 */     int nodeCount = 0;
/*  379: 735 */     for (int i = 0; i < this.clusters_d.length; i++) {
/*  380: 736 */       if (this.locked_d[i] == false)
/*  381:     */       {
/*  382: 737 */         c[i] = nodeCount;
/*  383: 738 */         nodeCount++;
/*  384:     */       }
/*  385:     */       else
/*  386:     */       {
/*  387: 741 */         c[i] = existingClusters[i];
/*  388:     */       }
/*  389:     */     }
/*  390: 743 */     numNodes = nodeCount;
/*  391:     */     
/*  392: 745 */     int numClusters = (int)(this.random_d.nextFloat() * (this.clusters_d.length - 1)) + 1;
/*  393: 746 */     int clustSize = numNodes / numClusters;
/*  394: 747 */     int remainder = numNodes % numClusters;
/*  395:     */     
/*  396: 749 */     int currC = 0;
/*  397: 750 */     int currNode = 0;
/*  398: 751 */     while (currC < numClusters)
/*  399:     */     {
/*  400: 753 */       int currCSize = 0;
/*  401: 754 */       while (currCSize < clustSize)
/*  402:     */       {
/*  403: 756 */         if (this.locked_d[currNode] == false)
/*  404:     */         {
/*  405: 758 */           currCSize++;
/*  406: 759 */           c[currNode] = currC;
/*  407:     */         }
/*  408: 761 */         currNode++;
/*  409:     */       }
/*  410: 763 */       currC++;
/*  411:     */     }
/*  412: 767 */     if (currNode < this.clusters_d.length)
/*  413:     */     {
/*  414: 770 */       int[] clustStack = new int[numClusters];
/*  415: 772 */       for (int i = 0; i < clustStack.length; i++) {
/*  416: 773 */         clustStack[i] = i;
/*  417:     */       }
/*  418: 776 */       for (int i = 0; i < clustStack.length; i++)
/*  419:     */       {
/*  420: 778 */         int pos1 = (int)(this.random_d.nextFloat() * (clustStack.length - 1));
/*  421: 779 */         int pos2 = (int)(this.random_d.nextFloat() * (clustStack.length - 1));
/*  422: 780 */         int tmp = clustStack[pos1];
/*  423: 781 */         clustStack[pos1] = clustStack[pos2];
/*  424: 782 */         clustStack[pos2] = tmp;
/*  425:     */       }
/*  426: 786 */       int stackIdx = 0;
/*  427: 787 */       for (int i = currNode; i < this.clusters_d.length; i++) {
/*  428: 789 */         if (this.locked_d[i] == false) {
/*  429: 790 */           c[i] = clustStack[(stackIdx++)];
/*  430:     */         }
/*  431:     */       }
/*  432:     */     }
/*  433: 795 */     for (int i = 0; i < this.clusters_d.length; i++)
/*  434:     */     {
/*  435: 796 */       int pos1 = (int)(this.random_d.nextFloat() * (this.clusters_d.length - 1));
/*  436: 797 */       int pos2 = (int)(this.random_d.nextFloat() * (this.clusters_d.length - 1));
/*  437: 799 */       if ((this.locked_d[pos1] == false) && (this.locked_d[pos2] == false))
/*  438:     */       {
/*  439: 800 */         int tmp = c[pos1];
/*  440: 801 */         c[pos1] = c[pos2];
/*  441: 802 */         c[pos2] = tmp;
/*  442:     */       }
/*  443:     */     }
/*  444: 805 */     return c;
/*  445:     */   }
/*  446:     */   
/*  447:     */   public int[] genRandomClusterSizeWithLimits(int min, int max)
/*  448:     */   {
/*  449: 814 */     int range = max - min;
/*  450: 816 */     if (range < 0) {
/*  451: 816 */       return null;
/*  452:     */     }
/*  453: 818 */     checkRandomOK();
/*  454: 819 */     int[] c = new int[this.nodes_d.length];
/*  455: 820 */     int[] existingClusters = getClusters();
/*  456: 821 */     int numNodes = this.clusters_d.length;
/*  457:     */     
/*  458: 823 */     int nodeCount = 0;
/*  459: 824 */     for (int i = 0; i < this.clusters_d.length; i++) {
/*  460: 825 */       if (this.locked_d[i] == false)
/*  461:     */       {
/*  462: 826 */         c[i] = nodeCount;
/*  463: 827 */         nodeCount++;
/*  464:     */       }
/*  465:     */       else
/*  466:     */       {
/*  467: 830 */         c[i] = existingClusters[i];
/*  468:     */       }
/*  469:     */     }
/*  470: 832 */     numNodes = nodeCount;
/*  471:     */     
/*  472: 834 */     int numClusters = (int)(this.random_d.nextFloat() * (range - 1)) + 1 + min;
/*  473: 835 */     int clustSize = numNodes / numClusters;
/*  474: 836 */     int remainder = numNodes % numClusters;
/*  475:     */     
/*  476: 838 */     int currC = 0;
/*  477: 839 */     int currNode = 0;
/*  478: 840 */     while (currC < numClusters)
/*  479:     */     {
/*  480: 842 */       int currCSize = 0;
/*  481: 843 */       while (currCSize < clustSize)
/*  482:     */       {
/*  483: 845 */         if (this.locked_d[currNode] == false)
/*  484:     */         {
/*  485: 847 */           currCSize++;
/*  486: 848 */           c[currNode] = currC;
/*  487:     */         }
/*  488: 850 */         currNode++;
/*  489:     */       }
/*  490: 852 */       currC++;
/*  491:     */     }
/*  492: 856 */     if (currNode < this.clusters_d.length)
/*  493:     */     {
/*  494: 859 */       int[] clustStack = new int[numClusters];
/*  495: 861 */       for (int i = 0; i < clustStack.length; i++) {
/*  496: 862 */         clustStack[i] = i;
/*  497:     */       }
/*  498: 865 */       for (int i = 0; i < clustStack.length; i++)
/*  499:     */       {
/*  500: 867 */         int pos1 = (int)(this.random_d.nextFloat() * (clustStack.length - 1));
/*  501: 868 */         int pos2 = (int)(this.random_d.nextFloat() * (clustStack.length - 1));
/*  502: 869 */         int tmp = clustStack[pos1];
/*  503: 870 */         clustStack[pos1] = clustStack[pos2];
/*  504: 871 */         clustStack[pos2] = tmp;
/*  505:     */       }
/*  506: 875 */       int stackIdx = 0;
/*  507: 876 */       for (int i = currNode; i < this.clusters_d.length; i++) {
/*  508: 878 */         if (this.locked_d[i] == false) {
/*  509: 879 */           c[i] = clustStack[(stackIdx++)];
/*  510:     */         }
/*  511:     */       }
/*  512:     */     }
/*  513: 884 */     for (int i = 0; i < this.clusters_d.length; i++)
/*  514:     */     {
/*  515: 885 */       int pos1 = (int)(this.random_d.nextFloat() * (this.clusters_d.length - 1));
/*  516: 886 */       int pos2 = (int)(this.random_d.nextFloat() * (this.clusters_d.length - 1));
/*  517: 888 */       if ((this.locked_d[pos1] == false) && (this.locked_d[pos2] == false))
/*  518:     */       {
/*  519: 889 */         int tmp = c[pos1];
/*  520: 890 */         c[pos1] = c[pos2];
/*  521: 891 */         c[pos2] = tmp;
/*  522:     */       }
/*  523:     */     }
/*  524: 894 */     return c;
/*  525:     */   }
/*  526:     */   
/*  527:     */   public void setRandom(Random r)
/*  528:     */   {
/*  529: 907 */     this.random_d = r;
/*  530:     */   }
/*  531:     */   
/*  532:     */   public Random getRandom()
/*  533:     */   {
/*  534: 920 */     checkRandomOK();
/*  535: 921 */     return this.random_d;
/*  536:     */   }
/*  537:     */   
/*  538:     */   public int[] getClusterNames()
/*  539:     */   {
/*  540: 933 */     int[] clusts = new int[this.nodes_d.length];
/*  541:     */     
/*  542: 935 */     int numClusts = 0;
/*  543: 936 */     for (int i = 0; i < this.clusters_d.length; i++)
/*  544:     */     {
/*  545: 937 */       int name = this.clusters_d[i];
/*  546: 938 */       int j = 0;
/*  547: 939 */       for (j = 0; j < numClusts; j++) {
/*  548: 940 */         if (clusts[j] == name) {
/*  549:     */           break;
/*  550:     */         }
/*  551:     */       }
/*  552: 943 */       if (j == numClusts)
/*  553:     */       {
/*  554: 944 */         clusts[j] = name;
/*  555: 945 */         numClusts++;
/*  556:     */       }
/*  557:     */     }
/*  558: 948 */     int[] tmp = new int[numClusts];
/*  559: 949 */     System.arraycopy(clusts, 0, tmp, 0, numClusts);
/*  560:     */     
/*  561: 951 */     return tmp;
/*  562:     */   }
/*  563:     */   
/*  564:     */   public int[] getUnlockedClusterNames()
/*  565:     */   {
/*  566: 966 */     int[] clusts = new int[this.nodes_d.length];
/*  567:     */     
/*  568: 968 */     int numClusts = 0;
/*  569: 969 */     for (int i = 0; i < this.clusters_d.length; i++) {
/*  570: 970 */       if (this.locked_d[i] == false)
/*  571:     */       {
/*  572: 973 */         int name = this.clusters_d[i];
/*  573: 974 */         int j = 0;
/*  574: 975 */         for (j = 0; j < numClusts; j++) {
/*  575: 976 */           if (clusts[j] == name) {
/*  576:     */             break;
/*  577:     */           }
/*  578:     */         }
/*  579: 979 */         if (j == numClusts)
/*  580:     */         {
/*  581: 980 */           clusts[j] = name;
/*  582: 981 */           numClusts++;
/*  583:     */         }
/*  584:     */       }
/*  585:     */     }
/*  586: 984 */     int[] tmp = new int[numClusts];
/*  587: 985 */     System.arraycopy(clusts, 0, tmp, 0, numClusts);
/*  588:     */     
/*  589: 987 */     return tmp;
/*  590:     */   }
/*  591:     */   
/*  592:     */   public boolean isMaximum()
/*  593:     */   {
/*  594:1001 */     return this.isMaximum_d;
/*  595:     */   }
/*  596:     */   
/*  597:     */   public void setMaximum(boolean b)
/*  598:     */   {
/*  599:1013 */     this.isMaximum_d = b;
/*  600:     */   }
/*  601:     */   
/*  602:     */   public void setPreviousLevelGraph(Graph g)
/*  603:     */   {
/*  604:1032 */     this.previousLevelGraph_d = g;
/*  605:     */   }
/*  606:     */   
/*  607:     */   public Graph getPreviousLevelGraph()
/*  608:     */   {
/*  609:1046 */     return this.previousLevelGraph_d;
/*  610:     */   }
/*  611:     */   
/*  612:     */   public void setGraphLevel(int gl)
/*  613:     */   {
/*  614:1059 */     this.graphLevel_d = gl;
/*  615:     */   }
/*  616:     */   
/*  617:     */   public int getGraphLevel()
/*  618:     */   {
/*  619:1072 */     return this.graphLevel_d;
/*  620:     */   }
/*  621:     */   
/*  622:     */   public boolean isClusterTree()
/*  623:     */   {
/*  624:1081 */     return this.isClusterTree_d;
/*  625:     */   }
/*  626:     */   
/*  627:     */   public void setIsClusterTree(boolean b)
/*  628:     */   {
/*  629:1084 */     this.isClusterTree_d = b;
/*  630:     */   }
/*  631:     */   
/*  632:     */   public Graph getMedianTree()
/*  633:     */   {
/*  634:1092 */     if (!isClusterTree()) {
/*  635:1093 */       return this;
/*  636:     */     }
/*  637:1095 */     int lvl = getGraphLevel();
/*  638:1096 */     int medLevel = Math.max(lvl / 2, 0);
/*  639:1097 */     Graph tmpGraph = this;
/*  640:1098 */     while (tmpGraph.getGraphLevel() > medLevel) {
/*  641:1099 */       tmpGraph = tmpGraph.getPreviousLevelGraph();
/*  642:     */     }
/*  643:1101 */     return tmpGraph;
/*  644:     */   }
/*  645:     */   
/*  646:     */   public Graph getDetailedGraph()
/*  647:     */   {
/*  648:1110 */     int lvl = getGraphLevel();
/*  649:     */     
/*  650:1112 */     Graph tmpGraph = this;
/*  651:1113 */     while (tmpGraph.getGraphLevel() > 0) {
/*  652:1114 */       tmpGraph = tmpGraph.getPreviousLevelGraph();
/*  653:     */     }
/*  654:1116 */     return tmpGraph;
/*  655:     */   }
/*  656:     */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.Graph
 * JD-Core Version:    0.7.0.1
 */