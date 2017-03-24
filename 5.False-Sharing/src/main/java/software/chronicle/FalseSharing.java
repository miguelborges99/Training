package software.chronicle;


import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class FalseSharing implements Callable<Void> {

    static class PaddedAtomicLong extends AtomicLong {
        public volatile long o1, o2, o3, o4, o5, o6, o7 = 7L;
    }

    private final static int NUM_THREADS = 4;
    private final static int RUNS = 1000 * 1000 * 1000;
    private final int i;

    private static final AtomicLong[] longs = new AtomicLong[NUM_THREADS];

    static {
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new AtomicLong(); // TODO change this line to do false sharing
        }
    }

    private FalseSharing(final int i) {
        this.i = i;
    }

    public static void main(final String[] args) throws Exception {
        final long start = System.nanoTime();

        runTest();

        final long duration = System.nanoTime() - start;
        System.out.printf("%,d ns\n", duration);
    }

    private static void runTest() throws InterruptedException {

        final CompletionService<Void> cs = new ExecutorCompletionService<>(Executors.newFixedThreadPool(NUM_THREADS));
        for (int i = 0; i < NUM_THREADS; i++) {
            cs.submit(new FalseSharing(i));
        }
        for (int i = 0; i < NUM_THREADS; i++) {
            cs.take();
        }
    }

    public Void call() {
        final AtomicLong atomicLong = longs[i];

        for (int i = 0; i < RUNS; i++) {
            atomicLong.set(i);
        }
        return null;
    }
}
