/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// The interface is located in the Acquaintance package.
package Acquaintance;

// Imports:
import java.util.Collection;
import java.util.Set;

/**
 * This interface defines the behavior for the logic facade. The methods in
 * the interface are implemented in the LogicFacade class in the logic layer.
 * 
 * @author HCHB
 */

public interface ILogicFacade {

    /**
     * This method injects the data layer to the logic layer.
     * 
     * @param data instance of IDataFacade that represents the data layer.
     */
    public abstract void injectData(IDataFacade data);

    /**
     * This method initializes the game.
     * 
     * @param numberOfZuulAtStart number of Zuuls at the beginning of the game.
     * @param spawnTime time before a new Zuul spawns.
     * @param name name of the game.
     */
    public abstract void initializeGame(int numberOfZuulAtStart, double spawnTime, String name);

    /**
     * This method is used to process a command.
     * 
     * @param command String that represents the command to be processed.
     */
    public abstract void processCommand(String command);

    /**
     * This method returns the exits from the current room of the character.
     * 
     * @return Set of Strings that represents the exits from the character's
     * current room.
     */
    public abstract Set<String> getExits();

    /**
     * This method returns the inventory of the current room of the character.
     * 
     * @return Set of Strings that represent the inventory of the current room
     * of the character.
     */
    public abstract Set<String> getRoomItemSet();

    /**
     * This method checks if the room has been looked at before. This is used
     * when updating the drop down menu for the room's inventory .
     * 
     * @return true if the character has looked at the room before; else it
     * returns false.
     */
    public abstract boolean isRoomLookedBefore();

    /**
     * This method checks if the game is finished.
     * 
     * @return true if the game is finished; else it returns false.
     */
    public abstract boolean isGameFinished();

    /**
     * This method returns a Set of Strings that represents the inventory of
     * the player.
     * 
     * @return Set of Strings representing the player's inventory.
     */
    public abstract Set<String> getInventorySet();

    /**
     * This method is used to load the game.
     * @return true if successful; else it returns false.
     */
    public abstract boolean loadGame();

    /**
     * This method is used to load a high score.
     * 
     * @return instance of IHighscore that represents the loaded high score.
     */
    public abstract IHighscore loadHighscore();

    /**
     * This method is used to save the game.
     */
    public abstract void saveGame(); 
    
    /**
     * This method is used to read and delete the game's "message". This is used
     * after the player has pressed a button to update the message printed in
     * the GUI.
     * 
     * @return a String that represents the message.
     */
    public abstract String readAndDeleteMessage();

    /**
     * This method checks whether a room is known.
     * 
     * @param room the room to be checked
     * 
     * @return true if the game is known; else it returns false.
     */
    public abstract boolean isRoomKnown(IRoom room);
    
    /**
     * This method returns the number of a specific item in the specified
     * inventory. This is used when updating the drop down menus in the GUI.
     * 
     * @param inventory String that specifies the kind of inventory to be
     * searched (room or character)
     * @param item String that represents the item to be searched for in the
     * inventory.
     * 
     * @return integer value that represents the number of the specified item
     * in the specified inventory.
     */
    public abstract int getNumberOfItems(String inventory, String item);
    
    /**
     * This method checks if the character is talking to the tech dude.
     * 
     * @return true if the character is talking to the tech dude; else it
     * returns false.
     */
    public abstract boolean isTalking();
    
    /**
     * This method is used to check which characters that are in the specified
     * room.
     * 
     * @param room the room whose characters are to be determined.
     * 
     * @return Collection of Strings that specifies the characters in the
     * specified room.
     */
    public abstract Collection<String> charactersInRoom(IRoom room);
    
    /**
     * This method returns the name of the current room of the character.
     * 
     * @return String which indicates the current room of the character.
     */
    public abstract String getCurrentRoomName();
}
