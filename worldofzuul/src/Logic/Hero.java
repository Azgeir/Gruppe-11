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

        if (command.hasSecondWord()) {
            String itemName = command.getSecondWord();
            Item item = this.inventory.getItem(itemName);

            if (item == null) {
                messageClass.appendMessage("You don't have such an item.");
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
                            messageClass.appendMessage("There isn't room in this room for that.");
                        } else if ((numberAdded < number) && (numberNonExisting == 0)) {
                            messageClass.appendMessage("You could only drop " + numberAdded + " " + itemName + " because there is not room enought for all of them in this room.");
                        } else if (numberNonExisting > 0 && (numberNonExisting + numberAdded) == number) {
                            messageClass.appendMessage("You dropped " + numberAdded + " " + itemName + " because there is only " + numberAdded + " in your inventory.");
                        } else {
                            messageClass.appendMessage("You dropped " + number + " " + itemName);
                        }
                    } else {
                        messageClass.appendMessage("The third word needs to be an integer.");
                    }
                } else {
                    boolean itemAdded = this.getCurrentRoom().getInventory().addItem(item);
                    if (itemAdded) {
                        this.inventory.removeItem(item);
                        messageClass.appendMessage("You dropped " + item.getName());
                    } else {
                        messageClass.appendMessage("There isn't room in this room for that item.");
                    }
                }
            }
        } else {
            messageClass.appendMessage("Drop what?");
        }

        this.speedFactorCalculation();
        this.setCharacterInitiative(this.getCharacterInitiative() + 2.5 * this.getSpeedFactor());
    }

    //Gives the player a detailed description of the items and the possibilities of a room
    /**
     *
     * @param command
     */
    private void look(Command command) {

        if (command.hasSecondWord()) {
            String direction = command.getSecondWord();
            if (direction.equals("around")) {
                // Print detailed description of room
                messageClass.appendMessage(this.getCurrentRoom().getDetailedDescription());
                // Prints out the characters present in the room, except for the player
                for (Entry<String, Boolean> hasCharacter : this.getCurrentRoom().getHasCharacters().entrySet()) {
                    if (hasCharacter.getValue() && !hasCharacter.getKey().equals("Hero")) {
                        messageClass.appendMessage("\n" + hasCharacter.getKey() + " is in this room");
                    }
                }

                // Print inventory of current room
                messageClass.appendMessage("There is the following in the room:\n" + this.getCurrentRoom().getInventory().showItems());
                // Print status of exits
                messageClass.appendMessage(this.getCurrentRoom().getLockedExitString());
                this.getCurrentRoom().setHasBeenLookedUpon(true);
                this.setCharacterInitiative(this.getCharacterInitiative() + 1 * this.getSpeedFactor());
            } else if (direction.equals("inventory")) {
                messageClass.appendMessage("There is the following in your inventory:\n" + this.getInventory().showItems());
                this.setCharacterInitiative(this.getCharacterInitiative() + 1.5 * this.getSpeedFactor());
            } else {
                messageClass.appendMessage("There is no such direction to look.");
            }
        } else {
            messageClass.appendMessage("Look where");
        }
    }

    //Checks whatever Zuul is in the next room and gives feedback if it is or isn't
//    @Override
   
    private void peek(Command command) {
        String direction = command.getSecondWord();

        boolean zuulNearby = false;
        
        this.knownRooms.clear();
        this.knownRooms.add(this.getCurrentRoom().getName());

        if (this.getCurrentRoom().hasCharacter("Zuul")) {
            messageClass.appendMessage("Zuul is in this room, you idiot.");
            zuulNearby = true;
        }

        if (this.getCurrentRoom().getExit(direction) != null) {
            for (Room neighbor : this.getCurrentRoom().getExits().values()) {
                this.knownRooms.add(neighbor.getName());
                if (neighbor.hasCharacter("Zuul")) {
                    messageClass.appendMessage("Zuul is " + neighbor.getShortDescription());
                    zuulNearby = true;
                }
            }

            Room neighbor = this.getCurrentRoom().getExit(direction);
            if (neighbor.getExit(direction) != null) {
                this.knownRooms.add(neighbor.getExit(direction).getName());
//                    neighbor.getHasCharacter("Zuul")) {
                if (neighbor.getExit(direction).hasCharacter("Zuul")) {
                    messageClass.appendMessage("Zuul is " + neighbor.getExit(direction).getShortDescription());
                    zuulNearby = true;

                }
            }

            if (!zuulNearby) {
                messageClass.appendMessage("There is no Zuul nearby.");
            }
        } else {
            messageClass.appendMessage("There is no direction by that name.");
        }

        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
    }

    //This command is used to lock a door
    private void lock(Command command) {
        String direction = command.getSecondWord();
        boolean lock = true;

        boolean directionExists = this.getCurrentRoom().getExits().containsKey(direction);

        if (directionExists) {
            if (this.getInventory().getItem("accesscard") != null) {
                this.lockUnlock(direction, lock);
                messageClass.appendMessage("You locked the door.");
            } else {
                messageClass.appendMessage("You don't have an access card to do that with.");
            }
        }

        // If there isn't any door that matches the secondWord, then this is print
        if (!directionExists) {
            messageClass.appendMessage("There isn't an exit by that name.");

        }
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());

    }

    //This command is used to unlock a door
//    @Override
    private void unlock(Command command) {
        String direction = command.getSecondWord();
        boolean lock = false;
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
