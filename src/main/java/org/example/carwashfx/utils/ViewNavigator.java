package org.example.carwashfx.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.carwashfx.CarWashApp;

import java.io.IOException;

public class ViewNavigator {

    public static FXMLLoader openView(Stage stage, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(CarWashApp.class.getResource("views/" + fxml));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();

            return loader;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}