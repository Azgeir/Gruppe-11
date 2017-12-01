/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Acquaintance.IRoom;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author HCHB
 */
public class RoomGUI implements IRoom {
    
    private BorderPane location;
    private FlowPane characterLocation;
    private String knownRoomFilePath;
    private String unknownRoomFilePath;
    private String name;
    
    RoomGUI(BorderPane location, FlowPane characterLocation, String knownRoomFilePath, String unknownRoomFilePath, String name){
        this.location = location;
        this.characterLocation = characterLocation;
        this.knownRoomFilePath = knownRoomFilePath;
        this.unknownRoomFilePath = unknownRoomFilePath;
        this.name = name;
    }

    public BorderPane getLocation() {
        return location;
    }

    public FlowPane getCharacterLocation() {
        return characterLocation;
    }

    public String getKnownRoomFilePath() {
        return knownRoomFilePath;
    }

    public String getUnknownRoomFilePath() {
        return unknownRoomFilePath;
    }
    
    @Override
    public String getName(){
        return this.name;
    }
}
