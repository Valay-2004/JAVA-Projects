import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;

public class BackupManager {

    private final ManifestManager manifestManager;
    private final FileCopier fileCopier;
    // Constructor
    public BackupManager(String manifestFilePath) {
        this.manifestManager = new ManifestManager(manifestFilePath);
        this.fileCopier = new FileCopier();
    }

    // ====== MAIN BACKUP LOGIC ===== //
    public void performBackup(Path sourceDir, Path backupDir){
        System.out.println("Scanning for changes in: " + sourceDir);
        try{
            // to ensure that the directory exists
            Files.createDirectories(backupDir);
            AtomicLong changeCount = new AtomicLong();

            //Walk all files in sourceDir
            // 1. Scan all files in sourceDir
            // 2. For each file:
            //      if hasFileChanged(file) => copy it + updateManifest
            Files.walk(sourceDir)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try{
                            if(manifestManager.hasFileChanged(file)){
                                Path relPath = sourceDir.relativize(file);
                                System.out.println("Backing up: " + relPath);

                                fileCopier.copyFile(file, sourceDir, backupDir);
                                manifestManager.updateManifest(file);
                                changeCount.getAndIncrement();
                            }
                        } catch (IOException e){
                            System.err.println("Error processing " + file + ": " + e.getMessage());
                        }
                    });

            if(changeCount.get() == 0) System.out.println("No changes detected. Backup Skipped.");
            else System.out.println("Backup completed! " + changeCount.get() + " file(s) updated");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
