package Logic;

// Imports:
import Acquaintance.IRoom;
import java.io.Serializable;
import java.util.ArrayList;
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
public class Room implements Serializable, IRoom {
    /**
     * description: short description of the room.
     */
    private String description;
    /**
     * detailedDescription: longer description of the room.
     */
    private String detailedDescription;
    /**
     * exits: HashMap of available exits from the room.
     */
    private HashMap<String, Room> exits;
    /**
     * lockedExits: HashMap indicating the status of the exits from the room.
     */
    private HashMap<String, Boolean> lockedExits;
    /**
     * inventory: an instance of Inventory representing the room's inventory.
     * name: name of the room.
     */
    private Inventory inventory;
    /**
     * name: The name of the room.
     */
    private String name;
    /**
     * hasCharacter: HashMap indicating if the different characters are present
     * in the room.
     */
    private HashMap<String,Boolean> hasCharacter = new HashMap<>();
    /**
     * charactersInRoom: ArrayList of Strings that represents the characters in
     * the room.
     */
    private ArrayList<String> charactersInRoom;
    /**
     * hasBeenLookedUpon: indicates whether the player has "looked" at the room,
     * thereby unlocking the room's inventory.
     */
    private boolean hasBeenLookedUpon;
    /**
     * messageClass: instance of LogicMessage for storing strings to be read
     * later.
     */
    private LogicMessage messageClass;
    
