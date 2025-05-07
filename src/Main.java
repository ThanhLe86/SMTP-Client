import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        SMTP_Connection tempConnection = new SMTP_Connection("smtp.gmail.com", 587); //includes connecting and attempting comm
        AuthenticationManager tempAuthenticator = new AuthenticationManager("lejames726yahhh@gmail.com", ""); // Need app password to work

        if(tempAuthenticator.Authenticate(tempConnection)){
            tempConnection.Reset();
            tempConnection.Quit();
        } else tempConnection.Quit();
    }
}
