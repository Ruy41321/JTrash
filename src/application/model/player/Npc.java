package application.model.player;

import java.util.ArrayList;
import java.util.Random;

import application.model.mazzo.Carta;

/** Class which represent the Npc players */
public class Npc extends Player {
	/** Random generator */
	private static Random rand = new Random();
	/** Field used to set a different name for every npc */
	private static ArrayList<Integer> namesValue = new ArrayList<>();

	/**
	 * Generate recursively a random name from the enum Name class excluding the
	 * already generated names and the user name
	 *
	 * @param user is the name to exclude
	 * @return an Random Name
	 */
	private static String randName(String user) {
		int index = rand.nextInt(Name.values().length);
		if (!namesValue.contains(index) && !Name.values()[index].name().equals(user)) {
			// if it's different from the user and the previous ones
			namesValue.add(index);
			return Name.values()[index].name();
		}
		return randName(user);
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
	
	public static void resetRandomNameGenerator() {
		namesValue = new ArrayList<>();
	}

	/**
	 * Method by which all the Npc play their turns
	 * <p>
	 * When they will draw a card from 1 to 10 the system automatically switch it if
	 * their card in that position is still hidden. Else if they will draw a king or
	 * jolly a random generator will choose an hidden position to place it
	 *
	 * @param card The card just draw
	 * @return card The card got from the hand after the play or Null if the card
	 *         cannot be place
	 */
	@Override
	public Carta play(Carta card) {
		System.out.println("\nHai un " + card.toString()); // Showing the draw card
		if (getCardNumber() - 1 < card.getV() && card.getV() < 12)
			// The case where he draws a card but doesn't have the position to place it
			return null;
		if (card.getV() < 10) {
			// The case where it's not a figure
			if (getCardFromHand(card.getV()).getHidenStatus()) {
				// The case where that position is still hidden
				card = changeCard(card, card.getV()).clone();
			} else
				// The case where the position is already shown
				return null;

		} else {
			// The case where it's a figure
			if (card.getV() < 12)
				// The case where it's a jack or queen
				return null;
			else {
				// The case where it's a king or queen
				int pos;
				do {
					pos = rand.nextInt(getCardNumber()); // Picking a random number to choose with which card switch the
															// drawn one
				} while (!getCardFromHand(pos).getHidenStatus()); // repeating the random picking until it choose an hidden card
				card = changeCard(card, pos).clone();
			}
		}
		setTrashStatus();
		return card;
	}
}
