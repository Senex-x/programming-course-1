package OtherWorks.Samples;

public class InnerClasses1 {
    public static void main(String[] args) {
        Parent.Inner inner = new Parent().new Inner();
        // private access
        // int a = inner.innerField;
    }
}

class Parent {
    private int parentField;

    Parent() {
        Inner inner = new Inner();
        parentField = inner.innerField;
    }

    class Inner {
        private int innerField;

        Inner() {
            innerField = parentField;
        }
    }

    static class StaticInner {

    }
}
