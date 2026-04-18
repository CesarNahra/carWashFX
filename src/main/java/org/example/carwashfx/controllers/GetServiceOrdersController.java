package org.example.carwashfx.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.carwashfx.models.dao.ServiceOrderDAO;
import org.example.carwashfx.models.database.Database;
import org.example.carwashfx.models.database.DatabaseFactory;
import org.example.carwashfx.models.domain.EOrderStatus;
import org.example.carwashfx.models.domain.ServiceOrderModel;
import org.example.carwashfx.models.domain.VehicleModel;
import org.example.carwashfx.utils.ViewNavigator;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class GetServiceOrdersController implements Initializable {
    @FXML
    private TextField tfOrderNumber;

    @FXML
    private TextField tfTotalValue;

    @FXML
    private TextField tfOrderDiscount;

    @FXML
    private TextField tfVehiclePlate;

    @FXML
    private DatePicker dpOrderDate;

    @FXML
    private ChoiceBox<EOrderStatus> cbOrderStatus;

    @FXML
    private TableColumn<ServiceOrderModel, Integer> tcOrderNumber;

    @FXML
    private TableColumn<ServiceOrderModel, LocalDate> tcOrderDate;

    @FXML
    private TableColumn<ServiceOrderModel, Double> tcOrderValue;

    @FXML
    private TableColumn<ServiceOrderModel, Double> tcOrderDiscount;

    @FXML
    private TableColumn<ServiceOrderModel, String> tcVehiclePlate;

    @FXML
    private TableColumn<ServiceOrderModel, EOrderStatus> tcOrderStatus;

    private EOrderStatus[] orderStatus = EOrderStatus.values();

    @FXML
    private TableView<ServiceOrderModel> tableViewServiceOrders;

    @FXML
    private Button btUpdateServiceOrder;

    @FXML
    private Button btDeleteServiceOrder;

    @FXML
    private Button btConfirmUpdateServiceOrder;

    @FXML
    private Button btCancelUpdateServiceOrder;

    private List<ServiceOrderModel> serviceOrderList;
    private ObservableList<ServiceOrderModel> observableServiceOrderList;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.connect();
    private final ServiceOrderDAO serviceOrderDAO = new ServiceOrderDAO();


    public void loadTableServiceOrderList() {
        tcOrderNumber.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
        tcOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tcOrderValue.setCellValueFactory(new PropertyValueFactory<>("totalValue"));
        tcOrderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        tcOrderDiscount.setCellValueFactory(new PropertyValueFactory<>("orderDiscount"));
        tcVehiclePlate.setCellValueFactory(cellData -> {
            VehicleModel vehicle = cellData.getValue().getVehicle();
            return new SimpleStringProperty(vehicle.getVehiclePlate());
        });

        serviceOrderList = serviceOrderDAO.getAllServiceOrder();

        observableServiceOrderList = FXCollections.observableArrayList(serviceOrderList);
        tableViewServiceOrders.setItems(observableServiceOrderList);
    }

    public void selectTableViewServiceOrdersItem(ServiceOrderModel serviceOrder) {
        if (serviceOrder != null) {
            tfOrderNumber.setText(String.valueOf(serviceOrder.getOrderNumber()));
            dpOrderDate.setValue(serviceOrder.getOrderDate());
            tfTotalValue.setText(String.valueOf(serviceOrder.getTotalValue()));
            tfOrderDiscount.setText(String.valueOf(serviceOrder.getOrderDiscount()));
            cbOrderStatus.getSelectionModel().select(serviceOrder.getOrderStatus());
            tfVehiclePlate.setText(serviceOrder.getVehicle().getVehiclePlate());
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
    protected void onHandleUpdateServiceOrder(ActionEvent event) {
        ServiceOrderModel serviceOrder = tableViewServiceOrders.getSelectionModel().getSelectedItem();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/refresh.png")));
        stage.setTitle("Atualização de Ordem");

        if(serviceOrder != null) {
            cbOrderStatus.setDisable(false);

            btUpdateServiceOrder.setVisible(false);
            btDeleteServiceOrder.setVisible(false);

            btConfirmUpdateServiceOrder.setVisible(true);
            btCancelUpdateServiceOrder.setVisible(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Prevenção");
            alert.setHeaderText(null);
            alert.setContentText("Esta operação requer a seleção \nde uma ordem na tabela ao lado");
            alert.show();
        }
    }

    @FXML
    protected void onHandleConfirmUpdateServiceOrder(ActionEvent event) {
        ServiceOrderModel serviceOrder = tableViewServiceOrders.getSelectionModel().getSelectedItem();

        serviceOrder.setOrderStatus(cbOrderStatus.getValue());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmação de Atualização de Ordem");
        alert.setHeaderText(null);
        alert.setContentText("Ordem atualizada com sucesso");
        alert.showAndWait();

        serviceOrderDAO.updateServiceOrder(serviceOrder);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/edit.png")));
        stage.setTitle("Listagem de Ordens");

        ViewNavigator.openView(stage, "GetServiceOrders.fxml");
    }

    @FXML
    protected void onHandleCancelUpdateServiceOrder(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        stage.setTitle("Listagem de Ordens");

        ViewNavigator.openView(stage, "GetServiceOrders.fxml");
    }

    @FXML
    protected void onHandleDeleteServiceOrder(ActionEvent event) {
        ServiceOrderModel serviceOrder = tableViewServiceOrders.getSelectionModel().getSelectedItem();

        if(serviceOrder != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Exclusão de ordem");
            alert.setHeaderText(null);
            alert.setContentText("Você deseja excluir todos os dados desta ordem? Esta exclusão é irreversível");
            alert.showAndWait();

            serviceOrderDAO.deleteServiceOrder(serviceOrder);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Prevenção");
            alert.setHeaderText(null);
            alert.setContentText("Esta operação requer a seleção \nde um ordem na tabela ao lado");
            alert.show();
        }

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        ViewNavigator.openView(stage, "GetServiceOrders.fxml");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceOrderDAO.setConnection(connection);

        loadTableServiceOrderList();

        cbOrderStatus.getItems().addAll(orderStatus);

        tableViewServiceOrders.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectTableViewServiceOrdersItem(newValue));
    }
}
