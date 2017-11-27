/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Acquaintance.IDataFacade;
import Acquaintance.IGame;
import Acquaintance.IHighscore;

/**
 *
 * @author HCHB
 */
public class DataFacade implements IDataFacade {
    
    private Data data;
    
    public DataFacade(){
        data = new Data();
    }
    
    @Override
    public IGame loadGame(){
        IGame game = data.loadGame();
        return game;
    }
    
    @Override
    public IHighscore loadHighscore(){
        IHighscore highscore = data.loadHighscore();
        return highscore;
    }
    
    @Override
    public void saveGame(IGame game){
        data.saveGame(game);
    }
    @Override
    public void saveHighscore(IHighscore highscore){
        data.saveHighscore(highscore);
    }

    
}
