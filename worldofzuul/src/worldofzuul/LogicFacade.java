/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;
import Acquaintance.*;
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
    
}
