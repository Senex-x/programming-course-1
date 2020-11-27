package OtherWorks.Samples;

public class InnerClasses1 {
    public static void main(String[] args) {
        ParentClass.Inner inner = new ParentClass().new Inner();
        // private access
        // int a = inner.innerField;
    }
}

class ParentClass {
    private int parentField;

    ParentClass() {
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
