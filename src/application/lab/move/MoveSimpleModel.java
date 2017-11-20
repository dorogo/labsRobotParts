package application.lab.move;

/**
 * Created by user on 20.11.17.
 */
public class MoveSimpleModel extends MoveModel {


    public MoveSimpleModel(float startPos, float endPos, float maxVelocity) {
        super(startPos, endPos, maxVelocity, 0);
        this.velocity = maxVelocity;
    }

    private double prevVel = 0;

    @Override
    public void reload(float startPos, float endPos, float maxVelocity) {
        this.velocity = maxVelocity;
        this.startPos = startPos;
        this.endPos = endPos;
        this.distance = 0;
        this.acceleration = 0;
        this.moveCount = 0;
    }

    @Override
    public void update(float tick) {
        distance += this.getVelocity();
        if (distance >= (endPos - startPos) || distance <= 0) {
            velocity *= -1;
            moveCount++;
        }
        prevVel = velocity;

    }
}
