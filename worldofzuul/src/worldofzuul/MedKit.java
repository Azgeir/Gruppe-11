/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worldofzuul;

import java.io.Serializable;

/**
 * This class represents a medical kit in the game. The item is used to increase
 * the player's health. The class extends the Item superclass and implements the
 * Serializable interface.
 * 
 * @author laurabrinkholmjustesen
 */

public class MedKit extends Item implements Serializable {
    /**
     * Data field.
     * healthAmount: the number of health points added to the player's health
     * when the medical kit is used.
     */
    private int healthAmount;
    
    /**
     * This constructor creates a MedKit object with a default health amount of
     * 5. The constructor calls the superclass's constructor via constructor
     * chaining.
     */
    MedKit() {
        super(5, "medkit");
        this.healthAmount = 5;
    }
    
    /**
     * This constructor creates a MedKit object with a specified health amount.
     * This constructor is not currently used, but is left for future
     * development, as it allows the creation of medical kits with varying
     * healing capabilities. The constructor calls MedKit's no-arg constructor.
     * 
     * @param healthAmount, which indicates the health amount of the medical
     * kit.
     */
    MedKit(int healthAmount) {
        this();
        this.healthAmount = healthAmount;
    }
    
    /**
     * This method returns the health amount of the medical kit.
     * 
     * @return healthAmount, which indicates the healing capabilities of the
     * medical kit.
     */
    int getHealthAmount() {
        return this.healthAmount;
    }
    
    /**
     * This method is called when the player uses the medical kit. The method
     * overrides the use() method in the Item class. It increases the health
     * of the player, based on the health amount of the medical kit, and removes
     * the medical kit from the player's inventory.
     * 
     * @param currentCharacter, which is an instance of Hero that represents the
     * player who uses the medical kit.
     * 
     * @return 0, because the act of using the medical kit does not affect the
     * initiative of Zuul.
     */
    @Override
    double use(Hero currentCharacter){
        // Increase the player's health by the health amount of the medical kit.
        currentCharacter.setHealth(currentCharacter.getHealth() + 
            this.healthAmount);
        
        /*
        If the medical kit increases the player's health to more than 10
        (which is the maximum health), reset the health to 10.
        */
        if (currentCharacter.getHealth() > 10) {
            currentCharacter.setHealth(10);
        }
        
        // Remove the medical kit from the player's inventory.
        currentCharacter.getInventory().removeItem(this);
        
        // Print message to player.
        System.out.println("You healed youself with a medkit, but it is now "
            + "spent.");
        
        /*
        Increase the player's initiative as a result of using the medical kit.
        */
        currentCharacter.setCharacterInitiative(
            currentCharacter.getCharacterInitiative() + 
            8 * currentCharacter.getSpeedFactor());
        
        // Return 0, as the action does not affect Zuul's initiative.
        return 0;
    }
}