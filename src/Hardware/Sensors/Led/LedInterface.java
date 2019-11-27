package Hardware.Sensors.Led;

public interface LedInterface {
        void turnOn();
        void turnOff();
        void blink(double frequency);
        boolean getStatus();
}
