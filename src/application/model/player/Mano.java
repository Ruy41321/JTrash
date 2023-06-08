package application.model.player;

import java.util.Iterator;
import java.util.Observable;

import application.model.mazzo.Carta;

/**
 * This class represent the hand of the player helping to manage his cards
 * 
 * @author io
 *
 */
@SuppressWarnings("deprecation")
public class Mano extends Observable implements Iterable<Carta> {

	/** Hand of the player (array of him cards) */
	private Carta[] mano;

	/** Length of object */
	private int size;

	/**
	 * Basic Constructor
	 * 
	 * @param cards an array of Carta
	 */
	public Mano(Carta... cards) {
		mano = cards;
		size = mano.length;
	}

	/**
	 * getter of the size
	 * 
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * setter of mano
	 * 
	 * @param mano
	 */
	public void setMano(Carta... mano) {
		this.mano = mano;
		size = mano.length;
		setChanged();
		notifyObservers();
	}

	/**
	 * getter of mano
	 * 
	 * @param i index
	 * @return this.mano[i]
	 */
	public Carta get(int i) {
		return mano[i];
	}

	/**
	 * This method switch the card on the index with the new card
	 * 
	 * @param index
	 * @param newC
	 */
	public void switchCard(int index, Carta newC) {
		mano[index] = newC;
		setChanged();
		notifyObservers(index);
	}

	@Override
	public Iterator<Carta> iterator() {
		return new ManoIterator();
	}

	/**
	 * This is the Iterator class for mano
	 * 
	 * @author Luigi Pennisi
	 *
	 */
	private class ManoIterator implements Iterator<Carta> {

		/** this is the index to iterate */
		private int pos = 0;

		@Override
		public boolean hasNext() {
			return (pos < mano.length);
		}

		@Override
		public Carta next() {
			return mano[pos++];
		}

	}
}
