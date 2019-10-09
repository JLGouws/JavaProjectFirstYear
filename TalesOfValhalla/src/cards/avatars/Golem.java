package cards.avatars;

import cards.Avatar;

import player.Player;

import java.io.Serializable;

final public class Golem extends Avatar implements Serializable {
	
	private static final int HEALTH = 7, DAMAGE = 7, RANGE = 1, MAX_MOVE = 1, MANA_COST = 7, MANA_ATTACK_COST = 3;
	private static final String uri = "imagedata/cards/avatars/golemCard.png", token = "imagedata/tokens/avatars/golemToken.png";
	
	public Golem(Player player){
		super(player, uri, token, HEALTH, DAMAGE, RANGE, MAX_MOVE, MANA_COST, MANA_ATTACK_COST);
	}

	public Golem(){
		
	}

	/**
	 * Returns a new copy of this card.
	 *
	 * @return A copy of this card.
	 */
	public Golem getNew(){
		return new Golem(this.player);
	}
}