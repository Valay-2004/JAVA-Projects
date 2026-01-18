import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileCopier {
    public void copyFile(Path sourceFile, Path sourceRoot, Path backupRoot) throws IOException {
        if(!Files.isRegularFile(sourceFile)) return;    //skip directories, symlinks, etc
        Path relativePath = sourceRoot.relativize(sourceFile);
        Path targetPath = backupRoot.resolve(relativePath);
        Files.createDirectories(targetPath.getParent());
        try {
            Files.copy(sourceFile, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            System.err.println("Failed to back up: " + sourceFile + " -> " + e.getMessage());
        }
    }
}
