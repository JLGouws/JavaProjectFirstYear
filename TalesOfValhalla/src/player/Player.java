package player;

import deck.*;

public class Player {
	private Deck deck;
	private Hand hand;
	static private final int MANA_CARD_DRAW_COST = 40; 
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
		if(mana - MANA_CARD_DRAW_COST >= 0){
			hand.addCard(deck.getNext(true));
			mana -= MANA_CARD_DRAW_COST;
		}
	}

	/**
	 * Void method that draws a card from the deck and puts it into the deck at a given index.
	 *
	 * @param index the index which the card must be added at.
	 */
	public void drawCard(int index){
		if(mana - MANA_CARD_DRAW_COST >= 0){
			hand.addCard(deck.getNext(true), index);
			mana -= MANA_CARD_DRAW_COST;
		}
	}

	/**
	 * Method that returns the hand of the player
	 *
	 * @return The hand of the player.
	 */
	public Hand getHand(){
		return hand;
	}

	/**
	 * A method that returns a boolean of whether there is a card for the player to draw or not.
	 *
	 * @return A boolean of whether any more cards can be drawn or not.
	 */
	public boolean canDraw(){
		return deck.getNext(false) != null;
	}
}
