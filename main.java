package SpitCardGame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Spit");

    // Create the menu bar
    MenuBar menuBar = new MenuBar();
    Menu gameMenu = new Menu("Game");
    MenuItem newGameItem = new MenuItem("New Game");
    MenuItem settingsItem = new MenuItem("Settings");
    MenuItem quitItem = new MenuItem("Quit");
    gameMenu.getItems().addAll(newGameItem, settingsItem, quitItem);
    menuBar.getMenus().add(gameMenu);

    // Create the main layout
    BorderPane root = new BorderPane();
    root.setTop(menuBar);

    // Create the game board
    GameBoard gameBoard = new GameBoard();
    root.setCenter(gameBoard);

    // Create the player's hand
    Hand hand = new Hand();
    root.setLeft(hand);
    // Create computer's hand
    Hand cHand = new Hand();
    root.setRight(cHand);

    // Create timer and score labels
    Label timerLabel = new Label("Timer: 0s");
    Label scoreLabel = new Label("Score: 0");

    // Add timer and score labels to a horizontal box
    HBox topBar = new HBox(timerLabel, scoreLabel);

    // Add the horizontal box to the top of the layout
    root.setTop(topBar);

    // Set the scene and show the stage
    primaryStage.setScene(new Scene(root, 800, 600));
    primaryStage.show();

    // Shuffle and deal the cards to the players
    List<Card> deck = createDeck();
    shuffleDeck(deck);
    dealCards(deck, gameBoard, hand, cHand);
  }

  /*
   * This function creates a new Card instance for each suit (0-3) and rank (0-12)
   * and adds it to the deck list. The Card constructor takes the suit and rank as
   * arguments,
   * and creates a new card with the corresponding suit and rank.
   * The createDeck function returns the deck list, which will contain 52 cards in
   * total (4 suits x 13 ranks).
   * You can then use this deck to shuffle and deal the cards to the players.
   */
  private List<Card> createDeck() {
    List<Card> deck = new ArrayList<>();
    for (int suit = 0; suit < 4; suit++) {
      for (int rank = 0; rank < 13; rank++) {
        Card card = new Card(suit, rank);
        deck.add(card);
      }
    }
    return deck;
  }

  /*
   * This function uses the Collections.shuffle method to shuffle the deck list
   * in-place.
   * The Collections.shuffle method uses the default random number generator to
   * randomly
   * rearrange the order of the elements in the list.
   */
  private void shuffleDeck(List<Card> deck) {
    Collections.shuffle(deck);
  }

  /*
   * This function uses a loop to iterate through the deck list and
   * add the cards to the player's hands.
   * The loop uses the modulo operator (%) to alternate between the two players.
   * If the index of the card (i) is even, the card is added to player 1's hand
   * using
   * the addCard method of the Hand class. If the index is odd, the card is added
   * to player 2's
   */
  private void dealCards(List<Card> deck, GameBoard gameBoard, Hand hand1, Hand hand2) {
    for (int i = 0; i < deck.size(); i++) {
      Card card = deck.get(i);
      if (i % 2 == 0) {
        // Deal card to player 1
        hand1.addCard(card);
      } else {
        // Deal card to player 2
        hand2.addCard(card);
      }
    }
  }

}
