package worldofzuul;

// Import:
import java.util.HashMap;

/**
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

// This class represents the command words of the game.
public class CommandWords
{
    // Data field:
    private HashMap<String, CommandWord> validCommands; // HashMap of valid commands

    // This constructor creates an instance of CommandWords
    CommandWords()
    {
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

    // This method returns the CommandWord associated with the command string
    CommandWord getCommandWord(String commandWord)
    {
        // Assign the CommandWord associated with the commandWord string to the variable command.
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
    
    // This method checks if a specified string is associated with a valid command in the HashMap.
    boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }

    // This method prints the valid commands.
    void showAll() 
    {
        // Traverse all command strings in the HashMap of valid commands.
        for(String command : validCommands.keySet()) {
            if (command != "kill"){
            // Print the valid command strings
            System.out.print(command + "  ");
            }
        }
        System.out.println();
    }
}
