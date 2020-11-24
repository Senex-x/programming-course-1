package OtherWorks.Samples;

import java.util.ArrayList;
import java.util.Arrays;

public class InnerClasses2 {

    public static void main(String[] args) {
        ArrayList<A> arrayA = new ArrayList<>(
                Arrays.asList(
                        new A(),
                        new A(),
                        new A()
                )
        );

        B b = new B();

        for (A a : arrayA) {
            b.add(a.createU());
        }

        b.run();

        b.remove(0);
        b.remove(2);
    }
}

interface U {
    void first();

    void second();

    void third();
}

class A {
    U createU() {
        return new U() {
            @Override
            public void first() {
                System.out.println("First invoked");
            }

            @Override
            public void second() {
                System.out.println("Second invoked");
            }

            @Override
            public void third() {
                System.out.println("Third invoked");
            }
        };
    }
}

class B {
    ArrayList<U> arrayU = new ArrayList<>();

    void add(U u) {
        arrayU.add(u);
    }

    void remove(int index) {
        if (index >= 0 && index < arrayU.size())
            arrayU.set(index, null);
    }

    void run() {
        for (U u : arrayU) {
            if (u != null) {
                u.first();
                u.second();
                u.third();
            }
        }
    }
}

