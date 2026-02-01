import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("\t\tProject Name -- File Backup Utility");
        System.out.println("-----------------------------------------------------");
        try {
            // Load config
            BackupConfig config = new BackupConfig("backup.properties");
            // Get user's choice
            BackupModeSelector selector = new BackupModeSelector();
            String mode = selector.selectMode();
            // Run backup
            BackupManager backupManager = new BackupManager("data/manifest.txt");
            backupManager.performBackup(config.sourceDir, config.backupDir, mode);
        } catch (IOException e) {
            System.err.println("⚠ Config error: " + e.getMessage());
            System.exit(1);
        }
    }
}