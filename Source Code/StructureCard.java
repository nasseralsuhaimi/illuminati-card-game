import java.util.ArrayList;
import java.util.HashMap;

public abstract class StructureCard extends Card{
	public static enum Arrow{
		TOP, BOTTOM, LEFT, RIGHT;
	}
	
	//Class Attributes
	protected int power;
	protected int transferablePower;
	protected int income;
	protected Arrow inwardArrow;
	protected ArrayList<Arrow> outwardArrows;
	protected HashMap<Arrow, GroupCard> children = new HashMap<Arrow, GroupCard>();
	
	//Accessor Methods
	
	public int Power(){
		return this.power;
	}
	
	public int TransferablePower(){
		return this.transferablePower;
	}
	
	public int Income(){
		return this.income;
	}
	
	public String ImagePath(){
		return this.imagePath;
	}
	
	public Arrow InwardArrow() {
		return this.inwardArrow;
	}

	public ArrayList<Arrow> OutwardArrows() {
		return this.outwardArrows;
	}
	
	public HashMap<Arrow, GroupCard> Children(){
		return this.children;
	}
	
	//Constructor Method
	public StructureCard(String name, int power, int transferablePower, int income, String imagePath,
						  Arrow inwardArrow){
		super(name, imagePath);
		
		this.power = power;
		this.transferablePower = transferablePower;
		this.income = income;
		this.inwardArrow = inwardArrow;
	}
	
	//Add child group card
	//Checks if the control arrow exists and is available, then adds the new child card to children.
	//Also sets the child's parent to this card.
	//Returns true if the arrow exists and is available, false otherwise.
	public boolean AddChild(GroupCard card, Arrow arrow){
		if(outwardArrows.indexOf(arrow) >= 0){
			if(children.containsKey(arrow)){
				return false;
			}
			else{
				children.put(arrow, card);
				card.parent = this;
				
				return true;
			}
		}
		return false;
	}
	
	//Remove child group card
	//If a group is at a given control arrow, it is removed from children and true is returned.
	//Also removes this as the child's parent.
	//Otherwise, returns false.
	public boolean RemoveChild(Arrow arrow){
		if(children.containsKey(arrow)){
			GroupCard child = children.remove(arrow);
			child.parent = null;
			
			return true;
		}
		return false;
	}
	
	//Calculates the modifier based on attacker power and defender resistance (or defender power if attack to destroy)
		public int CalculatePowerModifier(GroupCard defendingGroup, Global.AttackType attackType){
			if(attackType == Global.AttackType.DESTROY){
				//TODO - This occurs when Whispering Campaign is active. Make sure this is checked somewhere!
				if(defendingGroup.Power() == 0){
					return this.Power() - defendingGroup.Resistance();
				}else{
					return this.Power() - defendingGroup.Power();
				}
			}else{
				//TODO - Add check for Gun Lobby
				return this.Power() - defendingGroup.Resistance();
			}
		}

	@Override
	public String toString() {
		return "StructureCard [name=" + name + ", power=" + power + ", transferablePower=" + transferablePower
				+ ", income=" + income + ", imagePath=" + imagePath + ", inwardArrow=" + inwardArrow
				+ ", outwardArrows=" + outwardArrows + ", children=" + children + "]" + System.getProperty("line.separator");
	}
	
	
}
