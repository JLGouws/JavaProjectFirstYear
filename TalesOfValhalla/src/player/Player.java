package player;

import deck.*;

import card.Card;

import java.io.Serializable;

public class Player implements Serializable{

	static public final int MANA_CARD_DRAW_COST = 2; 
	private final String NAME;
	public int mana, health, max_health, max_mana;
	private Deck deck;
	private Hand hand;

	/**
	 * Constructor, initializes player
	 *
	 * @param name The name of the player that this deck belongs to.
	 */
	public Player(String name){
		NAME = name;
		deck = new Deck(this);
		hand = new Hand(deck);
		max_health = 20;
		max_mana = 12;
		mana = max_mana;
		health = max_health;
	}

	/**
	 * Constructor, initializes player
	 *
	 */
	public Player(){
		NAME = "NO-NAME9999999999999999999999999999999999999999999999999999999999999999999";
		deck = new Deck(this);
		hand = new Hand(deck);
		max_health = 20;
		max_mana = 12;
		mana = max_mana;
		health = max_health;
	}

	/**
	 * Accessor method for the mana field.
	 *
	 * @return The mana that the player has.
	 */
	public int getMana(){
		return this.mana;
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

	/**
	 * Adds mana to the player's current mana.
	 *
	 * @param mana the amount of mana that will be added to the players mana
	 */
	public void addMana(int mana){
		this.mana = this.mana + mana > max_mana ? max_mana : this.mana + mana; 
	}

	/**
	 * Removes mana to the player's current mana.
	 *
	 * @param mana the amount of mana that will be removed from the players mana.
	 *
	 * @return Returns if the move was valid.
	 */
	public boolean removeManaAndGetValid(int mana){
		if (this.mana - mana >= 0){
			this.mana -= mana;
			return true;
		}else return false;
	}

	/**
	 * A to string method, what more do you want?
	 *
	 * @return You guessed it, a String representation of a player, (really just their name).
	 */
	public String toString(){
		return this.NAME;
	}

	/**
	 * Helper method for the AI
	 * 
	 * @return The card that has the lowest mana cost of the cards in the player's hand.
	 */
	public Card getCheapestCard(){
		return hand.getCards().stream().reduce((x,y) -> x.PLAY_MANA_COST <= y.PLAY_MANA_COST ? x : y ).get();//get the lowest cost of a card in the player's hand.
	}

	/**
	 * Helper method for the AI
	 * 
	 * @return The mana cost of the cheapest card in the player's hand.
	 */
	public int getCheapest(){
		if(hand.getCards().size() > 0 )	return hand.getCards().stream().reduce((x,y) -> x.PLAY_MANA_COST <= y.PLAY_MANA_COST ? x : y ).get().PLAY_MANA_COST;//get the lowest cost of a card in the player's hand.
		return 2 * this.mana;
	}

	/**
	 * Accessor method for the player's deck.
	 *
	 * @return The players deck.
	 */
	public Deck getDeck(){
		return this.deck;
	}
}