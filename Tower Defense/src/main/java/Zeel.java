
public class Zeel extends Tower
  {
	int x, y;
	int upgrade = 15, sell = 0;
	public Zeel(int xSet, int ySet)
	{
		rate = "Very Fast";
		damage = 9;
		range = 70;
		level = 1;
		x= xSet;
		y = ySet;
		sell = 6;
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
			damage = 24;
			range = 70;
			upgrade = 26;
			sell = 18;
		}
		else if(level == 3)
		{
			damage = 50;
			splash1 = (int) (damage*.4);
			sell = 37;
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
