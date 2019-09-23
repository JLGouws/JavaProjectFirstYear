package graphics.pregame;

import processing.core.*;//to be able to create GUI

public class MenuDraw extends GameOptions {

	/**
	 * Driver method runs as an infinite loop.
	 */
	public void draw(){
		if(moveScreen) moveWindow();
		if(screenOn[0]) mainMenu();
		else if (screenOn[1]) drawGameOptions();
		drawWindowOptions();
		drawTitle();
	}
}