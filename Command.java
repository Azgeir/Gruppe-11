package worldofzuul;

/** 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
<<<<<<< HEAD

//This is a test
=======
>>>>>>> 0b2ecb10011faa18b52f22cc4ded9fe35ec68fe9
public class Command
{
    private CommandWord commandWord;
    private String secondWord;

    public Command(CommandWord commandWord, String secondWord)
    {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
    }

    public CommandWord getCommandWord()
    {
        return commandWord;
    }

    public String getSecondWord()
    {
        return secondWord;
    }

    public boolean isUnknown()
    {
        return (commandWord == CommandWord.UNKNOWN);
    }

    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
}

