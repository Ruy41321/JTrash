package application.model.mazzo;

/** Enum representation for the values of the French gaming cards */
public enum Valore {
	Asso, Due, Tre, Quattro, Cinque, Sei, Sette, Otto, Nove, Dieci, Jack, Queen, King, Joker;
	
	public String toEnglishStringValue() {
		return switch(name()) {
			case "Asso" -> "A";
			case "Due" -> "2";
			case "Tre" -> "3";
			case "Quattro" -> "4";
			case "Cinque" -> "5";
			case "Sei" -> "6";
			case "Sette" -> "7";
			case "Otto" -> "8";
			case "Nove" -> "9";
			case "Dieci" -> "10";
			case "Jack" -> "J";
			case "Queen" -> "Q";
			case "King" -> "K";
			case "Joker" -> "jolly";
			default -> null;
		};
	}
}
