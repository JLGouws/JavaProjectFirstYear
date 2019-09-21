package graphics.pregame;

import processing.core.*;//to be able to create GUI

import graphics.GraphicsHandler;//to communicate to PApplet and the program.

public class MainMenu extends Menu {

	private float[] coords = new float[3], rotCoords;


	/**
	 * Game loop.
	 */
	protected void mainMenu(){
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
		fill(34, 51, 0);
		stroke(0);//white lines
		//don't fill shapes
		background(0, 15, 137);//phthalo blue background
		translate(windowWidth/2, 2*windowHeight/3);//draw in the middle bottom

		for(int y = 2; y < rows; y++){
			beginShape(TRIANGLE_STRIP);
			for(int x = 0; x < cols; x++){
				
				coords[0] = x - cols/2;//make x and y coordinates
				coords[1] = y - 1;// - rows/2;
				coords[2] = terrain[x][y - 1];
				rotCoords  = xRotate(-PI/6);
				vertex(rotCoords[0]*scl*rotCoords[1]/20, scl*rotCoords[2]*rotCoords[1]/20);//draw triangle

				coords[0] = x - cols/2;//make x and y coordinates
				coords[1] = y ;
				coords[2] = terrain[x][y];
				rotCoords = xRotate(-PI/6);
				vertex(scl*rotCoords[0]*rotCoords[1]/20, scl*rotCoords[2]*rotCoords[1]/20);//draw triangle
				/*try{
					Thread.sleep(500);
				}catch(Exception e){}*/
			}
			endShape();
		}

		ellipse(0 ,0 ,300 ,74 );
		
		if(mouseX < windowWidth/2 + 150 && mouseX > windowWidth/2 - 150 && mouseY > 2*windowHeight/3 - 42 && mouseY < 2*windowHeight/3 + 42) fill(0, 0, 0);
		else fill(255,255,255);
		ellipse(0 ,0 ,300 ,74 );
		//TODO: Instert cool title here.
		PFont font = createFont("Ani", windowWidth/8);
		fill(0,0,0);
		textFont(font);
		textAlign(CENTER, CENTER);
		text("Tales Of Valhalla", 0, -windowHeight/2);
		font = createFont("Dialog.plain", 42);
		fill(0,0,255);
		textFont(font);
		text("Play", 0, 0);
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