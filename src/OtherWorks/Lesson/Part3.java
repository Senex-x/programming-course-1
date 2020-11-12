package OtherWorks.Lesson;

public class Part3 {
    int x = 100;

    void method() {
        int x = 50;
        System.out.println("X уровня метода: " + x);

        System.out.println("Х уровня класса: " + this.x);
    }

    public static void main(String[] args) {
        Part3 instance = new Part3();
        instance.method();
    }
}
