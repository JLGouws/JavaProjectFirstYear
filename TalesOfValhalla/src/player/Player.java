package player;

import deck.*;

import java.io.Serializable;

public class Player implements Serializable{

	static private final int MANA_CARD_DRAW_COST = 40; 
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
		max_mana = 200;
		mana = 200;
		health = 20;
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
}