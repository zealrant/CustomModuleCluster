package bunch.gxl.proxy;

public abstract interface IMDGtoGXL
{
  public abstract boolean convert();
  
  public abstract void setOptions(String paramString1, String paramString2);
  
  public abstract void setOptions(String paramString1, String paramString2, boolean paramBoolean);
  
  public abstract void setOptions(String paramString1, String paramString2, String paramString3);
  
  public abstract void setOptions(String paramString1, String paramString2, String paramString3, boolean paramBoolean);
}


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.gxl.proxy.IMDGtoGXL
 * JD-Core Version:    0.7.0.1
 */