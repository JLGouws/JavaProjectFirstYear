package AI;

/**
 * The AI class of the game.
 *
 * @author J L Gouws <jonathan.gouws@gmail.com>
 * @author 19G4436 <g19G4436@ru.ac.za> 
 */

import graphics.game.*;

import player.Player;

import board.Board;

import card.Card;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.stream.Stream;

import cards.Avatar;

public class aiCore extends Game {
	private Card[] playerOnePlayedCards = new Card[70], playerTwoPlayedCards = new Card[70];
	private int playerOneNumCardsPlayed, playerTwoNumCardsPlayed;
	private int[] numCardsOnRank = new int[10];
	private int iterations = 0;
	int lowestManaMoveCost = -1;
	Card lowestManaMoveCard = null;

	/**
	 * Constructor for the AI class
	 */
	public aiCore(){
		super(new Player());
	}
	
	@Override
	/**
	 * Setup method to be called before draw.
	 */
	public void setup(){
		surface.setVisible(false);//thou shall not be seen
		super.setup();
	}

	/**
	 * Method that indicates if there is space for the player to play a card.
	 *
	 * @param curBoard The current playing board, an two dimensional array of cards
	 *
	 * @return A boolean that indicates if there is space to play card.
	 */
	private boolean isSpace(Card[][] curBoard){
		if(playerOne == players[0])	for (int k = 0 ; k < 7 ; k++ ) if (curBoard[0][k] == null) return true;
		else if (playerOne == players[1]) for (int l = 0 ; l < 7 ; l++ ) if (curBoard[9][l] == null) return true;
		return false; 
	}

	/**
	 * Method that returns the y coordinates of free squares on the board.
	 *
	 * @param curBoard The current playing board, an two dimensional array of cards
	 *
	 * @return A list of integers of the free squares in the board
	 */
	private ArrayList<Integer> getFreeSquares(Card[][] curBoard){
		ArrayList<Integer> returnable = new ArrayList<>();
		if(playerOne == players[0])	for (int k = 0 ; k < 7 ; k++ ) if (curBoard[0][k] == null) returnable.add(k);
		else if (playerOne == players[1]) for (int l = 0 ; l < 7 ; l++ ) if (curBoard[9][l] == null) returnable.add(l);
		return returnable; 
	}

	/**
	 * Method that returns the cheapest move cost of the cards that have been played by the AI.
	 *
	 * @return An integer of the mana cost of the card that has the lowest mana cost.
	 */
	private int getLowestMoveCost(){
		int selectRank = selectRank();
		Stream.of(playerOnePlayedCards).forEach(card -> {/* go through each card that the player has played. */
			if(card != null){
				if(card instanceof Avatar && card.xPos == selectRank){
					int moveCost = ((Avatar) card).MANA_MOVE_COST;
					if(lowestManaMoveCost == -1) lowestManaMoveCost = moveCost;
					else if (lowestManaMoveCost > ((Avatar) card).MANA_MOVE_COST) lowestManaMoveCost = moveCost;
				}
			}
		});
		return lowestManaMoveCost;
	}

	/**
	 * Method that selects the rank of the card that should be played.
	 *
	 * @return The integer of the rank that should be played.
	 */
	private int selectRank(){
		boolean highestRank = false;
		int selectRank = playerOne == players[0] ? 0 : 9;
		for (int i = numCardsOnRank.length - 1 ; i >= 0; i--) {
			if (numCardsOnRank[i] != 0 ) {
				highestRank = true;
				if(highestRank && numCardsOnRank[i] >=3 ){
					return i;
				}
			}
		}
		return selectRank;
	}

	/**
	 * Method that returns the card with the cheapest move cost of the cards that have been played by the AI.
	 *
	 * @return The played card that has the lowest mana cost.
	 */
	private Card getLowestMoveCard(){
		int selectRank = selectRank();
		Stream.of(playerOnePlayedCards).forEach(card -> {/* go through each card that the player has played. */
			if(card != null){
				if(card instanceof Avatar && card.xPos == selectRank){
					int moveCost = ((Avatar) card).MANA_MOVE_COST;
					if(lowestManaMoveCost == moveCost) lowestManaMoveCard = card;
				}
			}
		});
		return lowestManaMoveCard;
	}


