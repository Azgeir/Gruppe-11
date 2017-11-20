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
    Hero() {
        this.inventory = new Inventory(100);
    }

    Hero(Room currentRoom, String name) {
        super(currentRoom, name);
        this.inventory = new Inventory(100);
    }

    Hero(int health, int capacity, Room currentRoom, String name) {
        this(currentRoom, name);
        this.health = health;
        this.inventory = new Inventory(capacity);
    }

    //Transfers an Item from the rooms inventory to the characters inventory
    @Override
    void pickUp(Command command) {

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
                            if (item == null) {
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
                        if (numberAdded == 0) {
                            System.out.println("You can't carry any of that.");
                        } else if ((numberAdded < number) && (numberNonExisting == 0)) {
                            System.out.println("You could only pick up " + numberAdded + " " + itemName);
                        } else if (numberNonExisting > 0 && (numberNonExisting + numberAdded) == number) {
                            System.out.println("You picked up " + numberAdded + " " + itemName + " because there is only " + numberAdded + " in this room.");
                        } else {
                            System.out.println("You picked up " + number + " " + itemName);
                        }
                    } else {
                        System.out.println("The third word needs to be an integer.");
                    }
                } else {
                    boolean itemAdded = this.inventory.addItem(item);
                    if (itemAdded) {
                        this.getCurrentRoom().getInventory().removeItem(item);
                        System.out.println("You picked up " + item.getName());
                    } else {
                        System.out.println("You can't carry that.");
                    }
                }
            }
        } else {
            System.out.println("Pickup what?");
        }

        this.speedFactorCalculation();
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
    }

    //Transfers an item from the characters inventory to the rooms
    @Override
    void dropItem(Command command) {

        if (command.hasSecondWord()) {
            String itemName = command.getSecondWord();
            Item item = this.inventory.getItem(itemName);

            if (item == null) {
                System.out.println("You don't have such an item.");
            } else {
                if (command.hasThirdWord()) {
                    String numberString = command.getThirdWord();

                    if (this.canBeParsedToInt(numberString)) {
                        int number = Integer.parseInt(numberString);
                        int numberAdded = 0;
                        int numberNonExisting = 0;

                        for (int i = 0; i < number; i++) {
                            item = this.inventory.getItem(itemName);
                            if (item == null) {
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
                        if (numberAdded == 0) {
                            System.out.println("There isn't room in this room for that.");
                        } else if ((numberAdded < number) && (numberNonExisting == 0)) {
                            System.out.println("You could only drop " + numberAdded + " " + itemName + " because there is not room enought for all of them in this room.");
                        } else if (numberNonExisting > 0 && (numberNonExisting + numberAdded) == number) {
                            System.out.println("You dropped " + numberAdded + " " + itemName + " because there is only " + numberAdded + " in your inventory.");
                        } else {
                            System.out.println("You dropped " + number + " " + itemName);
                        }
                    } else {
                        System.out.println("The third word needs to be an integer.");
                    }
                } else {
                    boolean itemAdded = this.getCurrentRoom().getInventory().addItem(item);
                    if (itemAdded) {
                        this.inventory.removeItem(item);
                        System.out.println("You dropped " + item.getName());
                    } else {
                        System.out.println("There isn't room in this room for that item.");
                    }
                }
            }
        } else {
            System.out.println("Drop what?");
        }

        this.speedFactorCalculation();
        this.setCharacterInitiative(this.getCharacterInitiative() + 2.5 * this.getSpeedFactor());
    }

    //Gives the player a detailed description of the items and the possibilities of a room
    @Override
    void look(Command command) {

        if (command.hasSecondWord()) {
            String direction = command.getSecondWord();
            if (direction.equals("around")) {
                // Print detailed description of room
                System.out.println(this.getCurrentRoom().getDetailedDescription());
                // Prints out the characters present in the room, except for the player
                for (Entry<String, Boolean> hasCharacter : this.getCurrentRoom().getHasCharacters().entrySet()) {
                    if (hasCharacter.getValue() && !hasCharacter.getKey().equals("Hero")) {
                        System.out.println("\n" + hasCharacter.getKey() + " is in this room");
                    }
                }

                // Print inventory of current room
                System.out.println("There is the following in the room:\n" + this.getCurrentRoom().getInventory().showItems());
                // Print status of exits
                System.out.println(this.getCurrentRoom().getLockedExitString());
                this.getCurrentRoom().setHasBeenLookedUpon(true);
                this.setCharacterInitiative(this.getCharacterInitiative() + 1 * this.getSpeedFactor());
            } else if (direction.equals("inventory")) {
                System.out.println("There is the following in your inventory:\n" + this.getInventory().showItems());
                this.setCharacterInitiative(this.getCharacterInitiative() + 1.5 * this.getSpeedFactor());
            } else {
                System.out.println("There is no such direction to look.");
            }
        } else {
            System.out.println("Look where");
        }
    }

    //Checks whatever Zuul is in the next room and gives feedback if it is or isn't
    @Override
    void peek(Command command) {
        String direction = command.getSecondWord();

        boolean zuulNearby = false;

        if (this.getCurrentRoom().getHasCharacter("Zuul")) {
            System.out.println("Zuul is in this room, you idiot.");
            zuulNearby = true;
        }

        if (this.getCurrentRoom().getExit(direction) != null) {
            for (Room neighbor : this.getCurrentRoom().getExits().values()) {
                if (neighbor.getHasCharacter("Zuul")) {
                    System.out.println("Zuul is " + neighbor.getShortDescription());
                    zuulNearby = true;
                }
            }

            Room neighbor = this.getCurrentRoom().getExit(direction);
            if (neighbor.getExit(direction) != null) {
//                    neighbor.getHasCharacter("Zuul")) {
                if (neighbor.getExit(direction).getHasCharacter("Zuul")) {
                    System.out.println("Zuul is " + neighbor.getShortDescription());
                    zuulNearby = true;

                }
            }

            if (!zuulNearby) {
                System.out.println("There is no Zuul nearby.");
            }
        } else {
            System.out.println("There is no direction by that name.");
        }

        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
    }

    //This command is used to lock a door
    void lock(Command command) {
        String direction = command.getSecondWord();
        boolean lock = true;

        boolean directionExists = this.getCurrentRoom().getExits().containsKey(direction);

        if (directionExists) {
            if (this.getInventory().getItem("accesscard") != null) {
                this.lockUnlock(direction, lock);
                System.out.println("You locked the door.");
            } else {
                System.out.println("You don't have an access card to do that with.");
            }
        }

        // If there isn't any door that matches the secondWord, then this is print
        if (!directionExists) {
            System.out.println("There isn't an exit by that name.");

        }
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());

    }

    //This command is used to unlock a door
    @Override
    void unlock(Command command) {
        String direction = command.getSecondWord();
        boolean lock = false;
        boolean directionExists = false;

        for (String exit : this.getCurrentRoom().getExits().keySet()) {
            if (direction.equals(exit)) {
                if (this.getInventory().getItem("accesscard") != null) {
                    this.lockUnlock(direction, lock);
                    System.out.println("You unlocked the door.");
                } else {
                    System.out.println("You don't have an access card to do that with.");
                }
                directionExists = true;
            }
        }
        // If there isnt any door that matches the secondWord then this is print
        if (!directionExists) {
            System.out.println("There isn't any exit by that name.");
        }
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
    }

    //Command for using an Item in your inventory
    @Override
    double use(Command command) {

        if (command.hasSecondWord()) {
            String itemName = command.getSecondWord();
            Item item = this.getInventory().getItem(itemName);
            if (item != null) {
                double initiativeReduction = item.use(this);
                this.setCharacterInitiative(this.getCharacterInitiative() + 2 * this.getSpeedFactor());
                return initiativeReduction;
            } else {
                this.setCharacterInitiative(this.getCharacterInitiative() + 2 * this.getSpeedFactor());
                System.out.println("You don't have any such item.");
            }
        } else {
            System.out.println("You have to select something to use.");
        }

        return 0;
    }

//    //£ characterInitiative
//    public void seeInventory(Command command) {
//        this.inventory.showItems();
//        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
//    }
    void speedFactorCalculation() {
        double newSpeedFactor = 1 + (this.inventory.getTotalWeight() / this.inventory.getMaxWeight()) / 2;
        this.setSpeedFactor(newSpeedFactor);
    }

    Inventory getInventory() {
        return inventory;
    }

    @Override
    Command getCommand(CommandWords commands, String GUICommand) {
        // Declare a String variable for the input
        String inputLine;

        Scanner reader = new Scanner(GUICommand);

        // Set words 1 and 2 to null
        String word1 = null;
        String word2 = null;
        String word3 = null;

        // Print "> " to prompt user input
        System.out.print("> ");

        // Use Scanner to read input line from user
        inputLine = reader.nextLine();

        // Create a Scanner called tokenizer based on inputLine
        Scanner tokenizer = new Scanner(GUICommand);
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
    void lockUnlock(String direction, boolean lock) {

        HashMap<String, Boolean> lockedExits = this.getCurrentRoom().getLockedExits();
        String getName = this.getCurrentRoom().getName();
        //lockedExits.put(direction, Boolean.TRUE);
        if (direction.equals("pod")) {
            if (this.getCurrentRoom().getHasCharacter("TechDude")) {
                if (this.getCurrentRoom().getExit(direction).getExits().containsKey(getName)) {
                    lockedExits.put(direction, lock);
                    this.getCurrentRoom().getExit(direction).getLockedExits().put(getName, lock);

                } else {
                    lockedExits.put(direction, lock);

                    HashMap<String, Boolean> templockExits = new HashMap<>();
                    templockExits.putAll(lockedExits);
                    templockExits.remove(direction);
                    String direction2 = (String) templockExits.keySet().toArray()[0];
                    this.getCurrentRoom().getExit(direction).getLockedExits().put(direction2, lock);
                }

            } else {
                System.out.println("The station is under quarantine and you therefore can't open the door.\nPerhaps you could find something or someone to force it open.");
            }

        } else {
            if (this.getCurrentRoom().getExit(direction).getExits().containsKey(getName)) {
                lockedExits.put(direction, lock);
                this.getCurrentRoom().getExit(direction).getLockedExits().put(this.getCurrentRoom().getName(), lock);
            } else {
                lockedExits.put(direction, lock);

                HashMap<String, Boolean> templockExits = new HashMap<>();
                templockExits.putAll(lockedExits);
                templockExits.remove(direction);
                String direction2 = (String) templockExits.keySet().toArray()[0];
                this.getCurrentRoom().getExit(direction).getLockedExits().put(direction2, lock);
            }
        }
    }

    //use this command to start the countdown timer for bonus points (by blowing up the reactor)
    @Override
    double activate(Command command, boolean reactorActivated) {
        this.setCharacterInitiative(this.getCharacterInitiative() + 15 * this.getSpeedFactor());
        if (!command.hasSecondWord()) {
            System.out.println("Activate what?");
            return Double.MAX_VALUE;
        }
        if (command.getSecondWord().equals("reactor")) {

            if (this.getCurrentRoom().getName().equals("reactor")) {
                if (this.getCurrentRoom().getHasCharacter("TechDude")) {
                    if (!reactorActivated) {
                        System.out.println("You activated the reactor. The space station will self-destruct in 10 turns.");
                        return (this.getCharacterInitiative() + 80);
                    } else {
                        System.out.println("The reactor is already activated.");
                        return Double.MAX_VALUE;
                    }
                } else {
                    System.out.println("You need the tech dude to do this.");
                    return Double.MAX_VALUE;
                }
            } else {
                System.out.println("There is no reactor in this room.");
                return Double.MAX_VALUE;
            }

        } else {
            System.out.println("You can't activate anything by that name.");
        }
        return Double.MAX_VALUE;
    }

    int getHealth() {
        return health;
    }

    void setHealth(int health) {
        this.health = health;
    }

    private boolean canBeParsedToInt(String numberString) {

        int punctuationMarkCounter = 0;
        boolean numberStringCanBeParsed = true;

        for (int i = 0; i < numberString.length(); i++) {
            char numberChar = numberString.charAt(i);
            if (java.lang.Character.isDigit(numberChar)) {
            } else {
                return false;
            }
        }
        return true;
    }

}
