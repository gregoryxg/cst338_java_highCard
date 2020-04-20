/**
 * Team 5: Jan Patrick Camaclang, Gregory Gonzalez
 * 2/7/2017
 * CST 338 - Software Design
 * GUICard.java
 * Defines the GUICard class, which is used for getting and storing card icons in the iconCard [][]
 */

import javax.swing.*;

public class GUICard
{
   static final int NUM_CARD_IMAGES = 57; // 52 + 4 jokers + 1 back-of-card image
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];
   private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K + joker
   private static Icon iconBack;
   static boolean iconsLoaded = false;
      
   static void loadCardIcons()
   {
      // build the file names ("AC.gif", "2C.gif", "3C.gif", "TC.gif", etc.)
      // in a SHORT loop.  For each file name, read it in and use it to
      // instantiate each of the 57 Icons in the icon[] array.
      String front = "images/";
      String suit = "";
      String val = "";
      String end = ".gif";           
      
      if (!iconsLoaded)
      {
         for (int i = 0; i < 4; i++)
         {
            suit = turnIntIntoCardSuit(i);
            
            for(int j = 0; j < 14; j++)
            {
               val = turnIntIntoCardValue(j);
               iconCards[j][i] = new ImageIcon(front + val + suit + end);
            }
         }      
         iconBack = new ImageIcon("images/BK.gif");
         iconsLoaded = true;   
      }
      
   }   
   
   // turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
   static String turnIntIntoCardValue(int k)
   {
      String value;
      
      if (k == 0)
      {
         value = "A";
      }
      else if (k > 0 && k < 9)
      {
         value = String.valueOf(k + 1);
      }
      else if (k == 9)
      {
         value = "T";
      }
      else if (k == 10)
      {
         value = "J";
      }
      else if (k == 11)
      {
         value = "Q";
      }
      else if (k == 12)
      {
         value = "K";
      }
      else
      {
         value = "X";
      }
      
      return value;

   }
   
   // turns 0 - 3 into "C", "D", "H", "S"
   static String turnIntIntoCardSuit(int j)
   {
      String suit;
      
      if (j == 0)
      {
         suit = "C";
      }
      else if (j == 1)
      {
         suit = "D";
      }
      else if (j == 2)
      {
         suit = "H";
      }
      else
      {
         suit = "S";
      }
      return suit;
   }
   
   public static Icon getIcon(Card card)
   {
      //Returns the icon for the passed Card
      char k = card.getValue();
      Card.Suit j = card.getSuit();
      int value;
      int suit;
      
      if (k == 'A')
      {
         value = 0;
      }
      else if (k == '2')
      {
         value = 1;
      }
      else if (k == '3')
      {
         value = 2;
      }
      else if (k == '4')
      {
         value = 3;
      }
      else if (k == '5')
      {
         value = 4;
      }
      else if (k == '6')
      {
         value = 5;
      }
      else if (k == '7')
      {
         value = 6;
      }
      else if (k == '8')
      {
         value = 7;
      }
      else if (k == '9')
      {
         value = 8;
      }
      else if (k == 'T')
      {
         value = 9;
      }
      else if (k == 'J')
      {
         value = 10;
      }
      else if (k == 'Q')
      {
         value = 11;
      }
      else if (k == 'K')
      {
         value = 12;
      }
      else
      {
         value = 13;
      }
      
      if (j == Card.Suit.CLUBS)
      {
         suit = 0;
      }
      else if (j == Card.Suit.DIAMONDS)
      {
         suit = 1;
      }
      else if (j == Card.Suit.HEARTS)
      {
         suit = 2;
      }
      else
      {
         suit = 3;
      }
      
      return iconCards[value][suit];
         
   }
   
   static public Icon getBackCardIcon() 
   {
      //Returns the Icon for the back of the card
      return iconBack;
   }
}