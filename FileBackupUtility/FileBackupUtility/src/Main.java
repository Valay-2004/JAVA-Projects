import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("\t\tProject Name -- File Backup Utility");
        try {
            BackupConfig config = new BackupConfig("backup.properties");
            BackupManager backupManager = new BackupManager("data/manifest.txt");
            backupManager.performBackup(config.sourceDir, config.backupDir);
        } catch (IOException e) {
            System.err.println("Config error: " + e.getMessage());
            System.exit(1);
        }
    }
}