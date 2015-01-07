package bunch;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface BunchCliMsg
  extends Remote
{
  public abstract boolean recvMessage(String paramString, byte[] paramArrayOfByte)
    throws RemoteException;
}


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchCliMsg
 * JD-Core Version:    0.7.0.1
 */