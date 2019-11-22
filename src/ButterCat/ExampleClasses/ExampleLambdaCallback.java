package ButterCat.ExampleClasses;

import ButterCat.Modules.Callback;

/**
 * the class which holds the callback and where the callback actualy can be called from
 */
public class ExampleLambdaCallback
{
    /**
     * create the callback.
     * if this callback gets called, anything that is in it will also be called
     * there can only be one method in the call back, if you put in a new one it will replace the old one
     */
    public Callback callback;
}


class AddMethodToCallBack
{
    /**
     * you need to have an instance of the class which holds the callback.
     * often you want an instance that already exists and it will be given to you
     * but for this example we will make a new one
     */
    ExampleLambdaCallback lambdaCallbackClass = new ExampleLambdaCallback();

    AddMethodToCallBack()
    {
        /**
         * you need to add the method that you want to the callback
         * we do this with a lambda
         * if there is already a method in the callback and you put a new one in to it
         * it will just replace the old one
         */
        lambdaCallbackClass.callback = () ->{
            thisMethodGetsPutInTheCallback();
        };
    }

    void thisMethodGetsPutInTheCallback()
    {
        System.out.println("hi");
    }
}
