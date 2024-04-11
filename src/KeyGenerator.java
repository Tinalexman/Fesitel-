import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class KeyGenerator {
    private static final long SEED = 98942897316645566L;
    private static final String characterPool = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";

    public static List<Integer> generateUniqueKeys(int rounds) {
        Random random = new Random(SEED);
        List<Integer> keys = new ArrayList<>();

        for (int i = 0; i < rounds; ++i) {
            char firstChar = characterPool.charAt((((int) Math.abs(random.nextInt())) % characterPool.length()));
            int secondChar = characterPool.charAt((((int) Math.abs(random.nextInt())) % characterPool.length()));
            int input = ((byte) firstChar);
            input <<= 8;
            input |= ((byte) secondChar);

            int response = switch(i) {
                case 0 -> generateRailFenceCipherKey(input);
                case 1 -> generateColumnarCipherKey(input);
                default -> generateVignereCipherKey(input);
            };

            keys.add(response);
        }
        return keys;
    }

    public static int generateRailFenceCipherKey(int input) {
        return input;
    }

    private static int generateColumnarCipherKey(int input) {
        return input;
    }

    private static int generateVignereCipherKey(int input) {
        return input;
    }

}
