import java.io.*;
import java.util.Base64;

public class Courier {
    public String sender;
    private String recipient;
    private String mailContent;
    private String mailSubject;
    private File mailAttachment = null; // optional
    
    // Once all the necessary information has been input by the user
    public Courier(String sendr, String rcpt, String Content, String Subject){
        sender = sendr;
        recipient = rcpt;
        mailContent = Content;
        mailSubject = Subject;
    }

    // Allow setting attachment (optional), we will decided in ComposeController.java
    public void setAttachmentFile(File attachment) {
        this.mailAttachment = attachment;
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
        StringBuilder sendingContent = new StringBuilder();
        String serverResponse;

        // Request mail initiation
        connection.out.write("DATA\r\n");
        connection.out.flush();
        serverResponse = connection.serverResponseReader();
        if(!serverResponse.startsWith("354")){
            System.out.println("DATA command failed");
            System.out.println(serverResponse);
            return false;
        } System.out.println("DATA succeeded, crafting mail");

        // Define MIME boundary
        /* instead of this, we will create a StringBuilder, then continously concatenate them
        sendingContent = "Subject: " + mailSubject + "\r\n" +
                      "From: " + sender + "\r\n" +
                      "To: " + recipient + "\r\n" +
                      "\r\n" +
                      mailContent;
        */ 
        
        // System.out.println("=== Email content ===");
        // System.out.println(mailContent);
        // System.out.println("=====================");
        String boundary = "====boundary123===";

        // Email headers
        sendingContent.append("Subject: ").append(mailSubject).append("\r\n");
        sendingContent.append("From: ").append(sender).append("\r\n");
        sendingContent.append("To: ").append(recipient).append("\r\n");
        sendingContent.append("MIME-Version: 1.0\r\n");
        sendingContent.append("Content-Type: multipart/mixed; boundary=").append(boundary).append("\r\n");
        sendingContent.append("\r\n");

        // Text part
        sendingContent.append("--").append(boundary).append("\r\n");
        sendingContent.append("Content-Type: text/plain; charset=UTF-8\r\n");
        sendingContent.append("Content-Transfer-Encoding: 7bit\r\n");
        sendingContent.append("\r\n");
        sendingContent.append(mailContent).append("\r\n");

        // Attachment part (optional)
        if (mailAttachment != null && mailAttachment.exists()) {
            sendingContent.append("--").append(boundary).append("\r\n");
            sendingContent.append("Content-Type: application/octet-stream; name=\"")
                   .append(mailAttachment.getName()).append("\"\r\n");
            sendingContent.append("Content-Disposition: attachment; filename=\"")
                   .append(mailAttachment.getName()).append("\"\r\n");
            sendingContent.append("Content-Transfer-Encoding: base64\r\n");
            sendingContent.append("\r\n");

            // Encode file to base64 in 76-character lines
            byte[] fileBytes = readFileToBytes(mailAttachment);     // use a helper function
            String encodedFile = Base64.getMimeEncoder(76, "\r\n".getBytes()).encodeToString(fileBytes);
            sendingContent.append(encodedFile).append("\r\n");
        }
              
        connection.out.write(sendingContent.toString());
        connection.out.write("\r\n.\r\n");
        connection.out.flush();
        serverResponse = connection.serverResponseReader();
        if(!serverResponse.startsWith("250")){
            System.out.println("Sending failed");
            System.out.println(serverResponse);
            return false;
        }

        return true;
    }

    // Actual function to read (any) file into a byte array
    private byte[] readFileToBytes(File file) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (InputStream in = new FileInputStream(file)) {
            byte[] chunk = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(chunk)) != -1) {
                buffer.write(chunk, 0, bytesRead);
            }
        }
        return buffer.toByteArray();
    }
}
