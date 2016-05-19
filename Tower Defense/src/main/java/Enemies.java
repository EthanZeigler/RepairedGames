
public abstract class Enemies 
  {
	boolean[] move = new boolean[12];
	boolean slow = false;
	int slowCount = 0;
	int hp, earn, size, x, y;
	boolean dead = false;
	String type;
	boolean scoreAdded = false;
	public Enemies()
	{
		hp = 0;
		for(int i = 0; i<12; i++)
		{
			move[i] = false;
		}
	}
	public Enemies(int hpSet, int earnSet, String typeSet)
	{
		hp = hpSet;
		earn = earnSet;
		type = typeSet;
		for(int i = 0; i<12; i++)
		{
			move[i] = false;
		}
	}
	public void reset()
	{
		for(int i = 0; i<12; i++)
		{
			move[i] = false;
		}
	}
	abstract public void setSize(int sizeSet);
	abstract public int getSize();
	abstract public void setX(int xSet);
	abstract public int getX();
	abstract public void setY(int ySet);
	abstract public int getY();
	abstract public int getHP();
	abstract public int getEarn();
	abstract public String getType();
	abstract public void setHP(int set);
	abstract public void setEarn(int set);
	abstract public void setType(String set);
	abstract public boolean[] getMoves();
	abstract public void setMoves(boolean[] set);
	public void hit(int hit)
	{
		hp -= hit;
		if(hp <= 0)
			dead = true;
	}
	public boolean status()
	{
		return dead;
	}
	public void slow()
	{
		slow = true;
		slowCount = 5;
	}
	public boolean speed()
	{
		return slow;
	}
	public void slowCheck()
	{
		slowCount--;
		if(slowCount == 0)
		{
			slow = false;
		}
	}
	public void scoreAdded()
	{
		scoreAdded = true;
	}
	public boolean checkAdd()
	{
		return scoreAdded;	
	}
  }
