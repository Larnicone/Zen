package zenproject.meditation.android.sketch.painting.flowers.branch;

import com.juankysoriano.rainbow.core.matrix.RVector;
import com.juankysoriano.rainbow.utils.RainbowMath;

import zenproject.meditation.android.ContextRetriever;
import zenproject.meditation.android.R;

/**
 * TODO
 * This class could be much better written, attention to the constructors.
 * We could avoid writing so many sh*t there.
 */
public class Branch {
    private static final float MIN_RADIUS = ContextRetriever.INSTANCE.getResources().getDimension(R.dimen.branch_min_radius);
    private static final float MIN_RADIUS_TO_BLOOM = ContextRetriever.INSTANCE.getResources().getDimension(R.dimen.branch_min_bloom_radius);
    private static final float DEFAULT_RADIUS = ContextRetriever.INSTANCE.getResources().getDimension(R.dimen.branch_default_radius);
    private static final float MIN_STEP = 0.05f;
    private static final float MAX_STEP = 0.25f;
    private static final float MIN_RADIUS_FACTOR = 0.9f;
    private static final float MAX_RADIUS_FACTOR = 1.1f;
    private static final float SHRINK = RainbowMath.random(0.94f, 0.96f);
    private float angle;

    private float radius;
    private RVector position;
    private final RVector previousPosition;
    private final float step;

    Branch(RVector position, float angle, float radius, float step) {
        this.position = new RVector(position.x, position.y);
        this.previousPosition = new RVector(this.position.x, this.position.y);
        this.angle = angle;
        this.step = step;
        this.radius = radius * RainbowMath.random(MIN_RADIUS_FACTOR, MAX_RADIUS_FACTOR);
    }

    private static float generateRandomStep(Branch branch) {
        float randomStep = RainbowMath.random(MIN_STEP, MAX_STEP);
        return branch.step >= 0 ? -randomStep : randomStep;
    }

    static Branch createFrom(Branch branch) {
        return new Branch(branch.position,
                branch.angle, branch.radius, generateRandomStep(branch));
    }

    public static Branch createAt(float x, float y) {
        float angle = RainbowMath.random(-RainbowMath.PI, RainbowMath.PI);
        RVector pos = new RVector(x, y);

        return new Branch(pos, angle, DEFAULT_RADIUS, 0);
    }

    boolean isDead() {
        return RainbowMath.abs(radius) < MIN_RADIUS;
    }

    boolean canBloom() {
        return RainbowMath.abs(radius) > MIN_RADIUS_TO_BLOOM;
    }

    void update() {
        backupPosition();
        updatePosition();
        updateAngle();
        updateRadius();
    }

    private void backupPosition() {
        previousPosition.x = position.x;
        previousPosition.y = position.y;
    }

    private void updatePosition() {
        position.x += radius * RainbowMath.cos(angle);
        position.y += radius * RainbowMath.sin(angle);
    }

    private void updateAngle() {
        angle += step;
    }

    private void updateRadius() {
        radius *= SHRINK;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    float getOldX() {
        return previousPosition.x;
    }

    float getOldY() {
        return previousPosition.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Branch)) {
            return false;
        }

        Branch branch = (Branch) o;

        return previousPosition.equals(branch.previousPosition) && position.equals(branch.position);

    }

    @Override
    public int hashCode() {
        int result = position.hashCode();
        result = 31 * result + previousPosition.hashCode();
        return result;
    }
}
