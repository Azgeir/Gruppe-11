package worldofzuul;

/** 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

// This class represents a command in the game.
public class Command
{
    // Data fields:
    private CommandWord commandWord; // The command's command word
    private String secondWord; // The second word of the command
    private String thirdWord; // The third word of the command

    // This constructor creates a Command with the specified command word and second word
    public Command(CommandWord commandWord, String secondWord, String thirdWord)
    {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
    }

    // This method returns the command word of the command.
    public CommandWord getCommandWord()
    {
        return commandWord;
    }

    // This method returns the second word of the command.
    public String getSecondWord()
    {
        return secondWord;
    }
    
    // This method returns the third word of the command
    public String getThirdWord()
    {
        return thirdWord;
    }
    
    // This method returns true if the command word is unknown.
    public boolean isUnknown()
    {
        return (commandWord == CommandWord.UNKNOWN);
    }

    // This method returns true if the command has a second word.
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
    
    // This method returns true if the command has a third word
    public boolean hasThirdWord()
    {
        return (thirdWord != null);
    }
}

