package statemachine.states;

import buttercat.DriverAI;
import hardwarelayer.sensors.linefollower.LineFollower;
import interfacelayer.Engine;
import buttercat.Remote;
import interfacelayer.LineFollowChecker;
import ooframework.FrameworkProgram;
import statemachine.State;
import statemachine.StateID;

public class FollowRoute extends State {
    DriverAI driverAI;
    Engine engine;
    Remote remote;

    LineFollowChecker lineFollowChecker;

    private boolean shouldGoToRemoteControl;

    public FollowRoute(DriverAI driverAI) {
        super(StateID.FollowRoute);
        shouldGoToRemoteControl = false;
        this.driverAI = driverAI;
        this.engine = driverAI.getEngine();
        this.remote = driverAI.getRemote();

        this.lineFollowChecker = new LineFollowChecker(new FrameworkProgram());
    }

    @Override
    protected void enter() {
        super.enter();
        remote.aButtonHasBeenPressed = this::setShouldGoToRemoteControlToTrue;
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

        //TODO test this with boebot
        if (lineFollowChecker.leftNoticedLine())
            engine.turnLeft(0.8);
        if (lineFollowChecker.rightNoticedLine())
            engine.turnRight(0.8);
        if (lineFollowChecker.midNoticedLine()) {
            engine.noTurn();
            engine.driveForward(250);
        }

    }

    @Override
    protected void leave() {
        super.leave();
    }
}
