// The class is located in the logic layer.
package Logic;

// Imports:
import Acquaintance.IGame;
import Acquaintance.IHighscore;
import Acquaintance.IRoom;
import Acquaintance.IScore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class was part of the source code framework.
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

/**
 * This class runs the main functionality of the game. It implements the
 * interfaces IGame and Serializable. The class has default visibility as it is
 * only used within its package.
 */
class Game implements IGame, Serializable{

    /**
     * Data fields.
     * parser: instance of Parser which is used to get a command.
     * characters: ArrayList of characters in the game. ArrayList is chosen
     * because it allows us to know which character is chosen when the
     * initiative is the same.
     * currentCharacter: instance of Character that represents the current
     * character of the game.
     * rooms: HashMap of Room objects that represents the rooms of the game.
     * HashMap is faster than ArrayList of rooms.
     * winConditionRoom: instance of Room which represents the win condition
     * of the game.
     * maxInitiative: double value which is used to set a new max initiative
     * when the player activates the reactor.
     * reactorActivated: boolean value that indicates if the reactor has been
     * activated.
     * finished: boolean value that indicates if the game is finished.
     * timeSinceSpawn: double value that indicates the time since the last Zuul
     * spawned.
     * spawnTime: double value that indicates the time between spawns of Zuuls.
     * name: String that represents the name of the game.
     * messageClass: instance of LogicMessage which is used to send String
     * messages to the GUI to be shown to the player.
     */
    private Parser parser;
    private ArrayList<Character> characters = new ArrayList<>();
    private Character currentCharacter;
    private HashMap<String, Room> rooms;
    private Room winConditionRoom;
    private double maxInititative = Double.MAX_VALUE;
    private boolean reactorActivated = false;
    private boolean finished;
    private double timeSinceSpawn;
    private double spawnTime;
    private String name;
    private LogicMessage messageClass;

    /**
     * This no-arg constructor creates an instance of Game with one Zuul. It
     * calls another Game constructor using constructor chaining.
     */
    Game() {
        this(1);
    }
    
    /**
     * This constructor creates an instance of Game with the specified number
     * of Zuuls. It calls another Game constructor via constructor chaining.
     * 
     * @param numberOfZuul number of Zuuls at the beginning of the game.
     */
    Game(int numberOfZuul) {
        this(numberOfZuul,200);
    }
    
    /**
     * This constructor creates an instance of Game with the specified number of
     * Zuuls and spawn time. It calls another Game constructor using constructor
     * chaining.
     * 
     * @param numberOfZuul number of Zuuls at the beginning of the game.
     * @param spawnTime time before a new Zuul spawns.
     */
    Game(int numberOfZuul, double spawnTime) {
        this(numberOfZuul, spawnTime, "Derp");
    }
    
    /**
     * This constructor creates an instance of Game with the specified number of
     * Zuuls, spawn time, and name.
     * 
     * @param numberOfZuul number of Zuuls at the beginning of the game.
     * @param spawnTime time before a new Zuul spawns.
     * @param name name of the game.
     */
    Game(int numberOfZuul, double spawnTime, String name){
        this.messageClass = new LogicMessage();
        // Set finished to false as the game is not finished when it is started.
        this.finished = false;
        // Time since spawn is reset at the beginning of the game.
        this.timeSinceSpawn = 0;
        // Initialise spawn time with the given argument.
        this.spawnTime = spawnTime;
        // Create the rooms HashMap.
        this.rooms = new HashMap<>();
        // Set name to the given argument.
        this.name = name;
        //Create all rooms by calling the createRooms() method
        createRooms();
        // Create the characters by calling the createCharacter() method
        createCharacter(numberOfZuul);
        // Create a parser
        parser = new Parser();
        // Print welcome message.
        this.printWelcome();
        // Select current character
        this.currentCharacter = this.chooseCharacter();
        
    }

