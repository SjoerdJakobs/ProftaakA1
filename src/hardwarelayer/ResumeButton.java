package hardwarelayer;

public class ResumeButton {
    private int pin;
    private boolean canResume;

    public ResumeButton(int pin) {
        this.pin = pin;
        this.canResume = true;
    }

    public boolean resume(){
        this.canResume = !this.canResume;
        return this.canResume;
    }
}