package cards;
import card.Card;
public class Avatar extends Card{
	public final int HEALTH, DAMAGE, RANGE, MAX_MOVE, MANA_COST;
	public Avatar(String uri, String token, int health, int damage, int range, int max_move, int mana_cost){
		super(uri, token);
		HEALTH = health;
		DAMAGE = damage;
		RANGE = range;
		MAX_MOVE = max_move;
		MANA_COST = mana_cost;
	}
}