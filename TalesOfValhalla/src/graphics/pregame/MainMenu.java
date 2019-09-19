package graphics.pregame;

import processing.core.*;//to be able to create GUI

import graphics.GraphicsHandler;//to communicate to PApplet and the program.

public class MainMenu extends Menu {

	private Menu menu;
	private float[] coords = new float[3], rotCoords;

	public MainMenu(Menu menu){
		//because java is stupid
		this.menu = menu;
		this.scl = menu.scl;
		this.rows = menu.rows;
		this.cols = menu.cols;
		this.windowWidth = menu.windowWidth;
		this.windowHeight = menu.windowHeight;
	}

	/**
	 * Game loop.
	 */
	protected void mainMenu(){
		flying -= 0.1;

		float yoff = flying;
		for (int y = 0; y < rows; y++) {
		float xoff = 0;
	    		for (int x = 0; x < cols; x++) {
	      			menu.terrain[x][y] = noise(xoff, yoff)*scl-20;
	      			xoff += 0.05;
	    		}
	    	yoff += 0.05;
	  	}


		//make array of triangles
		this.menu.fill(34, 51, 0);
		this.menu.stroke(0);//white lines
		//don't fill shapes
		this.menu.background(0, 15, 137);//phthalo blue background
		this.menu.translate(menu.windowWidth/2, 2*menu.windowHeight/3);//draw in the middle bottom

		for(int y = 2; y < rows; y++){
			menu.beginShape(TRIANGLE_STRIP);
			for(int x = 0; x < cols; x++){
				
				coords[0] = x - cols/2;//make x and y coordinates
				coords[1] = y - 1;// - rows/2;
				coords[2] = menu.terrain[x][y - 1];
				rotCoords  = xRotate(-PI/6);
				menu.vertex(rotCoords[0]*scl*rotCoords[1]/20, scl*rotCoords[2]*rotCoords[1]/20);//draw triangle

				coords[0] = x - cols/2;//make x and y coordinates
				coords[1] = y ;
				coords[2] = menu.terrain[x][y];
				rotCoords = xRotate(-PI/6);
				menu.vertex(scl*rotCoords[0]*rotCoords[1]/20, scl*rotCoords[2]*rotCoords[1]/20);//draw triangle
				/*try{
					Thread.sleep(500);
				}catch(Exception e){}*/
			}
			menu.endShape();
		}

		menu.ellipse(0 ,0 ,300 ,74 );
		
		if(menu.mouseX < windowWidth/2 + 150 && menu.mouseX > windowWidth/2 - 150 && menu.mouseY > 2*windowHeight/3 - 42 && menu.mouseY < 2*windowHeight/3 + 42) this.menu.fill(0, 0, 0);
		else this.menu.fill(255,255,255);
		this.menu.ellipse(0 ,0 ,300 ,74 );
		//TODO: Instert cool title here.
		PFont font = this.menu.createFont("Ani", windowWidth/8);
		this.menu.fill(0,0,0);
		this.menu.textFont(font);
		this.menu.textAlign(CENTER, CENTER);
		this.menu.text("Tales Of Valhalla", 0, -windowHeight/2);
		font = this.menu.createFont("Dialog.plain", 42);
		this.menu.fill(0,0,255);
		this.menu.textFont(font);
		this.menu.text("Play", 0, 0);
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