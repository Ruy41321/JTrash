package application.model.mazzo;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import application.model.ClassNotInstancedException;
import application.model.player.Player;

/** Class which represent the deck used to play the game */
@SuppressWarnings("deprecation")
public class Mazzo extends Observable {
	/** Array to represent the whole deck */
	private ArrayList<Carta> mazzo;
	/** Array to represent the main (used) deck to give cards to the players */
	private ArrayList<Carta> main;
	/** Array to represent the discard pile */
	private ArrayList<Carta> scarti = new ArrayList<>();
	/** The current card on the table that the player has to switch */
	private Carta cardToPlay;
	/** Pointer to handle the deck */
	private int pos = 0;

	public static Mazzo instance;

	public static Mazzo getInstance() {
		try {
			if (instance == null)
				throw new ClassNotInstancedException(
						"There is no current instance of Mazzo, to get an instance you have to set it first with the method setMazzo()");
		} catch (ClassNotInstancedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return instance;
	}

	/**
	 * Constructor for a deck of 54*n cards
	 *
	 * @param n is the multiplier of decks
	 */
	public Mazzo(int n) {
		mazzo = new ArrayList<>(108);
		for (int i = 0; i < n; i++)
			mazzo.addAll(new Mazzo().mazzo);

		setMain(mazzo);
		instance = this;
	}

	/** Constructor for a deck of 54 cards */
	public Mazzo() {
		mazzo = new ArrayList<>(54);
		for (Valore v : Valore.values()) {
			for (Seme s : Seme.values()) {
				if (v != Valore.Joker && s != Seme.Rosso && s != Seme.Nero)
					mazzo.add(new Carta(s, v));
			}
		}
		mazzo.add(new Carta(Seme.Rosso, Valore.Joker));
		mazzo.add(new Carta(Seme.Nero, Valore.Joker));
		setMain(mazzo);
		instance = this;
	}

	/**
	 * Setter of the main deck with the deck given as parameter
	 *
	 * @param mazzo deck used to set the main
	 */
	public void setMain(ArrayList<Carta> mazzo) {
		main = new ArrayList<>(mazzo.size());
		main.addAll(mazzo);
	}

	/**
	 * Method used to do some development checks, it stamps the entire deck stamping
	 * every single card
	 */
	public void stamp() {
		for (Carta carta : mazzo) {
			carta.changeStatus();
			System.out.println(carta.toString());
			carta.changeStatus();
			System.out.println();
		}
	}

	/**
	 * The distributeCards method is the starting phase of the game, in this phase
	 * the players get the card for their starting hand and set the TrashStatus on
	 * false
	 *
	 */
	public void distributeCards(ArrayList<Player> players) {
		mix(); // Mix the deck
		players.forEach(pl -> {
			pl.setMano(getMano(pl.getCardNumber()));
			pl.setTrashStatus();
		});
		discard(next());
	}

	/**
	 * Private Method used to mix the deck.
	 * <p>
	 * Precisely it check that the position of a card (r) is not already added in
	 * the new mixed deck.
	 * </p>
	 *
	 * @param indexMixed the list of position of the card already added
	 * @param rand       the position of the card to add
	 * @return true if the card has already got mix, false instead
	 */
	private boolean checkContain(int[] indexMixed, int rand) {
		for (int n : indexMixed) {
			if (n == rand)
				return (true);
		}
		return (false);
	}

	/**
	 * Generate one list of Carta object containing n cards to have be given to the
	 * player
	 *
	 * @param n the number of cards the player must get
	 * @return the hand (array of Carta)
	 */
	public Carta[] getMano(int n) {
		Carta[] mano = new Carta[n];
		for (int i = 0; i < mano.length; i++) {
			mano[i] = next();
		}
		return mano;
	}

	/**
	 * Method used to mix the deck 5 times, clear the discard pile array, reset the
	 * pointer and set the main
	 * <p>
	 * Precisely it generate a Random number who represent the position of the card
	 * to switch with current card of the iteration, switch them and add the
	 * position of the card mixed to the array of number of position Mixed. The
	 * method use the check() function to be sure to pick a random number different
	 * from a previous one to get a more accurate mix
	 * </p>
	 */
	public void mix() {
		int rand;
		Carta temp;
		for (int z = 0; z < 5; z++) {
			Random r = new Random();
			int[] indexMixed = new int[mazzo.size()];
			for (int i = 0; i < indexMixed.length; i++) {
				indexMixed[i] = mazzo.size() + 1; // Setting all elements to an invalid index
			}
			for (int i = 0; i < mazzo.size(); i++) {
				do {
					rand = r.nextInt(mazzo.size());
				} while (checkContain(indexMixed, rand));
				temp = mazzo.get(i);
				mazzo.set(i, mazzo.get(rand));
				mazzo.set(rand, temp);
				indexMixed[i] = rand;
			}
		}
		pos = 0;
		scarti.clear();
		hideAll(mazzo);
		setMain(mazzo);
	}

	/**
	 * Method used only when there isn't a next card to mix the discard pile 5
	 * times, set it as main deck, clear the discard pile array and reset the
	 * pointer
	 * <p>
	 * Precisely it generate a Random number who represent the position of the card
	 * to switch with current card of the iteration, switch them and add the
	 * position of the card mixed to the array of number of position Mixed. The
	 * method use the check() function to be sure to pick a random number different
	 * from a previous one to get a more accurate mix
	 * </p>
	 */
	public void mixScartiIfNecessary() {
		if (hasNext())
			return;
		for (int z = 0; z < 5; z++) {
			Random r = new Random();
			int[] nMixed = new int[scarti.size()];
			for (int i = 0; i < nMixed.length; i++) {
				nMixed[i] = scarti.size() + 1;
			}
			for (int i = 0; i < scarti.size(); i++) {
				int rand;
				do {
					rand = r.nextInt(scarti.size());
				} while (checkContain(nMixed, rand));
				Carta temp;
				temp = scarti.get(i);
				scarti.set(i, scarti.get(rand));
				scarti.set(rand, temp);
				nMixed[i] = rand;
			}
		}
		pos = 0;
		hideAll(scarti);
		setMain(scarti);
		scarti.clear();

	}

	/**
	 * Method to get the last discarded card
	 *
	 * @return the card at the top of the discard stack
	 */
	public Carta getLastDiscard() {
		return scarti.get(scarti.size() - 1);
	}

	/**
	 * Method to check if there are other available cards in the main deck
	 *
	 * @return the number of remaining cards
	 */
	public int getRimanenti() {
		return main.size() - pos;
	}

	/**
	 * Method to know if there is an next element
	 *
	 * @return true if there a next element
	 */
	public boolean hasNext() {
		return getRimanenti() != 0;
	}

	/**
	 * Method to get a card from the deck
	 *
	 * @return the next card
	 */
	public Carta next() {
		return main.get(pos++);
	}

	/**
	 * add to the discard pile the card given as parameter
	 *
	 * @param c Card to discard
	 */
	public void discard(Carta c) {
		scarti.add(c);
		setChanged();
		notifyObservers(0);
	}

	/**
	 * getter of cardToPlay
	 * 
	 * @return cardToPlay
	 */
	public Carta getCardToPlay() {
		return cardToPlay;
	}

	/**
	 * setter of cardToPlay
	 * 
	 * @param cardToPlay
	 */
	public void setCardToPlay(Carta cardToPlay) {
		this.cardToPlay = cardToPlay;
		if (cardToPlay != null)
			this.cardToPlay.changeStatus();
		setChanged();
		notifyObservers(1);
	}

	public void hideAll(ArrayList<Carta> deck) {
		for (Carta c : deck)
			// if the cards were shown re set'em to hidden
			if (!c.getHiddenStatus())
				c.changeStatus();
	}
}
