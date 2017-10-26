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
    
    @Override
    public double use(Character currentCharacter){
        String roomName = currentCharacter.getCurrentRoom().getName();
        
        if (roomName.equals("biolab")) {
            this.hasData = true;
            this.dataType = "bio";
            System.out.println("Important biological experimental data was saved. The USB is now full");
        }
        else if (roomName.equals("physicslab")){
            this.hasData = true;
            this.dataType = "physics";
            System.out.println("Important physics experimental data was saved. The USB is now full");
        }
        else if (roomName.equals("control")){
            this.hasData = true;
            this.dataType = "control" ;
            System.out.println("Survailiance records of the Zuul infestation was optained. The USB is now full");
        }
        else {
            System.out.println("There is nowhere to obtain useful data in this room");
        }
        return 0;
    }
}
