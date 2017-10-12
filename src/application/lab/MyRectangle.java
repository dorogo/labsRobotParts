package application.lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by user on 11.10.17.
 */
public class MyRectangle extends Zveno{


    public static final float DEFAULT_Y = 60;

    public MyRectangle(float x, float y, float startParam, float endParam, float velocity, double width, double height, GraphicsContext gc) {
        super(x, y, startParam, endParam, velocity, width, height, gc);
    }

    @Override
    public void draw() {
        gc.setFill(Color.GREEN);
        gc.fillRect(position.getX(), position.getY(), width, height);
    }

    private int moveRatio = 1;
    @Override
    public void move() {
        if (distance >= param.getY() && moveRatio == 1 || distance <= param.getX() && moveRatio == -1) {
            moveRatio *= -1;
        }
        distance += moveRatio * velocity;
        position = position.add(moveRatio * velocity, 0);
//                else position = position.subtract(velocity, 0);
    }

    @Override
    public void reset() {
        this.position = new Point2D(0, position.getY());
    }
}
