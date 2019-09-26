package graphics.pregame;

import processing.core.*;//to be able to create GUI

import graphics.GraphicsHandler;//to communicate to PApplet and the program.

import deck.DeckIO;

public class EditDeck extends MainMenu {

	/**
	 * Draws this screen.
	 */
	protected void drawDeckBuilder(){
		drawTerrain();
		drawTitle();
	}

	/**
	 * Performs actions for mouse clicks on this screen.
	 */
	protected void mouseClickedScreenTwo(){

	}
}