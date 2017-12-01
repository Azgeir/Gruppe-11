/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worldofzuul;

import Acquaintance.IHighscore;
import Acquaintance.IScore;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a high score in the game. The class implements the
 * interfaces IHighscore and Serializable.
 * 
 * @author HCHB
 */

public class Highscore implements IHighscore, Serializable {

    private IScore[] highscore;
    
    public Highscore(){
        highscore = new IScore[10];
    }
    
    public Highscore(IScore[] highscore){
        this.highscore = highscore;
    }
    
    void updataHighscore(IScore newScore){
        
        for (int i = 0; i < highscore.length; i++) {
            if (this.highscore[i] != null) {
                if (this.highscore[i].getScore() < newScore.getScore()) {
                    IScore temp = this.highscore[i];
                    this.highscore[i] = newScore;
                    newScore = temp;
                }
            }
            else {
                this.highscore[i] = newScore;
                break;
            }            
        }
    }
    
    @Override
    public IScore[] getScores(){
        return this.highscore;
    }
    
}
