package org.example.carwashfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import org.example.carwashfx.models.dao.ServiceOrderDAO;
import org.example.carwashfx.models.dao.VehicleDAO;
import org.example.carwashfx.models.database.Database;
import org.example.carwashfx.models.database.DatabaseFactory;
import org.example.carwashfx.models.domain.ECategory;
import org.example.carwashfx.models.domain.EOrderStatus;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class AfterSellController implements Initializable {
    @FXML
    private PieChart pcAfterSellDashboard;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.connect();
    private final ServiceOrderDAO serviceOrderDAO = new ServiceOrderDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceOrderDAO.setConnection(connection);

        ObservableList<PieChart.Data> pcData = FXCollections.observableArrayList(
                new PieChart.Data(EOrderStatus.CANCELED.name(), 50),
                new PieChart.Data(EOrderStatus.CLOSED.name(), 50)
        );

        pcAfterSellDashboard.setTitle("Pós Venda");
        pcAfterSellDashboard.setData(pcData);

        pcData.forEach(data -> {
            data.setName(data.getName() + " (" + (int) data.getPieValue() + ")");
        });
    }
}
