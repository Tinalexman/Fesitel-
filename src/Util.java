class Util {


    public static void printIntArray(String message, int[] array) {
        System.out.println(message);
        for(int i = 0; i < array.length; ++i) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    } 

    public static void printByteArray(String message, byte[] array) {
        System.out.println(message);
        for(int i = 0; i < array.length; ++i) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    } 
}