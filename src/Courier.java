import java.io.*;

public class Courier {
    public String sender;
    private String recipient;
    private String mailContent;
    private String mailSubject;
    
    // Once all the necessary information has been input by the user
    public Courier(String sender, String rcpt, String mailContent, String mailSubject){
        sender = sender;
        recipient = rcpt;
        mailContent = mailContent;
        mailSubject = mailSubject;
    }

    //function runs when pressing "Send"
    public boolean MailSender(SMTP_Connection connection) throws IOException{
        String originMessage = "MAIL FROM:" + "<" + sender + ">";
        String destinationMessage = "RCPT TO:" + "<" + recipient + ">";
        String serverResponse;

        //clarify sender
        System.out.println("Attempting to initiate mail...");
        connection.out.write(originMessage + "\r\n");
        connection.out.flush();
        serverResponse = connection.serverResponseReader(); // Uses new way of printing server response from here
        if(!serverResponse.startsWith("250")){
            System.out.println("Failed to clarify sender");
            System.out.println(serverResponse);
            return false;
        } else System.out.println("Sender clarified successfully");

        //clarify recipient
        connection.out.write(destinationMessage + "\r\n");
        connection.out.flush();
        serverResponse = connection.serverResponseReader();
        if(!serverResponse.startsWith("250")){
            System.out.println("Failed to clarify recipient");
            System.out.println(serverResponse);
            return false;
        } else System.out.println("Recipient clarified successfully");
        
        if(!mailCrafter(connection)) return false; //Generate mail 

        return true;
    }
    
    public boolean mailCrafter(SMTP_Connection connection) throws IOException{ 
        String sendingContent;
        String serverResponse;

        // Request mail initiationc
        connection.out.write("DATA\r\n");
        connection.out.flush();
        serverResponse = connection.serverResponseReader();
        if(!serverResponse.startsWith("354")){
            System.out.println("DATA command failed");
            System.out.println(serverResponse);
            return false;
        } System.out.println("DATA succeeded, crafting mail");

        sendingContent = "Subject: " + mailSubject + "\r\n" +
                      "From: " + sender + "\r\n" +
                      "To: " + recipient + "\r\n" +
                      "\r\n" +
                      mailContent;
        
        connection.out.write(sendingContent + "\r\n");
        connection.out.write(".\r\n");
        connection.out.flush();
        serverResponse = connection.serverResponseReader();
        if(!serverResponse.startsWith("250")){
            System.out.println("Sending failed");
            System.out.println(serverResponse);
            return false;
        }

        return true;
    }
}
