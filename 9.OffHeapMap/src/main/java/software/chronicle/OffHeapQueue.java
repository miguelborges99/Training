package software.chronicle;

import net.openhft.chronicle.queue.ExcerptAppender;
import net.openhft.chronicle.queue.ExcerptTailer;
import net.openhft.chronicle.queue.TailerDirection;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueue;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueueBuilder;
import net.openhft.chronicle.wire.DocumentContext;

import java.io.File;

/**
 * @author Rob Austin.
 */
public class OffHeapQueue {

    public static void main(String[] args) {
        final SingleChronicleQueue q = SingleChronicleQueueBuilder.binary(new File("q4")).build();
        long end = 0;

        final ExcerptAppender appender = q.acquireAppender();
        final ExcerptTailer tailer = q.createTailer().direction(TailerDirection.BACKWARD).toEnd();

        try (final DocumentContext dc = tailer.readingDocument()) {
            if (dc.isPresent())
                end = dc.wire().getValueIn().int64();
        }

        try (final DocumentContext documentContext = appender.writingDocument()) {
            documentContext.wire().getValueOut().int64(end + 1);
        }

        final ExcerptTailer direction = tailer.toStart().direction(TailerDirection.FORWARD);
        for (; ; ) {
            try (final DocumentContext dc = direction.readingDocument()) {
                if (!dc.isPresent())
                    return;
                System.out.println(dc.wire().getValueIn().int64());
            }
        }
    }
}
