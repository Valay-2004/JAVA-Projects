import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        System.out.println("Project Name -- File Backup Utility");
        BackupManager backupManager = new BackupManager("data/manifestFile.txt");
        backupManager.performBackup(
                Path.of("test-source"),
                Path.of("test-backup")
        );
    }
}