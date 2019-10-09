package cards.avatars;

import cards.Avatar;

import player.Player; 

import java.io.Serializable;

final public class Boar extends Avatar implements Serializable{
	
	private static final int HEALTH = 2, DAMAGE = 4, RANGE = 1, MAX_MOVE = 3, MANA_COST = 3, MANA_ATTACK_COST = 2;
	private static final String uri = "imagedata/cards/avatars/boarCard.png", token = "imagedata/tokens/avatars/boarToken.png";
	
	public Boar(Player player){
		super(player, uri, token, HEALTH, DAMAGE, RANGE, MAX_MOVE, MANA_COST, MANA_ATTACK_COST);
	}

	public Boar(){
		
	}

	/**
	 * Returns a new copy of this card.
	 *
	 * @return A copy of this card.
	 */
	public Boar getNew(){
		return new Boar(this.player);
	}
}