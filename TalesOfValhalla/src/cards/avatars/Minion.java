package cards.avatars;

import cards.Avatar;

import player.Player;

import java.io.Serializable;

public class Minion extends Avatar implements Serializable{
	
	private static final int HEALTH = 3, DAMAGE = 1, RANGE = 1, MAX_MOVE = 1, MANA_COST = 10;
	private static final String uri = "imagedata/cards/avatars/minionCard.png", token = "imagedata/tokens/avatars/minionToken.png";
	
	public Minion(Player player){
		super(player, uri, token, HEALTH, DAMAGE, RANGE, MAX_MOVE, MANA_COST);
	}

	public Minion(){
		
	}
}