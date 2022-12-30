package SpitCardGame;

import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.input.TransferMode;

public class Hand extends SplitPane {

  private static final int WIDTH = 300;
  private static final int HEIGHT = 600;

  private CardPile cards;
  private ButtonPane buttons;

  public Hand() {
    setOrientation(Orientation.VERTICAL);
    setMinSize(WIDTH, HEIGHT);
    setMaxSize(WIDTH, HEIGHT);

    cards = new CardPile();
    cards.setOnDragOver(event -> {
      if (event.getGestureSource() != cards && event.getDragboard().hasContent(Card.CARD_DATA_FORMAT)) {
        event.acceptTransferModes(TransferMode.MOVE);
      }
      event.consume();
    });
    cards.setOnDragDropped(event -> {
      Card card = (Card)event.getDragboard().getContent(Card.CARD_DATA_FORMAT);
      getChildren().remove(card);
      event.setDropCompleted(true);
      event.consume();
    });

    buttons = new ButtonPane();
    buttons.setSortButtonAction(event -> sortCards());

    getItems().addAll(cards, buttons);
  }

  public void addCard(Card card) {
    cards.getChildren().add(card);
  }

  public void sortCards() {
    // Sort the cards by suit, then rank
    cards.getChildren().sort((card1, card2) -> {
      Card c1 = (Card)card1;
      Card c2 = (Card)card2;
      if (c1.getSuit() != c2.getSuit()) {
        return c1.getSuit() - c2.getSuit();
      }
      return c1.getRank() - c2.getRank();
    });
  }

}
