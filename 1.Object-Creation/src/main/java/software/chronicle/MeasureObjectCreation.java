package software.chronicle;

/**
 * @author Rob Austin.
 */
public class MeasureObjectCreation {

    public static void main(String[] args) {
        Runtime.getRuntime().gc();
        long amountOfFreeMemoryInBytes = Runtime.getRuntime().freeMemory();
        new Object();
        System.out.println("objectSize=" + (amountOfFreeMemoryInBytes - Runtime.getRuntime().freeMemory()));
    }
}
