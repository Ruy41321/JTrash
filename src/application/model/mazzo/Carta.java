package application.model.mazzo;



/**
 * The Carta class is the card representation of the French gaming cards
 *
 * @author Luigi Pennisi
 *
 */
public class Carta implements Cloneable {

	/** The seem of the card who can be of 4 different types */
	private Seme s;
	/** The value of the card who can has 14 different representation */
	private Valore v;
	/** The status of the card who can be hidden or shown */
	private boolean hiddenStatus = true;

	/**
	 * Constructor of the class
	 *
	 * @param s The seem of the card
	 * @param v The value of the card
	 */
	public Carta(Seme s, Valore v) {
		this.s = s;
		this.v = v;
	}

	/**
	 * Getter of the value's field
	 *
	 * @return the value of the field
	 */
	public int getV() {
		return v.ordinal();
	}

	/**
	 * Getter of the seem
	 *
	 * @return the seem
	 */
	public Seme getSeme() {
		return s;
	}
	
	/**
	 * Getter of the value
	 *
	 * @return the value 
	 */
	public Valore getValore() {
		return v;
	}
	
	/**
	 * Method to stamp the String representation of the card if it's shown or
	 * "Nascosta" if hidden
	 */
	public void stamp() {
		if (hiddenStatus)
			System.out.println("Nascosta");
		else
			System.out.println(toString());
	}

	/** @return e.g. "Quattro Fiori" */
	@Override
	public String toString() {
		return ("".concat(v.name().concat(" ").concat(s.name())));
	}

	/**
	 * Getter of the Hidden status
	 *
	 * @return true if Hidden
	 */
	public boolean getHidenStatus() {
		return hiddenStatus;
	}

	/** Method who change the Hidden status of the card in the other one */
	public void changeStatus() {
		if (hiddenStatus)
			hiddenStatus = false;
		else
			hiddenStatus = true;
	}

	@Override
	public Carta clone() {
		Carta c = new Carta(this.s, this.v);
		c.hiddenStatus = this.hiddenStatus;
		return c;
	}
}
