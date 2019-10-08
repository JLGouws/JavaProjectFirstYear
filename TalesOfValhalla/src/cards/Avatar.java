package cards;

import card.Card;

import player.Player;

import java.io.Serializable;

public class Avatar extends Card{
	
	public final int HEALTH, DAMAGE, RANGE, MAX_MOVE, MANA_COST, MANA_MOVE_COST, MANA_ATTACK_COST;
	public int health;

	public Avatar(Player player, String uri, String token, int health, int damage, int range, int max_move, int mana_cost, int mana_attack_cost){
		super(player, uri, token, mana_cost);
		HEALTH = health;
		this.health = health;
		DAMAGE = damage;
		RANGE = range;
		MAX_MOVE = max_move;
		MANA_COST = mana_cost;
		MANA_MOVE_COST = MANA_COST;
		MANA_ATTACK_COST = mana_attack_cost;
	}

	public Avatar(){
		HEALTH = 0;
		DAMAGE = 0;
		RANGE = 0;
		MAX_MOVE = 0;
		MANA_COST = 0;
		MANA_MOVE_COST =0;
		MANA_ATTACK_COST = 0;	
	}
}