package interfacelayer;

import hardwarelayer.sensors.ultrasonicsensor.UltraSonicSensor;
import ooframework.FrameworkProgram;
import ooframework.StandardObject;

/**
 * object detection class to handle the logic for detecting objects
 */
public class ObjectDetection extends StandardObject {
    private UltraSonicSensor sensor;

    /**
     * makes a new objectDetection instance
     *
     * @param frameworkProgram the program to run it with
     */
    public ObjectDetection(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);

        // trigger pin: 7, echo pin: 8
        this.sensor = new UltraSonicSensor(10, 9);
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

            //System.out.println(sensor.getDistanceToObject());
            //System.out.println(objectIsTooClose());
    }

    /**
     * returns whether an object is too close or not
     *
     * @return true if an object is less than 100 mm away from the BoeBot
     */
    public boolean objectIsTooClose() {
        return objectIsTooClose(100);
    }

    /**
     * checks if an object is less than the given distance away
     *
     * @param distance the distance to check for in mm
     * @return true if an object is less than the distance amount away
     */
    public boolean objectIsTooClose(int distance) {
        return sensor.getDistanceToObject() < distance;
    }

    public int getDistance() {
        return this.sensor.getDistanceToObject();
    }
}
