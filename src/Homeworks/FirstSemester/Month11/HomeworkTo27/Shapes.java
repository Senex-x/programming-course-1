package Homeworks.FirstSemester.Month11.HomeworkTo27;

import java.util.ArrayList;
import java.util.Arrays;

abstract class Shape {
    protected float area;

    protected abstract float getArea();

    protected static float sq(float value) {
        return value * value;
    }

    @Override
    public abstract String toString();
}

class Circle extends Shape {
    private float radius;

    public Circle(float radius) {
        this.radius = radius;
        area = (float) Math.PI * sq(radius);
    }

    @Override
    public float getArea() {
        return area;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "area=" + area +
                ", radius=" + radius +
                '}';
    }
}

class Rectangle extends Shape {
    private float height;
    private float width;

    public Rectangle(float height, float width) {
        this.height = height;
        this.width = width;
        area = width * height;
    }

    @Override
    public float getArea() {
        return area;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "area=" + area +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}

class Triangle extends Shape {
    private float height;
    private float edgeBase;
    private float edgeLeft;
    private float edgeRight;
    private float alpha;
    private float sinAlpha;
    private float cosAlpha;

    private Triangle() {
    }

    static Triangle byEdges(float edgeBase, float edgeLeft, float edgeRight) {
        Triangle triangle = new Triangle();
        triangle.edgeBase = edgeBase;
        triangle.edgeLeft = edgeLeft;
        triangle.edgeRight = edgeRight;

        float p = (edgeBase + edgeLeft + edgeRight ) / 2;
        triangle.area = (float) Math.sqrt(p * (p - edgeBase) * (p - edgeLeft) * (p-edgeRight));

        triangle.height = 2 * triangle.area / edgeBase;

        triangle.sinAlpha = triangle.height / edgeLeft;
        triangle.cosAlpha = (float) Math.sqrt(sq(edgeLeft) - sq(triangle.height)) / edgeLeft;
        triangle.alpha = (float) Math.toDegrees(Math.asin(triangle.sinAlpha));

        return triangle;
    }

    static Triangle byAngle(float angle, float edgeBase, float edgeLeft) {
        Triangle triangle = new Triangle();
        triangle.alpha = angle;
        triangle.edgeBase = edgeBase;
        triangle.edgeLeft = edgeLeft;

        triangle.sinAlpha = (float) Math.sin(Math.toRadians(angle));
        triangle.cosAlpha = (float) Math.cos(Math.toRadians(angle));

        triangle.height = edgeLeft * triangle.sinAlpha;

        triangle.area = triangle.height * edgeBase / 2;

        triangle.edgeRight = (float) Math.sqrt(
                sq(edgeBase) + sq(edgeLeft) - 2 * edgeBase * edgeLeft * triangle.cosAlpha
        );

        return triangle;
    }

    @Override
    public float getArea() {
        return area;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "area=" + area +
                ", height=" + height +
                ", edgeBase=" + edgeBase +
                ", edgeLeft=" + edgeLeft +
                ", edgeRight=" + edgeRight +
                ", alpha=" + alpha +
                ", sinAlpha=" + sinAlpha +
                ", cosAlpha=" + cosAlpha +
                '}';
    }
}

class ShapesTest {
    public static void main(String[] args) {
        ArrayList<Shape> shapes = new ArrayList<>(
                Arrays.asList(
                        Triangle.byAngle(45, 3, 4.24f),
                        Triangle.byEdges(3,4.24f,3),
                        new Rectangle(134.2344f, 25.23453f),
                        new Circle(14.2343f)
                )
        );

        for (Shape shape : shapes) {
            System.out.println(
                    shape
            );
        }
    }
}
