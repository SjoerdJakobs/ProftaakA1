package MainPackage.Modules;

import java.util.ArrayList;

public class Receiver
{
    public void CheckForButtonPresses(ArrayList<Button> buttons)
    {
        for (Button button : buttons) {
            //if pressed
            button.SetPressed(true);
            if(!button.IsPressed())
            {
                button.onButtonPress.run();
            }
            else if(button.IsPressed() && button.isContinuousCallback())
            {
                button.onButtonPress.run();
            }
        }
    }
}
