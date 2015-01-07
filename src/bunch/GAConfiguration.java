package bunch;

import java.util.Enumeration;

public class GAConfiguration extends Configuration {
	GAMethod method_d;
	double mutationThreshold_d;
	double crossoverThreshold_d;
	GAMethodFactory methodFactory_d;

	public GAConfiguration() {
		this.methodFactory_d = new GAMethodFactory();
	}

	public GAConfiguration(Graph g) {
		init(g);
	}

	public void init(Graph g) {
		int nodes = g.getNumberOfNodes();
		setNumOfIterations(nodes * 100);
		setPopulationSize(nodes * 10);
		setCrossoverThreshold(0.6D + 0.2D * (getPopulationSize() / 1000));
		int bitsize = 0;
		for (bitsize = 0; bitsize < nodes; bitsize++) {
			double d = Math.pow(2.0D, bitsize);
			if (d > nodes) {
				break;
			}
		}
		setMutationThreshold(0.005D * bitsize);
		setMethod(this.methodFactory_d.getMethod((String) this.methodFactory_d
				.getAvailableItems().nextElement()));
	}

	public GAMethodFactory getMethodFactory() {
		return this.methodFactory_d;
	}

	public GAMethod getMethod() {
		return this.method_d;
	}

	public void setMethod(GAMethod m) {
		this.method_d = m;
	}

	public void setMethod(String m) {
		setMethod(this.methodFactory_d.getMethod(m));
	}

	public void setMutationThreshold(double m) {
		this.mutationThreshold_d = m;
	}

	public double getMutationThreshold() {
		return this.mutationThreshold_d;
	}

	public void setCrossoverThreshold(double c) {
		this.crossoverThreshold_d = c;
	}

	public double getCrossoverThreshold() {
		return this.crossoverThreshold_d;
	}
}

/*
 * Location: C:\Users\Joon\Desktop\Bunch-3.5\
 * 
 * Qualified Name: bunch.GAConfiguration
 * 
 * JD-Core Version: 0.7.0.1
 */