	/**
	 * Method that decides if the AI can make a move or not.
	 *
	 * @param curBoard The current playing board, an two dimensional array of cards
	 * 
	 * @return a boolean that indicates true if the AI can make a move, otherwise false.
	 */
	private boolean canPlay(Card[][] curBoard){
		int manaPool = playerOne.getMana();
		if (manaPool > this.playerOne.getCheapest() && isSpace(curBoard) && playerOneNumCardsPlayed < 5){
			return true;
		}
		if (manaPool > Player.MANA_CARD_DRAW_COST && playerOne.getHand().getCards().size() <= 3){
			return true;
		} if (manaPool >= getLowestMoveCost()) return true; 
		return false;
	}

	/**
	 * Moves the cheapest card 
	 */
	private void moveCheapest(){
		Card cheapest = getLowestMoveCard();
		if(cheapest != null){
			if(playerOne == players[0]){
				if(cheapest.xPos < 7){
					handleTokenMove(cheapest, cheapest.xPos, cheapest.yPos, 1, 0);
				}
			}
		}
	}

	/**
	 * Sets the played cards up for the AI.
	 *
	 * @param curBoard The board of playing cards.
	 */
	private void setupPlayedCards(Card[][] curBoard){
		/* reset values */
		playerOnePlayedCards = new Card[70];
		playerTwoPlayedCards = new Card[70];
		playerOneNumCardsPlayed = 0;
		playerTwoNumCardsPlayed = 0; 
		numCardsOnRank = new int[10];
		/* go through board */
		for (int i = 0 ; i < 10 ; i++ ) {
			for (int j = 0 ; j < 7; j++ ) {
				/* is there a card there? */
				if(curBoard[i][j] != null){
					if(curBoard[i][j].player == playerOne) {
						playerOnePlayedCards[7*i + j] = curBoard[i][j];
						playerOneNumCardsPlayed++;
						numCardsOnRank[i]++;
					} else {
						playerTwoPlayedCards[7*i + j] = curBoard[i][j];
						playerTwoNumCardsPlayed++;
					}
				}
			}
		}
	}

	/**
	 * Method that allows the ai to draw a card;
	 */
	private void handleAIDrawCard(){
		playerOne.drawCard();
	}

	/**
	 * Method that looks for targets.
	 *
	 * @param c the card that is doing the attacking. 
	 * @param x the x coordinate of the card.
	 * @param y the y coordinate of the card.
	 * 
	 * @return An integer array of the target card.
	 */
	private int[] lookForTarget(Card c, int x , int y){
		if(c instanceof Avatar){
			Avatar looker = (Avatar) c;
			for (int i = -looker.RANGE; i <= looker.RANGE; i++ ) {
			if(0 <= x + i && x + i < 10)for (int j = -looker.RANGE + Math.abs(i) ; j <= looker.RANGE - Math.abs(i); j++ ) {
				if(0 <= y + j && y + j < 7 && !(i == 0 && j == 0) && board.getBoard()[x + i][y + j] != null){
					if (board.getBoard()[x+i][y+j].player != playerOne) return new int[] {x + i, y + j};
				}
			}
		} 
		}
		return null;
	}

