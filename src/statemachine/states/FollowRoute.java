package statemachine.states;

import buttercat.DriverAI;
import hardwarelayer.sensors.linefollower.LineFollower;
import interfacelayer.Engine;
import buttercat.Remote;
import interfacelayer.LineFollowChecker;
import interfacelayer.ObjectDetection;
import ooframework.FrameworkProgram;
import statemachine.State;
import statemachine.StateID;

public class FollowRoute extends State {
    private DriverAI driverAI;
    private Engine engine;
    private Remote remote;
    private LineFollowChecker lineFollowChecker;
    private ObjectDetection objectDetection;

    private boolean shouldGoToRemoteControl,  canDrive;

    private int distance, lastDistance;

    public FollowRoute(DriverAI driverAI) {
        super(StateID.FollowRoute);
        shouldGoToRemoteControl = false;
        this.driverAI = driverAI;
        this.engine = driverAI.getEngine();
        this.remote = driverAI.getRemote();

        this.lineFollowChecker = driverAI.getLineFollowChecker();
        this.objectDetection = driverAI.getObjectDetection();

    }

    @Override
    protected void enter() {
        super.enter();
        remote.aButtonHasBeenPressed = this::setShouldGoToRemoteControlToTrue;
        lastDistance = 0;
    }

    private void setShouldGoToRemoteControlToTrue() {
        shouldGoToRemoteControl = true;
    }

    @Override
    protected void checkForStateSwitch() {
        super.checkForStateSwitch();
        if (shouldGoToRemoteControl) {
            stateMachine.SetState(StateID.ListenToRemote);
        }
    }

    @Override
    protected void logic() {
        super.logic();
        engine.drive();
//        System.out.println("L: " + lineFollowChecker.getValue(LineFollowChecker.LEFT_LINEFOLLOWER));
//        System.out.println("M: " + lineFollowChecker.getValue(LineFollowChecker.MID_LINEFOLLOWER));
//        System.out.println("R: " + lineFollowChecker.getValue(LineFollowChecker.RIGHT_LINEFOLLOWER));
//        System.out.println();
        canDrive = objectDetection.objectIsTooClose(10);
        distance = objectDetection.getDistance();

        if (distance < 10 && lastDistance - distance > 20) {
            engine.emergencyBrake();
            canDrive = false;
        }

        if (canDrive) {
            //TODO test this with boebot
            if (lineFollowChecker.midNoticedLine()) {
//                System.out.println("driving forward");
                engine.driveForward(50);
                engine.turnStop();
            }
            if (lineFollowChecker.leftNoticedLine()) {
//                System.out.println("adjusting left");
                engine.turnRight(1);
            }
            if (lineFollowChecker.rightNoticedLine()) {
//                System.out.println("adjusting right");
                engine.turnLeft(1);
            }

            if (lineFollowChecker.hasNoticedIntersection()) {
                System.out.println("stop");
                //TODO implement decellerating
            }
        }

        lastDistance = distance;

    }

    @Override
    protected void leave() {
        super.leave();
    }
}