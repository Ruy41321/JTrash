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

import application.model.ClassNotInstancedException;
import application.model.mazzo.Carta;

/**
 * Class to represent an user (Human Player) who extends player
 *
 * @author Luigi Pennisi
 */
public class User extends Player {
	/** Nick of the user */
	@Expose
	private String nick;
	/** Encrypted pass of the user */
	@Expose
	private String pass;
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
	 * Property which represent the instance of this object
	 */
	private static User instance;

	/**
	 * getter of the instance, the instance will not be created if null, to work the
	 * user has to be initialized through the login method. This method is just a
	 * simple instance getter
	 * 
	 * @return the static instance of User
	 */
	public static User getInstance() {
		try {
			if (instance == null)
				throw new ClassNotInstancedException(
						"There is no current instance of User, to get an instance you have to login first");
		} catch (ClassNotInstancedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return instance;
	}

	/**
	 * Create a new User from the parameter given, this constructor is used for the
	 * sign up
	 *
	 * @param nick    The nick of the user
	 * @param pass    The password
	 * @param vinte   Number of games won
	 * @param perse   Number of games lost
	 * @param livello Level of the player
	 * @param avatar  The path for the profile image
	 */
	public User(String nick, String pass, int vinte, int perse, float livello, String avatar) {
		super(nick);
		this.nick = nick;
		this.pass = pass;
		this.vinte = vinte;
		this.perse = perse;
		this.livello = livello;
		this.avatar = avatar;
		instance = this;
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
		this.livello = u.livello;
		this.avatar = u.avatar;
		instance = this;
	}

	@Override
	public String toString() {
		return "User:\t".concat(nick).concat("\nLivel:\t").concat(String.valueOf(livello)).concat("\nVinte:\t")
				.concat(String.valueOf(vinte)).concat("\nPerse:\t").concat(String.valueOf(perse)).concat("\nAvatar:\t")
				.concat(avatar);
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
	public int getPartiteTot() {
		return vinte + perse;
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
		vinte++;
	}

	/** Increment of lost game */
	public void incrPerse() {
		perse++;
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
			File file = new File("bin/users/" + nick + ".json");
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
		File file = new File("bin/users/" + nick + ".json");
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
	 * @param nick is the user's name choose to sign with
	 * @param pass is the password choose to protect the account
	 *
	 * @return true if works, false if it encounter an error
	 **/
	public static boolean signup(String nick, String pass) {
		try {
			File file = new File("bin/users/" + nick + ".json");
			// if the file already exist means the nick is already taken
			if (file.exists())
				System.out.println("Nick non disponibile");
			else if (file.createNewFile()) { // creating the file
				// setting to default the Users statistics
				int perse = 0, vinte = 0, livello = 1;
				String avatar = "bin/varie/avatar.png";

				// Initializing the Objects to write on files
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));

				// Writing on Json file with Gson libraries
				Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
				bw.write(gson.toJson(new User(nick, encrypt(pass), vinte, perse, livello, avatar)));

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
	 * @param nick is the name of the account in which log in
	 * @param pass is the password to access to the account
	 *
	 * @return the User instance if he logged in, Null if it encounter an error
	 */
	public static User login(String nick, String pass) {
		try {
			File file = new File("bin/users/" + nick + ".json");
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