	/**
	 * Handles the movement of a card on the board. This is only intended for AI use.
	 * This method is however left here, as it might find use here latere and is similar to the other methods of this class.
	 *
	 * @param c The card that must be moved.
	 * @param x The x position of the card.
	 * @param y The y position of the card.
	 * @param dx The change of the x position of the card.
	 * @param dy The change in the y position of the card.
	 */
	protected void handleTokenMove(Card c, int x, int y, int dx, int dy){
		int cost = ((cards.Avatar) c).MANA_MOVE_COST * (dx + dy);
		int[] coords = lookForTarget(c, x, y);
		if(coords != null && this.board.getBoard()[coords[0]][coords[1]] != null){
			System.out.println(c);
			System.out.println("x" + x);
			System.out.println("y" + y);
			System.out.println("Target x" + coords[0]);
			System.out.println("Target y" + coords[1]);
			handleAvatarAttack(c, x, y, coords[0], coords[1]);
		}else if(0 <= x + dx && x + dx < 10 && 0 <= y + dy && y + dy < 7 && c instanceof cards.Avatar && 0 < dx + dy && dx + dy <= ((cards.Avatar) c).MAX_MOVE && this.board.getBoard()[x + dx][y + dy] == null && players[index].removeManaAndGetValid(cost)){//is this move valid?
			this.board.getBoard()[x + dx][dy + y] = c;
			this.board.getBoard()[x][y] = null;
			updateBoard[0] = true;
			updateBoard[1] = true;
			c.xPos += dx; /* change the position of the card */
			c.yPos += dy;
		}
	}

	/**
	 * Makes an avatar do damage.
	 *
	 * @param attacker The avatar that is attacking anather avatar on the board.
	 * @param x the x position on the board of the attacking card.
	 * @param y the y position on the board of the attacking card.
	 */
	private void handleAvatarAttack(Card attacker, int x, int y, int targetX, int targetY){
		if(this.playerOne == players[0] && (10 - x) + Math.abs(3 - y) <= ((Avatar) attacker).RANGE){

		}else if(this.playerOne == players[1] && x + Math.abs(3 - y) <= ((Avatar) attacker).RANGE){
		} else{
			if (this.board.getBoard()[targetX][targetY] instanceof cards.Avatar){
				cards.Avatar attacked = (cards.Avatar) this.board.getBoard()[targetX][targetY];//get the attacked avatar
				if(attacked.player != attacker.player && playerOne.removeManaAndGetValid(((cards.Avatar) attacker).MANA_ATTACK_COST)){
					System.out.println("do damage");
					attacked.health -= ((cards.Avatar) attacker).DAMAGE;
					if (attacked.health <= 0){
						board.getBoard()[targetX][targetY] = null;
						this.updateBoard[0] = true;//the board must be updated.
						this.updateBoard[1] = true;
					}
				}
			}
		}
	}


	/**
	 * The method of the AI that allows the AI player to make a move.
	 */
	private void handleMakeMove(){
		System.out.println(playerOne.getMana());
		Card[][] cardsPlayed = board.getBoard();
		int manaPool = playerOne.getMana();
		iterations = 0;
		while (canPlay(cardsPlayed)){
			if(playerOne.getHand().getCards().size() == 0) handleAIDrawCard();
			if(playerOne.getHand().getCards().size() == 0) break;
			iterations++;
			setupPlayedCards(cardsPlayed);
			if (playerOne.getMana() > this.playerOne.getCheapest() && isSpace(cardsPlayed) && numCardsOnRank[9 * index] < 3){
				ArrayList<Integer> optionCoordinates = getFreeSquares(cardsPlayed); 
				int choice = (int) (Math.random() * optionCoordinates.size());
				Card playable = playerOne.getCheapestCard();
				if(playerOne.removeManaAndGetValid(((Avatar) playable).MANA_COST)) {
					if (playerOne == players[0]) handlePlayCard(playable, 0, optionCoordinates.get(choice));
					else if (playerOne == players[1]) handlePlayCard(playable, 9, optionCoordinates.get(choice));
				}
			} else if (manaPool > getLowestMoveCost()){
				moveCheapest();
			}
			if (iterations == 10000) break; /* to prevent any form of infinite loop, otherwise things get really ugly */
		}
	}

	/**
	 * Game loop for this method
	 */
	public void draw(){
		if (turnIndex == super.index){
			handleMakeMove();//makes the ai make a move
			handleEndTurn(); /* ends the players turn */
			System.out.println("ending turn");
		}
	}
}