package Hardware.Sensors;

import ButterCat.Modules.Button;
import ButterCat.Modules.Callback;
import TI.BoeBot;
import java.util.ArrayList;

public class InfraRedReceiver
{
    public Callback somethingHasBeenPressed;

    public void checkForButtonPresses(ArrayList<Button> buttons)
    {
        for (Button button : buttons) {
            if (isThisButtonPressed(button.getAddress())) {
                button.setPressed(true);
                if (!button.isPressed()) {
                    button.setPressed(true);
                    button.onButtonPress.run();
                    somethingHasBeenPressed.run();
                } else if (button.isPressed() && button.isContinuousCallback()) {
                    button.onButtonPress.run();
                }
            } else if (button.isPressed()) {
                button.setPressed(false);
            }
        }
    }

    private boolean isThisButtonPressed(long address)
    {
        int pulseLen = BoeBot.pulseIn(15, false, 6000);
        long number = 0;
        if (pulseLen <= 2000) {
            int lengtes[] = new int[12];
            for (int i = 0; i < 12; i++) {
                lengtes[i] = BoeBot.pulseIn(15, false, 20000);
            }
            for (int i = 0; i < 12; i++) {
                System.out.print(lengtes[i]);
            }
            number = readButtonReturnInt(lengtes);

            System.out.println("");
            System.out.println(number);
        }
        return (number == address);
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