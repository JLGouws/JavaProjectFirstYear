package graphics.game;

import processing.core.*;//to display graphics

import card.Card;//to be able to display cards

import player.Player;//to manage players

import java.util.ArrayList;// to keep track of cards

import board.Board;//playing board

import graphics.pregame.Menu;//to be able to go back to the menu

import graphics.GraphicsHandler;//because processing is a bit stupid

public class Game extends GraphicsHandler {

	static final private double CARD_SPREAD_EXP = 1.5, CARD_ROTATION_EXP = 1.5;
	private Menu menu;
	private int height, width, xTokenSelected, yTokenSelected, cardWidth, cardHeight;//environment variables
	private Player playerOne, playerTwo;
	private ArrayList<PImage> playerOneCards = new ArrayList<>(), playerTwoCards = new ArrayList<>();
	private float cardXpos, cardRot, downShift = 20, selectedCardXoffset, selectedCardYoffset;
	private PImage curCardImage;
	private Card curCard;
	private boolean cardSelected = false, updateBoard = false, tokenSelected = false;
	private Board board = new Board();
	private cards.Avatar selectedAvatar;

	/**
	 * Construtor for PApplet
	 */
	public Game(){
		running.add(this);
		menu = (Menu) running.get(0);//menu must be the first thing initialized.
	}

	/**
	 * Void settings sets up the processing environment.
	 */
	public void settings(){
		size(1920,1080);
		fullScreen();
	}

	/**
	 *Setup method runs before draw method to set variables in the class.
	 */
	public void setup(){
		//set up environment variables
		height = super.height;
		width = super.width;
		cardHeight = height/4;
		cardWidth = width/12;
		//create background
		translate(0,downShift);//shift screen down
		background(0);
		playerOne = new Player();
		playerOne.getHand().getCards().forEach(card -> playerOneCards.add(loadImage(card.URI)));//playerOneCards.add(loadImage(card.URI))
		drawBoard();
		drawNexuses();
		//drawHand();
		capture();
	}

	/**
	 * Method that is called when the mouse button is pressed.
	 */
	public void mousePressed() {
		int length = playerOneCards.size();
		float cardOffset = width/4*((float) -Math.pow(CARD_SPREAD_EXP, 1 -length) + 1) + cardWidth/2;
		if(mouseX < 1920 && mouseX > 1820 && mouseY > 0 && mouseY < 100){//menu clicked
			menu.revive();//revive the menu
			surface.setVisible(false);//make the display go away
			noLoop();//stop draw
		} else if((length != 0 && mouseY > height - cardHeight/2 && Math.abs(mouseX - width/2) < cardOffset && !cardSelected)){//was a card clicked
			float cardPos = length*(mouseX - width/2 + cardOffset)/(2*cardOffset);//quick maths
			int index = (int) cardPos;//work out the index
			float cardRot = - PI/15*((float) -Math.pow(CARD_ROTATION_EXP, 1 - length) + 1) * ( 1 - 2 * index / length);
			selectedCardXoffset = (cardPos % 1) * 2 * cardOffset / length;
			selectedCardYoffset = mouseY - (height - cardHeight/2) - sin(Math.abs(cardRot)); 
			curCardImage = playerOneCards.get(index);
			curCard = playerOne.getHand().getCard(index);
			playerOneCards.remove(index);
			playerOne.getHand().removeCard(index);
			cardSelected = true;
		} else if(isOnBoard() && !cardSelected){
			this.xTokenSelected = (int) mouseX/(cardWidth) - 1;
			this.yTokenSelected = (int) (mouseY - downShift)/(cardHeight/2);
			Card selectedToken = board.getBoard()[xTokenSelected][yTokenSelected];
			if(selectedToken instanceof cards.Avatar){//is this an Avatar
				selectedAvatar = (cards.Avatar) selectedToken;//convert the card to an avatar.
				tokenSelected = true;
			}
		}
	}
	
	/**
	 * Method that is called when the mouse button is released.
	 */
	public void keyPressed(){
		if(key == ESC){
			System.exit(0);
		}
	}

	/**
	 * Method that is called when the mouse button is released.
	 */
	public void mouseReleased(){
		if(!isOnBoard()){//cardSelected && mouseY > height - cardHeight/2 && mouseX > width/4 && mouseX < 3*width/4){//was a card released back to the cards
			replaceCard();//the card must be replaced
		}else if (cardSelected && isOnBoard()){
			int x = (int) mouseX/(cardWidth) - 1;
			int y = (int) (mouseY - downShift)/(cardHeight/2);
			if(curCard instanceof cards.Avatar && x != 0){
				replaceCard();
				return;
			}
			board.addCard(curCard, x, y);
			cardSelected = false;
			updateBoard = true;
		}
	}

	/**
	 * Method that returns the selected card to the hand.
	 */
	private void replaceCard(){
		int length = playerOneCards.size();
		float cardOffset = width/4*((float) -Math.pow(CARD_SPREAD_EXP, 1 -length) + 1) + cardWidth/2;
		int index = (int) (length*((mouseX - width/2 + cardOffset)/(2*cardOffset)));//quick maths
		playerOneCards.add(index, curCardImage);
		playerOne.getHand().addCard(curCard, index);	
		cardSelected = false;
		drawHand();//redraw hand as it has changed
	}


