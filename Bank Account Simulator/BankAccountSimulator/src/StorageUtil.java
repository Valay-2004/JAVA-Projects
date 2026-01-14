import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class StorageUtil {
    private StorageUtil(){}

    public static Path ensureDataDirectory() throws IOException {
        try {
            Path dataDir = Paths.get("data");
            Files.createDirectories(dataDir);

            return dataDir;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create data directory", e);
        }
    }
}
