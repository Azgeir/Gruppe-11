/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worldofzuul;

import Acquaintance.IHighscore;
import Acquaintance.IScore;
import java.io.Serializable;

/**
 * This class represents a high score in the game. The class implements the
 * interfaces IHighscore and Serializable. The class has default visibility as
 * it is only used in this package.
 * 
 * @author HCHB
 */

class Highscore implements IHighscore, Serializable {

    /**
     * Data field.
     * highscore: an array of IScore objects that represents the high score.
     */
    private IScore[] highscore;
    
    /**
     * This constructor creates a new Highscore object with a default length of
     * 10. This no-arg constructor is not called.
     */
    Highscore() {
        this.highscore = new IScore[10];
    }
    
    /**
     * This constructor creates a Highscore object with the specified highscore.
     * 
     * @param highscore array of IScore objects that initialize the highscore
     * data field.
     */
    Highscore(IScore[] highscore) {
        this.highscore = highscore;
    }
    
    /**
     * This method updates the high score with a new score.
     * 
     * @param newScore the new score used to update the high score.
     */
    void updateHighscore(IScore newScore){
        // Traverse the high score.
        for (int i = 0; i < highscore.length; i++) {
            // Check if the score is null.
            if (this.highscore[i] != null) {
                // Check if the new score is greater than the current score.
                if (this.highscore[i].getScore() < newScore.getScore()) {
                    // Insert new score into the high score array.
                    IScore temp = this.highscore[i];
                    this.highscore[i] = newScore;
                    newScore = temp;
                }
            }
            // If the score is null, insert new score at this position.
            else {
                this.highscore[i] = newScore;
                break;
            }            
        }
    }
    
    /**
     * This method returns an array of IScore objects. The method implements the
     * getScores() method from the IHighscore interface.
     * 
     * @return the data field highscore.
     */
    @Override
    public IScore[] getScores(){
        return this.highscore;
    }  
}
