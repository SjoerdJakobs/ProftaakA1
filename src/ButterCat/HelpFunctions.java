package ButterCat;

public class HelpFunctions {

    /**
     * Checks if a valid digital pin number is given.
     * @param argumentName The name of the argument to print in the Exception message.
     * @param argumentValue The value of the argument to check
     * @throws IllegalArgumentException
     */
    public static void checkDigitalPin(String argumentName, int argumentValue ) throws IllegalArgumentException {
        if (argumentValue < 0 || argumentValue > 15)
            throw new IllegalArgumentException(argumentName + " value must be between 0 and 15. Given value: " + argumentValue + ".");
    }

    /**
     * Checks if a valid analog pin number is given.
     * @param argumentName The name of the argument to print in the Exception message.
     * @param argumentValue The value of the argument to check
     * @throws IllegalArgumentException
     */
    public static void checkAnalogPin(String argumentName, int argumentValue ) throws IllegalArgumentException {
        if (argumentValue < 0 || argumentValue > 3)
            throw new IllegalArgumentException(argumentName + " value must be between 0 and 3. Given value: " + argumentValue + ".");
    }

    /**
     * Checks if a valid value is given.
     * @param argumentName The name of the argument to print in the Exception message.
     * @param argumentValue The value of the argument to check
     * @throws IllegalArgumentException
     */
    public static void checkValue(String argumentName, double argumentValue, double minValue, double maxValue) throws IllegalArgumentException {
        if (argumentValue < minValue || argumentValue > maxValue)
            throw new IllegalArgumentException(argumentName + " value must be between " + minValue + " and " + maxValue +
                    ". Given value: " + argumentValue + ".");
    }

}
