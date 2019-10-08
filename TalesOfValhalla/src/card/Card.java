package card;

import player.Player;

import java.io.*;//probably don't need all of this but anyway.

/**
 * The card class of the program. This potentially should be an Interface, but since it is already implemented I am reluctant to change it.
 */
public class Card implements Serializable {
	
	public int yPos, xPos;
	public final String URI, TOKEN;
	public Player player;
	public final int PLAY_MANA_COST;

	public Card(Player player, String uri, String token, int playManaCost){
		this.player = player;
		URI = uri;
		TOKEN = token;
		PLAY_MANA_COST = playManaCost;
	}

	/**
	 * Constructor used for serialization
	 */
	public Card(){
		URI = "";
		TOKEN = "";
		PLAY_MANA_COST = 0;
	}

	/**
	 * Returns a new copy of this card, only meant to be used when overloaded.
	 *
	 * @return A copy of this card.
	 */
	public Card getNew(){
		return new Card(new Player(), "", "", 0);
	}
}
