package starwars.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWGrid;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.entities.actors.Droid;
import starwars.entities.actors.LeiaOrgana;
import starwars.entities.actors.Player;

/**
 * An Affordance for Traveling
 * 
 * The affordance is offered by the <code>Millennium Falcon</code>
 *
 * Teleports Luke and his party to the Millennium Falcon on the other maps
 *
 * @author Daryl Ho
 */
public class Travel extends SWAffordance implements SWActionInterface {

	public Travel(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isMoveCommand() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canDo(SWActor a) {
		// You can always fly if you're at the same spot as the Millennium Falcon
		return true;
	}

	@Override
	public void act(SWActor a) {
		assert (a instanceof Player);
		// Check for available worlds and get user input on where to travel
		SWWorld world = a.getWorld();
		SWGrid currentGrid = world.getGrid();
		Scanner instream = new Scanner(System.in);
		ArrayList<SWGrid> grids = world.getWorldGrids();
		ArrayList<Integer> gridno = new ArrayList<Integer>();
		for (int i = 0; i < grids.size(); i++) {
			if (grids.get(i).getGridName() != currentGrid.getGridName()) {
				gridno.add(i);
			}
		}
		for (int i = 0; i < gridno.size(); i++) {
			System.out.println(i + 1 + " Fly to " + grids.get(gridno.get(i)).getGridName());
		}
		int selection = 0;
		while (selection < 1 || selection > grids.size()) {//loop until a command in the valid range has been obtained
			System.out.println("Enter command:");
			selection = (instream.nextInt());
		}
		// Change current grid to selected grid
		world.setCurrentGrid(gridno.get(selection-1));
		
		SWGrid newGrid = world.getGrid();
		
		// Change Player to new location in EM
		EntityManager<SWEntityInterface, SWLocation> entityManager = SWAction.getEntitymanager();
		
		// Get Location of Millennium Falcon on the new grid
		SWLocation falconLocation;
		falconLocation = newGrid.getLocationByCoordinates(0,  0);
		
		// Bring all party members following that are in NEIGHBOURING spots
		// If a party member is on the other side of the map...he/she/it can't be riding the falcon...	
		ArrayList<Direction> possibledirections = new ArrayList<Direction>();
		
		for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
			if (entityManager.seesExit(a, d)) {
				possibledirections.add(d);
			}
		}
		// If party in neighbouring location, string em along
		for (Direction d : possibledirections) {
			SWLocation neighbourLoc = (SWLocation) entityManager.whereIs(a).getNeighbour(d);
			List<SWEntityInterface> entities = entityManager.contents(neighbourLoc);
			if (entities != null) {
				for (SWEntityInterface e : entities) {
					if ( (e instanceof Droid &&  ( (Droid) e).getOwner() != null) || (e instanceof LeiaOrgana && ((LeiaOrgana) e).rescued()) )  {
						entityManager.setLocation(e, falconLocation);
					}
				}
			}
		}
		// Also check current spot player is on
		List<SWEntityInterface> entities = entityManager.contents(entityManager.whereIs(a));
		if (entities != null) {
			for (SWEntityInterface e : entities) {
				if ( (e instanceof Droid &&  ( (Droid) e).getOwner() != null) || (e instanceof LeiaOrgana && ((LeiaOrgana) e).rescued()) )  {
					entityManager.setLocation(e, falconLocation);
				}
			}
		}
		// Teleport Actor to location, done after checking neighbours in old location!
		entityManager.setLocation(a, falconLocation);
		a.resetMoveCommands(falconLocation);
		a.say(a.getShortDescription() + " has flown to the " + grids.get(selection).getGridName());
	}
	public void teleportTeam(List<SWEntityInterface> entityList) {
		
	}
	
	@Override
	public String getDescription() {
		return "Travel to another planet or space station";
	}
}

