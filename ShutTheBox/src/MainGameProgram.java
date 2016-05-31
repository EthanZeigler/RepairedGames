//Final game sophmore year shut the box

import sun.applet.Main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.peer.MouseInfoPeer;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MainGameProgram extends JFrame implements KeyListener, MouseListener, MouseMotionListener, Runnable
{
	private static final long serialVersionUID = 1L;

	Random gen = new Random();

	Thread animator;
	Image background;
	Image [] tileIMG = new Image [9];
	Image [] dieIMG = new Image [6];
	Image [] tileHighlightedIMG = new Image [9];
	Image confirmButtonON, confirmButtonOFF, resetButton;
	boolean [] tileUsed = new boolean [9];
	boolean [] tileClicked = new boolean [9];
	boolean [] dieDisplayed = new boolean [6];
	boolean refresh, reroll;
	int dieTotal, die1X, die1Y, die2X, die2Y, die1Value, die2Value, tileSelectedTotal;


    public static void main(String[] args) {
        MainGameProgram game = new MainGameProgram();
        game.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        game.init();
        game.start();
        game.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

	public void init()
	{
		setSize(750, 502);	

		background=loadImage("Background Shut The Box.jpg");
		tileIMG[0]= loadImage("Tile1.png");
		tileIMG[1]= loadImage("Tile2.png");
		tileIMG[2]= loadImage("Tile3.png");
		tileIMG[3]= loadImage("Tile4.png");
		tileIMG[4]= loadImage("Tile5.png");
		tileIMG[5]= loadImage("Tile6.png");
		tileIMG[6]= loadImage("Tile7.png");
		tileIMG[7]= loadImage("Tile8.png");
		tileIMG[8]= loadImage("Tile9.png");
		dieIMG[0]=loadImage("Die1.png");
		dieIMG[1]=loadImage("Die2.png");
		dieIMG[2]=loadImage("Die3.png");
		dieIMG[3]=loadImage("Die4.png");
		dieIMG[4]=loadImage("Die5.png");
		dieIMG[5]=loadImage("Die6.png");
		tileHighlightedIMG[0]=loadImage("Tile1Highlighted.png");
		tileHighlightedIMG[1]=loadImage("Tile2Highlighted.png");
		tileHighlightedIMG[2]=loadImage("Tile3Highlighted.png");
		tileHighlightedIMG[3]=loadImage("Tile4Highlighted.png");
		tileHighlightedIMG[4]=loadImage("Tile5Highlighted.png");
		tileHighlightedIMG[5]=loadImage("Tile6Highlighted.png");
		tileHighlightedIMG[6]=loadImage("Tile7Highlighted.png");
		tileHighlightedIMG[7]=loadImage("Tile8Highlighted.png");
		tileHighlightedIMG[8]=loadImage("Tile9Highlighted.png");
		resetButton=loadImage("ResetButton.png");
		confirmButtonON=loadImage("ConfirmButtonON.png");
		confirmButtonOFF=loadImage("ConfirmButtonOFF.png");
		refresh=true;
		reroll=true;
		Arrays.fill(tileUsed, false);
		Arrays.fill(tileClicked, false);

		tileSelectedTotal=0;

		setVisible(true);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		requestFocus();
	}
	public void start() 
	{
		if(animator==null)
		{
			animator=new Thread(this);
			animator.start();
		}
        JOptionPane.showMessageDialog(this, "Welcome to Shut The Box! The goal is to remove all the tiles.\n" +
                "On each roll, you must select tiles of the same total as the total of the dice.\n" +
                "If you roll a 2 and a 4, you must select a total of 6 with the tiles.\n" +
                "You win if you can remove every tile and shut the box!\n" +
                "(The last roll's total must still match the selected tiles)\n" +
                "Can't do anything? Hit reset and try again!");
	}
	public void stop()
	{
		animator=null;
	}
	public void run()
	{
		while(Thread.currentThread()!=null)
		{
			try
			{
				Thread.sleep(1);
				repaint();
			}
			catch(Exception e)
			{}
		}
	}
	@Override
	public void update(Graphics g)
	{
		paint(g);
	}                   	
	@Override
	public void paint(Graphics graphics)
	{

        BufferedImage bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bufferedImage.createGraphics();

		Font font = new Font("Arial",Font.BOLD,30);
		g.setFont(font);
		g.setColor(Color.CYAN);


		if(reroll)
		{
			boolean dieFarApart=false;

			//sets die positions, numbers, not overlapping
			die1X=gen.nextInt(650)+30;
			die1Y=gen.nextInt(175)+230;
			die2X=gen.nextInt(650)+30;
			die2Y=gen.nextInt(175)+230;
			die1Value=gen.nextInt(6)+1;
			die2Value=gen.nextInt(6)+1;
			dieTotal=die1Value+die2Value;
			while(!dieFarApart)
			{
				if(Math.sqrt( ((die2X-die1X) * (die2X-die1X)) + ((die2Y-die1Y) * (die2Y-die1Y))) < 75)
				{
					die1X=gen.nextInt(650)+30;
					die1Y=gen.nextInt(175)+230;
					die2X=gen.nextInt(650)+30;
					die2Y=gen.nextInt(175)+230;
				}
				else dieFarApart=true;
			}

			//draw dice
			//    IMG                          WDTH LNGHT THREAD
			g.drawImage(dieIMG[die1Value-1], die1X, die1Y, 50, 50, this);
			//	    IMG                        WDTH LNGHT THREAD
			g.drawImage(dieIMG[die2Value-1], die2X, die2Y, 50, 50, this);

			reroll=false;
		}

		if(refresh)
		{

			//draws background
			g.drawImage(background, 0, 0, 750, 502, this);

			//draws tiles if not used
			for(int count=0; count<=8; count++)
			{
				if(!tileUsed[count])
				{
					if(!tileClicked[count])
					{
						//           IMG             XPOS        YPOS  WDTH LNGTH THREAD
						g.drawImage(tileIMG[count], 40+(count*75), 100, 72, 129, this);
					}
					else
					{
						//		    IMG             XPOS        YPOS  WDTH LNGTH THREAD
						g.drawImage(tileHighlightedIMG[count], 40+(count*75), 100, 72, 129, this);
					}
				}
			}

			//counts total of selected tiles
			tileSelectedTotal=0;
			for(int count=0; count<9; count++)
			{
				if(tileClicked[count])
				{
					tileSelectedTotal+=count+1;
				}
			}

			//if selected tile equal die total, draw button on, else draw button off
			if(tileSelectedTotal==dieTotal)
			{
				//             IMG           X    Y WIDTH LNGH THREAD
				g.drawImage(confirmButtonON, 627, 25, 100, 50, this);
			}
			else g.drawImage(confirmButtonOFF, 627, 25, 100, 50, this);

			//draw selected total
			g.drawString("Total of Selected Tiles: " + tileSelectedTotal , 47, 61);

			g.drawImage(resetButton, 509, 25, 100, 50, this);

			//draw dice
			//    IMG                          WDTH LNGHT THREAD
			g.drawImage(dieIMG[die1Value-1], die1X, die1Y, 50, 50, this);
			//	    IMG                        WDTH LNGHT THREAD
			g.drawImage(dieIMG[die2Value-1], die2X, die2Y, 50, 50, this);


			for(int count=0, occurances = 0; count<9; count++)
			{
				if(!tileUsed[count])
					occurances++;
				if(count==8 && occurances==0)
				{
					//you win!
				}

			}

			refresh=false;

            graphics.drawImage(bufferedImage,0,20,null);
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) //Runs continually as the mouse click is held down and the mouse is moving
	{
		//cartx = e.getPoint().x;
		//carty = e.getPoint().y;

		//System.out.println("mouseDragged");
	}
	@Override
	public void mouseMoved(MouseEvent e) //Runs continually as the mouse moves
	{
		//cartx = e.getPoint().x;
		//carty = e.getPoint().y;

		//System.out.println("mouseMoved");
	}
	@Override
	public void mouseClicked(MouseEvent e) //Runs once when the mouse is released
	{
		//detects clicking of the confirm button
		if((629<e.getX() && e.getX()<724) && (29<e.getY() && e.getY()<69))
		{
			//System.out.println("Confirm clicked");

			if(tileSelectedTotal==dieTotal)
			{
				for(int count=0; count<9; count++)
				{
					if(tileClicked[count])
					{
						tileUsed[count]=true;
					}
				}
				reroll=true;
				refresh=true;
				Arrays.fill(tileClicked, false);
			}

		}

		else if((45<e.getX() && e.getX()<105) && (102<e.getY() && e.getY()<226) && !tileUsed[0])
		{
			//			System.out.println("1 clicked");

			if(!tileClicked[0])
			{
				tileClicked[0]=true;
			}
			else tileClicked[0]=false;
		}

		else if((120<e.getX() && e.getX()<181) && (102<e.getY() && e.getY()<226) && !tileUsed[1])
		{
			//			System.out.println("2 clicked");

			if(!tileClicked[1])
			{
				tileClicked[1]=true;
			}
			else tileClicked[1]=false;
		}

		else if((195<e.getX() && e.getX()<256) && (102<e.getY() && e.getY()<226) && !tileUsed[2])
		{
			//			System.out.println("3 clicked");

			if(!tileClicked[2])
			{
				tileClicked[2]=true;
			}
			else tileClicked[2]=false;
		}

		else if((270<e.getX() && e.getX()<332) && (102<e.getY() && e.getY()<226) && !tileUsed[3])
		{
			//			System.out.println("4 clicked");

			if(!tileClicked[3])
			{
				tileClicked[3]=true;
			}
			else tileClicked[3]=false;
		}

		else if((344<e.getX() && e.getX()<407) && (102<e.getY() && e.getY()<226) && !tileUsed[4])
		{
			//			System.out.println("5 clicked");

			if(!tileClicked[4])
			{
				tileClicked[4]=true;
			}
			else tileClicked[4]=false;
		}

		else if((420<e.getX() && e.getX()<481) && (102<e.getY() && e.getY()<226) && !tileUsed[5])
		{
			//			System.out.println("6 clicked");

			if(!tileClicked[5])
			{
				tileClicked[5]=true;
			}
			else tileClicked[5]=false;
		}

		else if((493<e.getX() && e.getX()<556) && (102<e.getY() && e.getY()<226) && !tileUsed[6])
		{
			//			System.out.println("7 clicked");

			if(!tileClicked[6])
			{
				tileClicked[6]=true;
			}
			else tileClicked[6]=false;
		}

		else if((570<e.getX() && e.getX()<631) && (102<e.getY() && e.getY()<226) && !tileUsed[7])
		{
			//			System.out.println("8 clicked");

			if(!tileClicked[7])
			{
				tileClicked[7]=true;
			}
			else tileClicked[7]=false;
		}

		else if((645<e.getX() && e.getX()<707) && (102<e.getY() && e.getY()<226) && !tileUsed[8])
		{
				System.out.println("9 clicked");

			if(!tileClicked[8])
			{
					tileClicked[8]=true;
			}
			else tileClicked[8]=false;
		}

		else if((509<e.getX() && e.getX()<606) && (29<e.getY() && e.getY()<68))
		{
			//reset
			refresh=true;
			reroll=true;
			Arrays.fill(tileUsed, false);
			Arrays.fill(tileClicked, false);

			tileSelectedTotal=0;
		}

		refresh=true;
		//System.out.println("mouseClicked");
	}
	@Override
	public void mousePressed(MouseEvent e) //Runs once when the mouse is pressed
	{
		//System.out.println("mousePressed");
	}
	@Override
	public void mouseReleased(MouseEvent e) //Runs once when the mouse is released
	{
		//System.out.println("mouseReleased");
	}
	@Override
	public void mouseEntered(MouseEvent e) //Runs once when the cursor hovers over the game window
	{
		//System.out.println("mouseEntered");
	}
	@Override
	public void mouseExited(MouseEvent e) //Runs once when the cursor hovers outside of the game window
	{
		//System.out.println("mouseExited");
	}
	@Override
	public void keyTyped(KeyEvent e) //Runs once when a key is pressed.  Runs continually when the key is held down
	{
	}
	@Override
	public void keyPressed(KeyEvent e) //Runs once when a key is pressed.  Runs continually when the key is held down
	{
	}
	@Override
	public void keyReleased(KeyEvent e) //Runs when the user takes finger off of the key
	{

	}

    private Image loadImage(String path) {
        return new ImageIcon(getClass().getResource("/" + path)).getImage();
    }
}
