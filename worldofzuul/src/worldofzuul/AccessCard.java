/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worldofzuul;

/**
 * This class represents an access card. The item is used to lock and unlock
 * doors in the game. The class extends the superclass Item.
 * 
 * @author HCHB
 */

public class AccessCard extends Item {
    
    /**
     * This constructor creates an instance of AccessCard with default weight, 
     * name, and use description via constructor chaining.
     */
    AccessCard (){
        super(5, "accesscard", "lock and unlock doors");
    }
    
    /**
     * This method is called when the player tries to use the access card via
     * the "use" command. The method prints a message and increases the
     * character's initiative (because it takes time to wave around the access
     * card).
     * 
     * @param currentCharacter is an instance of Hero, which represents the 
     * player.
     * 
     * @return 0, because this action does not affect Zuul's initiative.
     */
    @Override // Overridden from Item class
    double use(Hero currentCharacter){
        System.out.println("You wave the access card around.");
        currentCharacter.setCharacterInitiative(
            currentCharacter.getCharacterInitiative() + 3 * 
            currentCharacter.getSpeedFactor());
        return 0;
    }
}
