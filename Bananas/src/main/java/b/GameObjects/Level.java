package b.GameObjects;

public class Level
{
	private int monkX, monkY, objective, shotsGiven;
	private String objectsConfig, name;
	public Level(String name, int objective, int shotsGiven, int monkX, int monkY, String objectsConfig)
	{
		this.name=name;
		this.monkX=monkX; 	
		this.monkY=monkY;
		this.objective=objective;
		this.shotsGiven=shotsGiven;
		this.objectsConfig=objectsConfig;
	}

	public int getMonkX() {
		return monkX;
	}
	public int getMonkY() {
		return monkY;
	}
	public int getObjective() {
		return objective;
	}
	public int getShotsGiven() {
		return shotsGiven;
	}
	public String getObjectsConfig() {
		return objectsConfig;
	}
	public String getName() {
		return name;
	}
}
