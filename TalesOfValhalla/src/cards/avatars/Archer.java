package cards.avatars;

import cards.Avatar;

import player.Player; 

import java.io.Serializable;

public class Archer extends Avatar implements Serializable{
	
	private static final int HEALTH = 3, DAMAGE = 5, RANGE = 3, MAX_MOVE = 2, MANA_COST = 30;
	private static final String uri = "imagedata/cards/avatars/archerCard.png", token = "imagedata/tokens/avatars/archerToken.png";
	
	public Archer(Player player){
		super(player, uri, token, HEALTH, DAMAGE, RANGE, MAX_MOVE, MANA_COST);
	}

	public Archer(){
		
	}
}