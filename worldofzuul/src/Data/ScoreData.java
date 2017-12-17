/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// The class is located in the data layer.
package Data;

// Import:
import Acquaintance.IScore;

/**
 * This class represents a score in the data layer. The class implements the
 * IScore interface.
 * 
 * @author HCHB
 */

class ScoreData implements IScore {
    
    /**
     * name: String name associated with score.
     */
    private String name;
    /**
     * score: double value that represents the points obtained by the player.
     */
    private double score;
    
    /**
     * This constructor creates a ScoreData object with the specified name and
     * score.
     * 
     * @param name name associated with score.
     * @param score score associated with score.
     */
    ScoreData(String name, double score){
        this.name = name;
        this.score = score;
    }
    
    /**
     * This method returns the name associated with the score. The method
     * implements the getName() method in the IScore interface.
     * 
     * @return name
     */
    @Override
    public String getName(){
        return this.name;
    }
    
    /**
     * This method returns the score associated with the score. The method
     * implements the getScore() method in the IScore interface.
     * @return 
     */
    @Override
    public double getScore(){
        return this.score;
    }
}
