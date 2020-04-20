/**
 * Team 5: Jan Patrick Camaclang, Gregory Gonzalez
 * 2/7/2017
 * CST 338 - Software Design
 * Hand.java
 * Defines the Hand class
 */

class Hand
{
   //declare members
   public final static int MAX_CARDS = 50; //Initialize max cards in a hand
   private Card[] myCards;
   private int numCards;
   
   public Hand()
   {
      //Call resetHand() to initialize array and numCards
      resetHand();
   }
   
   public void resetHand()
   {
      //Initializes array and sets numCards
      myCards = new Card[MAX_CARDS];
      numCards = 0;     
   }
   
   public boolean takeCard(Card card)
   {
      //Checks if the quantity of cards in hand does not exceed MAX_CARDS
      if (numCards > MAX_CARDS - 1)
      {
         return false;
      }
      else //Set next spot in array to new object copy and increment numCards
      {
         myCards[numCards] = new Card(card.getValue(), card.getSuit());
         ++numCards;
         return true;
      }
   }
   
   public Card playCard(int index)
   {
      //Decrease numCards and removes and returns the top card in the array
      if (numCards > 0)
      {
         Card temp = myCards[index];
         myCards[index] = myCards[numCards - 1];
         myCards[numCards - 1] = null;
         numCards--;
         return temp;
      }
      else
      {
         return new Card('z', Card.Suit.CLUBS); 
      }
      
      
   }
   
   public String toString()
   {
      String hand = "( ";
      
      //Checks if hand is empty.  If not then it creates a String of Cards
      if (numCards == 0)
      {
         hand += " )";
      }
      else
      {
         for (int i = 0; i < numCards; i++)
         {
            hand += myCards[i].toString(i+1) + ", "; 
         }  
      }
      
      return hand;
   }
   
   public int getNumCards()
   {
      //Accessor for number of cards in hand
      return numCards;
   }
   
   public Card inspectCard(int k)
   {
      //checks if whether k is within range of numCards
      if (k >= 0 && k <= numCards)
      {
         return myCards[k];
      }
      else  //return invalid card
      {
         return new Card('M', Card.Suit.CLUBS); 
      }
   }
   
   void sort()
   {
      //Sorts the myCards array
      Card.arraySort(this.myCards, this.numCards);
   }
}