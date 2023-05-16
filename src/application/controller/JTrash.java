package application.controller;

import application.model.Model;
import application.view.View;

public class JTrash {

	private static Model model;
	private static View view;
	
	public JTrash() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		model = Model.getInstance();
		view = View.getInstance();
		
		View.lancia();
		
	}

}
