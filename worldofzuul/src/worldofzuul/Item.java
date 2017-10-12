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
    
    // This constructor creates an item with the specified weight and name
    public Item(int weight, String name) {
        this.weight = weight;
        this.name = name;
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
}