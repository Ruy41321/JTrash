package application.model.player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import application.model.mazzo.Carta;

/**
 * Class to represent an user (Human Player) who extends player
 *
 * @author Luigi Pennisi
 */
public class User extends Player {
	/** Text Scanner */
	private static Scanner in = new Scanner(System.in);
	/** Nick of the user */
	@Expose
	private String nick;
	/** Encrypted pass of the user */
	@Expose
	private String pass;
	/** Number of game played */
	@Expose
	private int pTot;
	/** Number of game won */
	@Expose
	private int vinte;
	/** Number of game lost */
	@Expose
	private int perse;
	/** Level got from the user */
	@Expose
	private float livello;
	/** Path of the user's profile image */
	@Expose
	private String avatar;

	/**
	 * Create a new User from the parameter given, this constructor is used for the
	 * sign up
	 *
	 * @param nick    The nick of the user
	 * @param pass    The password
	 * @param vinte   Number of games won
	 * @param perse   Number of games lost
	 * @param livello Level of the player
	 * @param pTot    Number of games
	 * @param avatar  The path for the profile image
	 */
	public User(String nick, String pass, int vinte, int perse, float livello, int pTot, String avatar) {
		super(nick);
		this.nick = nick;
		this.pass = pass;
		this.vinte = vinte;
		this.perse = perse;
		this.pTot = pTot;
		this.livello = livello;
		this.avatar = avatar;
	}

	/**
	 * Create a new User from an already existing User, this constructor is used for
	 * the log in
	 *
	 * @param u User got from the gson reader
	 */
	public User(User u) {
		super(u.nick);
		this.nick = u.nick;
		this.pass = u.pass;
		this.vinte = u.vinte;
		this.perse = u.perse;
		this.pTot = u.pTot;
		this.livello = u.livello;
		this.avatar = u.avatar;
	}

	@Override
	public String toString() {
		return "User:\t".concat(nick).concat("\nLivel:\t").concat(String.valueOf(livello)).concat("\nTotali:\t")
				.concat(String.valueOf(pTot)).concat("\nVinte:\t").concat(String.valueOf(vinte)).concat("\nPerse:\t")
				.concat(String.valueOf(perse)).concat("\nAvatar:\t").concat(avatar);
	}

	/**
	 * Getter
	 *
	 * @return the user's nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Getter
	 *
	 * @return the total number of game played
	 */
	public int getpTot() {
		return pTot;
	}

	/**
	 * setter which calculate at the moment the number of game played adding the won
	 * and the lost
	 */
	public void updatePTot() {
		pTot = vinte + perse;
	}

	/**
	 * Getter
	 *
	 * @return the number of game won
	 */
	public int getVinte() {
		return vinte;
	}

	/** Increment of won game */
	public void incrVinte() {
		vinte += 1;
	}

	/** Increment of lost game */
	public void incrPerse() {
		perse += 1;
	}

	/**
	 * Getter
	 *
	 * @return the number of game lost
	 */
	public int getPerse() {
		return perse;
	}

	/**
	 * Getter
	 *
	 * @return the level of the user
	 */
	public float getLivello() {
		return livello;
	}

	/** Calculator of the level second a formula invented by me */
	public void calcLivello() {
		// the level is calculated on the amount of won and lost games with a weight of
		// (3:1)
		livello = (float) ((vinte + 1) * 0.75 + (perse + 1) * 0.25);
	}

	/**
	 * Getter
	 *
	 * @return the path of the profile image
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * Setter for the path of the profile image
	 *
	 * @param path The path of the image chosen
	 * @return true if all went good, false if there was an error
	 */
	public boolean setAvatar(String path) {
		File file = new File(path);
		// Going to set the new path only if it's a file of jpeg or png format
		if (file.isFile())
			if (path.endsWith("png") || path.endsWith("jpeg")) {
				this.avatar = path;
				System.out.println("Modifica avvenuta con successo");
				return true;
			} else
				System.out.println("Sono accettati solo i formati png e jpeg");
		else
			System.out.println("Il percorso inserito non è un file");
		return false;
	}
	
