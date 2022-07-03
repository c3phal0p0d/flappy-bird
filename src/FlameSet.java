import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Class that implements the flames that shoot from metal pipes
 */

public class FlameSet implements Destroyable{
    private static final int FLAME_PIPE_DISTANCE = 10;
    private static final int FLAME_RENDER_FRAMES = 30;
    private static final int SHOOT_FLAME_FRAME = 20;
    private static final Image FLAME_IMAGE = new Image("res/level-1/flame.png");
    private static final DrawOptions LEFT_ROTATOR = new DrawOptions().setRotation(-Math.PI/2.0);
    private static final DrawOptions RIGHT_ROTATOR = new DrawOptions().setRotation(Math.PI/2.0);

    private int flameRenderCount;
    private int flameFrameCount;
    private double bottomFlameY;
    private double topFlameY;
    private double flameX;
    private boolean isDestroyed;

    PipeSet pipeSet;

    public FlameSet(PipeSet pipeSet) {
        this.pipeSet = pipeSet;
        flameRenderCount = 0;
        flameFrameCount = 0;
        isDestroyed = false;
    }

    /**
     * Performs a state update of the flame set
     */
    public void update(){
        // check whether the particular pipe set that the flames shoot from is destroyed
        if (pipeSet.isDestroyed()){
            destroy();
        }
        // check if the number of frames have passed before the flame should be shot
        if (flameFrameCount==SHOOT_FLAME_FRAME){
            flameFrameCount = 0;
            flameRenderCount++;
        } else if (flameFrameCount>=0){
            flameFrameCount++;
        }
        if ((flameRenderCount>0)&&(flameRenderCount<=FLAME_RENDER_FRAMES)){
            shootFlame();
            flameRenderCount++;
        } else if (flameRenderCount==FLAME_RENDER_FRAMES){
            flameRenderCount = 0;
        }
        updateFlameCoordinates();
    }

    /**
     * Renders the flame on the screen
     */
    public void shootFlame(){
        FLAME_IMAGE.draw(flameX, topFlameY, RIGHT_ROTATOR);
        FLAME_IMAGE.draw(flameX, bottomFlameY, LEFT_ROTATOR);
    }

    /**
     * Updates flame coordinates on the basis of the associated pipe set's position
     */
    public void updateFlameCoordinates(){
        flameX = pipeSet.pipeX;
        topFlameY = pipeSet.topPipeY+pipeSet.pipeImage.getHeight()/2.0+FLAME_PIPE_DISTANCE;
        bottomFlameY = pipeSet.bottomPipeY-pipeSet.pipeImage.getHeight()/2.0+FLAME_PIPE_DISTANCE;
    }

    /**
     * Gets Rectangle of top flame in set
     * @return top flame's Rectangle
     */
    public Rectangle getTopRectangle(){
        return FLAME_IMAGE.getBoundingBoxAt(new Point(flameX, topFlameY));
    }

    /**
     * Gets Rectangle of bottom flame in set
     * @return bottom flame's Rectangle
     */
    public Rectangle getBottomRectangle(){
        return FLAME_IMAGE.getBoundingBoxAt(new Point(flameX, bottomFlameY));
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
