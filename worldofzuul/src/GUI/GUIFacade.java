/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// This class is located in the GUI layer.
package GUI;

// Imports:
import Acquaintance.*;
import java.util.Collection;
import java.util.Set;

/**
 * This class represents the GUI facade. The class implements the IGUIFacade
 * interface.
 * 
 * @author Simon
 */

public class GUIFacade implements IGUIFacade {
    
    /**
     * Data field.
     * logic: instance of ILogicFacade that represents the logic layer.
     */
    private static ILogicFacade logic;
    
    /**
     * This method injects the logic layer to the GUI facade. This is done by
     * setting the value of the logic data field. The method implements the
     * injectLogic() method in the IGUIFacade.
     * 
     * @param logic instance of ILogicFacade
     */
    @Override
    public void injectLogic(ILogicFacade logic) {
        GUIFacade.logic = logic; 
    } 
   
    /**
     * This method is used to start the application. The method implements the
     * startApplication() method in the interface IGUIFacade. The method is
     * called from the main method in the Start class.
     * 
     * @param args String array sent from the main method
     */
    @Override
    public void startApplication(String[] args) {
        GUI.main(args);
    }

    /**
     * This method is used to initialize the game. The game is called when the
     * user presses the start button.
     * 
     * @param numberOfZuulAtStart the number of Zuuls present at the beginning
     * of the game
     * @param spawnTime the time before a new Zuul is spawned
     * @param name name of game
     */
    static void initializeGame(int numberOfZuulAtStart, double spawnTime,
        String name) {
        logic.initializeGame(numberOfZuulAtStart, spawnTime, name);
    }

    /**
     * This method is used to send a command from the GUI to the logic layer.
     * The method is called when the user presses a button.
     * 
     * @param command String that represents the command to be sent to the logic
     * layer
     */
    static void sendCommand(String command) {
        logic.processCommand(command);
    }

    /**
     * This method returns the exits from the character's current room as a Set
     * of Strings. The method is used when updating the GUI's drop down menus.
     * 
     * @return Set of Strings representing the exits from the character's
     * current room.
     */
    static Set<String> getExits() {
        Set<String> exits = logic.getExits();
        return exits;
    }

    /**
     * This method is used to get the inventory of the current room of the
     * character. The method is used to update the corresponding drop down
     * menu in the GUI.
     * 
     * @return Set of Strings that represents the room's inventory.
     */
    static Set<String> getRoomItemSet() {
        Set<String> itemSet = logic.getRoomItemSet();
        return itemSet;
    }

    /**
     * This method checks whether the character has looked at the current room.
     * The method is used to determine whether that room's inventory is
     * accessible to the character.
     * 
     * @return true if the character has looked at the room before; else it
     * returns false.
     */
    static boolean isRoomLookedBefore() {
        boolean lookedBefore = logic.isRoomLookedBefore();
        return lookedBefore;
    }

    /**
     * This method checks if the game is finished. If the game is finished, the
     * GUI controller consequently disables the command buttons and asks the
     * player if they want to play a new game.
     * 
     * @return true if game is finished; else it returns false
     */
    static boolean isGameFinished() {
        boolean finished = logic.isGameFinished();
        return finished;
    }

    /**
     * This method returns the inventory of the character. The method is used
     * when updating the corresponding drop down menu in the GUI.
     * 
     * @return Set of Strings that represents the character's inventory.
     */
    static Set<String> getInventorySet() {
        Set<String> inventorySet = logic.getInventorySet();
        return inventorySet;
    }
    
    /**
     * This method is used to save the game.
     */
    static void saveGame() {
        logic.saveGame();
    }
    
    /**
     * This method is used to load the game.
     */
    static boolean loadGame() {
        boolean success = logic.loadGame();
        return success;
    }
    
    /**
     * This method is used to load the high score. The method is used when
     * displaying the high score on the start screen.
     * 
     * @return instance of IHighscore that represents the high score.
     */
    static IHighscore loadHighscore() {
        IHighscore highscore = logic.loadHighscore();
        return highscore;
    }

    /**
     * This method is used to read and delete the message in the Game class. The
     * method is used when updating the message label in the GUI.
     * 
     * @return String representation of the message from the Game class.
     */
    static String readAndDeleteGameMessage() {
        String gameMessage = logic.readAndDeleteMessage();
        return gameMessage;
    }

    /**
     * This method is used to get the number of a specific item in the specified
     * inventory. The method is used when updating the corresponding drop down
     * menu in the GUI.
     * 
     * @param inventory String that indicates type of inventory (room or
     * character)
     * @param item String that indivates the item whose number is to be found
     * 
     * @return integer value that represents the number of the specified item
     * in the specified inventory
     */
    static int getNumberOfItems(String inventory, String item) {
        return logic.getNumberOfItems(inventory, item);
    }

    /**
     * This method is used to check if the specified room is "known". The method
     * is used when deciding whether a given room's is to be clearly shown in
     * the GUI.
     * 
     * @param room instance of IRoom which represents the room to be
     * investigated
     * 
     * @return true if room is known; else it returns false.
     */
    static boolean isRoomKnown(IRoom room) {
        boolean isKnown = logic.isRoomKnown(room);
        return isKnown;
    }

    /**
     * This method is used to check if the character is talking to tech dude.
     * 
     * @return true if the character is talking to tech dude; else it returns
     * false
     */
    static boolean isTalking() {
        boolean talking = logic.isTalking();
        return talking;
    }

    /**
     * This method is used to obtain information regarding what characters are
     * in the specified room. The method is used when deciding what character
     * images to show in the GUI.
     * 
     * @param room room whose characters are to be found
     * 
     * @return Collection of Strings that represents the characters in the
     * specified room.
     */
    static Collection<String> charactersInRoom(IRoom room) {
        return logic.charactersInRoom(room);
    }

    /**
     * This method returns the name of the current room of the character.
     * 
     * @return String that indicates the current room of the character.
     */
    static String getCurrentRoomName() {
        String roomName = logic.getCurrentRoomName();
        return roomName;
    }
}
