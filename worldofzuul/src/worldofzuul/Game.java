package worldofzuul;

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
    private ArrayList<Character> characters = new ArrayList<>();
    private Character currentCharacter;
    private HashMap<String, Room> characterCurrentRooms = new HashMap<>();
    private Room winConditionRoom;

    // This constructor creates a Game object by creating a Parser and calling the createRooms method.
    public Game() {
        //Create all rooms by calling the createRooms method
        createRooms();
        createCharacter();
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
        hallwayReactorPhysics.setExit("physics lab", physicsLaboratory, false);

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
        characterCurrentRooms.put("Computer", computerRoom);
        characterCurrentRooms.put("Control", controlRoom);
        characterCurrentRooms.put("Dorm", dormitory);
        winConditionRoom = escapePod;
        dormitory.setHasZuul(true);
    }

    // This method creates the hero, monster, and tech dude and adds them to the array list of characters.
    private void createCharacter() {
        this.characters.add(new Hero(characterCurrentRooms.get("Computer")));
        this.characters.add(new Zuul(characterCurrentRooms.get("Dorm")));
        this.characters.add(new TechDude(characterCurrentRooms.get("Control")));
    }

    // This method plays the game
    public void play() {
        // Call the printWelcome method to show a brief introduction to the game
        printWelcome();

        // Check if the player is still playing
        boolean finished = false;
        // As long as game is not finished, get and process user commands
        while (!finished) {
            this.currentCharacter = this.chooseCharacter();
            Command command = parser.getCommand(this.currentCharacter);
            finished = processCommand(command);

            finished = winTest();
        }
        // Print goodbye message when user exits game.
        System.out.println("Thank you for playing.  Good bye.");
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
        System.out.println(characterCurrentRooms.get("Computer").getLongDescription());
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

        // Execute the command if the input matches a valid command
        // If command is "help" print the help message
        if (commandWord == CommandWord.HELP) {
            printHelp();
        } // If command is "go", call goRoom method
        else if (commandWord == CommandWord.GO) {
            this.currentCharacter.go(command);
//            goRoom(command);
        } // If command is "quit", change value of wantToQuit to true
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        else if (commandWord == commandWord.STAY) {
            this.currentCharacter.stay(command);
        }
        // Return boolean value (false = continue playing; true = quit game)
        return wantToQuit;
    }

    // This method prints a help message, including available commands
    private void printHelp() {
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
    private Character chooseCharacter() {
        Character currentCharacter = null;
        double minInitiative = Integer.MAX_VALUE;
        for (Character character : characters) {
            if (minInitiative > character.getCharacterInitiative()) {
                minInitiative = character.getCharacterInitiative();
                currentCharacter = character;
            }
        }

        return currentCharacter;
    }

    // This method quits the game
    private boolean quit(Command command) {
        // If the "quit" command has a second word, print error message and exit method.
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } // If the command has no second word, return "true", which causes the game to end.
        else {
            printStopMessage("quit");
            return true;
        }
    }

    private void printStopMessage(String reason) {

        if (reason == "win") {
            double point = pointCalculation();
            System.out.println("Congratulations, you won the game");
            System.out.printf("You got %f.2 points \n ", point);
        } else if (reason == "lose") {
            System.out.println("You died and therefore lost the game");
        } else {
            System.out.println("You quit the current instance of the game");
        }
    }

    //£
    private double pointCalculation() {

        Hero hero = (Hero) (characters.get(0));
        USB usb;
        HashSet<String> pointSet = new HashSet<>();

        for (int i = 1; i < 4; i++) {
            String name = "USB " + i;
            usb = (USB) hero.getInventory().getItem(name);
            if (usb != null) {
                if (usb.getDataType() != null) {
                    System.out.println("You got the " + usb.getDataType() + " data");
                    pointSet.add(usb.getDataType());
                }
            }
        }

        double point = (pointSet.size() * 5 + 5) * (1 + (5 / (hero.getCharacterInitiative() + 5)));
        return point;
    }

    private boolean winTest() {
        boolean finished = false;
        if (characters.get(0).getCurrentRoom().equals(winConditionRoom)) {
            finished = true;
            printStopMessage("congratulation you escaped the space station, you won");
        }
        return finished;
    }

    private boolean lose() {
        boolean sameRoom = false;
        boolean zuulHadTurn = false;

        if (characters.get(0).getCurrentRoom().equals(characters.get(1).
                getCurrentRoom()) && currentCharacter == characters.get(0)) {
            sameRoom = true;
        } else if (characters.get(0).getCurrentRoom().equals(characters.get(1).
                getCurrentRoom()) && currentCharacter == characters.get(1)) {
            sameRoom = true;
            zuulHadTurn = true;
        } else if (currentCharacter != characters.get(0) && !(characters.get(0).
                getCurrentRoom().equals(characters.get(1).getCurrentRoom()))) {
            sameRoom = false;
            zuulHadTurn = false;
        }

        if ((sameRoom && currentCharacter == characters.get(0) && zuulHadTurn == true)
                && characters.get(0).getCharacterInitiative() < (characters.
                get(1).getCharacterInitiative() + 10)) {
            printStopMessage("you were caught and killed by the monster, you lost");
            return true;
        } else {
            return false;
        }

    }

    boolean TimerLose() {
        double MaxInititative = Double.MAX_VALUE;
        if (characters.get(0).getCharacterInitiative() > MaxInititative) {
            printStopMessage("the reactor overloaded and blew up the spacestation, you lost");
            return true;
        } else {
            return false;
        }
    }
}
