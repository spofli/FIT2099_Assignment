package starwars;

import java.util.ArrayList;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.space.World;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.Take;
import starwars.entities.*;
import starwars.entities.actors.*;

/**
 * Class representing a world in the Star Wars universe. 
 * 
 * @author ram
 */
/*
 * Change log
 * 2017-02-02:  Render method was removed from Middle Earth
 * 				Displaying the Grid is now handled by the TextInterface rather 
 * 				than by the Grid or MiddleWorld classes (asel)
 */
public class SWWorld extends World {
	
	/**
	 * <code>SWGrids</code> of this <code>SWWorld</code>
	 */
	public ArrayList<SWGrid> worldGrids = new ArrayList<SWGrid>();
	
	/**
	 * current <code>SWGrid</code> the <code>player</code> is on to display
	 */
	public int currentGrid;
	
	/**The entity manager of the world which keeps track of <code>SWEntities</code> and their <code>SWLocation</code>s*/
	protected static final EntityManager<SWEntityInterface, SWLocation> entityManager = new EntityManager<SWEntityInterface, SWLocation>();
	
	/**
	 * Constructor of <code>SWWorld</code>. This will initialize the <code>SWLocationMaker</code>
	 * and all the grids for all worlds.
	 */
	public SWWorld() {
		SWLocation.SWLocationMaker factory = SWLocation.getMaker();
		
		// Initialise all grids and place in an ArrayList
		// currentGrid will change depending on where the player is on
 		SWGrid tatooineGrid = new SWGrid(10, 10, "Tatooine", factory);
		worldGrids.add(tatooineGrid);
		
		SWGrid rebelGrid = new SWGrid(2, 2, "Rebel Base", factory);
		worldGrids.add(rebelGrid);
		
		SWGrid deathStarGrid = new SWGrid(10, 10, "Death Star", factory);
		worldGrids.add(deathStarGrid);
		
		// Start with Tatooine : position 0 in the array
		currentGrid = 0;
	}

	/** 
	 * Returns the height of the <code>Grid</code>. Useful to the Views when rendering the map.
	 * 
	 * @author ram
	 * @return the height of the grid
	 */
	public int height() {
		return getGrid().getHeight();
	}
	
	/** 
	 * Returns the width of the <code>Grid</code>. Useful to the Views when rendering the map.
	 * 
	 * @author ram
	 * @return the height of the grid
	 */
	public int width() {
		return getGrid().getWidth();
	}
	
