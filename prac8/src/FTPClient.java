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
        System.out.println("SERVER: " + reader.readLine());

        sendCommand("PASS " + password);
        System.out.println("SERVER: " + reader.readLine());
    }

    public Socket openPassiveDataSocket() throws IOException {
        sendCommand("PASV");

        String response = reader.readLine();
        System.out.println("SERVER: " + response);

        int start = response.indexOf('(');
        int end = response.indexOf(')');

        String insideBrackets = response.substring(start + 1, end);
        String[] parts = insideBrackets.split(",");

        String ipAddress = parts[0] + "." + parts[1] + "." + parts[2] + "." + parts[3];

        int p1 = Integer.parseInt(parts[4]);
        int p2 = Integer.parseInt(parts[5]);

        int dataPort = (p1 * 256) + p2;

        System.out.println("Opening passive data connection to " + ipAddress + ":" + dataPort);

        return new Socket(ipAddress, dataPort);
    }

    private void sendCommand(String command) throws IOException {
        System.out.println("CLIENT: " + command);
        writer.write(command + "\r\n");
        writer.flush();
    }

    public void disconnect() throws IOException {
        sendCommand("QUIT");
        System.out.println("SERVER: " + reader.readLine());
        controlSocket.close();
    }
}