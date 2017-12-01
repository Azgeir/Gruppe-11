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
public interface IGUIFacade {
    
    void injectLogic(ILogicFacade logic);
    
    void startApplication(String[] args);
    
}
