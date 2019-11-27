package Interface;

import ButterCat.HelpFunctions;
import Hardware.Actuators.Motor;
import TI.BoeBot;
import TI.Timer;

public class Engine {
    private Motor servoLeft;
    private Motor servoRight;
    private Timer turnTimer;
    private boolean needsToTurn;

    private NotificationSystem notificationSystem;

    private int turnTime;

    public Engine(int pinServoLeft, int pinServoRight) {
        HelpFunctions.checkDigitalPin("Engine left servo pin", pinServoLeft);
        HelpFunctions.checkDigitalPin("Engine right servo pin", pinServoRight);

        servoLeft = new Motor(pinServoLeft, false);
        servoRight = new Motor(pinServoRight, true);
        needsToTurn = false;

        notificationSystem = NotificationSystem.INSTANCE;

        turnTime = 0;
        turnDegreesTimer.mark();
        timerSwitch = true;
        amountTurned = 0;
    }

    private int originalTargetSpeed;

    public int getOriginalTargetSpeed() {
        return this.originalTargetSpeed;
    }

    private void setOriginalTargetSpeed(int originalTargetSpeed) {
        HelpFunctions.checkValue("Engine original target speed", originalTargetSpeed, -250, 250);
        this.originalTargetSpeed = originalTargetSpeed;
    }

    /**
     * (+)Getter and (-)setter for the target of the degrees in a turn
     */

    private int targetTurnRate;

    public int getTargetTurnRate() {
        return this.targetTurnRate;
    }

    private void setTargetTurnRate(int targetTurnRate) {
        HelpFunctions.checkValue("Engine servo turn rate", targetTurnRate, -180, 180);
        this.targetTurnRate = targetTurnRate;
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
    public void drive() {
        changeSpeed(servoLeft);
        changeSpeed(servoRight);
    }

    /**
     * Set the specifics of the kind of turn is made using (+)turnDegrees()
     *
     * @param turnDegrees
     * @param turnRadius
     */
    // TODO: currently not used
    public void setTurnSpecifics(int turnDegrees, int turnRadius) {
        setTargetTurnRate(turnDegrees);
        setTurnRadius(turnRadius);
        this.turnTimer = new Timer(turnRadius);
    }

    // TODO: CHANGE TO DOUBLE
    public void setTurnRate(double turnRate) {
        HelpFunctions.checkValue("Engine turn rate", turnRate, -1, 1);
    }


    public void turnLeft(double turnRate) {

        servoRight.updateTurnTargetSpeed(servoRight.getTargetSpeed(), turnRate);
        servoLeft.updateTurnTargetSpeed(servoLeft.getTargetSpeed(), 0);
        notificationSystem.left();
    }


    public void turnRight(double turnRate) {

        servoRight.updateTurnTargetSpeed(servoRight.getTargetSpeed(), 0);
        servoLeft.updateTurnTargetSpeed(servoLeft.getTargetSpeed(), turnRate);
        notificationSystem.right();
    }

    public void noTurn() {

        servoRight.updateTurnTargetSpeed(servoRight.getTargetSpeed(), 0);
        servoLeft.updateTurnTargetSpeed(servoLeft.getTargetSpeed(), 0);

        setEngineTargetSpeed(this.originalTargetSpeed);
    }

    public void stopDriving() {
        setEngineTargetSpeed(0);
        notificationSystem.turnLedsOn();
    }


    public void driveBackward(int speed) {
        setEngineTargetSpeed(speed);
        notificationSystem.backwards();
    }

    public void driveForward(int speed) {
        setEngineTargetSpeed(speed);
        notificationSystem.forward();
    }

    /**
     * Stop immediately
     */
    public void emergencyBrake() {
        servoLeft.stop();
        servoRight.stop();
        notificationSystem.turnLedsOn();
    }

    /**
     * Activate both servo motors
     */
    public void continueDriving() {
        servoLeft.start();
        servoRight.start();
        notificationSystem.forward();
    }

    /**
     * Set the target speed of both the servo's (thus the engine)
     *
     * @param targetSpeed The provided target speed which will be reached incrementally
     */
    public void setEngineTargetSpeed(int targetSpeed) {
        HelpFunctions.checkValue("Engine servo target speed", targetSpeed, -250, 250);
        this.originalTargetSpeed = targetSpeed;
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

    private Timer turnDegreesTimer = new Timer(this.turnTime);
    private boolean timerSwitch;
    private int amountTurned;

    public void driveDegrees(int degrees, int speed) {

        if (timerSwitch) {
            double temp = degrees * speed * (200 / 11);
            this.turnTime = (int) temp;
            turnDegreesTimer.setInterval(turnTime);
            timerSwitch = false;
            amountTurned = 0;

        }

        if (turnDegreesTimer.timeout()) {
            amountTurned++;

            needsToTurn = !needsToTurn;
            BoeBot.uwait(1);
        }

        if (needsToTurn) {
            turnLeft(0.1);
        } else {
            noTurn();
        }


    }


    private int checkAmount(int amountOfTimes) {
        return amountOfTimes * 2;
    }

    /**
     * makes the BoeBot drive in a square
     */

    public void driveSquare(int amountOfTimes, int speed) {
        amountOfTimes = checkAmount(amountOfTimes);
        if (amountTurned <= amountOfTimes * 4)
            driveDegrees(90, speed);

    }

    /**
     * makes the BoeBot drive in a triangle
     */

    public void driveTriangle(int amountOfTimes, int speed) {
        amountOfTimes = checkAmount(amountOfTimes);
        if (amountTurned <= amountOfTimes * 3)
        driveDegrees(120, speed);

    }

    /**
     * makes the BoeBot drive in a circle
     */

    public void driveCircle(int amountOfTimes, int speed) {
        amountOfTimes = checkAmount(amountOfTimes);
        if (amountTurned <= amountOfTimes * 360)
        driveDegrees(1, speed);
    }

    public String toString() {
        return ("\nLeft motor: " + this.servoLeft.toString() +
                "\nRight motor: " + this.servoRight.toString() +
                "\nCurrent turn degrees: " + this.currentTurnDegrees +
                "\nTarget turn degrees: " + this.targetTurnRate);

    }

}
