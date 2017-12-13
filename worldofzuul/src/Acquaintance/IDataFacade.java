/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// The interface is located in the Acquaintance package.
package Acquaintance;

import java.io.FileNotFoundException;

/**
 * This interface defines the behavior of the data facade. The methods are
 * implemented in the DataFacade class in the data layer. All methods are
 * public abstract instance methods.
 * 
 * @author HCHB
 */
public interface IDataFacade {
    
    /**
     * This method is used to load the game.
     * 
     * @return instance of IGame that represents the loaded game.
     * @throws java.io.FileNotFoundException
     */
    public abstract IGame loadGame() throws FileNotFoundException;
 
    /**
     * This method is used to save the game.
     * 
     * @param game instance of IGame that represents the game to be saved.
     */
    public abstract void saveGame(IGame game);
    
    /**
     * This method is used to save a high score. 
     * 
     * @param highscore instance of IHighscore that represents the high score
     * to be saved.
     */
    public abstract void saveHighscore(IHighscore highscore);
    
    /**
     * This method loads the high score.
     * 
     * @return instance of IHighscore that represents the high score to be
     * loaded.
     */
    public abstract IHighscore loadHighscore();     
}
