import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class Ransomware {

    static enum Mode {
        Encrypt,
        Decrypt
    }

    public static String process(String message, Mode mode) {
        if (mode == Mode.Encrypt) {
            message = checkAndPad(message);
        }

        int[] messageBytes = convertByteArrayToIntArray(message.getBytes());

        int[] data = switch (mode) {
            case Encrypt -> FesitelAlgorithm.encrypt(messageBytes);
            case Decrypt -> FesitelAlgorithm.decrypt(messageBytes);
        };
        
        String response = new String(convertIntArrayToByteArray(data));
        // if(mode == Mode.Decrypt) {
        //     response.trim();
        // }

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

    private static byte[] convertIntArrayToByteArray(int[] data) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(data.length * 4);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        for (int value : data) {
            byteBuffer.putInt(value);
        }
        return byteBuffer.array();
    }

    private static int[] convertByteArrayToIntArray(byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        int[] intArray = new int[data.length / 4];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = byteBuffer.getInt();
        }
        return intArray;
    }
}
