package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAffordance;
import starwars.SWEntity;
import starwars.actions.flyToDeathStar;
import starwars.actions.flyToTatooine;
/**
 * Class to represent the MillenniumFalcon on the RebelBase.
 * Can be used by Luke to travel to Tatooine and DeathStar
 * "Teleports" Luke and whoever is following Luke to said other worlds.
 *	
 * 
 * @author Daryl Ho
 */
public class RebelFalcon extends SWEntity {

	/**
	 * Constructor for the <code>RebelFalcon</code> class. This constructor will,
	 * 
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>RebelFalcon</code></li>
	 * 	<li>Set the short description of this <code>RebelFalcon</code>
	 * 	<li>Set the long description of this <code>RebelFalcon</code>
	 * 	<li>Add a <code>flyToTatooine</code> affordance to this <code>RebelFalcon</code> 
	 *  <li>Add a <code>flyToDeathStar</code> affordance to this <code>RebelFalcon</code> 
	 *	<li>Set the symbol of this <code>MillenniumFalcon</code> to "M"</li>
	 * </ul>
	 * 
	 * @param 	m <code>MessageRenderer</code> to display messages.
	 */
	public RebelFalcon(MessageRenderer m) {
		super(m);
		
		SWAffordance deathStar = new flyToDeathStar(this, m);
		this.addAffordance(deathStar);
		
		SWAffordance tatooine = new flyToTatooine(this, m);
		this.addAffordance(tatooine);
		
		this.setLongDescription("The Millennium Falcon");
		this.setShortDescription("The Millenium Falcon, a spaceship commanded by Han Solo");
		this.setSymbol("M");
	}
	
	@Override 
	public String getShortDescription() {
		return shortDescription;
	}
	
	public String getLongDescription() {
		return longDescription;
	}
}

