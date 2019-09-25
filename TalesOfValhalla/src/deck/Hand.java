package deck;

import java.util.ArrayList;

import card.Card;

import java.io.Serializable;

public class Hand implements Serializable{
	
	private ArrayList<Card> cards = new ArrayList<>();

	/**
	 * Constructor method for the Hand, takes the first five cards of the deck.
	 *
	 * @param deck A deck of cards that the first five cards come from.
	 */
	public Hand(Deck deck){
		for(int i = 0; i < 5; i++){
			cards.add(deck.getNext(true));
		}
	}

	/**
	 * Accessor method for the cards in the hand.
	 *
	 * @return The cards in the hand.
	 */
	public ArrayList<Card> getCards(){
		return cards;
	}

	/**
	 * Method that adds a card to the hand.
	 *
	 * @param card A Card that will be added to the hand.
	 */
	public void addCard(Card card){
		cards.add(card);
	}

	/**
	 * Method that adds a card to the hand at the given index.
	 *
	 * @param card A Card that will be added to the hand.
	 * @param i The index where the card will be replaced.
	 */
	public void addCard(Card card, int i){
		cards.add(i, card);
	}

	/**
	 * Method that removes a card to the hand.
	 *
	 * @param index The index that the card must be removed from.
	 */
	public void removeCard(int index){
		cards.remove(index);
	}

	/**
	 * Method that gets a card from the hand at a specified index.
	 *
	 * @param i An Integer for the index of the card that is wanted.
	 */
	public Card getCard(int i){
		return cards.get(i);
	}

	/**
	 * Method that gets the last card in the hand
	 *
	 * @return the last card in the hand.
	 */
	public Card getLast(){
		return cards.get(cards.size()-1);
	}
}
