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
    
    //Changed it to return true if successfully added the item and false otherwise
    public boolean addItem(Item item){
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
                return true;
                //System.out.println(list.size());
            }
            else{
                inventory.get(item.getName()).add(item);
                System.out.println("Added " + item.getName() + " to the inventory");
                return true;
            }
        }
        else   
            System.out.println("You cannot carry that");
        return false;
    }
    
    public void removeItem(Item item){
        //Checks if the inventory has the parsed item and if it does, removes it
        if (inventory.containsKey(item.getName())){
            if (inventory.get(item.getName()).contains(item))
                inventory.get(item.getName()).remove(item);
            
            //Removes the key if the ArrayList is empty
            if (inventory.get(item.getName()).isEmpty())
                inventory.remove(item.getName());               
        }
        else
            System.out.println("You don't have that item");
    }
    
    // iterates all items in the ArrayList corresponding to the key itemName,
    // when an element in the ArrayList is found that isn't null it is returned
    public Item getItem(String itemName){
        Item returnItem = null;
        if (this.inventory.size()!=0) {
            if (this.inventory.containsKey(itemName)){
                for (Item item : this.inventory.get(itemName)){
                    if (item != null){
                        returnItem = item;
                    }
                }
            }
            else {
            }
        }
        
        return returnItem;
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
            for (Item item: entry.getValue()){
                total += item.getWeight();
                //System.out.println(total);
            }
        }
        //Sets the variable totalWeight to the total it just calculated
        totalWeight = total;
        return total;
    }
    public String showItems(){
        //System.out.println("Trying to show items");
        //Creates a set with the entries of the hashmap
        String returnString = "";
        Set<HashMap.Entry<String, ArrayList<Item>>> entrySet = inventory.entrySet();
        for (HashMap.Entry<String, ArrayList<Item>> entry: entrySet){
            stack = entry.getValue().size();//Checks the length of each itemlist
            //System.out.println(stack); 
            //System.out.println("I'm in the loop");
            //Prints the name of each list and the length of the list
            returnString += stack + " " + entry.getKey() + 
                ((stack > 1)?"s":"") + "\n";
        }
        return returnString;
    }

    public int getMaxWeight() {
        return maxWeight;
    }
    
}
