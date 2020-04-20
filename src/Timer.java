/**
 * Team 5: Jan Patrick Camaclang, Gregory Gonzalez
 * 2/7/2017
 * CST 338 - Software Design
 * Timer.java
 * Defines the Timer class to be executed in Assig5.java
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.text.*;

public class Timer extends Thread
{
   JFrame timer;
   JPanel top, bot, mid;   
   DecimalFormat secondsPattern;
   JLabel title, counter;
   JButton startStop;
   int minutes, seconds;
   boolean running, currState;
   String currTime;
   
   
   public Timer()
   {
      this.timer = new JFrame();
      this.top = new JPanel();
      this.bot = new JPanel();
      this.mid = new JPanel();
      this.secondsPattern = new DecimalFormat("00");
      this.title = new JLabel("Timer");
      this.counter = new JLabel();
      this.startStop = new JButton("Start/Stop");
      this.startStop.addActionListener(new setTimer(this));
      this.minutes = 0;
      this.seconds = 0;
      this.running = true;
      this.currState = true;
      this.currTime = "";
      init();      
   }
   
   public void init()
   {
      GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
      int width = gd.getDisplayMode().getWidth();
      int height = gd.getDisplayMode().getHeight();
      timer.setSize(100,130);
      timer.setLocation((int)(.65 * width), (int)(.4 * height)); //Set for 1920 x 1080p resolution
      timer.setLayout(new BorderLayout());
      timer.add(top, BorderLayout.NORTH);
      timer.add(mid, BorderLayout.CENTER);
      timer.add(bot, BorderLayout.SOUTH);
      printTimer();
   }
   
   public void setCurrState(boolean newState)
   {
      this.currState = newState;
   }
   
   public void resetClock()
   {
      this.minutes = 0;
      this.seconds = 0;
   }
   
   public void printTimer()
   {
      top.removeAll();
      mid.removeAll();
      bot.removeAll();
      counter = new JLabel(currTime);      
      top.add(title);
      mid.add(counter);
      bot.add(startStop);
      timer.setVisible(true);
   }
   
   void getTime()
   {
      currTime = minutes + ":" + secondsPattern.format(seconds);
   }
   
   void incrementTime()
   {      
      if (seconds < 59)
      {
         seconds++;
      }
      else if (seconds == 59)
      {
         if (minutes < 9)
         {
            minutes++;
            seconds = 0;
         }
         else
         {
            minutes = 0;
            seconds = 0;
         }         
      }      
   }

   public void run()
   {
      while (running)
      {
         timeBetweenSeconds();
         if (currState)
         {            
            incrementTime();           
            getTime();             
         }
         printTimer();
      }      
   }
   
   public void timeBetweenSeconds()
   {
      try
      {
         Thread.sleep(1000); //1000 ms in 1 second
      }
      catch (InterruptedException e)
      {
         System.out.println("Unexpected Interrupt");
         System.exit(0);
      }
   }
}

class setTimer implements ActionListener
{
   Timer timer;   
   
   public setTimer(Timer t)
   {
      this.timer = t;      
   }
   
   public void actionPerformed(ActionEvent e)
   {
      if (timer.currState)
      {
         timer.setCurrState(false);         
      }
      else
      {
         timer.setCurrState(true);
      }
   }
}
