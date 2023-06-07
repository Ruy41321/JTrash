package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.model.player.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;

/**
 * This class is used to control the login view
 * 
 * @author Luigi Pennisi
 *
 */
public class LoginController implements Initializable {
	/**
	 * this field is the field to get the user name
	 */
	@FXML
	private TextField username;

	/**
	 * this field is the field to get the password
	 */
	@FXML
	private TextField password;

	/**
	 * this field is used to print an error message on the view
	 */
	@FXML
	private Label errorMessage;

	/**
	 * this field is used to save the effect of an error input field
	 */
	private Effect errorEffect;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		errorEffect = username.getEffect();
		username.setEffect(null);

		username.setFocusTraversable(true);
		password.setFocusTraversable(true);
	}

	/**
	 * Method to set an error message to show the user what went wrong
	 * 
	 * @param message The message to print
	 * @param f       is a flag to identify if the problem was with the user's
	 *                name(1), password(2) or both(0)
	 */
	private void setError(String message, int f) {
		switch (f) {
		case 0:
			username.setText("");
			password.setText("");
			username.setEffect(errorEffect);
			password.setEffect(errorEffect);
			break;
		case 1:
			username.setText("");
			username.setEffect(errorEffect);
			password.setEffect(null);
			break;
		case 2:
			password.setText("");
			password.setEffect(errorEffect);
			username.setEffect(null);
			break;
		default:
			break;
		}
		errorMessage.setText(message);
		errorMessage.setVisible(true);
	}

	/**
	 * This method is invoked when the user click on login, the method tries to
	 * login with the credentials got, if it success it change scene going in the
	 * home, else show to the user an error
	 * 
	 */
	public void doLogin() {
		AudioManager.getInstance().play(AudioManager.clickSound);
		;
		if (username.getText().equals("")) {
			username.setText("giggio");
			password.setText("ciccio");
		}
		if (User.login(username.getText(), password.getText()) != null) {
			JTrash.changeStage("/application/view/Home.fxml", true, true);
		} else {
			setError("Riprovare: Password o Username errati", 0);
		}

	}

	/**
	 * This method is invoked clicking on "Registrati", the method checks if the
	 * user name and password has the right requisite else show an error then if
	 * they are good tries to sign up, if it has success invoke the login method to
	 * execute the login with the same credentials for the sign up which will be
	 * always correct. If it finds a problem in the sign up it shows an error
	 * message
	 * 
	 */
	public void doSignup() {
		AudioManager.getInstance().play(AudioManager.clickSound);
		;
		if (username.getText().length() < 4) {
			setError("Username non valido, minimo 4 caratteri", 1);
		} else if (username.getText().length() > 10) {
			setError("Username non valido, massimo 10 caratteri", 1);
		} else if (password.getText().length() < 4) {
			setError("Password non valida, minimo 4 caratteri", 2);
		} else if (User.signup(username.getText(), password.getText())) {
			doLogin();
		} else {
			setError("Riprovare: Username già registrato", 1);
		}
	}
}
