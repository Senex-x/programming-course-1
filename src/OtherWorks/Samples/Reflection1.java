package OtherWorks.Samples;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Reflection1 {
    public static void main(String[] args) {
        Class<Reflective> ref = Reflective.class;
        Reflective reflective = new Reflective();

        Method method = Arrays.stream(ref.getDeclaredMethods())
                .filter(m -> m.getName().equals("privateIntMethod"))
                .collect(Collectors.toList()).get(0);
        method.setAccessible(true);

        try {
            System.out.println(method.invoke(reflective, 10));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        try {
            ref.getConstructor().newInstance();
        } catch (NoSuchMethodException |
                IllegalAccessException |
                InstantiationException |
                InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static class Reflective {
        private int value;
        private String data;

        public Reflective() {}

        void voidMethod() {

        }

        int intMethod(int n) {
            return n;
        }

        private int privateIntMethod(int n) {
            return n;
        }

        private String privateSiringMethod(String s) {
            return s;
        }
    }
}
