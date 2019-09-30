package graphics.pregame;

import processing.core.*;//to be able to create GUI

import graphics.GraphicsHandler;//to communicate to PApplet and the program.

public class MenuDraw extends GameOptions {

	/**
	 * Driver method runs as an infinite loop.
	 */
	public void draw(){
		if(moveScreen) moveWindow();
		if(screenOn[0]) mainMenu();
		else if (screenOn[1]) drawGameOptions();
		else if (screenOn[2]) drawDeckBuilder();
		drawWindowOptions();
		drawTitle();
	}

	/**
	 * Performs action on mouse click.
	 */
	public void mousePressed() {
		if (Math.pow((mouseX - (super.width - super.width/138)), 2) + Math.pow(mouseY - super.width/138, 2) < Math.pow(super.width/138, 2)) exit();
		else if(mouseY < 100)handleBeginMoveScreen();//make screen moveable
		if(screenOn[0]) mouseClickedScreenZero();
		else if (screenOn[1]) mouseClickedScreenOne();
		else if (screenOn[2]) mouseClickedScreenTwo();
	}
}