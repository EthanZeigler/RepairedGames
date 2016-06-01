package b.GameObjects;


public class Apple
{
	private byte snakeHits=0; 
	private double appleX, appleY, velX, velY, rotation, increment; 
	private boolean slicedApple, pathHolder=true; 
	
	public Apple(double appleX, double appleY, double velX, double velY, double increment)
	{
		this.appleX=appleX;	
		this.appleY=appleY;	
		this.velX=velX;	
		this.velY=velY;	
		this.increment=increment;
		rotation=0;
	}
	public double getAppX() {
		return appleX;
	}
	public void setAppX(double appleX) {
		this.appleX = appleX;
	}
	public double getAppY() {
		return appleY;
	}
	public void setAppY(double appleY) {
		this.appleY = appleY;
	}
	public double getVelX() {
		return velX;
	}
	public void setVelX(double velX) {
		this.velX = velX;
	}
	public double getVelY() {
		return velY;
	}
	public void setVelY(double velY) {
		this.velY = velY;
	}

	public boolean isSliced() {
		return slicedApple;
	}
	public void setSliced(boolean slicedApple) {
		this.slicedApple = slicedApple;
	}
	public boolean isPathHolder() {
		return pathHolder;
	}
	public void setPathHolder(boolean pathHolder) {
		this.pathHolder = pathHolder;
	}
	public byte getSnakeHits() {
		return snakeHits;
	}
	public void addToSnakeHits(byte increase) {
		snakeHits+=increase;
	}
	public double getRotation() {
		return rotation;
	}
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	public double getIncrement() {
		return increment;
	}
	public void reverseIncrement() {
		increment*=-1;
	}

	

	
	
	
}
