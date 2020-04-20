/**
 * Team 5: Jan Patrick Camaclang, Gregory Gonzalez
 * 2/7/2017
 * CST 338 - Software Design
 * CardTable.java
 * Defines the CardTable class
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class CardTable extends JFrame
{
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;  // for now, we only allow 2 person games
   
   private int numCardsPerHand;
   private int numPlayers;
   
   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;
   
   public CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      //Constructs a special JFrame called CardTable
      super(title);
      this.numCardsPerHand = numCardsPerHand;
      this.numPlayers = numPlayers;      
      this.initPanels();      
   }
   
   public void initPanels()
   {
      //Initializes JPanels
      pnlComputerHand = new JPanel();
      pnlHumanHand = new JPanel();
      pnlPlayArea = new JPanel();      
      setLayout(new BorderLayout());
      
      pnlComputerHand.setLayout(new GridLayout(1,numCardsPerHand));
      pnlPlayArea.setLayout(new GridLayout(2, MAX_PLAYERS));
      pnlHumanHand.setLayout(new GridLayout(1,numCardsPerHand));
      
      add(pnlComputerHand, BorderLayout.NORTH);
      add(pnlPlayArea, BorderLayout.CENTER);
      add(pnlHumanHand, BorderLayout.SOUTH);
    
      TitledBorder[] titleBorder = new TitledBorder[3];
      Border blackline = BorderFactory.createLineBorder(Color.black);
      
      titleBorder[0] = BorderFactory.createTitledBorder(blackline, "Computer Hand" );
      pnlComputerHand.setBorder(titleBorder[0]);
      
      titleBorder[1] = BorderFactory.createTitledBorder(blackline, "Playing Area" );
      pnlPlayArea.setBorder(titleBorder[1]);
      
      titleBorder[2] = BorderFactory.createTitledBorder(blackline, "Your Hand" );
      pnlHumanHand.setBorder(titleBorder[2]);
   }
   
   public void resetPanels()
   {
      //Removes all objects from JPanels
      pnlComputerHand.removeAll();
      pnlHumanHand.removeAll();
      pnlPlayArea.removeAll();  
   }     
}
