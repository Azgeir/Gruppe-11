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
        Room biologyLaboratory, computerRoom, storage, medicalBay, dormitory, physicsLaboratory, dock, controlRoom, reactor, escapePod;

        // Initialize the rooms
        biologyLaboratory = new Room("in the biology laboratory", "biolab");
        computerRoom = new Room("in the computer room", "computer");
        storage = new Room("in the storage room", "storage");
        medicalBay = new Room("in the medical bay", "medbay");
        dormitory = new Room("in the dormitory", "dorm");
        physicsLaboratory = new Room("in the physics laboratory", "physicslab");
        dock = new Room("in the dock", "dock");
        controlRoom = new Room("in the control room", "control");
        reactor = new Room("near the reactor", "reactor");
        escapePod = new Room("in the escape pod", "pod");

        // Declare hallways between rooms
        Room hallwayStorageComputer, hallwayComputerBiology, hallwayBiologyControl, hallwayControlDock, hallwayDockPhysics, hallwayPhysicsDormitory, hallwayDormitoryMedical, hallwayMedicalStorage;

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
        Room hallwayReactorBiology, hallwayReactorControl, hallwayReactorDock, hallwayReactorPhysics, hallwayReactorDormitory, hallwayReactorMedical, hallwayReactorStorage, hallwayReactorComputer;

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

        hallwayBiologyControl.setExit("control", controlRoom, false);
        hallwayBiologyControl.setExit("biolab", biologyLaboratory, false);

        hallwayControlDock.setExit("control", controlRoom, false);
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
        hallwayReactorControl.setExit("control", controlRoom, false);

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

        controlRoom.setExit("biolab", hallwayBiologyControl, false);
        controlRoom.setExit("dock", hallwayControlDock, false);
        controlRoom.setExit("reactor", hallwayReactorControl, false);

        dock.setExit("control", hallwayControlDock, false);
        dock.setExit("physicslab", hallwayDockPhysics, false);
        dock.setExit("reactor", hallwayReactorDock, false);
        dock.setExit("pod", escapePod, false);

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
        
        // Add items to the inventory of the computer room
        computerRoom.getInventory().addItem(new USB(1));
        computerRoom.getInventory().addItem(new AcidVial(5));
        computerRoom.getInventory().addItem(new MedKit());
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
            // Check if current character is tech dude and the current room contains hero and tech dude.
            if (this.currentCharacter.equals(this.characters.get(2)) &&
                    (this.currentCharacter.getCurrentRoom().getHasCharacter("Hero")
                    && this.currentCharacter.getCurrentRoom().getHasCharacter("TechDude"))) {
                // Set that tech dude has met the hero
                this.currentCharacter.meetHero(this.characters.get(0));
            }
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
        }
    }

    // This method prints the welcome message.
    private void printWelcome() {
        // Print welcome message
        System.out.println();
        System.out.println("Welcome to Escape Pod!");
        System.out.println("\nYou are a Software engineer in a space station, and\n"
                + "the emergency alarm has just gone off. You must find\n"
                + "the other crew members, find out what is going on and\n"
                + "find the escape pod if necessary.\n");
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
            System.out.println("I don't know what you mean...");
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
                double zuulInitiativeChange = this.currentCharacter.use(command);
                Character zuul = this.characters.get(1);
                zuul.setCharacterInitiative(zuul.getCharacterInitiative()+zuulInitiativeChange);
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
                this.maxInititative = this.currentCharacter.activate(command);
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
        System.out.println("You are on a spacestation, conducting experiments for the good of the human race.");
        System.out.println("Something hit the spacestation, and you now have to save yourself and any possible survivors.");
        System.out.println("The escape pod is in the dock, and it is the only way to get off the spacestation");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
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
        // If the player won the game, print message specifying the total points earned
        if (reason == "win") {
            // Calculate earned points
            double point = pointCalculation();
            System.out.println("Congratulations, you escaped the space station. You won.");
            System.out.printf("You got %1.2f points\n ", point);
        } 
        // If the player is killed by Zuul, print message
        else if (reason == "lose") {
            System.out.println("You were caught and killed by the monster. You lost.");
        } 
        // If player is killed by reactor, print message
        else if (reason == "timer"){
            System.out.println("The reactor overloaded and blew up the spacestation. You lost.");
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
            String name = "USB " + i;
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
        double point = (pointSet.size() * 5 + 5) * (1 + (5 / (hero.getCharacterInitiative() + 5)));
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
                && characters.get(0).getCharacterInitiative() < (characters.
                        get(1).getCharacterInitiative() + 10)) {
            printStopMessage("lose");
            return true;
        }
        // Else, return false
        else {
            return false;
        }
    }

    boolean timerLose() {
        
        if (characters.get(0).getCharacterInitiative() > maxInititative) {
            printStopMessage("timer");
            return true;
        } else {
            return false;
        }
    }
}
