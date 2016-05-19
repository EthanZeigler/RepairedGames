
public class Evan extends Tower
  {
	int x, y;
	int upgrade = 75, sell = 0;
	public Evan(int xSet, int ySet)
	{
		rate = "Very Slow";
		damage = 144;
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

	public void select()
	{
		selected = true;
	}
	public void unselect()
	{
		selected = false;
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
			damage = 288;
			range = 110;
			upgrade = 100;
			rate = "Very Slow";
			sell = 93;
		}
		else if(level == 3)
		{
			damage = 576;
			range = 120;
			upgrade = 150;
			sell = 168;
		}
		else if(level == 4)
		{
			damage = 1152;
			range = 130;
			sell = 281;
		}
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
