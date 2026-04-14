package org.example.carwashfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CarWashApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CarWashApp.class.getResource("views/GetServiceOrders.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/car.png")));
        stage.setTitle("Início");
        stage.setScene(scene);
        stage.show();
    }
}
