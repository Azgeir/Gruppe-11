/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
     */
    private int health = 10;
    private Inventory inventory;
    private boolean reactorActivated = false;
    private Set<String> knownRooms;
    private boolean talking;
    private String previousCommand;
    

    /**
     * This constructor creates a Hero object with the specified current room
     * and name. The hero has an inventory of default size 100. The set of
     * known rooms is initialized and the current room of the character is added
     * to the set.
     * 
     * @param currentRoom the hero's current room
     * @param name represents the character type (e.g. "Hero")
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
     * This method moves the hero according to the command passed.
     * 
     * @param command Is the command the hero receives. Consists of 3 words
     * where the second defines the direction the hero attempt to go.
     */
    @Override
    void go(Command command) {
        // If the command does not have a second word, print error message
        if (!command.hasSecondWord()) {
            LogicFacade.appendMessage("Go where?");
            return;
        }

        // Set the direction string to the second word of the command
        String direction = command.getSecondWord();

        // Set nextRoom to be the neighbouring room specified by the direction string
        Room nextRoom = this.getCurrentRoom().getExit(direction);

        // If the specified exit does not exist, print error message
        if (nextRoom == null) {
            LogicFacade.appendMessage("There is no door!");
        } 
        // If the specified exit is locked, print locked message
        else if (this.getCurrentRoom().isExitLocked(direction)){
            LogicFacade.appendMessage("This exit is locked, so you can't get through.");
        }
        // If the specified exit exists and is not locked, move character
        else {
            // Remove character from current room
            this.getCurrentRoom().removeCharacterInRoom(this.getName());
            this.knownRooms.clear();
            // Change the value of currentRoom to the specified neighbouring room
            this.setCurrentRoom(nextRoom);
            // Add character to the new current room
            this.getCurrentRoom().addCharacterInRoom(this.getName());
            this.knownRooms.add(this.getCurrentRoom().getName());
            // Print description of the current room
            LogicFacade.appendMessage(this.getCurrentRoom().getLongDescription());
        }
        
        if (this.getCurrentRoom().hasCharacter("Zuul")){
            LogicFacade.appendMessage("The Zuul is in this room.");
        }
        
        this.setCharacterInitiative(this.getCharacterInitiative() + 10 * this.getSpeedFactor());
    }
        
    /**
     * This method picks up an item from the heros current room and moves it 
     * to the heros inventory.
     * 
     * @param command Is the command the hero receives. Consists of 3 words
     * where the second is the name of the item to pick up, and the third is an 
     * optional number of items to pick up.
     */
    void pickUp(Command command) {

        if (command.hasSecondWord()) {
            String itemName = command.getSecondWord();
            Item item = this.getCurrentRoom().getInventory().getItem(itemName);

            if (item == null) {
                LogicFacade.appendMessage("The room doesn't contain that item.");
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
                            LogicFacade.appendMessage("You can't carry any of that.");
                        } else if ((numberAdded < number) && (numberNonExisting == 0)) {
                            LogicFacade.appendMessage("You could only pick up " + numberAdded + " " + itemName);
                        } else if (numberNonExisting > 0 && (numberNonExisting + numberAdded) == number) {
                            LogicFacade.appendMessage("You picked up " + numberAdded + " " + itemName + " because there is only " + numberAdded + " in this room.");
                        } else {
                            LogicFacade.appendMessage("You picked up " + number + " " + itemName);
                        }
                    } else {
                        LogicFacade.appendMessage("The third word needs to be an integer.");
                    }
                } else {
                    boolean itemAdded = this.inventory.addItem(item);
                    if (itemAdded) {
                        this.getCurrentRoom().getInventory().removeItem(item);
                        LogicFacade.appendMessage("You picked up " + item.getName());
                    } else {
                        LogicFacade.appendMessage("You can't carry that.");
                    }
                }
            }
        } else {
            LogicFacade.appendMessage("Pickup what?");
        }

        this.speedFactorCalculation();
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
    }

    
    /**
     * This method drops an item from the heros inventory and moves it to the
     * current room.
     * 
     * @param command Is the command the hero receives. Consists of 3 words
     * where the second is the name of the item to drop, and the third is an 
     * optional number of items to drop.
     */
    void dropItem(Command command) {

        if (command.hasSecondWord()) {
            String itemName = command.getSecondWord();
            Item item = this.inventory.getItem(itemName);

            if (item == null) {
                LogicFacade.appendMessage("You don't have such an item.");
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
                            LogicFacade.appendMessage("There isn't room in this room for that.");
                        } else if ((numberAdded < number) && (numberNonExisting == 0)) {
                            LogicFacade.appendMessage("You could only drop " + numberAdded + " " + itemName + " because there is not room enought for all of them in this room.");
                        } else if (numberNonExisting > 0 && (numberNonExisting + numberAdded) == number) {
                            LogicFacade.appendMessage("You dropped " + numberAdded + " " + itemName + " because there is only " + numberAdded + " in your inventory.");
                        } else {
                            LogicFacade.appendMessage("You dropped " + number + " " + itemName);
                        }
                    } else {
                        LogicFacade.appendMessage("The third word needs to be an integer.");
                    }
                } else {
                    boolean itemAdded = this.getCurrentRoom().getInventory().addItem(item);
                    if (itemAdded) {
                        this.inventory.removeItem(item);
                        LogicFacade.appendMessage("You dropped " + item.getName());
                    } else {
                        LogicFacade.appendMessage("There isn't room in this room for that item.");
                    }
                }
            }
        } else {
            LogicFacade.appendMessage("Drop what?");
        }

        this.speedFactorCalculation();
        this.setCharacterInitiative(this.getCharacterInitiative() + 2.5 * this.getSpeedFactor());
    }

    //Gives the player a detailed description of the items and the possibilities of a room
    /**
     *
     * @param command
     */
    void look(Command command) {

        if (command.hasSecondWord()) {
            String direction = command.getSecondWord();
            if (direction.equals("around")) {
                // Print detailed description of room
                LogicFacade.appendMessage(this.getCurrentRoom().getDetailedDescription());
                // Prints out the characters present in the room, except for the player
                for (Entry<String, Boolean> hasCharacter : this.getCurrentRoom().getHasCharacters().entrySet()) {
                    if (hasCharacter.getValue() && !hasCharacter.getKey().equals("Hero")) {
                        LogicFacade.appendMessage("\n" + hasCharacter.getKey() + " is in this room");
                    }
                }

                // Print inventory of current room
                LogicFacade.appendMessage("There is the following in the room:\n" + this.getCurrentRoom().getInventory().showItems());
                // Print status of exits
                LogicFacade.appendMessage(this.getCurrentRoom().getLockedExitString());
                this.getCurrentRoom().setHasBeenLookedUpon(true);
                this.setCharacterInitiative(this.getCharacterInitiative() + 1 * this.getSpeedFactor());
            } else if (direction.equals("inventory")) {
                LogicFacade.appendMessage("There is the following in your inventory:\n" + this.getInventory().showItems());
                this.setCharacterInitiative(this.getCharacterInitiative() + 1.5 * this.getSpeedFactor());
            } else {
                LogicFacade.appendMessage("There is no such direction to look.");
            }
        } else {
            LogicFacade.appendMessage("Look where");
        }
    }

    //Checks whatever Zuul is in the next room and gives feedback if it is or isn't
//    @Override
    void peek(Command command) {
        String direction = command.getSecondWord();

        boolean zuulNearby = false;
        
        this.knownRooms.clear();
        this.knownRooms.add(this.getCurrentRoom().getName());

        if (this.getCurrentRoom().hasCharacter("Zuul")) {
            LogicFacade.appendMessage("Zuul is in this room, you idiot.");
            zuulNearby = true;
        }

        if (this.getCurrentRoom().getExit(direction) != null) {
            for (Room neighbor : this.getCurrentRoom().getExits().values()) {
                this.knownRooms.add(neighbor.getName());
                if (neighbor.hasCharacter("Zuul")) {
                    LogicFacade.appendMessage("Zuul is " + neighbor.getShortDescription());
                    zuulNearby = true;
                }
            }

            Room neighbor = this.getCurrentRoom().getExit(direction);
            if (neighbor.getExit(direction) != null) {
                this.knownRooms.add(neighbor.getExit(direction).getName());
//                    neighbor.getHasCharacter("Zuul")) {
                if (neighbor.getExit(direction).hasCharacter("Zuul")) {
                    LogicFacade.appendMessage("Zuul is " + neighbor.getExit(direction).getShortDescription());
                    zuulNearby = true;

                }
            }

            if (!zuulNearby) {
                LogicFacade.appendMessage("There is no Zuul nearby.");
            }
        } else {
            LogicFacade.appendMessage("There is no direction by that name.");
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
                LogicFacade.appendMessage("You locked the door.");
            } else {
                LogicFacade.appendMessage("You don't have an access card to do that with.");
            }
        }

        // If there isn't any door that matches the secondWord, then this is print
        if (!directionExists) {
            LogicFacade.appendMessage("There isn't an exit by that name.");

        }
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());

    }

    //This command is used to unlock a door
//    @Override
    void unlock(Command command) {
        String direction = command.getSecondWord();
        boolean lock = false;
        boolean directionExists = false;

        for (String exit : this.getCurrentRoom().getExits().keySet()) {
            if (direction.equals(exit)) {
                if (this.getInventory().getItem("accesscard") != null) {
                    this.lockUnlock(direction, lock);
                    LogicFacade.appendMessage("You unlocked the door.");
                } else {
                    LogicFacade.appendMessage("You don't have an access card to do that with.");
                }
                directionExists = true;
            }
        }
        // If there isnt any door that matches the secondWord then this is print
        if (!directionExists) {
            LogicFacade.appendMessage("There isn't any exit by that name.");
        }
        this.setCharacterInitiative(this.getCharacterInitiative() + 5 * this.getSpeedFactor());
    }

    //Command for using an Item in your inventory
//    @Override
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
                LogicFacade.appendMessage("You don't have any such item.");
            }
        } else {
            LogicFacade.appendMessage("You have to select something to use.");
        }

        return 0;
    }
    
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
                LogicFacade.appendMessage("The station is under quarantine and you therefore can't open the door.\nPerhaps you could find something or someone to force it open.");
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
//    @Override
    double activate(Command command, boolean reactorActivated) {
        this.clearMessage();
        this.setCharacterInitiative(this.getCharacterInitiative() + 15 * this.getSpeedFactor());
        if (!command.hasSecondWord()) {
            LogicFacade.appendMessage("Activate what?");
            return Double.MAX_VALUE;
        }
        if (command.getSecondWord().equals("Reactor")) {

            if (this.getCurrentRoom().getName().equals("Reactor")) {
                if (this.getCurrentRoom().hasCharacter("TechDude")) {
                    if (!reactorActivated) {
                        LogicFacade.appendMessage("You activated the reactor. The space station will self-destruct in an unspecified amount of time.");
                        this.reactorActivated = true;
                        this.setMessage("Reactor activated");
                        return (this.getCharacterInitiative() + 80);
                    } else {
                        LogicFacade.appendMessage("The reactor is already activated.");
                        return Double.MAX_VALUE;
                    }
                } else {
                    LogicFacade.appendMessage("You need the tech dude to do this.");
                    return Double.MAX_VALUE;
                }
            } else {
                LogicFacade.appendMessage("There is no reactor in this room.");
                return Double.MAX_VALUE;
            }

        } else {
            LogicFacade.appendMessage("You can't activate anything by that name.");
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
    
    boolean isReactorActivated() {
        return this.reactorActivated;
    }

    @Override
    public double performCommand(Command command) {
        // Create instance of CommandWord using the command word of the specified command (from Parser)
        CommandWord commandWord = command.getCommandWord();
        
        this.talking = false;
        
        if (null != commandWord) // Execute the command if the input matches a valid command
        {
            switch (commandWord) {
                // If command is "go", call go() method on current character
                case GO:
                    this.go(command);
                    break;
                case STAY:
                    this.stay(command);
                    LogicFacade.appendMessage("You stay in this room");
                    break;
                // If command is "pickup", call pickup() method on current character
                case PICKUP:
                    this.pickUp(command);
                    break;
                // If command is "drop", call dropItem() method on current character
                case DROP:
                    this.dropItem(command);
                    break;
                // If command is "look", call look() method on current character
                case LOOK:
                    this.look(command);
                    break;
                // If command is "peek", call peek() method on current character
                case PEEK:
                    this.peek(command);
                    break;
                // (?)If command is "use", call use() method on current character and change Zuul's initiative
                case USE:
                    return this.use(command);
                // If command is "lock", call lock() command on current character
                case LOCK:
                    this.lock(command);
                    break;
                // If command is "unlock", call unlock() command on current character
                case UNLOCK:
                    this.unlock(command);
                    break;
                // If command is "activate", set MaxInitiative to the return value of the activate() method
                case ACTIVATE:
                    return this.activate(command, reactorActivated);
                // If command does not match any of the options, break.
                default:
                    break;
            }
        }
        // Return boolean value (false = continue playing; true = quit game)
        previousCommand = commandWord.toString();
        return 0;
    }

    public Set<String> getKnownRooms() {
        return knownRooms;
    }

    public boolean isTalking() {
        return talking;
    }

    public void setTalking(boolean talking) {
        this.talking = talking;
    }

    public String getPreviousCommand() {
        return previousCommand;
    }

    public void setPreviousCommand(String previousCommand) {
        this.previousCommand = previousCommand;
    }
    
    
    
    
    
    
}
