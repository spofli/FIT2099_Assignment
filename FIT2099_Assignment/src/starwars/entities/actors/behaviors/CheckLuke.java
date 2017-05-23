package starwars.entities.actors.behaviors;


import java.util.List;

import edu.monash.fit2099.simulator.matter.EntityManager;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;

import starwars.entities.actors.Player;

/**
 * Behavior made for Leia to check if Luke is at her location 
 * while she is still imprisoned in the Death Star
 * 
 * @author Daryl Ho
 *
 */
public class CheckLuke{
	/**
	 * Functions checks location of <code>SWActor</code> (Leia) for Luke
	 * Returns luke <code>SWActor</code> object 
	 * for Leia to store so she can follow Luke
	 * 
	 * @param actor the <code>SWActor</code> that is looking for Luke
	 * @param world the <code>SWWorld</code> the <code>SWActor</code> is in
	 * @return
	 */
	public static SWActor findLuke(SWActor actor, SWWorld world) {
		SWLocation location = world.getEntityManager().whereIs(actor);
		EntityManager<SWEntityInterface, SWLocation> em = world.getEntityManager();
		List<SWEntityInterface> entities = em.contents(location);

		// Select Luke from available entities
		SWActor Luke;
		for (SWEntityInterface e : entities) {
			// Figure out entity is Luke
			if (e instanceof Player) {
				Luke = (SWActor) e;	
				return Luke;
			}
		}
		Luke = null;
		return Luke;
	}
}