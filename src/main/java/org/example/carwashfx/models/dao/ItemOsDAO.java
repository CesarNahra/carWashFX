package org.example.carwashfx.models.dao;

import org.example.carwashfx.models.domain.ECategory;
import org.example.carwashfx.models.domain.ItemOsModel;
import org.example.carwashfx.models.domain.ServiceModel;
import org.example.carwashfx.models.domain.ServiceOrderModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemOsDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean insertItemOs(ItemOsModel itemOs) {
        String query = "INSERT INTO item_os(item_value, item_observations, service_order_number, service_id) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, itemOs.getServiceValue());
            stmt.setString(2, itemOs.getObservations());
            stmt.setInt(3, itemOs.getServiceOrder().getOrderNumber());
            stmt.setInt(4, itemOs.getService().getServiceId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ItemOsDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean updateItemOs(ItemOsModel itemOs) {
        String query = "UPDATE item_os SET item_value = ?, item_observations = ? WHERE service_order_number = ? AND service_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDouble(1, itemOs.getServiceValue());
            stmt.setString(2, itemOs.getObservations());
            stmt.setInt(3, itemOs.getServiceOrder().getOrderNumber());
            stmt.setInt(4, itemOs.getService().getServiceId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ItemOsDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean deleteItemOs(ItemOsModel itemOs) {
        String query = "DELETE FROM item_os WHERE service_order_number = ? AND service_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, itemOs.getServiceOrder().getOrderNumber());
            stmt.setInt(2, itemOs.getService().getServiceId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ItemOsDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public List<ItemOsModel> getAllItemOs() {
        String query = "SELECT item.item_value, item.item_observations, s.service_id, s.service_description, s.service_price, s.service_given_points, s.vehicle_category_service, so.service_order_number FROM item_os item join service s on s.service_id = item.service_id join service_order so on so.service_order_number = item.service_order_number";
        List<ItemOsModel> itemOses = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ItemOsModel itemOs = populateItemOsList(rs);
                itemOses.add(itemOs);
            }
        } catch (SQLException e) {
            Logger.getLogger(ItemOsDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return itemOses;
    }

    private ItemOsModel populateItemOsList(ResultSet rs) throws SQLException {
        ItemOsModel itemOs = new ItemOsModel();
        ServiceModel service = new ServiceModel();
        ServiceOrderModel serviceOrder = new ServiceOrderModel();

        itemOs.setServiceValue(rs.getDouble("item_value"));
        itemOs.setObservations(rs.getString("item_observations"));

        service.setServiceId(rs.getInt("service_id"));
        service.setServiceDescription(rs.getString("service_description"));
        service.setServicePrice(rs.getDouble("service_price"));
        service.setGivenPoints(rs.getInt("service_given_points"));
        service.setCategory(ECategory.valueOf(rs.getString("vehicle_category_service")));

        serviceOrder.setOrderNumber(rs.getInt("service_order_number"));

        itemOs.setServiceOrder(serviceOrder);
        itemOs.setService(service);

        return itemOs;
    }
}