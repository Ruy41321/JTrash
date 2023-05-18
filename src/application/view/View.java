package application.view;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class View extends Application {
	
	private static Stage stg;

	private static View instance;
	
	
	@Override
	public void start(Stage primaryStage) {
		stg = primaryStage;
		try {
			URL bg = getClass().getResource("/backgrounds");
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root);
			
			//primaryStage.setMaximized(true);
			primaryStage.setResizable(true);
			primaryStage.setScene(scene);
			primaryStage.setTitle("JTrash");
			primaryStage.getIcons().add(new Image(getClass().getResource("/varie/icon16.png").toString()));
			
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static Stage getStg() {
		return stg;
	}
	
	public void changeScene(String fxml){
		try {
			stg.hide();
			Parent root = FXMLLoader.load(getClass().getResource(fxml));
			stg.getScene().setRoot(root);
			stg.show();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public View() {
		instance = this;
	}
	
	public static View getInstance() {
		if (instance == null) {
			return new View();
		}
		return instance;
	}

	public static void lancia() {
		launch();
	}

}
