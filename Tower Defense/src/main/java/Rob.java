
public class Rob extends Tower
  {
	int x, y;
	int upgrade = 20, sell = 0;
	public Rob(int xSet, int ySet)
	{
		rate = "Very Fast";
		damage = 20;
		range = 120;
		level = 1;
		x= xSet;
		y = ySet;
		sell = 9;
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
			damage = 35;
			range = 120;
			upgrade = 30;
			sell = 24;
		}
		else if(level == 3)
		{
			damage = 56;
			range = 120;
			sell = 46;
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
