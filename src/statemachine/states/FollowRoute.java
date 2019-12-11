package statemachine.states;

import TI.BoeBot;
import buttercat.DriverAI;
import interfacelayer.Engine;
import buttercat.Remote;
import interfacelayer.LineFollowChecker;
import interfacelayer.NotificationSystem;
import interfacelayer.ObjectDetection;
import statemachine.State;
import statemachine.StateID;

import java.awt.*;

public class FollowRoute extends State {
    private DriverAI driverAI;
    private Engine engine;
    private Remote remote;
    private LineFollowChecker lineFollowChecker;
    private ObjectDetection objectDetection;
    private NotificationSystem notificationSystem;

    private boolean shouldGoToRemoteControl, canDrive, goingForward, goingLeft, goingRight;

    private int distance, lastDistance, timer = 5;

    private int engineTargetSpeed = 125;

    private float rainbowValue;
    private Color rgb;

    public FollowRoute(DriverAI driverAI) {
        super(StateID.FollowRoute);
        shouldGoToRemoteControl = false;
        this.driverAI = driverAI;
        this.engine = driverAI.getEngine();
        this.remote = driverAI.getRemote();

        this.lineFollowChecker = driverAI.getLineFollowChecker();
        this.objectDetection = driverAI.getObjectDetection();
        this.notificationSystem = NotificationSystem.INSTANCE;
        this.goingForward = false;
        this.goingLeft = false;
        this.goingRight = false;

        this.rainbowValue = 0;

    }

    @Override
    protected void enter() {
        super.enter();
        System.out.println("following");
        remote.aButtonHasBeenPressed = this::setShouldGoToRemoteControlToTrue;
        lastDistance = 0;
        engine.setEngineTargetSpeed(this.engineTargetSpeed);
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

    // make variables so they don't have to be initialized every time the loop is entered
    private boolean leftNoticed;
    private boolean rightNoticed;
    private boolean midNoticed;

    @Override
    protected void logic() {
        super.logic();
        colors(0.009f);
//        System.out.print("left : " + lineFollowChecker.getValue(LineFollowChecker.LEFT_LINEFOLLOWER));
//        System.out.print(" mid l: " + lineFollowChecker.getValue(LineFollowChecker.MID_LEFT_LINEFOLLOWER));
//        System.out.print(" mid r: " + lineFollowChecker.getValue(LineFollowChecker.MID_RIGHT_LINEFOLLOWER));
//        System.out.println(" right: " + lineFollowChecker.getValue(LineFollowChecker.RIGHT_LINEFOLLOWER));

        System.out.println(engine.toString());

        canDrive = !objectDetection.objectIsTooClose(80);
//        System.out.println(objectDetection.getDistance());
        distance = objectDetection.getDistance();

        if (canDrive) {
            notificationSystem.turnLedsOff();
            //TODO test this with boebot
            //TODO add 4th line follower
            if (lineFollowChecker.hasNoticedIntersection()) {
//                System.out.println("stop");
                //TODO implement decellerating
                notificationSystem.allLineFollowers(rgb);
                engine.emergencyBrake();
            }

            if (!lineFollowChecker.hasNoticedIntersection()) {
                checkLeft();
                checkMidLeft();
                checkMidRight();
                checkRight();
                checkMid();
            }

//            BoeBot.wait(100);
        } else {
            notificationSystem.objectDetected();
            engine.emergencyBrake();
            goingForward = false;
            goingLeft = false;
            goingRight = false;
        }


        lastDistance = distance;

        for (int i = 0; i < timer; i++) {
            engine.drive();
        }
    }

    /**
     * method to calculate the color for displaying the line follower
     */
    private void colors(float value) {

        rgb = Color.getHSBColor(rainbowValue, 1, 1);

        rainbowValue += 0.009;

        if (rainbowValue == 1.0) {
            rainbowValue = 0;
        }


    }

    private void checkMid() {
        if (lineFollowChecker.midLeftNoticedLine() && lineFollowChecker.midRightNoticedLine() && !(lineFollowChecker.rightNoticedLine() || lineFollowChecker.leftNoticedLine())) {
//                System.out.println("noticed mid");
            notificationSystem.midLineFollower(rgb);
//            if (!goingForward) {
                engine.turnStop();
                engine.updateInstantPulse(this.engineTargetSpeed);
//            }

            goingForward = true;
            goingLeft = false;
            goingRight = false;
        }
    }

    private void checkMidLeft() {
        if (lineFollowChecker.midLeftNoticedLine() && !(lineFollowChecker.rightNoticedLine() || lineFollowChecker.midRightNoticedLine())) {
//            System.out.println("noticed mid left");
            notificationSystem.leftLineFollower(rgb);
//            if (!goingLeft) {
                engine.instantLeft();
                notificationSystem.midLeftLineFollower(rgb);
//            }

            goingLeft = true;
            goingRight = false;
            goingForward = false;

        }
    }

    private void checkMidRight() {
        if (lineFollowChecker.midRightNoticedLine() && !(lineFollowChecker.leftNoticedLine() || lineFollowChecker.midLeftNoticedLine() )) {
//                System.out.println("adjusting mid right");
            notificationSystem.rightLineFollower(rgb);
//            if (!goingRight) {
                engine.instantRight();
                notificationSystem.midRightLineFollower(rgb);
//            }


            goingRight = true;
            goingLeft = false;
            goingForward = false;
        }
    }

    private void checkRight() {
        if (lineFollowChecker.rightNoticedLine() && !(lineFollowChecker.midLeftNoticedLine() || lineFollowChecker.leftNoticedLine())) {
            colors(0.1f);
//                System.out.println("adjusting right");
            notificationSystem.rightLineFollower(rgb);
//            if (!goingRight) {
                engine.instantRight();
//            }


            goingRight = true;
            goingLeft = false;
            goingForward = false;
        }
    }

    private void checkLeft() {
        if (lineFollowChecker.leftNoticedLine() && !(lineFollowChecker.midRightNoticedLine() || lineFollowChecker.rightNoticedLine())) {
            colors(0.1f);
            System.out.println("noticed left");
            notificationSystem.leftLineFollower(rgb);
//            if (!goingLeft) {
//                engine.turnLeft(1);
                engine.instantLeft();
//                System.out.println(engine.toString());
//            }

            goingLeft = true;
            goingRight = false;
            goingForward = false;

        }
    }

    public void followHardCodedRoute(int counter) {

    }

    @Override
    protected void leave() {
        super.leave();
    }
}