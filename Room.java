package worldofzuul;

// Imports:
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


/**
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
// This is the Room class.
public class Room 
{
    // Data fields:
    private String description;
    private HashMap<String, Room> exits;

    // This contructor creates a Room object with the specified description.
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
    }

    // This method sets an exit for the given Room.
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    // This method returns the description of the Room.
    public String getShortDescription()
    {
        return description;
    }

    /*
    This method returns a longer description of the current Room and possible 
    exits.
    */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    // This method returns a string listing the possible exits.
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    // This method returns the Room associated with the given exit.
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

