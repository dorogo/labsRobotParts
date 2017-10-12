package application.lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by user on 11.10.17.
 */
public abstract class Zveno {
    Point2D position;
    Point2D param;
    double width;
    double height;
    float velocity;
    float distance;
    GraphicsContext gc;


    /**
     *
     * @param x - start
     * @param y - end
     * @param velocity
     * @param width
     * @param height
     * @param gc
     */
    Zveno (float x, float y, float startParam, float endParam, float velocity, double width, double height, GraphicsContext gc) {
        this.position = new Point2D(x, y);
        this.param = new Point2D(startParam, endParam);
        this.velocity = velocity;
        this.gc = gc;
        this.width = width;
        this.height = height;
        distance = startParam;
    }

    public void reloadParams (float startParam, float endParam, float velocity, double width, double height) {
//        this.position = new Point2D(x, y);
        this.param = new Point2D(startParam, endParam);
        this.velocity = velocity;
        this.width = width;
        this.height = height;
        distance = startParam;
    }

    public abstract void draw();
    public abstract void move();
    public abstract void reset();

}
