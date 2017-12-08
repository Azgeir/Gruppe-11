/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// The interface is located in the Acquaintance package.
package Acquaintance;

/**
 * This interface defines common behavior for scores. The interface is
 * implemented by the Score class in the logic layer and by the ScoreData class
 * in the data layer.
 * 
 * @author HCHB
 */

public interface IScore {
    
    /**
     * This method returns the name associated with a score.
     * 
     * @return String that represents the name associated with the score.
     */
    public abstract String getName();
    
    /**
     * This method returns the score associated with the score.
     * 
     * @return double value that represents the score of the score.
     */
    public abstract double getScore();
}
