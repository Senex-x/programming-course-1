package OtherWorks.Lectures.Presentation1;

public class Part2 {
    public static void main(String[] args) {
        SampleClass sampleClass = new SampleClass();
        System.out.println("Без конструктора: " + sampleClass.a);

        SampleClass anotherClass = new SampleClass(10, 20);
        System.out.println("С конструктором: " + anotherClass.a);
    }
}

class SampleClass {
    int a;
    int b;
    // конструктор, не принимающий аргументов
    SampleClass() {
    }
    // конструктор, принимающий аргументы,
    // для того чтобы записать их во внутренние поля класса
    SampleClass(int a, int b) {
        this.a = a;
        this.b = b;
    }
}
