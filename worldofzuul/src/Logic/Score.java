/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic;

import Acquaintance.IScore;
import java.io.Serializable;

/**
 * This class represents a score in the high score. The class implements the
 * interfaces IScore and Serializable.
 * 
 * @author HCHB
 */
public class Score implements IScore, Serializable {
    
    /**
     * name: a String that indicates the name of the player whose score is
     * represented by the object.
     */
    private String name;
    /**
     * score: a double value that represents the total points obtained by the
     * player.
     */
    private double score;
    
    /**
     * This constructor creates a Score object with the specified name and
     * score. The constructor is called when the game has been won by the
     * player. The constructor has default visibility because it will only
     * be called from within its package.
     * 
     * @param name name of the player
     * @param score score obtained by the player
     */
    Score(String name, double score){
        this.name = name;
        this.score = score;
    }
    
    /**
     * This method returns the name associated with the score. The method
     * implements the getName() method from the IScore interface.
     * 
     * @return name associated with score.
     */
    @Override
    public String getName(){
        return this.name;
    }
    
    /**
     * This method returns the score. The method implements the getScore()
     * method from the IScore interface.
     * 
     * @return score
     */
    @Override
    public double getScore(){
        return this.score;
    }
}
