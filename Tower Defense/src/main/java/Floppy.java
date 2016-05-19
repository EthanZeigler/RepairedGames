
public class Floppy extends Enemies
  {
	public Floppy(int hpSet, int earnSet, String typeSet)
	{
		hp = hpSet;
		earn = earnSet;
		type = typeSet;
		for(int i = 0; i<12; i++)
		{
			move[i] = false;
		}
	}
	public void setSize(int sizeSet)
	{
		size = sizeSet;
	}
	public int getSize() 
	{
		return size;
	}
	public int getX() 
	{
		return x;
	}
	public int getY() 
	{
		return y;
	}
	public void setX(int set) 
	{
		x = set;
	}
	public void setY(int set) 
	{
		y = set;
	}
	public String toString()
	{
		return "Floppy:\nx"+x+"\ny"+y;
	}
	public int getEarn() 
	{
		return earn;
	}
	public int getHP() 
	{
		return hp;
	}
	public String getType() 
	{
		return type;
	}
	public void setEarn(int set) 
	{
		earn = set;
	}
	public void setHP(int set) 
	{
		hp = set;
	}
	public void setType(String set) 
	{
		type = set;
	}
	public void setMoves(boolean[] set)
	{
		move = set;
	}
	public boolean[] getMoves()
	{
		return move;
	}
  }
