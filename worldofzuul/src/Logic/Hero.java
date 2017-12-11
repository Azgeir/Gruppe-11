/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// This class is located in the logic layer.
package Logic;

// Imports:
import java.io.Serializable;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This class represents the hero (the player). The class extends the superclass
 * Character and implements the interface Serializable. The class has default
 * visibility as it is only used from within this package.
 * 
 * @author HCHB
 */

class Hero extends Character implements Serializable {

    /**
     * Data fields.
     * health: the hero's health (default value of 10)
     * inventory: an instance of Inventory that represents the hero's inventory
     * reactorActivated: boolean value that indicates if the player has
     * activated the reactor (default value of false).
     * knownRooms: a Set of Strings that indicate the rooms that the character
     * knows of. This is used when peeking.
     * talking: boolean value that indicates if the hero is talking to the tech
     * dude.
     * previousCommand: String that represents the previous command of the hero.
     * messageClass: instance of LogicMessage used for storing Strings to be
     * read later.
     */
    private int health = 10;
    private Inventory inventory;
    private boolean reactorActivated = false;
    private Set<String> knownRooms;
    private boolean talking;
    private String previousCommand;
    private LogicMessage messageClass;

    /**
     * This constructor creates a Hero object with the specified current room
     * and name. The hero has an inventory of default size 100. The set of
     * known rooms is initialized and the current room of the character is added
     * to the set.
     * 
     * @param currentRoom instance of Room that indicates the hero's current
     * room.
     * @param name String that indicates the character type (e.g. "Hero").
     */
    Hero(Room currentRoom, String name) {
        super(currentRoom, name);
        this.inventory = new Inventory(100);
        this.knownRooms = new HashSet<String>();
        this.knownRooms.add(currentRoom.getName());
        this.talking = false;
    }
    
    /**
     * This constructor creates a Hero object with the specified current room,
     * name, inventory capacity and health. The hero has an inventory of default
     * size 100.
     * 
     * @param health  The amount of health the hero gets.
     * @param inventoryCapacity  The total weight of items the hero can carry.
     * @param currentRoom The room the hero starts in.
     * @param name The name of the hero.
     */
    Hero(int health, int inventoryCapacity, Room currentRoom, String name) {
        this(currentRoom, name);
        this.health = health;
        this.inventory = new Inventory(inventoryCapacity);
    }
    
    /**
     * This constructor creates a Hero object with the specified current room,
     * name, inventory capacity and health. The hero has an inventory of default
     * size 100.
     * 
     * @param health  The amount of health the hero gets.
     * @param inventoryCapacity  The total weight of items the hero can carry.
     * @param currentRoom The room the hero starts in.
     * @param name The name of the hero.
     * @param messageClass An object for storing Strings to be read later.
     */
    Hero(int health, int inventoryCapacity, Room currentRoom, String name,
        LogicMessage messageClass) {
        this(currentRoom, name);
        this.health = health;
        this.inventory = new Inventory(inventoryCapacity, messageClass);
        this.messageClass = messageClass;
    }
    
    /**
     * This method moves the hero according to the command passed.
     * 
     * @param command the command the hero receives. Consists of 3 words
     * where the second defines the direction in which the hero attempt to go.
     */
    @Override
    void go(Command command) {
        // If the command does not have a second word, print error message
        if (!command.hasSecondWord()) {
            messageClass.appendMessage("Go where?");
            return;
        }

        // Set the direction string to the second word of the command
        String direction = command.getSecondWord();

        /*
        Set nextRoom to be the neighbouring room specified by the direction
        string.
        */
        Room nextRoom = this.getCurrentRoom().getExit(direction);

        // If the specified exit does not exist, print error message
        if (nextRoom == null) {
            messageClass.appendMessage("There is no door!");
        } 
        // If the specified exit is locked, print locked message
        else if (this.getCurrentRoom().isExitLocked(direction)){
            messageClass.appendMessage("This exit is locked, so you can't get "
                + "through.");
        }
        // If the specified exit exists and is not locked, move character.
        else {
            // Remove character from current room
            this.getCurrentRoom().removeCharacterInRoom(this.getName());
            this.knownRooms.clear();
            /*
            Change the value of currentRoom to the specified neighbouring room.
            */
            this.setCurrentRoom(nextRoom);
            // Add character to the new current room
            this.getCurrentRoom().addCharacterInRoom(this.getName());
            this.knownRooms.add(this.getCurrentRoom().getName());
            // Print description of the current room
            messageClass.appendMessage(this.getCurrentRoom().
                getLongDescription());
        }
        // If Zuul is in the room, the player is informed.
        if (this.getCurrentRoom().hasCharacter("Zuul")){
            messageClass.appendMessage("The Zuul is in this room.");
        }
        
        // The player's initiative is increased.
        this.setCharacterInitiative(this.getCharacterInitiative()
            + 10 * this.getSpeedFactor());
    }
        
