import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class StorageUtil {
    private StorageUtil(){}

    public static void ensureDataDirectory() throws IOException {
        Path dataDir = Paths.get("data");
        Files.createDirectories(dataDir);

        Path filePath = dataDir.resolve("accounts.db");
    }
}
