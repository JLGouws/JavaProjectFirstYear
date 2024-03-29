package cards.avatars;

import cards.Avatar;

import player.Player;

import java.io.Serializable;

final public class Mage extends Avatar implements Serializable {
	
	private static final int HEALTH = 4, DAMAGE = 4, RANGE = 4, MAX_MOVE = 3, MANA_COST = 6, MANA_ATTACK_COST = 3;
	private static final String uri = "imagedata/cards/avatars/mageCard.png", token = "imagedata/tokens/avatars/mageToken.png";
	
	public Mage(Player player){
		super(player, uri, token, HEALTH, DAMAGE, RANGE, MAX_MOVE, MANA_COST, MANA_ATTACK_COST);
	}

	public Mage(){
		
	}

	/**
	 * Returns a new copy of this card.
	 *
	 * @return A copy of this card.
	 */
	public Mage getNew(){
		return new Mage(this.player);
	}
}