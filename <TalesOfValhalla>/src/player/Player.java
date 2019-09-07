package player;

import deck.*;

public class Player {
	private Deck deck;
	private Hand hand;

	/**
	* Constructor, initializes player
	*/
	public Player(){
		deck = new Deck();
		hand = new Hand(deck);
	}
	
	/**
	 * Void method that draws a card from the deck.
	 */
	public void drawCard(){
		hand.addCard(deck.getNext());
	}

	/**
	 * Method that returns the hand of the player
	 *
	 * @return The hand of the player.
	 */
	public Hand getHand(){
		return hand;
	}
}