    /**
     * This method picks up an item from the hero's current room and moves it 
     * to the hero's inventory.
     * 
     * @param command the command the hero receives. Consists of 3 words
     * where the second is the name of the item to pick up, and the third is an 
     * optional number of items to pick up.
     */
    private void pickUp(Command command) {
        /*
        Check if the command has a second word, as this indicates the item to be
        picked up.
        */
        if (command.hasSecondWord()) {
            // Find item name.
            String itemName = command.getSecondWord();
            
            // Check if the item is not in the current room.
            Item item = this.getCurrentRoom().getInventory().getItem(itemName);
            if (item == null) {
                messageClass.appendMessage("The room doesn't contain that "
                    + "item.");
            }
            // If the item is in the current room...
            else {
                /*
                If the command has a third word, this indicates the number of
                items to be picked up.
                */
                if (command.hasThirdWord()) {
                    String numberString = command.getThirdWord();
                    
                    // Check if the third word indicates a valid number.
                    if (this.canBeParsedToInt(numberString)) {
                        // Parse the third word to an integer.
                        int number = Integer.parseInt(numberString);
                        /*
                        Initially no item has been added to the player's
                        inventory.
                        */
                        int numberAdded = 0;
                        int numberNonExisting = 0;
                        
                        /*
                        Search the room's inventory for the item "number" times.
                        */
                        for (int i = 0; i < number; i++) {
                            item = this.getCurrentRoom().getInventory().
                                getItem(itemName);
                            /*
                            If item does not exist in room's inventory increment
                            numberNonExisting.
                            */
                            if (item == null) {
                                numberNonExisting++;
                            }
                            /*
                            If item exists in room, attempt to add it to
                            player's inventory.
                            */
                            else {
                                boolean itemAdded =
                                    this.inventory.addItem(item);
                                /*
                                If this is successful, increment the number of
                                items added and remove the item from the room's
                                inventory.
                                */
                                if (itemAdded) {
                                    numberAdded++;
                                    this.getCurrentRoom().getInventory().
                                        removeItem(item);
                                }
                                else {}
                            }
                        }
                        /*
                        If no items were added, the player cannot carry any of
                        it.
                        */
                        if (numberAdded == 0) {
                            messageClass.appendMessage("You can't carry any of "
                                + "that.");
                        }
                        /*
                        If only some of the desired items were added to the
                        player's inventory while more items exist in the room,
                        it is because the player could not carry any more.
                        */
                        else if ((numberAdded < number) &&
                            (numberNonExisting == 0)) {
                            messageClass.appendMessage("You could only pick up "
                                + numberAdded + " " + itemName);
                        }
                        /*
                        If the player picked up all the items in the room, but
                        attempted to pick up more.
                        */
                        else if (numberNonExisting > 0 && (numberNonExisting
                            + numberAdded) == number) {
                            messageClass.appendMessage("You picked up "
                                + numberAdded + " " + itemName
                                + " because there is only " + numberAdded
                                + " in this room.");
                        }
                        // If the player picked up the desired number of items.
                        else {
                            messageClass.appendMessage("You picked up " + number
                                + " " + itemName);
                        }
                    }
                    // If the third word is not a valid number, tell player.
                    else {
                        messageClass.appendMessage("The third word needs to be "
                            + "an integer.");
                    }
                }
                /*
                If the command does not have a third word, only one item is to
                be picked up.
                */
                else {
                    // Attempt to add item.
                    boolean itemAdded = this.inventory.addItem(item);
                    /*
                    If item was successfully added, remove item from room's
                    inventory.
                    */
                    if (itemAdded) {
                        this.getCurrentRoom().getInventory().removeItem(item);
                        messageClass.appendMessage("You picked up "
                            + item.getName());
                    }
                    /*
                    If item could not be added (itemAdded == false), tell
                    player.
                    */
                    else {
                        messageClass.appendMessage("You can't carry that.");
                    }
                }
            }
        }
        /*
        If the command does not have a second word (indicating item), the
        command cannot be performed.
        */
        else {
            messageClass.appendMessage("Pickup what?");
        }

        // Update speed factor and character initiative.
        this.speedFactorCalculation();
        this.setCharacterInitiative(this.getCharacterInitiative()
            + 5 * this.getSpeedFactor());
    }

    
    /**
     * This method drops an item from the hero's inventory and moves it to the
     * current room.
     * 
     * @param command the command the hero receives. Consists of 3 words
     * where the second is the name of the item to drop, and the third is an 
     * optional number of items to drop.
     */
    private void dropItem(Command command) {
        /*
        Check if the command has a second word. This word indicates the item to
        be dropped.
        */
        if (command.hasSecondWord()) {
            // Find the item in the player's inventory.
            String itemName = command.getSecondWord();
            Item item = this.inventory.getItem(itemName);

            /*
            If the item does not exist in the player's inventory, tell the
            player.
            */
            if (item == null) {
                messageClass.appendMessage("You don't have such an item.");
            }
            // If the item is in the inventory, attempt to drop item.
            else {
                /*
                Check if the command has a third word. This word indicates the
                number of items to be dropped.
                */
                if (command.hasThirdWord()) {
                    String numberString = command.getThirdWord();

                    // Check if the third word can be pared to int.
                    if (this.canBeParsedToInt(numberString)) {
                        // Parse third word to an integer.
                        int number = Integer.parseInt(numberString);
                        
                        // Initially, no items have been dropped.
                        int numberAdded = 0;
                        int numberNonExisting = 0;

                        // Attempt to drop "number" items.
                        for (int i = 0; i < number; i++) {
                            // Search for item in inventory.
                            item = this.inventory.getItem(itemName);
                            /*
                            If the item does not exist in the inventory,
                            increment numberNonExisting.
                            */
                            if (item == null) {
                                numberNonExisting++;
                            }
                            // If item exists in the inventory, drop the item.
                            else {
                                boolean itemAdded = this.getCurrentRoom().
                                    getInventory().addItem(item);
                                /*
                                If the item was successfully added to the room's
                                inventory, increment the number of items dropped
                                and remove the item from the player's inventory.
                                */
                                if (itemAdded) {
                                    numberAdded++;
                                    this.inventory.removeItem(item);
                                }
                                else {}
                            }
                        }
                        /*
                        If no item was dropped, there wasn't room in the room's
                        inventory for it.
                        */
                        if (numberAdded == 0) {
                            messageClass.appendMessage("There isn't room in "
                                + "this room for that.");
                        }
                        /*
                        If not all desired items were dropped, but there are
                        still items in the player's inventory.
                        */
                        else if ((numberAdded < number) &&
                            (numberNonExisting == 0)) {
                            messageClass.appendMessage("You could only drop " 
                                + numberAdded + " " + itemName + " because "
                                + "there is not room enought for all of them in"
                                + " this room.");
                        }
                        /*
                        If the player tried to drop more items than were in
                        their inventory.
                        */
                        else if (numberNonExisting > 0 &&
                            (numberNonExisting + numberAdded) == number) {
                            messageClass.appendMessage("You dropped " 
                                + numberAdded + " " + itemName + " because "
                                + "there is only " + numberAdded + " in your "
                                + "inventory.");
                        }
                        /*
                        If the player succeeded in dropping the desired number
                        of items.
                        */
                        else {
                            messageClass.appendMessage("You dropped " + number
                                + " " + itemName);
                        }
                    }
                    // If the third word is not a number, tell player.
                    else {
                        messageClass.appendMessage("The third word needs to be "
                            + "an integer.");
                    }
                }
                /*
                If the command does not have a third word, only one item is to
                be dropeed.
                */
                else {
                    // Attempt to add item to room's inventory.
                    boolean itemAdded = this.getCurrentRoom().getInventory().
                        addItem(item);
                    // If successful, remove item from player's inventory.
                    if (itemAdded) {
                        this.inventory.removeItem(item);
                        messageClass.appendMessage("You dropped " 
                            + item.getName());
                    }
                    /*
                    If unsuccessful, there was not room in the room's inventory.
                    */
                    else {
                        messageClass.appendMessage("There isn't room in this "
                            + "room for that item.");
                    }
                }
            }
        }
        // If the command does not have a second word, no item is indicated.
        else {
            messageClass.appendMessage("Drop what?");
        }

        // Update speed factor and character initiative.
        this.speedFactorCalculation();
        this.setCharacterInitiative(this.getCharacterInitiative()
            + 2.5 * this.getSpeedFactor());
    }

