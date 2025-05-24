import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SearchEngine {
    public static ObservableList<Email> searchEmails(ObservableList<Email> emails, String query, boolean searchSubject, boolean searchBody, boolean searchRecipient) {
        ObservableList<Email> filtered = FXCollections.observableArrayList();

        // chrck if null (will be handled to "return to default later")
        if (query == null || query.trim().isEmpty()) {
            return emails;
        }
        // convert the query to lowercase for case-insensitive search
        String lowercaseQuery = query.toLowerCase();

        // iterate through emails
        for (Email email : emails) {
            boolean match = false;

            // search in the subject, body, and recipient, if they are selected
            if (searchSubject && email.getSubject().toLowerCase().contains(lowercaseQuery)) {
                match = true;
            }

            if (searchBody && email.getBody().toLowerCase().contains(lowercaseQuery)) {
                match = true;
            }

            if (searchRecipient && email.getRecipient().toLowerCase().contains(lowercaseQuery)) {
                match = true;
            }

            if (match) {
                filtered.add(email);
            }
        }

        return filtered;
    }
}
