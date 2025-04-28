package Application;

import java.awt.*;
import java.io.File;

//import javafx.scene.image.*;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;



public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
			Scene scene = new Scene(root,1280,720);
			String cssString = this.getClass().getResource("application.css").toExternalForm();
			scene.getStylesheets().add(cssString);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			/*
			primaryStage.setTitle("Ubongo");
			Image iconImage = new Image(new File("res/images/Ubongo-icon.jpg").toURI().toString());
			primaryStage.getIcons().add(iconImage);
			Controller.currentScreen = "StartScreen.fxml";
			Controller.storeCurrentScreen(Controller.currentScreen);
			Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
			Scene scene = new Scene(root, 1280, 720);
			String cssString = this.getClass().getResource("application.css").toExternalForm();
			scene.getStylesheets().add(cssString);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			Controller.playMedia();
			System.out.println("StartScreen opened");
			*/
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
