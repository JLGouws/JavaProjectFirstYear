package graphics.pregame;

import processing.core.*;//to be able to create GUI

import graphics.GraphicsHandler;//to communicate to PApplet and the program.

public class GameOptions extends EditDeck {

	/**
	 * Draws the game options screen.
	 */
	protected void drawGameOptions(){
		drawTerrain();
		if(mouseX < windowWidth/2 + 150 && mouseX > windowWidth/2 - 150 && mouseY > 2*windowHeight/3 - 42 && mouseY < 2*windowHeight/3 + 42) fill(0, 0, 0);
		else fill(255,255,255);
		ellipse(0 ,0 ,300 ,74 );
		
		PFont font = createFont("Dialog.plain", 21);
		fill(0,0,255);
		textFont(font);
		text("Play versus player", 0, 0);
	
		if(mouseX < windowWidth/2 + 150 && mouseX > windowWidth/2 - 150 && mouseY > 2*windowHeight/3 + windowHeight/8 - 42 && mouseY < 2*windowHeight/3 + windowHeight/8 + 42) fill(0, 0, 0);
		else fill(255,255,255);
		ellipse(0 ,windowHeight/8 ,300 ,74 );
		fill(0,0,255);
		textFont(font);
		text("Play versus A.I.", 0, windowHeight/8);
	}

	/**
	 * Performs action on mouse if the click is on the game options menu.
	 */
	protected void mouseClickedScreenOne(){
		if(mouseX < super.width/2 + 150 && mouseX > super.width/2 - 150 && mouseY > 2*super.height/3 - 42 && mouseY < 2*super.height/3 + 42){
			GraphicsHandler.setUpGameForTwoPlayer();
			PApplet.main("graphics.game.Game");
			PApplet.main("graphics.game.Game");
			surface.setVisible(false);
			noLoop();
		} else if(mouseX < windowWidth/2 + 150 && mouseX > windowWidth/2 - 150 && mouseY > 2*windowHeight/3 + windowHeight/8 - 42 && mouseY < 2*windowHeight/3 + windowHeight/8 + 42){
			GraphicsHandler.setUpGameForAi();
			PApplet.main("AI.aiCore");//have to start AI first
			PApplet.main("graphics.game.Game");
			surface.setVisible(false);
			noLoop();
		}
	}	
}