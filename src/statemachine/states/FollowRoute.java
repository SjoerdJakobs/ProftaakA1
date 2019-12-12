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
import hardwarelayer.sensors.button.Button;

import java.awt.*;

public class FollowRoute extends State {
    private DriverAI driverAI;
    private Engine engine;
    private Remote remote;
    private LineFollowChecker lineFollowChecker;
    private ObjectDetection objectDetection;
    private NotificationSystem notificationSystem;
    private Button button = new Button(11);

    private boolean shouldGoToRemoteControl, canDrive;

    private int distance, lastDistance, timer = 5;

    private int engineTargetSpeed = -125;

    private float rainbowValue;
    private Color rgb;

    private boolean leftHasLine = false;
    private boolean midLeftHasLine = false;
    private boolean midRightHasLine = false;
    private boolean rightHasLine = false;
    private boolean changeNeoLeds = true;

    public FollowRoute(DriverAI driverAI) {
        super(StateID.FollowRoute);
        shouldGoToRemoteControl = false;
        this.driverAI = driverAI;
        this.engine = driverAI.getEngine();
        this.remote = driverAI.getRemote();

        this.lineFollowChecker = driverAI.getLineFollowChecker();
        this.objectDetection = driverAI.getObjectDetection();
        this.notificationSystem = NotificationSystem.INSTANCE;

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
        checkAllLinefollowers();

        colors(0.009f);

//        System.out.println(engine.toString());
//        System.out.println(lineFollowChecker.toString());
//        System.out.println(objectDetection.toString() + "\t Can drive: " + (canDrive ? "true" : "false"));

        /*
        if (button.isPressed()) {
            System.out.println("Pressed");
            canDrive = true;
        }
        */
        // TODO: use Callback button instead of digitalRead
        // TODO: fix bug: when starting in FollowRoute, ultrasonic sensor reads <80 thus must press the button to start the BoeBot
        if (!BoeBot.digitalRead(11)) canDrive = true;
//        System.out.println("distance: " + objectDetection.getDistance());
        if (objectDetection.objectIsTooClose(80)) {
            canDrive = false;
        }

//        canDrive = !objectDetection.objectIsTooClose(80);

        if (canDrive) {
            if (changeNeoLeds) {
                notificationSystem.turnLedsOff();
            }
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
                checkLeftCombi();
                checkMidLeft();
                checkMidCombi();
                checkMidRight();
                checkRightCombi();
                checkRight();
            }

//            BoeBot.wait(100);
        } else {
            notificationSystem.objectDetected();
            engine.emergencyBrake();
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

        rainbowValue += value;

        if (rainbowValue == 1.0) {
            rainbowValue = 0;
        }


    }

    private void checkLeft() {
        if (this.leftHasLine && !this.midLeftHasLine && !this.midRightHasLine && !this.rightHasLine) {
            notificationSystem.leftLineFollower(rgb);
            engine.updateInstantPulse(engineTargetSpeed, -1);
        }
    }

    private void checkLeftCombi() {
        if (this.leftHasLine && this.midLeftHasLine && !this.midRightHasLine && !this.rightHasLine) {
            notificationSystem.leftLineFollower(rgb);
            engine.updateInstantPulse(engineTargetSpeed, -0.6);
        }
    }

    private void checkMidLeft() {
        if (!this.leftHasLine && this.midLeftHasLine && !this.midRightHasLine && !this.rightHasLine) {
            notificationSystem.midLeftLineFollower(rgb);
            engine.updateInstantPulse(engineTargetSpeed, -0.2);
        }
    }

    private void checkMidCombi() {
        if (!this.leftHasLine && this.midLeftHasLine && this.midRightHasLine && !this.rightHasLine) {
            notificationSystem.midLineFollower(rgb);
            engine.updateInstantPulse(engineTargetSpeed, 0);
        }
    }

    private void checkMidRight() {
        if (!this.leftHasLine && !this.midLeftHasLine && this.midRightHasLine && !this.rightHasLine) {
            notificationSystem.midRightLineFollower(rgb);
            engine.updateInstantPulse(engineTargetSpeed, 0.2);
        }
    }

    private void checkRightCombi() {
        if (!this.leftHasLine && !this.midLeftHasLine && this.midRightHasLine && this.rightHasLine) {
            notificationSystem.rightLineFollower(rgb);
            engine.updateInstantPulse(engineTargetSpeed, 0.6);
        }
    }

    private void checkRight() {
        if (!this.leftHasLine && !this.midLeftHasLine && !this.midRightHasLine && this.rightHasLine) {
            notificationSystem.rightLineFollower(rgb);
            engine.updateInstantPulse(engineTargetSpeed, 1);
        }
    }

    /**
     * Put all noticeLine functions in a variable for better readability.
     * Also checks if detected lines have changes since the previous detection using hasLineBits.
     */
    private void checkAllLinefollowers() {
        int hasLineBits = (this.leftHasLine ? 8 : 0) + (this.midLeftHasLine ? 4 : 0) +
                (this.midRightHasLine ? 2 : 0) + (this.rightHasLine ? 1 : 0);

        this.leftHasLine = lineFollowChecker.leftNoticedLine(); // 0b1000
        this.midLeftHasLine = lineFollowChecker.midLeftNoticedLine(); // 0b0100
        this.midRightHasLine = lineFollowChecker.midRightNoticedLine(); // 0b0010
        this.rightHasLine = lineFollowChecker.rightNoticedLine(); // 0b0001

        if (hasLineBits == (this.leftHasLine ? 8 : 0) + (this.midLeftHasLine ? 4 : 0) +
                (this.midRightHasLine ? 2 : 0) + (this.rightHasLine ? 1 : 0)) {
            changeNeoLeds = false;
        } else {
            changeNeoLeds = true;
        }
    }

    public void followHardCodedRoute(int counter) {

    }

    @Override
    protected void leave() {
        super.leave();
    }
}