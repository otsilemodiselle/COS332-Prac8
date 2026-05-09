import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {

        Path watchedDirectory = Paths.get("watched");

        FileWatcher watcher = new FileWatcher(watchedDirectory);

        System.out.println("Watching directory: " + watchedDirectory.toAbsolutePath());

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect("127.0.0.1", 2121);
            ftpClient.login("anonymous", "test");

            ftpClient.uploadFile(
                    Paths.get("watched", "notes.txt"),
                    "notes.txt"
            );

            ftpClient.disconnect();
        }
        catch (IOException e) {
            System.out.println("FTP error: " + e.getMessage());
        }

        while (true) {
            try {
                watcher.scanDirectory();
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("Watcher interrupted.");
                break;
            } catch (IOException e) {
                System.out.println("Directory scan failed: " + e.getMessage());
            }
        }
    }
}