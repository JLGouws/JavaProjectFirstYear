package deck;
/**
 * The class that writes a deck to a file
 *
 * @author J L Gouws <jonathan.gouws@gmail.com>
 * @author 19G4436 <g19G4436@ru.ac.za>
 */

import cards.avatars.*;//

import card.Card;//the deck has cards

import player.Player;

import java.util.Arrays;//because I am lazy

import java.io.*;

abstract public class DeckIO{//abstract because I can.
	
	/**
	 * Writes the given deck to a file
	 *
	 * @param deck the deck that mus be written to a file
	 */
	static public void writeDeck(Deck deck){
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;

		try {

			fout = new FileOutputStream("deckPersistence/" + deck.getPlayer() + "sDeck.ser");// I was going to put an apostrophe here, but don't know if windows can handle that.
			oos = new ObjectOutputStream(fout);
			//System.out.println(deck);
			oos.writeObject(deck);

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * Reads a deck in for a given Player
 	 *
	 * @param player The player that this deck belongs to.
	 */
	static public Deck readDeck(Player player) {

		Deck deck = null;

		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try {

			fin = new FileInputStream("deckPersistence/" + player + "sDeck.ser");
			ois = new ObjectInputStream(fin);
			deck = (Deck) ois.readObject();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return deck;
	}

	/**
	 * Reads a deck in for a given Player's name
	 *
	 * @param player the name of the player that must have their deck found.
	 */
	static public Deck readDeck(String player) {

		Deck deck = null;

		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try {

			fin = new FileInputStream("deckPersistence/" + player + "sDeck.ser");
			ois = new ObjectInputStream(fin);
			deck = (Deck) ois.readObject();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return deck;
	}
}