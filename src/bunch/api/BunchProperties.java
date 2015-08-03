/*   1:    */ package bunch.api;
/*   2:    */ 
/*   3:    */ import java.util.Properties;
/*   4:    */ 
/*   5:    */ public class BunchProperties
/*   6:    */   extends Properties
/*   7:    */ {
/*   8:    */   public static final String MDG_INPUT_FILE_NAME = "MDGInputFile";
/*   9:    */   public static final String MDG_PARSER_DELIMS = "MDGParserDelimeters";
/*  10:    */   public static final String MDG_PARSER_USE_SPACES = "MDGParserUseSpaces";
/*  11:    */   public static final String MDG_PARSER_USE_TABS = "MDGParserUseTabs";
/*  12:    */   public static final String OUTPUT_DEVICE = "OutputDevice";
/*  13:    */   public static final String OUTPUT_FILE = "OutputFile";
/*  14:    */   public static final String OUTPUT_HASHTABLE = "OutputHashtable";
/*  15:    */   public static final String MDG_OUTPUT_FILE_BASE = "MDGOutputFile";
/*  16:    */   public static final String MDG_OUTPUT_MODE = "MDGOutputMode";
/*  17:    */   public static final String OUTPUT_NONE = "NoOutputFile";
/*  18:    */   public static final String OUTPUT_DETAILED = "MDGOutputDetailed";
/*  19:    */   public static final String OUTPUT_MEDIAN = "MDGOutputMedian";
/*  20:    */   public static final String OUTPUT_TOP = "MDGOutputTop";
/*  21:    */   public static final String OUTPUT_TREE = "OutputTree";
/*  22:    */   public static final String OUTPUT_FORMAT = "OutputFormat";
/*  23:    */   public static final String DOT_OUTPUT_FORMAT = "DotOutputFormat";
/*  24:    */   public static final String TEXT_OUTPUT_FORMAT = "TextOutputFormat";
/*  25:    */   public static final String GXL_OUTPUT_FORMAT = "GXLOutputFormat";
/*  26:    */   public static final String NULL_OUTPUT_FORMAT = "NullOutputFormat";
/*  27:    */   public static final String OUTPUT_DIRECTORY = "OutputDirectory";
/*  28:    */   public static final String CLUSTERING_APPROACH = "ClusteringApproach";
/*  29:    */   public static final String ONE_LEVEL = "ClustApproachOneLevel";
/*  30:    */   public static final String AGGLOMERATIVE = "ClustApproachAgglomerative";
/*  31:    */   public static final String MQ_CALCULATOR_CLASS = "MQCalculatorClass";
/*  32:    */   public static final String MQ_CALC_DEFAULT_CLASS = "bunch.TurboMQIncrW";
/*  33:    */   public static final String ECHO_RESULTS_TO_CONSOLE = "EchoResultsToConsole";
/*  34:    */   public static final String CLUSTERING_ALG = "ClusteringAlgorithm";
/*  35:    */   public static final String ALG_EXHAUSTIVE = "Exhaustive";
/*  36:    */   public static final String ALG_GA = "GA";
/*  37:    */   public static final String ALG_GA_SELECTION_METHOD = "GASelectionMethod";
/*  38:    */   public static final String ALG_GA_SELECTION_TOURNAMENT = "GASelectionMethodTournament";
/*  39:    */   public static final String ALG_GA_SELECTION_ROULETTE = "GASelectionMethodRoulette";
/*  40:    */   public static final String ALG_GA_POPULATION_SZ = "GAPopulationSize";
/*  41:    */   public static final String ALG_GA_CROSSOVER_PROB = "GACrossoverProbability";
/*  42:    */   public static final String ALG_GA_MUTATION_PROB = "GAMutationProb";
/*  43:    */   public static final String ALG_GA_NUM_GENERATIONS = "GANumGenerations";
				
				
/*  44:    */   public static final String ALG_NAHC = "NAHC";
/*  45:    */   public static final String ALG_NAHC_POPULATION_SZ = "NAHCPopulationSize";
/*  46:    */   public static final String ALG_NAHC_HC_PCT = "NAHCHillClimbPct";
/*  47:    */   public static final String ALG_NAHC_RND_PCT = "NAHCRandomizePct";
/*  48:    */   public static final String ALG_NAHC_SA_CLASS = "NAHCSimulatedAnnealingClass";
/*  49:    */   public static final String ALG_NAHC_SA_CONFIG = "NAHCSimulatedAnnealingConfig";
/*  50:    */   public static final String ALG_HILL_CLIMBING = "NAHC";
/*  51:    */   public static final String ALG_HC_POPULATION_SZ = "NAHCPopulationSize";
/*  52:    */   public static final String ALG_HC_HC_PCT = "NAHCHillClimbPct";
/*  53:    */   public static final String ALG_HC_RND_PCT = "NAHCRandomizePct";
/*  54:    */   public static final String ALG_HC_SA_CLASS = "NAHCSimulatedAnnealingClass";
/*  55:    */   public static final String ALG_HC_SA_CONFIG = "NAHCSimulatedAnnealingConfig";
/*  56:    */   public static final String ALG_SAHC = "SAHC";
/*  57:    */   public static final String ALG_SAHC_POPULATION_SZ = "SAHCPopulationSize";
/*  58:    */   public static final String RUN_MODE = "RunMode";
/*  59:    */   public static final String RUN_MODE_CLUSTER = "Cluster";
/*  60:    */   public static final String RUN_MODE_MQ_CALC = "MQCalculator";
/*  61:    */   public static final String MQCALC_MDG_FILE = "MQCalcMDGFile";
/*  62:    */   public static final String MQCALC_SIL_FILE = "MQCalcSILFile";
/*  63:    */   public static final String RUN_MODE_MOJO_CALC = "MOJOCalculator";
/*  64:    */   public static final String MOJO_FILE_1 = "MOJOFile1";
/*  65:    */   public static final String MOJO_FILE_2 = "MOJOFile2";
/*  66:    */   public static final String RUN_MODE_PR_CALC = "PRCalculator";
/*  67:    */   public static final String PR_EXPERT_FILE = "PRExpertDecomposition";
/*  68:    */   public static final String PR_CLUSTER_FILE = "PRClusterDecomposition";
/*  69:    */   public static final String LIBRARY_LIST = "LibraryList";
/*  70:    */   public static final String OMNIPRESENT_CLIENTS = "OmnipresentClients";
/*  71:    */   public static final String OMNIPRESENT_SUPPLIERS = "OmnipresentSuppliers";
/*  72:    */   public static final String OMNIPRESENT_BOTH = "OmnipresentBoth";
/*  73:    */   public static final String PROGRESS_CALLBACK_CLASS = "ProgressCallbackClass";
/*  74:    */   public static final String PROGRESS_CALLBACK_FREQ = "ProgressCallbackFreq";
/*  75:    */   public static final String TIMEOUT_TIME = "TimoutTime";
/*  76:    */   public static final String SPECIAL_MODULE_HASHTABLE = "SpecialModuleHashTable";
/*  77:    */   public static final String MDG_GRAPH_OBJECT = "MDGGraphObject";
/*  78:    */   public static final String CLUSTERING_THREAD_PRIORITY = "ClusteringThreadPriority";
/*  79:    */   public static final String RUN_ASYNC_NOTIFY_CLASS = "RunAsyncNotifyClass";
/*  80:    */   public static final String USER_DIRECTED_CLUSTER_SIL = "UserDirectedClusterSIL";
/*  81:    */   public static final String LOCK_USER_SET_CLUSTERS = "LockUserSetClusters";
/*  82:    */   
/*  83:    */   public BunchProperties()
/*  84:    */   {
/*  85:127 */     this.defaults = new Properties();
/*  86:128 */     this.defaults.setProperty("MDGParserUseSpaces", "True");
/*  87:129 */     this.defaults.setProperty("MDGParserUseTabs", "True");
/*  88:130 */     this.defaults.setProperty("MDGOutputMode", "MDGOutputMedian");
/*  89:131 */     this.defaults.setProperty("EchoResultsToConsole", "False");
/*  90:132 */     this.defaults.setProperty("ClusteringAlgorithm", "NAHC");
/*  91:133 */     this.defaults.setProperty("RunMode", "Cluster");
/*  92:134 */     this.defaults.setProperty("MDGOutputMode", "MDGOutputMedian");
/*  93:135 */     this.defaults.setProperty("OutputTree", "False");
/*  94:136 */     this.defaults.setProperty("OutputDevice", "OutputFile");
/*  95:137 */     this.defaults.setProperty("ClusteringApproach", "ClustApproachAgglomerative");
/*  96:138 */     this.defaults.setProperty("MQCalculatorClass", "bunch.TurboMQIncrW");
/*  97:139 */     this.defaults.setProperty("ProgressCallbackFreq", "1000");
/*  98:140 */     this.defaults.setProperty("OutputFormat", "NullOutputFormat");
/*  99:141 */     this.defaults.setProperty("MQCalculatorClass", "Incremental MQ Weighted");
/* 100:142 */     this.defaults.setProperty("bunch.TurboMQIncrW", "Incremental MQ Weighted");
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.api.BunchProperties
 * JD-Core Version:    0.7.0.1
 */