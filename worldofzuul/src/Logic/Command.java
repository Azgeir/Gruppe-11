// The class is located in the logic layer.
package Logic;

// Import:
import java.io.Serializable;

/** 
 * This class was originally part of the source framework.
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

/**
 * This class represents a command in the game. It implements the interface
 * Serializable.
 */

class Command implements Serializable {
    /**
     * commandWord: the command's command word
     * 
     * 
     */
    private CommandWord commandWord;
    /**
     * secondWord: the second word of the command
     */
    private String secondWord;
    /**
     * thirdWord: the third word of the command
     */
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
        return this.commandWord;
    }

    /**
     * This method returns the second word of the command.
     * 
     * @return secondWord
     */
    String getSecondWord() {
        return this.secondWord;
    }
    
    /**
     * This method returns the third word of the command.
     * 
     * @return thirdWord
     */
    String getThirdWord() {
        return this.thirdWord;
    }
    
    /**
     * This method checks if the command is unknown.
     * 
     * @return true if the command is unknown; else it returns false.
     */
    boolean isUnknown() {
        return (this.commandWord == CommandWord.UNKNOWN);
    }

    /**
     * This method checks if the command has a second word.
     * 
     * @return true if the command has a second word; else it returns false.
     */
    boolean hasSecondWord() {
        return (this.secondWord != null);
    }
    
    /**
     * This method checks if the command has a third word.
     * 
     * @return true if the command has a third word; else it returns false.
     */
    boolean hasThirdWord() {
        return (this.thirdWord != null);
    }
}

