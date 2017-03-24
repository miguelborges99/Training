package software.chronicle;

/**
 * @author Rob Austin.
 */
public class StackNotFreeingPoppedValuesMemoryLeak {

    private static final int SIZE = 2000;

    private int maxSize;
    private Object[] stackArray;
    private int pointer;

    private StackNotFreeingPoppedValuesMemoryLeak(int s) {
        maxSize = s;
        stackArray = new Object[maxSize];
        pointer = -1;
    }

    private void push(Object j) {
        stackArray[++pointer] = j;
    }

    private Object pop() {
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

        StackNotFreeingPoppedValuesMemoryLeak stack = new StackNotFreeingPoppedValuesMemoryLeak(SIZE);

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
