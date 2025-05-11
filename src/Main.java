import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        StringBuilder emailContent = new StringBuilder();
        String emailSubject;
        String emailRecipient;

        SMTP_Connection tempConnection = new SMTP_Connection("smtp.gmail.com", 587); //includes connecting and attempting comm
        AuthenticationManager tempAuthenticator = new AuthenticationManager("", ""); // Need app password to work

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

            //After pressing Send
            Courier newMail = new Courier("", emailRecipient, emailContent.toString(), emailSubject); //sender is automatically inputted by the system
            if(newMail.MailSender(tempConnection))
                System.out.println("Send successful, check destination inbox! Maybe in Spam");
            else System.out.println("Something went wrong"); 

            tempConnection.Reset();
            tempConnection.Quit();
        } else tempConnection.Quit();
    }
}
