### Update 31.07.2025
- SMTP does not support sending emails with attached files. Therefore, we will use one of its extensions: Multipurpose Internet Mail Extensions message, which can be used to send non-textual content (pictures, audios, etc.) or different character sets (beyond US-ASCII characters)
- Since we are not allowed to use extra libraries, we will construct the email manually in this structure (example):
From: <sender@example.com>          (1)
To: <receiver@example.com>
Subject: Email with Attachment
MIME-Version: 1.0
Content-Type: multipart/mixed; boundary="====BOUNDARY_1722449141653===="

--====BOUNDARY_1722449141653====    (2)
Content-Type: text/plain; charset="utf-8"       (3)
Content-Transfer-Encoding: 7bit

Hello,
This email contains an attachment.

--====BOUNDARY_1722449141653====
Content-Type: application/octet-stream; name="report.pdf"   (4)
Content-Transfer-Encoding: base64
Content-Disposition: attachment; filename="report.pdf"

JVBERi0xLjQKJeLjz9MKMiAwIG9iago8PC9MZW5ndGggMzE4Pj4Kc3RyZWFtC...
(more base64 lines)

--====BOUNDARY_1722449141653====--      (5)

(1) Headers (basic SMTP headers + MIME version and Content-Type)
(2) Boundary (starts multipart message)
(3) Text part   (content type, encoding)
(4) Attachment part (content type, encoding, disposition)
(5) Closing Boundary

### Update 26.05.2025
Regarding connection termination procedures:
- By Google's SMTP Server standard, all connection must firstly be reset via RSET command, then use QUIT command.
- Therefore, we give an update regarding logics for ExitApp() and LogOut()
- When clicking Exit button, which will invoke ExitApp(), tempConnection will do as aformentioned
- When clicking Log Out button, which will invoke LogOutNow(), tempConnection for the instance MainScreenController will do as aformentioned. After that, when clicking Exit button in LogIn.fxml, then it will check if tempConnection for the instance LogInController is null, as if we click it the first time without logging in, no tempConnection is made and therefore still null, which will return errors.

### Update 24.05.2025
Added a view local email feature.
- To view a local email in the table, double click the email (row) you want to see
- Instead of reading the Body column in the table (which is not in correct format since we removed "\n" for display), we will use the date and time in the table, revert it to the correct format of the .txt file (which contains the body), and read the body from it.
- The project can now be run on all machines without needing to configure run directory upon importing, to run the project, follow the instruction in the "How to install and run" section above.
- An executable .jar file is now available for ease of use, the project can is now ready for demo and concluded if there are no further testing issues.

### Update 22.05.2025
Added a simple search feature for local emails
- The top bar in Main.fxml is the search bar.
- Currently, we have 3 options: search in Recipient, Subbject, Body
- Use Check Box for each options.
- Add AdvanceSearch Toggle for UI simplicity (only show the check boxes in advance search mode)
- Simply take the boolean value of each check boxes and use it as parameters for searchEmails()
- To be added: search by date

### Update 21.05.2025
Major changes in storing emails.
- As parsing the .csv file is increasingly difficult as the number of lines in the email body increases, we are no longer storing the body in the .csv file.
- Instead, a new folder /database is made to store email bodies in .txt files. Their names is in the format: DDMMYYYY_HHMMSS
- database.csv is also moved to /database.

### Update 17.05.2025
Added a functionality that helps storing sent emails from this client (local save/database).
- Instead of String, we use StringProperty, which makes String observable and enable automatic update in UI for JavaFX programs.
- Every emails have 5 attributes: emailTime, emailSender, emailRecipient, emailSubject, emailBody
- Call Email.logEmail() to write the currently sent email (in Compose.fxml) into the database (database.csv)
- Call Email.readEmails(this.userEmail) to retrieve a List of sent emails from this.userEmail to show it in emailTableView in Main.fxml
- emailTableView is now usable. By retrieving a List of emails, we now have the data to set value of the cellData into columns
- DEBUG: see the top of Email.java

### Update 17.05.2025
Added a functionality that helps switching between light mode and dark mode.
- The ThemeManager.java will manage the switch between the modes.
- toggleTheme() will switch the Theme state with boolean operation
- applyTheme() will call the respective .css file with scene.getStyleSheets()
- Use ThemeManager.applyTheme() to set up current state (light/dark mode) correctly (instead of default light mode) after creating scene in Handle...()

- Current setup:
    - Light: white AnchorPane, white Button with black text, and Label with black text.
    - Dark: black AnchorPane, black Button with white text and Label with white text.