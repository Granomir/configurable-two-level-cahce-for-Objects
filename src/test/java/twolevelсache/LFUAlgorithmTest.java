package twolevelсache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LFUAlgorithmTest {
    TwoLevelCache twoLevelCache = new TwoLevelCacheImpl(1, 1, "LFU");

    @Test
    public void getWeakestKey() throws SpecifiedKeyExistsException {
        twoLevelCache.cacheObject("1", new MyClass1(new MyClass2(1), 2));
        assertNotNull(twoLevelCache.getObject("1"));
        twoLevelCache.cacheObject("2", new MyClass1(new MyClass2(1), 2));
        twoLevelCache.getObject("2");
        twoLevelCache.getObject("2");
        twoLevelCache.getObject("2");
        twoLevelCache.getObject("2");
        twoLevelCache.cacheObject("3", new MyClass1(new MyClass2(1), 2));
        assertNull(twoLevelCache.getObject("1"));
    }
}