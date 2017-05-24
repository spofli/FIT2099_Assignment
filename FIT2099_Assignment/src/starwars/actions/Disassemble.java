package starwars.actions;

import starwars.SWAffordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWAction;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.entities.DroidParts;
import starwars.entities.actors.Droid;
import starwars.entities.actors.R2D2;

/**
 * An Affordance for disassembling a disabled Droid.
 * 
 * The affordance is offered by entities that can be disassembled(Droids).
 * Only offered when disabled
 *
 * @author Daryl Ho
 */
public class Disassemble extends SWAffordance implements SWActionInterface {

	/**
	 * Constructor
	 * @param theTarget target that is being disassembled
	 * @param m
	 */
	public Disassemble(SWEntityInterface theTarget, MessageRenderer m) {
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
		return a.hasCapability(Capability.MECHANIC);
		}

	@Override
	public void act(SWActor a) {
		// Remove the droid from the map. Goodbye...
		Droid droid = (Droid) target;
		SWAction.getEntitymanager().remove(droid);
		
		if (droid instanceof R2D2) { //lose game if R2D2 is disassembled
			((R2D2)droid).r2isDisassembled();
		}
		
		// Creates droidparts at the location
		DroidParts droidparts = new DroidParts(this.messageRenderer);
		EntityManager<SWEntityInterface, SWLocation> entityManager = SWAction.getEntitymanager();
		entityManager.setLocation((SWEntityInterface)droidparts, entityManager.whereIs(a));
		
		a.say(a.getShortDescription() + " has disassembled " + target.getLongDescription());
		a.say("droidparts have been produced");
	}
	
	@Override
	public String getDescription() {
		return "disassemble  " + target.getShortDescription();
	}
}
