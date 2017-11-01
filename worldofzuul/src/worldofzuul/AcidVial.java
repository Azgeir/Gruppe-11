/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

/**
 *
 * @author laurabrinkholmjustesen
 */

// This class represents an AcidVial item.
public class AcidVial extends Item {
    // Data fields:
    private int damageAmount; // Damage caused to the player when the acid vial is used
    private double initiativeReduction;
    
    // (£) This constructor creates an acid vial with a specified damage amount
    public AcidVial(int damageAmount, int initiativeReduction) {
        super(5, "acidvial", "attack something.");
        this.damageAmount = damageAmount;
        this.initiativeReduction = initiativeReduction;
    }

    // This constructor creates an acid vial with a specified damage amount
    public AcidVial(int damageAmount) {
        super(5, "acidvial", "attack something."); // Calls constructor for superclass
        this.damageAmount = damageAmount; // Sets damage amount
    }
    
    // (£) This constructor creates an acid vial with a default damage amount
    public AcidVial() {
        this.damageAmount = 4;
        this.initiativeReduction = 15;
    }
    
    // This method returns the damage amount of the acid vial.
    public int getDamageAmount() {
        return this.damageAmount;
    }
    
    // This method is called when the acid vial is used
    @Override // Overrides method from the Item class
    public double use(Character currentCharacter){
        // Set tempCharacter to currentCharacter
        Hero tempCharacter = (Hero)currentCharacter;
        // Reduce player's health by acid vial's damage amount
        tempCharacter.setHealth(tempCharacter.getHealth() - this.damageAmount);
        // Remove acid vial from player's inventory
        tempCharacter.getInventory().removeItem(this);
        
        currentCharacter.setCharacterInitiative(currentCharacter.getCharacterInitiative()+1.5*currentCharacter.getSpeedFactor());
        
        // If Zuul is in the current room, player hits Zuul with the acid vial.
        if (tempCharacter.getCurrentRoom().getHasCharacter("Zuul")) {
            System.out.println("You throw an acidvial at the terrifying Zuul\nYou hit it in the face. You were hit\nby a splash of acid, but now you have a chance to flee.");
            return this.initiativeReduction;
        }
        // If Zuul is not in the current room, the player throws acid at the wall.
        else {
            System.out.println("For some reason, you throw an acidvial at the wall\nYou where hit by a splash of acid and hurt yourself.");
            return 0;
        }
    }
}
