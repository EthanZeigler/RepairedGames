import java.awt.image.BufferedImage;
import java.io.PrintStream;











































public class ImagesPlayer
{
  private String imName;
  private boolean isRepeating;
  private boolean ticksIgnored;
  private ImagesLoader imsLoader;
  private int animPeriod;
  private long animTotalTime;
  private int showPeriod;
  private double seqDuration;
  private int numImages;
  private int imPosition;
  private ImagesPlayerWatcher watcher = null;
  


  public ImagesPlayer(String nm, int ap, double d, boolean isr, ImagesLoader il)
  {
    imName = nm;
    animPeriod = ap;
    seqDuration = d;
    isRepeating = isr;
    imsLoader = il;
    
    animTotalTime = 0L;
    
    if (seqDuration < 0.5D) {
      System.out.println("Warning: minimum sequence duration is 0.5 sec.");
      seqDuration = 0.5D;
    }
    
    if (!imsLoader.isLoaded(imName)) {
      System.out.println(imName + " is not known by the ImagesLoader");
      numImages = 0;
      imPosition = -1;
      ticksIgnored = true;
    }
    else {
      numImages = imsLoader.numImages(imName);
      imPosition = 0;
      ticksIgnored = false;
      showPeriod = ((int)(1000.0D * seqDuration / numImages));
    }
  }
  



  public void updateTick()
  {
    if (!ticksIgnored)
    {
      animTotalTime = (long) ((animTotalTime + animPeriod) % (1000.0D * seqDuration));
      

      imPosition = ((int)(animTotalTime / showPeriod));
      if ((imPosition == numImages - 1) && (!isRepeating)) {
        ticksIgnored = true;
        if (watcher != null) {
          watcher.sequenceEnded(imName);
        }
      }
    }
  }
  
  public BufferedImage getCurrentImage()
  {
    if (numImages != 0) {
      return imsLoader.getImage(imName, imPosition);
    }
    return null;
  }
  
  public int getCurrentPosition()
  {
    return imPosition;
  }
  
  public void setWatcher(ImagesPlayerWatcher w)
  {
    watcher = w;
  }
  

  public void stop()
  {
    ticksIgnored = true;
  }
  
  public boolean isStopped() {
    return ticksIgnored;
  }
  
  public boolean atSequenceEnd()
  {
    return (imPosition == numImages - 1) && (!isRepeating);
  }
  




  public void restartAt(int imPosn)
  {
    if (numImages != 0) {
      if ((imPosn < 0) || (imPosn > numImages - 1)) {
        System.out.println("Out of range restart, starting at 0");
        imPosn = 0;
      }
      
      imPosition = imPosn;
      
      animTotalTime = (imPosition * showPeriod);
      ticksIgnored = false;
    }
  }
  


  public void resume()
  {
    if (numImages != 0) {
      ticksIgnored = false;
    }
  }
}


/* Location:              /home/ethan/Desktop/AngryMan.jar!/ImagesPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */