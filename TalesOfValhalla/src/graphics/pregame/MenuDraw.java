package graphics.pregame;

import processing.core.*;//to be able to create GUI

public class MenuDraw extends MainMenu {

	/**
	 * Driver method runs as an infinite loop.
	 */
	public void draw(){
		if(moveScreen) moveWindow();
		mainMenu();
		drawWindowOptions();
	}
}