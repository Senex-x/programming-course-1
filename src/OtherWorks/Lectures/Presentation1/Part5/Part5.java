package OtherWorks.Lectures.Presentation1.Part5;

public class Part5 {
    public static void main(String[] args) {
        SampleClass sampleClass = new SampleClass();
        int[] a = new int[10];
        // эквивалентные записи
        String str = new String("example");
        String string = "example";
        // вызываем метод у экземпляра класса String
        string = string.toUpperCase();
        System.out.println("Строка после вызова метода: " + string);
        // вызываем метод у экземпляра класса SampleClass
        int result = sampleClass.count();
        System.out.println("Результат вызова метода в экземпляре SampleClass: " + result);
    }
}
class SampleClass {
    int a = 10;
    int b = 20;
    // метод существует на уровне экземпляра
    // соответственно для разных экземпляров одного и того же класса
    // вывод может быть разным
    int count() {
        int c = a + b;
        return c;
    }
}



