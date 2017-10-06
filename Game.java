package worldofzuul;
 
 /**
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    public Game() 
    {
        //Create all rooms by calling the createRooms method
        createRooms();
        parser = new Parser();
    }

    private void createRooms()
    {
        //Declare the rooms
        Room Biolab, Computer, Storage, Medbay, Dorm, PhysicsLab, Dock, Control, Reactor, Pod;
      
        //Initialize the rooms
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
     
        //Declare hallways between rooms
        Room HallwayStorageComputer, HallwayComputerBio, HallwayBioControl, HallwayControlDock, HallwayDockPhysics, HallwayPhysicsDorm, HallwayDormMed,  HallwayMedStorage; 
                
        HallwayStorageComputer = new Room("in the hallway between the storage and computer rooms");
        HallwayComputerBio = new Room("in the hallway between the storage room and the biology laboratory");
        HallwayBioControl = new Room("in the hallway between the biology laboratory and the control room");
        HallwayControlDock = new Room("in the hallway between the control room and the dock");
        HallwayDockPhysics = new Room("in the hallway between the dock and the physics laboratory");
        HallwayPhysicsDorm = new Room("in the hallway between the physics laboratory and the dormitory");
        HallwayDormMed = new Room("in the hallway between the dormitory and the medical bay");
        HallwayMedStorage = new Room("in the hallway between the medical bay and the storage room");
        
        //Declare hallways connected to the reactor
        Room HallwayReactorBio, HallwayReactorControl, HallwayReactorDock, HallwayReactorPhysics, HallwayReactorDorm, HallwayReactorMed, HallwayReactorStorage, HallwayReactorComputer;
        
        HallwayReactorBio = new Room("in the hallway between the reactor and the biology laboratory");
        HallwayReactorControl = new Room("in the hallway between the reactor and the control room");
        HallwayReactorDock = new Room("in the hallway between the reactor and the dock");
        HallwayReactorPhysics = new Room("in the hallway between the reactor and the physics laboratory");
        HallwayReactorDorm = new Room("in the hallway between the reactor and the dormitory");
        HallwayReactorMed = new Room("in the hallway between the reactor and the medical bay");
        HallwayReactorStorage = new Room("in the hallway between the reactor and the storage room");
        HallwayReactorComputer = new Room("in the hallway between the reactor and the computer room");
        
        //Sets possible exits for each hallway
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
        
        //Sets possible exits for hallways from the reactor
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
        
        
        //Sets the possible exits to each room
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
        
        //Sets the exits for the reactor room
        Reactor.setExit("computer", HallwayReactorComputer);
        Reactor.setExit("biolab", HallwayReactorBio);
        Reactor.setExit("control", HallwayReactorControl);
        Reactor.setExit("dock", HallwayReactorDock);
        Reactor.setExit("physicslab", HallwayReactorPhysics);
        Reactor.setExit("dorm", HallwayReactorDorm);
        Reactor.setExit("medbay", HallwayReactorMed);
        Reactor.setExit("storage", HallwayReactorStorage);
        
        
        
        
        //Sets the current room to "computer"
        currentRoom = Computer;
    }

    
    public void play() 
    {            
        //Calls the printWelcome method to show a brief introduction to the game
        printWelcome();
        
        //Checks if the player is still playing
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) 
    {
        //Assumes the player wants to continue playing
        boolean wantToQuit = false;

        //Gets the commandword and executes an if-else statement
        CommandWord commandWord = command.getCommandWord();

        //checks if the input equals any of the defined commands
        //and prints an "error" if it doesnt
        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        
        //Executes the command if the input matches
        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            goRoom(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        return wantToQuit;
    }

    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;
        }
    }
}
