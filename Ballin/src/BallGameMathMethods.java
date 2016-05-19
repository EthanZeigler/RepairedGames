
import java.util.Random;

public class BallGameMathMethods {
	public double CirclePosition(double xcen,double xpos, double radius)
	{
		double ypos;
		double xvalue;
		if(xcen>xpos)
			xvalue=xcen-xpos;
		else
			xvalue=xpos-xcen;
		ypos=Math.sqrt(Math.pow(radius, 2)-Math.pow(xvalue, 2));
		return ypos;
	}
	public boolean CircleCollision(double xcen1,double ycen1,double rad1,double xcen2,double ycen2,double rad2)
	{
		double x=xcen1-xcen2;
		double y=ycen1-ycen2;
		if(Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2))<=(rad1+rad2))
			return true;
		else	
			return false;
	}
	public boolean CircleSpawnCollision(double xcen1,double ycen1,double rad1,double xcen2,double ycen2,double rad2)
	{
		double x=xcen1-xcen2;
		double y=ycen1-ycen2;
		if(Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2))<=(rad1+rad2)*3)
			return true;
		else	
			return false;
	}
	public int RandomSign()
	{
		Random r=new Random();
		if(r.nextInt(2)==0)
			return -1;
		else 
			return 1;
	}
}
//&&Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2))>=0.8*(rad1+rad2)-1