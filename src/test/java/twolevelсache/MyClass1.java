package twolevelсache;

import java.util.Objects;

public class MyClass1 {
    private MyClass2 myClass2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyClass1 myClass1 = (MyClass1) o;
        return i == myClass1.i &&
                Objects.equals(myClass2, myClass1.myClass2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myClass2, i);
    }

    private int i;

    MyClass1(MyClass2 myClass2, int i) {
        this.myClass2 = myClass2;
        this.i = i;
    }
}
