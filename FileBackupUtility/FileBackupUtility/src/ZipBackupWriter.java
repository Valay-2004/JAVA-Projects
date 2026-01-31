import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipBackupWriter {
    public void backup(Path sourceDir, Path backupDir, ManifestManager manifest) throws IOException {
        Files.createDirectories(backupDir);
        Path zipFile = backupDir.resolve("backup.zip"); // or timestamped name

        try (ZipOutputStream zipOut = new ZipOutputStream(
                new BufferedOutputStream(Files.newOutputStream(zipFile)))) {

            Files.walk(sourceDir)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            if (manifest.hasFileChanged(file)) {
                                addToZip(zipOut, file, sourceDir);
                                manifest.updateManifest(file);
                            }
                        } catch (IOException e) {
                            System.err.println("⚠️ ZIP error: " + e.getMessage());
                        }
                    });
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