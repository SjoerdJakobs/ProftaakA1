package interfacelayer;

import buttercat.HelpFunctions;
import hardwarelayer.sensors.linefollower.LineFollower;
import ooframework.FrameworkProgram;
import ooframework.StandardObject;

public class LineFollowChecker extends StandardObject {
    private LineFollower left;
    private LineFollower midLeft;
    private LineFollower midRigth;
    private LineFollower right;

    private final static int THRESHOLD = 1200;

    public static final int LEFT_LINEFOLLOWER = 0;
    public static final int MID_LEFT_LINEFOLLOWER = 1;
    public static final int MID_RIGHT_LINEFOLLOWER = 2;
    public static final int RIGHT_LINEFOLLOWER = 3;

    /**
     * creates a new LineFollowChecker object
     *
     * @param frameworkProgram the program to run with
     */
    public LineFollowChecker(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);

        //TODO change if pins are different after putting the linefollowers on project boebot
        this.left = new LineFollower(2);
        this.midLeft = new LineFollower(3);
        this.midRigth = new LineFollower(1);
        this.right = new LineFollower(0);
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

    /**
     * checks if the BoeBot has noticed an intersection
     *
     * @return true if the BoeBot's line followers are on an intersection
     */
    public boolean hasNoticedIntersection() {
        return leftNoticedLine() && midLeftNoticedLine() && midRightNoticedLine() && rightNoticedLine();
    }

    /**
     * gets the value of the given line follower. The line followers you van use:
     * <ul>
     * <li>{@link LineFollowChecker#LEFT_LINEFOLLOWER left (0)}</li>
     * <li>{@link LineFollowChecker#MID_LEFT_LINEFOLLOWER midLeft (1)}</li>
     * <li>{@link LineFollowChecker#MID_RIGHT_LINEFOLLOWER right (2)}</li>
     * <li>{@link LineFollowChecker#RIGHT_LINEFOLLOWER right (3)}</li>
     * </ul>
     *
     * @param lineFollower the line follower to get the value from.
     * @return the value of the line follower as an <code>int</code>.
     */
    public int getValue(int lineFollower) {
        HelpFunctions.checkLineFollowers("Line Follower selection", lineFollower);
        int res = 0;
        switch (lineFollower) {
            case 0:
                res = left.getValue();
                break;
            case 1:
                res = midLeft.getValue();
                break;
            case 2:
                res = midRigth.getValue();
                break;
            case 3:
                res = right.getValue();
                break;
        }
        return res;

    }

    /**
     * debug method
     */
    public void readAll() {
        left.read();
        midLeft.read();
        midRigth.read();
        right.read();
    }


}