    /**
     * This method gives the player a detailed description of the items and the
     * possibilities of a room.
     * 
     * @param command instance of Command that represents the "look" command to
     * be executed.
     */
    private void look(Command command) {
        /*
        Check if the command has a second word. This word indicates where the
        player wants to look ("around" or "inventory").
        */
        if (command.hasSecondWord()) {
            String direction = command.getSecondWord();
            /*
            If the second word is "around", give detailed description of room,
            including inventory, exits, and characters.
            */
            if (direction.equals("around")) {
                // Print detailed description of room
                messageClass.appendMessage(this.getCurrentRoom().
                    getDetailedDescription());
                
                /*
                Print out the characters present in the room, except for the'
                player.
                */
                for (Entry<String, Boolean> hasCharacter : 
                    this.getCurrentRoom().getHasCharacters().entrySet()) {
                    if (hasCharacter.getValue() &&
                        !hasCharacter.getKey().equals("Hero")) {
                        messageClass.appendMessage("\n" + hasCharacter.getKey()
                            + " is in this room");
                    }
                }

                // Print inventory of current room
                messageClass.appendMessage("There is the following in the room:"
                    + "\n" + this.getCurrentRoom().getInventory().showItems());
                
                // Print status of exits
                messageClass.appendMessage(this.getCurrentRoom().
                    getLockedExitString());
                
                /*
                Since the player has looked around this room, the inventory of
                the room is permanently unlocked.
                */
                this.getCurrentRoom().setHasBeenLookedUpon(true);
                
                // Increase character initiative.
                this.setCharacterInitiative(this.getCharacterInitiative()
                    + 1 * this.getSpeedFactor());
            }
            /*
            If second word is inventory, print inventory of player (This is no
            longer used, as this is shown in a drop down menu in the GUI).
            */
            else if (direction.equals("inventory")) {
                messageClass.appendMessage("There is the following in your "
                    + "inventory:\n" + this.getInventory().showItems());
                this.setCharacterInitiative(this.getCharacterInitiative()
                    + 1.5 * this.getSpeedFactor());
            }
            /*
            If the second word does not correspond to the given options, tell
            player.
            */
            else {
                messageClass.appendMessage("There is no such direction to "
                    + "look.");
            }
        }
        /*
        If the command does not have a second word ("around" or "inventory"),
        then the command is incomplete.
        */
        else {
            messageClass.appendMessage("Look where?");
        }
    }

