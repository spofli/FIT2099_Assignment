package starwars;
/**
 * Capabilities that various entities may have.  This is useful in <code>canDo()</code> methods of 
 * <code>SWActionInterface</code> implementations.
 *  
 * @author 	ram
 * @see 	SWActionInterface
 * @see     starwars.entities.Fillable
 */

public enum Capability {
	CHOPPER,//CHOPPER capability allows an entity to Chop another entity which has the Chop Affordance
	
	WEAPON,//WEAPON capability allows an entity to Attack another entity which has the Attack Affordance
	
	FILLABLE,//FILLABLE capability allows an entity to be refilled by another entity that
	            // has the Dip affordance.  Any FILLABLE Entity MUST implement the Fillable interface
	
	EMPTIABLE,//EMPTIABLE capability allows an entity to be emptied by another entity that has the 
				// Drink affordance or Oil Affordance. An EMPTIABLE Entity MUST implement the EMPTIABLE interface	
	
	OILABLE, //OILABLE capability allows an entity to be Oil another entity that has the
			    // Oil Affordance. Works together with an EMPTIABLE entity
	
	MECHANIC, // MECHANIC capability allows an entity to Repair entities with the Repair Affordance and
				// Disassemble entities with the Disassemble Affordance
}		
	