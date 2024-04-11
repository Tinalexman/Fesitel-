import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class KeyGenerator {
    private static final String characterPool = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
    

    public static List<Integer> generateUniqueKeys(int rounds) {
        List<Integer> keys = new ArrayList<>();
        
        Collections.addAll(keys, 0b0110001111011110, 0b0110001111011111, 0b0110001111011011);
        return keys;
    }

    public static int generateRailFenceCipherKey() {
        return 0b0000000011011011;
        // return value << 8;
    }


}
