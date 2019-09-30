package deck;

import java.util.Arrays;

import player.Player;

import card.Card;

public class TestDeck extends Deck{
	static public void main(String[] args) {
		Player p = new Player("JG69");//test player
		Deck d = new Deck(p);
		d.places = d.doNoRandomsShuffle(d.places);
		for(int i : d.places) System.out.print(i + " ");
		System.out.println();
		System.out.print(d.places.length);
		DeckIO.writeDeck(d);
		d = DeckIO.readDeck(p);
		for(Card c : d.getCards())System.out.println(c.URI);
	}
}