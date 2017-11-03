package worldofzuul;

// Imports:
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


/**
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

// This class represents a room.
public class Room 
{
    // Data fields:
    private String description; // Description of room
    private String detailedDescription; // This description is called when player looks around
    private HashMap<String, Room> exits; // Available exits from room
    private HashMap<String, Boolean> lockedExits; // List if exits are locked (true = locked, false = unlocked)
    private Inventory inventory; // The place Items are sotred in the room
    private String name; // Name of the room
    private HashMap<String,Boolean> hasCharacter = new HashMap<>(); // Checks if the room has the different characters
    
    // This constructor creates a Room with a specified description string.
    public Room(String description) 
    {
        // Set description
        this.description = description;
        // Create HashMap for exits
        exits = new HashMap<String, Room>();
        lockedExits = new HashMap<String, Boolean>();
        // Create an inventory in the room with the standard capacity (int max value)
        this.inventory = new Inventory();
        this.detailedDescription = "You are in a hallway. There isn't really anything interesting here.";
        this.name = "hallway";
        this.hasCharacter.put("Hero", false);
        this.hasCharacter.put("Zuul", false);
        this.hasCharacter.put("TechDude", false);
    }
    
    // This constructor creates a room with the specified description and name.
    public Room(String description, String roomName) {
        this(description);
        this.name = roomName;
    }
    
    // This constructor creates a room with the specified description, name,
    // and detailed description.
    public Room(String description, String roomName, String detailedDescription){
        this(description, roomName);
        this.detailedDescription = detailedDescription;
    }

    // This method returns the name of the room.
    public String getName() {
        return name;
    }
    
    // This method sets an available exit from the room with a specified 
    // direction string and locked value.
    public void setExit(String direction, Room neighbor, boolean locked) 
    {
        // Add exit to HashMap of exits
        exits.put(direction, neighbor);
        // Add exit to lockedExits
        lockedExits.put(direction, locked);
    }
    
    // This method returns a short description of the room.
    public String getShortDescription()
    {
        return description;
    }

    // This method returns a longer description of the room with a list of available exits.
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }
    
    // This method returns the detailed description
    public String getDetailedDescription() {
        return this.detailedDescription;
    }
    
    // This method returns a string with the available exits.
    private String getExitString()
    {
        String returnString = "Exits:";
        // Create a set of all exit directions from the HashMap
        Set<String> keys = exits.keySet();
        // Traverse the set of exit directions and add them to the exit string
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    // This method returns the room associated with a given exit direction.
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    // This method checks if the specified exit is locked:
    // true = locked
    // false = unlocked
    public boolean getLockedExit(String direction){
        return this.lockedExits.get(direction);
    }

    // This method returns the inventory of the room.
    public Inventory getInventory() {
        return inventory;
    }

    // This method returns the hashmap of locked/unlocked exits
    public HashMap<String, Boolean> getLockedExits() {
        return lockedExits;
    }
    
    // This method returns a string specifying the status of the available exits
    public String getLockedExitString() {
        String returnString = "The status of the exits:\n";
        // Traverse the elements in the lockedExits hashmap
        for (Entry<String, Boolean> exit : lockedExits.entrySet()) {
            // Print whether the given entry is locked or unlocked
            returnString += exit.getKey() + ": " + ((exit.getValue())?"locked\n":"open\n");
        }
        // Return the string of exit statuses
        return returnString;
    }

    // This method returns the hashmap of available exits.
    public HashMap<String, Room> getExits() {
        return exits;
    }
  
    // This method returns true if the given character is in the room, and
    // returns false if the given character is not in the room.
    public boolean getHasCharacter(String character){
        return this.hasCharacter.get(character);
    }
    
    // This method specifies whether the character (specified by a string) is
    // in the room.
    public void setHasCharacter(String character, boolean presence){
        hasCharacter.put(character, presence);
    }
    
    public HashMap<String, Boolean> getHasCharacters(){
        return this.hasCharacter;
    }
    
}
