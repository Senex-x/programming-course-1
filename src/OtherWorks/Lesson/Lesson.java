package OtherWorks.Lesson;

public class Lesson {
    public static void main(String[] args) {
        int a = 10;
        int b;
        b = a;
        // значение просто копируется
        b++;
        a++;
        // эти переменные полностью независимы друг от друга
        System.out.println("a = " + a + " b = " + b);

        int[] firstArray = {10};
        int[] secondArray;
        secondArray = firstArray;
        // непосредственно копируется ссылка на область в памяти, где хранится массив firstArray
        // теперь обе переменные firstArray и secondArray ссылаются на одну и ту же область в памяти,
        // где хранится массив из одного элемента {10}

        firstArray[0]++;
        secondArray[0]++;
        // эти две переменные зависят от одной области в памяти, так как обе хранят ссылки, указывающте в одно и то же место

        System.out.println("firstArray element = " + firstArray[0] + " secondArray element = " + secondArray[0]);

    }
}
