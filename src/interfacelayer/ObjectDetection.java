package interfacelayer;

import Hardware.Sensors.ultrasonicsensor.UltraSonicSensor;
import OOFramework.FrameworkProgram;
import OOFramework.StandardObject;
import TI.Timer;

/**
 * object detection class to handle the logic for detecting objects
 */
public class ObjectDetection extends StandardObject {
    private UltraSonicSensor sensor;

    private Timer timer;

    /**
     * makes a new objectDetection instance
     * @param frameworkProgram the program to run it with
     */
    public ObjectDetection(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);

        timer = new Timer(5);
        // trigger pin: 7, echo pin: 8
        this.sensor = new UltraSonicSensor(7,8);
    }

    @Override
    protected void start() {
        super.start();
    }

    @Override
    protected void awake() {
        super.awake();
    }

    @Override
    protected void sleep()
    {
        super.sleep();
    }


    /**
     * the main loop where everything is calculated
     * @param deltaTime the time difference
     */
    @Override
    protected void mainLoop(double deltaTime) {
        super.mainLoop(deltaTime);

        sensor.onDistanceCallback().run();

        System.out.println(sensor.getDistanceToObject());
        checkDistance();
    }

    /**
     * checks the distance that is measured
     */
    private void checkDistance() {
//        System.out.println("Stoppen");
    }


}
