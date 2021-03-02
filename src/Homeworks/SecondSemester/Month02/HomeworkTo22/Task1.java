package Homeworks.SecondSemester.Month02.HomeworkTo22;

public class Task1 {
    public static void main(String[] args) {
        FourTuple<Integer, Integer, Integer, Integer> fourTuple =
                new FourTuple<>(1, 2, 3, 4);
        System.out.println(fourTuple.a);
    }

    static class ThreeTuple <A, B, C> {
        A a;
        B b;
        C c;
        public ThreeTuple(A a, B b, C c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    static class FourTuple <A, B, C, D> extends ThreeTuple <A, B, C> {
        D d;
        public FourTuple(A a, B b, C c, D d) {
            super(a, b, c);
            this.d = d;
        }
    }
}

