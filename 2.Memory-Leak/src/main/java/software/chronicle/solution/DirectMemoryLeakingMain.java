package software.chronicle.solution;

import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;

/**
 * Created by peter on 28/03/2017.
 */
public class DirectMemoryLeakingMain {
    public static void main(String[] args) {
        // run with -verbose:gc
        for (int i = 0; i < 10_000; i++) {
            ByteBuffer bb = ByteBuffer.allocateDirect(1 << 20);
            bb.putInt(0);

//            System.out.println(Long.toHexString(((DirectBuffer) bb).address()));
            ((DirectBuffer) bb).cleaner().clean();
        }
    }
}
