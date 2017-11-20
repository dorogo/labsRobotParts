package application.lab;

import application.Main;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.text.DecimalFormat;

/**
 * Created by user on 11.10.17.
 */
public class MyRectangle extends Zveno {


    public static final float DEFAULT_Y = 60;

    public MyRectangle(float x, float y, float startParam, float endParam, float velocity, double width, double height, GraphicsContext gc) {
        super(x, y, startParam, endParam, velocity, width, height, gc);
        position = new Point2D(startParam, DEFAULT_Y);
    }

    @Override
    public void draw() {
        gc.setFill(Color.GREEN);
        gc.fillRect(position.getX(), position.getY(), width, height);
    }

    @Override
    public void move(long period) {
        super.move(period);
        distance += this.moveModel.getVelocity();
        position = position.add(this.moveModel.getVelocity(), 0);
    }

    @Override
    public void reloadParams(float startParam, float endParam, float velocity, double width, double height) {
        super.reloadParams(startParam, endParam, velocity, width, height);
        this.position = new Point2D(startParam, DEFAULT_Y);
    }

    @Override
    public void reset() {
        this.position = new Point2D(param.getX(), DEFAULT_Y);
        this.moveModel.reset();
    }

    @Override
    public float getCurrentPosition() {
        return (float) position.getX();
    }
}
