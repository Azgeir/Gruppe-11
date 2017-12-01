package worldofzuul;

// Imports:
import java.io.Serializable;
import java.util.HashMap;

/**
 * This class was originally part of the source framework.
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

/**
 * This class represents the command words of the game. It implements the
 * interface Serializable, which allows the game to be saved.
 */
public class CommandWords  implements Serializable
{
    /**
     * Data field.
     * validCommands: HashMap of valid commands with a String key and
     * CommandWord value
     */
    private HashMap<String, CommandWord> validCommands;

    /**
     * This constructor creates an instance of CommandWords. It initializes the
     * HashMap validCommands by traversing the values in CommandWord and
     * setting the command string as the key and the CommandWord as the value.
     */
    CommandWords() {
        // Create a HashMap with valid commands
        validCommands = new HashMap<String, CommandWord>();
        // Traverse the commands from CommandWord class
        for(CommandWord command : CommandWord.values()) {
            // All known commands are added to the HashMap of valid commands.
            if(command != CommandWord.UNKNOWN) {
                // Key = command string, value = command
                validCommands.put(command.toString(), command);
            }
        }
    }

    /**
     * This method returns the CommandWord associated with the given command
     * string.
     * 
     * @param commandWord, a string which is associated with a CommandWord.
     * 
     * @return CommandWord associated with the given string.
     */
    CommandWord getCommandWord(String commandWord) {
        /*
        Assign the CommandWord associated with the commandWord string to the
        variable command.
        */
        CommandWord command = validCommands.get(commandWord);
        // If command is not unknown, return the command
        if(command != null) {
            return command;
        }
        // If command is null, return unknown
        else {
            return CommandWord.UNKNOWN;
        }
    }
    
    /**
     * This method checks if a specified string is associated with a valid
     * command in the HashMap validCommands.
     * 
     * @param aString, which might be associated with a command.
     * 
     * @return true if the string is associated with a command; else it returns
     * false.
     */
    boolean isCommand(String aString) {
        // Check if the given string is a key in the HashMap of valid commands
        return validCommands.containsKey(aString);
    }

    /**
     * This method prints the valid commands. It traverses all keys in the
     * HashMap and prints these. "kill" is not printed as this command can
     * only be executed by Zuul and thus should not be shown to the player.
     */
    void showAll() {
        // Traverse all command strings in the HashMap of valid commands.
        for(String command : validCommands.keySet()) {
            // Check that the command is not "kill"
            if (command != "kill"){
                // Print the valid command strings
                System.out.print(command + "  ");
            }
        }
        LogicFacade.appendMessage("");
    }
}
