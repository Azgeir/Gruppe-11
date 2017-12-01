package worldofzuul;

// Imports:
import java.io.Serializable;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

// This class represents the Parser of the game.
public class Parser implements Serializable 
{
    // Data fields:
    private CommandWords commands; // The valid commands (contained in a HashMap)
//    private Scanner reader; // A Scanner

    // This constructor creates an instance of the Parser class
    Parser() 
    {
        // Create data fields:
        commands = new CommandWords();
//        reader = new Scanner(System.in);
    }

    // This method returns a command
    Command getCommand(Character currentCharacter, String GUICommand) {
        Command characterCommand = currentCharacter.getCommand(commands, GUICommand);
        
        return characterCommand;
    }

    // This method prints the available commands.
    void showCommands()
    {
        commands.showAll();
    }
}
