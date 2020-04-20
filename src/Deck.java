/**
 * Team 5: Jan Patrick Camaclang, Gregory Gonzalez
 * 2/7/2017
 * CST 338 - Software Design
 * Deck.java
 * Defines the Deck class
 */

public class Deck
{
   //declare members
   public final static int MAX_CARDS = 312; //Initialize to allow a max of six packs * 52 cards
   public final static int DECK_SIZE = 52; //The standard 52 card deck, including all cards sans Joker

   private static Card[] masterPack = new Card[DECK_SIZE];

   private Card[] cards;  //Array of all the cards of the many packs in the deck
   private int topCard;
   private int numPacks;

   //Constructor that populate the arrays and assign initial values to members
   public Deck()
   {
      this(1); //Overloaded (directly below) so if no parameters passed, 1 pack is assumed
   }

   public Deck(int numPacks)
   {
      allocateMasterPack();
      this.numPacks = numPacks;
      this.topCard = (DECK_SIZE * numPacks) - 1;
      this.init(this.numPacks);
   }

   /*re-populate cards[] with the standard 52 × numPacks cards.  We should not 
    * re-populate the static array, masterPack[], since that was done once, in 
    * the (first-invoked) constructor and  never changes.
    */
   void init(int numPacks)
   {  
      if(numPacks * DECK_SIZE <= MAX_CARDS)  //Check that initialization does not exceed max allowed
      {  
         this.cards = new Card[numPacks * DECK_SIZE];
         int init_counter = 0;
         for (int i = 0; i < cards.length; i++)
         {
            this.cards[i] = masterPack[init_counter];
            init_counter ++;
            if(init_counter >= DECK_SIZE)  //After a pack is initialized, reset to zero
            {
               init_counter = 0;                 
            }
         }
      }
   }

   //Mixes up the cards with the help of the standard random number generator
   public void shuffle() 
   {
      for(int j = 0; j < this.cards.length; j++)
      {
         //Generate an index randomly from (0-1 * array length)
         int index = (int)(Math.random() * this.cards.length);
         Card card_place_holder = this.cards[j];  //Perform the swap, assigning card in j position to place holder
         this.cards[j] = this.cards[index];  //Assign the random card to the 'vacated' j position
         this.cards[index] = card_place_holder;  //Return the j position card to the random card's place
      }
   }

   //Returns and removes the card in the top occupied position of cards[] (a mutator)
   public Card dealCard()
   {
      if (this.topCard >= 0)
      {
         Card topmostCard = this.cards[this.topCard];
         this.cards[topCard] = null;
         --this.topCard;
         return topmostCard;
      }
      else
         return null;
   }

   //An accessor for the int, topCard (no mutator)
   public int getTopCard()
   {
      return this.topCard;
   }

   //Accessor for individual card. Returns card with errorFlag = true if k bad
   public Card inspectCard(int k) 
   {
      if(this.cards[k] != null)
      {
         return this.cards[k];
      }
      else
      {
         return new Card('M', Card.Suit.CLUBS); // x results errorFlag thrown
      }
   }

   /*This is a private method that will be called by the constructor.  However, it has to be done with a very simple 
    * twist:  even if many Deck objects are constructed in a given program, this static method will not allow itself 
    * to be executed more than once.  Since masterPack[] is a static, unchanging, entity, it need not be built every 
    * time a new Deck is instantiated.  So this method needs to be able to ask itself, "Have I been here before?", 
    * and if the answer is "yes", it will immediately return without doing anything; it has already built masterPack[] 
    * in a previous invocation.
    */
   
   private static void allocateMasterPack()
   {
      Card.Suit suitAssignment;
      int i, j;
      char val;

      for (i = 0; i < masterPack.length; i++)
      {
         if(masterPack[i] != null)
         {
            //Stop masterPack reallocation when already extant
            return;
         }
      }

      //Initialize 52 cards in the master deck
      for (i = 0; i < masterPack.length; i++)
      {
         masterPack[i] = new Card();
      }

      for (i = 0; i < 4; i++)
      {
         //Set the suit for this loop pass
         switch(i)
         {
         case 0:
            suitAssignment = Card.Suit.CLUBS; break;
         case 1:
            suitAssignment = Card.Suit.DIAMONDS; break;
         case 2:
            suitAssignment = Card.Suit.HEARTS; break;
         case 3:
            suitAssignment = Card.Suit.SPADES; break;
         default:
            // should not happen but ...
            suitAssignment = Card.Suit.SPADES; break;
         }   

         //Now set all the values for this case's suit
         masterPack[13 * i].set('A', suitAssignment);
         for (val = '2', j = 1; val <= '9'; val++, j++)
         {
            masterPack[13 * i + j].set(val, suitAssignment);
         }
         masterPack[13 * i + 9].set('T', suitAssignment);
         masterPack[13 * i + 10].set('J', suitAssignment);
         masterPack[13 * i + 11].set('Q', suitAssignment);
         masterPack[13 * i + 12].set('K', suitAssignment);         
      }
   }
   
   boolean addCard(Card card)
   {
      //Ensures there aren't more instances of the passed Card than their are numDecks,
      //and adds the passed Card to the top of the deck if so, else returns false
      int uniqueCounter = 0;
      for (int i = 0; i < cards.length; i++)
      {
         if (cards[i].equals(card))
         {
            uniqueCounter++;
         }
      }
      
      if (uniqueCounter < numPacks)
      {
         cards[topCard + 1] = card;
         topCard++;
         return true;
      }
      else
      {
         return false;
      }      
      
   }
   
   boolean removeCard(Card card) 
   {
      //Removes the passed Card at the first position in the array if it exists, else returns false
      for (int i = 0; i < cards.length; i++)
      {
         if (cards[i].equals(card))
         {
            cards[i] = cards[topCard];
            cards[topCard] = null;
            topCard--;
            return true;
         }
      }
      return false;         
   }
   
   void sort()
   {
      //Sorts the cards array
      Card.arraySort(cards, this.topCard);
   }
   
   int getNumCards()
   {
      //Returns the number of cards in the deck
      if (this.topCard >= 0)
      {
         return this.topCard + 1;
      }
      else
      {
         return 0;
      }      
   }
}
