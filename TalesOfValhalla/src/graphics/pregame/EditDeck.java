package graphics.pregame;

import processing.core.*;//to be able to create GUI

import graphics.GraphicsHandler;//to communicate to PApplet and the program.

import deck.DeckIO;

import deck.Deck;

import card.Card;

import cards.avatars.*;

import java.awt.event.MouseEvent;

import java.util.*;

import player.Player;

/**
 * @author Thabo Sifumba
 */
public class EditDeck extends MainMenu {
	Player player1= new Player(), player2 = new Player();
	private Card[] temp = new Card [5];
	Card selected = null;
	private Deck deck = new Deck(player1);
	boolean[] onScreen = {true, false, false};
	private Card[] cards = deck.getBaseCards();

	/**  windowWidth/2, windowHeight/2
	 * Questions which deck to edit / player one or player tow
	 */
	protected void drawOptionsBuilder(){
		//drawTerrain();
		
		
		translate(windowWidth/2, windowHeight/2);

		if(mouseX < windowWidth/2 + 150 && mouseX > windowWidth/2 - 150 && mouseY > windowHeight/2 - 42 && mouseY < windowHeight/2 + 42) fill(0, 0, 0);
		else fill(255,255,255);
		ellipse(0 ,0 ,300 ,74 );

		PFont font = createFont("Dialog.plain", 21);
		fill(0,0,255);
		textFont(font);
		text("Player 1", 0, 0);
	
		if(mouseX < windowWidth/2 + 150 && mouseX > windowWidth/2 - 150 && mouseY > windowHeight/2 + windowHeight/8 - 42 && mouseY < windowHeight/2 + windowHeight/8 + 42) fill(0, 0, 0);
		else fill(255,255,255);
		ellipse(0 ,windowHeight/8 ,300 ,74 );
		fill(0,0,255);
		textFont(font);
		text("Player 2", 0, windowHeight/8);
		translate(-windowWidth/2, -windowHeight/2);
	} // pick player method

	protected void backGround(){
		PImage curCardImage = loadImage("imagedata/title/back.png");// this is the code changed ( path , but relative addressing "imagedata/title/")
		image(curCardImage, 0, 0 , windowWidth, windowHeight );
	} // method for background

	protected void cardSlot(){
		PImage curCardImage = loadImage("imagedata/cards/avatars/archerCard.png");// this is the code changed ( path , but relative addressing "imagedata/title/")
		image(curCardImage, 0, windowHeight/2, windowWidth/ 8, windowHeight/ 4 );

		curCardImage = loadImage("imagedata/cards/avatars/boarCard.png");// this is the code changed ( path , but relative addressing "imagedata/title/")
		image(curCardImage, windowWidth/5, windowHeight/2, windowWidth/ 8, windowHeight/ 4 );

		curCardImage = loadImage("imagedata/cards/avatars/golemCard.png");// this is the code changed ( path , but relative addressing "imagedata/title/")
		image(curCardImage, windowWidth*2/5, windowHeight/2, windowWidth/ 8, windowHeight/ 4 );

		curCardImage = loadImage("imagedata/cards/avatars/mageCard.png");// this is the code changed ( path , but relative addressing "imagedata/title/")
		image(curCardImage, windowWidth*3/5, windowHeight/2, windowWidth/ 8, windowHeight/ 4 );

		curCardImage = loadImage("imagedata/cards/avatars/minionCard.png");// this is the code changed ( path , but relative addressing "imagedata/title/")
		image(curCardImage, windowWidth*4/5, windowHeight/2, windowWidth/ 8, windowHeight/ 4 );

	} // show image of 5 base cards
	/**
	 * Draws this screen.
	 */ 
	protected void drawDeckBuilder(){
		//drawTerrain();
		backGround();

		if(onScreen[0]) drawOptionsBuilder();
		if(onScreen[1]) startrecord1();
		if(onScreen[2]) startrecord2();
		//setUpDeck();
		//drawTitle();
	}

	protected void createDeck(){
		if (mouseX < windowWidth/2 + 150 && mouseX > windowWidth/2 - 150 && mouseY > 2*windowHeight/3 - 42 && mouseY < 2*windowHeight/3 + 42){
			Deck deck = new Deck();

		}

	}

