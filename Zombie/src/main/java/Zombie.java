import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Zombie extends JFrame implements KeyListener, MouseListener, MouseMotionListener, Runnable
{
	private static final long serialVersionUID = 1L; //Default.
	Random generate = new Random();

	Image marioImg, zombieImg, bullet,fivePts,heart,win,lose;
	boolean left=false,right=true,shot=false,shotStart=false,SIP=false;
	int xPosition=550, yPosition=550, pressedKey, enemyX=770,enemyY=770,bulletX,bulletY,zombieHP=3,level=1,zombiesKilled=0,playerHP=5,score=0; 
	boolean[] zombie = new boolean[10];

	Enemy enemy;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	int marioX, marioY;


	//	mouseX=e.getX();
	//		mouseY=e.getY();
	//pressedKey=e.getKeyCode();
	//if(pressedKey==KeyEvent.VK_UP)
	//	mouseY-=5;
	protected Thread animator = null;


    public static void main(String[] args) {
        Zombie game = new Zombie();
        game.init();
        game.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        game.start();
        game.setVisible(true);
    }

	public void init() 
	{
		setFocusable(true);
		for(int count=0;count<=9;count++)
			zombie[count]=true;
		setSize(1080,720);
		//		backBuffer = createImage(1080,720); 
		//		g=(Graphics2D)backBuffer.getGraphics();

		addKeyListener(this); 
		addMouseListener(this);
		addMouseMotionListener(this);
		marioImg = getImage("mario.png");
		zombieImg = getImage("zombie.png");
		bullet= getImage("bullet.png");
		heart= getImage("heart.png");
		win= getImage("win.png");
		lose = getImage("lose.png");
		marioX=100;
		marioY=100;
	}	

	public void paint(Graphics g) 
	{	
		update(g);			
	}
	public void update(Graphics g)
	{
		BufferedImage offscreen = new BufferedImage(1080,720, BufferedImage.TYPE_3BYTE_BGR);
        Graphics buff = offscreen.getGraphics();
		if((playerHP==0)||(level==5))
			endGame(buff);
		else
		{
			background(buff);
			drawLives(buff);
			drawMario(buff);
			zombieSpawn();
			moveZombie(buff);
			drawBullet(buff);
			buff.setColor(Color.white);
			buff.drawString(" LEVEL "+level, 300,30);
			buff.drawString("Score: "+score, 200, 30);
		}

        g.drawImage(offscreen, 0, 20, null);
	}

	public void endGame(Graphics g)
	{
		if(playerHP==0)
		{
			g.setColor(Color.black);
			g.fillRect(0, 0, 1080, 720);
			g.drawImage(lose, 100,300,this);
			g.setColor(Color.white);
			g.drawString("Final Score: "+score, 200, 200);
		}
		if(level==5)
		{
			g.setColor(Color.black);
			g.fillRect(0, 0, 1080, 720);
			g.drawImage(win, 100,300,this);
			g.setColor(Color.white);
			g.drawString(" Final Score: "+score, 200, 200);
		}

	}

	public void drawLives(Graphics g)
	{
		for(int num=1;num<=playerHP;num++)
			g.drawImage(heart,num*30,10,this);

	}

	public void background(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, 1080, 720);



	}

	public void drawMario(Graphics g)
	{
		g.drawImage(marioImg, xPosition, yPosition, this);
	}
	public void zombieCollision(int position)
	{

		if ((xPosition-20<= enemies.get(position).xPos) && (xPosition+74 >= enemies.get(position).xPos) && (yPosition-35 <= enemies.get(position).yPos) && (yPosition+89 >= enemies.get(position).yPos))
		{
			enemies.get(position).isHit=true;
			playerHP--;
			if(level==1)
				score-=15;
			else if(level==2)
				score-=10;
			else if(level==3)
				score-=5;
			else
				score-=1;
		//	System.out.println("Score : "+score);
		}

	}
	public void zombieShot(Graphics g)
	{

		if (enemies.size() > 0)
		{
			if(right==true)
			{
				if((bulletX+20>enemies.get(0).xPos) && (bulletY>=enemies.get(0).yPos) && (bulletY<=enemies.get(0).yPos+41))
				{
					//System.out.println("Test");
					zombieHP--;
					shot=false;
					SIP=false;
				}
			}
			else if(left==true)
			{
				if((bulletX<enemies.get(0).xPos+41) && (bulletY>=enemies.get(0).yPos) && (bulletY<=enemies.get(0).yPos+41))
				{
					zombieHP--;
					shot=false;
					SIP=false;
				}
			}
			if(zombieHP==0)
			{
				enemies.remove(0);
				zombiesKilled++;
				if(level==1)
					score+=5;
				else if(level==2)
					score+=10;
				else if(level==3)
					score+=15;
				else
					score+=25;
				if(zombiesKilled%10==0)
				{
					level=(zombiesKilled/10)+1;
				//	System.out.println("++++++++++++++++ LEVEL "+level+" ++++++++++++++++++");
				}
				//System.out.println("Score : "+score);
			}
		}

	}

	public void moveZombie(Graphics g)
	{	
		for (int x=0; x<enemies.size(); x++)
		{
			zombieCollision(x);

			if (enemies.get(x).isHit == true)
				enemies.remove(x);

			else
			{
				enemies.get(x).move(marioY,marioX,level);
				g.drawImage(zombieImg, enemies.get(x).xPos, enemies.get(x).yPos, this);
				//g.setColor(Color.magenta);
				//g.drawRect(enemies.get(x).xPos+2,enemies.get(x).yPos+1, 40, 40);

			}

		}
	}

	public void zombieSpawn()
	{
		if (enemies.size() < 1)
		{
			Enemy enemy = new Enemy();
			enemies.add(enemy);
			if((level==1)||(level==2))
				zombieHP=3;
			else if((level==3)||(level==4))
				zombieHP=5;


		}
	}
	public void drawBullet(Graphics g)
	{
		if(shotStart==true)
		{
			g.drawImage(bullet, marioX, marioY, this);	
			bulletX=marioX;
			bulletY=marioY;
			shotStart=false;
		}
		else if((shotStart==false)&&(shot==true))
		{	
			g.drawImage(bullet, bulletX, bulletY, this);
			if(right==true)
				bulletX+=5;
			else if(left=true)
				bulletX-=5;
			if((bulletX>1080)||(bulletX<0))
			{
				shot=false;
				SIP=false;
			}	
			zombieShot(g);
		}

	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent e) 
	{
		pressedKey=e.getKeyCode();
		if(pressedKey==KeyEvent.VK_UP)//&&(yPosition>30))
		{
			yPosition-=15;
			if(yPosition<-80)
				yPosition=720;
		}
		if(pressedKey==KeyEvent.VK_DOWN)//&&(yPosition<600))
		{
			yPosition+=15;
			if(yPosition>720)
				yPosition=-80;
		}
		if(pressedKey==KeyEvent.VK_RIGHT)//&&(xPosition<1000))
		{
			xPosition+=15;
			if(shot==false)
			{
				right=true;
				left=false;
			}
			if(xPosition>1080)
				xPosition=0;
		}
		if(pressedKey==KeyEvent.VK_LEFT)//&&(xPosition>-75))
		{
			xPosition-=15;
			if(shot==false)
			{
				left=true;
				right=false;
			}
			if(xPosition<-75)
				xPosition=1080;
		}
		if(pressedKey==KeyEvent.VK_S)
		{
			if(SIP==false)
			{
				shotStart=true;
				shot=true;
			}
			SIP=true;
		}
		marioX=xPosition+50;
		marioY=yPosition+50;
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void run()
	{
		while(Thread.currentThread() == animator)
		{
			try
			{
				Thread.sleep(16);
				repaint();
			}
			catch(Exception e)
			{ }
		}
	}
	public void start()
	{
		if(animator == null)
		{
			animator = new Thread(this);
			animator.start();
		}
	}

	public void stop()
	{
		animator = null;
	}


    private Image getImage(String loc) {
        return new ImageIcon(getClass().getResource("/" + loc)).getImage();
    }


}