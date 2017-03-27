package software.chronicle;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Rob Austin.
 */
public class SimpleObjectWithOutPool {

    enum ObjectPools implements ObjectPool {
        NO_POOL {
            public StringBuilder acquire() {
                return null; // TODO  - re write this line
            }

            public void release(StringBuilder sb) {

            }
        },
        THREAD_LOCAL_POOL {

            final ThreadLocal<StringBuilder> pool = ThreadLocal.withInitial(StringBuilder::new);

            public StringBuilder acquire() {
                StringBuilder sb = null; // TODO  -  - re write this line
                sb.setLength(0);
                return sb;
            }

            public void release(StringBuilder sb) {

            }
        },
        SINGLE_OBJECT_POOL {

            final AtomicReference<StringBuilder> pool = new AtomicReference<>(new StringBuilder());

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
        },
        SPINNING_SINGLE_OBJECT_POOL {

            final AtomicReference<StringBuilder> pool = new AtomicReference<>(new StringBuilder());

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
        }
    }

    private final Callable<Void> work;
    private static int blackHole;

    private SimpleObjectWithOutPool(ObjectPool pool) {

        work = () -> {
            for (long i = 0; i < 4_000_000L; i++) {
                StringBuilder sb = pool.acquire();
                try {
                    sb.append("hello").append("world").append(" longer string appended");
                    blackHole = sb.hashCode();
                } finally {
                    pool.release(sb);
                }

                // simulates other work in the app
                Thread.yield();
            }
            return null;
        };
    }

    // run this with -XX:+PrintGCDetails to see GC Pauses
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        for (int i = 0; i < 3; i++) {
            for (ObjectPool op : ObjectPools.values()) {

                System.out.println("-------------------------------------------------------");
                System.out.println("running " + op.name());
                final SimpleObjectWithOutPool pool = new SimpleObjectWithOutPool(op);

                // warm up
                pool.run();

                long l = System.currentTimeMillis();
                pool.run();
                System.out.println("time taken " + (System.currentTimeMillis() - l) + "ms when using " + op.name());
            }
        }
    }

    private void run() throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newCachedThreadPool();
        Future<?> f1 = es.submit(work);
        Future<?> f2 = es.submit(work);

        // wait for all threads to finish
        f1.get();
        f2.get();
        es.shutdown();
    }
}
