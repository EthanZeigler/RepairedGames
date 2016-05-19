
public class Herrick extends Tower
  {
	int x, y;
	int upgrade = 25, sell = 0;
	public Herrick(int xSet, int ySet)
	{
		rate = "Very Fast";
		damage = 25;
		range = 75;
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
			damage = 30;
			splash1 = (int) (damage*.4);
			splash2 = 0;
			splash3 = 0;
			range = 75;
			sell = 56;
		}
		else if(level == 3)
		{
			damage = 35;
			splash1 = (int) (damage*.4);
			splash2 = (int) (damage*.2);
			splash3 = 0;
			range = 75;
			sell = 75;
		}
		else if(level == 4)
		{
			damage = 40;
			splash1 = (int) (damage*.4);
			splash2 = (int) (damage*.2);
			splash3 = (int) (damage*.1);
			range = 75;
			sell = 93;
		}
	}
	public int getUCost()
	{
		return upgrade;
	}
	public void select()
	{
		selected = true;
	}
	public void unselect()
	{
		selected = false;
	}
	public int getSell()
	{
		return sell;
	}

  }
