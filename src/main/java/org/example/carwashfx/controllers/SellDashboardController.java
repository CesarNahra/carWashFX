package org.example.carwashfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.carwashfx.utils.ViewNavigator;

import java.net.URL;
import java.util.ResourceBundle;

public class SellDashboardController implements Initializable {
    @FXML
    private LineChart<String, Number> lcSellDashboard;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    protected void onHandleHome(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/car.png")));
        stage.setTitle("Início");

        ViewNavigator.openView(stage, "Index.fxml");
    }

    @FXML
    protected void onHandleGetVehiclesOnAction(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/edit.png")));
        stage.setTitle("Listagem de Veículos");

        ViewNavigator.openView(stage, "GetVehicles.fxml");
    }

    @FXML
    protected void onHandleGetOwnersOnAction(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/edit.png")));
        stage.setTitle("Listagem de Proprietários");

        ViewNavigator.openView(stage, "GetOwners.fxml");

    }

    @FXML
    protected void onHandleRegisterVehicle(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/contract.png")));
        stage.setTitle("Registro de Veículo");

        ViewNavigator.openView(stage, "RegisterVehicle.fxml");

    }

    @FXML
    protected void onHandleRegisterOwner(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/add.png")));
        stage.setTitle("Registro de Proprietário");

        ViewNavigator.openView(stage, "RegisterOwner.fxml");

    }

    @FXML
    protected void onHandleShowAttendanceDashboard(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/dashboard.png")));
        stage.setTitle("Dashboard de Atendimentos");

        ViewNavigator.openView(stage, "AttendanceDashboard.fxml");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<String, Number> lcData = new XYChart.Series<>();
        lcData.setName("2026");

        lcData.getData().add(new XYChart.Data<>("1", 23));
        lcData.getData().add(new XYChart.Data<>("5", 612));
        lcData.getData().add(new XYChart.Data<>("10", 800));
        lcData.getData().add(new XYChart.Data<>("20", 780));
        lcData.getData().add(new XYChart.Data<>("40", 810));
        lcData.getData().add(new XYChart.Data<>("80", 850));

        lcSellDashboard.getData().add(lcData);

    }
}
