package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortException;

public class WindowsMain extends Application{
    private Stage stage;

    public static void main(String[] args) {
        launch(WindowsMain.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));

        //TODO add speed to manual control
        //TODO add power button to manual control
        //TODO add mute button to manual control

        Scene scene = new Scene(root);
        scene.getStylesheets().add("gui/stylesheet.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Buttercat");
        stage.show();
    }

    public Stage getStage() {
        return this.stage;
    }

    public void Bluetooth() {
        System.out.println("Entering bluetooth method");
        SerialPort serialPort = new SerialPort("COM6");
        System.out.println("After intitializing serialPort");
        int[] commands = new int[5];
        commands[0] = 250;
        commands[1] = 118;
        commands[2] = 117;
        commands[3] = 116;
        commands[4] = 251;

        try {
            System.out.println("Before opening the port");
            serialPort.openPort();
            System.out.println("Opened the port");

            serialPort.setParams(SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            System.out.println("Set params");
            serialPort.writeIntArray(commands);
            System.out.println("commands has been send");
//            byte[] buffer;
//
//            buffer = serialPort.readBytes(1);
//            serialPort.closePort();
        } catch (SerialPortException ex) {
            ex.printStackTrace();
        }
    }
}