    /**
     * This method checks the surrounding rooms for the presence of Zuul.
     * 
     * @param command instance of Command that represents the "peek" command to
     * be executed.
     */
    private void peek(Command command) {
        /*
        The second word of the command indicates the direction in which to peek.
        */
        String direction = command.getSecondWord();

        // Initially, it is assumed that Zuul is not nearby.
        boolean zuulNearby = false;
        
        // The Set of known rooms is cleared at the beginning of a peek.
        this.knownRooms.clear();
        
        // The character's current room is added to the set of known rooms.
        this.knownRooms.add(this.getCurrentRoom().getName());

        /*
        If the current room has Zuul, inform the player and update the value of
        zuulNearBy.
        */
        if (this.getCurrentRoom().hasCharacter("Zuul")) {
            messageClass.appendMessage("Zuul is in this room, you idiot.");
            zuulNearby = true;
        }

        // If the specified exit exists, peek in that direction.
        if (this.getCurrentRoom().getExit(direction) != null) {
            // Add all neightbors to the current room to the set of known rooms.
            for (Room neighbor : this.getCurrentRoom().getExits().values()) {
                this.knownRooms.add(neighbor.getName());
                /*
                If a neighboring room contains Zuul inform player and update
                value of zuulNearBy.
                */
                if (neighbor.hasCharacter("Zuul")) {
                    messageClass.appendMessage("Zuul is "
                        + neighbor.getShortDescription());
                    zuulNearby = true;
                }
            }

            /*
            Find the neighboring room in the given direction to peek beyond
            that.
            */
            Room neighbor = this.getCurrentRoom().getExit(direction);
            
            /*
            If an exit from the neighbouring room in the given direction exists,
            add the corresponding room to the set of known rooms.
            */
            if (neighbor.getExit(direction) != null) {
                this.knownRooms.add(neighbor.getExit(direction).getName());
                /*
                If the room contains Zuul, inform player and update zuulNearBy.
                */
                if (neighbor.getExit(direction).hasCharacter("Zuul")) {
                    messageClass.appendMessage("Zuul is " + neighbor.
                        getExit(direction).getShortDescription());
                    zuulNearby = true;
                }
            }

            // If zuul was in none of the searched rooms, inform player.
            if (!zuulNearby) {
                messageClass.appendMessage("There is no Zuul nearby.");
            }
        }
        // If there is no exit in the specified direction, tell player.
        else {
            messageClass.appendMessage("There is no direction by that name.");
        }

        // Increase character initiative.
        this.setCharacterInitiative(this.getCharacterInitiative()
            + 5 * this.getSpeedFactor());
    }

