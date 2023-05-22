package application.model.player;

import application.model.mazzo.Carta;

/**
 * Abstract class to extend to get the methods to handle the game
 *
 * @author Luigi Pennisi
 */
public abstract class Player implements playable {

	/** Nick of the player */
	private String name;
	/** Hand of the player (array of him cards) */
	private Carta[] mano;
	/** variable to know how many card the player deserve */
	private int cardNumber = 10;
	/** flag used to know if the player has done Trash */
	private boolean trashStatus;

	/**
	 * Create a new Player using the name given
	 *
	 * @param name The name of the User or Npc
	 */
	public Player(String name) {
		this.name = name;
	}

	/**
	 * Setting the mano (the hand) with the array of Carta given as parameter
	 *
	 * @param c is The array of Carta generate from mazzo
	 */
	public void setMano(Carta[] c) {
		mano = new Carta[c.length];
		int i = 0;
		for (Carta c1 : c) {
			mano[i++] = c1.clone();
		}
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
	 * Method used to stamp the entire mano as string representation of each single
	 * card
	 */
	public void stampMano() {
		System.out.println();
		for (Carta c : mano) {
			c.stamp();
		}
	}

	/**
	 * Method to get a single card of from the hand
	 *
	 * @param i the index of mano for the card to return
	 * @return the card who correspond to the index given
	 */
	public Carta getCardFromHand(int i) {
		return mano[i];
	}

	/**
	 * Method used to switch 2 cards
	 *
	 * @param newC Card to set
	 * @param i    Index of the card to get
	 * @return the card got
	 */
	public Carta changeCard(Carta newC, int i) {
		Carta temp = mano[i].clone();
		mano[i] = newC.clone();
		mano[i].changeStatus();
		return temp;
	}

	/**
	 * Checks if the player did trash, if he trashed the cardNumnber get one
	 * decrement
	 *
	 * @return true if he trash or false if he didn't
	 */
	private boolean checkTrash() {
		for (Carta c : mano) {
			if (c.getHidenStatus())
				return false;
		}
		cardNumber--;
		return true;
	}

	/** setter of the Trash Status calling checkTrash() */
	public void setTrashStatus() {
		trashStatus = checkTrash();
	}

	/**
	 * getter of the Trash Status
	 *
	 * @return trashStatus
	 */
	public boolean getTrashStatus() {
		return trashStatus;
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
	 * Checks if the player won checking if his cardNumber is 0
	 *
	 * @return true if he won, false he did't
	 */
	public boolean checkWin() {
		if (cardNumber == 0)
			return true;
		return false;
	}
	

	public boolean cardIsOut(Carta card) {
		return (getCardNumber() - 1 < card.getV() && card.getV() < 12); // The case where he draws a card but doesn't have the position to place it
	}
	
	public boolean cardIsHidden(Carta card) {
		return getCardFromHand(card.getV()).getHidenStatus();
	}
	
	public boolean cardIsHidden(int index) {
		return getCardFromHand(index).getHidenStatus();
	}
	
	public boolean cardIsJolly(Carta card) {
		return !(card.getV() < 12);
	}

	
}
