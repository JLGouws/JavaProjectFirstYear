package graphics.pregame;

import processing.core.*;//to be able to create GUI

import graphics.GraphicsHandler;//to communicate to PApplet and the program.

import graphics.game.Game;//to be able to launch a game.

import java.util.Arrays;//for utility to print fonts when needed.

public class Menu extends GraphicsHandler{
	
	private int cols, rows, scl;
	private float focalLength = 1, flying = 0;
	private float[] coords = new float[3], rotCoords; // 3d coordinates
	private float[][] terrain;//height of each point

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
		//System.out.println(Arrays.toString(PFont.list()));
		rotate(PI);
		int height = 2000;
		int width = 2000;
		scl = 10;
		cols = width/scl;
		rows = height/scl;
		terrain = new float[cols][rows];
	}

	public void settings(){
		size(1600, 900);//size of screen
	}

	/**
	 * Performs action on mouse click.
	 */
	public void mousePressed() {
		if(mouseX < super.width/2 + 150 && mouseX > super.width/2 - 150 && mouseY > 2*super.height/3 - 42 && mouseY < 2*super.height/3 + 42){
			PApplet.main("graphics.game.Game");
			surface.setVisible(false);
			noLoop();
		}
	}

	/**
	 * Revives the menu, that is allows it to run again
	 */
	public void revive(){
		surface.setVisible(true);
		loop();
	}


	/**
	 * Game loop.
	 */
	private void mainMenu(){
		flying -= 0.1;

		float yoff = flying;
		for (int y = 0; y < rows; y++) {
		float xoff = 0;
	    		for (int x = 0; x < cols; x++) {
	      			terrain[x][y] = noise(xoff, yoff)*scl-20;
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
				rotCoords  = xRotate((float) (-PI/6));
				vertex(rotCoords[0]*scl*rotCoords[1]/20, scl*rotCoords[2]*rotCoords[1]/20);//draw triangle
				
				//if (x != cols){
				dist = (float) Math.sqrt(Math.pow(scl*(x-cols/2),2) + Math.pow(scl*y ,2));			 
				coords[0] = x - cols/2;//make x and y coordinates
				coords[1] = y ;
				coords[2] = terrain[x][y];
				rotCoords = xRotate(-PI/6);
				vertex(scl*rotCoords[0]*rotCoords[1]/20, scl*rotCoords[2]*rotCoords[1]/20);//draw triangle
				
				//vertex(scl*coords[0], scl*coords[1]);
				//vertex(scl*coords[0], scl*(coords[1]+1));
			}
			endShape();
		}
		
		if(mouseX < super.width/2 + 150 && mouseX > super.width/2 - 150 && mouseY > 2*super.height/3 - 42 && mouseY < 2*super.height/3 + 42)fill(0, 0, 0);
		else fill(255,255,255);
		//TODO: Instert cool title here.
		PFont font = createFont("Ani", 200);
		fill(0,0,0);
		textFont(font);
		text("Tales Of Valhalla", -750, -300);

		ellipse(0 ,0 ,300 ,74 );
		font = createFont("Ani", 42);
		fill(0,0,255);
		textFont(font);
		text("Play", -42, 11);
	}

	/**
	 * Driver method runs as an infinite loop.
	 */
	public void draw(){
		mainMenu();
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