    /**
     * This method is used to lock a door.
     * 
     * @param command instance of Command that specifies the "lock" command to
     * be executed.
     */
    private void lock(Command command) {
        /*
        The second word of the command indicates the direction of the exit to
        be locked.
        */
        String direction = command.getSecondWord();
        
        /*
        The boolean variable lock is set to true to indicate that the exit is
        to be locked.
        */
        boolean lock = true;

        // Check if an exit exists in the specified direction.
        boolean directionExists = this.getCurrentRoom().getExits().
            containsKey(direction);

        // If the exit exists, attempt to lock the exit.
        if (directionExists) {
            /*
            If the character has an access card in their inventory, lock the
            door by calling the lockUnlock() method.
            */
            if (this.getInventory().getItem("accesscard") != null) {
                this.lockUnlock(direction, lock);
                messageClass.appendMessage("You locked the door.");
            }
            /*
            If the character does not have an access card, the door cannot be
            locked.
            */
            else {
                messageClass.appendMessage("You don't have an access card to do"
                    + " that with.");
            }
        }

        /*
        The there is no exit, which matches the specified direction, the exit
        cannot be locked.
        */
        if (!directionExists) {
            messageClass.appendMessage("There isn't an exit by that name.");
        }
        
        // Increase the character's initiative.
        this.setCharacterInitiative(this.getCharacterInitiative()
            + 5 * this.getSpeedFactor());
    }

    /**
     * This method is used to unlock a door.
     * 
     * @param command instance of Command which represents the "unlock" command
     * to be executed.
     */
    private void unlock(Command command) {
        /*
        The command's second word indicates the direction of the exit to be
        unlocked.
        */
        String direction = command.getSecondWord();
        
        /*
        The boolean variable lock is set to false to indicate that the exit is
        to be unlocked.
        */
        boolean lock = false;
        
        // Initially, it is assumed that the exit does not exist.
        boolean directionExists = false;

        
        for (String exit : this.getCurrentRoom().getExits().keySet()) {
            if (direction.equals(exit)) {
                if (this.getInventory().getItem("accesscard") != null) {
                    this.lockUnlock(direction, lock);
                    messageClass.appendMessage("You unlocked the door.");
                } else {
                    messageClass.appendMessage("You don't have an access card to do that with.");
                }
                directionExists = true;
            }
        }
        // If there isnt any door that matches the secondWord then this is print
        if (!directionExists) {
            messageClass.appendMessage("There isn't any exit by that name.");
        }
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
    }

