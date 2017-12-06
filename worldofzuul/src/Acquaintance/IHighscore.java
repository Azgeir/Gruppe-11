/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// The interface is located in the Acquaintance package.
package Acquaintance;

/**
 * This interface defines common behavior for high score classes in the data
 * and logic layers. The method is implemented in the Highscore class in the
 * logic layer and the HighscoreData class in the data layer.
 * 
 * @author HCHB
 */
public interface IHighscore {
    
    /**
     * This method returns an array of IScore objects.
     * 
     * @return IScore[]
     */
    public abstract IScore[] getScores();
}