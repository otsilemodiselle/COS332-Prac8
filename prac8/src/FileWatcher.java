import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FileWatcher {

    private final Path watchedDirectory;

    private final Map<String, Long> knownFiles;

    public FileWatcher(Path watchedDirectory) {
        this.watchedDirectory = watchedDirectory;
        this.knownFiles = new HashMap<>();
    }

    public void scanDirectory() throws IOException {

        Files.list(watchedDirectory)
                .filter(Files::isRegularFile)
                .forEach(file -> {

                    try {

                        long modifiedTime = Files.getLastModifiedTime(file).toMillis();

                        String filename = file.getFileName().toString();

                        if (!knownFiles.containsKey(filename)) {
                            System.out.println("New file detected: " + filename);
                            knownFiles.put(filename, modifiedTime);
                        }
                        else if (knownFiles.get(filename) != modifiedTime) {
                            System.out.println("Modified file detected: " + filename);
                            knownFiles.put(filename, modifiedTime);
                        }

                    }
                    catch (IOException e) {
                        System.out.println("Error reading file metadata: " + e.getMessage());
                    }
                });
    }
}