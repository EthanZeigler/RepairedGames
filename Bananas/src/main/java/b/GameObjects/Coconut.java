package b.GameObjects;


public class Coconut extends Object
{
	private boolean falling;
	public Coconut(String objectName, int objectX, int objectY, int w, int h) 
	{
		super(objectName, objectX, objectY, w, h);
	}
	public boolean isFalling() {
		return falling;
	}
	public void setFalling(boolean falling) {
		this.falling = falling;
	}
}
