package application.lab.move;

/**
 * Created by user on 14.11.17.
 */
public class MoveNormalModel extends MoveModel {

    public MoveNormalModel(float startPos, float endPos, float maxVelocity) {
        super(startPos, endPos, maxVelocity, .5f);
    }

    @Override
    public void reload(float startPos, float endPos, float maxVelocity) {
        this.maxVelocity = maxVelocity;
        this.startPos = startPos;
        this.endPos = endPos;
        this.distance = 0;
        this.acceleration = maxVelocity / (((endPos - startPos) * this.percentOfAccRoad) / (this.maxVelocity / 2));
        this.moveCount = 0;
    }

    @Override
    public void update(float tick) {
        if (moveCount < 2) {

            if ((distance > (endPos - startPos) * percentOfAccRoad && acceleration > 0) || (distance < (endPos - startPos) * percentOfAccRoad && acceleration < 0)) {
                acceleration *= -1;
            }

            velocity += acceleration * tick / 1000.0f;
            distance += this.getVelocity();
            if (velocity == 0.0f) {
                moveCount++;
            }
        }

    }
}
