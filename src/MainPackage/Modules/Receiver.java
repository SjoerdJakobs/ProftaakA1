package MainPackage.Modules;

import java.util.ArrayList;

public class Receiver
{
    public void checkForButtonPresses(ArrayList<Button> buttons)
    {
        for (Button button : buttons) {
            //if pressed
            button.setPressed(true);
            if(!button.isPressed())
            {
                button.onButtonPress.run();
            }
            else if(button.isPressed() && button.isContinuousCallback())
            {
                button.onButtonPress.run();
            }
        }
    }
}
