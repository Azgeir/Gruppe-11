/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Acquaintance;

/**
 *
 * @author HCHB
 */
public interface IDataFacade {
    
    void loadGame();
 
    void saveGame(IGame game);
    
    void saveHighscore();
    
    
    void loadHighscore();
      
}
