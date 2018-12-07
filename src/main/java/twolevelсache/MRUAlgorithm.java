package twolevelсache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MRUAlgorithm implements CachingAlgorithm {

    private Map<String, Long> lastTimeOfUsageMap;

    private final Logger logger = LoggerFactory.getLogger(TwoLevelCacheImpl.class);

    MRUAlgorithm() {
        lastTimeOfUsageMap = new HashMap<>();
    }

    @Override
    public void grabKey(String key) {
        lastTimeOfUsageMap.put(key, System.currentTimeMillis());
    }

    public String getWeakestKey(Cache cache) {

        String weakestKey = null;
        Long weakestValue = 0L;
        for (String key : cache.keySet()) {
            if (weakestKey == null) {
                weakestKey = key;
                weakestValue = lastTimeOfUsageMap.get(key);
            } else if (lastTimeOfUsageMap.get(key) > weakestValue) {
                weakestKey = key;
                weakestValue = lastTimeOfUsageMap.get(key);
            }
        }
        return weakestKey;
    }

    @Override
    public void removeKey(String key) {
        lastTimeOfUsageMap.remove(key);
    }

    @Override
    public String getName() {
        return "MRU";
    }
}
