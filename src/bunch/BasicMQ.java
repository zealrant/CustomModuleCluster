package bunch;

public class BasicMQ
  implements ObjectiveFunctionCalculator
{
  private Graph graph_d;
  private static int[][] clusterMatrix_d = (int[][])null;
  private Node[] nodes_x;
  private int[] clusters_x;
  private int numberOfNodes_d;
  
  public void init(Graph g)
  {
    this.graph_d = g;
    this.numberOfNodes_d = g.getNumberOfNodes();
    this.nodes_x = g.getNodes();
    this.clusters_x = g.getClusters();
    if (clusterMatrix_d == null) {
      clusterMatrix_d = new int[this.numberOfNodes_d][this.numberOfNodes_d + 1];
    }
    for (int i = 0; i < clusterMatrix_d.length; i++) {
      clusterMatrix_d[i][0] = 0;
    }
  }
  
  public double calculate(Cluster c)
  {
    this.graph_d.setClusters(c.getClusterVector());
    init(c.getGraph());
    calculate();
    return this.graph_d.getObjectiveFunctionValue();
  }
  
  public void calculate()
  {
    int k = 0;
    double intra = 0.0D;
    double inter = 0.0D;
    if (clusterMatrix_d.length != this.numberOfNodes_d) {
      clusterMatrix_d = (int[][])null;
    }
    if (clusterMatrix_d == null) {
      clusterMatrix_d = new int[this.numberOfNodes_d][this.numberOfNodes_d + 1];
    }
    for (int i = 0; i < this.numberOfNodes_d; i++)
    {
      clusterMatrix_d[i][0] = 0;
      this.nodes_x[i].cluster = -1;
    }
    int pos = 0;
    for (int i = 0; i < this.numberOfNodes_d; i++)
    {
      int numCluster = this.clusters_x[i]; int 
        tmp123_122 = 0; int[] tmp123_121 = clusterMatrix_d[numCluster]; int tmp127_126 = (tmp123_121[tmp123_122] + 1);tmp123_121[tmp123_122] = tmp127_126;clusterMatrix_d[numCluster][tmp127_126] = i;
      this.nodes_x[i].cluster = numCluster;
    }
    for (int i = 0; i < clusterMatrix_d.length; i++) {
      if (clusterMatrix_d[i][0] > 0)
      {
        int[] clust = clusterMatrix_d[i];
        
        intra += calculateIntradependenciesValue(clust, i);
        k++;
        for (int j = i + 1; j < clusterMatrix_d.length; j++) {
          if (clusterMatrix_d[j][0] > 0) {
            inter += calculateInterdependenciesValue(clust, clusterMatrix_d[j], i, j);
          }
        }
      }
    }
    if (k == 0)
    {
      intra = 0.0D;
      inter = 0.0D;
    }
    else if (k == 1)
    {
      inter = 0.0D;
    }
    else
    {
      intra /= k;
      inter /= k * (k - 1) / 2.0D;
    }
    this.graph_d.setIntradependenciesValue(intra);
    this.graph_d.setInterdependenciesValue(inter);
    this.graph_d.setObjectiveFunctionValue(intra - inter);
  }
  
  public double calculateIntradependenciesValue(int[] c, int numCluster)
  {
    double intradep = 0.0D;
    int k = 0;
    for (int i = 1; i <= c[0]; i++)
    {
      Node node = this.nodes_x[c[i]];
      k++;
      int[] c2 = node.dependencies;
      if (c2 != null) {
        for (int j = 0; j < c2.length; j++) {
          if (this.nodes_x[c2[j]].cluster == numCluster) {
            intradep += 1.0D;
          }
        }
      }
    }
    if (k == 0) {
      k = 1;
    }
    k *= k;
    double[] d = new double[1];
    d[0] = new Double(intradep / k);
    return intradep / k;
  }
  
  public double calculateInterdependenciesValue(int[] c1, int[] c2, int nc1, int nc2)
  {
    double interdep = 0.0D;
    for (int i = 1; i <= c1[0]; i++)
    {
      int[] ca = this.nodes_x[c1[i]].dependencies;
      if (ca != null) {
        for (int j = 0; j < ca.length; j++) {
          if (this.nodes_x[ca[j]].cluster == nc2) {
            interdep += 1.0D;
          }
        }
      }
    }
    for (int i = 1; i <= c2[0]; i++)
    {
      int[] ca = this.nodes_x[c2[i]].dependencies;
      if (ca != null) {
        for (int j = 0; j < ca.length; j++) {
          if (this.nodes_x[ca[j]].cluster == nc1) {
            interdep += 1.0D;
          }
        }
      }
    }
    interdep /= 2.0D * c1[0] * c2[0];
    return interdep;
  }
}
