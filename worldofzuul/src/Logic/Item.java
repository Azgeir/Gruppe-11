/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worldofzuul;

import java.io.Serializable;

/**
 * This class represents an item in the game. The class is the superclass of the
 * subclasses AccessCard, AcidVial, Medkit, and USB. The class implements the
 * Serializable interface.
 * 
 * @author laurabrinkholmjustesen
 */

public class Item implements Serializable {
    /**
     * Data fields.
     * weight: the weight of the item
     * name: the name of the item
     * useDescription: describes how the item is used
     */
    private int weight;
    private String name;
    private String useDescription;
    
    /**
     * This constructor creates an Item object with the specified weight and
     * name.
     * 
     * @param weight, the weight of the item.
     * @param name, the name of the item.
     */
    Item(int weight, String name) {
        this.weight = weight;
        this.name = name;
    }
    
    /**
     * This constructor creates an Item object with the specified weight, name,
     * and use description. The constructor uses constructor chaining. 
     * 
     * @param weight, the weight of the item.
     * @param name, the name of the item.
     * @param useDescription, describes how the item is used.
     */
    Item(int weight, String name, String useDescription) {
        this(weight, name);
        this.useDescription = useDescription;
    }

    /**
     * This method returns the weight of the item.
     * 
     * @return weight
     */
    int getWeight() {
        return this.weight;
    }
    
    /**
     * This method returns the name of the item.
     * 
     * @return name
     */
    String getName() {
        return this.name;
    }
    
    /**
     * This method is used when the player uses an item. The method prints a
     * message describing the use of the object and increases the character's
     * initiative as a result of the action. The method is overridden in the
     * subclasses AccessCard, AcidVial, MedKit, and USB.
     * 
     * @param currentCharacter, which is an instance of Hero representing the
     * player.
     * 
     * @return 0, because the action has no effect on Zuul's initiative.
     */
    double use(Hero currentCharacter) {
        /*
        Print message depending on whether or not the item has a use
        description, and whether or not Zuul is in the same room as the player.
        */
        if (useDescription == null) {
            if (currentCharacter.getCurrentRoom().hasCharacter("Zuul")) {
                LogicFacade.appendMessage("You throw the " + this.name + " in blind \n"
                + "panic. It doesn't have any effect.");
            } else {
                LogicFacade.appendMessage("You wave around the " + this.name + ",\n"
                        + "seemingly with no purpose.");
            }
        } else {
            if (currentCharacter.getCurrentRoom().hasCharacter("Zuul")) {
                LogicFacade.appendMessage("You use the " + this.name + " to " 
                    + this.useDescription + ". It has no effect on the Zuul.");                
            } else {
                LogicFacade.appendMessage("You use the " + this.name + " to " 
                    + this.useDescription);
            }
        }
        
        // Increase player's initiative.
        currentCharacter.setCharacterInitiative(
            currentCharacter.getCharacterInitiative() + 
            3 * currentCharacter.getSpeedFactor());
        
        // Return 0, as the action does not affect Zuul's initiative.
        return 0;
    }
}