	/**
	 * Method by which the user plays his turn
	 * <p>
	 * When he will draw a card from 1 to 10 the system automatically switch it if
	 * their card in that position is still hidden. Else if he will draw a king or
	 * jolly he will have the possibility to choose an hidden position to place it
	 *
	 * @param card The card just draw
	 * @return card The card got from the hand after the play or Null if the card
	 *         cannot be place
	 */
	@Override
	public Carta play(Carta card) {
		System.out.println("\nHai un " + card.toString()); // Showing the draw card
		if (cardIsOut(card)) {
			// The case where he draws a card but doesn't have the position to place it
			return null;
		}
		if (card.getV() < 10) {
			// The case where it's not a figure
			if (cardIsHidden(card)) {
				// The case where that position is still hidden
				card = changeCard(card, card.getV()).clone();
			} else
				// The case where the position is already shown
				return null;

		} else {
			// The case where it's a figure
			if (!cardIsJolly(card))
				// The case where it's a jack or queen
				return null;
			else {
				// The case where it's a king or queen
				int pos;
				do {
					// The user choose where to place the card until he choose an hidden card
					System.out.println("Scegli dove posizionare la carta");
					pos = in.nextInt();
				} while (!getCardFromHand(pos - 1).getHiddenStatus());
				card = changeCard(card, pos - 1).clone();
			}
		}
		setTrashStatus();
		return card;
	}

	/**
	 * This method update the player statistics based on the status of the current
	 * game and save them
	 *
	 * @param win is a boolean used to know if the player won or lost the game
	 */
	public void endGame(boolean win) {
		if (win) {
			System.out.println("Complimenti " + nick + " Hai Vinto !");
			incrVinte();
		} else {
			incrPerse();
		}
		updatePTot();
		calcLivello();
		save();
	}

	/**
	 * This Method encrypt the password with hash(md5) encryption
	 *
	 * @param pass is the pass to encrypt
	 * @return the encrypted password or null if there has been an error
	 */
	private static String encrypt(String pass) {
		try { // Encrypting the pass with the hash(md5)
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(pass.getBytes());
			return String.format("%032x", new BigInteger(1, md5.digest()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method save the player's statistics in his Json file using the gson
	 * library
	 *
	 * @return true if it saved, false if it didn't
	 */
	public boolean save() {
		try {
			File file = new File("bin/application/model/player/db/" + nick + ".json");
			// if the file already exist means the nick is already taken
			if (!file.exists()) {
				System.out.println("Errore: File in cui salvare non trovato");
			} else { // creating the file
						// Initializing the Objects to write on files
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));

				// Writing on Json file with Gson libraries
				Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

				bw.write(gson.toJson(this));

				// Correctly closing the file
				bw.flush(); // Cleaning the buffer to ensure that the data has been written
				bw.close();

				System.out.println("Salvataggio eseguito");
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method needs to delete an account so the file from the folder
	 *
	 * @return true if works, false if it encounter an error
	 */
	public boolean delete() {
		File file = new File("bin/application/model/player/db/" + nick + ".json");
		// if the file already exist means the nick is already taken
		if (file.delete()) {
			System.out.println("Elimazione eseguita");
			return true;
		}
		System.out.println("Errore nell'eliminazione");
		return false;
	}

	/**
	 * This method is used to sign up a new account
	 *
	 * @return true if works, false if it encounter an error
	 **/
	public static boolean signup(String nick, String pass) {
		try {
			File file = new File("bin/application/model/player/db/" + nick + ".json");
			// if the file already exist means the nick is already taken
			if (file.exists())
				System.out.println("Nick non disponibile");
			else if (file.createNewFile()) { // creating the file
				// setting to default the Users statistics
				int perse = 0, vinte = 0, pTot = 0, livello = 1;
				String avatar = "res/varie/avatar.png";

				// Initializing the Objects to write on files
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));

				// Writing on Json file with Gson libraries
				Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
				bw.write(gson.toJson(new User(nick, encrypt(pass), vinte, perse, livello, pTot, avatar)));

				// Correctly closing the file
				bw.flush(); // Cleaning the buffer to ensure that the data has been written
				bw.close();

				System.out.println("Account creato con successo");
				return true;
			} else
				System.out.println("Errore nella creazione dell'account");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method is used to permit the user to log in his account
	 *
	 * @return the User instance if he logged in, Null if it encounter an error
	 */
	public static User login(String nick, String pass) {
		try {
			File file = new File("bin/application/model/player/db/" + nick + ".json");
			// I go over only if the file exist
			if (!file.exists())
				System.out.println("Nick non registrato");
			else {
				// If the file exist
				FileReader fr = new FileReader(file);
				char[] content = new char[252];
				// I read the content of the file as an array of char
				int len = fr.read(content);
				fr.close(); // closing the file

				// Going to open a Json file containing the user data
				Gson gson = new Gson();

				// Going to convert the Json formatted String in an User Object
				User u = gson.fromJson(new String(content).substring(0, len), User.class);

				// Check Password
				if (u.pass.equals(encrypt(pass))) {
					System.out.println("Login successfull");
					return new User(u);
				} else {
					System.out.println("Password errata");
					// In this case it returns null out from the try-catch
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
