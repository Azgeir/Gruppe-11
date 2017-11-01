package worldofzuul;
// Imports:
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

// This class runs the main functionality of the game.
public class Game {

    // Data fields:
    private Parser parser;
    // ArrayList is chosen because it allows us to know which character is chosen when the initiative is the same.
    private ArrayList<Character> characters = new ArrayList<>(); // This is an arraylist because it offer a simple way to break an initiative tie in a predefined way
    private Character currentCharacter;
    private HashMap<String, Room> characterStartRooms = new HashMap<>();
    private Room winConditionRoom;
    private boolean sameRoom = false;
    private boolean zuulHadTurn = false;
    private double maxInititative = Double.MAX_VALUE;
    private boolean reactorActivated = false;

    // This constructor creates a Game object by creating a Parser and calling the createRooms method.
    public Game() {
        //Create all rooms by calling the createRooms method
        createRooms();
        // Create the characters by calling the createCharacter() method
        createCharacter();
        // Create a parser
        parser = new Parser();
    }

    // This method creates the rooms of the game.
    private void createRooms() {
        // Declare the rooms
        Room biologyLaboratory, computerRoom, storage, medicalBay, dormitory, 
            physicsLaboratory, dock, controlRoom, reactor, escapePod;

        // Initialize the biology laboratory
        biologyLaboratory = new Room("in the biology laboratory", "biolab",
            ("You are in the biology laboratory. On the table to your right\n" +
            "there is a row of microscopes, and the shelf above contains a\n" +
            "collection of test tubes with liquids of different colours.\n" +
            "In the corner of the room there is a computer. Maybe you'll be\n" +
            "able to save some of the research.\n"));
        
        // Initialise the computer room
        computerRoom = new Room("in the computer room", "computer",
        ("You are in the computer room, this is here you had your working \n" +
        "hours, you see several computers, chairs and USB's and larger storage \n" + 
        "units you also see your accescard laying on the desk. \n"));
        
        // Initialise the storage room
        storage = new Room("in the storage room", "storage",
        ("You are in the storage facility. here you see several drawers and \n" + 
        "shelves, containing everything from dried food to different tools, \n" +
        "medkits and reseach compartments \n"));
        
        // Initialise the medical bay
        medicalBay = new Room("in the medical bay", "medbay",
        ("You are in the medbay. This is where you would get treated and \n" +
        "contained if you fell sick or got minor injuries, there are beds \n" + 
        "and several drawers with medkits, pills and injectors \n"));
        
        // Initialise the dormitory
        dormitory = new Room("in the dormitory", "dorm",
            ("You are in the dormitory. This is where you used to sleep and\n" +
            "relax with your colleagues. Sadly, this is also where the monster\n" +
            "arrived. You see the corpses of your beloved colleagues scattered\n" +
            "around the room.\n"));
        
        // Initialise the physics laboratory
        physicsLaboratory = new Room("in the physics laboratory", "physicslab",
            "You are in the physics laboratory. The room is filled with various\n" +
            "equipment. On a table nearby, you see a Helmholtz coil, and on your\n" +
            "right, there is a mass spectrometer. In the corner of the room, you\n" +
            "see a computer. Maybe you'll be able to save some of the research.\n");
        
        // Initialise the dock
        dock = new Room("in the dock", "dock",
        "You are in the Dock. This is where supply ships come and go and also \n" +
        "the only way off the spacestation via the pod, but the spacestation \n" +
        "is currently under quarantine and you don't know how to overwrite \n" + 
        "the quarantine, there are tools for repairs, 3d printers and spacesuits \n");
        
        // Initialise the control room
        controlRoom = new Room("in the control room", "control",
        ("You are in the control room. this is where information goes to and \n" + 
        "from the spacestation, this is where you find the Tech dude he was \n" + 
        "trying to reestablish the connection to earth but to no avail \n"));
        
        // Initialise the reactor
        reactor = new Room("near the reactor", "reactor",
        ("You are in the reactor room. The reactor is a very dense nuclear \n" + 
        "reactor, if it where to melt down the spacestation would be annialated \n" + 
        "you see some basic tools and some Geiger counters and a coupple of \n" + 
        "spacesuits \n"));
        
        // Initialise the escape pod
        escapePod = new Room("in the escape pod", "pod");

        // Declare hallways between rooms
        Room hallwayStorageComputer, hallwayComputerBiology, hallwayBiologyControl, 
            hallwayControlDock, hallwayDockPhysics, hallwayPhysicsDormitory, 
            hallwayDormitoryMedical, hallwayMedicalStorage;

        // Initialize the hallways between the outer rooms
        hallwayStorageComputer = new Room("in the hallway between the storage and computer rooms");
        hallwayComputerBiology = new Room("in the hallway between the storage room and the biology laboratory");
        hallwayBiologyControl = new Room("in the hallway between the biology laboratory and the control room");
        hallwayControlDock = new Room("in the hallway between the control room and the dock");
        hallwayDockPhysics = new Room("in the hallway between the dock and the physics laboratory");
        hallwayPhysicsDormitory = new Room("in the hallway between the physics laboratory and the dormitory");
        hallwayDormitoryMedical = new Room("in the hallway between the dormitory and the medical bay");
        hallwayMedicalStorage = new Room("in the hallway between the medical bay and the storage room");

        // Declare hallways connected to the reactor
        Room hallwayReactorBiology, hallwayReactorControl, hallwayReactorDock,
            hallwayReactorPhysics, hallwayReactorDormitory, hallwayReactorMedical,
            hallwayReactorStorage, hallwayReactorComputer;

        // Initialize the hallways connected to the reactor
        hallwayReactorBiology = new Room("in the hallway between the reactor and the biology laboratory");
        hallwayReactorControl = new Room("in the hallway between the reactor and the control room");
        hallwayReactorDock = new Room("in the hallway between the reactor and the dock");
        hallwayReactorPhysics = new Room("in the hallway between the reactor and the physics laboratory");
        hallwayReactorDormitory = new Room("in the hallway between the reactor and the dormitory");
        hallwayReactorMedical = new Room("in the hallway between the reactor and the medical bay");
        hallwayReactorStorage = new Room("in the hallway between the reactor and the storage room");
        hallwayReactorComputer = new Room("in the hallway between the reactor and the computer room");

        // Set possible exits for hallways between rooms
        hallwayStorageComputer.setExit("storage", storage, false);
        hallwayStorageComputer.setExit("computer", computerRoom, false);

        hallwayComputerBiology.setExit("computer", computerRoom, false);
        hallwayComputerBiology.setExit("biolab", biologyLaboratory, false);

        hallwayBiologyControl.setExit("control", controlRoom, true);
        hallwayBiologyControl.setExit("biolab", biologyLaboratory, false);

        hallwayControlDock.setExit("control", controlRoom, true);
        hallwayControlDock.setExit("dock", dock, false);

        hallwayDockPhysics.setExit("physicslab", physicsLaboratory, false);
        hallwayDockPhysics.setExit("dock", dock, false);

        hallwayPhysicsDormitory.setExit("physicslab", physicsLaboratory, false);
        hallwayPhysicsDormitory.setExit("dorm", dormitory, false);

        hallwayDormitoryMedical.setExit("medbay", medicalBay, false);
        hallwayDormitoryMedical.setExit("dorm", dormitory, false);

        hallwayMedicalStorage.setExit("medbay", medicalBay, false);
        hallwayMedicalStorage.setExit("storage", storage, false);

        // Set possible exits for hallways from the reactor
        hallwayReactorBiology.setExit("reactor", reactor, false);
        hallwayReactorBiology.setExit("biolab", biologyLaboratory, false);

        hallwayReactorControl.setExit("reactor", reactor, false);
        hallwayReactorControl.setExit("control", controlRoom, true);

        hallwayReactorDock.setExit("reactor", reactor, false);
        hallwayReactorDock.setExit("dock", dock, false);

        hallwayReactorPhysics.setExit("reactor", reactor, false);
        hallwayReactorPhysics.setExit("physicslab", physicsLaboratory, false);

        hallwayReactorDormitory.setExit("reactor", reactor, false);
        hallwayReactorDormitory.setExit("dorm", dormitory, false);

        hallwayReactorMedical.setExit("reactor", reactor, false);
        hallwayReactorMedical.setExit("medbay", medicalBay, false);

        hallwayReactorStorage.setExit("reactor", reactor, false);
        hallwayReactorStorage.setExit("storage", storage, false);

        hallwayReactorComputer.setExit("reactor", reactor, false);
        hallwayReactorComputer.setExit("computer", computerRoom, false);

        // Set the possible exits for each room
        biologyLaboratory.setExit("computer", hallwayComputerBiology, false);
        biologyLaboratory.setExit("control", hallwayBiologyControl, false);
        biologyLaboratory.setExit("reactor", hallwayReactorBiology, false);

        controlRoom.setExit("biolab", hallwayBiologyControl, true);
        controlRoom.setExit("dock", hallwayControlDock, true);
        controlRoom.setExit("reactor", hallwayReactorControl, true);

        dock.setExit("control", hallwayControlDock, false);
        dock.setExit("physicslab", hallwayDockPhysics, false);
        dock.setExit("reactor", hallwayReactorDock, false);
        dock.setExit("pod", escapePod, true);

        physicsLaboratory.setExit("dock", hallwayDockPhysics, false);
        physicsLaboratory.setExit("dorm", hallwayPhysicsDormitory, false);
        physicsLaboratory.setExit("reactor", hallwayReactorPhysics, false);

        dormitory.setExit("physicslab", hallwayPhysicsDormitory, false);
        dormitory.setExit("medbay", hallwayDormitoryMedical, false);
        dormitory.setExit("reactor", hallwayReactorDormitory, false);

        medicalBay.setExit("dorm", hallwayDormitoryMedical, false);
        medicalBay.setExit("storage", hallwayMedicalStorage, false);
        medicalBay.setExit("reactor", hallwayReactorMedical, false);

        storage.setExit("medbay", hallwayMedicalStorage, false);
        storage.setExit("computer", hallwayStorageComputer, false);
        storage.setExit("reactor", hallwayReactorStorage, false);

        computerRoom.setExit("storage", hallwayStorageComputer, false);
        computerRoom.setExit("biolab", hallwayComputerBiology, false);
        computerRoom.setExit("reactor", hallwayReactorComputer, false);

        escapePod.setExit("dock", dock, false);

        // Set the exits for the reactor room
        reactor.setExit("computer", hallwayReactorComputer, false);
        reactor.setExit("biolab", hallwayReactorBiology, false);
        reactor.setExit("control", hallwayReactorControl, false);
        reactor.setExit("dock", hallwayReactorDock, false);
        reactor.setExit("physicslab", hallwayReactorPhysics, false);
        reactor.setExit("dorm", hallwayReactorDormitory, false);
        reactor.setExit("medbay", hallwayReactorMedical, false);
        reactor.setExit("storage", hallwayReactorStorage, false);

        // Set the current room to "computer" (Possibly moved to character class)
        characterStartRooms.put("Computer", computerRoom);
        characterStartRooms.put("Control", controlRoom);
        characterStartRooms.put("Dorm", dormitory);
        
        // Set the value of the win condition
        winConditionRoom = escapePod;
        
        // Set the initial positions of Zuul, hero, and tech dude
        dormitory.setHasCharacter("Zuul", true);
        computerRoom.setHasCharacter("Hero", true);
        controlRoom.setHasCharacter("TechDude", true);
        
        // Add items to the inventory of the rooms
        this.fillRoom(computerRoom);
        this.fillRoom(storage);
        this.fillRoom(medicalBay);
        this.fillRoom(biologyLaboratory);
        this.fillRoom(dock);
        this.fillRoom(reactor);
        this.fillRoom(controlRoom);
        this.fillRoom(physicsLaboratory);
        this.fillRoom(dormitory);
        this.fillRoom(escapePod);
    }

    // This method initialises the inventory of the given room
    private void fillRoom(Room room) {
        switch (room.getName()) {
            case "biolab":
                room.getInventory().addItem(new Item(10, "notebook"));
                room.getInventory().addItem(new Item(50, "microscope"), 3);
                room.getInventory().addItem(new Item(200, "DNA-sequencing-machine"));
                room.getInventory().addItem(new Item(150, "incubator"), 2);
                room.getInventory().addItem(new Item(200, "refrigerator"), 2);
                room.getInventory().addItem(new Item(20, "animal-cell-model"));
                room.getInventory().addItem(new Item(20, "plant-cell-model"));
                room.getInventory().addItem(new Item(20, "water-sampler"));
                room.getInventory().addItem(new Item(20, "air-sampler"));
                room.getInventory().addItem(new Item(5, "methylene-blue-stain"), 10);
                room.getInventory().addItem(new Item(5, "Gram's-iodine-solution"), 10);
                room.getInventory().addItem(new Item(5, "pipette"), 20);
                room.getInventory().addItem(new Item(5, "microscope-slide"), 20);
                room.getInventory().addItem(new Item(5, "cell-lysis-solution"), 10);
                room.getInventory().addItem(new Item(5, "DNA-precipitate-solution"), 10);
                room.getInventory().addItem(new Item(5, "test-tube"), 20);
                room.getInventory().addItem(new Item(75, "centrifuge"), 2);
                room.getInventory().addItem(new AcidVial(5, 50), 10);
                break;
            case "computer":
                room.getInventory().addItem(new USB(3));
                room.getInventory().addItem(new USB(2));
                room.getInventory().addItem(new USB(1));
                room.getInventory().addItem(new Item(150, "computer-monitor"), 10);
                room.getInventory().addItem(new Item(10, "computer-mouse"), 10);
                room.getInventory().addItem(new Item(20, "keyboard"), 10);
                room.getInventory().addItem(new AccessCard());
                break;
            case "storage":
                room.getInventory().addItem(new AcidVial(5, 50), 5);
                room.getInventory().addItem(new MedKit(), 10);
                room.getInventory().addItem(new Item(150, "box"), 30);
                room.getInventory().addItem(new Item(150, "bed"), 15);
                room.getInventory().addItem(new Item(75, "electronics"), 25);
                room.getInventory().addItem(new Item(135, "solarpanel"), 15);
                room.getInventory().addItem(new Item(2, "hammer"), 13);
                room.getInventory().addItem(new Item(5, "saw"), 9);
                room.getInventory().addItem(new Item(3, "crowbar"), 7);
                room.getInventory().addItem(new Item(110, "plasma-saw"), 6);
                room.getInventory().addItem(new Item(150, "nailgun"), 8);
                room.getInventory().addItem(new AccessCard());
                break;
            case "medbay":
                room.getInventory().addItem(new AcidVial(5, 50), 7);
                room.getInventory().addItem(new MedKit(), 10);
                room.getInventory().addItem(new Item(1, "syringe"), 17);
                room.getInventory().addItem(new Item(2, "pill"), 29);
                room.getInventory().addItem(new Item(6, "glass"), 49);
                room.getInventory().addItem(new Item(20, "book"), 200);
                room.getInventory().addItem(new Item(9, "coat"), 19);
                room.getInventory().addItem(new Item(350, "desk"), 8);
                room.getInventory().addItem(new Item(15, "display-skeleton"), 5);
                break;
            case "dorm":
                room.getInventory().addItem(new Item(5, "dehydrated-food"), 50);
                room.getInventory().addItem(new Item(200, "bed"), 20);
                room.getInventory().addItem(new Item(10, "pillow"), 20);
                room.getInventory().addItem(new Item(20, "duvet"), 20);
                room.getInventory().addItem(new Item(200, "corpse"), 10);
                room.getInventory().addItem(new Item(200, "couch"));
                room.getInventory().addItem(new Item(200, "table"), 2);
                room.getInventory().addItem(new Item(200, "bookcase"));
                room.getInventory().addItem(new Item(10, "book"), 100);
                room.getInventory().addItem(new Item(150, "desk"), 2);
                room.getInventory().addItem(new Item(50, "chair"), 6);
                break;
            case "physicslab":
                room.getInventory().addItem(new AcidVial(5, 50), 7);
                room.getInventory().addItem(new MedKit(), 10);
                room.getInventory().addItem(new Item(5, "test tube"), 20);
                room.getInventory().addItem(new Item(2, "coat"), 10);
                room.getInventory().addItem(new Item(2, "knive"), 10);
                room.getInventory().addItem(new Item(1, "syringe"), 15);
                room.getInventory().addItem(new Item(350, "desk"), 2);
                room.getInventory().addItem(new Item(50, "chair"), 10);
                room.getInventory().addItem(new Item(75, "computer"), 5);
                room.getInventory().addItem(new Item(200, "table"), 4);
                room.getInventory().addItem(new Item(2, "quantum- eqqiupment"), 29);
                room.getInventory().addItem(new Item(5, "test tube"), 20);
                room.getInventory().addItem(new Item(5, "funny chemical"), 25);
                break;
            case "dock":
                room.getInventory().addItem(new Item(200, "crate"), 30);
                room.getInventory().addItem(new Item(500, "fuel station"), 1);
                room.getInventory().addItem(new Item(50, "barrel"), 40);
                room.getInventory().addItem(new Item(35, "baggage"), 10);
                room.getInventory().addItem(new Item(150, "computer-moniter"), 15);
                room.getInventory().addItem(new Item(200, "corpse"), 2);
                break;
            case "control":
                room.getInventory().addItem(new MedKit(), 10);
                room.getInventory().addItem(new Item(175, "computer"), 5);
                room.getInventory().addItem(new Item(150, "computer-monitor"), 15);
                room.getInventory().addItem(new Item(20, "keyboard"), 15);
                room.getInventory().addItem(new Item(2, "screwdriver"), 6);
                room.getInventory().addItem(new Item(2, "hammer"), 4);
                room.getInventory().addItem(new Item(1, "paper"), 20);
                break;
            case "reactor":
                room.getInventory().addItem(new Item(150, "computer-monitor"), 10);
                room.getInventory().addItem(new Item(175, "computer"), 4);
                room.getInventory().addItem(new Item(2, "screwdriver"), 8);
                room.getInventory().addItem(new Item(200, "crate"), 10);
                break;
            case "pod":
                room.getInventory().addItem(new MedKit());
                room.getInventory().addItem(new Item(5, "dehydrated-food"), 5);
                room.getInventory().addItem(new Item(2, "hammer"), 2);
                room.getInventory().addItem(new Item(2, "screwdriver"), 2);
                break;
            default:
                break;
        }
    }

    // This method creates the hero, monster, and tech dude and adds them to the array list of characters.
    private void createCharacter() {
        this.characters.add(new Hero(characterStartRooms.get("Computer"), "Hero"));
        this.characters.add(new Zuul(characterStartRooms.get("Dorm"),"Zuul"));
        this.characters.add(new TechDude(characterStartRooms.get("Control"),"TechDude"));
    }

    // This method plays the game
    public void play() {
        // Call the printWelcome method to show a brief introduction to the game
        printWelcome();
        // Check if the player is still playing
        boolean finished = false;
        // As long as game is not finished, get and process user commands
        while (!finished) {
            // Select current character
            this.currentCharacter = this.chooseCharacter();
            // checks if the TechDude has met the Hero
            techDudeMeetHero();
            // Get command from parser
            Command command = parser.getCommand(this.currentCharacter);
            // Process command
            finished = processCommand(command);
            // Check if player lost game because they met Zuul
            
            if (!finished) {
                finished = lose();
            }
            // Check if player lost game because of reactor
            if (!finished) {
                finished = timerLose();
            }
            // Check if player won game
            if (!finished) {
                finished = winTest();
            }
            if (!finished) {
                finished = healthTest();
            }
        }
    }

    // This method prints the welcome message.
    private void printWelcome() {
        // Print welcome message
        System.out.println();
        System.out.println("Welcome to Escape Pod!");
        System.out.println("\nYou are a Software engineer in a space station, and\n"
        + "the emergency alarm has just gone off and the station is under \n"
        + "qurrantine. You must find items and other survivors and escape \n"
        + "the station before you are caught by what is ravaging the station \n");
        System.out.println("Type '" + CommandWord.HELP + "' for more information about controls and the game.");
        System.out.println();
        // Description of current room of the player, including available exits.
        System.out.println(characterStartRooms.get("Computer").getLongDescription());
    }

