package twolevelсache;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwoLevelCacheImplTest {
    private TwoLevelCache twoLevelCache = new TwoLevelCacheImpl(2, 2, "LFU");

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        twoLevelCache.clear();
    }

    @Test
    public void cacheObject() {
        MyClass1 obj1 = new MyClass1(new MyClass2(2), 1);
        MyClass1 obj2 = new MyClass1(new MyClass2(3), 2);
        twoLevelCache.cacheObject("1", obj1);
        MyClass1 obj3 = (MyClass1) twoLevelCache.getObject("1");
        assertEquals(obj1, obj3);

        twoLevelCache.cacheObject("1", obj2);
        MyClass1 obj4 = (MyClass1) twoLevelCache.getObject("1");
        assertEquals(obj2, obj4);
    }

    @Test
    public void getObject() {
        Object obj = new Object();
        twoLevelCache.cacheObject("1", obj);
        Assert.assertEquals(obj, twoLevelCache.getObject("1"));
    }

    @Test
    public void clear() {
        twoLevelCache.cacheObject("1", new Object());
        Assert.assertEquals(1, twoLevelCache.getSize());
        twoLevelCache.clear();
        Assert.assertEquals(0, twoLevelCache.getSize());
    }
}