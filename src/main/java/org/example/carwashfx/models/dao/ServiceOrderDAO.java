package org.example.carwashfx.models.dao;

import org.example.carwashfx.models.domain.ServiceOrderModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceOrderDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean insertServiceOrder(ServiceOrderModel serviceOrder) {
        String query = "INSERT INTO service_order (service_order_total_value, service_order_date, service_order_discount, service_order_status, vehicle_id, service_id) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setDouble(1, serviceOrder.getTotalValue());
            stmt.setDate(2, java.sql.Date.valueOf(serviceOrder.getOrderDate()));
            stmt.setDouble(3, serviceOrder.getDiscountValue());
            stmt.setString(4, serviceOrder.getOrderStatus().toString());
            stmt.setInt(5, serviceOrder.getVehicle().getVehicleId());
            stmt.setInt(6, serviceOrder.getService().getServiceId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                serviceOrder.setOrderNumber(generatedId);
            }
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ServiceOrderDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean updateServiceOrder(ServiceOrderModel serviceOrder) {
        String query = "UPDATE service_order SET service_id = ? WHERE service_order_number = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, serviceOrder.getService().getServiceId());
            stmt.setInt(2, serviceOrder.getOrderNumber());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ServiceOrderDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean deleteServiceOrder(ServiceOrderModel serviceOrder) {
        String query = "DELETE FROM service_order WHERE service_order_number = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, serviceOrder.getOrderNumber());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ServiceOrderDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public List<ServiceOrderModel> getAllServiceOrder() {
        String query = "SELECT * FROM service_order";
        List<ServiceOrderModel> serviceOrders = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ServiceOrderModel serviceOrder = populateServiceOrderList(rs);
                serviceOrders.add(serviceOrder);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServiceOrderDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return serviceOrders;
    }

    public ServiceOrderModel findServiceOrderByOrderNumber(ServiceOrderModel serviceOrder) {
        String query = "SELECT * FROM service_order WHERE service_order_number = ?";
        ServiceOrderModel rtServiceOrder = null;

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, serviceOrder.getOrderNumber());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rtServiceOrder = populateServiceOrderList(rs);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServiceOrderDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rtServiceOrder;
    }

    public ServiceOrderModel populateServiceOrderList(ResultSet rs) throws SQLException {
        ServiceOrderModel serviceOrder = new ServiceOrderModel();

        serviceOrder.setOrderNumber(Integer.parseInt(rs.getString("service_order_number")));
        serviceOrder.setOrderDiscount(rs.getDouble("service_order_discount"));
        return serviceOrder;
    }
}
