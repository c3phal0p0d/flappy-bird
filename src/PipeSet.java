import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Rectangle;
import bagel.Window;
import bagel.util.Point;

/**
 * Class that implements a generic pipe set
 */

public abstract class PipeSet implements Destroyable, SpeedChangeable{
    protected static final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI);

    protected Image pipeImage;
    private double gapY;
    protected double topPipeY;
    protected double bottomPipeY;
    protected double pipeX;
    private boolean isDestroyed;
    private boolean isPassed;

    private static double speed = DEFAULT_SPEED;

    protected PipeSet(double gapY){
        this.gapY = gapY;
        topPipeY = gapY - Window.getHeight()/2.0;
        bottomPipeY = Window.getHeight()/2.0 + gapY + Level.PIPE_GAP;
        pipeX = Window.getWidth();
        isDestroyed = false;
        isPassed = false;
    }

    /**
     * Performs state update of pipe set
     */
    public void update(){
        renderPipeSet();
        pipeX -= speed;
    }

    /**
     * Renders pipe set on screen
     */
    public void renderPipeSet(){
        pipeImage.draw(pipeX, topPipeY);
        pipeImage.draw(pipeX, bottomPipeY, ROTATOR);
    }

    /**
     * Gets Rectangle of top pipe in set
     * @return top pipe Rectangle
     */
    public Rectangle getTopRectangle(){
        return pipeImage.getBoundingBoxAt(new Point(pipeX, topPipeY));
    }

    /**
     * Gets rectangle of bottom pipe in set
     * @return bottom pipe Rectangle
     */
    public Rectangle getBottomRectangle(){
        return pipeImage.getBoundingBoxAt(new Point(pipeX, bottomPipeY));
    }

    /**
     * Determines if pipe set has been passed by the player
     * @param player player
     * @return boolean indicating if pipe has been passed by player
     */
    public boolean passedByPlayer(Player player){
        return player.getPosition().x > getTopRectangle().right();
    }

    /**
     * Increases speed of pipe set
     */
    public static void increaseSpeed(){
        speed *=INCREASE_SPEED_MULTIPLIER;
    }

    /**
     * Decreases speed of pipe set
     */
    public static void decreaseSpeed(){
        speed *=DECREASE_SPEED_MULTIPLIER;
    }

    /**
     * Resets speed of pipe set to default value
     */
    public static void resetSpeed(){
        speed = DEFAULT_SPEED;
    }

    /**
     * Gets whether the pipe has been passed
     * @return boolean isPassed
     */
    public boolean isPassed(){
        return isPassed;
    }

    /**
     * Sets isPassed to true
     */
    public void pass(){
        isPassed = true;
    }

    @Override
    public void destroy(){
        isDestroyed = true;
    }

    @Override
    public boolean isDestroyed(){
        return isDestroyed;
    }

}
