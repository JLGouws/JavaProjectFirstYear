package graphics.pregame;

import processing.core.*;//to be able to create GUI

import graphics.GraphicsHandler;//to communicate to PApplet and the program.

public class MainMenu extends Menu {


	/**
	 * Game loop.
	 */
	protected void mainMenu(){
		drawTerrain();
		
		if(mouseX < windowWidth/2 + windowWidth/10 && mouseX > windowWidth/2 - windowWidth/10 && mouseY > 2*windowHeight/3 - windowHeight/24 && mouseY < 2*windowHeight/3 + windowHeight/24) fill(0, 0, 0);
		else fill(255,255,255);//change colour if this is hovered
		ellipse(0 ,0 ,windowWidth/5 ,windowHeight/12 );
		
		PFont font = createFont("Dialog.plain",(float) windowWidth/38);
		textAlign(CENTER, CENTER);
		fill(0,0,255);
		textFont(font);
		text("Play", 0, (float)-windowWidth/152);//shift up because centering looks off


		if(mouseX < windowWidth/2 + windowWidth/10 && mouseX > windowWidth/2 - windowWidth/10 && mouseY > 2*windowHeight/3 + windowHeight/8 - windowHeight/24 && mouseY < 2*windowHeight/3 + windowHeight/8 + windowHeight/24) fill(0, 0, 0);
		else fill(255,255,255);//change colour based on mouse position
		ellipse(0 ,windowHeight/8 ,windowWidth/5 ,windowHeight/12 );

		fill(0,0,255);
		text("Deck Builder", 0, (float) windowHeight/8 - (float) windowHeight/160);
	}


	/**
	 * Performs action on mouse if the click is on the main menu.
	 */
	protected void mouseClickedScreenZero(){
		if(mouseX < super.width/2 + 150 && mouseX > super.width/2 - 150 && mouseY > 2*super.height/3 - 42 && mouseY < 2*super.height/3 + 42){
			switchToGameOptions();
		}if(mouseX < windowWidth/2 + windowWidth/10 && mouseX > windowWidth/2 - windowWidth/10 && mouseY > 2*windowHeight/3 + windowHeight/8 - windowHeight/24 && mouseY < 2*windowHeight/3 + windowHeight/8 + windowHeight/24){
			switchToDeckBuilder();
		}
	}
}