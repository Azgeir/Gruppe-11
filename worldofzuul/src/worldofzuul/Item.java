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
    public Item(int weight, String name, String useDescription) {
        this.weight = weight;
        this.name = name;
        this.useDescription = useDescription;
    }
    
    // This is an empty no-arg constructor
    public Item() {    
    }
    
    // This method returns the weight of the item
    public int getWeight() {
        return this.weight;
    }
    
    // This method returns the name of the item
    public String getName() {
        return this.name;
    }
    
    // This method prints a description of how the item is used
    public void use() {
        System.out.println("You use the " + this.name + " to " + this.useDescription);
    }
    
    // This method returns a description of the possible use of the item
    public void getDescription() {
        System.out.println("This is a " + this.name + ". You can use this to " + this.useDescription);
    }
}