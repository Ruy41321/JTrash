package application.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.model.Model;
import application.model.mazzo.Carta;
import application.model.mazzo.Mazzo;
import application.model.player.Player;
import application.model.player.User;
import application.view.Mano;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

public class HomeController implements Initializable {
	/** Random generator */
	private static Random rand = new Random();

	@FXML
	private ImageView avatar;

	@FXML
	private Label username;

	@FXML
	private Label totali;

	@FXML
	private Label vinte;

	@FXML
	private Label perse;

	@FXML
	private Label lvlAtt;

	@FXML
	private ProgressBar lvlBar;

	@FXML
	private Label lvlSucc;

	@FXML
	private HBox footer;

	@FXML
	private CheckBox uno;

	@FXML
	private CheckBox due;

	@FXML
	private CheckBox tre;

	@FXML
	private VBox npcChooser;

	@FXML
	private VBox gameField;

	@FXML
	private Label allertBox;

	@FXML
	private Button leaveButton;

	@FXML
	private Button trashButton;

	@FXML
	private Button drawButton;

	@FXML
	private Button discardButton;

	// cards
	@FXML
	private VBox drawnCardContainer;

	@FXML
	private ImageView drawnCard;

	@FXML
	private ImageView mazzoView;

	@FXML
	private ImageView scarti;

	@FXML
	private VBox npc2_hand;

	@FXML
	private VBox npc3_hand;

	@FXML
	private Label npc1_name;

	@FXML
	private Label npc2_name;

	@FXML
	private Label npc3_name;

	@FXML
	private ImageView user1;

	@FXML
	private ImageView user2;

	@FXML
	private ImageView user3;

	@FXML
	private ImageView user4;

	@FXML
	private ImageView user5;

	@FXML
	private ImageView user6;

	@FXML
	private ImageView user7;

	@FXML
	private ImageView user8;

	@FXML
	private ImageView user9;

	@FXML
	private ImageView user10;

	@FXML
	private ImageView npc1_1;

	@FXML
	private ImageView npc1_2;

	@FXML
	private ImageView npc1_3;

	@FXML
	private ImageView npc1_4;

	@FXML
	private ImageView npc1_5;

	@FXML
	private ImageView npc1_6;

	@FXML
	private ImageView npc1_7;

	@FXML
	private ImageView npc1_8;

	@FXML
	private ImageView npc1_9;

	@FXML
	private ImageView npc1_10;

	@FXML
	private ImageView npc2_1;

	@FXML
	private ImageView npc2_2;

	@FXML
	private ImageView npc2_3;

	@FXML
	private ImageView npc2_4;

	@FXML
	private ImageView npc2_5;

	@FXML
	private ImageView npc2_6;

	@FXML
	private ImageView npc2_7;

	@FXML
	private ImageView npc2_8;

	@FXML
	private ImageView npc2_9;

	@FXML
	private ImageView npc2_10;

	@FXML
	private ImageView npc3_1;

	@FXML
	private ImageView npc3_2;

	@FXML
	private ImageView npc3_3;

	@FXML
	private ImageView npc3_4;

	@FXML
	private ImageView npc3_5;

	@FXML
	private ImageView npc3_6;

	@FXML
	private ImageView npc3_7;

	@FXML
	private ImageView npc3_8;

	@FXML
	private ImageView npc3_9;

	@FXML
	private ImageView npc3_10;

	private int currRound = 1;

	private boolean trashFlag;

	private CheckBox selection;

	private Carta cardToPlay;

	private Effect clickableEffect;

	private Mano userMano;

	private Mano npc1Mano;

	private Mano npc2Mano;

	private Mano npc3Mano;

	private Mano centerTable;

	private Player currPlayer;

	private Mazzo mazzo;

	private ArrayList<Player> players;

	private User user = Model.getInstance().getUser();

