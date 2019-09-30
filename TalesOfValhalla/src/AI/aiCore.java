package AI;

/**
 * The AI class of the game.
 *
 * @author J L Gouws <jonathan.gouws@gmail.com>
 * @author 19G4436 <g19G4436@ru.ac.za> 
 */

import graphics.game.*;

import player.Player;

public class aiCore extends Game {

	/**
	 * Constructor for the AI class
	 */
	public aiCore(){
		super(new Player());
	}
	
	@Override
	public void setup(){
		surface.setVisible(false);
		super.setup();
	}

	public void draw(){
		if (turnIndex == super.index){
			handleEndTurn();
		}
	}
}