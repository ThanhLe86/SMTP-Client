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
- Pull the project to your device
- In `JAVA PROJECTS` (bottom left of the screen), find 'Referenced Libraries' and add all JavaFX .jar files in lib folder (currently 24.0.1)
- In launch.json, look for "vmArgs", then change the module path "D:/openjfx-24.0.1_windows-x64_bin-sdk/javafx-sdk-24.0.1/lib" to your local JavaFX SDK's lib folder.
- Use Ctrl+Alt+N to run code or click on the triangle on the top right of the screen

## TO DO
- Debug the stack screenHistory in Controller.java: current cannot turn back to LogIn.fxml from Main.fxml



### Update 07.05.2025
- The authentication step was completed successfully and here are the steps to properly sign in when using this project application. To properly authenticate, first you would need to create an app password for your google account; Go to this link: ***[critical update](https://myaccount.google.com/apppasswords)*** and create it by typing in your app name. To be sure, you should set the app name as "test.client" since it is the same as the name our app client declares itself as during EHLO testing. Goodgle will then generate a 16-digit password dedicated for your app to use to authenticate when attempting to use the Google account. Copy that and use it from now on when you attempt to sign in to the account using this project application. 
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

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. 