	/**
	 * Creates the playing board.
	 */
	private void drawBoard(){
		Card[][] curBoard = board.getBoard();//store the current board so that the board doesn't have to be fetched again.
		for(int i = 1; i < 11; i++){
			for ( int j = 0 ; j < 7 ; j++ ) {
				if( (i % 2 == 0) != (j % 2 == 0)) fill(34, 51, 0); //alternating colours
				else fill(255, 224, 102);
				rect(i*cardWidth, j*cardHeight/2 , cardWidth, cardHeight/2);
				if(curBoard[i - 1][j] != null) {
					image(loadImage(curBoard[i - 1][j].TOKEN),i*cardWidth, j*cardHeight/2 , cardWidth, cardHeight/2);
				}
			}
		}
	}

	/**
	 *Draws the players hand.
	 */
	private void drawHand(){
		float length = playerOneCards.size();
		cardXpos = width/2 - width/4*((float) -Math.pow(CARD_SPREAD_EXP, 1 - length) + 1);//to approach some limiting value
		//System.out.println(cardXpos == width/2 + cardWidth/2);
		cardRot = - PI/15*((float) -Math.pow(CARD_ROTATION_EXP, 1 - length) + 1);//to approach some limiting value
		float fullRot = -cardRot*2;
		float fullXSpace =  width/2*((float) -Math.pow(CARD_SPREAD_EXP, 1 - length) + 1);
		playerOneCards.forEach(card -> {
			//TODO: make Cards rotate. Maybe only when there are more than three cards?
			translate(cardXpos, height + sin(Math.abs(cardRot))*cardWidth);
			rotate(cardRot);//rotate card
			image(card,-cardWidth/2, -cardHeight/2, cardWidth,cardHeight);//draw card at center of card
			rotate(-cardRot);//derotate card
			translate(-cardXpos, -height - sin(Math.abs(cardRot))*cardWidth);
			cardXposIncrement(length, fullXSpace);
			cardRotIncrement(length, fullRot);
		});
		//capture();
	}

	/**
	 *Draws the Nexuses of the board.
	 */
	private void drawNexuses(){
		fill(0, 0, 255);
		ellipse(cardWidth/2, ((float) 3.5) * cardHeight/2 , cardWidth, cardHeight/2);
		fill(255, 0, 0);
		ellipse(width - cardWidth/2, ((float) 3.5) * cardHeight/2 , cardWidth, cardHeight/2);
	}
	
	/**
	 * Increments the x position of the card and returns the value that it had before it was incremented.
	 *
	 * @param length A float for the number of cards that must be drawn.
	 *
	 * @return The value of cardXpos before it was incremented.
	 */
	private void cardXposIncrement(float length, float full){
		cardXpos += length != 1 ? full/(length -1):0;
	}

	/**
	 * Increments the angle that the card must be rotated by.
	 *
	 * @param length A  float for the number of cards that must be drawn.
	 */
	private void cardRotIncrement(float length, float full){
		cardRot += length != 1 ? full/(length - 1):0;
	}

	/**
	 * Method that indicates if the mouse is on the board or not
	 *
	 * @return A boolean that is true if the mouse is on the board otherwise false
	 */
	private boolean isOnBoard(){
		return (mouseX > cardWidth && mouseX < (width - cardWidth) && mouseY > downShift && mouseY < (7*cardHeight/2 ));
	}

	/**
	 * Highlights the square that the mouse is in.
	 */
	private void highlightSquare(){
		int x = (int) mouseX/(cardWidth) - 1;
		int y = (int) (mouseY - downShift)/(cardHeight/2);
		boolean shouldHighlight = false;//should the square be highlighted?
		if (curCard instanceof cards.Avatar && x == 0){
			shouldHighlight = true;
		} 
		if (shouldHighlight && board.getBoard()[x][y] == null){
			fill(255, 0, 0);
			rect((x+1)*cardWidth, y*cardHeight/2 , cardWidth, cardHeight/2);
			if( ((x+1) % 2 == 0) != (y % 2 == 0)) fill(34, 51, 0); //alternating colours
			else fill(255, 224, 102);
			rect((x+1)*cardWidth + (cardWidth - width/13)/2, y*cardHeight/2 + (cardHeight/2 - height/9)/2 , width/13, height/9);
		}
	}

	/**
	 * Draws the option squares that avatar can move to.
	 */
	private void drawTokenMove(){
		fill(128, 128, 128);
		for (int i = 1; i <= selectedAvatar.MAX_MOVE; i++ ) {
			//TODO: maybe fix size
			ellipse((this.xTokenSelected + 1 + i) * cardWidth + cardWidth/2, this.yTokenSelected * cardHeight/2 + height/18 + 30 , cardWidth/2, cardWidth/2);
		}
	}

	/**
	 * Captures the current display
	 */
	private void capture(){
		saveFrame("imagedata/frame/curFrame.png");//stores the current frame as a png
	}

	/**
	 * Helper method that draws the selected card.
	 */
	private void drawSelectedCard(){
		if(isOnBoard())	highlightSquare();
		System.out.println(selectedCardYoffset);
		image(curCardImage, mouseX - selectedCardXoffset, mouseY - selectedCardYoffset, cardWidth, cardHeight);
	}
		
	/**
	 * A void method of processing, a game loop that repeats.
	 */
	public void draw(){
		translate(0,20);//shift screen down
		background(loadImage("imagedata/frame/curFrame.png"));
		//drawNexuses();
		fill(255, 0, 0);
		rect(1820,0,100,100);

		if(updateBoard){
			updateBoard = false;
			drawBoard();
			capture();
		}
		if (playerOneCards.size() != 0) drawHand();
		if (cardSelected) drawSelectedCard();
		if (tokenSelected) drawTokenMove();
	}
}
