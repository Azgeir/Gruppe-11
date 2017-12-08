// The class is located in the logic layer.
package worldofzuul;

// Import:
import java.io.Serializable;

/**
 * This class was originally part of the source framework.
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

/**
 * This enum class defines the available command words. It implements the
 * interface Serializable.
 */

enum CommandWord implements Serializable {
    /**
     * This defines the available enums for the command words.
     */
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"), PICKUP("pickup"),
    DROP("drop"), STAY("stay"), LOOK("look"), PEEK("peek"),
    USE("use"), LOCK("lock"), UNLOCK("unlock"), ACTIVATE("activate"),
    TALK("talk"), KILL("kill");
    
    /**
     * Data field.
     * commandString: command string of the CommandWord object.
     */
    private String commandString;
    
    /**
     * This constructor creates a CommandWord object using the specified command
     * string.
     * 
     * @param commandString 
     */
    private CommandWord(String commandString) {
        this.commandString = commandString;
    }
    
    /**
     * This toString() method returns the command's command string.
     * 
     * @return commandString
     */
    @Override
    public String toString() {
        return commandString;
    }
}
