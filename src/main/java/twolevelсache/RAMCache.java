package twolevelсache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RAMCache implements Cache {
    private Map<String, Object> cache;
    private int capacity;

    private final Logger logger = LoggerFactory.getLogger(TwoLevelCacheImpl.class);

    RAMCache(int capacity) {
        this.capacity = capacity;
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public void cacheObject(String key, Object obj) {
        logger.info("начинается кэширование объекта в RAM");
        cache.put(key, obj);
    }

    @Override
    public Object getObject(String key) {
        logger.info("выдается объект из RAM");
        return cache.get(key);
    }

    @Override
    public boolean containsObject(String key) {
        return cache.containsKey(key);
    }

    @Override
    public Object removeObject(String key) {
        logger.info("вытаскивается объект из RAM");
        return cache.remove(key);
    }

    @Override
    public boolean deleteObject(String key) {
        logger.info("удаляется элемент из RAM");
        cache.remove(key);
        return !cache.containsKey(key);
    }

    @Override
    public boolean isNotFull() {
        return cache.size() < capacity;
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public Set<String> keySet() {
        return cache.keySet();
    }

    @Override
    public int getSize() {
        return cache.size();
    }


}
