import java.util.ArrayList;


public class EnemyList 
  {
	ArrayList<Enemies> enemies = new ArrayList<Enemies>();
	public EnemyList()
	{
		enemies.add(new Keyboard(10, 1, "none"));//1
		enemies.add(new Monitor(42, 1, "none"));//1
		enemies.add(new Keyboard(65, 1, "none"));//1
		enemies.add(new Mouse(75, 1, "none"));//1
		enemies.add(new Floppy(101, 1, "none"));//1
		enemies.add(new Mouse(87, 1, "none"));//1, fast
		enemies.add(new Floppy(136, 1, "none"));//1
		enemies.add(new Keyboard(101, 1, "none"));//1, log after
		enemies.add(new Floppy(158, 1, "air"));//1, air
		enemies.add(new Keyboard(189, 1, "none"));//1
		enemies.add(new Monitor(212, 2, "immune"));//2, immune
		enemies.add(new Ulmer(2000, 45, "boss"));//45, boss
		enemies.add(new Floppy(246, 2, "none"));//2
		enemies.add(new Keyboard(331, 2, "none"));//2, log after
		enemies.add(new Floppy(384, 2, "none"));//2
		enemies.add(new Mouse(445, 2, "none"));//2
		enemies.add(new Floppy(580, 2, "air"));//2, air
		enemies.add(new Keyboard(695, 2, "none"));//2
		enemies.add(new Mouse(599, 2, "fast"));//2, fast
		enemies.add(new Floppy(806, 3, "none"));//3
		enemies.add(new Monitor(1125, 3, "immune"));//2, immune, wood after
		enemies.add(new Ulmer(14000 ,55, "boss"));//55, boss
		enemies.add(new Mouse(1075, 3, "none"));//3
		enemies.add(new Floppy(1265, 4, "none"));//4
		enemies.add(new Keyboard(1468, 4, "none"));//4
		enemies.add(new Mouse(1256, 4, "fast"));//4, fast
		enemies.add(new Floppy(1615, 4, "air"));//4, air
		enemies.add(new Keyboard(1935, 4, "none"));//4, wood after
		enemies.add(new Floppy(2165, 5, "none"));//5
		enemies.add(new Keyboard(2005, 5, "none"));//5
		enemies.add(new Floppy(2655, 5, "none"));//5
		enemies.add(new Monitor(2500, 2, "immune/fast"));//2, immune, fast
		enemies.add(new Ulmer(35000, 100, "boss"));//100, boss
		enemies.add(new Floppy(5000, 5, "none"));//5
		enemies.add(new Keyboard(7000, 10, "none"));//10, 40 people, wood after
		enemies.add(new Floppy(10000, 15, "none"));//15
		enemies.add(new Keyboard(15000, 20, "none"));//20
		enemies.add(new Mouse(20000, 25, "none"));//25
		enemies.add(new Floppy(25001, 30, "none"));//30
		
	}
  }
