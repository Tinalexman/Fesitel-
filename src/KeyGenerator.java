import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class KeyGenerator {
    
    private static final List<String> generatedKeys = new ArrayList<>();
    private static final String characterPool = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
    
    public static int generateRailFenceCipherKey(int value) {
        return 0b0000000011011011;
        // return value << 8;
    }

    public static void reset() {
        KeyGenerator.generatedKeys.clear();
    }
}
