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
    HashMap<String, ArrayList<Item>> inventory;
    private int stack;
    private int maxWeight;
    private int totalWeight;
    
    public void addItem(Item i){
        if ((totalWeight +i.getWeight()) < maxWeight ){
            if (!inventory.containsKey(i.getName())){
                ArrayList<Item> list = new ArrayList<>();
                inventory.put(i.getName(), list);
                list.add(i);
                System.out.println("Added " + i.getName() + " to the inventory");
                //System.out.println(list.size());
            }
            else{
                inventory.get(i.getName()).add(i);
                System.out.println("Added " + i.getName() + " to the inventory");
            }
        }
        else   
            System.out.println("You cant carry that");
    }
    public Inventory(int newMaxWeight){
        maxWeight = newMaxWeight;
        inventory = new HashMap<>();
    }

    public void removeItem(Item i){
        if (inventory.containsKey(i.getName()))
            inventory.remove(i.getName());
    }
    public int getTotalWeight(){
        Set<HashMap.Entry<String, ArrayList<Item>>> entrySet = inventory.entrySet();
        int total = 0;
        //System.out.println("Trying to get total weight");
        for (HashMap.Entry<String, ArrayList<Item>> entry: entrySet){
            stack = entry.getValue().size();
            //System.out.println(stack);
            for (Item i: entry.getValue()){
                total += i.getWeight();
                //System.out.println(total);
            }
        }
        totalWeight = total;
        return total;
    }
    public void showItems(){
        //System.out.println("Trying to show items");
        Set<HashMap.Entry<String, ArrayList<Item>>> entrySet = inventory.entrySet();
        for (HashMap.Entry<String, ArrayList<Item>> entry: entrySet){
            stack = entry.getValue().size();
            //System.out.println(stack); 
            //System.out.println("I'm in the loop");
            System.out.println("You currently have " + stack + " " + entry.getKey() + 
                ((stack > 1)?"s":""));
            
        }   
        
    }
}
