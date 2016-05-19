
public class Mike extends Tower
  {
	int x, y;
	int upgrade = 75, sell = 0;
	public Mike(int xSet, int ySet)
	{
		rate = "Fast";
		damage = 75;
		range = 100;
		level = 1;
		x= xSet;
		y = ySet;
		sell = 37;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getRange()
	{
		return range;
	}
	public int getDamage()
	{
		return damage;
	}
	public String getRate()
	{
		return rate;
	}
	public int getLevel()
	{
		return level;
	}
	public void setDamage(int set)
	{
		damage = set;
	}
	public void setRate(String set)
	{
		rate = set;
	}
	public void setRange(int set)
	{
		range =set;
	}
	public void levelUp()
	{
		level++;
		if(level == 2)
		{
			damage = 175;
			splash1 = (int) (damage*.4);
			splash2 = 0;
			splash3 = 0;
			upgrade = 100;
			sell = 93;
		}
		else if(level == 3)
		{
			damage = 300;
			splash1 = (int) (damage*.4);
			splash2 = (int) (damage*.2);
			splash3 = 0;
			upgrade = 150;
			sell = 168;
		}
		else if(level == 4)
		{
			damage = 400;
			splash1 = (int) (damage*.4);
			splash2 = (int) (damage*.2);
			splash3 = (int) (damage*.1);
			sell = 281;
		}
	}
	public void select()
	{
		selected = true;
	}
	public void unselect()
	{
		selected = false;
	}
	public int getUCost()
	{
		return upgrade;
	}
	public int getSell()
	{
		return sell;
	}
  }
