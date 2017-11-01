/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

/**
 *
 * @author laurabrinkholmjustesen
 */

// This class represents a USB item.
public class USB extends Item {
    // Data fields:
    private boolean hasData; // Indicates whether the USB is empty or not
    private String dataType; // Indicates the type of data: biology, physics, monster, or null (for empty USB)
    
    // (£) This constructor creates an empty USB with default name and weight(?)
    public USB (int number) {
        super(1, "USB"+number, "do something");
        this.hasData = false;
        this.dataType = null;
    }
    
    // This method returns a boolean value indicating whether or not the USB has data
    public boolean hasData() {
        return this.hasData;
    }
    
    // This method returns the data type stored on the USB (null if the USB is empty)
    public String getDataType() {
        return this.dataType;
    }
    
    // This method sets the value of hasData
    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }
    
    // This method sets the value of dataType
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    
    // This method is called when the player uses the usb
    @Override
    public double use(Character currentCharacter){
        // Set roomName to current room of the character
        String roomName = currentCharacter.getCurrentRoom().getName();
        
        // If current room is "biolab", add bio data to the USB.
        if (roomName.equals("biolab")) {
            this.hasData = true;
            this.dataType = "bio";
            System.out.println("Important biological experimental data was saved. The USB is now full.");
        }
        // If current room is "physicslab", add physics data to the USB.
        else if (roomName.equals("physicslab")){
            this.hasData = true;
            this.dataType = "physics";
            System.out.println("Important physics experimental data was saved. The USB is now full.");
        }
        // If current room is "control", add control data to the USB.
        else if (roomName.equals("control")){
            this.hasData = true;
            this.dataType = "control" ;
            System.out.println("Surveillance records of the Zuul infestation was optained. The USB is now full.");
        }
        // If current room is not "biolab", "physicslab" or "control", print error.
        else {
            System.out.println("There is nowhere to obtain useful data in this room.");
        }
        
        currentCharacter.setCharacterInitiative(currentCharacter.getCharacterInitiative()+7.5*currentCharacter.getSpeedFactor());
        return 0;
    }
}
