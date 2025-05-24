/*
 * Control Main.fxml, main interaction of the app
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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;   //clicks on emails

public class MainScreenController implements Initializable {
    //for .fxml elements
    @FXML
    private Button composeButton;

    @FXML
    private Button logOutButton;

    @FXML
    private TableView<Email> emailTableView;

    @FXML
    private TableColumn<Email, String> dateColumn;

    @FXML
    private TableColumn<Email, String> recipientColumn;

    @FXML
    private TableColumn<Email, String> subjectColumn;

    @FXML
    private TableColumn<Email, String> bodyColumn;
    
    @FXML
	private Button exitButton;

    @FXML
    private Button DarkModeButton;

    @FXML
    private TextField searchBarField;

    @FXML 
    private CheckBox recipientCheckBox;   

    @FXML 
    private CheckBox subjectCheckBox;

    @FXML 
    private CheckBox bodyCheckBox;

    @FXML
	private Button advanceSearchButton;

    

    //for the system
    private String userEmail;
    private SMTP_Connection tempConnection;
    private ObservableList<Email> emailList;        //observable list of emails as it can listen to changes
    private static boolean advanceSearch = false;    //toggle for advanced search

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	composeButton.setVisible(true);

        recipientCheckBox.setVisible(false);
        subjectCheckBox.setVisible(false);
        bodyCheckBox.setVisible(false);

        // Link each column to the Email class properties
        // dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        // recipientColumn.setCellValueFactory(new PropertyValueFactory<>("recipient"));
        // headerColumn.setCellValueFactory(new PropertyValueFactory<>("header"));
        // emailTableView.setPlaceholder(new Label("No emails yet"));

        dateColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        recipientColumn.setCellValueFactory(cellData -> cellData.getValue().recipientProperty());
        subjectColumn.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());
        bodyColumn.setCellValueFactory(cellData -> cellData.getValue().bodyProperty());

        emailTableView.setPlaceholder(new Label("No emails yet!"));

        // Initialization logic if needed (e.g., setting up the gridPane)
        // set up the click-to-view function
        emailTableView.setOnMouseClicked(event -> {
            try {
                HandleViewEmail(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void initializeData(String userEmail,SMTP_Connection connection) {
        this.userEmail = userEmail;
        this.tempConnection = connection;

        System.out.println("User logged in");
        System.out.println("User email: " + this.userEmail);

        // Load and display emails
        // List<Email> emails = Email.readEmails(this.userEmail);
        // emailTableView.getItems().setAll(emails);

        this.emailList = FXCollections.observableArrayList(Email.readEmails(this.userEmail));
        emailTableView.setItems(this.emailList);
    }
    
    // public void LogOut(ActionEvent e) throws IOException {
    //     //go back to main screen
    //     if (!LogInController.screenHistory.isEmpty()) {
    //         LogInController.screenHistory.pop();
    //         String previousScreen = LogInController.screenHistory.peek(); 
    //         if (previousScreen == "LogIn.fxml"){
    //             LogInController.screenHistory.pop();
    //             LogInController.storeCurrentScreen(previousScreen);
    //         }
    //         System.out.println("Attempting to go back to: " + previousScreen);
    //         Parent root = FXMLLoader.load(getClass().getResource("/application/" + previousScreen));
    //         Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    //         Scene scene = new Scene(root);
    //         stage.setScene(scene);
    //         stage.show();

    //         //remove details of the account
    //         this.userEmail = null;
    //         this.userPassword = null;

    //         System.out.println("Navigated back to: " + previousScreen);
    //     } else {
    //         System.out.println("Screen history is empty. Cannot go back.");
    //     }
    // }

    public void LogOutNow(ActionEvent e) throws IOException {
        // Load the login screen directly
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
        Parent root = loader.load();
    
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1280, 720);

        ThemeManager.applyTheme(scene);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    
        // Clear user data
        this.userEmail = null;
    
        System.out.println("Logged out. Returned to LogIn.fxml.");
    }
    
    public void ExitApp(ActionEvent e) {
        Platform.exit();
        System.exit(0);
        System.out.println("Stopped");
    }

    @FXML 
    private void HandleCompose(ActionEvent event) throws IOException {
        //go to compose screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Compose.fxml"));
        Parent root = loader.load();

        ComposeController composeController = loader.getController();
        composeController.initializeData(this.userEmail, this.tempConnection); // Pass email and password to the controller

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1280,720);

        ThemeManager.applyTheme(scene);

        stage.setScene(scene);
        stage.setResizable(false);

        // set always on top 
        stage.setAlwaysOnTop(true);

        stage.show();

        System.out.println("Compose.fxml Opened");
    }
    
    @FXML
    private void HandleDarkMode(ActionEvent event) {
        Scene currentScene = ((Node) event.getSource()).getScene();
        ThemeManager.toggleTheme(currentScene);
    }

    @FXML
    private void HandleAdvanceSearch(ActionEvent event) {
        advanceSearch = !advanceSearch;
        if (advanceSearch){
            recipientCheckBox.setVisible(true);
            subjectCheckBox.setVisible(true);
            bodyCheckBox.setVisible(true);
        } else {
            recipientCheckBox.setVisible(false);
            subjectCheckBox.setVisible(false);
            bodyCheckBox.setVisible(false);
        }
    }

    @FXML
    private void HandleSearchKey(KeyEvent event) {
        if (event.getCode() != KeyCode.ENTER) return;
        
        String query = searchBarField.getText().toLowerCase().trim();
        //perform query with no advance search (all 3 options)
        if (!advanceSearch) {
            ObservableList<Email> results = SearchEngine.searchEmails(emailList, query, true, true, true);
            emailTableView.setItems(results);
            return;
        }

        //perform query with advance search
        boolean searchSubject = subjectCheckBox.isSelected();
        boolean searchBody = bodyCheckBox.isSelected();
        boolean searchRecipient = recipientCheckBox.isSelected();
        
        ObservableList<Email> results = SearchEngine.searchEmails(emailList, query, searchSubject, searchBody, searchRecipient);
        emailTableView.setItems(results);
    }

    private void HandleViewEmail(MouseEvent event) throws IOException {
        // Only trigger on double click
        if (event.getClickCount() != 2) return;
        
        // check if doublle-clicked on an email
        Email selectedEmail = emailTableView.getSelectionModel().getSelectedItem();
        if (selectedEmail == null) return;
    
        // Load Mail.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Mail.fxml"));
        Parent root = loader.load();
    
        ViewMailController viewMailController = loader.getController();
        viewMailController.initializeData(selectedEmail, this.userEmail, this.tempConnection);
    
        Stage stage = new Stage();
        Scene scene = new Scene(root, 1280, 720); 
    
        ThemeManager.applyTheme(scene);
    
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    
        System.out.println("Mail.fxml Opened for: " + selectedEmail.getSubject());
    }
    
}