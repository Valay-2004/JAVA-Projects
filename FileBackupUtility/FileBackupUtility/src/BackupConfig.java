import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class BackupConfig {
    public final Path sourceDir;
    public final Path backupDir;

    public BackupConfig(String configFile) throws IOException{
        Properties props = new Properties();
        props.load(Files.newInputStream(Paths.get(configFile)));

        this.sourceDir = Paths.get(props.getProperty("source.dir", "source"));
        this.backupDir = Paths.get(props.getProperty("backup.dir", "backups"));
    }
}


