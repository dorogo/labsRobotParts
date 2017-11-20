package application.lab.move;

import application.Main;

/**
 * Created by user on 14.11.17.
 */
public abstract class MoveModel {
    float startPos;
    float endPos;
    float acceleration;
    double velocity;
    float maxVelocity;
    float percentOfAccRoad; //процент пути для разгона/тормжения
    double distance;
    int moveCount = 0;

    MoveModel(float startPos, float endPos, float maxVelocity, float percentOfAccRoad) {
        this.velocity = 0;
        this.maxVelocity = maxVelocity;
        this.startPos = startPos;
        this.endPos = endPos;
        this.percentOfAccRoad = percentOfAccRoad;
        this.acceleration = maxVelocity / (((endPos - startPos) * this.percentOfAccRoad) / (this.maxVelocity / 2));
        this.distance = 0;
    }

    public abstract void reload(float startPos, float endPos, float maxVelocity);

    public abstract void update(float tick);

    public float getAcceleration() {
        return acceleration;
    }

    public double getVelocity() {
        return velocity / (1000 / Main.PERIOD_IN_MS);
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void reset() {
        this.moveCount = 0;
        this.velocity = 0;
        this.distance = 0;
    }
}
