package TEMP;

import TI.Servo;

public class TempEngine
{
    public TempEngine()
    {
        shouldDrive = true;
    }

    Servo rightWheel    = new Servo(14);
    Servo leftWheel     = new Servo(15);

    public int leftSpeed    = 0;
    public int rightSpeed   = 0;

    public int currentSpeed = 0;
    public int targetSpeed  = 0;

    public double turnRate          = 0;
    public double accelerateSpeed   = 0;

    public double fastWheel = 0;
    public double slowWheel = 0;


    public boolean left;
    public boolean shouldDrive;

    public void drive()
    {
        //System.out.println("is driving");
        if(shouldDrive)
        {
            ChangeSpeed();
            TakeCorner();
            //System.out.println("rightSpeed" + rightSpeed);
            //System.out.println("leftSpeed" + leftSpeed);
            //System.out.println("currentSpeed" + currentSpeed);
            //System.out.println("targetSpeed" + targetSpeed);
            //System.out.println("turnRate" + turnRate);
            rightWheel.update(rightSpeed);
            leftWheel.update(leftSpeed);
        }
    }

    private void TakeCorner()
    {
        if(fastWheel != 0 || slowWheel != 0) {
            if (!left) {
                rightSpeed = (int) (1500 - (currentSpeed + fastWheel));
                leftSpeed = (int) (1500 + (currentSpeed + slowWheel));
            } else {
                rightSpeed = (int) (1500 - (currentSpeed + slowWheel));
                leftSpeed = (int) (1500 + (currentSpeed + fastWheel));
            }
        }
        if (turnRate != 0) {
            if (turnRate == 3) {
                if (!left) {
                    rightSpeed = (int) (1500 - (currentSpeed));
                    leftSpeed = (int) (1500 - (currentSpeed));
                } else {
                    rightSpeed = (int) (1500 + (currentSpeed));
                    leftSpeed = (int) (1500 + (currentSpeed));
                }
            }
            else if(turnRate > 1 && turnRate <= 2)
            {
                //System.out.println("super turn");
                if (!left) {
                    rightSpeed = (int) (1500 - (currentSpeed * (1.6)));
                    leftSpeed = (int) (1500 - (currentSpeed * (turnRate-1)));
                } else {
                    rightSpeed = (int) (1500 + (currentSpeed * (turnRate-1)));
                    leftSpeed = (int) (1500 + (currentSpeed * (1.6)));
                }
                /*if (!left) {
                    rightSpeed = (int) (1500 - (currentSpeed * ((turnRate-1)+ 1)));
                    leftSpeed = (int) (1500 - (currentSpeed * (1 - (turnRate-1))));
                } else {
                    rightSpeed = (int) (1500 + (currentSpeed * (1 - (turnRate-1))));
                    leftSpeed = (int) (1500 + (currentSpeed * ((turnRate-1) + 1)));
                }*/
            }
            else if (turnRate <= 1)
            {
                //System.out.println("normal turn");
                if (!left) {
                    rightSpeed = (int) (1500 - (currentSpeed * (turnRate + 1)));
                    leftSpeed = (int) (1500 + (currentSpeed * (1 - turnRate)));
                } else {
                    rightSpeed = (int) (1500 - (currentSpeed * (1 - turnRate)));
                    leftSpeed = (int) (1500 + (currentSpeed * (turnRate + 1)));
                }
            }
        }

    }

    private void ChangeSpeed()
    {
        if(currentSpeed != targetSpeed)
        {
            if(accelerateSpeed == 0)
            {
                currentSpeed = targetSpeed;
            }
            else
            {
                currentSpeed += accelerateSpeed;
                if(currentSpeed > targetSpeed)
                {
                    currentSpeed = targetSpeed;
                }
            }
            rightSpeed  = 1500 - currentSpeed;
            leftSpeed   = 1500 + currentSpeed;
        }
    }

    public void SetTargetSpeed(int speed, double accelerationSpeed)
    {
        targetSpeed = speed;
        if(targetSpeed < currentSpeed)
        {
            accelerateSpeed = (accelerationSpeed * -1);
        }
    }

    public void Steer(boolean left, double turnRate)
    {
        this.left = left;
        this.turnRate = turnRate;
        this.fastWheel = 0;
        this.slowWheel = 0;
    }

    public void ManualSteer(boolean left, double fastWheelVal, double slowWheelVal)
    {
        this.left = left;
        this.turnRate = 0;
        this.slowWheel = slowWheelVal;
        this.fastWheel = fastWheelVal;
    }
}

