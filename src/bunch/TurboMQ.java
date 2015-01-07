package bunch;

public class TurboMQ implements ObjectiveFunctionCalculator {
	private Graph graph_d;
	private static int[][] clusterMatrix_d = (int[][]) null;
	private Node[] nodes_x;
	private int[] clusters_x;
	private int numberOfNodes_d;

	public void init(Graph g) {
		this.graph_d = g;
		this.numberOfNodes_d = g.getNumberOfNodes();
		this.nodes_x = g.getNodes();
		this.clusters_x = g.getClusters();
		clusterMatrix_d = new int[this.numberOfNodes_d][this.numberOfNodes_d + 1];
		if (clusterMatrix_d == null) {
			clusterMatrix_d = new int[this.numberOfNodes_d][this.numberOfNodes_d + 1];
		}
		for (int i = 0; i < clusterMatrix_d.length; i++) {
			clusterMatrix_d[i][0] = 0;
		}
	}

	public double calculate(Cluster c) {
		this.graph_d.setClusters(c.getClusterVector());
		calculate();
		return this.graph_d.getObjectiveFunctionValue();
	}

	public void calculate() {
		int k = 0;
		double intra = 0.0D;
		double inter = 0.0D;
		double objTalley = 0.0D;

		this.clusters_x = this.graph_d.getClusters();
		if (clusterMatrix_d.length != this.numberOfNodes_d) {
			clusterMatrix_d = (int[][]) null;
		}
		if (clusterMatrix_d == null) {
			clusterMatrix_d = new int[this.numberOfNodes_d][this.numberOfNodes_d + 1];
		}
		for (int i = 0; i < this.numberOfNodes_d; i++) {
			clusterMatrix_d[i][0] = 0;
			this.nodes_x[i].cluster = -1;
		}
		int pos = 0;
		for (int i = 0; i < this.numberOfNodes_d; i++) {
			int numCluster = this.clusters_x[i];
			int tmp137_136 = 0;
			int[] tmp137_135 = clusterMatrix_d[numCluster];
			int tmp141_140 = (tmp137_135[tmp137_136] + 1);
			tmp137_135[tmp137_136] = tmp141_140;
			clusterMatrix_d[numCluster][tmp141_140] = i;
			this.nodes_x[i].cluster = numCluster;
		}
		double dDebug = this.graph_d.getObjectiveFunctionValue();
		for (int i = 0; i < clusterMatrix_d.length; i++) {
			if (clusterMatrix_d[i][0] > 0) {
				int[] clust = clusterMatrix_d[i];
				objTalley += calculateIntradependenciesValue(clust, i);
				k++;
			}
		}
		this.graph_d.setIntradependenciesValue(0.0D);
		this.graph_d.setInterdependenciesValue(0.0D);
		this.graph_d.setObjectiveFunctionValue(objTalley);
	}

	public double calculateIntradependenciesValue(int[] c, int numCluster) {
		double intradep = 0.0D;
		double intraEdges = 0.0D;
		double interEdges = 0.0D;
		double exitEdges = 0.0D;
		int k = 0;

		String cstr = "";
		for (int i = 1; i <= c[0]; i++) {
			if (this.nodes_x[c[i]].cluster == numCluster) {
				cstr = cstr + this.nodes_x[c[i]].getName() + " ";
			}
		}
		for (int i = 1; i <= c[0]; i++) {
			Node node = this.nodes_x[c[i]];
			k++;
			int[] c2 = node.dependencies;
			int[] w = node.weights;
			if (c2 != null) {
				for (int j = 0; j < c2.length; j++) {
					if (this.nodes_x[c2[j]].cluster == numCluster) {
						intradep += w[j];
						intraEdges += 1.0D;
					} else {
						exitEdges += w[j];
						interEdges += 1.0D;
					}
				}
			}
			int[] c2b = node.backEdges;
			int[] wb = node.beWeights;
			if (c2b != null) {
				for (int j = 0; j < c2b.length; j++) {
					if (this.nodes_x[c2b[j]].cluster != numCluster) {
						exitEdges += wb[j];
						interEdges += 1.0D;
					}
				}
			}
		}
		double objValue = 0.0D;
		if (exitEdges + intradep == 0.0D) {
			objValue = 0.0D;
		} else {
			objValue = intradep / (intradep + 0.5D * exitEdges);
		}
		return objValue;
	}

	public double calculateInterdependenciesValue(int[] c1, int[] c2, int nc1,
			int nc2) {
		double interdep = 0.0D;
		for (int i = 1; i <= c1[0]; i++) {
			int[] ca = this.nodes_x[c1[i]].dependencies;
			int[] w = this.nodes_x[c1[i]].weights;
			if (ca != null) {
				for (int j = 0; j < ca.length; j++) {
					if (this.nodes_x[ca[j]].cluster == nc2) {
						interdep += w[j];
					}
				}
			}
		}
		for (int i = 1; i <= c2[0]; i++) {
			int[] ca = this.nodes_x[c2[i]].dependencies;
			int[] w = this.nodes_x[c2[i]].weights;
			if (ca != null) {
				for (int j = 0; j < ca.length; j++) {
					if (this.nodes_x[ca[j]].cluster == nc1) {
						interdep += w[j];
					}
				}
			}
		}
		interdep /= 2.0D * c1[0] * c2[0];
		return interdep;
	}
}

/*
 * Location: C:\Users\Joon\Desktop\Bunch-3.5\
 * 
 * Qualified Name: bunch.TurboMQ
 * 
 * JD-Core Version: 0.7.0.1
 */