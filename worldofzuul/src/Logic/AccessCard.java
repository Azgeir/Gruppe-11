/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// The class is located in the logic layer.
package worldofzuul;

// Import:
import java.io.Serializable;

/**
 * This class represents an access card. The item is used to lock and unlock
 * doors in the game. The class extends the superclass Item and implements the
 * interface Serializable.
 * 
 * Test
 * 
 * @author HCHB
 */

class AccessCard extends Item implements Serializable {
    
    /**
     * This constructor creates an instance of AccessCard with default weight 
     * and name by calling the superclass's constructor via constructor
     * chaining.
     */
    AccessCard () {
        super(5, "accesscard");
    }
    
    /**
     * This method is called when the player tries to use the access card via
     * the "use" command. The method prints a message and increases the
     * character's initiative (because it takes time to wave around the access
     * card). The method overrides the use() method in the Item class.
     * 
     * @param currentCharacter an instance of Hero, which represents the 
     * player.
     * 
     * @return 0 because this action does not affect Zuul's initiative.
     */
    @Override
    double use(Hero currentCharacter) {
        // Print message that indicates use.
        LogicFacade.appendMessage("You wave the access card around.");
        
        // Increase the character's initiaitve.
        currentCharacter.setCharacterInitiative(
            currentCharacter.getCharacterInitiative()
            + 3 * currentCharacter.getSpeedFactor());
        
        // Return 0 as the action does not affect Zuul's initiative.
        return 0;
    }
}
