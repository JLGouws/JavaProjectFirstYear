package graphics.game;

import processing.core.*;//to display graphics

import card.Card;//to be able to display cards

import player.Player;//to manage players

import java.util.ArrayList;// to keep track of cards

import board.Board;//playing board

import graphics.pregame.Menu;//to be able to go back to the menu

import graphics.GraphicsHandler;//because processing is a bit stupid

import java.util.Arrays;

public class Game extends GraphicsHandler {

	static final private double CARD_SPREAD_EXP = 1.2, CARD_ROTATION_EXP = 1.5;
	static final private float TOKEN_FILL = (float) 0.9;
	static final private int PLAYER0COLOUR = 0xFF2F74F4, PLAYER1COLOUR = 0xFFFF2800, BOARD_LIGHT = 0xFFFFE066, BOARD_DARK = 0xFF223300;//Don't know what the first FF is for but processing requires it
	static final private String CARD_BACK_IMAGE_URI= "imagedata/cards/cardBack.png", ICON_PATH = "imagedata/tokens/avatars/archerToken.png";//TODO: change the icon;
	static private float tileHeight, tileWidth, boardXOffset, boardYOffset;
	public int index;
	private Menu menu;
	private int height, width, xTokenSelected, yTokenSelected,cardWidth, cardHeight;//environment variables
	protected Player playerOne, playerTwo;
	private ArrayList<PImage> playerOneCards = new ArrayList<>(), playerTwoCards = new ArrayList<>();
	private float cardXpos, cardRot, downShift = 20, selectedCardXoffset, selectedCardYoffset, nexusRot = 0, nexusRotSpeed = (float) 0.1;
	private PImage curCardImage;
	private Card curCard;
	private boolean cardSelected = false, tokenSelected = false, drawnCardSelected;
	private cards.Avatar selectedAvatar;

	/**
	 * Construtor for PApplet
	 */
	public Game(){
		if(running.size() == 1){
			index = 0;
			playerOne = players[0];
			playerTwo = players[1];
		} else if(running.size() == 2){
			index = 1;
			playerOne = players[1];
			playerTwo = players[0];
		}
		running.add(this);
		menu = (Menu) running.get(0);//menu must be the first thing initialized.
	}

	/**
	 * Convenience constructor for the game. Its main use is for the AI class.
	 */
	public Game(Player player){
		players[1] = player;
		if(running.size() == 1){
			index = 0;
			playerOne = players[0];
			playerTwo = players[1];
		} else if(running.size() == 2){
			index = 1;
			playerOne = players[1];
			playerTwo = players[0];
		}
		running.add(this);
		menu = (Menu) running.get(0);//menu must be the first thing initialized.
	}

	/**
	 * Void settings sets up the processing environment.
	 */
	public void settings(){
		int screen = playerOne == players[1] ? 1 : 2;
		fullScreen(screen);
	}

	/**
	 * Setup method runs before draw method to set variables in the class.
	 */
	public void setup(){
		PImage icon = loadImage(ICON_PATH);//create icon
		surface.setIcon(icon);//change the icon 
		surface.setTitle("Tales Of Valhalla Game");
		//set up environment variables
		height = super.height;
		width = super.width;
		cardHeight = height/4;
		cardWidth = width/12;
		tileHeight = 3*cardHeight/7;
		tileWidth = cardWidth/(float) 1.035;
		boardXOffset = width/(float) 9.75;
		boardYOffset = height/(float) 8.9;
		//create background
		translate(0,downShift);//shift screen down
		PImage background = loadImage("imagedata/backGround/backing.jpg");
		image(background, 0, -downShift, super.width, super.height);
		//playerOne = new Player();
		playerOne.getHand().getCards().forEach(card -> playerOneCards.add(loadImage(card.URI)));//playerOneCards.add(loadImage(card.URI))
		drawBoard();
		//drawNexuses();
		//drawHand();
		capture();
	}

