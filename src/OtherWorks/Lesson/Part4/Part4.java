package OtherWorks.Lesson.Part4;

public class Part4 {
    public static void main(String[] args) {
        SampleClass sampleClass = new SampleClass(10, 20, 300);
        SampleClass anotherClass = new SampleClass(55, 77, 999);

        System.out.println("Поле sampleClass int a: " + sampleClass.a);
        System.out.println("Поле anotherClass int a: " + anotherClass.a);

        System.out.println("Статическое поле int с: " + sampleClass.c);
        System.out.println("Статическое поле int с: " + anotherClass.c);
    }
}
class SampleClass {
    int a;
    int b;
    static int c;
    SampleClass(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}
