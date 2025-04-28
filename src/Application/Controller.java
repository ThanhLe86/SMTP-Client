package Application;

/* control GUI interactions */

import java.awt.Event;
import java.awt.MediaTracker;
import java.io.File;
//import java.awt.Event;
import java.io.IOException;
import java.net.URL;
import java.nio.file.attribute.PosixFileAttributes;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;

public class Controller implements Initializable{
	
	@FXML
	private TextField emailTextField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private Button LogInButton;
	
	
	public static String currentScreen;
	private Stage stage;
	private Scene scene;
	private String cssString = this.getClass().getResource("/Application/application.css").toExternalForm();
	private String emailAccount;
	private String passwordAccount;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		// TODO Auto-generated method stub
////		buttonClickSound = new Media(new File("res/sounds/button-click-sound.mp3").toURI().toString());
////		buttonMediaPlayer = new MediaPlayer(buttonClickSound);
//		
//		//play music
//		if(!isPlaying(mediaplayer)) {
//			songs = new ArrayList<File>();
//			directory = new File("res/music");
//			files = directory.listFiles();
//			
//			for(File file : files) {
////				System.out.println(file);
//				songs.add(file);
//			}
//			
//			if(files != null) {
//				System.out.println("Music ready");
//			} else System.out.println("null music");
//			playMusic(songs.get(0));
//		}
//		
//		if (volumeSlider != null) {
//          volumeSlider.setValue(mediaplayer.getVolume() * 100); // Set initial value in percentage
//          volumeLabel.setText(String.format("%.0f%%", volumeSlider.getValue())); // Set initial label text
//          volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
//              handleVolumeChange();
//          });
//      }
	}
	
	@FXML
    private void handleLogInButtonAction(ActionEvent event) {
		emailAccount  = emailTextField.getText();
        passwordAccount = passwordField.getText();
        
        if (emailAccount == null || passwordAccount == null) {
            // Handle case where user didn't select both
            System.out.println("Please enter both email and password.");
            return; // Or show an alert
        }

        try {
            MainScreen(event);
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("Failed to load MainScreen.fxml");
        }
    }
	
  public void MainScreen(ActionEvent e) throws IOException {
      currentScreen = "MainScreen.fxml";
      storeCurrentScreen(currentScreen);
      System.out.println("Navigating to MainScreen.fxml");
      Parent root = FXMLLoader.load(getClass().getResource("/Application/MainScreen.fxml"));
      stage = (Stage)((Node)e.getSource()).getScene().getWindow();
      scene = new Scene(root);
      scene.getStylesheets().add(cssString);
      stage.setScene(scene);
      stage.setResizable(false);
      stage.show();
      System.out.println("MainScreen Opened");
  }
  

	
	// Stack to store the previous pages
	public static Stack<String> screenHistory = new Stack<>();
	
	public static void storeCurrentScreen(String currentScreen) {
      screenHistory.push(currentScreen);
		// System.out.println("Stored current screen: " + currentScreen);
		// System.out.println("Current History: " + screenHistory);
		
	}
}
