    // This method processes the command of the player (returns true if player wants to quit)
    private boolean processCommand(Command command) {
        // Assume the player wants to continue playing
        boolean wantToQuit = false;

        // Create instance of CommandWord using the command word of the specified command (from Parser)
        CommandWord commandWord = command.getCommandWord();

        // Check if the input equals any of the defined commands and print an "error" if it does not
        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...\nTyping help will give you the valid commands ");
            return false;
        }

        if (null != commandWord) // Execute the command if the input matches a valid command
        // If command is "help" print the help message
        switch (commandWord) {
            // If command is "help", print help message
            case HELP:
                printHelp();
                break;
            // If command is "go", call go() method on current character
            case GO:
                this.currentCharacter.go(command);
                break;
            // If command is "quit", call quit() method (returns true, if player wants to quit)
            case QUIT:
                wantToQuit = quit(command);
                break;
            // If command is "stay", call stay() method on current character
            case STAY:
                this.currentCharacter.stay(command);
                break;
            // If command is "pickup", call pickup() method on current character
            case PICKUP:
                this.currentCharacter.pickUp(command);
                break;
            // If command is "drop", call dropItem() method on current character
            case DROP:
                this.currentCharacter.dropItem(command);
                break;
            // If command is "look", call look() method on current character
            case LOOK:
                this.currentCharacter.look(command);
                break;
            // If command is "peek", call peek() method on current character
            case PEEK:
                this.currentCharacter.peek(command);
                break;
            // If command is "use", call use() method on current character and change Zuul's initiative
            case USE:
                double zuulInitiativeReduction = this.currentCharacter.use(command);
                Character zuul = this.characters.get(1);
                zuul.setCharacterInitiative(zuul.getCharacterInitiative()+zuulInitiativeReduction);
                break;
            // If command is "lock", call lock() command on current character
            case LOCK:
                this.currentCharacter.lock(command);
                break;
            // If command is "unlock", call unlock() command on current character
            case UNLOCK:
                this.currentCharacter.unlock(command);
                break;
            // If command is "activate", set MaxInitiative to the return value of the activate() method
            case ACTIVATE:
                double newInitiative = this.currentCharacter.activate(command);
                if (this.maxInititative == Double.MAX_VALUE){ 
                    reactorActivated = true;
                    this.maxInititative = newInitiative;
                }
                break;
            // If command does not match any of the options, break.
            default:
                break;
        }
        // Return boolean value (false = continue playing; true = quit game)
        return wantToQuit;
    }

    // This method prints a help message, including available commands
    private void printHelp() {
        System.out.println("You must find other crewmembers save them, \n" +
        "you must survive and reach the escape pod. find items that can \n" +
        "help you on your way by using the look command and then picking \n" +
        "them up, secret bonus objectives are scattered around be mindfull \n" +
        "of what survivers tell you and uses that certain items can have \n" +
        "you can only carry a certain weight of items so manage your \n" + 
        "inventory accordingly");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
        System.out.println();
        System.out.println("After you put in a command you will almost always \n"
        + "need to follow the command up with a second or third word. \n"
        + "example would be when you want to lock a door you will type \n"
        + "lock then the exit you want to lock. \n"
        + "execptions to this is \n"
        + "stay which will just make you use your turn on not moving \n"
        + "quit which will quit the game and end the current play session \n"
        + "Command words that require another word will often ask you to enter \n"
        + "a specefic second word as putting in go will ask you 'go where?'");
    }

    // This method iterates over the different characters to determine whose turn it is.
    // If two characters have the same initiative, the breaker is who is defined first in the
    // ArrayList
    private Character chooseCharacter() {
        // Set current character to null
        Character currentCharacter = null;
        // Set minInitiative to maximum integer value
        double minInitiative = Integer.MAX_VALUE;
        // Traverse all characters
        for (Character character : characters) {
            // Select character with the lowest initiative
            if (minInitiative > character.getCharacterInitiative()) {
                minInitiative = character.getCharacterInitiative();
                currentCharacter = character;
            }
        }
        // Return the selected character
        return currentCharacter;
    }

    // This method quits the game
    private boolean quit(Command command) {
        // If the "quit" command has a second word, print error message and exit method.
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        // If the command has no second word, return "true", which causes the game to end.
        else {
            printStopMessage("quit");
            return true;
        }
    }

    // This method prints a stop message depending on the reason string
    private void printStopMessage(String reason) {
        
        boolean techDudeIsThere = this.currentCharacter.getCurrentRoom().getHasCharacter("TechDude");
        
        // If the player won the game, print message specifying the total points earned
        if (reason == "win") {
            // Calculate earned points
            double point = pointCalculation();
            if (techDudeIsThere) {
                System.out.println("Tech dude: Good job mate, I knew we would make it!");
            }
            System.out.println("Congratulations, you escaped the space station. You won.");
            System.out.printf("You got %1.2f points\n", point);
        } 
        // If the player is killed by Zuul, print message
        else if (reason == "lose") {
            if (techDudeIsThere) {
                System.out.println("Tech dude: AAAARRGHGHRGHRHGRH (Death Gurgle)");
            }
            System.out.println("You were caught and killed by the monster. You lost.");
        } 
        // If player is killed by reactor, print message
        else if (reason == "timer"){
            if (techDudeIsThere) {
                System.out.println("Tech dude: Good run mate see ya on the other side");
            }
            System.out.println("The reactor overloaded and blew up the spacestation. You lost.");
        }
        // if the player dies due to low health
        else if (reason == "health") {
            if (techDudeIsThere) {
                System.out.println("Tech dude: Don't go into the light!");
            }
            System.out.println("You died due to extensive wound.");
        }
        // If player exits the game without losing or winning.
        else {
            System.out.println("You quit the current instance of the game.");
        }
    }

    // (£) This method calculates the points earned by the player
    private double pointCalculation() {
        // Set hero to the Hero character
        Hero hero = (Hero)(characters.get(0));
        // Declare usb
        USB usb;
        // Create hash set for points
        HashSet<String> pointSet = new HashSet<>();

        // Check for the 3 different USBs
        for (int i = 1; i < 4; i++) {
            // Set name of USB
            String name = "USB" + i;
            // Set usb to the specified USB item in the player's inventory
            usb = (USB)hero.getInventory().getItem(name);
            // If the specified USB is in the player's inventory...
            if (usb != null) {
                // If the specified USB has data stored on it...
                if (usb.getDataType() != null) {
                    // Print the data type collected
                    System.out.println("You got the " + usb.getDataType() + " data");
                    // Add value to pointSet
                    pointSet.add(usb.getDataType());
                }
            }
        }

        // Calculate earned points
        double point = (pointSet.size() * 5 + 5) * (1 + ((reactorActivated)?5:0) + (5 / (hero.getCharacterInitiative() + 5)));
        return point;
    }

    // This method tests if the game is won
    private boolean winTest() {
        // Set finished to false
        boolean finished = false;
        // If the player is in the escape pod, set finished to true and print win message
        if (characters.get(0).getCurrentRoom().equals(winConditionRoom)) {
            finished = true;
            printStopMessage("win");
        }
        // Return value of finished (true if player has won)
        return finished;
    }
    
    // This method tests if the player has lost
    private boolean lose() {
        // If player is in same room as Zuul, and player is current character, set sameRoom to true

        if (characters.get(0).getCurrentRoom().getHasCharacter("Zuul")
                && currentCharacter.equals(characters.get(0))) {
            sameRoom = true;
        }
        // If Zuul is in the same room as the player, and Zuul is the current character, set sameRoom and zuulHadTurn to true
        else if (characters.get(1).getCurrentRoom().getHasCharacter("Hero")
                && currentCharacter.equals(characters.get(1))) {
            sameRoom = true;
            zuulHadTurn = true;
        }
        // If current character is not player, and Zuul is not in player's current room, set sameRoom and zuulHadTurn to false
        else if (currentCharacter != characters.get(0) && !(characters.get(0).
                getCurrentRoom().getHasCharacter("Zuul"))) {
            sameRoom = false;
            zuulHadTurn = false;
        }
        // If player and Zuul are in the same room, and current character is player,
        // and zuulHadTurn is true, and player's initiative is less than Zuul's initiative + 10,
        // print lose message and return true
        if ((sameRoom && currentCharacter.equals(characters.get(0)) && zuulHadTurn)
                && characters.get(0).getCharacterInitiative() > characters.
                        get(1).getCharacterInitiative()) {
            printStopMessage("lose");
            return true;
        }
        // Else, return false
        else {
            return false;
        }
    }

    // This method tests if the player loses because of the reactor
    boolean timerLose() {
        // If player's initiative is greater than maxInitiative, print lose 
        // message based on "timer" and return true.
        if (characters.get(0).getCharacterInitiative() > maxInititative) {
            printStopMessage("timer");
            return true;
        }
        // Else, return false
        else {
            return false;
        }
    }
    
    private void techDudeMeetHero(){
        // Check if current character is tech dude and the current room contains hero and tech dude.
        if (this.currentCharacter.equals(this.characters.get(2)) &&
                (this.currentCharacter.getCurrentRoom().getHasCharacter("Hero")
                && this.currentCharacter.getCurrentRoom().getHasCharacter("TechDude"))) {
            // Set that tech dude has met the hero
            this.currentCharacter.meetHero(this.characters.get(0));
        }
    }
    
    // This method checks whether or not the player has died because of low health.
    private boolean healthTest(){
        // Check if current player is hero
        if (this.currentCharacter.equals(this.characters.get(0))) {
            // Set tempCharacter to current character
            Hero tempCharacter = (Hero)this.currentCharacter;
            // If player's health is less than or equal to zero, print "health"
            // lose message and return true.
            if (tempCharacter.getHealth() <= 0){
                printStopMessage("health");
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}
