package application.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.Model;
import application.view.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class HomeController implements Initializable{

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
	private VBox botChooser;
	
	@FXML
	private VBox gameField;
	
	//cards
	@FXML
	private ImageView mazzo;

	@FXML
	private ImageView scarti;
	
	@FXML
	private VBox npc2_hand;
	
	@FXML
	private VBox npc3_hand;
	
	@FXML
	private ImageView u1;

	@FXML
	private ImageView u2;

	@FXML
	private ImageView u3;

	@FXML
	private ImageView u4;

	@FXML
	private ImageView u5;

	@FXML
	private ImageView u6;

	@FXML
	private ImageView u7;

	@FXML
	private ImageView u8;

	@FXML
	private ImageView u9;

	@FXML
	private ImageView u10;

	@FXML
	private ImageView np1_1;

	@FXML
	private ImageView np1_2;

	@FXML
	private ImageView np1_3;

	@FXML
	private ImageView np1_4;

	@FXML
	private ImageView np1_5;

	@FXML
	private ImageView np1_6;

	@FXML
	private ImageView np1_7;

	@FXML
	private ImageView np1_8;

	@FXML
	private ImageView np1_9;

	@FXML
	private ImageView np1_10;

	@FXML
	private ImageView np2_1;

	@FXML
	private ImageView np2_2;

	@FXML
	private ImageView np2_3;

	@FXML
	private ImageView np2_4;

	@FXML
	private ImageView np2_5;

	@FXML
	private ImageView np2_6;

	@FXML
	private ImageView np2_7;

	@FXML
	private ImageView np2_8;

	@FXML
	private ImageView np2_9;

	@FXML
	private ImageView np2_10;

	@FXML
	private ImageView np3_1;

	@FXML
	private ImageView np3_2;

	@FXML
	private ImageView np3_3;

	@FXML
	private ImageView np3_4;

	@FXML
	private ImageView np3_5;

	@FXML
	private ImageView np3_6;

	@FXML
	private ImageView np3_7;

	@FXML
	private ImageView np3_8;

	@FXML
	private ImageView np3_9;

	@FXML
	private ImageView np3_10;

	@FXML
	private ImageView np4_1;

	@FXML
	private ImageView np4_2;

	@FXML
	private ImageView np4_3;

	@FXML
	private ImageView np4_4;

	@FXML
	private ImageView np4_5;

	@FXML
	private ImageView np4_6;

	@FXML
	private ImageView np4_7;

	@FXML
	private ImageView np4_8;

	@FXML
	private ImageView np4_9;

	@FXML
	private ImageView np4_10;
	
	
	
//..\..\..\resource\varie\avatar.png
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//System.out.println(System.getProperty("user.dir"));
		String avatarPath = Model.getInstance().getUser().getAvatar();
		int nTot = Model.getInstance().getUser().getpTot();
		int nVinte = Model.getInstance().getUser().getVinte();
		int nPerse = Model.getInstance().getUser().getPerse();
		float lvlCompleto = Model.getInstance().getUser().getLivello();
		int lvlAttuale = (int) lvlCompleto;
		float expMancante = lvlCompleto - lvlAttuale;
		int lvlNext = lvlAttuale+1;
		
		avatar.setImage(new Image("file:"+avatarPath));
		username.setText(Model.getInstance().getUser().getName());
		totali.setText("Partite Totali: "+nTot);
		vinte.setText("Vinte: "+nVinte);
		perse.setText("Perse: "+nPerse);
		lvlAtt.setText(""+lvlAttuale);
		lvlSucc.setText(""+lvlNext);
		lvlBar.setProgress(expMancante);
		
	}
	
	
	//menu actions
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
	
	private void reloadAvatar() {
		String avatarPath = Model.getInstance().getUser().getAvatar();
		avatar.setImage(new Image("file:"+avatarPath));
	}
	
	public void vediStats() {
		try {
			Stage stats = new Stage();
			System.out.println(System.getProperty("user.dir"));
			Parent root = FXMLLoader.load(getClass().getResource("src/application/view/Login.fxml"));
			Scene scene = new Scene(root);
			stats.setScene(scene);
			stats.setTitle("JTrash - Le tue Statistiche");
			//stats.getIcons().add(new Image("file:resource/varie/icon16.png"));
			stats.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void chiudiTutto() {
		Model.getInstance().getUser().save();
		System.exit(0);
	}
	
	public void disconnect() {
		Model.getInstance().getUser().save();
		View.getInstance().changeScene("Login.fxml");
	}
	
	public void deleteProfile() {
		Model.getInstance().getUser().delete();
		View.getInstance().changeScene("Login.fxml");
	}
	
	//number of bot selection
	
	public void botSelection(ActionEvent e) {
		CheckBox select = (CheckBox) e.getSource();
		switch(select.getId()) {
			case "uno" ->
			{
				uno.setSelected(true);
				due.setSelected(false);
				tre.setSelected(false);
				selection = uno;
			}
			case "due"->
			{
				uno.setSelected(false);
				due.setSelected(true);
				tre.setSelected(false);
				selection = due;
			}
			case "tre"->
			{
				due.setSelected(false);
				uno.setSelected(false);
				tre.setSelected(true);
				selection = tre;
			}
			default ->
			{
				due.setSelected(false);
				uno.setSelected(false);
				tre.setSelected(false);
				selection = null;
			}
		}	
	}
	
	public void startsPlay() {
		if (selection == null)
			return;
		switch(selection.getId()) {
			case "uno":
			{
				npc2_hand.setVisible(false);
				npc3_hand.setVisible(false);
				break;
			}
			case "due":
			{
				npc3_hand.setVisible(false);
				break;
			}
			case "tre":
			{
				break;
			}
			default: break;
		}
		
		
		
		botChooser.setVisible(false);
		gameField.setVisible(true);
	}

}
