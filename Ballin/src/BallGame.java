import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.*;

@SuppressWarnings("serial")
public class BallGame extends JFrame implements Runnable, MouseMotionListener,MouseListener{

	double xpos,ypos;//circle coordinates
	double xcen,ycen;//mouse position
	double xsqu,ysqu;//square coordinates
	double xcircle, yplus,yminus, addvalue;//values for checking circle boundaries
	double vplus, vmax=1, vmin=.25;//speed up ball after all are spawned
	double counter=0;
	double attraction=0.01;
	double drag=0.01;
	double [][]randomcircles=new double[8][25];
	boolean GameRunning=true,PlayAgain=true;

    /*
	 * 0=x-position
	 * 1=y-position
	 * 2=diameter
	 * 3=x-velocity
	 * 4=y-velocity
	 * 5=radius
	 * 6=x-center
	 * 7=y-center
	 */
	boolean touched=false;//is true when mouse not touching square
	boolean circles=false;//true when circles are generated
	int width,height;
	int score=-10;
	int savedscore=0;
	Image offscreenImage;
	Graphics offscr;
	Random r;
	Thread t;
	BallGameMathMethods d;
	boolean inBounds=true;
	boolean onScreen=true;
	boolean playNormal=false, playSeeker=false;

	Image title;
	boolean hasStarted=false;

