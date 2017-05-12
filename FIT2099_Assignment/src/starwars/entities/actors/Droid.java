package starwars.entities.actors;

import edu.monash.fit2099.simulator.matter.Affordance;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Oil;
import starwars.actions.Own;
import starwars.actions.Repair;
import starwars.actions.Attack;
import starwars.actions.Disassemble;

public abstract class Droid extends SWActor {

	private String name;
	protected SWActor owner;
	private Boolean isDisabled;
	/**
	 * Abstract class for creating a <code>Droid</code>.  
	 * 
	 * <code>Droids</code> will follow their owners if they have one
	 * (Move until they are on the same square)
	 * <code>Droids</code> will also attack any enemies not on the owners team.
	 * 
	 * If the <code>Droid</code> has no owner, it will perform it's default behavior each turn.
	 * 
	 * <code>Droids</code> can be oiled.
	 * <code>Droids</code> become disabled when their hitpoints drop to 0 and below
	 * A Disabled <code>Droid</code> can be repaired/disassembled
	 * 
	 * Unlike other actors, <code>Droids</code> can change teams - follow their owners
	 * <code>Droids</code> begin with a NEUTRAL team. (Will not attacked by other entities)
	 * 
	 * @param hitpoints
	 *            the number of hit points of this Droid. If this
	 *            decreases to below zero, the Droid becomes disabled
	 * @param name
	 *            this Droid's's name. Used in displaying descriptions.
	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>Droid</code> belongs to
	 * 
	 */
	public Droid(int hitpoints, String name, MessageRenderer m, SWWorld world) {
		super(Team.NEUTRAL, hitpoints, m, world);
		// TODO Auto-generated constructor stub
		this.name = name;
		
		//Begin with no owner
		this.owner = null;
		
		//Set disabled to False at start
		this.isDisabled = false;
		
		//Droids can be oiled!
		SWAffordance Oil = new Oil(this,m);
		this.addAffordance(Oil);
		
		//Droids can be taken ownership of
		SWAffordance Own = new Own(this, m);
		this.addAffordance(Own);	
	}
	
	/**
	 * Method to disable the droid.
	 * Set isDisabled to true.
	 * Adds <code>Repair</code> and <code>Disassemble</code> Affordances.
	 * Removes <code>Oil</code> Affordance, <code>Attack</code> Affordance
	 */
	public void disable() {
		isDisabled = true;
		for (Affordance a: this.getAffordances()) {
			if (a instanceof Oil || a instanceof Attack) {
				this.removeAffordance(a);
			}
		}
		SWAffordance repair = new Repair(this, this.messageRenderer);
		this.addAffordance(repair);
		SWAffordance disassemble = new Disassemble(this, this.messageRenderer);
		this.addAffordance(disassemble);
	}
	/**
	 * Method to enable the droid
	 * (Called after repairing!)
	 * Adds <code>Oil</code> Affordance
	 * Adds <code>Attack</code> Affordance
	 * Removes <code>Repair</code> & <code>Disassemble</code> Affordance
	 */
	public void enable() {
		isDisabled = false;
		for (Affordance a: this.getAffordances()) {
			if (a instanceof Repair || a instanceof Disassemble)  {
				this.removeAffordance(a);
			}
		}
		SWAffordance oil = new Oil(this, this.messageRenderer);
		this.addAffordance(oil);
		SWAffordance attack = new Attack(this, this.messageRenderer);
		this.addAffordance(attack);
	}
	/**
	 * Code to change the owner of this <code>Droid</code>
	 * @param the <code>SWActor</code> that take ownership of this <code>Droid</code>
	 */
	public void changeOwner(SWActor actor) {
	//Changes the droid's owner
		owner = actor;
	}
	/**
	 * @return the <code>SWActor</code> that take ownership of this <code>Droid</code>
	 */
	public Object getOwner() {
		return owner;
	}
	/**
	 * @return True/False if <code>Droid</code> is disabled or not
	 */
	public Boolean getIsDisabled() {
		return isDisabled;
	}
	@Override
	// Special only to droids, if disabled, will show in its description
	public String getShortDescription() {
		if (getIsDisabled()) {
			return name + " the droid[disabled]";
		} else {
			return name + " the droid";
		}
	}


}

