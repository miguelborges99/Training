package software.chronicle.solution;

/**
 * @author Rob Austin.
 */
public class MeasureObjectCreation {

    private static void createObject() {
        // TODO create the object
    }

    public static void main(String[] args) {
        System.gc();

        createObject(); // warm up.

        long used1 = memoryUsed();

        createObject();

        long used2 = memoryUsed();
        if (used1 == used2)
            System.err.println("You need to turn off the TLAB with -XX:-UseTLAB");
        else
            System.out.printf("Space used by one object is " + (used2 - used1) + " bytes%n");
    }

    public static long memoryUsed() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }
}
