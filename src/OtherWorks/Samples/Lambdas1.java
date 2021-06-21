package OtherWorks.Samples;

public class Lambdas1 {
    static class Test {
        public static void main(String[] args) {
            Lambdas1.OneMethod one = new Lambdas1.OneMethod() {
                @Override
                public void display(String string) {
                    System.out.println(string);
                }
            };

            Lambdas1.OneMethod oneLambda = string -> System.out.println(string);

            Lambdas1.TwoMethods two = new Lambdas1.TwoMethods() {
                @Override
                public void display(String string, int a) {
                    System.out.println(string);
                }

                @Override
                public void doubleDisplay(String string) {
                    System.out.println(string + " " + string);
                }
            };

            // Multiple methods are not allowed
            // Lambdas.TwoMethods twoLambda = (String string, int a) -> System.out.println(string);

            Lambdas1.SampleFunctionalInterface inter = n -> n + 10;
        }
    }

    @FunctionalInterface
    private interface OneMethod {
        void display(String string);
    }

    private interface TwoMethods {
        void display(String string, int a);

        void doubleDisplay(String string);
    }

    @FunctionalInterface
    private interface SampleFunctionalInterface {
        int function(int n);
    }
}




