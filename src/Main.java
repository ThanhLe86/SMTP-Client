import java.io.*;
import java.util.Scanner;

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

/* sample login and send code structure
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        StringBuilder emailContent = new StringBuilder();
        String emailSubject;
        String emailRecipient;

        SMTP_Connection tempConnection = new SMTP_Connection("smtp.gmail.com", 587); //includes connecting and attempting comm
        AuthenticationManager tempAuthenticator = new AuthenticationManager("lejames726yahhh@gmail.com", "wjcn jcio hpwe spwx "); // Need app password to work

        if(tempAuthenticator.Authenticate(tempConnection)){ // if authentication succeeds

            //From now this represents what takes place in the GUI
            System.out.println("Enter your email subject: ");
            emailSubject = keyboard.nextLine();
            System.out.println("Enter recipient email (e.g. htbaoUgly@.gmail.com): ");
            emailRecipient = keyboard.nextLine();

            System.out.println("Enter your email content, end the email inputting by typing . and pressing enter");
            while(true){
                String line = keyboard.nextLine();
                if(line.equals(".")) break;
                emailContent.append(line + "\r\n");
            }
            keyboard.close();

            //After pressing Send
            Courier newMail = new Courier("lejames726yahhh@gmail.com", emailRecipient, emailContent.toString(), emailSubject); //sender is automatically inputted by the system
            if(newMail.MailSender(tempConnection))
                System.out.println("Send successful, check destination inbox! Maybe in Spam");
            else System.out.println("Something went wrong"); 

            tempConnection.Reset();
            tempConnection.Quit();
        } else tempConnection.Quit();
    }
}
*/
