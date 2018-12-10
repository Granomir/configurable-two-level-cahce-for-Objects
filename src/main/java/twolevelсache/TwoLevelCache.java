package twolevelсache;

public interface TwoLevelCache {

    void cacheObject(String key, Object obj) throws SpecifiedKeyExistsException;

    Object getObject(String key);

    void clear();

    int getSize();
}
