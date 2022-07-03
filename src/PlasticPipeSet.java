import bagel.Image;

/**
 * Class that implements the plastic pipe set, extending from the PipeSet class
 */

public class PlasticPipeSet extends PipeSet{
    private static final Image PLASTIC_PIPE_IMAGE = new Image("res/level/plasticPipe.png");

    public PlasticPipeSet(double gapY){
        super(gapY);
        super.pipeImage = PLASTIC_PIPE_IMAGE;
    }
}
