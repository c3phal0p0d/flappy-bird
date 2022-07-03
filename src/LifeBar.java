import bagel.Image;
import bagel.util.Point;

/**
 * Class that implements the life bar
 */
public class LifeBar {
    private static final Image FULL_LIFE = new Image("res/level/fullLife.png");
    private static final Image NO_LIFE = new Image("res/level/noLife.png");
    private static final Point LIFE_BAR_POS = new Point(100, 15);
    private static final int SPACE_BETWEEN_HEARTS = 50;

    private static final int LEVEL_0_MAX_LIVES = 3;
    private static final int LEVEL_1_MAX_LIVES = 5;

    private int initialLivesCount;
    private int livesCount;

    public LifeBar(){
        // checks what the current level is and assigns the maximum lives accordingly
        if (ShadowFlap.getLevelNum()==0){
            initialLivesCount = LEVEL_0_MAX_LIVES;
        } else {
            initialLivesCount = LEVEL_1_MAX_LIVES;
        }
        livesCount = initialLivesCount;
    }

    public void update(){
        renderLifeBar();
    }

    /**
     * Renders the lifebar
     */
    public void renderLifeBar(){
        int i;

        for (i=0; i<livesCount; i++){
            FULL_LIFE.drawFromTopLeft(LIFE_BAR_POS.x + SPACE_BETWEEN_HEARTS*i, LIFE_BAR_POS.y);
        }

        for (i=livesCount; i<initialLivesCount; i++){
            NO_LIFE.drawFromTopLeft(LIFE_BAR_POS.x + SPACE_BETWEEN_HEARTS*i, LIFE_BAR_POS.y);
        }

    }

    /**
     * Removes one life from the lives count
     */
    public void loseLife(){
        livesCount--;
    }

    /**
     * Gets the lives count
     * @return lives count
     */
    public int getLivesCount(){
        return livesCount;
    }
}
