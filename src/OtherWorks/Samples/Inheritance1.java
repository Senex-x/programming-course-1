package OtherWorks.Samples;

public class Inheritance1 {
    public static void main(String[] args) {
        new Child();
    }
}

class Parent {
    Parent() {
        System.out.println("Parent default constructor call");
    }

    Parent(String tag) {
        System.out.println("Parent overloaded constructor call");
    }
}

class Child extends Parent{
    Child() {
        super("tag");
        System.out.println("Child default constructor call");
    }

    Child(String tag) {
        super(tag);
        System.out.println("Child overloaded constructor call");
    }
}

