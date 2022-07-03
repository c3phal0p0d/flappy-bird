import bagel.Image;
import bagel.Input;

import java.util.ArrayList;

/**
 * Class that implements the Rock weapon, extending from the Weapon class
 */

public class Rock extends Weapon{
    private static final Image ROCK_IMAGE = new Image("res/level-1/rock.png");
    private static final int SHOOTING_RANGE = 25;

    public Rock(Player player, double spawnPointY) {
        super(player, spawnPointY);
        super.weaponImage = ROCK_IMAGE;
        super.shootingRange = SHOOTING_RANGE;
    }

    @Override
    public boolean detectPipeCollision(ArrayList<PipeSet> pipeSets){
        for (PipeSet pipeSet: pipeSets){
            if (isShot&&(getRectangle().intersects(pipeSet.getTopRectangle()) || getRectangle().intersects(pipeSet.getBottomRectangle()))){
                // rock can only destroy plastic pipes
                if (pipeSet instanceof PlasticPipeSet){
                    pipeSet.destroy();
                    destroy();
                    return true;
                }
                destroy();
                return false;
            }
        }
        return false;
    }

}
