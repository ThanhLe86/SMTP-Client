import java.io.*;
import java.net.Socket;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SMTP_Connection {
    public Socket socket;
    public BufferedReader in;
    public BufferedWriter out;

    public SMTP_Connection(String addr, int port) throws IOException{
        
        //Establish connection
        socket = new Socket(addr, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String response = in.readLine(); //variable to contain the first line of server response -> for sampling purposes

        if(!response.startsWith("220")) //sampling
        {
            System.err.println("Connection failed: ");
            System.out.println(response); // print first line of response
            printServerResponse(); //print out the rest of the response 
        }
        else System.out.println("Connection successful!");

        verifyConnection();

        // Negotiate TLS
        out.write("STARTTLS\r\n");
        out.flush();
        response = in.readLine();
        if(!response.startsWith("220")){
            System.out.println("STARTTLS failed, server response: ");
            System.out.println(response);
            printServerResponse();
            Quit();
        } else System.out.println("STARTTLS successful");

        sslSocketWrapper();
        // sslChecker(); This doesnt work

        // if(!verifyConnection()){Reset(); Quit();}

        out.write("EHLO test.client\r\n");
        out.flush();
        response = in.readLine();
        System.out.println(response);
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

    public boolean verifyConnection() throws IOException{
        
        //Attempt communication
        String response;
        out.write("EHLO test.client\r\n");
        out.flush();
        response = in.readLine(); //250 means success
        if(response.startsWith("250")) {
            System.out.println("Communication attempt with EHLO succeeded");
            System.out.println(response);
            printServerResponse();
            return true;
        }
        else {
            System.out.println("EHLO test failed, Server response: "); 
            System.out.println(response);
            printServerResponse();
        }
        return false;
    }

    //after this, in and out will be wrapped with sslSocket
    public void sslSocketWrapper() throws IOException{
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(
            socket,
            socket.getInetAddress().getHostAddress(),
            socket.getPort(),
            true
        );

        in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(sslSocket.getOutputStream()));
    }

    public boolean sslChecker() {
        if(socket instanceof SSLSocket) {
            System.out.println("Socket has been wrapped in SSL");
            return true;
        } else {
            System.out.println("Socket is not wrapped in SSL");
            return false;
        }
    }

    public String serverResponseReader() throws IOException{ //New way to read server response
        StringBuilder response = new StringBuilder();
        String responseLine;
        do {
            responseLine = in.readLine();
            response.append(responseLine + "\r\n");
        } while (responseLine != null && responseLine.length() >= 4 && responseLine.charAt(3) == '-');
        return response.toString();
    }
}
