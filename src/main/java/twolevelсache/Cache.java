package twolevelсache;

import java.util.Set;

public interface Cache {
    void cacheObject(String key, Object obj);

    Object getObject(String key);

    boolean containsObject(String key);

    Object removeObject(String key);

    boolean deleteObject(String key);

    boolean isNotFull();

    void clear();

    Set<String> keySet();

    int getSize();
}
