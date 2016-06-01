/*AUTHOR: 		TOM MATSLIACH
 *WRITTEN: 		Senior Year of High School.
 *COURSE:		Independent Study Computer Science  
 *DESCRIPTION: 
 *	Basic Precision Shooting Game, 
 *  very satisfied with the object detection and efficiency.	
 *  
 *  When you have the privilege of time, 
 *  efficiency should be implied.
 */ 
package a.MainComponents;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioPermission;
import javax.swing.*;

import b.GameObjects.Apple;
import b.GameObjects.Level;
import b.GameObjects.Object;
import b.GameObjects.Coconut;
import com.ethanzeigler.jgamegui.audio.AudioClip;

public class BananasMain extends JFrame implements KeyListener, MouseListener, MouseMotionListener
{
	/*NOTE TO READER
	 * 
	 * Please respect the integrity of this code 
	 * and do not claim any parts of it as your own.
	 * 
	 * You may use it as a teaching tool or reference as 
	 * long as this source is properly acknowledged.
	 * 
	 * Also: Many aspects of this code are very 
	 * delicate and should not be modified. TREAD LIGHTLY.
	 *
	 * -Tom
	 *
	 * ANOTHER NOTE TO READER
	 * I, Ethan, migrated this game from Applet to Frame. To do so, I also added my sound API class.
	 * I apologize if I broke anything while completing this process.
	 * PS. Sorry about the ugly fix for getting the program to stop correctly...
	 *
	 */
	
	private static final long serialVersionUID = 1L; //Default for applet.

	Graphics2D g2d;
	Image backBuffer, image, image2; //Backbuffer for double buffering.
	AudioClip music, sound;
	Random r;
	ArrayList<Level> levels;    //Level components
	ArrayList<Object> objects; //Objects that react to the apples.
	ArrayList<Apple> apples;  //Useful when there are multiple apples in play simultaneously.
	LevelsSetUpCode SetUpLevels; //BluePrints of levels.
	ArrayList<Point2D.Double> path; //Path of the apple.

	//GENERAL
	boolean MainMenu, runGame, musicON, invisible, ultimate; 
	byte masterC=0, barSpeed, chimpSpeed, appleSpeed; 
	Point mouse; int pressedKey; 
	//LEVEL
	String objectsConfig, name; Point monk; boolean mMirror, limbMirror;
	byte bananasHit, objective, levelNum; int shotsLeft; 
	//Power, Aiming, Scaling
	boolean barActive, pInc;
	double power, angle;
	int pastPower, currentPower, h, w, scaleX;
	//APPLE
	Point2D.Double apple, velocity, origin;
	double rotation; 
	//OBJECT
	Point object, pivot; byte index; 
	boolean animal, specialPath, L, R, elephant, chimp, tailInc, coconut;
	//Reused:
	double i;
	/*NOTES
	 *The point of defrenciating between Pivot and Origin is to avoid restricting future objects with a shared limb-pivot/apple-origin location.
	 */

	public void init() 
	{
		System.out.println("Game Program Started");
		resize(1000,600); //25x15 squares (40p each)
		backBuffer = new BufferedImage(1000, 600, BufferedImage.TYPE_INT_ARGB);
        addWindowListener(new WindowAdapter() {
            // This is horribly ugly, but the main thread isn't disposing properly at the end of the game after JFrame migration
            // This was the fastest solution...
            // Ethan Zeigler
            @Override
            public void windowClosed(WindowEvent windowEvent) {
                System.exit(1);
            }
        });
		g2d=(Graphics2D)backBuffer.getGraphics();

		music= new AudioClip("Menu/Music.wav");
		music.playUntilStopped();

		r = new Random();
		levels = new ArrayList<Level>(); 
		SetUpLevels = new LevelsSetUpCode(levels);
		objects = new ArrayList<Object>();
		apples = new ArrayList<Apple>(); 
		path = new ArrayList<Point2D.Double>();
		addMouseMotionListener(this); 
		addMouseListener(this); 
		addKeyListener(this); 
		mouse = new Point();
		monk = new Point();
		object = new Point();
		pivot = new Point();
		apple = new Point2D.Double();
		velocity = new Point2D.Double();
		origin = new Point2D.Double();

		MainMenu=true; 
		musicON=true;
		barSpeed=3;
		chimpSpeed=3;
		appleSpeed=3;

		//ultimate=true;
		//LevelSetUp(); //needed as long as the program skips the Main Menu
	}	
	public void run() 
	{
	}
	public void paint(Graphics g) //Run through Update();
	{	
		update(g);			
	}
	public void update(Graphics g)
	{
		g.drawImage(backBuffer, 0, 0, this);
		if(runGame)
			GameSequence();
		else if(MainMenu)
			MainMenu();
		repaint();
	}

