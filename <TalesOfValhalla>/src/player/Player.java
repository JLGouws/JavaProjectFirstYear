package player;

import deck.*;

public class Player {
	private Deck deck;
	private Hand hand;
	public int mana, health, max_health, max_mana;

	/**
	* Constructor, initializes player
	*/
	public Player(){
		deck = new Deck(this);
		hand = new Hand(deck);
		max_health = 20;
		max_mana = 200;
		mana = 200;
		health = 20;
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
