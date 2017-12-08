/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

/**
 *
 * @author Aske Wulf
 */
public class Item{
    private int weight;
    private String name;
    
    public int getWeight(){
        return weight;
    }
    public String getName(){
        return name;
    }
    public Item(int newWeight, String newName){
        weight = newWeight;
        name = newName;
    }
}
