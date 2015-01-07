package bunch;

import java.io.Serializable;

public abstract interface ObjectiveFunctionCalculator extends Serializable
{
  public abstract void init(Graph paramGraph);
  
  public abstract double calculate(Cluster paramCluster);
  
}


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.ObjectiveFunctionCalculator
 * JD-Core Version:    0.7.0.1
 */