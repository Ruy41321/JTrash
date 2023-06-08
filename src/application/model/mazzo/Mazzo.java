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

	/**
	 * Property which represent the instance of this object (no SingleTon)
	 */
	public static Mazzo instance;

	/**
	 * getter of the instance, the instance will not be created if null, to work the
	 * deck has to be initialize trough the classic constructor. This method is just
	 * a simple getter
	 * 
	 * @return the static instance of Mazzo
	 */
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
	 * This method set the deck used to play the session
	 * 
	 * @param numOfNpc is the number of npc choose to play, needed to know how big
	 *                 construct the deck
	 */
	public static Mazzo setMazzo(int numOfNpc) {
		return (numOfNpc == 1) ? new Mazzo() : new Mazzo(2);// Generate a deck of 54 cards if true, else of 108 cards
	}

	/**
	 * Constructor for a deck of 54*n cards
	 *
	 * @param n is the multiplier of decks
	 */
	private Mazzo(int n) {
		mazzo = new ArrayList<>(108);
		for (int i = 0; i < n; i++)
			mazzo.addAll(new Mazzo().mazzo);

		setMain(mazzo);
		instance = this;
	}

	/** Constructor for a deck of 54 cards */
	private Mazzo() {
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
	@Deprecated
	public void stamp() {
		for (Carta carta : mazzo) {
			carta.changeHiddenStatus();
			System.out.println(carta.toString());
			carta.changeHiddenStatus();
			System.out.println();
		}
	}

	/**
	 * The distributeCards method is the starting phase of the game, in this phase
	 * the players get the card for their starting hand and set the TrashStatus on
	 * false
	 *
	 * @param players is the array of player to which distribute cards
	 */
	public void distributeCards(ArrayList<Player> players) {
		mix(mazzo); // Mix the deck
		players.forEach(pl -> {
			pl.setMano(getMano(pl.getCardNumber()));
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
	 * Method used to mix an arrayList of cards 5 times, reset the pointer, set this
	 * arrayList as the main deck, and clear the discard pile
	 * <p>
	 * Precisely it generate a Random number who represent the position of the card
	 * to switch with current card of the iteration, switch them and add the
	 * position of the card mixed to the array of number of position Mixed. The
	 * method use the check() function to be sure to pick a random number different
	 * from a previous one to get a more accurate mix
	 * </p>
	 * 
	 * @param deckToMix is the deck to mix and set as main
	 */
	public void mix(ArrayList<Carta> deckToMix) {
		for (int z = 0; z < 5; z++) {
			Random r = new Random();
			int[] indexMixed = new int[deckToMix.size()];
			for (int i = 0; i < indexMixed.length; i++) {
				indexMixed[i] = deckToMix.size() + 1; // Setting all elements to an invalid index
			}
			for (int i = 0; i < deckToMix.size(); i++) {
				int rand;
				do {
					rand = r.nextInt(deckToMix.size());
				} while (checkContain(indexMixed, rand));
				Carta temp;
				temp = deckToMix.get(i);
				deckToMix.set(i, deckToMix.get(rand));
				mazzo.set(rand, temp);
				indexMixed[i] = rand;
			}
		}
		pos = 0;
		hideAll(deckToMix);
		setMain(deckToMix);
		scarti.clear();
	}

	/**
	 * Method used only when there isn't a next card: to mix the discard pile 5
	 * times, set it as main deck, clear the discard pile array and reset the
	 * pointer
	 * 
	 * @param mainHasNext is a boolean to indicate if there is an next card on the
	 *                    main deck
	 */
	public void useDiscardPile(boolean mainHasNext) {
		if (mainHasNext)
			return;
		mix(scarti);
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
	 * @return cardToPlayis the card to play
	 */
	public Carta getCardToPlay() {
		return cardToPlay;
	}

	/**
	 * setter of cardToPlay
	 * 
	 * @param cardToPlay is the card to play
	 */
	public void setCardToPlay(Carta cardToPlay) {
		this.cardToPlay = cardToPlay;
		if (cardToPlay != null)
			this.cardToPlay.changeHiddenStatus();
		setChanged();
		notifyObservers(1);
	}

	/**
	 * reset all shown card to hidden
	 * 
	 * @param deck is the array of card to hide
	 */
	public void hideAll(ArrayList<Carta> deck) {
		for (Carta c : deck)
			// if the cards were shown re set'em to hidden
			if (!c.isHidden())
				c.changeHiddenStatus();
	}
}
