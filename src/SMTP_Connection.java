import java.io.*;
import java.net.Socket;

public class SMTP_Connection {
    public Socket socket;
    public BufferedReader in;
    public BufferedWriter out;

    public SMTP_Connection(String addr, int port) throws IOException{
        
        //Establish connection
        socket = new Socket(addr, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String response = in.readLine();

        if(!response.startsWith("220")) 
        {
            System.err.println("Connection failed: ");
            System.out.println(response);
            printServerResponse();
        }

        else System.out.println("Connection successful!");
        
        //Attempt communication
        out.write("EHLO test.client\r\n");
        out.flush();
        System.out.println("Server response: "); //250 means success
        printServerResponse();
    } 

    public void Reset() throws IOException{

        //Reset
        System.out.println("Resetting session...");
        out.write("RSET\r\n");
        out.flush();
        printServerResponse();
        System.out.println("Connection reset successfully");
    }

    public void Quit() throws IOException{

        //Quit
        System.out.println("Terminating session...");
        out.write("QUIT\r\n");
        out.flush();
        printServerResponse();
        socket.close();
        System.out.println("Connection terminated successfully");
    }

    public void printServerResponse() throws IOException{
        String response;
        do {
            response = in.readLine();
            System.out.println(response);
        } while (response != null && response.length() >= 4 && response.charAt(3) == '-');
    }
}
