/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import Acquaintance.IScore;
import java.io.Serializable;

/**
 *
 * @author HCHB
 */
public class Score implements IScore, Serializable {
    
    private String name;
    private double score;
    
    public Score(String name, double score){
        this.name = name;
        this.score = score;
    }
    
    @Override
    public String getName(){
        return this.name;
    }
    
    @Override
    public double getScore(){
        return this.score;
    }
}
