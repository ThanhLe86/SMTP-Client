/*
 * Control LogIn.fxml, log in interface of the program
 */

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
  
  
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
 
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
 
 
public class LogInController implements Initializable{
    //for system
    private String email;
    private String password;
    private SMTP_Connection tempConnection;

    //for .fxml elements
    @FXML
    private TextField userEmail;
 
    @FXML
    private PasswordField userPassword;
 
    @FXML
    private Button logInButton;
      
    @FXML
    private Label warningLabel;
 
    @FXML
    private Button exitButton;

    @FXML
    private Button DarkModeButton;
 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        warningLabel.setVisible(false);
 
        this.email = null;
        this.password = null;
         
    }
    
    @FXML
    private void HandleLogIn(ActionEvent event) throws IOException {
        warningLabel.setVisible(false);
 
        this.email = userEmail.getText();
        this.password = userPassword.getText();
 
        //handle not null logics
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            // Handle case where user didn't select both
            warningLabel.setVisible(true);
            warningLabel.setText("Email or password cannot be empty.");
            System.out.println("Email or password cannot be empty.");
            return; 
        }

        //create SMTP connection and check if it's work, otherwise return
        tempConnection = new SMTP_Connection("smtp.gmail.com", 587); //includes connecting and attempting comm
        AuthenticationManager tempAuthenticator = new AuthenticationManager(this.email, this.password);

        if(!tempAuthenticator.Authenticate(tempConnection)){
            tempConnection.Quit();

            warningLabel.setVisible(true);
            warningLabel.setTextFill(Color.color(1.0, 0.0, 0.0)); // label red
            warningLabel.setText("Authenication failed. Check your email and password. Remember to use the app password, NOT your email password!");
            return;
        }

        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        LoadMainScreen(event);
    }
 
    public void LoadMainScreen(ActionEvent event) {
        try {
            warningLabel.setVisible(true);
            warningLabel.setText("Please wait...");
            warningLabel.setTextFill(Color.color(1.0, 1.0, 0.0)); // label yellow

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml")); 
            Parent root = loader.load();
 
            MainScreenController mainScreenController = loader.getController();
            mainScreenController.initializeData(this.email, this.tempConnection); // Pass email, password and connection to the controller
 
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root,1280,720);

            ThemeManager.applyTheme(scene);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
 
            System.out.println("Main.fxml Opened");
 
        } catch (IOException e) {
            e.printStackTrace();
        }  	
    }
 
      
    public void ExitApp(ActionEvent e) {
        Platform.exit();
        System.exit(0);
        System.out.println("Stopped");
    }
    
    @FXML
    private void HandleDarkMode(ActionEvent event) {
        Scene currentScene = ((Node) event.getSource()).getScene();
        ThemeManager.toggleTheme(currentScene);
    }

  
     
}
   