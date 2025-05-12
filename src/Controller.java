/*
 * Control LogIn.fxml, log in interface of the program
 * 
 * TO DO:
 * - implement logics for account in HandleLogIn
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
 
 
public class Controller implements Initializable{
      
    public static String currentScreen;
    //private String cssString = this.getClass().getResource("/application/application.css").toExternalForm();
 
    // Stack to store the previous pages
    public static Stack<String> screenHistory = new Stack<>();
     
    //for system
    private String email;
    private String password;
 
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
 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        warningLabel.setVisible(false);
 
        this.email = null;
        this.password = null;
         
    }
      
    public static void storeCurrentScreen(String currentScreen) {
        //debug: LogIn.fxml will always in stack, to prevent it from being empty (when returning from Main.fxml)
        //currently not working
        if(Controller.screenHistory.isEmpty()){
            screenHistory.push("LogIn.fxml");
        }
 
        screenHistory.push(currentScreen);
        // System.out.println("Stored current screen: " + currentScreen);
        // System.out.println("Current History: " + screenHistory);
         
    }
 
    @FXML
    private void HandleLogIn(ActionEvent event) throws IOException {
        warningLabel.setVisible(false);
 
        this.email = userEmail.getText();
        this.password = userPassword.getText();
 
        //default logics is not null
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            // Handle case where user didn't select both
            warningLabel.setVisible(true);
            warningLabel.setText("Email or password cannot be empty.");
            System.out.println("Email or password cannot be empty.");
            return; 
        }

        //create SMTP connection and check if it's work, otherwise return
        SMTP_Connection tempConnection = new SMTP_Connection("smtp.gmail.com", 587); //includes connecting and attempting comm
        AuthenticationManager tempAuthenticator = new AuthenticationManager(this.email, this.password);

        if(!tempAuthenticator.Authenticate(tempConnection)){
            tempConnection.Quit();

            warningLabel.setVisible(true);
            warningLabel.setText("Authenication failed. Please check your email and password.");
            return;
        }


        
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        LoadMainScreen(event);
    }
 
    public void LoadMainScreen(ActionEvent event) {
        try {
            currentScreen = "Main.fxml";
            Controller.storeCurrentScreen(currentScreen);
             
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml")); 
            Parent root = loader.load();
 
            MainScreenController mainScreenController = loader.getController();
            mainScreenController.initializeData(this.email, this.password); // Pass email and password to the controller
 
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
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
     
  
     
}
   