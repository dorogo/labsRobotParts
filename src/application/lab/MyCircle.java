package application.lab;

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
        super(x, y, startParam, endParam, velocity, width, height, gc);
        this.radius = width / 2;
        this.lineStart = new Point2D(position.getX() + radius, position.getY() + radius);
        this.lineEnd = new Point2D(lineStart.getX(), lineStart.getY() - radius);
        this.angle = 0;
        this.velocity = (float) (velocity * Math.PI / 180.0f);
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
        distance +=  angle * 180 / Math.PI;
        angle = moveRatio * velocity/100;
        rx = lineEnd.getX() - lineStart.getX();
        ry = lineEnd.getY() - lineStart.getY();
        c = Math.cos(angle);
        s = Math.sin(angle);
        lineEnd = new Point2D(lineStart.getX() + rx * c - ry * s, lineStart.getY() + rx * s + ry * c);
    }

    private double rx, ry, x0, x, y0,y, x1,y1, c, s;

    @Override
    public void reloadParams(float x, float y, float velocity, double width, double height) {
        super.reloadParams(x, y, velocity, width, height);
        this.radius = width / 2;
        this.lineStart = new Point2D(position.getX() + radius, position.getY() + radius);
        this.lineEnd = new Point2D(lineStart.getX(), lineStart.getY() - radius);
        this.angle = 0;
        this.velocity = (float) (velocity * Math.PI / 180.0f);
    }

    @Override
    public void reset() {
        lineEnd = new Point2D(lineStart.getX(), lineStart.getY() - radius);
    }
}
