package interfacelayer;

import Hardware.Sensors.ultrasonicsensor.UltraSonicSensor;
import OOFramework.FrameworkProgram;
import OOFramework.StandardObject;

public class ObjectDetection extends StandardObject {
    private UltraSonicSensor sensor;

    public ObjectDetection(FrameworkProgram frameworkProgram) {
        super(frameworkProgram);
        this.sensor = new UltraSonicSensor(7,8);
        System.out.println("Making new objectDetection");
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


    @Override
    protected void mainLoop(double deltaTime) {
        super.mainLoop(deltaTime);

        sensor.distanceCallback.run();

        checkDistance();
    }

    private void checkDistance() {
        if (sensor.getCm() < 10) {
            // stop
        }
    }


}
