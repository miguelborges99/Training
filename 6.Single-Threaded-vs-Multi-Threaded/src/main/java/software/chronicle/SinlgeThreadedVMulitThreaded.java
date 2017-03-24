package software.chronicle;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SinlgeThreadedVMulitThreaded {

    private static final int SIZE = 1_000_000;
    private static volatile int x;

    public static void main(String[] args) {

        List<Integer> data = randomNumbers();
        singleThreadedSorting(data);
        multiThreadedSorting(data);
    }

    private static void singleThreadedSorting(List<Integer> data) {
        // warmup
        data.stream().sorted().mapToInt(i -> i).toArray();

        long start = System.nanoTime();
        int[] ints = data.stream().sorted().mapToInt(i -> i).toArray();
        long duration = System.nanoTime() - start;
        System.out.printf("%,d duration(ns) single thread\n", duration);

        // to ensure not optimized out
        x = ints[0];
    }

    private static void multiThreadedSorting(List<Integer> data) {
        // warmup
        // TODO

        long start = System.nanoTime();

        int[] ints = {0}; // TODO change this line


        long duration = System.nanoTime() - start;
        System.out.printf("%,d duration(ns) parallel\n", duration);

        // to ensure not optimized out
        x = ints[0];
    }

    private static List<Integer> randomNumbers() {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            data.add(i);
        }

        Collections.shuffle(data);
        return data;
    }


}
