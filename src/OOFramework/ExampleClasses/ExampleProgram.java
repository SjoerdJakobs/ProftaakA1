package OOFramework.ExampleClasses;
import OOFramework.FrameworkProgram;

    public class ExampleProgram extends FrameworkProgram
    {
        ExampleProgram()
        {

        }

        @Override
        protected void start()
        {
            super.start();

            System.out.println("Hello World!");


        }

        @Override
        protected void addToLoop()
        {
            super.addToLoop();

        }

        @Override
        protected void exitProgram()
        {
            super.exitProgram();
        }
    }