	public void mouseMoved(MouseEvent e) {
		mouse.x=e.getX(); mouse.y=e.getY();
	}
	public void mouseDragged(MouseEvent e) {
		mouse.x=e.getX(); mouse.y=e.getY();
	}
	public void mouseClicked(MouseEvent e)  //(MAIN MENU ACTIONS).
	{
		if(MainMenu)
		{
			i=(4.0/25)*mouse.x; //slope of the line.
			if(mouse.x>185 && mouse.x<690 && mouse.y>i+210 && mouse.y<i+270)
			{
				levelNum=0;
				MainMenu=false;
				runGame=true;
				LevelSetUp();
				JOptionPane.showMessageDialog(null, "Objective: Hit All of the Bananas." +
													"\n\nRed #: Apples you have left." +
													"\nBlue #: Current Power of throw." +
													"\nGrey #: Power of previous throw." +
													"\n\n*LEVEL NAME: Bottom left corner." +
													"\nCheck Game Options for all commands." +
													"\n(P- to return to Main Menu).", "DIRECTIONS", JOptionPane.PLAIN_MESSAGE);
			}
			else if(mouse.x>30 && mouse.x<530 && mouse.y>i+330 && mouse.y<i+370)
			{
				levelNum=-1;
				name=JOptionPane.showInputDialog(null, "Enter Level's Name: ", "Banana Split");
				for(int x=0; x<levels.size(); x++)
					if(name.equalsIgnoreCase(levels.get(x).getName()))
						levelNum=(byte)x;
				if(levelNum==-1)
					JOptionPane.showMessageDialog(null, "Incorrect Level Name", "ERROR", JOptionPane.ERROR_MESSAGE);	
				else
				{
					MainMenu=false;
					runGame=true;
					LevelSetUp();
				}
				KeyExited(null);
			}		
			else if(mouse.x>165 && mouse.x<665 && mouse.y>i+425 && mouse.y<i+475)
			{
				JOptionPane.showMessageDialog(null, "ALL KEYBOARD COMMANDS:" +
						"\n\nENTER - Reset current level." +
						"\nP - Return to Main Menu."+
						"\nM - Mute/Resume Music."+
						"\nI - Invisible Apple Mode."+
						"\n\nGame Speeds: (10 levels each)"+
						"\nPower: Q- reduce, W- increase."+
						"\nApple: A- reduce, S- increase."+
						"\nChimp: Z- reduce, X- increase."
						,"Game Options", JOptionPane.PLAIN_MESSAGE);

			}				
		}
	}
	public void keyPressed(KeyEvent e)	 //(Game Options)
	{
		pressedKey=e.getKeyCode();

		if(pressedKey==KeyEvent.VK_ENTER) //RESET
			LevelSetUp();
		else if(pressedKey==KeyEvent.VK_P) //Main Menu
		{
			MainMenu=true;
			runGame=false;
		}
		else if(pressedKey==KeyEvent.VK_M) //Music
		{
			musicON=!musicON;
			if(musicON)
				music.playUntilStopped();
			else
				music.stop();
		}
		else if(pressedKey==KeyEvent.VK_I)
			invisible=!invisible;
		//GAME SPEEDS:
		else if(pressedKey==KeyEvent.VK_Q) 
		{
			if(barSpeed<10)
				barSpeed++;
		}
		else if(pressedKey==KeyEvent.VK_W)
		{
			if(barSpeed>1)
				barSpeed--;	
		}
		else if(pressedKey==KeyEvent.VK_A)
		{
			if(appleSpeed>1)
				appleSpeed--;
		}
		else if(pressedKey==KeyEvent.VK_S)
		{
			if(appleSpeed<10)
				appleSpeed++;
		}
		else if(pressedKey==KeyEvent.VK_Z)
		{
			if(chimpSpeed<10)
				chimpSpeed++;
		}
		else if(pressedKey==KeyEvent.VK_X)
		{
			if(chimpSpeed>1)
				chimpSpeed--;
		}

		if(ultimate)
		{
			if(pressedKey==KeyEvent.VK_SPACE)
			{
				if(levelNum<8)
					levelNum++;
				LevelSetUp();

			}
			else if(pressedKey==KeyEvent.VK_BACK_SPACE)
			{	
				if(levelNum>0)
					levelNum--;
				LevelSetUp();
			}
			else if(pressedKey==KeyEvent.VK_T && apples.size()<40)
			{
				i=apples.size();
				for(int x=0; x!=i; x++)
					for(int angle=0; angle!=360; angle+=30)
					{
						sound=new AudioClip("Sounds/Porcupine.wav");
						sound.playOnce();
						velocity.x=1*(Math.cos(Math.toRadians(angle)));
						velocity.y=1*(Math.sin(Math.toRadians(angle)));
						apples.add(new Apple(apples.get(x).getAppX(), apples.get(x).getAppY(), velocity.x, velocity.y, 0));
						apples.get(apples.size()-1).setPathHolder(false);
					}
			}
		}
		//FOR TESTING PURPESES.
//				if(pressedKey==KeyEvent.VK_E)	
//					apples.add(new Apple(mouse.x, mouse.y, 0, -0.5, 0));
//				else if(pressedKey==KeyEvent.VK_R)	
//					apples.clear();
	}