	/**
	 * This is the method called when the linked view is launched, it's needed to
	 * initialize the dynamic fields
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		updateStatsMenu();

		gameField.setVisible(false);
		npcChooser.setVisible(true);

		setClickableEffect(user2.getEffect());
		user2.setEffect(null);

		// Grouping the numerous Image of the cards on the field in arrays of Image who
		// indicates the Hands of the player
		userMano = new Mano(0, user1, user2, user3, user4, user5, user6, user7, user8, user9, user10);
		npc1Mano = new Mano(1, npc1_1, npc1_2, npc1_3, npc1_4, npc1_5, npc1_6, npc1_7, npc1_8, npc1_9, npc1_10);
		npc2Mano = new Mano(2, npc2_1, npc2_2, npc2_3, npc2_4, npc2_5, npc2_6, npc2_7, npc2_8, npc2_9, npc2_10);
		npc3Mano = new Mano(3, npc3_1, npc3_2, npc3_3, npc3_4, npc3_5, npc3_6, npc3_7, npc3_8, npc3_9, npc3_10);
		centerTable = new Mano(-1, scarti, drawnCard);
	}

	// **********************convenience methods************************

	/**
	 * This method returns the number of the card by his ImageView's Id
	 * 
	 * @param card Is the card from which return the position
	 * @param n    Is the number of chars in the ID before the number
	 * @return the integer that represent the position of the card in the hand
	 */
	private int getCardPosition(ImageView card, int n) {
		return Integer.parseInt(card.getId().substring(n)) - 1;
	}

	/**
	 * This method sets the clickable Effect property
	 * 
	 * <h1>Property Description:</h1> This variable of type Effect contain the
	 * effect that must have a clickable card to inform the user that it's
	 * clickable.
	 * 
	 * @param clickableEffect is the Effect with which sets the property
	 */
	private void setClickableEffect(Effect clickableEffect) {
		this.clickableEffect = clickableEffect;
	}

	/**
	 * This method shows a String got as input in the allertBox label in the middle
	 * of the screen (above the deck) applying the Fade Transition
	 * 
	 * @param text         Is the String with which sets the text of the allertBox
	 *                     in the center
	 * @param time         Is the time the transition must go on
	 * @param opacityStart Is the opacity the node starts with
	 * @param opacityEnd   Is the opacity with which the node will end
	 * @param reverse      Is a boolean needed to indicate if the transition have to
	 *                     disappear fading in reverse
	 * @param onFinish     Is the EventHandler with which set the OnFinished
	 *                     Transition Property
	 */
	private void fadeEffect(String text, Duration time, double opacityStart, double opacityEnd, boolean reverse,
			EventHandler<ActionEvent> onFinish) {
		allertBox.setText(text);
		FadeTransition fadeTransition = new FadeTransition(time, allertBox);
		// settings
		fadeTransition.setFromValue(opacityStart);
		fadeTransition.setToValue(opacityEnd);
		fadeTransition.setOnFinished(onFinish);
		if (reverse) {
			fadeTransition.setAutoReverse(true);
			fadeTransition.setCycleCount(2);
		}
		// start
		fadeTransition.play();
	}

	/**
	 * This method shows an Object got as input in the allertBox label in the middle
	 * of the screen (above the deck) applying the Fade Transition
	 * 
	 * @param JObj         Is the Object to downCast to Node and to show in the
	 *                     Transition
	 * @param time         Is the time the transition must go on
	 * @param opacityStart Is the opacity the node starts with
	 * @param opacityEnd   Is the opacity with which the node will end
	 * @param reverse      Is a boolean needed to indicate if the transition have to
	 *                     disappear fading in reverse
	 * @param onFinish     Is the EventHandler with which set the OnFinished
	 *                     Transition Property
	 */
	private void fadeEffect(Object JObj, Duration time, double opacityStart, double opacityEnd, boolean reverse,
			EventHandler<ActionEvent> onFinish) {
		FadeTransition fadeTransition = new FadeTransition(time, (Node) JObj);
		// settings
		fadeTransition.setFromValue(opacityStart);
		fadeTransition.setToValue(opacityEnd);
		fadeTransition.setOnFinished(onFinish);
		if (reverse) {
			fadeTransition.setAutoReverse(true);
			fadeTransition.setCycleCount(2);
		}
		fadeTransition.play(); // start
	}

