package graphics.pregame;

import processing.core.*;//to be able to create GUI

import graphics.GraphicsHandler;//to communicate to PApplet and the program.

import graphics.game.Game;//to be able to launch a game.

import java.util.Arrays;//for utility to print fonts when needed.

import java.awt.*;


public class Menu extends GraphicsHandler{

	static final private String ICON_PATH = "imagedata/tokens/avatars/archerToken.png";//TODO: change the icon
	protected int cols, rows, platformWidth, platformHeight, windowWidth, windowHeight, desktopXOffset, desktopYOffset, desktopMouseX = 0, desktopMouseY = 0;
	protected float focalLength = 1, flying = 0, scl;
	protected float[] coords = new float[3], rotCoords; // 3d coordinates
	protected float[][] terrain;//height of each point
	protected boolean moveScreen = false;
	protected boolean[] screenOn = new boolean[3];
	//private MainMenu mainMenu = new MainMenu();

	/**
	 * Default constructor for PApplet
	 */
	public Menu(){
		running.add(this);
	}
	
	/**
	 * Creates the display
	 */
	public void setup(){
		screenOn[0] = true;
		//printArray(PFont.list());
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();//get the dimensions of the platform
		platformWidth = (int) d.getWidth();
		platformHeight = (int) d.getHeight();
		windowWidth = 10*platformWidth/16;
		windowHeight = 10*platformHeight/16;
		desktopXOffset = (platformWidth - windowWidth)/2;
		desktopYOffset = (platformHeight - windowHeight)/2;
		setDisplayEnvironment();
		rotate(PI);
		scl = 10;
		cols = (int) (windowWidth/scl);
		rows = (int) (windowHeight/scl);
		terrain = new float[cols][rows];
	}

	/**
	 * Sets the display environment.
	 */
	public void settings(){
		fullScreen();//fullScreen mode 
	}

	private void setDisplayEnvironment(){
		surface.setResizable(true);
		surface.setSize(windowWidth, windowHeight);//don't make the the window take up the whole screen
		surface.setLocation(desktopXOffset, desktopYOffset);
		PImage icon = loadImage(ICON_PATH);//create icon
		surface.setIcon(icon);//change the icon 
	}

	/**
	 * Prepares the surface to be moved.
	 */
	protected void handleBeginMoveScreen(){
		moveScreen = true;
		desktopMouseX = mouseX + desktopXOffset;
		desktopMouseY = mouseY + desktopYOffset;
	}

	/**
	 * Moves the window.
	 */
	protected void moveWindow(){
		int dx = mouseX - desktopMouseX + desktopXOffset;//how much has the mouse moved by in the x direction?
		int dy = mouseY - desktopMouseY + desktopYOffset;//how much has the mouse moved by in the y direction?
		int dxsmall = 0, dysmall =0 ;
		System.out.println(dx + " " + dy);
		System.out.println();
		while (dxsmall <= Math.abs(dx) || dysmall <= Math.abs(dy) ){//smoothing
			surface.setLocation(desktopXOffset, desktopYOffset);
			if(dxsmall < Math.abs(dx)){
				desktopXOffset += (dx > 0 ? 1 : -1);
			}
			if(dysmall < Math.abs(dy)){
				desktopYOffset += (dy > 0 ? 1 : -1);
			}
			dxsmall++;//this caused several crashes. DO NOT REMOVE!
			dysmall++;
		}	
		desktopMouseX += dx;
		desktopMouseY += dy;	
	}

	/**
	 * Allows for the screen to be switched to the game options screen.
	 */
	protected void switchToGameOptions(){
		screenOn[0] = false;
		screenOn[1] = true;
	}

	/**
	 * Allows for the screen to be switched to the deckbuilder screen.
	 */
	protected void switchToDeckBuilder(){
		screenOn[0] = false;
		screenOn[2] = true;
	}

	/**
	 * Performs action on mouse release
	 */
	public void mouseReleased(){
		moveScreen = false;
	}

	/**
	 * Revives the menu, that is allows it to run again
	 */
	public void revive(){
		surface.setVisible(true);
		loop();
	}


	/**
	 * Draws the terrain in the background.
	 */
	protected void drawTerrain(){
		flying -= 0.1;

		float yoff = flying;
		for (int y = 0; y < rows; y++) {
		float xoff = 0;
	    		for (int x = 0; x < cols; x++) {
	      			terrain[x][y] = noise(xoff, yoff)*scl-20;//a psuedorandom number that is seeded by x offset and y offset
	      			xoff += 0.05;
	    		}
	    		yoff += 0.05;
	  	}


		//make array of triangles
		float dist = 0;
		fill(34, 51, 0);
		stroke(0);//white lines
		//don't fill shapes
		background(0, 15, 137);//phthalo blue background
		translate(super.width/2, 2*super.height/3);//draw in the middle bottom

		for(int y = 2; y < rows; y++){
			beginShape(TRIANGLE_STRIP);
			for(int x = 0; x < cols; x++){
				dist = (float) Math.sqrt(Math.pow(scl*(x-cols/2),2) + Math.pow(scl*(y -1) ,2));			
				coords[0] = x - cols/2;//make x and y coordinates
				coords[1] = y - 1;// - rows/2;
				coords[2] = terrain[x][y - 1];
				rotCoords  = xRotate(-PI/6);
				vertex(rotCoords[0]*scl*rotCoords[1]/20, scl*rotCoords[2]*rotCoords[1]/20);//draw triangle
				
				//if (x != cols){
				dist = (float) Math.sqrt(Math.pow(scl*(x-cols/2),2) + Math.pow(scl*y ,2));			 
				coords[0] = x - cols/2;//make x and y coordinates
				coords[1] = y ;
				coords[2] = terrain[x][y];
				rotCoords = xRotate(-PI/6);
				vertex(scl*rotCoords[0]*rotCoords[1]/20, scl*rotCoords[2]*rotCoords[1]/20);//draw triangle
				
			}
			endShape();
		}
	}

	/**
	 * Draws the title of the game.
	 */
	protected void drawTitle(){
		PFont font = createFont("Ani", windowWidth/8);
		fill(0,0,0);
		textFont(font);
		textAlign(CENTER, CENTER);
		text("Tales Of Valhalla", 0, -windowHeight/2);
	}

	/**
	 * Draws the window options
	 */
	protected void drawWindowOptions(){
		fill(0xFF888888);//grey 
		//TODO add X?
		ellipse(super.width/2  - super.width/138, -2*super.height/3 + super.width/138, super.width/69, super.width/69);
	}

	/**
	 * Rotates coordinates by a given angle
	 */
	private float[] xRotate(float x){
	
		float[][] rotX = new float[][] {new float[] {1, 		0, 		0}, // rotation matrix
						new float[] {0, (float) Math.cos(x), (float) - Math.sin(x)},
						new float[] {0, (float) Math.sin(x), (float) Math.cos(x) }};
		return matMul(coords, rotX);//multiply matrix by vector
	}

	/**
	 * Performs matrix multiplication
	 */
	private float[] matMul(float[] vector, float[][] matrix){
		float[] newVector = new float[matrix.length];
		for(int i = 0; i < newVector.length; i++){
			for(int	 j = 0; j < 3; j++){
				newVector[i] += vector[j]*matrix[j][i]; 
			}
		}
		return newVector;		
	}
}

