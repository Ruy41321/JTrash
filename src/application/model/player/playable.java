package application.model.player;

import application.model.mazzo.Carta;

/**
 * A class implements the playable interface to indicate they must specify a
 * method to play
 */
public interface playable {
	/**
	 * Method by which all the players play their turns
	 *
	 * @param c The card just draw
	 * @return card The card got from the hand after the play or Null if the card
	 *         cannot be place
	 */
	public Carta play(Carta c);
}
