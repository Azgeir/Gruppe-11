/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Acquaintance.IHighscore;
import Acquaintance.IScore;

/**
 *
 * @author HCHB
 */
public class HighscoreData implements IHighscore {
    
    private IScore[] highscore;
    
    public HighscoreData(){
        highscore = new IScore[10];
    }
    
    public HighscoreData(IScore[] highscore){
        this.highscore = highscore;
    }
    
    @Override
    public IScore[] getScores(){
        return this.highscore;
    }
    
}
