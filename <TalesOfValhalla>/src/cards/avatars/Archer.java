package cards.avatars;
import cards.Avatar;
public class Archer extends Avatar{
	
	private static final int HEALTH = 3, DAMAGE = 5, RANGE = 3, MAX_MOVE = 2, MANA_COST = 30;
	private static final String uri = "imagedata/cards/avatars/archerCard.png", token = "imagedata/tokens/avatars/archerToken.png";
	
	public Archer(){
		super(uri, token, HEALTH, DAMAGE, RANGE, MAX_MOVE, MANA_COST);
	}
}