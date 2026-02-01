import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class FolderBackupWriter {
    public void backup(Path sourceDir, Path backupDir, ManifestManager manifestManager) throws IOException {
        Files.createDirectories(backupDir);

        AtomicLong changedCount = new AtomicLong();
        System.out.println("Scanning for changes in: " + sourceDir);
        try(Stream<Path> paths = Files.walk(sourceDir)) {
            paths.filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            if (manifestManager.hasFileChanged(file)) {
                                copyFile(file, sourceDir, backupDir);
                                manifestManager.updateManifest(file);
                                changedCount.getAndIncrement();
                                System.out.println("📦 Backed up: " + sourceDir.relativize(file));
                            }
                        } catch (IOException e) {
                            System.err.println("⚠️ Error backing up " + file + ": " + e.getMessage());
                        }
                    });
        }
        if(changedCount.get() == 0){
            System.out.println("✅ No changes detected.");
        } else {
            System.out.println("✅ Backup Completed! " + changedCount.get() + " file(s) updated.");
        }
    }

    private void copyFile(Path sourceFile, Path sourceRoot, Path backupRoot) throws IOException{
        if(!Files.isRegularFile(sourceFile)) return;

        Path relativePath = sourceRoot.relativize(sourceFile);
        Path targetPath = backupRoot.resolve(relativePath);
        Files.createDirectories(targetPath.getParent());

        try{
            Files.copy(sourceFile, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("⚠️ Copy failed: " + e.getMessage());
        }
    }
}
