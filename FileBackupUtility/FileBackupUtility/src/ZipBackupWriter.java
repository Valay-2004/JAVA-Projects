import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipBackupWriter {
    public void backup(Path sourceDir, Path backupDir, ManifestManager manifest) throws IOException {
        Files.createDirectories(backupDir);
        Path zipFile = backupDir.resolve("backup.zip");

        System.out.println("📁 Scanning for changed files in: " + sourceDir);

        try (ZipOutputStream zipOut = new ZipOutputStream(
                new BufferedOutputStream(Files.newOutputStream(zipFile)));
             var stream = Files.walk(sourceDir)) {

            AtomicLong fileCount = new AtomicLong(0);

            stream
                    .filter(Files::isRegularFile)
                    .filter(manifest::hasFileChanged)
                    .forEach(file -> {
                        try {
                            System.out.println("✅ Adding to ZIP: " + sourceDir.relativize(file));
                            addToZip(zipOut, file, sourceDir);
                            manifest.updateManifest(file);
                            fileCount.incrementAndGet();
                        } catch (IOException e) {
                            System.err.println("❌ ZIP add failed: " + e.getMessage());
                        }
                    });

            System.out.println("📦 ZIP Backup completed. Files added: " + fileCount.get());
        }
    }

    private void addToZip(ZipOutputStream zipOut, Path file, Path sourceRoot) throws IOException {
        Path relPath = sourceRoot.relativize(file);
        ZipEntry entry = new ZipEntry(relPath.toString());
        zipOut.putNextEntry(entry);
        Files.copy(file, zipOut);
        zipOut.closeEntry();
    }
}