
public abstract class Tower 
  {
	boolean shoot = false;
	int timeCount = 0, splash1 = 0, splash2 = 0, splash3 = 0;
	int x, y, max;
	int range = 0, damage =0, level = 0, sell = 0;
	String rate = null;
	boolean selected;
	public Tower()
	{
		
	}
	public Tower(int xSet, int ySet)
	{
		x= xSet;
		y = ySet;
	}
	public void setSplash1(int set)
	{
		splash1 = set;
	}
	public int getSplash1()
	{
		return splash1;
	}
	public void setSplash2(int set)
	{
		splash2 = set;
	}
	public int getSplash2()
	{
		return splash2;
	}
	public void setSplash3(int set)
	{
		splash3 = set;
	}
	public int getSplash3()
	{
		return splash3;
	}
	abstract public int getX();
	abstract public int getY();
	abstract public int getRange();
	abstract public void setRange(int set);
	abstract public int getDamage();
	abstract public void setDamage(int set);
	abstract public String getRate();
	abstract public void setRate(String set);
	abstract public int getLevel();
	abstract public void levelUp();
	abstract public void select();
	abstract public void unselect();
	abstract public int getUCost();
	abstract public int getSell();
	public boolean canShoot()
	{
		return shoot;
	}
	public void cantShoot()
	{
		timeCount = 0;
		shoot = false;
	}
	public void delay()
	{
		if(timeCount != max)
			timeCount++;
		if(rate.equals("Very Slow"))
			max = 140;
		else if(rate.equals("Slow"))
			max = 90;
		else if(rate.equals("Fast"))
			max = 60;
		else if(rate.equals("Very Fast"))
			max = 40;
		if(timeCount == max)
		{
			shoot = true;
		}
	}
	
  }
