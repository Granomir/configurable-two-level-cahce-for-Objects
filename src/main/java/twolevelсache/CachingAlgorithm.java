package twolevelсache;

public interface CachingAlgorithm {

    void grabKey(String key);

    String getWeakestKey(Cache cache);

    void removeKey(String key);

    String getName();
}
