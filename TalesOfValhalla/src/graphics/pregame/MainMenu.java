package graphics.pregame;

import processing.core.*;//to be able to create GUI

import graphics.GraphicsHandler;//to communicate to PApplet and the program.

public class MainMenu extends Menu {


	/**
	 * Game loop.
	 */
	protected void mainMenu(){
		drawTerrain();
		
		if(mouseX < windowWidth/2 + 150 && mouseX > windowWidth/2 - 150 && mouseY > 2*windowHeight/3 - 42 && mouseY < 2*windowHeight/3 + 42) fill(0, 0, 0);
		else fill(255,255,255);
		ellipse(0 ,0 ,300 ,74 );
		
		PFont font = createFont("Dialog.plain", 42);
		fill(0,0,255);
		textFont(font);
		text("Play", 0, 0);
	}
}