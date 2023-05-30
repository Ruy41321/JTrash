package application.controller;

import java.util.Observable;
import java.util.Observer;

import application.model.Model;
import application.model.mazzo.Carta;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@SuppressWarnings("deprecation")
public class Mano implements Observer {

	private int playerIndex;
	private int size;
	private ImageView[] mano;
	public static final String HiddenPath = "file:../../res/cards/card back/cardBackRed.png";

	/**
	 * This method return the relative path of a card got in input. The path is set
	 * to work with the current directory deployment
	 * 
	 * @param card is The card which return the path
	 * @return path as String
	 */
	public static final String getCardPath(Carta card) {
		try {
			if (card.getHidenStatus())
				return HiddenPath;
			String seme = card.getSeme().toEnglishString();
			String valore = card.getValore().toCardValue();
			return "file:../../res/cards/" + seme.toLowerCase() + "/card" + seme + "_" + valore + ".png";
		} catch (NullPointerException e) {
			return HiddenPath;
		}
	}

	/**
	 * Constructor of Mano
	 * 
	 * @param playerIndex is an index to understand the owner of the mano or if it's
	 *                    the center of the table (discard and card to play)
	 * @param mano is the array of ImageView that the class manage
	 */
	public Mano(int playerIndex, ImageView... mano) {
		this.mano = mano;
		size = mano.length;
		this.playerIndex = playerIndex;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(playerIndex != -1)
			size = Model.getInstance().getPlayers().get(playerIndex).getMano().getSize();
		if (size != mano.length) {
			for (int i = size; i<mano.length; i++)
				mano[i].setVisible(false);
		}
		if (arg != null)
			refreshCard((int) arg);
		else
			for (int i = 0; i < size; i++)
				refreshCard(i);
	}

	/**
	 * This method refresh the view of a specific card by the index
	 * 
	 * @param i is the index of the card to refresh if are card of hands else it's
	 *          needed to understand if refresh the cardToPlay or the discardPile
	 */
	public void refreshCard(int i) {
		Carta card = (playerIndex != -1) ? Model.getInstance().getPlayers().get(playerIndex).getMano().get(i)
				: (i == 0) ? Model.getInstance().getMazzo().getLastDiscard()
						: Model.getInstance().getMazzo().getCardToPlay();
		mano[i].setImage(new Image(getCardPath(card)));
	}

	/**
	 * getters of mano
	 * 
	 * @return ImageView[] mano
	 */
	public ImageView[] getMano() {
		return mano;
	}

	/**
	 * setters of mano
	 * 
	 * @param mano ImageView[]
	 */
	private void setMano(ImageView[] mano) {
		this.mano = mano;
	}

	/**
	 * This methods remove the clickable effect and onMouseEvent from each card
	 */
	public void removeClickableEffects() {
		for (ImageView card : mano) {
			card.setOnMouseClicked(null);
			card.setEffect(null);
		}
	}

}
