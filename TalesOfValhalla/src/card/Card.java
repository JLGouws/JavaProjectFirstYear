package card;

import player.Player;

import java.io.*;//probably don't need all of this but anyway.

public class Card implements Serializable {
	
	public int yPos, xPos;
	public final String URI, TOKEN;
	public Player player;

	public Card(Player player, String uri, String token){
		this.player = player;
		URI = uri;
		TOKEN = token;
	}

	public Card(){
		URI = "";
		TOKEN = "";
	}
}
