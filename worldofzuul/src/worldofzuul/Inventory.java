/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worldofzuul;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * This class represents an inventory in the game. The class implements the
 * interface Serializable.
 * 
 * @author Aske Wulf
 */

public class Inventory implements Serializable {

    /**
     * Data fields.
     * inventory: a HashMap that represents the items in the inventory. The keys
     * are Strings that identify the item, while the values are ArrayLists of
     * Item objects.
     * maxWeight: maximum weight of an inventory. This is mostly used for
     * player's whose inventory has limited capacity.
     * totalWeight: current weight of the inventory.
     */
    private HashMap<String, ArrayList<Item>> inventory;
    private int maxWeight;
    private int totalWeight = 0;

    /**
     * This constructor creates an Inventory object with a default maximum
     * weight. This constructor is called when a room is created in the game.
     * The maximum weight is set to the maximum positive integer, as the room
     * should have a practically unlimited capacity.
     */
    Inventory() {
        this.maxWeight = Integer.MAX_VALUE;
        this.inventory = new HashMap<>();
    }
    
    /**
     * This constructor creates an Inventory object with the specified maximum
     * weight. This constructor is called when a player is created, as the
     * player has a specified limited inventory capacity.
     * 
     * @param maxWeight maximum weight of the inventory.
     */
    Inventory(int maxWeight) {
        this.maxWeight = maxWeight;
        this.inventory = new HashMap<>();
    }

    //Changed it to return true if successfully added the item and false otherwise
    boolean addItem(Item item) {
        //The first (outer) if statement checks if the item would put the
        //current weight of the inventory above the maximum weight and prints
        // "You cannot carry that" if it would.
        if ((totalWeight + item.getWeight()) <= maxWeight) {
            //The inner if statement checks if the inventory has an ArrayList for that
            //exact item and if it doesnt, creates one and adds the item.
            //It just adds the item to the list if the list already exists
            if (!inventory.containsKey(item.getName())) {
                ArrayList<Item> list = new ArrayList<>();
                inventory.put(item.getName(), list);
                list.add(item);
                this.totalWeight += item.getWeight();
                return true;
                //System.out.println(list.size());
            } else {
                inventory.get(item.getName()).add(item);
                this.totalWeight += item.getWeight();
                return true;
            }
        }
        else {
        }
        return false;
    }

    int addItem(Item item, int number) {
        int counter = 0;
        for (int i = 0; i < number; i++) {
            if(this.addItem(item)){
                counter++;
            }    
        }
        return counter;
    }

    void removeItem(Item item) {
        //Checks if the inventory has the parsed item and if it does, removes it
        if (inventory.containsKey(item.getName())) {
            if (inventory.get(item.getName()).contains(item)) {
                inventory.get(item.getName()).remove(item);
                this.totalWeight -= item.getWeight();
            }

            //Removes the key if the ArrayList is empty
            if (inventory.get(item.getName()).isEmpty()) {
                inventory.remove(item.getName());
            }
        } else {
            LogicFacade.appendMessage("You don't have that item.");
        }
    }

    int removeItem(Item item, int number) {
        //Checks if the inventory has the parsed item and if it does, removes it
        int counter = 0;
        for (int i = 0; i < number; i++) {
            this.removeItem(item);
            counter++;
        }
        return counter;
    }

    // iterates all items in the ArrayList corresponding to the key itemName,
    // when an element in the ArrayList is found that isn't null it is returned
    Item getItem(String itemName) {
        Item returnItem = null;
        if (this.inventory.size() != 0) {
            if (this.inventory.containsKey(itemName)) {
                for (Item item : this.inventory.get(itemName)) {
                    if (item != null) {
                        returnItem = item;
                    }
                }
            } else {
            }
        }

        return returnItem;
    }
    
    int getNumberOfItems(String itemName){
        if(!this.inventory.isEmpty()){
            if(this.inventory.containsKey(itemName)){
                return this.inventory.get(itemName).size();
            }
        }
        return -1;
    }

    //This method calculates the total weight of the inventory
    int getTotalWeight() {
        //Creates a set with the entries of the hashmap
        Set<HashMap.Entry<String, ArrayList<Item>>> entrySet = inventory.entrySet();
        int total = 0; //Declares and initializes an integer total
        //System.out.println("Trying to get total weight");
        for (HashMap.Entry<String, ArrayList<Item>> entry : entrySet) {
            //Calculates the total weight of the inventory, one list at a time
            //stack = entry.getValue().size(); Not sure if this is important xD
            //System.out.println(stack);
            for (Item item : entry.getValue()) {
                total += item.getWeight();
                //System.out.println(total);
            }
        }
        //Sets the variable totalWeight to the total it just calculated
        totalWeight = total;
        return total;
    }

    String showItems() {
        //System.out.println("Trying to show items");
        //Creates a set with the entries of the hashmap
        String returnString = "";
        Set<HashMap.Entry<String, ArrayList<Item>>> entrySet = inventory.entrySet();
        for (HashMap.Entry<String, ArrayList<Item>> entry : entrySet) {
            int stack = entry.getValue().size();//Checks the length of each itemlist
            //System.out.println(stack); 
            //System.out.println("I'm in the loop");
            //Prints the name of each list and the length of the list
            returnString += stack + " " + entry.getKey()
                    + ((stack > 1) ? "s" : "") + "\n";
        }
        return returnString;
    }
    
    Set<String> listItems(){
        return this.inventory.keySet();
    }

    int getMaxWeight() {
        return maxWeight;
    }

}
