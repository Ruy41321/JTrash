module JTrashFX {
	requires javafx.controls;
	requires com.google.gson;
	requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	requires java.desktop;
	
	exports application.controller;

	opens application.model.mazzo to com.google.gson;
	opens application.model.player to com.google.gson;
	opens application.controller to javafx.fxml;
	opens application.view to javafx.graphics, javafx.fxml;
}