	/**
	 * Method that is called when the mouse button is pressed.
	 */
	public void mousePressed() {
		if(mouseX < width-width/15 + width/20 && mouseX > width-width/15 - width/20 && mouseY > downShift && mouseY < downShift + height/12){//menu clicked
				transitionToMenu();
		} else if (turnIndex == index){//is it this player's turn?
			int length = playerOneCards.size();
			float cardOffset = width/4*((float) -Math.pow(CARD_SPREAD_EXP, 1 -length) + 1) + cardWidth/2;
			if(mouseX > width-width/13  && mouseX < width && mouseY > height - cardHeight - height/12 + downShift && mouseY < height - cardHeight + downShift){
				handleEndTurn();
			}else if((length != 0 && mouseY > height - cardHeight/2 && Math.abs(mouseX - width/2) < cardOffset && !cardSelected)){//was a card clicked
				selectCard(length, cardOffset);
			} else if(isOnBoard() && !tokenSelected && !cardSelected){
				handleTokenSelection();
			} else if((isOnBoard() || ((boardYOffset + 3.5 * tileHeight - cardWidth < mouseY && mouseY < boardYOffset + 3.5 * tileHeight + cardWidth) && (mouseX < boardXOffset || mouseX > super.width - boardXOffset ) )) && tokenSelected ){
				handleTokenMove();
			}
			if(mouseX > super.width - cardWidth && mouseY > super.height - cardHeight) handleCardDrawn(); 
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
		if(turnIndex == index){
			if(!isOnBoard() && cardSelected){//cardSelected && mouseY > height - cardHeight/2 && mouseX > width/4 && mouseX < 3*width/4){//was a card released back to the cards
				replaceCard();//the card must be replaced
			}else if (cardSelected && isOnBoard()){
				int x = (int) ((mouseX - boardXOffset - selectedCardXoffset)/(tileWidth));
				int y = (int) ((mouseY - boardYOffset - selectedCardYoffset - downShift)/(tileHeight));
				if(curCard instanceof cards.Avatar && x != 0 && playerOne == players[0]){
					replaceCard();
					return;
				}else if(curCard instanceof cards.Avatar && x != 9 && playerOne == players[1]){
					replaceCard();
					return;
				} if(board.getBoard()[x][y] == null && playerOne.removeManaAndGetValid(((cards.Avatar) curCard).MANA_COST)){
					handlePlayCard(x, y);
				} else replaceCard();
			}
		}
	}

	/**
	 * Makes the game move to the menu.
	 */
	private void transitionToMenu(){
		menu.revive();//revive the menu
		surface.setVisible(false);//make the display go away
		((Game)running.get(1)).kill();
		((Game)running.get(2)).kill();
		running.remove(1);
		running.remove(1);
	}

	/**
	 * Selects card from the players hand.
	 */
	private void selectCard(int length, float cardOffset){
		tokenSelected = false;
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
	}

	/**
	 * Switches the players turn
	 */
	protected void handleEndTurn(){
		turnIndex = (turnIndex + 1) % 2;//switch turn index
		players[index].addMana(3);//increment this player's mana
	}

	/**
	 * Plays the selected card to the board.
	 *
	 * @param x the x position of the board that the card will be added to.
	 * @param y the y position of the board that the card will be added to.
	 */
	protected void handlePlayCard(int x, int y){
		curCard.xPos = x; /* set the coordinates of the card position */
		curCard.yPos = y;
		board.addCard(curCard, x, y);
		cardSelected = false;
		updateBoard[0] = true;
		updateBoard[1] = true;
	}

	/**
	 * Plays a given card from the board to the hand.
	 *
	 * @param i the index of the card in the hand that will be played
	 * @param x the x position of the board that the card will be added to.
	 * @param y the y position of the board that the card will be added to.
	 */
	protected void handlePlayCard(int i, int x, int y){
		curCard.xPos = x; /* set the coordinates of the card position */
		curCard.yPos = y;
		board.addCard(curCard, x, y);
		cardSelected = false;
		updateBoard[0] = true;
		updateBoard[1] = true;
	}

	/**
	 * Plays a given card from the board to the hand.
	 *
	 * @param c the card that will be played
	 * @param x the x position of the board that the card will be added to.
	 * @param y the y position of the board that the card will be added to.
	 */
	protected void handlePlayCard(Card c, int x, int y){
		c.xPos = x; /* set the coordinates of the card position */
		c.yPos = y;
		board.addCard(c, x, y);
		playerOne.getHand().removeCard(c);
		cardSelected = false;
		updateBoard[0] = true;
		updateBoard[1] = true;
	}

	/**
	 * Handles the selection of a token
	 */
	private void handleTokenSelection(){
		this.xTokenSelected = (int) ((mouseX - boardXOffset)/tileWidth);
		this.yTokenSelected = (int) ((mouseY - boardYOffset - downShift)/(tileHeight));
		Card selectedToken = board.getBoard()[xTokenSelected][yTokenSelected];
		if (selectedToken != null && selectedToken.player == playerOne){
			if(selectedToken instanceof cards.Avatar){//is this an Avatar
				selectedAvatar = (cards.Avatar) selectedToken;//convert the card to an avatar.
				tokenSelected = true;
			}else if (selectedToken == null){
				tokenSelected = false;
			}
		}
	}

	/**
	 * Handles the movement of a card on the board.
	 */
	private void handleTokenMove(){
		int x = (int) ((mouseX - boardXOffset)/(tileWidth)), y = (int) ((mouseY - boardYOffset- downShift)/(tileHeight)), dx = Math.abs(this.xTokenSelected - x), dy = Math.abs(this.yTokenSelected - y);
		Card selectedToken = board.getBoard()[this.xTokenSelected][this.yTokenSelected];
		int cost = ((cards.Avatar) selectedToken).MANA_MOVE_COST * (dx + dy);
		if((boardYOffset + 3.5 * tileHeight - cardWidth < mouseY && mouseY < boardYOffset + 3.5 * tileHeight + cardWidth) && (mouseX < boardXOffset || mouseX > super.width - boardXOffset )){
			if(mouseX <= boardXOffset) handleAvatarAttack(selectedToken,-1, 3);
			else handleAvatarAttack(selectedToken, 10, 3);
		}else if(0 <= x && x < 10 && 0 <= y && y < 7 && selectedToken instanceof cards.Avatar && 0 < dx + dy && dx + dy <= ((cards.Avatar) selectedToken).MAX_MOVE && this.board.getBoard()[x][y] == null && players[index].removeManaAndGetValid(cost)){//is this move valid?
			this.board.getBoard()[x][y] = selectedToken;
			this.board.getBoard()[this.xTokenSelected][this.yTokenSelected] = null;
			updateBoard[0] = true;
			updateBoard[1] = true;
		} else if(this.board.getBoard()[x][y] != null){
			handleAvatarAttack(selectedToken, x, y);
		}
		this.tokenSelected = false;
	}

	/**
	 * Handles a card being drawn from the deck
	 */
	private void handleCardDrawn(){
		cardSelected = true;//a card has been selected
		curCardImage = loadImage(CARD_BACK_IMAGE_URI);
		drawnCardSelected = true;
		this.selectedCardXoffset = mouseX - (super.width - cardWidth);
		this.selectedCardYoffset = mouseY - (super.height - cardHeight);
	}

	/**
	 * Makes an avatar do damage.
	 *
	 * @param attacker The avatar that is attacking anather avatar on the board.
	 * @param x the x position on the board that the attacker will attack.
	 * @param y the y position on the board that the attacker will attack.
	 */
	private void handleAvatarAttack(Card attacker, int x, int y){
		if(this.playerOne == players[0] && (10 - this.xTokenSelected) + Math.abs(3 - this.yTokenSelected) <= this.selectedAvatar.RANGE){
			if(x == 10 && y == 3 && playerOne.removeManaAndGetValid(((cards.Avatar) attacker).MANA_ATTACK_COST)) players[1].health -= ((cards.Avatar) attacker).DAMAGE;
		}else if(this.playerOne == players[1] && (this.xTokenSelected ) + Math.abs(3 - this.yTokenSelected) <= this.selectedAvatar.RANGE ){
			if(x == -1 && y == 3 && playerOne.removeManaAndGetValid(((cards.Avatar) attacker).MANA_ATTACK_COST)) players[0].health -= ((cards.Avatar) attacker).DAMAGE;
		} else {
			if (this.board.getBoard()[x][y] instanceof cards.Avatar && playerOne.removeManaAndGetValid(((cards.Avatar) attacker).MANA_ATTACK_COST)){
				cards.Avatar attacked = (cards.Avatar) this.board.getBoard()[x][y];//get the attacked avatar
				attacked.health -= ((cards.Avatar) attacker).DAMAGE;
				if (attacked.health <= 0){
					board.getBoard()[x][y] = null;
					attacked.player.getDeck().returnCard(attacked);
				}
				this.updateBoard[0] = true;//the board must be updated.
				this.updateBoard[1] = true;
			}
		}
		this.tokenSelected = false;//yes this is reduntant
	}

	/**
	 * Ends the game.
	 *
	 * @param winner An integer that represents the winner of the game.
	 */
	protected void handleEndGame(int winner){
		turnIndex = 42;
		PFont font = createFont("Dialog.plain", width/8);
		textFont(font);
		textAlign(CENTER, CENTER);
		text(winner == 1 ? "You da best": "You suck at \nthis game", width/2, height/2);//useful feedback
	}

	/**
	 * Method that returns the selected card to the hand.
	 */
	private void replaceCard(){
		int length = playerOneCards.size();
		float cardOffset = width/4*((float) -Math.pow(CARD_SPREAD_EXP, 1 -length) + 1) + cardWidth/2;
		int index = (int) (length*((mouseX - width/2 + cardOffset)/(2*cardOffset)));//quick maths
		if (drawnCardSelected){
			if(index < length && index >= 0){
				players[this.index].drawCard(index);
				playerOneCards.add(index, loadImage(playerOne.getHand().getCard(index).URI));//playerOneCards.add(loadImage(card.URI))	
			}
			drawnCardSelected = false;
		}else{
			if(index < length && index >= 0){
				playerOneCards.add(index, curCardImage);
				playerOne.getHand().addCard(curCard, index);	
			} else if(index >= length){
				playerOneCards.add(curCardImage);
				playerOne.getHand().addCard(curCard);	
			}else if(index < 0 ){
				playerOneCards.add(0, curCardImage);
				playerOne.getHand().addCard(curCard, 0);	
			}
		}
		cardSelected = false;
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
			//TODODONE?: make Cards rotate. Maybe only when there are more than three cards?
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
		noStroke();
		nexusRot = (nexusRot + nexusRotSpeed) % (2*PI);
		PImage nexusImage = loadImage("imagedata/nexusImage/funkySpiral.png");
		//draw player one nexus
		translate(0, boardYOffset);//x is alligned, but y is not.
		translate(cardWidth/2, ((float) 3.5) * tileHeight);
		rotate(nexusRot);
		fill(PLAYER0COLOUR);
		ellipse(0, 0, cardWidth, cardWidth);
		image(nexusImage, -cardWidth/2, -cardWidth/2, cardWidth, cardWidth);
		rotate(-nexusRot);
		translate(-cardWidth/2, -((float) 3.5) * tileHeight);
		//draw player two nexus
		translate(width - cardWidth/2, ((float) 3.5) * tileHeight);
		rotate(nexusRot);
		fill(PLAYER1COLOUR);
		ellipse(0, 0, cardWidth, cardWidth);
		image(nexusImage, -cardWidth/2, -cardWidth/2, cardWidth, cardWidth);
		rotate(-nexusRot);
		translate(-(width - cardWidth/2), -((float) 3.5) * tileHeight);
		translate(0, -boardYOffset);
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
		if (this.cardSelected) return (mouseX - selectedCardXoffset > boardXOffset && mouseX - selectedCardXoffset < (width - boardXOffset) && mouseY - selectedCardYoffset > downShift + boardYOffset && mouseY - selectedCardYoffset < downShift + boardYOffset + (7*tileHeight));
		else return (mouseX  > boardXOffset && mouseX  < (width - boardXOffset) && mouseY > downShift + boardYOffset && mouseY < downShift + boardYOffset + (7*tileHeight));
	}

	/**
	 * Highlights the square that the mouse is in.
	 */
	private void highlightSquare(){
		int x = (int) ((mouseX - boardXOffset - selectedCardXoffset)/(tileWidth));
		int y = (int) ((mouseY - boardYOffset - selectedCardYoffset - downShift)/(tileHeight));
		boolean shouldHighlight = false;//should the square be highlighted?
		if (curCard instanceof cards.Avatar && x == 0 && playerOne == players[0]){
			shouldHighlight = true;
		} else if (curCard instanceof cards.Avatar && x == 9 && playerOne == players[1]){
			shouldHighlight = true;
		}
		if (shouldHighlight && board.getBoard()[x][y] == null){
			translate(boardXOffset, boardYOffset);
			fill(255, 0, 0);
			rect((x)*tileWidth, y*tileHeight , tileWidth, tileHeight);
			if( ((x) % 2 == 0) != (y % 2 == 0)) fill(BOARD_DARK); //alternating colours
			else fill(BOARD_LIGHT);
			rect((x)*tileWidth + (tileWidth - (float) 0.9*tileWidth)/2, y*tileHeight + (tileHeight - (float) 0.9 * tileHeight)/2 , (float) 0.9 * tileWidth, (float) 0.9 * tileHeight);
			translate(-boardXOffset, - boardYOffset);
		}
	}

	/**
	 * Draws the option squares that avatar can move to.
	 */
	private void drawAvatarMove(){
		/* Naive code
		fill(128, 128, 128);
		for (int i = 1; i <= selectedAvatar.MAX_MOVE; i++ ) {
			if(this.yTokenSelected + i <= 7)ellipse((this.xTokenSelected + 1) * cardWidth + cardWidth/2, (this.yTokenSelected + i) * cardHeight/2  + cardHeight/4, cardWidth/2, cardWidth/2);//should this be drawn?
			if(this.yTokenSelected - i >= 0)ellipse((this.xTokenSelected + 1) * cardWidth + cardWidth/2, (this.yTokenSelected - i) * cardHeight/2  + cardHeight/4, cardWidth/2, cardWidth/2);
		}
		for (int i = 1; i <= selectedAvatar.MAX_MOVE; i++ ) {
			//TODO: maybe fix size
			if (this.xTokenSelected + i < 10 )ellipse((this.xTokenSelected + 1 + i) * cardWidth + cardWidth/2, (this.yTokenSelected) * cardHeight/2  + cardHeight/4, cardWidth/2, cardWidth/2);//should this be drawn?
			if (this.xTokenSelected - i >= 0 )ellipse((this.xTokenSelected + 1 - i) * cardWidth + cardWidth/2, (this.yTokenSelected) * cardHeight/2  + cardHeight/4, cardWidth/2, cardWidth/2);
			for (int j = 1; j <= selectedAvatar.MAX_MOVE - i; j++ ) {
				if (this.xTokenSelected + i < 10 ){
					if(this.yTokenSelected + j <= 7)ellipse((this.xTokenSelected + 1 + i) * cardWidth + cardWidth/2, (this.yTokenSelected + j) * cardHeight/2  + cardHeight/4, cardWidth/2, cardWidth/2);//should this be drawn?
					if(this.yTokenSelected - j >= 0)ellipse((this.xTokenSelected + 1 + i) * cardWidth + cardWidth/2, (this.yTokenSelected - j) * cardHeight/2  + cardHeight/4, cardWidth/2, cardWidth/2);
				}if (this.xTokenSelected - i >= 0 ){
					if(this.yTokenSelected + j <= 7)ellipse((this.xTokenSelected + 1 - i) * cardWidth + cardWidth/2, (this.yTokenSelected + j) * cardHeight/2  + cardHeight/4, cardWidth/2, cardWidth/2);
					if(this.yTokenSelected + j >= 0)ellipse((this.xTokenSelected + 1 - i) * cardWidth + cardWidth/2, (this.yTokenSelected - j) * cardHeight/2  + cardHeight/4, cardWidth/2, cardWidth/2);
				}
			}
		}*/	
		PFont font = createFont("Dialog.plain", cardWidth/8);
		textFont(font);
		textAlign(CENTER, CENTER);
		translate(boardXOffset, boardYOffset);
			for (int i = -selectedAvatar.MAX_MOVE; i <= selectedAvatar.MAX_MOVE; i++ ) {
				if(0 <= this.xTokenSelected + i && this.xTokenSelected + i < 10)for (int j = -selectedAvatar.MAX_MOVE + Math.abs(i) ; j <= selectedAvatar.MAX_MOVE - Math.abs(i); j++ ) {
					fill(0x88888888);
					if(0 <= this.yTokenSelected + j && this.yTokenSelected + j < 7 && !(i == 0 && j == 0) && board.getBoard()[this.xTokenSelected + i][this.yTokenSelected + j] == null){
						ellipse((this.xTokenSelected + i) * tileWidth + tileWidth/2, (this.yTokenSelected + j) * tileHeight  + tileHeight/2, tileWidth/2, tileHeight/2);//should this be drawn?
						fill(0xFF001399);
						text(selectedAvatar.MANA_MOVE_COST * (Math.abs(j) + Math.abs(i)), (this.xTokenSelected + i) * tileWidth + tileWidth/2, (this.yTokenSelected + j) * tileHeight + tileHeight/2);
					}
				}
			}
		translate(-boardXOffset, -boardYOffset);
	}

	/**
	 * Draws the option squares that avatar can move to.
	 */
	private void drawAvatarAttack(){
		PFont font = createFont("Dialog.plain", cardWidth/8);
		textFont(font);
		textAlign(CENTER, CENTER);
		for (int i = -selectedAvatar.RANGE; i <= selectedAvatar.RANGE; i++ ) {
			if(0 <= this.xTokenSelected + i && this.xTokenSelected + i < 10)for (int j = -selectedAvatar.RANGE + Math.abs(i) ; j <= selectedAvatar.RANGE - Math.abs(i); j++ ) {
				if(0 <= this.yTokenSelected + j && this.yTokenSelected + j < 7 && !(i == 0 && j == 0) && board.getBoard()[this.xTokenSelected + i][this.yTokenSelected + j] != null){
					translate(boardXOffset, boardYOffset);
					fill(0x88FF0000);
					ellipse((this.xTokenSelected + i) * tileWidth + tileWidth/2, (this.yTokenSelected + j) * tileHeight  + tileHeight/2, tileWidth/2, tileHeight/2);//should this be drawn?
					fill(0xFF001399);
					text(selectedAvatar.MANA_ATTACK_COST, (this.xTokenSelected + i) * tileWidth + tileWidth/2, (this.yTokenSelected + j) * tileHeight + tileHeight/2);
					translate(-boardXOffset, -boardYOffset);
				}
			}
		} if(this.playerOne == players[0]){
			if((10 - this.xTokenSelected) + Math.abs(3 - this.yTokenSelected) <= this.selectedAvatar.RANGE){
				fill(0x88FF0000);
				translate(width - cardWidth/2, boardYOffset + ((float) 3.5) * tileHeight);
				ellipse(0, 0, tileWidth/2, tileHeight/2);
				translate(-(width - cardWidth/2), -(boardYOffset + ((float) 3.5) * tileHeight));
			}
		}else if(this.playerOne == players[1]){
			if((this.xTokenSelected + 1) + Math.abs(3 - this.yTokenSelected) <= this.selectedAvatar.RANGE){
				fill(0x88FF0000);
				translate(cardWidth/2, boardYOffset + ((float) 3.5) * tileHeight);
				ellipse(0, 0, tileWidth/2, tileHeight/2);
				translate( - cardWidth/2, -(boardYOffset + ((float) 3.5) * tileHeight));
			}
		}
	}

	/**
	 * Captures the current display
	 */
	public void capture(){
		saveFrame("imagedata/frame/curFrame" + index + ".png");//stores the current frame as a png
	}

	/**
	 * Helper method that draws the selected card.
	 */
	private void drawSelectedCard(){
		if(isOnBoard())	highlightSquare();
		image(curCardImage, mouseX - selectedCardXoffset, mouseY - selectedCardYoffset, cardWidth, cardHeight);
	}

	/**
	 * Creates the playing board.
	 */
	public void drawBoard(){
		stroke(0);
		translate(boardXOffset, boardYOffset);
			Card[][] curBoard = board.getBoard();//store the current board so that the board doesn't have to be fetched again.
			for(int i = 0; i < 10; i++){
				for ( int j = 0 ; j < 7 ; j++ ) {
					if( (i % 2 == 0) != (j % 2 == 0)) fill(BOARD_DARK); //alternating colours
					else fill(BOARD_LIGHT);
					rect(i*tileWidth, j*tileHeight , tileWidth, tileHeight);
					if(curBoard[i][j] != null) {
						Card tmpCard = curBoard[i][j];
						if(tmpCard.player == playerOne) {
							if(index == 0) fill(PLAYER0COLOUR);
							else fill(PLAYER1COLOUR);
						}else{
							if(index == 0) fill(PLAYER1COLOUR);
							else fill(PLAYER0COLOUR);
						}					
						ellipse(((float) 0.5 + i)*tileWidth, ((float) 0.5 + j)*tileHeight, 3*tileWidth/4, 13*tileHeight/14);
						image(loadImage(tmpCard.TOKEN),(i + (1 - TOKEN_FILL)/2)*tileWidth, (j + (1 - TOKEN_FILL)/2)*tileHeight , TOKEN_FILL*tileWidth, TOKEN_FILL*tileHeight);

						PImage vitalImg = loadImage("imagedata/healthImages/" + ((cards.Avatar) tmpCard).health + ".png");/* load the image of the card's health */
						image(vitalImg, (i + (float) 5/6)*tileWidth , (j + (float) 3/4)*tileHeight, tileWidth/6, tileHeight/4);
						vitalImg = loadImage("imagedata/ADImages/" + ((cards.Avatar) tmpCard).DAMAGE + ".png");/* load the image of the card's attack */
						image(vitalImg, (i + (float) 1/6)*tileWidth , (j + (float) 3/4)*tileHeight, tileWidth/6, tileHeight/4);
					}
				}
			}
		translate(-boardXOffset, -boardYOffset);
	}

	/**
	 * Draws the mana and the health that the players have
	 */
	private void drawVitals(){
		fill(0, 255, 0);
		rect(cardWidth/16, (float)2.1*cardHeight, 7*cardWidth/8, cardHeight/8);
		rect(super.width - (15*cardWidth/16), (float)2.1*cardHeight, 7*cardWidth/8, cardHeight/8);
		fill(0x88001399);
		rect(cardWidth/16, (float) 2.35*cardHeight, 7*cardWidth/8, cardHeight/8);
		rect(super.width - (15*cardWidth/16), (float) 2.35*cardHeight, 7*cardWidth/8, cardHeight/8);
		
		fill(0, 255, 0);
		rect(cardWidth/16, (float)2.1*cardHeight, 7*cardWidth/8 * players[0].health/players[0].max_health, cardHeight/8);
		rect(super.width - (15*cardWidth/16), (float)2.1*cardHeight, 7*cardWidth/8 * players[1].health/players[1].max_health, cardHeight/8);
		fill(0xFF001399);
		rect(cardWidth/16, (float) 2.35*cardHeight, 7*cardWidth/8 * players[0].mana/players[0].max_mana, cardHeight/8);
		rect(super.width - (15*cardWidth/16), (float) 2.35*cardHeight, 7*cardWidth/8 * players[1].mana/players[1].max_mana, cardHeight/8);
	
		PFont font = createFont("Dialog.plain", cardHeight/8);
		fill(0xFFFFFFFF);
		textFont(font);
		textAlign(CENTER, CENTER);
		text("" + players[0].health + "/" + players[0].max_health, cardWidth/2, (float)2.15*cardHeight);
		text("" + players[1].health + "/" + players[1].max_health, super.width - cardWidth/2, (float)2.15*cardHeight);

		text("" + players[0].mana + "/" + players[0].max_mana, cardWidth/2, (float)2.4*cardHeight);
		text("" + players[1].mana + "/" + players[1].max_mana, super.width - cardWidth/2, (float)2.4*cardHeight);
	}	

	/**
	 * Draws the deck of cards so that the player can draw a card from the deck.
	 */
	private void drawDeck(){
		PImage cardImage = loadImage(CARD_BACK_IMAGE_URI);
		if(players[index].canDraw())image(cardImage, super.width - cardWidth, super.height - cardHeight, cardWidth, cardHeight);
	}

	/**
	 * Method that causes this object to close the object from drawing.
	 */
	public void kill(){
		noLoop();
		surface.setVisible(false);
	}

	/**
	 * Draws buttons on the screen for players to do some actions.
	 */
	public void drawButtons(){
		stroke(0);
		if(mouseX < width-width/15 + width/20 && mouseX > width-width/15 - width/20 && mouseY > downShift && mouseY < downShift + height/12) fill(0, 0, 0);
		else fill(255,255,255);//change colour if this is hovered
		ellipse(width-width/15 ,height/24 ,width/10 ,height/12 );
		PFont font = createFont("Dialog.plain",(float) width/100);
		textAlign(CENTER, CENTER);
		fill(0,0,255);
		textFont(font);
		text("Quit to menu.", width- width/15, height/24);

		if(mouseX > width-width/13  && mouseX < width && mouseY > height - cardHeight - height/12 + downShift && mouseY < height - cardHeight + downShift) fill(0, 0, 0);
		else fill(255,255,255);//change colour if this is hovered
		ellipse(width-width/26 ,height - cardHeight - height/24 ,width/13 ,height/12 );
		textAlign(CENTER, CENTER);
		fill(0,0,255);
		textFont(font);
		text("End Turn.", width- width/26, height - cardHeight - height/24);
	}

	/**
	 * A void method of processing, a game loop that repeats.
	 */
	public void draw(){
		translate(0,downShift);//shift screen down
		background(loadImage("imagedata/frame/curFrame" + index + ".png"));
		drawDeck();	

		drawButtons();

		if(updateBoard[index]){
			updateBoard[index] = false;
			drawBoard();
			capture();
		}
		drawNexuses();
		drawVitals();
		if (playerOneCards.size() != 0) drawHand();
		if (cardSelected) drawSelectedCard();
		if (tokenSelected) {
			drawAvatarMove();
			drawAvatarAttack();
		}
		if (playerOne.health <= 0) handleEndGame(0);
		if (playerTwo.health <= 0) handleEndGame(1);//haHaa you one the game
	}
}
