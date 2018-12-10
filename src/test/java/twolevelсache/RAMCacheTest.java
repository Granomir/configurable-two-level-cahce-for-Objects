package twolevelсache;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RAMCacheTest {
    private RAMCache ramCache = new RAMCache(2);

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        ramCache.clear();
    }

    @Test
    public void cacheObject() {
        Object obj = new Object();
        ramCache.cacheObject("1", obj);
        Assert.assertEquals(obj, ramCache.getObject("1"));
    }

    @Test
    public void getObject() {
        Object obj = new Object();
        ramCache.cacheObject("1", obj);
        Assert.assertEquals(obj, ramCache.getObject("1"));
    }

    @Test
    public void containsObject() {
        Object obj = new Object();
        ramCache.cacheObject("1", obj);
        Assert.assertTrue(ramCache.containsObject("1"));
    }

    @Test
    public void removeObject() {
        Object obj = new Object();
        ramCache.cacheObject("1", obj);
        Assert.assertTrue(ramCache.containsObject("1"));
        ramCache.removeObject("1");
        Assert.assertFalse(ramCache.containsObject("1"));
    }

    @Test
    public void deleteObject() {
        Object obj = new Object();
        ramCache.cacheObject("1", obj);
        Assert.assertTrue(ramCache.containsObject("1"));
        Assert.assertTrue(ramCache.deleteObject("1"));
        Assert.assertFalse(ramCache.containsObject("1"));
    }

    @Test
    public void isNotFull() {
        ramCache.cacheObject("1", new Object());
        Assert.assertTrue(ramCache.isNotFull());
        ramCache.cacheObject("2", new Object());
        Assert.assertFalse(ramCache.isNotFull());
    }

    @Test
    public void clear() {
        ramCache.cacheObject("1", new Object());
        Assert.assertEquals(1, ramCache.getSize());
        ramCache.clear();
        Assert.assertEquals(0, ramCache.getSize());
    }
}