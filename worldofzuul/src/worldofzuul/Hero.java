/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author HCHB
 */
public class Hero extends Character {

    private int health = 10;
    private Inventory inventory;

    /*
    £
     */
    public Hero() {
        this.inventory = new Inventory(100);
    }

    public Hero(Room currentRoom, String name) {
        super(currentRoom, name);
        this.inventory = new Inventory(100);
    }

    public Hero(int health, int capacity, Room currentRoom, String name) {
        this(currentRoom, name);
        this.health = health;
        this.inventory = new Inventory(capacity);
    }

    //£
    @Override
    public void pickUp(Command command) {
        String itemName = command.getSecondWord();
        Item item = this.getCurrentRoom().getInventory().getItem(itemName);
        if (item == null) {
            System.out.println("The room doesn't contain that item.");
        } else {
            boolean itemAdded = this.inventory.addItem(item);
            if (itemAdded) {
                this.getCurrentRoom().getInventory().removeItem(item);
            } else {
                System.out.println("You can't carry that much");
            }
        }

        this.speedFactorCalculation();
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
    }

    //£
    @Override
    public void dropItem(Command command) {
        String itemName = command.getSecondWord();
        Item item = this.inventory.getItem(itemName);
        if (item != null) {
            boolean itemAdded = this.getCurrentRoom().getInventory().addItem(item);
            if (itemAdded) {
                this.inventory.removeItem(item);
            } else {
                System.out.println("There isn't room in this room for that item");
            }
        } else {
            System.out.println("You don't have such an item");
        }

        this.speedFactorCalculation();
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
    }

    // £ Initiative
    @Override
    public void look(Command command) {
        // Print hero's inventory
        System.out.println("There is the following in the room:\n" + this.getCurrentRoom().getInventory().showItems());
        // Print detailed description of room
        System.out.println(this.getCurrentRoom().getDetailedDescription());
        // Print status of exits
        System.out.println(this.getCurrentRoom().getLockedExitString());
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
    }

    //£ should monster be called zuul
    @Override
    public void peek(Command command) {

        String direction = command.getSecondWord();

        boolean zuulNearby = false;
        for (Room neighbor : this.getCurrentRoom().getExits().values()) {
            if (neighbor.getHasCharacter("Zuul")) {
                System.out.println("Zuul is " + neighbor.getShortDescription());
                zuulNearby = true;
            }
        }

        if (this.getCurrentRoom().getExits().keySet().size() > 2) {
            Room neighbor = this.getCurrentRoom().getExit(direction);
            if (neighbor.getExit(direction).getHasCharacter("Zuul")) {
                System.out.println("Zuul is " + neighbor.getExit(direction).getShortDescription());
                zuulNearby = true;
            }
        }

        if (!zuulNearby) {
            System.out.println("There is no Zuul nearby");
        }

        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
    }

    //£
    @Override
    public void lock(Command command) {
        String direction = command.getSecondWord();
        boolean lock = true;
        boolean directionExists = false;
        
        for (String exit : this.getCurrentRoom().getExits().keySet()){
            if (direction.equals(exit)){
                this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
                this.lockUnlock(direction, lock);
            directionExists = true;
            }
        }
        if (!directionExists) {
            System.out.println("there isn't any exit by that name");
            
        }
    }

    //£
    @Override
    public void unlock(Command command) {
        String direction = command.getSecondWord();
        boolean lock = false;
        boolean directionExists = false;
        
        for (String exit : this.getCurrentRoom().getExits().keySet()){
            if (direction.equals(exit)){
                this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
                this.lockUnlock(direction, lock);
            directionExists = true;
            }
        }
        if (!directionExists) {
            System.out.println("there isn't any exit by that name");
            
        }
    }

    //£
    @Override
    public double use(Command command) {
        
        if (command.hasSecondWord()) {
            String itemName = command.getSecondWord();
            Item item = this.getInventory().getItem(itemName);
            if (item != null) {
                double initiativeReduction = item.use(this);
                this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
                return initiativeReduction;
            }
            else {
                this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
                System.out.println("You don't have any such item");
            }
        }
        else {
            System.out.println("You have to select something to use");
        }
        
        return 0;
    }

    //£ characterInitiative
    public void seeInventory(Command command) {
        this.inventory.showItems();
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
    }

    private void speedFactorCalculation() {
        double newSpeedFactor = 1 + (this.inventory.getTotalWeight() / this.inventory.getMaxWeight()) / 2;
        this.setSpeedFactor(newSpeedFactor);
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public Command getCommand(CommandWords commands) {
        // Declare a String variable for the input
        String inputLine;

        Scanner reader = new Scanner(System.in);

        // Set words 1 and 2 to null
        String word1 = null;
        String word2 = null;
        String word3 = null;

        // Print "> " to prompt user input
        System.out.print("> ");

        // Use Scanner to read input line from user
        inputLine = reader.nextLine();

        // Create a Scanner called tokenizer based on inputLine
        Scanner tokenizer = new Scanner(inputLine);
        // If the input line has a first word, assign it to word1
        if (tokenizer.hasNext()) {
            word1 = tokenizer.next();
            // If the input line has a second word, assign it to word2
            if (tokenizer.hasNext()) {
                word2 = tokenizer.next();
                //if the input line has a third word assign it to word3
                if (tokenizer.hasNext()) {
                    word3 = tokenizer.next();
                }
            }
        }

        // Create a Command object based on words 1 and 2, and return the command.
        return new Command(commands.getCommandWord(word1), word2, word3);
    }

    private void lockUnlock(String direction, boolean lock) {

        HashMap<String, Boolean> lockedExits = this.getCurrentRoom().getLockedExits();
        String getName = this.getCurrentRoom().getName();
        lockedExits.put(direction, Boolean.TRUE);
        // first try at creating a lock funktion
        if (lockedExits.keySet().size() > 2) {
            this.getCurrentRoom().getLockedExits().put(direction, lock);
            this.getCurrentRoom().getExit(direction).getLockedExits().put(this.getCurrentRoom().getName(), lock);

        } else {
            this.getCurrentRoom().getLockedExits().put(direction, lock);

            HashMap<String, Boolean> templockExits = new HashMap<>();
            templockExits.putAll(this.getCurrentRoom().getLockedExits());
            templockExits.remove(direction);
            String direction2 = (String) templockExits.keySet().toArray()[0];
            this.getCurrentRoom().getExit(direction).getLockedExits().put(direction2, lock);
        }
    }
    
    //£
    @Override
    public double activate(Command command){
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
        
        if (command.getSecondWord().equals("reactor")) {
            if (this.getCurrentRoom().getName().equals("reactor")) {
                if (this.getCurrentRoom().getHasCharacter("TechDude")) {
                    System.out.println("You activated the reactor. It will detonate in 10 turns");
                    return (this.getCharacterInitiative()+50);
                }
                else {
                    System.out.println("You need the TechDude to do this");
                    return Double.MAX_VALUE;
                }
            } else {
                System.out.println("There is no reactor in this room");
                return Double.MAX_VALUE;
            }
        } else {
            System.out.println("Activate what?");
            return Double.MAX_VALUE;
        }
        
        
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    
}
