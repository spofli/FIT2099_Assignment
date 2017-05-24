package starwars.entities.actors;

import java.util.ArrayList;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWLegend;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Move;
import starwars.actions.TurnDark;
import starwars.entities.LightSaber;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;
import starwars.entities.actors.behaviors.ForceChokeNeighbours;
import starwars.entities.actors.behaviors.TurnLukeDark;
import starwars.actions.Learn;

/**
 * Darth Vader.  
 * 
 * Strong boss wielding a <code>Lightsaber</code> who wanders around the Death Star
 * and neatly slices any Actor not on his team with his lightsaber. He also has a
 * 50% chance of Force Choking any actor because he can. Darth Vader also has a 50%
 * chance to attempt to convert Luke to the Dark Side.
 * 
 * Note that you can only create ONE Vader, like all SWLegends.
 * @author rober_000
 *
 */
public class DarthVader extends SWLegend {

	private static DarthVader vader = null; // yes, it is OK to return the static instance!
	private DarthVader(MessageRenderer m, SWWorld world) {
		super(Team.EVIL, 10000, 100, m, world);
		this.setShortDescription("Darth Vader");
		this.setLongDescription("Darth Vader, is your father");
		LightSaber vadersweapon = new LightSaber(m);
		setItemCarried(vadersweapon);
		SWAffordance learn = new Learn(this,messageRenderer);
		this.addAffordance(learn);
	}

	public static DarthVader getDarthVader(MessageRenderer m, SWWorld world) {
		vader = new DarthVader(m, world);
		vader.activate();
		return vader;
	}
	
	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}
	
	@Override
	protected void legendAct() {
		
		if(isDead()) {
			scheduler.endGame("WIN");
			return;
		}
		say(describeLocation());

		AttackInformation attack;
		attack = AttackNeighbours.attackLocals(vader,  vader.world, true, true);
		AttackInformation choke;
		choke = ForceChokeNeighbours.chokeLocals(vader, vader.world); // gets targets to choke similar to getting targets to attack
		SWActor luke;
		luke = TurnLukeDark.findLuke(vader, vader.world); // gets Luke if luke is on the same cell
		
		if (Math.random() > 0.5 && choke != null) { // 50% chance to choke
			say(getShortDescription() + " uses force to choke " + choke.entity.getShortDescription());
			scheduler.schedule(choke.affordance, vader, 1);
		}
		else if (luke != null && Math.random() > 0.5) { // 50% chance to try turn luke to dark side
			TurnDark turndark = new TurnDark(luke, messageRenderer);
			scheduler.schedule(turndark, vader, 1);
		}
		
		else if (attack != null) {
			say(getShortDescription() + " suddenly looks sprightly and attacks " +
		attack.entity.getShortDescription());
			scheduler.schedule(attack.affordance, vader, 1);
		}
		else {
			ArrayList<Direction> possibledirections = new ArrayList<Direction>();

			// build a list of available directions
			for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
				if (SWWorld.getEntitymanager().seesExit(this, d)) {
					possibledirections.add(d);
				}
			}

			Direction heading = possibledirections.get((int) (Math.floor(Math.random() * possibledirections.size())));
			say(getShortDescription() + " is heading " + heading + " next.");
			Move myMove = new Move(heading, messageRenderer, world);

			scheduler.schedule(myMove, this, 1);
		}
	}
}