    /**
     * This constructor creates a room with a specified description string. The
     * values of name and detailedDescription represent a hallway as this is the
     * default room type. More detailed constructors are used for non-hallway
     * rooms.
     * 
     * @param description short description of the room indicating which
     * hallway it is.
     */
    Room(String description) {
        this.description = description;
        // Create HashMaps for exits
        this.exits = new HashMap<String, Room>();
        this.lockedExits = new HashMap<String, Boolean>();
        this.charactersInRoom = new ArrayList<>();
        /*
        Create an inventory for the room with a standard capacity (int max
        value).
        */
        this.inventory = new Inventory(messageClass);
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
     * @param description short description of the room.
     * @param roomName name of the room.
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
     * @param description short description of the room.
     * @param roomName name of the room.
     * @param detailedDescription longer description of the room.
     */
    Room(String description, String roomName, String detailedDescription) {
        this(description, roomName);
        this.detailedDescription = detailedDescription;
    }
    
    /**
     * This constructor creates a room with the specified description and name.
     * The constructor is used to create the escape pod room, as it has a name
     * different from hallway. Even so, since the escape pod is the win
     * condition, it does not need a detailed description. Therefore, this is
     * left at the hallway default. The constructor uses constructor chaining.
     * 
     * @param description short description of the room.
     * @param roomName name of the room.
     * @param messageClass instance of LogicMessage for storing strings to be
     * read later.
     */
    Room(String description, String roomName, LogicMessage messageClass) {
        this(description);
        this.name = roomName;
        this.messageClass = messageClass;
    }
    
    /**
     * This constructor creates a room with the specified description, name, and
     * detailed description. The constructor is used to create the non-hallway
     * rooms in the game. The constructor uses constructor chaining.
     * 
     * @param description short description of the room.
     * @param roomName name of the room.
     * @param detailedDescription longer description of the room.
     * @param messageClass an instance of LogicMessage for storing strings to
     * be read later.
     * 
     */
    Room(String description, String roomName, String detailedDescription,
        LogicMessage messageClass) {
        this(description, roomName, messageClass);
        this.detailedDescription = detailedDescription;
    }

    /**
     * This method returns the name of the room.
     * 
     * @return name
     */
    @Override
    public String getName() {
        return this.name;
    }
    
    /**
     * This method returns the inventory of the room.
     * 
     * @return inventory
     */
    Inventory getInventory() {
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
    HashMap<String, Room> getExits() {
        return this.exits;
    }

    /**
     * This method returns the room associated with a given exit direction.
     * 
     * @param direction indicates the direction of the exit.
     * 
     * @return the Room object associated with the given direction.
     */
    Room getExit(String direction) {
        return this.exits.get(direction);
    }
    
    /**
     * This method returns the HashMap of exits indicating the status of the
     * exits.
     * 
     * @return lockedExits
     */
    HashMap<String, Boolean> getLockedExits() {
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
    boolean isExitLocked(String direction){
        return this.lockedExits.get(direction);
    }
    
    /**
     * This method returns a String specifying the status of the available
     * exits. This is done by traversing the lockedExits HashMap and assigning
     * locked/open status to the exits based on their boolean value (true
     * becomes locked and false becomes open).
     * 
     * @return a String that lists the available exits and indicates whether the
     * exit is locked or open.
     */
    String getLockedExitString() {
        String returnString = "The status of the exits:\n";
        // Traverse the elements in the lockedExits hashmap
        for (Entry<String, Boolean> exit : lockedExits.entrySet()) {
            // Print whether the given entry is locked or open
            returnString += exit.getKey() + ": " 
                + ((exit.getValue())?"locked\n":"open\n");
        }
        // Return the string of exit statuses
        return returnString;
    }
    
    /**
     * This method returns a HashMap which indicates the presence or absence of
     * the characters in the game. This is done by returning the HashMap
     * hasCharacter.
     * 
     * @return hasCharacter
     */
    HashMap<String, Boolean> getHasCharacters(){
        return this.hasCharacter;
    }
  
    /**
     * This method is used to check whether a given character is in the room.
     * This is done by checking the corresponding entry in the hasCharacter
     * HashMap.
     * 
     * @param character a String that specifies the character whose presence in
     * the room is to be tested.
     * 
     * @return true if the character is in the room, and false if the character
     * is not in the room.
     */
    boolean hasCharacter(String character){
        return this.hasCharacter.get(character);
    }
    
    /**
     * This method is used to specify if a certain character is in the room.
     * This is done by updating the corresponding entry in the HashMap
     * hasCharacter.
     * 
     * @param character a String that specifies the character whose presence is
     * to be set.
     * @param presence a boolean value that indicates whether the character is
     * present in the room; true indicates that the room contains the character,
     * while false indicates that the character is not in the room.
     */
    private void setHasCharacter(String character, boolean presence){
        this.hasCharacter.put(character, presence);
    }

    /**
     * This method checks whether the player has already looked at the room
     * using the "look" command. This is done by returning the boolean value
     * hasBeenLookedUpon. If the player has already looked at a room, the room's
     * inventory remains unlocked if the player returns to the room at a later
     * point in the game.
     * 
     * @return hasBeenLookedUpon, which is true if the player has looked at the
     * room and false if the player has not looked at the room.
     */
    boolean hasBeenLookedUpon() {
        return this.hasBeenLookedUpon;
    }

    /**
     * This method sets the value of the data field hasBeenLookedUpon. The
     * method is called when the player looks at a room and sets the value to
     * true. This allows the game to check, at a later point, whether the player
     * has looked at the room.
     * 
     * @param hasBeenLookedUpon the new value of hasBeenLookedUpon (true)
     */
    void setHasBeenLookedUpon(boolean hasBeenLookedUpon) {
        this.hasBeenLookedUpon = hasBeenLookedUpon;
    } 
    
    /**
     * This method is used to add a character to the room.
     * 
     * @param character String that represents the character to be added to
     * the room.
     */
    void addCharacterInRoom(String character){
        // Add character to the ArrayList of characters.
        this.charactersInRoom.add(character);
        // Set the character's presence as true.
        this.setHasCharacter(character, true);
    }
    
    /**
     * This method is used to remove a character from the room.
     * 
     * @param character String that represents the character to be removed.
     */
    void removeCharacterInRoom(String character){
        // Remove character from ArrayList of characters.
        this.charactersInRoom.remove(character);
        /*
        Check if the room still contains a character after the character has
        been removed. For example, if a Zuul is removed, there might still be
        another Zuul in the room. If this is the case, the presence of the
        character is set to true.
        */
        if (this.charactersInRoom.contains(character)) {
            this.setHasCharacter(character, true);
        }
        /*
        If no characters of this type are present, set the presence of the
        character to false.
        */
        else {
            this.setHasCharacter(character, false);
        }
    }
    
    /**
     * This method returns an ArrayList of the characters in the room.
     * 
     * @return ArrayList of Strings that represents the characters in the room.
     */
    ArrayList<String> getCharacterInRoom(){
        return this.charactersInRoom;
    }
}
