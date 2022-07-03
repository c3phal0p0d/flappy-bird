import bagel.Image;
import bagel.Window;
import bagel.util.Point;

/**
 * Class that implements level 0
 */
public class Level0 extends Level{
    private static final int MAX_SCORE = 10;
    private static final Image BACKGROUND_IMAGE = new Image("res/level-0/background.png");
    private static final String LEVEL_UP_MSG = "LEVEL-UP!";
    private static final Point LEVEL_UP_POINT = new Point((Window.getWidth() - FONT.getWidth(LEVEL_UP_MSG)) / 2.0, (Window.getHeight()-FONT_SIZE)/2.0);

    private static final double HIGH_GAP_Y = 100;
    private static final double MID_GAP_Y = 300;
    private static final double LOW_GAP_Y = 500;

    private final double[] gapYChoices = {HIGH_GAP_Y, MID_GAP_Y, LOW_GAP_Y};

    public Level0(){
        super.backgroundImage = BACKGROUND_IMAGE;
        super.maxScore = MAX_SCORE;
    }

    @Override
    public void renderWinScreen(){
        FONT.drawString(LEVEL_UP_MSG, LEVEL_UP_POINT.x, LEVEL_UP_POINT.y);
        FONT.drawString(FINAL_SCORE_MSG + score, FINAL_SCORE_POINT.x, FINAL_SCORE_POINT.y);
    }

    @Override
    public void spawnPipeSet(){
        PipeSet pipeSet = new PlasticPipeSet(randomY());
        pipeSets.add(pipeSet);
    }

    @Override
    public double randomY(){
       int randomInt = (int)(Math.random()*3);
       return gapYChoices[randomInt];
    }

}
