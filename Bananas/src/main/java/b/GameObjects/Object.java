package b.GameObjects;

public class Object //Takes care of Tree, Snake, Bird, Elephant.
{
	private String objectName;
	private int objectX, objectY, height, width=0;
	private boolean draw=true;
	public Object(String objectName, int objectX, int objectY, int w, int h)
	{
		this.objectName=objectName;
		this.objectX=objectX;
		this.objectY=objectY;
		width=w;
		height=h;
		
	}
	public String getName() {
		return objectName;
	}
	public void setName(String objectName) {
		this.objectName = objectName;
	}
	public int getX() {
		return objectX;
	}
	public void setX(int objectX) {
		this.objectX = objectX;
	}
	public int getY() {
		return objectY;
	}
	public void setY(int objectY) {
		this.objectY = objectY;
	}
	public boolean draw() {
		return draw;
	}
	public void setDraw(boolean draw) {
		this.draw = draw;
	}
	public int getH() {
		return height;
	}
	public int getW() {
		return width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setWidth(int width) {
		this.width = width;
	}

}
