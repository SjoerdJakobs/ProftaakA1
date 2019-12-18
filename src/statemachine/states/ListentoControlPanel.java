package statemachine.states;


import TI.SerialConnection;
import buttercat.ControlPanel;
import buttercat.DriverAI;
import buttercat.Remote;
import interfacelayer.Engine;
import interfacelayer.NotificationSystem;
import interfacelayer.ObjectDetection;
import statemachine.State;
import statemachine.StateID;

import java.util.ArrayList;

public class ListentoControlPanel extends State {

    DriverAI driverAI;
    Engine engine;
    Remote remote;
    ControlPanel controlPanel;
    ObjectDetection objectDetection;

    private boolean shouldReturnControlToAi;
    private boolean hasAnyButtonHasBeenPressed;
    private boolean canGoForward;
    private double sumDeltaTime = 0;
    private int engineTargetSpeed = 125;
    private NotificationSystem notificationSystem;
    private int objectDetectionDistance = 80; // in millimeters
    private boolean muted = false;
    private int buzzerFrequency = 1000;
    private ArrayList<Integer> commands;
    private boolean listen;

    private boolean shouldGoToRemoteControl;

    public ListentoControlPanel(DriverAI driverAI) {
        super(StateID.ListenToControlPanel);
        shouldGoToRemoteControl = false;
        shouldReturnControlToAi = false;
        hasAnyButtonHasBeenPressed = false;
        canGoForward = true;
        this.driverAI = driverAI;
        this.engine = driverAI.getEngine();
        this.remote = driverAI.getRemote();
        this.controlPanel = driverAI.getControlPanel();
        this.objectDetection = driverAI.getObjectDetection();
        this.notificationSystem = NotificationSystem.INSTANCE;
        this.commands = new ArrayList<>();
        this.listen = false;
    }

    @Override
    protected void enter() {
        super.enter();
        System.out.println("Entered Bluetooth State");
        shouldReturnControlToAi = false;
        setAsciiButtons();
        remote.aButtonHasBeenPressed = () -> {
            setShouldGoToRemoteControlToTrue();
        };
        System.out.println("leaving enter method");
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
        if (shouldReturnControlToAi) {
            stateMachine.SetState(StateID.GetRoute);
        }
    }

    private void setAsciiButtons() {
        System.out.println("Setting the asciibuttons");
        controlPanel.getWButton().onButtonPress = () -> {
            System.out.println("drive"); driveForward();
        };        System.out.println("W");

        controlPanel.getSButton().onButtonPress = () -> {
            driveBackwards();
        };
        controlPanel.getAButton().onButtonPress = () -> {
            driveLeft();
        };System.out.println("a");
        controlPanel.getDButton().onButtonPress = () -> {
            driveRight();
        };
        controlPanel.getEscButton().onButtonPress = () -> {
            returnToAiControl();
        };
        controlPanel.getMButton().onButtonPress = () -> {
            muteBuzzer();
        };System.out.println("m");
        controlPanel.getZButton().onButtonPress = () -> {
            turn90DegreesLeft();
        };
        controlPanel.getCButton().onButtonPress = () -> {
            turn90DegreesRight();
        };System.out.println("c");
        controlPanel.getQButton().onButtonPress = () -> {
            turn180DegreesLeft();
        };
        controlPanel.getEButton().onButtonPress = () -> {
            turn180DegreesRight();
        };System.out.println("e");
        controlPanel.getSpaceButton().onButtonPress = () -> {
            noSpeed();
        };
        controlPanel.getOneButton().onButtonPress = () -> {
            slowSpeed();
        };System.out.println("1");
        controlPanel.getTwoButton().onButtonPress = () -> {
            mediumSpeed();
        };
        controlPanel.getThreeButton().onButtonPress = () -> {
            fastSpeed();
        };System.out.println("3");
        controlPanel.getPButton().onButtonPress = () -> {
            listen();
        };
        System.out.println("asciibuttons set");
    }

    private void driveForward() {
        if (canGoForward) {
            engine.turnStop();
            engine.driveForward(this.engineTargetSpeed);
            System.out.println("forward");
        }
    }

    private void driveLeft() {
        if (this.canGoForward) {
            this.engine.turnLeft(0.6);
            System.out.println("left");
        }
    }

    private void driveBackwards() {
        this.engine.turnStop();
        this.engine.driveBackward(this.engineTargetSpeed);

        System.out.println("backwards");
    }

    private void driveRight() {
        if (this.canGoForward) {
            this.engine.turnRight(0.6);
            System.out.println("right");
        }
    }

    private void noSpeed() {
        this.engine.driveStop();
        System.out.println("noSpeed");
    }

    public void muteBuzzer() {
        this.muted = !this.muted;
    }

