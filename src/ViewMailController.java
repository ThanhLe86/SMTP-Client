import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ViewMailController {

    private String userEmail;
    private SMTP_Connection tempConnection;
    @FXML
    private TextField recipientField;

    @FXML
    private TextField subjectField;

    @FXML
    private TextArea bodyField;

    @FXML
	private Button returnButton;

    public void initializeData(Email email, String userEmail, SMTP_Connection tempConnection) {
        recipientField.setText(email.getRecipient());
        subjectField.setText(email.getSubject());
        bodyField.setText(email.getBody());

        this.userEmail = userEmail;
        this.tempConnection = tempConnection;

        // Convert "dd-MM-yyyy HH:mm:ss" (column format) to "ddMMyyyy_HHmmss" (file format) to retrieve .txt file
        try {
            String timestamp = email.getTime(); // Format: dd-MM-yyyy HH:mm:ss
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            DateTimeFormatter fileFormatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");

            LocalDateTime dateTime = LocalDateTime.parse(timestamp, inputFormatter);
            String filename = fileFormatter.format(dateTime) + ".txt";

            File file = new File("database", filename);
            if (!file.exists()) {
                bodyField.setText("[Error] Email body file not found: " + filename);
            } else {
                // Read full content from file
                StringBuilder content = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        content.append(line).append("\n");  //add \n if getting a new line (as this return a string for .setText)
                    }
                }
                bodyField.setText(content.toString().trim());
            }
        } catch (Exception e) {
            bodyField.setText("[Error] Could not load email body.\n" + e.getMessage());
        }

        // Make all fields non-editable
        recipientField.setEditable(false);
        subjectField.setEditable(false);
        bodyField.setEditable(false);
    }

    @FXML
    private void HandleDarkMode(ActionEvent event) {
        Scene currentScene = ((Node) event.getSource()).getScene();
        ThemeManager.toggleTheme(currentScene);
    }

    @FXML
    private void HandleReturn(ActionEvent event) throws IOException {
        //go back to main screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();

        MainScreenController mainController = loader.getController();
        mainController.initializeData(this.userEmail, this.tempConnection); // use "" for password if not needed

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1280,720);

        ThemeManager.applyTheme(scene);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }



}