	/**
	 * This method returns the ImageView of the player card on the position got as
	 * parameter
	 * 
	 * @param position The position of the card to return
	 * @return the ImageView of the card on the specified position
	 */
	private ImageView getCardViewByPosition(String position) {
		return switch (position) {
		case "A" -> user1;
		case "1" -> user1;
		case "2" -> user2;
		case "3" -> user3;
		case "4" -> user4;
		case "5" -> user5;
		case "6" -> user6;
		case "7" -> user7;
		case "8" -> user8;
		case "9" -> user9;
		case "10" -> user10;
		default -> null;
		};
	}

	/**
	 * This method apply to the Node got in input an effect to inform that it can be
	 * clicked and the EventHandler also got as parameter if the boolean got is
	 * true, else it set the effect and the event to null
	 * 
	 * @param JObj         Is the node on which set the effect
	 * @param isClickable  Is a boolean to know if apply or delete the effect
	 * @param onMouseClick is the EventHandler to set as onMouseClick Action
	 */
	private void setCardClickable(Node JObj, boolean isClickable, EventHandler<MouseEvent> onMouseClick) {
		if (isClickable) {
			JObj.setEffect(clickableEffect);
			JObj.setOnMouseClicked(onMouseClick);
		} else {
			JObj.setEffect(null);
			JObj.setOnMouseClicked(null);
		}
	}

	/**
	 * This method apply the clickableEffect on all cards that are still Hidden
	 */
	private void setAllHiddenCardsClickable() {
		userMano.toStream().filter(cardImage -> {
			return user.cardIsHidden(getCardPosition(cardImage, 4));
		}).forEach(cardImage -> {
			setCardClickable(cardImage, true, this::switchCard);
		});
	}

	/**
	 * This method starts the turn for the following player showing that it's his
	 * turn and setting the action to do after the fade transition is over that is
	 * different if the player is the user or an npc. If the next Player is a player
	 * who has already trashed it pass to the next round
	 */
	private void passToNextTurn() {
		if (Model.getInstance().getFollowingPlayer(currPlayer).getTrashStatus()) // if it's his turn and has trashed all
																					// the other played the last turn
		{
			nextRound();
			return;
		}
		currPlayer = Model.getInstance().getFollowingPlayer(currPlayer);
		/*
		 * the fadeEffect has to be set with the eventHandler to able the drawButton if
		 * it's the turn of user or to call the automatic playing methods
		 * (showCardToPlay and after it npcPlay) for npc
		 */
		EventHandler<ActionEvent> actionAfterShowName = (currPlayer instanceof User) ? event -> {
			drawButton.setDisable(false);
		} : /* else */ event -> {
			showCardToPlay(event2 -> {
				npcPlay();
			});
		};
		fadeEffect("E' il turno di: " + currPlayer.getName(), Duration.seconds(1), 0, 1, true, actionAfterShowName);
	}

	/**
	 * This method reset the cards on the table , mix the deck and distribute the
	 * new cards to the players simulating so a new round
	 */
	private void nextRound() {
		trashFlag = false;
		fadeEffect("Round n." + (++currRound), Duration.seconds(2), 0, 1, true, event -> {
			Model.getInstance().distributeCards();
			passToNextTurn();
		});
	}

	/**
	 * This method return the array of ImageView that represent the hand of a player
	 * 
	 * @param p Is the player which will back the hand
	 * @return an ImageView[] who has the instance of the single cards of the player
	 */
	private ImageView[] getHandImages(Player p) {
		if (p == players.get(0))
			return userMano.getMano();
		if (p == players.get(1))
			return npc1Mano.getMano();
		if (p == players.get(2))
			return npc2Mano.getMano();
		else
			return npc3Mano.getMano();
	}