	public void MainMenu()
	{
		image = getImage("Menu/MainMenu.jpg");
		g2d.drawImage(image, 0, 0, this);

		i=(4.0/25)*mouse.x; //slope of the line.
		if(mouse.x>185 && mouse.x<690 && mouse.y>i+210 && mouse.y<i+270)
			image = getImage("Menu/Play.gif");
		else if(mouse.x>30 && mouse.x<530 && mouse.y>i+330 && mouse.y<i+370)
			image = getImage("Menu/Level.gif");
		else if(mouse.x>165 && mouse.x<665 && mouse.y>i+425 && mouse.y<i+475)
			image = getImage("Menu/Options.gif");
		g2d.drawImage(image, 0, 0, this);

		image = getImage("Object/Banana.gif");
		g2d.drawImage(image, mouse.x-4, mouse.y, this);

	}
	public void LevelSetUp() 
	{
		power=0;
		apples.clear(); 
		path.clear();
		animal=false;
		elephant=false;
		chimp=false;
		coconut=false;
		specialPath=false;

		bananasHit=0;
		shotsLeft=levels.get(levelNum).getShotsGiven();
		objective=(byte)levels.get(levelNum).getObjective();
		objectsConfig=levels.get(levelNum).getObjectsConfig();

		monk.x=levels.get(levelNum).getMonkX();
		monk.y=levels.get(levelNum).getMonkY();
		pivot.x=monk.x+83; 
		pivot.y=monk.y+50; 
		origin.setLocation(pivot);
		createObjects();
	}
	public void createObjects()
	{
		object.x=0; object.y=0; objects.clear(); 
		for(int x=0, y, z; x!=objectsConfig.length(); x+=2, y=0)
		{
			if(Character.isDigit(objectsConfig.charAt(x))) 
			{
				if(Character.isDigit(objectsConfig.charAt(x+1))) 
					y=2; //(For two digit numbers)
				else
					y=1;

				if(objectsConfig.charAt(x+y)=='T') //TAB
					object.x+=40*(Integer.parseInt(objectsConfig.substring(x, x+y))); 
				else
				{
					for(int c=0; c<(Integer.parseInt(objectsConfig.substring(x, x+y)));  c++)
					{
						if(objectsConfig.charAt(x+y)=='B') 
							objects.add(new Object("Banana", object.x, object.y, 40, 40));
						else if(objectsConfig.charAt(x+y)=='K') 
						{
							z=r.nextInt(2);
							if(z==0)
								objects.add(new Object("SnakeR", object.x, object.y, 40, 40));
							else
								objects.add(new Object("SnakeL", object.x, object.y, 40, 40));
						}		
						else if(objectsConfig.charAt(x+y)=='X') 
						{
							objects.add(new Object("TreeX", object.x, object.y, 120, 80));
							object.x+=80; //+40 later
						}
						else if(objectsConfig.charAt(x+y)=='Y') 
						{
							z=r.nextInt(2);
							if(z==0)
								objects.add(new Object("TreeY1", object.x, object.y, 40, 40));
							else
								objects.add(new Object("TreeY2", object.x, object.y, 40, 40));
						}
						else if(objectsConfig.charAt(x+y)=='Z') 
							objects.add(new Object("TreeZ", object.x-18, object.y, 80, 40));
						else if(objectsConfig.charAt(x+y)=='L') 
						{
							objects.add(new Object("ElephantL", object.x, object.y, 160,120));
							object.x+=120; //+40 later
						}
						else if(objectsConfig.charAt(x+y)=='R') 
						{
							objects.add(new Object("ElephantR", object.x, object.y, 160,120));
							object.x+=120; //+40 later
						}
						else if(objectsConfig.charAt(x+y)=='P') 
							objects.add(new Object("Porcupine", object.x, object.y, 40, 40));
						else if(objectsConfig.charAt(x+y)=='M') 
							objects.add(new Object("ChimpL", object.x, object.y, 40, 80));
						else if(objectsConfig.charAt(x+y)=='W') 
							objects.add(new Object("ChimpR", object.x, object.y, 40, 80));
						else if(objectsConfig.charAt(x+y)=='G') 
							objects.add(new Coconut("Coconut", object.x, object.y, 40, 40));
						object.x+=40;
					}
				}
				x+=(y-1);
			}
			else if(objectsConfig.charAt(x)==' ') //ENTER
			{
				if(Character.isSpaceChar(objectsConfig.charAt(x+2)))
				{
					object.x=0; 
					object.y+=40*(Integer.parseInt(Character.toString(objectsConfig.charAt(x+1))));
					x++;	
				}
				else 
				{
					object.x=0; 
					object.y+=40;
					x--;	
				}		
			}
			else //Will not dedict all string config errors.
				JOptionPane.showMessageDialog(null, "OBJECT-CONFIGURATION STRING ERROR DETECTED.", "SOMEONE IS MESSING WITH MY CODE.", JOptionPane.ERROR_MESSAGE);		
		}

	}

