package starwars.entities.actors.behaviors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.actions.Disassemble;
import starwars.actions.Oil;
import starwars.actions.Repair;
import starwars.actions.Take;
import starwars.entities.DroidParts;
import starwars.entities.actors.Droid;

/**
 * Behavior for <code>R2D2</code> to perform special repair droid actions.
 * 
 * @author Seolhyun95
 *
 */
public class RepairDroid {

	/**
	 * Checks the <code>Location</code> of the <code>SWActor</code>
	 * for <code>Droids</code> to perform special repair actions on or
	 * for <code>DroidParts</code> to <code>Take</code>
	 * 
	 * @param actor The <code>SWActor</code> performing the repair droid action
	 * @param world The <code>SWWorld</code> the </code>SWActor</code> is in
	 * @return AttackInformation the <code>Entity</code> and <code>Affordance</code>
	 */
	public static AttackInformation repairAction(SWActor actor, SWWorld world) {
		SWLocation location = world.getEntityManager().whereIs(actor);
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		List<SWEntityInterface> entities = em.contents(location);
		
		// look for a repairDroidAction
		// This in priority order is:
		// (1) Repair droid if holding parts
		// (2) Oil droid
		// (3) Disassemble droid
		// (4) Pick up droidparts
		ArrayList<AttackInformation> repairActions = new ArrayList<AttackInformation>();
		
		// Repairing
		if (actor.getItemCarried() instanceof DroidParts) {
			for (SWEntityInterface e: entities) {
				if ( e!= actor && (e instanceof Droid)) {
					for (Affordance a : e.getAffordances()) {
						if (a instanceof Repair) {
							repairActions.add(new AttackInformation(e, a));
						}
					}
				}
			}
		}
		// Get random repair target if exists
		if (repairActions.size() > 0) {
				return getRepairAction(repairActions);
			}
		// Oiling
		for (SWEntityInterface e: entities) {
			if (e instanceof Droid) {
				if (e.getHitpoints() < ((SWActor) e).getMaxHitpoints()) {
					for (Affordance a : e.getAffordances()) {
						if (a instanceof Oil) {
							repairActions.add(new AttackInformation(e, a));
						}
					}
				}
			}
		}
		// Get random oil target if exists
		if (repairActions.size() > 0) {
			return getRepairAction(repairActions);
		}
		// Disassembling
		for (SWEntityInterface e: entities) {
			if (e instanceof Droid) {		
				for (Affordance a : e.getAffordances()) {
					if (a instanceof Disassemble) {
						repairActions.add(new AttackInformation(e, a));
					}
				}
						
			}
		}
		// Get random disassemble target if exists
		if (repairActions.size() > 0) {
			return getRepairAction(repairActions);
		}
		
		// Picking up Parts
		for (SWEntityInterface e: entities) {
			if (e instanceof DroidParts) {		
				for (Affordance a : e.getAffordances()) {
					if (a instanceof Take) {
						repairActions.add(new AttackInformation(e, a));
					}
				}
						
			}
		}
		// Get random part to pick up if exists
		if (repairActions.size() > 0) {
			return getRepairAction(repairActions);
		}
		return null;
			
	}
	// Method to choose one possible target at random
	public static AttackInformation getRepairAction(ArrayList<AttackInformation> actionsList) {
			return actionsList.get((int) (Math.floor(Math.random() * actionsList.size())));
		}
	}
		

			


	
	