	/**
	 * Sets up Tatooine, setting descriptions for locations and placing items and actors
	 * on the grid.
	 * 
	 * @author 	ram
	 * @param 	iface a MessageRenderer to be passed onto newly-created entities
	 * @return 
	 */
	public void initializeTatooine(MessageRenderer iface) {
		// Set Grid to modify to Tatooine's grids
		SWGrid tatooineGrid = worldGrids.get(0);
		
		SWLocation loc;
		// Set default location string
		for (int row=0; row < 10; row++) {
			for (int col=0; col < 10; col++) {
				loc = tatooineGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription("SWWorld (" + col + ", " + row + ")");
				loc.setShortDescription("SWWorld (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}
		
		
		// BadLands
		for (int row = 5; row < 8; row++) {
			for (int col = 4; col < 7; col++) {
				loc = tatooineGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription("Badlands (" + col + ", " + row + ")");
				loc.setShortDescription("Badlands (" + col + ", " + row + ")");
				loc.setSymbol('b');
			}
		}
		
		//Ben's Hut
		loc = tatooineGrid.getLocationByCoordinates(5, 6);
		loc.setLongDescription("Ben's Hut");
		loc.setShortDescription("Ben's Hut");
		loc.setSymbol('H');
		
		Direction [] patrolmoves = {CompassBearing.EAST, CompassBearing.EAST,
	            CompassBearing.SOUTH,
	            CompassBearing.WEST, CompassBearing.WEST,
	            CompassBearing.SOUTH,
	            CompassBearing.EAST, CompassBearing.EAST,
	            CompassBearing.NORTHWEST, CompassBearing.NORTHWEST};
		
		BenKenobi ben = BenKenobi.getBenKenobi(iface, this, patrolmoves);
		ben.setSymbol("B");
		
		loc = tatooineGrid.getLocationByCoordinates(4,  5);
		entityManager.setLocation(ben, loc);
			
		// Luke	
		Player luke = new Player(Team.GOOD, 100, 20, iface, this);
		luke.setShortDescription("Luke");
		loc = tatooineGrid.getLocationByCoordinates(5,9);
		entityManager.setLocation(luke, loc);
		luke.resetMoveCommands(loc);
	
		//TatooineFalcon falcon = new TatooineFalcon(iface);
		//loc = tatooineGrid.getLocationByCoordinates(4, 9);
		//entityManager.setLocation(falcon,  loc);

		// Millennium Falcon
		MillenniumFalcon Tfalcon = new MillenniumFalcon(iface);
		loc = tatooineGrid.getLocationByCoordinates(0, 0);
		entityManager.setLocation(Tfalcon, loc);
		
		// Beggar's Canyon 
		for (int col = 3; col < 8; col++) {
			loc = tatooineGrid.getLocationByCoordinates(col, 8);
			loc.setShortDescription("Beggar's Canyon (" + col + ", " + 8 + ")");
			loc.setLongDescription("Beggar's Canyon  (" + col + ", " + 8 + ")");
			loc.setSymbol('C');
			loc.setEmptySymbol('='); // to represent sides of the canyon
		}
		
		// Moisture Farms
		for (int row = 0; row < 10; row++) {
			for (int col = 8; col < 10; col++) {
				loc = tatooineGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription("Moisture Farm (" + col + ", " + row + ")");
				loc.setShortDescription("Moisture Farm (" + col + ", " + row + ")");
				loc.setSymbol('F');
				
				// moisture farms have reservoirs
				entityManager.setLocation(new Reservoir(iface), loc);				
			}
		}
		// R2-D2
		// MUST come with these set of patrol
		// and placed somewhere with enough space
		Direction [] r2d2Patrol = 
			{CompassBearing.EAST, CompassBearing.EAST,CompassBearing.EAST, CompassBearing.EAST,CompassBearing.EAST,
	            CompassBearing.WEST, CompassBearing.WEST, CompassBearing.WEST, CompassBearing.WEST, CompassBearing.WEST};
		R2D2 theR2 = new R2D2(iface, this, r2d2Patrol);
		theR2.setSymbol("R");
		//loc = tatooineGrid.getLocationByCoordinates(1, 1);
		loc = tatooineGrid.getLocationByCoordinates(5, 8);
		entityManager.setLocation(theR2, loc);
		
		// C-3P0
		C3P0 theC3 = new C3P0(iface, this);
		theC3.setSymbol("3");
		loc = tatooineGrid.getLocationByCoordinates(1, 2);
		entityManager.setLocation(theC3, loc);
		
		// Disabled Droids for R2-D2
		BasicDroid disabledDroid1 = new BasicDroid(50, 0, "Generic Droid A", iface, this) ;
		disabledDroid1.disable();
		disabledDroid1.setSymbol("D");
		loc = tatooineGrid.getLocationByCoordinates(2, 1);
		entityManager.setLocation(disabledDroid1, loc);
		
		BasicDroid disabledDroid2 = new BasicDroid(50, 0, "Generic Droid B", iface, this) ;
		disabledDroid2.disable();
		disabledDroid2.setSymbol("D");
		loc = tatooineGrid.getLocationByCoordinates(4, 1);
		entityManager.setLocation(disabledDroid2, loc);
		
		BasicDroid disabledDroid3 = new BasicDroid(50, 0, "Generic Droid C", iface, this) ;
		disabledDroid3.disable();
		disabledDroid3.setSymbol("D");
		loc = tatooineGrid.getLocationByCoordinates(6, 1);
		entityManager.setLocation(disabledDroid3, loc);
		
		BasicDroid disabledDroid4 = new BasicDroid(50, 0, "Generic Droid D", iface, this) ;
		disabledDroid4.disable();
		disabledDroid4.setSymbol("D");
		loc = tatooineGrid.getLocationByCoordinates(7, 2);
		entityManager.setLocation(disabledDroid4, loc);
		
		BasicDroid disabledDroid5 = new BasicDroid(50, 0, "Generic Droid E", iface, this) ;
		disabledDroid5.disable();
		disabledDroid5.setSymbol("D");
		loc = tatooineGrid.getLocationByCoordinates(3, 4);
		entityManager.setLocation(disabledDroid5, loc);
		
		/*
		 * Scatter some other entities and actors around
		 */
		// a canteen
		loc = tatooineGrid.getLocationByCoordinates(5,8);
		SWEntity canteen = new Canteen(iface, 50,0);
		canteen.setSymbol("o");
		canteen.setHitpoints(500);
		entityManager.setLocation(canteen, loc);
		canteen.addAffordance(new Take(canteen, iface));
		
		//a full canteen on Ben's patrol route
		loc = tatooineGrid.getLocationByCoordinates(5,6);
		SWEntity bensCanteen = new Canteen(iface, 50, 50);
		bensCanteen.setSymbol("o");
		bensCanteen.setHitpoints(500);;
		entityManager.setLocation(bensCanteen, loc);
		bensCanteen.addAffordance(new Take(bensCanteen, iface));
	
		// an oil can treasure
		loc = tatooineGrid.getLocationByCoordinates(5,8);
		SWEntity oilcan = new OilCan(iface, 100, 100);
		oilcan.setSymbol("x");
		oilcan.setHitpoints(100);
		// add a Take affordance to the oil can, so that an actor can take it
		entityManager.setLocation(oilcan, loc);
		oilcan.addAffordance(new Take(oilcan, iface));
		
		// a lightsaber
		LightSaber lightSaber = new LightSaber(iface);
		loc = tatooineGrid.getLocationByCoordinates(5,5);
		entityManager.setLocation(lightSaber, loc);
		
		// A blaster 
		Blaster blaster = new Blaster(iface);
		loc = tatooineGrid.getLocationByCoordinates(3, 4);
		entityManager.setLocation(blaster, loc);
		
		// DroidParts for testing
		DroidParts droidparts = new DroidParts(iface);
		loc = tatooineGrid.getLocationByCoordinates(5, 8);
		entityManager.setLocation(droidparts, loc);
		
		// A Tusken Raider
		TuskenRaider tim = new TuskenRaider(200, 0, "Tim", iface, this);
		
		tim.setSymbol("T");
		loc = tatooineGrid.getLocationByCoordinates(4,3);
		entityManager.setLocation(tim, loc);
		
		// A Tusken Raider
		TuskenRaider tom = new TuskenRaider(200, 0, "Tom", iface, this);
		
		tom.setSymbol("T");
		loc = tatooineGrid.getLocationByCoordinates(4,3);
		entityManager.setLocation(tom, loc);
		
		// A Tusken Raider
		TuskenRaider tem = new TuskenRaider(200, 0, "Tem", iface, this);
		
		tem.setSymbol("T");
		loc = tatooineGrid.getLocationByCoordinates(4,3);
		entityManager.setLocation(tem, loc);
		
		// A Tusken Raider
		TuskenRaider tym = new TuskenRaider(200, 0, "Tym", iface, this);
		
		tym.setSymbol("T");
		loc = tatooineGrid.getLocationByCoordinates(4,3);
		entityManager.setLocation(tym, loc);
		
		// Uncle Owen
		Dummy uown = new Dummy(10,0,"Uncle Owen", iface, this);
		
		uown.setSymbol("U");
		loc = tatooineGrid.getLocationByCoordinates(8, 4);
		entityManager.setLocation(uown, loc);
		
		// Aunt Beru
		Dummy aberu = new Dummy(10,0,"Aunt Beru", iface, this);
		
		aberu.setSymbol("U");
		loc = tatooineGrid.getLocationByCoordinates(9, 4);
		entityManager.setLocation(aberu, loc);

	}	
	/**
	 * Sets up the Rebel Base, setting descriptions for locations and placing items and actors
	 * on the grid.
	 * 
	 * @author 	ram
	 * @param 	iface a MessageRenderer to be passed onto newly-created entities
	 * @return 
	 */
	public void initializeRebelBase(MessageRenderer iface) {
		
		//set Grid to modify to RebelBase grid
		SWGrid rebelGrid = worldGrids.get(1);
		
		
		SWLocation loc;
		// Set default location string
		for (int row=0; row < 2; row++) {
			for (int col=0; col < 2; col++) {
				loc = rebelGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription("SWWorld (" + col + ", " + row + ")");
				loc.setShortDescription("SWWorld (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}
		
		//RebelFalcon falcon = new RebelFalcon(iface);
		//loc = rebelGrid.getLocationByCoordinates(0, 0);
		//entityManager.setLocation(falcon,  loc);

		// Millenium Falcon
		MillenniumFalcon RBfalcon = new MillenniumFalcon(iface);
		loc = rebelGrid.getLocationByCoordinates(0, 0);
		entityManager.setLocation(RBfalcon, loc);
		
		//Admiral Ackbar
		AdmiralAckbar ackbar = AdmiralAckbar.getAdmiralAckbar(iface, this);
		ackbar.setSymbol("A");
		loc = rebelGrid.getLocationByCoordinates(1, 1);
		entityManager.setLocation(ackbar, loc);
		
		// Mon Mothma
		MonMothma mothma = MonMothma.getMonMothma(iface, this);
		mothma.setSymbol("O");
		loc = rebelGrid.getLocationByCoordinates(0, 1);
		entityManager.setLocation(mothma, loc);
	}
	/**
	 * Sets up the Death Star, setting descriptions for locations and placing items and actors
	 * on the grid.
	 * 
	 * @author 	ram
	 * @param 	iface a MessageRenderer to be passed onto newly-created entities
	 * @return 
	 */
	public void initializeDeathStar(MessageRenderer iface) {
		
		//set Grid to modify to DeathStar grid
		SWGrid deathStarGrid = worldGrids.get(2);
		
		SWLocation loc;
		// Set default location string
		for (int row=0; row < 10; row++) {
			for (int col=0; col < 10; col++) {
				loc = deathStarGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription("SWWorld (" + col + ", " + row + ")");
				loc.setShortDescription("SWWorld (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}
		
		//DeathStarFalcon falcon = new DeathStarFalcon(iface);
		//loc = deathStarGrid.getLocationByCoordinates(0, 0);
		//entityManager.setLocation(falcon,  loc);
		
		// Millenium Falcon
		MillenniumFalcon DSfalcon = new MillenniumFalcon(iface);
		loc = deathStarGrid.getLocationByCoordinates(0, 0);
		entityManager.setLocation(DSfalcon, loc);
		
		//Princess Leia
		LeiaOrgana leia = LeiaOrgana.getLeiaOrgana(iface, this);
		leia.setSymbol("L");
		loc = deathStarGrid.getLocationByCoordinates(9, 9);
		entityManager.setLocation(leia, loc);
		
		DarthVader vader = DarthVader.getDarthVader(iface, this);
		vader.setSymbol("V");
		loc = deathStarGrid.getLocationByCoordinates(5,  5);
		entityManager.setLocation(vader, loc);

		
		Stormtrooper storm = new Stormtrooper(100, 20, iface, this);
		storm.setSymbol("S");
		loc = deathStarGrid.getLocationByCoordinates(6, 6);
		entityManager.setLocation(storm, loc);
		
		Stormtrooper storm2 = new Stormtrooper(100, 20, iface, this);
		storm2.setSymbol("S");
		loc = deathStarGrid.getLocationByCoordinates(6, 5);
		entityManager.setLocation(storm2, loc);
		
		Stormtrooper storm3 = new Stormtrooper(100, 20, iface, this);
		storm3.setSymbol("S");
		loc = deathStarGrid.getLocationByCoordinates(6, 4);
		entityManager.setLocation(storm3, loc);
		
		Stormtrooper storm4 = new Stormtrooper(100, 20, iface, this);
		storm4.setSymbol("S");
		loc = deathStarGrid.getLocationByCoordinates(4, 6);
		entityManager.setLocation(storm4, loc);
		
		Stormtrooper storm5 = new Stormtrooper(100, 20, iface, this);
		storm5.setSymbol("S");
		loc = deathStarGrid.getLocationByCoordinates(4, 4);
		entityManager.setLocation(storm5, loc);
		
	}

	/*
	 * Render method was removed from here
	 */
	
	/**
	 * Determine whether a given <code>SWActor a</code> can move in a given direction
	 * <code>whichDirection</code>.
	 * 
	 * @author 	ram
	 * @param 	a the <code>SWActor</code> being queried.
	 * @param 	whichDirection the <code>Direction</code> if which they want to move
	 * @return 	true if the actor can see an exit in <code>whichDirection</code>, false otherwise.
	 */
	public boolean canMove(SWActor a, Direction whichDirection) {
		SWLocation where = (SWLocation)entityManager.whereIs(a); // requires a cast for no reason I can discern
		return where.hasExit(whichDirection);
	}
	
	/**
	 * Accessor for the grid.
	 * 
	 * @author ram
	 * @return the grid
	 */
	public SWGrid getGrid() {
		return worldGrids.get(currentGrid);
	}
	/**
	 * Accessor for the GRIDS
	 * 
	 * @return the gridlist
	 */
	public ArrayList<SWGrid> getWorldGrids() {
		return worldGrids;
	}

	/**
	 * Move an actor in a direction.
	 * 
	 * @author ram
	 * @param a the actor to move
	 * @param whichDirection the direction in which to move the actor
	 */
	public void moveEntity(SWActor a, Direction whichDirection) {
		
		//get the neighboring location in whichDirection
		Location loc = entityManager.whereIs(a).getNeighbour(whichDirection);
		
		// Base class unavoidably stores superclass references, so do a checked downcast here
		if (loc instanceof SWLocation)
			//perform the move action by setting the new location to the the neighboring location
			entityManager.setLocation(a, (SWLocation) entityManager.whereIs(a).getNeighbour(whichDirection));
	}

	/**
	 * Returns the <code>Location</code> of a <code>SWEntity</code> in this grid, null if not found.
	 * Wrapper for <code>entityManager.whereIs()</code>.
	 * 
	 * @author 	ram
	 * @param 	e the entity to find
	 * @return 	the <code>Location</code> of that entity, or null if it's not in this grid
	 */
	public Location find(SWEntityInterface e) {
		return entityManager.whereIs(e); //cast and return a SWLocation?
	}

	/**
	 * This is only here for compliance with the abstract base class's interface and is not supposed to be
	 * called.
	 */

	@SuppressWarnings("unchecked")
	public EntityManager<SWEntityInterface, SWLocation> getEntityManager() {
		return SWWorld.getEntitymanager();
	}

	/**
	 * Returns the <code>EntityManager</code> which keeps track of the <code>SWEntities</code> and
	 * <code>SWLocations</code> in <code>SWWorld</code>.
	 * 
	 * @return 	the <code>EntityManager</code> of this <code>SWWorld</code>
	 * @see 	{@link #entityManager}
	 */
	public static EntityManager<SWEntityInterface, SWLocation> getEntitymanager() {
		return entityManager;
	}

	/**
	 * Changes the current <code>SWGrid</code> to display
	 * 
	 * @param newCurrent index of the grid to change to
	 */
	public void setCurrentGrid(int newCurrent) {
		currentGrid = newCurrent;
	}
}

