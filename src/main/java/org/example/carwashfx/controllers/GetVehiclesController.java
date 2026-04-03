package org.example.carwashfx.controllers;


import com.mysql.cj.xdevapi.Client;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.carwashfx.models.dao.VehicleDAO;
import org.example.carwashfx.models.database.Database;
import org.example.carwashfx.models.database.DatabaseFactory;
import org.example.carwashfx.models.domain.ECategory;
import org.example.carwashfx.models.domain.EGasType;
import org.example.carwashfx.models.domain.VehicleModel;
import org.example.carwashfx.utils.ViewNavigator;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class GetVehiclesController implements Initializable {
    @FXML
    private Label lbVehiclePlate;

    @FXML
    private Label lbVehicleOwner;

    @FXML
    private Label lbVehicleObservations;

    @FXML
    private Label lbVehicleBrand;

    @FXML
    private Label lbVehicleModel;

    @FXML
    private Label lbVehicleEngine;

    @FXML
    private Label lbVehicleGasType;

    @FXML
    private Label lbVehicleColor;

    @FXML
    private Label lbVehicleCategory;

    @FXML
    private TableColumn<VehicleModel, String> tcVehiclePlate;

    @FXML
    private TableColumn<VehicleModel, String> tcVehicleOwner;

    @FXML
    private TableColumn<VehicleModel, String> tcVehicleObservations;

    @FXML
    private TableColumn<VehicleModel, String> tcVehicleBrand;

    @FXML
    private TableColumn<VehicleModel, String> tcVehicleModel;

    @FXML
    private TableColumn<VehicleModel, ECategory> tcVehicleCategory;

    @FXML
    private TableColumn<VehicleModel, Integer> tcVehicleEngine;

    @FXML
    private TableColumn<VehicleModel, EGasType> tcVehicleGasType;

    @FXML
    private TableColumn<VehicleModel, String> tcVehicleColor;

    @FXML
    private TextField tfVehiclePlate;

    @FXML
    private TextField tfVehicleOwner;

    @FXML
    private TextArea taVehicleObservations;

    @FXML
    private TextField tfVehicleBrand;

    @FXML
    private TextField tfVehicleModel;

    @FXML
    private TextField tfVehicleEngine;

    @FXML
    private TextField tfVehicleGasType;

    @FXML
    private TextField tfVehicleColor;

    @FXML
    private TextField tfVehicleCategory;

    @FXML
    private Button btUpdateVehicle;

    @FXML
    private Button btDeleteVehicle;

    @FXML
    private Button btServiceOrder;

    @FXML
    private Button btConfirmUpdateVehicle;

    @FXML
    private Button btCancelUpdateVehicle;

    @FXML
    private TableView<VehicleModel> tableViewVehicles;

    private List<VehicleModel> vehicleList;
    private ObservableList<VehicleModel> observableVehicleList;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.connect();
    private final VehicleDAO vehicleDAO = new VehicleDAO();


    public void loadTableVehicleList() {
        tcVehiclePlate.setCellValueFactory(new PropertyValueFactory<>("vehiclePlate"));
        tcVehicleOwner.setCellValueFactory(new PropertyValueFactory<>("vehicleOwner"));
        tcVehicleObservations.setCellValueFactory(new PropertyValueFactory<>("vehicleObservations"));
        tcVehicleBrand.setCellValueFactory(new PropertyValueFactory<>("vehicleBrand"));
        tcVehicleCategory.setCellValueFactory(new PropertyValueFactory<>("vehicleCategory"));
        tcVehicleModel.setCellValueFactory(new PropertyValueFactory<>("vehicleModel"));
        tcVehicleEngine.setCellValueFactory(new PropertyValueFactory<>("vehicleEnginePower"));
        tcVehicleGasType.setCellValueFactory(new PropertyValueFactory<>("vehicleGasType"));
        tcVehicleColor.setCellValueFactory(new PropertyValueFactory<>("vehicleColor"));


        vehicleList = vehicleDAO.getAllVehicles();

        observableVehicleList = FXCollections.observableArrayList(vehicleList);
        tableViewVehicles.setItems(observableVehicleList);
    }

    public void selectTableViewVehiclesItem(VehicleModel vehicle) {
        if (vehicle != null) {
            tfVehiclePlate.setText(vehicle.getVehiclePlate());
            tfVehicleOwner.setText(vehicle.getClient().getClientName());
            taVehicleObservations.setText(vehicle.getVehicleObservations());
            tfVehicleBrand.setText(vehicle.getModel().getBrand().getBrandName());
            tfVehicleModel.setText(vehicle.getModel().getModelDescription());
            tfVehicleCategory.setText(vehicle.getModel().getCategory().toString());
            tfVehicleEngine.setText(String.valueOf(vehicle.getModel().getEngine().getEnginePower()));
            tfVehicleGasType.setText(vehicle.getModel().getEngine().getGasType().toString());
            tfVehicleColor.setText(vehicle.getColor().getColorName());
        }
    }

    @FXML
    protected void onHandleHome(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/car.png")));
        stage.setTitle("Início");

        ViewNavigator.openView(stage, "Index.fxml");
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

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/edit.png")));
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
    protected void onHandleService(ActionEvent event) throws IOException {
        VehicleModel vehicle = tableViewVehicles.getSelectionModel().getSelectedItem();

        if (vehicle != null) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            FXMLLoader loader = ViewNavigator.openView(stage, "ServiceOrder.fxml");

            if (loader != null) {
                ServiceOrderController controller = loader.getController();
                controller.loadVehicleData(vehicle);
            }

            stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/repairing-service.png")));
            stage.setTitle("Criação de Ordem de Serviço");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Prevenção");
            alert.setHeaderText(null);
            alert.setContentText("Esta operação requer a seleção \nde um Veículo na tabela ao lado");
            alert.show();
        }
    }


    @FXML
    protected void onHandleUpdateVehicle(ActionEvent event) {
        VehicleModel vehicle = tableViewVehicles.getSelectionModel().getSelectedItem();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/refresh.png")));
        stage.setTitle("Atualização de Veículo");

        if(vehicle != null) {
            tfVehiclePlate.setDisable(false);
            tfVehiclePlate.setEditable(true);
            tfVehicleOwner.setDisable(false);
            tfVehicleOwner.setEditable(true);
            taVehicleObservations.setDisable(false);
            taVehicleObservations.setEditable(true);

            btUpdateVehicle.setVisible(false);
            btDeleteVehicle.setVisible(false);
            btServiceOrder.setVisible(false);

            btConfirmUpdateVehicle.setVisible(true);
            btCancelUpdateVehicle.setVisible(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Prevenção");
            alert.setHeaderText(null);
            alert.setContentText("Esta operação requer a seleção \nde um Veículo na tabela ao lado");
            alert.show();
        }
    }

    @FXML
    protected void onHandleConfirmUpdateVehicle(ActionEvent event) {
        VehicleModel vehicle = tableViewVehicles.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmação de Atualização de Veículo");
        alert.setHeaderText(null);
        alert.showAndWait();
        vehicleDAO.updateVehicle(vehicle);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/edit.png")));
        stage.setTitle("Listagem de Veículos");

        ViewNavigator.openView(stage, "GetVehicles.fxml");
    }

    @FXML
    protected void onHandleCancelUpdateVehicle(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        stage.setTitle("Listagem de Veículos");

        ViewNavigator.openView(stage, "GetVehicles.fxml");
    }

    @FXML
    protected void onHandleDeleteVehicle(ActionEvent event) {
        VehicleModel vehicle = tableViewVehicles.getSelectionModel().getSelectedItem();

        if(vehicle != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Exclusão de veículo");
            alert.setHeaderText(null);
            alert.setContentText("Você deseja excluir todos os dados deste veículo? Esta exclusão é irreversível");
            alert.showAndWait();
            vehicleDAO.deleteVehicle(vehicle);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Prevenção");
            alert.setHeaderText(null);
            alert.setContentText("Esta operação requer a seleção \nde um Veículo na tabela ao lado");
            alert.show();
        }

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        ViewNavigator.openView(stage, "GetVehicles.fxml");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vehicleDAO.setConnection(connection);

        loadTableVehicleList();

        tableViewVehicles.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectTableViewVehiclesItem(newValue));
    }
}
