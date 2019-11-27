package Interface;

import Hardware.Sensors.ultrasonicsensor.UltraSonicSensor;
import OOFramework.FrameworkProgram;
import OOFramework.StandardObject;
import TI.Timer;

/**
 * object detection class to handle the logic for detecting objects
 */
public class ObjectDetection extends StandardObject {
    private UltraSonicSensor sensor;
    private Engine engine;

    private Timer timer;

    /**
     * makes a new objectDetection instance
     *
     * @param frameworkProgram the program to run it with
     */
    public ObjectDetection(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);

        timer = new Timer(5);
        // trigger pin: 7, echo pin: 8
        this.sensor = new UltraSonicSensor(7, 8);
    }

    /**
     * the main loop where everything is calculated
     *
     * @param deltaTime the time difference
     */
    @Override
    protected void mainLoop(double deltaTime) {
        super.mainLoop(deltaTime);

        sensor.onDistanceCallback().run();

//        System.out.println(sensor.getDistanceToObject());
         //System.out.println(objectIsTooClose());
    }

    /**
     * returns whether an object is too close or not
     *
     * @return true if an object is less than 10 cm away from the BoeBot
     */
    public boolean objectIsTooClose() {
        return objectIsTooClose(10);
    }

    /**
     * checks if an object is less than the given distance away
     *
     * @param distance the distance to check for in cm
     * @return true if an object is less than the distance amount away
     */
    public boolean objectIsTooClose(int distance) {
        return sensor.getDistanceToObject() < distance;
    }


}
