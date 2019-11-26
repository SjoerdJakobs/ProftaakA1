package Interface;

    import ButterCat.HelpFunctions;
    import Hardware.Actuators.Motor;
    import TI.Timer;

public class Engine {
    private Motor servoLeft;
    private Motor servoRight;
    private Timer timer;

    public Engine(int pinServoLeft, int pinServoRight) {
        HelpFunctions.checkDigitalPin("Engine left servo pin", pinServoLeft);
        HelpFunctions.checkDigitalPin("Engine right servo pin", pinServoRight);

        servoLeft = new Motor(pinServoLeft, false);
        servoRight = new Motor(pinServoRight, true);
    }

    // TODO: move targetSpeed to Motor class to create individual targetSpeed
    private int targetSpeed;
    public int getTargetSpeed() { return this.targetSpeed; }
    public void setTargetSpeed(int targetSpeed) {
        HelpFunctions.checkValue("Engine servo target speed", targetSpeed, -250, 250);
        this.targetSpeed = targetSpeed;
    }

    private int targetTurnDegrees;
    public int getTargetTurnDegrees() { return this.targetTurnDegrees; }
    private void setTargetTurnDegrees(int targetTurnDegrees) {
        HelpFunctions.checkValue("Engine servo turn rate", targetTurnDegrees, -180, 180);
        this.targetTurnDegrees = targetTurnDegrees;
    }

    private int currentTurnDegrees;
    public int getCurrentTurnDegrees() { return this.getCurrentTurnDegrees(); }
    private void setCurrentTurnDegrees(int currentTurnDegrees) {
        HelpFunctions.checkValue("Engine current turn degrees", currentTurnDegrees,-180,180);
        this.currentTurnDegrees = currentTurnDegrees;
    }

    private int turnRadius;
    public int getTurnRadius() { return this.turnRadius; }
    private void setTurnRadius(int turnRadius) {
        this.turnRadius = turnRadius;
    }

    public int getCurrentSpeedServoLeft() { return servoLeft.getServo().getPulseWidth(); }
    public int getCurrentSpeedServoRight() { return servoRight.getServo().getPulseWidth(); }

    private void changeSpeed(Motor servo) {
        if (Math.abs(servo.getServo().getPulseWidth() - servo.getMotionlessBaseValue()) != Math.abs(this.targetSpeed)) {
            servo.updateInstantDifferential((servo.getServo().getPulseWidth() < servo.getMotionlessBaseValue() + this.targetSpeed ? 1 : -1));
        }
    }

    public void driveForward() {
        changeSpeed(servoLeft);
        changeSpeed(servoRight);
    }

    public void setTurnSpecifics(int turnDegrees, int turnRadius) {
        setTargetTurnDegrees(turnDegrees);
        setTurnRadius(turnRadius);
        this.timer = new Timer(turnRadius);
    }

    public void turnDegrees() {
        if (timer.timeout()) {
            timer.mark();
            if (this.targetTurnDegrees > 0 || this.targetTurnDegrees < 90) {

            }

        }
    }

    public void emergencyBreak() {
        servoLeft.stop();
        servoRight.stop();
    }

    public void continueDriving() {
        servoLeft.start();
        servoRight.start();
    }

    public Motor getMotorLeft() { return this.servoLeft; }
    public Motor getMotorRight() { return this.servoRight; }

/*
    //Deaccelerate and evantually stops the BoeBot
    private void off() {
        for (int i = 0; servoLeft.getMotor().getPulseWidth() == servoLeft.getMotionlessBaseValue() &&
                        servoRight.getMotor().getPulseWidth() == servoRight.getMotionlessBaseValue(); i++) {
                if (servoLeft.getMotor().getPulseWidth() > 1500) {
                        servoLeft.updateInstantDifferential(-i);
                    }
                if (servoRight.getMotor().getPulseWidth() < 1500) {
                        servoRight.updateInstantDifferential(-i);
                    }
            }
    }

*/

    public String toString() { return ("\nLeft motor:" +
            "\n- " + this.servoLeft.toString() +
            "\n- Current speed: " + this.getCurrentSpeedServoLeft() +
            "\nRight motor:" +
            "\n- " + this.servoRight.toString() +
            "\n- Current speed: " + this.getCurrentSpeedServoRight() +
            "\nTarget speed: " + this.targetSpeed +
            "\nCurrent turn degrees: " + this.currentTurnDegrees +
            "\nTarget turn degrees: " + this.targetTurnDegrees);}
}
