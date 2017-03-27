package software.chronicle;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PipelineOfTasks {
    public static void main(String[] args) throws InterruptedException {
        new software.chronicle.PipelineOfTasks();
    }

    private static final long SIZE = 1_000_000L;
    private long result;
    private Queue<Runnable> queue = new ConcurrentLinkedQueue<>(); // todo create the queue

    /**
     * task that are created on a number of threads can be executed on a single thread
     *
     * @throws InterruptedException
     */
    private PipelineOfTasks() throws InterruptedException {
        createTasksOnDiffrentThreads();

        // all the tasks are executed on the main thread
        for (; ; ) {
            Runnable task = queue.poll();
            if (task == null)
                break;
            task.run();
        }

        assert result == 0;
    }

    private void createTasksOnDiffrentThreads() throws InterruptedException {
        Thread t1 = new Thread(createTasks());
        Thread t2 = new Thread(createTasks());

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    private Runnable createTasks() {
        return () -> {
            for (long i = -SIZE; i <= SIZE; i++) {
                queue.add(nonThreadSafeAddTask(i));
            }
        };
    }

    private Runnable nonThreadSafeAddTask(long i) {
        return () -> result += i;
    }

}

