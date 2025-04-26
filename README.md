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
- Caveat: It is still not clear if fakeSMTP can successfully receive emails from the program yet since the sending capability has not been implemented. Additionally, one drawback of fakeSMTP is that it does not feature authentication process like big email servers, so it is currently still best to test the program using google's gmail server.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. 
