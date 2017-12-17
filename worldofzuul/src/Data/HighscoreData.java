/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// The class is located in the data layer.
package Data;

// Imports:
import Acquaintance.IHighscore;
import Acquaintance.IScore;

/**
 * This class represents the high score in the data layer. The class has default
 * visibility (only visible within this package) but it has a public method
 * defined in the IHighscore interface.
 * 
 * @author HCHB
 */

class HighscoreData implements IHighscore {
    
    /**
     * highscore: array of IScore objects which represents the high score.
     */
    private IScore[] highscore;
    
    /**
     * This constructor creates a HighscoreData object with highscore of default
     * length 10.
     */
    HighscoreData() {
        this.highscore = new IScore[10];
    }
    
    /**
     * This constructor creates a HighscoreData object with the specified
     * high score.
     * 
     * @param highscore the high score used to initialize highscore.
     */
    HighscoreData(IScore[] highscore){
        this.highscore = highscore;
    }
    
    /**
     * This method returns the scores in the high score. This method is public
     * as it is defined in the IHighscore interface.
     * 
     * @return an array of IScore objects.
     */
    @Override
    public IScore[] getScores(){
        return this.highscore;
    }
}
