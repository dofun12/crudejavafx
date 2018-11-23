package com.lemanoman.javafxmaven;

import com.lemanoman.javafxmaven.interfaces.FXController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

public class Starter extends Application {
    private Stage primaryStage;




    public void changeStage(String name){
        try {
            FXMLLoader loader = new FXMLLoader(Starter.class.getClassLoader().getResource(name+".fxml"));
            Parent root = loader.load();
            FXController controller = loader.getController();
            controller.initialize(root,this);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayTray() throws AWTException, MalformedURLException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        java.awt.Image image = Toolkit.getDefaultToolkit().createImage("/home/kevim/Imagens/semfoto.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));



        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        trayIcon.displayMessage("Hello, World", "notification demo", TrayIcon.MessageType.INFO);


    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        /**
        try {
            displayTray();
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
         **/
        SQLiteJDBCDriverConnection sqLiteJDBCDriverConnection = new SQLiteJDBCDriverConnection();
        sqLiteJDBCDriverConnection.createDefaultTable();


        this.primaryStage = primaryStage;
        primaryStage.setTitle("Hello World");
        changeStage("user");
        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
