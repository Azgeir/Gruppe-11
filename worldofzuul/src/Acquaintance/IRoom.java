/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// The interface is located in the Acquaintance package.
package Acquaintance;

/**
 * This interface is used to represent a room in the game. The interface is
 * implemented by RoomGUI in the GUI layer and by Room in the logic layer.
 * 
 * @author HCHB
 */
public interface IRoom {
    
    /**
     * This method returns the name of the room.
     * 
     * @return String that represents the name of the room.
     */
    public abstract String getName(); 
}