    /**
     * This method creates the rooms of the game. The method is private as it is
     * only called from within the class.
     */
    private void createRooms() {
        // Declare the rooms
        Room biologyLaboratory, computerRoom, storage, medicalBay, dormitory,
                physicsLaboratory, dock, controlRoom, reactor, escapePod;

        // Initialize the biology laboratory
        biologyLaboratory = new Room("in the biology laboratory", "Biolab",
            "You are in the biology laboratory. On the table to your right\n"
            + "there is a row of microscopes, and the shelf above contains a\n"
            + "collection of test tubes with liquids of different colours.\nIn "
            + "the corner of the room there is a computer. Maybe you'll be\n"
            + "able to save some of the research.\n", messageClass);

        // Initialise the computer room
        computerRoom = new Room("in the computer room", "Computer",
            "You are in the computer room. This is where you had your working\n"
            + "hours. You see several computers, chairs, USBs, and larger "
            + "storage\nunits. You also see your access card lying on the "
            + "desk.\n", messageClass);

        // Initialise the storage room
        storage = new Room("in the storage room", "Storage",
            "You are in the storage facility. Here you see several drawers and"
            + "\nshelves, containing everything from dried food to different "
            + "tools,\nmedkits and reseach compartments.\n", messageClass);

        // Initialise the medical bay
        medicalBay = new Room("in the medical bay", "Medbay",
            "You are in the medical bay. This is where you would get treated "
            + "and\ncontained if you fell sick or got minor injuries. There are"
            + " beds\nand several drawers with medkits, pills and syringes.\n"
            , messageClass);

        // Initialise the dormitory
        dormitory = new Room("in the dormitory", "Dorm",
            "You are in the dormitory. This is where you used to sleep and\n"
            + "relax with your colleagues. Sadly, this is also where the "
            + "monster\narrived. You see the corpses of your beloved colleagues"
            + " scattered\naround the room.\n", messageClass);

        // Initialise the physics laboratory
        physicsLaboratory = new Room("in the physics laboratory", "Physicslab",
            "You are in the physics laboratory. The room is filled with "
            + "various\nequipment. On a table nearby, you see a Helmholtz coil,"
            + " and on your\nright, there is a mass spectrometer. In the corner"
            + " of the room, you\nsee a computer. Maybe you'll be able to save"
            + " some of the research.\n", messageClass);

        // Initialise the dock
        dock = new Room("in the dock", "Dock", "You are in the dock. This is "
            + "where supply ships come and go and also \nthe only way off the "
            + "space station via the pod. However, the space station \nis "
            + "currently under quarantine and you don't know how to overwrite\n"
            + "it. Around you, you see tools for repairs, 3D printers and space"
            + " suits \n", messageClass);

        // Initialise the control room
        controlRoom = new Room("in the control room", "Control", "You are in "
            + "the control room. This is where information goes to and\nfrom "
            + "the space station. This is where you find the tech dude. He was"
            + " \ntrying to reestablish the connection to earth, but to no "
            + "avail.\nMaybe you could get surveilliance data back with you."
            , messageClass);

        // Initialise the reactor
        reactor = new Room("near the reactor", "Reactor", "You are in the "
            + "reactor room. The reactor is a very dense nuclear\nreactor. If "
            + "it were to melt down the space station would be annihilated.\n"
            + "You see some basic tools, some Geiger counters, and a coupple of"
            + "\nspace suits.\n", messageClass);

        // Initialise the escape pod
        escapePod = new Room("in the escape pod", "Pod", messageClass);

        // Declare hallways between rooms
        Room hallwayStorageComputer, hallwayComputerBiology,
            hallwayBiologyControl, hallwayControlDock, hallwayDockPhysics,
            hallwayPhysicsDormitory, hallwayDormitoryMedical,
            hallwayMedicalStorage;

        // Initialize the hallways between the outer rooms
        hallwayStorageComputer = new Room("in the hallway between the storage "
            + "and computer rooms", "StorageComputer", messageClass);
        hallwayComputerBiology = new Room("in the hallway between the computer "
            + "room and the biology laboratory","ComputerBiolab", messageClass);
        hallwayBiologyControl = new Room("in the hallway between the biology "
            + "laboratory and the control room", "BiolabControl", messageClass);
        hallwayControlDock = new Room("in the hallway between the control room "
            + "and the dock", "ControlDock", messageClass);
        hallwayDockPhysics = new Room("in the hallway between the dock and the "
            + "physics laboratory", "DockPhysicslab", messageClass);
        hallwayPhysicsDormitory = new Room("in the hallway between the physics "
            + "laboratory and the dormitory", "PhysicslabDorm", messageClass);
        hallwayDormitoryMedical = new Room("in the hallway between the "
            + "dormitory and the medical bay", "DormMedbay", messageClass);
        hallwayMedicalStorage = new Room("in the hallway between the medical "
            + "bay and the storage room", "MedbayStorage", messageClass);

        // Declare hallways connected to the reactor
        Room hallwayReactorBiology, hallwayReactorControl, hallwayReactorDock,
            hallwayReactorPhysics, hallwayReactorDormitory,
            hallwayReactorMedical, hallwayReactorStorage,
            hallwayReactorComputer;

        // Initialize the hallways connected to the reactor
        hallwayReactorBiology = new Room("in the hallway between the reactor "
            + "and the biology laboratory", "ReactorBiolab", messageClass);
        hallwayReactorControl = new Room("in the hallway between the reactor "
            + "and the control room", "ReactorControl", messageClass);
        hallwayReactorDock = new Room("in the hallway between the reactor and "
            + "the dock", "ReactorDock", messageClass);
        hallwayReactorPhysics = new Room("in the hallway between the reactor "
            + "and the physics laboratory", "ReactorPhysicslab", messageClass);
        hallwayReactorDormitory = new Room("in the hallway between the reactor "
            + "and the dormitory", "ReactorDorm", messageClass);
        hallwayReactorMedical = new Room("in the hallway between the reactor "
            + "and the medical bay", "ReactorMedbay", messageClass);
        hallwayReactorStorage = new Room("in the hallway between the reactor "
            + "and the storage room", "ReactorStorage", messageClass);
        hallwayReactorComputer = new Room("in the hallway between the reactor "
            + "and the computer room", "ReactorComputer", messageClass);

        // Set possible exits for hallways between rooms
        hallwayStorageComputer.setExit("Storage", storage, false);
        hallwayStorageComputer.setExit("Computer", computerRoom, false);

        hallwayComputerBiology.setExit("Computer", computerRoom, false);
        hallwayComputerBiology.setExit("Biolab", biologyLaboratory, false);

        hallwayBiologyControl.setExit("Control", controlRoom, true);
        hallwayBiologyControl.setExit("Biolab", biologyLaboratory, false);

        hallwayControlDock.setExit("Control", controlRoom, true);
        hallwayControlDock.setExit("Dock", dock, false);

        hallwayDockPhysics.setExit("Physicslab", physicsLaboratory, false);
        hallwayDockPhysics.setExit("Dock", dock, false);

        hallwayPhysicsDormitory.setExit("Physicslab", physicsLaboratory, false);
        hallwayPhysicsDormitory.setExit("Dorm", dormitory, false);

        hallwayDormitoryMedical.setExit("Medbay", medicalBay, false);
        hallwayDormitoryMedical.setExit("Dorm", dormitory, false);

        hallwayMedicalStorage.setExit("Medbay", medicalBay, false);
        hallwayMedicalStorage.setExit("Storage", storage, false);

        // Set possible exits for hallways from the reactor
        hallwayReactorBiology.setExit("Reactor", reactor, false);
        hallwayReactorBiology.setExit("Biolab", biologyLaboratory, false);

        hallwayReactorControl.setExit("Reactor", reactor, false);
        hallwayReactorControl.setExit("Control", controlRoom, true);

        hallwayReactorDock.setExit("Reactor", reactor, false);
        hallwayReactorDock.setExit("Dock", dock, false);

        hallwayReactorPhysics.setExit("Reactor", reactor, false);
        hallwayReactorPhysics.setExit("Physicslab", physicsLaboratory, false);

        hallwayReactorDormitory.setExit("Reactor", reactor, false);
        hallwayReactorDormitory.setExit("Dorm", dormitory, false);

        hallwayReactorMedical.setExit("Reactor", reactor, false);
        hallwayReactorMedical.setExit("Medbay", medicalBay, false);

        hallwayReactorStorage.setExit("Reactor", reactor, false);
        hallwayReactorStorage.setExit("Storage", storage, false);

        hallwayReactorComputer.setExit("Reactor", reactor, false);
        hallwayReactorComputer.setExit("Computer", computerRoom, false);

        // Set the possible exits for each room
        biologyLaboratory.setExit("Computer", hallwayComputerBiology, false);
        biologyLaboratory.setExit("Control", hallwayBiologyControl, false);
        biologyLaboratory.setExit("Reactor", hallwayReactorBiology, false);

        controlRoom.setExit("Biolab", hallwayBiologyControl, true);
        controlRoom.setExit("Dock", hallwayControlDock, true);
        controlRoom.setExit("Reactor", hallwayReactorControl, true);

        dock.setExit("Control", hallwayControlDock, false);
        dock.setExit("Physicslab", hallwayDockPhysics, false);
        dock.setExit("Reactor", hallwayReactorDock, false);
        dock.setExit("Pod", escapePod, true);

        physicsLaboratory.setExit("Dock", hallwayDockPhysics, false);
        physicsLaboratory.setExit("Dorm", hallwayPhysicsDormitory, false);
        physicsLaboratory.setExit("Reactor", hallwayReactorPhysics, false);

        dormitory.setExit("Physicslab", hallwayPhysicsDormitory, false);
        dormitory.setExit("Medbay", hallwayDormitoryMedical, false);
        dormitory.setExit("Reactor", hallwayReactorDormitory, false);

        medicalBay.setExit("Dorm", hallwayDormitoryMedical, false);
        medicalBay.setExit("Storage", hallwayMedicalStorage, false);
        medicalBay.setExit("Reactor", hallwayReactorMedical, false);

        storage.setExit("Medbay", hallwayMedicalStorage, false);
        storage.setExit("Computer", hallwayStorageComputer, false);
        storage.setExit("Reactor", hallwayReactorStorage, false);

        computerRoom.setExit("Storage", hallwayStorageComputer, false);
        computerRoom.setExit("Biolab", hallwayComputerBiology, false);
        computerRoom.setExit("Reactor", hallwayReactorComputer, false);

        escapePod.setExit("Dock", dock, false);

        // Set the exits for the reactor room
        reactor.setExit("Computer", hallwayReactorComputer, false);
        reactor.setExit("Biolab", hallwayReactorBiology, false);
        reactor.setExit("Control", hallwayReactorControl, false);
        reactor.setExit("Dock", hallwayReactorDock, false);
        reactor.setExit("Physicslab", hallwayReactorPhysics, false);
        reactor.setExit("Dorm", hallwayReactorDormitory, false);
        reactor.setExit("Medbay", hallwayReactorMedical, false);
        reactor.setExit("Storage", hallwayReactorStorage, false);

        // Add rooms to HashMap of rooms.
        rooms.put("Computer", computerRoom);
        rooms.put("Control", controlRoom);
        rooms.put("Dorm", dormitory);
        rooms.put("Biolab", biologyLaboratory);
        rooms.put("Storage", storage);
        rooms.put("Medbay", medicalBay);
        rooms.put("Physicslab", physicsLaboratory);
        rooms.put("Dock", dock);
        rooms.put("Control", controlRoom);
        rooms.put("Reactor", reactor);
        rooms.put("ReactorBiolab", hallwayReactorBiology);
        rooms.put("ReactorControl", hallwayReactorControl);
        rooms.put("ReactorDock", hallwayReactorDock);
        rooms.put("ReactorPhysicslab", hallwayReactorPhysics);
        rooms.put("ReactorDorm", hallwayReactorDormitory);
        rooms.put("ReactorMedbay", hallwayReactorMedical);
        rooms.put("ReactorStorage", hallwayReactorStorage);
        rooms.put("ReactorComputer", hallwayReactorComputer);
        rooms.put("StorageComputer",hallwayStorageComputer);
        rooms.put("ComputerBiolab",hallwayComputerBiology);
        rooms.put("BiolabControl",hallwayBiologyControl);
        rooms.put("ControlDock",hallwayControlDock);
        rooms.put("DockPhysicslab",hallwayDockPhysics);
        rooms.put("PhysicslabDorm",hallwayPhysicsDormitory);
        rooms.put("DormMedbay",hallwayDormitoryMedical);
        rooms.put("MedbayStorage",hallwayMedicalStorage);
        rooms.put("Pod", escapePod);

        // Set the value of the win condition
        winConditionRoom = escapePod;

        /*
        Add items to the inventory of the rooms by calling the fillRoom()
        method.
        */
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

    /**
     * This method initializes the inventory of the specified room. This method
     * is private as it is only called from within the class.
     * 
     * @param room instance of Room that represents the room whose inventory
     * is to be filled.
     */
    private void fillRoom(Room room) {
        // Fill the room depending on the name of the room.
        switch (room.getName()) {
            // Fill biology laboratory.
            case "Biolab":
                room.getInventory().addItem(new Item(10, "notebook", 
                    "smash it down on the floor in pure anger.", messageClass));
                room.getInventory().addItem(new Item(50, "microscope",
                    messageClass), 3);
                room.getInventory().addItem(new Item(1000, 
                    "DNA-sequencing-machine", messageClass));
                room.getInventory().addItem(new Item(150, "incubator",
                    messageClass), 2);
                room.getInventory().addItem(new Item(500, "refrigerator",
                    messageClass), 2);
                room.getInventory().addItem(new Item(20, "animal-cell-model",
                    messageClass));
                room.getInventory().addItem(new Item(20, "plant-cell-model",
                    messageClass));
                room.getInventory().addItem(new Item(20, "water-sample",
                    "piss in it to show dominance.", messageClass));
                room.getInventory().addItem(new Item(20, "air-sample",
                    messageClass));
                room.getInventory().addItem(new Item(10,
                    "methylene-blue-stain", messageClass), 10);
                room.getInventory().addItem(new Item(10,
                    "Gram's-iodine-solution", messageClass), 10);
                room.getInventory().addItem(new Item(5, "pipette", messageClass)
                    , 20);
                room.getInventory().addItem(new Item(5, "microscope-slide",
                    messageClass), 20);
                room.getInventory().addItem(new Item(10, "cell-lysis-solution",
                    messageClass), 10);
                room.getInventory().addItem(new Item(10,
                    "DNA-precipitate-solution"), 10);
                room.getInventory().addItem(new Item(5, "test-tube",
                    messageClass), 20);
                room.getInventory().addItem(new Item(400, "centrifuge",
                    messageClass), 2);
                room.getInventory().addItem(new Item(99, "cat",
                    "pet it. It scratces your hand.", messageClass));
                room.getInventory().addItem(new AcidVial(4, 25,
                    this.messageClass), 10);
                break;
            // Fill computer room.
            case "Computer":
                room.getInventory().addItem(new USB(3, messageClass));
                room.getInventory().addItem(new USB(2, messageClass));
                room.getInventory().addItem(new USB(1, messageClass));
                room.getInventory().addItem(new Item(150, "computer-monitor",
                    messageClass), 10);
                room.getInventory().addItem(new Item(10, "computer-mouse",
                    messageClass), 10);
                room.getInventory().addItem(new Item(20, "keyboard",
                    "write code, but it dosen't work, so you smash it into "
                    + "pieces.", messageClass), 10);
                room.getInventory().addItem(new AccessCard(this.messageClass));
                break;
            // Fill storage room.
            case "Storage":
                room.getInventory().addItem(new AcidVial(4, 25,
                    this.messageClass), 5);
                room.getInventory().addItem(new MedKit(messageClass), 10);
                room.getInventory().addItem(new Item(400, "box", messageClass),
                    30);
                room.getInventory().addItem(new Item(600, "bed", messageClass),
                    15);
                room.getInventory().addItem(new Item(75, "electronics",
                    messageClass), 25);
                room.getInventory().addItem(new Item(1350, "solarpanel",
                    messageClass), 15);
                room.getInventory().addItem(new Item(20, "hammer",
                    "hit yourself in hope of convincing yourself that this is "
                    + "not reality.", messageClass), 13); 
                room.getInventory().addItem(new Item(20, "saw"), 9);
                room.getInventory().addItem(new Item(20, "crowbar", "smash some"
                    + " stuff and pretend that you are Gordon Freeman. This "
                    + "isn't Half Life 3, you dumbass!", messageClass), 7);
                room.getInventory().addItem(new Item(20, "plasma-saw",
                    messageClass), 6);
                room.getInventory().addItem(new Item(30, "nailgun", "shoot all"
                    + " over the place like a stormtrooper.", messageClass), 8);
                room.getInventory().addItem(new AccessCard(this.messageClass));
                break;
            // Fill medical bay.
            case "Medbay":
                room.getInventory().addItem(new AcidVial(4, 25,
                    this.messageClass), 7);
                room.getInventory().addItem(new MedKit(messageClass), 10);
                room.getInventory().addItem(new Item(5, "syringe", "stick it at"
                    + " yourself to see, if you end up tripping balls.",
                    messageClass), 17);
                room.getInventory().addItem(new Item(2, "pill", "try to aquire "
                    + "superpowers, but you end up having extreme explosive "
                    + "diaherra being shot out of your ass as a cannon.",
                    messageClass), 29);
                room.getInventory().addItem(new Item(6, "glass", messageClass),
                    49);
                room.getInventory().addItem(new Item(20, "book", "read up on "
                    + "how to fart like a machine gun.", messageClass), 50);
                room.getInventory().addItem(new Item(20, "coat", messageClass),
                    19);
                room.getInventory().addItem(new Item(400, "desk", messageClass),
                    8);
                room.getInventory().addItem(new Item(100, "display-skeleton",
                    messageClass), 5);
                break;
            // Fill dormitory.
            case "Dorm":
                room.getInventory().addItem(new Item(20, "cake", "you try to "
                    + "eat the cake but remember that the cake is a lie",
                    messageClass));
                room.getInventory().addItem(new Item(10, "dehydrated-food",
                    messageClass), 50);
                room.getInventory().addItem(new Item(600, "bed", messageClass),
                    20);
                room.getInventory().addItem(new Item(10, "pillow", "remind "
                    + "yourself that Mommy is not going to save you.",
                    messageClass), 20);
                room.getInventory().addItem(new Item(500, "duvet", "clean the "
                    + "stain where you pissed yourself at.", messageClass), 20);
                room.getInventory().addItem(new Item(200, "corpse",
                    messageClass), 10);
                room.getInventory().addItem(new Item(600, "couch",
                    messageClass));
                room.getInventory().addItem(new Item(200, "table",
                    messageClass), 2);
                room.getInventory().addItem(new Item(600, "bookcase",
                    messageClass));
                room.getInventory().addItem(new Item(10, "book", "read up on "
                    + "how to fart like a machine gun.", messageClass), 100);
                room.getInventory().addItem(new Item(600, "desk", messageClass),
                    2);
                room.getInventory().addItem(new Item(75, "chair", "sit down on "
                    + "it to contemplate about your life choices at the moment."
                    + " Honestly, don't you have better things to do!",
                    messageClass), 6);
                break;
            // Fill physics laboratory.
            case "Physicslab":
                room.getInventory().addItem(new AcidVial(4, 25,
                    this.messageClass), 7);
                room.getInventory().addItem(new MedKit(messageClass), 10);
                room.getInventory().addItem(new Item(5, "test-tube",
                    messageClass), 20);
                room.getInventory().addItem(new Item(20, "coat", messageClass),
                    10);
                room.getInventory().addItem(new Item(15, "knife", messageClass),
                    10);
                room.getInventory().addItem(new Item(5, "syringe", "stick it at"
                    + " yourself to see, if you become one with the Matrix.",
                    messageClass), 15);
                room.getInventory().addItem(new Item(400, "desk", messageClass),
                    2);
                room.getInventory().addItem(new Item(75, "chair", "sit down on "
                    + "it to contemplate about your life choices at the moment."
                    + " Seriosly, don't you not have better things to do!",
                    messageClass), 10);
                room.getInventory().addItem(new Item(175, "computer",
                    messageClass), 5);
                room.getInventory().addItem(new Item(200, "table",
                    messageClass), 4);
                room.getInventory().addItem(new Item(1000, "quantum-equipment"),
                    29);
                room.getInventory().addItem(new Item(5, "test-tube",
                    messageClass), 20);
                room.getInventory().addItem(new Item(10, "funny-chemical",
                    "sniff at it, then lick at it and then scream 'LEEROY "
                    + "JENKINS'.", messageClass), 25);
                break;
            // Fill dock.
            case "Dock":
                room.getInventory().addItem(new Item(1500, "crate",
                    messageClass), 30);
                room.getInventory().addItem(new Item(500, "fuel-station",
                    messageClass), 1);
                room.getInventory().addItem(new Item(400, "barrel",
                    messageClass), 40);
                room.getInventory().addItem(new Item(50, "baggage",
                    messageClass), 10);
                room.getInventory().addItem(new Item(150, "computer-moniter",
                    messageClass), 15);
                room.getInventory().addItem(new Item(100, "spacesuit",
                    messageClass), 10);
                room.getInventory().addItem(new Item(200, "corpse",
                    messageClass), 2);
                room.getInventory().addItem(new Item(500, "3D-printer", "scan "
                    + "your ass and print it for science.", messageClass), 1);
                break;
            // Fill control room.
            case "Control":
                room.getInventory().addItem(new MedKit(), 10);
                room.getInventory().addItem(new Item(175, "computer",
                    messageClass), 5);
                room.getInventory().addItem(new Item(150, "computer-monitor",
                    messageClass), 15);
                room.getInventory().addItem(new Item(20, "keyboard",
                    "write code, but it dosen't work, so you smash it into "
                    + "pieces.", messageClass), 15);
                room.getInventory().addItem(new Item(10, "screwdriver",
                    messageClass), 6);
                room.getInventory().addItem(new Item(20, "hammer",
                    messageClass), 4);
                room.getInventory().addItem(new Item(1, "paper", "fold a paper "
                    + "plane and make StarWars sounds.", messageClass), 20);
                break;
            // Fill reactor.
            case "Reactor":
                room.getInventory().addItem(new Item(150, "computer-monitor",
                    messageClass), 10);
                room.getInventory().addItem(new Item(175, "computer",
                    messageClass), 4);
                room.getInventory().addItem(new Item(10, "screwdriver",
                    messageClass), 8);
                room.getInventory().addItem(new Item(1500, "crate",
                    messageClass), 10);
                room.getInventory().addItem(new Item(20, "Geiger-counter",
                    messageClass), 2);
                room.getInventory().addItem(new Item(100, "spacesuit",
                    messageClass), 2);
                break;
            // Fill ecape pod.
            case "Pod":
                room.getInventory().addItem(new MedKit(messageClass));
                room.getInventory().addItem(new Item(10, "dehydrated-food",
                    messageClass), 5);
                room.getInventory().addItem(new Item(20, "hammer",
                    messageClass), 2);
                room.getInventory().addItem(new Item(10, "screwdriver",
                    messageClass), 2);
                break;
            default:
                break;
        }
    }

    /**
     * This method creates the hero, Zuul, and tech dude and adds them to the
     * ArrayList of characters. The method is private as it is only called from
     * within the class.
     * 
     * @param numberOfZuul number of Zuuls at the beginning of the game.
     */
    private void createCharacter(int numberOfZuul) {
        /*
        Create instance of Hero and add this to the ArrayList of characters.
        Hero starts in the computer room.
        */
        this.characters.add(new Hero(10, 100, this.rooms.get("Computer"),
            "Hero", messageClass));
        /*
        Create the specified number of Zuuls and add them to the ArrayList of
        characters. The Zuuls start in the dormitory, and have a speed factor of
        1.15.
        */
        for (int i = 0; i < numberOfZuul; i++) {
            this.characters.add(new Zuul(this.rooms.get("Dorm"), "Zuul", 1.15,
                messageClass));
        }
        /*
        Create an instance of TechDude and add him to the ArrayList of
        characters. Tech dude starts in the control room, and has a speed factor
        of 0.5.
        */
        this.characters.add(new TechDude(this.rooms.get("Control"), "TechDude",
            0.5, messageClass));
    }
    
    /**
     * This method is used to add new Zuuls during the game. The method checks
     * the value of the time since spawn to determine if its time to add a new
     * Zuul.
     * 
     * @param initiativeBefore double value that represents the initiative of
     * the character who has just performed their command.
     */
    private void timeAddedZuul(double initiativeBefore) {
        /*
        Increase the value of time since spawn by the initiative difference.
        */
        this.timeSinceSpawn +=
            this.currentCharacter.getCharacterInitiative() - initiativeBefore;
        /*
        If the time since spawn is greater than spawn time, add a new Zuul in a
        random room. Time since spawn is reset to allow for more spawning.
        */
        if (this.timeSinceSpawn > this.spawnTime) {
            this.characters.add(new Zuul(this.randomZuulSpawnRoom(), "Zuul", 
                1.15, this.currentCharacter.getCharacterInitiative(), 
                messageClass));
            this.timeSinceSpawn -= this.spawnTime;
        }
    }

    /**
     * This method plays the game.
     * 
     * @param GUICommand String which represents a command from the GUI.
     */
    void play(String GUICommand) {
        /*
        This do-while loop is used to execute commands and check whether the
        game has ended. The loop initially processes the command from the GUI.
        It then executes the commands for the other non-hero characters before
        the hero gets to execute a new command.
        */
        do {
            // Get command from parser. The command is based on the GUI String.
            Command command = this.parser.getCommand(this.currentCharacter,
                GUICommand);
            
            /*
            Record the character initiative before performing the command. This
            is used when adding additional Zuuls to the game.
            */
            double initiativeBefore =
                this.currentCharacter.getCharacterInitiative();
            
            // Process the command by calling the processCommand() method
            this.finished = processCommand(command);
            
            /*
            If the current character is Hero, check if its time to add a new
            Zuul.
            */
            if (this.currentCharacter instanceof Hero) {
                this.timeAddedZuul(initiativeBefore);
                /*
                Check if the player has lost the game because of the reactor.
                This test is before the win test because it is not possible to
                win by entering the escape pod as the space station blows up.
                */
                if (!this.finished) {
                    this.finished = timerLose();
                }
                // Check if player won game
                if (!this.finished) {
                    this.finished = winTest();
                }
                // Check if the player loses because of health problems.
                if (!this.finished) {
                    this.finished = healthTest();
                }
            }
            
            // Choose the next character based on the characters' initiatives.
            this.currentCharacter = this.chooseCharacter();
            
        } while(!(this.currentCharacter instanceof Hero) && !this.finished);
    }

    /**
     * This method prints the welcome message. The method is private because it
     * is only called from within the class.
     */
    private void printWelcome() {
        // Create String message that introduces the game.
        this.messageClass.appendMessage("\nWelcome to Escape Pod!\n\nYou are a"
                + " software engineer in a space station. \nThe emergency alarm"
                + " has just gone off, and the station is under \nquarantine. "
                + "You must find items and other survivors and escape \nthe "
                + "station through the escape pod before you are caught by what"
                + " \nis ravaging the station.\n\nYou suddenly hear a rumbling"
                + " voice emanating from everywhere:\n\n\"We are Zuul, "
                + "devourers of worlds.\"\n\nPress 'Help' for more information "
                + "about controls and the game.\n\n");
        // Description of current room of the player, including available exits.
        this.messageClass.appendMessage((this.rooms.get("Computer").
            getLongDescription() + "\n"));
    }

    /**
     * This method processes the command of the player.
     * 
     * @param command instance of Command which represents the command to be
     * processed.
     * 
     * @return true if the game is finished; else it returns false.
     */
    private boolean processCommand(Command command) {
        // Assume the player wants to continue playing.
        boolean wantToQuit = false;

        /*
        Create instance of CommandWord using the command word of the specified
        command.
        */
        CommandWord commandWord = command.getCommandWord();

        /*
        Check if the input equals any of the defined commands and print an
        "error" if it does not.
        */
        if (command.isUnknown()) {
            messageClass.appendMessage("I don't know what you mean...\nTyping "
                + "'help' will give you the valid commands.");
            return false;
        }

        /*
        Execute the command if the input matches a valid command. The execution
        is determined by the value of the command word.
        */
        if (commandWord != null) {
            switch (commandWord) {
                /*
                If the command word is "go", "stay", "pickup", "drop", "look", 
                "peek", "lock", or "unlock", call performCommand() on the
                current character.
                */
                case GO:
                case STAY:
                case PICKUP:
                case DROP:
                case LOOK:
                case PEEK:
                case LOCK:
                case UNLOCK:
                    this.currentCharacter.performCommand(command);
                    break;
                // If command is "help", print help message.
                case HELP:
                    printHelp();
                    break;
                /*
                If command is "quit", call the quit() method, which returns
                true, if the player wants to quit.
                */
                case QUIT:
                    wantToQuit = quit(command);
                    break;
                /*
                If command is "use", call the performCommand() method on the
                current character and change Zuul's initiative.
                */
                case USE:
                    /*
                    If Zuul's initiative is affected by the action, the
                    performCommand() method returns a value.
                    */
                    double zuulInitiativeReduction =
                        this.currentCharacter.performCommand(command);
                    // Traverse all characters.
                    for (Character character : characters) {
                        /*
                        If Zuul is in the same room as the player, increase its
                        initiative.
                        */
                        if ((character instanceof Zuul)
                            && character.getCurrentRoom().equals
                            (this.currentCharacter.getCurrentRoom())) {
                            character.setCharacterInitiative
                                (character.getCharacterInitiative()
                                + zuulInitiativeReduction);
                        }
                    }
                    break;
                /*
                If command is "activate", set MaxInitiative to the return value
                of the performCommand() method.
                */
                case ACTIVATE:
                    double newInitiative = this.currentCharacter.
                        performCommand(command);
                    if(this.currentCharacter.getMessage() != null) {
                        this.reactorActivated = true;
                        this.currentCharacter.clearMessage();
                        this.maxInititative = newInitiative;
                    }
                    break;
                /*
                If the command is "talk",
                */
                case TALK:
                    /*
                    If the tech dude is in the same room as the player, the
                    player talks to the tech dude.
                    */
                    if (currentCharacter.getCurrentRoom().hasCharacter(
                        "TechDude")) {
                        // Get the player.
                        Hero tempHero = null;
                        for (Character character : characters) {
                            if (character instanceof Hero) {
                                tempHero = (Hero)character;
                            }
                        }
                        // Perform talk.
                        for (Character character : characters) {
                            // Find tech dude.
                            if (character instanceof TechDude){
                                TechDude techDude = (TechDude)character;

                                /*
                                It is recorded whether the tech dude follows the
                                hero before and after the conversation.
                                */
                                Boolean isFollowingBefore = techDude.
                                    followsHero();
                                character.performCommand(command);
                                boolean isFollowingAfter = techDude.
                                    followsHero();
                                tempHero.setPreviousCommand(command.
                                    getCommandWord().toString());
                                    
                                tempHero.setTalking(techDude.wantsToTalk());
                                
                                /*
                                The player is informed if the tech dude stops
                                following the hero.
                                */
                                if (!isFollowingAfter && isFollowingBefore) {
                                    Character hero = null;
                                    
                                    techDude.setFollowsHero(hero, false);
                                    messageClass.appendMessage("Tech dude no "
                                        + "longer follows you.");
                                        
                                }
                                /*
                                The player is informed, if the tech dude starts
                                to follow him.
                                */
                                else if (isFollowingAfter
                                    && !isFollowingBefore) {
                                    Character hero = null;
                                    for (Character character1 : characters) {
                                        if (character1 instanceof Hero) {
                                            hero = character1;
                                            break;
                                        }
                                    }
                                        
                                    messageClass.appendMessage("Tech dude is "
                                        + "now following you");
                                    techDude.setFollowsHero(hero, true);
                                }
                                else {}
                                
                                break;
                            }
                        }    
                    }
                    /*
                    If tech dude is not present, the player talks to themselves.
                    */
                    else {
                        messageClass.appendMessage("You talk to yourself as you"
                            + " begin to question your sanity.");
                    }
                    
                    /*
                    The character initiative is set. The value depends on
                    whether or not the current character is an instance of Hero.
                    */
                    if (currentCharacter instanceof Hero) {
                        this.currentCharacter.setCharacterInitiative(
                            this.currentCharacter.getCharacterInitiative()
                            + 1 * this.currentCharacter.getSpeedFactor());
                    }
                    else {
                        this.currentCharacter.setCharacterInitiative(
                            this.currentCharacter.getCharacterInitiative()
                            + 10 * this.currentCharacter.getSpeedFactor());
                    }
                    
                    break;
                /*
                If the command is "kill", the command is performed, and the
                game ends.
                */
                case KILL:
                    this.currentCharacter.performCommand(command);
                    wantToQuit = true;
                    this.printStopMessage(this.currentCharacter.getMessage());
                    this.currentCharacter.clearMessage();
                // If command does not match any of the options, break.
                default:
                    break;
            }
        }
        // Return boolean value (false = continue playing; true = quit game)
        return wantToQuit;
    }

    /**
     * This method prints a help message, including a description of the 
     * available commands.
     */
    private void printHelp() {
        messageClass.appendMessage("Your goal is to survive and reach the "
            + "escape pod. You may find and rescue other crew members. There "
            + "will also be items that can help you on your way.\n\nThe "
            + "following command buttons are used to direct your actions:\n\n"
            + "Pickup: select an item from the dropdown above and press Pickup "
            + "to pickup the item (use the dropdown above Activate to specify "
            + "the number of items to pickup).\nDrop: select an item from the "
            + "dropdown above and press Drop to drop the item (use the dropdown"
            + " above Activate to specify the number of items to drop).\nUse: "
            + "select an item from the dropdown above and press Use to use the "
            + "item.\nActivate: press to activate specific objects.\nSave: "
            + "press to save the current game.\nLook: press to get information "
            + "about the room.\nQuit: press to end the game.\nTalk: press to "
            + "initiate a conversation.\nStay: press to skip a turn.\nHelp: "
            + "press to read this message.\nPeek: select a direction from the "
            + "dropdown to the right and press Peek to peek around you and in "
            + "the specified direction.\nGo: select a direction from the "
            + "dropdown below and press Go to go in that direction.\nLock: "
            + "select a direction from the dropdown to the left and press Lock "
            + "to lock the door.\nUnlock: select a direction from the dropdown "
            + "above and press Unlock to unlock the door.\n");
    }

    /**
     * This method iterates over the different characters to determine whose
     * turn it is. If two characters have the same initiative, the breaker is
     * who is defined first in the ArrayList.
     * 
     * @return an instance of Character, which represents the character whose
     * turn it is.
     */
    private Character chooseCharacter() {
        // Set current character to null
        currentCharacter = null;
        // Set minInitiative to maximum integer value
        double minInitiative = Double.MAX_VALUE;
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

    /**
     * This method quits the game.
     * 
     * @param command a Command object which represents the "quit" command.
     * 
     * @return true if the game is quitted; else it returns false.
     */
    private boolean quit(Command command) {
        /*
        If the "quit" command has a second word, print error message and exit
        method.
        */
        if (command.hasSecondWord()) {
            messageClass.appendMessage("Quit what?");
            return false;
        }
        /*
        If the command has no second word, return "true", which causes the game
        to end.
        */
        else {
            printStopMessage("quit");
            return true;
        }
    }

    /**
     * This method prints a stop message depending on the reason string.
     * 
     * @param reason String that indicates the reason why the game was ended.
     */
    private void printStopMessage(String reason) {
        /*
        Check if the tech dude is following the hero. The stop message includes
        the tech dude if he is following the hero.
        */
        boolean techDudeIsThere = false;
        
        for (Character character : characters) {
            if (character instanceof TechDude) {
                TechDude temp = (TechDude)character;
                if (temp.followsHero()) {
                    techDudeIsThere = true;
                }
            }
        }
                
        /*
        If the player won the game, print message specifying the total points
        earned.
        */
        switch (reason){
            /*
            If the player has won, a win message is printed and the total points
            are calculated.
            */
            case "win":
                // Calculate earned points.
                double point = pointCalculation();
                // Print message for win with tech dude.
                if (techDudeIsThere) {
                    messageClass.appendMessage("Tech dude: Good job, mate! I "
                        + "knew we would make it!");
                }
                // Print generic win message.
                messageClass.appendMessage("Congratulations, you escaped the "
                    + "space station. You won.");
                // Get point with 2 digits after the decimal point.
                point = ((int)(point * 100)) / 100.0;
                // Print points obtained.
                messageClass.appendMessage("You got " + point +" points.\n");
                break;
            /*
            If the player dies, trying to run away from the Zuul.
            */
            case "lose":
                // Include tech dude if present.
                if (techDudeIsThere) {
                    messageClass.appendMessage("Tech dude: AAAARRGHGHRGHRHGRH "
                        + "(Death Gurgle)");
                }
                messageClass.appendMessage("The Zuul mauled you in the back "
                    + "while you were running away like a coward. You lost.");
                break;
            // Random lose message #1
            case "lose1": 
                // Include tech dude if present.
                if (techDudeIsThere) {
                    messageClass.appendMessage("Tech dude: AAAARRGHGHRGHRHGRH "
                        + "(Death Gurgle)");
                }
                messageClass.appendMessage("The Zuul rips your head off and "
                    + "kicks it across the room, cheering like it just won the "
                    + "world cup.");
                break;
            // Random lose message #2
            case "lose2": 
                // Include tech dude if present.
                if (techDudeIsThere) {
                    messageClass.appendMessage("Tech dude: AAAARRGHGHRGHRHGRH "
                        + "(Death Gurgle)");
                }
                messageClass.appendMessage("The Zuul rips out your throat, "
                    + "sticks its claws up your ass, and prances you around "
                    + "like a ventriloquist puppet saying: \"Look at me! I'm a "
                    + "scared little human! I can code!\"");
                break;
            // Random lose message #3
            case "lose3":
                // Include tech dude if present.
                if (techDudeIsThere) {
                    messageClass.appendMessage("Tech dude: AAAARRGHGHRGHRHGRH "
                        + "(Death Gurgle)");
                }
                messageClass.appendMessage("The Zuul rips you in half and then "
                    + "starts teabagging your face that's frozen in horrer by "
                    + "sight of the Zuul's hairy ass.");
                break;
            // If player dies because reactor blows up space station.
            case "timer":
                // Include tech dude if present.
                if (techDudeIsThere) {
                    messageClass.appendMessage("Tech dude: Good run, mate! See "
                        + "ya on the other side.");
                }
                messageClass.appendMessage("The reactor overloaded and blew up "
                    + "the space station. You lost.");
                break;
            // If player dies because of excessive use of acid vials.
            case "health":
                // Include tech dude if present.
                if (techDudeIsThere) {
                    messageClass.appendMessage("Tech dude: Don't go into the "
                        + "light!");
                }
                messageClass.appendMessage("You died due to extensive wounds.");
                break;
            /*
            This message is not currently used. The idea is that at some point,
            it is possible to die from using an item in the inventory. This
            message would then be printed.
            */
            case "derp":
                messageClass.appendMessage("You committed suicide because you "
                    + "are too weak to kill anything else.\nYou pussed out like"
                    + " a bitch.");
                break;
            // If player exits the game without losing or winning.
            default:
                messageClass.appendMessage("You committed suicide.\nYou pussed "
                    + "out like a bitch.");
        }
    }

    /**
     * This method calculates the points earned by the player.
     * 
     * @return double value that indicates the total points obtained by the
     * player.
     */
    private double pointCalculation() {
        // Set hero to the Hero character
        Hero hero = (Hero)(currentCharacter);
        // Declare usb
        USB usb;
        // Create HashSet for points
        HashSet<String> pointSet = new HashSet<>();

        // Check for the 3 different USBs
        for (int i = 1; i < 4; i++) {
            // Set name of USB
            String name = "USB" + i;
            // Set usb to the specified USB item in the player's inventory
            usb = (USB)(hero.getInventory().getItem(name));
            // If the specified USB is in the player's inventory...
            if (usb != null) {
                // If the specified USB has data stored on it...
                if (usb.getDataType() != null) {
                    // Print the data type collected
                    messageClass.appendMessage("You got the "
                        + usb.getDataType() + " data.");
                    // Add value to pointSet
                    pointSet.add(usb.getDataType());
                }
            }
        }

        // Calculate earned points
        double point = (pointSet.size() * 5 + 5) * (1 +
            ((reactorActivated) ? 2 : 0) + (150 / (hero.getCharacterInitiative()
            + 150)));
        
        // Update high score based on the score obtained by the player.
        IScore newScore = new Score(this.name, point);
        IHighscore highscoreData = LogicFacade.getHighscore();
        IScore[] scores = highscoreData.getScores();
        Highscore highscore = new Highscore(scores);
        highscore.updateHighscore(newScore);
        LogicFacade.saveHighscore(highscore);
        
        // Return points obtained.
        return point;
    }

    /**
     * This method tests if the game is won.
     * 
     * @return true if the game is won; else it returns false.
     */
    private boolean winTest() {
        // Set finished to false
        boolean finished = false;
        /*
        If the player is in the escape pod, set finished to true and print win
        message.
        */
        if (currentCharacter.getCurrentRoom().equals(winConditionRoom)) {
            finished = true;
            printStopMessage("win");
        }
        
        // Return value of finished (true if player has won).
        return finished;
    }

    /**
     * This method tests if the player loses because of the reactor.
     * 
     * @return true if the game is ended; else it returns false.
     */
    boolean timerLose() {
        /*
        If player's initiative is greater than maxInitiative, print lose message
        based on "timer" and return true.
        */
        if (currentCharacter.getCharacterInitiative() > maxInititative) {
            printStopMessage("timer");
            return true;
        }
        // Else, return false
        else {
            return false;
        }
    }

    /**
     * This method checks whether or not the player has died because of low
     * health.
     * 
     * @return true if the game is finished; else it returns false.
     */
    private boolean healthTest() {
        // Set tempCharacter to current character
        Hero tempCharacter = (Hero)currentCharacter;
        /*
        If player's health is less than or equal to zero, print "health" lose
        message and return true.
        */
        if (tempCharacter.getHealth() <= 0) {
            printStopMessage("health");
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This method returns the current character.
     * 
     * @return instance of Character which represents the current character.
     */
    Character getCurrentCharacter() {
        return this.currentCharacter;
    }

    /**
     * This method is used to check if the game is finished.
     * 
     * @return boolean data field "finished".
     */
    boolean isFinished() {
        return this.finished;
    }
    
    /**
     * This method is used when spawning new Zuuls. The method chooses a Room
     * for the new Zuul.
     * 
     * @return 
     */
    private Room randomZuulSpawnRoom(){       
        // Create a Set of all rooms in the game.
        Set<String> allRooms = new HashSet<>(this.rooms.keySet());
        /*
        Remove the escape pod from this set, since Zuuls are not to spawn in
        the escape pod.
        */
        allRooms.remove("Pod");
        
        // Remove rooms where there already is a Zuul.
        for (Character character : characters) {
            if (character instanceof Zuul) {
                if (allRooms.size() > 1) {
                    allRooms.remove(character.getCurrentRoom().getName());
                }
            }
        }
        
        // Generate a random index
        int randomRoomKeyIndex = (int)(Math.random() * allRooms.size());       
        
        // Create a List of the rooms to choose from.
        List<String> allRoomsList = new ArrayList<>(allRooms);
        // Get the key of the chosen room based on the random index.
        String randomRoomKey = allRoomsList.get(randomRoomKeyIndex);
        // Find chosen room.
        Room randomRoom = rooms.get(randomRoomKey);
        // Return this room.
        return randomRoom;
    }

    /**
     * This method returns the rooms of the game.
     * 
     * @return HashMap of the rooms in the game.
     */
    HashMap<String, Room> getRooms() {
        return this.rooms;
    }
    
    /**
     * This method is used to read and delete the message of the game.
     * 
     * @return String which represents the message of the game.
     */
    String readAndDeleteMessage() {
        String returnMessage = messageClass.readAndDelete();
        return returnMessage;
    }
    
    /**
     * This method checks if the player is talking to the tech dude.
     * 
     * @return true if the hero is talking to the tech dude; else it returns
     * false.
     */
    boolean isTalking(){
        boolean talking;
        if (currentCharacter instanceof Hero) {
            talking = ((Hero)currentCharacter).isTalking();
        }
        else {
            talking = false;
        }
        return talking;
    }
    
    /**
     * This method is used to find out what characters are present in the
     * specified room.
     * 
     * @param room instance of IRoom, which represents the room to be searched.
     * 
     * @return ArrayList of Strings which indicates the characters present in 
     * the specified room.
     */
    ArrayList<String> charactersInRoom(IRoom room){
        Room tempRoom = rooms.get(room.getName());
        ArrayList<String> charactersInRoom = tempRoom.getCharacterInRoom();
        return charactersInRoom;
    }
    
    /**
     * This method is used to get the name of the current room of the current
     * character.
     * 
     * @return String which represents the current room of the current
     * character.
     */
    String getCurrentRoomName(){
        String roomName = this.currentCharacter.getCurrentRoom().getName();
        return roomName;
    }
}
