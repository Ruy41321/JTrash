package application.model;

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
	/** Text Scanner */
	private static Scanner in = new Scanner(System.in);
	/** Deck for the game */
	private static Mazzo m1;

	private static Model instance;
	
	private User u;
	

	/** Basic Constructor */
	public Model(User u) {
		this.u = u;
		instance = this;
	}
	
	public static Model getInstance() {
		return instance;
	}
	
	public static Model login(String nick, String pass) {
		
		instance = new Model(User.login(nick,pass));
		if (instance.getUser() == null)
			instance = null;
		return instance;
		
		/*
		u = User.login(nick, pass);
		if (u==null)
			return new Model();
		return new Model();
		*/
	}
	
	public static boolean signup(String nick, String pass) {
		return User.signup(nick, pass);
	}

	public static void setup() {
		instance = null;
		m1 = null;
	}

	
	public User getUser() {
		return u;
	}

	/**
	 * This is the main body of the game, it permit to sign up/log in and to play.
	 * <p>
	 * It ends when a player win and kill the process
	 * </p>
	 *
	 * @param args no needed
	 */
	public void main(String[] args) {

		// Initializing
		System.out.println("Con quanti NPC vuoi giocare? 1,2,3?");
		int npc = in.nextInt();
		m1 = (npc == 1) ? new Mazzo() : new Mazzo(2);// Generate a deck of 54 cards if true, else of 108 cards like the
														// merge of 2 standard decks

		Player[] p = new Player[npc + 1];// Creating an array of player who contains the Player's instances of size(npc
											// + user)
		p[0] = u; // Setting the User in the first field
		for (int i = 1; i < npc + 1; i++)
			p[i] = new Npc(p[0].getName()); // Setting the following fields for the Npc

		boolean trasherFlag; // Flag to know if someone has done trash

		// Game starting
		while (true) { // Go on until someone win
			// m1.stamp();
			phase1(p); // Distribute the cards

			do {
				trasherFlag = false;
				// Starting the turn for each player
				for (Player pl : p) {
					if (!m1.hasNext()) // If the deck is over continue using the mixed discard pile
						m1.mixScarti();

					phase2(pl); // The Player plays

					if (pl.checkWin()) { // Check if the current player won
						if (p[0].checkWin()) { // Check if the current player who has won is the User
							((User) p[0]).endGame(true);
							System.exit(0);
						}
						System.out.println("Ha vinto il giocatore: " + pl.getName());
						((User) p[0]).endGame(false);
						System.exit(0);
					}
					if (pl.getTrashStatus()) { // Check if the current Player has done trash
						trasherFlag = true;
						// Giving to all the others one last round
						for (Player pLeft : p) {
							if (pLeft.equals(pl))
								continue; // The just trasher player can't play the last round

							if (!m1.hasNext())// If the deck is over continue using the mixed discard pile
								m1.mixScarti();

							phase2(pLeft);

							if (pLeft.checkWin()) {
								if (p[0].checkWin()) {
									((User) p[0]).endGame(true);
									System.exit(0);
								}
								System.out.println("Ha vinto il giocatore: " + pLeft.getName());
								((User) p[0]).endGame(false);
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
	 * The phase1 method is the starting phase of the game, in this phase the
	 * players get the card for their starting hand and set the TrashStatus on false
	 *
	 * @param p the array containing the Players instances
	 */
	private static void phase1(Player[] p) {
		m1.mix(); // Mix the deck
		for (Player pl : p) {
			// System.out.println(pl.getName());
			pl.setMano(m1.getMano(pl.getCardNumber()));
			pl.setTrashStatus();
			// pl.stampMano();
		}
		m1.discard(m1.next());

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
	private static void phase2(Player pl) {
		System.out.println("E' il turno di: " + pl.getName());
		Carta newC = m1.next(); // drawing the card
		while (!pl.getTrashStatus()) { // the turn goes on until there is a break condition or someone has trashed

			pl.stampMano();
			Carta prima = newC.clone();
			newC = pl.play(newC);
			if (newC == null) {
				m1.discard(prima);
				break;
			}
		}

		if (pl.getTrashStatus()) {
			m1.discard(newC);
			System.out.println(pl.getName() + " dice Trash");
		}

	}


}