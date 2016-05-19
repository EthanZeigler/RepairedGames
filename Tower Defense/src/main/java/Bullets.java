
public class Bullets 
{
	Tower tower = null;
	int x, y, damage;
	Enemies enemy = null;
	public Bullets()
	{
		x = 0;
		y= 0;
	}
	public void setTower(Tower set)
	{
		tower = set;
	}
	public Tower getTower()
	{
		return tower;
	}
	public void setX(int set)
	{
		x = set;
	}
	public void setY(int set)
	{
		y= set;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public Enemies getTarget()
	{
		return enemy;
	}
	public void setTarget(Enemies target)
	{
		enemy =  target;
	}
	public void setDamage(int set)
	{
		damage = set;
	}
	public int getDamage()
	{
		return damage;
	}
}
