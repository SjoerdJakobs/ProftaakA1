package Hardware.Actuators;

import ButterCat.HelpFunctions;
import TI.Servo;

public class Motor {

    private Servo servo;
    private int motionlessBaseValue;
    private boolean turningClockwise;

    /**
     * Initialise the Motor variables with standard motionlessBaseValue of 1500.
     * @param pin The servo pin which is used to control the servo.
     * @param turningClockwise Determines in which direction the servo rotates.
     */
    public Motor(int pin, boolean turningClockwise) {
        HelpFunctions.checkDigitalPin("Servo pin", pin);

        this.servo = new Servo(pin);
        this.turningClockwise = turningClockwise;
        this.motionlessBaseValue = 1500;
    }

    /**
     * Initialise the Motor variables.
     * @param pin The servo pin which is used to control the servo.
     * @param turningClockwise Determines in which direction the servo rotates.
     * @param motionlessBaseValue The base value of when the servo does not rotate.
     */
    public Motor(int pin, boolean turningClockwise, int motionlessBaseValue) {
        HelpFunctions.checkDigitalPin("Servo pin", pin);
        HelpFunctions.checkValue("Motor (servo) motionless base value", motionlessBaseValue, 750, 2250);
        this.servo = new Servo(pin);
        this.turningClockwise = turningClockwise;
        this.motionlessBaseValue = motionlessBaseValue;
    }

    public void start() { servo.start(); }
    public void stop() { servo.stop(); }

    /**
     * Increase or decrease the rotation speed of the servo instantly without acceleration based on the current speed.
     * @param differentialSpeed The differential to increase or decrease the current speed.
     */
    public void updateInstantDifferential(int differentialSpeed) {
        HelpFunctions.checkValue("Motor (servo) differential speed",
                Math.abs(differentialSpeed + this.servo.getPulseWidth() - motionlessBaseValue),0, 250);
        differentialSpeed = this.turningClockwise ? differentialSpeed : differentialSpeed * -1;
        servo.update(servo.getPulseWidth() + differentialSpeed);
    }

    /**
     * Update the rotation speed of the servo instantly with a new pulse width.
     * @param pulseWidth The new pulse with of the servo.
     */
    public void updateInstantPulse(int pulseWidth) {
        HelpFunctions.checkValue("Servo pulse width", pulseWidth,
                this.motionlessBaseValue - 250, this.motionlessBaseValue + 250);
        pulseWidth = this.turningClockwise ? pulseWidth : pulseWidth * -1;
        servo.update(this.motionlessBaseValue + pulseWidth);
    }

    /**
     * Duplicate the settings of another servo to this servo.
     * @param servo Copy the settings from this servo.
     */
    public void duplicateSettings(Motor servo) {
        this.motionlessBaseValue = servo.getMotionlessBaseValue();
        this.turningClockwise = servo.getTurningClockwise();
    }

    /**
     * Getters and setters for each variable (not objects).
     */
    public Servo getServo() { return this.servo; }
    public int getMotionlessBaseValue() { return this.motionlessBaseValue; }
    public boolean getTurningClockwise() { return this.turningClockwise; }

    public void setMotionlessBaseValue(int motionlessBaseValue) { this.motionlessBaseValue = motionlessBaseValue; }
    public void setTurningClockwise(boolean turningClockwise) { this.turningClockwise = turningClockwise; }

    public String toString() { return ("MotionlessBaseValue: " + this.motionlessBaseValue + " and isTurningClockwise: " + this.turningClockwise); }
}
