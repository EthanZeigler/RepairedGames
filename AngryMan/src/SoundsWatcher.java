public abstract interface SoundsWatcher
{
  public static final int STOPPED = 0;
  public static final int REPLAYED = 1;
  
  public abstract void atSequenceEnd(String paramString, int paramInt);
}


/* Location:              /home/ethan/Desktop/AngryMan.jar!/SoundsWatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */