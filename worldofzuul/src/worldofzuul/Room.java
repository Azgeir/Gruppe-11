package worldofzuul;

// Imports:
import java.io.Serializable;
import java.util.Set;
import java.util.HashMap;
import java.util.Map.Entry;


/**
 * This class was part of the source code framework.
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

/**
 * This class represents a room. The class implements the Serializable
 * interface.
 */
public class Room implements Serializable {
    /**
     * Data fields.
     * description: short description of the room.
     * detailedDescription: longer description of the room.
     * exits: HashMap of available exits from the room.
     * lockedExits: HashMap indicating the status of the exits from the room.
     * inventory: an instance of Inventory representing the room's inventory.
     * name: name of the room.
     * hasCharacter: HashMap indicating if the different characters are present
     * in the room.
     * hasBeenLookedUpon: indicates whether the player has "looked" at the room,
     * thereby unlocking the room's inventory.
     */
    private String description;
    private String detailedDescription;
    private HashMap<String, Room> exits;
    private HashMap<String, Boolean> lockedExits;
    private Inventory inventory;
    private String name;
    private HashMap<String,Boolean> hasCharacter = new HashMap<>();
    private boolean hasBeenLookedUpon;
    
    /**
     * This constructor creates a room with a specified description string. The
     * values of name and detailedDescription represent a hallway as this is the
     * default room type. More detailed constructors are used for non-hallway
     * rooms.
     * 
     * @param description, short description of the room indicating which
     * hallway it is.
     */
    Room(String description) {
        this.description = description;
        // Create HashMaps for exits
        this.exits = new HashMap<String, Room>();
        this.lockedExits = new HashMap<String, Boolean>();
        /*
        Create an inventory for the room with a standard capacity (int max
        value).
        */
        this.inventory = new Inventory();
        /*
        By default, the detailed description and name of the room represents a
        hallway.
        */
        this.detailedDescription = "You are in a hallway. There isn't really "
            + "anything interesting here.";
        this.name = "hallway";
        // By default, a room does not contain any of the characters.
        this.hasCharacter.put("Hero", false);
        this.hasCharacter.put("Zuul", false);
        this.hasCharacter.put("TechDude", false);
        // At the start of the game, the player has not looked at the room.
        this.hasBeenLookedUpon = false;
    }
    
    /**
     * This constructor creates a room with the specified description and name.
     * The constructor is used to create the escape pod room, as it has a name
     * different from hallway. Even so, since the escape pod is the win
     * condition, it does not need a detailed description. Therefore, this is
     * left at the hallway default. The constructor uses constructor chaining.
     * 
     * @param description, short description of the room.
     * @param roomName, name of the room.
     */
    Room(String description, String roomName) {
        this(description);
        this.name = roomName;
    }
    
    /**
     * This constructor creates a room with the specified description, name, and
     * detailed description. The constructor is used to create the non-hallway
     * rooms in the game. The constructor uses constructor chaining.
     * 
     * @param description, short description of the room.
     * @param roomName, name of the room.
     * @param detailedDescription, longer description of the room.
     */
    Room(String description, String roomName, String detailedDescription) {
        this(description, roomName);
        this.detailedDescription = detailedDescription;
    }

    /**
     * This method returns the name of the room.
     * 
     * @return name
     */
    String getName() {
        return this.name;
    }
    
    /**
     * This method returns the inventory of the room.
     * 
     * @return inventory
     */
    public Inventory getInventory() {
        return this.inventory;
    }
    
    /**
     * This method returns a short description of the room.
     * 
     * @return description
     */
    String getShortDescription() {
        return this.description;
    }
    
    /**
     * This method returns the detailed description of the room.
     * 
     * @return detailedDescription
     */
    String getDetailedDescription() {
        return this.detailedDescription;
    }
    
    /**
     * This method returns a longer description of the room with a list of 
     * available exits.
     * 
     * @return a String description of the room and its available exits.
     */
    String getLongDescription() {
        return "You are " + this.description + ".\n" + getExitString();
    }
    
    /**
     * This method returns a String with the available exits. It traverses the
     * keys in the exits HashMap and returns a String with these keys.
     * 
     * @return returnString, which lists the available exits from the room.
     */
    private String getExitString() {
        String returnString = "Exits:";
        // Create a set of all exit directions from the HashMap exits
        Set<String> keys = this.exits.keySet();
        // Traverse the set of exit directions and add them to the exit string
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
    
    /**
     * This method sets an available exit from the room with the specified 
     * direction and status.
     * 
     * @param direction indicates the direction of the exit from the room.
     * @param neighbor indicates the room the exit leads to.
     * @param locked indicates whether or not the exit is locked.
     */
    void setExit(String direction, Room neighbor, boolean locked) {
        // Add exit to HashMap of exits.
        this.exits.put(direction, neighbor);
        // Add exit to lockedExits based on the status of the exit.
        this.lockedExits.put(direction, locked);
    }

    /**
     * This method returns the HashMap of available exits from the room.
     * 
     * @return exits
     */
    public HashMap<String, Room> getExits() {
        return this.exits;
    }

    /**
     * This method returns the room associated with a given exit direction.
     * 
     * @param direction indicates the direction of the exit.
     * 
     * @return the Room object associated with the given direction.
     */
    public Room getExit(String direction) {
        return this.exits.get(direction);
    }
    
    /**
     * This method returns the HashMap of exits indicating the status of the
     * exits.
     * 
     * @return lockedExits
     */
    public HashMap<String, Boolean> getLockedExits() {
        return this.lockedExits;
    }
    
    /**
     * This method checks if the specified exit is locked. This is done by
     * returning the boolean value associated with the direction in the HashMap
     * lockedExits.
     * 
     * @param direction indicates the direction of the exit.
     * 
     * @return true if the exit is locked and false if the exit is unlocked.
     */
    boolean getLockedExit(String direction){
        return this.lockedExits.get(direction);
    }
    
    // This method returns a string specifying the status of the available exits
    String getLockedExitString() {
        String returnString = "The status of the exits:\n";
        // Traverse the elements in the lockedExits hashmap
        for (Entry<String, Boolean> exit : lockedExits.entrySet()) {
            // Print whether the given entry is locked or unlocked
            returnString += exit.getKey() + ": " + ((exit.getValue())?"locked\n":"open\n");
        }
        // Return the string of exit statuses
        return returnString;
    }
    
    public HashMap<String, Boolean> getHasCharacters(){
        return this.hasCharacter;
    }
  
    // This method returns true if the given character is in the room, and
    // returns false if the given character is not in the room.
    boolean getHasCharacter(String character){
        return this.hasCharacter.get(character);
    }
    
    // This method specifies whether the character (specified by a string) is
    // in the room.
    void setHasCharacter(String character, boolean presence){
        hasCharacter.put(character, presence);
    }

    boolean isHasBeenLookedUpon() {
        return hasBeenLookedUpon;
    }

    void setHasBeenLookedUpon(boolean hasBeenLookedUpon) {
        this.hasBeenLookedUpon = hasBeenLookedUpon;
    } 
}