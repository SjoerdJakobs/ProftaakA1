package hardwarelayer.sensors.button;

import interfacelayer.Callback;

public class Button
{


    public Button(long address)
    {
        this(address, false);
    }
    public Button(long address, boolean continuousCallback)
    {
         this.address            = address;
         this.continuousCallback = continuousCallback;
         this.isPressed          = false;
         onButtonPress = () ->
         {
             emptyCallback();
         };
    }
    private void emptyCallback(){System.out.println("this callBack is empty, class: button    line: 19");  }


    public Callback onButtonPress;

    /**
     * if this is set to true the button will make continuously call onButtonPress when pressed
     * if this is set to false the button will only make one call to onButtonPress per press
     */
    private boolean continuousCallback;
    public  boolean isContinuousCallback()
    {
        return continuousCallback;
    }

    public  void    setContinuousCallback(boolean continuousCallback)
    {
        this.continuousCallback = continuousCallback;
    }

    private double sincePressedCounter = 0;
    public double getSincePressedCounter()
    {
        return sincePressedCounter;
    }

    public void setSincePressedCounter(double sincePressedCounter)
    {
        this.sincePressedCounter = sincePressedCounter;
    }

    private boolean isPressed;
    public  boolean isPressed()
    {
        return isPressed;
    }
    public  void    setPressed(boolean pressed)
    {
        isPressed = pressed;
    }

    private long    address;
    public  long    getAddress()
    {
        return address;
    }
    public  void    setAddress(long address)
    {
        this.address = address;
    }


}
