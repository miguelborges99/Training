package software.chronicle;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Rob Austin.
 */
public class PipelineOfTasks {

    public static void main(String[] args) throws InterruptedException {
        new PipelineOfTasks();
    }

    private static final long SIZE = 1_000_000L;
    private final Queue<Runnable> queue = new LinkedList<>(); // TODO replace with a thread safe Queue
    private long result;

    /**
     * task that are created on a number of threads can be executed on a single thread
     *
     * @throws InterruptedException
     */
    private PipelineOfTasks() throws InterruptedException {
        createTasksOnDifferentThreads();

        // all the tasks are executed on the main thread
        for (; ; ) {
            Runnable task = queue.poll();
            if (task == null)
                break;
            task.run();
        }

        System.out.println("Result: " + result);
        assert result == SIZE;
    }

    private void createTasksOnDifferentThreads() throws InterruptedException {
        Thread t1 = new Thread(createTasks());
        Thread t2 = new Thread(createTasks());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    private Runnable createTasks() {
        return () -> {
            for (long i = 0; i < SIZE; i += 2) {
                queue.add(nonThreadSafeAddTask(1));
            }
        };
    }

    private Runnable nonThreadSafeAddTask(long i) {
        return () -> result += i;
    }

}
