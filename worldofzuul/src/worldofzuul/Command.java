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

    // This constructor creates a Command with the specified command word and second word
    public Command(CommandWord commandWord, String secondWord)
    {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
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
}

