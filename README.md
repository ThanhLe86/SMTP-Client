# SMTP Client implementation using Java

Welcome to our Java project for SMTP Client Implementation. Here is a guide on how the project can be run.

## Details

Once the project is imported from github to your local machine, it should be opened in the IDE of your choice, best to use VSCode.

The Java Project will contain two main folders that you will need to use, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

- There are currently three main .java files located inside the 'src' folder: 
    - The project can be run starting from the file Main.java; the file SMTP Connection will contain all of what is necessary for managing a connection to an SMTP server
    - The file AuthenticationManager.java contains all functions associated with the user authentication process necessary for sending an email. This file is currently incomplete and if you do not want to run the Authentication Manager, just delete the corresponding line from Main.java and alter the code a bit. Else, you can attempt to authenticate using the username and password of your desired google account.

- This project currently attempts to connect to google's gmail server 'smtp.gmail.com' as can be seen in Main.java. However, there are a number of other mail servers that can be used beside that. Aside from that, the port number can also vary but it is generally best to use 587.

- If you are hesitant to connect to big name servers, there is an attached .jar file in the project called "fakeSMTP-2.0.jar". This is a program that allows you to test your local SMTP client by mimicking an email server. This program allows you to test connecting to an email server, sending various messages as well as sending a an email. 

## How to operate fakeSMTP-2.0.jar
- Double click on the .jar file in File Explorer, then configure the "Listening port" to 587 and press "Start server".
- In your program, change the 'addr' in Main.java to "localhost" and run the program.
- Caveat: It is still not clear if fakeSMTP can successfully receive emails from the program yet. Additionally, one drawback of fakeSMTP is that it does not feature authentication process like big email servers, so it is currently still best to test the program using google's gmail server.

## How to install and run
*For the source code*
- Pull the project to your device
- Open the project in VScode
- On the left side, in the "Run and Debug" view, open the dropdown menu nect to the green run button and choose "LaunchConfig"
- Now you can run the app using the green button
- If you want to run the project in the VScode terminal, follow the following instruction:
    - Change the terminal directory to the project folder instead of the project src folder
    - Copy the following 2 lines into it and press enter:
        javac --module-path javafx-sdk-24.0.1/lib --add-modules javafx.controls,javafx.fxml -d bin src/Main.java
        java --module-path javafx-sdk-24.0.1/lib --add-modules javafx.controls,javafx.fxml -cp bin Main


*For the executable*
- Pull the project to your device
- Open the "executable" folder
- Click and run the "run.bat" file, the app will run automatically 



### Update 07.05.2025
- The authentication step was completed successfully and here are the steps to properly sign in when using this project application. To properly authenticate, first you would need to create an app password for your google account; Go to this link: ***[critical update](https://myaccount.google.com/apppasswords)*** and create it by typing in your app name. To be sure, you should set the app name as "test.client" since it is the same as the name our app client declares itself as during EHLO testing. Google will then generate a 16-digit password dedicated for your app to use to authenticate when attempting to use the Google account. Copy that and use it from now on when you attempt to sign in to the account using this project application. 
- It is important to note that since Google changed its security features, using your conventional account password and username will not work since Google will reject it for being an unsecure connection attempt

### Update 11.05.2025
- Sending functionality was implemented successfully with some drawbacks, here is how it works:
    - Once Authentication is completed, the email can be generated requiring 3 basic components: Subject, Sender, Recipient, and Email Content. 
    - We initiate the email sending with by specifying the sender with "MAIL FROM:" and the recipient with "RCPT TO:". Once the server has confirmed these two steps as successful with the response starting with 250, we start crafting the email with "DATA" as follows:
        C: Subject: Hello from Java
        C: From: you@gmail.com
        C: To: someone@example.com
        C:
        C: This is a test email sent from my custom SMTP client.
        C:
        C: Regards,
        C: Me
        C: .
    The "." at the end is to signify to the server that the message is completed and ready to be sent. The server then sends a message starting with 250 to confirm sending and you can end the transaction.
- All of the steps above mostly happen in the backend. To implement sending feature into the GUI, follow the program flow in the file Main.java.

- Caveat: The sent mail will appear in the Spam inbox and has both the subject and the content as "null". This will be worked on later.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

### Update 17.05.2025
Added a functionality that helps switching between light mode and dark mode.
- The ThemeManager.java will manage the switch between the modes.
- toggleTheme() will switch the Theme state with boolean operation
- applyTheme() will call the respective .css file with scene.getStyleSheets()
- Use ThemeManager.applyTheme() to set up current state (light/dark mode) correctly (instead of default light mode) after creating scene in Handle...()

- Current setup:
    - Light: white AnchorPane, white Button with black text, and Label with black text.
    - Dark: black AnchorPane, black Button with white text and Label with white text.

### Update 17.05.2025
Added a functionality that helps storing sent emails from this client (local save/database).
- Instead of String, we use StringProperty, which makes String observable and enable automatic update in UI for JavaFX programs.
- Every emails have 5 attributes: emailTime, emailSender, emailRecipient, emailSubject, emailBody
- Call Email.logEmail() to write the currently sent email (in Compose.fxml) into the database (database.csv)
- Call Email.readEmails(this.userEmail) to retrieve a List of sent emails from this.userEmail to show it in emailTableView in Main.fxml
- emailTableView is now usable. By retrieving a List of emails, we now have the data to set value of the cellData into columns
- DEBUG: see the top of Email.java

### Update 21.05.2025
Major changes in storing emails.
- As parsing the .csv file is increasingly difficult as the number of lines in the email body increases, we are no longer storing the body in the .csv file.
- Instead, a new folder /database is made to store email bodies in .txt files. Their names is in the format: DDMMYYYY_HHMMSS
- database.csv is also moved to /database.

### Update 22.05.2025
Added a simple search feature for local emails
- The top bar in Main.fxml is the search bar.
- Currently, we have 3 options: search in Recipient, Subbject, Body
- Use Check Box for each options.
- Add AdvanceSearch Toggle for UI simplicity (only show the check boxes in advance search mode)
- Simply take the boolean value of each check boxes and use it as parameters for searchEmails()
- To be added: search within date

### Update 24.05.2025
Added a view local email feature.
- To view a local email in the table, double click the email (row) you want to see
- Instead of reading the Body column in the table (which is not in correct format since we removed "\n" for display), we will use the date and time in the table, revert it to the correct format of the .txt file (which contains the body), and read the body from it.
- The project can now be run on all machines without needing to configure run directory upon importing, to run the project, follow the instruction in the "How to install and run" section above.
- An executable .jar file is now available for ease of use, the project can is now ready for demo and concluded if there are no further testing issues.

### Update 26.05.2025
Regarding connection termination procedures:
- By Google's SMTP Server standard, all connection must firstly be reset via RSET command, then use QUIT command.
- Therefore, we give an update regarding logics for ExitApp() and LogOut()
- When clicking Exit button, which will invoke ExitApp(), tempConnection will do as aformentioned
- When clicking Log Out button, which will invoke LogOutNow(), tempConnection for the instance MainScreenController will do as aformentioned. After that, when clicking Exit button in LogIn.fxml, then it will check if tempConnection for the instance LogInController is null, as if we click it the first time without logging in, no tempConnection is made and therefore still null, which will return errors.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. 
