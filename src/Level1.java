import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;
import java.util.Random;

/**
 * Class that implements level 1
 */

public class Level1 extends Level{
    private static final Image BACKGROUND_IMAGE = new Image("res/level-1/background.png");
    private static final String SHOOT_MSG = "PRESS 'S' TO SHOOT";
    private static final Point SHOOT_MSG_POINT = new Point((Window.getWidth() - FONT.getWidth(SHOOT_MSG)) / 2.0, START_MSG_POINT.y+68);

    private static final int MAX_SCORE = 30;
    private static final int MIN_GAP_Y = 100;
    private static final int MAX_GAP_Y = 500;

    public Level1(){
        super.backgroundImage = BACKGROUND_IMAGE;
        super.maxScore = MAX_SCORE;
    }


    /**
     * Randomly generates either one of integers 1 and 2 for use in choosing between two options
     * @return random integer 1 or 2
     */
    public int chooseBetweenTwoOptions(){
        Random random = new Random();
        return random.nextInt(2);
    }

    /**
     * Detects whether player has collided with flame
     * @return boolean indicating if collision has occurred
     */
    public boolean detectPlayerFlameCollision(){
        for (FlameSet flameSet: flameSets){
            if (!flameSet.isDestroyed()&&(player.getRectangle().intersects(flameSet.getTopRectangle()) || player.getRectangle().intersects(flameSet.getBottomRectangle()))){
                flameSet.destroy();
                return true;
            }
        }
        return false;
    }

    @Override
    public void renderStartScreen(Input input){
        if (input.wasPressed(Keys.SPACE)) {
            gameStart = true;
        }
        FONT.drawString(START_MSG, START_MSG_POINT.x, START_MSG_POINT.y);
        FONT.drawString(SHOOT_MSG, SHOOT_MSG_POINT.x, SHOOT_MSG_POINT.y);
    }

    @Override
    public void renderWinScreen(){
        FONT.drawString(CONGRATULATIONS_MSG, CONGRATULATIONS_POINT.x, CONGRATULATIONS_POINT.y);
        FONT.drawString(FINAL_SCORE_MSG + score, FINAL_SCORE_POINT.x, FINAL_SCORE_POINT.y);
    }

    @Override
    public double randomY(){
        return (Math.random() * (MAX_GAP_Y - MIN_GAP_Y)) + MIN_GAP_Y;
    }

    @Override
    public void spawnPipeSet(){
        int choice = chooseBetweenTwoOptions();
        PipeSet pipeSet;
        if (choice==0){
            pipeSet = new PlasticPipeSet(randomY());
        } else {
            pipeSet = new SteelPipeSet(randomY());
            FlameSet flameSet = new FlameSet(pipeSet);
            flameSets.add(flameSet);
        }
        pipeSets.add(pipeSet);
    }

    @Override
    public void spawnWeapon(){
        int choice = chooseBetweenTwoOptions();
        Weapon weapon;
        if (choice==0){
            weapon = new Rock(player, randomY());
        } else {
            weapon = new Bomb(player, randomY());
        }
        weapons.add(weapon);
    }

    @Override
    public boolean detectPlayerCollison(){
        if (detectPlayerPipeCollision()||detectPlayerFlameCollision()){
            lifeBar.loseLife();
            return true;
        }
        return false;
    }

}
