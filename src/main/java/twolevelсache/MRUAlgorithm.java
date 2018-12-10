package twolevelсache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MRUAlgorithm implements CachingAlgorithm {

    private Map<String, Long> lastTimeOfUsageMap;

    private final Logger logger = LoggerFactory.getLogger(TwoLevelCacheImpl.class);

    MRUAlgorithm() {
        lastTimeOfUsageMap = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void grabKey(String key) {
        lastTimeOfUsageMap.put(key, System.currentTimeMillis());
    }

    public String getWeakestKey(Cache... cache) {

        String weakestKey = null;
        Long weakestValue = 0L;
        for (Cache cache1 : cache) {
            for (String key : cache1.keySet()) {
                if (weakestKey == null) {
                    weakestKey = key;
                    weakestValue = lastTimeOfUsageMap.get(key);
                } else if (lastTimeOfUsageMap.get(key) > weakestValue) {
                    weakestKey = key;
                    weakestValue = lastTimeOfUsageMap.get(key);
                }
            }
        }
        return weakestKey;
    }

    @Override
    public synchronized void removeKey(String key) {
        lastTimeOfUsageMap.remove(key);
    }
}
