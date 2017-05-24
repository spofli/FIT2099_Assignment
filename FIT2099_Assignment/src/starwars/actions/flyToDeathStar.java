package starwars.actions;

import starwars.SWAffordance;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWGrid;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.entities.actors.Droid;
import starwars.entities.actors.LeiaOrgana;
import starwars.entities.actors.Player;

/**
 * An Affordance for flying to the DeathStar
 * 
 * The affordance is offered by the Millennium Falcons on the Rebel Base and Tatooine
 * <code>RebelFalcon</code> and <code>TatooineFalcon</code>
 *
 * Teleports Luke and his party to the Millennium Falcon on the other maps
 *
 * @author Daryl Ho
 */
public class flyToDeathStar extends SWAffordance implements SWActionInterface {

	public flyToDeathStar(SWEntityInterface theTarget, MessageRenderer m) {
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
		
		// Change current grid to DeathStar
		SWWorld world = a.getWorld();
		world.setCurrentGrid(2);
		
		// Get Location of Millennium Falcon on the new grid
		SWGrid currentGrid = world.getGrid();
		
		SWLocation falconLocation;
		falconLocation = currentGrid.getLocationByCoordinates(0,  0);
		
		// Change Player to new location in EM
		EntityManager<SWEntityInterface, SWLocation> entityManager = SWAction.getEntitymanager();
		
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
		a.say(a.getShortDescription() + " has flown to the DeathStar.");
	}
	public void teleportTeam(List<SWEntityInterface> entityList) {
		
	}
	
	@Override
	public String getDescription() {
		return "Fly to the DeathStar";
	}
}

