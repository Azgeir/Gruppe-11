/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

/**
 *
 * @author Simon
 */
public class Monster {

    private Room currentRoom;

    public Monster(Room currentRoom) {
        this.currentRoom = currentRoom;

    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void turn() {
        int monsterInitiative = (int) (Math.random() * 4.0);

        Command command;
        //boolean reroll = true;
        String word1 = "go";
        String word2 = null;

        //while (reroll){
        if (monsterInitiative == 0) {
            word2 = "east";
            //reroll = false

        } else if (monsterInitiative == 1) {
            word2 = "south";

        } else if (monsterInitiative == 2) {
            word2 = "west";

        } else if (monsterInitiative == 3) {
            word2 = "north";
        } else {
            word1 = "stay";
        }
        //}
        CommandWords commands = new CommandWords();

        command = new Command(commands.getCommandWord(word1), word2);
        this.goRoom(command);

    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Monster Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Monster There is no door!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription() + "Monster ");
        }
    }

}
