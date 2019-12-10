package StateMachine.States;

import ButterCat.ControlPanel;
import ButterCat.DriverAI;
import ButterCat.Remote;
import Interface.Engine;
import Interface.NotificationSystem;
import Interface.ObjectDetection;
import StateMachine.State;
import StateMachine.StateID;

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

    private boolean shouldGoToRemoteControl;

    public ListentoControlPanel(DriverAI driverAI)
    {
        super(StateID.ListenToControlPanel);
        shouldGoToRemoteControl = false;
        shouldReturnControlToAi = false;
        hasAnyButtonHasBeenPressed = false;
        canGoForward = true;
        this.driverAI = driverAI;
        this.engine = driverAI.getEngine();
        this.remote = driverAI.getRemote();
        this.objectDetection = driverAI.getObjectDetection();
        this.notificationSystem = NotificationSystem.INSTANCE;
    }

    @Override
    protected void enter()
    {
        super.enter();
        shouldReturnControlToAi = false;
        remote.aButtonHasBeenPressed = () ->{
            setShouldGoToRemoteControlToTrue();};
    }

    private void setShouldGoToRemoteControlToTrue()
    {
        shouldGoToRemoteControl = true;
    }

    @Override
    protected void checkForStateSwitch()
    {
        super.checkForStateSwitch();
        if (shouldGoToRemoteControl)
        {
            stateMachine.SetState(StateID.ListenToRemote);
        }
        if(shouldReturnControlToAi) {
            stateMachine.SetState(StateID.GetRoute);
        }
    }

    private void setAsciiButtons()
    {
        controlPanel.getwButton().onButtonPress = () -> driveForward();
    }

    private void returnToAiControl()
    {
        shouldReturnControlToAi = true;
//        on = !on;
//        if (on) {
//            notificationSystem.remoteControll();
//        } else {
//            notificationSystem.noRemoteControl();
//        }
        System.out.println("on/off");

    }

    public void driveForward(){
        if (canGoForward) {
            engine.noTurn();
            engine.driveForward(this.engineTargetSpeed);
            System.out.println("forward");
        }
    }

    @Override
    protected void logic() {
        super.logic();
        canGoForward = !objectDetection.objectIsTooClose(8);

        sumDeltaTime += stateMachine.getDeltaTime();
        if (sumDeltaTime >= 0.001) {

            sumDeltaTime = 0;
            engine.drive();
        }

        if (!canGoForward && engine.getOriginalTargetSpeed() > 0) {
            notificationSystem.objectDetected();
            engine.emergencyBrake();
            System.out.println("noticed object on " + objectDetection.getDistance());
            engine.getMotorLeft().updateInstantPulse(0);
            engine.getMotorRight().updateInstantPulse(0);
            engine.setEngineTargetSpeed(0);
            notificationSystem.makeSound();
//            engine.getMotorLeft().updateInstantPulse(1500);
//            engine.getMotorRight().updateInstantPulse(1500);
        }
    }

    @Override
    protected void leave() {
        super.leave();
    }

}
