public class Main {
    public static void main(String[] args) {
        String message = "This is a message";
        // System.out.println("Original Message: " + message);

        String encryptedMessage = Ransomware.process(message, Ransomware.Mode.Encrypt);
        System.out.println("Encrypted Message: " + encryptedMessage);

        String decryptedMessage = Ransomware.process(encryptedMessage, Ransomware.Mode.Decrypt);
        System.out.println("Decrypted Message: " + decryptedMessage);
    }
}



