package card;

import player.Player;

public class Card{
	
	public int yPos, xPos;
	public final String URI, TOKEN;
	public Player player;

	public Card(Player player, String uri, String token){
		this.player = player;
		URI = uri;
		TOKEN = token;
	}
}
