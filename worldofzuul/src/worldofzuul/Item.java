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

// This class represents an item in the game.
public class Item {
    // Data fields:
    private int weight; // The weight of the item
    private String name; // The name of the item
    private String useDescription; // Describes how the item is used
    
    // This constructor creates an item with the specified weight and name
    Item(int weight, String name, String useDescription) {
        this.weight = weight;
        this.name = name;
        this.useDescription = useDescription;
    }

    // This constructor creates an item with the specified weight and name
    Item(int weight, String name) {
        this.weight = weight;
        this.name = name;
    }

    // This is an empty no-arg constructor
    Item() {    
    }
    
    // This method returns the weight of the item
    int getWeight() {
        return this.weight;
    }
    
    // This method returns the name of the item
    String getName() {
        return this.name;
    }
    
    // This method prints a description of how the item is used
    double use(Hero currentCharacter) {
//        Hero currentCharacter = (Hero)currentCharacter;
        if (useDescription == null) {
            if (currentCharacter.getCurrentRoom().getHasCharacter("Zuul")) {
                System.out.println("You throw the " + this.name + " in blind \n"
                + "panic. It doesn't have any effect.");
            } else {
                System.out.println("You wave around the " + this.name + ",\n"
                        + "seemingly with no purpose.");
            }
        } else {
            if (currentCharacter.getCurrentRoom().getHasCharacter("Zuul")) {
                System.out.println("You use the " + this.name + " to " + this.useDescription + ". It has no effect on the zuul");                
            } else {
                System.out.println("You use the " + this.name + " to " + this.useDescription);
            }
        }
        currentCharacter.setCharacterInitiative(currentCharacter.getCharacterInitiative()+3*currentCharacter.getSpeedFactor());
        return 0;
    }
    
    // This method returns a description of the possible use of the item
    void getDescription() {
        System.out.println("This is a " + this.name + ". You can use this to " + this.useDescription);
    }
}