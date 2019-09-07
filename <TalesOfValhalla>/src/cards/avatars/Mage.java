package cards.avatars;
import cards.Avatar;
public class Mage extends Avatar {
	
	private static final int HEALTH = 15, DAMAGE = 4, RANGE = 4, MAX_MOVE = 3, MANA_COST = 100;
	private static final String uri = "imagedata/cards/avatars/mageCard.png", token = "imagedata/tokens/avatars/mageToken.png";
	
	public Mage(){
		super(uri, token, HEALTH, DAMAGE, RANGE, MAX_MOVE, MANA_COST);
	}
}