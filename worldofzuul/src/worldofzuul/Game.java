package worldofzuul;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

 /**
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

// This class runs the main functionality of the game.
public class Game 
{
    // Data fields:
    private Parser parser;
    // ArrayList is chosen because it allows us to know which character is chosen when the initiative is the same.
    private ArrayList<Character> characters = new ArrayList<>();
    private Character currentCharacter;
    private HashMap<String,Room> characterCurrentRooms = new HashMap<>();
    private Room winConditionRoom;
    
    // This constructor creates a Game object by creating a Parser and calling the createRooms method.
    public Game() 
    {
        //Create all rooms by calling the createRooms method
        createRooms();
        createCharacter();
        parser = new Parser();
    }
    
    // This method creates the rooms of the game.
    private void createRooms()
    {
        // Declare the rooms
        Room Biolab, Computer, Storage, Medbay, Dorm, PhysicsLab, Dock, Control, Reactor, Pod;
      
        // Initialize the rooms
        Biolab = new Room("in the biology laboratory");
        Computer = new Room("in the computer room");
        Storage = new Room("in the storage room");
        Medbay = new Room("in the medical bay");
        Dorm = new Room("in the dormitory");
        PhysicsLab = new Room ("in the physics laboratory");
        Dock = new Room("in the dock");
        Control= new Room("in the control room");
        Reactor = new Room("near the reactor");
        Pod = new Room("in the escape pod");
     
        // Declare hallways between rooms
        Room HallwayStorageComputer, HallwayComputerBio, HallwayBioControl, HallwayControlDock, HallwayDockPhysics, HallwayPhysicsDorm, HallwayDormMed,  HallwayMedStorage; 
         
        // Initialize the hallways between the outer rooms
        HallwayStorageComputer = new Room("in the hallway between the storage and computer rooms");
        HallwayComputerBio = new Room("in the hallway between the storage room and the biology laboratory");
        HallwayBioControl = new Room("in the hallway between the biology laboratory and the control room");
        HallwayControlDock = new Room("in the hallway between the control room and the dock");
        HallwayDockPhysics = new Room("in the hallway between the dock and the physics laboratory");
        HallwayPhysicsDorm = new Room("in the hallway between the physics laboratory and the dormitory");
        HallwayDormMed = new Room("in the hallway between the dormitory and the medical bay");
        HallwayMedStorage = new Room("in the hallway between the medical bay and the storage room");
        
        // Declare hallways connected to the reactor
        Room HallwayReactorBio, HallwayReactorControl, HallwayReactorDock, HallwayReactorPhysics, HallwayReactorDorm, HallwayReactorMed, HallwayReactorStorage, HallwayReactorComputer;
        
        // Initialize the hallways connected to the reactor
        HallwayReactorBio = new Room("in the hallway between the reactor and the biology laboratory");
        HallwayReactorControl = new Room("in the hallway between the reactor and the control room");
        HallwayReactorDock = new Room("in the hallway between the reactor and the dock");
        HallwayReactorPhysics = new Room("in the hallway between the reactor and the physics laboratory");
        HallwayReactorDorm = new Room("in the hallway between the reactor and the dormitory");
        HallwayReactorMed = new Room("in the hallway between the reactor and the medical bay");
        HallwayReactorStorage = new Room("in the hallway between the reactor and the storage room");
        HallwayReactorComputer = new Room("in the hallway between the reactor and the computer room");
        
        // Set possible exits for hallways between rooms
        HallwayStorageComputer.setExit("storage", Storage);
        HallwayStorageComputer.setExit("computer", Computer);
        
        HallwayComputerBio.setExit("computer", Computer);
        HallwayComputerBio.setExit("biolab", Biolab);
        
        HallwayBioControl.setExit("control", Control);
        HallwayBioControl.setExit("biolab", Biolab);
        
        HallwayControlDock.setExit("control", Control);
        HallwayControlDock.setExit("dock", Dock);
        
        HallwayDockPhysics.setExit("physicslab", PhysicsLab);
        HallwayDockPhysics.setExit("dock", Dock);
        
        HallwayPhysicsDorm.setExit("physicslab", PhysicsLab);
        HallwayPhysicsDorm.setExit("dorm", Dorm);
        
        HallwayDormMed.setExit("medbay", Medbay);
        HallwayDormMed.setExit("dorm", Dorm);
        
        HallwayMedStorage.setExit("medbay", Medbay);
        HallwayMedStorage.setExit("storage", Storage);
        
        // Set possible exits for hallways from the reactor
        HallwayReactorBio.setExit("reactor", Reactor);
        HallwayReactorBio.setExit("biolab", Biolab);
        
        HallwayReactorControl.setExit("reactor", Reactor);
        HallwayReactorControl.setExit("control", Control);
        
        HallwayReactorDock.setExit("reactor", Reactor);
        HallwayReactorDock.setExit("dock", Dock);
        
        HallwayReactorPhysics.setExit("reactor", Reactor);
        HallwayReactorPhysics.setExit("physics lab", PhysicsLab);
        
        HallwayReactorDorm.setExit("reactor", Reactor);
        HallwayReactorDorm.setExit("dorm", Dorm);
        
        HallwayReactorMed.setExit("reactor", Reactor);
        HallwayReactorMed.setExit("medbay", Medbay);
        
        HallwayReactorStorage.setExit("reactor", Reactor);
        HallwayReactorStorage.setExit("storage", Storage);
        
        HallwayReactorComputer.setExit("reactor", Reactor);
        HallwayReactorComputer.setExit("computer", Computer);
        
        // Set the possible exits for each room
        Biolab.setExit("computer", HallwayComputerBio);
        Biolab.setExit("control", HallwayBioControl);
        Biolab.setExit("reactor", HallwayReactorBio);
        
        Control.setExit("biolab", HallwayBioControl);
        Control.setExit("dock", HallwayControlDock);
        Control.setExit("reactor", HallwayReactorControl);

        Dock.setExit("control", HallwayControlDock);
        Dock.setExit("physicslab", HallwayDockPhysics);
        Dock.setExit("reactor", HallwayReactorDock);
        Dock.setExit("pod", Pod);

        PhysicsLab.setExit("dock", HallwayDockPhysics);
        PhysicsLab.setExit("dorm", HallwayPhysicsDorm);
        PhysicsLab.setExit("reactor", HallwayReactorPhysics);
        
        Dorm.setExit("physicslab", HallwayPhysicsDorm);
        Dorm.setExit("medbay", HallwayDormMed);
        Dorm.setExit("reactor", HallwayReactorDorm);

        Medbay.setExit("dorm", HallwayDormMed);
        Medbay.setExit("storage", HallwayMedStorage);
        Medbay.setExit("reactor", HallwayReactorMed);
        
        Storage.setExit("medbay", HallwayMedStorage);
        Storage.setExit("computer", HallwayStorageComputer);
        Storage.setExit("reactor", HallwayReactorStorage);
        
        Computer.setExit("storage", HallwayStorageComputer);
        Computer.setExit("biolab", HallwayComputerBio);
        Computer.setExit("reactor", HallwayReactorComputer);
        
        Pod.setExit("dock", Dock);
        
        // Set the exits for the reactor room
        Reactor.setExit("computer", HallwayReactorComputer);
        Reactor.setExit("biolab", HallwayReactorBio);
        Reactor.setExit("control", HallwayReactorControl);
        Reactor.setExit("dock", HallwayReactorDock);
        Reactor.setExit("physicslab", HallwayReactorPhysics);
        Reactor.setExit("dorm", HallwayReactorDorm);
        Reactor.setExit("medbay", HallwayReactorMed);
        Reactor.setExit("storage", HallwayReactorStorage);
        
        // Set the current room to "computer" (Possibly moved to character class)
        characterCurrentRooms.put("Computer",Computer);
        characterCurrentRooms.put("Control",Control);
        characterCurrentRooms.put("Dorm",Dorm);
        winConditionRoom = Pod;
    }
    private void createCharacter(){
        this.characters.add(new Hero(characterCurrentRooms.get("Computer")));
        this.characters.add(new Zuul(characterCurrentRooms.get("Dorm")));
        this.characters.add(new TechDude(characterCurrentRooms.get("Control")));
    }

    // This method plays the game
    public void play() 
    {            
        // Call the printWelcome method to show a brief introduction to the game
        printWelcome();
        
        // Check if the player is still playing
        boolean finished = false;
        // As long as game is not finished, get and process user commands
        while (!finished) {
            this.currentCharacter = this.chooseCharacter();
            Command command = parser.getCommand();
            finished = processCommand(command);
            
            finished = winTest();
        }
        // Print goodbye message when user exits game.
        System.out.println("Thank you for playing.  Good bye.");
    }

    // This method prints the welcome message.
    private void printWelcome()
    {
        // Print welcome message
        System.out.println();
        System.out.println("Welcome to Escape Pod!");
        System.out.println("\nYou are a Software engineer in a space station, and\n" +
                "the emergency alarm has just gone off. You must find\n" +
                "the other crew members, find out what is going on and\n" +
                "find the escape pod if necessary.\n");
        System.out.println("Type '" + CommandWord.HELP + "' for more information about controls and the game.");
        System.out.println();
        // Description of current room of the player, including available exits.
        System.out.println(characterCurrentRooms.get("Dorm").getLongDescription());
}

    // This method processes the command of the player (returns true if player wants to quit)
    private boolean processCommand(Command command) 
    {
        // Assume the player wants to continue playing
        boolean wantToQuit = false;

        // Create instance of CommandWord using the command word of the specified command (from Parser)
        CommandWord commandWord = command.getCommandWord();

        // Check if the input equals any of the defined commands and print an "error" if it does not
        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        
        // Execute the command if the input matches a valid command
        // If command is "help" print the help message
        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        // If command is "go", call goRoom method
        else if (commandWord == CommandWord.GO) {
            this.currentCharacter.go(command);
//            goRoom(command);
        }
        // If command is "quit", change value of wantToQuit to true
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        // Return boolean value (false = continue playing; true = quit game)
        return wantToQuit;
    }

    // This method prints a help message, including available commands
    private void printHelp() 
    {
        System.out.println("You are on a spacestation, conducting experiments for the good of the human race.");
        System.out.println("Something hit the spacestation, and you now have to save yourself and any possible survivors.");
        System.out.println("The escape pod is in the dock, and is the only way to get off the spacestation");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    //iterates over the different characters to determine whos turn it is
    // if two have the same initiative the breaker is who is defined first in the
    // ArrayList
    private Character chooseCharacter(){
        Character currentCharacter = null;
        double minInitiative = Integer.MAX_VALUE;
        boolean characterChosen = false; 
        for(Character character : characters){
            if (minInitiative>character.getCharacterInitiative()){
                minInitiative = character.getCharacterInitiative();
                currentCharacter = character;
                characterChosen = true;
            }
        }
        
        return currentCharacter;
    }
    


// This method changes the value of current room based on the specified command.
//    private void goRoom(Command command) 
//    {
//        // If the command does not have a second word (specifying direction), print error message and exit method.
//        if(!command.hasSecondWord()) {
//            System.out.println("Go where?");
//            return;
//        }
//
//        // If the command has a second word, assign second word to "direction".
//        String direction = command.getSecondWord();
//
//        // Assign the Room in the specified direction to "nextRoom"
//        Room nextRoom = currentRoom.getExit(direction);
//
//        // If "nextRoom" is null, print an error message.
//        if (nextRoom == null) {
//            System.out.println("There is no door!");
//        }
//        // If "nextRoom" is not null, change currentRoom to nextRoom, and print description of the new room.
//        else {
//            currentRoom = nextRoom;
//            System.out.println(currentRoom.getLongDescription());
//        }
//    }

    // This method quits the game
    private boolean quit(Command command) 
    {
        // If the "quit" command has a second word, print error message and exit method.
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        // If the command has no second word, return "true", which causes the game to end.
        else {
            printStopMessage("quit");
            return true;
        }
    }
    
    private void printStopMessage(String reason){
        
        if (reason == "win"){
            double point = pointCalculation();
            System.out.println("Congratulations, you won the game");
            System.out.printf("You got %f.2 points \n ",point);
        }
        else if (reason == "lose"){
            System.out.println("You died and terefore lost the game");
        }
        else {
            System.out.println("You quit the current instance of the game");
        }
    }
    //£
    private double pointCalculation(){
        
        Hero hero = (Hero)(characters.get(0));
        USB usb;
        HashSet<String> pointSet = new HashSet<>();
        
        for (int i = 1 ; i<4 ; i++){
            String name = "USB " + i;
            usb = (USB)hero.getInventory().getItem(name);
            if (usb != null){
                if (usb.getDataType() != null) {
                    System.out.println("You got the " + usb.getDataType() + " data");
                    pointSet.add(usb.getDataType());
                }
            }
        }
        
        double point = (pointSet.size()*5+5)*(1+(5/(hero.getCharacterInitiative()+5)));
        return point;
    }
    
    private boolean winTest(){
        boolean finished = false;
        if(characters.get(0).getCurrentRoom().equals(winConditionRoom)){
            finished = true;
            printStopMessage("win");
        }
        return finished;
    }
}
