package software.chronicle;

/**
 * @author Rob Austin.
 */
public class StackNotCleaningPoppedValuesMemoryLeak {

    public static final int SIZE = 2000;

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
        stackArray[pointer] = null;
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

        System.out.println("pushing");
        for (int i = 0; i < SIZE; i++) {
            Thread.sleep(10);
            stack.push(new byte[1 << 20]);
            System.out.print(".");
        }
        System.out.println("\n\npopping");
        for (int i = 0; i < SIZE; i++) {
            Thread.sleep(10);
            Object element = stack.pop();
            System.out.print(".");
        }
        System.out.println("\n\nfinished");
    }

}
