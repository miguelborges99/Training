package software.chronicle;

/**
 * @author Rob Austin.
 */
public class StackNotCleaningPoppedValuesMemoryLeak {

    public static final int SIZE = 1000;

    private int maxSize;
    private Object[] stackArray;
    private int pointer;

    public StackNotCleaningPoppedValuesMemoryLeak(int s) {
        maxSize = s;
        stackArray = new Object[maxSize];
        pointer = -1;
    }

    public void push(Object j) {
        stackArray[++pointer] = j;
    }

    public Object pop() {
        Object i = stackArray[pointer];
        // TODO
        pointer--;
        return i;
    }

    public Object peek() {
        return stackArray[pointer];
    }

    public boolean isEmpty() {
        return (pointer == -1);
    }

    public boolean isFull() {
        return (pointer == maxSize - 1);
    }


    public static void main(String[] args) throws InterruptedException {

        StackNotCleaningPoppedValuesMemoryLeak stack = new StackNotCleaningPoppedValuesMemoryLeak(SIZE);

        for (int i = 0; i < SIZE; i++) {
            Thread.sleep(100);
            stack.push(new byte[1 << 20]);
        }
        for (int i = 0; i < SIZE; i++) {
            Thread.sleep(100);
            Object element = stack.pop();
        }
        System.out.println("finished");
    }

}
