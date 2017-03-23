package software.chronicle;

/**
 * @author Rob Austin.
 */
public class AutoBoxingMemoryLeak {
    
    private void addIncremental(long l) {
        long sum = 0L;
        sum = sum + l;
    }

    public static void main(String[] args) {
        AutoBoxingMemoryLeak adder = new AutoBoxingMemoryLeak();
        for (long i = 0; i < 1000; i++) {
            adder.addIncremental(i);
        }
    }

}
