/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// The class is located in the logic layer.
package Logic;

// Imports:
import Acquaintance.*;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents the facade to and from the logic layer of the game.
 * The class implements the interface ILogicFacade, which defines the behavior
 * of this class. The class therefore implements the method from ILogicFacade.
 * 
 * @author Simon
 */

public class LogicFacade implements ILogicFacade {

    /**
     * Data fields.
     * data: an instance of IDataFacade, which represents the data layer
     * game: an instance of Game, which represents central game logic.
     */
    private static IDataFacade data;
    private static Game game;

    /**
     * This method sets the "data" data field.
     * 
     * @param data value of "data" data field.
     */
    @Override
    public void injectData(IDataFacade data) {
        LogicFacade.data = data;
    }

    /**
     * This method initializes the game with the specified number of Zuuls,
     * spawn time, and name. The method calls the constructor in Game with the
     * given arguments.
     * 
     * @param numberOfZuulAtStart number of Zuuls in the game at the start of
     * the game.
     * @param spawnTime determines the time before a new Zuul is spawned.
     * @param name name of game; used to create high scores.
     */
    @Override
    public void initializeGame(int numberOfZuulAtStart, double spawnTime,
        String name) {
        LogicFacade.game = new Game(numberOfZuulAtStart, spawnTime, name);
    }

    /**
     * This method is used to process a command. It calls the play() method in
     * Game with the String command as an argument.
     * 
     * @param command String command from GUI.
     */
    @Override
    public void processCommand(String command) {
        LogicFacade.game.play(command);
    }

    /**
     * This method returns a set of Strings that specify the exits from the
     * current room of the current character.
     * 
     * @return a Set of Strings that represents the exits from the room.
     */
    @Override
    public Set<String> getExits() {
        Set<String> exits = LogicFacade.game.getCurrentCharacter()
            .getCurrentRoom().getExits().keySet();
        return exits;
    }

    /**
     * This method returns a Set of Strings that specify the items in the
     * current room of the current character.
     * 
     * @return a Set of Strings that represents the items of the current room.
     */
    @Override
    public Set<String> getRoomItemSet() {
        Set<String> itemSet = LogicFacade.game.getCurrentCharacter()
            .getCurrentRoom().getInventory().listItems();
        return itemSet;
    }

    /**
     * This method is used to check if the current character has looked at the
     * current room before.
     * 
     * @return true if the character has looked at the room; false if the
     * character has not looked at the room before.
     */
    @Override
    public boolean isRoomLookedBefore() {
        boolean lookedBefore = LogicFacade.game.getCurrentCharacter()
            .getCurrentRoom().hasBeenLookedUpon();
        return lookedBefore;
    }

    /**
     * This method checks if the game is finished.
     * 
     * @return true if the game is finished; else it returns false.
     */
    @Override
    public boolean isGameFinished() {
        boolean finished = LogicFacade.game.isFinished();
        return finished;
    }

    /**
     * This method returns a Set of Strings that
     * @return 
     */
    @Override
    public Set<String> getInventorySet() {
        // Declare Set.
        Set<String> inventorySet;
        //  Get the current character
        Character currentCharacter = game.getCurrentCharacter();
        /*
        If current character is Hero, set inventorySet to be the list of items
        in the character's inventory.
        */
        if (currentCharacter instanceof Hero) {
            inventorySet = ((Hero)game.getCurrentCharacter()).getInventory()
                .listItems();
        }
        else {
            /*
            If the current character is not Hero, add "derp" to the inventory
            set. The type of Set doesn't matter because it is only supposed to
            hold a single value to not cause a null pointer exception
            */
            inventorySet = new HashSet<>();
            inventorySet.add("derp");
        }
        return inventorySet;
    }

    /**
     * This method is used to load the game. The method calls the loadGame()
     * method on data.
     */
    @Override
    public boolean loadGame() {
        boolean success;
        try {
            LogicFacade.game = (Game)data.loadGame();
            success = true;
        }
        catch (FileNotFoundException ex){
            success = false;
        }
        return success;
    }

    /**
     * This method is used to load the high score. It calls the loadHighScore()
     * method on data.
     * 
     * @return highscore
     */
    @Override
    public IHighscore loadHighscore() {
        IHighscore highscore = LogicFacade.data.loadHighscore();
        return highscore;
    }

    /**
     * This method is used to save the game. It calls the saveGame() method on
     * data.
     */
    @Override
    public void saveGame() {
        LogicFacade.data.saveGame(LogicFacade.game);
    }

    /**
     * This method is used to save the high score. It calls the saveHighscore()
     * method on data.
     * 
     * @param highscore instance of IHighscore.
     */
    static void saveHighscore(IHighscore highscore) {
        LogicFacade.data.saveHighscore(highscore);
    }

    /**
     * This method returns the high score as an instance of IHighscore. It calls
     * the loadHighscore() method on data.
     * 
     * @return highscore
     */
    static IHighscore getHighscore() {
        IHighscore highscore = LogicFacade.data.loadHighscore();
        return highscore;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String readAndDeleteMessage() {
        String message = game.readAndDeleteMessage();
        return message;
    }

//    /**
//     * This method is used to append a message. It calls the appendMessage()
//     * method on game.
//     * 
//     * @param appendMessage a String to be appended.
//     */
//    static void appendMessage(String appendMessage) {
//        LogicFacade.game.appendMessage(appendMessage);
//    }

    /**
     * This method is used to get the number of items in the specified
     * inventory (room or character).
     * 
     * @param inventory a String that specifies whether it is the room or
     * character inventory.
     * @param item a String that specifies the item to be found.
     * 
     * @return an integer that specifies the number of the specified item in
     * the specified inventory; returns -1 if the inventory string is different
     * from Room and Character, or if the current character is not Hero.
     */
    @Override
    public int getNumberOfItems(String inventory, String item) {
        //  Get the current character
        Character currentCharacter = game.getCurrentCharacter();
        // Check if current character is Hero.
        if (currentCharacter instanceof Hero){
            // If inventory string is Room, search inventory of current room.
            if (inventory.equals("Room")) {
                return currentCharacter.getCurrentRoom().getInventory()
                        .getNumberOfItems(item);
            }
            /*
            If inventory string is Character, search inventory of current
            character.
            */
            if (inventory.equals("Character")) {
                return ((Hero)currentCharacter).getInventory()
                        .getNumberOfItems(item);
            }
            return -1;
        }
        else {
            return -1;
        }
    }
    
    /**
     * This method is used to check if the character knows a room. This is used
     * when peeking.
     * 
     * @param room the room which is to be checked (instance of IRoom).
     * 
     * @return true if the room is known; else it returns false.
     */
    @Override
    public boolean isRoomKnown(IRoom room){
        Character character = LogicFacade.game.getCurrentCharacter();
        // Declare and initialize isKnown. It is set to false as default.
        boolean isKnown = false;
        // Check if current character is Hero.
        if (character instanceof Hero) {
            if (((Hero)character).getKnownRooms().contains(room.getName())) {
                isKnown = true;
            }
        }
        return isKnown;
    }
    
    @Override
    public boolean isTalking(){
        boolean talking = game.isTalking();
        return talking;
    }
    
    @Override
    public Collection<String> charactersInRoom(IRoom room){
        return game.charactersInRoom(room);
    }
    
    @Override
    public String getCurrentRoomName(){
        String roomName = game.getCurrentRoomName();
        return roomName;
    }
}
