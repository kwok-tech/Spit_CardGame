package SpitCardGame;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Card extends StackPane {

  private static final int WIDTH = 75;
  private static final int HEIGHT = 100;

  private static final Color[] SUIT_COLORS = { Color.RED, Color.BLACK };
  private static final String[] SUIT_SYMBOLS = { "♠", "♥", "♣", "♦" };
  private static final String[] RANK_NAMES = {
    "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"
  };

  private int suit;
  private int rank;

  public Card(int suit, int rank) {
    this.suit = suit;
    this.rank = rank;

    setMinSize(WIDTH, HEIGHT);
    setMaxSize(WIDTH, HEIGHT);

    Rectangle cardBackground = new Rectangle(WIDTH, HEIGHT);
    cardBackground.setFill(SUIT_COLORS[suit % SUIT_COLORS.length]);
    cardBackground.setStroke(Color.BLACK);

    Text rankText = new Text(RANK_NAMES[rank]);
    rankText.setFont(Font.font(18));
    rankText.setFill(SUIT_COLORS[(suit + 1) % SUIT_COLORS.length]);

    Text suitText = new Text(SUIT_SYMBOLS[suit]);
    suitText.setFont(Font.font(36));
    suitText.setFill(SUIT_COLORS[(suit + 1) % SUIT_COLORS.length]);

    Label cardLabel = new Label(RANK_NAMES[rank]);
    cardLabel.setTextFill(SUIT_COLORS[(suit + 1) % SUIT_COLORS.length]);
    cardLabel.setFont(Font.font(18));

    getChildren().addAll(cardBackground, rankText, suitText, cardLabel);
  }

  public int getSuit() {
    return suit;
  }

  public int getRank() {
    return rank;
  }
}
