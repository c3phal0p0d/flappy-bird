import bagel.*;
import bagel.util.Point;
import java.util.ArrayList;

/**
 * Class that implements a generic level
 */

public abstract class Level {
    protected static final int FONT_SIZE = 48;
    protected static final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);
    protected static final String START_MSG = "PRESS SPACE TO START";
    protected static final String GAME_OVER_MSG = "GAME OVER";
    protected static final String CONGRATULATIONS_MSG = "CONGRATULATIONS!";
    protected static final String FINAL_SCORE_MSG = "FINAL SCORE: ";
    protected static final String SCORE_MSG = " SCORE: ";

    protected static final Point START_MSG_POINT = new Point((Window.getWidth() - FONT.getWidth(START_MSG)) / 2.0, (Window.getHeight()-FONT_SIZE)/2.0);
    protected static final Point GAME_OVER_POINT = new Point((Window.getWidth() - FONT.getWidth(GAME_OVER_MSG)) / 2.0, (Window.getHeight()-FONT_SIZE)/2.0);
    protected static final Point CONGRATULATIONS_POINT = new Point((Window.getWidth() - FONT.getWidth(CONGRATULATIONS_MSG)) / 2.0, (Window.getHeight()-FONT_SIZE)/2.0);
    protected static final Point SCORE_POINT = new Point(100, 100);
    protected static final Point FINAL_SCORE_POINT = new Point((Window.getWidth() - FONT.getWidth(FINAL_SCORE_MSG+ "  ")) / 2.0, Window.getHeight() / 2.0-FONT_SIZE/2.0 + 75);

    public static final int PIPE_GAP = 168;
    private static final double DEFAULT_PIPE_GAP_Y = -PIPE_GAP / 2.0;
    private static final int MAX_RENDER_FRAMES = 150;
    private int renderFrameCount = 0;

    public static final int DEFAULT_TIMESCALE = 1;
    private static final int MIN_TIMESCALE = 1;
    private static final int MAX_TIMESCALE = 5;
    public static final double FRAME_SPEED_DECREASE_MULTIPLIER = 1/SpeedChangeable.DECREASE_SPEED_MULTIPLIER;
    public static final double FRAME_SPEED_INCREASE_MULTIPLIER = 1/SpeedChangeable.INCREASE_SPEED_MULTIPLIER;

    private int timescale;

    private static int frameCount = 0;
    private static final int DEFAULT_PIPE_SPAWN_FRAME = 100;
    private static final int DEFAULT_WEAPON_SPAWN_FRAME = 170;
    private static int pipeSpawnFrame = DEFAULT_PIPE_SPAWN_FRAME;
    private static int weaponSpawnFrame = DEFAULT_WEAPON_SPAWN_FRAME;

    protected Image backgroundImage;

    protected Player player;
    protected LifeBar lifeBar;

    public ArrayList<PipeSet> pipeSets;
    public ArrayList<FlameSet> flameSets;
    public ArrayList<Weapon> weapons;

    protected int score = 0;
    protected int maxScore;

    protected boolean gameStart;
    private boolean gameOver;
    private boolean gameWin;
    private boolean levelUp;

    protected Level(){
        gameStart = false;
        gameOver = false;
        gameWin = false;
        levelUp = false;

        pipeSets = new ArrayList<>();
        weapons = new ArrayList<>();
        flameSets = new ArrayList<>();
        player = new Player();
        lifeBar = new LifeBar();

        resetTimescale();
    }

    /**
     * Performs a state update of the current level
     */
    public void update(Input input){
        backgroundImage.drawFromTopLeft(0, 0);
        updateTimescale(input);

        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        if (!gameStart) {
            renderStartScreen(input);
        }

        if (gameOver) {
            renderGameOverScreen();
        }

        if (detectOutOfBounds()){
            player.respawn();
            lifeBar.loseLife();
        }

        if (gameWin){
            if (renderFrameCount>=MAX_RENDER_FRAMES){
                levelUp();
            }
            renderWinScreen();
            renderFrameCount++;
        }

        if (gameStart && !detectPlayerCollison() && !gameOver && !gameWin) {
            if (frameCount%pipeSpawnFrame==0){
                spawnPipeSet();
            }
            else if (frameCount%weaponSpawnFrame==0){
                spawnWeapon();
            }

            // remove flames that have been destroyed
            flameSets.removeIf(FlameSet::isDestroyed);

            for (FlameSet flameSet: flameSets){
                flameSet.update();
            }

            // remove pipe sets that have been destroyed
            pipeSets.removeIf(PipeSet::isDestroyed);

            for (PipeSet pipeSet: pipeSets){
                if (!pipeSet.isPassed()&&!pipeSet.isDestroyed()&&pipeSet.passedByPlayer(player)){
                    pipeSet.pass();
                    score++;
                }
                pipeSet.update();
            }

            // remove weapons that have been destroyed
            weapons.removeIf(Weapon::isDestroyed);

            for (Weapon weapon: weapons){
                if (weapon.detectPipeCollision(pipeSets)){
                    score++;
                }
                weapon.update(input);
            }

            lifeBar.update();
            player.update(input);

            renderScore();
            detectWin();
            detectGameOver();

        }
        frameCount++;

    }

    /**
     * Renders level start screen
     * @param input keyboard input
     */
    public void renderStartScreen(Input input){
        if (input.wasPressed(Keys.SPACE)) {
            gameStart = true;
        }
        FONT.drawString(START_MSG, START_MSG_POINT.x, START_MSG_POINT.y);
    }

    /**
     * Renders end of level screen once maximum score has been reached
     */
    public void renderWinScreen(){
    }

    /**
     * Renders game over screen
     */
    public void renderGameOverScreen(){
        FONT.drawString(GAME_OVER_MSG, GAME_OVER_POINT.x, GAME_OVER_POINT.y);
        FONT.drawString(FINAL_SCORE_MSG + score, FINAL_SCORE_POINT.x, FINAL_SCORE_POINT.y);
    }

    /**
     * Renders score on screen
     */
    public void renderScore(){
        FONT.drawString(SCORE_MSG + score, SCORE_POINT.x, SCORE_POINT.y);
    }

    /**
     * Spawns pipe set
     */
    public void spawnPipeSet(){
    }

    /**
     * Spawns weapon
     */
    public void spawnWeapon() {
    }

    /**
     * Generates random Y value
     * @return y value
     */
    public double randomY(){
        return DEFAULT_PIPE_GAP_Y;
    }

    /**
     * Detects whether player is out of bounds
     * @return boolean indicating if player is out of bounds
     */
    public boolean detectOutOfBounds(){
        return (player.getPosition().y > Window.getHeight()) || (player.getPosition().y < 0);
    }

    /**
     * Detects collision between player and pipe
     * @return boolean indicating if collision has occurred
     */
    public boolean detectPlayerPipeCollision(){
        for (PipeSet pipeSet: pipeSets){
            if (!pipeSet.isDestroyed()&&(player.getRectangle().intersects(pipeSet.getTopRectangle()) || player.getRectangle().intersects(pipeSet.getBottomRectangle()))){
                pipeSet.destroy();
                return true;
            }
        }
        return false;
    }

    /**
     * Detects if a player collision has occurred and if it has removes a life from the lives count
     * @return boolean indicating if player collision has occurred
     */
    public boolean detectPlayerCollison(){
        if (detectPlayerPipeCollision()){
            lifeBar.loseLife();
            return true;
        }
        return false;
    }

    /**
     * Updates the timescale depending on keyboard input
     * @param input keyboard input
     */
    public void updateTimescale(Input input) {
        if (input.wasPressed(Keys.K)&&timescale>MIN_TIMESCALE) {
            PipeSet.decreaseSpeed();
            Weapon.decreaseSpeed();
            pipeSpawnFrame=(int)(pipeSpawnFrame*FRAME_SPEED_DECREASE_MULTIPLIER);
            weaponSpawnFrame=(int)(weaponSpawnFrame*FRAME_SPEED_DECREASE_MULTIPLIER);
            timescale--;

        } else if (input.wasPressed(Keys.L)&&timescale<MAX_TIMESCALE) {
            PipeSet.increaseSpeed();
            Weapon.increaseSpeed();
            pipeSpawnFrame=(int)(pipeSpawnFrame*FRAME_SPEED_INCREASE_MULTIPLIER);
            weaponSpawnFrame=(int)(weaponSpawnFrame*FRAME_SPEED_INCREASE_MULTIPLIER);
            timescale++;
        }
    }

    /**
     * Resets the timescale and speeds of pipe sets and weapons to default values
     */
    public void resetTimescale(){
        PipeSet.resetSpeed();
        Weapon.resetSpeed();
        pipeSpawnFrame=DEFAULT_PIPE_SPAWN_FRAME;
        weaponSpawnFrame=DEFAULT_WEAPON_SPAWN_FRAME;
        timescale = DEFAULT_TIMESCALE;
    }

    /**
     * Detects whether the maximum score has been reached and the level won
     */
    public void detectWin(){
        if (score >= maxScore){
            gameWin = true;
        }
    }

    /**
     * Detects whether all the player's lives have run out and the game over
     */
    public void detectGameOver(){
        if (lifeBar.getLivesCount()<=0){
            gameOver = true;
        }
    }

    /**
     * Gets whether the game has levelled up
     * @return boolean levelUp
     */
    public boolean levelledUp(){
        return levelUp;
    }

    /**
     * Levels up
     */
    public void levelUp(){
        levelUp = true;
    }

    /**
     * Gets the frame count
     * @return frame count
     */
    public static int getFrameCount(){
        return frameCount;
    }
}
