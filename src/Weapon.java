import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;

/**
 * Class that implements a generic weapon
 */

public abstract class Weapon implements Destroyable, SpeedChangeable {
    private static final double SPAWN_POINT_X = Window.getWidth();
    private static final int SHOOT_SPEED = 5;

    protected Image weaponImage;
    protected int shootingRange;
    private int distanceTravelled;
    private double x;
    private double y;

    private static double speed;

    private Player player;

    private boolean isPickedUp;
    protected boolean isShot;
    private boolean isDestroyed;

    protected Weapon(Player player, double spawnPointY){
        this.y = spawnPointY;
        this.player = player;
        x = SPAWN_POINT_X;
        isPickedUp = false;
        isShot = false;
        isDestroyed = false;
        distanceTravelled = 0;
    }

    /**
     * Performs state update of weapon
     * @param input keyboard input
     */
    public void update(Input input){
        if (isPickedUp&&!isShot&&!isDestroyed){
            if (input.wasPressed(Keys.S)){
                shoot();
            }
            x = player.getRectangle().topRight().x;
            y = player.getRectangle().topRight().y;
        } else if (isShot&&!isDestroyed){
            player.removeWeapon();
            x += SHOOT_SPEED;
            distanceTravelled += SHOOT_SPEED;
        } else if (!isDestroyed){
            if (detectPlayerWeaponCollision()&&!player.hasWeapon()){
                isPickedUp = true;
                player.pickUpWeapon();
            } else {
                x -= speed;
            }

        }
        travelledShootingRange();
        renderWeapon();
    }

    /**
     * Renders weapon on screen
     */
    public void renderWeapon(){
        weaponImage.draw(x, y);
    }

    /**
     * Shoots weapon
     */
    public void shoot(){
        isPickedUp = false;
        isShot = true;
    }

    /**
     * Gets position of weapon
     * @return Point position
     */
    public Point getPosition(){
        return new Point(x, y);
    }

    /**
     * Gets weapon's rectangle
     * @return Rectangle of weapon
     */
    public Rectangle getRectangle(){
        return weaponImage.getBoundingBoxAt(getPosition());
    }

    /**
     * Determines if weapon has travelled its maximum range, and if so destroys it
     */
    public void travelledShootingRange(){
        if (distanceTravelled>=shootingRange){
            destroy();
        }
    }

    /**
     * Detects collision between player and weapon
     * @return boolean indicating if collision has occurred
     */
    public boolean detectPlayerWeaponCollision(){
        return player.getRectangle().intersects(getRectangle());
    }

    /**
     * Detects collision between weapon and any of the pipe sets
     * @param pipeSets pipe sets
     * @return boolean indicating if collision has occurred
     */
    public boolean detectPipeCollision(ArrayList<PipeSet> pipeSets){
        return false;
    }

    /**
     * Increase the weapon's speed
     */
    public static void increaseSpeed(){
        speed *= INCREASE_SPEED_MULTIPLIER;
    }

    /**
     * Decrease the weapon's speed
     */
    public static void decreaseSpeed(){
        speed *= DECREASE_SPEED_MULTIPLIER;
    }

    /**
     * Reset the weapon's speed to its default
     */
    public static void resetSpeed(){
        speed = DEFAULT_SPEED;
    }

    @Override
    public void destroy(){
        player.removeWeapon();
        isPickedUp = false;
        isDestroyed = true;
    }

    @Override
    public boolean isDestroyed(){
        return isDestroyed;
    }
}
