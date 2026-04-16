package org.example.carwashfx.controllers;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.carwashfx.models.dao.ClientDAO;
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
import java.util.List;
import java.util.ResourceBundle;

public class ServiceOrderController implements Initializable {
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
    private TableView<ItemOsModel> tableViewItems;

    @FXML
    private Label lbServiceSubtotal;

    @FXML
    private Label lbServiceDiscount;

    @FXML
    private Label lbServiceTotal;

    @FXML
    private Label lbGivenPoints;

    @FXML
    private TextField tfVehiclePlate;

    @FXML
    private TextField tfVehicleOwner;

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
    private TextField tfOrderStatus;

    @FXML
    private TextArea taVehicleObservations;

    @FXML
    private ChoiceBox<ServiceModel> cbServicesChosen;

    @FXML
    private CheckBox checkBoxClientScore;

    @FXML
    private Button btConfirmService;

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
    private final ClientDAO clientDAO = new ClientDAO();

    private ServiceOrderModel serviceOrder = new ServiceOrderModel();

    private double discount = 0.3;
    private double fine = 0.35;

    private void updateTotalsUI() {
        double totalValue = serviceOrder.calculateServiceTotal();
        int totalPoints = serviceOrder.calculateTotalPoints();
        double totalDiscounted = serviceOrder.getDiscountValue();
        double totalWithDiscount = serviceOrder.calculateTotalWithDiscount();

        lbGivenPoints.setText(String.valueOf(totalPoints));
        lbServiceSubtotal.setText(String.format("R$ %.2f", totalValue));
        lbServiceDiscount.setText(String.format("- R$ %.2f", totalDiscounted));
        lbServiceTotal.setText(String.format("R$ %.2f", totalWithDiscount));
    }

    public void loadVehicleData(VehicleModel vehicle) {
        if(vehicle != null) {
            tfVehiclePlate.setText(vehicle.getVehiclePlate());
            tfVehicleOwner.setText(vehicle.getClient().getClientName());
            taVehicleObservations.setText(vehicle.getVehicleObservations());
            tfVehicleBrand.setText(vehicle.getModel().getBrand().getBrandName());
            tfVehicleModel.setText(vehicle.getModel().getModelDescription());
            tfVehicleCategory.setText(vehicle.getModel().getCategory().name());
            tfVehicleGasType.setText(vehicle.getModel().getEngine().getGasType().toString());
            tfVehicleEngine.setText(String.valueOf(vehicle.getModel().getEngine().getEnginePower()));
            tfVehicleColor.setText(vehicle.getColor().getColorName());
            checkBoxClientScore.setText(String.format("Usar %d pontos?", vehicle.getClient().getScore().getQuantity()));

            this.serviceOrder.setVehicle(vehicle);
        }
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
    protected void onHandleShowAfterSellDashboard(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/dashboard.png")));
        stage.setTitle("Dashboard de Pós Vendas");

        ViewNavigator.openView(stage, "AfterSellDashboard.fxml");

    }

    @FXML
    protected void onHandleConfirmService(ActionEvent event) {
        PrintingOs printer = new PrintingOs();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de serviço no veículo");
        alert.setHeaderText(null);
        alert.setContentText(serviceOrder.getServiceOrderData());
        alert.showAndWait();

        printer.printOs(serviceOrder);

        ScoreModel score = serviceOrder.getVehicle().getClient().getScore();

        if(checkBoxClientScore.isSelected()) {
            score.subQuantity(score.getQuantity());
            score.addQuantity(serviceOrder.calculateTotalPoints());
        } else {
            score.addQuantity(serviceOrder.calculateTotalPoints());
        }

        clientDAO.updateClientScore(serviceOrder.getVehicle().getClient());

        tableViewItems.setDisable(true);
        checkBoxClientScore.setDisable(true);
        cbServicesChosen.setDisable(true);
        tfOrderStatus.setDisable(true);
        btCancelService.setDisable(true);
        btConfirmService.setDisable(true);

        serviceOrderDAO.insertServiceOrder(serviceOrder);

        PauseTransition pause = new PauseTransition(Duration.seconds(2));

        pause.setOnFinished(redirect -> {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/car.png")));
            stage.setTitle("Início");

            ViewNavigator.openView(stage, "GetServiceOrders.fxml");
        });
        pause.play();
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
        ItemOsModel itemOs = new ItemOsModel();

        double finalValue = serviceAdded.getServicePrice() - (serviceAdded.getServicePrice() * discount);

        finalValue = Math.round(finalValue * 100.0) / 100.0;

        itemOs.setServiceValue(finalValue);
        itemOs.setObservations(String.format("%.1f%% de desconto", discount * 100.0));

        itemOs.setService(serviceAdded);

        tableViewItems.getItems().add(itemOs);
        serviceOrder.addItemOs(itemOs);

        updateTotalsUI();

        cbServicesChosen.getSelectionModel().clearSelection();
    }

    @FXML
    protected void onHandleRemoveService(ActionEvent event) {
        ItemOsModel itemOs = tableViewItems.getSelectionModel().getSelectedItem();

        tableViewItems.getItems().remove(itemOs);
        serviceOrder.removeItemOs(itemOs);

        updateTotalsUI();

        tableViewItems.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceOrderDAO.setConnection(connection);
        serviceDAO.setConnection(connection);
        itemOsDAO.setConnection(connection);
        clientDAO.setConnection(connection);

        loadTableItems();
        updateTotalsUI();

        checkBoxClientScore.selectedProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                serviceOrder.setUseClientPoints(true);
            } else {
                serviceOrder.setUseClientPoints(false);
            }
            updateTotalsUI();
        });


        tableViewItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(tableViewItems.getSelectionModel().isEmpty()) {
                btRemoveService.setVisible(false);
            } else {
                btRemoveService.setVisible(true);
            }
        });

        serviceList = serviceDAO.getServicesByCategory(ECategory.LARGE);
        observableServiceList = FXCollections.observableArrayList(serviceList);

        cbServicesChosen.setItems(observableServiceList);
        cbServicesChosen.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(cbServicesChosen.getSelectionModel().isEmpty()) {
                btAddService.setVisible(false);
            } else {
                btAddService.setVisible(true);
            }
        });
        tfOrderStatus.setText(EOrderStatus.OPENED.name());
    }
}