	// *************top bar and menu actions*******************

	/**
	 * This Method refresh the view of the top Bar containing the player's info like
	 * the counts of the games and the level
	 */
	private void updateStatsMenu() {
		float lvlCompleto = user.getLivello();
		int lvlAttuale = ((int) lvlCompleto == 0) ? 1 : (int) lvlCompleto;
		float expMancante = lvlCompleto - lvlAttuale;

		avatar.setImage(new Image("file:" + user.getAvatar()));
		username.setText(user.getName());
		totali.setText("Partite Totali: " + user.getpTot());
		vinte.setText("Vinte: " + user.getVinte());
		perse.setText("Perse: " + user.getPerse());
		lvlAtt.setText("" + lvlAttuale);
		lvlSucc.setText("" + lvlAttuale + 1);
		lvlBar.setProgress(expMancante);
	}

	/**
	 * This method opens an file picker windows to permit the user to select an
	 * image to set as avatar. Only png and jpeg images are allowed
	 */
	public void changeAvatar() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Immagini", "*.png", "*.jpeg"));
		File f = fc.showOpenDialog(null);

		if (f != null) {
			user.setAvatar(f.getAbsolutePath());
			reloadAvatar();
			user.save();
		}
	}

	/**
	 * This method refresh the view of the avatar of the user
	 */
	private void reloadAvatar() {
		String avatarPath = user.getAvatar();
		avatar.setImage(new Image("file:" + avatarPath));
	}

	/**
	 * This method terminates the execution of the application saving first
	 */
	public void chiudiTutto() {
		user.save();
		System.exit(0);
	}

	/**
	 * This method log out the user going back in the login window saving and
	 * resetting the model first
	 */
	public void disconnect() {
		user.save();
		Model.reset();
		JTrash.changeScene("/application/view/Login.fxml", false, false);
	}

	/**
	 * This method delete the user's account going back in the login window
	 * resetting the model first
	 */
	public void deleteProfile() {
		user.delete();
		Model.reset();
		JTrash.changeScene("/application/view/Login.fxml", false, false);
	}

	// ********************bottom bar actions************************

	/**
	 * This method delete the ongoing game considering the player loser so update
	 * his statistics and switch back to the npc selector view.
	 */
	public void leaveGame() {
		int choose = JOptionPane.showConfirmDialog(null, "Stai per abbandonare la partita,\ndesideri continuare?",
				"Abbandona", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (choose == JOptionPane.OK_OPTION) {
			Model.getInstance().resetGame();
			Model.getInstance().updateUserStats(players.get(0).checkWin());
			JTrash.changeScene("/application/view/Home.fxml", true, true);
		}
	}

	/**
	 * This method is called when someone has trashed, if the player who trashed has
	 * won it ends the game else it advice that all the others players has one last
	 * turn to trash, this only if it's the first time in the round that this method
	 * is being called
	 */
	public void sayTrash() {
		trashButton.setDisable(true);
		discard();
		fadeEffect(currPlayer.getName() + " dice Trash !!!", Duration.seconds(2), 0, 1, true, event -> {
			if (currPlayer.checkWin()) {
				fadeEffect(currPlayer.getName().toUpperCase() + " HAI VINTO !!!", Duration.seconds(2), 0, 1, false,
						event3 -> {
							JOptionPane.showMessageDialog(null, "La partita è finita, grazie per aver giocato",
									"Partita conclusa", JOptionPane.INFORMATION_MESSAGE);

							Model.getInstance().resetGame();
							Model.getInstance().updateUserStats(players.get(0).checkWin());
							JTrash.changeScene("/application/view/Home.fxml", true, true);

						});
			} else if (!trashFlag) {
				trashFlag = true;
				fadeEffect("Avete un ultimo turno a testa !", Duration.seconds(1), 0, 1, true, event2 -> {
					passToNextTurn();
				});
			} else
				passToNextTurn();
		});
	}

	/**
	 * This method discard the current card on the table which can be the just drawn
	 * card or the switched one, and after to have discarded it
	 */
	public void discard() {
		mazzo.discard(mazzo.getCardToPlay());
		mazzo.setCardToPlay(null);
		discardButton.setDisable(true);
	}

	/**
	 * This is the discard method invoked by clicking on the discard button so from
	 * the user
	 * 
	 * @param e ActionEvent
	 */
	public void discard(ActionEvent e) {
		discard();
		passToNextTurn();
	}

	// *****************main game logic method************************

	/**
	 * This method works when the checks box to choose the number of npc are
	 * selected, the method remove the selection on the other checks box and set the
	 * variable selection = the clicked check box
	 * 
	 * @param e Is the Node who invoked the event/method
	 */
	public void npcSelection(ActionEvent e) {
		due.setSelected(false);
		uno.setSelected(false);
		tre.setSelected(false);

		selection = (CheckBox) e.getSource();
		selection.setSelected(true);
	}

	/**
	 * This method starts the game: using the selection property it works only if a
	 * number of npc is selected. Then depending on the number of npc choose it sets
	 * visible the hands of the npc Then initialize the Model with this number Then
	 * says to the Model to distribute the cards Then witch the view from the npc
	 * selection to the game table In the end able the player to play
	 */
	@SuppressWarnings("deprecation")
	public void startsPlay() {
		if (selection == null)
			return;
		npc2_hand.setVisible(false);
		npc3_hand.setVisible(false);
		switch (selection.getId()) {
		case "uno": {
			initModelVar(1);
			break;
		}
		case "due": {
			initModelVar(2);
			npc2_name.setText(players.get(2).getName());
			players.get(2).getMano().addObserver(npc2Mano);
			npc2_hand.setVisible(true);
			break;
		}
		case "tre": {
			initModelVar(3);
			npc2_name.setText(players.get(2).getName());
			players.get(2).getMano().addObserver(npc2Mano);
			npc3_name.setText(players.get(3).getName());
			players.get(3).getMano().addObserver(npc3Mano);
			npc2_hand.setVisible(true);
			npc3_hand.setVisible(true);
			break;
		}
		default:
			break;
		}
		npc1_name.setText(players.get(1).getName());
		players.get(0).getMano().addObserver(userMano);
		players.get(1).getMano().addObserver(npc1Mano);
		mazzo.addObserver(centerTable);

		Model.getInstance().distributeCards();

		npcChooser.setVisible(false);
		gameField.setVisible(true);
		drawnCardContainer.setVisible(true);

		leaveButton.setDisable(false);

		passToNextTurn();

	}

	/**
	 * This method initialize the array of player and the deck in the Model
	 * 
	 * @param n is the number of npc
	 */
	private void initModelVar(int n) {
		Model.getInstance().setPlayers(n);
		Model.getInstance().setMazzo(n);
		mazzo = Model.getInstance().getMazzo();
		players = Model.getInstance().getPlayers();
	}

	/**
	 * This method show in the view the image of the current card to play. The card
	 * is drawn if not present (it's present if is a card switched)
	 * 
	 * @param afterFading is the Event to run on the finish of the fade effect
	 */
	public void showCardToPlay(EventHandler<ActionEvent> afterFading) {
		Model.getInstance().checkIfMazzoHasNext();

		if (mazzo.getCardToPlay() == null)
			mazzo.setCardToPlay(mazzo.next()); // draws the cards if not present

		drawnCard.setOpacity(0);
		fadeEffect(drawnCard, Duration.seconds(1), 0, 1, false, afterFading);
	}

	/**
	 * This method is invoked by the user in the view when he/she draws a card. The
	 * method invoke the method to show the current card to play and allow the user
	 * to choose the card to switch
	 */
	public void userPlay() {
		showCardToPlay(null);
		cardToPlay = mazzo.getCardToPlay();
		if (user.cardIsOut(cardToPlay)) {
			// case when the card is bigger than 10 or than the player remaining cards
			// number
			discardButton.setDisable(false);
		} else {
			if (user.cardIsJolly(cardToPlay)) {
				// case when the card is King or Jolly
				setAllHiddenCardsClickable();
			} else if (!user.cardIsHidden(cardToPlay)) {
				// case when the card to replace is already replaced
				discardButton.setDisable(false);
			} else {
				// case when the card is a number and the card to replace is still hidden
				setCardClickable(getCardViewByPosition(cardToPlay.getValore().toCardValue()), true, this::switchCard);
			}
		}
		drawButton.setDisable(true);
	}

	/**
	 * This method switch the current card on the table(newC) with the clicked
	 * ImageView then it let the user turn go over
	 * 
	 * @param cardClicked Is the MouseEvent object on which the event has been
	 *                    invoked that is always an ImageView
	 */
	private void switchCard(MouseEvent cardClicked) {
		ImageView card = (ImageView) cardClicked.getSource(); // This method is called only by ImageView Nodes

		// switch in the model and in the view with the observer Observable
		mazzo.setCardToPlay(currPlayer.changeCard(mazzo.getCardToPlay(), getCardPosition(card, 4)));

		userMano.removeClickableEffects();

		// checks if Trash
		if (user.getTrashStatus()) {
			// drawButton.setDisable(true);
			trashButton.setDisable(false);
			return;
		}
		userPlay(); // play with the returned card of the switching
	}

	// ***********************main game logic method**********************
	// *****************************npc actions***************************

	/**
	 * This method checks if the card to play is switchable with one of the npc, if
	 * it can be switched it invoke the method to switch the cards else it discard
	 * it and pass the turn
	 * 
	 * @param npc Is the current npc playing
	 */
	private void npcPlay() {
		cardToPlay = mazzo.getCardToPlay();
		if (currPlayer.cardIsOut(cardToPlay)) {
			// case when the card is bigger than 10 or the player remaining cards number
			discard();
			passToNextTurn();
		} else {
			if (currPlayer.cardIsJolly(cardToPlay)) {
				// case when the card is King or Jolly
				npcSwitchCard();
			} else if (!currPlayer.cardIsHidden(cardToPlay)) {
				// case when the card to replace is already replaced
				discard();
				passToNextTurn();
				return;
			} else {
				// case when the card is a number and it's still hidden
				npcSwitchCard();
			}

			if (!currPlayer.getTrashStatus()) // it continue to to invoke itSelf method until do trash or discard
				showCardToPlay(event -> {
					npcPlay();
				});
			else
				sayTrash();
		}
	}

	/**
	 * This method switch the current card on the table (to play) with a card in the
	 * hand of the npc picking it randomly for jolly or king
	 */
	private void npcSwitchCard() {
		ImageView card;
		cardToPlay = mazzo.getCardToPlay();
		try {
			card = getHandImages(currPlayer)[cardToPlay.getV()];
		} catch (ArrayIndexOutOfBoundsException e) {
			// If there is this Exception means the drawn card was a King or Jolly
			int pos;
			do {
				pos = rand.nextInt(currPlayer.getCardNumber()); // Picking a random number to choose with which card
																// switch the
				// drawn one
			} while (!currPlayer.getCardFromHand(pos).getHiddenStatus()); // repeating the random picking until it
																			// choose an hidden card
			card = getHandImages(currPlayer)[pos];
		}

		// switch in the model and in the view with the observer Observable
		mazzo.setCardToPlay(currPlayer.changeCard(cardToPlay, getCardPosition(card, 5)));
	}

}
