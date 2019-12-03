package interfacelayer;

import hardwarelayer.sensors.linefollower.LineFollower;
import ooframework.FrameworkProgram;
import ooframework.StandardObject;

public class LineFollowChecker extends StandardObject {
    private LineFollower left;
    private LineFollower midLeft;
    private LineFollower midRigth;
    private LineFollower right;

    private final static int THRESHOLD = 1300;

    public LineFollowChecker(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);

        //TODO change if pins are different after putting the linefollowers on project boebot
        this.left = new LineFollower(0);
        this.midLeft = new LineFollower(1);
        this.midRigth = new LineFollower(2);
        this.right = new LineFollower(3);
    }

    /**
     * the main loop where everything is calculated
     *
     * @param deltaTime the time difference
     */
    @Override
    protected void mainLoop(double deltaTime) {
        super.mainLoop(deltaTime);

        left.startReading().run();
        midLeft.startReading().run();
        midRigth.startReading().run();
        right.startReading().run();
    }

    public boolean leftNoticedLine() {
        return left.getValue() > THRESHOLD;
    }

    public boolean midLeftNoticedLine() {
        return midLeft.getValue() > THRESHOLD;
    }

    public boolean midRightNoticedLine() {
        return midRigth.getValue() > THRESHOLD;
    }

    public boolean rightNoticedLine() {
        return right.getValue() > THRESHOLD;
    }


}