	public void GameSequence() //GAME SEQUENCE
	{	
		//showStatus("Level Name: "+levels.get(levelNum).getName().toUpperCase()+".   Game Speeds: Power: "+(11-barSpeed)+". Apple: "+appleSpeed+". Chimp: "+(11-chimpSpeed));
		masterC++;
		if(masterC==101)
			masterC=0;

		if(masterC%10==0)
			LevelCheck();

		image = getImage("Origins/JungleBackground.jpg");
		g2d.drawImage(image, 0, 0, this);

		if(chimp==false)
		{
			angle();
			if(barActive && masterC%barSpeed==0)
				power();
		}
		else if(chimp && masterC%chimpSpeed==0)
			chimpTail();
		drawPath();
		if(shotsLeft>0 || animal)
			drawPivotLimb();
		if(apples.size()>0)
			for(byte x=0; x<appleSpeed; x++)
				appleProjectiles();
		if(coconut)
			coconutDetection();
		drawMonk();
		drawObjects();
		drawPower();

		//Pivot Point
		//		g2d.setColor(Color.red);
		//		g2d.fillOval(pivot.x, pivot.y, 5, 5);

	}

	//Drawing Methods
	public void drawMonk()
	{
		if(!animal && shotsLeft>0)
		{
			image = getImage("Origins/MonkBody.gif");
			image2 = getImage("Origins/MonkGlow.gif");
			if(angle>90 && angle<270)
				mMirror=true;
			else  
				mMirror=false;
		}
		else
			image = getImage("Origins/MonkPose.gif");

		h=image.getHeight(null); 
		w=image.getWidth(null);
		g2d.translate(monk.x, monk.y);
		if(!mMirror)
		{
			g2d.drawImage(image, 0, 0, this);
			if(!animal)
				g2d.drawImage(image2, 0, h, w, (int)(h-power*1.7), 0, h, w, (int)(h-power*1.7), this);
		}
		else
		{
			g2d.drawImage(image, 0, 0, w, h, w, 0, 0, h, this); 
			if(!animal)
				g2d.drawImage(image2, 0, h, w, (int)(h-power*1.7), w, h, 0, (int)(h-power*1.7), this);
		}
		g2d.translate(-monk.x, -monk.y);


	}
	public void drawPivotLimb()
	{
		if(angle>90 && angle<270)
			limbMirror=true;
		else
			limbMirror=false;

		if(!animal)
			image = getImage("Origins/MonkHand.gif");
		else if(elephant)
			image = getImage("Origins/ElephantHead.gif");
		else if(chimp)
		{
			image = getImage("Origins/ChimpTail.gif");
			if(R)
				limbMirror=true;
			else if(L)
				limbMirror=false;
		}

		h=image.getHeight(null); 
		w=image.getWidth(null);
		scaleX=w;
		if(elephant)
			scaleX+=(int)(power/2);

		g2d.rotate(Math.toRadians(-angle), pivot.x, pivot.y);
		if(!limbMirror)
			g2d.drawImage(image, pivot.x, pivot.y-(h/2), scaleX, h, this);
		else
		{
			g2d.translate(pivot.x, pivot.y-(h/2));
			g2d.drawImage(image, 0, 0, scaleX, h, 0, h, w, 0, this);
			g2d.translate(-pivot.x, (-pivot.y+(h/2)));
		}
		g2d.rotate(Math.toRadians(angle),pivot.x, pivot.y);
	}
	public void drawObjects()
	{
		for(Object b: objects)	
		{
			if(b.draw())
			{
				name=b.getName();
				if(name.charAt(name.length()-1)=='R')  //Vertical Axis Translation
				{
					image = getImage("Object/" +name.substring(0, name.length()-1)+"L.gif");
					w=image.getWidth(null); h=image.getHeight(null);
					g2d.translate(b.getX(), b.getY());
					g2d.drawImage(image, 0, 0, w, h, w, 0, 0, h, this); 
					g2d.translate(-b.getX(), -b.getY());
				}
				else
				{
					image = getImage("Object/" +name+".gif");
					g2d.drawImage(image, b.getX(), b.getY(), this);
				}
			}
		}
	}
	public void drawPower()
	{
		if(!animal)
		{
			g2d.setFont(new Font("Serif", Font.BOLD, 20));

			g2d.setColor(Color.blue);
			if(!mMirror)
				g2d.drawString(""+currentPower, monk.x+32, monk.y+30);
			else
				g2d.drawString(""+currentPower, monk.x+115, monk.y+30);

			g2d.setColor(Color.darkGray);
			if(!mMirror)
				g2d.drawString(""+pastPower, monk.x+60, monk.y+140);
			else
				g2d.drawString(""+pastPower, monk.x+90, monk.y+140);

			g2d.setColor(Color.red);
			if(!mMirror)
				g2d.drawString(""+shotsLeft, monk.x+5, monk.y);
			else
				g2d.drawString(""+shotsLeft, monk.x+155, monk.y);
		}
		else if(elephant)
		{
			g2d.setColor(Color.gray);
			g2d.setFont(new Font("Serif", Font.BOLD, 30));
			if(L)
				g2d.drawString(""+currentPower, objects.get(index).getX()+50, objects.get(index).getY()+50);
			else if(R)
				g2d.drawString(""+currentPower, objects.get(index).getX()+80, objects.get(index).getY()+50);
		}
	}
	public void drawPath()
	{
		for(int x=0; x<path.size()-1; x++)
		{
			if(x%2==0)
				g2d.setColor(Color.green);
			else
			{
				if(specialPath)
					g2d.setColor(Color.blue);
				else
					g2d.setColor(Color.orange);
			}
			g2d.fillOval((int)path.get(x).getX()-5, (int)path.get(x).getY()-5, 10, 10);	
		}
	}

