/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.util.Timer;
import java.util.TimerTask;

public class TimeCounter {
  Timer timer;

  public TimeCounter (int seconds) {
    timer = new Timer();
    timer.schedule(new ToDoTask(), seconds*1000);
  }


  class ToDoTask extends TimerTask {
    public void run()   {
      System.out.println ("OK, It's time to do something!");
      timer.cancel() ; //Terminate the thread
    }
  }


  public static void main(String args[])  {
    System.out.println("Schedule something to do in 5 seconds.");
    new TimeCounter(5);
    System.out.println("Waiting.");
    new TimeCounter(10);
    new TimeCounter(20);
    new TimeCounter(50);
  }
}

// The idea is to count the amount of time spent in turns. 