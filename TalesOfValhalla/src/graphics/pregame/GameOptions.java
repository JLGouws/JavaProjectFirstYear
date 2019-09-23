package graphics.pregame;

import processing.core.*;//to be able to create GUI

public class GameOptions extends MainMenu {
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
}