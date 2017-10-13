/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import java.util.Map;
import java.util.Scanner;
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

    public Hero(Room currentRoom) {
        super(currentRoom);
        this.inventory = new Inventory(100);
    }

    public Hero(int health, int capacity, Room currentRoom) {
        this(currentRoom);
        this.health = health;
        this.inventory = new Inventory(capacity);
    }

    //£
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
    public void lookAround(Command command) {
        // Print hero's inventory
        System.out.println("There is the following in the room:\n" + this.getCurrentRoom().getInventory().showItems());
        // Print detailed description of room
        System.out.println(this.getCurrentRoom().getDetailedDescription());
        // Print status of exits
        System.out.println(this.getCurrentRoom().getLockedExitString());
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
    }

    //£ should monster be called zuul
    public void peek(Command command) {

        String direction = command.getSecondWord();

        boolean zuulNearby = false;
        for (Room neighbor : this.getCurrentRoom().getExits().values()) {
            if (neighbor.getHasZuul()) {
                System.out.println("Zuul is " + neighbor.getShortDescription());
                zuulNearby = true;
            }
        }

        if (this.getCurrentRoom().getExits().keySet().size() > 2) {
            Room neighbor = this.getCurrentRoom().getExit(direction);
            if (neighbor.getExit(direction).getHasZuul()) {
                System.out.println("Zuul is " + neighbor.getExit(direction).getShortDescription());
                zuulNearby = true;
            }
        }

        if (!zuulNearby) {
            System.out.println("There is no Zuul nearby");
        }

        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
    }

    public void lock(Command command) {

    }

    public void unlock(Command command) {
        
    }
    public int use(Command command) {

        System.out.println("The use method cannot be used");
        return -1;
    }

    public void seeInventory(Command command) {
        this.inventory.showItems();
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
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();
            // If the input line has a second word, assign it to word2
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();
                //if the input line has a third word assign it to word3
                if(tokenizer.hasNext()) {
                    word3 = tokenizer.next();
                }
            }
        }

        // Create a Command object based on words 1 and 2, and return the command.
        return new Command(commands.getCommandWord(word1), word2, word3);
    }
}
