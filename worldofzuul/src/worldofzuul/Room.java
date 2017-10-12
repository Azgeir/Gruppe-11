package worldofzuul;

// Imports:
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


/**
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

// This class represents a room.
public class Room 
{
    // Data fields:
    private String description; // Description of room
    private HashMap<String, Room> exits; // Available exits from room

    // This constructor creates a Room with a specified description string.
    public Room(String description) 
    {
        // Set description
        this.description = description;
        // Create HashMap for exits
        exits = new HashMap<String, Room>();
    }

    // This method sets an available exit from the room with a specified 
    // direction string.
    public void setExit(String direction, Room neighbor) 
    {
        // Add exit to HashMap of exits
        exits.put(direction, neighbor);
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
}

