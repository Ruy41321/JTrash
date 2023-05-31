package application.model;

import java.util.ArrayList;
import java.util.Scanner;

import application.model.mazzo.Carta;
import application.model.mazzo.Mazzo;
import application.model.player.Npc;
import application.model.player.Player;
import application.model.player.User;

/**
 * The JTrash class is the main class of the project and allows to play the game
 *
 * @author Luigi Pennisi
 *
 */
public class Model {
	/** Instance of the class */
	private static Model instance;

	/** Deck for the game */
	private Mazzo mazzo;

	/**
	 * The User object to save all his data
	 */
	private User u;

	/**
	 * Array used to manage all the player who are playing
	 */
	private ArrayList<Player> players;

	/**
	 * Constructor using the user who's playing
	 * 
	 * @param u The User logged in
	 */
	private Model(User u) {
		this.u = u;
		instance = this;
	}

	public static class ClassNotInstancedException extends Exception {

		private static final long serialVersionUID = 1L;

		public ClassNotInstancedException(String message) {
			super(message);
		}

	}

	public static Model getInstance() {
		try {
			if (instance == null)
				throw new ClassNotInstancedException(
						"There is no current instance of Model, to get an instance you have to login");
		} catch (ClassNotInstancedException e) {
			e.printStackTrace();
		}
		return instance;
	}

	public static Model login(String nick, String pass) {

		instance = new Model(User.login(nick, pass));
		if (instance.getUser() == null)
			instance = null;
		return instance;

		/*
		 * u = User.login(nick, pass); if (u==null) return new Model(); return new
		 * Model();
		 */
	}

	public static boolean signup(String nick, String pass) {
		return User.signup(nick, pass);
	}

	public static void reset() {
		instance = null;
		Npc.resetRandomNameGenerator();
	}

	public void resetGame() {
		players = null;
		u.setCardNumber(10);
		mazzo = null;
		Npc.resetRandomNameGenerator();
	}

	public User getUser() {
		return u;
	}

	public Mazzo getMazzo() {
		return mazzo;
	}

	public void setMazzo(int numOfNpc) {
		mazzo = (numOfNpc == 1) ? new Mazzo() : new Mazzo(2);// Generate a deck of 54 cards if true, else of 108 cards
																// like the merge of 2 standard decks
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(int numOfNpc) {
		this.players = new ArrayList<Player>(numOfNpc + 1);// Creating an array of player who contains the Player's
															// instances of
		// size(number of Npc + one user)
		players.add(u); // Setting the User in the first field
		for (int i = 1; i < numOfNpc + 1; i++)
			players.add(new Npc(players.get(0).getName())); // Setting the following fields for the Npc
	}

	public void updateUserStats(boolean hasWin) {
		u.endGame(hasWin);
	}

	public void checkIfMazzoHasNext() {
		if (!mazzo.hasNext()) // If the deck is over continue using the mixed discard pile
			mazzo.mixScarti();
	}

	/**
	 * This is the main body of the game, it permit to sign up/log in and to play.
	 * <p>
	 * It ends when a player win and kill the process
	 * </p>
	 *
	 * @param args no needed
	 */
	public void mainAsConsoleProgram() {

		boolean trasherFlag; // Flag to know if someone has done trash

		// Game starting
		while (true) { // Go on until someone win
			// m1.stamp();
			distributeCards(); // Distribute the cards

			do {
				trasherFlag = false;
				// Starting the turn for each player
				for (Player pl : players) {

					phase2(pl); // The Player plays

					if (pl.checkWin()) { // Check if the current player won
						if (players.get(0).checkWin()) { // Check if the current player who has won is the User
							((User) players.get(0)).endGame(true);
							System.exit(0);
						}
						System.out.println("Ha vinto il giocatore: " + pl.getName());
						((User) players.get(0)).endGame(false);
						System.exit(0);
					}
					if (pl.getTrashStatus()) { // Check if the current Player has done trash
						trasherFlag = true;
						// Giving to all the others one last round
						for (Player pLeft : players) {
							if (pLeft.equals(pl))
								continue; // The just trasher player can't play the last round

							if (!mazzo.hasNext())// If the deck is over continue using the mixed discard pile
								mazzo.mixScarti();

							phase2(pLeft);

							if (pLeft.checkWin()) {
								if (players.get(0).checkWin()) {
									((User) players.get(0)).endGame(true);
									System.exit(0);
								}
								System.out.println("Ha vinto il giocatore: " + pLeft.getName());
								((User) players.get(0)).endGame(false);
								System.exit(0);
							}
							if (pLeft.getTrashStatus())
								trasherFlag = true;
						}
						break;
					}
				}
			} while (!trasherFlag); // Repeat if none has done trash
		}
	}

	/**
	 * The distributeCards method is the starting phase of the game, in this phase
	 * the players get the card for their starting hand and set the TrashStatus on
	 * false
	 *
	 */
	public void distributeCards() {
		mazzo.mix(); // Mix the deck
		for (Player pl : players) {
			// System.out.println(pl.getName());
			pl.setMano(mazzo.getMano(pl.getCardNumber()));
			pl.setTrashStatus();
			// pl.stampMano();
		}
		mazzo.discard(mazzo.next());
	}

	/**
	 * The phase2 method is the main phase of the game, it has to handle the players
	 * turn.
	 * <p>
	 * In this method the single players draws a card and switch it with the hand
	 * until an end's turn condition happens. and in the end before to exit checks
	 * if the player has done trash
	 * </p>
	 *
	 * @param pl the array containing the Players instances
	 */
	private void phase2(Player pl) {
		checkIfMazzoHasNext();

		System.out.println("E' il turno di: " + pl.getName());
		Carta newC = mazzo.next(); // drawing the card
		while (!pl.getTrashStatus()) { // the turn goes on until there is a break condition or someone has trashed

			//pl.stampMano();
			Carta prima = newC.clone();
			newC = pl.play(newC);
			if (newC == null) {
				mazzo.discard(prima);
				break;
			}
		}

		if (pl.getTrashStatus()) {
			mazzo.discard(newC);
			System.out.println(pl.getName() + " dice Trash");
		}

	}

	public Player getFollowingPlayer(Player currPlayer) {
		int i = players.indexOf(currPlayer);
		return (i == 0 && players.size() > 2) ? players.get(2)
				: (i == 0 || i == 2) ? players.get(1)
						: (i == 1 && players.size() > 3) ? players.get(3) : players.get(0);
	}

}