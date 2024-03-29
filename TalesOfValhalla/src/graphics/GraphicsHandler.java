package graphics;

import processing.core.*;

import graphics.pregame.*;

import graphics.game.*;

import java.util.ArrayList;

import player.Player;

import board.Board;//playing board

import card.Card;

public class GraphicsHandler extends PApplet{//abstract for shits and giggles
	static protected ArrayList<GraphicsHandler> running = new ArrayList<>();
	static protected Player[] players = new Player[2];
	static protected Board board;
	static protected boolean[] updateBoard = new boolean[]{false, false};
	static protected int turnIndex = 0;

	/**
	 * Constructor for game.
	 */
	static public void setUpGameForTwoPlayer(){
		players[0] = new Player();
		players[1] = new Player();
		board = new Board();
	}

	/**
	 * Constructor for game with one player against AI.
	 */
	static public void setUpGameForAi(){
		players[0] = new Player();
		board = new Board();
	}
}