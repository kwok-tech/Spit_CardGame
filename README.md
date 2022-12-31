# Spit Card Game
Spit is a fast-paced card game for two players. The goal of the game is to get rid of all of your cards before your opponent does.

# Gameplay
Each player is dealt a hand of cards, and there are four piles of cards called "spit" piles. The game begins with the players playing cards from their hand onto the spit piles. A player can play a card on a spit pile if the pile is empty or if the card has a higher rank than the top card on the pile. If a player cannot play a card, they must draw from the stock pile until they can play a card.

The game ends when one player runs out of cards in their hand and the stock pile is empty. The other player is the winner.

If a player plays a card that causes the spit piles to become alternating sequences of the same suit, the player can "spit" the cards on the spit piles into their hand and continue playing. If a player's hand becomes empty, they can draw from the spit piles until they can play a card.

# Implementation
This project is implemented in JavaFX, with a `Card` class to represent a playing card, a `Hand` class to represent a player's hand of cards, and a `GameBoard` class to represent the game board and handle the gameplay mechanics.

The game includes a timer to keep track of the elapsed time for each player's turn and a score counter to keep track of the score for each player. There is also an option for computer players, with AI implemented to handle their actions.

# How to play
To play the game, run the `Main` class. Use the menu bar at the top to start a new game or adjust the settings. Click on a card in your hand to play it, or click the "Draw" button to draw a card from the stock pile.

# Future improvements
Some potential improvements for this project include:

Adding more options to the settings menu, such as the ability to choose the number of cards dealt to each player or the ability to turn on or off the timer and score counter.
Improving the AI for computer players to make more strategic decisions.
Adding a visual indicator to show which pile is currently active or whose turn it is to play.
Adding the ability to sort or rearrange the cards in the player's hand.
Adding additional game modes, such as a single player mode or a multiplayer mode for more than two players.
