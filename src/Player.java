import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Class that implements the behaviour of the player
 */

public class Player {
    private static final Image BIRD_WING_UP_0 = new Image("res/level-0/birdWingUp.png");
    private static final Image BIRD_WIND_DOWN_0 = new Image("res/level-0/birdWingDown.png");
    private static final Image BIRD_WING_UP_1 = new Image("res/level-1/birdWingUp.png");
    private static final Image BIRD_WIND_DOWN_1 = new Image("res/level-1/birdWingDown.png");

    private Image birdWingUp;
    private Image birdWingDown;
    private Image playerImage;

    private static final int FLAP_RENDER_FRAME = 10;   // number of frames that pass before bird's wings flap
    private static final double FLY_SPEED = 6 ;
    private static final double NO_SPEED = 0;
    private static final double MAX_FALL_SPEED = -10;
    private static final double ACCELERATION = -0.4 ;
    private static final int INITIAL_Y = 350;
    private static final double X = 200;
    private double y;
    private double speed;

    private boolean hasWeapon;

    public Player(){
        speed = FLY_SPEED;  // bird initially flies upwards
        y = INITIAL_Y;
    }

    /**
     * Performs a state update of the player
     */
    public void update(Input input){
        if (input.wasPressed(Keys.SPACE)){
            fly();
        } else {
            fall();
        }
        y-=speed;   // updates the y-coordinate of the player
        renderBird();
    }

    /**
     * Returns a Point indicating the current position of the player
     */
    public Point getPosition(){
        return new Point(X, y);
    }

    /**
     * Returns the Rectangle that bounds the player
     */
    public Rectangle getRectangle(){
        return playerImage.getBoundingBoxAt(getPosition());
    }

    /**
     * Checks whether ten frames have passed, and if so renders the birdWingUp image
     * rather than the birdWingDown image that is rendered otherwise
     */
    public void renderBird(){
        if (Level.getFrameCount()>0 && Level.getFrameCount()%FLAP_RENDER_FRAME==0){
            if (ShadowFlap.getLevelNum() == 0){
                birdWingUp = BIRD_WING_UP_0;
            } else {
                birdWingUp = BIRD_WING_UP_1;
            }
            playerImage = birdWingUp;
        } else {
            if (ShadowFlap.getLevelNum() == 0){
                birdWingDown = BIRD_WIND_DOWN_0;
            } else {
                birdWingDown = BIRD_WIND_DOWN_1;
            }
            playerImage = birdWingDown;
        }
        playerImage.draw(X, y);
    }

    /**
     * Performs the bird's flying action
     */
    public void fly(){
        speed=FLY_SPEED;
    }

    /**
     * Implements the bird's falling behaviour
     */
    public void fall(){
        if (speed+ACCELERATION>MAX_FALL_SPEED){
            speed+=ACCELERATION;
        } else {
            speed = MAX_FALL_SPEED;
        }
    }

    /**
     * Respawns the player at its original position
     */
    public void respawn(){
        y = INITIAL_Y;
        speed = NO_SPEED;
    }

    /**
     * Sets hasWeapon to true if player picks up weapon
     */
    public void pickUpWeapon(){
        hasWeapon = true;
    }

    /**
     * Sets hasWeapon to false if player shoots weapon
     */
    public void removeWeapon(){
        hasWeapon = false;
    }

    /**
     * Gets whether the player has a weapon
     * @return boolean hasWeapon
     */
    public boolean hasWeapon(){
        return hasWeapon;
    }

}
