package worldofzuul;

import java.io.Serializable;

/** 
 * This class was originally part of the source framework.
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

/**
 * This class represents a command in the game. It implements the interface
 * Serializable to allow the game to be saved.
 */

public class Command implements Serializable {
    /**
     * Data fields.
     * commandWord: the command's command word
     * secondWord: the second word of the command
     * thirdWord: the third word of the command
     */
    private CommandWord commandWord;
    private String secondWord;
    private String thirdWord;

    /**
     * This constructor creates a Command object with the specified command
     * word, second word, and third word.
     * 
     * @param commandWord
     * @param secondWord
     * @param thirdWord 
     */
    Command(CommandWord commandWord, String secondWord, String thirdWord) {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
    }

    /**
     * This method returns the command word of the command.
     * 
     * @return commandWord
     */
    CommandWord getCommandWord() {
        return commandWord;
    }

    /**
     * This method returns the second word of the command.
     * 
     * @return secondWord
     */
    String getSecondWord() {
        return secondWord;
    }
    
    /**
     * This method returns the third word of the command.
     * 
     * @return thirdWord
     */
    String getThirdWord() {
        return thirdWord;
    }
    
    /**
     * This method checks if the command is unknown.
     * 
     * @return true if the command is unknown; else it returns false.
     */
    boolean isUnknown() {
        return (commandWord == CommandWord.UNKNOWN);
    }

    /**
     * This method checks if the command has a second word.
     * 
     * @return true if the command has a second word; else it returns false.
     */
    public boolean hasSecondWord() {
        return (secondWord != null);
    }
    
    /**
     * This method checks if the command has a third word.
     * 
     * @return true if the command has a third word; else it returns false.
     */
    public boolean hasThirdWord() {
        return (thirdWord != null);
    }
}

