package worldofzuul;

/**
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public enum CommandWord
{
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"), PICKUP("pickup"), 
    STAY("stay"), DROP("drop"), LOOKAROUND("look around");
    
    private String commandString;
    
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    public String toString()
    {
        return commandString;
    }
}
