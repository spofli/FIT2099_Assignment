package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.entities.actors.Stormtrooper;

/**
 * Command to call for backup
 * 
 */
public class CallBackup extends SWAction {

	public CallBackup(MessageRenderer m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * Method creates a new stormtrooper on the position of the stormtrooper
	 * who used this action
	 * @param SWActor a a is  the stormtrooper who called this action
	 */
	@Override
	public void act(SWActor a) {
		SWLocation location = a.getWorld().getEntityManager().whereIs(a);
		Stormtrooper backup = new Stormtrooper(100, 20, messageRenderer, a.getWorld());
		backup.setSymbol("S");
		SWAction.getEntitymanager().setLocation(backup, location);
	}
		// TODO Auto-generated method stub
		

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return " call for backup";
	}
}
