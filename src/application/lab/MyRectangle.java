package application.lab;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by user on 11.10.17.
 */
public class MyRectangle extends Zveno{

    public MyRectangle(float x, float y, float velocity, GraphicsContext gc) {
        super(x, y, velocity, gc);
    }


    @Override
    public void draw() {
        gc.setFill(Color.GREEN);
        gc.fillRect(position.getX(), position.getY(), 30, 30);
    }

    @Override
    public void move(boolean forward) {
        if (forward) position = position.add(velocity, 0);
                else position = position.subtract(velocity, 0);
    }
}
