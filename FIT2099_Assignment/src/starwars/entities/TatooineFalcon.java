package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAffordance;
import starwars.SWEntity;
import starwars.actions.flyToDeathStar;
import starwars.actions.flyToRebelBase;
/**
 * Class to represent the MillenniumFalcon on Tatooine.
 * Can be used by Luke to travel to the RebelBase and DeathStar
 * "Teleports" Luke and whoever is following Luke to said other worlds.
 *	
 * 
 * @author Daryl Ho
 */
public class TatooineFalcon extends SWEntity {

	/**
	 * Constructor for the <code>TatooineFalcon</code> class. This constructor will,
	 * 
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>TatooineFalcon</code></li>
	 * 	<li>Set the short description of this <code>TatooineFalcon</code>
	 * 	<li>Set the long description of this <code>TatooineFalcon</code>
	 * 	<li>Add a <code>flyToRebelBase</code> affordance to this <code>TatooineFalcon</code> 
	 *  <li>Add a <code>flyToDeathStar</code> affordance to this <code>TatooineFalcon</code> 
	 *	<li>Set the symbol of this <code>TatooineFalcon</code> to "M"</li>
	 * </ul>
	 * 
	 * @param 	m <code>MessageRenderer</code> to display messages.
	 */
	public TatooineFalcon(MessageRenderer m) {
		super(m);
		
		SWAffordance deathStar = new flyToDeathStar(this, m);
		this.addAffordance(deathStar);
		
		SWAffordance rebelBase = new flyToRebelBase(this, m);
		this.addAffordance(rebelBase);
		
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