    //Command for using an Item in your inventory
//    @Override
    private double use(Command command) {

        if (command.hasSecondWord()) {
            String itemName = command.getSecondWord();
            Item item = this.getInventory().getItem(itemName);
            if (item != null) {
                double initiativeReduction = item.use(this);
                this.setCharacterInitiative(this.getCharacterInitiative() + 2 * this.getSpeedFactor());
                return initiativeReduction;
            } else {
                this.setCharacterInitiative(this.getCharacterInitiative() + 2 * this.getSpeedFactor());
                messageClass.appendMessage("You don't have any such item.");
            }
        } else {
            messageClass.appendMessage("You have to select something to use.");
        }

        return 0;
    }
    
    /**
     * This method calculates and sets the value of the speed factor.
     */
    private void speedFactorCalculation() {
        double newSpeedFactor = 1 + (this.inventory.getTotalWeight()
            / this.inventory.getMaxWeight()) / 2;
        this.setSpeedFactor(newSpeedFactor);
    }

    /**
     * This method returns the hero's inventory.
     * 
     * @return inventory
     */
    Inventory getInventory() {
        return this.inventory;
    }

    /**
     * This method is used to obtain a method based on the specified String from
     * the GUI.
     * 
     * @param commands instance of CommandWords which represents the valid
     * commands.
     * @param GUICommand String from GUI which represents the command.
     * 
     * @return instance of Command, which represents the command.
     */
    @Override
    Command getCommand(CommandWords commands, String GUICommand) {
        // Set words 1, 2, and 3 to null.
        String word1 = null;
        String word2 = null;
        String word3 = null;

        // Create a Scanner called tokenizer based on the GUI command.
        Scanner tokenizer = new Scanner(GUICommand);
        
        // If the command has a first word, assign it to word1.
        if (tokenizer.hasNext()) {
            word1 = tokenizer.next();
            // If the command has a second word, assign it to word2.
            if (tokenizer.hasNext()) {
                word2 = tokenizer.next();
                // If the command a third word assign it to word3.
                if (tokenizer.hasNext()) {
                    word3 = tokenizer.next();
                }
                // Else if the command is talk, set third word.
                else if (word1.equals("talk")){
                    if (previousCommand.equals("talk")) {
                        word3 = "TRUE";
                    }
                    else {
                        word3 = "FALSE";
                    }
                }
            }
        }

        /*
        Create a Command object based on words 1, 2, and 3, and return the
        command.
        */
        return new Command(commands.getCommandWord(word1), word2, word3);
    }

