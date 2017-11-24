/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import java.io.Serializable;

/**
 *
 * @author laurabrinkholmjustesen
 */

// This class represents a MedKit item.
public class MedKit extends Item implements Serializable {
    // Data fields:
    private int healthAmount; // Health points added to player's health when used
    
    // (£) This constructor creates an instance of MedKit with a specified health amount
    MedKit(int healthAmount) {
        super(15, "MedKit", "heal yourself.");
        this.healthAmount = healthAmount;
    }
    
    // (£) This constructor creates an instance of MedKit with a default health amount
    MedKit() {
        super(5, "medkit", "heal yourself.");
        this.healthAmount = 5;
    }
    
    // This method returns the health amount of the MedKit
    int getHealthAmount() {
        return this.healthAmount;
    }
    
    // This method is called when the medkit is used
    @Override
    double use(Hero currentCharacter){
        // tempCharacter is set to current character
//        Hero currentCharacter = (Hero)currentCharacter;
        // Player's health is increased by the health amount of the the medkit.
        currentCharacter.setHealth(currentCharacter.getHealth() + this.healthAmount);
        if (currentCharacter.getHealth() > 10)
            currentCharacter.setHealth(10);
        // Medkit is removed from the player's inventory.
        currentCharacter.getInventory().removeItem(this);
        // Print message to player
        System.out.println("You healed youself with a medkit, but it is now spent.");
        currentCharacter.setCharacterInitiative(currentCharacter.getCharacterInitiative()+8*currentCharacter.getSpeedFactor());
        return 0;
    }
}
