package cards.avatars;

import cards.Avatar;

import player.Player; 

import java.io.Serializable;

public class Boar extends Avatar implements Serializable{
	
	private static final int HEALTH = 10, DAMAGE = 5, RANGE = 1, MAX_MOVE = 3, MANA_COST = 30;
	private static final String uri = "imagedata/cards/avatars/boarCard.png", token = "imagedata/tokens/avatars/boarToken.png";
	
	public Boar(Player player){
		super(player, uri, token, HEALTH, DAMAGE, RANGE, MAX_MOVE, MANA_COST);
	}

	public Boar(){
		
	}
}