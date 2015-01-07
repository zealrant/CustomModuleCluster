package bunch;

import java.util.Random;

public class Joon2ArchiveClustering extends GenericClusteringMethod {
	GAConfiguration config_d;
	GAMethod method_d;
	Feature[] preFeatures_d;
	Feature[] features_d;
	Feature[] postFeatures_d;

	public Joon2ArchiveClustering() {
		setConfigurable(true);
		setThreshold(1.0D);
	}

	public int getMaxIterations() {
		return getConfiguration().getNumOfIterations();
	}

	public void init() {
		setPopSize(getConfiguration().getPopulationSize());
		setNumOfExperiments(getConfiguration().getNumOfIterations());
		this.config_d = ((GAConfiguration) getConfiguration());
		this.method_d = this.config_d.getMethod();

		Graph graph = getGraph().cloneGraph();
		this.method_d.setRandomNumberGenerator(new Random(System.currentTimeMillis()));
		this.method_d.setBestGraph(graph.cloneWithRandomClusters());
		this.method_d.getBestGraph().shuffleClusters();
		this.method_d.getBestGraph().calculateObjectiveFunctionValue();
		/*
		this.currentPopulation_d = new Graph[getPopSize()];

		for (int i = 0; i < getPopSize(); i++) {
			this.currentPopulation_d[i] = graph.cloneWithRandomClusters();
			this.currentPopulation_d[i].shuffleClusters();
			this.currentPopulation_d[i].calculateObjectiveFunctionValue();
			if (this.currentPopulation_d[i].getObjectiveFunctionValue() > getBestGraph().getObjectiveFunctionValue()) {
				setBestGraph(this.currentPopulation_d[i].cloneGraph());
			}
		}
		this.currentPopulation_d[0] = this.currentPopulation_d[0]
				.cloneAllNodesCluster();
		this.currentPopulation_d[0].calculateObjectiveFunctionValue();
		if (getPopSize() >= 2) {
			this.currentPopulation_d[1] = this.currentPopulation_d[0].cloneSingleNodeClusters();
			this.currentPopulation_d[1].calculateObjectiveFunctionValue();
		}
		this.method_d.setPopulation(this.currentPopulation_d);
		this.method_d.setMutationThreshold(this.config_d.getMutationThreshold());
		this.method_d.setCrossoverThreshold(this.config_d.getCrossoverThreshold());
		this.method_d.init();

		this.preFeatures_d = this.config_d.getPreConditionFeatures();
		this.features_d = this.config_d.getFeatures();
		this.postFeatures_d = this.config_d.getPostConditionFeatures();
		*/
	}

	// TODO: 이부분을 고치면 되겠지
	public void run() {
		init();
		long t = System.currentTimeMillis();
		/*
		int generationsSinceLastChange = 0;

		Graph g2 = this.currentPopulation_d[0].cloneAllNodesCluster();
		g2.calculateObjectiveFunctionValue();
		if (g2.getObjectiveFunctionValue() > getBestGraph()ㅉ.getObjectiveFunctionValue()) {
			setBestGraph(g2);
		}
		Graph g3 = this.currentPopulation_d[0].cloneSingleNodeClusters();
		g3.calculateObjectiveFunctionValue();
		if (g3.getObjectiveFunctionValue() > getBestGraph()
				.getObjectiveFunctionValue()) {
			setBestGraph(g3);
		}
		
		
		
		IterationEvent ev = new IterationEvent(this);
		this.bestOFValue_d = getBestGraph().getObjectiveFunctionValue();
		for (int x = 0; x < this.numExperiments_d; x++) {
			boolean end = nextGeneration();
			if (this.bestOFValue_d != getBestGraph().getObjectiveFunctionValue()) {
				setBestObjectiveFunctionValue(getBestGraph().getObjectiveFunctionValue());
				generationsSinceLastChange = x;
			}
			if (end) {
				if (x - generationsSinceLastChange > this.numExperiments_d
						* getThreshold()) {
					break;
				}
				ev.setIteration(x - generationsSinceLastChange);
				ev.setOverallIteration(x);
				fireIterationEvent(ev);
				reInit();
			} else {
				ev.setIteration(x);
				ev.setOverallIteration(x);
				fireIterationEvent(ev);
			}
			setElapsedTime((System.currentTimeMillis() - t) / 1000.0D);
		}
		ev.setIteration(getMaxIterations());
		ev.setOverallIteration(getMaxIterations());
		fireIterationEvent(ev);
		*/
		setElapsedTime((System.currentTimeMillis() - t) / 1000.0D);
	}

	public void setBestGraph(Graph g) {
		if (this.method_d != null) {
			this.method_d.setBestGraph(g);
		}
	}

	public Cluster getBestCluster() {
		Graph bestG = getBestGraph();
		Cluster c = new Cluster(bestG, bestG.getClusters());
		c.calcObjFn();
		return c;
	}

	public Graph getBestGraph() {
		return this.method_d.getBestGraph();
	}

	public boolean nextGeneration() {
		this.method_d.calcStatistics();

		int parent1 = 0;
		int parent2 = 0;
		int crossp = 0;

		int n = this.method_d.getInitCounter();
		int incr = this.method_d.getIncrementCounter();
		int top = this.method_d.getMaxCounter();
		if (this.preFeatures_d != null) {
			for (int i = 0; i < this.preFeatures_d.length; i++) {
				this.preFeatures_d[i].apply(this.currentPopulation_d);
			}
		}
		while (n < top) {
			this.method_d.selectReproduceCrossAndMutate(n);
			if (this.features_d != null) {
				for (int i = 0; i < this.features_d.length; i++) {
					this.features_d[i].apply(this.method_d);
				}
			}
			n += incr;
		}
		this.method_d.shakePopulation();

		this.method_d.finishGeneration();

		this.currentPopulation_d = this.method_d.getCurrentPopulation();
		if (this.postFeatures_d != null) {
			for (int i = 0; i < this.postFeatures_d.length; i++) {
				this.postFeatures_d[i].apply(this.currentPopulation_d);
			}
		}
		this.method_d.getRandomNumberGenerator().setSeed(
				System.currentTimeMillis());

		return false;
	}

	public String getConfigurationDialogName() {
		return "bunch.Joon2ArchiveClusteringConfigurationDialog";
	}

	public Configuration getConfiguration() {
		if (this.configuration_d == null) {
			this.configuration_d = new GAConfiguration();
		}
		return this.configuration_d;
	}
}

/*
 * Location: C:\Users\Joon\Desktop\Bunch-3.5\
 * 
 * Qualified Name: bunch.GAClusteringMethod
 * 
 * JD-Core Version: 0.7.0.1
 */