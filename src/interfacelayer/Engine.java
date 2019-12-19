package interfacelayer;

import buttercat.HelpFunctions;
import hardwarelayer.actuators.Motor.Motor;
import TI.BoeBot;
import TI.Timer;

public class Engine {
    private Motor servoLeft;
    private Motor servoRight;

    /**** VARIABLES FOR UNUSED TURNDEGREES() ****/
    private int turnTime;
    private boolean needsToTurn;

    /**********************
    * VARIABLES OF SJOERD *
    ***********************/

    private int leftSpeed    = 0;
    private int rightSpeed   = 0;

    private int currentSpeed = 0;
    private int targetSpeed  = 0;

    private double turnRate          = 0;
    private double accelerateSpeed   = 0;

    private double fastWheel = 0;
    private double slowWheel = 0;

    private boolean left;

    /**************************
    * END VARIABLES OF SJOERD *
    ***************************/

    private NotificationSystem notificationSystem;

    /**
     * ORIGINAL TARGET SPEED: getter and setter
     * Used to save the original target speed and call for use when it's turning (because in a turn the targetSpeed is adjusted)
     */
    private int originalTargetSpeed;

    public int getOriginalTargetSpeed() { return this.originalTargetSpeed; }

    private void setOriginalTargetSpeed(int originalTargetSpeed) {
        HelpFunctions.checkValue("Engine original target speed", originalTargetSpeed, -250, 250);
        this.originalTargetSpeed = originalTargetSpeed;
    }

    /**
     * CONSTRUCTOR
     * @param pinServoLeft Pin connected to the left servo motor.
     * @param pinServoRight Pin connected to the right servo motor.
     */
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

    /**
     * Change the speed of a servo incrementally
     * @param servo The servo object
     */
    private void changeSpeed(Motor servo) {
        if (Math.abs(servo.getServo().getPulseWidth()) != servo.getTargetSpeed())
            servo.updateIncremental();
    }

    /**
     * To drive forward with the speed set using setTargetSpeed()
     * This function doesn't need to be used when using updateInstantPulse()
     */
    public void drive(int iterations) {
        for (int i = 0; i < iterations; i++) {
            drive();
        }
    }
    public void drive() {
        changeSpeed(servoLeft);
        changeSpeed(servoRight);
    }

    /**
     * Turn left while driving
     * @param turnRate turn rate with value 0: no turn, 1: stop right wheel, 2: drive right wheel backwards
     */
    public void turnRight(double turnRate) {
        servoRight.updateTurnTargetSpeed(servoRight.getTargetSpeed(), turnRate);
        servoLeft.updateTurnTargetSpeed(servoLeft.getTargetSpeed(), 0);
        notificationSystem.right();
    }

    /**
     * Turn right while driving
     * @param turnRate turn rate with value 0: no turn, 1: stop left wheel, 2: drive left wheel backwards
     */
    public void turnLeft(double turnRate) {
        servoRight.updateTurnTargetSpeed(servoRight.getTargetSpeed(), 0);
        servoLeft.updateTurnTargetSpeed(servoLeft.getTargetSpeed(), turnRate);
        notificationSystem.left();
    }

    /**
     * Stop turning regardless of the target speed
     */
    public void turnStop() {
        servoRight.updateTurnTargetSpeed(servoRight.getTargetSpeed(), 0);
        servoLeft.updateTurnTargetSpeed(servoLeft.getTargetSpeed(), 0);
        setEngineTargetSpeed(this.originalTargetSpeed);
    }

    /**
     * TODO: fix the this.originalTargetSpeed
     * Turn right while standing still (targetSpeed of 0)
     */
    public void stillturnRight() {
        setEngineTargetSpeed(this.originalTargetSpeed / 2);
        turnRight(2);
    }

    /**
     * TODO: fix the this.originalTargetSpeed
     * Turn left while standing still (targetSpeed of 0)
     */
    public void stillturnLeft() {
        setEngineTargetSpeed(this.originalTargetSpeed / 2);
        turnLeft(2);
    }

