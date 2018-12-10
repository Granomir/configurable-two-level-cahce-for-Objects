package twolevelсache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwoLevelCacheImpl implements TwoLevelCache {
    private CachingAlgorithm cachingAlgorithm;
    private RAMCache ramCache;
    private MEMORYCache memoryCache;

    private final Logger logger = LoggerFactory.getLogger(TwoLevelCacheImpl.class);

    public TwoLevelCacheImpl(int RAMCapacity, int MEMORYCapacity, String cachingAlgorithm) {
        switch (cachingAlgorithm) {
            case "LFU":
                this.cachingAlgorithm = new LFUAlgorithm();
                break;
            case "LRU":
                this.cachingAlgorithm = new LRUAlgorithm();
                break;
            case "MRU":
                this.cachingAlgorithm = new MRUAlgorithm();
                break;
            default:
                this.cachingAlgorithm = new LRUAlgorithm();
        }
        ramCache = new RAMCache(RAMCapacity);
        memoryCache = new MEMORYCache(MEMORYCapacity);
        logger.info("создан кэш с размерами {} и {} и алгоритмом кэширования {}", RAMCapacity, MEMORYCapacity, cachingAlgorithm);
    }

    @Override
    public synchronized void cacheObject(String key, Object obj) throws SpecifiedKeyExistsException {
        logger.info("начинается кэширование объекта");
        if (!ramCache.containsObject(key) & !memoryCache.containsObject(key)) {
            logger.info("в кэше еще нет такого ключа");
            if (ramCache.isNotFull()) {
                logger.info("в RAM есть место");
                ramCache.cacheObject(key, obj);
                cachingAlgorithm.grabKey(key);
            } else {
                logger.info("в RAM нет места");
                makeCrowdingOut();
                ramCache.cacheObject(key, obj);
                cachingAlgorithm.grabKey(key);
            }
        } else {
            throw new SpecifiedKeyExistsException("Specified key already exists in cache.");
        }
    }

    @Override
    public synchronized Object getObject(String key) {
        logger.info("начинается получение объекта из кэша");
        if (ramCache.containsObject(key)) {
            logger.info("объект нашелся в RAM");
            cachingAlgorithm.grabKey(key);
            return ramCache.getObject(key);
        }
        if (memoryCache.containsObject(key)) {
            logger.info("объект нашелся в Memory и переносится в RAM");
            Object obj = memoryCache.removeObject(key);
            if (ramCache.isNotFull()) {
                logger.info("в RAM есть место");
                ramCache.cacheObject(key, obj);
            } else {
                logger.info("в RAM нет места");
                makeCrowdingOut();
                ramCache.cacheObject(key, obj);
            }
            cachingAlgorithm.grabKey(key);
            return ramCache.getObject(key);
        }
        return null;
    }

    @Override
    public void clear() {
        ramCache.clear();
        memoryCache.clear();
    }

    @Override
    public int getSize() {
        return ramCache.getSize() + memoryCache.getSize();
    }

    private synchronized void makeCrowdingOut() {
        logger.info("начинается вытеснение по выбранному алгоритму");
        if (!memoryCache.isNotFull()) {
            logger.info("в кэше нет места");
            logger.info("Поиск худшего элемента в кэше");
            String weakestKey = cachingAlgorithm.getWeakestKey(ramCache, memoryCache);
            logger.info("Удаление худшего элемента в кэше");
            this.deleteObject(weakestKey);
            cachingAlgorithm.removeKey(weakestKey);
        }
        if (memoryCache.isNotFull()) {
            logger.info("в Memory есть место, поиск худшего элемента в RAM для переноса в Memory");
            String weakestRamKey = cachingAlgorithm.getWeakestKey(ramCache);
            memoryCache.cacheObject(weakestRamKey, ramCache.removeObject(weakestRamKey));
        }

    }

    private void deleteObject(String weakestKey) {
        if (ramCache.containsObject(weakestKey)) {
            ramCache.deleteObject(weakestKey);
        } else {
            memoryCache.deleteObject(weakestKey);
        }
    }
}