	private void drawCards(){
		int i = 0;
		for (Card c: cards) {
			PImage img = loadImage(c.URI);
			image(img , i * windowWidth/ 30, 0, windowWidth/ 8, windowHeight/ 4 );
			i++;
			
		}
	}

	protected void startrecord1(){
		backGround();
		drawCards();
		cardSlot();
		drawOptionLeave();
		

	}

	protected void startrecord2(){
		backGround();
		drawCards();
		cardSlot();
		drawOptionLeave();
	}
	/**
	 * Performs actions for mouse clicks on this screen. , now move to drawDeckBuilder
	 */
	protected void mouseClickedScreenTwo(){
		//System.out.println(mouseX);
		//System.out.println(mouseY);
		/*System.out.println((int) (mouseY/(windowHeight/30)));
		System.out.println((int) (mouseX/(windowWidth/30)));  // for where each index of the basedeck is spread out */
		if(onScreen[0] && mouseX < super.width/2 + 150 && mouseX > super.width/2 - 150 && mouseY > super.height/2 - 42 && mouseY < super.height/2 + 42){
			onScreen[0] = false;
			onScreen[1] = true;
		} else if(onScreen[0] && mouseX < windowWidth/2 + 150 && mouseX > windowWidth/2 - 150 && mouseY > windowHeight/2 + windowHeight/8 - 42 && mouseY < windowHeight/2 + windowHeight/8 + 42){
			onScreen[0] = false;
			onScreen[2] = true;
		} if(mouseX < windowWidth*4/5 + windowWidth/10 && mouseX > windowWidth*4/5 - windowWidth/10 && mouseY < 11*windowHeight/12 + windowHeight/24 && mouseY > 11*windowHeight/12 - windowHeight/24 ){
			screenOn[0] = true;
			screenOn[2] = false;
			DeckIO.writeDeck(deck);
		}
		if(selected == null)mousePick();
		else mousePlace();
	}
	    /*
	    * mouseX < windowWidth/2 + windowWidth/10 && mouseX > windowWidth/2 - windowWidth/10 && mouseY > 2*windowHeight/3 - windowHeight/24 && mouseY < 2*windowHeight/3 + windowHeight/24
	    * fix co ords for fill
		*/

	protected void drawOptionLeave(){

		if(mouseX < windowWidth*4/5 + windowWidth/10 && mouseX > windowWidth*4/5 - windowWidth/10 && mouseY < 11*windowHeight/12 + windowHeight/24 && mouseY > 11*windowHeight/12 - windowHeight/24 )fill(0, 0, 0);
		else fill(255,255,255);//change colour if this is hovered
		ellipse((windowWidth*4/5),  (windowHeight*11/12) ,windowWidth/5 ,windowHeight/12 );
		
		PFont font = createFont("Dialog.plain",(float) windowWidth/38);
		textAlign(CENTER, CENTER);
		fill(0,0,255);
		textFont(font);
		text("Menu", windowWidth*4/5,  (windowHeight*11/12));

		
	}// this should be for leaving deck builder, but co ords and actaully leave not there
	

	/*
	*these numbers in the if and else statements are using the System.out.println(mouseX); and the one for mouseY
	*not the System.out.println((int) (mouseX/(windowidth/30))); and also the mouseY one 
	*/

	protected void mousePick(){
		if(mouseX < 133 && mouseX > 13 && mouseY < 489 && mouseY > 357){
			selected = new Archer(onScreen[1] ? player1: player2);
		}
		
		else if (mouseX < 374 && mouseX > 255 && mouseY < 489 && mouseY > 357){
			selected = new Boar(onScreen[1] ? player1: player2);
		}
		
		else if (mouseX < 614 && mouseX > 496 && mouseY < 489 && mouseY > 357){
			selected = new Golem(onScreen[1] ? player1: player2);
		}
		else if (mouseX < 854 && mouseX > 736 && mouseY < 489 && mouseY > 357){
			selected = new Mage(onScreen[1] ? player1: player2);
		}
		else if (mouseX < 1094 && mouseX > 976 && mouseY < 489 && mouseY > 357){
			selected = new Minion(onScreen[1] ? player1: player2);
		}
	
	} // method should be for 5 base cards selected 
	
	protected void mousePlace(){
		cards[(int) (mouseX/(windowWidth/30))] = selected.getNew();
		selected = null;
	} // method should be for cardsss 30  of them , to take on the value of temp from above method 
	
}
