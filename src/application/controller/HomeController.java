package application.controller;

import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.model.Model;
import application.model.mazzo.Carta;
import application.model.player.Player;
import application.view.View;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
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

	private CheckBox selection;

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

	private Carta newC;

	@FXML
	private ImageView mazzo;

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

	private Effect clickableEffect;
	
	private Image hiddenCardImage;

	private ImageView[] userCards;

	private ImageView[] npc1Cards;

	private ImageView[] npc2Cards;

	private ImageView[] npc3Cards;

	private int npcWhosPlaying = 0;

	/**
	 * This is the method called when the linked view is launched, it's needed to initialize the dynamic fields 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		updateStatsMenu();

		gameField.setVisible(false);
		npcChooser.setVisible(true);
		drawnCardContainer.setVisible(false);

		setClickableEffect(user2.getEffect());
		user2.setEffect(null);

		//Grouping the numerous Image of the cards on the field in arrays of Image who indicates the Hands of the player
		ImageView[] tempUserHand = { user1, user2, user3, user4, user5, user6, user7, user8, user9, user10 };
		this.userCards = tempUserHand;
		ImageView[] tempNpc1Hand = { npc1_1, npc1_2, npc1_3, npc1_4, npc1_5, npc1_6, npc1_7, npc1_8, npc1_9, npc1_10 };
		this.npc1Cards = tempNpc1Hand;
		ImageView[] tempNpc2Hand = { npc2_1, npc2_2, npc2_3, npc2_4, npc2_5, npc2_6, npc2_7, npc2_8, npc2_9, npc2_10 };
		this.npc2Cards = tempNpc2Hand;
		ImageView[] tempNpc3Hand = { npc3_1, npc3_2, npc3_3, npc3_4, npc3_5, npc3_6, npc3_7, npc3_8, npc3_9, npc3_10 };
		this.npc3Cards = tempNpc3Hand;
		
		hiddenCardImage = user1.getImage();
	}
	
	//**********************convenience methods************************

	/**
	 * This method return the clickableEffect
	 * 
	 * <h1>Property Description:</h1>
	 * This variable of type Effect contain the effect that must have a clickable card to inform the user that it's clickable.
	 * 
	 * @return
	 * the Effect of a clickable card
	 */
	public Effect getClickableEffect() {
		return clickableEffect;
	}

	/**
	 * This method sets the clickable Effect property
	 * 
	 * <h1>Property Description:</h1>
	 * This variable of type Effect contain the effect that must have a clickable card to inform the user that it's clickable.
	 * 
	 * @param clickableEffect is the Effect with which sets the property
	 */
	public void setClickableEffect(Effect clickableEffect) {
		this.clickableEffect = clickableEffect;
	}

	/**
	 * This method shows a String got as input in the allertBox label in the middle of the screen (above the deck) applying the Fade Transition
	 * 
	 * @param text Is the String with which sets the text of the allertBox in the center
	 * @param time Is the time the transition must go on
	 * @param opacityStart Is the opacity the node starts with
	 * @param opacityEnd Is the opacity with which the node will end
	 * @param reverse Is a boolean needed to indicate if the transition have to disappear fading in reverse
	 * @param onFinish Is the EventHandler with which set the OnFinished Transition Property 
	 */
	public void fadeEffect(String text, Duration time, double opacityStart, double opacityEnd,  boolean reverse, EventHandler<ActionEvent> onFinish) {
		allertBox.setText(text);
		FadeTransition fadeTransition = new FadeTransition(time, allertBox);
		//settings
		fadeTransition.setFromValue(opacityStart);
		fadeTransition.setToValue(opacityEnd);
		fadeTransition.setOnFinished(onFinish);
		if (reverse) {
			fadeTransition.setAutoReverse(true);
			fadeTransition.setCycleCount(2);
		}
		//start
		fadeTransition.play();
	}
	
	/**
	 * This method shows an Object got as input in the allertBox label in the middle of the screen (above the deck) applying the Fade Transition
	 * 
	 * @param JObj Is the Object to downCast to Node and to show in the Transition
	 * @param time Is the time the transition must go on
	 * @param opacityStart Is the opacity the node starts with
	 * @param opacityEnd Is the opacity with which the node will end
	 * @param reverse Is a boolean needed to indicate if the transition have to disappear fading in reverse
	 * @param onFinish Is the EventHandler with which set the OnFinished Transition Property 
	 */
	public void fadeEffect(Object JObj, Duration time, double opacityStart, double opacityEnd,  boolean reverse, EventHandler<ActionEvent> onFinish) {
		FadeTransition fadeTransition = new FadeTransition(time, (Node) JObj);
		//settings
		fadeTransition.setFromValue(opacityStart);
		fadeTransition.setToValue(opacityEnd);
		fadeTransition.setOnFinished(onFinish);
		if (reverse) {
			fadeTransition.setAutoReverse(true);
			fadeTransition.setCycleCount(2);
		}
		//start
		fadeTransition.play();
	}
	
	/**
	 * This method able the User to play fading in that is his turn
	 */
	public void switchToUserTurn() {
		fadeEffect(username.getText() + " è il tuo turno !!!", Duration.seconds(2), 0, 1, true, null);
		drawButton.setDisable(false);
	}
	
	// ****************************top bar and menu actions***************************

	/**
	 * This Method refresh the view of the top Bar containing the player's info like the counts of the games and the level
	 */
	private void updateStatsMenu() {
		String avatarPath = Model.getInstance().getUser().getAvatar();
		int nTot = Model.getInstance().getUser().getpTot();
		int nVinte = Model.getInstance().getUser().getVinte();
		int nPerse = Model.getInstance().getUser().getPerse();
		float lvlCompleto = Model.getInstance().getUser().getLivello();
		int lvlAttuale = ((int) lvlCompleto == 0) ? 1 : (int) lvlCompleto;
		float expMancante = lvlCompleto - lvlAttuale;
		int lvlNext = lvlAttuale + 1;

		avatar.setImage(new Image("file:" + avatarPath));
		username.setText(Model.getInstance().getUser().getName());
		totali.setText("Partite Totali: " + nTot);
		vinte.setText("Vinte: " + nVinte);
		perse.setText("Perse: " + nPerse);
		lvlAtt.setText("" + lvlAttuale);
		lvlSucc.setText("" + lvlNext);
		lvlBar.setProgress(expMancante);
	}
	
	/**
	 * This method opens an file picker windows to permit the user to select an image to set as avatar.
	 * Only png and jpeg images are allowed
	 */
	public void changeAvatar() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Immagini", "*.png", "*.jpeg"));
		File f = fc.showOpenDialog(null);

		if (f != null) {
			Model.getInstance().getUser().setAvatar(f.getAbsolutePath());
			reloadAvatar();
			Model.getInstance().getUser().save();
		}

	}

	/**
	 * This method refresh the view of the avatar of the user
	 */
	private void reloadAvatar() {
		String avatarPath = Model.getInstance().getUser().getAvatar();
		avatar.setImage(new Image("file:" + avatarPath));
	}

	/**
	 * This method terminates the execution of the application saving first
	 */
	public void chiudiTutto() {
		Model.getInstance().getUser().save();
		System.exit(0);
	}

	/**
	 * This method log out the user going back in the login window saving and resetting the model first
	 */
	public void disconnect() {
		Model.getInstance().getUser().save();
		Model.reset();
		View.getInstance().changeScene("Login.fxml");
	}

	/**
	 * This method delete the user's account going back in the login window resetting the model first
	 */
	public void deleteProfile() {
		Model.getInstance().getUser().delete();
		Model.reset();
		View.getInstance().changeScene("Login.fxml");
	}

	//********************bottom bar actions************************
	
	/**
	 * This method delete the ongoing game considering the player loser so update his statistics and switch back to the npc selector view.
	 */
	public void leaveGame() {
		int choose = JOptionPane.showConfirmDialog(null,
				"Stai per abbandonare la partita,\n quest'ultima verrà considerata come persa,\n desideri continuare?",
				"Abbandona", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (choose == JOptionPane.OK_OPTION) {
			Model.getInstance().updateUserStats(false);
			Model.getInstance().resetGame();
			leaveButton.setDisable(true);
			gameField.setVisible(false);
			drawnCardContainer.setVisible(false);
			npcChooser.setVisible(true);
			updateStatsMenu();
			
			//resets the view of the cards on the table
			scarti.setImage(null);
			for (ImageView img : userCards)
				img.setImage(hiddenCardImage);
			for (ImageView img : npc1Cards)
				img.setImage(hiddenCardImage);
			for (ImageView img : npc2Cards)
				img.setImage(hiddenCardImage);
			for (ImageView img : npc3Cards)
				img.setImage(hiddenCardImage);
		}
	}
	
	/**
	 * to upload: manage the user trash action
	 */
	public void sayTrash() {
		fadeEffect(username.getText() + " dice Trash !!!", Duration.seconds(2), 0, 1, true, event -> {
			npcsStartPlay();
		});
		trashButton.setDisable(true);
	}
	
	/**
	 * This method discard the current unswitchable card on the table which can be the just drawn card or the switched one,
	 * and after to have discarded it invoke the method to allow the npcs to start to play
	 */
	public void discardCard() {
		Model.getInstance().getMazzo().discard(newC);
		updateDiscardPile();
		newC = null;
		drawnCardContainer.setVisible(false);
		discardButton.setDisable(true);

		npcsStartPlay();
	}
	
	// *****************main game logic method************************
	
	/**
	 * This method works when the checks box to choose the number of npc are selected, the method remove the selection on the 
	 * other checks box and set the variable selection = the clicked check box
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
	 * This method starts the game: using the selection property it works only if a number of npc is selected.
	 * Then depending on the number of npc choose it sets visible the hands of the npc
	 * Then initialize the Model with this number
	 * Then says to the Model to distribute the cards
	 * Then witch the view from the npc selection to the game table
	 * In the end able the player to play
	 */
	public void startsPlay() {
		if (selection == null)
			return;
		switch (selection.getId()) {
		case "uno": {
			Model.getInstance().setPlayers(1);
			Model.getInstance().setMazzo(1);
			npc1_name.setText(Model.getInstance().getPlayers()[1].getName());
			npc2_hand.setVisible(false);
			npc3_hand.setVisible(false);
			break;
		}
		case "due": {
			Model.getInstance().setPlayers(2);
			Model.getInstance().setMazzo(2);
			npc1_name.setText(Model.getInstance().getPlayers()[1].getName());
			npc2_name.setText(Model.getInstance().getPlayers()[2].getName());
			npc2_hand.setVisible(true);
			npc3_hand.setVisible(false);
			break;
		}
		case "tre": {
			Model.getInstance().setPlayers(3);
			Model.getInstance().setMazzo(3);
			npc1_name.setText(Model.getInstance().getPlayers()[1].getName());
			npc2_name.setText(Model.getInstance().getPlayers()[2].getName());
			npc3_name.setText(Model.getInstance().getPlayers()[3].getName());
			npc2_hand.setVisible(true);
			npc3_hand.setVisible(true);
			break;
		}
		default:
			break;
		}

		Model.getInstance().distributeCards();
		updateDiscardPile();

		npcChooser.setVisible(false);
		gameField.setVisible(true);

		leaveButton.setDisable(false);

		switchToUserTurn();

	}

	/**
	 * 
	 */
	public void playCard() {
		Model.getInstance().checkIfMazzoHasNext();
		if (newC == null)
			newC = Model.getInstance().getMazzo().next(); // draws the cards if not present
		String seme = newC.getSeme().toEnglishString();
		String valore = newC.getValore().toEnglishStringValue();
		drawnCard.setImage(
				new Image("file:../../res/cards/" + seme.toLowerCase() + "/card" + seme + "_" + valore + ".png"));
		drawnCardContainer.setVisible(true);
		fadeEffect(drawnCard, Duration.seconds(1), 0, 1, false, null);

		// da implementare tutti i controlli per gestire le figure e le carte gia
		// piazzate
		if (Model.getInstance().getUser().cardIsOut(newC)) {
			// case when the card is bigger than 10 or than the player remaining cards number
			discardButton.setDisable(false);
		} else {
			if (Model.getInstance().getUser().cardIsJolly(newC)) {
				// case when the card is King or Jolly
				applyClickableEffectToAllHidenCards();
			} else if (!Model.getInstance().getUser().cardIsHidden(newC)) {
				// case when the card to replace is already replaced
				discardButton.setDisable(false);
			} else {
				// case when the card is a number and the card to replace is still hidden
				applyClickableEffect(valore);
			}
		}
		drawButton.setDisable(true);
	}

	private void applyClickableEffectToAllHidenCards() {
		for (int i = 0; i < Model.getInstance().getUser().getCardNumber(); i++) {
			if (Model.getInstance().getUser().cardIsHidden(i))
				applyClickableEffect(String.valueOf(i + 1));
		}
	}
	
	public ImageView getCardViewByValue(String cardValue) {
		return switch (cardValue) {
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

	public void applyClickableEffect(String cardValue) {
		ImageView card = switch (cardValue) {
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
		default -> throw new IllegalArgumentException("Unexpected value: " + cardValue);
		};
		card.setEffect(clickableEffect);
		card.setOnMouseClicked(event -> {
			switchCard(card);
		});
	}

	public void unClickableCard(ImageView card) {
		card.setEffect(null);
		card.setOnMouseClicked(null);
	}

	public void switchCard(ImageView card) {
		// switch in the model
		newC = Model.getInstance().getUser().changeCard(newC, Integer.parseInt(card.getId().substring(4)) - 1).clone();
		Model.getInstance().getUser().setTrashStatus();
		// switch in the view
		card.setImage(drawnCard.getImage());
		drawnCardContainer.setVisible(false);

		for (ImageView img : userCards) {
			unClickableCard(img);
		}

		// checks if Trash
		if (Model.getInstance().getUser().getTrashStatus()) {
			drawButton.setDisable(true);
			trashButton.setDisable(false);
			return;
		}
		playCard(); // play with the returned card of the switching
	}

	public void npcSwitchCard(Player npc, ImageView[] npcHand) {
		ImageView card;
		try {
			card = npcHand[newC.getV()];
		} catch (ArrayIndexOutOfBoundsException e) {
			// If there is this Exception means the drawn card was a King or Jolly
			int pos;
			do {
				pos = rand.nextInt(npc.getCardNumber()); // Picking a random number to choose with which card switch the
															// drawn one
			} while (!npc.getCardFromHand(pos).getHidenStatus()); // repeating the random picking until it choose an
																	// hidden card
			card = npcHand[pos];
		}

		// switch in the model
		newC = npc.changeCard(newC, Integer.parseInt(card.getId().substring(5)) - 1).clone();
		npc.setTrashStatus();
		// switch in the view
		card.setImage(drawnCard.getImage());
		drawnCardContainer.setVisible(false);

		// checks if Trash
		if (npc.getTrashStatus()) {
			fadeEffect(npc.getName() + " dice Trash !!!", Duration.seconds(2), 0, 1, true, null);
		}
	}


	public void npcDiscardsCard() {
		Model.getInstance().getMazzo().discard(newC);
		updateDiscardPile();
		newC = null;
		drawnCardContainer.setVisible(false);
	}

	/**
	 * This Method manage the turns of the npcs to permit to play in the anti-hour sense
	 */
	private void npcsStartPlay() {
		//npc2 if it's present plays
		if (npc2_hand.isVisible()) {
			allertBox.setText("E' il turno di: " + Model.getInstance().getPlayers()[2].getName());
			showNpcTurnFading(allertBox, Duration.seconds(1), Model.getInstance().getPlayers()[2], npc2Cards);
			
			//npcPlay(Model.getInstance().getPlayers()[2], npc2Cards);
		}else {
		//npc1 which is always present plays
			allertBox.setText("E' il turno di: " + Model.getInstance().getPlayers()[1].getName());
			showNpcTurnFading(allertBox, Duration.seconds(1), Model.getInstance().getPlayers()[1], npc1Cards);
		}
		
		//npcPlay(Model.getInstance().getPlayers()[1], npc1Cards);
		/*
		//npc3 if it's present plays
		if (npc3_hand.isVisible()){
			allertBox.setText("E' il turno di: " + Model.getInstance().getPlayers()[3].getName());
			showNpcTurnFading(allertBox, Duration.seconds(2), Model.getInstance().getPlayers()[3], npc3Cards);
			
			//npcPlay(Model.getInstance().getPlayers()[3], npc3Cards);
		}
		//allow the user to play printing that is his turn
		drawButton.setDisable(false);
		allertBox.setText(username.getText() + " è il tuo turno");
		fadeIn(allertBox, Duration.seconds(2), true);
		*/
	}

	private void showNpcTurnFading(Object JObj, Duration delay, Player npc, ImageView[] hand) {
		npcWhosPlaying++;
		FadeTransition fadeTransition = new FadeTransition(delay, (Node) JObj);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeTransition.setOnFinished(event -> {
			FadeTransition fadeTransition2 = new FadeTransition(delay, (Node) JObj);
			fadeTransition2.setFromValue(1);
			fadeTransition2.setToValue(0);
			fadeTransition2.setOnFinished(e -> {
				npcShowCardToPlay(Duration.seconds(1), npc, hand);
			});
			fadeTransition2.play();
		});
		fadeTransition.play();
	}
	
	private void npcShowCardToPlay(Duration delay, Player npc, ImageView[] hand) {
		Model.getInstance().checkIfMazzoHasNext();
		if (newC == null)
			newC = Model.getInstance().getMazzo().next(); // draws the cards if not present
		String seme = newC.getSeme().toEnglishString();
		String valore = newC.getValore().toEnglishStringValue();
		drawnCard.setImage(
				new Image("file:../../res/cards/" + seme.toLowerCase() + "/card" + seme + "_" + valore + ".png"));
		drawnCard.setOpacity(0);
		drawnCardContainer.setVisible(true);
		FadeTransition fadeTransition = new FadeTransition(delay, drawnCard);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeTransition.setOnFinished(event -> {
			npcPlay(npc, hand);
			
		});
		fadeTransition.play();
	}

	private void npcPlay(Player npc, ImageView[] npcHand) {
		/*Model.getInstance().checkIfMazzoHasNext();
		if (newC == null)
			newC = Model.getInstance().getMazzo().next(); // draws the cards if not present
		String seme = newC.getSeme().toEnglishString();
		String valore = newC.getValore().toEnglishStringValue();
		drawnCard.setImage(
				new Image("file:../../res/cards/" + seme.toLowerCase() + "/card" + seme + "_" + valore + ".png"));
		drawnCardContainer.setVisible(true);

		//while (!isTransitionFinished);
		isTransitionFinished = false;
		fadeIn(drawnCardContainer, Duration.seconds(1), false);
		//while (!isTransitionFinished);
		isTransitionFinished = false;
*/
		if (npc.cardIsOut(newC)) {
			// case when the card is bigger than 10 or the player remaining cards number
			npcDiscardsCard();

			if (npcWhosPlaying == 1 && npc2_hand.isVisible()) {
			//	npcWhosPlaying ++;
				allertBox.setText("E' il turno di: " + Model.getInstance().getPlayers()[1].getName());
				showNpcTurnFading(allertBox, Duration.seconds(1), Model.getInstance().getPlayers()[1], npc1Cards);
			}else if (npcWhosPlaying == 2 && npc3_hand.isVisible()) {
			//	npcWhosPlaying ++;
				allertBox.setText("E' il turno di: " + Model.getInstance().getPlayers()[3].getName());
				showNpcTurnFading(allertBox, Duration.seconds(1), Model.getInstance().getPlayers()[3], npc3Cards);
			}else if (npcWhosPlaying == 1 && !npc2_hand.isVisible()) {
				npcWhosPlaying = 0;
				;
			}else if (npcWhosPlaying == 3) {
				npcWhosPlaying = 0;
				switchToUserTurn();
			}
			return;
		} else {
			if (npc.cardIsJolly(newC)) {
				// case when the card is King or Jolly
				npcSwitchCard(npc, npcHand);
			} else if (!npc.cardIsHidden(newC)) {
				// case when the card to replace is already replaced
				npcDiscardsCard();
				if (npcWhosPlaying == 1 && npc2_hand.isVisible()) {
					//	npcWhosPlaying ++;
						allertBox.setText("E' il turno di: " + Model.getInstance().getPlayers()[1].getName());
						showNpcTurnFading(allertBox, Duration.seconds(1), Model.getInstance().getPlayers()[1], npc1Cards);
					}else if (npcWhosPlaying == 2 && npc3_hand.isVisible()) {
					//	npcWhosPlaying ++;
						allertBox.setText("E' il turno di: " + Model.getInstance().getPlayers()[3].getName());
						showNpcTurnFading(allertBox, Duration.seconds(1), Model.getInstance().getPlayers()[3], npc3Cards);
					}else if (npcWhosPlaying == 1 && !npc2_hand.isVisible()) {
						npcWhosPlaying = 0;
						switchToUserTurn();
					}else if (npcWhosPlaying == 3) {
						npcWhosPlaying = 0;
						switchToUserTurn();
					}
				return;
			} else {
				// case when the card is a number and it's still hidden
				npcSwitchCard(npc, npcHand);
			}
		}
		if (!npc.getTrashStatus()) //it continue to to invoke itSelf method until the npc do trash or discard
			npcShowCardToPlay(Duration.seconds(1), npc, npcHand);
	}

	private void updateDiscardPile() {
		Carta scarto = Model.getInstance().getMazzo().getLastDiscard();
		String seme = scarto.getSeme().toEnglishString();
		String valore = scarto.getValore().toEnglishStringValue();
		scarti.setImage(
				new Image("file:../../res/cards/" + seme.toLowerCase() + "/card" + seme + "_" + valore + ".png"));
	}
}
