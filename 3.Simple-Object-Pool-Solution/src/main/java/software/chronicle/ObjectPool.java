package software.chronicle;

/**
 * @author Rob Austin.
 */
public interface ObjectPool {

    StringBuilder acquire();

    void release(StringBuilder sb);

    String name();
}
