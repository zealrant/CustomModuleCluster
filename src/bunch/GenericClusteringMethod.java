package bunch;

import java.util.Random;

public abstract class GenericClusteringMethod extends ClusteringMethod {
	public static int DEFAULT_NUM_EXPERIMENTS = 200;
	public static int DEFAULT_POP_SIZE = 25;
	public static double DEFAULT_THRESHOLD = 0.1D;
	protected Graph[] currentPopulation_d;
	protected int popSize_d = DEFAULT_POP_SIZE;
	protected int numExperiments_d = DEFAULT_NUM_EXPERIMENTS;
	protected double threshold_d = DEFAULT_THRESHOLD;
	protected double bestOFValue_d = 0.0D;

	public GenericClusteringMethod() {
		setPopSize(DEFAULT_POP_SIZE);
		setThreshold(DEFAULT_THRESHOLD);
		setNumOfExperiments(DEFAULT_NUM_EXPERIMENTS);
	}

	public void init() {
		Graph graph = getGraph().cloneGraph();
		graph.getRandom().setSeed(System.currentTimeMillis());
		if (getBestGraph() == null) {
			setBestGraph(graph.cloneWithRandomClusters());
		}
		this.currentPopulation_d = new Graph[getPopSize()];
		for (int i = 0; i < getPopSize(); i++) {
			this.currentPopulation_d[i] = graph.cloneWithRandomClusters();
			this.currentPopulation_d[i].calculateObjectiveFunctionValue();
			this.currentPopulation_d[i].setMaximum(false);
		}
	}

	public void reInit() {
	}

	public void run() {
		init();

		int generationsSinceLastChange = 0;

		Graph g2 = this.currentPopulation_d[0].cloneAllNodesCluster();
		g2.calculateObjectiveFunctionValue();
		if (g2.getObjectiveFunctionValue() > getBestGraph()
				.getObjectiveFunctionValue()) {
			setBestGraph(g2);
		}
		Graph g3 = this.currentPopulation_d[0].cloneSingleNodeClusters();
		g3.calculateObjectiveFunctionValue();
		if (g3.getObjectiveFunctionValue() > getBestGraph()
				.getObjectiveFunctionValue()) {
			setBestGraph(g3);
		}
		long t = System.currentTimeMillis();
		IterationEvent ev = new IterationEvent(this);
		this.bestOFValue_d = getBestGraph().getObjectiveFunctionValue();
		for (int x = 0; x < this.numExperiments_d; x++) {
			boolean end = nextGeneration();
			if (this.bestOFValue_d != getBestGraph()
					.getObjectiveFunctionValue()) {
				setBestObjectiveFunctionValue(getBestGraph()
						.getObjectiveFunctionValue());
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
		setElapsedTime((System.currentTimeMillis() - t) / 1000.0D);
	}

	public abstract boolean nextGeneration();

	public void setThreshold(double t) {
		this.threshold_d = t;
	}

	public double getThreshold() {
		return this.threshold_d;
	}

	public int getMaxIterations() {
		return (int) (getNumOfExperiments() * getThreshold());
	}

	public void setNumOfExperiments(int max) {
		this.numExperiments_d = max;
	}

	public int getNumOfExperiments() {
		return this.numExperiments_d;
	}

	public void setPopSize(int psz) {
		this.popSize_d = psz;
	}

	public int getPopSize() {
		return this.popSize_d;
	}

	public void setBestObjectiveFunctionValue(double v) {
		this.bestOFValue_d = v;
	}

	public double getBestObjectiveFunctionValue() {
		return this.bestOFValue_d;
	}
}

/*
 * Location: C:\Users\Joon\Desktop\Bunch-3.5\
 * 
 * Qualified Name: bunch.GenericClusteringMethod
 * 
 * JD-Core Version: 0.7.0.1
 */