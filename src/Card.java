/**
 * Team 5: Jan Patrick Camaclang, Gregory Gonzalez
 * 2/7/2017
 * CST 338 - Software Design
 * Card.java
 * Defines the Card class
 */

public class Card
{
   public enum Suit {CLUBS, DIAMONDS, HEARTS, SPADES}; //Sets up enum type for suits
   private char value; //Holds value {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X'}
   private Suit suit; //Holds suit from enum Suit {CLUBS, DIAMONDS, HEARTS, SPADES}
   private boolean errorFlag; //Used to inform when a card is in an illegal state (i.e. '10' instead of 'T')
   public static char[] valuRanks = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X'};
   public Card(char val, Suit ste)
   { 
      //Constructor allows programmer to set value and suit using mutators
      set(val, ste);
   }
   
   public Card(char val)
   { 
      //Overloaded constructor allows programmer to set value, and suit defaults to SPADES
      set(val, Suit.SPADES);
   }
   
   public Card(Suit ste)
   { 
      //Overloaded constructor allows programmer to set suit, and value defaults to 'A'
      set('A', ste);
   }
   
   public Card()
   { 
      //Overloaded constructor defaults value to 'A' and suit to SPADES
      set('A', Suit.SPADES);      
   }
   
   public char getValue()
   { 
      //Accessor for value
      return value;
   }
   
   public Suit getSuit()
   { 
      //Accessor for suit
      return suit;
   }
   
   public boolean getFlag()
   { 
      //Accessor for errorFlag
      return errorFlag;
   }
   
   public String toString()
   { 
      //Returns a string displaying card information if errorFlag is false, otherwise invalid
      String cardFace = "";      
      if (getFlag())
      {
         cardFace += "[ Invalid ]";
      }
      else
      {
         cardFace += "Card: " + getValue() + " of " + getSuit();
      }
      return cardFace;
   }
   
   public String toString(int cardNumber)
   { 
      //Returns a string displaying card number and information if errorFlag is false, otherwise invalid
      String cardFace = "";
      if (getFlag())
      {
         cardFace += "Card " + (cardNumber) + ": [ Invalid ]";
      }
      else
      {
         cardFace += "Card " + (cardNumber) + ": " + getValue() + " of " + getSuit();
      }
      return cardFace;
   }
   
   public boolean set(char val, Suit ste)
   { 
      //Mutator for setting values (returns true if values are set). Converts passed val to uppercase
      if (Character.isLetter(val))
      {
         val = Character.toUpperCase(val);
      }
      errorFlag = !isValid(val, ste);
      
      if (!errorFlag)
      {
         value = val;
         suit = ste;
         return true;
      }
      else
      {
        return false; 
      }
      
   }   
   
   public boolean equals(Card card)
   { 
      //Compares suit and value of calling card to passed card, and returns true if both match
      
      if (card.getSuit() == suit)
      {
         if (card.getValue() == value)
         {
            if(card.getFlag() == errorFlag)
            {
               return true;
            }
            else
            {
               return false;
            }
         }
         else{
            return false;
         }
      }
      else
      {
         return false;
      }
   }   
   
   private boolean isValid(char val, Suit ste)
   { 
      //Used to test for valid value and suit when constructing Cards
      Suit [] steComp = {Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES};
      char [] valComp = valuRanks;
      boolean tempFlag = true;
      
      for (int i = 0; i < steComp.length; i++)
      { //tempFlag is set to false if passed Suit is valid, otherwise true
         if (ste == steComp[i])
         {
            tempFlag = true;
            break;
         }
         if (i == steComp.length - 1 && ste != steComp[i])
         {
            tempFlag = false;
         }
      }
      
      if (!tempFlag){ //tempFlag is set to false if passed value is valid, otherwise true
         for (int i = 0; i < valComp.length; i++)
         {
            if (val == valComp[i])
            {
               tempFlag = true;
               break;
            }
            if (i == valComp.length - 1 && val != valComp[i])
            {
               tempFlag = false;
            }
         }
      }
      
      return tempFlag;
   }
   
   static void arraySort(Card[] cardArray, int numCards) 
   {
      //Arranges the cards in the array in order of rank (Ace = 0, 1 = 1, King = 13, Joker = 14)      
      
      Card temp = new Card(); 
      boolean flag = true;      

      while ( flag )
      {
          flag = false;
          for(int j = 0;  j < numCards - 1;  j++ )
          {
              if (getRank(cardArray[j].getValue()) > getRank(cardArray[j+1].getValue()))   // change to > for ascending sort
              {
                temp = cardArray[ j ];
                cardArray[j] = cardArray[j+1];
                cardArray[j+1] = temp;
                flag = true;
             } 
          } 
       } 
   }
   
   static int getRank(char k)
   {
      //Assigns an integer rank for each card value
      int value;
      
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
      
      return value;
      
   }
   
   
}