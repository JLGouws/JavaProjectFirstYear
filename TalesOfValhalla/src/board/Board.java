package board;

import card.Card;

public class Board {
	
	private Card[][] board = new Card[10][7];

	/**
	 * Constructor instantiates a board.
	 */
	public Board(){

	}

	/**
	 * A method that returns the playing board as a two dimensional array of cards.
	 *
	 * @return A two dimensional array of playing cards.
	 */
	public Card[][] getBoard(){
		return board;
	}

	/**
	 * Adds card to the board at the given coordinates.
	 *
	 * @param card A Card that must be added to the board.
	 * @param X The x position (horizontal) that the card must be added at.
	 * @param Y The y position (vertical) that the card must be added at.
	 */
	public void addCard(Card card, int X, int Y){
		board[X][Y] =  card;
	}
}