package twolevelсache;

import java.util.Objects;

public class MyClass2 {
    private int i;

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

    MyClass2(int i) {
        this.i = i;
    }
}
