package org.example.carwashfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.carwashfx.models.dao.*;
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
    private TextField tfVehiclePlate;

    @FXML
    private TextField tfVehicleEngine;

    @FXML
    private TextField tfVehicleModel;

    @FXML
    private TextArea taVehicleObservations;

    @FXML
    private ChoiceBox<BrandModel> cbVehicleBrand;

    @FXML
    private ChoiceBox<EGasType> cbVehicleGasType;

    @FXML
    private ChoiceBox<ColorModel> cbVehicleColor;

    @FXML
    private ChoiceBox<ECategory> cbVehicleCategory;

    @FXML
    private ComboBox<ClientModel> comboVehicleOwner;

    private ECategory[] categories = ECategory.values();
    private EGasType[] gasTypes = EGasType.values();

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.connect();
    private final VehicleDAO vehicleDAO = new VehicleDAO();
    private final BrandDAO brandDAO = new BrandDAO();
    private final VehicleModelDAO vehicleModelDAO = new VehicleModelDAO();
    private final ColorDAO colorDAO = new ColorDAO();
    private final ClientDAO clientDAO = new ClientDAO();

    private List<ColorModel> colorList;
    private ObservableList<ColorModel> observableColorList;

    private List<BrandModel> brandList;
    private ObservableList<BrandModel> observableBrandList;

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
    protected void onHandleShowAfterSellDashboard(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/dashboard.png")));
        stage.setTitle("Dashboard de Pós Vendas");

        ViewNavigator.openView(stage, "AfterSellDashboard.fxml");

    }


    @FXML
    protected void btRegisterVehicleOnAction(ActionEvent event) {
        BrandModel brand = cbVehicleBrand.getSelectionModel().getSelectedItem();
        ColorModel color = cbVehicleColor.getSelectionModel().getSelectedItem();
        ClientModel client = comboVehicleOwner.getSelectionModel().getSelectedItem();
        ECategory category = cbVehicleCategory.getSelectionModel().getSelectedItem();
        EGasType gasType = cbVehicleGasType.getSelectionModel().getSelectedItem();

        VehicleModelModel vehicleModel = new VehicleModelModel();
        vehicleModel.setBrand(brand);
        vehicleModel.setModelDescription(tfVehicleModel.getText());
        vehicleModel.setCategory(category);
        vehicleModel.getEngine().setEnginePower(Integer.parseInt(tfVehicleEngine.getText()));
        vehicleModel.getEngine().setGasType(gasType);

        vehicleModelDAO.insertVehicleModel(vehicleModel);

        VehicleModel vehicle = new VehicleModel(tfVehiclePlate.getText(), taVehicleObservations.getText(), client, vehicleModel, color);

        client.addVehicle(vehicle);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de novo veículo");
        alert.setHeaderText(null);
        alert.setContentText(vehicle.getVehicleData());
        alert.showAndWait();

        vehicleDAO.insertVehicle(vehicle);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        ViewNavigator.openView(stage, "GetVehicles.fxml");
    }

    @FXML
    protected void btNewVehicleOnAction(ActionEvent event) {
        tfVehiclePlate.clear();
        comboVehicleOwner.getSelectionModel().clearSelection();
        taVehicleObservations.clear();
        cbVehicleBrand.getSelectionModel().clearSelection();
        tfVehicleModel.clear();
        tfVehicleEngine.clear();
        cbVehicleColor.getSelectionModel().clearSelection();
        cbVehicleCategory.getSelectionModel().clearSelection();
        cbVehicleGasType.getSelectionModel().clearSelection();
        tfVehiclePlate.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        brandDAO.setConnection(connection);
        colorDAO.setConnection(connection);
        vehicleModelDAO.setConnection(connection);
        vehicleDAO.setConnection(connection);
        clientDAO.setConnection(connection);

        colorList = colorDAO.getAllColors();
        observableColorList = FXCollections.observableArrayList(colorList);

        cbVehicleColor.setItems(observableColorList);

        brandList = brandDAO.getAllBrands();
        observableBrandList = FXCollections.observableArrayList(brandList);

        cbVehicleBrand.setItems(observableBrandList);

        loadClientData();
        comboVehicleOwner.setItems(observableOwnerList);

        cbVehicleCategory.getItems().addAll(categories);
        cbVehicleGasType.getItems().addAll(gasTypes);

        cbVehicleCategory.getSelectionModel().select(0);
        cbVehicleGasType.getSelectionModel().select(0);
    }
}
