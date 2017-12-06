/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// The interface is located in the Acquaintance package.
package Acquaintance;

/**
 * This interface defines the behavior of the GUI facade. The methods are
 * implemented in the GUIFacade class in the data layer.
 * 
 * @author HCHB
 */

public interface IGUIFacade {
    
    /**
     * This method is used to inject the logic layer to the GUI layer.
     * 
     * @param logic instance of ILogicFacade that represents the logic layer.
     */
    public abstract void injectLogic(ILogicFacade logic);
    
    /**
     * This method is used to start the application.
     * 
     * @param args String array
     */
    public abstract void startApplication(String[] args);
}
