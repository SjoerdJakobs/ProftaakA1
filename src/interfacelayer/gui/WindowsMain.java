package interfacelayer.gui;

import TI.BoeBot;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortException;

public class WindowsMain extends Application {
    public static void main(String[] args) {
        launch(WindowsMain.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));


        //TODO add saving a route under a name, adding window to give name, adding window to select route and see
        Scene scene = new Scene(root);
        scene.getStylesheets().add("interfacelayer/gui/stylesheet.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Buttercat");
        stage.show();
        System.out.println("Going in bluetooth");
        Bluetooth();
    }

    public void Bluetooth() {
        System.out.println("Entering bluetooth method");
        SerialPort serialPort = new SerialPort("COM6");
        System.out.println("After intitializing serialPort");
        int[] commands = new int[5];
        commands[0] = 119;
        commands[1] = 118;
        commands[2] = 117;
        commands[3] = 116;
        commands[4] = 115;

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
