package application.lab;

import application.Main;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by user on 11.10.17.
 */
public class MyCircle extends Zveno{


    public static final int DEFAULT_X = 60;
    public static final int DEFAULT_Y = 60;
    private Point2D lineStart;
    private Point2D lineEnd;
    private double radius;
    private float angle;

    public MyCircle(float x, float y, float startParam, float endParam, float velocity, double width, double height, GraphicsContext gc) {
        super(x, y, startParam, endParam, velocity/ ( 1000 / Main.PERIOD_IN_MS), width, height, gc);
        this.radius = width / 2;
        this.lineStart = new Point2D(position.getX() + radius, position.getY() + radius);
        this.lineEnd = new Point2D(lineStart.getX(), lineStart.getY() - radius);
        this.angle = 0;
    }

    @Override
    public void draw() {
        gc.setFill(Color.AQUA);
        gc.fillOval(position.getX(),position.getY(), width, height);
        gc.strokeLine(lineStart.getX(), lineStart.getY(), lineEnd.getX(), lineEnd.getY());
    }

    private int moveRatio = 1;
    @Override
    public void move() {
        if (distance >= param.getY() && moveRatio == 1 || distance <= param.getX() && moveRatio == -1) {
            moveRatio *= -1;
        }
        angle = moveRatio * velocity;
        distance += angle;
        this.lineEnd = getPointByAngle(angle, lineStart, lineEnd);
    }

    private Point2D getPointByAngle(double a, Point2D center, Point2D end){
        double rx, ry, c, s;
        a = a * Math.PI / 180.0f;
        rx = end.getX() - center.getX();
        ry = end.getY() - center.getY();
        c = Math.cos(a);
        s = Math.sin(a);
        return new Point2D(center.getX() + rx * c - ry * s, center.getY() + rx * s + ry * c);
    }

    @Override
    public void reloadParams(float x, float y, float velocity, double width, double height) {
        super.reloadParams(x, y, velocity/ ( 1000 / Main.PERIOD_IN_MS), width, height);
        this.radius = width / 2;
        this.lineStart = new Point2D(position.getX() + radius, position.getY() + radius);
        this.lineEnd = getPointByAngle((float) param.getX(), lineStart, new Point2D(lineStart.getX(), lineStart.getY() - radius));
        this.angle = 0;
    }

    @Override
    public void reset() {
        this.lineEnd = getPointByAngle((float) param.getX(), lineStart, new Point2D(lineStart.getX(), lineStart.getY() - radius));
    }
}
