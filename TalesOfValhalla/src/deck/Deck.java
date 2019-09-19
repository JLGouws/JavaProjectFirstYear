package deck;

import cards.avatars.*;//

import card.Card;//the deck has cards

import player.Player;

import java.util.Arrays;//because I am lazy

import java.io.*;

public class Deck implements Serializable {

	static private final int DECK_SIZE = 30;
	private Card[] baseCards = new Card[30], cards = new Card[30];//the cards that can be played.
	private int curIndex = 0; 
	private int[] places = new int[DECK_SIZE];
	private double entropy;

	/**
	 * Constructor, initialises the baseCards
	 */
	public Deck(Player player){
		setUpDeck(player);
		while(entropy < 4.2){
			riffleShuffle();
			findEntropy();
		}
		buildDeck();
	}

	/**
	 * Constructor for testing
	 */
	public Deck(){;
		Player player = new Player();
		setUpDeck(player);
		this.fisherYatesShuffle();
		while(entropy < 4.2 ){
			riffleShuffle();
			findEntropy();
		}
		buildDeck();
	}

	/**
	 * Builds the deck from the places array
	 */
	private void buildDeck(){
		for(int i = 0 ; i < DECK_SIZE ; i++ ) cards[i] = baseCards[places[i]]; 
	}

	/**
	 * Sets the deck up for a player.
	 * @param plaer The player that this deck is for.
	 *
	 */
	private void setUpDeck(Player player){
		for (int i = 0; i < 30; i++) {
			if(i < 6) baseCards[i] = new Archer(player);
			else if(i >= 6 && i < 12) baseCards[i] = new Boar(player);
			else if(i >= 12 && i < 18) baseCards[i] = new Golem(player);
			else if(i >= 18 && i < 24) baseCards[i] = new Mage(player);
			else if( i >= 24 && i < 30) baseCards[i] = new Minion(player);	
		}
		for (int i = 0 ; i < DECK_SIZE ; i++ ) places[i] = i;
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

	
	/*protected void randomShuffle(){
		int random; //stores a temporary random number.
		Card[] baseCardsCopy = new Card[25];
		int[] indices = new int[25], choices = new int[25];
		//create integers from 1 to n-1 to select from later
		for (int i = 0 ; i < 25 ; i++ ) {
			choices[i] = i;
			baseCardsCopy[i] = baseCards[i];
		}

		//add numbers to each place in array
		for (int i = 0; i < 25 ; i++) {
			random = (int) (Math.random()* (25-i));
			indices[i] = choices[random];//add random number to array
			for (int j = random; j < 25 - i - 1; j++) choices[j] = choices[j + 1];//gets rid of integer just chosen
		}
		for(int i = 0; i < 25; i++){
			baseCards[i] = baseCardsCopy[indices[i]];
		}				
	}*/

	/**
	 * Calculates the shannon entropy of a given array of indices.
	 *
	 * @param places the positions of the baseCards in the deck.
	 */
	protected void findEntropy(){
		entropy = 0;//reset entropy
		int[] diffs = new int[DECK_SIZE];//reset the diffs
		Arrays.stream(places).reduce((first, second) -> {
			diffs[second - first > 0 ? second - first - 1: DECK_SIZE - 1 + second - first]++;//increment differences
			return second;//to keep comparing
		});
		diffs[places[0] - places[DECK_SIZE - 1] > 0 ? places[0] - places[DECK_SIZE - 1] : DECK_SIZE - 1 + places[0] - places[DECK_SIZE - 1]]++;//last ones
		Arrays.stream(diffs).forEach(unnormProb -> {
		entropy += unnormProb == 0 ? 0:-( (double) unnormProb/DECK_SIZE)*Math.log( (double) unnormProb/DECK_SIZE)/Math.log(2.0);});//add entropy factor
	}

	/**
	 * Shuffles the deck with the fisher yates algorithm.
	 */
	protected void fisherYatesShuffle(){
		int random;//to hold the random number generated
		int[] choices = new int[DECK_SIZE];
		//create integers from 1 to n-1 to select from later
		for (int i = 0 ; i < DECK_SIZE ; i++ ) {//n
			choices[i] = i;
		}
		//add numbers to each place in array
		for (int i = 0; i < DECK_SIZE ; i++) {
			random = (int) (Math.random()* (DECK_SIZE-i));
			places[i] = choices[random];//add random number to array
			for (int j = random; j < DECK_SIZE - i - 1; j++) {//n(n+1)/2
				choices[j] = choices[j + 1];//gets rid of integer just chosen
			}
		}
	}

	/**
	 * Shuffles the deck with an algorithm that models a riffle shuffle.
	 */
	protected void riffleShuffle(){
		int split = (int) (Math.random() * 11), i = DECK_SIZE - 1, half;//useful numbers
		int[] halfCounter = {DECK_SIZE - 1, DECK_SIZE/2 - 5 + split};//index counters
		int[] placesCopy = places.clone();
		while(halfCounter[0] > DECK_SIZE/2 - 5 + split && halfCounter[1] >= 0){//go through array
			half = (int) Math.round(Math.random());//choose a half
			places[i] = placesCopy[halfCounter[half]];//add the corresponding element
			halfCounter[half]--;//decrease half counter
			i--;
		}
		if (halfCounter[1] > 0) for (int j = halfCounter[1]; j >= 0 ; j-- ) {//rest of numbers
			places[i] = placesCopy[j];
			i--;
		} else if (halfCounter[0] > DECK_SIZE/2 - 5 + split ) for (int j = halfCounter[0]; j > DECK_SIZE/2 - 5 + split ; j-- ) {//rest of numbers
			places[i] = placesCopy[j];
			i--;
		}
	}
}
