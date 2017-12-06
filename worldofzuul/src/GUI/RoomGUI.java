/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// This class is located in the GUI layer.
package GUI;

// Imports:
import Acquaintance.IRoom;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

/**
 * This class represents a room in the GUI layer. The class implements the
 * IRoom interface.
 * 
 * @author HCHB
 */

class RoomGUI implements IRoom {
    
    /**
     * Data fields.
     * location: a BorderPane that specifies the room's location in the GUI.
     * characterLocation: a FlowPane that specifies where the character images
     * are placed in the GUI.
     * knownRoomFilePath: file path to the picture of the room when known.
     * unknownRoomFilePath: file path to the picture of the room when unknown.
     * name: String that specifies the name of the room.
     */
    private BorderPane location;
    private FlowPane characterLocation;
    private String knownRoomFilePath;
    private String unknownRoomFilePath;
    private String name;
    
    /**
     * This constructor creates an instance of RoomGUI with the specified
     * location, character location, known room file path, unknown room file
     * path, and name.
     * 
     * @param location BorderPane of the room
     * @param characterLocation FlowPane for character images
     * @param knownRoomFilePath file path to clear image of room
     * @param unknownRoomFilePath file path to blurred image of room
     * @param name name of room
     */
    RoomGUI(BorderPane location, FlowPane characterLocation,
        String knownRoomFilePath, String unknownRoomFilePath, String name) {
        this.location = location;
        this.characterLocation = characterLocation;
        this.knownRoomFilePath = knownRoomFilePath;
        this.unknownRoomFilePath = unknownRoomFilePath;
        this.name = name;
    }

    /**
     * This method returns the location BorderPane. The method is used when
     * setting the room background.
     * 
     * @return location
     */
    BorderPane getLocation() {
        return this.location;
    }

    /**
     * This method returns the character location FlowPane. The method is used
     * to set images in the map.
     * 
     * @return characterLocation
     */
    FlowPane getCharacterLocation() {
        return this.characterLocation;
    }

    /**
     * This method returns the known room file path String. The method is used
     * to set the room background when the room is known.
     * 
     * @return knownRoomFilePath
     */
    String getKnownRoomFilePath() {
        return this.knownRoomFilePath;
    }

    /**
     * This method returns the unknown room file path String. The method is used
     * when setting the room background when the room is not known.
     * 
     * @return unknownRoomFilePath
     */
    String getUnknownRoomFilePath() {
        return this.unknownRoomFilePath;
    }
    
    /**
     * This method returns the name String. The method implements the getName()
     * method from the interface IRoom.
     * 
     * @return name
     */
    @Override
    public String getName(){
        return this.name;
    }
}
