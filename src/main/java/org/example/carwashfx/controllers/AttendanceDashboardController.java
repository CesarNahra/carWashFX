package org.example.carwashfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.carwashfx.models.dao.VehicleDAO;
import org.example.carwashfx.models.database.Database;
import org.example.carwashfx.models.database.DatabaseFactory;
import org.example.carwashfx.models.domain.ECategory;
import org.example.carwashfx.utils.ViewNavigator;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class AttendanceDashboardController implements Initializable {
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.connect();
    private final VehicleDAO vehicleDAO = new VehicleDAO();

    @FXML
    private PieChart pcAttendanceDashboard;

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
        stage.setTitle("Listagem de veículos");

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
        stage.setTitle("Registro de veículo");

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
    protected void onHandleShowSellDashboard(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/dashboard.png")));
        stage.setTitle("Dashboard de Vendas");

        ViewNavigator.openView(stage, "SellDashboard.fxml");

    }

    @FXML
    protected void onHandleShowAfterSellDashboard(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/dashboard.png")));
        stage.setTitle("Dashboard de Pós Vendas");

        ViewNavigator.openView(stage, "AfterSellDashboard.fxml");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vehicleDAO.setConnection(connection);

        ObservableList<PieChart.Data> pcData = FXCollections.observableArrayList(
                new PieChart.Data(ECategory.SMALL.name(), vehicleDAO.countVehiclesByCategory(ECategory.SMALL)),
                new PieChart.Data(ECategory.MEDIUM.name(), vehicleDAO.countVehiclesByCategory(ECategory.MEDIUM)),
                new PieChart.Data(ECategory.LARGE.name(), vehicleDAO.countVehiclesByCategory(ECategory.LARGE)),
                new PieChart.Data(ECategory.MOTORBIKE.name(), vehicleDAO.countVehiclesByCategory(ECategory.MOTORBIKE)),
                new PieChart.Data(ECategory.DEFAULT.name(), vehicleDAO.countVehiclesByCategory(ECategory.DEFAULT))
        );

        pcAttendanceDashboard.setTitle("Atendimentos por tamanho");
        pcAttendanceDashboard.setData(pcData);

        pcData.forEach(data -> {
            data.setName(data.getName() + " (" + (int) data.getPieValue() + ")");
        });
    }
}
