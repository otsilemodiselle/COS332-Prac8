import java.io.*;
import java.net.Socket;

public class FTPClient {

    private Socket controlSocket;

    private BufferedReader reader;

    private BufferedWriter writer;


    public void connect(String host, int port) throws IOException {

        controlSocket = new Socket(host, port);

        reader = new BufferedReader(
                new InputStreamReader(controlSocket.getInputStream())
        );

        writer = new BufferedWriter(
                new OutputStreamWriter(controlSocket.getOutputStream())
        );

        String response = reader.readLine();

        System.out.println("SERVER: " + response);
    }


    public void login(String username, String password) throws IOException {

        sendCommand("USER " + username);

        String userResponse = reader.readLine();

        System.out.println("SERVER: " + userResponse);


        sendCommand("PASS " + password);

        String passResponse = reader.readLine();

        System.out.println("SERVER: " + passResponse);
    }


    private void sendCommand(String command) throws IOException {

        System.out.println("CLIENT: " + command);

        writer.write(command + "\r\n");

        writer.flush();
    }


    public void disconnect() throws IOException {

        sendCommand("QUIT");

        String response = reader.readLine();

        System.out.println("SERVER: " + response);

        controlSocket.close();
    }
}