package deck;

public class TestDeck extends Deck{
	static public void main(String[] args) {
		Deck d = new Deck();
		d.overHandShuffle();
		for(int i : d.places) System.out.print(i + " ");
		System.out.println();
		System.out.print(d.places.length);
	}
}