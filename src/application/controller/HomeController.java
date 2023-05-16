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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
	
	public HomeController() {
		// TODO Auto-generated constructor stub
	}
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
	
	public void changeAvatar(ActionEvent e) {
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
			stats.getIcons().add(new Image("file:resource/varie/icon16.png"));
			stats.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void chiudiTutto() {
		Model.getInstance().getUser().save();
		System.exit(0);
	}

}
