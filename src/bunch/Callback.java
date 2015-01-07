package bunch;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface Callback
  extends Remote
{
  public abstract byte[] callFromServer(String paramString)
    throws RemoteException;
  
  public abstract byte[] callFromServerWithObj(String paramString, byte[] paramArrayOfByte)
    throws RemoteException;
  
  public abstract boolean bCallFromServerWithObj(String paramString, byte[] paramArrayOfByte)
    throws RemoteException;
  
  public abstract boolean bCallFromServer(String paramString)
    throws RemoteException;
}


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.Callback
 * JD-Core Version:    0.7.0.1
 */