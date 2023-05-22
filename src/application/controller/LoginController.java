package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.model.Model;
import application.view.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController implements Initializable{

	public LoginController() {
		// TODO Auto-generated constructor stub
	}


	@FXML
	private TextField username;
	
	@FXML
	private TextField password;
	
	@FXML
	private Label wrongMessage;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Model.reset();
	}
	
	public void doLogin(ActionEvent event){
		if(Model.login(username.getText(), password.getText()) != null){
			View.getInstance().changeScene("Home.fxml");
		}else
		{
			username.setText("");
			password.setText("");
			wrongMessage.setText("Riprovare: Password o Username errati");
			wrongMessage.setVisible(true);
		}
		
	}
	
	public void doSignup(ActionEvent event){
		if(Model.signup(username.getText(), password.getText())){
			doLogin(event);
		}else
		{
			username.setText("");
			password.setText("");
			wrongMessage.setText("Riprovare: Username già registrato");
			wrongMessage.setVisible(true);
		}
		
	}


}