    //Method for locking and unlocking, first lockUnlock the first door(direction) you call
    // then get the next rooms exits and lockUnlock the direction towards currentRoom of the character
    private void lockUnlock(String direction, boolean lock) {

        HashMap<String, Boolean> lockedExits = this.getCurrentRoom().getLockedExits();
        String getName = this.getCurrentRoom().getName();

        if (direction.equals("pod")) {
            if (this.getCurrentRoom().hasCharacter("TechDude")) {
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
                messageClass.appendMessage("The station is under quarantine and you therefore can't open the door.\n"
                        + "Perhaps you could find something or someone to force it open.");
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
    private double activate(Command command, boolean reactorActivated) {
        this.clearMessage();
        this.setCharacterInitiative(this.getCharacterInitiative() + 15 * this.getSpeedFactor());
        if (!command.hasSecondWord()) {
            messageClass.appendMessage("Activate what?");
            return Double.MAX_VALUE;
        }
        if (command.getSecondWord().equals("Reactor")) {

            if (this.getCurrentRoom().getName().equals("Reactor")) {
                if (this.getCurrentRoom().hasCharacter("TechDude")) {
                    if (!reactorActivated) {
                        messageClass.appendMessage("You activated the reactor. The space station will self-destruct in an unspecified amount of time.");
                        this.reactorActivated = true;
                        this.setMessage("Reactor activated");
                        return (this.getCharacterInitiative() + 80);
                    } else {
                        messageClass.appendMessage("The reactor is already activated.");
                        return Double.MAX_VALUE;
                    }
                } else {
                    messageClass.appendMessage("You need the tech dude to do this.");
                    return Double.MAX_VALUE;
                }
            } else {
                messageClass.appendMessage("There is no reactor in this room.");
                return Double.MAX_VALUE;
            }

        } else {
            messageClass.appendMessage("You can't activate anything by that name.");
        }
        return Double.MAX_VALUE;
    }

    /**
     * This method returns the health of the player.
     * 
     * @return health
     */
    int getHealth() {
        return this.health;
    }

    /**
     * This method sets the value of health.
     * 
     * @param health new value of health.
     */
    void setHealth(int health) {
        this.health = health;
    }

    /**
     * 
     * @param numberString
     * @return 
     */
    private boolean canBeParsedToInt(String numberString) {

        for (int i = 0; i < numberString.length(); i++) {
            char numberChar = numberString.charAt(i);
            if (java.lang.Character.isDigit(numberChar)) {
            }
            else {
                return false;
            }
        }
        return true;
    }
    
    /**
     * This method is used to
     * @return 
     */
    boolean isReactorActivated() {
        return this.reactorActivated;
    }

    /**
     * This method is used to perform the specified command.
     * 
     * @param command instance of Command that represents the command to be
     * performed.
     * 
     * @return double value that indicates the effect of the action.
     */
    @Override
    double performCommand(Command command) {
        /*
        Create instance of CommandWord using the command word of the specified
        command.
        */
        CommandWord commandWord = command.getCommandWord();
        
        // Set talking to false.
        this.talking = false;
        
        // Execute the command if the input matches a valid command.
        if (null != commandWord) {
            switch (commandWord) {
                // If command is "go", call go() method.
                case GO:
                    this.go(command);
                    break;
                /*
                If the commad is "stay", call the stay() method and append a
                message.
                */
                case STAY:
                    this.stay(command);
                    messageClass.appendMessage("You stay in this room");
                    break;
                // If command is "pickup", call pickup() method.
                case PICKUP:
                    this.pickUp(command);
                    break;
                // If command is "drop", call dropItem() method.
                case DROP:
                    this.dropItem(command);
                    break;
                // If command is "look", call look() method.
                case LOOK:
                    this.look(command);
                    break;
                // If command is "peek", call peek() method.
                case PEEK:
                    this.peek(command);
                    break;
                /*
                If command is "use", call use() method and return its return
                    value.
                */
                case USE:
                    return this.use(command);
                // If command is "lock", call lock() method.
                case LOCK:
                    this.lock(command);
                    break;
                // If command is "unlock", call unlock() method.
                case UNLOCK:
                    this.unlock(command);
                    break;
                /*
                If command is "activate", call the activate() method and return
                its return value.
                */
                case ACTIVATE:
                    return this.activate(command, reactorActivated);
                // If command does not match any of the options, break.
                default:
                    break;
            }
        }
        /*
        Set the value of previousCommand using the command word of the command.
        */
        previousCommand = commandWord.toString();
        
        // Return zero, as the command is not activate or use.
        return 0;
    }

    /** 
     * This method returns a Set of the rooms that the hero knows.
     * 
     * @return Set of Strings that indicate the known rooms.
     */
    Set<String> getKnownRooms() {
        return this.knownRooms;
    }

    /**
     * This method returns the value of talking.
     * 
     * @return true if hero is talking; else it returns false.
     */
    boolean isTalking() {
        return this.talking;
    }

    /**
     * This method is used to set the value of talking.
     * 
     * @param talking boolean value that updates the value of talking. True
     * indicates that hero is talking.
     */
    void setTalking(boolean talking) {
        this.talking = talking;
    }

    /**
     * This method returns the value of previousCommand.
     * 
     * @return String that represents the previous command.
     */
    String getPreviousCommand() {
        return this.previousCommand;
    }

    /**
     * This method sets the value of previousCommand.
     * 
     * @param previousCommand String that represents the previous command.
     */
    void setPreviousCommand(String previousCommand) {
        this.previousCommand = previousCommand;
    }
}
