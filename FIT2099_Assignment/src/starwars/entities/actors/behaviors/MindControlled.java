package starwars.entities.actors.behaviors;

import java.util.ArrayList;
import java.util.Scanner;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.Direction;
import starwars.SWActor;
import starwars.SWWorld;
/**
 * Class that contains method getDirection
 * @author spofli
 *
 */
public class MindControlled {
	/**
	 * 
	 * @param actor <code>SWActor actor</code> being mind controlled
	 * @return direction to move <code>SWActor actor</code> being mind controlled
	 */
	public static Direction getDirection(SWActor actor) {
		Scanner instream = new Scanner(System.in);
		ArrayList<Direction> directions = new ArrayList<Direction>();
		for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
			if (SWWorld.getEntitymanager().seesExit(actor, d)) {
				directions.add(d);
			}
		}
		System.out.println("Mind controlling " + actor.getShortDescription());
		for (int i = 0; i < directions.size(); i++) {
			System.out.println(i + 1 + " Move " + actor.getShortDescription() + " " + directions.get(i));
		}
		int selection = 0;
		while (selection < 1 || selection > directions.size()) {//loop until a command in the valid range has been obtained
			System.out.println("Enter direction to move:");
			selection = (instream.nextInt());
			
		}
		return directions.get(selection - 1);
	}
}
