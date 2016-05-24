import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;








public class AngryManFrame
  extends JFrame
  implements WindowListener
{
  private static final long serialVersionUID = 1L;
  private AngryManPanel ap;
  
  public AngryManFrame(long period)
  {
    super("AngryMan");
    
    Container c = getContentPane();
    ap = new AngryManPanel(this, period);
    c.add(ap, "Center");
    
    addWindowListener(this);
    pack();
    setResizable(false);
    setVisible(true);
  }
  


  public void windowActivated(WindowEvent evt) { ap.resumeGame(); }
  
  public void windowDeactivated(WindowEvent evt) { ap.pauseGame(); }
  

  public void windowDeiconified(WindowEvent evt) { ap.resumeGame(); }
  
  public void windowIconified(WindowEvent evt) { ap.pauseGame(); }
  
  public void windowClosing(WindowEvent evt)
  {
    ap.stopGame();
  }
  
  public void windowClosed(WindowEvent evt) {}
  
  public void windowOpened(WindowEvent evt) {}
}


/* Location:              /home/ethan/Desktop/AngryMan.jar!/AngryManFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */