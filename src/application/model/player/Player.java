package application.model.player;

import application.model.mazzo.Carta;

/**
 * Abstract class to extend to get the methods to handle the game
 *
 * @author Luigi Pennisi
 */
public abstract class Player {

	/** Nick of the player */
	private String name;
	/** Hand of the player */
	private transient Mano mano;
	/** variable to know how many card the player deserve */
	private int cardNumber = 10;

	/**
	 * Create a new Player using the name given
	 *
	 * @param name The name of the User or Npc
	 */
	public Player(String name) {
		this.name = name;
		this.mano = new Mano();
	}

	/**
	 * Setting the mano (the hand) with the array of Carta given as parameter
	 *
	 * @param c is The array of Carta generate from mazzo
	 */
	public void setMano(Carta[] c) {
		mano.setMano(c);
	}

	/**
	 * Getter of the mano
	 * 
	 * @return the mano
	 */
	public Mano getMano() {
		return mano;
	}

	/**
	 * Getter of name
	 *
	 * @return the nick of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to get a single card of from the hand
	 *
	 * @param i the index of mano for the card to return
	 * @return the card who correspond to the index given
	 */
	public Carta getCardFromHand(int i) {
		return mano.get(i);
	}

	/**
	 * Method used to switch 2 cards
	 *
	 * @param newC Card to set
	 * @param i    Index of the card to get
	 * @return the card got
	 */
	public Carta changeCard(Carta newC, int i) {
		Carta temp = mano.get(i).clone();
		mano.switchCard(i, newC.clone());
		if(hasTrash()) cardNumber--;
		return temp;
	}

	/**
	 * Checks if the player did trash, if he trashed the cardNumnber get one
	 * decrement
	 *
	 * @return true if he trash or false if he didn't
	 */
	public boolean hasTrash() {
		for (Carta c : mano) {
			if (c.isHidden())
				return false;
		}
		return true;
	}

	/**
	 * getter of the card number
	 *
	 * @return cardNumber
	 */
	public int getCardNumber() {
		return cardNumber;
	}

	/**
	 * Setter of the card number
	 * 
	 * @param i the value to set on card number
	 */
	public void setCardNumber(int i) {
		cardNumber = i;
	}

	/**
	 * Checks if the player won checking if his cardNumber is 0
	 *
	 * @return true if he won, false he did't
	 */
	public boolean hasWin() {
		if (cardNumber == 0)
			return true;
		return false;
	}

	/**
	 * This method checks if the card is playable
	 * 
	 * @param card Is the card to check
	 * @return true when the card is bigger than the player remaining cards number
	 *         but not a jolly or king
	 */
	public boolean cardIsOut(Carta card) {
		return (getCardNumber() - 1 < card.getV() && card.getV() < 12); // The case where he draws a card but doesn't
																		// have the position to place it
	}

	/**
	 * This method checks if the card is playable
	 * 
	 * @param index Is the index of the card to check
	 * @return true when the card is Hidden
	 */
	public boolean cardIsHidden(int index) {
		return getCardFromHand(index).isHidden();
	}
}
