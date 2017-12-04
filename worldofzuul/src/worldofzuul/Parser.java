package worldofzuul;

// Import:
import java.io.Serializable;

/**
 * This class was part of the source code framework.
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

/**
 * This class is used to get a command. The class implements the interface
 * Serializable. The class has default visibility, as it is only used from
 * within this package.
 */
class Parser implements Serializable {
    
    /**
     * Data field.
     * commands: a CommandWords object that represents the valid commands.
     */
    private CommandWords commands;

    /**
     * This constructor creates an instance of the Parser class by initializing
     * the commands data field.
     */
    Parser() {
        // Initialise commands.
        commands = new CommandWords();
    }

    /**
     * This method returns a command. The method converts a String GUI command
     * to a command compatible with the logic layer.
     * 
     * @param currentCharacter the current character whose command is to be
     * specified.
     * @param GUICommand a command sent from the graphical user interface.
     * 
     * @return an instance of Command based on the current character and the
     * string command from the GUI.
     */
    Command getCommand(Character currentCharacter, String GUICommand) {
        /*
        The method calls the getCommand() method on the current character. This
        method uses the valid commands in "commands" and the String GUICommand
        to create a Command object which is returned and initializes
        characterCommand.
        */
        Command characterCommand = currentCharacter.getCommand(commands, GUICommand);
        // Return the command.
        return characterCommand;
    }
}