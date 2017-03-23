package software.chronicle;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Arrays.asList;

/**
 * @author Rob Austin.
 */
public class SimpleObjectWithOutPool {

    private static final DaemonThreadFactory DAEMON_THREAD_FACTORY = new DaemonThreadFactory();

    private static ObjectPool SINGLE_OBJECT_POOL = new ObjectPool() {

        private final AtomicReference<StringBuilder> pool = new AtomicReference<>(new StringBuilder());

        public StringBuilder acquire() {
            StringBuilder sb = null; // TODO  - re write this line
            if (sb == null)
                return new StringBuilder();
            sb.setLength(0);
            return sb;
        }

        public void release(StringBuilder sb) {
            pool.lazySet(sb);
        }

        @Override
        public String name() {
            return "single object pool";
        }

    };

    static ObjectPool NO_POOL = new ObjectPool() {

        public StringBuilder acquire() {
            return null; // TODO  - re write this line
        }

        public void release(StringBuilder sb) {

        }

        @Override
        public String name() {
            return "no-pool";
        }

    };


    static ObjectPool THREAD_LOCAL_POOL = new ObjectPool() {

        private final ThreadLocal<StringBuilder> pool = ThreadLocal.withInitial(StringBuilder::new);

        public StringBuilder acquire() {
            StringBuilder sb = null; // TODO  -  - re write this line
            sb.setLength(0);
            return sb;
        }

        public void release(StringBuilder sb) {

        }

        @Override
        public String name() {
            return "thread-local-pool";
        }

    };

    private final Callable<Void> work;
    long count;

    private SimpleObjectWithOutPool(ObjectPool pool) {

        work = () -> {
            for (long i = 0; i < 1000_000L; i++) {
                StringBuilder sb = pool.acquire();
                try {
                    sb.append("hello").append("world");
                    count = sb.hashCode();
                } finally {
                    pool.release(sb);
                }

                // simulates other work in the app
                for (int j = 0; j < 1_000; j++) {
                    count++;
                }
            }
            return null;
        };
    }

    static class DaemonThreadFactory implements ThreadFactory {
        DaemonThreadFactory() {
        }

        public Thread newThread(Runnable r) {
            Thread daemonThread = new Thread(r);
            daemonThread.setDaemon(Boolean.TRUE.booleanValue());
            return daemonThread;
        }
    }

    private void run() throws InterruptedException, ExecutionException {
        Future<?> f1 = Executors.newSingleThreadExecutor(DAEMON_THREAD_FACTORY).submit(work);
        Future<?> f2 = Executors.newSingleThreadExecutor(DAEMON_THREAD_FACTORY).submit(work);

        // wait for all threads to finish
        f1.get();
        f2.get();
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        for (ObjectPool op : asList(NO_POOL, SINGLE_OBJECT_POOL, THREAD_LOCAL_POOL)) {

            final SimpleObjectWithOutPool pool = new SimpleObjectWithOutPool(op);

            // warm up
            pool.run();

            long l = System.currentTimeMillis();
            pool.run();
            System.out.println("time taken " + (System.currentTimeMillis() - l) + "ms when using " + op.name());
        }
    }
}