	//Math Methods
	public void power()
	{
		if(pInc)
			power++;
		else
			power--;
		if(power==100)
			pInc=false;
		else if(power==0)
			pInc=true;

		if(power%10==0)
			currentPower=(int)power;
	}
	public void angle()
	{
		angle=Math.atan2(mouse.x-(pivot.x), mouse.y-(pivot.y));
		if(angle<Math.PI/2)
			angle+=2*(Math.PI);
		angle=Math.toDegrees(angle);
		angle-=90;

		if(elephant)
		{
			if(L && angle>65 && angle<180)
				angle=65;
			else if(L && angle>180 && angle<310)
				angle=310;
			else if(R && angle<115 && angle>0)
				angle=115;
			else if(R && angle>230 && angle<360)
				angle=230;
		}
	}
	public void appleProjectiles()
	{
		for(int i=0; i<apples.size(); i++)
		{
			Apple a = apples.get(i);

			apple.x=a.getAppX();
			apple.y=a.getAppY();
			velocity.x=a.getVelX();
			velocity.y=a.getVelY();

			apple.x+=velocity.x;
			apple.y-=velocity.y;
			velocity.y=velocity.y-0.0008;
			collisionDetection(a);

			a.setAppX(apple.x);
			a.setAppY(apple.y);
			a.setVelX(velocity.x);
			a.setVelY(velocity.y);

			if(a.isPathHolder())
			{
				path.add(new Point2D.Double());
				path.get(path.size()-1).setLocation(apple);
			}

			rotation=a.getRotation();
			rotation+=a.getIncrement();
			a.setRotation(rotation);

			if(!invisible)
			{
				image = getImage("Origins/Apple.gif");
				g2d.rotate(Math.toRadians(rotation), apple.x, apple.y);
				g2d.drawImage(image, (int)apple.x-10, (int)apple.y-10, this);
				g2d.rotate(Math.toRadians(-rotation), apple.x, apple.y);
			}
			if(apple.x>1010 || apple.x<-10 || apple.y>610)	
				apples.remove(i);
		}
	}
	public void collisionDetection(Apple a)
	{
		i=-1;
		if(!mMirror && path.size()>200 && apple.x>monk.x && apple.x<monk.x+100 && apple.y>monk.y && apple.y<monk.y+160) //Re-catch
		{
			apple.x=-100;
			shotsLeft++;
		}
		else if(mMirror && path.size()>200 && apple.x>monk.x+65 && apple.x<monk.x+165 && apple.y>monk.y && apple.y<monk.y+160) //Re-catch
		{
			apple.x=-100;
			shotsLeft++;
		}
		else
			for(Object b: objects)
			{
				i++; //for index;
				name=b.getName();
				object.x=b.getX()+b.getW()/2; //middle of object.
				object.y=b.getY()+b.getH()/2;

				if(object.distance(apple)<=b.getH()/2+10 && b.draw())  //Circle Method
				{
					if(name.equals("Banana"))
					{
						b.setDraw(false);
						bananasHit++;
					}
					else if(name.equals("TreeX"))
					{
						apple.x=-100;
						sound=new AudioClip("Sounds/TreeX.wav");
						sound.playOnce();
					}
					else if(name.equals("TreeY1") || name.equals("TreeY2") || name.equals("TreeZ"))
						apple.x=-100;
					else if(name.equals("SnakeR") || name.equals("SnakeL"))
					{
						a.addToSnakeHits((byte)1); //(pun intended).
						if(a.getSnakeHits()==8)
							apple.x=-100;
						else if(apple.x<=object.x-20) //LEFT
						{
							velocity.x*=-1;
							apple.x-=5;
							velocity.x-=0.03;
							a.reverseIncrement();
						}
						else if(apple.x>=object.x+20) //RIGHT
						{
							velocity.x*=-1;
							apple.x+=5;
							velocity.x-=0.03;
							a.reverseIncrement();
						}
						else if(apple.y<=object.y-20) //TOP
						{
							velocity.y*=-1;
							apple.y-=5;
							velocity.y-=0.05;

						}
						else if(apple.y>=object.y+20) //BOTTOM
						{
							velocity.y*=-1;
							apple.y+=5;
							velocity.y-=0.05;

						}

						sound=new AudioClip("Sounds/Snake.wav");
						sound.playOnce();

					}
					else if(name.equals("Porcupine")) 
					{
						apple.x=-100;
						b.setName("Porcupine2");
						b.setX(b.getX()-10);
						b.setY(b.getY()-10);
						b.setWidth(60);
						b.setHeight(60);
						for(Object c: objects)
							if(c.getName().equals("Banana") && c.draw()==true && (c.getX()>=object.x-60 && c.getX()<=object.x+20 && c.getY()>=object.y-60 && c.getY()<=object.y+20))
							{		
								c.setDraw(false);
								bananasHit++;
							}
						sound=new AudioClip("Sounds/Porcupine.wav");
						sound.playOnce();
					}
					else if(name.equals("Porcupine2"))
						apple.x=-100;
					else if(name.equals("Coconut") && ((Coconut) b).isFalling()==false)
					{
						coconut=true;
						((Coconut) b).setFalling(true);
						sound=new AudioClip("Sounds/Coconut.wav");
						sound.playOnce();
					}
				}
				if(Math.abs(((int)apple.x)-(object.x))<=90 && Math.abs(((int)apple.y)-(object.y))<=60) //Retangle Method
				{
					if(!animal && ((name.equals("ElephantL") || name.equals("ElephantR"))) && path.size()>150) 
					{
						apple.x=-100;
						animal=true;
						elephant=true;
						index=(byte)i;
						pivot.y=b.getY()+40;
						if(name.equals("ElephantL"))
						{
							L=true; R=false;
							pivot.x=b.getX()+110;
							origin.setLocation(pivot);
							b.setName("ElephantHeadlessL");
						}
						else 
						{
							R=true; L=false;
							pivot.x=b.getX()+50;
							origin.setLocation(pivot);
							b.setName("ElephantHeadlessR");
						}
					}
				}
				if(Math.abs(((int)apple.x)-(object.x))<=30 && Math.abs(((int)apple.y)-(object.y))<=50)  //Retangle Method
				{
					if(!animal && ((name.equals("ChimpL") || name.equals("ChimpR"))) && path.size()>40)
					{
						apple.x=-100;
						animal=true;
						chimp=true;
						power=100;
						angle=270;
						if(name.equals("ChimpL"))
						{
							L=true; R=false;
							tailInc=true;	
							pivot.x=b.getX()+30;

						}
						else 
						{
							R=true; L=false;
							tailInc=false;
							pivot.x=b.getX();

						}
						pivot.y=b.getY()+60;
						origin.setLocation(pivot);
					}
				}

			}



	}

