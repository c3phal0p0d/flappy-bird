import bagel.*;
import bagel.util.Point;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2021
 *
 * Please filling your name below
 * @nchiorsac
 */
public class ShadowFlap extends AbstractGame {
    private static int levelNum = 0;

    private Level level;

    public ShadowFlap(){
        level = new Level0();
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        if (level.levelledUp()&&levelNum==0){
            levelNum++;
            level = new Level1();
        }
        level.update(input);
    }

    /**
     * Gets the current level number
     * @return level number
     */
    public static int getLevelNum(){
        return levelNum;
    }
}


