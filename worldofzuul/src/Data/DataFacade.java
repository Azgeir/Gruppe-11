/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// The class is located in the data layer.
package Data;

// Imports:
import Acquaintance.IDataFacade;
import Acquaintance.IGame;
import Acquaintance.IHighscore;
import java.io.FileNotFoundException;

/**
 * This class represents the data facade. The class implements the interface
 * IDataFacade, which defines its behavior. The class therefore implements
 * the methods defined in IDataFacade.
 * 
 * @author HCHB
 */
public class DataFacade implements IDataFacade {
    
    /**
     * Data field.
     * data: instance of Data, which represents the data layer.
     */
    private Data data;
    
    /**
     * This constructor creates a DataFacade object by initializing the data
     * data field.
     */
    public DataFacade() {
        this.data = new Data();
    }
    
    /**
     * This method is used to load the game. It calls the loadGame() method on
     * data.
     * 
     * @return an instance of IGame.
     */
    @Override
    public IGame loadGame() throws FileNotFoundException {
        try {
            IGame game = this.data.loadGame();
            return game;
        }
        catch (FileNotFoundException ex){
            throw ex;
        }
    }
    
    /**
     * This method is used to load the high score. It calls the loadHigscore()
     * method on data.
     * 
     * @return an instance of IHighscore
     */
    @Override
    public IHighscore loadHighscore() {
        IHighscore highscore = this.data.loadHighscore();
        return highscore;
    }
    
    /**
     * This method is used to save the game. It calls the saveGame() method on
     * data.
     * 
     * @param game an instance of IGame that represents the game to be saved.
     */
    @Override
    public void saveGame(IGame game) {
        this.data.saveGame(game);
    }
    
    /**
     * This method is used to save the high score. It calls the saveHighscore()
     * method on data.
     * 
     * @param highscore an instance of IHighscore that represents the high
     * score to be saved.
     */
    @Override
    public void saveHighscore(IHighscore highscore) {
        this.data.saveHighscore(highscore);
    }
}
