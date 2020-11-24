package Homeworks.Month11.HomeworkTo06;

import Methods.Methods;

class VectorTest {
    public static void main(String[] args) {
        Vector2D v = new Vector2D(
                Methods.getRandDouble(10),
                Methods.getRandDouble(10)
        );
        Vector2D c = new Vector2D(
                Methods.getRandDouble(10),
                Methods.getRandDouble(10)
        );

        System.out.println("v1: " + v.toString());
        System.out.println("v2: " + c.toString());

        v.add(c);
        System.out.println("v1 + v2");
        System.out.println("v1: " + v.toString());

        c.sub(v);
        System.out.println("v2 - v1");
        System.out.println("v2: " + c.toString());

        c.multiply(Methods.getRandInt(1, 6));
        System.out.println("v2: " + c.toString());
    }
}

class Vector2D {
    private double x;
    private double y;

    Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    void add(Vector2D vector) {
        System.out.println("\nSumming up vectors...");
        x = x + vector.x;
        y = y + vector.y;
    }

    void sub(Vector2D vector) {
        System.out.println("\nSubtracting vectors...");
        x = x - vector.x;
        y = y - vector.y;
    }

    void multiply(double factor) {
        System.out.println("\nMultiplying vector by " + factor + "...");
        x = x * factor;
        y = y * factor;
    }

    @Override
    public String toString() {
        return "Vector2D {" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}


