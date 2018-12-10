package twolevelсache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LFUAlgorithm implements CachingAlgorithm {
    private Map<String, Long> numberOfUsagesMap;

    private final Logger logger = LoggerFactory.getLogger(TwoLevelCacheImpl.class);

    LFUAlgorithm() {
        numberOfUsagesMap = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void grabKey(String key) {
        if (numberOfUsagesMap.containsKey(key)) {
            numberOfUsagesMap.put(key, numberOfUsagesMap.get(key) + 1);
        } else {
            numberOfUsagesMap.put(key, 1L);
        }
    }

    public String getWeakestKey(Cache... cache) {

        String weakestKey = null;
        Long weakestValue = 0L;
        for (Cache cache1 : cache) {
            for (String key : cache1.keySet()) {
                if (weakestKey == null) {
                    weakestKey = key;
                    weakestValue = numberOfUsagesMap.get(key);
                } else if (numberOfUsagesMap.get(key) < weakestValue) {
                    weakestKey = key;
                    weakestValue = numberOfUsagesMap.get(key);
                }
            }
        }
        return weakestKey;
    }

    @Override
    public synchronized void removeKey(String key) {
        numberOfUsagesMap.remove(key);
    }
}
