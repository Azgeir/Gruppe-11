/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;
import Acquaintance.*;
import java.util.Set;
/**
 *
 * @author Simon
 */
public class LogicFacade implements ILogicFacade{
    
    private IDataFacade data;
    Game game;
    
    @Override
    public void injectData(IDataFacade data) {
        this.data = data;
    }
    
    @Override
    public void initializeGame(int numberOfZuulAtStart){
        game = new Game(numberOfZuulAtStart);
    }
    
    @Override
    public void processCommand(String command){
        game.play(command);
    }
    
    public Set<String> getExits(){
        Set<String> exits = game.getCurrentCharacter().getCurrentRoom().getExits().keySet();
        return exits;
    }
    
    public Set<String> getRoomItemSet(){
        Set<String> itemSet = game.getCurrentCharacter().getCurrentRoom().getInventory().listItems();
        return itemSet;
    }
    
    public boolean isRoomLookedBefore(){
        boolean lookedBefore = game.getCurrentCharacter().getCurrentRoom().isHasBeenLookedUpon();
        return lookedBefore;
    }
    
    public boolean isGameFinished(){
        boolean finished = game.isFinished();
        return finished;
    }
    
    public Set<String> getInventorySet(){
        Set<String> inventorySet;
        if (game.getCurrentCharacter().getName().equals("Hero")) {
            Hero heroTemp = (Hero)game.getCurrentCharacter();
            inventorySet = heroTemp.getInventory().listItems();
        }
        else {
            inventorySet = null; 
        }
        
        return inventorySet;
    }
    
}
