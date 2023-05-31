package application.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class JTrash extends Application {
	
	private static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		JTrash.primaryStage = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/view/Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			
			primaryStage.setResizable(false);
			primaryStage.setTitle("JTrash");
			primaryStage.getIcons().add(new Image(getClass().getResource("/varie/icon16.png").toString()));
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void changeScene(String fxml, boolean isMaximized, boolean isResizable){
		try {
			primaryStage.hide();
			Parent root = FXMLLoader.load(JTrash.class.getResource(fxml));
			primaryStage.setMaximized(isMaximized);
			primaryStage.setResizable(isResizable);
			primaryStage.getScene().setRoot(root);
			primaryStage.show();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch();
	}
}
