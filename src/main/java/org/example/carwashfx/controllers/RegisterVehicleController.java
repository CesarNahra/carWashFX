package org.example.carwashfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.carwashfx.models.dao.ClientDAO;
import org.example.carwashfx.models.dao.VehicleDAO;
import org.example.carwashfx.models.database.Database;
import org.example.carwashfx.models.database.DatabaseFactory;
import org.example.carwashfx.models.domain.*;
import org.example.carwashfx.utils.ViewNavigator;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;


public class RegisterVehicleController implements Initializable {
    @FXML
    private Label lbVehiclePlate;

    @FXML
    private TextField tfVehiclePlate;

    @FXML
    private Label lbVehicleObservations;

    @FXML
    private TextArea taVehicleObservations;

    @FXML
    private Label lbVehicleOwner;

    @FXML
    private Label lbVehicleBrand;

    @FXML
    private TextField tfVehicleBrand;

    @FXML
    private Label lbVehicleEngine;

    @FXML
    private TextField tfVehicleEngine;

    @FXML
    private Label lbVehicleGasType;

    @FXML
    private ChoiceBox<EGasType> cbVehicleGasType;

    @FXML
    private Label lbVehicleModel;

    @FXML
    private TextField tfVehicleModel;

    @FXML
    private Label lbVehicleColor;

    @FXML
    private TextField tfVehicleColor;

    @FXML
    private Label lbVehicleCategory;

    @FXML
    private ComboBox<ClientModel> comboVehicleOwner;

    @FXML
    private ChoiceBox<ECategory> cbVehicleCategory;

    private ECategory[] categories = ECategory.values();
    private EGasType[] gasTypes = EGasType.values();

    ClientModel client;
    VehicleModel vehicle = new VehicleModel();

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.connect();
    private final VehicleDAO vehicleDAO = new VehicleDAO();
    private final ClientDAO clientDAO = new ClientDAO();

    private List<ClientModel> ownerList;
    private ObservableList<ClientModel> observableOwnerList;

    public void loadClientData() {
        ownerList = clientDAO.getAllOwners();

        observableOwnerList = FXCollections.observableArrayList(ownerList);
    }

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
    protected void onHandleShowAttendanceDashboard(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/dashboard.png")));
        stage.setTitle("Dashboard de Atendimentos");

        ViewNavigator.openView(stage, "AttendanceDashboard.fxml");

    }


    @FXML
    protected void btRegisterVehicleOnAction(ActionEvent event) {
        ClientModel client = comboVehicleOwner.getSelectionModel().getSelectedItem();
        ECategory category = cbVehicleCategory.getSelectionModel().getSelectedItem();
        EGasType gasType = cbVehicleGasType.getSelectionModel().getSelectedItem();

        vehicle.getModel().setCategory(category);
        vehicle.getModel().getEngine().setGasType(gasType);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de novo veículo");
        alert.setHeaderText(null);
        alert.setContentText(vehicle.getVehicleData());
        alert.showAndWait();

        client.addVehicle(vehicle);
        vehicle.setClient(client);
        vehicleDAO.insertVehicle(vehicle);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        ViewNavigator.openView(stage, "GetVehicles.fxml");
    }

    @FXML
    protected void btNewVehicleOnAction(ActionEvent event) {
        tfVehiclePlate.clear();
        comboVehicleOwner.getSelectionModel().clearSelection();
        taVehicleObservations.clear();
        tfVehicleBrand.clear();
        tfVehicleModel.clear();
        tfVehicleEngine.clear();
        tfVehicleColor.clear();
        cbVehicleCategory.getSelectionModel().clearSelection();
        cbVehicleGasType.getSelectionModel().clearSelection();
        tfVehiclePlate.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vehicleDAO.setConnection(connection);
        clientDAO.setConnection(connection);

        loadClientData();
        comboVehicleOwner.setItems(observableOwnerList);

        cbVehicleCategory.getItems().addAll(categories);
        cbVehicleGasType.getItems().addAll(gasTypes);

        cbVehicleCategory.getSelectionModel().select(0);
        cbVehicleGasType.getSelectionModel().select(0);
    }
}
