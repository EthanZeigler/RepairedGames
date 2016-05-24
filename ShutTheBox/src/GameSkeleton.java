import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

public abstract class GameSkeleton extends JFrame implements KeyListener, MouseListener, MouseMotionListener, Runnable
{
	private Thread animator = null;

	
	public void init()
	{
		
	}
	
	public void start()
	{
		if(animator == null) {
            animator = new Thread(this);
            animator.start();
        }
	}
	
	public void stop()
	{
		animator = null;
	}
	
	public void run()
	{
		while(Thread.currentThread() == animator)
		{
			try
			{
				Thread.sleep(33);
				repaint();
			}
			catch(Exception e)
			{ }
		}
	}
	
	public abstract void update(Graphics g);
	
	public abstract void paint(Graphics g);

	@Override
	public abstract void mouseDragged(MouseEvent e);

	@Override
	public abstract void mouseMoved(MouseEvent e);

	@Override
	public abstract void mouseClicked(MouseEvent e);

	@Override
	public abstract void mousePressed(MouseEvent e);

	@Override
	public abstract void mouseReleased(MouseEvent e);

	@Override
	public abstract void mouseEntered(MouseEvent e);

	@Override
	public abstract void mouseExited(MouseEvent e);

	@Override
	public abstract void keyTyped(KeyEvent e);

	@Override
	public abstract void keyPressed(KeyEvent e);
	

	@Override
	public abstract void keyReleased(KeyEvent e);

	public void update() 
	{
	
		
	}
}