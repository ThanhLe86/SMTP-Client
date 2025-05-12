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
import javafx.scene.paint.Color;
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

    @FXML
    private Label warningLabel;

    @FXML
	private Button cancelButton;

    @FXML
	private Button exitButton;

    //for the system
    private String userEmail;
    private String recipientEmail;
    private String emailSubject;
    private String emailBody;
    private SMTP_Connection tempConnection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        warningLabel.setVisible(false);
        cancelButton.setVisible(true);
        sendButton.setVisible(true);
        exitButton.setVisible(true);

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
        //get essential email components
        this.recipientEmail = recipientField.getText();
        this.emailSubject = subjectField.getText();

        String[] lines = bodyField.getText().split("\n");
        StringBuilder formattedBody = new StringBuilder();
        for (String line : lines) {
            formattedBody.append(line).append("\r\n");
        }
        this.emailBody = formattedBody.toString();

        System.out.println("Body field value: " + bodyField.getText());

        // Handle case where user didn't fill in all fields
        if (this.recipientEmail == null || this.recipientEmail.isEmpty() || 
            this.emailSubject == null || this.emailSubject.isEmpty() || 
            this.emailBody == null || this.emailBody.isEmpty()) {
            warningLabel.setVisible(true);
            warningLabel.setText("Please fill in all fields.");
            warningLabel.setTextFill(Color.color(1.0, 0.0, 0.0)); // label red
            System.out.println("Please fill in all fields.");
            return; 
        }
        
        //After pressing Send
        warningLabel.setVisible(true);
        warningLabel.setText("Please wait...");
        warningLabel.setTextFill(Color.color(1.0, 1.0, 0.0)); // label yellow

        Courier newMail = new Courier(this.userEmail, this.recipientEmail, this.emailBody, this.emailSubject); //sender is automatically inputted by the system
        if(newMail.MailSender(tempConnection)){
            System.out.println("Send successful, check destination inbox! Maybe in Spam");
            warningLabel.setText("Email sent successfully!");
            warningLabel.setTextFill(Color.color(0.0, 1.0, 0.0)); // label green
        } else{
            warningLabel.setText("Something went wrong.");
            warningLabel.setTextFill(Color.color(1.0, 0.0, 0.0)); // label red
            System.out.println("Something went wrong");
        }
    }


    public void ExitApp(ActionEvent e) {
        Platform.exit();
        System.exit(0);
        System.out.println("Stopped");
    }
    
    @FXML
    private void HandleCancel(ActionEvent event) throws IOException {
        //go back to main screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();

        MainScreenController mainController = loader.getController();
        mainController.initializeData(this.userEmail, this.tempConnection); // use "" for password if not needed

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1280, 720));
        stage.setResizable(false);
        stage.show();
    }
}