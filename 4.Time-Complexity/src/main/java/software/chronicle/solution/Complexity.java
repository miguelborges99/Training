package software.chronicle.solution;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author Rob Austin.
 */
public class Complexity {

    public static void main(String[] args) throws Exception {
        final List<String> words = loadWords();

        final List<? extends Class<? extends AbstractCollection>> types = Arrays.asList(ArrayList.class,
                LinkedList.class,
                HashSet.class,
                TreeSet.class);

        types.forEach(type -> contains(type, words));
        types.forEach(type -> addEach(type, words));
        types.forEach(type -> addAll(type, words));
        types.forEach(type -> remove(type, words));
    }

    private static void contains(Class<? extends Collection> type, final List<String> data) {
        final Collection<String> words = newInstance(type);
        words.addAll(data);
        logTime(() -> {
            words.contains("cat");
        }, type);
    }

    @SuppressWarnings("UseBulkOperation")
    private static void addAll(Class<? extends Collection> type, final List<String> words) {
        Collection<String> collection = newInstance(type);
        logTime(() -> {
            collection.addAll(words);
        }, type);
    }

    @SuppressWarnings("UseBulkOperation")
    private static void addEach(Class<? extends Collection> type, final List<String> words) {
        Collection<String> collection = newInstance(type);
        logTime(() -> {
            words.forEach(collection::add);
        }, type);
    }

    private static void remove(Class<? extends Collection> type, final List<String> words) {
        Collection<String> collection = newInstance(type);
        collection.addAll(words);
        logTime(() -> {
            words.remove("cat");
        }, type);
    }

    private static Collection<String> newInstance(Class<? extends Collection> type) {
        try {
            //noinspection unchecked
            return type.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void logTime(Runnable r, Class<? extends Collection> type) {
        // warn up
        r.run();

        final long start = System.nanoTime();
        r.run();
        final long latency = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - start);
        String method = Thread.currentThread().getStackTrace()[2].getMethodName();
        System.out.println(type.getSimpleName() + " " + method + " took " + latency + "us");
    }

    private static List<String> loadWords() throws IOException {
        final Pattern whiteSpace = Pattern.compile("\\s");
        final List<String> words = new ArrayList<String>();
        final InputStream stream = Complexity.class.getResourceAsStream("/big.txt");
        final Reader read = new InputStreamReader(stream);

        final BufferedReader lineReader = new BufferedReader(read);

        for (; ; ) {

            final String line = lineReader.readLine();

            if (line == null)
                return words;

            if (line.length() == 0)
                continue;

            for (String word : whiteSpace.split(line)) {
                words.add(word.trim().toLowerCase());
            }
        }
    }
}

