/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;
import Acquaintance.*;
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author Simon
 */
public class LogicFacade implements ILogicFacade{
    
    private static IDataFacade data;
    private Game game;
    
    @Override
    public void injectData(IDataFacade data) {
        this.data = data;
    }
    
    @Override
    public void initializeGame(int numberOfZuulAtStart, double spawnTime, String name){
        game = new Game(numberOfZuulAtStart, spawnTime ,name);
    }
    
    @Override
    public void processCommand(String command){
        game.play(command);
    }
    
    @Override
    public Set<String> getExits(){
        Set<String> exits = game.getCurrentCharacter().getCurrentRoom().getExits().keySet();
        return exits;
    }
    
    @Override
    public Set<String> getRoomItemSet(){
        Set<String> itemSet = game.getCurrentCharacter().getCurrentRoom().getInventory().listItems();
        return itemSet;
    }
    
    @Override
    public boolean isRoomLookedBefore(){
        boolean lookedBefore = game.getCurrentCharacter().getCurrentRoom().isHasBeenLookedUpon();
        return lookedBefore;
    }
    
    @Override
    public boolean isGameFinished(){
        boolean finished = game.isFinished();
        return finished;
    }
    
    @Override
    public Set<String> getInventorySet(){
        Set<String> inventorySet;
        if (game.getCurrentCharacter().getName().equals("Hero")) {
            Hero heroTemp = (Hero)game.getCurrentCharacter();
            inventorySet = heroTemp.getInventory().listItems();
        }
        else {
            //The type of Set doesn't matter because it is only supposed to hold a single value to not cause a null pointer exception
            inventorySet = new HashSet<String>();
            inventorySet.add("derp"); 
        }
        
        return inventorySet;
    }
    
    @Override
    public void loadGame(){
        game = (Game)data.loadGame();
    }
    
    @Override
    public IHighscore loadHighscore(){
        IHighscore highscore = data.loadHighscore();
        return highscore;
    }
    
    @Override
    public void saveGame(){
        data.saveGame(this.game);
    }
    
    static void saveHighscore(IHighscore highscore){
        data.saveHighscore(highscore);
    }
    
    static IHighscore getHighscore(){
        IHighscore highscore = data.loadHighscore();
        return highscore;
    }
}
