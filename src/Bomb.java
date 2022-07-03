import bagel.Image;
import bagel.Input;

import java.util.ArrayList;

/**
 * Class that implements the Bomb weapon, extending from the Weapon class
 */

public class Bomb extends Weapon{
    private static final Image BOMB_IMAGE = new Image("res/level-1/bomb.png");
    private static final int SHOOTING_RANGE = 50;

    public Bomb(Player player, double spawnPointY) {
        super(player, spawnPointY);
        super.weaponImage = BOMB_IMAGE;
        super.shootingRange = SHOOTING_RANGE;
    }

    @Override
    public boolean detectPipeCollision(ArrayList<PipeSet> pipeSets){
        for (PipeSet pipeSet: pipeSets){
            if (isShot&&(getRectangle().intersects(pipeSet.getTopRectangle()) || getRectangle().intersects(pipeSet.getBottomRectangle()))){
                destroy();
                pipeSet.destroy();
                return true;
            }
        }
        return false;
    }
}
