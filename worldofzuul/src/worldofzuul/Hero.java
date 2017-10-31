/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

// Imports:
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author HCHB
 */
// This class represents the player
public class Hero extends Character {

    // Data fields:
    private int health = 10; // Hero's health
    private Inventory inventory; // Hero's inventory

    /*
    £
     */
    // This constructor creates a Hero with an inventory of size 100.
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

    //Transfers an Item from the rooms inventory to the characters inventory
    @Override
    public void pickUp(Command command) {
        
        
        if (command.hasSecondWord()) {
            String itemName = command.getSecondWord();
            Item item = this.getCurrentRoom().getInventory().getItem(itemName);
            
            if (item == null) {
                System.out.println("The room doesn't contain that item.");
            } else {
                if (command.hasThirdWord()) {
                    String numberString = command.getThirdWord();
                    
                    if (this.canBeParsedToInt(numberString)) {
                        int number = Integer.parseInt(numberString);
                        int numberAdded = 0;
                        int numberNonExisting = 0;
                        
                        for (int i = 0; i < number; i++) {
                            item = this.getCurrentRoom().getInventory().getItem(itemName);
                            if (item == null){
                                numberNonExisting++;
                            } else {
                                boolean itemAdded = this.inventory.addItem(item);
                                if (itemAdded) {
                                    numberAdded++;
                                    this.getCurrentRoom().getInventory().removeItem(item);
                                } else {
                                }
                            }
                        }
                        if (numberAdded == 0){
                            System.out.println("You can't carry any of that");
                        }
                        else if ((numberAdded < number) && (numberNonExisting == 0) ){
                            System.out.println("You could only pickup " + numberAdded + " " + itemName);
                        }
                        else if (numberNonExisting>0 && (numberNonExisting+numberAdded)==number){
                            System.out.println("You picked up " + numberAdded + " " + itemName + " because there is only " + numberAdded + " in this room");
                        }
                        else {
                            System.out.println("You picked up " + number + " " + itemName);
                        }
                    } else {
                        System.out.println("The third word needs to be an integer");
                    }
                } else {
                    boolean itemAdded = this.inventory.addItem(item);
                    if (itemAdded) {
                        this.getCurrentRoom().getInventory().removeItem(item);
                        System.out.println("You picked up " + item.getName());
                    } else {
                        System.out.println("You can't carry that");
                    }
                }
            }
            
            
        }
        else {
            System.out.println("Pickup what?");
        }
        
        this.speedFactorCalculation();
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
    }
    
    //Transfers an item from the characters inventory to the rooms
    @Override
    public void dropItem(Command command) {
        
        if (command.hasSecondWord()) {
            String itemName = command.getSecondWord();
            Item item = this.inventory.getItem(itemName);
            
            if (command.hasThirdWord()) {
                int number = Integer.parseInt(command.getThirdWord());
                int numberAdded = 0;
                int numberNonExisting = 0;
                
                if (item == null) {
                    System.out.println("You don't have such an item");
                } else {
                    
                    for (int i = 0; i < number; i++) {
                        item = this.inventory.getItem(itemName);
                        if (item == null){
                            numberNonExisting++;
                        } else {
                            boolean itemAdded = this.getCurrentRoom().getInventory().addItem(item);
                            if (itemAdded) {
                                numberAdded++;
                                this.inventory.removeItem(item);
                            } else {
                            }
                        }
                    }
                    if (numberAdded == 0){
                        System.out.println("There isn't room in this room for that");
                    }
                    else if ((numberAdded < number) && (numberNonExisting == 0) ){
                        System.out.println("You could only drop " + numberAdded + " " + itemName + " because there is not room enought for all of them in this room");
                    }
                    else if (numberNonExisting>0 && (numberNonExisting+numberAdded)==number){
                        System.out.println("You dropped " + numberAdded + " " + itemName + " because there is only " + numberAdded + " in your inventory");
                    }
                    else {
                        System.out.println("You dropped " + number + " " + itemName);
                    }
                }
            } else {
                if (item != null) {
                    boolean itemAdded = this.getCurrentRoom().getInventory().addItem(item);
                    if (itemAdded) {
                        this.inventory.removeItem(item);
                        System.out.println("You dropped " + item.getName());
                    } else {
                        System.out.println("There isn't room in this room for that item");
                    }
                } else {
                    System.out.println("You don't have such an item");
                }
            }
        }
        else {
            System.out.println("Drop what?");
        }
        
        
        this.speedFactorCalculation();
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
    }
    
    //Gives the player a detailed description of the items and the possibilities of a room
    @Override
    public void look(Command command) {
        
        if (command.hasSecondWord()) {
            String direction = command.getSecondWord();
            if (direction.equals("around")) {
                // Print detailed description of room
                System.out.println(this.getCurrentRoom().getDetailedDescription());
                // Print inventory of current room
                System.out.println("There is the following in the room:\n" + this.getCurrentRoom().getInventory().showItems());
                // Print status of exits
                System.out.println(this.getCurrentRoom().getLockedExitString());
                this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
            }
            else if (direction.equals("inventory")) {
                System.out.println("There is the following in your inventory:\n" + this.getInventory().showItems());
                this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
            }
            else{
                System.out.println("There is no such direction to look.");
            }
        } else {
            System.out.println("Look where");
        }
    }
    
