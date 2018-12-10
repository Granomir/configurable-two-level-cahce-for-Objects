package twolevelсache;

import java.io.Serializable;
import java.util.Objects;

public class Main implements Serializable {
    public static void main(String[] args) throws SpecifiedKeyExistsException {
        TwoLevelCache twoLevelCache = new TwoLevelCacheImpl(2, 2, "LFU");
        MyClass obj1 = new MyClass(1, new MyClass1());
        System.out.println(obj1);
        MyClass obj2 = new MyClass(2, new MyClass1());
        MyClass obj3 = new MyClass(3, new MyClass1());
        twoLevelCache.cacheObject("1", obj1);
        twoLevelCache.cacheObject("4", new Object());
        twoLevelCache.cacheObject("2", obj2);
        twoLevelCache.cacheObject("3", obj3);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        twoLevelCache.clear();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        twoLevelCache.cacheObject("1", new Object());
        twoLevelCache.cacheObject("2", new Object());
        twoLevelCache.cacheObject("3", new Object());
        twoLevelCache.cacheObject("4", new Object());
        System.out.println("\n========================================================");
//        MyClass test = (MyClass) twoLevelCache.getObject("1");
//        System.out.println(test);
//        System.out.println(obj1.equals(test));

    }

    static class MyClass {
        @Override
        public String toString() {
            return "MyClass{" +
                    "myClass1=" + myClass1 +
                    ", i=" + i +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyClass myClass = (MyClass) o;
            boolean a = i == myClass.i;
            boolean b = Objects.equals(myClass1, myClass.myClass1);
            return a && b                    ;
        }

        @Override
        public int hashCode() {
            return Objects.hash(myClass1, i);
        }

        MyClass1 myClass1;

        public MyClass(int i, MyClass1 myClass1) {
            this.myClass1 = myClass1;
            this.i = i;
        }

        int i;
    }

    static class MyClass1 {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyClass1 myClass1 = (MyClass1) o;
            return i == myClass1.i;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i);
        }

        @Override
        public String toString() {
            return "MyClass1{" +
                    "i=" + i +
                    '}';
        }

                MyClass2 myClass2 = new MyClass2(3);
        int i = 2;
    }

    static class MyClass2 {
        int i;

        @Override
        public String toString() {
            return "MyClass2{" +
                    "i=" + i +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyClass2 myClass2 = (MyClass2) o;
            return i == myClass2.i;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i);
        }

        public MyClass2(int i) {
            this.i = i;
        }
    }
}
