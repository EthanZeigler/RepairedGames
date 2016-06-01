/*AUTHOR: Tom Matsliach*/
package a.MainComponents;
import java.util.ArrayList;

import b.GameObjects.Level;


/*INSTRUCTIONS: To create an object that interacts with the ball (Apple), 
 *type the quantity (single or double digit), and then the object's representing character. 
 *Enter t for tab, same quantity rules and format apply. 
 *Enter a space to skip a full line on the grid, or for multiple lines: a space, a single digit number, and a space. Example:( 4 )
 *OBJECTS KEY: (Case sensitive) 
 *  T: tab
 *  B: Banana
 *  X- Top part of the Tree. (2X3 squares).
 *  Y- Middle part of the Tree. 
 *  Z- Bottom part of the Tree. 
 *  R- Elephant placed on the right /=side.
 *  L- Elephant placed on the left side.
 *  K- Snake 
 *  P- Porcupine
 *  M- Chimpanzee placed on the left.
 *  W- Chimpanzee placed on the right.
 *  G- Coconut
 */ 
public class LevelsSetUpCode
{
	public LevelsSetUpCode(ArrayList<Level> level) 
	{
		//(int objective, int shotsGiven, int monkX, int monkY, String objectsConfig)
		
		/*LV1:*/ level.add(new Level("Banana Split", 30, 20, 20, 280, " 4 11T1B 10T3B 10T3B 10T4B 11T3B 12T3B 12T3B 1T3K9T3B 13T4B 14T3B"));  
		/*LV2:*/ level.add(new Level("If A Tree Gets Hit", 15, 15, 20, 420, " 2 1T1B 7T1B3T1B9T1X 15T1B 1T1B20T1Y 9T1B8T2B2T1Y 14T1X1T2B2T1Y 1T1B6T1X1T1B3T6T1Y 12T1B2T1Y6T1Y 5T2B2T1Y5T1Y6T1Y 9T1Y5T1Y6T1Y 9T1Y5T1Y6T1Y 9T1Y5T1Y6T1Y 9T1Z5T1Z6T1Z"));
		/*LV3:*/ level.add(new Level("A Game Of Catch" ,12, 5, 10, 400, " 5 15T2B 4T1X7T4B 13T6B 5T1Y 5T1Y 5T1Y1T1L10T1R 5T1Y 5T1Y 5T1Z")); 
		/*LV4:*/ level.add(new Level("Snakes On A Plane", 8, 4, 80, 400, "8T4K 3T1K 3T1K 3T1K11T4B 3T1K11T4B 3T1K6T1K 10T1K 10T1K1X 2 12T1Y 12T1Y 12T1Y8T1R 12T1Y 12T1Y 12T1Z"));
		/*LV5:*/ level.add(new Level("Banana Skewers" ,35, 5, 420, 250, "5K5T3B 1K3B1K5T1B1P1B 1K1B1P1B1K5T3B 1K3B1K6T1B 2K1B1T1K10T1X 23T1K 16T1Y6T1K 16T1Y2T2K2T1K 1K15T1Y2T1K3B1K 1K5T5K5T1Y2T1K1B1P1B1K 1K6T3B5K1T1Y2T1K3B1K 6T2B1P1B1K5T1Y2T5K 6T1K3B1K5T1Y 6T5K5T1Y 16T1Z"));
		/*LV6:*/ level.add(new Level("Monkeying Around", 15, 2, 50, 420, "3T1M10T1M 2 6T1X 3T1B 7T1Y2T3K9T1X 7T1Y2T3B 7T1Y2T1B1P1B9T1W1Y 3T1B3T1Y2T3B10T1Y 7T1Y6T5K4T1Y 7T1Y15T1Y 7T1Y15T1Y 7T1Y10T1R1T1Y 7T1Y2T5B8T1Y 7T1Y15T1Y 7T1Z15T1Z"));
		/*LV7:*/ level.add(new Level("Tie Fighter" ,15, 1, 480, 280, "4T1M19T1W 19T1G 1K 1K3T5K7T1X 1K21T1G 1K5T1B10T1Y 17T1Y3T1G 6T1K10T1Y 6T1K10T1Y1B1T1B 10T1G6T1Y1B1P1B 8T2G7T1Y1B1T1B 1L3T1G4T4K1T1Y 17T1Y3T2B 7T4B6T1Y3T2B 17T1Z"));
		/*LV8:*/ level.add(new Level("Sniper", 51, 2, 20, 50, "9T1M5T7K 5T1K 5T1K 5T1K13T5B1K 19T5B1K 1T3K14T1K5B1K 4K14T1K5B1K 1X5K10T1K5B1K 18T1K5B1K 1T1Y1M15T7K 1T1Y11T1G2T1G1T1G1T1G 1T1Y6T1R7T3B1T1G 1T1Y15T3B1P1B1T1B 1T1Y11T1B1T3B1P3B1T1B 1T1Z11T1B1T1B1P3B")); 
		/*LV9:*/ level.add(new Level("Finish It", 30, 1, 350, 50, "14K10T1W 1M4T1K7T1K 5T1K7T1K2T5K2T1X 5T1K7T1K2T4B1K 13T8K2T1W1Y 1X2T1K2K1X2K8T2G1T1Y 24T1Y 1T1Y1M6T1Y1M5T1G4T2B1T1Y 1T1Y7T1Y13T1W1Y 1T1Y2T3K2T1Y1T2K5T2G4T1Y 1T1Y7T1Y1M2T2G6T2B1T1Y 1T1Y1T1L2T1Y14T1Y 1T1Y7T1Z2T1K7B4T1Y 1T1Y11T3B1P4B2P1B1Y 1T1Z7T1K3T7B4T1Z")); 
		level.add(new Level("Appleworks", 10, 10, -120, -160, "")); 
		
		
		}
	
	
}
