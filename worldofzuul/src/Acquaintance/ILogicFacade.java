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
public interface ILogicFacade {
    
    void injectData(IDataFacade data);
    
    void initializeGame(int numberOfZuulAtStart);
    
    void processCommand(String command);
}
