package application.lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by user on 11.10.17.
 */
public abstract class Zveno {
    Point2D position;
    float velocity;
    GraphicsContext gc;


    /**
     *
     * @param x
     * @param y
     * @param velocity - px/ms
     * @param gc
     */
    Zveno (float x, float y, float velocity, GraphicsContext gc) {
        this.position = new Point2D(x, y);
        this.velocity = velocity;
        this.gc = gc;
    }

    public void reset(){
        this.position = new Point2D(0, position.getY());
    }

    public abstract void draw();
    public abstract void move(boolean forward);

}
