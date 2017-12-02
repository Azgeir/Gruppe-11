/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worldofzuul;

import java.io.Serializable;

/**
 * This class represents a USB item. This item is used by the player to obtain
 * data in the game, which contributes to the total points earned when winning
 * the game. The class extends the superclass Item and implements the interface
 * Serializable.
 * 
 * @author laurabrinkholmjustesen
 */

public class USB extends Item implements Serializable {
    
    /**
     * Data field.
     * dataType: String that indicates the type of data (bio, physics, control, 
     * or null, where null indicates an empty USB).
     * 
     * dataType does not have a setter method as it is only changed internally
     * from the USB class in the use() method.
     */
    private String dataType;
    
    /**
     * This constructor creates an empty USB. The USB has a default weight of 5
     * and a name determined by the number parameter. The constructor calls
     * Item's constructor by constructor chaining.
     * 
     * @param number, used to name the USB by concatenating "USB" with number.
     */
    USB (int number) {
        super(5, "USB" + number);
        this.dataType = null;
    }
    
    /**
     * This method returns the data type stored on the USB.
     * 
     * @return dataType, which indicates the data type stored on the USB (null
     * indicates that the USB is empty).
     */
    String getDataType() {
        return this.dataType;
    }
    
    /**
     * This method is called when the player uses the USB. It updates the data
     * type of the USB depending on the room of the player. If the player is in
     * a room where there is no available data, it prints an error message. The
     * method overrides the use() method in Item.
     * 
     * @param currentCharacter, which is the Hero object that calls the use()
     * method on an item in its inventory.
     * 
     * @return 0, because using the USB has no effect on Zuul's initiative.
     */
    @Override
    double use(Hero currentCharacter) {
        // Create a String variable for the character's current room.
        String roomName = currentCharacter.getCurrentRoom().getName();
        
        /*
        Update the USB's data type depending on the character's current room,
        and print a message indicating the result of the action.
        */
        switch (roomName) {
            case "biolab":
                this.dataType = "bio";
                LogicFacade.appendMessage("Important biological experimental "
                    + "data was saved. The USB is now full.");
                break;
            case "physicslab":
                this.dataType = "physics";
                LogicFacade.appendMessage("Important physics experimental data "
                    + "was saved. The USB is now full.");
                break;
            case "control":
                this.dataType = "control" ;
                LogicFacade.appendMessage("Surveillance records of the Zuul "
                    + "infestation was optained. The USB is now full.");
                break;
            default:
                LogicFacade.appendMessage("There is nowhere to obtain useful "
                    + "data in this room.");
                break;
        }
        
        // Update the character's initiative.
        currentCharacter.setCharacterInitiative(
            currentCharacter.getCharacterInitiative() 
            + 7.5 * currentCharacter.getSpeedFactor());
        
        // Return 0, as the action does not affect Zuul's initiative.
        return 0;
    }
}