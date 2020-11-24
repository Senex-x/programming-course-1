package Homeworks.Month11.HomeworkTo27;

import java.util.ArrayList;
import java.util.Arrays;

abstract class Shape {
    abstract float getArea();
}

class Circle extends Shape {
    private float radius;

    public Circle(float radius) {
        this.radius = radius;
    }

    @Override
    float getArea() {
        return (float) Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    private float height;
    private float width;

    public Rectangle(float height, float width) {
        this.height = height;
        this.width = width;
    }

    @Override
    float getArea() {
        return width * height;
    }
}

class Triangle extends Shape {
    private float edgeBase;
    private float height;
    private float edgeLeft;
    private float edgeRight;
    private float alpha;
    private float sinAlpha;
    private float cosAlpha;

    private Triangle() {

    }

    private static Triangle byHeight(float edgeBase, float edgeLeft, float height) {
        Triangle triangle = new Triangle();
        triangle.edgeBase = edgeBase;
        triangle.edgeLeft = edgeLeft;
        triangle.height = height;

        triangle.sinAlpha = height / edgeLeft;
        triangle.cosAlpha = (float) (
                Math.sqrt(edgeLeft * edgeLeft - height * height)
                        / edgeLeft
        );
        //triangle.alpha = (float)Math.asin(triangle.sinAlpha);

        triangle.edgeRight = (float) Math.sqrt(
                sq(edgeLeft) + sq(edgeBase)
                        - 2 * edgeLeft * edgeBase * triangle.cosAlpha
        );

        return triangle;
    }

    static Triangle byEdges(float edgeBase, float edgeLeft, float edgeRight) {
        Triangle triangle = new Triangle();
        triangle.edgeBase = edgeBase;
        triangle.edgeLeft = edgeLeft;
        triangle.edgeRight = edgeRight;
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
        return triangle;
    }

    static float sq(float value) {
        return value * value;
    }


    @Override
    float getArea() {
        return edgeBase * height;
    }
}

class ShapesTest {
    public static void main(String[] args) {
        ArrayList<Shape> shapes = new ArrayList<>(
                Arrays.asList(
                        new Circle(10),
                        new Rectangle(10, 20),
                        Triangle.byAngle(45, 3, 4.24f)
                )
        );

        for (Shape shape : shapes) {
            System.out.println(
                    shape.getClass().getSimpleName() +
                            " area is " + shape.getArea()
            );
        }
    }
}
