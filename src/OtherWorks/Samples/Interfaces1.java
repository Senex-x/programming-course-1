package OtherWorks.Samples;

public class Interfaces1 {
    public static void main(String[] args) {
        SampleInterface sampleInterface = new SampleInterface() {
            @Override
            public void sampleMethod() {
                System.out.println("Sample method invoke");
            }
        };

        SampleInterface sampleClass = new SampleClass();

       // SampleInterface interfaceInstance = new SampleInterface();
    }
}


interface SampleInterface {
    void sampleMethod();
}

class SampleClass implements SampleInterface{
    @Override
    public void sampleMethod() {
        System.out.println("Sample method invoke");
    }
}
