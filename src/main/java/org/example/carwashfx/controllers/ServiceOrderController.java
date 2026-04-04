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
import org.controlsfx.control.PropertySheet;
import org.example.carwashfx.models.dao.ItemOsDAO;
import org.example.carwashfx.models.dao.ServiceDAO;
import org.example.carwashfx.models.dao.ServiceOrderDAO;
import org.example.carwashfx.models.database.Database;
import org.example.carwashfx.models.database.DatabaseFactory;
import org.example.carwashfx.models.domain.*;
import org.example.carwashfx.report.PrintingOs;
import org.example.carwashfx.utils.ViewNavigator;
import org.example.carwashfx.models.domain.VehicleModel;
import org.example.carwashfx.models.domain.ServiceOrderModel;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ServiceOrderController implements Initializable {
    @FXML
    private Label lbVehiclePlate;

    @FXML
    private Label lbClientName;

    @FXML
    private Label lbVehicleObservations;

    @FXML
    private Label lbVehicleBrand;

    @FXML
    private Label lbVehicleModel;

    @FXML
    private Label lbVehicleEngine;

    @FXML
    private Label lbVehicleColor;

    @FXML
    private Label lbVehicleCategory;

    @FXML
    private Label lbVehicleGasType;

    @FXML
    private Label lbSubtotal;

    @FXML
    private Label lbServiceSubtotal;

    @FXML
    private Label lbDiscount;

    @FXML
    private Label lbServiceDiscount;

    @FXML
    private Label lbTotal;

    @FXML
    private Label lbGivenPoints;

    @FXML
    private Label lbServiceTotal;

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
    private TextField tfVehicleColor;

    @FXML
    private TextField tfVehicleCategory;

    @FXML
    private TextField tfVehicleGasType;

    @FXML
    private ChoiceBox<ServiceModel> cbServicesChosen;

    @FXML
    private ChoiceBox<EOrderStatus> cbServiceOrderStatus;

    private EOrderStatus[] orderStatus = EOrderStatus.values();

    @FXML
    private CheckBox checkBoxClientScore;

    @FXML
    private TableColumn<ItemOsModel, Integer> tcServiceId;

    @FXML
    private TableColumn<ItemOsModel, String> tcServiceDescription;

    @FXML
    private TableColumn<ItemOsModel, Double> tcServicePrice;

    @FXML
    private TableColumn<ItemOsModel, Integer> tcGivenPoints;

    @FXML
    private TableColumn<ItemOsModel, ECategory> tcVehicleCategoryService;

    @FXML
    private TableColumn<ItemOsModel, Double> tcItemTotal;

    @FXML
    private TableColumn<ItemOsModel, String> tcItemObservations;

    @FXML
    private TableView<ServiceModel> tableViewServices;

    @FXML
    private TableView<ItemOsModel> tableViewItems;

    @FXML
    private Button btUpdateVehicle;

    @FXML
    private Button btCancelService;

    @FXML
    private Button btAddService;

    @FXML
    private Button btRemoveService;

    private List<ServiceModel> serviceList;
    private ObservableList<ServiceModel> observableServiceList;

    private List<ItemOsModel> itemOsList;
    private ObservableList<ItemOsModel> observableItemOsList;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private Connection connection = database.connect();
    private final ServiceOrderDAO serviceOrderDAO = new ServiceOrderDAO();
    private final ServiceDAO serviceDAO = new ServiceDAO();
    private final ItemOsDAO itemOsDAO = new ItemOsDAO();

    private ServiceOrderModel serviceOrder = new ServiceOrderModel();

    private double value = 0;
    private int points = 0;
    private double discount = 0;
    private double multa = 0;

    String formattedValue = String.format("R$ %.2f", value);
    String formattedDiscount = String.format("R$ %.2f", discount);


    public void loadVehicleData(VehicleModel vehicle) {
        tfVehiclePlate.setText(vehicle.getVehiclePlate());
        tfVehicleOwner.setText(vehicle.getClient().getClientName());
        taVehicleObservations.setText(vehicle.getVehicleObservations());
        tfVehicleBrand.setText(vehicle.getModel().getBrand().getBrandName());
        tfVehicleModel.setText(vehicle.getModel().getModelDescription());
        tfVehicleCategory.setText(vehicle.getModel().getCategory().toString());
        tfVehicleGasType.setText(vehicle.getModel().getEngine().getGasType().toString());
        tfVehicleEngine.setText(String.valueOf(vehicle.getModel().getEngine().getEnginePower()));
        tfVehicleColor.setText(vehicle.getColor().getColorName());
    }

    public void loadTableItems() {
        tcServiceId.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        tcServiceDescription.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));
        tcServicePrice.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
        tcGivenPoints.setCellValueFactory(new PropertyValueFactory<>("serviceGivenPoints"));
        tcVehicleCategoryService.setCellValueFactory(new PropertyValueFactory<>("serviceCategory"));
        tcItemTotal.setCellValueFactory(new PropertyValueFactory<>("serviceValue"));
        tcItemObservations.setCellValueFactory(new PropertyValueFactory<>("observations"));

        itemOsList = itemOsDAO.getAllItemOs();
        observableItemOsList = FXCollections.observableArrayList(itemOsList);

        tableViewItems.setItems(observableItemOsList);
    }

    @FXML
    protected void onHandleHome(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream(("/icon/car.png"))));
        stage.setTitle("Início");

        ViewNavigator.openView(stage, "Index.fxml");
    }

    @FXML
    protected void onHandleGetOwnersOnAction(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image((getClass().getResourceAsStream("/icon/edit.png"))));
        stage.setTitle("Listagem de Proprietários");

        ViewNavigator.openView(stage, "GetOwners.fxml");

    }

    @FXML
    protected void onHandleRegisterVehicle(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image((getClass().getResourceAsStream("/icon/edit.png"))));
        stage.setTitle("Listagem de Veículos");

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

        stage.getIcons().add(new Image(getClass().getResourceAsStream(("/icon/dashboard.png"))));
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
    protected void onHandleConfirmService(ActionEvent event) {
        ServiceOrderModel serviceOrder = new ServiceOrderModel();
        PrintingOs printer = new PrintingOs();

        EOrderStatus orderSelected = cbServiceOrderStatus.getSelectionModel().getSelectedItem();

        if(cbServiceOrderStatus.getSelectionModel().getSelectedItem() == EOrderStatus.OPENED) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de serviço no veículo");
            alert.setHeaderText(null);
            alert.setContentText("hkhlkçlkj");
            alert.showAndWait();
        } else {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/edit.png")));
            stage.setTitle("Listagem de Veículos");

            ViewNavigator.openView(stage, "GetVehicles.fxml");
        }

        serviceOrder.setOrderStatus(orderSelected);
        printer.printOs(serviceOrder);

        serviceOrderDAO.insertServiceOrder(serviceOrder);
    }

    @FXML
    protected void onHandleCancelService(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cancelamento de serviço");
        alert.setHeaderText(null);
        alert.setContentText("Você está prestes a cancelar os serviços. Deseja prosseguir?");
        alert.showAndWait();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        stage.setTitle("Listagem de Veículos");

        ViewNavigator.openView(stage, "GetVehicles.fxml");

    }

    @FXML
    protected void onHandleAddService(ActionEvent event) {
        ServiceModel serviceAdded = cbServicesChosen.getSelectionModel().getSelectedItem();
        ServiceOrderModel serviceOrder = new ServiceOrderModel();

        for(ItemOsModel itemOs: itemOsDAO.getAllItemOs()) {
            value += itemOs.getServicePrice();
            points += itemOs.getServiceGivenPoints();

            itemOs.setService(serviceAdded);

            tableViewItems.getItems().add(itemOs);
            serviceOrder.getItemOsList().add(itemOs);
        }

        String formattedValue = String.format("R$ %.2f", value);
        String formattedDiscount = String.format("R$ %.2f", discount);

        lbGivenPoints.setText(String.valueOf(points));
        lbServiceSubtotal.setText(formattedValue);
        lbServiceDiscount.setText(formattedDiscount);
        lbServiceTotal.setText(formattedValue);

        cbServicesChosen.getSelectionModel().clearSelection();
    }

    @FXML
    protected void onHandleRemoveService(ActionEvent event) {
        ItemOsModel itemOs = tableViewItems.getSelectionModel().getSelectedItem();
        ServiceOrderModel serviceOrder = new ServiceOrderModel();

        itemOs.setService(itemOs.getService());

        tableViewItems.getItems().remove(itemOs);
        serviceOrder.getItemOsList().remove(itemOs);

        tableViewItems.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceOrderDAO.setConnection(connection);
        serviceDAO.setConnection(connection);
        itemOsDAO.setConnection(connection);


        lbGivenPoints.setText(String.valueOf(points));
        lbServiceSubtotal.setText(formattedValue);
        lbServiceDiscount.setText(formattedDiscount);
        lbServiceTotal.setText(formattedValue);

        loadTableItems();

        tableViewItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(tableViewItems.getSelectionModel().isEmpty()) {
                btRemoveService.setVisible(false);
            } else {
                btRemoveService.setVisible(true);
            }
        });

        serviceList = serviceDAO.getAllServices();
        observableServiceList = FXCollections.observableArrayList(serviceList);

        cbServicesChosen.setItems(observableServiceList);
        cbServicesChosen.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(cbServicesChosen.getSelectionModel().isEmpty()) {
                btAddService.setVisible(false);
            } else {
                btAddService.setVisible(true);
            }
        });

        cbServiceOrderStatus.getItems().addAll(orderStatus);
        cbServiceOrderStatus.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(cbServiceOrderStatus.getSelectionModel().getSelectedItem() == EOrderStatus.CLOSED) {
                cbServicesChosen.setDisable(true);
                checkBoxClientScore.setDisable(true);
                tableViewItems.setDisable(true);
                btCancelService.setDisable(true);
                btCancelService.setVisible(false);
                btAddService.setDisable(true);
                btRemoveService.setDisable(true);
            } else if(cbServiceOrderStatus.getSelectionModel().getSelectedItem() == EOrderStatus.CANCELED) {
                cbServicesChosen.setDisable(true);
                checkBoxClientScore.setDisable(true);
                tableViewItems.setDisable(true);
                btCancelService.setDisable(true);
                btCancelService.setVisible(false);
                btAddService.setDisable(true);

                lbServiceSubtotal.setText(String.valueOf(multa));
                lbServiceDiscount.setText(String.valueOf(discount));
                lbServiceTotal.setText(String.valueOf(multa));

                lbGivenPoints.setText(String.valueOf(points));
            } else {
                cbServicesChosen.setDisable(false);
                checkBoxClientScore.setDisable(false);
                tableViewItems.setDisable(false);
                btCancelService.setDisable(false);
                btCancelService.setVisible(true);
                btAddService.setDisable(false);
                btRemoveService.setDisable(false);
                lbTotal.setText("Total: ");
            }
        });
        cbServiceOrderStatus.getSelectionModel().select(EOrderStatus.OPENED);
    }
}
