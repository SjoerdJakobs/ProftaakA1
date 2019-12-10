package hardwarelayer.sensors;

import buttercat.HelpFunctions;
import hardwarelayer.sensors.button.Button;
import interfacelayer.Callback;
import TI.BoeBot;
import java.util.ArrayList;

public class InfraRedReceiver
{
    public Callback somethingHasBeenPressed;
    private int pin;

    public InfraRedReceiver(int pin) {
        this.pin = pin;
    }

    public void checkForButtonPresses(ArrayList<Button> buttons, double deltaTime)
    {
        int pulseLen = BoeBot.pulseIn(this.pin, false, 6000);
        long number = 0;
        if (pulseLen > 2000) {
            int lengtes[] = new int[12];
            for (int i = 0; i < 12; i++) {
                lengtes[i] = BoeBot.pulseIn(pin, false, 20000);
            }
            number = readButtonReturnInt(lengtes);

            //System.out.println(number);

            for (Button button : buttons) {
                if (button.getAddress() == number) {
                    if (!button.isPressed()) {
                        //System.out.println("hey");
                        button.setPressed(true);
                        button.onButtonPress.run();
                        somethingHasBeenPressed.run();
                    } else if (button.isPressed() && button.isContinuousCallback()) {
                        //System.out.println("hey2");
                        button.onButtonPress.run();
                    }
                    button.setSincePressedCounter(0);
                } else if (button.isPressed()) {
                    if(button.getSincePressedCounter() > 0.1)
                    {
                        button.setPressed(false);
                    }
                    else
                    {
                        button.setSincePressedCounter(button.getSincePressedCounter()+deltaTime);
                    }
                }
            }
        } else
            for (Button button : buttons) {
                if (button.isPressed()) {
                    if(button.getSincePressedCounter() > 0.1)
                    {
                        button.setPressed(false);
                    }
                    else
                    {
                        button.setSincePressedCounter(button.getSincePressedCounter()+deltaTime);
                    }
                }
            }
        //BoeBot.wait(10);
    }

    public long readButtonReturnInt(int lengtes[])
    {
        long number = 0;
        for(int i = 0; i <12; i++)
        {
            if(Math.abs(lengtes[i] - 600) < 250)
            {
                number += (0 * (Math.pow(10, 11-i)));
            }
            else if(Math.abs(lengtes[i] - 1200) < 250)
            {
                number +=(1* (Math.pow(10, 11-i)));
            }
            else
            {
                number +=(2* (Math.pow(10, 11-i)));
            }
        }
        if(number > 219999999999l)
        {
            number = -1;
        }
        return number;
    }
}