	public static void main(String[] args)
	{
		BallGame game = new BallGame();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            game.init();
            game.setVisible(true);
            game.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }).start();
	}
	public BallGame()
	{
		r=new Random();
		t=new Thread(this);
		d=new BallGameMathMethods();
		t.start();
	}

	public void init()
	{
		addMouseMotionListener(this);
		addMouseListener(this);
		//resize(500, 500);
		setSize(500, 500);
		width = getSize().width;
		height = getSize().height;
		title=getImage("Title.jpg");

		offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		offscr = offscreenImage.getGraphics();
	}

	public void checkBounds()
	{

		if ((xcen+10 <= 490) && (ycen+10 <= 490) && (xcen-10 >= 10) && (ycen-10 >= 10))
		{
			System.out.println("In");
			inBounds=true;
		}
		else if (!onScreen)
			inBounds=false;
		else
		{
			inBounds=false;
			System.out.println("Out");
		}
	}

	public void paint(Graphics g)
	{
		//checkBounds();

		// Game has begun - option selected
		if (hasStarted)
		{
			if (inBounds)
			{
				super.paint(offscr);

				offscr.setColor(getBackground());
				offscr.fillRect(0, 0, width, height);
				offscr.setColor(Color.GREEN);
				offscr.fillOval((int)xpos, (int)ypos, 20, 20);
				if(playNormal==true){
					normal();
				}
				if(playSeeker==true) {
					seeker();
				}
				System.out.println(randomcircles[3][0]);
				offscr.drawString("Score: "+score, 430, 500);
				offscr.setColor(Color.BLACK);
				offscr.drawRect(0, 0, 499, 499);
				g.drawImage(offscreenImage, 0, 0, this);
				if(GameRunning==false)
				{
					offscr.setColor(getBackground());
					offscr.fillRect(0, 0, width, height);
					offscr.setColor(Color.BLACK);
					offscr.drawString("Game over! Final score: "+savedscore, 300, 300);
					offscr.drawRect(300, 310, 80, 20);
					offscr.drawString("Play again!", 303, 323);
					offscr.setColor(Color.BLACK);
					offscr.drawRect(0, 0, 499, 499);
					g.drawImage(offscreenImage, 0, 0, this);
				}
			}
			else
			{
				//			offscr.drawString("Out of Bounds", 250, 250);
				//			g.drawImage(offscreenImage, 0, 0, this);
				GameRunning=false;
				offscr.setColor(getBackground());
				offscr.fillRect(0, 0, width, height);
				offscr.setColor(Color.BLACK);
				offscr.drawString("Game over!", 300, 285);
				offscr.drawString("(Don't cheat next time!)", 300, 300);
				offscr.drawRect(300, 310, 80, 20);
				offscr.drawString("Play again!", 303, 323);
				offscr.setColor(Color.BLACK);
				offscr.drawRect(0, 0, 499, 499);
				g.drawImage(offscreenImage, 0, 0, this);
			}
		}

		else
		{
			// Game has not started yet.
			g.drawImage(title, 0, 0, this);
			//g.drawString("X: "+xpos, 350, 490);
			//g.drawString("Y: "+ypos, 425, 490);
		}
	}
	public void seeker()
	{
		offscr.setColor(Color.RED);
		System.out.println("Keel");
		initialSpawn();
		if(circles==true)
		{
			double[]fx=new double[25];
			double[]fy=new double[25];
			for(int x=0;x<counter;x++)
			{
				if (!inBounds)
					break;
				//bounce off of walls
				if(Math.abs(randomcircles[6][x]+randomcircles[3][x])>(500-randomcircles[5][x]))
					randomcircles[3][x]=-randomcircles[3][x];
				if(Math.abs(randomcircles[6][x]+randomcircles[3][x])<0+randomcircles[5][x])
					randomcircles[3][x]=-randomcircles[3][x];
				if(Math.abs(randomcircles[7][x]+randomcircles[4][x])>500-randomcircles[5][x])
					randomcircles[4][x]=-randomcircles[4][x];
				if(Math.abs(randomcircles[7][x]+randomcircles[4][x])<0+randomcircles[5][x])
					randomcircles[4][x]=-randomcircles[4][x];
				offscr.fillOval((int)randomcircles[0][x], (int)randomcircles[1][x], (int)randomcircles[2][x], (int)randomcircles[2][x]);
				//force

				//				double dx = getMousePosition().x - randomcircles[0][x];
				//				double dy = getMousePosition().y - randomcircles[1][x];
				double dx = xpos - randomcircles[0][x];
				double dy = ypos - randomcircles[1][x];
				
				fx[x] += attraction * dx;
				fy[x] += attraction * dy;
				//drag
				fx[x] += -drag * randomcircles[3][x];
				fy[x] += -drag * randomcircles[4][x];
				//update velocity
				randomcircles[3][x]+=fx[x]*.05;
				randomcircles[4][x]+=fy[x]*.05;
				//updates position
				randomcircles[0][x]+=randomcircles[3][x];
				randomcircles[1][x]+=randomcircles[4][x];
				//adjusts center coordinates
				randomcircles[6][x]=randomcircles[0][x]+randomcircles[5][x];
				randomcircles[7][x]=randomcircles[1][x]+randomcircles[5][x];
				//
				collisions(x);
			}
		}
		offscr.setColor(Color.BLUE);
		offscr.fillRect((int)xsqu, (int)ysqu, 30, 30);
		xcircle=xpos;
		addvalue=20.000/100;
		for(int x=0;x<100;x++)
		{
			if (!inBounds)
				break;
			yplus=ycen+d.CirclePosition(xcen, xcircle, 10);
			yminus=ycen-d.CirclePosition(xcen, xcircle, 10);
			if(xcircle>xsqu)
			{
				if((yplus>ysqu&&yplus<ysqu+30)||(yminus>ysqu&&yminus<ysqu+30))
					touched=false;
				circles=true;
			}
			xsqu+=addvalue;
		}
	}
	public void collisions(int x)
	{
		for(int y=x+1;y<counter;y++)
		{
			if(d.CircleCollision(randomcircles[6][x], randomcircles[7][x], randomcircles[5][x], randomcircles[6][y], randomcircles[7][y], randomcircles[5][y])==true)
			{
				randomcircles[3][x]=-randomcircles[3][x]*1.25;
				randomcircles[4][x]=-randomcircles[4][x]*1.25;
				randomcircles[3][y]=-randomcircles[3][y]*1.25;
				randomcircles[4][y]=-randomcircles[4][y]*1.25;
				//updates position
				randomcircles[0][x]+=randomcircles[3][x];
				randomcircles[1][x]+=randomcircles[4][x];
				randomcircles[0][y]+=randomcircles[3][y];
				randomcircles[1][y]+=randomcircles[4][y];
				//adjusts center coordinates
				randomcircles[6][x]=randomcircles[0][x]+randomcircles[5][x];
				randomcircles[7][x]=randomcircles[1][x]+randomcircles[5][x];
				randomcircles[6][y]=randomcircles[0][y]+randomcircles[5][y];
				randomcircles[7][y]=randomcircles[1][y]+randomcircles[5][y];
				//
				randomcircles[3][x]-=Math.random()*PositiveNegative(randomcircles[3][x]);
				randomcircles[4][x]-=Math.random()*PositiveNegative(randomcircles[4][x]);
				randomcircles[3][y]-=Math.random()*PositiveNegative(randomcircles[3][y]);
				randomcircles[4][y]-=Math.random()*PositiveNegative(randomcircles[4][y]);
			}
			//System.out.println(d.CircleCollision(randomcircles[6][x], randomcircles[7][x], randomcircles[5][x], randomcircles[6][y], randomcircles[7][y], randomcircles[5][y]));
		}
	}
	public void initialSpawn()
	{
		if(touched==false)
		{
			xsqu=r.nextInt(400);
			ysqu=r.nextInt(400);
			score+=10;
			touched=true;
			if(counter<25&&circles==true)//sets initial values for circles
			{
				randomcircles[0][(int) counter]=r.nextInt(150)+50;
				randomcircles[1][(int) counter]=r.nextInt(150)+50;
				randomcircles[2][(int) counter]=r.nextInt(20)+20;
				randomcircles[3][(int) counter]=Math.random()*d.RandomSign();
				randomcircles[4][(int) counter]=Math.random()*d.RandomSign();
				randomcircles[5][(int) counter]=randomcircles[2][(int) counter]/2;
				randomcircles[6][(int) counter]=randomcircles[0][(int) counter]+randomcircles[5][(int) counter];
				randomcircles[7][(int) counter]=randomcircles[1][(int) counter]+randomcircles[5][(int) counter];
				while(PositionCheck((int)counter)==true||BallSpawn((int)counter)==true)
				{
					randomcircles[0][(int) counter]+=r.nextInt(150)+100;
					randomcircles[1][(int) counter]+=r.nextInt(150)+100;
					randomcircles[6][(int) counter]=randomcircles[0][(int) counter]+randomcircles[5][(int) counter];
					randomcircles[7][(int) counter]=randomcircles[1][(int) counter]+randomcircles[5][(int) counter];
				}
				counter++;
			}
			if (counter==25)//slowly increase speed of circles
			{
				vplus=Math.random()/2;
				for(int x=0;x<counter;x++)
				{
					if (Math.abs(randomcircles[3][x])<vmax&&randomcircles[3][x]>0)
						randomcircles[3][x]+=vplus;
					if (Math.abs(randomcircles[3][x])<vmax&&randomcircles[3][x]<0)
						randomcircles[3][x]-=vplus;
					if (Math.abs(randomcircles[4][x])<vmax&&randomcircles[4][x]>0)
						randomcircles[4][x]+=vplus;
					if (Math.abs(randomcircles[4][x])<vmax&&randomcircles[4][x]<0)
						randomcircles[4][x]-=vplus;
				}
			}
		}
	}
	public void normal()
	{
		offscr.setColor(Color.RED);
		initialSpawn();
		if(circles==true)//used to animate extra circles moving about
		{
			for (int x=0;x<counter;x++)
			{
				//bounce off of walls
				if(Math.abs(randomcircles[6][x]+randomcircles[3][x])>(500-randomcircles[5][x]))
					randomcircles[3][x]=-randomcircles[3][x];
				if(Math.abs(randomcircles[6][x]+randomcircles[3][x])<0+randomcircles[5][x])
					randomcircles[3][x]=-randomcircles[3][x];
				if(Math.abs(randomcircles[7][x]+randomcircles[4][x])>500-randomcircles[5][x])
					randomcircles[4][x]=-randomcircles[4][x];
				if(Math.abs(randomcircles[7][x]+randomcircles[4][x])<0+randomcircles[5][x])
					randomcircles[4][x]=-randomcircles[4][x];
				offscr.fillOval((int)randomcircles[0][x], (int)randomcircles[1][x], (int)randomcircles[2][x], (int)randomcircles[2][x]);
				//updates position
				randomcircles[0][x]+=randomcircles[3][x];
				randomcircles[1][x]+=randomcircles[4][x];
				//adjusts center coordinates
				randomcircles[6][x]=randomcircles[0][x]+randomcircles[5][x];
				randomcircles[7][x]=randomcircles[1][x]+randomcircles[5][x];
				//adds collision effect 
				collisions(x);
			}
		}	
		offscr.setColor(Color.BLUE);
		offscr.fillRect((int)xsqu, (int)ysqu, 30, 30);
		xcircle=xpos;
		addvalue=20.000/100;
		for(int x=0;x<100;x++)
		{
			yplus=ycen+d.CirclePosition(xcen, xcircle, 10);
			yminus=ycen-d.CirclePosition(xcen, xcircle, 10);
			if(xcircle>xsqu&&xcircle<xsqu+30)
			{
				if((yplus>ysqu&&yplus<ysqu+30)||(yminus>ysqu&&yminus<ysqu+30))
					touched=false;
				circles=true;
			}
			xcircle+=addvalue;
		}

	}
	public void update(Graphics g)
	{
		paint(g);
	}
	public void run()
	{
		// TODO Auto-generated method stub
		while(PlayAgain==true)
		{
			while(GameRunning==true)
			{
				SpeedCheck();
				SpeedCheckMin();
				MouseHit();
				repaint();
				try
				{
					Thread.sleep(2);
				}
				catch (InterruptedException e){};
			}
			if(GameRunning==false)
			{
				xpos=0;ypos=0;
				xcen=0;ycen=0;
				xsqu=0;ysqu=0;
				xcircle=0; yplus=0;yminus=0; addvalue=0;
				vplus=0;
				counter=0;
				score=-10;
				for(int x=0;x<8;x++)
				{
					for(int y=0;y<25;y++)
						randomcircles[x][y]=0;
				}
				circles=false;
				touched=false;
			}
		}	
	}
	public void MouseHit()
	{
		for(int x=0;x<counter;x++)
		{
			if(d.CircleCollision(randomcircles[6][x], randomcircles[7][x], randomcircles[5][x],xcen, ycen, 10))
			{
				savedscore=score;
				GameRunning=false;
			}
		}
	}
	public void SpeedCheck()
	{
		for(int x=0;x<counter;x++)
		{
			if(Math.abs(randomcircles[3][x])>vmax)
				randomcircles[3][x]*=.5;
			if(Math.abs(randomcircles[4][x])>vmax)
				randomcircles[4][x]*=.5;
		}
	}
	public void SpeedCheckMin()
	{
		for(int x=0;x<counter;x++)
		{
			if(Math.abs(randomcircles[3][x])<vmin)
				randomcircles[3][x]*=2.5;
			if(Math.abs(randomcircles[4][x])<vmin)
				randomcircles[4][x]*=2.5;
		}
	}
	public int PositiveNegative(double number)
	{
		if(number>=0)
			return 1;
		else
			return -1;
	}
	public boolean PositionCheck(int x)
	{
		if(d.CircleSpawnCollision(randomcircles[6][x], randomcircles[7][x], randomcircles[5][x], xcen, ycen, 10)==true)
		{
			//randomcircles[0][x]+=150;
			//randomcircles[1][x]+=150;
			return true;
		}	
		return false;
	}
	public boolean BallSpawn(int x)
	{
		for( int y=0;y<counter;y++)
		{
			if(d.CircleCollision(randomcircles[6][x], randomcircles[7][x], randomcircles[5][x], randomcircles[6][y], randomcircles[7][y], randomcircles[5][y])==true)
			{
				//randomcircles[0][x]=r.nextInt(200+100);
				//randomcircles[1][x]=r.nextInt(200+100);
				return true;
			}
			return false;
		}
		return false;
	}
	public void mouseDragged(MouseEvent ev) {
		// TODO Auto-generated method stub
		xpos=ev.getX()-10;
		ypos=ev.getY()-10;
		xcen=ev.getX();
		ycen=ev.getY();
		//repaint();
	}
	public void mouseMoved(MouseEvent ev) {
		// TODO Auto-generated method stub
		xpos=ev.getX()-10;
		ypos=ev.getY()-10;
		xcen=ev.getX();
		ycen=ev.getY();

		//repaint();
	}
	public void mouseClicked(MouseEvent ev)
	{
		// TODO Auto-generated method stub
		if (!hasStarted)
		{
			if ((ev.getX() >= 240 && ev.getX() <= 290) && (ev.getY() >= 310 && ev.getY() <= 350)) {
				playNormal = true;
			}
			else if ((ev.getX() >= 385 && ev.getX() <= 435) && (ev.getY() >= 310 && ev.getY() <= 350))
				playSeeker=true;
			
			hasStarted=true;
		}
		if(GameRunning==false)
		{
			if(ev.getX()>300&&ev.getX()<380&&ev.getY()>310&&ev.getY()<330)
			{
				GameRunning=true;
				System.out.println("clicked");
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
		onScreen=true;
		inBounds=true;
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent ev)
	{
		onScreen=false;
		inBounds=false;
		// TODO Auto-generated method stub
		//GameRunning=false;
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	private Image getImage(String loc) {
		return new ImageIcon(this.getClass().getResource("/" + loc)).getImage();
	}

}
