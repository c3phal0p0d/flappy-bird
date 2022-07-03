/**
 * Interface that allows objects to change their speeds
 */

public interface SpeedChangeable {
    public static final double DEFAULT_SPEED = 5;
    public static final double INCREASE_SPEED_MULTIPLIER = 1.5;
    public static final double DECREASE_SPEED_MULTIPLIER = 1/1.5;

    /**
     * Increases speed of object
     */
    public static void increaseSpeed(){
    }

    /**
     * Decreases speed of object
     */
    public static void decreaseSpeed(){
    }

    /**
     * Resets speed of object to default value
     */
    public static void resetSpeed(){
    }
}
