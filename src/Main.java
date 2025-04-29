import java.io.*;

// for gui
import java.io.File;
import java.io.IOException;
import java.lang.ModuleLayer.Controller;

//import javafx.scene.image.*;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class Main extends Application {
	@FXML
    private TextField userEmail;

    @FXML
    private PasswordField userPassword;

	@FXML
	private Button logInButton;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
			Scene scene = new Scene(root,1280,720);
			//String cssString = this.getClass().getResource("application.css").toExternalForm();
			//scene.getStylesheets().add(cssString);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
/*  content pf this function is implemented in Controller.java, private void HandleLogIn()
public class Main {
    public static void main(String[] args) throws IOException {
        SMTP_Connection tempConnection = new SMTP_Connection("smtp.gmail.com", 587); //includes connecting and attempting comm
        AuthenticationManager tempAuthenticator = new AuthenticationManager("lejames726yahhh@gmail.com", "12345Theodd1soutbest");

        if(tempAuthenticator.Authenticate(tempConnection)){
            tempConnection.Reset();
            tempConnection.Quit();
        } else tempConnection.Quit();
    }
}
*/