	//Specials
	public void chimpTail()
	{
		if(tailInc)
			angle++;
		else
			angle--;
		if(angle>360)
			angle-=360;
		else if(angle<0)
			angle+=360;

		if(L)
		{
			if(angle==45)
				tailInc=false;
			else if(angle==250)
				tailInc=true;	
		}
		else if(R)
		{
			if(angle==135)
				tailInc=true;
			else if(angle==280)
				tailInc=false;	
		}

	}
	public void coconutDetection()
	{
		i=0;
		for(Object b: objects)
		{
			if(b instanceof Coconut && ((Coconut) b).isFalling() && ((Coconut) b).draw())
			{
				i++;
				for(Object c: objects)
				{
					object.x=c.getX(); object.y=c.getY();
					if(b.getX()==object.x && b.getY()+40==object.y && c.draw()==true)
					{
						if(c.getName().equals("Banana"))
						{
							c.setDraw(false);
							bananasHit++;
						}
						else if(c.getName().equals("Porcupine")) //add a ; here for a surprise.
						{	
							((Coconut) b).setFalling(false);
							b.setDraw(false);
							c.setName("Porcupine2");
							c.setX(c.getX()-10);
							c.setY(c.getY()-10);
							c.setWidth(60);
							c.setHeight(60);
							for(Object d: objects)
								if(d.getName().equals("Banana") && d.draw()==true && (d.getX()>=object.x-40 && d.getX()<=object.x+40 && d.getY()>=object.y-40 && d.getY()<=object.y+40))
								{		
									d.setDraw(false);
									bananasHit++;
								}
							sound=new AudioClip("Sounds/Porcupine.wav");
							sound.playOnce();

						}
					}
					else if(c.getName().equals("Porcupine2") && b.getX()==object.x+10 && b.getY()+40==object.y+10) 
					{
						((Coconut) b).setFalling(false);
						b.setDraw(false);
					}
					if(b.getY()>600)
					{
						b.setDraw(false);
						((Coconut) b).setFalling(false);
					}
				}
				b.setY(b.getY()+1);
			}
		}
		if(i==0)
			coconut=false;
	}
	public void LevelCheck()
	{
		if(levelNum==9)
			fireWorks();
		else if(bananasHit==levels.get(levelNum).getObjective())
		{
			levelNum++;
			LevelSetUp();	
			if(levelNum!=9)
				JOptionPane.showMessageDialog(null, "Level Completed!", "CONGRATULATIONS!", JOptionPane.INFORMATION_MESSAGE);
			else
				KeyExited(null);
		}
		else if(shotsLeft==0 && apples.size()==0 && !animal && !coconut)
		{
			LevelSetUp();
			JOptionPane.showMessageDialog(null, "You're out of apples!", "Try Again", JOptionPane.ERROR_MESSAGE);
		}


	}
	public void fireWorks()
	{
		path.clear();	
		specialPath=!specialPath;
		w=r.nextInt(800)+100;
		h=r.nextInt(400)+100;
		for(int angle=0; angle!=360; angle+=10)
		{
			velocity.x=1*(Math.cos(Math.toRadians(angle)));
			velocity.y=1*(Math.sin(Math.toRadians(angle)));
			apples.add(new Apple(w, h, velocity.x, velocity.y, 0));
		}
		if(apples.size()>600)
			apples.clear();
	}

