package interfacelayer;

import Hardware.Actuators.Motor;

public class Engine {
    private Motor servoLeft = new Motor(14, true);
    private Motor servoRight = new Motor(15, false);

    private void driveForward() {
        for (int i = 20; i <= 300; i += 20) {
            servoLeft.updateInstantDifferential(i);
            servoRight.updateInstantDifferential(i);
        }
    }

    private void rotateLeft() {
        servoLeft.updateInstantDifferential(10);
    }

    private void rotateRight() {
        servoRight.stop();
    }

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

    //Let the Boebot stop immediately
    private void emergencyBreak() {
        servoLeft.updateInstantPulse(0);
        servoRight.updateInstantPulse(0);
    }

}
