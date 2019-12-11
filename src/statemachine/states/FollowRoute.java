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

    private int engineTargetSpeed = 70;

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

    @Override
    protected void logic() {
        super.logic();
        colors(0.009f);

        canDrive = !objectDetection.objectIsTooClose(80);
        distance = objectDetection.getDistance();

        if (canDrive) {
            notificationSystem.turnLedsOff();
            //TODO test this with boebot
            //TODO add 4th line follower
            if (lineFollowChecker.hasNoticedIntersection()) {
                System.out.println("stop");
                //TODO implement decellerating
                notificationSystem.allLineFollowers(rgb);
            }

            checkLeft();
            checkMidLeft();
            checkMidRight();
            checkRight();
            checkMid();

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
        if (lineFollowChecker.midLeftNoticedLine() && lineFollowChecker.midRightNoticedLine() && !(lineFollowChecker.leftNoticedLine() || lineFollowChecker.rightNoticedLine())) {
//                System.out.println("noticed mid");
            notificationSystem.midLineFollower(rgb);
            if (!goingForward) {
                engine.turnStop();
                engine.driveForward(this.engineTargetSpeed);
            }

            goingForward = true;
            goingLeft = false;
            goingRight = false;
        }
    }

    private void checkMidLeft() {
        if (lineFollowChecker.midLeftNoticedLine() && !lineFollowChecker.rightNoticedLine()) {

            notificationSystem.leftLineFollower(rgb);
            if (!goingLeft) {
                engine.turnLeft(0.4);
            }

            goingLeft = true;
            goingRight = false;
            goingForward = false;

        }
    }

    private void checkMidRight() {
        if (lineFollowChecker.midRightNoticedLine() && !lineFollowChecker.leftNoticedLine()) {
//                System.out.println("adjusting right");
            notificationSystem.rightLineFollower(rgb);
            if (!goingRight) {
                engine.turnRight(0.4);
            }


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
            if (!goingRight) {
                engine.turnRight(1);
            }


            goingRight = true;
            goingLeft = false;
            goingForward = false;
        }
    }

    private void checkLeft() {
        if (lineFollowChecker.leftNoticedLine() && !(lineFollowChecker.midRightNoticedLine() || lineFollowChecker.rightNoticedLine())) {
            colors(0.1f);

            notificationSystem.leftLineFollower(rgb);
            if (!goingLeft) {
                engine.turnLeft(1);
            }

            goingLeft = true;
            goingRight = false;
            goingForward = false;

        }
    }

    @Override
    protected void leave() {
        super.leave();
    }
}
