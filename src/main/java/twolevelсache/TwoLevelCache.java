package twolevelсache;

public interface TwoLevelCache {

    void cacheObject(String key, Object obj);

    Object getObject(String key);

    void clear();

    int getSize();
}
