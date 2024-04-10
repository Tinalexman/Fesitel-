import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.IntBuffer;


public class Ransomware {

    static enum Mode {
        Encrypt,
        Decrypt
    }

    public static String process(String message, Mode mode) {
        if (mode == Mode.Encrypt) {
            message = checkAndPad(message);
        }

    
        int[] messageBytes = convertCharArrayToIntArray(message.toCharArray());
        
        int[] data = switch (mode) {
            case Encrypt -> FesitelAlgorithm.encrypt(messageBytes);
            case Decrypt -> FesitelAlgorithm.decrypt(messageBytes);
        };
    
        String response = new String(convertIntArrayToCharArray(data));
        if(mode == Mode.Decrypt) {
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
