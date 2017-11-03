/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

/**
 *
 * @author Aske Wulf
 */
public class Conversation {
    //Techdude conversation
    String techTalk1 = "Is the Zuul gone?";
    String techTalk2 = "The arrival of Zuul has triggered stations quarantine mechanism,\n"
            + "causing the doors to all the escape pods to be locked.";
    String techTalk3 = "Yes, I can override the mechanism and unlock one of the pods,\n"
            + "but I will have to be in the dock to do this.";
    //Replace (time) with the number of turns after tuning
    String techTalk4 = "I can overload the reactor, causing it to self-destruct with the whole space station.\n"
            + "This will give you a short time to get off the station before it blows.\n"
            + "But I must be near the reactor to overload it.";
    
    //Player responses
    String techAnswer1 = "For now. What's happened to the station?";
    String techAnswer2 = "Can you help me get off this station?";
    String techAnswer3 = "Anything else you can help with?";
    String letsGo = "Let's go!";
    String fuckOff = "Fuck off.";
    
    public void options(String string){
        if (string == null){
            System.out.println("String is null");
        }
        else switch (string) {
            case "TechDude1":
                System.out.println("1: " + techAnswer1);
                System.out.println("2: " + fuckOff);
                break;
            case "TechDude2":
                System.out.println("1: " + techAnswer2);
                System.out.println("2: " + fuckOff);
                break;
            case "TechDude3":
                System.out.println("1: " + techAnswer3);
                System.out.println("2: " + fuckOff);
                System.out.println("3: " + letsGo);
                break;
            case "TechDude4":
                System.out.println("1: " + letsGo);
                System.out.println("2: " + fuckOff);
                break;
            default:
                System.out.println("I dont know what you mean");
                break;
        }
    }
    public void talk(String string){
        if (string == null){
            System.out.println("String is null");
        }
        else switch (string){
            case "TechDude1":
                System.out.println(techTalk1);
                break;
            case "TechDude2":
                System.out.println(techTalk2);
                break;
            case "TechDude3":
                System.out.println(techTalk3);
                break;
            case "TechDude4":
                System.out.println(techTalk4);
                break;
            default:
                System.out.println("I dont know what you want");
                break;
        }
    }

    }
