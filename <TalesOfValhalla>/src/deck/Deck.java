package deck;

import cards.avatars.*;
import card.Card;

public class Deck{

	private Card[] cards = new Card[25];//the cards that can be played.
	private int curIndex = 0; 

	/**
	 * Constructer, initialises the cards
	 */
	public Deck(){
		for (int i = 0; i < 25; i++) {
			if(i < 5) cards[i] = new Archer();
			else if(i >= 5 && i < 10) cards[i] = new Boar();
			else if(i >= 10 && i < 15) cards[i] = new Golem();
			else if(i >= 15 && i < 20) cards[i] = new Mage();
			else if( i >= 20 && i < 25) cards[i] = new Minion();	
		}
		this.randomShuffle();
	}

	/**
	 *returns the next card in the deck.
	 *
	 * @return the next card in the deck.
	 */
	public Card getNext(){
		curIndex = curIndex < 24 ? curIndex + 1: 0;
		return cards[curIndex - 1];
	}

	/**
	 * Moves each card in the deck to a random position
	 */
	private void randomShuffle(){
		int random; //stores a temporary random number.
		Card[] cardsCopy = new Card[25];
		int[] indices = new int[25], choices = new int[25];
		//create integers from 1 to n-1 to select from later
		for (int i = 0 ; i < 25 ; i++ ) {
			choices[i] = i;
			cardsCopy[i] = cards[i];
		}

		//add numbers to each place in array
		for (int i = 0; i < 25 ; i++) {
			random = (int) (Math.random()* (25-i));
			indices[i] = choices[random];//add random number to array
			for (int j = random; j < 25 - i - 1; j++) choices[j] = choices[j + 1];//gets rid of integer just chosen
		}
		for(int i = 0; i < 25; i++){
			cards[i] = cardsCopy[indices[i]];
		}				
	}

}
