
public class Tyler extends Tower
  {
	int x, y;
	int upgrade = 0, sell = 0;
	public Tyler(int xSet, int ySet)
	{
		rate = "Slow";
		damage = 2000;
		range = 170;
		level = 1;
		x= xSet;
		y = ySet;
		sell = 150;
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
			range = 170;
			rate = "Fast";
			sell = 292;
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
