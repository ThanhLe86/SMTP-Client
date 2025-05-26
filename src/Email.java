/*
 * Email data structure
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Email {
    //Email main attributes
    private StringProperty emailTime;
    private StringProperty emailSender;
    private StringProperty emailRecipient;
    private StringProperty emailSubject;
    private StringProperty emailBody;

    //Database path and date format
    private static final String FILE_PATH = "database/database.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    //constructor
    public Email(String time, String sender, String recipient, String subject, String body) {
        this.emailTime = new SimpleStringProperty(time);
        this.emailSender = new SimpleStringProperty(sender);
        this.emailRecipient = new SimpleStringProperty(recipient);
        this.emailSubject = new SimpleStringProperty(subject);
        this.emailBody = new SimpleStringProperty(body);
    }

    //getters functions
    //for javafx elements
    public StringProperty timeProperty() { return this.emailTime; }
    public StringProperty senderProperty() { return this.emailSender; }
    public StringProperty recipientProperty() { return this.emailRecipient; }
    public StringProperty subjectProperty() { return this.emailSubject; }
    public StringProperty bodyProperty() { return this.emailBody; }

    //for other classes
    public String getTime() { return emailTime.get(); }
    public String getSender() { return emailSender.get(); }
    public String getRecipient() { return emailRecipient.get(); }
    public String getSubject() { return emailSubject.get(); }
    public String getBody() { return emailBody.get(); }
    
    //write to database.csv
    public static void logEmail(String sender, String recipient, String subject, String body) {
        String timestamp = LocalDateTime.now().format(formatter);

        // formating .txt name
        DateTimeFormatter fileFormatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
        String fileName = LocalDateTime.now().format(fileFormatter);

        // create the .txt file name
        File databaseFolder = new File("database");
        String bodyFilename = fileName + ".txt";
        File bodyFile = new File(databaseFolder, bodyFilename);

        // save body text to the .txt file
        try (FileWriter bodyWriter = new FileWriter(bodyFile)) {
            bodyWriter.write(body);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.append(escapeCSV(timestamp)).append(",");
            writer.append(escapeCSV(sender)).append(",");
            writer.append(escapeCSV(recipient)).append(",");
            writer.append(escapeCSV(subject)).append(",");
            writer.append(escapeCSV(bodyFilename)).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //helper function to escape CSV fields
    private static String escapeCSV(String field) {
        if (field == null) return "";
        field = field.replace("\"", "\"\""); // escape double quotes
        return "\"" + field + "\"";          // wrap in quotes to handle commas/newlines
    }

    //read emails from database.csv
    public static List<Email> readEmails(String userEmail) {
        List<Email> emailList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = parseCSVLine(line);

                if (fields.length >= 5) {
                    //read the email components
                    String time = fields[0];
                    String sender = fields[1];
                    String recipient = fields[2];
                    String subject = fields[3];
                    
                    // read the body from the corresponding .txt file
                    String fileName = fields[4];
                    File bodyFile = new File("database", fileName);

                    StringBuilder bodyContent = new StringBuilder();
                    try (BufferedReader bodyReader = new BufferedReader(new FileReader(bodyFile))) {
                        String bodyLine;
                        while ((bodyLine = bodyReader.readLine()) != null) {
                            bodyContent.append(bodyLine).append(" ");
                        }
                    } catch (IOException e) {
                        bodyContent.append("[Could not read body]");
                    }

                    String body = bodyContent.toString().trim();
                    
                    if (sender.equals(userEmail)) { //only retrieve emails sent from the current user
                        emailList.add(new Email(time, sender, recipient, subject, body));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emailList;
    }

    //helper function to parse CSV lines
    private static String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        result.add(sb.toString());
        return result.toArray(new String[0]);
    }
}