    /**
     * TODO: fix the this.originalTargetSpeed
     * Stop turning
     */
    public void stillturnStop() {
        setEngineTargetSpeed(0);
        turnStop();
    }

    public void driveStop() {
        setEngineTargetSpeed(0);
        notificationSystem.turnLedsOn();
    }


    public void driveBackward(int speed) {
        setEngineTargetSpeed(speed);
        notificationSystem.backwards();
    }

    public void driveForward(int speed) {
        setEngineTargetSpeed(speed * -1);
        notificationSystem.forward();
    }

    public void emergencyBrake() {
        servoLeft.stop();
        servoRight.stop();
        getMotorLeft().updateInstantPulse(0);
        getMotorRight().updateInstantPulse(0);
        setEngineTargetSpeed(0);
        notificationSystem.turnLedsOn();
    }

    public void continueDriving() {
        servoLeft.start();
        servoRight.start();
        notificationSystem.turnLedsOff();
    }

    /**
     * Set the target speed of both the servo's (thus the engine)
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
     * Set the target speed per servo instantly (without (de)acceleration).
     * @param turn The turn value between -2 and 2.
     */
    public void updateInstantTurn(double turn) {
        HelpFunctions.checkValue("Engine Instant pulse update turn value", turn, -2, 2);
        if (turn != 0) {
            if (turn > 0) {
                servoLeft.updateInstantPulse(this.originalTargetSpeed);
                servoRight.updateInstantPulse((int) (this.originalTargetSpeed - this.originalTargetSpeed * Math.abs(turn)));
                setServoTargetSpeed(servoLeft, this.originalTargetSpeed);
                setServoTargetSpeed(servoRight, (int) (this.originalTargetSpeed - this.originalTargetSpeed * Math.abs(turn)));
            } else {
                servoLeft.updateInstantPulse((int) (this.originalTargetSpeed - this.originalTargetSpeed * Math.abs(turn)));
                servoRight.updateInstantPulse(this.originalTargetSpeed);
                setServoTargetSpeed(servoLeft, (int) (this.originalTargetSpeed - this.originalTargetSpeed * Math.abs(turn)));
                setServoTargetSpeed(servoRight, this.originalTargetSpeed);
            }
        }
    }
    public void updateInstantPulse(int targetSpeed) {
        this.originalTargetSpeed = targetSpeed;
        setServoTargetSpeed(servoLeft, targetSpeed);
        setServoTargetSpeed(servoRight, targetSpeed);

        servoLeft.updateInstantPulse(targetSpeed);
        servoRight.updateInstantPulse(targetSpeed);
    }

    private Timer turnDegreesTimer = new Timer(this.turnTime);
    private boolean timerSwitch;
    private int amountTurned;

    private void driveDegrees(int degrees, int speed) {
        if (timerSwitch) {
            double temp = degrees * speed * (200d / 11d);
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
            setEngineTargetSpeed(speed);
        } else {
            turnStop();
        }
    }


    private int checkAmount(int amountOfTimes) {
        return amountOfTimes * 2;
    }

    /**
     * makes the BoeBot drive in a square
     */

    private void driveSquare(int amountOfTimes, int speed) {
        amountOfTimes = checkAmount(amountOfTimes);
        if (amountTurned <= amountOfTimes * 4)
            driveDegrees(90, speed);

    }

    /**
     * makes the BoeBot drive in a triangle
     */

    private void driveTriangle(int amountOfTimes, int speed) {
        amountOfTimes = checkAmount(amountOfTimes);
        if (amountTurned <= amountOfTimes * 3)
            driveDegrees(120, speed);

    }

    /**
     * makes the BoeBot drive in a circle
     */

    private void driveCircle(int amountOfTimes, int speed) {
        amountOfTimes = checkAmount(amountOfTimes);
        if (amountTurned <= amountOfTimes * 360)
            driveDegrees(1, speed);
    }

