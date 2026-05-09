import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {

        Path watchedDirectory = Paths.get("watched");
        FTPClient ftpClient = new FTPClient();

        FileWatcher watcher = new FileWatcher(watchedDirectory, ftpClient);

        System.out.println("Watching directory: " + watchedDirectory.toAbsolutePath());

        while (true) {
            try {
                watcher.scanDirectory();
                Thread.sleep(10000);
            }
            catch (InterruptedException e) {
                System.out.println("Watcher interrupted.");
                break;
            }
            catch (IOException e) {
                System.out.println("Directory scan failed: " + e.getMessage());
            }
        }
    }
}