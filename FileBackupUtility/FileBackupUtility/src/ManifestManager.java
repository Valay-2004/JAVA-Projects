import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ManifestManager {
    private final Path manifestPath;

    public ManifestManager(String manifestPath) {
        this.manifestPath = Paths.get(manifestPath);
        ensureManifestExists();
    }

    // ====== ensure manifest exists =====//
    private void ensureManifestExists() {
        // check for the file
        try {
            Path parentDir = manifestPath.getParent();
            if (parentDir != null && Files.notExists(parentDir)) {
                Files.createDirectories(parentDir); // creating parent directory;
            }
            // create file if not exist
            if (Files.notExists(manifestPath)) {
                Files.createFile(manifestPath);
            }
            // do nothing as file already exists
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean hasFileChanged(Path sourceFile) {
        if (!Files.exists(sourceFile)) return true; // file was deleted so there was a change
        try (BufferedReader reader = Files.newBufferedReader(manifestPath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                // 2 limits to 2 parts (just in case someone has | is filepath)
                String[] p = line.split("\\|", 2);
                if (p.length < 2) continue; // skip any malformed lines
                String path = p[0];

                if (path.equals(sourceFile.toString())) {
                    try {
                        long lastKnownTime = Long.parseLong(p[1].trim());
                        long currentLastModified = Files.getLastModifiedTime(sourceFile).toMillis();
                        return currentLastModified != lastKnownTime;
                    } catch (NumberFormatException e) {
                        return true; // if corrupted timestamp then must be changed
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading manifest: " + e.getMessage());
        }
        // file not found so return true! as must be new file
        return true;
    }

    public void updateManifest(Path sourceFile) throws IOException {
        Path tempFilePath = Files.createTempFile(manifestPath.getParent(), "manifest", ".tmp");
        long currentTime = Files.getLastModifiedTime(sourceFile).toMillis();
        try (BufferedReader reader = Files.newBufferedReader(manifestPath);
             BufferedWriter writer = Files.newBufferedWriter(tempFilePath)) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith(sourceFile + "|")) {
                    continue;       // skip old versions
                }
                writer.write(line);
                writer.newLine();
            }

            // Update the entry only once
            writer.write(sourceFile + "|" + currentTime);
            writer.newLine();
        } catch (IOException e) {
            // cleanup delete temp file
            Files.deleteIfExists(tempFilePath);
            throw new IOException("Failed to update manifest: " + e.getMessage());
        }
        Files.move(tempFilePath, manifestPath, StandardCopyOption.REPLACE_EXISTING);

    }

}
