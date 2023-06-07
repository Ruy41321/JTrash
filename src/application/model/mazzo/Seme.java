package application.model.mazzo;

/** Enum representation of seems for the French gaming cards */
public enum Seme {
	Cuori, Quadri, Fiori, Picche, Rosso, Nero;

	/**
	 * Method used to get the name in English
	 */
	public String toEnglishString() {
		return switch (name()) {
		case "Cuori" -> "Hearts";
		case "Quadri" -> "Diamonds";
		case "Fiori" -> "Clubs";
		case "Picche" -> "Spades";
		case "Rosso" -> "Red";
		case "Nero" -> "Black";
		default -> null;
		};
	}
}
