/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Aske Wulf
 */
public class Inventory {
    private HashMap<String, ArrayList<Item>> inventory; //Declares the hashmap "inventory"
    private int stack; //The amount of duplicate items, eg. 2 vials
    private int maxWeight; //The maximum weight, mostly used for the player
    private int totalWeight = 0; //The current weight of the inventory
    
    //Constructor for the inventory, with the maximum weight as a parameter
    //and initializes the hashmap
    public Inventory(int newMaxWeight){
        maxWeight = newMaxWeight;
        inventory = new HashMap<>();
    }
    //No-args constructor for inventory which sets the max weight to the max
    //value for integers
    public Inventory(){
        maxWeight = Integer.MAX_VALUE;
        inventory = new HashMap<>();
    }
    
    public void addItem(Item item){
        //The first (outer) if statement checks if the item would put the
        //current weight of the inventory above the maximum weight and prints
        // "You cannot carry that" if it would.
        if ((totalWeight +item.getWeight()) < maxWeight ){
            //The inner if statement checks if the inventory has an ArrayList for that
            //exact item and if it doesnt, creates one and adds the item.
            //It just adds the item to the list if the list already exists
            if (!inventory.containsKey(item.getName())){
                ArrayList<Item> list = new ArrayList<>();
                inventory.put(item.getName(), list);
                list.add(item);
                System.out.println("Added " + item.getName() + " to the inventory");
                //System.out.println(list.size());
            }
            else{
                inventory.get(item.getName()).add(item);
                System.out.println("Added " + item.getName() + " to the inventory");
            }
        }
        else   
            System.out.println("You cannot carry that");
    }
    
    public void removeItem(Item item){
        //Checks if the inventory has the parsed item and if it does, removes it
        if (inventory.containsKey(item.getName()))
            inventory.remove(item.getName());
        else
            System.out.println("You dont have that item");
    }
    //This method calculates the total weight of the inventory
    public int getTotalWeight(){
        //Creates a set with the entries of the hashmap
        Set<HashMap.Entry<String, ArrayList<Item>>> entrySet = inventory.entrySet();
        int total = 0; //Declares and initializes an integer total
        //System.out.println("Trying to get total weight");
        for (HashMap.Entry<String, ArrayList<Item>> entry: entrySet){
            //Calculates the total weight of the inventory, one list at a time
            //stack = entry.getValue().size(); Not sure if this is important xD
            //System.out.println(stack);
            for (Item i: entry.getValue()){
                total += i.getWeight();
                //System.out.println(total);
            }
        }
        //Sets the variable totalWeight to the total it just calculated
        totalWeight = total;
        return total;
    }
    public void showItems(){
        //System.out.println("Trying to show items");
        //Creates a set with the entries of the hashmap
        Set<HashMap.Entry<String, ArrayList<Item>>> entrySet = inventory.entrySet();
        for (HashMap.Entry<String, ArrayList<Item>> entry: entrySet){
            stack = entry.getValue().size();//Checks the length of each itemlist
            //System.out.println(stack); 
            //System.out.println("I'm in the loop");
            //Prints the name of each list and the length of the list
            System.out.println("You currently have " + stack + " " + entry.getKey() + 
                ((stack > 1)?"s":""));
            
        }   
        
    }
}
