/**
 * Team 5: Jan Patrick Camaclang, Gregory Gonzalez
 * 2/7/2017
 * CST 338 - Software Design
 * Assig5.java
 * Allows the user to play a high-card game, where the goal is to beat the computer's card at least 4/7 times per game to win.
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Assig5
{
   public static void main(String[] args)
   {
      GameModel gameModel = new GameModel();  //Model
      GameView gameView = new GameView(gameModel); //View
      GameController gameController = new GameController(gameModel, gameView); //Controller
   }
}

class GameModel
{
   static final int NUM_CARDS_PER_HAND = 7; //Sets the number of cards per hand
   static final int  NUM_PLAYERS = 2; //Sets the number of players (Game's implementation only allows 2 at this time)   
   static JLabel [] computerLabels; //Tracks the JLabels for the computer's hand 
   static JButton [] humanButtons; //Tracks the JButtons for the player's hand   
   static JLabel [] playedCardLabels; //Tracks the JLabels for the played cards  
   static JLabel [] playLabelText; //Tracks the JLabels for the played cards' players
   static int roundCounter; //Tracks the current round (7 per game)
   static int numPacksPerDeck; //Used to initialize the main deck in highCardGame
   static int numJokersPerPack; //Used to add Jokers to the main deck in highCardGame
   static int numUnusedCardsPerPack; //Used to remove unused cards from the main deck
   static Card [] unusedCardsPerPack; //Allows the removal of certain cards per pack from the main deck
   static int [] score;   //Keeps track of computer and player's scores
   
   public GameModel()
   {      
      //Sets all initial values
      initGame();
   }
   static void initGame()
   {
      //Sets and resets all initial values for the game
      
      computerLabels = new JLabel[NUM_CARDS_PER_HAND];
      humanButtons = new JButton[NUM_CARDS_PER_HAND];  
      playedCardLabels  = new JLabel[NUM_PLAYERS]; 
      playLabelText  = new JLabel[NUM_PLAYERS];
      roundCounter = 0;
      numPacksPerDeck = 1;
      numJokersPerPack = 0;
      numUnusedCardsPerPack = 0;
      unusedCardsPerPack = null;
      score = new int[NUM_PLAYERS];
      
      CardGameFramework highCardGame = new CardGameFramework( //Sets up the game framework
            numPacksPerDeck, numJokersPerPack,  
            numUnusedCardsPerPack, unusedCardsPerPack, 
            NUM_PLAYERS, NUM_CARDS_PER_HAND);
   }
   
   static void updateRound(CardTable cardTable, JLabel [] compLabels, JButton [] hButtons, 
         JLabel [] playedCards, JLabel [] playText, int [] scor, int rndCounter)
   { 
      
      //Used for updating the JLabels and JButtons on the myCardTable window
      // CREATE LABELS ----------------------------------------------------
      
      //Fills computerLabels array with card backs            
      for (int i = 0; i < hButtons.length; i++)
      {
         compLabels[i] = new JLabel(new ImageIcon("images/BK.gif"));
      }
      
      //Fills the hButtons array with updated set of JButtons passed to printRound(), and adds selectCard() action listener to each button
      Icon currIcon;
      for (int i = 0; i < hButtons.length; i++)
      {
         currIcon = GUICard.getIcon(CardGameFramework.getHand(GameModel.NUM_PLAYERS-1).inspectCard(i));
         hButtons[i] = new JButton(currIcon);
         hButtons[i].addActionListener(new GameController.selectCard(hButtons, playedCards, 
               playText, i, cardTable, scor, rndCounter + 1));
      }
      
      //Used for the initial display of the play area when game first starts
      if (rndCounter == 0)
      {
         for (int i = 0; i < playedCards.length; i++)
         {
            playedCards[i] = new JLabel("X", JLabel.CENTER);
         }
      }
      
      //Fills the playedCardLabels array with the JLabel text "Computer" at pos 0, 
      //and "Player " + i for each position after
      for (int i = 0; i < playText.length; i++)
      {
         if (i == 0)
         {
            playText[i] = new JLabel("Computer", JLabel.CENTER);
         }
         else
         {
            playText[i] = new JLabel("Player " + (i), JLabel.CENTER);
         } 
      }
   }
   static JButton [] reducedHand(JButton [] buttons, int popButton)
   {
      //Returns a JButton [] with the player's selected card removed
      JButton [] reduced = new JButton[buttons.length - 1];
      int counter = 0;
      for (int i = 0; i < buttons.length; i++)
      {
         if (i != popButton)
         {
            reduced[counter] = buttons[i];
            counter++;
         }         
      }
      return reduced;
   }
   static JLabel [] setPlayedCardLabels(JLabel [] pLabels, Card computerCard, Card playerCard)
   {
      //Updates and returns the playedCardLabels [] with the JLabels for the selected cards
      pLabels[0] = new JLabel(GUICard.getIcon(computerCard));
      pLabels[pLabels.length - 1] = new JLabel(GUICard.getIcon(playerCard));
      return pLabels;
   }   
   static int computerChoice(Card playerCard)
   {
      //Returns the int position for the computer's card to play each round
      Hand computerHand = CardGameFramework.getHand(0);      
      
      //Checks for the lowest value card in the hand that beats the player's card, and returns its position
      for (int i = 0; i < computerHand.getNumCards(); i++)
      {
         if (Card.getRank(computerHand.inspectCard(i).getValue()) > Card.getRank(playerCard.getValue()))
         {
            return i;
         }
      }
      
      //If the computer doesn't have a higher card than the player, it checks for one that equals the player's card, and returns its position
      for (int i = 0; i < computerHand.getNumCards(); i++)
      {
         if (Card.getRank(computerHand.inspectCard(i).getValue()) == Card.getRank(playerCard.getValue()))
         {
            return i;
         }
      }
      
      //If the computer doesn't have a card to tie the round, it plays its lowest card by returning the first position in the hand
      return 0;
   }
}

class GameView
{
   static CardTable myCardTable;   
   GameModel gameModel;
   
   public GameView(GameModel gameModel)
   {
      this.gameModel = gameModel;
      this.createTable();      
   }
   
   private void createTable()
   {
      //Sets up the main JFrame myCardTable      
      myCardTable = new CardTable("CardTable", GameModel.NUM_CARDS_PER_HAND, GameModel.NUM_PLAYERS);
      
      // establish main frame in which program will run     
      myCardTable.setSize(86*GameModel.NUM_CARDS_PER_HAND, 600); //Allows width customization based on the # of cards in the hand
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
   }
   static CardTable getTable()
   {
      return myCardTable;
   }
   static void setVisible(boolean val)
   {
      if (val)
      {
      // show everything to the user
         myCardTable.setVisible(true); 
      }
      else
      {
         myCardTable.setVisible(false); 
      }
   }
   
   static void resetTable()
   {
      myCardTable.resetPanels();
      myCardTable.initPanels();
   }
   static void printRound (CardTable cardTable, JLabel [] compLabels, JButton [] hButtons, 
   JLabel [] playedCards, JLabel [] playText)
   {
      // ADD LABELS TO PANELS -----------------------------------------
      
      // Fills computer and players hands
      for (int k = 0; k < hButtons.length; k++)
      {
         cardTable.pnlComputerHand.add(compLabels[k]);
         cardTable.pnlHumanHand.add(hButtons[k]);         
      }
      
      // Fills the top row of the play area with the cards, starting with the computer's card to NUM_PLAYERS      
      for (int k = 0; k < playText.length; k++)
      {
         cardTable.pnlPlayArea.add(playedCards[k]);
      }
         
      // Labels the cards in the play area with the appropriate player, starting with the computer's card to NUM_PLAYERS
      for (int k = 0; k < playText.length; k++)
      {
         cardTable.pnlPlayArea.add(playText[k]);
      }
   }
   
   static void gameOutcome(Card computerCard, Card playerCard, int [] score, int roundCounter)
   {      
      //Sets up the JFrame for the last round of each game
      JFrame roundFrame = new JFrame();
      JButton newGame = new JButton("Play Again");
      JButton endGame = new JButton("Quit");      
      roundFrame.setSize(225, 125); 
      roundFrame.setLocationRelativeTo(null);
      roundFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      JPanel top = new JPanel();
      JPanel mid = new JPanel();
      JPanel bot = new JPanel();
      roundFrame.setLayout(new BorderLayout());
      
      
      top.setLayout(new GridLayout(1,1));
      mid.setLayout(new GridLayout(2,1));
      bot.setLayout(new GridLayout(1,2));
      
      roundFrame.add(top, BorderLayout.NORTH);
      roundFrame.add(mid, BorderLayout.CENTER);
      roundFrame.add(bot, BorderLayout.SOUTH);
      
      //Increments scores for the last round
      if (Card.getRank(computerCard.getValue()) > Card.getRank(playerCard.getValue()))
      {         
         score[0]++;
      }
      else if (Card.getRank(computerCard.getValue()) < Card.getRank(playerCard.getValue()))
      {         
         score[score.length - 1]++;
      }
      
      //Checks for a winner or tie based on the final scores
      if (score[0] > score[score.length - 1])
      {
         top.add(new JLabel("Computer Wins the Game!",  JLabel.CENTER));
      }
      else if (score[0] < score[score.length - 1])
      {
         top.add(new JLabel("Player Wins the Game!",  JLabel.CENTER));
      }
      else
      {
         top.add(new JLabel("The Game Ended in a Tie!",  JLabel.CENTER));
      }
      
      mid.add(new JLabel("Computer Score: " + score[0], JLabel.CENTER));
      mid.add(new JLabel("Player Score: " + score[1], JLabel.CENTER));
      
      //JButton allows player to play a new game
      newGame.addActionListener(new GameController.playAgain(roundFrame));
      //JButton allows player to quit
      endGame.addActionListener(new GameController.endGame());
      bot.add(newGame);
      bot.add(endGame);
      roundFrame.setVisible(true);
   }
   
   static void roundOutcome(Card computerCard, Card playerCard, int [] score, int roundCounter, Timer gameClock)
   {
      //Displays JFrame that shows the round's results
      if (roundCounter < GameModel.NUM_CARDS_PER_HAND)
      {
         JFrame roundFrame = new JFrame();
         JButton okayButton = new JButton("Continue");
         roundFrame.setSize(200, 125); 
         roundFrame.setLocationRelativeTo(null);
         JPanel top = new JPanel();
         JPanel mid = new JPanel();
         JPanel bot = new JPanel();
         roundFrame.setLayout(new BorderLayout());
         
         
         top.setLayout(new GridLayout(1,1));
         mid.setLayout(new GridLayout(2,1));
         bot.setLayout(new GridLayout(1,1));
         
         roundFrame.add(top, BorderLayout.NORTH);
         roundFrame.add(mid, BorderLayout.CENTER);
         roundFrame.add(bot, BorderLayout.SOUTH);
         
         //Checks for the round's winner or a tie
         if (Card.getRank(computerCard.getValue()) > Card.getRank(playerCard.getValue()))
         {
            top.add(new JLabel("Computer Wins the Round!", JLabel.CENTER));
            score[0]++;
         }
         else if (Card.getRank(computerCard.getValue()) < Card.getRank(playerCard.getValue()))
         {
            top.add(new JLabel("Player Wins the Round!", JLabel.CENTER));
            score[score.length - 1]++;
         }
         else
         {
            top.add(new JLabel("This Round was a Tie!", JLabel.CENTER));
         }
         mid.add(new JLabel("Computer Score: " + score[0], JLabel.CENTER));
         mid.add(new JLabel("Player Score: " + score[1], JLabel.CENTER));
         
         //JButton allows the game to continue
         okayButton.addActionListener(new GameController.continueButton(roundFrame));
         bot.add(okayButton,  BorderLayout.SOUTH);
         roundFrame.setVisible(true);
      }
      else
      {
         //If final card in the hand has been played, gameOutcome() will be called
         gameClock.setCurrState(false); //Pauses the clock
         gameOutcome(computerCard, GameController.selectCard.getPlayedCard(), score, roundCounter);
      }
      
   }
}

class GameController
{
   static GameModel gameModel;
   static GameView gameView;
   static Timer gameClock;
   
   public GameController(GameModel gameModel, GameView gameView)
   {
      this.gameModel = gameModel;
      this.gameView = gameView;
      this.gameClock = new Timer();
      this.gameClock.start();
      resetGame();      
   }
   
   static void resetGame()
   {
      gameView.resetTable();
      gameModel.initGame();
      //Sets the initial information to be printed to myCardTable
      gameModel.updateRound(GameView.getTable(), gameModel.computerLabels, gameModel.humanButtons, 
            gameModel.playedCardLabels, gameModel.playLabelText, gameModel.score, gameModel.roundCounter);
      //Prints the initial information to myCardTable      
      gameView.printRound(gameView.getTable(), gameModel.computerLabels, gameModel.humanButtons, 
            gameModel.playedCardLabels, gameModel.playLabelText);
      //Shows the player everything
      GameView.getTable().setVisible(true);      
      gameClock.resetClock(); //Resets the timer's clock for a new game
      gameClock.setCurrState(true);
   }
   static class selectCard implements ActionListener
   {
      //Implements the action taken when a button (Card Icon) is selected
      JLabel [] computerLabels;
      JLabel [] playedCardLabels;
      JLabel [] playLabelText;
      JButton [] humanButtons;
      int position; //Used to determine the array position for the player's selected card
      int [] score;
      int roundCounter;
      CardTable myCardTable;
      static Card playedCard;
      Card computerCard;
      JLabel playCard;
      
      public selectCard(JButton [] hButtons, JLabel [] pLabels, 
            JLabel [] tLabels, int pos, CardTable table, int [] score, int roundCounter)
      {
         this.humanButtons = hButtons;
         this.playedCardLabels = new JLabel[pLabels.length];
         this.computerLabels = new JLabel[hButtons.length - 1];
         this.playLabelText = tLabels;
         this.position = pos;
         this.myCardTable = table;
         this.score = score;
         this.roundCounter = roundCounter;
      }
      
      public void actionPerformed(ActionEvent e) 
      {
           humanButtons = GameModel.reducedHand(humanButtons, position); //Removes the selected card from the humanButtons array
           playedCard = CardGameFramework.getHand(1).playCard(position); //Removes the selected card from the player's hand, and saves it to playedCard
           computerCard = CardGameFramework.getHand(0).playCard(gameModel.computerChoice(playedCard)); //Removes the selected card from the computer's hand, and saves it to computerCard
           playedCardLabels = GameModel.setPlayedCardLabels(playedCardLabels, computerCard, playedCard);  //Updates the playedCardLabels array with the selected computer and player cards
           GameView.resetTable();
           CardGameFramework.sortHands(); //Re-sorts players' hands        
           gameView.roundOutcome(computerCard, playedCard, score, roundCounter, gameClock); //Displays JFrame showing the winner of the round and current scores           
           gameModel.updateRound(myCardTable, computerLabels, humanButtons, //Updates the JFrame with the new hands for computer and player
                 playedCardLabels, playLabelText, score, roundCounter);
           gameView.printRound(myCardTable, computerLabels, humanButtons, //Displays the JFrame with the new hands for computer and player
                 playedCardLabels, playLabelText);
      }
      
      static Card getPlayedCard()
      {
         return playedCard;
      }
   }
   
   static class playAgain implements ActionListener
   {
      //Action listener that resets the JFrame window
      JFrame roundFrame;
      
      public playAgain(JFrame roundFrame)
      {
         this.roundFrame = roundFrame;
      }
      public void actionPerformed(ActionEvent e)
      {
         roundFrame.setVisible(false);
         resetGame();
      }
   }
   
   static class endGame implements ActionListener
   {
      //Action listener that exits the game
      public void actionPerformed(ActionEvent e)
      {
         System.exit(0);
      }
   }
   
   static class continueButton implements ActionListener
   {
      //Allows the player to continue the game after each round
      JFrame roundFrame;
      public continueButton(JFrame roundFrame)
      {
         this.roundFrame = roundFrame;
      }
      public void actionPerformed(ActionEvent e)
      {
         roundFrame.setVisible(false);
      }
   }
}