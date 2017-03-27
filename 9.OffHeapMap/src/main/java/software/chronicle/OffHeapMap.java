package software.chronicle;

import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Rob Austin.
 */
public class OffHeapMap {

    /**
     * each time you run this the number of entries in the map increases
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        final ChronicleMap<Long, CharSequence> map = ChronicleMapBuilder.of(Long.class, CharSequence.class)
                .createPersistedTo(new java.io.File("file.map"));

        final long key = (map.isEmpty()) ? 1 : map.size() + 1;

        map.put(key, LocalDateTime.now().toString());

        for (Map.Entry<Long, CharSequence> e : map.entrySet()) {
            System.out.println(e);
        }

    }
}
