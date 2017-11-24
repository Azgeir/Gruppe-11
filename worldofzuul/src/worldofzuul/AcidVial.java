/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worldofzuul;

/**
 * This class represents an acid vial. This item is used to attack the Zuul,
 * which gives the player a chance to escape. The class extends the superclass
 * Item.
 * 
 * @author laurabrinkholmjustesen
 */

public class AcidVial extends Item {
    // Data fields:
    private int damageAmount; // Damage caused to the player when the acid vial is used
    private double initiativeReduction; // Amount added to Zuul's initiative when used
    
    /*
    This constructor creates an acid vial with a specified damage amount and
    initiative reduction
    */
    AcidVial(int damageAmount, int initiativeReduction) {
        super(5, "acidvial", "attack something.");
        this.damageAmount = damageAmount;
        this.initiativeReduction = initiativeReduction;
    }

    /*
    This constructor creates an acid vial with a specified damage amount. This
    constructor is not currently used, but it is included to allow for future
    use.
    */
    AcidVial(int damageAmount) {
        super(5, "acidvial", "attack something.");
        this.damageAmount = damageAmount;
    }
    
    /*
    This constructor creates an acid vial with a default damage amount and
    intiative reduction. This constructor is not currently used, but it is
    included to allow for future use.
    */
    AcidVial() {
        super(5, "acidvial", "attack something.");
        this.damageAmount = 4;
        this.initiativeReduction = 23;
    }
    
    // This method returns the damage amount of the acid vial.
    int getDamageAmount() {
        return this.damageAmount;
    }
    
    /**
     * This method is called when the player tries to use the acid vial. It
     * reduces the player's health, removes the item from the player's
     * inventory, and increases their initiative (because it takes time to throw
     * an acid vial. If Zuul is in the room, its initiative is also increased,
     * giving the player a chance to escape.
     * 
     * @param currentCharacter is an instance of Hero. currentCharacter is set
     * to be an instance of Character because...(?) currentCharacter is cast
     * to Hero to access methods such as setHealth() and getHealth() from the
     * Hero class.
     * 
     * @return initiativeReduction if Zuul is in the room; else it returns 0.
     */
    @Override // Overrides method from the Item class
    double use(Character currentCharacter){
        // Cast currentCharacter to Hero
        Hero tempCharacter = (Hero)currentCharacter;
        
        // Reduce player's health by acid vial's damage amount
        tempCharacter.setHealth(tempCharacter.getHealth() - this.damageAmount);
        
        // Remove acid vial from player's inventory
        tempCharacter.getInventory().removeItem(this);
        
        // Increase the player's intiative
        currentCharacter.setCharacterInitiative(
            currentCharacter.getCharacterInitiative() +
            1.5 * currentCharacter.getSpeedFactor());
        
        /*
        If Zuul is in the current room, player hits Zuul with the acid vial,
        and Zuul's initiative is increased.
        */
        if (tempCharacter.getCurrentRoom().getHasCharacter("Zuul")) {
            System.out.println("You throw an acid vial at the terrifying Zuul." +
                "\nYou hit it in the face. You were hit\nby a splash of acid," +
                " but now you have a chance to flee.");
            return this.initiativeReduction;
        }
        // If Zuul is not in the current room, the player throws acid at the wall.
        else {
            System.out.println("For some reason, you throw an acidvial at the " +
                "wall.\nYou where hit by a splash of acid and hurt yourself.");
            return 0;
        }
    }
}
