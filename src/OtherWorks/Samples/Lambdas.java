package OtherWorks.Samples;

public class Lambdas {
    static class Test {
        public static void main(String[] args) {
            Lambdas test = new Lambdas();

            Lambdas.OneMethod one = new Lambdas.OneMethod() {
                @Override
                public void display(String string) {
                    System.out.println(string);
                }
            };

            Lambdas.OneMethod oneLambda = string -> System.out.println(string);

            Lambdas.TwoMethods two = new Lambdas.TwoMethods() {
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
        }
    }

    private interface OneMethod {
        void display(String string);
    }

    private interface TwoMethods {
        void display(String string, int a);

        void doubleDisplay(String string);
    }
}




