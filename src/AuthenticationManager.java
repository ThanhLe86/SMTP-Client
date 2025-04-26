import java.io.*;
import java.util.Base64;

public class AuthenticationManager {
    private String username;
    private String pass;

    public AuthenticationManager(String usrname, String passwrd){
        username = usrname;
        pass = passwrd;
    }

    public boolean Authenticate(SMTP_Connection connection) throws IOException{
        String serverResponse;

        //Start authenticating
        System.out.println("Attemting to authenticate...");
        connection.out.write("AUTH LOGIN\r\n");
        connection.out.flush();
        serverResponse = connection.in.readLine();
        if(!serverResponse.startsWith("334")){
            System.err.println("Authentication error: ");
            System.out.println(serverResponse);
            connection.printServerResponse();
            return false;
        }


        connection.out.write(Base64.getEncoder().encodeToString(username.getBytes()) + "\r\n");
        connection.out.flush();
        serverResponse = connection.in.readLine(); // Should start with 334
        if(!serverResponse.startsWith("334")){
            System.err.println("Authentication error: ");
            System.out.println(serverResponse);
            connection.printServerResponse();
            return false;
        }


        connection.out.write(Base64.getEncoder().encodeToString(pass.getBytes()) + "\r\n");
        connection.out.flush();
        serverResponse = connection.in.readLine(); // Should start with 235
        if (serverResponse.startsWith("235")) System.out.println("Authentication succeeded!");
        else
        { 
            System.err.println("Authentication error: ");
            System.out.println(serverResponse);
            connection.printServerResponse();
            return false;
        }

        return true;

    }
}
