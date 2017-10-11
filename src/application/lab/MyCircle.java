package application.lab;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by user on 11.10.17.
 */
public class MyCircle extends Zveno{


    public MyCircle(float x, float y, float velocity, GraphicsContext gc) {
        super(x, y, velocity, gc);
    }

    @Override
    public void draw() {
        gc.setFill(Color.AQUA);
        gc.fillOval(position.getX(),position.getY(), 30, 30);
    }

    @Override
    public void move(boolean forward) {
        position = position.add(velocity, 0);
    }
}
