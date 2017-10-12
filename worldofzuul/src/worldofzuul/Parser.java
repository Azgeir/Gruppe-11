package worldofzuul;

// Imports:
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

// This class represents the Parser of the game.
public class Parser 
{
    // Data fields:
    private CommandWords commands; // The valid commands (contained in a HashMap)
    private Scanner reader; // A Scanner

    // This constructor creates an instance of the Parser class
    public Parser() 
    {
        // Create data fields:
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    // This method returns a command
    public Command getCommand() 
    {
        // Declare a String variable for the input
        String inputLine;
        
        // Set words 1 and 2 to null
        String word1 = null;
        String word2 = null;

        // Print "> " to prompt user input
        System.out.print("> ");

        // Use Scanner to read input line from user
        inputLine = reader.nextLine();

        // Create a Scanner called tokenizer based on inputLine
        Scanner tokenizer = new Scanner(inputLine);
        // If the input line has a first word, assign it to word1
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();
            // If the input line has a second word, assign it to word2
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();
            }
        }

        // Create a Command object based on words 1 and 2, and return the command.
        return new Command(commands.getCommandWord(word1), word2);
    }

    // This method prints the available commands.
    public void showCommands()
    {
        commands.showAll();
    }
}
