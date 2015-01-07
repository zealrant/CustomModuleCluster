package bunch.BunchServer;

import bunch.BunchPreferences;
import bunch.Configuration;
import bunch.Graph;
import java.io.Serializable;

public class DistribInit
  implements Serializable
{
  public String svrName;
  public int svrID;
  public Graph theGraph;
  public String clusteringTechnique;
  public String objFunction;
  public Configuration config;
  public BunchPreferences bp;
  public boolean adaptiveEnabled;
}


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchServer.DistribInit
 * JD-Core Version:    0.7.0.1
 */