    public String toString() {
        return ("\nLeft motor:  " + this.servoLeft.toString() +
                "\nRight motor: " + this.servoRight.toString());
    }

    /**
     * (+)Getter for both the left and right motor
     */
    public Motor getMotorLeft() { return this.servoLeft; }
    public Motor getMotorRight() { return this.servoRight; }

    /*******************************************************************************************************************
     *******************************************************************************************************************
     *************************************                                        **************************************
     *************************************          ENGINE   OF   SJOERD          **************************************
     *************************************                                        **************************************
     *******************************************************************************************************************
     ******************************************************************************************************************/


    public void sJDrive()
    {
        sJChangeSpeed();
        sJTakeCorner();
        servoRight.getServo().update(rightSpeed);
        servoLeft.getServo().update(leftSpeed);
    }

    private void sJTakeCorner()
    {
        if(fastWheel != 0 || slowWheel != 0) {
            if (!left) {
                rightSpeed = (int) (1500 - (currentSpeed + fastWheel));
                leftSpeed = (int) (1500 + (currentSpeed + slowWheel));
            } else {
                rightSpeed = (int) (1500 - (currentSpeed + slowWheel));
                leftSpeed = (int) (1500 + (currentSpeed + fastWheel));
            }
        }
        if (turnRate != 0) {
            if (turnRate == 3) {
                if (!left) {
                    rightSpeed = (int) (1500 - (currentSpeed));
                    leftSpeed = (int) (1500 - (currentSpeed));
                } else {
                    rightSpeed = (int) (1500 + (currentSpeed));
                    leftSpeed = (int) (1500 + (currentSpeed));
                }
            }
            else if(turnRate > 1 && turnRate <= 2)
            {
                //System.out.println("super turn");
                if (!left) {
                    rightSpeed = (int) (1500 - (currentSpeed * (1.6)));
                    leftSpeed = (int) (1500 - (currentSpeed * (turnRate-1)));
                } else {
                    rightSpeed = (int) (1500 + (currentSpeed * (turnRate-1)));
                    leftSpeed = (int) (1500 + (currentSpeed * (1.6)));
                }
            }
            else if (turnRate <= 1)
            {
                //System.out.println("normal turn");
                if (!left) {
                    rightSpeed = (int) (1500 - (currentSpeed * (turnRate + 1)));
                    leftSpeed = (int) (1500 + (currentSpeed * (1 - turnRate)));
                } else {
                    rightSpeed = (int) (1500 - (currentSpeed * (1 - turnRate)));
                    leftSpeed = (int) (1500 + (currentSpeed * (turnRate + 1)));
                }
            }
        }
    }

    private void sJChangeSpeed()
    {
        if(currentSpeed != targetSpeed)
        {
            if(accelerateSpeed == 0)
            {
                currentSpeed = targetSpeed;
            }
            else
            {
                currentSpeed += accelerateSpeed;
                if(currentSpeed > targetSpeed)
                {
                    currentSpeed = targetSpeed;
                }
            }
            rightSpeed  = 1500 - currentSpeed;
            leftSpeed   = 1500 + currentSpeed;
        }
    }

    public void sJSetTargetSpeed(int speed, double accelerationSpeed)
    {
        targetSpeed = speed;
        if(targetSpeed < currentSpeed)
        {
            accelerateSpeed = (accelerationSpeed * -1);
        }
    }

    public void sJSteer(boolean left, double turnRate)
    {
        this.left = left;
        this.turnRate = turnRate;
        this.fastWheel = 0;
        this.slowWheel = 0;
    }

    public void sJManualSteer(boolean left, double fastWheelVal, double slowWheelVal)
    {
        this.left = left;
        this.turnRate = 0;
        this.slowWheel = slowWheelVal;
        this.fastWheel = fastWheelVal;
    }

}