    //Checks whatever Zuul is in the next room and gives feedback if it is or isn't
    @Override
    public void peek(Command command) {
        
        String direction = command.getSecondWord();
        
        boolean zuulNearby = false;
        
        if (this.getCurrentRoom().getHasCharacter("Zuul")) {
            System.out.println("Zuul is in this room you idiot");
        }
        
        if (this.getCurrentRoom().getExit(direction) != null) {
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
        }
        else {
            System.out.println("There is no direction by that name");
        }
        
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
    }
    
    //This command is used to lock a door
    @Override
    public void lock(Command command) {
        String direction = command.getSecondWord();
        boolean lock = true;
        boolean directionExists = false;
        
        for (String exit : this.getCurrentRoom().getExits().keySet()) {
            if (direction.equals(exit)) {
                if (this.getInventory().getItem("accesscard") != null) {
                    this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
                    this.lockUnlock(direction, lock);
                    System.out.println("You locked the door");
                }
                else {
                    System.out.println("You don't have an access card to do that with");
                }
                directionExists = true;
                
            }
        }
        // If there isnt any door that matches the secondWord then this is print
        if (!directionExists) {
            System.out.println("there isn't any exit by that name");
            
        }
    }
    
    //This command is used to unlock a door
    @Override
    public void unlock(Command command) {
        String direction = command.getSecondWord();
        boolean lock = false;
        boolean directionExists = false;
        
        for (String exit : this.getCurrentRoom().getExits().keySet()) {
            if (direction.equals(exit)) {
                if (this.getInventory().getItem("accesscard") != null) {
                    this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
                    this.lockUnlock(direction, lock);
                    System.out.println("You unlocked the door");
                }
                else {
                    System.out.println("You don't have an access card to do that with");
                }
                directionExists = true;
                
            }
        }
        // If there isnt any door that matches the secondWord then this is print
        if (!directionExists) {
            System.out.println("there isn't any exit by that name");
            
        }
    }
    
    //Command for using an Item in your inventory
    @Override
    public double use(Command command) {
        
        if (command.hasSecondWord()) {
            String itemName = command.getSecondWord();
            Item item = this.getInventory().getItem(itemName);
            if (item != null) {
                double initiativeReduction = item.use(this);
                this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
                return initiativeReduction;
            } else {
                this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
                System.out.println("You don't have any such item");
            }
        } else {
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
        
        if (this.getCurrentRoom().getHasCharacter("Zuul")){
            System.out.println("The Zuul is in this room");
        }
        
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
    
    //Method for locking and unlocking, first lockUnlock the first door(direction) you call
    // then get the next rooms exits and lockUnlock the direction towards currentRoom of the character
    private void lockUnlock(String direction, boolean lock) {
        
        HashMap<String, Boolean> lockedExits = this.getCurrentRoom().getLockedExits();
        String getName = this.getCurrentRoom().getName();
        lockedExits.put(direction, Boolean.TRUE);
        if (direction.equals("pod")) {
            if (this.getCurrentRoom().getHasCharacter("TechDude")) {
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
                
            } else {
                System.out.println("The station is under quarentine and you therefore can't open the door.\n Perhaps you could find something or someone to force it open");
            }
            
        } else {
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
    }
    
    //use this command to start the countdown timer for bonus points (by blowing up the reactor)
    @Override
    public double activate(Command command) {
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
        if (!command.hasSecondWord()) {
            System.out.println("Activate what?");
            return Double.MAX_VALUE;
        }
        if (command.getSecondWord().equals("reactor")) {
            
            if (this.getCurrentRoom().getName().equals("reactor")) {
                if (this.getCurrentRoom().getHasCharacter("TechDude")) {
                    
                    System.out.println("You activated the reactor. The spacestation will selfdestruct in 10 turns");
                    return (this.getCharacterInitiative() + 50);
                    
                } else {
                    System.out.println("You need the TechDude to do this");
                    return Double.MAX_VALUE;
                }
            } else {
                System.out.println("There is no reactor in this room");
                return Double.MAX_VALUE;
            }
            
        }
        return 0;
    }
    
    public int getHealth() {
        return health;
    }
    
    public void setHealth(int health) {
        this.health = health;
    }
    
    private boolean canBeParsedToInt(String numberString){
        
        int punctuationMarkCounter = 0;
        boolean numberStringCanBeParsed = true;
        
        for (int i = 0; i < numberString.length(); i++) {
            char numberChar = numberString.charAt(i);
            if (java.lang.Character.isDigit(numberChar) ){
            }
            else {
                return false;
            }
        }
        return true;
    }
    
    
}
