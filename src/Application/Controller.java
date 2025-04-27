package Application;

/* control GUI interactions */

import java.awt.Button;
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

public class Controller implements Initializable{
	
	public static String currentScreen;
	private Stage stage;
	private Scene scene;
	private String cssString = this.getClass().getResource("/application/application.css").toExternalForm();
//	private Parent root;
	
	//Setup button clicking sounds
//	private Media buttonClickSound;
//	private MediaPlayer buttonMediaPlayer;

	@FXML
	private Slider volumeSlider;
  @FXML
  private Label volumeLabel;
	
	
	//Initialize background music
	private File directory;
	private File[] files;
	private Media media;
	public static MediaPlayer mediaplayer;
	public static ArrayList<File> songs;
	
	//Initialize Card information
	private Image cardImage;
	private ImageView cardbackGroundImageView;
	private GridPane cardGridPane;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
//		buttonClickSound = new Media(new File("res/sounds/button-click-sound.mp3").toURI().toString());
//		buttonMediaPlayer = new MediaPlayer(buttonClickSound);
		
		//play music
		if(!isPlaying(mediaplayer)) {
			songs = new ArrayList<File>();
			directory = new File("res/music");
			files = directory.listFiles();
			
			for(File file : files) {
//				System.out.println(file);
				songs.add(file);
			}
			
			if(files != null) {
				System.out.println("Music ready");
			} else System.out.println("null music");
			playMusic(songs.get(0));
		}
		
		if (volumeSlider != null) {
          volumeSlider.setValue(mediaplayer.getVolume() * 100); // Set initial value in percentage
          volumeLabel.setText(String.format("%.0f%%", volumeSlider.getValue())); // Set initial label text
          volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
              handleVolumeChange();
          });
      }
	}
	
//	public void playTest() {
//		buttonMediaPlayer.setVolume(1.0);
//		buttonMediaPlayer.play();
//	}

	public static void setVolume(double volume) {
		if (mediaplayer != null) {
			mediaplayer.setVolume(volume);
			// System.out.println("Volume set to: " + volume);
		} else {
			System.out.println("MediaPlayer is not initialized.");
		}
	}
	
	public static void playMusic(File filename) {
		if(isPlaying(mediaplayer)) mediaplayer.stop();
		
		String songDir = filename.toURI().toString();
		Media media = new Media(songDir);
		mediaplayer = new MediaPlayer(media);
		
		mediaplayer.setOnEndOfMedia(() -> {
			mediaplayer.seek(javafx.util.Duration.ZERO);
			mediaplayer.play();
		});
		
		mediaplayer.play();
	}
	
	public static void checkScreenForMusicChange(String currentScreen) {
		File choiceSong = songs.get(0);
		if(currentScreen.equals("MPGameScreen.fxml") || currentScreen.equals("SPGameScreen.fxml")) {
			choiceSong = songs.get(1);
			playMusic(choiceSong);
		} else {
			if(isPlaying(mediaplayer) && choiceSong != songs.get(0)) playMusic(songs.get(0));
		}
	}

	@FXML
	public void handleVolumeChange() {
		double volume = Math.round(volumeSlider.getValue());
		volumeSlider.setValue(volume); // Set the slider to the rounded value
		setVolume(volume / 100); // Set volume in MediaPlayer (0.0 to 1.0)
		volumeLabel.setText(String.format("%.0f%%", volume)); // Update the label text
	}

  public void AudioSettingsPage(ActionEvent e) throws IOException {
      currentScreen = "AudioSettings.fxml";
      checkScreenForMusicChange(currentScreen);
      storeCurrentScreen(currentScreen);
      System.out.println("Navigating to AudioSettings.fxml");
      Parent root = FXMLLoader.load(getClass().getResource("/application/AudioSettings.fxml"));
      stage = (Stage)((Node)e.getSource()).getScene().getWindow();
      scene = new Scene(root);
      scene.getStylesheets().add(cssString);
      stage.setScene(scene);
      stage.setResizable(false);
      stage.show();
      System.out.println("Audio Settings Page Opened");
  }
  
  public void GameRulesPage(ActionEvent e) throws IOException {
      currentScreen = "GameRules.fxml";
      checkScreenForMusicChange(currentScreen);
      storeCurrentScreen(currentScreen);
      System.out.println("Navigating to GameRules.fxml");
      Parent root = FXMLLoader.load(getClass().getResource("/application/GameRules.fxml"));
      stage = (Stage)((Node)e.getSource()).getScene().getWindow();
      scene = new Scene(root);
      scene.getStylesheets().add(cssString);
      stage.setScene(scene);
      stage.setResizable(false);
      stage.show();
      System.out.println("GameRules Page Opened");
  }

	public static boolean isPlaying(MediaPlayer mediaPlayer) {
      if (mediaPlayer != null) {
          return mediaPlayer.getStatus() == Status.PLAYING; 
      }
      return false; 
  }
	
	public static void playMedia() {
		mediaplayer.play();
	}
	
	// Stack to store the previous pages
	public static Stack<String> screenHistory = new Stack<>();
	
	public static void storeCurrentScreen(String currentScreen) {
      screenHistory.push(currentScreen);
		// System.out.println("Stored current screen: " + currentScreen);
		// System.out.println("Current History: " + screenHistory);
		
	}
	
	/*
	public void goBack(ActionEvent e) throws IOException {
		if (!screenHistory.isEmpty()) {
			screenHistory.pop();
			String previousScreen = screenHistory.peek(); 
			if (previousScreen == "StartScreen.fxml"){
				screenHistory.pop();
				storeCurrentScreen(previousScreen);
			}
			System.out.println("Attempting to go back to: " + previousScreen);
			Parent root = FXMLLoader.load(getClass().getResource("/application/" + previousScreen));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			scene.getStylesheets().add(cssString);
			stage.setScene(scene);
			stage.show();
			System.out.println("Navigated back to: " + previousScreen);
      } else {
          System.out.println("Screen history is empty. Cannot go back.");
		}
	}
	
	
	public void QuitGame(ActionEvent e) {
		Platform.exit();
		System.exit(0);
		System.out.println("Stopped");
	}
	
	public void StartGame(ActionEvent e) throws IOException {
		currentScreen = "GameModeSelection.fxml";
		checkScreenForMusicChange(currentScreen);
		storeCurrentScreen(currentScreen);
		Parent root = FXMLLoader.load(getClass().getResource("/application/GameModeSelection.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(cssString);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		System.out.println("Commenced Gamesetup");
	}
	

	public void SettingsPage(ActionEvent e) throws IOException {
		currentScreen = "Settings.fxml";
		checkScreenForMusicChange(currentScreen);
		storeCurrentScreen(currentScreen);
		System.out.println("Navigating to Settings.fxml");
		Parent root = FXMLLoader.load(getClass().getResource("/application/Settings.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(cssString);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		System.out.println("Settings Page Opened");
	}
	
	public void LobbySettingScreen(ActionEvent e) throws IOException{
		currentScreen = "LobbySettingScreen.fxml";
		checkScreenForMusicChange(currentScreen);
		storeCurrentScreen(currentScreen);
		System.out.println("Navigating to LobbySettingScreen.fxml");
		Parent root = FXMLLoader.load(getClass().getResource("/application/LobbySettingScreen.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(cssString);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		System.out.println("LobbySettingScreen Opened");
	}
	
	public void SingleConfigScreen(ActionEvent e) throws IOException{
		currentScreen = "SingleConfig.fxml";
		checkScreenForMusicChange(currentScreen);
		storeCurrentScreen(currentScreen);
		System.out.println("Navigating to SingleConfig.fxml");
		Parent root = FXMLLoader.load(getClass().getResource("/application/SingleConfig.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(cssString);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		System.out.println("TestSingleplayer Configurations Opened");
	}


	public void DifficultySetting(ActionEvent e) throws IOException{
		currentScreen = "DifficultyScene.fxml";
		checkScreenForMusicChange(currentScreen);
		storeCurrentScreen(currentScreen);
		System.out.println("Navigating to DifficultyScene.fxml");
		Parent root = FXMLLoader.load(getClass().getResource("/application/DifficultyScene.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(cssString);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		System.out.println("Choose Difficulty");
	}
	
	*/
}



//StackPane StackPaneroot = new StackPane();

//cardbackGroundImageView.setFitWidth(200); 
//cardbackGroundImageView.setFitHeight(150);

//StackPaneroot.setPadding(new Insets(0, 0, 0, 0));
//StackPaneroot.setAlignment(Pos.CENTER_RIGHT);
//StackPaneroot.getChildren().add(cardbackGroundImageView);

////Initialize card image
//cardImage = new Image(new File("res/images/AppBackground.jpg").toURI().toString());
//if(cardImage != null) {
//	GridPane cardGridPane = new GridPane();
//	cardGridPane.setPrefSize(cardImage.getWidth() / 3, cardImage.getHeight() / 3);
//	cardbackGroundImageView = new ImageView(cardImage);
//	
//	// Define grid size (example: 5x5)
//  int rows = 5;
//  int cols = 5;
//
//  // Create and add grid cells (example: using Rectangles)
//  for (int row = 0; row < rows; row++) {
//      for (int col = 0; col < cols; col++) {
//          Rectangle cell = new Rectangle(cardImage.getWidth() / cols, cardImage.getHeight() / rows);
//          cell.setFill(Color.TRANSPARENT); 
//          cell.setStroke(Color.LIGHTGRAY); 
//          // Add event handlers to the cell here
//          cardGridPane.add(cell, row, col);
//      }
//  }
//} else System.out.println("invalid");















