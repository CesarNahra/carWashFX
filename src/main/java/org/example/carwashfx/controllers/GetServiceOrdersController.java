package org.example.carwashfx.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.carwashfx.models.dao.ServiceOrderDAO;
import org.example.carwashfx.models.database.Database;
import org.example.carwashfx.models.database.DatabaseFactory;
import org.example.carwashfx.models.domain.EOrderStatus;
import org.example.carwashfx.models.domain.ServiceOrderModel;
import org.example.carwashfx.utils.ViewNavigator;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class GetServiceOrdersController implements Initializable {
    @FXML
    private TableColumn<ServiceOrderModel, LocalDate> tcOrderDate;

    @FXML
    private TableColumn<ServiceOrderModel, Double> tcOrderValue;

    @FXML
    private TableColumn<ServiceOrderModel, Double> tcOrderDiscount;

    @FXML
    private TableColumn<ServiceOrderModel, EOrderStatus> tcOrderStatus;

    @FXML
    private TableView<ServiceOrderModel> tableViewServiceOrders;

    private List<ServiceOrderModel> serviceOrderList;
    private ObservableList<ServiceOrderModel> observableServiceOrderList;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.connect();
    private final ServiceOrderDAO serviceOrderDAO = new ServiceOrderDAO();


    public void loadTableServiceOrderList() {
        tcOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tcOrderValue.setCellValueFactory(cellData -> {
            ServiceOrderModel serviceOrder = cellData.getValue();
            return new SimpleObjectProperty<>(serviceOrder.calculateServiceTotal());
        });
        tcOrderDiscount.setCellValueFactory(cellData -> {
            ServiceOrderModel serviceOrder = cellData.getValue();
            return new SimpleObjectProperty<>(serviceOrder.getDiscountValue());
        });
        tcOrderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));

        serviceOrderList = serviceOrderDAO.getAllServiceOrder();

        observableServiceOrderList = FXCollections.observableArrayList(serviceOrderList);
        tableViewServiceOrders.setItems(observableServiceOrderList);
    }

    @FXML
    protected void onHandleHome(ActionEvent event) {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/car.png")));
        stage.setTitle("Início");

        ViewNavigator.openView(stage, "Index.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceOrderDAO.setConnection(connection);

        loadTableServiceOrderList();

//        tableViewServiceOrders.getSelectionModel().selectedItemProperty().addListener(
//                (observable, oldValue, newValue) -> selectTableViewVehiclesItem(newValue));
    }
}
