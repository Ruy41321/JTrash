package application.model.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import application.model.mazzo.Carta;
import application.model.mazzo.Mazzo;

/** Class which represent the Npc players */
public class Npc extends Player {
	/** Random generator */
	private static Random rand = new Random();
	/** Field used to set a different name for every npc */
	private static ArrayList<Name> names = new ArrayList<Name>(List.of(Name.values()));

	/**
	 * Generate recursively a random name from the enum Name class excluding the
	 * already generated names and the user name
	 *
	 * @param user is the name to exclude
	 * @return an Random Name
	 */
	private static String randName(String user) {
		int index = rand.nextInt(names.size());
		String name = names.get(index).toString();
		names.remove(index);
		// if the name generated equals to the user's name it generate another name
		return (name.equalsIgnoreCase(user)) ? randName(user) : name;
	}

	/**
	 * this method reset the random name generator setting again all the names in
	 * the array list
	 */
	public static void resetRandomNameGenerator() {
		names = new ArrayList<Name>(List.of(Name.values()));
	}

	/**
	 * Creates a new Npc with a name picked from the enum list
	 *
	 * @param user is the name of the User to exclude from the random generator of
	 *             the npc names
	 */
	public Npc(String user) {
		super(randName(user));
	}

	/**
	 * Method by which all the Npc play their turns
	 * <p>
	 * When they will draw a card from 1 to 10 the system automatically switch it if
	 * their card in that position is still hidden. Else if they will draw a king or
	 * jolly a random generator will choose an hidden position to place it
	 *
	 */
	public void play() {
		int pos;
		Carta cardToPlay = Mazzo.getInstance().getCardToPlay();
		try {
			pos = cardToPlay.getV();
			if (pos > 11)
				throw new IndexOutOfBoundsException();
		} catch (IndexOutOfBoundsException e) {
			// If there is this Exception means the drawn card was a King or Jolly
			do {
				pos = rand.nextInt(getCardNumber()); // Picking a random number to choose with which card
														// switch
			} while (!getCardFromHand(pos).isHidden()); // repeating the random picking until it
														// choose an hidden card
		}
		// switch in the model and in the view with the observer Observable
		Mazzo.getInstance().setCardToPlay(changeCard(cardToPlay, pos));
	}

}
