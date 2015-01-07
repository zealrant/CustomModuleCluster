/*  1:   */ package bunch;
/*  2:   */ 
/*  3:   */ import java.rmi.RemoteException;
/*  4:   */ import javax.rmi.PortableRemoteObject;
/*  5:   */ 
/*  6:   */ public class BunchCliMsgImpl
/*  7:   */   extends PortableRemoteObject
/*  8:   */   implements BunchCliMsg
/*  9:   */ {
/* 10:   */   public BunchCliMsgImpl()
/* 11:   */     throws RemoteException
/* 12:   */   {}
/* 13:   */   
/* 14:   */   public boolean recvMessage(String name, byte[] serializedClass)
/* 15:   */   {
/* 16:43 */     return true;
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.BunchCliMsgImpl
 * JD-Core Version:    0.7.0.1
 */