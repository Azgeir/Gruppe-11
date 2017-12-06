/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
        String name){
        logic.initializeGame(numberOfZuulAtStart, spawnTime, name);
    }

    /**
     * This method is used to send a command from the GUI to the logic layer.
     * The method is called when the user presses a button.
     * 
     * @param command String that represents the command to be sent to the logic
     * layer
     */
    static void sendCommand(String command){
        logic.processCommand(command);
    }

    /**
     * This method returns the exits from the character's current room as a Set
     * of Strings. The method is used when updating the GUI's drop down menus.
     * 
     * @return Set of Strings representing the exits from the character's
     * current room.
     */
    static Set<String> getExits(){
        Set<String> exits = logic.getExits();
        return exits;
    }

    static Set<String> getRoomItemSet(){
        Set<String> itemSet = logic.getRoomItemSet();
        return itemSet;
    }

    static boolean isRoomLookedBefore(){
        boolean lookedBefore = logic.isRoomLookedBefore();
        return lookedBefore;
    }

    static boolean isGameFinished(){
        boolean finished = logic.isGameFinished();
        return finished;
    }

    static Set<String> getInventorySet(){
        Set<String> inventorySet = logic.getInventorySet();
        return inventorySet;
    }
    
    static void saveGame(){
        logic.saveGame();
    }
    static void loadGame(){
        logic.loadGame();
    }
    static IHighscore loadHighscore(){
        IHighscore highscore = logic.loadHighscore();
        return highscore;
    }

    static String readAndDeleteGameMessage(){
        String gameMessage = logic.readAndDeleteMessage();
        return gameMessage;
    }

    static int getNumberOfItems(String inventory, String item){
        return logic.getNumberOfItems(inventory, item);
    }

    static boolean isRoomKnown(IRoom room){
        boolean isKnown = logic.isRoomKnown(room);
        return isKnown;
    }

    static boolean isTalking(){
        boolean talking = logic.isTalking();
        return talking;
    }

    static Collection<String> charactersInRoom(IRoom room){
        return logic.charactersInRoom(room);
    }

    static String getCurrentRoomName(){
        String roomName = logic.getCurrentRoomName();
        return roomName;
    }
}