	//Active Methods
	public void mousePressed(MouseEvent e) 
	{
		if(runGame && (shotsLeft>0 || animal))
		{
			barActive=true;
			pInc=true;
		}
	}
	public void mouseReleased(MouseEvent e) 
	{
		if(runGame && (shotsLeft>0 || animal))
		{
			barActive=false; path.clear();  
			velocity.x=(power/80)*(Math.cos(Math.toRadians(angle)));
			velocity.y=(power/80)*(Math.sin(Math.toRadians(angle)));
			apples.add(new Apple(origin.x, origin.y, velocity.x, velocity.y, power/80));
			if(limbMirror)
				apples.get(apples.size()-1).reverseIncrement();
			if(apples.size()>1)
				apples.get(apples.size()-2).setPathHolder(false);

			//Animals
			if(chimp)
			{
				animal=false;
				chimp=false;
				specialPath=true;
				sound=new AudioClip("Sounds/ChimpThrow.wav");
			}
			else if(elephant)
			{
				animal=false;
				elephant=false;	
				if(L)
					objects.get(index).setName("ElephantL");
				else if(R)
					objects.get(index).setName("ElephantR");
				specialPath=true;
				sound=new AudioClip("Sounds/ElephantThrow.wav");
			}
			else
			{
				specialPath=false;
				shotsLeft--;
				pastPower=(int)power;
				sound=new AudioClip("Sounds/MonkThrow.wav");
			}
			power=0;
			currentPower=0;
			if(!chimp && !elephant) //ALL FALSE
			{
				pivot.x=monk.x+83; 
				pivot.y=monk.y+50; 
				origin.setLocation(pivot);
			}
			sound.playOnce();
		}
	}

	//Not used:
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void KeyExited(KeyEvent e)
	{
		if(levelNum==9)
			JOptionPane.showMessageDialog(null, "GAME COMPLETED!!!\n\nEnter Mr. Successful\nas the Level Name for a Bonus!\n(P- to return to menu).", "CONGRATULATIONS!", JOptionPane.INFORMATION_MESSAGE);
		if(name.equalsIgnoreCase("Mr. Successful"))
		{
			ultimate=true;
			JOptionPane.showMessageDialog(null, "Error Overide...\n\nHere's a Little Gift From the Creator!\n\nRoam through all the levels using Space / Backspace\nAnd press T for an extra blast!                       \n-Tom", "Tom Matsliach", JOptionPane.WARNING_MESSAGE);	
		}
	}	
	public void keyTyped(KeyEvent e) {


	}
	public void keyReleased(KeyEvent e){
	}

    private Image getImage(String loc) {
        return new ImageIcon(getClass().getResource("/" + loc)).getImage();
    }

	public static void main(String[] args) {
		BananasMain game = new BananasMain();
		game.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		game.init();
		game.setVisible(true);
	}

}
