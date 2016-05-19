
public class Sougata extends Tower
  {
	int x, y;
	int upgrade = 13, sell = 5;
	public Sougata(int xSet, int ySet)
	{
		rate = "Very Fast";
		damage = 6;
		range = 100;
		level = 1;
		x= xSet;
		y = ySet;
		sell = 5;
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
			damage = 16;
			range = 110;
			upgrade = 32;
			sell = 15;
		}
		else if(level == 3)
		{
			damage = 39;
			range = 125;
			sell = 39;
		}
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
	public int getUCost()
	{
		return upgrade;
	}
	public int getSell()
	{
		return sell;
	}
  }
