/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worldofzuul;

import java.io.Serializable;

/**
 * This class represents an acid vial. The item is used to attack the Zuul,
 * which gives the player a chance to escape. The class extends the superclass
 * Item and implements the interface Serializable.
 * 
 * @author laurabrinkholmjustesen
 */

public class AcidVial extends Item implements Serializable {
    
    /**
     * Data fields.
     * damageAmount: Damage caused to the player when the acid vial is used.
     * initiativeReduction: Added to Zuul's initiative when used against Zuul.
     */
    private int damageAmount;
    private double initiativeReduction;
    
    /**
     * This constructor creates an acid vial with a default damage amount and 
     * initiative reduction. The constructor calls the constructor from the
     * superclass Item (constructor chaining).
     */
    AcidVial() {
        super(5, "acidvial");
        this.damageAmount = 4;
        this.initiativeReduction = 25;
    }
    
    /**
     * This constructor creates an acid vial with a specified damage amount via
     * constructor chaining. The constructor overwrites the default value of
     * damageAmount.
     * 
     * @param damageAmount caused to player when used.
     */
    AcidVial(int damageAmount) {
        this();
        this.damageAmount = damageAmount;
    }
    
    /**
     * This constructor creates an acid vial with a specified damage amount and
     * initiative reduction via constructor chaining. The constructor overwrites
     * the default values for damageAmount and initiativeReduction.
     * 
     * @param damageAmount caused to player by acid vial when used.
     * @param initiativeReduction added to Zuul's initiative when used against
     * Zuul.
     */
    AcidVial(int damageAmount, int initiativeReduction) {
        this(damageAmount);
        this.initiativeReduction = initiativeReduction;
    }
    
    /**
     * This is a getter method for damageAmount.
     * 
     * @return damageAmount
     */
    int getDamageAmount() {
        return this.damageAmount;
    }
    
    /**
     * This method is called when the player tries to use the acid vial. It
     * reduces the player's health, removes the item from the player's
     * inventory, and increases their initiative (because it takes time to throw
     * an acid vial). If Zuul is in the room, its initiative is also increased,
     * giving the player a chance to escape.
     * 
     * @param currentCharacter is an instance of Hero and represents the player. 
     * 
     * @return initiativeReduction if Zuul is in the room; else it returns 0.
     */
    @Override // Overrides method from the Item class
    double use(Hero currentCharacter){
        // Reduce player's health by acid vial's damage amount
        currentCharacter.setHealth(currentCharacter.getHealth() - 
            this.damageAmount);
        
        // Remove acid vial from player's inventory
        currentCharacter.getInventory().removeItem(this);
        
        // Increase the player's intiative
        currentCharacter.setCharacterInitiative(
            currentCharacter.getCharacterInitiative() +
            1.5 * currentCharacter.getSpeedFactor());
        
        /*
        If Zuul is in the current room, player hits Zuul with the acid vial,
        and Zuul's initiative is increased.
        */
        if (currentCharacter.getCurrentRoom().getHasCharacter("Zuul")) {
            System.out.println("You throw an acid vial at the terrifying Zuul."
                + "\nYou hit it in the face. You were hit\nby a splash of acid,"
                + " but now you have a chance to flee.");
            return this.initiativeReduction;
        }
        /*
        If Zuul is not in the current room, the player throws acid at the wall.
        */
        else {
            System.out.println("For some reason, you throw an acidvial at the"
                + " wall.\nYou where hit by a splash of acid and hurt "
                + "yourself.");
            return 0;
        }
    }
}
