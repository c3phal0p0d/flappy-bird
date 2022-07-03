import bagel.Image;

/**
 * Class that implements the steel pipe set, extending from the PipeSet class
 */

public class SteelPipeSet extends PipeSet{
    private static final Image STEEL_PIPE_IMAGE = new Image("res/level-1/steelPipe.png");

    public SteelPipeSet(double gapY){
        super(gapY);
        super.pipeImage = STEEL_PIPE_IMAGE;
    }

    @Override
    public void renderPipeSet(){
        pipeImage.draw(pipeX, topPipeY);
        pipeImage.draw(pipeX, bottomPipeY, ROTATOR);
    }
}
