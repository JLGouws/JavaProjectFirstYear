package cards.avatars;

import cards.Avatar;

import player.Player;

public class Golem extends Avatar {
	
	private static final int HEALTH = 30, DAMAGE = 1, RANGE = 1, MAX_MOVE = 1, MANA_COST = 100;
	private static final String uri = "imagedata/cards/avatars/golemCard.png", token = "imagedata/tokens/avatars/golemToken.png";
	
	public Golem(Player player){
		super(player, uri, token, HEALTH, DAMAGE, RANGE, MAX_MOVE, MANA_COST);
	}
}