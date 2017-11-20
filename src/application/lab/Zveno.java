package application.lab;

import application.Main;
import application.lab.move.MoveModel;
import application.view.ParametersController;
import application.lab.move.MoveNormalModel;
import application.lab.move.MoveSimpleModel;
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
    int currentMoveModeId;


    MoveModel moveModel;


    /**
     * @param x        - start
     * @param y        - end
     * @param velocity
     * @param width
     * @param height
     * @param gc
     */
    Zveno(float x, float y, float startParam, float endParam, float velocity, double width, double height, GraphicsContext gc) {
        this.position = new Point2D(x, y);
        this.param = new Point2D(startParam, endParam);
        this.gc = gc;
        this.width = width;
        this.height = height;
        distance = startParam;
//        moveModel = new MoveNormalModel(startParam, endParam, velocity);
        moveModel = new MoveSimpleModel(startParam, endParam, velocity);
        currentMoveModeId = 1;
    }

    public void reloadParams(float startParam, float endParam, float velocity, double width, double height) {
//        this.position = new Point2D(x, y);
        this.param = new Point2D(startParam, endParam);
        this.width = width;
        this.height = height;
        distance = startParam;
        moveModel.reload(startParam, endParam, velocity);
    }

    public abstract void draw();

    public void move(long period) {
        moveModel.update(period);
    }

    public abstract void reset();

    public abstract float getCurrentPosition();

    public float getAcceleration() {
        return moveModel.getAcceleration();
    }

    public float getVelocity() {
        return (float)moveModel.getVelocity() * 1000 / Main.PERIOD_IN_MS;
    }

    public void setMoveModel(int mode) {
        switch (mode) {
            case ParametersController.MODE_1:
                this.moveModel = new MoveSimpleModel((float)this.param.getX(), (float) this.param.getY(), (float)this.velocity);
                break;
            case ParametersController.MODE_2:
                this.moveModel = new MoveNormalModel((float)this.param.getX(), (float) this.param.getY(), (float)this.velocity);
                break;
        }
        currentMoveModeId = mode;
    }

    public boolean canMove() {
        return moveModel.getMoveCount() < 2;
    }

    public int getCurrentMoveModeId() {
        return currentMoveModeId;
    }
}
