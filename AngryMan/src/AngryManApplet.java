import javax.swing.*;


public class AngryManApplet
  extends JApplet
{
  private static final long serialVersionUID = 1L;
  private static final int DEFAULT_FPS = 30;
  
  public void init()
  {
    long period = 33L;
    new AngryManFrame(period * 1000000L);
  }

  public void destroy()
  {
    System.exit(0);
  }

  public static void main(String[] args) {
      long period = 33L;
      new AngryManFrame(period * 1000000L);
  }
}


/* Location:              /home/ethan/Desktop/AngryMan.jar!/AngryManApplet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */