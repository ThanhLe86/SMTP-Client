/*
 * Control Compose.fxml, main interaction of the app
 */
import java.awt.Event;
import java.awt.MediaTracker;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.attribute.PosixFileAttributes;
import java.security.PublicKey;

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
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;   //clicks on emails

import javafx.scene.control.TextArea;   //email body

public class ComposeController implements Initializable {

    //for .fxml elements
    @FXML
    private TextField recipientField;

    @FXML 
    private TextField subjectField;

    @FXML
    private TextArea bodyField;

    @FXML
	private Button sendButton;

    //for the system
    private String userEmail;
    private String recipientEmail;
    private String emailSubject;
    private String emailBody;
    private SMTP_Connection tempConnection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Initialization logic if needed (e.g., setting up the gridPane)
    }

    public void initializeData(String email, SMTP_Connection connection) {
        this.userEmail = email;
        this.tempConnection = connection;
        System.out.println("User chooses compose.");
        System.out.println("User email: " + this.userEmail);
    }
    
    @FXML
    private void HandleSend(ActionEvent event) throws IOException {
 
        this.recipientEmail = recipientField.getText();
        this.emailSubject = subjectField.getText();
        this.emailBody = bodyField.getText();
 
        //handle not null logics

        //After pressing Send
        Courier newMail = new Courier(this.userEmail, this.recipientEmail, emailBody.toString(), emailSubject); //sender is automatically inputted by the system
        if(newMail.MailSender(tempConnection))
            System.out.println("Send successful, check destination inbox! Maybe in Spam");
        else System.out.println("Something went wrong"); 
    }


    public void ExitApp(ActionEvent e) {
        Platform.exit();
        System.exit(0);
        System.out.println("Stopped");
    }
    

}