    private void turn90DegreesLeft() {
        if (this.canGoForward) {
            System.out.println("turn90DegreesLeft");
        }
    }

    private void turn90DegreesRight() {
        if (this.canGoForward) {
            System.out.println("turn90DegreesRight");
        }
    }

    private void turn180DegreesLeft() {
        System.out.println("turn180DegreesLeft");
    }

    private void turn180DegreesRight() {
        System.out.println("turn180DegreesRight");
    }

    private void slowSpeed() {
        this.engineTargetSpeed = 50;
        this.engine.setEngineTargetSpeed(this.engineTargetSpeed);
        System.out.println("slowSpeed");
    }

    private void mediumSpeed() {
        this.engineTargetSpeed = 125;
        this.engine.setEngineTargetSpeed(this.engineTargetSpeed);
        System.out.println("mediumSpeed");
    }

    private void fastSpeed() {
        this.engineTargetSpeed = 200;
        this.engine.setEngineTargetSpeed(this.engineTargetSpeed);
        System.out.println("fastSpeed");
    }

    private void listen() {
        SerialConnection conn = new SerialConnection();
        System.out.println("enters listen");

        int data = conn.readByte();
        System.out.println("received: " + data);

        if (data == controlPanel.getPButton().getAscii() && !this.listen) {
            this.listen = true;
            this.commands.clear();
            System.out.println("cleared commands");
            controlPanel.getPButton().setPressed(true);
            controlPanel.getPButton().onButtonPress.run();
            return;
        } else if (data == controlPanel.getPButton().getAscii() && this.listen && !this.commands.isEmpty()) {
            this.listen = false;
            controlPanel.getPButton().setPressed(false);
            System.out.println("stop listening");
            return;
        } else if (data != controlPanel.getPButton().getAscii()) {
            this.commands.add(data);
            System.out.println("added command: " + data);
            System.out.println(this.commands.toString());
        } else {
            System.out.println("didn't do anything");
        }
    }

    //        if (data != controlPanel.getPButton().getAscii()) {
//            conn.writeByte(data);
////        if(ascii == ascii van o) {
////            stopListen();
////        }
//            if (!controlPanel.getPButton().isPressed()) {
//                controlPanel.getPButton().setPressed(true);
//                commands.add(data);
//                controlPanel.getPButton().onButtonPress.run();
//            } else if (controlPanel.getOButton().getAscii() == data) {
//                controlPanel.getPButton().setPressed(false);
//                controlPanel.getOButton().setPressed(false);
//            } else if (controlPanel.getPButton().isPressed()) {
//                commands.add(data);
//                controlPanel.getPButton().onButtonPress.run();
//            }
//        }
//    }

    private void returnToAiControl() {
        this.shouldReturnControlToAi = true;
        System.out.println("on/off");
//        on = !on;
//        if (on) {
//            notificationSystem.remoteControll();
//        } else {
//            notificationSystem.noRemoteControl();
//        }
    }

    private void anyButtonHasBeenPressed() {
        this.hasAnyButtonHasBeenPressed = true;
    }

    @Override
    protected void logic() {
        super.logic();
        System.out.println("in logic");
        this.canGoForward = !this.objectDetection.objectIsTooClose(this.objectDetectionDistance);
        if (this.hasAnyButtonHasBeenPressed) {
            notificationSystem.noRemoteControl();
            this.hasAnyButtonHasBeenPressed = false;
        }

        // Each 0.001s the engine will update the servo motors' speed.
        sumDeltaTime += stateMachine.getDeltaTime();
        if (sumDeltaTime >= 0.001) {

            sumDeltaTime = 0;
            engine.drive();
        }

        // Slow down if an object is detected and stops at the distance objectDetectionDistance
        this.canGoForward = !this.objectDetection.objectIsTooClose(this.objectDetectionDistance);
        if (this.canGoForward) {
            if (this.objectDetection.objectIsTooClose(this.objectDetectionDistance + 200)) {
                this.engine.setEngineTargetSpeed(this.engineTargetSpeed - this.engineTargetSpeed *
                        ((200 - (this.objectDetection.getDistance() - this.objectDetectionDistance)) / 200));
            }
        }

        if (!this.muted) this.notificationSystem.makeSound(this.buzzerFrequency, 10);

        // If can go forward and wants to go forward (targetSpeed > 0) than stop immediately and make sound if not muted
        if (!this.canGoForward && this.engine.getOriginalTargetSpeed() > 0) {
            this.notificationSystem.objectDetected();
            this.engine.emergencyBrake();
            this.buzzerFrequency = 10000;
        } else {
            if (this.buzzerFrequency != 1000) this.buzzerFrequency = 5000;
        }
    }

    @Override
    protected void leave() {
        super.leave();
        engine.emergencyBrake();
    }

}
