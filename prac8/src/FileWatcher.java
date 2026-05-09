import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FileWatcher {

    private final Path watchedDirectory;
    private final FTPClient ftpClient;
    private final Map<String, Long> knownFiles;

    public FileWatcher(Path watchedDirectory, FTPClient ftpClient) {
        this.watchedDirectory = watchedDirectory;
        this.ftpClient = ftpClient;
        this.knownFiles = new HashMap<>();
    }

    public void scanDirectory() throws IOException {

        Files.list(watchedDirectory)
                .filter(Files::isRegularFile)
                .filter(file -> file.getFileName().toString().endsWith(".txt"))
                .forEach(file -> {

                    try {
                        long modifiedTime = Files.getLastModifiedTime(file).toMillis();
                        String filename = file.getFileName().toString();

                        if (!knownFiles.containsKey(filename)) {
                            System.out.println("New file detected: " + filename);
                            uploadChangedFile(file, filename);
                            knownFiles.put(filename, modifiedTime);
                        }
                        else if (knownFiles.get(filename) != modifiedTime) {
                            System.out.println("Modified file detected: " + filename);
                            uploadChangedFile(file, filename);
                            knownFiles.put(filename, modifiedTime);
                        }

                    }
                    catch (IOException e) {
                        System.out.println("Error processing file: " + e.getMessage());
                    }
                });
    }

    private void uploadChangedFile(Path file, String filename) throws IOException {
        ftpClient.connect("127.0.0.1", 2121);
        ftpClient.login("anonymous", "test");
        ftpClient.uploadFile(file, filename);
        ftpClient.disconnect();

        System.out.println("Uploaded: " + filename);
    }
}