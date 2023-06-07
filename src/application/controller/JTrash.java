package application.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * This is the main application class, here is contained the main method to
 * start the process
 * 
 * @author Luigi Pennisi
 *
 */
public class JTrash extends Application {

	/**
	 * This property is used to externalize the primaryStage variable and make it
	 * accessible and so editable from other method like the changeStage one which
	 * use it to change stage
	 */
	private static Stage primaryStage;

	/**
	 * This method starts the application showing the first stage, that is the Login
	 * scene. the first stage is not resizable.
	 */
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
			
			AudioManager.getInstance().setBackgroundMusic(AudioManager.jazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to change the main Stage
	 * 
	 * @param fxml        is the path of the fxml file from which load the root
	 * @param isMaximized is a boolean to indicate if the stage has to be maximized
	 * @param isResizable is a boolean to indicate if the stage has to be resizable
	 */
	public static void changeStage(String fxml, boolean isMaximized, boolean isResizable) {
		try {
			primaryStage.hide();
			Parent root = FXMLLoader.load(JTrash.class.getResource(fxml));
			primaryStage.setMaximized(isMaximized);
			primaryStage.setResizable(isResizable);
			primaryStage.getScene().setRoot(root);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The main method from which starts the application launching the first stage(
	 * Login )
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch();
	}
}
