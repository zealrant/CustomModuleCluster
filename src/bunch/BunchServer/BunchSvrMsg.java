package bunch.BunchServer;

import bunch.Callback;
import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface BunchSvrMsg
  extends Remote
{
  public abstract boolean invokeMessage(String paramString, byte[] paramArrayOfByte)
    throws RemoteException;
  
  public abstract boolean registerCallback(Callback paramCallback)
    throws RemoteException;
  
  public abstract boolean doAction(String paramString)
    throws RemoteException;
}


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchServer.BunchSvrMsg
 * JD-Core Version:    0.7.0.1
 */