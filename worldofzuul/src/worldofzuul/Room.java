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
    private boolean hasZuul;

    // This constructor creates a Room with a specified description string.
    public Room(String description) 
    {
        // Set description
        this.description = description;
        // Create HashMap for exits
        exits = new HashMap<String, Room>();
        lockedExits = new HashMap<String, Boolean>();
        // creates a inventory in the room with the sandard capacity (int max value)
        this.inventory = new Inventory();
        this.detailedDescription = "You are in a hallway.";
        this.hasZuul = false;
    }
    
    public Room(String description, String detailedDescription){
        this(description);
        this.detailedDescription = detailedDescription;
    }

    // This method sets an available exit from the room with a specified 
    // direction string.
    public void setExit(String direction, Room neighbor, boolean locked) 
    {
        // Add exit to HashMap of exits
        exits.put(direction, neighbor);
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

    public Inventory getInventory() {
        return inventory;
    }

    public HashMap<String, Boolean> getLockedExits() {
        return lockedExits;
    }
    
    public String getLockedExitString() {
        String returnString = "The status of the exits:\n";
        for (Entry<String, Boolean> exit : lockedExits.entrySet()) {
            returnString += exit.getKey() + ": " + ((exit.getValue())?"locked\n":"open\n");
        }
        return returnString;
    }

    public void setHasZuul(boolean hasZuul) {
        this.hasZuul = hasZuul;
    }

    public HashMap<String, Room> getExits() {
        return exits;
    }

    public boolean getHasZuul() {
        return hasZuul;
    }
    
    
    
    
    
    
}
