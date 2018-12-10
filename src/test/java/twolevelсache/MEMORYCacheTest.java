package twolevelсache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MEMORYCacheTest {
    private MEMORYCache memoryCache = new MEMORYCache(2);

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        memoryCache.clear();
    }

    @Test
    public void cacheObject() {
        MyClass1 obj = new MyClass1(new MyClass2(2), 1);
        memoryCache.cacheObject("1", obj);
        MyClass1 obj1 = (MyClass1) memoryCache.getObject("1");
        assertEquals(obj, obj1);
    }

    @Test
    public void getObject() {
        MyClass1 obj = new MyClass1(new MyClass2(2), 1);
        memoryCache.cacheObject("1", obj);
        MyClass1 obj1 = (MyClass1) memoryCache.getObject("1");
        assertEquals(obj, obj1);
    }

    @Test
    public void containsObject() {
        Object obj = new Object();
        memoryCache.cacheObject("1", obj);
        assertTrue(memoryCache.containsObject("1"));
    }

    @Test
    public void removeObject() {
        Object obj = new Object();
        memoryCache.cacheObject("1", obj);
        assertTrue(memoryCache.containsObject("1"));
        memoryCache.removeObject("1");
        assertFalse(memoryCache.containsObject("1"));
    }

    @Test
    public void deleteObject() {
        Object obj = new Object();
        memoryCache.cacheObject("1", obj);
        assertTrue(memoryCache.containsObject("1"));
        assertTrue(memoryCache.deleteObject("1"));
        assertFalse(memoryCache.containsObject("1"));
    }

    @Test
    public void isNotFull() {
        memoryCache.cacheObject("1", new Object());
        assertTrue(memoryCache.isNotFull());
        memoryCache.cacheObject("2", new Object());
        assertFalse(memoryCache.isNotFull());
    }

    @Test
    public void clear() {
        memoryCache.cacheObject("1", new Object());
        assertEquals(1, memoryCache.getSize());
        memoryCache.clear();
        assertEquals(0, memoryCache.getSize());
    }
}