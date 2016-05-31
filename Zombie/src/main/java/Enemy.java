import java.util.Random;

public class Enemy 
{
	Random gen = new Random();

	int xPos=0, yPos, speed=1;
	boolean isHit=false;

	public Enemy()
	{
		generatePosition();
	}

	public void move(int marioY, int marioX, int level)
	{
		if(level==1)
		{
		if(yPos<marioY)
			yPos+=1;
		if(yPos>marioY)
			yPos-=1;
		if(xPos<marioX)
			xPos+=1;
		if(xPos>marioX)
			xPos-=1;
		}
		else if((level==2)||(level==3))
		{
		if(yPos<marioY)
			yPos+=2;
		if(yPos>marioY)
			yPos-=2;
		if(xPos<marioX)
			xPos+=2;
		if(xPos>marioX)
			xPos-=2;
		}
		else if(level==4)
		{
		if(yPos<marioY)
			yPos+=3;
		if(yPos>marioY)
			yPos-=3;
		if(xPos<marioX)
			xPos+=3;
		if(xPos>marioX)
			xPos-=3;
		}
		
		//System.out.println("Z x- "+xPos);
		//System.out.println("Z y- "+yPos);
	}


	public void generatePosition()
	{
		int random;
		random=gen.nextInt(2)+1;
		if(random==1)
		{
			yPos=gen.nextInt(720)+1;
			xPos=0;
		}
		else if(random==2)
		{
			xPos=gen.nextInt(1080)+1;
			yPos=0;
		}
	}

	public int getxPos()
	{
		return xPos;
	}

	public void setxPos(int xPos)
	{
		this.xPos = xPos;
	}

	public int getyPos()
	{
		return yPos;
	}

	public void setyPos(int yPos)
	{
		this.yPos = yPos;
	}

	public int getSpeed() 
	{
		return speed;
	}

	public void setSpeed(int speed) 
	{
		this.speed = speed;
	}

}

