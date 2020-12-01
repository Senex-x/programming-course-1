package OtherWorks.Samples;

public class Enums1 {
    static Sample sample;

    public static void main(String[] args) {
        sample = new Sample(10);
        Example first = Example.FIRST;
        System.out.println(first.content.a);
        sample.a = 99;
        System.out.println(first.content.a);
    }

    static class Sample {
        int a;

        public Sample(int a) {
            this.a = a;
        }
    }

    private enum Example {
        FIRST(sample);

        Sample content;

        Example(Sample s) {
            content = s;
        }


    }
}


