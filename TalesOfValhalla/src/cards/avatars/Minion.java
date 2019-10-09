package cards.avatars;

import cards.Avatar;

import player.Player;

import java.io.Serializable;

final public class Minion extends Avatar implements Serializable{
	
	private static final int HEALTH = 1, DAMAGE = 1, RANGE = 1, MAX_MOVE = 1, MANA_COST = 1, MANA_ATTACK_COST = 1;
	private static final String uri = "imagedata/cards/avatars/minionCard.png", token = "imagedata/tokens/avatars/minionToken.png";
	
	public Minion(Player player){
		super(player, uri, token, HEALTH, DAMAGE, RANGE, MAX_MOVE, MANA_COST, MANA_ATTACK_COST);
	}

	public Minion(){
		
	}

	/**
	 * Returns a new copy of this card.
	 *
	 * @return A copy of this card.
	 */
	public Minion getNew(){
		return new Minion(this.player);
	}
}