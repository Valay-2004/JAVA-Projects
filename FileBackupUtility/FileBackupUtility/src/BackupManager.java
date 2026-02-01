import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;

public class BackupManager {

    private final ManifestManager manifestManager;

    // Constructor
    public BackupManager(String manifestFilePath) {
        this.manifestManager = new ManifestManager(manifestFilePath);
    }

    // ====== MAIN BACKUP LOGIC ===== //
    public void performBackup(Path sourceDir, Path backupDir, String mode) {
        try{
            if("zip".equals(mode)){
                System.out.println("📁 Using ZIP backup mode");
                ZipBackupWriter writer = new ZipBackupWriter();
                writer.backup(sourceDir, backupDir, manifestManager);
            } else {
                System.out.println("📂 Using folder backup mode");
                FolderBackupWriter writer = new FolderBackupWriter();
                writer.backup(sourceDir, backupDir, manifestManager);
            }

        } catch (IOException e) {
            throw new RuntimeException("⚠️ Backup failed ", e.getCause());
        }
    }
}
