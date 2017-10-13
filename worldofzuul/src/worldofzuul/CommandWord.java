package worldofzuul;

/**
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

// This enum class defines the available command words.
public enum CommandWord
{
    // Available commands:
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"), PICKUP("pickup"),
    DROP("drop"), STAY("stay"), LOOKAROUND("lookaround"), PEEK("peek"),
    USE("use"), LOCK("lock"), UNLOCK("unlock");
    
    // Command string of the command.
    private String commandString;
    
    // This constructor creates a CommandWord using the specified command string.
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    // This method returns the command's commmand string.
    @Override
    public String toString()
    {
        return commandString;
    }
}
