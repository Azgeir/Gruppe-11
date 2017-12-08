/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * This class represents an inventory in the game. The class implements the
 * interface Serializable. The class has default visibility as it is not to be
 * used outside of its package.
 * 
 * @author Aske Wulf
 */

class Inventory implements Serializable {

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
        this();
        this.maxWeight = maxWeight;
    }

    /**
     * This method adds the specified item to the inventory if there is room.
     * This is done by either adding a new entry to the HashMap or by adding
     * and item to an existing entry in the HashMap. If there is no room in the
     * inventory for the specified item, the method returns false.
     * 
     * @param item the item to be added to the inventory.
     * 
     * @return true if the item is added to the inventory; false if the item
     * could not be added to the inventory.
     */
    boolean addItem(Item item) {
        /*
        Check if the specified item will increase the total weight of the
        inventory to a value above the maximum weight. If so, skip this block
        of code.
        */
        if ((totalWeight + item.getWeight()) <= maxWeight) {
            /*
            Check if the inventory HashMap has an ArrayList for the specified
            item. If not, create new ArrayList for the item and add it to the
            HashMap.
            */
            if (!inventory.containsKey(item.getName())) {
                // Create new ArrayList.
                ArrayList<Item> list = new ArrayList<>();
                /*
                Add ArrayList to the inventory HashMap with the item name as
                key.
                */
                inventory.put(item.getName(), list);
                // Add the item to the ArrayList in the HashMap.
                list.add(item);
                // Increase total weight of inventory be the weight of the item.
                this.totalWeight += item.getWeight();
                /*
                Return true to indicate that the item has been added to the
                inventory.
                */
                return true;
            }
            /*
            If the item already has an entry in the inventory HashMap, add the
            item to this HashMap.
            */
            else {
                // Add item to corresponding ArrayList in inventory HashMap.
                inventory.get(item.getName()).add(item);
                // Increase total weight by weight of item.
                this.totalWeight += item.getWeight();
                // Return true to indicate that item has been added.
                return true;
            }
        }
        // Return false, if the item cannot be added to the inventory.
        return false;
    }

    /**
     * This method adds a specified number of items to the inventory. This is
     * done by calling the overloaded addItem() method.
     * 
     * @param item the item to be added to the inventory.
     * @param number the number of the specified item to be added to the
     * inventory.
     * 
     * @return the number of items that have been successfully added to the
     * inventory.
     */
    int addItem(Item item, int number) {
        /*
        The counter is used to count how many items that have been successfully
        added to the inventory. It is initially set to 0.
        */
        int counter = 0;
        /*
        Call the addItem() method repeatedly to add items to the inventory. The
        counter is increased when an item is successfully added to the
        inventory.
        */
        for (int i = 0; i < number; i++) {
            // Check if the item is successfully added.
            if(this.addItem(item)){
                // Increment counter.
                counter++;
            }    
        }
        /*
        Return the value of the counter, which indicates the number of items
        successfully added to the inventory.
        */
        return counter;
    }

    /**
     * This method removes the specified item from the inventory.
     * 
     * @param item 
     */
    void removeItem(Item item) {
        //Checks if the inventory has the parsed item and if it does, removes it
        /*
        Check if the inventory contains the specified item by checking if the
        inventory HashMap has an entry with a key equal to the item's name.
        */
        if (inventory.containsKey(item.getName())) {
            /*
            If the item is in the inventory, remove the item from the HashMap,
            and decrease the total weight of the inventory.
            */
            if (inventory.get(item.getName()).contains(item)) {
                // Remove item.
                inventory.get(item.getName()).remove(item);
                // Decrease total weight of inventory.
                this.totalWeight -= item.getWeight();
            }
            /*
            If the ArrayList associated with the item is empty, the ArrayList
            is removed from the inventory HashMap.
            */
            if (inventory.get(item.getName()).isEmpty()) {
                // Remove ArrayList.
                inventory.remove(item.getName());
            }
        }
        /*
        If the inventory does not contain the item, an error message is printed.
        */
        else {
            LogicFacade.appendMessage("You don't have that item.");
        }
    }

    /**
     * This method returns the item specified by the String parameter.
     * 
     * @param itemName String that represents the name of the item.
     * 
     * @return an item from inventory as specified by the string; else, if the
     * item is not found in the inventory, the method returns null.
     */
    Item getItem(String itemName) {
        /*
        returnItem is represents the Item object to be returned at the end of
        the method. This is initially set to null, as the item has not yet been
        found.
        */
        Item returnItem = null;
        // Check if the inventory HashMap is empty.
        if (!this.inventory.isEmpty()) {
            /*
            Check if the inventory HashMap contains an entry for the specified
            String key.
            */
            if (this.inventory.containsKey(itemName)) {
                // Traverse the ArrayList identified by the String key.
                for (Item item : this.inventory.get(itemName)) {
                    /*
                    If the item is not null, update the value of returnItem to
                    item.
                    */
                    if (item != null) {
                        returnItem = item;
                    }
                }
            }
        }
        // Return the item (or null if the item was not found).
        return returnItem;
    }
    
    /**
     * This method returns the number of a certain item (as specified by the
     * String) found in the inventory.
     * 
     * @param itemName String that represents the item's name.
     * 
     * @return the number the specified item found in the inventory. The method
     * returns -1 if the item is not found in the inventory.
     */
    int getNumberOfItems(String itemName){
        // Check if the inventory HashMap is empty.
        if(!this.inventory.isEmpty()){
            // Check if the inventory HashMap contains the specified key String.
            if(this.inventory.containsKey(itemName)){
                /*
                The size of the ArrayList associated with the key indicates the
                number of items of that type. This value is returned.
                */
                return this.inventory.get(itemName).size();
            }
        }
        // Return -1 if the item is not found in the inventory.
        return -1;
    }

    /**
     * This method returns the total weight of the inventory.
     * 
     * @return the data field totalWeight, which represents the total weight of
     * the inventory.
     */
    int getTotalWeight() {
        // Return the total weight of the inventory.
        return this.totalWeight;
    }

    /**
     * This method returns a string that lists the items found in the inventory.
     * 
     * @return a string, which lists the items in the inventory and the number
     * of each item.
     */
    String showItems() {
        // Declare and initialise the return string.
        String returnString = "";
        // Traverse the entries in the inventory HashMap.
        for (HashMap.Entry<String, ArrayList<Item>> entry
            : inventory.entrySet()) {
            /*
            The length of the ArrayList (value in the inventory HashMap)
            indicates the number of a specific item. This is saved in the
            variable stack.
            */
            int stack = entry.getValue().size();
            /*
            Add the item (and the number of items) to the return string. If
            there are multiple items of a specific type, an s is added to 
            indicate plural.
            */
            returnString += stack + " " + entry.getKey()
                    + ((stack > 1) ? "s" : "") + "\n";
        }
        // Return the string of items.
        return returnString;
    }
    
    /**
     * This method returns a set of strings that indicates the items found in
     * the inventory. Each string in the set is a key from the inventory
     * HashMap.
     * 
     * @return 
     */
    Set<String> listItems() {
        return this.inventory.keySet();
    }

    /**
     * This method returns the maximum weight of the inventory.
     * 
     * @return the data field maxWeight, which represents the maximum weight of
     * the inventory.
     */
    int getMaxWeight() {
        return this.maxWeight;
    }
}
