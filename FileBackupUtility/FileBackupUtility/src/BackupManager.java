import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
        try{
            // to ensure that the directory exists
            Files.createDirectories(backupDir);

            //Walk all files in sourceDir
            // 1. Scan all files in sourceDir
            // 2. For each file:
            //      if hasFileChanged(file) => copy it + updateManifest
            Files.walk(sourceDir)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try{
                            if(manifestManager.hasFileChanged(file)){
                                System.out.println("Backing up: " + file);
                                fileCopier.copyFile(file, sourceDir, backupDir);
                                manifestManager.updateManifest(file);
                            }
                        } catch (IOException e){
                            System.err.println("Error processing " + file + ": " + e.getMessage());
                        }
                    });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
