import java.util.List;
import java.util.ArrayList;

public class FesitelAlgorithm {

    public static final int ROUNDS = 3;
    private static final List<Integer> uniqueKeys = new ArrayList<>();

    public static void initialize(List<Integer> keys) {
        FesitelAlgorithm.uniqueKeys.clear();
        FesitelAlgorithm.uniqueKeys.addAll(keys);
    }

    /**
     * Since one INT is 32 bits or 4 bytes, it is divisible by 2 so we can use the
     * individual numbers in the array as inputs to the Feistel Cipher
     * 
     * @param data
     * @return
     */
    public static int[] encryptData(int[] data) {

        for (int i = 0; i < data.length; ++i) {
            int modifiedValue = data[i];
            for (int round = 0; round < ROUNDS; ++round) {
                int key = uniqueKeys.get(round);
                modifiedValue = encryptRound(modifiedValue, key);
            }

            data[i] = modifiedValue;
        }

        return data;
    }

    private static int combine(int value, int key) {
        return key;
    }

    private static int encryptRound(int value, int key) {

        int l0 = value >> 16, r0 = ((value << 16) >> 16); // Extract the lower and upper 16 bits of the integer

        int E = combine(r0, key); // combine the right hand value with the key

        int l1 = r0;
        int r1 = l0 ^ E;

        int response = l1;
        response <<= 16;
        response |= r1;

        return response;
    }

    private static int decryptRound(int value, int key) {

        int l1 = value >> 16, r1 = ((value << 16) >> 16);

        int r0 = l1;
        int E = combine(r0, key);
        int l0 = r1 ^ E;

        int response = l0;
        response <<= 16;
        response |= r0;

        return response;
    }

    public static int[] decryptData(int[] data) {

        for (int i = 0; i < data.length; ++i) {
            int modifiedValue = data[i];
            for (int round = 0; round < ROUNDS; ++round) {
                int key = uniqueKeys.get(ROUNDS - round - 1);
                modifiedValue = decryptRound(modifiedValue, key);
            }

            data[i] = modifiedValue;
        }

        return data;
    }
}
