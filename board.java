package SpitCardGame;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class GameBoard extends GridPane {

  private final int NUM_SPIT_PILES = 4;
  private final int SPIT_PILES_ROWS = 2;
  private final int SPIT_PILES_COLS = 2;
  // Fields for the spit piles, stock pile, and discard pile
  private StackPane[] spitPiles;
  private StackPane stockPile;
  private StackPane discardPile;

  // Fields for the players
  private Hand player1;
  private Hand player2;

  // Field for the current player
  private Hand currentPlayer;

  public GameBoard() {
    spitPiles = new StackPane[NUM_SPIT_PILES];
    for (int i = 0; i < NUM_SPIT_PILES; i++) {
      spitPiles[i] = new StackPane();
      this.add(spitPiles[i], i % SPIT_PILES_COLS, i / SPIT_PILES_COLS);
    }

    stockPile = new StackPane();
    this.add(stockPile, SPIT_PILES_COLS, 0, 1, SPIT_PILES_ROWS);

    discardPile = new StackPane();
    this.add(discardPile, SPIT_PILES_COLS, SPIT_PILES_ROWS);
  }

  // Method for starting a player's turn
  public void startTurn() {
    // Display a prompt for the player to choose an action
    System.out.println("It's your turn. Do you want to draw a card or play a card?");
    System.out.println("Enter 'draw' to draw a card or 'play' to play a card.");

    // Read the player's input
    Scanner scanner = new Scanner(System.in);
    String action = scanner.nextLine();

    // Check the player's input and take the appropriate action
    if (action.equalsIgnoreCase("draw")) {
      // Draw a card from the stock pile
      drawCard();
    } else if (action.equalsIgnoreCase("play")) {
      // Display a prompt for the player to choose a card to play
      System.out.println("Enter the index of the card you want to play (0-based).");

      // Read the player's input
      int index = scanner.nextInt();

      // Play the chosen card
      playCard(index);
    } else {
      // Invalid input, start the turn again
      startTurn();
    }
  }

  // Method for drawing a card from the stock pile
  public void drawCard() {
    // Check if the stock pile is empty
    if (stockPile.isEmpty()) {
      // The stock pile is empty, end the game
      endGame();
    } else {
      // Draw a card from the top of the stock pile
      Card card = stockPile.pop();

      // Add the card to the player's hand
      currentPlayer.addCard(card);

      // Update the game state
      updateState();
    }
  }

  // adds a card to a specific spit pile
  public void addCardToPile(int pileIndex, Card card) {
    if (pileIndex < 0 || pileIndex >= NUM_SPIT_PILES) {
      throw new IllegalArgumentException("Invalid pile index");
    }
    spitPiles[pileIndex].getChildren().add(card);
  }

  // adds a card to the stock pile
  public void addCardToStock(Card card) {
    stockPile.getChildren().add(card);
  }

  // adds a card to the discard pile
  public void addCardToDiscard(Card card) {
    discardPile.getChildren().add(card);
  }

  // adds a yellow highlight to a specific spit pile to indicate which pile is
  // currently active or whose turn it is to play
  public void highlightPile(int pileIndex) {
    if (pileIndex < 0 || pileIndex >= NUM_SPIT_PILES) {
      throw new IllegalArgumentException("Invalid pile index");
    }
    Label highlight = new Label();
    highlight.setStyle("-fx-background-color: yellow;");
    highlight.setMinSize(spitPiles[pileIndex].getWidth(), spitPiles[pileIndex].getHeight());
    spitPiles[pileIndex].getChildren().add(highlight);
  }

  // removes the highlight from the pile.
  public void clearHighlight(int pileIndex) {
    if (pileIndex < 0 || pileIndex >= NUM_SPIT_PILES) {
      throw new IllegalArgumentException("Invalid pile index");
    }
    spitPiles[pileIndex].getChildren().remove(spitPiles[pileIndex].getChildren().size() - 1);
  }

  // Method for playing a card from the player's hand
public void playCard(int index) {
  // Get the card from the player's hand
  Card card = currentPlayer.getHand().get(index);

  // Check if the player can play the card on any of the spit piles
  for (int i = 0; i < 4; i++) {
    StackPane pile = spitPiles[i];
    if (pile.isEmpty() || card.compareTo(pile.peek()) > 0) {
      // The player can play the card on this spit pile, add the card to the pile
      addCardToPile(i, card);

      // Remove the card from the player's hand
      currentPlayer.getHand().remove(index);

      // Check if the cards on the spit piles are alternating sequences of the same suit
      if (spitPilesAlternating()) {
        // The cards on the spit piles are alternating sequences of the same suit, spit the cards
        spitCards();
      }

      // Update the game state
      updateState();

      // Return from the method
      return;
    }
  }

  // The player cannot play the card on any of the spit piles, add the card to the discard pile
  addCardToDiscard(card);

  // Remove the card from the player's hand
  currentPlayer.getHand().remove(index);

  // Update the game state
  updateState();
}

  // Method for handling the end of the game
  public void endGame() {
    // Display a message indicating the winner or loser
    if (currentPlayer == player1) {
      System.out.println("Player 2 wins!");
    } else {
      System.out.println("Player 1 wins!");
    }

    // Reset the game board and player hands
    resetGame();
  }

  // Method for updating the game state
  public void updateState() {
    // Update the visual display of the game board and player hands
    updateDisplay();

    // Check if the current player can play a card or spit the cards on the spit
    // piles
    if (!canPlayCard()) {
      // The current player cannot play a card, end their turn
      endTurn();
    }
  }

  // Method for updating the visual display of the game board and player hands
  private void updateDisplay() {
    // Clear the visual display of the spit piles, stock pile, and discard pile
    for (StackPane pile : spitPiles) {
      pile.getChildren().clear();
    }
    stockPile.getChildren().clear();
    discardPile.getChildren().clear();

    // Redraw the cards on the spit piles, stock pile, and discard pile
    for (StackPane pile : spitPiles) {
      for (Card card : pile) {
        pile.getChildren().add(card.getView());
      }
    }
    for (Card card : stockPile) {
      stockPile.getChildren().add(card.getView());
    }
    for (Card card : discardPile) {
      discardPile.getChildren().add(card.getView());
    }

    // Update the visual display of the player's hands
    player1.updateHand();
    player2.updateHand();
  }

  // Method for checking if the current player can play a card or spit the cards
  // on the spit piles
  private boolean canPlayCard() {
    // Check if the current player has any cards in their hand
    if (currentPlayer.getHand().isEmpty()) {
      // The current player has no cards in their hand, return false
      return false;
    }

    // Check if the current player can play a card on any of the spit piles
    for (int i = 0; i < 4; i++) {
      StackPane pile = spitPiles[i];
      if (pile.isEmpty() || currentPlayer.getHand().get(0).compareTo(pile.peek()) > 0) {
        // The current player can play a card on this spit pile, return true
        return true;
      }
    }

    // The current player cannot play a card on any of the spit piles, return false
    return false;
  }

  // Method for ending the current player's turn
  private void endTurn() {
    // Switch the current player
    if (currentPlayer == player1) {
      currentPlayer = player2;
    } else {
      currentPlayer = player1;
    }

    // Update the timer or score counter

    // Check if the game has ended
    if (gameEnded()) {
      // The game has ended, end the game
      endGame();
    } else {
      // The game has not ended, start the next player's turn
      startTurn();
    }
  }

  // Method for checking if the game has ended
  private boolean gameEnded() {
    // Check if one of the players has no cards in their hand and the stock pile is
    // empty
    return player1.getHand().isEmpty() && stockPile.isEmpty() || player2.getHand().isEmpty() && stockPile.isEmpty();
  }

}
