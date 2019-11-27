package Interface;

import ButterCat.HelpFunctions;
import Hardware.Actuators.Motor;
import TI.Timer;

public class Engine {
    private Motor servoLeft;
    private Motor servoRight;
    private Timer turnTimer;

    public Engine(int pinServoLeft, int pinServoRight) {
        HelpFunctions.checkDigitalPin("Engine left servo pin", pinServoLeft);
        HelpFunctions.checkDigitalPin("Engine right servo pin", pinServoRight);

        servoLeft = new Motor(pinServoLeft, false);
        servoRight = new Motor(pinServoRight, true);
    }

    /**
     * (+)Getter and (-)setter for the target of the degrees in a turn
     */
    private int targetTurnDegrees;

    public int getTargetTurnDegrees() {
        return this.targetTurnDegrees;
    }

    private void setTargetTurnDegrees(int targetTurnDegrees) {
        HelpFunctions.checkValue("Engine servo turn rate", targetTurnDegrees, -180, 180);
        this.targetTurnDegrees = targetTurnDegrees;
    }

    /**
     * (+)Getter and (-)setter for the current degrees turned in a turn
     */
    private int currentTurnDegrees;

    public int getCurrentTurnDegrees() {
        return this.getCurrentTurnDegrees();
    }

    private void setCurrentTurnDegrees(int currentTurnDegrees) {
        HelpFunctions.checkValue("Engine current turn degrees", currentTurnDegrees, -180, 180);
        this.currentTurnDegrees = currentTurnDegrees;
    }

    /**
     * (+)Getter and (-)setter for the radius of a turn
     */
    private int turnRadius;

    public int getTurnRadius() {
        return this.turnRadius;
    }

    private void setTurnRadius(int turnRadius) {
        this.turnRadius = turnRadius;
    }

    /**
     * Change the speed of a servo incrementally
     *
     * @param servo The servo object
     */
    private void changeSpeed(Motor servo) {
        if (Math.abs(servo.getServo().getPulseWidth()) != servo.getTargetSpeed()) {
            //setServoTargetSpeed(servo, this.targetSpeed);
            servo.updateIncremental();
        }
    }

    /**
     * To drive forward with the speed set using setTargetSpeed()
     */
    public void driveForward() {
        changeSpeed(servoLeft);
        changeSpeed(servoRight);
    }

    /**
     * Set the specifics of the kind of turn is made using (+)turnDegrees()
     *
     * @param turnDegrees
     * @param turnRadius
     */
    public void setTurnSpecifics(int turnDegrees, int turnRadius) {
        setTargetTurnDegrees(turnDegrees);
        setTurnRadius(turnRadius);
        this.turnTimer = new Timer(turnRadius);
    }

    /**
     * Make a turn using the specifics set with (+)setTurnSpecifics
     */
    // TODO: create double turnRate (-1..1) to apply to each Motor (targetSpeed + (targetSpeed * turnRate)
    public void turnDegrees() {
        if (turnTimer.timeout()) {
            turnTimer.mark();
            if (this.targetTurnDegrees > 0 || this.targetTurnDegrees < 90) {
                changeSpeed(servoLeft);
                // TODO not finished yet!!!
            }
        }
    }

    /**
     * Stop immediately
     */
    public void emergencyBrake() {
        servoLeft.stop();
        servoRight.stop();
    }

    /**
     * Activate both servo motors
     */
    public void continueDriving() {
        servoLeft.start();
        servoRight.start();
    }

    /**
     * Set the target speed of both the servo's (thus the engine)
     *
     * @param targetSpeed The provided target speed which will be reached incrementally
     */
    public void setEngineTargetSpeed(int targetSpeed) {
        HelpFunctions.checkValue("Engine servo target speed", targetSpeed, -250, 250);
        setServoTargetSpeed(servoLeft, targetSpeed);
        setServoTargetSpeed(servoRight, targetSpeed);
    }

    /**
     * Set the target speed per servo (used in the (+)setEngineTargetSpeed)
     *
     * @param servo       The servo in question
     * @param targetSpeed The provided target speed which will be reached incrementally
     */
    private void setServoTargetSpeed(Motor servo, int targetSpeed) {
        servo.setTargetSpeed(servo.getTurningClockwise() ?
                servo.getMotionlessBaseValue() + targetSpeed : servo.getMotionlessBaseValue() - targetSpeed);
        // Previous if-statement:
        //servo.getServo().getPulseWidth() < servo.getTargetSpeed() ?
    }

    /**
     * (+)Getter for both the left and right motor
     */
    public Motor getMotorLeft() {
        return this.servoLeft;
    }

    public Motor getMotorRight() {
        return this.servoRight;
    }

    public String toString() {
        return ("\nLeft motor: " + this.servoLeft.toString() +
                "\nRight motor: " + this.servoRight.toString() +
                "\nCurrent turn degrees: " + this.currentTurnDegrees +
                "\nTarget turn degrees: " + this.targetTurnDegrees);
    }

    private Timer shapeTimer = new Timer(1000);
    private Timer circleTimer = new Timer(5);
    private int squareCounter = 0;
    private int triangleCounter = 0;
    private int circleCounter = 0;

    /**
     * makes the BoeBot drive in a square
     */
    public void driveSquare() {
        setCurrentTurnDegrees(360 / 4);
        if (squareCounter != 4) {
            if (shapeTimer.timeout()) {
                turnDegrees();
                squareCounter++;
            } else {
                driveForward();
            }
        }
    }

    /**
     * makes the BoeBot drive in a triangle
     */
    public void driveTriangle() {
        setCurrentTurnDegrees(360 / 3);
        if (triangleCounter != 3) {
            if (shapeTimer.timeout()) {
                turnDegrees();
                triangleCounter++;
            } else {
                driveForward();
            }
        }
    }

    /**
     * makes the BoeBot drive in a circle
     */
    public void driveCircle() {
        setCurrentTurnDegrees(1);
        if (circleCounter != 360) {
            if (circleTimer.timeout()) {
                turnDegrees();
                circleCounter++;
            } else {
                driveForward();
            }
        }
    }
}
