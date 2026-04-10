package org.example.carwashfx.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.carwashfx.utils.ViewNavigator;

public class PrintController {

    @FXML
    private TextArea txtReceipt;

    public void setReceiptText(String text) {
        txtReceipt.setText(text);
    }

    public void autoReturn(Stage stage) {

        PauseTransition pause = new PauseTransition(Duration.seconds(5));

        pause.setOnFinished(e -> {
            ViewNavigator.openView(stage, "Index.fxml");
        });

        pause.play();
    }
}
